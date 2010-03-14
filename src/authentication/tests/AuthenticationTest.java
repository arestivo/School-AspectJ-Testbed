package authentication.tests;

import junit.framework.TestCase;
import people.Administrator;
import people.PersonFactory;
import people.Student;
import authentication.Authentication;

import com.feup.contribution.aida.annotations.TestFor;

@TestFor("authentication")
public class AuthenticationTest extends TestCase {

	public void testAuthentication() {
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		assertEquals(admin, Authentication.aspectOf().getCurrentUser());

		Student s = PersonFactory.createStudent("Jonh", "103 St. James Street");
		s.setLogin("jonh");
		s.setPassword("1234");
		
		Authentication.aspectOf().authenticate("jonh", "1234");
		assertEquals(s, Authentication.aspectOf().getCurrentUser());
	}
}
