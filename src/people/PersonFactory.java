package people;

public class PersonFactory {
	public static Teacher createTeacher(String name, String address) {
		Teacher t = new Teacher(name, address);
		if (!Person.addPerson(t)) return null;
		return t;
	}	

	public static Student createStudent(String name, String address) {
		Student s = new Student(name, address);
		if (!Person.addPerson(s)) return null;
		return s;
	}	

	public static Administrator createAdministrator(String name, String address) {
		Administrator a = new Administrator(name, address);
		if (!Person.addPerson(a)) return null;
		return a;
	}	
}
