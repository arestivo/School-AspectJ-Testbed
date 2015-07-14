package reporting;

import grading.Evaluation;
import grading.Grade;
import instance.Instance;

import java.util.Collection;
import java.util.Iterator;

import people.Student;

public class Reporting {
	public static String getStudentList(Instance instance) {
		String list = "";

		Collection<Student> students = instance.getStudents();
		
		for (Iterator<Student> iterator = students.iterator(); iterator.hasNext();) {
			Student student = (Student) iterator.next();
			list += student.getName() + "\n";
		}
		
		return list;
	}

	public static String getGrades(Evaluation evaluation) {
		String list = "";
		
		Collection<Grade> grades = Grade.getGrades(evaluation);

		for (Grade grade : grades) {
			list += grade.getStudent().getName() + " = " + grade.getGrade() + "\n";
		}
		
		return list;
	}

	public static String getGrades(Instance instance) {
		String list = "";
		
		Collection<Student> students = instance.getStudents();

		for (Student student : students) {
			list += student.getName() + ",";
			Collection<Grade> grades = Grade.getGrades(student);			
			for (Grade grade : grades) {
				list += grade.getGrade() + ",";
			}
			list += "\n";
		}
		
		return list;
	}

}
