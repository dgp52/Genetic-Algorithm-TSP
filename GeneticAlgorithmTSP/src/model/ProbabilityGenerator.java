package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProbabilityGenerator<T> {
	
	//Element can represent any object
	private List<T> elements = new ArrayList<>();
	//Probability is represented as double
	private List<Double> probability = new ArrayList<>();
	private List<Integer> count = new ArrayList<>(); 
	private double probabilitySum = 0.0;
	
	
	public void addElementProbability(T element, Double probability) {
		addElement(element);
		addProbability(probability);
		addCount();
	}
	
	private void addProbability(Double d) {
		if (d < 0.0) {
			//Negative probability is converted to zero
			probability.add(0.0);
		} else {
			probability.add(d);
		}
	}
	
	private void addElement(T t) {
		elements.add(t);
	}
	
	public T sumAndPick() {
		sumProbability();
		return pickAnElement();
	}
	
	private void sumProbability() {
		probabilitySum = 0.0;
		for(Double d : probability) {
			probabilitySum += d;
		}
	}
	
	private T pickAnElement () {
		 Random random = new Random();
		 double randDouble = probabilitySum * random.nextDouble();
		 double sum = 0;
		 int index = 0;
		 while(sum < randDouble) {
			 sum = sum + probability.get(index++);
		 }
		 int temp = Math.max(0, index-1);
		 count.set(temp, count.get(temp)+1);
		 return elements.get(temp);
	}
	
	private void addCount() {
		count.add(0);
	}
	
	public void printElements() {
		System.out.println("Elements: " + elements.toString());
	}
	
	public void printProbability() {
		System.out.println("Probability: " + probability.toString());
	}
	
	public void printCount() {
		System.out.println("Count: " + count.toString());
	}
	
	public void printProbabilitySum() {
		System.out.print("Sum: " + probabilitySum);
	}
}