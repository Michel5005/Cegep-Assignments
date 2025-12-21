package moteurPhysique;

/**
 * Classe qui sera héritée par tous les objets possédant une vitesse, une
 * position (x et y), une accélération, une masse et des forces appliquées sur
 * celles-ci. Celle-ci incluera toutes ces propriétés. Cette classe simplifiera
 * l'implémentation de l'algorithme RK4.
 * 
 * @author Ismaïl Boukari
 *
 */
public class Objet {

	/**
	 * Vitesse de l'objet.
	 */
	protected SVector3d vitesse;
	/**
	 * Acceleration de l'objet.
	 */
	protected SVector3d acceleration;
	/**
	 * Somme de tous les forces appliquées sur l'objet.
	 */
	protected SVector3d sommeDesForces;
	/**
	 * Position de l'objet.
	 */
	protected SVector3d position;
	/**
	 * Masse de l'objet en kilogrammes.
	 */
	protected double masse;

	/**
	 * Contructeur de la classe Objet.
	 * @param position La position de l'objet.
	 * @param vitesse La vitesse de l'objet.
	 * @param acceleration L'accélération de l'objet.
	 * @param sommeDesForces La somme des forces appliquées sur l'objet.
	 * @param masse La masse de l'objet.
	 */
	public Objet(SVector3d position, SVector3d vitesse, SVector3d acceleration, SVector3d sommeDesForces,
			double masse) {
		this.vitesse = vitesse;
		this.acceleration = acceleration;
		this.sommeDesForces = sommeDesForces;
		this.position = position;
		this.masse = masse;
	}

	/**
	 *
	 * Renvoie la masse de l'objet en kilogrammes.
	 * 
	 * @return la masse de l'objet en kilogrammes
	 */
	public double getMasse() {
		return masse;
	}// fin methode

	/**
	 * 
	 * Retourne la vitesse de déplacement de l'objet.
	 * 
	 * @return La vitesse de déplacement de l'objet.
	 */
	public SVector3d getVitesse() {
		return vitesse;
	}// fin methode

	/**
	 * 
	 * Retourne l'accélération de l'objet.
	 * 
	 * @return L'accélération de l'objet.
	 */
	public SVector3d getAcceleration() {
		return acceleration;
	}// fin methode

	/**
	 * 
	 * Retourne la somme des forces appliquées sur l'objet.
	 * 
	 * @return La somme des forces appliquées sur l'objet.
	 */
	public SVector3d getSommeDesForce() {
		return sommeDesForces;
	}// fin methode

	/**
	 * 
	 * Retourne la position de cet objet dans l'espace.
	 * 
	 * @return La position de cet objet dans l'espace.
	 */
	public SVector3d getPosition() {
		return position;
	}// fin methode

	/**
	 * 
	 * Définit la vitesse de déplacement de l'objet.
	 * 
	 * @param vitesse La nouvelle vitesse de déplacement de l'objet.
	 */
	public void setVitesse(SVector3d vitesse) {
		this.vitesse = vitesse;
	}// fin methode

	/**
	 * 
	 * Définit l'accélération de l'objet.
	 * 
	 * @param acceleration La nouvelle accélération de l'objet.
	 */
	public void setAcceleration(SVector3d acceleration) {
		this.acceleration = acceleration;
	}// fin methode

	/**
	 * 
	 * Définit la somme des forces appliquées sur l'objet.
	 * 
	 * @param sommeDesForce La nouvelle somme des forces appliquées sur l'objet.
	 */
	public void setSommeDesForce(SVector3d sommeDesForce) {
		this.sommeDesForces = sommeDesForce;
	}// fin methode

	/**
	 * 
	 * Définit la position de cet objet dans l'espace.
	 * 
	 * @param position La nouvelle position de cet objet dans l'espace.
	 */
	public void setPosition(SVector3d position) {
		this.position = position;
	}// fin methode

	/**
	 * 
	 * Définit la masse de l'objet en utilisant une valeur en kilogrammes.
	 * 
	 * @param masse la nouvelle masse de l'objet en kilogrammes
	 */
	public void setMasse(double masse) {
		this.masse = masse;
	}// fin methode

}// fin classe
