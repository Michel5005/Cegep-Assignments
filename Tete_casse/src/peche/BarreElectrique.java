package peche;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * Classe qui représente la barre magnétique
 * 
 * @author Michel Vuu
 *
 */
public class BarreElectrique {
	/** Position en x de la barre magnétique */
	private double x;
	/** Position en y de la barre magnétique */
	private double y;
	/** Largeur de la barre magnétique */
	private double largeur;
	/** Hauteur de la barre magnetique */
	private double hauteur;
	/** Rectangle fantome */
	private Rectangle2D.Double rect;
	/** Pixel par metre */
	private double pixelsParMetre;

	/**
	 * Constructeur
	 * 
	 * @param x              Position en x de la barre magnétique
	 * @param y              Position en y de la barre magnétique
	 * @param largeur        Largeur de la barre magnétique
	 * @param hauteur        Hauteur de la barre magnétique
	 * @param pixelsParMetre Pixel par metre
	 */
	public BarreElectrique(double x, double y, double largeur, double hauteur, double pixelsParMetre) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.pixelsParMetre = pixelsParMetre;
		creerLaGeometrie();
	}

	/**
	 * Permet de créer la barre magnétique qui sera affiché
	 */
	private void creerLaGeometrie() {
		rect = new Rectangle2D.Double(x, y, largeur, hauteur);
	}

	/**
	 * Permet de dessiner la barre magnétique, sur le contexte graphique passe en
	 * parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();

		g2dPrive.draw(mat.createTransformedShape(rect));
		g2dPrive.setColor(Color.red);
		g2dPrive.fillRect((int) x, (int) y, (int) largeur / 5, (int) hauteur);
		g2dPrive.fillRect((int) (x + (4 * (largeur / 5))), (int) y, (int) largeur / 5, (int) hauteur);
		g2dPrive.setColor(Color.yellow);
		g2dPrive.fillRect((int) (x + largeur / 5), (int) y, (int) largeur / 5, (int) hauteur);
		g2dPrive.fillRect((int) (x + (3 * (largeur / 5))), (int) y, (int) largeur / 5, (int) hauteur);
		g2dPrive.setColor(Color.green);
		g2dPrive.fillRect((int) (x + (2 * (largeur / 5))), (int) y, (int) largeur / 5, (int) hauteur);

	}
	
	public Area aireBarre() {
		Area barre = new Area(rect);
		
		return new Area(barre);
	}

	/**
	 * Retourne la valeur de la position en x de la barre magnétique
	 * 
	 * @return La position en x de la barre magnétique
	 */
	public double getX() {
		return x;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x et recréer la barre magnétique avec la
	 * nouvelle position en x
	 * 
	 * @param x La position en x de la barre magnétique
	 */
	public void setX(double x) {
		this.x = x;
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y de la barre magnétique
	 * 
	 * @return La position en y de la barre magnétique
	 */
	public double getY() {
		return y;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y et recréer la barre magnétique avec la
	 * nouvelle position en y
	 * 
	 * @param y La position en y de la barre magnétique
	 */
	public void setY(double y) {
		this.y = y;
	}// fin méthode

	/**
	 * Retourne la largeur de la barre magnétique
	 * 
	 * @return La largeur de la barre magnétique
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur de la barre magnétique et recréer la barre
	 * magnétique avec la nouvelle largeur
	 * 
	 * @param largeur La largeur de la barre magnétique
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}// fin méthode

	/**
	 * Retourne la hauteur de la barre magnétique
	 * 
	 * @return La hauteur de la barre magnétique
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur de la barre magnétique et recréer la barre
	 * magnétique avec la nouvelle hauteur
	 * 
	 * @param hauteur La hauteur de la barre magnétique
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}// fin méthode

	/**
	 * Retourne le pixel par metre
	 * 
	 * @return Le pixel par metre
	 */
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}// fin méthode

	/**
	 * * Modifie le pixel par metre et recreer la barre magnetique avec le nouveau
	 * pixel par metre;
	 * 
	 * @param pixelsParMetre Le pixel par metre
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}// fin méthode

}// fin classe