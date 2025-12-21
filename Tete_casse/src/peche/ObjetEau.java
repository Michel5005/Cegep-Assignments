package peche;

import moteurPhysique.SVector3d;

/**
 * Classe ObjetAttirable représente les objets attirables
 * 
 * @author Jason Yin
 *
 */
public class ObjetEau {
	/** Vecteur position */
	private SVector3d position;
	/** Vecteur vitesse initial */
	private SVector3d vitesseInit;
	/** Masse des objets attirables */
	private double masse;
	/** Charge des objets attirables */
	private double charge;

	/**
	 * Constructeur
	 * 
	 * @param position    Vecteur position des objets attirables
	 * @param vitesseInit Vecteur vitesse initial des objets attirables
	 * @param masse       Masse des objets attirables
	 * @param charge      Charge que les objets attirables possède
	 */
	public ObjetEau(SVector3d position, SVector3d vitesseInit, double masse, double charge) {
		this.position = position;
		this.vitesseInit = vitesseInit;
		this.masse = masse;
		this.charge = charge;
	}// fin constructeur

	/**
	 * Retourne le vecteur position des objets attirables
	 * 
	 * @return Vecteur position des objets attirables
	 */
	public SVector3d getPosition() {
		return position;
	}// fin méthode

	/**
	 * Modifie la valeur du vecteur position des objets attirables
	 * 
	 * @param position Vecteur position des objets attirables
	 */
	public void setPosition(SVector3d position) {
		this.position = position;
	}// fin méthode

	/**
	 * Retourne le vecteur vitesse initial des objets attirables
	 * 
	 * @return Vecteur vitesse initial des objets attirables
	 */
	public SVector3d getVitesseInit() {
		return vitesseInit;
	}// fin méthode

	/**
	 * Modifie la valeur du vecteur vitesse initial des objets attirables
	 * 
	 * @param vitesseInit Vecteur vitesse initial des objets attirables
	 */
	public void setVitesseInit(SVector3d vitesseInit) {
		this.vitesseInit = vitesseInit;
	}// fin méthode

	/**
	 * Retourne la masse des objets attirables
	 * 
	 * @return La masse des objets attirables
	 */
	public double getMasse() {
		return masse;
	}// fin méthode

	/**
	 * Modifie la valeur de la masse des objets attirables
	 * 
	 * @param masse La masse des objets attirables
	 */
	public void setMasse(double masse) {
		this.masse = masse;
	}// fin méthode

	/**
	 * Retourne la charge des objets attirables
	 * 
	 * @return La charge des objets attirables
	 */
	public double getCharge() {
		return charge;
	}// fin méthode

	/**
	 * Modifie la valeur de la charge des objets attirables
	 * 
	 * @param charge La charge des objets attirables
	 */
	public void setCharge(double charge) {
		this.charge = charge;
	}// fin méthode

}// fin classe
