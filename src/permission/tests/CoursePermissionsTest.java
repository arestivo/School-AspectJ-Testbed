package permission.tests;

import junit.framework.TestCase;
import people.Administrator;
import people.PersonFactory;
import people.Student;
import people.Teacher;
import permission.exceptions.PermissionException;
import authentication.Authentication;
import authentication.exceptions.AuthenticationException;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.ReplaceTest;
import com.feup.contribution.aida.annotations.TestFor;

import courses.Course;
import courses.CourseFactory;

@PackageName("permission")
@TestFor("permission")
public class CoursePermissionsTest extends TestCase {

	@ReplaceTest("courses.tests.CourseTest.testCreateCourse")
	public void testCreateCourseNoLogin() {
		try {
			CourseFactory.createCourse("Programming 101");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());
		}
	}

	@ReplaceTest("courses.tests.CourseTest.testRemoveCourse")
	public void testRemoveCourseNoLogin() {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");

		admin.setLogin("admin");
		admin.setPassword("1234");
		
		Authentication.aspectOf().authenticate("admin", "1234");
		Course course = CourseFactory.createCourse("Programming 101");
		Authentication.aspectOf().logoff();

		try {
			Course.removeCourse(course);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());
		}
	}
	
	@ReplaceTest("courses.tests.CourseTest.testCreateCourse")
	public void testCreateCourseStudent() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");

		admin.setLogin("admin");
		admin.setPassword("1234");
		
		Authentication.aspectOf().authenticate("admin", "1234");
		Student s = PersonFactory.createStudent("John", "103 St. James Street");
		s.setLogin("john");
		s.setPassword("1234");
		
		Authentication.aspectOf().authenticate("john", "1234");

		try {
			CourseFactory.createCourse("Programming 101");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());
		}
	}
	
	@ReplaceTest("courses.tests.CourseTest.testCreateCourse")
	public void testCreateCourseTeacher() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");

		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		Teacher t = PersonFactory.createTeacher("John", "103 St. James Street");
		t.setLogin("john2");
		t.setPassword("1234");
		
		Authentication.aspectOf().authenticate("john2", "1234");

		try {
			CourseFactory.createCourse("Programming 101");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());			
		}
	}
	
	@ReplaceTest("courses.tests.CourseTest.testCreateCourse")
	public void testCreateCourseAdmin() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
	
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		assertNotNull(CourseFactory.createCourse("Programming 101"));
	}

	@ReplaceTest("courses.tests.CourseTest.testRemoveCourse")
	public void testRemoveCourseAdmin() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
	
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Course course = CourseFactory.createCourse("Programming 101");
		Course.removeCourse(course);
		assertNull(Course.getCourse("Programming 101"));
	}
}