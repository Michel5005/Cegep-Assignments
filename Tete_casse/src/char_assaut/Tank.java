package char_assaut;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import interfaces.Dessinable;

/**
 * La classe Tank représente un char d'assaut, qui peut être dessiné dans une
 * interface graphique.
 * 
 * @author Michel Vuu
 *
 */
public class Tank implements Dessinable, Serializable {

	/** Nombre de points de vie du char d'assaut */
	protected int pointDeVie;
	/** Niveau de carburant que contient le char d'assaut */
	protected int carburant;
	/** Nombre de points de bouclier du char d'assaut */
	protected int bouclier;
	/** Nombre de projectiles que le char d'assaut possède */
	protected int nbProjectile;
	/** Position en x du char d'assaut */
	protected double x;
	/** Position en y du char d'assaut */
	protected double y;
	/** Largeur du char d'assaut */
	protected double largeur;
	/** Longueur du char d'assaut */
	protected double hauteur;
	/** Initiation du char d'assaut */
	protected Rectangle2D.Double tank = null;
	/** Facteur de mise à l'échelle utilisé lors du dessin du char d'assaut. */
	protected double pixelsParMetre;
	/** L'angle de rotation lié au déplacement du char d'assaut */
	protected double theta = 0;

	protected Area aireTank;

	/**
	 * Constructeur
	 * 
	 * @param x              Position en x du char d'assaut
	 * @param y              Position en y du char d'assaut
	 * @param largeur        Largeur du char d'assaut
	 * @param hauteur        Hauteur du char d'assaut
	 * @param pointDeVie     Nombre de points de vie que le char d'assaut possède
	 * @param carburant      Niveau de carburant que le char d'assaut possède
	 * @param bouclier       Bouclier que le char d'assaut possède
	 * @param nbProjectile   Nombre de projectiles que le char d'assaut possède
	 * @param pixelsParMetre Facteur de mise à l'échelle
	 */
	public Tank(double x, double y, double largeur, double hauteur, int pointDeVie, int carburant, int bouclier,
			int nbProjectile, double pixelsParMetre) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.pointDeVie = pointDeVie;
		this.carburant = carburant;
		this.bouclier = bouclier;
		this.nbProjectile = nbProjectile;
		this.pixelsParMetre = pixelsParMetre;
		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Permet de créer le char d'assaut qui sera affiché
	 */
	private void creerLaGeometrie() {
		tank = new Rectangle2D.Double(x, y, largeur, hauteur);

	}// fin méthode

	/**
	 * Permet de dessiner le char d'assaut, sur le contexte graphique passe en
	 * parametre.
	 *
	 * @param g2d Le contexte graphique
	 */
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);
		mat.rotate(theta, x, y);
		aireTank = new Area(mat.createTransformedShape(tank));
		g2dCopie.setColor(Color.BLACK);
		g2dCopie.fill(mat.createTransformedShape(tank));
		g2dCopie.setColor(new Color(105, 219, 245));
		g2dCopie.setStroke(new BasicStroke(3));
		g2dCopie.draw(mat.createTransformedShape(tank));

	}// fin méthode

	/**
	 * Modifie l'angle de rotation que fait le char d'assaut quand il se déplace sur
	 * le terrain courbé
	 * 
	 * @param theta L'angle de rotation lié au déplacement du char d'assaut
	 */
	public void setAngleOfRotation(double theta) {
		this.theta = theta;
	}// fin méthode

	/**
	 * Retourne le nombre de points de vie que le char d'assaut possède
	 * 
	 * @return Nombre de points de vie du char d'assaut
	 */
	public int getPointDeVie() {
		return pointDeVie;
	}// fin méthode

	/**
	 * Modifie le nombre de points de vie que le char d'assaut possède
	 * 
	 * @param pointDeVie Nombre de points de vie du char d'assaut
	 */
	public void setPointDeVie(int pointDeVie) {
		this.pointDeVie = pointDeVie;
	}// fin méthode

	/**
	 * Retourne la valeur du niveau de carburant qui est contenu dans le char
	 * d'assaut
	 * 
	 * @return Niveau de carburant que contient le char d'assaut
	 */
	public int getCarburant() {
		return carburant;
	}// fin méthode

	/**
	 * Modifie la valeur du niveau de carburant qui est contenu dans le char
	 * d'assaut
	 * 
	 * @param carburant Niveau de carburant que contient le char d'assaut
	 */
	public void setCarburant(int carburant) {
		this.carburant = carburant;
	}// fin méthode

	/**
	 * Retourne la valeur du nombre de points de bouclier que le char d'assaut
	 * possède
	 * 
	 * @return Nombre de points de bouclier que le char d'assaut possède
	 */
	public int getBouclier() {
		return bouclier;
	}// fin méthode

	/**
	 * Modifie la valeur du nombre de points de bouclier que le char d'assaut
	 * possède
	 * 
	 * @param bouclier Nombre de points de bouclier que le char d'assaut possède
	 */
	public void setBouclier(int bouclier) {
		this.bouclier = bouclier;
	}// fin méthode

	/**
	 * Retourne la valeur du nombre de projectiles que le char d'assaut possède
	 * 
	 * @return Le nombre de projectiles que le char d'assaut possède
	 */
	public int getNbProjectile() {
		return nbProjectile;
	}// fin méthode

	/**
	 * Modifie la valeur du nombre de projectiles que le char d'assaut possède
	 * 
	 * @param nbProjectile Nombre de projectiles que le char d'assaut possède
	 */
	public void setNbProjectile(int nbProjectile) {
		this.nbProjectile = nbProjectile;
	}// fin méthode

	/**
	 * Retourne la valeur de la position en x du char d'assaut
	 * 
	 * @return La position en x du char d'assaut
	 */
	public double getX() {
		return x;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x et recréer le char d'assaut avec la
	 * nouvelle position en x
	 * 
	 * @param x La position en x du char d'assaut
	 */
	public void setX(double x) {
		this.x = x;
		creerLaGeometrie();
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y du char d'assaut
	 * 
	 * @return La position en y du char d'assaut
	 */
	public double getY() {
		return y;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y et recréer le char d'assaut avec la
	 * nouvelle position en y
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
		creerLaGeometrie();
	}// fin méthode

	/**
	 * Retourne la largeur du char d'assaut
	 * 
	 * @return La largeur du char d'assaut
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur du char d'assaut et recréer le char d'assaut
	 * avec la nouvelle largeur
	 * 
	 * @param largeur La largeur du char d'assaut
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
		creerLaGeometrie();
	}// fin méthode

	/**
	 * Retourne la hauteur du char d'assaut
	 * 
	 * @return La hauteur du char d'assaut
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur du char d'assaut et recréer le char d'assaut
	 * avec la nouvelle hauteur
	 * 
	 * @param hauteur La hauteur du char d'assaut
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
		creerLaGeometrie();
	}// fin méthode

	/**
	 * Retourne la valeur de pixelsParMetre
	 * 
	 * @return Facteur de mise à l'échelle.
	 */
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}// fin méthode

	/**
	 * Modifie la valeur du pixelsParMetre
	 * 
	 * @param pixelsParMetre Facteur de mise à l'échelle.
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}// fin méthode

	public Area getAireTank() {
		return aireTank;
	}

	public void setAireTank(Area aireTank) {
		this.aireTank = aireTank;
	}
	/**
	 * Retourne la valeur de la position en x et y du char d'assaut sous forme de chaines de caractères
	 * @return La chaine de caractères de la position en x et y du char d'assaut
	 */
	public String positionToString() {
		return String.format("[%.2f",x)+" , "+String.format("%.2f]",y);
	}

}// fin classe
