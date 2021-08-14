
/**
 * Luis Carlos Diez
 * Daniel Yepes
 * Carlos Agudelo
 */

public class Main {
	public static void main(String[] args) {
		String target = "Ingeniería informática, inteligencia artificial";
		int popmax = 200;
		double mutationRate = 0.01;
		
		// Se crea la poblacion inicial
		Poblacion population = new Poblacion(target, mutationRate, popmax);
		
		while(!population.isFinished()) {
			  population.naturalSelection();
			  population.generate();
			  population.calcFitness();
			  population.evaluate();

			  String answer = population.getBest();

			  String bestPhrase = answer;

			  String statstext =
			    "Generacion:     " + population.getGenerations() + "\n";
			  statstext += "Poblacion:      " + popmax + "\n";
			  statstext += "Rango de mutacion:         " + (int)(mutationRate * 100) + "%";
			  
			  System.out.println(String.valueOf(bestPhrase));
			  System.out.println(statstext);
		}
	}
}
