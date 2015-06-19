package authentication;

import java.util.Collection;
import java.util.Iterator;

import authentication.exceptions.AuthenticationException;
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

	public void authenticate(String login, String password) throws AuthenticationException {
		Collection<Person> people = Person.getPeople();
		for (Iterator<Person> iterator = people.iterator(); iterator.hasNext();) {
			Person person = (Person) iterator.next();
			if (person.getLogin().equals(login) && person.getPassword().equals(password)) {
				currentUser = person;
				return;
			}
		}
		throw new AuthenticationException();
	}
	
	public void logoff() {
		currentUser = null;
	}
	
	public Person getCurrentUser() {
		return currentUser;
	}
}
