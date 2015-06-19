package grading.tests;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

import people.Student;
import people.PersonFactory;
import grading.Evaluation;
import grading.EvaluationFactory;
import grading.Grade;
import grading.GradeFactory;
import teaching.Instance;
import teaching.InstanceFactory;
import courses.CourseFactory;
import junit.framework.TestCase;

@PackageName("grading")
@TestFor("grading")
public class TestGrade extends TestCase {
	public void testGrade() {
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programming 101"), 2010);
		Evaluation a = EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
		Student s = PersonFactory.createStudent("Jonh", "132 St. James Street");

		Grade g = GradeFactory.createGrade(a, s, 10);

		assertEquals(a,g.getEvaluation());
		assertEquals(s,g.getStudent());
		assertEquals(10, g.getGrade());
	}
}
