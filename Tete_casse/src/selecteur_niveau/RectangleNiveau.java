package selecteur_niveau;

import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import interfaces.Dessinable;
import interfaces.Selectionnable;
/**
 * Classe représentant un rectangle avec des coins arrondis.
 * @author Ismaïl Boukari
 *
 */
public class RectangleNiveau implements Dessinable, Selectionnable {
	
	/**
	 * Position x du composant
	 */ 
	private int x;
	/**
	 * Position y du composant
	 */
	private int y;
	/**
	 * Largeur y du composant
	 */
	private int largeur;
	/**
	 * Hauteur y du composant
	 */
	private int hauteur;
	/**
	 * Arc d'arrondissement du rectangle
	 */
	private final int ARC = 15;
	
	/**
	 * Rectangle arrondi
	 */
	private RoundRectangle2D.Double rectangle;
	
	/**
     * Constructeur d'un rectangle avec coins arrondis à partir de ses coordonnées et dimensions.
     *
     * @param x la coordonnée x du coin supérieur gauche du rectangle
     * @param y la coordonnée y du coin supérieur gauche du rectangle
     * @param largeur la largeur du rectangle
     * @param hauteur la hauteur du rectangle
     */
	public RectangleNiveau(int x, int y, int largeur, int hauteur) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}
	
	/**
     * Constructeur d'un rectangle avec coins arrondis à partir de ses dimensions et positionné en (0,0).
     *
     * @param largeur la largeur du rectangle
     * @param hauteur la hauteur du rectangle
     */
	public RectangleNiveau(int largeur, int hauteur) {
		this.x = 0;
		this.y = 0;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}
	
	/**
     * Crée la géométrie du rectangle avec coins arrondis.
     */
	public  void creationDeLaGeometrie() {
		rectangle = new RoundRectangle2D.Double(this.x,this.y, this.largeur, this.hauteur, this.ARC, this.ARC);
	}
	
	/**
     * Dessine le rectangle avec coins arrondis.
     *
     * @param g2d le contexte graphique dans lequel le rectangle doit être dessiné
     */
	@Override
	public void dessiner(Graphics2D g2d) {
		g2d.fill(rectangle);
	}
	
	
	 /**
     * Modifie la position et les dimensions du rectangle avec coins arrondis.
     *
     * @param x la nouvelle coordonnée x du coin supérieur gauche du rectangle
     * @param y la nouvelle coordonnée y du coin supérieur gauche du rectangle
     * @param largeur la nouvelle largeur du rectangle
     * @param hauteur la nouvelle hauteur du rectangle
     */
	public void setFrame(int x, int y, int largeur, int hauteur) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}
	
	/**
     * Modifie la position du coin supérieur gauche du rectangle avec coins arrondis.
     *
     * @param x la nouvelle coordonnée x du coin supérieur gauche du rectangle
     * @param y la nouvelle coordonnée y du coin supérieur gauche du rectangle
     */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Détermine si le rectangle avec coins arrondis contient un point.
	 * @param xPix la coordonnée x du point
	 * @param yPix la coordonnée y du point
	 * @return true si le rectangle avec coins arrondis contient le point, false sinon
	 */
	@Override
	public boolean contient(double xPix, double yPix) {
		
		return rectangle.contains(xPix, yPix);
	}
	
}
