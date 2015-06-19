package schedule;

import infrastructure.Room;
import instance.Instance;
import people.Teacher;
import schedule.Lecture.WEEKDAY;

public class LectureFactory {
	public static Lecture createLecture(Teacher t, Room r, Instance i, WEEKDAY w, int h) {
		Lecture l = new Lecture(t, r, i, w, h);
		Lecture.addLecture(l);
		return l;
	}
}
