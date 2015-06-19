package people;

import people.exceptions.PersonAlreadyExists;

public class PersonFactory {
	public static Teacher createTeacher(String name, String address) throws PersonAlreadyExists {
		Teacher t = new Teacher(name, address);
		Person.addPerson(t);
		return t;
	}	

	public static Student createStudent(String name, String address) throws PersonAlreadyExists {
		Student s = new Student(name, address);
		Person.addPerson(s);
		return s;
	}	

	public static Administrator createAdministrator(String name, String address) throws PersonAlreadyExists {
		Administrator a = new Administrator(name, address);
		Person.addPerson(a);
		return a;
	}	
}
