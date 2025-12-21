package circuit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import application.AppPrincipale7;
import outils.Utils;

/**
 * La classe Resistance représente une résistance dans un circuit. Elle hérite
 * de la classe Fil et implémentel'interface Selectionnable.
 * 
 * @author Tin Pham
 * @author Ismaïl Boukari
 *
 */
public class Resistance extends Fil {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final double PI = Math.PI;
	protected final double PISUR2 = PI/2;
	protected Point pointDeRotation;
	protected Rectangle2D.Double resFantome;
	protected double theta = 0;

	/** La coordonnée x du coin supérieur gauche de la résistance */
	private int x;
	/** La coordonnée y du coin supérieur gauche de la résistance */
	private int y;
	/** La largeur de la résistance */
	private int largeur;
	/** La hauteur de la résistance */
	private int hauteur;
	/** L'image du resistor */
	private List<Line2D.Double> lines = new ArrayList<>();

	private AffineTransform mat;

	/**
	 * Constructeur de la classe Resistance.
	 */
	// Tin Pham
	public Resistance() {
		super(0,0);
	}

	/**
	 * Constructeur de la classe Resistance.
	 *
	 * @param x          la coordonnée x du coin supérieur gauche de la résistance
	 * @param y          la coor du coin supérieur gauche de la résistance
	 * @param resistance la résistance de la résistance
	 */
	// Tin Pham
	public Resistance(int x, int y, double resistance) {
		super(0, 0);
		this.x = x;
		this.y = y;
		this.largeur = 50;
		this.hauteur = 50;
		this.resFantome = new Rectangle2D.Double(this.x, this.y+10, 50, 30);
		this.origine = new Point(x,y+25);
		this.destination = new Point(x+50,y+25);
		this.resistance = resistance;
		pointDeRotation = new Point(x+25, y+25);
		creerLaGeometrie();
	} //Fin constructeur
	
	/**
	 * Génère la géométrie de la résistance.
	 */
	// Ismaïl Boukari
	public void creerLaGeometrie() {
		double x = this.x;
		double y = this.y+25;
		this.resFantome = new Rectangle2D.Double(this.x, this.y+10, 50, 30);
		double step = 3.15;
		double spike = 10;
		lines.clear();
		lines.add(new Line2D.Double(x, y, x+step, y));
		x+=step;
		lines.add(new Line2D.Double(x, y, x+step, y+spike));
		x+=step;
		y+=spike;
		lines.add(new Line2D.Double(x, y, x+2*step, y-2*spike));
		x+=2*step;
		y-=2*spike;
		lines.add(new Line2D.Double(x, y, x+2*step, y+2*spike));
		x+=2*step;
		y+=2*spike;
		lines.add(new Line2D.Double(x, y, x+2*step, y-2*spike));
		x+=2*step;
		y-=2*spike;
		lines.add(new Line2D.Double(x, y, x+2*step, y+2*spike));
		x+=2*step;
		y+=2*spike;
		lines.add(new Line2D.Double(x, y, x+2*step, y-2*spike));
		x+=2*step;
		y-=2*spike;
		lines.add(new Line2D.Double(x, y, x+2*step, y+2*spike));
		x+=2*step;
		y+=2*spike;
		lines.add(new Line2D.Double(x, y, x+step, y-spike));
		x+=step;
		y-=spike;
		lines.add(new Line2D.Double(x, y, x+step, y));
	}
	
	/**
	 * Cette méthode dessine la résistance. Elle prend en paramètre un objet "Graphics2D".
	 * @param g2d Contexte graphique
	 */
	// Ismaïl Boukari
	@Override
	public void dessiner(Graphics2D g2d) {
		
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		mat = g2dPrive.getTransform();
		mat.rotate(theta, pointDeRotation.x, pointDeRotation.y);
		// g2dPrive.draw(path);
		if (enPlacement) {
			g2dPrive.setColor(PLACEMENT_COLOR);
			g2dPrive.setStroke(new BasicStroke(4));
			for (Line2D line: lines) {
				g2dPrive.draw(mat.createTransformedShape(line));
			}
		} else {
			if (selectionne) {
				g2dPrive.setColor(SELECTION_COLOR);
				g2dPrive.setStroke(new BasicStroke(7));
				for (Line2D line: lines) {
					g2dPrive.draw(mat.createTransformedShape(line));
				}
			}
			if (survole) {
				g2dPrive.setColor(HOVER_COLOR);
				g2dPrive.setStroke(new BasicStroke(7));
				for (Line2D line: lines) {
					g2dPrive.draw(mat.createTransformedShape(line));
				}
			}

			g2dPrive.setColor(Color.black);
			g2dPrive.setStroke(new BasicStroke(4));
			for (Line2D line: lines) {
				g2dPrive.draw(mat.createTransformedShape(line));
			}
		}
	}

	/**
	 * Cette méthode permet de dessiner les cercles aux extrémités de la résistance.
	 * @param g Contexte graphique
	 */
	@Override
	public void dessinerCerclesExtremites(Graphics2D g) {
		if (origine != null && destination != null && (survole||selectionne)&& !enPlacement) {
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
	 * Cette méthode dessine les données scientifiques de la résistance.
	 * @param g Contexte graphique
	 */
	// Ismaïl Boukari
	@Override
	public void dessinerDonnesScientifiques(Graphics2D g) {
		if (survole) {
			Graphics2D g2dPrive = (Graphics2D) g.create();
			g2dPrive.setColor(COULEUR_DONNEES);
			g2dPrive.setFont(AppPrincipale7.interBlack.deriveFont(15f));
			g2dPrive.drawString("dV = " + String.format("%." + 3 + "f", courant*getResistance()) + " volt(s)", (int)(x+largeur/2-15), (int)(y+hauteur/2-15));
			g2dPrive.drawString("I = " + String.format("%." + 3 + "f", courant) + " ampère(s)", (int)(x+largeur/2-15), (int)(y+hauteur/2-30));
			g2dPrive.drawString("R = " + String.format("%." + 3 + "f", resistance) + " ohms(s)", (int)(x+largeur/2-15), (int)(y+hauteur/2-45));
		}
	}

	/**
	 * Cette méthode retourne la coordonnée x du coin supérieur gauche de la
	 * résistance.
	 *
	 * @return la coordonnée x du coin supérieur gauche de la résistance
	 */
	// Tin Pham
	public int getX() {
		return x;
	}//Fin méthode

	/**
	 * Modifie la coordonnée x de l'angle supérieur gauche de la résistance.
	 * 
	 * @param x la nouvelle coordonnée x de l'angle supérieur gauche de la
	 *          résistance
	 */
	// Tin Pham
	public void setX(int x) {
		this.x = x;
		pointDeRotation.x = x+largeur/2;
		origine.setLocation(x,y+25);
		destination.setLocation(x+50,y+25);
		Utils.rotatePoint(origine, pointDeRotation, theta);
		Utils.rotatePoint(destination, pointDeRotation, theta);
		creerLaGeometrie();
		if (!enPlacement) {
			generatePositionPoints();
		}
	}//Fin méthode

	/**
	 * Retourne la coordonnée y de l'angle supérieur gauche de la résistance.
	 * 
	 * @return la coordonnée y de l'angle supérieur gauche de la résistance
	 */
	// Tin Pham
	public int getY() {
		return y;
	}//Fin méthode

	/**
	 * Modifie la coordonnée y de l'angle supérieur gauche de la résistance.
	 * 
	 * @param y la nouvelle coordonnée y de l'angle supérieur gauche de la
	 *          résistance
	 */
	// Tin Pham
	public void setY(int y) {
		this.y = y;
		pointDeRotation.y = y+hauteur/2;
		origine.setLocation(x,y+25);
		destination.setLocation(x+50,y+25);
		Utils.rotatePoint(origine, pointDeRotation, theta);
		Utils.rotatePoint(destination, pointDeRotation, theta);
		creerLaGeometrie();
		if (!enPlacement) {
			generatePositionPoints();
		}	
	}//Fin méthode

	/**
	 * Retourne la largeur de la résistance.
	 * 
	 * @return largeur la largeur de la résistance
	 */
	// Tin Pham
	public int getLargeur() {
		return largeur;
	}//Fin méthode

	/**
	 * Modifie la largeur de la résistance.
	 * 
	 * @param largeur la nouvelle largeur de la résistance
	 */
	// Tin Pham
	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}//Fin méthode

	/**
	 * Retourne la hauteur de la résistance.
	 * 
	 * @return la hauteur de la résistance
	 */
	// Ismaïl Boukari
	public int getHauteur() {
		return hauteur;
	}//Fin méthode

	/**
	 * Modifie la hauteur de la résistance.
	 * 
	 * @param hauteur la nouvelle hauteur de la résistance
	 */
	// Ismaïl Boukari
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}//Fin méthode
	
	
	/**
	 *
	 * Cette méthode retourne si le fil contient le point (xPix, yPix). Elle prend
	 * en paramètre les coordonnées du point en pixels.
	 * @param xPix la coordonnée x du point en pixels
	 * @param yPix la coordonnée y du point en pixels
	 * @return true si le fil contient le point (xPix, yPix), false sinon
	 */
	// Ismaïl Boukari
	@Override
	public boolean contient(double xPix, double yPix) {
		AffineTransform matContient = new AffineTransform();
		matContient.rotate(theta, pointDeRotation.x, pointDeRotation.y);
		return matContient.createTransformedShape(resFantome).contains(xPix, yPix);
	}//Fin méthode
	
	
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
	
}
