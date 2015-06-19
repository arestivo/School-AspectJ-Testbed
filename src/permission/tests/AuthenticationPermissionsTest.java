package permission.tests;

import junit.framework.TestCase;
import people.Administrator;
import people.PersonFactory;
import people.Student;
import permission.exceptions.PermissionException;
import authentication.Authentication;
import authentication.exceptions.AuthenticationException;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.ReplaceTest;
import com.feup.contribution.aida.annotations.TestFor;

@PackageName("permission")
@TestFor("permission")
public class AuthenticationPermissionsTest extends TestCase {
	@ReplaceTest("authentication.tests.AuthenticationTest.testAuthentication")
	public void testAuthenticationPermissions() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Student s = PersonFactory.createStudent("John", "103 St. James Street");
		s.setLogin("john");
		s.setPassword("1234");
		assertEquals("1234", s.getPassword());
	
		Authentication.aspectOf().authenticate("john", "1234");
		try {
			s.setPassword("4321");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());			
		}
		assertEquals("1234", s.getPassword());

		Authentication.aspectOf().authenticate("admin", "1234");
		s.setPassword("4321");
		assertEquals("4321", s.getPassword());		
	}
	
}
