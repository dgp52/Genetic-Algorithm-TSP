package model;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgo {
	
	private Population p;
	private Points bestPath;
	private int generation;
	
	public GeneticAlgo (List<Point> pp ,int populationSize, int gen) {
		this.p = new Population(pp,populationSize);
		this.bestPath = new Points();
		this.generation = gen;
		bestPath.setDistance(0.0);
	}
	
	public void start() {
		
		int i = 0;
		p.createPopulation();
		while(i < generation) {
			p.calculateFitness();
			p.printPopulation();
			bestPath();
			printBestPath();
			//System.out.println(pp.getDistance());
			//System.out.println(pp.getPoints());
			p.naturalSelection();
			//p.printPopulationDNA();
			i++;
		}
	}
	
	public void bestPath() {
		for(Points pp : p.getPopulation()) {
			if(bestPath.getDistance() == 0.0) {
				bestPath.setDistance(pp.getDistance());
				bestPath.setPoints(pp.getPoints());
			} else if (pp.getDistance() < bestPath.getDistance()) {
				bestPath.setDistance(pp.getDistance());
				bestPath.setPoints(pp.getPoints());
			}
		}
	}
	
	public void printBestPath () {
		System.out.println(" Distance: " + bestPath.getDistance());
	}
	

}
