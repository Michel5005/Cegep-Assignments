package peche;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.w3c.dom.css.Rect;

import interfaces.Dessinable;
import moteurPhysique.SVector3d;
import outils.OutilsImage;

/**
 * Classe qui représente l'obstacle sous l'eau
 * 
 * @author Michel Vuu
 *
 */
public class Mine extends ObjetEau implements Dessinable {
	/** Position de l'obstacle sous eau en x */
	private double x;
	/** Position de l'obstacle sous eau en y */
	private double y;
	/** Largeur de l'obstacle sous eau */
	private double largeur;
	/** Hauteur de l'obstacle sous eau */
	private double hauteur;
	/** Facteur de redimensionnement */
	private double pixelsParMetre;
	/** Rectangle fantome */
	private Rectangle2D.Double mine;
	/** Masse de la mine en kg */
	private double masse = 1;
	/** Charge de la mine */
	private double charge;
	/** Position de la mine */
	private SVector3d position;
	/** Vitesse de la mine (nulle) */
	private SVector3d vitesse = new SVector3d(0, 0, 0);

	/**
	 * Constructeur
	 * 
	 * @param position       Position de l'obstacle sous eau
	 * @param vitesse        Vitesse de l'obstacle sous eau
	 * @param largeur        Largeur de l'obstacle sous eau
	 * @param hauteur        Hauteur de l'obstacle sous eau
	 * @param masse          La masse de l'obstacle sous eau
	 * @param charge         La charge de l'obstacle sous eau
	 * @param pixelsParMetre Le pixel par metre
	 */
	public Mine(SVector3d position, SVector3d vitesse, double largeur, double hauteur, double masse, double charge,
			double pixelsParMetre) {
		super(position, vitesse, masse, charge);
		this.position = position;
		this.vitesse = vitesse;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.masse = masse;
		this.charge = charge;
		this.pixelsParMetre = pixelsParMetre;

		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Creer l'obstacle sous eau
	 */
	private void creerLaGeometrie() {
		mine = new Rectangle2D.Double(position.getX(), position.getY(), largeur, hauteur);

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

		g2dPrive.draw(mat.createTransformedShape(mine));
	}// fin méthode

	/**
	 * Retourne la position
	 * 
	 * @return La position de la mine
	 */
	public SVector3d getPosition() {
		return position;
	}// fin methode

	/**
	 * Modifie la position et recreer l'obstacle sous eau avec la nouvelle position
	 * 
	 * @param position La position de l'obstacle sous eau
	 */
	public void setPosition(SVector3d position) {
		this.position = position;
	}// fin methode

	/**
	 * Retourne la masse de la mine
	 * 
	 * @return La masse de la mine
	 */
	public double getMasse() {
		return masse;
	}// fin méthode

	/**
	 * Modifie la masse de l'aimant
	 * 
	 * @param masse Masse de l'aimant
	 * 
	 */
	public void setMasse(double masse) {
		this.masse = masse;
	}// fin méthode

	/**
	 * Retourne la charge de la mine
	 * 
	 * @return La charge de la mine
	 */
	public double getCharge() {
		return charge;
	}// fin méthode

	/**
	 * Modifie la charge de la mine
	 * 
	 * @param charge Charge de la mine
	 */
	public void setCharge(double charge) {
		this.charge = charge;
	}// fin méthode

	/**
	 * Retourne la vitesse de la mine
	 * 
	 * @return La vitesse de la mine
	 */
	public SVector3d getVitesse() {
		return vitesse;
	}// fin méthode

	/**
	 * Modifie la vitesse de la mine
	 * 
	 * @param vitesse La vitesse de la mine
	 */
	public void setVitesse(SVector3d vitesse) {
		this.vitesse = vitesse;
	}// fin méthode

	/**
	 * Retourne la valeur de la position en x de l'obstacle sous eau
	 * 
	 * @return La position en x de l'obstacle sous eau
	 */
	public double getX() {
		return position.getX();
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x et recréer l'obstacle sous eau avec la
	 * nouvelle position en x
	 * 
	 * @param x La position en x de l'obstacle sous eau
	 */
	public void setX(double x) {
		this.x = x;
		this.position = new SVector3d(x, position.getY(), 0);
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y de l'obstacle sous eau
	 * 
	 * @return La position en y de l'obstacle sous eau
	 */
	public double getY() {
		return position.getY();
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y et recréer l'obstacle sous eau avec la
	 * nouvelle position en y
	 * 
	 * @param y La position en y de l'obstacle sous eau
	 */
	public void setY(double y) {
		this.y = y;
		this.position = new SVector3d(position.getX(), y, 0);
	}// fin méthode

	/**
	 * Retourne la largeur de l'obstacle sous eau
	 * 
	 * @return La largeur de l'obstacle sous eau
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur de l'obstacle sous eau et recréer l'obstacle
	 * sous eau avec la nouvelle largeur
	 * 
	 * @param largeur La largeur de l'obstacle sous eau
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}// fin méthode

	/**
	 * Retourne la hauteur de l'obstacle sous eau
	 * 
	 * @return La hauteur de l'obstacle sous eau
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur de l'obstacle sous eau et recréer l'obstacle
	 * sous eau avec la nouvelle hauteur
	 * 
	 * @param hauteur La hauteur de l'obstacle sous eau
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
	 * Modifie le pixel par metre et recreer l'obstacle sous eay avec le nouveau
	 * pixel par metre
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
	public Rectangle2D.Double getMine() {
		return mine;
	}// fin méthode

	/**
	 * Modifie le rectangle fantome et recreer l'obstacle sous eau avec le nouveau
	 * rectangle fantome
	 * 
	 * @param mine Le rectangle fantome
	 */
	public void setMine(Rectangle2D.Double mine) {
		this.mine = mine;
	}// fin méthode

}// fin classe
