package minimumgrade;

import java.util.Collection;

import people.Student;
import instance.Instance;
import grading.Evaluation;
import grading.Grade;

public aspect MinimumGrade {
	private int Evaluation.minimumGrade = 0;
	
	public void Evaluation.setMinimumGrade(int minimumGrade) {
		this.minimumGrade = minimumGrade;
	}

	public int Evaluation.getMinimumGrade() {
		return this.minimumGrade;
	}

	pointcut getFinalResult(Instance instance, Student student): execution (String Grade.getFinalResult(..)) && args(instance, student);

	String around(Instance instance, Student student) : getFinalResult(instance, student) {
		Collection<Grade> grades = Grade.getGrades(student);

		for (Grade grade : grades) {
			if (grade.getEvaluation().getInstance() == instance) {
				if (grade.getEvaluation().getMinimumGrade() > grade.getGrade()) return "M";
			}
		}
		
		return proceed(instance, student);
	}
}
