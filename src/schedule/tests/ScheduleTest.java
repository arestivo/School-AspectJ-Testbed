package schedule.tests;

import infrastructure.Room;
import infrastructure.RoomFactory;
import instance.Instance;
import instance.InstanceFactory;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

import junit.framework.TestCase;
import people.PersonFactory;
import people.Teacher;
import schedule.Schedule;
import schedule.ScheduleFactory;
import courses.CourseFactory;

@PackageName("schedule")
@TestFor("schedule")
public class ScheduleTest extends TestCase {
	public void testSchedule() {
		Teacher t = PersonFactory.createTeacher("Jonh", "125 St Something Street");
		Room r = RoomFactory.createRoom(103);
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		
		Schedule s = ScheduleFactory.createSchedule(t, r, i, Schedule.WEEKDAY.MONDAY, 10);

		assertEquals(t, s.getTeacher());
		assertEquals(r, s.getRoom());
		assertEquals(i, s.getInstance());
		assertEquals(Schedule.WEEKDAY.MONDAY, s.getDay());
		assertEquals(10, s.getHour());
	}
}
