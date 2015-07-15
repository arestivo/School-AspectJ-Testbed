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

	public static Person Person.getPerson(String login) {
		Collection<Person> people = Person.getPeople();
		for (Iterator<Person> iterator = people.iterator(); iterator.hasNext();) {
			Person person = (Person) iterator.next();
			if (person.getLogin().equals(login)) return person;
		}
		return null;
	}

	public void authenticate(String login, String password) throws AuthenticationException {
		Person person = isCorrect(login, password);
		if (person != null)
			currentUser = person;
		else
			throw new AuthenticationException();
	}
	
	private Person isCorrect(String login, String password) {
		Person person = Person.getPerson(login);
		if (person != null && person.getLogin().equals(login) && person.getPassword().equals(password))
			return person;
		return null;
	}

	public void logoff() {
		currentUser = null;
	}
	
	public Person getCurrentUser() {
		return currentUser;
	}
}
