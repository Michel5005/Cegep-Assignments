/* Inspiré de https://www.programiz.com/java-programming/examples/graph-implementation */
package circuit.graph;
/**
 * Classe représentant une arête (edge) d'un graphe.
 * @author  Ismaïl Boukari
 */
public class Edge {

    /**
     * Sommet d'origine
     */
    private int origine; 
    /**
     * Sommet de destination
     */
    private int destination;

    /**
     * Constructeur créant une nouvelle arête.
     * @param origine origine
     * @param destination destination
     */
    public Edge(int origine, int destination) {
        this.origine = origine;
        this.destination = destination;
    }

    /**
     * Retourne le sommet d'origine.
     * @return origine
     */
	public int getOrigine() {
		return origine;
	}

    /**
     * Retourne le sommet de destination.
     * @return destination
     */
	public int getDestination() {
		return destination;
	}

    /**
     * Modifie le sommet d'origine.
     * @param origine origine
     */
	public void setOrigine(int origine) {
		this.origine = origine;
	}

    /**
     * Modifie le sommet de destination.
     * @param destination destination
     */
	public void setDestination(int destination) {
		this.destination = destination;
	}

    /**
     * Méthode qui retourne une une valeur booléenne indiquant si l'objet passé en paramètre est égal à l'arête.
     * @param obj objet à comparer
     * @return true si l'objet est égal à l'arête, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {   // Si on parle du même objet
            return true;  
         }  
        else if (!(obj instanceof Edge)) {  // Si l'objet n'est pas une arête
            return false;
        }  
        else { // Si l'objet est une arrête
            Edge edge = (Edge) obj;
            return (this.origine == edge.origine && this.destination == edge.destination) || (this.origine == edge.destination && this.destination == edge.origine);
        }
    }
    
}
