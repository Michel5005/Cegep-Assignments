package peche;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;

import interfaces.Dessinable;

/**
 * Classe qui représente la fleche qui permet de choisir la force magnetique
 * 
 * @author Tin Pham
 *
 */
public class FlecheAnimeForce implements Dessinable {

	/** Position x,y de la fleche */
	private double x, y;
	/** La fleche */
	private Path2D.Double hauteurTriangle;
	/** Largeur et hauteur de la fleche */
	private double largeur, hauteur;

	/**
	 * Constructeur
	 * 
	 * @param x       Position en x
	 * @param y       Position en y
	 * @param hauteur Hauteur
	 * @param largeur Largeur
	 */
	public FlecheAnimeForce(double x, double y, double hauteur, double largeur) {
		this.x = x;
		this.y = y;
		this.hauteur = hauteur;
		this.largeur = largeur;

		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Creer la fleche
	 */
	public void creerLaGeometrie() {

		hauteurTriangle = new Path2D.Double();
		hauteurTriangle.moveTo(x, y + hauteur);
		hauteurTriangle.lineTo(x + largeur, y + hauteur);
		hauteurTriangle.lineTo(x + largeur / 2, y);
		hauteurTriangle.closePath();
	}// fin méthode

	/**
	 * Permet de dessiner la fleche, sur le contexte graphique passe en parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		g2d.fill(hauteurTriangle);

	}// fin methode

	/**
	 * Retourne le x de la fleche
	 * 
	 * @return Le x de la fleche
	 */
	public double getX() {
		return x;
	}// fin methode

	/**
	 * Modifie la valeur de x et recreer la fleche avec la nouvelle valeur de x
	 * 
	 * @param x Le x de la fleche
	 */
	public void setX(double x) {
		this.x = x;
		creerLaGeometrie();
	}// fin methode

	/**
	 * Retourne le y de la fleche
	 * 
	 * @return Le y de la fleche
	 */
	public double getY() {
		return y;
	}// fin methode

	/**
	 * Modifie la valeur de y et recreer la fleche avec la nouvelle valeur de y
	 * 
	 * @param y Le y de la fleche
	 */
	public void setY(double y) {
		this.y = y;
		creerLaGeometrie();
	}// fin methode

	/**
	 * Retourne la largeur de la fleche
	 * 
	 * @return La largeur de la fleche
	 */
	public double getLargeur() {
		return largeur;
	}// fin methode

	/**
	 * Modifie la valeur de la largeur et recreer la fleche avec la nouvelle largeur
	 * 
	 * @param largeur La largeur de la fleche
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
		creerLaGeometrie();
	}// fin methode

	/**
	 * Retourne la hauteur de la fleche
	 * 
	 * @return La hauteur de la fleche
	 */
	public double getHauteur() {
		return hauteur;
	}// fin methode

	/**
	 * Modifie la valeur de la hauteur et recreer la fleche avec la nouvelle hauteur
	 * 
	 * @param hauteur La hauteur de la fleche
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
		creerLaGeometrie();
	}// fin methode
}// fin classe
