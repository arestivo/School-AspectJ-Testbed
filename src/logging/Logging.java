package logging;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
		private static Logger logger;
		
		public static Logger getLogger() {
			if (logger == null) {
				logger = Logger.getLogger("School");
				
				try {  
			        FileHandler fh = new FileHandler("%t/school.log");  
			        logger.addHandler(fh);
			        SimpleFormatter formatter = new SimpleFormatter();  
			        fh.setFormatter(formatter);  
		
			    } catch (Exception e) {  
			        e.printStackTrace();  
				}  
			}
			return logger;
		}
}

