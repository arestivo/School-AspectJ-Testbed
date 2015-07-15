package security;

import people.Person;
import authentication.Authentication;

public aspect HashedPassword {
	pointcut setPassword(String password) : execution(public void Person+.setPassword(..)) && args(password);

	pointcut isCorrect(String login, String password) : execution(private Person Authentication.isCorrect(..)) && args(login, password);

	void around(String password) : setPassword(password) {
		try {
			proceed(PasswordUtil.generatePassword(password));
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	Person around(String login, String password) : isCorrect(login, password) {
		Person person = Person.getPerson(login);
		try {
			if (person != null && person.getLogin().equals(login) && PasswordUtil.validatePassword(password, person.getPassword()))
				return person;
		} catch (Exception e) {
			throw new SecurityException(e);
		}
		return null;
	}
}
