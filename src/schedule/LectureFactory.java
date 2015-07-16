package schedule;

import java.util.Date;

import infrastructure.Room;
import instance.Instance;
import people.Teacher;
import schedule.Schedule.WEEKDAY;

public class LectureFactory {
	public static Lecture createLecture(Schedule schedule, int number) {
		Lecture l = new Lecture(schedule, number);
		return l;
	}
}
