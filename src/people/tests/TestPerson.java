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
	public void testCreateStudent(){
		assertEquals(0, Person.getPeople().size());
		
		Student s = PersonFactory.createStudent("John", "141 St Baker Street");
		assertEquals(1, Person.getPeople().size());
		
		assertEquals(Person.getPerson(s.getId()), s);
	}

	public void testCreateTeacher(){
		assertEquals(0, Person.getPeople().size());
		
		Teacher t = PersonFactory.createTeacher("John", "141 St Baker Street");
		assertEquals(1, Person.getPeople().size());

		assertEquals(Person.getPerson(t.getId()), t);
	}
	
	public void testCreateAdmin(){
		assertEquals(0, Person.getPeople().size());
		
		Administrator a = PersonFactory.createAdministrator("John", "141 St Baker Street");
		assertEquals(1, Person.getPeople().size());

		assertEquals(Person.getPerson(a.getId()), a);
	}

	public void testCreatePeople(){
		assertEquals(0, Person.getPeople().size());
		
		Administrator a = PersonFactory.createAdministrator("John", "141 St Baker Street");
		assertEquals(1, Person.getPeople().size());

		Teacher t = PersonFactory.createTeacher("John", "141 St Baker Street");
		assertEquals(2, Person.getPeople().size());

		Student s = PersonFactory.createStudent("John", "141 St Baker Street");
		assertEquals(3, Person.getPeople().size());
		
		assertEquals(Person.getPerson(a.getId()), a);
		assertEquals(Person.getPerson(s.getId()), s);
		assertEquals(Person.getPerson(t.getId()), t);
	}
}