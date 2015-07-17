package security.tests;

import junit.framework.TestCase;
import people.Administrator;
import people.PersonFactory;
import authentication.Authentication;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.ReplaceTest;
import com.feup.contribution.aida.annotations.TestFor;

@PackageName("security")
@TestFor("security")
public class TestSecurity extends TestCase {
	@ReplaceTest("authentication.tests.AuthenticationTest.testSetCredentials")
	public void testArePasswordsHashed() {
		Administrator admin = PersonFactory.createAdministrator("John", "Somewhere");
		admin.setLogin("john");
		admin.setPassword("1234");
		assertFalse("1234".equals(admin.getPassword()));
	}
	
	public void testAuthenticate() {
		Administrator admin = PersonFactory.createAdministrator("John", "Somewhere");
		admin.setLogin("john");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("john", "1234");
		assertSame (admin, Authentication.aspectOf().getCurrentUser());
	}
	
}
