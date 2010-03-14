package grading;

import people.Student;

public class GradeFactory {
	public static Grade createGrade(Avaliation avaliation, Student student, int grade) {
		Grade g = new Grade(avaliation, student, grade);
		Grade.addGrade(g);
		return g;
	}
}
