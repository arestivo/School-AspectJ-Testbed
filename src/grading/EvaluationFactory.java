package grading;

import instance.Instance;

public class EvaluationFactory {
	public static Evaluation createEvaluation(Evaluation.TYPE type, int weight, Instance instance) {
		Evaluation evaluation = new Evaluation(type, weight, instance);
		Evaluation.addEvaluation(evaluation);
		return evaluation;
	}
}
