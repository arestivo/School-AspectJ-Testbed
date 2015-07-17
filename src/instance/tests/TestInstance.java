package instance.tests;

import instance.Instance;
import instance.InstanceFactory;
import junit.framework.TestCase;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

import courses.CourseFactory;

@PackageName("instance")
@TestFor("instance")
public class TestInstance extends TestCase {
	public void testCreateInstance() {
		assertEquals(0, Instance.getInstances().size());
		InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		assertEquals(1, Instance.getInstances().size());
	}
}
