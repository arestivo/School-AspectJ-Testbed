package permission.tests;

import grading.Evaluation;
import grading.EvaluationFactory;
import grading.GradeFactory;
import junit.framework.TestCase;
import people.Administrator;
import people.PersonFactory;
import people.Student;
import people.Teacher;
import permission.PermissionException;
import teaching.Instance;
import teaching.InstanceFactory;
import authentication.Authentication;
import authentication.AuthenticationException;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.ReplaceTest;
import com.feup.contribution.aida.annotations.TestFor;

import courses.CourseFactory;

@PackageName("permission")
@TestFor("permission")
public class GradingPermissionsTest extends TestCase {

	@ReplaceTest("grading.tests.TestGrade.testGrade")
	public void testCreateGradeNoLogin() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		
		Student student = PersonFactory.createStudent("John", "124 St Something Street");
		
		Teacher teacher = PersonFactory.createTeacher("John", "104 St Saint Street");
		teacher.setLogin("john");
		teacher.setPassword("1234");
		
		Instance instance = InstanceFactory.createInstance(CourseFactory.createCourse("Programmin 101"), 2010);
		instance.addTeacher(teacher);

		Authentication.aspectOf().authenticate("john", "1234");

		Evaluation a1 = EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, instance);
		Evaluation a2 = EvaluationFactory.createEvaluation(Evaluation.TYPE.TEST, 30, instance);

		Authentication.aspectOf().logoff();
		
		try {
			GradeFactory.createGrade(a1, student, 10);
			GradeFactory.createGrade(a2, student, 10);
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_LOGIN, e.getMessage());						
		}
	}
	
	@ReplaceTest("grading.tests.TestGrade.testGrade")
	public void testCreateGradeNotTeacher() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		
		Student student = PersonFactory.createStudent("John", "124 St Something Street");
		student.setLogin("mary");
		student.setPassword("1234");
		
		Teacher teacher = PersonFactory.createTeacher("John", "104 St Saint Street");
		teacher.setLogin("john");
		teacher.setPassword("1234");
		
		Instance instance = InstanceFactory.createInstance(CourseFactory.createCourse("Programmin 101"), 2010);
		instance.addTeacher(teacher);

		Authentication.aspectOf().authenticate("john", "1234");
		
		Evaluation e1 = EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, instance);
		Evaluation e2 = EvaluationFactory.createEvaluation(Evaluation.TYPE.TEST, 30, instance);

		Authentication.aspectOf().authenticate("mary", "1234");

		try {
			GradeFactory.createGrade(e1, student, 10);
			GradeFactory.createGrade(e2, student, 10);
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_TEACHER, e.getMessage());						
		}
	}

	@ReplaceTest("grading.tests.TestGrade.testGrade")
	public void testCreateGradeNotInstanceTeacher() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		
		Student student = PersonFactory.createStudent("John", "124 St Something Street");
		student.setLogin("mary");
		student.setPassword("1234");
		
		Teacher t = PersonFactory.createTeacher("John", "104 St Saint Street");
		t.setLogin("john");
		t.setPassword("1234");
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programmin 101"), 2010);
		i.addTeacher(t);

		Authentication.aspectOf().authenticate("john", "1234");

		Evaluation a1 = EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
		Evaluation a2 = EvaluationFactory.createEvaluation(Evaluation.TYPE.TEST, 30, i);

		i.removeTeacher(t);

		try {
			GradeFactory.createGrade(a1, student, 10);
			GradeFactory.createGrade(a2, student, 10);
		} catch (PermissionException e) {
			assertEquals(PermissionException.NEEDS_INSTANCE_TEACHER, e.getMessage());						
		}
	}
	
	@ReplaceTest("grading.tests.TestGrade.testGrade")
	public void testCreateGradeInstanceTeacher() throws AuthenticationException {
		Administrator admin = PersonFactory.createAdministrator("John", "103 St. James Street");
		admin.setLogin("admin");
		admin.setPassword("1234");

		Authentication.aspectOf().authenticate("admin", "1234");
		
		Student student = PersonFactory.createStudent("John", "124 St Something Street");
		student.setLogin("mary");
		student.setPassword("1234");
		
		Teacher t = PersonFactory.createTeacher("John", "104 St Saint Street");
		t.setLogin("john");
		t.setPassword("1234");
		
		Instance i = InstanceFactory.createInstance(CourseFactory.createCourse("Programmin 101"), 2010);
		i.addTeacher(t);

		Authentication.aspectOf().authenticate("john", "1234");

		Evaluation a1 = EvaluationFactory.createEvaluation(Evaluation.TYPE.EXAM, 20, i);
		Evaluation a2 = EvaluationFactory.createEvaluation(Evaluation.TYPE.TEST, 30, i);
		
		GradeFactory.createGrade(a1, student, 10);
		GradeFactory.createGrade(a2, student, 10);
	}	
}