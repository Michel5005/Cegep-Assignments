  package circuit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import application.AppPrincipale7;
import outils.Utils;

/**
 * La classe Source répresente la source d'un circuit, qui peut être dessiné sur
 * un interface graphique
 * 
 * @author Tin Pham
 * @author Ismaïl Boukari
 *
 */
public class Source extends Fil {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Position en x de la source */
	private int x;
	/** Position en y de la source */
	private int y;
	/** Largeur de la source */
	private int largeur;
	/** Hauteur de la source */
	private int hauteur;
	/** L'image de la source */
	private Rectangle2D.Double ligneVertSup;
	private Rectangle2D.Double borneSup;
	private Rectangle2D.Double ligneVertInf;
	private Rectangle2D.Double borneInf;
	private double theta = 0;
	private Point pointDeRotation;
	private Rectangle2D.Double ligneVertSupSelec;
	private Rectangle2D.Double ligneVertInfSelec;
	private Rectangle2D.Double borneSupSelec;
	private Rectangle2D.Double borneInfSelec;
	private final double PI = Math.PI;
	private final double PISUR2 = PI / 2;
	private AffineTransform mat = new AffineTransform();

	/**
	 * Crée une nouvelle source avec les coordonnées x et y, la largeur et la
	 * hauteur
	 * données, ainsi que le courant et le potentiel.
	 * 
	 * @param x         La position en x de la source
	 * @param y         La position en y de la source
	 * @param potentiel Le potentiel électrique de la source
	 */
	// Tin Pham
	public Source(int x, int y, double potentiel) {
		super(0, potentiel);
		this.x = x;
		this.y = y;
		this.largeur = 50;
		this.hauteur = 50;
		this.potentiel = potentiel;
		this.resistance = 0;
		origine = new Point(x + 25, y + 50);
		destination = new Point(x + 25, y);
		pointDeRotation = new Point(x + largeur / 2, y + hauteur / 2);
		creerLaGeometrie();
	}

	/**
	 * Crée la géométrie de la source
	*/
	// Ismaïl Boukari
	public void creerLaGeometrie() {
		ligneVertSup = new Rectangle2D.Double(x + largeur / 2 - 2, y - 2, 4, hauteur / 2 - 3);
		ligneVertInf = new Rectangle2D.Double(x + largeur / 2 - 2, y + hauteur / 2 + 4, 4, hauteur / 2 - 2);
		borneSup = new Rectangle2D.Double(x + largeur / 2 - 15, y + hauteur / 2 - 5, 30, 4);
		borneInf = new Rectangle2D.Double(x + largeur / 2 - 10, y + hauteur / 2 + 2, 20, 4);

		ligneVertSupSelec = new Rectangle2D.Double(x + largeur / 2 - 4, y - 4, 8, hauteur / 2 + 1);
		ligneVertInfSelec = new Rectangle2D.Double(x + largeur / 2 - 4, y + hauteur / 2 + 2, 8, hauteur / 2 + 2);
		borneSupSelec = new Rectangle2D.Double(x + largeur / 2 - 17, y + hauteur / 2 - 7, 34, 8);
		borneInfSelec = new Rectangle2D.Double(x + largeur / 2 - 12, y + hauteur / 2, 24, 8);
	}

	/**
	 * Permet de dessiner la source, sur le contexte graphique passe en parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Ismaïl Boukari
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		mat = g2dPrive.getTransform();
		mat.rotate(theta, pointDeRotation.x, pointDeRotation.y);
		if (enPlacement) {
			g2dPrive.setColor(PLACEMENT_COLOR);
			g2dPrive.fill(mat.createTransformedShape(ligneVertSup));
			g2dPrive.fill(mat.createTransformedShape(ligneVertInf));
			g2dPrive.fill(mat.createTransformedShape(borneSup));
			g2dPrive.fill(mat.createTransformedShape(borneInf));

		} else {
			if (selectionne) {
				g2dPrive.setColor(SELECTION_COLOR);
				g2dPrive.fill(mat.createTransformedShape(ligneVertSupSelec));
				g2dPrive.fill(mat.createTransformedShape(ligneVertInfSelec));
				g2dPrive.fill(mat.createTransformedShape(borneSupSelec));
				g2dPrive.fill(mat.createTransformedShape(borneInfSelec));

			}
			if (survole) {
				g2dPrive.setColor(HOVER_COLOR);
				g2dPrive.fill(mat.createTransformedShape(ligneVertSupSelec));
				g2dPrive.fill(mat.createTransformedShape(ligneVertInfSelec));
				g2dPrive.fill(mat.createTransformedShape(borneSupSelec));
				g2dPrive.fill(mat.createTransformedShape(borneInfSelec));

				if (origine != null && destination != null && survole) { // Je veux que les cercles apparaissent au dessus du fil.
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
			g2dPrive.setColor(Color.BLACK);
			g2dPrive.fill(mat.createTransformedShape(ligneVertSup));
			g2dPrive.fill(mat.createTransformedShape(ligneVertInf));
			g2dPrive.fill(mat.createTransformedShape(borneSup));
			g2dPrive.fill(mat.createTransformedShape(borneInf));
		}

	}// Fin méthode


	/**
	 * Cette méthode permet de dessiner les cercles aux extrémités de la source.
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
	 * Permet de dessiner les données scientifiques de la source.
	 * @param g Le contexte graphique
	 */
	// Ismaïl Boukari
	@Override
	public void dessinerDonnesScientifiques(Graphics2D g) {
		if (survole) {
			Graphics2D g2dPrive = (Graphics2D) g.create();
			g2dPrive.setColor(COULEUR_DONNEES);
			g2dPrive.setFont(AppPrincipale7.interBlack.deriveFont(15f));
			g2dPrive.drawString("ε = " + String.format("%." + 3 + "f", potentiel) + " volt(s)", (int)(x+largeur/2-15), (int)(y+hauteur/2-15));
		}
	}

	/**
	 * Retourne la position en x de la source
	 * 
	 * @return La position en x
	 */
	// Tin Pham
	@Override
	public int getX() {
		return x;
	}// Fin méthode

	/**
	 * Modifie la valeur de la position en x de la source
	 * 
	 * @param x La position en x de la source
	 */
	// Tin Pham
	@Override
	public void setX(int x) {
		this.x = x;
		pointDeRotation.x = x + largeur / 2;
		origine.setLocation(x + 25, y + 50);
		destination.setLocation(x + 25, y);
		Utils.rotatePoint(origine, pointDeRotation, theta);
		Utils.rotatePoint(destination, pointDeRotation, theta);
		creerLaGeometrie();
		if (!enPlacement) {
			generatePositionPoints();
		}
	}// Fin méthode

	/**
	 * Retourne la position en y de la source
	 * 
	 * @return La position en y de la source
	 */
	// Tin Pham
	@Override
	public int getY() {
		return y;
	}// Fin méthode

	/**
	 * Modifie la valeur de la position en y de la source
	 * 
	 * @param y La position en y de la source
	 */
	// Tin Pham
	@Override
	public void setY(int y) {
		this.y = y;
		pointDeRotation.y = y + hauteur / 2;
		origine.setLocation(x + 25, y + 50);
		destination.setLocation(x + 25, y);
		Utils.rotatePoint(origine, pointDeRotation, theta);
		Utils.rotatePoint(destination, pointDeRotation, theta);
		creerLaGeometrie();
		if (!enPlacement) {
			generatePositionPoints();
		}
	}// Fin méthode

	/**
	 * Retourne la largeur de la source
	 * 
	 * @return La largeur de la source
	 */
	// Tin Pham
	public int getLargeur() {
		return largeur;
	}// Fin méthode

	/**
	 * Modifie la valeur de la largeur de la source
	 * 
	 * @param largeur La largeur de la source
	 */
	// Tin Pham
	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}// Fin méthode

	/**
	 * Retourne la hauteur de la source
	 * 
	 * @return La hauteur de la source
	 */
	// Tin Pham
	public int getHauteur() {
		return hauteur;
	}// Fin méthode

	/**
	 * Modifie la valeur de la hauteur de la source
	 * 
	 * @param hauteur La hauteur de la source
	 */
	// Tin Pham
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}// Fin méthode

	/**
	 * Méthode qui détermine si la source est survolé (ou sélectionné) ou non.
	 * @param xPix La position en x de la souris
	 * @param yPix La position en y de la souris
	 * @return Vrai si la source est survolé (ou sélectionné), faux sinon.
	 */
	// Ismaïl Boukari
	@Override
	public boolean contient(double xPix, double yPix) {
		AffineTransform matContient = new AffineTransform();
		matContient.rotate(theta, pointDeRotation.x, pointDeRotation.y);
		return (matContient.createTransformedShape(ligneVertInf).contains(xPix, yPix)|| matContient.createTransformedShape(ligneVertSup).contains(xPix, yPix)
				|| matContient.createTransformedShape(borneInf).contains(xPix, yPix) || matContient.createTransformedShape(borneSup).contains(xPix, yPix));
	}

	/**
	 * Tourne la source dans le sens des aiguilles d'une montre de 90 degrés
	 */
	// Ismaïl Boukari
	@Override
	public void rotate90Deg() {
		if (Utils.almostEqual(theta, 2 * PI, 0.01d)) {
			theta = PISUR2;
		} else {
			theta += PISUR2;
		}
		origine.setLocation(x + 25, y + 50);
		destination.setLocation(x + 25, y);
		Utils.rotatePoint(origine, pointDeRotation, theta);
		Utils.rotatePoint(destination, pointDeRotation, theta);
		if (!enPlacement) {
			generatePositionPoints();
		}

	}

	/**
	 * Tourne la source dans le sens inverse des aiguilles d'une montre de 90 degrés
	 */
	// Ismaïl Boukari
	@Override
	public void rotateMinus90Deg() {
		if (Utils.almostEqual(theta, -2 * PI, 0.01d)) {
			theta = -PISUR2;
		} else {
			theta -= PISUR2;
		}
		origine.setLocation(x + 25, y + 50);
		destination.setLocation(x + 25, y);
		Utils.rotatePoint(origine, pointDeRotation, theta);
		Utils.rotatePoint(destination, pointDeRotation, theta);
		if (!enPlacement) {
			generatePositionPoints();
		}
	}
}
