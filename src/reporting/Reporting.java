package reporting;

import grading.Avaliation;
import grading.Grade;
import people.Person;
import people.Student;

public class Reporting {
	public void getStudentList() {
		String list = "";
		for (Person person : Person.getPeople()) {
			if (person instanceof Student) list += person.getName() + "\n";
		}
	}

	public void getAvaliationGrades(Avaliation a) {
		String list = "";
		for (Grade grade : Grade.getGrades(a)) {
			list += grade.getStudent().getName() + " : " + grade.getGrade() + "\n";
		}
	}

}
