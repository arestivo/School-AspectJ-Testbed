package logging;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public aspect FactoryLogging {
	private static Logger log = Logger.getLogger("School");

	static {
		try {  
	        FileHandler fh = new FileHandler("school.log");  
	        log.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	    } catch (Exception e) {  
	        e.printStackTrace();  
		}  
	}

	pointcut objectCreated() : call (public * *Factory.*(..));
	
	after() returning(Object object) : objectCreated() {
		log.log(Level.INFO, object.getClass().getName() + ": " + object + " created.");
	}
}
