package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Population {
	
	private List<Point> originalPoints;
	private int populationSize;
	private List<Points> population;

	private int generation;
	
	public Population( List<Point> points,int size) {
		this.populationSize = size;
		this.originalPoints = points;
		this.population = new ArrayList<Points>();
		this.generation = 1;
	}
	
	public void createPopulation () {
		for (int i =0 ; i < populationSize; i++) {			
			Points  p = new Points();
			p.addPoint(originalPoints.get(0));
			List<Point> temp = new ArrayList<Point>(originalPoints);
			temp.remove(0);
			Collections.shuffle(temp);
			p.getPoints().addAll(temp);
			p.addPoint(originalPoints.get(0));
			population.add(p);
		}
	}
	
	public void printPopulation () {
		for (Points pp : population) {
			pp.printPoints();
			pp.printFitnessValue();
		}
	}
	
	public void calculateFitness () {
		for(Points pp : population) {
			pp.calculateFitnessValue();
		}
	}
	
	private Points crossOver(Points[] parents) {
		Points child = new Points();
		List<Points> p = Arrays.asList(parents);
		Points maxFitnessPoint = Collections.max(p, Comparator.comparing(s -> s.getFitnessValue()));
		Points minFitnessPoint = Collections.min(p, Comparator.comparing(s -> s.getFitnessValue()));
		//Take 60% from parent one -> parent with highest fitness value
		int indexes = (int) Math.floor(0.6 * maxFitnessPoint.getSize());
		for(int i = 0; i < indexes; i++) {
			child.addPoint(maxFitnessPoint.getPoints().get(i));
		}
		
		for(int i = 0; i < minFitnessPoint.getSize(); i++) {
			if(!(child.getPoints().contains(minFitnessPoint.getPoints().get(i)))) {
				child.addPoint(minFitnessPoint.getPoints().get(i));
			}
		}
		
		child.addPoint(maxFitnessPoint.getPoints().get(0));
		
		//System.out.println("hIGHEST : " +  maxFitnessPoint.getFitnessValue() + " : " + maxFitnessPoint + " : " + indexes + " : " +  maxFitnessPoint.getSize());
		//System.out.println("Child: " + child.getPoints());
		//System.out.println("Highest: " + maxFitnessPoint.getPoints() + " : " + maxFitnessPoint.getFitnessValue());
		//System.out.println("Lowest: " + minFitnessPoint.getPoints() + " : " + minFitnessPoint.getFitnessValue());
		
		return child;
	}
	
	public Points mutation(Points child) {
		Points p = child;
		if(child.getSize() >= 4) {
			int index = ((int)Math.floor(0.3 * child.getSize()))-1;
			Collections.swap(child.getPoints(), index, index+1);
		}
		return p;
	}
	
	public void naturalSelection () {
		//System.out.println("test");
		Points[] newGeneration = new Points[populationSize];
		for (int i = 0 ; i < populationSize; i++) {
			ProbabilityGenerator<Points> pg = new ProbabilityGenerator<>();
			for(Points pp : population) {
				pg.addElementProbability(pp, pp.getFitnessValue());
			}
			Points[] parents = new Points[2];
			for(int j = 0; j < parents.length; j++) {
				parents[j] = pg.sumAndPick();
			}
			//Cross Over
			Points pp = crossOver(parents);
			pp = mutation(pp);
			newGeneration[i] = pp;
		}
		population = Arrays.asList(newGeneration);
		generation++;
	}
		
	public int getGeneration() {
		return generation;
	}
	
	public List<Points> getPopulation() {
		return population;
	}
	
	public void printPopulationDNA () {
		for(Points pp : population) {
			System.out.println(pp.getPoints());
		}
	}
}
