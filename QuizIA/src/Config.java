import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Config {

	private final List<Integer> range = new ArrayList<>();

	final void addRange(int min, int max) {
		for (int i = min; i <= max; i++) {
			this.range.add(i);
		}
	}

	int getRandom() {
		return this.range.get(new Random().nextInt(this.range.size()));
	}

	char newChar() {
		RandomInRanges rir = new RandomInRanges(63, 122);
		rir.addRange(43, 44);
		rir.addRange(192, 255);
		int c = (int) (rir.getRandom());
		if (c == 63)
			c = 32;
		if (c == 64)
			c = 46;

		return (char) c;
	}

	List<Character> genes;
	double fitness;

	public Config(int num) {
		super();
		this.genes = new ArrayList<>();
		this.fitness = 0;
		for (int i = 0; i < num; i++) {
			this.genes.add(i, newChar());
		}
	}

	// Obtiene la frase convirtiendo la lista de caracteres en String
	public String getPhrase() {
		StringBuilder sb = new StringBuilder();

		for (Character ch : this.genes) {
			sb.append(ch);
		}

		return sb.toString();
	}

	// Calcula el porcentaje de fitness de la frase vs el targer que es la palabra a buscar
	public void calcFitness(String target) {
		int score = 0;
		for (int i = 0; i < this.genes.size(); i++) {
			if (this.genes.get(i) == target.charAt(i)) {
				score++;
			}
		}
		this.fitness = ((double) score / target.length());
		this.fitness = Math.pow(this.fitness, 4);
	}

	// Cruce
	public Config cruce(Config partner) {
		Random rn = new Random();
		//Se crea un nuevo hijo
		Config child = new Config(this.genes.size());

		int midpoint = (int) (rn.nextInt(this.genes.size())); // Pick a midpoint

		// Se toma mitad de uno y mitad del otro
		for (int i = 0; i < this.genes.size(); i++) {
			if (i > midpoint) {
				child.genes.set(i, this.genes.get(i));
			} else {
				child.genes.set(i, partner.genes.get(i));
			}
		}
		return child;
	}

	// Basado en la probabilidad escoge un nuevo caracter
	public void mutacion(double mutationRate) {
		Random rn = new Random();
		for (int i = 0; i < this.genes.size(); i++) {
			double randomValue = 0 + (1 - 0) * rn.nextDouble();
			if (randomValue < mutationRate) {
				this.genes.set(i, newChar());
			}
		}
	}

}
