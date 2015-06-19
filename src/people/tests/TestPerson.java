package people.tests;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

import people.Administrator;
import people.Person;
import people.PersonFactory;
import people.Student;
import people.Teacher;
import junit.framework.TestCase;

@PackageName("people")
@TestFor("people")
public class TestPerson extends TestCase {
	public void testPersonList(){
		assertEquals(0, Person.getPeople().size());
		
		Administrator a = PersonFactory.createAdministrator("Jonh", "141 St Baker Street");
		assertEquals(1, Person.getPeople().size());

		Teacher t = PersonFactory.createTeacher("Jonh", "141 St Baker Street");
		assertEquals(2, Person.getPeople().size());

		Student s = PersonFactory.createStudent("Jonh", "141 St Baker Street");
		assertEquals(3, Person.getPeople().size());
		
		assertEquals(Person.getPerson(a.getId()), a);
		assertEquals(Person.getPerson(s.getId()), s);
		assertEquals(Person.getPerson(t.getId()), t);
	}

}
