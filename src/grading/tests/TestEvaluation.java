package grading.tests;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

import courses.CourseFactory;
import teaching.Instance;
import teaching.InstanceFactory;
import grading.Evaluation;
import grading.EvaluationFactory;
import junit.framework.TestCase;

@PackageName("grading")
@TestFor("grading")
public class TestEvaluation extends TestCase {
	public void testEvaluation() {
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		Evaluation a = EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);

		assertEquals(a.getInstance().getCourse().getName(), "Programming 101");
		assertEquals(a.getInstance().getYear(), 2010);
		assertEquals(a.getType(), Evaluation.TYPE.EXAM);
		assertEquals(a.getWeight(), 20);
	}
}
