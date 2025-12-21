package peche;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import moteurPhysique.SVector3d;

/**
 * Classe Aimant représente l'aimant
 * 
 * @author Jason Yin
 * @author Tin Pham
 *
 */
public class Aimant {
	/** Vecteur position */
	private SVector3d position;
	/** Position de l'aimant en x */
	private double x;
	/** Position de l'aimant en y */
	private double y;
	/** Charge électrique */
	private double charge;
	/** Rectangle fantome */
	private Rectangle2D.Double aimant;
	/** Largeur de l'aimant */
	private double largeur;
	/** Hauteur de l'aimant */
	private double hauteur;
	/** Pixel par mètre */
	private double pixelsParMetre;
	/** Aire de l'aimant */
	private Area aimantAire;
	/** Aire de l'aimant après avoit subit des transformations */
	private Shape aimantTransfo;

	/**
	 * Constructeur
	 * 
	 * @param position       Vecteur position de l'aimant
	 * @param charge         Charge électrique de l'aimant
	 * @param largeur        Largeur de l'aimant
	 * @param hauteur        Hauteur de l'aimant
	 * @param pixelsParMetre Pixel par mètre
	 */
	// Tin Pham
	public Aimant(SVector3d position, double charge, double largeur, double hauteur, double pixelsParMetre) {
		this.position = position;
		this.charge = charge;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.pixelsParMetre = pixelsParMetre;
		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Creer l'aimant
	 */
	// Jason Yin
	private void creerLaGeometrie() {
		aimant = new Rectangle2D.Double(position.getX(), position.getY(), hauteur, largeur);
	}

	/**
	 * Permet de dessiner l'aimant, sur le contexte graphique passe en parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Tin Pham
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();

		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);
		aimantTransfo = mat.createTransformedShape(aimant);
		aimantAire = new Area(aimantTransfo);
		g2dPrive.setColor(Color.red);
		g2dPrive.draw(mat.createTransformedShape(aimant));
	}// fin méthode

	/**
	 * Retourne si l'hameçon est en contact avec le trésor
	 * 
	 * @param tresorAire L'aire du trésor
	 * @return Si l'hameçon est en contact avec le trésor
	 */
	// Michel Vuu
	public boolean contact(Area tresorAire) {
		boolean enContact = false;
		tresorAire.intersect(aimantAire);
		if (!tresorAire.isEmpty()) {
			enContact = true;
		} // fin si

		return enContact;

	}// fin méthode

	/**
	 * Retourne le rectangle fantome
	 * 
	 * @return Le rectangle fantome
	 */
	// Jason Yin
	public Rectangle2D.Double getAimant() {
		return aimant;
	}// fin methode

	/**
	 * Modifie le rectangle fantome et recreer l'aimant avec le nouveau rectangle
	 * fantome
	 * 
	 * @param aimant Le rectangle fantome
	 */
	// Jason Yin
	public void setAimant(Rectangle2D.Double aimant) {
		this.aimant = aimant;
	}// fin methode

	/**
	 * Retourne la largeur de l'aimant
	 * 
	 * @return La largeur de l'aimant
	 */
	// Jason Yin
	public double getLargeur() {
		return largeur;
	}// fin methode

	/**
	 * Modifie la valeur de la largeur et recreer l'aimant avec la nouvelle largeur
	 * 
	 * @param largeur La largeur de l'aimant
	 */
	// Jason Yin
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}// fin methode

	/**
	 * Retourne la hauteur de l'aimant
	 * 
	 * @return La hauteur de l'aimant
	 */
	// Jason Yin
	public double getHauteur() {
		return hauteur;
	}// fin methode

	/**
	 * Modifie la valeur de la hauteur de l'aimant et recreer l'aimant avec la
	 * nouvelle hauteur
	 * 
	 * @param hauteur La hauteur de l'aimant
	 */
	// Jason Yin
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}// fin methode

	/**
	 * Retourne le vecteur position de l'aimant
	 * 
	 * @return Vecteur position de l'aimant
	 */
	// Jason Yin
	public SVector3d getPosition() {
		return position;
	}// fin méthode

	/**
	 * Retourne le x de l'aimant
	 * 
	 * @return Le x de l'aimant
	 */
	// Jason Yin
	public double getX() {
		return position.getX();
	}// fin methode

	/**
	 * Modifie la valeur de x de l'aimant et recreer le poisson avec la nouvelle
	 * valeur de x
	 * 
	 * @param x Le x de l'aimant
	 */
	// Jason Yin
	public void setX(double x) {
		this.x = x;
		this.position = new SVector3d(x, position.getY(), 0);
	}// fin methode

	/**
	 * Retourne le y de l'aimant
	 * 
	 * @return Le y de l'aimant
	 */
	// Jason Yin
	public double getY() {
		return position.getY();
	}// fin methode

	/**
	 * Modifie la valeur de y de l'aimant et recreer le poisson avec la nouvelle
	 * valeur de y
	 * 
	 * @param y Le y de l'aimant
	 */
	// Jason Yin
	public void setY(double y) {
		this.y = y;
		this.position = new SVector3d(position.getX(), y, 0);
	}// fin methode

	/**
	 * Modifie la valeur de vecteur position de l'aimant
	 * 
	 * @param position Vecteur position
	 */
	// Jason Yin
	public void setPosition(SVector3d position) {
		this.position = position;
	}// fin méthode

	/**
	 * Retourne le vecteur champ magnétique de l'aimant
	 * 
	 * @return Charge électrique de l'aimant
	 */
	// Jason Yin
	public double getCharge() {
		return charge;
	}// fin méthode

	/**
	 * Modifie la valeur de la charge de l'aimant
	 * 
	 * @param charge Charge de l'aimant
	 */
	// Jason Yin
	public void setCharge(double charge) {
		this.charge = charge;
	}// fin méthode

	/**
	 * Retourne le pixel par metre
	 * 
	 * @return Le pixel par metre
	 */
	// Tin Pham
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}// fin méthode

	/**
	 * Modifie la valeur du pixel par metre et recreer le poisson avec le nouveau
	 * pixel par metre
	 * 
	 * @param pixelsParMetre Le pixel par metre
	 */
	// Tin Pham
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}// fin méthode

	/**
	 * Méthode qui s'occupe de faire le déplacement de l'aimant
	 * 
	 * @param deplacement Déplacement de l'aimant
	 */
	// Tin Pham
	public void deplacement(double deplacement) {
		y = position.getY();
		this.y += deplacement;
		position = new SVector3d(position.getX(), y, 0);

	}// fin methode

}// fin classe