package model;

import java.util.List;

public class GeneticAlgo {
	
	private Population p;
	private double bestDistance  = -1;
	
	public GeneticAlgo (List<Point> pp ,int populationSize) {
		this.p = new Population(pp,populationSize);
	}
	
	public void start() {
		
		
		p.createPopulation();
		p.calculateFitness();
		Points pp = p.bestPoints();
		System.out.println(pp.getDistance());
		System.out.println(pp.getPoints());
		p.naturalSelection();
		//p.printPopulationDNA();
	}
	

}
