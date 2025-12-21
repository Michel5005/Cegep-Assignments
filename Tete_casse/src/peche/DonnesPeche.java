package peche;

import java.io.Serializable;

/**
 * Classe enregistrant les donnés du jeu de curling.
 * 
 * @author Michel Vuu
 */
public class DonnesPeche implements Serializable {
	/** Le rendre sérializable */
	private static final long serialVersionUID = 103L;
	/** Le temps écoulé */
	private long tempsEcoule = 0;
	/** Le temps */
	private double temps = 0;
	/** Le nombre de tours fait avec le widget */
	private int nbTours;
	/** Boolean pour savoir si le mode triche est activé ou non */
	private boolean modeTriche = false;

	/**
	 * Retourne le booléan du mode triche
	 * 
	 * @return Le booléan modeTriche
	 */
	public boolean isModeTriche() {
		return modeTriche;
	}

	/**
	 * Modifie la valeur du booléan
	 * 
	 * @param modeTriche Booléan qui regarde si le mode triche est activé ou non
	 */
	public void setModeTriche(boolean modeTriche) {
		this.modeTriche = modeTriche;
	}

	/**
	 * Retourne le nombre de tours fait avec le widget
	 * 
	 * @return Le nombre de tours fait avec le widget
	 */
	public int getNbTours() {
		return nbTours;
	}// fin méthode

	/**
	 * Modifie la valeur du nombre de tours
	 * 
	 * @param nbTours Le nombre de tours fait avec le widget
	 */
	public void setNbTours(int nbTours) {
		this.nbTours = nbTours;
	}// fin méthode

	/**
	 * Retourne le temps
	 * 
	 * @return Le temps
	 */
	public double getTemps() {
		return temps;
	}// fin méthode

	/**
	 * Modifie la valeur du temps
	 * 
	 * @param temps Le temps
	 */
	public void setTemps(double temps) {
		this.temps = temps;
	}// fin méthode

	/**
	 * Retourne le temps écoulé du jeu de pêche
	 * 
	 * @return Le temps total
	 */
	public long getTempsEcoule() {
		return tempsEcoule;
	}// fin méthode

	/**
	 * Modifie la valeur du temps écoulé
	 * 
	 * @param tempsEcoule Le temps écoulé
	 */
	public void setTempsEcoule(long tempsEcoule) {
		this.tempsEcoule = tempsEcoule;
	}// fin méthode

}// fin classe