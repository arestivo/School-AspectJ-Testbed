package permission.tests;

import instance.Instance;
import instance.InstanceFactory;
import grading.Evaluation;
import grading.EvaluationFactory;
import junit.framework.TestCase;
import people.Administrator;
import people.PersonFactory;
import people.Teacher;
import permission.exceptions.PermissionException;
import authentication.Authentication;
import authentication.exceptions.AuthenticationException;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.ReplaceTest;
import com.feup.contribution.aida.annotations.TestFor;

import courses.CourseFactory;

@PackageName("permission")
@TestFor("permission")
public class EvaluationPermissionsTest extends TestCase {

	@ReplaceTest("grading.tests.TestEvaluation.testEvaluation")
	public void testCreateEvaluationNoLogin() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programmin 101"), 2010);

		Authentication.aspectOf().logoff();;
		
		try {
			EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());			
		}
	}	
	
	@ReplaceTest("grading.tests.TestEvaluation.testEvaluation")
	public void testCreateEvaluationNotTeacher() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programmin 101"), 2010);
		
		assertEquals(0, Evaluation.getEvaluations().size());

		try {
			EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_TEACHER, e.getMessage());			
		}
		assertEquals(0, Evaluation.getEvaluations().size());

	}

	@ReplaceTest("grading.tests.TestEvaluation.testEvaluation")
	public void testCreateEvaluationNotInstanceTeacher() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		
		Teacher t = PersonFactory.createTeacher("John", "104 St Saint Street");
		t.setLogin("john");
		t.setPassword("1234");
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programmin 101"), 2010);
		
		assertEquals(0, Evaluation.getEvaluations().size());

		Authentication.aspectOf().authenticate("john", "1234");

		try {
			EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
			fail("Missing Exception");
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_INSTANCE_TEACHER, e.getMessage());			
		}
		assertEquals(0, Evaluation.getEvaluations().size());
	}

	@ReplaceTest("grading.tests.TestEvaluation.testEvaluation")
	public void testCreateEvaluationInstanceTeacher() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		
		Teacher t = PersonFactory.createTeacher("John", "104 St Saint Street");
		t.setLogin("john");
		t.setPassword("1234");
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programmin 101"), 2010);
		i.addTeacher(t);
		
		assertEquals(0, Evaluation.getEvaluations().size());

		Authentication.aspectOf().authenticate("john", "1234");

		EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
		assertEquals(1, Evaluation.getEvaluations().size());
		
		EvaluationFactory.createEvaluation(Evaluation.TYPE.TEST, 30, i);
		assertEquals(2, Evaluation.getEvaluations().size());
	}
}