package curling;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Classe représentant les graphiques pour la vitesse et l'accélération.
 * 
 * @author Liangchen Liu
 */
public class Graphiques extends JPanel {
	private final double DEFAUT_X_MIN = 0;
	private final double DEFAUT_X_MAX = 10;
	private final double DEFAUT_Y_MIN = -5;
	private final double DEFAUT_Y_MAX = 5;

	private double xMin = DEFAUT_X_MIN;
	private double xMax = DEFAUT_X_MAX;
	private double yMin = DEFAUT_Y_MIN;
	private double yMax = DEFAUT_Y_MAX;

	private double xClick;
	private double yClick;

	private Path2D.Double axes, ligneBrisee, quadrillage;

	private boolean enCoursDAnimation = false;
	private ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();

	private int graphiqueNum = 0;

	private double pixelsParUniteX;
	private double pixelsParUniteY;

	private boolean deplacementGraphique = true;

	private int compteur = 0;

	private double temps;
	private double posY;

	/**
	 * Constructeur du panel.
	 */
	public Graphiques() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						yMax += 1;
						yMin += 1;
						recadrer(xMin, xMax, yMin, yMax);

						break;
					case KeyEvent.VK_DOWN:
						yMax -= 1;
						yMin -= 1;
						recadrer(xMin, xMax, yMin, yMax);

						break;
					case KeyEvent.VK_LEFT:
						if (xMin != 0) {
							xMax -= 1;
							xMin -= 1;
							recadrer(xMin, xMax, yMin, yMax);

						}

						break;
					case KeyEvent.VK_RIGHT:
						xMax += 1;
						xMin += 1;
						recadrer(xMin, xMax, yMin, yMax);

						break;

					case KeyEvent.VK_L:
						if (compteur % 2 == 0) {
							deplacementGraphique = false;

						} else {
							deplacementGraphique = true;
						}
						compteur++;

						break;
				}

			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xClick = e.getX();
				yClick = e.getY();
				requestFocusInWindow();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int directionX = e.getX() - (int) xClick;
				if (directionX < 0) {
					xMax += 1;
					xMin += 1;
					xClick = e.getX();
					recadrer(xMin, xMax, yMin, yMax);

				} else {
					if (xMin + 5 != xMax && !(xMin <= DEFAUT_X_MIN)) {
						xMax -= 1;
						xMin -= 1;
						xClick = e.getX();
						recadrer(xMin, xMax, yMin, yMax);

					}

				}

				int directionY = e.getY() - (int) yClick;
				if (directionY > 0) {
					yMax += 1;
					yMin += 1;
					yClick = e.getY();
					recadrer(xMin, xMax, yMin, yMax);

				} else {
					yMax -= 1;
					yMin -= 1;
					yClick = e.getY();
					recadrer(xMin, xMax, yMin, yMax);

				}

			}
		});
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				requestFocusInWindow();
				int direction = e.getWheelRotation();
				if (xMin + 5 == xMax && direction == -1) {
					direction = 0;

				} else if ((xMin <= DEFAUT_X_MIN) && direction == 1) {
					xMax += direction;
					yMax += direction;
					recadrer(xMin, xMax, yMin, yMax);

				} else {
					xMax += direction;
					xMin -= direction;

					recadrer(xMin, xMax, yMin, yMax);

				}

			}
		});
		setBackground(Color.white);

	}// fin constructeur

	public void reini(){
		points.clear();
		temps = 0;
		posY = 0;

	}

	/**
	 * Permet de dessiner les éléments sur le contexte graphique qui passe en
	 * parametre.
	 * 
	 * @param g Le contexte graphique
	 */
	@Override
	public void paintComponent(Graphics g) {
		pixelsParUniteX = getWidth() / (xMax - xMin);
		pixelsParUniteY = getHeight() / (yMax - yMin);

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform mat = new AffineTransform();

		mat.scale(pixelsParUniteX, pixelsParUniteY);
		mat.translate(-xMin, yMax);
		mat.scale(1, -1);

		addPoint(temps, posY);

		creerAxes();
		creerApproxCourbe();
		creerQuadrillage();

		Shape axesTransfo = mat.createTransformedShape(axes);

		Shape ligneBriseeTranfo = mat.createTransformedShape(ligneBrisee);

		Shape quadrillageTransfo = mat.createTransformedShape(quadrillage);

		// on dessine le quadrillage
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.draw(quadrillageTransfo);

		// on dessine les axes
		g2d.setColor(Color.blue);
		g2d.draw(axesTransfo);

		// on dessine les nombres repères
		drawNbReperes(g2d, pixelsParUniteX, pixelsParUniteY);

		// on dessine la courbe
		g2d.setColor(Color.magenta);

		g2d.draw(ligneBriseeTranfo);
	}

	/**
	 * Creation du Path2D formant les axes
	 */
	private void creerAxes() {
		axes = new Path2D.Double();
		axes.moveTo(xMin, 0);
		axes.lineTo(xMax, 0);
		axes.moveTo(xMin, yMin);
		axes.lineTo(xMin, yMax);
	}

	/**
	 * Creation de l'approximation de la courbe sous la forme d'un Path2D
	 */
	private void creerApproxCourbe() {
		ligneBrisee = new Path2D.Double();

		for (int i = 0; i + 1 < points.size(); i++) {
			Point2D.Double p1 = points.get(i);
			Point2D.Double p2 = points.get((i + 1));
			ligneBrisee.moveTo(p1.getX(), p1.getY() * pixelsParUniteY);

			ligneBrisee.lineTo(p2.getX(), p2.getY() * pixelsParUniteY);

			if (deplacementGraphique) {
				if (p2.getX() > xMax) {
					xMin += 1;
					xMax += 1;
					recadrer(xMin, xMax, yMin, yMax);
				}

			}

		}

	}// fin méthode

	/**
	 * Ajout d'un point à la liste des points
	 * 
	 * @param x Abscisse du point
	 * @param y Ordonnée du point
	 */
	private void addPoint(double x, double y) {
		Point2D.Double p = new Point2D.Double(x, y);

		points.add(p);

	}

	/**
	 * Méthode qui permet de dessin les nombres repères
	 * 
	 * @param g2d             graphique 2d
	 * @param pixelsParUniteX Nombre de pixels par unité de x
	 * @param pixelsParUniteY Nombre de pixels par unité de y
	 */
	private void drawNbReperes(Graphics2D g2d, double pixelsParUniteX, double pixelsParUniteY) {
		g2d.setColor(Color.GRAY);
		double posTexteXx;
		double posTexteXy;
		for (int k = (int) xMin; k <= (int) (xMax); k++) {
			posTexteXx = (-xMin + k) * pixelsParUniteX + 5;
			posTexteXy = (getHeight() / (yMax - yMin) * yMax) - 5;

			if (posTexteXy < 0) {
				if (k != xMin) {
					posTexteXy = 15;

				}

			} else if (posTexteXy > getHeight()) {
				posTexteXy = getHeight() - 5;

			}
			g2d.drawString(k + "", (int) (posTexteXx), (int) (posTexteXy));

		}
		double posTexteY;
		for (int k = (int) yMin; k <= (int) (yMax); k++) {
			posTexteY = getHeight() - (-yMin + k) * pixelsParUniteY + 15;
			g2d.drawString(k + "", (int) Math.max((getWidth() / (xMax - xMin) * -xMin), 0) + 5, (int) (posTexteY));

		}
	}// fin méthode

	/**
	 * Creation du Path2D formant le quadrillage
	 */
	private void creerQuadrillage() {
		quadrillage = new Path2D.Double();
		double nbUniteX = xMax - xMin;
		double nbUniteY = yMax - yMin;
		for (int i = 1; i < nbUniteX; i++) {
			quadrillage.moveTo(xMin + i, yMin);
			quadrillage.lineTo(xMin + i, yMax);
		}
		for (int i = 1; i < nbUniteY; i++) {
			quadrillage.moveTo(xMin, yMin + i + 0.01);
			quadrillage.lineTo(xMax, yMin + i + 0.01);
		}

	}// fin méthode

	public int getGraphiqueNum() {
		return graphiqueNum;
	}

	public void setGraphiqueNum(int graphiqueNum) {
		this.graphiqueNum = graphiqueNum;
	}

	public boolean isEnCoursDAnimation() {
		return enCoursDAnimation;
	}

	public void setEnCoursDAnimation(boolean enCoursDAnimation) {
		this.enCoursDAnimation = enCoursDAnimation;
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
	}// fin méthode

	public double getTemps() {
		return temps;
	}

	public void setTemps(double temps) {
		this.temps = temps;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

}// fin classe
