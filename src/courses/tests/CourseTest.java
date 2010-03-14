package courses.tests;

import com.feup.contribution.aida.annotations.TestFor;

import junit.framework.TestCase;
import courses.Course;
import courses.CourseFactory;

@TestFor("courses")
public class CourseTest extends TestCase {
	
	public void testCourseList(){
		assertEquals(0, Course.getCourses().size());
		CourseFactory.createCourse("Programming 101");
		assertEquals(1, Course.getCourses().size());
		CourseFactory.createCourse("Programming 201");
		assertEquals(2, Course.getCourses().size());
	}
}
