package logging;

import java.util.logging.Level;

public aspect FactoryLogging {
	pointcut objectCreated() : execution(public * *.create*(..));
	
	after() returning(Object object) : objectCreated() {
		Logging.getLogger().log(Level.INFO, object.getClass().getName() + ": " + object + " created.");
	}
}
