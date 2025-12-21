package peche;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;
import outils.OutilsImage;

/**
 * Classe qui s'occupe du bateau
 * 
 * @author Michel Vuu
 *
 */
public class Bateau implements Dessinable {
	/** Position en x du bateau */
	private double x;
	/** Position en y du bateau */
	private double y;
	/** Largeur du bateau */
	private double largeur;
	/** Hauteur du bateau */
	private double hauteur;
	/** Pixel par metre */
	private double pixelsParMetre;
	/** Rectangle fantome */
	private Rectangle2D.Double bateau;

	/**
	 * Constructeur
	 * 
	 * @param x              Position en x du bateau
	 * @param y              Position en y du bateau
	 * @param largeur        Largeur du bateau
	 * @param hauteur        Hauteur du bateau
	 * @param pixelsParMetre Pixel par metre
	 */
	public Bateau(double x, double y, double largeur, double hauteur, double pixelsParMetre) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.pixelsParMetre = pixelsParMetre;

		creerLaGeometrie();
	}

	/**
	 * Creer le bateau
	 */
	private void creerLaGeometrie() {
		bateau = new Rectangle2D.Double(x, y, largeur, hauteur);

	}

	/**
	 * Permet de dessiner le poisson, sur le contexte graphique passe en parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);

		g2dPrive.draw(mat.createTransformedShape(bateau));
	}// fin méthode

	/**
	 * Retourne la valeur de la position en x du bateau
	 * 
	 * @return La position en x du bateau
	 */
	public double getX() {
		return x;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x et recréer le bateau avec la nouvelle
	 * position en x
	 * 
	 * @param x La position en x du bateau
	 */
	public void setX(double x) {
		this.x = x;
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y du bateau
	 * 
	 * @return La position en y du bateau
	 */
	public double getY() {
		return y;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y et recréer le bateau avec la nouvelle
	 * position en y
	 * 
	 * @param y La position en y du bateau
	 */
	public void setY(double y) {
		this.y = y;
	}// fin méthode

	/**
	 * Retourne la largeur du bateau
	 * 
	 * @return La largeur du bateau
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur du bateau et recréer le bateau avec la
	 * nouvelle largeur
	 * 
	 * @param largeur La largeur du bateau
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}// fin méthode

	/**
	 * Retourne la hauteur du bateau
	 * 
	 * @return La hauteur du bateau
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur du bateau et recréer le bateau avec la
	 * nouvelle hauteur
	 * 
	 * @param hauteur La hauteur du bateau
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}// fin méthode

	/**
	 * Retourne le pixel par metre
	 * 
	 * @return Le pixel par metre
	 */
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}// fin méthode

	/**
	 * Modifie le pixel par metre et recreer le bateau avec le nouveau pixel par
	 * metre;
	 * 
	 * @param pixelsParMetre Le pixel par metre
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}// fin méthode

	/**
	 * Retourne le rectangle fantome
	 * 
	 * @return Le rectangle fantome
	 */
	public Rectangle2D.Double getBateau() {
		return bateau;
	}// fin méthode

	/**
	 * Modifie le rectangle fantome et recreer le bateau avec le nouveau rectangle
	 * fantome
	 * 
	 * @param bateau Le rectangle fantome
	 */
	public void setBateau(Rectangle2D.Double bateau) {
		this.bateau = bateau;
	}// fin méthode

}// fin classe
