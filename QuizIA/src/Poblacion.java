import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Poblacion {
	List<Config> listaPoblacion;
	List<Config> listaCoincidencias; 
	int generations;
	boolean finished;
	String objetivo;
	double rangoMutacion; 
	int perfectScore;
	String best;

	public Poblacion(String p, double m, int num) {
		this.generations = 0;
		this.finished = false; 
		this.objetivo = p;
		this.rangoMutacion = m;
		this.perfectScore = 1;
		this.best = "";
		this.listaPoblacion = new ArrayList<Config>();
		for (int i = 0; i < num; i++) {
			listaPoblacion.add(i, new Config(this.objetivo.length()));
		}
		this.listaCoincidencias = new ArrayList<Config>();
		this.calcFitness();
	}

	public void calcFitness() {
		for (int i = 0; i < this.listaPoblacion.size(); i++) {
			this.listaPoblacion.get(i).calcFitness(objetivo);
		}
	}
	
	// Genera un arreglo de listas posibles
	  public void naturalSelection() {
	    this.listaCoincidencias = new ArrayList<Config>();

	    double maxFitness = 0;
	    for (int i = 0; i < this.listaPoblacion.size(); i++) {
	      if (this.listaPoblacion.get(i).fitness > maxFitness) {
	        maxFitness = this.listaPoblacion.get(i).fitness;
	      }
	    }

	    // Basado en el fitnes se agregan al atributo 
	    for (int i = 0; i < this.listaPoblacion.size(); i++) {
	      double fitness = map(this.listaPoblacion.get(i).fitness, 0, maxFitness, 0, 1);
	      int n = (int)(fitness * 100); // multiplicador arbitrario
	      for (int j = 0; j < n; j++) {
	        this.listaCoincidencias.add(this.listaPoblacion.get(i));
	      }
	    }
	  }
	  
	  public double map(double n, int start1, double stop1, int start2, int stop2) {
		  double division = (stop1 - start1) == 0 ? 1 : (stop1 - start1);
		  return (n - start1) / division * (stop2 - start2) + start2;
	  }
	  
	// Se generan 2 frases y se comparan para hacer la mutacion
	 public void generate() {
		 Random rn = new Random();
	    for (int i = 0; i < this.listaPoblacion.size(); i++) {
	      int a = (int)(rn.nextInt(this.listaCoincidencias.size()));
	      int b = (int)(rn.nextInt(this.listaCoincidencias.size()));
	      Config partnerA = this.listaCoincidencias.get(a);
	      Config partnerB = this.listaCoincidencias.get(b);
	      Config child = partnerA.cruce(partnerB);
	      child.mutacion(this.rangoMutacion);
	      this.listaPoblacion.set(i, child);
	    }
	    this.generations++;
	  }
	  
	 public String getBest() {
		    return this.best;
	  }
	 
	// Obtiene la frase mas cercana hasta el momento
	 public void evaluate() {
	    double worldrecord = 0.0;
	    int index = 0;
	    for (int i = 0; i < this.listaPoblacion.size(); i++) {
	      if (this.listaPoblacion.get(i).fitness > worldrecord) {
	        index = i;
	        worldrecord = this.listaPoblacion.get(i).fitness;
	      }
	    }

	    this.best = this.listaPoblacion.get(index).getPhrase();
	    if (worldrecord == this.perfectScore) {
	      this.finished = true;
	    }
	  }

	  public boolean isFinished() {
	    return this.finished;
	  }

	  public int getGenerations() {
	    return this.generations;
	  }

	  // Calcula el porcentaje del fitness
	  public double getAverageFitness() {
	    int total = 0;
	    for (int i = 0; i < this.listaPoblacion.size(); i++) {
	      total += this.listaPoblacion.get(i).fitness;
	    }
	    return ((double)total / this.listaPoblacion.size());
	  }

	  public String allPhrases() {
	    String everything = "";

	    int displayLimit = Math.min(this.listaPoblacion.size(), 50);

	    for (int i = 0; i < displayLimit; i++) {
	      everything += this.listaPoblacion.get(i).getPhrase() + "<br>";
	    }
	    return everything;
	  }

}
