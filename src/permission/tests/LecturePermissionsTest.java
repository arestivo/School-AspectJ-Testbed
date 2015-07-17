package permission.tests;

import infrastructure.Room;
import infrastructure.RoomFactory;
import instance.Instance;
import instance.InstanceFactory;
import junit.framework.TestCase;
import people.Administrator;
import people.PersonFactory;
import people.Teacher;
import permission.exceptions.PermissionException;
import schedule.Lecture;
import schedule.LectureFactory;
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
public class LecturePermissionsTest extends TestCase {
	@ReplaceTest("schedule.tests.LectureTest.testCreateLecture")
	public void testCreateLectureNoLogin() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Teacher teacher = PersonFactory.createTeacher("John", "125 St Something Street");

		Room room = RoomFactory.createRoom(103);
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		
		Schedule schedule = ScheduleFactory.createSchedule(teacher, room, i, Schedule.WEEKDAY.MONDAY, 10);

		Authentication.aspectOf().logoff();
		try {
			LectureFactory.createLecture(schedule, 1);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());						
		}
	}

	@ReplaceTest("schedule.tests.LectureTest.testCreateLecture")
	public void testChangeLectureState() {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Teacher teacher = PersonFactory.createTeacher("John", "125 St Something Street");

		Room room = RoomFactory.createRoom(103);
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		
		Schedule schedule = ScheduleFactory.createSchedule(teacher, room, i, Schedule.WEEKDAY.MONDAY, 10);

		try {
			LectureFactory.createLecture(schedule, 1);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_TEACHER, e.getMessage());						
		}

		Authentication.aspectOf().logoff();

		try {
			LectureFactory.createLecture(schedule, 1);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());						
		}

		Authentication.aspectOf().authenticate("admin", "1234");

		teacher.setLogin("teacher");
		teacher.setPassword("1111");

		Authentication.aspectOf().authenticate("teacher", "1111");		
		
		try {
			LectureFactory.createLecture(schedule, 1);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_INSTANCE_TEACHER, e.getMessage());						
		}

		i.addTeacher(teacher);
		Lecture lecture = LectureFactory.createLecture(schedule, 1);
		
		assertEquals(schedule, lecture.getSchedule());		
	}
}