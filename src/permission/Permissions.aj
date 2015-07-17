package permission;

import infrastructure.Room;
import infrastructure.RoomFactory;
import instance.Instance;
import instance.InstanceFactory;
import grading.Evaluation;  
import grading.EvaluationFactory;
import authentication.Authentication;
import people.Administrator;
import people.Person;
import people.PersonFactory;
import people.Student;
import people.Teacher;
import permission.exceptions.PermissionException;
import courses.Course;
import courses.CourseFactory;
import grading.Grade;
import grading.GradeFactory;
import schedule.Schedule;
import schedule.ScheduleFactory;
import schedule.Lecture;
import schedule.LectureFactory;

public aspect Permissions {
	pointcut createRoom() : call(Room RoomFactory.createRoom(..));
	pointcut createCourse() : call(Course CourseFactory.createCourse(..));
	pointcut removeCourse() : call(void Course.removeCourse(..));
	pointcut createAdministrator() : call(Administrator PersonFactory.createAdministrator(..));
	pointcut createTeacher() : call(Teacher PersonFactory.createTeacher(..));
	pointcut createStudent() : call(Student PersonFactory.createStudent(..));
	pointcut createInstance() : call(Instance InstanceFactory.createInstance(..));
	pointcut createSchedule() : call(Schedule ScheduleFactory.createSchedule(..));
	pointcut createLecture(Schedule schedule) : call(Lecture LectureFactory.createLecture(Schedule, int)) && args(schedule, ..);
	pointcut changeLectureState(Lecture lecture) : call(void Lecture.cancel()) && target(lecture) || call(void Lecture.present()) && target(lecture) ;
	pointcut changeAttendanceState(Lecture lecture) : call(void Lecture.addAttendance(..)) && target(lecture) || call(void Lecture.removeAttendance(..)) && target(lecture) ;
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

	void around() : removeCourse() {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator))  throw new PermissionException(PermissionException.NEEDS_ADMIN);
		proceed();
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

	Schedule around() : createSchedule() {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Administrator)) throw new PermissionException(PermissionException.NEEDS_ADMIN);
		return proceed();
	}

	Lecture around(Schedule schedule) : createLecture(schedule) {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Teacher)) throw new PermissionException(PermissionException.NEEDS_TEACHER);

		Instance instance = schedule.getInstance();
		if (!instance.hasTeacher((Teacher) Authentication.aspectOf().getCurrentUser())) throw new PermissionException(PermissionException.NEEDS_INSTANCE_TEACHER);

		return proceed(schedule);
	}

	void around(Lecture lecture) : changeLectureState(lecture) {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Teacher)) throw new PermissionException(PermissionException.NEEDS_TEACHER);

		Instance instance = lecture.getSchedule().getInstance();
		if (!instance.hasTeacher((Teacher) Authentication.aspectOf().getCurrentUser())) throw new PermissionException(PermissionException.NEEDS_INSTANCE_TEACHER);

		proceed(lecture);
	}

	void around(Lecture lecture) : changeAttendanceState(lecture) {
		if (Authentication.aspectOf().getCurrentUser() == null) throw new PermissionException(PermissionException.NEEDS_LOGIN);
		if (!(Authentication.aspectOf().getCurrentUser() instanceof Teacher)) throw new PermissionException(PermissionException.NEEDS_TEACHER);

		Instance instance = lecture.getSchedule().getInstance();
		if (!instance.hasTeacher((Teacher) Authentication.aspectOf().getCurrentUser())) throw new PermissionException(PermissionException.NEEDS_INSTANCE_TEACHER);

		proceed(lecture);
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