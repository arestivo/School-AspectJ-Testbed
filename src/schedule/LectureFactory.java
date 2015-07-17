package schedule;


public class LectureFactory {
	public static Lecture createLecture(Schedule schedule, int number) {
		Lecture l = new Lecture(schedule, number);
		return l;
	}
}
