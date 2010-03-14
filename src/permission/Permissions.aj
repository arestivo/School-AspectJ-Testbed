package permission;

import grading.Avaliation;  
import grading.AvaliationFactory;
import authentication.Authentication;
import people.Administrator;
import people.Person;
import people.PersonFactory;
import people.Student;
import people.Teacher;
import structure.Room;
import structure.RoomFactory;
import teaching.Instance;
import teaching.InstanceFactory;
import courses.Course;
import courses.CourseFactory;
import grading.Grade;
import grading.GradeFactory;
import schedule.Lecture;
import schedule.LectureFactory;

public aspect Permissions {
	pointcut createRoom() : call(Room RoomFactory.createRoom(..));
	pointcut createCourse() : call(Course CourseFactory.createCourse(..));
	pointcut createAdministrator() : call(Administrator PersonFactory.createAdministrator(..));
	pointcut createTeacher() : call(Teacher PersonFactory.createTeacher(..));
	pointcut createStudent() : call(Student PersonFactory.createStudent(..));
	pointcut createInstance() : call(Instance InstanceFactory.createInstance(..));
	pointcut createLecture() : call(Lecture LectureFactory.createLecture(..));
	pointcut createAvaliation(Avaliation.TYPE type, int weight, Instance instance) 
	: call(Avaliation AvaliationFactory.createAvaliation(Avaliation.TYPE, int, Instance)) && args(type, weight, instance);
	pointcut createGrade(Avaliation avaliation, Student student, int grade) 
	: call(Grade GradeFactory.createGrade(Avaliation, Student, int)) && args(avaliation, student, grade);

	pointcut changePassword() : call(void Person.setPassword(..));	
	
	Room around() : createRoom() {
		if (Authentication.aspectOf().getCurrentUser() == null) return null;
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) return null;
		return proceed();
	}

	Course around() : createCourse() {
		if (Authentication.aspectOf().getCurrentUser() == null) return null;
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) return null;
		return proceed();
	}

	Administrator around() : createAdministrator() {
		if (Person.getPeople().size()==0) return proceed();
		if (Authentication.aspectOf().getCurrentUser() == null) return null;
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) return null;
		return proceed();
	}

	Teacher around() : createTeacher() {
		if (Authentication.aspectOf().getCurrentUser() == null) return null;
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) return null;
		return proceed();
	}

	Student around() : createStudent() {
		if (Authentication.aspectOf().getCurrentUser() == null) return null;
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) return null;
		return proceed();
	}

	Instance around() : createInstance() {
		if (Authentication.aspectOf().getCurrentUser() == null) return null;
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) return null;
		return proceed();
	}

	Avaliation around(Avaliation.TYPE type, int weight, Instance instance) : createAvaliation(type, weight, instance) {
		if (Authentication.aspectOf().getCurrentUser() == null) return null;
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Teacher)) return null;
		if (!instance.hasTeacher((Teacher) Authentication.aspectOf().getCurrentUser())) return null;
		return proceed(type, weight, instance);
	}

	Grade around(Avaliation avaliation, Student student, int grade) : createGrade(avaliation, student, grade) {
		if (Authentication.aspectOf().getCurrentUser() == null) return null;
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Teacher)) return null;
		Instance instance = avaliation.getInstance();
		if (!instance.hasTeacher((Teacher) Authentication.aspectOf().getCurrentUser())) return null;
		return proceed(avaliation, student, grade);
	}

	Lecture around() : createLecture() {
		if (Authentication.aspectOf().getCurrentUser() == null) return null;
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) return null;
		return proceed();
	}

	void around() : changePassword() {
		if (Person.getPeople().size()==1) proceed();
		if (Authentication.aspectOf().getCurrentUser() == null) return;
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) return;
		proceed();
	}
}
