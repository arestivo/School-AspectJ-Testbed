package logging;

import java.util.logging.Level;

import authentication.Authentication;
import people.Person;
import logging.Logging;

public aspect AuthenticationLogging {
	pointcut isCorrect(String login, String password) : execution(private Person Authentication.isCorrect(..)) && args(login, password);
	
	Person around(String login, String password) : isCorrect(login, password) {
		Person person = proceed(login, password);
		if (person != null)
			Logging.getLogger().log(Level.INFO, "Login success: " + login);
		else
			Logging.getLogger().log(Level.INFO, "Login fail: " + login);
		return person;
	}
}
