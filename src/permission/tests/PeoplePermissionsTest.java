package permission.tests;

import junit.framework.TestCase;
import people.Administrator;
import people.Person;
import people.PersonFactory;
import people.Student;
import people.Teacher;
import permission.exceptions.PermissionException;
import authentication.Authentication;
import authentication.exceptions.AuthenticationException;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.ReplaceTest;
import com.feup.contribution.aida.annotations.TestFor;

@PackageName("permission")
@TestFor("permission")
public class PeoplePermissionsTest extends TestCase {

	@ReplaceTest("people.tests.TestPerson.testCreatePeople,people.tests.TestPerson.testCreateStudent,people.tests.TestPerson.testCreateTeacher,people.tests.TestPerson.testCreateAdmin")
	public void testCreatePeopleNoLogin() {		
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		try {
			PersonFactory.createAdministrator("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());
		}
		
		try {
			PersonFactory.createStudent("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());
		}
	
		try {
			PersonFactory.createTeacher("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());			
		}

		assertEquals(1, Person.getPeople().size());
	}
	
	@ReplaceTest("people.tests.TestPerson.testCreatePeople,people.tests.TestPerson.testCreateStudent,people.tests.TestPerson.testCreateTeacher,people.tests.TestPerson.testCreateAdmin")
	public void testCreatePeopleStudent() throws AuthenticationException {		
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");
		
		Authentication.aspectOf().authenticate("admin", "1234");

		Student student = PersonFactory.createStudent("John", "103 St. James Street");
		student.setLogin("john");
		student.setPassword("1234");

		Authentication.aspectOf().authenticate("john", "1234");
		
		try {
			PersonFactory.createAdministrator("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());
		}
		
		try {
			PersonFactory.createStudent("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());
		}
	
		try {
			PersonFactory.createTeacher("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());			
		}

		assertEquals(2, Person.getPeople().size());
	}	

	@ReplaceTest("people.tests.TestPerson.testCreatePeople,people.tests.TestPerson.testCreateStudent,people.tests.TestPerson.testCreateTeacher,people.tests.TestPerson.testCreateAdmin")
	public void testCreatePeopleTeacher() throws AuthenticationException {		
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");
		
		Authentication.aspectOf().authenticate("admin", "1234");

		Teacher teacher = PersonFactory.createTeacher("John", "103 St. James Street");
		teacher.setLogin("john");
		teacher.setPassword("1234");

		Authentication.aspectOf().authenticate("john", "1234");
		
		try {
			PersonFactory.createAdministrator("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());
		}
		
		try {
			PersonFactory.createStudent("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());
		}
	
		try {
			PersonFactory.createTeacher("Jonh", "103 St. James Street");
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());			
		}

		assertEquals(2, Person.getPeople().size());
	}		
	
	@ReplaceTest("people.tests.TestPerson.testCreatePeople,people.tests.TestPerson.testCreateStudent,people.tests.TestPerson.testCreateTeacher,people.tests.TestPerson.testCreateAdmin")
	public void testCreatePeopleAdmin() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("Jonh", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		assertNotNull(PersonFactory.createAdministrator("Jonh", "103 St. James Street"));
		assertNotNull(PersonFactory.createStudent("Jonh", "103 St. James Street"));
		assertNotNull(PersonFactory.createTeacher("Jonh", "103 St. James Street"));
		
		assertEquals(4, Person.getPeople().size());
	}
}