package teaching.tests;

import junit.framework.TestCase;
import teaching.Instance;
import teaching.InstanceFactory;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

import courses.CourseFactory;

@PackageName("teaching")
@TestFor("teaching")
public class TestInstance extends TestCase {
	public void testInstanceList() {
		assertEquals(0, Instance.getInstances().size());
		InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		assertEquals(1, Instance.getInstances().size());
	}
}
