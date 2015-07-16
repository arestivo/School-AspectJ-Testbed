package attendance.tests;

import infrastructure.Room;
import infrastructure.RoomFactory;
import instance.Instance;
import instance.InstanceFactory;
import junit.framework.TestCase;
import people.PersonFactory;
import people.Student;
import people.Teacher;
import schedule.Lecture;
import schedule.LectureFactory;
import schedule.Schedule;
import schedule.ScheduleFactory;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

import courses.CourseFactory;

@PackageName("attendance")
@TestFor("attendance")
public class AttendanceTest extends TestCase {

	public void testAttendance() {
		Teacher t = PersonFactory.createTeacher("Jonh", "125 St Something Street");
		Room r = RoomFactory.createRoom(103);
		
		Student s1 = PersonFactory.createStudent("Carl", "125 St Something Street");
		Student s2 = PersonFactory.createStudent("Mary", "125 St Something Street");
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		
		Schedule s = ScheduleFactory.createSchedule(t, r, i, Schedule.WEEKDAY.MONDAY, 10);

		Lecture l = LectureFactory.createLecture(s, 1);
		
		assertFalse(l.hasAttended(s1));
		assertFalse(l.hasAttended(s2));

		l.addAttendance(s1);
		
		assertTrue(l.hasAttended(s1));
		assertFalse(l.hasAttended(s2));
		
		l.addAttendance(s2);

		assertTrue(l.hasAttended(s1));
		assertTrue(l.hasAttended(s2));

		l.removeAttendance(s1);

		assertFalse(l.hasAttended(s1));
		assertTrue(l.hasAttended(s2));
}

}
