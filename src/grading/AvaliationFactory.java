package grading;

import teaching.Instance;

public class AvaliationFactory {
	public static Avaliation createAvaliation(Avaliation.TYPE type, int weight, Instance instance) {
		Avaliation a = new Avaliation(type, weight, instance);
		Avaliation.addAvaliation(a);
		return a;
	}
}
