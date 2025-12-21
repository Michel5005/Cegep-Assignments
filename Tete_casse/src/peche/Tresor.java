package peche;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import org.w3c.dom.css.Rect;

import interfaces.Dessinable;
import moteurPhysique.SVector3d;
import outils.OutilsImage;

/**
 * Classe qui représente le trésor
 * 
 * @author Michel Vuu
 *
 */
public class Tresor extends ObjetEau implements Dessinable {
	/** Position du trésor en x */
	private double x;
	/** Position du trésor en y */
	private double y;
	/** Largeur du trésor */
	private double largeur;
	/** Hauteur du trésor */
	private double hauteur;
	/** Facteur redimensionnement */
	private double pixelsParMetre;
	/** Rectangle fantome */
	private Rectangle2D.Double tresor;
	/** Charge du trésor */
	private double charge;
	/** Masse du trésor */
	private double masse;
	/** Position du trésor */
	private SVector3d position;
	/** Vitesse du trésor */
	private SVector3d vitesse;
	/** Aire du trésor */
	private Area aireTresor;
	/** Aire du trésor qui a subit des transformations */
	private Shape tresorTransfo;

	/**
	 * Constructeur
	 * 
	 * @param position       Position du trésor
	 * @param vitesse        Vitesse du trésor
	 * @param largeur        Largeur du trésor
	 * @param hauteur        Hauteur du trésor
	 * @param masse          Masse du trésor
	 * @param charge         Charge du trésor
	 * @param pixelsParMetre Pixel par metre
	 */
	public Tresor(SVector3d position, SVector3d vitesse, double largeur, double hauteur, double masse, double charge,
			double pixelsParMetre) {
		super(position, vitesse, masse, charge);
		this.position = position;
		this.vitesse = vitesse;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.masse = masse;
		this.pixelsParMetre = pixelsParMetre;
		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Créer le trésor
	 */
	private void creerLaGeometrie() {
		tresor = new Rectangle2D.Double(position.getX(), position.getY(), largeur, hauteur);

	}

	/**
	 * Permet de dessiner le trésor, sur le contexte graphique passe en parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);
		tresorTransfo = mat.createTransformedShape(tresor);
		Area aireTresor = new Area(tresorTransfo);
		g2dPrive.draw(mat.createTransformedShape(tresor));

	}// fin méthode

	/**
	 * Retourne l'aire du trésor
	 * 
	 * @return L'aire du trésor
	 */
	public Area getAireTresor() {
		return aireTresor;
	}// fin méthode

	/**
	 * Modifie l'aire du trésor
	 * 
	 * @param aireTresor L'aire du trésor
	 */
	public void setAireTresor(Area aireTresor) {
		this.aireTresor = aireTresor;
	}// fin méthode

	/**
	 * Retourne la valeur de la position en x du trésor
	 * 
	 * @return La position en x du trésor
	 */
	public double getX() {
		return x;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x et recréer le trésor avec la nouvelle
	 * position en x
	 * 
	 * @param x La position en x du trésor
	 */
	public void setX(double x) {
		this.x = x;
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y du trésor
	 * 
	 * @return La position en y du trésor
	 */
	public double getY() {
		return y;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y et recréer le trésor avec la nouvelle
	 * position en y
	 * 
	 * @param y La position en y du trésor
	 */
	public void setY(double y) {
		this.y = y;
	}// fin méthode

	/**
	 * Retourne la largeur du trésor
	 * 
	 * @return La largeur du trésor
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur du trésor et recréer le trésor avec la
	 * nouvelle largeur
	 * 
	 * @param largeur La largeur du trésor
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}// fin méthode

	/**
	 * Retourne la hauteur du trésor
	 * 
	 * @return La hauteur du trésor
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur du trésor et recréer le trésor avec la
	 * nouvelle hauteur
	 * 
	 * @param hauteur La hauteur du trésor
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}// fin méthode

	/**
	 * Retourne la charge du trésor
	 * 
	 * @return La charge du trésor
	 */
	public double getCharge() {
		return charge;
	}

	/**
	 * Modifie la charge du trésor
	 * 
	 * @param charge Charge du trésor
	 */
	public void setCharge(double charge) {
		this.charge = charge;
	}

	/**
	 * Retourne la masse du trésor
	 * 
	 * @return Masse du trésor
	 */
	public double getMasse() {
		return masse;
	}

	/**
	 * Modifie la masse du trésor
	 * 
	 * @param masse Masse du trésor
	 */
	public void setMasse(double masse) {
		this.masse = masse;
	}

	/**
	 * Retourne le pixel par metre
	 * 
	 * @return Le pixel par metre
	 */
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}// fin méthode

	/**
	 * Modifie le pixel par metre et recreer le tresor avec le nouveau pixel par
	 * metre
	 * 
	 * @param pixelsParMetre Pixel par mètre
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}// fin méthode

	/**
	 * Retourne le rectangle fantome
	 * 
	 * @return Le rectangle fantome
	 */
	public Rectangle2D.Double getTresor() {
		return tresor;
	}// fin méthode

	/**
	 * Modifie le rectangle fantome du tresor et recreer le tresor avec le nouveau
	 * rectangle fantome
	 * 
	 * @param tresor Le rectangle fantome
	 */
	public void setTresor(Rectangle2D.Double tresor) {
		this.tresor = tresor;
	}// fin méthode

}// fin classe
