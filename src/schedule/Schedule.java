package schedule;

import infrastructure.Room;
import instance.Instance;

import java.util.HashSet; 

import people.Teacher;

public class Schedule {
	private static HashSet<Schedule> schedules = new HashSet<Schedule>();
	
	public enum WEEKDAY {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY};
	
	private Teacher teacher;

	private Instance instance;
	
	private Room room;
	
	private WEEKDAY day;

	private int hour;

	protected Schedule(Teacher t, Room r, Instance i, WEEKDAY w, int h) {
		teacher = t;
		room = r;
		instance = i;
		day = w;
		hour = h;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}
	
	public Instance getInstance() {
		return instance;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Room getRoom() {
		return room;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getHour() {
		return hour;
	}

	public void setDay(WEEKDAY day) {
		this.day = day;
	}

	public WEEKDAY getDay() {
		return day;
	}

	public static void addSchedule(Schedule l) {
		schedules.add(l);
	}
}