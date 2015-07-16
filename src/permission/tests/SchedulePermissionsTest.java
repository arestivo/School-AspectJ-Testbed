package permission.tests;

import infrastructure.Room;
import infrastructure.RoomFactory;
import instance.Instance;
import instance.InstanceFactory;
import junit.framework.TestCase;
import people.Administrator;
import people.PersonFactory;
import people.Student;
import people.Teacher;
import permission.exceptions.PermissionException;
import schedule.Schedule;
import schedule.ScheduleFactory;
import authentication.Authentication;
import authentication.exceptions.AuthenticationException;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.ReplaceTest;
import com.feup.contribution.aida.annotations.TestFor;

import courses.CourseFactory;

@PackageName("permission")
@TestFor("permission")
public class SchedulePermissionsTest extends TestCase {
	@ReplaceTest("schedule.tests.ScheduleTest.testSchedule")
	public void testCreateScheduleNoLogin() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Teacher teacher = PersonFactory.createTeacher("John", "125 St Something Street");

		Room room = RoomFactory.createRoom(103);
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		
		Authentication.aspectOf().logoff();
		try {
			ScheduleFactory.createSchedule(teacher, room, i, Schedule.WEEKDAY.MONDAY, 10);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());						
		}
	}

	@ReplaceTest("schedule.tests.ScheduleTest.testSchedule")
	public void testCreateScheduleStudent() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Student student = PersonFactory.createStudent("John", "103 St. James Street");
		student.setLogin("john");
		student.setPassword("1234");

		Teacher teacher = PersonFactory.createTeacher("John", "125 St Something Street");

		Room room = RoomFactory.createRoom(103);
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		
		Authentication.aspectOf().authenticate("john", "1234");
		try {
			ScheduleFactory.createSchedule(teacher, room, i, Schedule.WEEKDAY.MONDAY, 10);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());			
		}
	}

	@ReplaceTest("schedule.tests.ScheduleTest.testSchedule")
	public void testCreateScheduleTeacher() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Teacher teacher = PersonFactory.createTeacher("John", "125 St Something Street");
		teacher.setLogin("mary");
		teacher.setPassword("1234");

		Room room = RoomFactory.createRoom(103);
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		
		Authentication.aspectOf().authenticate("mary", "1234");
		try {
			ScheduleFactory.createSchedule(teacher, room, i, Schedule.WEEKDAY.MONDAY, 10);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());						
		}
	}

	@ReplaceTest("schedule.tests.ScheduleTest.testSchedule")
	public void testCreateScheduleAdmin() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Teacher teacher = PersonFactory.createTeacher("John", "125 St Something Street");

		Room room = RoomFactory.createRoom(103);
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);

		Authentication.aspectOf().authenticate("admin", "1234");
		assertNotNull(ScheduleFactory.createSchedule(teacher, room, i, Schedule.WEEKDAY.MONDAY, 10));
	}
}