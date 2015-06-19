package grading;

import people.Student;

public class GradeFactory {
	public static Grade createGrade(Evaluation evaluation, Student student, int grade) {
		Grade g = new Grade(evaluation, student, grade);
		Grade.addGrade(g);
		return g;
	}
}
