package permission.tests;

import infrastructure.Room;
import infrastructure.RoomFactory;
import grading.Evaluation;
import grading.EvaluationFactory;
import grading.GradeFactory;
import people.Administrator;
import people.Person;
import people.PersonFactory;
import people.Student;
import people.Teacher;
import permission.PermissionException;
import schedule.Lecture;
import schedule.LectureFactory;
import teaching.Instance;
import teaching.InstanceFactory;
import authentication.Authentication;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.ReplaceTest;
import com.feup.contribution.aida.annotations.TestFor;

import courses.CourseFactory;
import junit.framework.TestCase;

@PackageName("permission")
@TestFor("permission")
public class PermissionsTest extends TestCase {
	
	@ReplaceTest("infrastructure.tests.RoomTest.testRoomList")
	public void testRoomPermissions() {
		try {
			RoomFactory.createRoom(100);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());
		}
		
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		Student s = PersonFactory.createStudent("Jonh", "103 St. James Street");
		s.setLogin("jonh");
		s.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh", "1234");

		try {
			RoomFactory.createRoom(100);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());
		}

		Authentication.aspectOf().authenticate("admin", "1234");
		Teacher t = PersonFactory.createTeacher("Jonh", "103 St. James Street");
		t.setLogin("jonh2");
		t.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh2", "1234");

		try {
			RoomFactory.createRoom(100);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());
		}		

		Authentication.aspectOf().authenticate("admin", "1234");
		Administrator a = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		a.setLogin("jonh3");
		a.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh3", "1234");

		assertNotNull(RoomFactory.createRoom(100));
	}

	@ReplaceTest("courses.tests.CourseTest.testCourseList")
	public void testCoursePermissions() {
		try {
			CourseFactory.createCourse("Programming 101");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());
		}

		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");

		admin.setLogin("admin");
		admin.setPassword("1234");
		
		Authentication.aspectOf().authenticate("admin", "1234");
		Student s = PersonFactory.createStudent("Jonh", "103 St. James Street");
		s.setLogin("jonh");
		s.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh", "1234");

		try {
			CourseFactory.createCourse("Programming 101");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());
		}

		Authentication.aspectOf().authenticate("admin", "1234");
		Teacher t = PersonFactory.createTeacher("Jonh", "103 St. James Street");
		t.setLogin("jonh2");
		t.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh2", "1234");

		try {
			CourseFactory.createCourse("Programming 101");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());			
		}

		Authentication.aspectOf().authenticate("admin", "1234");
		Administrator a = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		a.setLogin("jonh3");
		a.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh3", "1234");

		assertNotNull(CourseFactory.createCourse("Programming 101"));
	}

	@ReplaceTest("people.tests.TestPerson.testPersonList")
	public void testPeoplePermissions() {		
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		try {
			PersonFactory.createAdministrator("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());
		}
		
		try {
			PersonFactory.createStudent("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());
		}

		try {
			PersonFactory.createTeacher("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());			
		}

		assertEquals(1, Person.getPeople().size());

		Authentication.aspectOf().authenticate("admin", "1234");

		assertNotNull(PersonFactory.createAdministrator("Jonh", "103 St. James Street"));
		assertNotNull(PersonFactory.createStudent("Jonh", "103 St. James Street"));
		assertNotNull(PersonFactory.createTeacher("Jonh", "103 St. James Street"));
		
		assertEquals(4, Person.getPeople().size());
	}
	
	@ReplaceTest("teaching.tests.TestInstance.testInstanceList")
	public void testInstancePermissions() {
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		assertEquals(0, Instance.getInstances().size());

		try {
			InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());
		}
		assertEquals(0, Instance.getInstances().size());

		Authentication.aspectOf().authenticate("admin", "1234");

		InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		assertEquals(1, Instance.getInstances().size());
	}

	@ReplaceTest("grading.tests.TestEvaluation.testEvaluation")
	public void testEvaluationPermissions() {
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		
		Teacher t = PersonFactory.createTeacher("Jonh", "104 St Saint Street");
		t.setLogin("jonh");
		t.setPassword("1234");
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programmin 101"), 2010);
		
		assertEquals(0, Evaluation.getEvaluations().size());

		try {
			EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_TEACHER, e.getMessage());			
		}
		assertEquals(0, Evaluation.getEvaluations().size());

		Authentication.aspectOf().authenticate("jonh", "1234");

		try {
			EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_INSTANCE_TEACHER, e.getMessage());			
		}
		assertEquals(0, Evaluation.getEvaluations().size());

		i.addTeacher(t);

		EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
		assertEquals(1, Evaluation.getEvaluations().size());
		
		EvaluationFactory.createEvaluation(Evaluation.TYPE.TEST, 30, i);
		assertEquals(2, Evaluation.getEvaluations().size());
	}

	@ReplaceTest("grading.tests.TestGrade.testGrade")
	public void testGradingPermissions() {
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		
		Student student = PersonFactory.createStudent("Jonh", "124 St Something Street");
		
		Teacher t = PersonFactory.createTeacher("Jonh", "104 St Saint Street");
		t.setLogin("jonh");
		t.setPassword("1234");
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programmin 101"), 2010);

		try {
			EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_TEACHER, e.getMessage());						
		}

		Authentication.aspectOf().authenticate("jonh", "1234");

		try {
			EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_INSTANCE_TEACHER, e.getMessage());			
		}
		
		i.addTeacher(t);

		Evaluation a1 = EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
		Evaluation a2 = EvaluationFactory.createEvaluation(Evaluation.TYPE.TEST, 30, i);
		
		GradeFactory.createGrade(a1, student, 10);
		GradeFactory.createGrade(a2, student, 10);
	}
	
	@ReplaceTest("schedule.tests.ScheduleTest.testSchedule")
	public void testSchedulePermissions() {
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Student s = PersonFactory.createStudent("Jonh", "103 St. James Street");
		Teacher t = PersonFactory.createTeacher("Jonh", "125 St Something Street");
		Room r = RoomFactory.createRoom(103);
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);

		s.setLogin("jonh1");
		s.setPassword("1234");
		t.setLogin("jonh2");
		t.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh1", "1234");
		try {
			LectureFactory.createLecture(t, r, i, Lecture.WEEKDAY.MONDAY, 10);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());			
		}

		Authentication.aspectOf().authenticate("jonh2", "1234");
		try {
			LectureFactory.createLecture(t, r, i, Lecture.WEEKDAY.MONDAY, 10);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());						
		}

		Authentication.aspectOf().authenticate("admin", "1234");
		assertNotNull(LectureFactory.createLecture(t, r, i, Lecture.WEEKDAY.MONDAY, 10));
	}

	@ReplaceTest("authentication.tests.AuthenticationTest.testAuthentication")
	public void testAuthenticationPermissions() {
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Student s = PersonFactory.createStudent("Jonh", "103 St. James Street");
		s.setLogin("jonh");
		s.setPassword("1234");
		assertEquals("1234", s.getPassword());
	
		Authentication.aspectOf().authenticate("jonh", "1234");
		try {
			s.setPassword("4321");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());			
		}
		assertEquals("1234", s.getPassword());

		Authentication.aspectOf().authenticate("admin", "1234");
		s.setPassword("4321");
		assertEquals("4321", s.getPassword());		
	}
	
}
