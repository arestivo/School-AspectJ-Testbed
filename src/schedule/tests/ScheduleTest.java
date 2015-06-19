package schedule.tests;

import infrastructure.Room;
import infrastructure.RoomFactory;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

import junit.framework.TestCase;
import people.PersonFactory;
import people.Teacher;
import schedule.Lecture;
import schedule.LectureFactory;
import teaching.Instance;
import teaching.InstanceFactory;
import courses.CourseFactory;

@PackageName("schedule")
@TestFor("schedule")
public class ScheduleTest extends TestCase {
	public void testSchedule() {
		Teacher t = PersonFactory.createTeacher("Jonh", "125 St Something Street");
		Room r = RoomFactory.createRoom(103);
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		
		Lecture l = LectureFactory.createLecture(t, r, i, Lecture.WEEKDAY.MONDAY, 10);

		assertEquals(t, l.getTeacher());
		assertEquals(r, l.getRoom());
		assertEquals(i, l.getInstance());
		assertEquals(Lecture.WEEKDAY.MONDAY, l.getDay());
		assertEquals(10, l.getHour());
	}
}
