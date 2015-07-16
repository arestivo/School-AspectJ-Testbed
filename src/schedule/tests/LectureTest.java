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
import schedule.Lecture;
import schedule.LectureFactory;
import schedule.Schedule;
import schedule.ScheduleFactory;
import courses.CourseFactory;

@PackageName("schedule")
@TestFor("schedule")
public class LectureTest extends TestCase {
	public void testLecture() {
		Teacher t = PersonFactory.createTeacher("Jonh", "125 St Something Street");
		Room r = RoomFactory.createRoom(103);
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		
		Schedule s = ScheduleFactory.createSchedule(t, r, i, Schedule.WEEKDAY.MONDAY, 10);

		Lecture l1 = LectureFactory.createLecture(s, 1);
		Lecture l2 = LectureFactory.createLecture(s, 2);
		
		assertEquals(s, l1.getSchedule());
		assertEquals(s, l2.getSchedule());

		assertEquals(1, l1.getNumber());
		assertEquals(2, l2.getNumber());
		
		assertEquals(Lecture.STATE.SCHEDULED, l1.getState());
		l1.cancel();
		assertEquals(Lecture.STATE.CANCELED, l1.getState());

		assertEquals(Lecture.STATE.SCHEDULED, l2.getState());
		l2.present();
		assertEquals(Lecture.STATE.PRESENTED, l2.getState());
	}
}
