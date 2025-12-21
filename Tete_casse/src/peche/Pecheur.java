package peche;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;
import outils.OutilsImage;

/**
 * Classe qui représente le pêcheur
 * 
 * @author Michel Vuu
 *
 */
public class Pecheur implements Dessinable {
	/** Position en x du pecheur */
	private double x;
	/** Position en y du pecheur */
	private double y;
	/** Largeur du pecheur */
	private double largeur;
	/** Hauteur du pecheur */
	private double hauteur;
	/** Pixel par metre */
	private double pixelsParMetre;
	/** Rectangle fantome */
	private Rectangle2D.Double pecheur;

	/**
	 * Constructeur
	 * 
	 * @param x              Position en x du pêcheur
	 * @param y              Position en y du pêcheur
	 * @param largeur        Largeur du pêcheur
	 * @param hauteur        Hauteur du pêcheur
	 * @param pixelsParMetre Pixel par metre
	 */
	public Pecheur(double x, double y, double largeur, double hauteur, double pixelsParMetre) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.pixelsParMetre = pixelsParMetre;

		creerLaGeometrie();
	}

	/**
	 * Creer le pecheur
	 */
	private void creerLaGeometrie() {
		pecheur = new Rectangle2D.Double(x, y, largeur, hauteur);

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

		g2dPrive.setColor(Color.white);
		g2dPrive.draw(mat.createTransformedShape(pecheur));
	}// fin méthode

	/**
	 * Retourne la valeur de la position en x du pêcheur
	 * 
	 * @return La position en x du pêcheur
	 */
	public double getX() {
		return x;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x et recréer le pêcheur avec la nouvelle
	 * position en x
	 * 
	 * @param x La position en x du pêcheur
	 */
	public void setX(double x) {
		this.x = x;
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y du pêcheur
	 * 
	 * @return La position en y du pêcheur
	 */
	public double getY() {
		return y;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y et recréer le pêcheur avec la nouvelle
	 * position en y
	 * 
	 * @param y La position en y du pêcheur
	 */
	public void setY(double y) {
		this.y = y;
	}// fin méthode

	/**
	 * Retourne la largeur du pêcheur
	 * 
	 * @return La largeur du pêcheur
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur du pêcheur et recréer le pêcheur avec la
	 * nouvelle largeur
	 * 
	 * @param largeur La largeur du pêcheur
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}// fin méthode

	/**
	 * Retourne la hauteur du pêcheur
	 * 
	 * @return La hauteur du pêcheur
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur du pêcheur et recréer le pêcheur avec la
	 * nouvelle hauteur
	 * 
	 * @param hauteur La hauteur du pêcheur
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
	 * Modifie le pixel par metre et recreer le pecheur avec le nouveau pixel par
	 * metre
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
	public Rectangle2D.Double getPecheur() {
		return pecheur;
	}// fin méthode

	/**
	 * Modifie le rectangle fantome du pecheur et recreer le pecheur avec le nouveau
	 * rectangle fantome
	 * 
	 * @param pecheur Le rectangle fantome
	 */
	public void setPecheur(Rectangle2D.Double pecheur) {
		this.pecheur = pecheur;
	}// fin méthode

}// fin classe