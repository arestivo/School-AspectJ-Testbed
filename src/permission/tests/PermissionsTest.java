package permission.tests;

import infrastructure.Room;
import infrastructure.RoomFactory;
import grading.Avaliation;
import grading.AvaliationFactory;
import grading.GradeFactory;
import people.Administrator;
import people.PersonFactory;
import people.Student;
import people.Teacher;
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
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		Student s = PersonFactory.createStudent("Jonh", "103 St. James Street");
		s.setLogin("jonh");
		s.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh", "1234");

		assertNull(RoomFactory.createRoom(100));

		Authentication.aspectOf().authenticate("admin", "1234");
		Teacher t = PersonFactory.createTeacher("Jonh", "103 St. James Street");
		t.setLogin("jonh2");
		t.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh2", "1234");

		assertNull(RoomFactory.createRoom(100));

		Authentication.aspectOf().authenticate("admin", "1234");
		Administrator a = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		a.setLogin("jonh3");
		a.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh3", "1234");

		assertNotNull(RoomFactory.createRoom(100));
	}

	@ReplaceTest("courses.tests.CourseTest.testCourseList")
	public void testCoursePermissions() {
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		Student s = PersonFactory.createStudent("Jonh", "103 St. James Street");
		s.setLogin("jonh");
		s.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh", "1234");

		assertNull(CourseFactory.createCourse("Programming 101"));

		Authentication.aspectOf().authenticate("admin", "1234");
		Teacher t = PersonFactory.createTeacher("Jonh", "103 St. James Street");
		t.setLogin("jonh2");
		t.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh2", "1234");

		assertNull(CourseFactory.createCourse("Programming 101"));

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

		assertNull(PersonFactory.createAdministrator("Jonh", "103 St. James Street"));
		assertNull(PersonFactory.createStudent("Jonh", "103 St. James Street"));
		assertNull(PersonFactory.createTeacher("Jonh", "103 St. James Street"));

		Authentication.aspectOf().authenticate("admin", "1234");

		assertNotNull(PersonFactory.createAdministrator("Jonh", "103 St. James Street"));
		assertNotNull(PersonFactory.createStudent("Jonh", "103 St. James Street"));
		assertNotNull(PersonFactory.createTeacher("Jonh", "103 St. James Street"));
		
	}
	
	@ReplaceTest("teaching.tests.TestInstance.testInstanceList")
	public void testInstancePermissions() {
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		assertEquals(0, Instance.getInstances().size());

		InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		assertEquals(0, Instance.getInstances().size());

		Authentication.aspectOf().authenticate("admin", "1234");

		InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		assertEquals(1, Instance.getInstances().size());
	}

	@ReplaceTest("grading.tests.TestAvaliation.testAvaliation")
	public void testAvaliationPermissions() {
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		
		Teacher t = PersonFactory.createTeacher("Jonh", "104 St Saint Street");
		t.setLogin("jonh");
		t.setPassword("1234");
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programmin 101"), 2010);
		
		assertEquals(0, Avaliation.getAvaliations().size());

		AvaliationFactory.createAvaliation(Avaliation.TYPE.EXAM, 20, i);
		assertEquals(0, Avaliation.getAvaliations().size());

		Authentication.aspectOf().authenticate("jonh", "1234");

		AvaliationFactory.createAvaliation(Avaliation.TYPE.EXAM, 20, i);
		assertEquals(0, Avaliation.getAvaliations().size());

		i.addTeacher(t);

		AvaliationFactory.createAvaliation(Avaliation.TYPE.EXAM, 20, i);
		assertEquals(1, Avaliation.getAvaliations().size());
		AvaliationFactory.createAvaliation(Avaliation.TYPE.TEST, 30, i);
		assertEquals(2, Avaliation.getAvaliations().size());
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
		
		i.addTeacher(t);

		Avaliation a1 = AvaliationFactory.createAvaliation(Avaliation.TYPE.EXAM, 20, i);
		Avaliation a2 = AvaliationFactory.createAvaliation(Avaliation.TYPE.TEST, 30, i);
		
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
		assertNull(LectureFactory.createLecture(t, r, i, Lecture.WEEKDAY.MONDAY, 10));

		Authentication.aspectOf().authenticate("jonh2", "1234");
		assertNull(LectureFactory.createLecture(t, r, i, Lecture.WEEKDAY.MONDAY, 10));

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
		s.setPassword("4321");
		assertEquals("1234", s.getPassword());

		Authentication.aspectOf().authenticate("admin", "1234");
		s.setPassword("4321");
		assertEquals("4321", s.getPassword());		
	}
	
}
