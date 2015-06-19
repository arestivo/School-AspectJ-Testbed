package people;

import java.util.Collection;
import java.util.HashMap;

public abstract class Person {
	private static HashMap<Integer, Person> people = new HashMap<Integer, Person>();
	private static int lastId = 0;
	
	private String name;
	private String address;
	private int id;

	public Person(String name, String address) {
		this.name = name;
		this.address = address;
		this.setId(generateId());
	}
	
	private synchronized int generateId() {
		while (true)
			if (!people.containsKey(new Integer(++lastId))) return lastId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public static void addPerson(Person p) {
		if (people.containsKey(p.getId())) throw new PersonAlreadyExists();
		people.put(new Integer(p.getId()), p);
	}

	public static Collection<Person> getPeople() {
		return people.values();
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static Person getPerson(int id) {
		return people.get(new Integer(id));
	}
}
