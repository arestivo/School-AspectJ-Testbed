package grading.tests;

import com.feup.contribution.aida.annotations.TestFor;

import courses.CourseFactory;
import teaching.Instance;
import teaching.InstanceFactory;
import grading.Avaliation;
import grading.AvaliationFactory;
import junit.framework.TestCase;

@TestFor("grading")
public class TestAvaliation extends TestCase {
	public void testAvaliation() {
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		Avaliation a = AvaliationFactory.createAvaliation(Avaliation.TYPE.EXAM, 20, i);
		assertEquals(a.getInstance().getCourse().getName(), "Programming 101");
		assertEquals(a.getInstance().getYear(), 2010);
		assertEquals(a.getType(), Avaliation.TYPE.EXAM);
		assertEquals(a.getWeight(), 20);
	}
}
