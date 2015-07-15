package minimumgrade.tests;

import instance.Instance;
import instance.InstanceFactory;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

import people.Student;
import people.PersonFactory;
import grading.Evaluation;
import grading.EvaluationFactory;
import grading.Grade;
import grading.GradeFactory;
import courses.CourseFactory;
import junit.framework.TestCase;

@PackageName("minimumgrade")
@TestFor("minimumgrade")
public class TestMinimumGrade extends TestCase {
	public void testMinimumGrade() {
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		Evaluation a = EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
		Student s = PersonFactory.createStudent("Jonh", "132 St. James Street");

		
		GradeFactory.createGrade(a, s, 8);

		assertEquals("8", Grade.getFinalResult(i, s));

		a.setMinimumGrade(10);
		assertEquals("M", Grade.getFinalResult(i, s));
	}
}
