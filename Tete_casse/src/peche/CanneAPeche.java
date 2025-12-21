package peche;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;
import outils.OutilsImage;

/**
 * Classe qui représente la canne à pêche
 * 
 * @author Michel Vuu
 *
 */
public class CanneAPeche implements Dessinable {
	/** Position de la canne à pêche en x */
	private double x;
	/** Position de la canne à pêche en y */
	private double y;
	/** Largeur de la canne à pêche */
	private double largeur;
	/** Hauteur de la canne à pêche */
	private double hauteur;
	/** Le pixel par mètre */
	private double pixelsParMetre;
	/** Rectangle fantôme */
	private Rectangle2D.Double canne;

	/**
	 * Constructeur
	 * 
	 * @param x              Position de la canne à pêche en x
	 * @param y              Position de la canne à pêche en y
	 * @param largeur        Largeur de la canne à pêche
	 * @param hauteur        Hauteur de la canne à pêche
	 * @param pixelsParMetre Pixel par metre
	 */
	public CanneAPeche(double x, double y, double largeur, double hauteur, double pixelsParMetre) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.pixelsParMetre = pixelsParMetre;

		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Creer la canne
	 */
	private void creerLaGeometrie() {
		canne = new Rectangle2D.Double(x, y, largeur, hauteur);
	}

	/**
	 * Permet de dessiner le char d'assaut, sur le contexte graphique passe en
	 * parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);

		g2dPrive.draw(mat.createTransformedShape(canne));
	}// fin méthode

	/**
	 * Retourne la valeur de la position en x de la canne à pêche
	 * 
	 * @return La position en x de la canne à pêche
	 */
	public double getX() {
		return x;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x et recréer la canne à pêche avec la
	 * nouvelle position en x
	 * 
	 * @param x La position en x de la canne à pêche
	 */
	public void setX(double x) {
		this.x = x;
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y de la canne à pêche
	 * 
	 * @return La position en y de la canne à pêche
	 */
	public double getY() {
		return y;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y et recréer la canne à pêche avec la
	 * nouvelle position en y
	 * 
	 * @param y La position en y de la canne à pêche
	 */
	public void setY(double y) {
		this.y = y;
	}// fin méthode

	/**
	 * Retourne la largeur de la canne à pêche
	 * 
	 * @return La largeur de la canne à pêche
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur de la canne à pêche et recréer la canne à
	 * pêche avec la nouvelle largeur
	 * 
	 * @param largeur La largeur de la canne à pêche
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}// fin méthode

	/**
	 * Retourne la hauteur de la canne à pêche
	 * 
	 * @return La hauteur de la canne à pêche
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur de la canne à pêche et recréer la canne à
	 * pêche avec la nouvelle hauteur
	 * 
	 * @param hauteur La hauteur de la canne à pêche
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
	 * Modifie le pixel par metre et recreer la canne a peche avec le nouveau pixel
	 * par metre
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
	public Rectangle2D.Double getCanne() {
		return canne;
	}// fin méthode

	/**
	 * Modifie le rectangle fantome et recreer la canne a peche avec le nouveau
	 * rectangle fantome
	 * 
	 * @param canne Le rectangle fantome
	 */
	public void setCanne(Rectangle2D.Double canne) {
		this.canne = canne;
	}// fin méthode

}// fin classe
