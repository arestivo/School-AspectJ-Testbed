package permission.tests;

import infrastructure.RoomFactory;
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

@PackageName("permission")
@TestFor("permission")
public class RoomPermissionsTest extends TestCase {
	
	@ReplaceTest("infrastructure.tests.RoomTest.testCreateRoom")
	public void testCreateRoomNoLogin() {
		try {
			RoomFactory.createRoom(100);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());
		}
	}
	
	@ReplaceTest("infrastructure.tests.RoomTest.testCreateRoom")
	public void testCreateRoomStudent() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Student s = PersonFactory.createStudent("John", "103 St. James Street");
		s.setLogin("john");
		s.setPassword("1234");
		
		Authentication.aspectOf().authenticate("john", "1234");

		try {
			RoomFactory.createRoom(100);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());
		}
	}
	
	@ReplaceTest("infrastructure.tests.RoomTest.testCreateRoom")
	public void testCreateRoomTeacher() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");

		Teacher t = PersonFactory.createTeacher("John", "103 St. James Street");
		t.setLogin("john2");
		t.setPassword("1234");
		
		Authentication.aspectOf().authenticate("john2", "1234");

		try {
			RoomFactory.createRoom(100);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_ADMIN, e.getMessage());
		}		
	}
	
	@ReplaceTest("infrastructure.tests.RoomTest.testCreateRoom")
	public void testCreateRoomAdmin() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		Administrator a = PersonFactory.createAdministrator("John", "103 St. James Street");
		a.setLogin("john3");
		a.setPassword("1234");
		
		Authentication.aspectOf().authenticate("john3", "1234");

		assertNotNull(RoomFactory.createRoom(100));
	}
}