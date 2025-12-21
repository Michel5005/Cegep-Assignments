/* L'implémentation de la théorie des graphes a été basé sur plusieurs articles/sites. Voici les liens:
https://diposit.ub.edu/dspace/bitstream/2445/170548/1/170548.pdf
https://core.ac.uk/download/53745212.pdf
https://www.programiz.com/java-programming/examples/graph-implementation
https://www.researchgate.net/publication/351869800_Using_Graph_Theory_for_Automated_Electric_Network_Solving_and_Analysis
 */
package circuit.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import circuit.kirchhof.SMatrix;
import outils.Utils;

/**
 * Classe représentant l'implémentation de la théorie des graphes (pour les circuit électriques).
 * 
 * @author Ismaïl Boukari
 *
 */
public class Graph {
	/**
	 * Tous les cycles du graphe
	 */
	private List<ArrayList<Integer>> cycles = new ArrayList<ArrayList<Integer>>();
	/**
	 * Liste des cycles déjà vus
	 */
	private List<ArrayList<Integer>> seenCycles = new ArrayList<ArrayList<Integer>>();
	/**
	 * Liste des cycles à analyser
	 */
	private List<ArrayList<Integer>> cyclesForAnalysis = new ArrayList<ArrayList<Integer>>();
	/**
	 * Liste des arrêtes du graphe
	 */
	private List<Edge> edges = new ArrayList<Edge>();
	/**
	 * Nombre de sommets (noeuds)
	 */
	private int v;
	/**
	 * Liste d'ajacence du graphe
	 */
	private List<ArrayList<Integer>> adjacencyList;
	/**
	 * Matrice d'ajacence
	 */
	private int[][] adjacencyMatrix;
	/**
	 * Liste des cycles sous forme de matrice d'adjacence
	 */
	private List<int[][]> adjacencyMatrixOfCycles;
	/**
	 * Matrice de résistance
	 */
	private double[][] resistanceMatrix;
	/**
	 * Matrice de tension
	 */
	private double[][] voltageMatrix;
	/**
	 * Matrice des courants
	 */
	private double[][] currentMatrix;
	/**
	 * Courants
	 */
	private double[] currents;
	/**
	 * Liste des équations
	 */
	private List<double[]> equations = new ArrayList<double[]>();
	/**
	 * Matrice des équations
	 */
	private SMatrix kirchhofEquations;
	/**
	 * Liste des index des cycles vides
	 */
	private List<Integer> cyclesVidesIndex = new ArrayList<Integer>();

	/**
	 * Contructeur créant une nouveau graphe.
	 * 
	 * @param adjacencyMatrix matrice d'adjacence
	 */
	public Graph(int[][] adjacencyMatrix) {
		this.v = adjacencyMatrix.length;
		this.adjacencyMatrix = adjacencyMatrix;
		adjacencyList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < this.v; i++) {
			adjacencyList.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < this.v; i++) {
			for (int j = 0; j < this.v; j++) {
				if (adjacencyMatrix[i][j] == 1) {
					adjacencyList.get(i).add(j);
				}
			}
		}
		for (int i = 0; i < adjacencyList.size(); i++) {
			for (int j = 0; j < adjacencyList.get(i).size(); j++) {
				Edge edge = new Edge(i, adjacencyList.get(i).get(j));
				Edge edge2 = new Edge(adjacencyList.get(i).get(j), i);
				if (!edges.contains(edge) && !edges.contains(edge2)) {
					edges.add(edge);
				}
			}
		}
	}

	/**
	 * Contructeur créant une nouveau graphe à partir d'une liste d'arrêts.
	 * 
	 * @param edges Liste d'arrêts
	 * @param numberOfNodes Nombre de sommets (noeuds)
	 */
	public Graph(List<Edge> edges, int numberOfNodes) {
		this.edges = edges;
		this.v = numberOfNodes;

		adjacencyMatrix = new int[this.v][this.v];
		adjacencyList = new ArrayList<ArrayList<Integer>>();
		resistanceMatrix = new double[this.v][this.v];
		voltageMatrix = new double[this.v][this.v];
		currentMatrix = new double[this.v][this.v];

		for (int i = 0; i < this.v; i++) {
			adjacencyList.add(new ArrayList<Integer>());
		}

		for (Edge edge : edges) {
			adjacencyList.get(edge.getOrigine()).add(edge.getDestination());
			adjacencyMatrix[edge.getOrigine()][edge.getDestination()] = 1;
		}
	}

	/**
	 * Méthode ajoutant une connection entre deux sommets (noeuds).
	 * 
	 * @param node1 Noeud d'origine
	 * @param node2 Noeud connecté au noeud d'origine
	 */
	public void addConnection(int node1, int node2) {
		adjacencyList.get(node1).add(node2);
		adjacencyMatrix[node1][node2] = 1;
	}

	/**
	 * Méthode générant une liste d'arrêts.
	 * 
	 * @return La liste d'arrêts
	 */
	public ArrayList<int[]> generateListOfEdges() {
		ArrayList<int[]> listOfEdges = new ArrayList<int[]>();
		for (int origine = 0; origine < this.v; origine++) {
			ArrayList<Integer> connectionsOfNode = adjacencyList.get(origine);
			for (int destination = 0; destination < connectionsOfNode.size(); destination++) {
				if (origine < destination) {
					int[] a = { origine, destination };
					listOfEdges.add(a);
				}
			}
		}
		return listOfEdges;
	}

	/**
	 * Méthode trouvant tous les cycles possibles à paritir d'une liste d'arrêts.
	 */
	public void findAllPossibleCycles() {
		int startNode, node1, node2;
		for (int i = 0; i < edges.size(); i++) {
			ArrayList<Integer> path = new ArrayList<Integer>();
			startNode = edges.get(i).getOrigine();
			node1 = startNode;
			node2 = edges.get(i).getDestination();
			path.add(node1);
			path.add(node2);

			explore(startNode, node1, node2, path);
		}
	}

	/**
	* Méthode trouve les cycles à analyser (pour les équations de Kirchhoff).
	*/
	public void findCyclesForAnalysis() {
		int nbEdges = this.edges.size();
		int nbNodes = this.v;
		int nbCyclesToAnalyze = nbEdges-nbNodes+1;
		System.out.println("Nombre d'arrêts : " + nbEdges);
		System.out.println("Nombre de noeuds : " + nbNodes);
		System.out.println("Nombre de cycles à analyser : " + nbCyclesToAnalyze);
		List<ArrayList<Integer>> sortedCycle = new ArrayList<ArrayList<Integer>>(cycles);
		Collections.sort(sortedCycle, new Comparator<ArrayList<Integer>>(){
			public int compare(ArrayList<Integer> a1, ArrayList<Integer> a2) {
				return a1.size() - a2.size(); 
			}
		});
		System.out.println("Sorted cycles :");
		printArrayListOfArrayList(sortedCycle);
		List<Edge> visitedEdges = new ArrayList<Edge>();
		int sortedCycleSize = sortedCycle.size();
		for (int i = 0; (cyclesForAnalysis.size() < nbCyclesToAnalyze) && (i<sortedCycleSize); i++) {
			ArrayList<Integer> currentCycle = sortedCycle.get(i);
			List<Edge> listOfEdgesOfCycle = getEdgesFromCycle(currentCycle);
			System.out.println("Edges of cycle :" + i + " : ");
			printListOfEdges(listOfEdgesOfCycle);
			if (!listOfEdgesContainsAll(visitedEdges,listOfEdgesOfCycle)) { // On filtre les cycles qui contiennent des arrêtes déjà visités (on veut des cycles indépendants)
				cyclesForAnalysis.add(currentCycle);
				visitedEdges = (List<Edge>) Utils.addAllWithoutDuplicates(visitedEdges, listOfEdgesOfCycle);
				System.out.println("Visited edges : ");
				printListOfEdges(visitedEdges);
				System.out.println("Cycles for analysis : ");
				printCyclesForAnalysis();
			}
		}
		// int lastItemSize = cyclesForAnalysis.get(cyclesForAnalysis.size()-1).size();
		// if (cyclesForAnalysis.size() < nbCyclesToAnalyze) {
		// 	cyclesForAnalysis.add(sortedCycle.get(5));
		// }
		// for (int i = 0; cyclesForAnalysis.size() < nbCyclesToAnalyze; i++) { 
		// 	ArrayList<Integer> currentCycle = sortedCycle.get(i);
		// 	if (!cyclesForAnalysis.contains(currentCycle)) {
		// 		cyclesForAnalysis.add(currentCycle);
		// 	}
		// }
		printCyclesForAnalysis();
	}

	/**
	 * Méthode explorant tous les chemins possibles à partir d'un noeud.
	 * 
	 * @param start_node  Noeud de départ
	 * @param node1       Noeud d'origine
	 * @param node2       Noeud connecté au noeud d'origine
	 * @param path        Chemin actuel
	 */
	public void explore(int start_node, int node1, int node2, ArrayList<Integer> path) {
		// Cette méthode aurait pu être implémentée sans utiliser la récursivité (avec des piles par exemple). Cependant, elle est plus simple à comprendre (et à coder) avec la récursivité.
		ArrayList<Edge> paths = findAllPathsToMoveFromNode(node1, node2, path);
		int numberOfPaths = paths.size();
		if (numberOfPaths == 0) { // "Dead end", on retourne au noeud précédent.
			return;
		} else if (numberOfPaths == 1) {  // On explore le seul chemin possible.
			node1 = node2;
			node2 = paths.get(0).getDestination();
			if (node2 == start_node) {
				ArrayList<Integer> sortedPath = new ArrayList<Integer>(path);
				sortedPath.sort(null);
				if (!seenCycles.contains(sortedPath))
					cycles.add(path);
					seenCycles.add(sortedPath);

			} else if ((!path.contains(node2))) {
				path.add(node2);
				explore(start_node, node1, node2, path); // On adore la récursivité :)!
			}
		} else if (numberOfPaths > 1) { // Si le noeud d'origine a plusieurs chemins possibles, on explore chacun d'eux.
			for (Edge currentEdge : paths) {
				node1 = currentEdge.getOrigine();
				node2 = currentEdge.getDestination();
				ArrayList<Integer> pathCopy = new ArrayList<Integer>(path);
				if (node2 == start_node) {
					ArrayList<Integer> sortedPath = new ArrayList<Integer>(pathCopy);
					sortedPath.sort(null);
					if (!seenCycles.contains(sortedPath))
						cycles.add(pathCopy);
						seenCycles.add(sortedPath);
				} else if (!pathCopy.contains(node2)) {
					pathCopy.add(node2);
					explore(start_node, node1, node2, pathCopy); // On adore la récursivité :)!
				}
			}
		}
	}

	/**
	 * Méthode trouvant tous les chemins possibles à partir d'un noeud.
	 * 
	 * @param node1 Noeud d'origine
	 * @param node2 Noeud connecté au noeud d'origine
	 * @param path  Chemin actuel
	 * @return Liste des chemins possibles
	 */
	private ArrayList<Edge> findAllPathsToMoveFromNode(int node1, int node2, ArrayList<Integer> path) {
		Edge currentEdge;
		ArrayList<Edge> pathsToMove = new ArrayList<Edge>();
		for (int i = 0; i < edges.size(); i++) {
			currentEdge = edges.get(i);
			if (currentEdge.getOrigine() != node1 && currentEdge.getDestination() != node1) {
				if ((currentEdge.getOrigine() == node2)) {
					pathsToMove.add(currentEdge);
				}
				if ((currentEdge.getDestination() == node2)) { // Graphe non orienté
					pathsToMove.add(new Edge(currentEdge.getDestination(), currentEdge.getOrigine()));
				}
			}
		}
		return pathsToMove;
	}

	/**
	 * Méthode affichant la une liste de listes d'entiers.
	 * 
	 * @param arrayList La liste de listes d'entiers
	 */
	private void printArrayListOfArrayList(List<ArrayList<Integer>> arrayList) {
		System.out.print("[");
		for (int i = 0; i < arrayList.size(); i++) {
			System.out.print("[");
			ArrayList<Integer> currArray = arrayList.get(i);
			for (int j = 0; j < currArray.size(); j++) {
				System.out.print(currArray.get(j));
				if (j != currArray.size() - 1) {
					System.out.print(", ");
				}
			}
			if (i != arrayList.size() - 1) {
				System.out.print("], ");
			} else {
				System.out.print("]");
			}
		}
		System.out.print("]");
		System.out.println();
	}

	/**
	 * Méthode affichant les cycles du graphe trouvés.
	 */
	public void printCycles() {
		System.out.print("Cycles : ");
		printArrayListOfArrayList(cycles);
	}

	/**
	 * Méthode affichant les cycles du graphe trouvés (qu'on analyse).
	 */
	public void printCyclesForAnalysis() {
		System.out.println("Cycles à analyser: ");
		for (ArrayList<Integer> cycle : cyclesForAnalysis) {
			System.out.print("[");
			for (int i = 0; i < cycle.size(); i++) {
				System.out.print(cycle.get(i));
				if (i != cycle.size() - 1) {
					System.out.print(", ");
				}
			}
			System.out.println("]");
		}
	}
	/**
	 * Méthode affichant la liste d'arêtes du graphe.
	 */
	public void printListOfEdges() {
		System.out.print("Liste d'arrêts : ");
		for (Edge edge : edges) {
			System.out.print("[" + edge.getOrigine() + ", " + edge.getDestination() + "],");
		}
		System.out.println();
	}

	/**
	 * Méthode affichant la liste d'arêtes d'un graphe.
	 * @param edges La liste d'arêtes
	 */
	public void printListOfEdges(List<Edge> edges) {
		System.out.print("Liste d'arrêts (C) : ");
		for (Edge edge : edges) {
			System.out.print("[" + edge.getOrigine() + ", " + edge.getDestination() + "],");
		}
		System.out.println();
	}
	/**
	 * Méthode générant les matrices d'adjacence des cycles.
	 */
	public void generateAdjacencyMatrixOfCycles() {
		adjacencyMatrixOfCycles = new ArrayList<int[][]>();
		for (ArrayList<Integer> cycle : cyclesForAnalysis) {
			int[][] adjacencyMatrix = new int[this.v][this.v];
			for (int i = 0; i < cycle.size(); i++) {
				int node1 = cycle.get(i);
				int node2 = cycle.get((i + 1) % cycle.size());
				adjacencyMatrix[node1][node2] = 1;
				adjacencyMatrix[node2][node1] = 1;
			}
			adjacencyMatrixOfCycles.add(adjacencyMatrix);
		}
	}

	/**
	 * Méthode affichant les matrices d'adjacence des cycles.
	 */
	public void printAdjacencyMatrixOfCycles() {
		System.out.println("Matrices d'adjacence des cycles à analyser : ");
		for (int i = 0; i < adjacencyMatrixOfCycles.size(); i++) {
			System.out.println("Cycle " + (i + 1) + " : ");
			int[][] adjacencyMatrix = adjacencyMatrixOfCycles.get(i);
			for (int j = 0; j < adjacencyMatrix.length; j++) {
				for (int k = 0; k < adjacencyMatrix.length; k++) {
					System.out.print(adjacencyMatrix[j][k] + " ");
				}
				System.out.println();
			}
		}
	}

	/**
	 * Méthode effectuant la multiplication matricielle de deux matrices.
	 * @param matrix_a La première matrice
	 * @param matrix_b La deuxième matrice
	 * @return La matrice résultante de la multiplication
	 */
	public static  int[][] matrixMultiplication(int[][] matrix_a, int[][] matrix_b) {
		try{
			int[][] matrixC = new int[matrix_a.length][matrix_b[0].length];
			for (int i = 0; i < matrix_a.length; i++) {
				for (int j = 0; j < matrix_b[0].length; j++) {
					for (int k = 0; k < matrix_a[0].length; k++) {
						matrixC[i][j] += matrix_a[i][k] * matrix_b[k][j];
					}
				}
			}
			return matrixC;
		} catch (Exception e) {
			System.out.println("Erreur dans la multiplication matricielle!");
			return null;
		}
	}
	/**
	 * Méthode ajoutant une résistance entre deux noeuds.
	 * @param node1 Le premier noeud
	 * @param node2 Le deuxième noeud
	 * @param resistance La résistance
	 */
	public void addResistance(int node1, int node2, double resistance) {
		resistanceMatrix[node1][node2] = resistance;
		resistanceMatrix[node2][node1] = resistance; // La matrice de résistance est symétrique!

	}

	/** 
	 * Méthode ajoutant une tension entre deux noeuds.
	 * @param node1 Le premier noeud
	 * @param node2 Le deuxième noeud
	 * @param voltage La tension
	 */
	public void addVoltage(int node1, int node2, double voltage) {
		voltageMatrix[node1][node2] = voltage;
		voltageMatrix[node2][node1] = -voltage; // La matrice de voltage est anti-symétrique!

	}

	/**
	 * Méthode affectant la matrice de résistance.
	 * @param resistanceMatrix La nouvelle matrice de résistance
	 */
	public void setResistanceMatrix(double[][] resistanceMatrix) {
		this.resistanceMatrix = resistanceMatrix;
	}

	/**
	 * Méthode affectant la matrice de tension.
	 * @param voltageMatrix La nouvelle matrice de tension
	 */
	public void setVoltageMatrix(double[][] voltageMatrix) {
		this.voltageMatrix = voltageMatrix;
	}

	/**
	 * Méthode générant les équations de Kirchhoff.
	 */
	public void generateKirchhofEquations() {
		int nbCycles = cyclesForAnalysis.size();
		equations.clear();

		for (int i = 0; i<nbCycles; i++) {
			equations.add(new double[nbCycles+1]);
		}
		for (int i = 0; i < nbCycles; i++) { // Pour chaque cycle
			ArrayList<Integer> currentCycle = cyclesForAnalysis.get(i);
			int currentCycleSize = currentCycle.size();
			ArrayList<int[][]>commonBranchesMatrices = new ArrayList<int[][]>();
			for (int x = 0; x < nbCycles; x++) { // Calcul des matrices de branches communes
				if (x != i) {
					int [][] currentPrimaryMatrix = adjacencyMatrixOfCycles.get(i);
					int [][] currentSecondaryMatrix = adjacencyMatrixOfCycles.get(x);
					int[][] commonBranchesMatrix = elementWiseMultiplication(currentPrimaryMatrix, currentSecondaryMatrix);
					commonBranchesMatrices.add(commonBranchesMatrix);
				} else {
					commonBranchesMatrices.add(null);
				}
			}
			printCommonBranchesMatrices(commonBranchesMatrices, i);
			for (int j = 0; j<currentCycleSize; j++) { // Pour chaque noeud du cycle
				int node1 = currentCycle.get(j);
				int node2 = currentCycle.get((j+1)%currentCycleSize);
				double resistance = resistanceMatrix[node1][node2];
				double voltage = voltageMatrix[node1][node2];
				if (resistance != 0) { // Si il y a une résistance entre les deux noeuds, il faut l'ajouter à l'équation.
					equations.get(i)[i] += resistance;
					for (int k = 0; k < nbCycles; k++) { // On parcourt les autres cycles pour voir s'il y a des branches communes. Si c'est le cas, il faut soustraire la résistance de la branche commune.
						if ((k != i) && (commonBranchesMatrices.get(k)[node1][node2]==1)) {
							int posNode1 = cyclesForAnalysis.get(k).indexOf(node1);
							int posNode2 = cyclesForAnalysis.get(k).indexOf(node2);
							int sizeOfCycle = cyclesForAnalysis.get(k).size();
							if ((posNode1 + 1 == posNode2)||(posNode1 == sizeOfCycle-1 && posNode2 == 0))
								equations.get(i)[k] += resistance * commonBranchesMatrices.get(k)[node1][node2];
							else if ((posNode2 + 1 == posNode1)||(posNode2 == sizeOfCycle-1 && posNode1 == 0))
								equations.get(i)[k] -= resistance * commonBranchesMatrices.get(k)[node1][node2];
						}
					}
				}
				if (voltage != 0) {
					equations.get(i)[nbCycles] -= voltage;
				}

			}
		}
		Utils.printEquationsSystem(equations);
	}

	/**
	 * Méthode supprimant les lignes vides de la matrice des équations (branches non nécessaires).
	 */
	private void removeEmptyLines() {
		cyclesVidesIndex.clear();
		System.out.println("Equations avant suppression des lignes vides:");
		Utils.printEquationsSystem(equations);
		for (double[] equation: equations) {
			boolean ligneVide = true;
			for (int i = 0; i<equation.length; i++) {
				if (equation[i] != 0) {
					ligneVide = false;
					continue;
				}
			}
			if (ligneVide)
				cyclesVidesIndex.add(equations.indexOf(equation));
		}
		int decalage = 0;
		for (int index: cyclesVidesIndex) {
			int i = index - decalage;
			equations.remove(i);
			decalage++;
			for (double[]equation : equations) {
				double[] nouvEquation = new double[equation.length-1];
				for (int j = 0; j<i; j++) {
					nouvEquation[j] = equation[j];
				}
				for (int j = i; j<nouvEquation.length; j++) {
					nouvEquation[j] = equation[j+1];
				}
				equations.set(equations.indexOf(equation), nouvEquation);
			}
		}
		System.out.println("Equations après suppression des lignes vides:");
		Utils.printEquationsSystem(equations);
	}

	/**
	 * Méthode résolvant le système d'équations.
	 */
	public void solveEquations () {
		currents = new double[equations.size()];
		removeEmptyLines();
		kirchhofEquations = new SMatrix(equations);
		double[] currentsArray = null;
		try {
			currentsArray = kirchhofEquations.solvingLinearEquationsSystem();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erreur dans la résolution du système d'équations!");
		}
		int decalage = 0;
		for (int i = 0; i<currents.length; i++) {
			if (cyclesVidesIndex.contains(i)) {
				currents[i] = 0;
				decalage++;
			} else {
				currents[i] = currentsArray[i-decalage];
			}
		}	
	}

	
	/**
	 * Méthode générant la matrice des courants.
	 */
	public void generateCurrentMatrix() {
		int nbCycles = cyclesForAnalysis.size();
		int length = currentMatrix.length;
		for (int i = 0; i<length; i++) {
			for (int j = 0; j<i ; j++) {
				for (int k = 0; k<nbCycles; k++) {
					int node1 = i;
					int node2 = j;
					if (adjacencyMatrixOfCycles.get(k)[node1][node2] == 1) {
						int posNode1 = cyclesForAnalysis.get(k).indexOf(node1);
						int posNode2 = cyclesForAnalysis.get(k).indexOf(node2);
						int sizeOfCycle = cyclesForAnalysis.get(k).size();
						if ((posNode1 + 1 == posNode2)||(posNode1 == sizeOfCycle-1 && posNode2 == 0)) {
							currentMatrix[node1][node2] += currents[k];
							currentMatrix[node2][node1] -= currents[k];
						} 
						else {
						 	currentMatrix[node1][node2] -= currents[k];
						 	currentMatrix[node2][node1] += currents[k];
						}
					}
				}
			}
		}
	}

  /**
   * Méthode qui effectue l'affichage de la matrice de branches communes.
   * @param commonBranchesMatrices La liste des matrices de branches communes.
   * @param cycle Le cycle dont on affiche la matrice de branches communes.
   */
  private void printCommonBranchesMatrices(List<int[][]> commonBranchesMatrices, int cycle) {
	for (int i = 0; i < commonBranchesMatrices.size(); i++) {
		if (commonBranchesMatrices.get(i) == null) {
			System.out.println("Matrice de branches communes " + "(cycle " + (cycle+1) + ")" +" avec le cycle " + (i+1) + " : null");
		}
		else {
			System.out.println("Matrice de branches communes " + "(cycle " + (cycle+1) + ")" +" avec le cycle " + (i+1) + " :");
			int[][] currentMatrix = commonBranchesMatrices.get(i);
			for (int j = 0; j < currentMatrix.length; j++) {
				for (int k = 0; k < currentMatrix.length; k++) {
					System.out.print(currentMatrix[j][k] + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
  }



  /**
   * Méthode qui effectue la multiplication élément par élément de deux matrices.
   * @param matrix1 La première matrice.
   * @param matrix2 La deuxième matrice.
   * @return La matrice résultante de la multiplication élément par élément.
   */
  private int[][] elementWiseMultiplication(int[][] matrix1, int[][] matrix2) {
	int[][] result = new int[matrix1.length][matrix1.length];
	for (int i = 0; i < matrix1.length; i++) {
		for (int j = 0; j<matrix2.length; j++) {
			result[i][j] = matrix1[i][j] * matrix2[i][j];
		}
	}
	return result;
  }

	public List<ArrayList<Integer>> getCycles() {
		return cycles;
	}

	public void setCycles(List<ArrayList<Integer>> cycles) {
		this.cycles = cycles;
	}

	public List<ArrayList<Integer>> getCyclesForAnalysis() {
		return cyclesForAnalysis;
	}

	public void setCyclesForAnalysis(List<ArrayList<Integer>> cyclesForAnalysis) {
		this.cyclesForAnalysis = cyclesForAnalysis;
	}
	
	/**
	 * Méthode qui effectue l'affichage de la matrice des courants.
	 */
	public void printCurrentMatrix() {
		System.out.println("Matrice des courants :");
		for (int i = 0; i < currentMatrix.length; i++) {
			for (int j = 0; j < currentMatrix.length; j++) {
				System.out.print(currentMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 'Getter' de la matrice des courants.
	 * @param node1 Le noeud de départ.
	 * @param node2 Le noeud d'arrivée.
	 * @return La valeur du courant.
	 */
	public double getCurrent(int node1, int node2) {
		return currentMatrix[node1][node2];
	}

	/**
	 * Méthode qui effectue l'affichage des courants.
	 */
	public void printArrayCurrents() {
		for (int i = 0; i < currents.length; i++) {
			System.out.println("I" + (i+1) + " = " + currents[i]);
		}
	}

	/**
	 * Méthod qui effectue retourne la liste des arêtes d'un cycle.
	 * @param cycle Le cycle dont on veut les arêtes.
	 * @return La liste des arêtes du cycle.
	 */
	public List<Edge> getEdgesFromCycle(ArrayList<Integer> cycle) {
		List<Edge> edges = new ArrayList<Edge>();
		for (int i = 0; i < cycle.size(); i++) {
			if (i == cycle.size()-1) {
				edges.add(new Edge(cycle.get(i), cycle.get(0)));
			}
			else {
				edges.add(new Edge(cycle.get(i), cycle.get(i+1)));
			}
		}
		return edges;
	}

	/**
	 * Méthode qui détermine si une liste d'arêtes contient toutes les arêtes d'une autre liste d'arêtes.
	 * @param edges1 La liste d'arêtes 1.
	 * @param edges2 La liste d'arêtes 2.
	 * @return true si la liste d'arêtes 1 contient toutes les arêtes de la liste d'arêtes 2, false sinon.
	 */
	public boolean listOfEdgesContainsAll(List<Edge> edges1, List<Edge> edges2) {
		for (Edge e2 : edges2) {
			if (!edges1.contains(e2)) {
				return false;
			}
		}
		return true;
	}

	public double[][] getCurrentMatrix() {
		return currentMatrix;
	}
}


