package permission;

import infrastructure.Room;
import infrastructure.RoomFactory;
import grading.Evaluation;  
import grading.EvaluationFactory;
import authentication.Authentication;
import people.Administrator;
import people.Person;
import people.PersonFactory;
import people.Student;
import people.Teacher;
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
	pointcut createEvaluation(Evaluation.TYPE type, int weight, Instance instance) 
	: call(Evaluation EvaluationFactory.createEvaluation(Evaluation.TYPE, int, Instance)) && args(type, weight, instance);
	pointcut createGrade(Evaluation evaluation, Student student, int grade) 
	: call(Grade GradeFactory.createGrade(Evaluation, Student, int)) && args(evaluation, student, grade);

	pointcut changePassword() : call(void Person.setPassword(..));	
	
	Room around() : createRoom() {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) throw new PermissionException(PermissionException.NEEDS_ADMIN);
		return proceed();
	}

	Course around() : createCourse() {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator))  throw new PermissionException(PermissionException.NEEDS_ADMIN);
		return proceed();
	}

	Administrator around() : createAdministrator() {
		if (Person.getPeople().size()==0) return proceed();
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) throw new PermissionException(PermissionException.NEEDS_ADMIN);
		return proceed();
	}

	Teacher around() : createTeacher() {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) throw new PermissionException(PermissionException.NEEDS_ADMIN);
		return proceed();
	}

	Student around() : createStudent() {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) throw new PermissionException(PermissionException.NEEDS_ADMIN);
		return proceed();
	}

	Instance around() : createInstance() {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) throw new PermissionException(PermissionException.NEEDS_ADMIN);
		return proceed();
	}

	Evaluation around(Evaluation.TYPE type, int weight, Instance instance) : createEvaluation(type, weight, instance) {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Teacher)) throw new PermissionException(PermissionException.NEEDS_TEACHER);
		if (!instance.hasTeacher((Teacher) Authentication.aspectOf().getCurrentUser())) throw new PermissionException(PermissionException.NEEDS_INSTANCE_TEACHER);
		return proceed(type, weight, instance);
	}

	Grade around(Evaluation evaluation, Student student, int grade) : createGrade(evaluation, student, grade) {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Teacher)) throw new PermissionException(PermissionException.NEEDS_TEACHER);

		Instance instance = evaluation.getInstance();
		if (!instance.hasTeacher((Teacher) Authentication.aspectOf().getCurrentUser())) throw new PermissionException(PermissionException.NEEDS_INSTANCE_TEACHER);
		
		return proceed(evaluation, student, grade);
	}

	Lecture around() : createLecture() {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) throw new PermissionException(PermissionException.NEEDS_ADMIN);
		return proceed();
	}

	void around() : changePassword() {
		if (Person.getPeople().size()==1) 
			proceed();
		else {
			if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
			if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) throw new PermissionException(PermissionException.NEEDS_ADMIN);
			proceed();
		}
	}
}