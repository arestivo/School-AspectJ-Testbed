package courses.tests;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

import junit.framework.TestCase;
import courses.Course;
import courses.CourseFactory;

@PackageName("courses")
@TestFor("courses")
public class CourseTest extends TestCase {
	
	public void testCreateCourse(){
		assertEquals(0, Course.getCourses().size());
		
		Course c1 = CourseFactory.createCourse("Programming 101");
		assertEquals(1, Course.getCourses().size());
		assertEquals("Programming 101", c1.getName());
		assertEquals(c1, Course.getCourse("Programming 101"));

		Course c2 = CourseFactory.createCourse("Programming 201");
		assertEquals(2, Course.getCourses().size());
		assertEquals("Programming 201", c2.getName());
		assertEquals(c2, Course.getCourse("Programming 201"));
	}

	public void testRemoveCourse(){
		assertEquals(0, Course.getCourses().size());
		
		Course c1 = CourseFactory.createCourse("Programming 101");
		Course c2 = CourseFactory.createCourse("Programming 201");
		
		assertNotNull(Course.getCourse("Programming 101"));
		Course.removeCourse(c1);
		assertNull(Course.getCourse("Programming 101"));
		assertNotNull(Course.getCourse("Programming 201"));
		Course.removeCourse(c2);
		assertNull(Course.getCourse("Programming 201"));
	}

}
