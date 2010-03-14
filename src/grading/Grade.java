package grading;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import people.Student;

public class Grade {
	private static HashMap<Avaliation, LinkedList<Grade>> grades = new HashMap<Avaliation, LinkedList<Grade>>();

	private Avaliation avaliaton;

	private Student student;
	
	private int grade;

	protected Grade(Avaliation avaliation, Student student, int grade) {
		avaliaton = avaliation;
		this.student = student;
		this.grade = grade;
	}
	
	public void setAvaliaton(Avaliation avaliaton) {
		this.avaliaton = avaliaton;
	}

	public Avaliation getAvaliaton() {
		return avaliaton;
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
		if (!grades.containsKey(g.getAvaliaton())) grades.put(g.avaliaton, new LinkedList<Grade>());
		grades.get(g.getAvaliaton()).add(g);
	}

	public static Collection<Grade> getGrades(Avaliation a) {
		return (grades.get(a));
	}
}
