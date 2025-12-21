package peche;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import moteurPhysique.MoteurPhysiqueElecMag;
import moteurPhysique.SVector3d;
import interfaces.Dessinable;
import outils.OutilsImage;

/**
 * Classe qui représente le poisson
 * 
 * @author Michel Vuu
 *
 */
public class Poisson extends ObjetEau implements Dessinable {
	/** Position du poisson sous forme de vecteur */
	private SVector3d position;
	/** Position du poisson en x */
	private double x;
	/** Position du poisson en y */
	private double y;
	/** Largeur du poisson */
	private double largeur;
	/** Hauteur du poisson */
	private double hauteur;
	/** Masse du poisson */
	private double masse = 0.4;
	/** Le pixel par mètre */
	private double pixelsParMetre;
	/** Rectangle fantome */
	private Rectangle2D.Double poisson;
	/** Vitesse du poisson sous forme de vecteur */
	private SVector3d vitesse;
	/** Vitesse initiale du poisson sous forme de vecteur */
	private SVector3d vitesseInit;

	/**
	 * Constructeur
	 * 
	 * @param position       Position du poisson
	 * @param vitesse        Vitesse du poisson
	 * @param largeur        Largeur du poisson
	 * @param hauteur        Hauteur du poisson
	 * @param masse          Masse du poisson
	 * @param charge         Charge du poisson
	 * @param pixelsParMetre Pixel par metre
	 */

	public Poisson(SVector3d position, SVector3d vitesse, double largeur, double hauteur, double masse, double charge,
			double pixelsParMetre) {
		super(position, vitesse, masse, charge);
		this.position = position;
		this.vitesseInit = vitesse;
		this.vitesse = vitesse;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.masse = masse;
		this.pixelsParMetre = pixelsParMetre;
		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Creer le poisson
	 */
	private void creerLaGeometrie() {
		poisson = new Rectangle2D.Double(position.getX(), position.getY(), largeur, hauteur);

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
		g2dPrive.draw(mat.createTransformedShape(poisson));
	}// fin méthode

	/**
	 * Retourne la position du poisson
	 * 
	 * @return La position du poisson
	 */
	public SVector3d getPosition() {
		return position;
	}// fin methode

	/**
	 * Modifie la position du poisson et recreer le poisson avec la nouvelle
	 * position
	 * 
	 * @param position La position du poisson
	 */
	public void setPosition(SVector3d position) {
		this.position = position;
	}// fin methode

	
	/**
	 * Retourne le x du poisson
	 * 
	 * @return Le x du poisson
	 */
	public double getX() {
		return position.getX();
	}// fin methode

	/**
	 * Modifie la valeur de x du poisson et recreer le poisson avec la nouvelle
	 * valeur de x
	 * 
	 * @param x Le x du poisson
	 */
	public void setX(double x) {
		this.x = x;
		this.position = new SVector3d(x, position.getY(), 0);
	}// fin methode

	/**
	 * Retourne le y du poisson
	 * 
	 * @return Le y du poisson
	 */
	public double getY() {
		return position.getY();
	}// fin methode

	/**
	 * Modifie la valeur de y du poisson et recreer le poisson avec la nouvelle
	 * valeur de y
	 * 
	 * @param y Le y du poisson
	 */
	public void setY(double y) {
		this.y = y;
		this.position = new SVector3d(position.getX(), y, 0);
	}// fin methode

	/**
	 * Retourne la vitesse du poisson sous forme de vecteur
	 * 
	 * @return La vitesse du poisson sous forme de vecteur
	 */
	public SVector3d getVitesse() {
		return vitesse;
	}// fin méthode

	/**
	 * Modifie la vitesse et récreer le poisson avec la nouvelle vitesse
	 * 
	 * @param vitesse La vitesse du poisson sous forme de vecteur
	 */
	public void setVitesse(SVector3d vitesse) {
		this.vitesse = vitesse;
	}// fin méthode

	/**
	 * Retourne la vitesse initiale du poisson sous forme de vecteur
	 * 
	 * @return La vitesse initiale du poisson sous forme de vecteur
	 */
	public SVector3d getVitesseInit() {
		return vitesseInit;
	}// fin méthode

	/**
	 * Modifie la vitesse initiale et récreer le poisson avec la nouvelle vitesse
	 * initiale
	 * 
	 * @param vitesseInit La vitesse initiale du poisson sous forme de vecteur
	 * 
	 */
	public void setVitesseInit(SVector3d vitesseInit) {
		this.vitesseInit = vitesseInit;
	}// fin méthode

	/**
	 * Retourne la largeur du poisson
	 * 
	 * @return La largeur du poisson
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur du poisson et recréer le poisson avec la
	 * nouvelle largeur
	 * 
	 * @param largeur La largeur du poisson
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}// fin méthode

	/**
	 * Retourne la hauteur du poisson
	 * 
	 * @return La hauteur du poisson
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur du poisson et recréer le poisson avec la
	 * nouvelle hauteur
	 * 
	 * @param hauteur La hauteur du poisson
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}// fin méthode

	/**
	 * Retourne la masse du poisson
	 * 
	 * @return La masse du poisson
	 */
	public double getMasse() {
		return masse;
	}

	/**
	 * Modifie la valeur de la masse du poisson et recréer le poisson avec la
	 * nouvelle masse
	 * 
	 * @param masse La masse du poisson
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
	 * Modifie la valeur du pixel par metre et recreer le poisson avec le nouveau
	 * pixel par metre
	 * 
	 * @param pixelsParMetre Le pixel par metre
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}// fin méthode

	/**
	 * Retourne le rectangle fantome du poisson
	 * 
	 * @return Le rectangle fantome
	 */
	public Rectangle2D.Double getPoisson() {
		return poisson;
	}// fin méthode

	/**
	 * Modifie le rectangle fantome et recreer le rectangle fantome
	 * 
	 * @param poisson Le rectangle fantome
	 */
	public void setPoisson(Rectangle2D.Double poisson) {
		this.poisson = poisson;
	}// fin méthode

	/**
	 * Méthode qui se charge du déplacement des poissons
	 */
	public void deplacement() {
		x = position.getX();
		this.x += 0.01 * vitesse.getX();
		position = new SVector3d(x, position.getY(), 0);
	}// fin methode
}// fin classe
