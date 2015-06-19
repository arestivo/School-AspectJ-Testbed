package permission.tests;

import junit.framework.TestCase;
import people.Administrator;
import people.PersonFactory;
import permission.PermissionException;
import teaching.Instance;
import teaching.InstanceFactory;
import authentication.Authentication;
import authentication.AuthenticationException;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.ReplaceTest;
import com.feup.contribution.aida.annotations.TestFor;

import courses.CourseFactory;

@PackageName("permission")
@TestFor("permission")
public class InstancePermissionsTest extends TestCase {

	@ReplaceTest("teaching.tests.TestInstance.testInstanceList")
	public void testCreateInstanceNoLogin() {
		try {
			InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());
		}
		assertEquals(0, Instance.getInstances().size());
	}
		
	@ReplaceTest("teaching.tests.TestInstance.testInstanceList")
	public void testCreateInstanceAdmin() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		assertEquals(1, Instance.getInstances().size());
	}
}