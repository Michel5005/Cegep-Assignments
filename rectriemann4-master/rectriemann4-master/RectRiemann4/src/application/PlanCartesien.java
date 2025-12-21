package application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * Composant permettant de tracer une fonction en segments avec une forme Path2D
 * Il est possible de recadrer la portion visible de la fonction.
 *
 * @author Tin Pham
 * @author Michel Vuu
 */
public class PlanCartesien extends JPanel {
	/** numero d'identification pour la serialisation */
	private static final long serialVersionUID = 1L;

	/** Valeur de default de x minimum */
	private final double DEFAUT_X_MIN = -5;
	/** Valeur de default de x maximum */
	private final double DEFAUT_X_MAX = 5;
	/** Valeur de default de y minimum */
	private final double DEFAUT_Y_MIN = -5;
	/** Valeur de default de y maximum */
	private final double DEFAUT_Y_MAX = 5;

	/** Valeur de xMin */
	private double xMin = DEFAUT_X_MIN;
	/** Valeur de xMax */
	private double xMax = DEFAUT_X_MAX;
	/** Valeur de yMin */
	private double yMin = DEFAUT_Y_MIN;
	/** Valeur de yMax */
	private double yMax = DEFAUT_Y_MAX;
	/** Valeur du nombres de segments pour approximer */
	private int nbSegmentsPourApproximer = 80;

	/** Declaration des axes */
	private Path2D.Double axes;
	/** Declaration de la ligne brisee */
	private Path2D.Double ligneBrisee;
	/** Declaration du rectangle */
	private Rectangle2D.Double rect;
	/** Declaration de la grille en x et la grille en y */
	private Path2D.Double gridX;
	/** Declaration de la grille en y */
	private Path2D.Double gridY;

	/** Valeur intial du coefficient a */
	private double a = 2.4;
	/** Valeur intial du coefficient b */
	private double b = 1.1;
	/** Valeur intial du coefficient c */
	private double c = 0.8;
	/** Valeur intial du nombre de rectangles */
	private int nbRect = 10;

	private double pixelsParUniteX;
	/** */
	private double pixelsParUniteY;
	/** Declaration de aire geometrique */
	private double aireGeo;
	/** Declaration de aire algebrique */
	private double aireAlge;
	/** Controle la visibilite des rectangles */
	private boolean rectVisible = true;

	/**
	 * Constructeur qui cree le composant et fixe la couleur de fond
	 */
	public PlanCartesien() {
		setBackground(Color.white);
	}// fin du constructeur

	/**
	 * Dessiner la fonction sur le composant
	 * 
	 * @param g Le contexte graphique
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		AffineTransform mat = new AffineTransform();
		pixelsParUniteX = getWidth() / (xMax - xMin);
		pixelsParUniteY = getHeight() / (yMax - yMin);

		mat.scale(pixelsParUniteX, pixelsParUniteY);
		mat.translate(-xMin, yMax);
		mat.scale(1, -1);

		creerAxes();
		creerGrid();
		creerApproxCourbe();

		g2d.setColor(Color.lightGray);
		g2d.draw(mat.createTransformedShape(gridX));
		g2d.draw(mat.createTransformedShape(gridY));

		g2d.setColor(Color.black);
		creerNombreRepere(g2d);
		// on dessine les axes
		g2d.setColor(Color.blue);
		g2d.draw(mat.createTransformedShape(axes));

		// on dessine la courbe

		g2d.setColor(Color.magenta);
		g2d.draw(mat.createTransformedShape(ligneBrisee));

		g2d.setColor(new Color(0, 0, 0, 100));
		if (rectVisible) {
			creerRect(g2d, mat);
		}

		g2d.setColor(Color.black);

		aireGeo();
		String aireG = String.format("%.3f", aireGeo);
		g2d.drawString("Aire géométrique : " + aireG + " u\u00b2", 0, getHeight() - 50);

		aireAlge();
		String aireA = String.format("%.3f", aireAlge);
		g2d.drawString("Aire algébrique : " + aireA + " u\u00b2", 0, getHeight() - 35);

		double diff = aireGeo - aireAlge;
		String diffe = String.format("%.3f", diff);
		g2d.drawString("Différence : " + diffe + " u\u00b2", 0, getHeight() - 20);

		double pourcentage = (diff / aireAlge) * 100.0;
		String pourc = String.format("%.3f", pourcentage);
		g2d.drawString("Pourcentage : " + pourc + " %", 0, getHeight() - 5);

	}// fin paintComponent

	/**
	 * Methode aireAlge() calcule l'aire par l'integrale, donc F(b) - F(a) = aire
	 * algebrique.
	 */
	// Huu Tin Pham
	private void aireAlge() {
		aireAlge = 0;
		double aireBorneA, aireBorneB;
		aireBorneB = this.a * Math.sin(xMax) - this.b * Math.cos(xMax) + this.c * xMax;
		aireBorneA = this.a * Math.sin(xMin) - this.b * Math.cos(xMin) + this.c * xMin;
		aireAlge = aireBorneB - aireBorneA;
	}// fin methode

	// Huu Tin Pham
	private void aireGeo() {
		double largeur = (xMax - xMin) / this.nbRect;
		double hauteur;
		aireGeo = 0;
		double aireUnRect;

		if (rectVisible) {
			for (double i = (xMin + largeur / 2); i < (xMax); i += largeur) {
				hauteur = evalFonction(i);
				aireUnRect = (hauteur * largeur);
				aireGeo += aireUnRect;
			}

		} else {
			aireGeo = 0;
		}
	}// fin methode

	/**
	 * Cette methode va creer les rectangles en-dessous de la courbe
	 * 
	 * @param g2d Le contexte graphique en 2D
	 * @param mat La matrice où les transformations sont effectuees
	 */
	// Huu Tin Pham
	private void creerRect(Graphics2D g2d, AffineTransform mat) {
		double largeur = (xMax - xMin) / this.nbRect;
		double hauteur;

		for (double i = (xMin + largeur / 2); i < (xMax); i += largeur) {
			hauteur = evalFonction(i);
			if (hauteur > 0) {
				rect = new Rectangle2D.Double(i - largeur / 2, 0, largeur, hauteur);
				g2d.fill(mat.createTransformedShape(rect));
				g2d.draw(mat.createTransformedShape(rect));
			} else {
				rect = new Rectangle2D.Double(i - largeur / 2, hauteur, largeur, -hauteur);
				g2d.fill(mat.createTransformedShape(rect));
				g2d.draw(mat.createTransformedShape(rect));
			}
		}
	}// fin methode

	/**
	 * Creation des nombres-reperes qui s'afficheront dans le graphique
	 * 
	 * @param g Le contexte graphique
	 */
	// Michel Vuu
	private void creerNombreRepere(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		double posTexteX;
		double posTexteY;
		double petitAjustX, petitAjustY;

		for (int k = (int) xMin + 1; k < (int) (xMax - yMin - 1); k++) {
			if (k >= 0) {
				petitAjustX = 3;
			} else {
				petitAjustX = 6;
			}
			posTexteX = (-xMin + k) * pixelsParUniteX;
			g2d.drawString(k + "", (int) (posTexteX - petitAjustX), (int) (yMax * pixelsParUniteY));
		}
		for (int k = (int) yMin + 1; k < (int) (yMax - xMin - 1); k++) {
			if (k >= 0) {
				petitAjustY = 3;
			} else {
				petitAjustY = 6;
			}
			posTexteY = (yMax - k) * pixelsParUniteY;
			g2d.drawString(k + "", (int) ((-xMin * pixelsParUniteX) - petitAjustY), (int) (posTexteY));

		}
	}// fin methode

	/**
	 * Creation du Path2D formant la grille
	 */
	// Michel Vuu
	private void creerGrid() {
		gridX = new Path2D.Double();
		for (int k = (int) xMin; k < (int) xMax; k++) {
			gridX.moveTo(k, yMin);
			gridX.lineTo(k, yMax);
		}
		gridY = new Path2D.Double();
		for (int k = (int) yMin; k < (int) yMax; k++) {
			gridY.moveTo(xMin, k);
			gridY.lineTo(xMax, k);
		}

	}// fin methode

	/**
	 * Creation du Path2D formant les axes
	 */
	private void creerAxes() {
		axes = new Path2D.Double();
		axes.moveTo(xMin, 0);
		axes.lineTo(xMax, 0);
		axes.moveTo(0, yMin);
		axes.lineTo(0, yMax);
	}

	/**
	 * Creation de l'approximation de la courbe sous la forme d'un Path2D
	 */
	private void creerApproxCourbe() {
		double x, y;

		ligneBrisee = new Path2D.Double();
		x = xMin; // on commence a l'extreme gauche
		y = evalFonction(x);
		ligneBrisee.moveTo(x, y);

		for (int k = 1; k < nbSegmentsPourApproximer + 1; k++) {

			x = xMin + k * (xMax - xMin) / nbSegmentsPourApproximer; // on calcule le nouveau x, un peu plus loin,
																		// où on évaluera la fonction de nouveau

			y = evalFonction(x); // modifier cette ligne!!!
			ligneBrisee.lineTo(x, y); // modifier cette ligne!!!

		} // fin for

	}

	/**
	 * Evaluation de la valeur de la fonction en vigueur pour un x donné
	 * 
	 * @param x Valeur de x
	 * @return Valeur de la fonction pour ce x, en fonction de la fonction en
	 *         vigueur
	 */
	private double evalFonction(double x) {
		double y;

		y = a * Math.cos(x) + b * Math.sin(x) + c;

		return (y);
	}

	/**
	 * Modifie le nombre de petits segments de droite qui formeront la courbe
	 * (nombre d'echantillonages)
	 * 
	 * @param nbSegmentsPourApproximer Le nombre de segments voulus
	 */
	public void setNbSegmentsPourApproximer(int nbSegmentsPourApproximer) {
		this.nbSegmentsPourApproximer = nbSegmentsPourApproximer;
		repaint(); // refaire la courbe!
	}

	/**
	 * Permet de modifier les limites entre lesquelles la fonction sera tracee
	 * 
	 * @param xMin Abcisse minimale visible
	 * @param xMax Abcisse maximale visible
	 * @param yMin Ordonnee minimale visible
	 * @param yMax Ordonnee maximale visible
	 */

	public void recadrer(double xMin, double xMax, double yMin, double yMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		repaint(); // refaire la courbe!
	}

	/**
	 * Modifie la valeur du coefficient a
	 * 
	 * @param a Le coefficient a de la fonction
	 */
	// Michel Vuu
	public void setA(double a) {
		this.a = a;
		repaint();
	}

	/**
	 * Modifie la valeur du coefficient b
	 * 
	 * @param b La valeur b de la fonction
	 */
	// Michel Vuu
	public void setB(double b) {
		this.b = b;
		repaint();
	}

	/**
	 * Modifie la valeur du coefficient c
	 * 
	 * @param c La valeur c de la fonction
	 */
	// Michel Vuu
	public void setC(double c) {
		this.c = c;
		repaint();
	}

	/**
	 * Modifie le nombre de rectangles qui s'afficheront sous la courbe
	 * 
	 * @param nbRect Le nombre de rectangles sous la fonction
	 */
	// Michel Vuu
	public void setNbRect(int nbRect) {
		this.nbRect = nbRect;
		repaint();
	}

	/**
	 * Agrandi la section visible du plan
	 */
	// Huu Tin Pham
	public void zoomIn() {
		if (xMax - xMin > 2) {
			recadrer(xMin + 1, xMax - 1, yMin + 1, yMax - 1);
		}
		repaint();
	}

	/**
	 * Retreci la section visible du plan
	 */
	// Huu Tin Pham
	public void zoomOut() {
		recadrer(xMin - 1, xMax + 1, yMin - 1, yMax + 1);

		repaint();
	}

	/**
	 * Deplace la section visible vers la droite
	 */
	// Michel Vuu
	public void deplacementD() {
		recadrer(xMin + 1, xMax + 1, yMin, yMax);

		repaint();
	}

	/**
	 * Deplace la section visible vers la gauche
	 */
	// Michel Vuu
	public void deplacementG() {
		recadrer(xMin - 1, xMax - 1, yMin, yMax);

		repaint();
	}

	/**
	 * Deplace la section visible vers le haut
	 */
	// Michel Vuu
	public void deplacementH() {
		recadrer(xMin, xMax, yMin + 1, yMax + 1);

		repaint();
	}

	/**
	 * Deplace la section visible vers le bas
	 */
	// Michel Vuu
	public void deplacementB() {
		recadrer(xMin, xMax, yMin - 1, yMax - 1);

		repaint();
	}

	/**
	 * Controle la visibilite des rectangles sous la courbe. Si l'utilisateur coche
	 * la case, les rectangles s'afficheront, sinon si la case n'est pas cochee, les
	 * rectangles ne s'afficheront pas
	 * 
	 * @param rectVisible La visibilite des rectangles
	 */
	// Huu Tin Pham
	public void setRectVisible(boolean rectVisible) {
		this.rectVisible = rectVisible;

		repaint();
	}

}// fin classe