package authentication;

import java.util.Collection;
import java.util.Iterator;

import people.Person;

public aspect Authentication {
	private String Person.login = "";
	private String Person.password = "";
	private Person currentUser;
	
	public void Person.setLogin(String login) {
		this.login = login;
	}

	public void Person.setPassword(String password) {
		this.password = password;
	}

	public String Person.getLogin() {
		return login;
	}

	public String Person.getPassword() {
		return password;
	}

	public boolean authenticate(String login, String password) {
		Collection<Person> people = Person.getPeople();
		for (Iterator<Person> iterator = people.iterator(); iterator.hasNext();) {
			Person person = (Person) iterator.next();
			if (person.getLogin().equals(login) && person.getPassword().equals(password)) {
				currentUser = person;
				return true;
			}
		}
		return false;
	}
	
	public Person getCurrentUser() {
		return currentUser;
	}
}