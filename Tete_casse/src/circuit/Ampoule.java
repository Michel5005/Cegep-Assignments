package circuit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import application.AppPrincipale7;
import interfaces.Dessinable;
import outils.OutilsImage;
import outils.Utils;

/**
 * Classe représentant une ampoule électrique dans un circuit électrique. Elle
 * hérite de la classe Fil et implémente l'interface Dessinable et Serializable.
 * 
 * @author Tin Pham
 * @author Ismaïl Boukari
 *
 */
public class Ampoule extends Resistance implements Dessinable, Serializable {

	/**  */
	private static final long serialVersionUID = 1L;
	/** Position en x de l'ampoule */
	private int x;
	/** Position en y de l'ampoule */
	private int y;
	/** Largeur de l'ampoule */
	private int largeur;
	/** Hauteur de l'ampoule */
	private int hauteur;
	/** L'image de l'ampoule */
	private transient Image ampoule = null;
	private transient Image ampoulePlacing = null;
	private transient Image ampouleHover = null;
	private transient Image ampouleSelec = null;
	private transient Image ampouleAlume = null;
	private transient boolean pasPremiereFois = false;
	private AffineTransform mat;

	/**
	 * Constructeur de la classe Ampoule.
	 * 
	 * @param x         la coordonnée x du coin supérieur gauche de l'ampoule
	 * @param y         la coordonnée y du coin supérieur gauche de l'ampoule
	 */
	// Tin Pham
	public Ampoule(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.largeur = 50;
		this.hauteur = 50;
		this.resFantome = new Rectangle2D.Double(this.x, this.y + 10, 50, 30);
		this.origine = new Point(x, y + 25);
		this.destination = new Point(x + 50, y + 25);
		this.resistance = 2;
		pointDeRotation = new Point(x + 25, y + 25);

	}// fin constructeur

	/**
	 * Génère la géométrie de l'ampoule.
	 */
	// Ismaïl Boukari
	@Override
	public void creerLaGeometrie() {
		this.resFantome = new Rectangle2D.Double(this.x, this.y+10, 50, 30);
	}

	/**
	 * Méthode pour dessiner l'ampoule électrique.
	 * @param g2d le contexte graphique
	 */
	// Tin Pham
	@Override
	public void dessiner(Graphics2D g2d) {
		if (!pasPremiereFois) {
			this.ampoule = OutilsImage.lireImageEtRedimensionner("lightbulb.png", largeur, hauteur);
			this.ampoulePlacing = OutilsImage.lireImageEtRedimensionner("lightbulbPlacing.png", largeur, hauteur);
			this.ampouleHover = OutilsImage.lireImageEtRedimensionner("lightbulbHover.png", largeur, hauteur);
			this.ampouleSelec = OutilsImage.lireImageEtRedimensionner("lightbulbSelect.png", largeur, hauteur);
			this.ampouleAlume = OutilsImage.lireImageEtRedimensionner("lightbulbon.png", largeur, hauteur);
			pasPremiereFois = true;
		}
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		mat = new AffineTransform();
		mat.rotate(theta, pointDeRotation.x, pointDeRotation.y);
		mat.translate(x, y);
		if (enPlacement) {
			g2dPrive.drawImage(ampoulePlacing, mat, null);
		}
		else {
			if (courant > 0) {
				g2dPrive.drawImage(ampouleAlume, mat, null);
			}
			else if (selectionne) {
				g2dPrive.drawImage(ampouleSelec, mat, null);
			}
			else if (survole) {
				g2dPrive.drawImage(ampouleHover, mat, null);
			}
			else {
				g2dPrive.drawImage(ampoule, mat, null);
			}
			
		}
		
	}// fin méthode

	/**
	 * Cette méthode permet de dessiner les cercles aux extrémités de la résistance (ampoule).
	 * @param g Contexte graphique
	 */
	// Ismaïl Boukari
	@Override
	public void dessinerCerclesExtremites(Graphics2D g) {
		if (origine != null && destination != null && (survole||selectionne) && !enPlacement) {
			Graphics2D g2dPrive = (Graphics2D) g.create();
			cercleOriginine.setFrame(origine.getX()-RAYON/2, origine.getY()-RAYON/2, RAYON, RAYON);
			cercleDestination.setFrame(destination.getX()-RAYON/2, destination.getY()-RAYON/2, RAYON, RAYON);
			g2dPrive.setColor(COULEUR_CERCLE);
			g2dPrive.fill(cercleOriginine);
			g2dPrive.fill(cercleDestination);
			g2dPrive.setStroke(new BasicStroke(2));
			g2dPrive.setColor(Color.BLACK);
			g2dPrive.draw(cercleOriginine);
			g2dPrive.draw(cercleDestination);
		}
	}

	/**
	 * Méthode pour dessiner les données scientifiques de l'ampoule.
	 * @param g le contexte graphique
	 */
	// Ismaïl Boukari
	@Override
	public void dessinerDonnesScientifiques(Graphics2D g) {
		if (survole) {
			Graphics2D g2dPrive = (Graphics2D) g.create();
			g2dPrive.setColor(COULEUR_DONNEES);
			g2dPrive.setFont(AppPrincipale7.interBlack.deriveFont(15f));
			g2dPrive.drawString("R = " + String.format("%." + 3 + "f", resistance) + " ohm(s)", (int)(x+largeur/2-15), (int)(y+hauteur/2-15));
			g2dPrive.drawString("dV = " + String.format("%." + 3 + "f", courant*getResistance()) + " volt(s)", (int)(x+largeur/2-15), (int)(y+hauteur/2-25));
		}
	}

	/**
	 * Getter pour la coordonnée y de l'ampoule.
	 * 
	 * @return la coordonnée y de l'ampoule
	 */
	// Tin Pham
	public int getY() {
		return y;

	}// fin méthode

	/**
	 * Modifie la coordonnée y de l'angle supérieur gauche de la résistance.
	 * 
	 * @param y la nouvelle coordonnée y de l'angle supérieur gauche de la
	 *          résistance
	 */
	// Ismaïl Boukari
	public void setY(int y) {
		this.y = y;
		pointDeRotation.y = y + hauteur / 2;
		origine.setLocation(x, y + 25);
		destination.setLocation(x + 50, y + 25);
		Utils.rotatePoint(origine, pointDeRotation, theta);
		Utils.rotatePoint(destination, pointDeRotation, theta);
		creerLaGeometrie();
		if (!enPlacement) {
			generatePositionPoints();
		}
	}// Fin méthode

	// Ismaïl Boukari
	@Override
	public int getX() {
		return x;
	}

	/**
	 * Modifie la coordonnée x de l'angle supérieur gauche de la résistance.
	 * 
	 * @param x la nouvelle coordonnée x de l'angle supérieur gauche de la
	 *          résistance
	 */
	// Ismaïl Boukari
	public void setX(int x) {
		this.x = x;
		pointDeRotation.x = x + largeur / 2;
		origine.setLocation(x, y + 25);
		destination.setLocation(x + 50, y + 25);
		Utils.rotatePoint(origine, pointDeRotation, theta);
		Utils.rotatePoint(destination, pointDeRotation, theta);
		creerLaGeometrie();
		if (!enPlacement) {
			generatePositionPoints();
		}

	}// Fin méthode

	/**
	 * Getter pour la largeur de l'ampoule.
	 * 
	 * @return la largeur de l'ampoule
	 */
	// Tin Pham
	public int getLargeur() {
		return largeur;

	}// fin méthode

	/**
	 * Setter pour la largeur de l'ampoule.
	 * 
	 * @param largeur la nouvelle largeur de l'ampoule
	 */
	// Tin Pham
	public void setLargeur(int largeur) {
		this.largeur = largeur;

	}// fin méthode

	/**
	 * Getter pour la hauteur de l'ampoule.
	 * 
	 * @return hauteur la hauteur de l'ampoule
	 */
	// Ismaïl Boukari
	public int getHauteur() {
		return hauteur;

	}// fin méthode

	/**
	 * Setter pour la hauteur de l'ampoule.
	 * 
	 * @param hauteur la nouvelle hauteur de l'ampoule
	 */
	// Ismaïl Boukari
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;

	}// fin méthode

	/**
	 *
	 * Cette méthode retourne si le fil contient le point (xPix, yPix). Elle prend
	 * en paramètre les coordonnées du point en pixels.
	 * @param xPix la coordonnée x du point en pixels
	 * @param yPix la coordonnée y du point en pixels
	 * @return true si le fil (ampoule) contient le point (xPix, yPix), false sinon
	 */
	// Ismaïl Boukari
	@Override
	public boolean contient(double xPix, double yPix) {
		AffineTransform matContient = new AffineTransform();
		matContient.rotate(theta, pointDeRotation.x, pointDeRotation.y);
		return matContient.createTransformedShape(resFantome).contains(xPix, yPix);
	}// Fin méthode


	/**
	 * Méthode qui effectue la rotation d'un composant de 90 degrés dans le sens des aiguilles d'une montre.
	 */
	// Ismaïl Boukari
	@Override
	public void rotate90Deg() {
		if (Utils.almostEqual(theta,2*PI, 0.01f)) {
			theta = PISUR2;
		}
		else {
			theta += PISUR2;
		}
		setX(x); 
		setY(y);
		if (!enPlacement) {
			generatePositionPoints();
		}
	}
	
	/**
	 * Méthode qui effectue la rotation d'un composant de 90 degrés dans le sens inverse des aiguilles d'une montre.
	 */
	// Ismaïl Boukari
	@Override
	public void rotateMinus90Deg() {
		if (Utils.almostEqual(theta,-2*PI, 0.01f)) {
			theta = -PISUR2;
		}
		else {
			theta -= PISUR2;
		}
		setX(x);
		setY(y);
		if (!enPlacement) {
			generatePositionPoints();
		}
	}
}// fin classe
