package circuit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.AppPrincipale7;
import interfaces.Dessinable;
import interfaces.Selectionnable;
import outils.Utils;

/**
 * Cette classe représente un fil électrique dans le circuit électrique. Un fil
 * possède un courant et un potentiel, ainsi que des coordonnées de début et de
 * fin. Il implémente les interfaces "Dessinable" et "Selectionnable".
 * 
 * @author Tin Pham
 * @author Ismaïl Boukari
 *
 */
public class Fil implements Dessinable, Serializable, Selectionnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final protected Color PLACEMENT_COLOR = new Color(0,0,0,100);
	final protected Color SELECTION_COLOR = new Color(127,255,212); 
	final protected Color HOVER_COLOR = new Color(108,180,238,180);
	final protected Color CURRENT_COLOR = new Color(255,255,0);
	final protected Color COULEUR_CERCLE = new Color(169,169,169);
	final protected Color COULEUR_DONNEES = new Color(168,50,255);
	
	protected Ellipse2D.Double cercleOriginine;
	protected Ellipse2D.Double cercleDestination;
	protected Point origine;
	protected Point destination;
	protected Node origineNode;
	protected Node destinationNode;
	protected final double RAYON = 10;
	protected boolean enPlacement = true;
	protected boolean survole = false;
	protected boolean selectionne = false;

	/** Le courant traversant le fil */
	protected double courant = 0;
	/** Le potentiel électrique du fil */
	protected double potentiel = 0;
	protected double resistance = 0.000000001;
	protected List<Rectangle2D.Double> currentRectangles = new ArrayList<Rectangle2D.Double>();
	protected List<Point> points = new ArrayList<Point>();
	protected List<Point> pointsPosition = new ArrayList<Point>();
	/** La géométrie du fil */
	private Rectangle2D.Double fil = null;
	/** Les coordonnées x et y de l'extrémité de départ du fil */
	private int x, width;
	/** Les coordonnées x et y de l'extrémité d'arrivée du fil */
	private int y, height;
	private Rectangle2D.Double rectangleDeSelection;




	/**
	 * Crée un nouveau fil avec le courant et le potentiel donnés. Les coordonnées
	 * des extrémités ne sont pas spécifiées.
	 * 
	 * @param courant   Le courant traversant le fil.
	 * @param potentiel Le potentiel électrique du fil.
	 */
	// Tin Pham
	public Fil(double courant, double potentiel) {
		this.courant = courant;
		this.potentiel = potentiel;
		cercleOriginine = new Ellipse2D.Double();
		cercleDestination = new Ellipse2D.Double();
	} //Fin constructeur

	/**
	 * Crée un nouveau fil avec les coordonnées des extrémités, le courant et le potentiel donnés.
	 * @param x La coordonnée x du fil.
	 * @param y La coordonnée y du fil.
	 * @param width La largeur du fil.
	 * @param height La hauteur du fil.
	 * @param courant Le courant traversant le fil.
	 * @param potentiel Le potentiel électrique du fil.
	 */
	// Tin Pham
	public Fil(int x, int y, int width, int height, double courant, double potentiel) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.courant = courant;
		this.potentiel = potentiel;
		cercleOriginine = new Ellipse2D.Double();
		cercleDestination = new Ellipse2D.Double();
		creerLaGeometrie();
	} //Fin constructeur
	
	/**
	 * Crée un nouveau fil à partir d'un fil déjà créé (copie)
	 * @param fil Le fil à copier.
	 */ 
	// Ismaïl Boukari
	public Fil(Fil fil) {
		this.x = fil.getX();
		this.y = fil.getY();
		this.width = fil.getWidth();
		this.height = fil.getHeight();
		this.courant = fil.getCourant();
		this.potentiel = fil.getPotentiel();
		this.origine = fil.getOrigine();
		this.destination = fil.getDestination();
		cercleOriginine = new Ellipse2D.Double();
		cercleDestination = new Ellipse2D.Double();
		creerLaGeometrie();
	}//Fin méthode
	
	/**
	 * Cette méthode crée la géométrie du fil.
	 */
	// Ismaïl Boukari
	private void creerLaGeometrie() {
		fil = new Rectangle2D.Double(x, y, width, height);
		rectangleDeSelection = new Rectangle2D.Double(x-2, y-2, width+4, height+4);
	}//Fin méthode

	/**
	 * Cette méthode dessine le fil. Elle prend en paramètre un objet "Graphics2D".
	 * @param g2d Le contexte graphique.
	 */
	// Tin Pham
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		if (selectionne) {
			g2dCopie.setColor(SELECTION_COLOR);
			g2dCopie.fill(rectangleDeSelection);
		} else if (survole) {
			g2dCopie.setColor(HOVER_COLOR);
			g2dCopie.fill(rectangleDeSelection);
		} 
		if (enPlacement) {
			g2dCopie.setColor(PLACEMENT_COLOR);
		}
		else {
			g2dCopie.setColor(Color.BLACK);
		}
		g2dCopie.fill(fil);
	}//Fin méthode
	
	/**
	 * Cette méthode permet de dessiner les cercles aux extrémités du fil.
	 * @param g Contexte graphique
	 */
	// Ismaïl Boukari
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
	 * Cette méthode dessine les données scientifiques du fil.
	 * @param g Le contexte graphique.
	 */
	// Ismaïl Boukari
	public void dessinerDonnesScientifiques(Graphics2D g) {
		if (survole) {
			Graphics2D g2dCopie = (Graphics2D) g.create();
			g2dCopie.setColor(COULEUR_DONNEES);
			g2dCopie.setFont(AppPrincipale7.interBlack.deriveFont(15f));
			g2dCopie.drawString("I = " + String.format("%." + 3 + "f", courant) + " ampère(s)", (int)(x+width/2-15), (int)(y+height/2-35));
		}
	}
	/**
	 * Cette méthode dessine le courant dans le fil.
	 * @param g2d Contexte graphique
	 */
	// Ismaïl Boukari
	public void dessinerCourant(Graphics2D g2d) {
		Graphics2D g2dCopie = g2d;
		if (courant>0 && currentRectangles.size()>0) {
			g2dCopie.setColor(CURRENT_COLOR);
			for (int i = 0; i < currentRectangles.size()-1; i++) {
				g2dCopie.fill(currentRectangles.get(i));
			}
		}
	}

	/**
	 * Cette méthode retourne le courant dans le fil.
	 * 
	 * @return courant Le courant
	 */
	// Tin Pham
	public double getCourant() {
		return courant;
	}//Fin méthode

	/**
	 * Cette méthode modifie le courant dans le fil. Elle prend en paramètre le
	 * nouveau courant.
	 * 
	 * @param courant Le courant
	 */
	// Tin Pham
	public void setCourant(double courant) {
		this.courant = courant;
	}//Fin méthode

	/**
	 * Cette méthode retourne le potentiel dans le fil.
	 * 
	 * @return potentiel Le potentiel
	 */
	// Tin Pham
	public double getPotentiel() {
		return potentiel;
	}//Fin méthode

	/**
	 * Cette méthode modifie le potentiel dans le fil. Elle prend en paramètre le
	 * nouveau potentiel.
	 * 
	 * @param potentiel Le potentiel
	 */
	// Ismaïl Boukari
	public void setPotentiel(double potentiel) {
		this.potentiel = potentiel;
	}//Fin méthode

	/**
	 *
	 * Cette méthode retourne si le fil contient le point (xPix, yPix). Elle prend
	 * en paramètre les coordonnées du point en pixels.
	 * @param xPix Coordonnée x du point en pixels
	 * @param yPix Coordonnée y du point en pixels
	 * @return boolean Vrai si le fil contient le point, faux sinon.
	 */
	// Tin Pham
	@Override
	public boolean contient(double xPix, double yPix) {
		return fil.contains(xPix, yPix);
	}//Fin méthode
	
	// Tin Pham
	public int getX() {
		return x;
	}

	//	Tin Pham
	public int getWidth() {
		return width;
	}

	//	Tin Pham
	public int getY() {
		return y;
	}

	// Tin Pham
	public int getHeight() {
		return height;
	}
	
	// Tin Pham
	public void setX(int x) {
		int dx = x - this.x;
		if (origine != null && destination != null) {
			origine.setLocation(origine.getX() + dx, origine.getY());
			destination.setLocation(destination.getX() + dx, destination.getY());
			System.out.println("(DRAG) Origine: " + origine + ", Destination: " + destination);
		}
		this.x = x;
		creerLaGeometrie();
		if (!enPlacement) {
			generatePositionPoints();
		}
	}
	
	// Tin Pham
	public void setWidth(int width) {
		this.width = width;
		creerLaGeometrie();
	}
	
	// Tin Pham
	public void setY(int y) {
		int dy = y - this.y;
		if (origine != null && destination != null) {
			origine.setLocation(origine.getX(), origine.getY() + dy);
			destination.setLocation(destination.getX(), destination.getY() + dy);
			System.out.println("(DRAG) Origine: " + origine + ", Destination: " + destination);
		}
		this.y = y;
		creerLaGeometrie();
		if (!enPlacement) {
			generatePositionPoints();
		}
	}
	
	// Tin Pham
	public void setHeight(int height) {
		this.height = height;
		creerLaGeometrie();
	}

	// Ismaïl Boukari
	public Point getOrigine() {
		return origine;
	}

	// Ismaïl Boukari
	public void setOrigine(Point origine) {
		this.origine = origine;
	}

	// Ismaïl Boukari
	public Point getDestination() {
		return destination;
	}

	// Ismaïl Boukari
	public void setDestination(Point destination) {
		this.destination = destination;
	}

	// Ismaïl Boukari
	public Node getOrigineNode() {
		return origineNode;
	}

	// Ismaïl Boukari
	public void setOrigineNode(Node origineNode) {
		this.origineNode = origineNode;
	}
	
	// Ismaïl Boukari
	public Node getDestinationNode() {
		return destinationNode;
	}

	// Tin Pham
	public double getResistance() {
		return resistance;
	}

	// Tin Pham
	public void setResistance(double resistance) {
		this.resistance = resistance;
	}

		
	// Ismaïl Boukari
	public void setDestinationNode(Node destinationNode) {
		this.destinationNode = destinationNode;
	}

	// Ismaïl Boukari
	public boolean isSelectionne() {
		return selectionne;
	}

	// Ismaïl Boukari
	public void setSelectionne(boolean filSelectionne) {
		this.selectionne = filSelectionne;
	}

	// Ismaïl Boukari
	public boolean isSurvole() {
		return survole;
	}

	// Ismaïl Boukari
	public void setSurvole(boolean filSurvole) {
		this.survole = filSurvole;
	}
	
	/**
	 * Méthode qui effectue la rotation d'un composant de 90 degrés dans le sens des aiguilles d'une montre.
	 */
	// Ismaïl Boukari
	public void rotate90Deg() {
		// Les fils ne seront pas tournés.
	}
	
	/**
	 * Méthode qui effectue la rotation d'un composant de 90 degrés dans le sens inverse des aiguilles d'une montre.
	 */
	// Ismaïl Boukari
	public void rotateMinus90Deg() {
		// Les fils ne seront pas tournés.
	}

	// Ismaïl Boukari
	public boolean isEnPlacement() {
		return enPlacement;
	}

	// Ismaïl Boukari
	public void setEnPlacement(boolean enPlacement) {
		this.enPlacement = enPlacement;
	}

	/**
	 * Cette méthode génère les points de position du fil. 
	 */
	// Ismaïl Boukari
	public void generatePositionPoints() {
		pointsPosition.clear();
		try {
			int dx = (int) (destination.getX() - origine.getX());
			int dy = (int) (destination.getY() - origine.getY());
			pointsPosition.add(origine);
			
			if (dx>25) {
				for (int i = 1; i <= dx/25-1; i++) {
					Point p = new Point((int) (origine.getX() + i*25), (int) (origine.getY()));
					pointsPosition.add(p);
				}
			}
			else if (dx < -25) {
				for (int i = 1; i <= (dx/-25)-1; i++) {
					Point p = new Point((int) (origine.getX() - i*25), (int) (origine.getY()));
					pointsPosition.add(p);
				}
			}
			else if (dy > 25) {
				for (int i = 1; i <= dy/25-1; i++) {
					Point p = new Point((int) (origine.getX()), (int) (origine.getY() + i*25));
					pointsPosition.add(p);
				}
			}
			else if (dy < -25) {
				for (int i = 1; i <= dy/-25-1; i++) {
					Point p = new Point((int) (origine.getX()), (int) (origine.getY() - i*25));
					pointsPosition.add(p);
				}
			}

			pointsPosition.add(destination);
		} catch (NullPointerException e) {
			System.out.println(" (GENERATION DE POINTS POSITION) NullPointerException");
		}
	}

	/**
	 * Cette méthode génère les points du fil. 
	 */
	// Ismaïl Boukari
	public void generatePoints() {
		points.clear();
		int dx = (int) (destinationNode.getPosition().getX() - origineNode.getPosition().getX());
		int dy = (int) (destinationNode.getPosition().getY() - origineNode.getPosition().getY());

		points.add(origine);

		if (dx>25) {
			for (int i = 1; i <= dx/25-1; i++) {
				Point p = new Point((int) (origine.getX() + i*25), (int) (origine.getY()));
				points.add(p);
			}
		}
		else if (dx < -25) {
			for (int i = 1; i <= (dx/-25)-1; i++) {
				Point p = new Point((int) (origine.getX() - i*25), (int) (origine.getY()));
				points.add(p);
			}
		}
		else if (dy > 25) {
			for (int i = 1; i <= dy/25-1; i++) {
				Point p = new Point((int) (origine.getX()), (int) (origine.getY() + i*25));
				points.add(p);
			}
		}
		else if (dy < -25) {
			for (int i = 1; i <= dy/-25-1; i++) {
				Point p = new Point((int) (origine.getX()), (int) (origine.getY() - i*25));
				points.add(p);
			}
		}
		points.add(destination);

	}

	/**
	 * Cette méthode génère les rectangles (courant) du fil.
	 */
	// Ismaïl Boukari
	public void generateCurrentRectangles() {
		currentRectangles.clear();
		for (Point p : points) {
			Rectangle2D.Double r = new Rectangle2D.Double(p.getX()-2, p.getY()-2, 4, 4);
			currentRectangles.add(r);
		}
	}

	/**
	 * Cette effectue une étape de l'animation du courant du fil.
	 */
	// Ismaïl Boukari
	public void currentAnimationStep() {
		// wow :o
		if (points.size()>0) {
			int dx = (int) (destinationNode.getPosition().getX() - origineNode.getPosition().getX());
			int dy = (int) (destinationNode.getPosition().getY() - origineNode.getPosition().getY());

			double destinationNodePositionX = destinationNode.getPosition().getX();
			double destinationNodePositionY = destinationNode.getPosition().getY();
			double origineNodePositionX = origineNode.getPosition().getX();
			double origineNodePositionY = origineNode.getPosition().getY();
			int hauteur = 4;
			int largeur = 4;
			
			if (dx != 0) {
				dx = dx/Math.abs(dx);
			}
			if (dy != 0) {
				dy = dy/Math.abs(dy);
			}
			double stepX;
			double stepY;
			if (courant > 100) {
				stepX = (dx*0.03*100);
				stepY = (dy*0.03*100);
			}
			else {
				stepX = (dx*0.03*courant);
				stepY = (dy*0.03*courant);
			}

			if (courant > 0.0001) {
				for (int i = 0; i < currentRectangles.size()-1; i++) {
					Rectangle2D.Double r = currentRectangles.get(i);

					double rX = r.getX();
					double rY = r.getY();

					if (dx < 0) { //
						if (rX+stepX < destinationNodePositionX-2 || Utils.almostEqual(rX+stepX, destinationNodePositionX-2, 0.01)) {
							r.setRect(origineNodePositionX-2, origineNodePositionY-2, largeur, hauteur);
						} else {
							r.setRect(rX+stepX, rY, largeur, hauteur);
						}
					}	
					if (dx > 0) {
						if (rX+stepX+largeur > destinationNodePositionX+2|| Utils.almostEqual(rX+stepX+largeur, destinationNodePositionX+2, 0.01)) {
							r.setRect(origineNodePositionX-2, origineNodePositionY-2, largeur, hauteur);
						} else {
							r.setRect(rX+stepX, rY, largeur, hauteur);
						}
					}
					if (dy < 0) {
						if (r.getY()+stepY < destinationNodePositionY-2 || Utils.almostEqual(r.getY()+stepY, destinationNodePositionY-2, 0.01)) {
							r.setRect(origineNodePositionX-2, origineNodePositionY-2, largeur, hauteur);
						} else {
							r.setRect(rX, rY+stepY, largeur, hauteur);
						}
					}
					if (dy > 0) { //
						if (r.getY()+stepY+hauteur > destinationNodePositionY+2 || Utils.almostEqual(r.getY()+stepY+hauteur, destinationNodePositionY+2, 0.01)) {
							r.setRect(origineNodePositionX-2, origineNodePositionY-2, largeur, hauteur);
						} else {
							r.setRect(rX, rY+stepY, largeur, hauteur);
						}
					}
				}
			}
		}
	}

	/**
	 * Cette méthode permet de savoir si un point est dans le fil.
	 * @param p le point à tester
	 * @return true si le point est dans le fil, false sinon
	 */
	// Ismaïl Boukari
	public boolean isPointInside(Point p) {
		for (int i = 1; i < pointsPosition.size()-1; i++) {
			if (p.equals(pointsPosition.get(i))) {
				return true;
			}
		}
		return false;
	}

	// Ismaïl Boukari
	public List<Point> getPoints() {
		return points;
	}

	// Ismaïl Boukari
	public List<Point> getPositionPoints() {
		return pointsPosition;
	} 
}
