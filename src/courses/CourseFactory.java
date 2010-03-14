package courses;

public class CourseFactory {
	public static Course createCourse(String name) {
		Course c = new Course(name);
		if (Course.addCourse(c)) return c;
		return null;
	}
}
