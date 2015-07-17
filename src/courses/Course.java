package courses;

import java.util.Collection; 
import java.util.HashMap;

public class Course{
	private String name;

	private static HashMap<String, Course> courses = new HashMap<String, Course>();
	
	protected Course(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static boolean addCourse(Course c){
		if (courses.containsKey(c.getName())) return false;
		courses.put(c.getName(), c);
		return true;
	}

	public static Collection<Course> getCourses() {
		return courses.values();
	}

	public static Course getCourse(String name) {
		return courses.get(name);
	}
	
	public static void removeCourse(Course course) {
		courses.remove(course.getName());
	}
}
