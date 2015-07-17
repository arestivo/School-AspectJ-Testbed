package authentication.tests;

import junit.framework.TestCase;
import people.Administrator;
import people.PersonFactory;
import people.Student;
import authentication.Authentication;
import authentication.exceptions.AuthenticationException;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

@PackageName("authentication")
@TestFor("authentication")
public class AuthenticationTest extends TestCase {

	public void testSetCredentials() {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");
		
		assertEquals("admin", admin.getLogin());
		assertEquals("1234", admin.getPassword());
	}
	
	public void testAuthenticate() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		assertEquals(admin, Authentication.aspectOf().getCurrentUser());

		Student s = PersonFactory.createStudent("John", "103 St. James Street");
		s.setLogin("john");
		s.setPassword("1234");
		
		Authentication.aspectOf().authenticate("john", "1234");
		assertEquals(s, Authentication.aspectOf().getCurrentUser());
	}

	public void testLogoff() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		assertEquals(admin, Authentication.aspectOf().getCurrentUser());

		Authentication.aspectOf().logoff();

		assertNull(Authentication.aspectOf().getCurrentUser());
	}

	public void testWrongCredentials() {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		try {
			Authentication.aspectOf().authenticate("admin", "4321");
			fail("Missing Exception");
		} catch (AuthenticationException e) {}

		assertNull(Authentication.aspectOf().getCurrentUser());
	}
}
