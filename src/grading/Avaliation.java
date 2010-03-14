package grading;

import java.util.Collection;
import java.util.LinkedList;

import teaching.Instance;

public class Avaliation {
	public enum TYPE {EXAM, TEST};

	private int weight;
	
	private TYPE type;
	
	private Instance instance;

	private static Collection<Avaliation> avaliations = new LinkedList<Avaliation>();
	
	protected Avaliation(TYPE type, int weight, Instance instance) {
		this.type = type;
		this.weight = weight;
		this.instance = instance;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public TYPE getType() {
		return type;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	public Instance getInstance() {
		return instance;
	}

	public static void addAvaliation(Avaliation a) {
		avaliations.add(a);
	}
	
	public static Collection<Avaliation> getAvaliations() {
		return avaliations;
	}
}
