package circuit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.AppPrincipale7;
import interfaces.Dessinable;
import interfaces.Selectionnable;

/**
*	La classe Node représente un nœud dans un circuit électrique. Un nœud est 
*	caractérisé par sa tension, son courant, sa position, ainsi que d'autres propriétés 
*	comme son apparence graphique et son état de sélection. Cette classe implémente les 
*	interfaces Serializable,Dessinable et Selectionnable.
*
*	@author Ismaïl Boukari
*/
public class Node implements Serializable,Dessinable,Selectionnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Color COULEUR = new Color(169,169,169);
	private final Color COULEUR_SELECTION = new Color(200,200,200);
	private final Color COULEUR_DONNEES = new Color(127,200,212);

	private double voltage = 0; 
	private double courant;
	private Point position;
	private Ellipse2D.Double cercle;
	private final double RAYON = 10;
	private boolean afficherVoltage = false;
	private List<Node> neighbours = new ArrayList<Node>();
	private List<Node> neighboursVisited = new ArrayList<Node>();
	private int number = 0;

	
	/**
	*	Construit un nouveau nœud avec la position, la tension et le courant spécifiés.
	*	@param position la position du nœud
	*	@param voltage la tension du nœud
	*	@param courant le courant du nœud
	*/
	public Node (Point position, double voltage, double courant) {
		this.voltage = voltage;
		this.courant = courant;
		this.position = position;
		cercle = new Ellipse2D.Double(position.getX()-RAYON/2,position.getY()-RAYON/2,RAYON,RAYON);
	}
	
	/**
	*	Dessine le nœud avec ses propriétés graphiques actuelles.
	*	@param g2d le contexte graphique
	*/
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		g2dCopie.setColor(Color.BLUE);
		
		if (afficherVoltage) {
			g2dCopie.setColor(COULEUR_DONNEES);
			g2dCopie.setFont(AppPrincipale7.interBlack.deriveFont(15f));
			g2dCopie.drawString("V = " + String.format("%." + 3 + "f", voltage) + " volt(s)", (int)position.getX()-15, (int)position.getY()-15);
			g2dCopie.setColor(COULEUR_SELECTION);
			g2dCopie.fill(cercle);
		} else {
			g2dCopie.setColor(COULEUR);
			g2dCopie.fill(cercle);

		}
		g2dCopie.setColor(Color.BLACK);
		g2dCopie.setStroke(new BasicStroke(2));
		g2dCopie.draw(cercle);
	}

	
	/**
	 * Méthode ajoutant un voisin au noeud.
	 * @param neighbour Voisin à ajouter
	 */
	public void addNeighbour(Node neighbour) {
		if (!neighbours.contains(neighbour)){
			neighbours.add(neighbour);
			neighbour.addNeighbour(this);
		}
	}

	/**
	 * Méthode retournant la liste des voisins du noeud.
	 * @return Liste des voisins
	 */
	public List<Node> getNeighbours() {
		return neighbours;
	}

	/**
	 * Méthode permettant de modifier la liste des voisins du noeud.
	 * @param neighbours Nouvelle liste de voisins
	 */
	public void setNeighbours(List<Node> neighbours) {
		this.neighbours = neighbours;
	}
	
	/**
	 * Méthode permettant d'ajouter un voisin visité au noeud.
	 * @param node Voisin visité à ajouter
	 */
	public void addNdVisited(Node node) {
		if (!neighboursVisited.contains(node)){
			neighboursVisited.add(node);
		}
	}

	/**
	 * Méthode retournant la liste des voisins visités du noeud.
	 * @return Liste des voisins visités
	 */
	public List<Node> getNdVisited() {
		return neighboursVisited;
	}

	/**
	Détermine si le point spécifié est à l'intérieur du nœud.
	@param xPix la coordonnée x du point
	@param yPix la coordonnée y du point
	@return true si le point est à l'intérieur du nœud, false sinon.
	*/
	@Override
	public boolean contient(double xPix, double yPix) {
		return cercle.contains(xPix, yPix);
	}

	/**
	 *	Retourne l'indicateur pour afficher ou non la tension et le courant du nœud.
	 *	@return true si la tension et le courant doivent être affichés, false sinon.
	 */
	public boolean isAfficherVoltage() {
		return afficherVoltage;
	}
	
	public void setAfficherVoltage(boolean afficherVoltage) {
		this.afficherVoltage = afficherVoltage;
	}

	public double getVoltage() {
		return voltage;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}

	public double getCourant() {
		return courant;
	}

	public void setCourant(double courant) {
		this.courant = courant;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Ellipse2D.Double getCercle() {
		return this.cercle;
	}

	public void setCercle(Ellipse2D.Double cercle) {
		this.cercle = cercle;
	}

	public double getRAYON() {
		return this.RAYON;
	}


	public boolean getAfficherVoltage() {
		return this.afficherVoltage;
	}


	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
