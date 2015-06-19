package instance;

import courses.Course;

public class InstanceFactory {
	public static Instance createInstance(Course c, int year) {
		Instance i = new Instance(c, year);
		Instance.addInstance(i);
		return i;
	}

}
