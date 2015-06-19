package grading;

import teaching.Instance;

public class EvaluationFactory {
	public static Evaluation createEvaluation(Evaluation.TYPE type, int weight, Instance instance) {
		Evaluation a = new Evaluation(type, weight, instance);
		Evaluation.addEvaluation(a);
		return a;
	}
}
