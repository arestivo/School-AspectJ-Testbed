package grading;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import people.Student;

public class Grade {
	private static HashMap<Evaluation, LinkedList<Grade>> evaluationGrades = new HashMap<Evaluation, LinkedList<Grade>>();
	private static HashMap<Student, LinkedList<Grade>> studentGrades = new HashMap<Student, LinkedList<Grade>>();

	private Evaluation evaluation;

	private Student student;
	
	private int grade;

	protected Grade(Evaluation evaluation, Student student, int grade) {
		this.evaluation = evaluation;
		this.student = student;
		this.grade = grade;
	}
	
	public void setAvaliaton(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return student;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	public static void addGrade(Grade g) {
		if (!evaluationGrades.containsKey(g.getEvaluation())) evaluationGrades.put(g.evaluation, new LinkedList<Grade>());
		evaluationGrades.get(g.getEvaluation()).add(g);

		if (!studentGrades.containsKey(g.getStudent())) studentGrades.put(g.student, new LinkedList<Grade>());
		studentGrades.get(g.getStudent()).add(g);
	}

	public static Collection<Grade> getGrades(Evaluation a) {
		return (evaluationGrades.get(a));
	}

	public static Collection<Grade> getGrades(Student s) {
		return (studentGrades.get(s));
	}

}
