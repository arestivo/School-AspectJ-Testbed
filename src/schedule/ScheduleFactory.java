package schedule;

import infrastructure.Room;
import instance.Instance;
import people.Teacher;
import schedule.Schedule.WEEKDAY;

public class ScheduleFactory {
	public static Schedule createSchedule(Teacher t, Room r, Instance i, WEEKDAY w, int h) {
		Schedule s = new Schedule(t, r, i, w, h);
		Schedule.addSchedule(s);
		return s;
	}
}
