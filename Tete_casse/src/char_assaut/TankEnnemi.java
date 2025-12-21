package char_assaut;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * Classe qui représente le char d'assaut ennemi
 * 
 * @author Michel Vuu
 *
 */
public class TankEnnemi extends Tank{

	/**
	 * Constructeur
	 * 
	 * @param x              Position en x du char d'assaut
	 * @param y              Position en y du char d'assaut
	 * @param largeur        Largeur du char d'assaut
	 * @param hauteur        Hauteur du char d'assaut
	 * @param pointDeVie     Nombre de points de vie que le char d'assaut possède
	 * @param carburant      Niveau de carburant que le char d'assaut possède
	 * @param bouclier       Bouclier que le char d'assaut possède
	 * @param nbProjectile   Nombre de projectiles que le char d'assaut possède
	 * @param pixelsParMetre Facteur de mise à l'échelle
	 */
	public TankEnnemi(double x, double y, double largeur, double hauteur, int pointDeVie, int carburant, int bouclier,
			int nbProjectile, double pixelsParMetre) {
		super(x, y, largeur, hauteur, pointDeVie, carburant, bouclier, nbProjectile, pixelsParMetre);
		creerLaGeometrie();
	}

	/**
	 * 
	 */
	private void creerLaGeometrie() {
		tank = new Rectangle2D.Double(x, y, largeur, hauteur);
	}// fin méthode

	/**
	 * Permet de dessiner le char d'assaut, sur le contexte graphique passe en
	 * parametre.
	 *
	 * @param g2d Le contexte graphique
	 * 
	 */
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);
		mat.rotate(theta, x, y);
		aireTank = new Area(mat.createTransformedShape(tank));
		g2dCopie.setColor(Color.BLACK);
		g2dCopie.fill(mat.createTransformedShape(tank));
		g2dCopie.setColor(Color.red);
		g2dCopie.setStroke(new BasicStroke(3));
		g2dCopie.draw(mat.createTransformedShape(tank));

	}// fin méthode
	
	/**
	 * Retourne le nombre de points de vie que le char d'assaut possède
	 * 
	 * @return Nombre de points de vie du char d'assaut
	 */
	public int getPointDeVie() {
		return pointDeVie;
	}// fin méthode

	/**
	 * Modifie le nombre de points de vie que le char d'assaut possède
	 * 
	 * @param pointDeVie Nombre de points de vie du char d'assaut
	 */
	public void setPointDeVie(int pointDeVie) {
		this.pointDeVie = pointDeVie;
	}// fin méthode

	/**
	 * Retourne la valeur du niveau de carburant qui est contenu dans le char
	 * d'assaut
	 * 
	 * @return Niveau de carburant que contient le char d'assaut
	 */
	public int getCarburant() {
		return carburant;
	}// fin méthode

	public void setCarburant(int carburant) {
		this.carburant = carburant;
	}// fin méthode

	public int getBouclier() {
		return bouclier;
	}// fin méthode

	public void setBouclier(int bouclier) {
		this.bouclier = bouclier;
	}// fin méthode

	public int getNbProjectile() {
		return nbProjectile;
	}// fin méthode

	public void setNbProjectile(int nbProjectile) {
		this.nbProjectile = nbProjectile;
	}// fin méthode

	/**
	 * Retourne la valeur de la position en x du char d'assaut
	 * 
	 * @return La position en x du char d'assaut
	 */
	public double getX() {
		return x;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x et recréer le char d'assaut avec la
	 * nouvelle position en x
	 * 
	 * @param x La position en x du char d'assaut
	 */
	public void setX(double x) {
		this.x = x;
		creerLaGeometrie();
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y du char d'assaut
	 * 
	 * @return La position en y du char d'assaut
	 */
	public double getY() {
		return y;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y et recréer le char d'assaut avec la
	 * nouvelle position en y
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
		creerLaGeometrie();
	}// fin méthode

	/**
	 * Retourne la largeur du char d'assaut
	 * 
	 * @return La largeur du char d'assaut
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur du char d'assaut et recréer le char d'assaut
	 * avec la nouvelle largeur
	 * 
	 * @param largeur La largeur du char d'assaut
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
		creerLaGeometrie();
	}// fin méthode

	/**
	 * Retourne la hauteur du char d'assaut
	 * 
	 * @return La hauteur du char d'assaut
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur du char d'assaut et recréer le char d'assaut
	 * avec la nouvelle hauteur
	 * 
	 * @param hauteur La hauteur du char d'assaut
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
		creerLaGeometrie();
	}// fin méthode

	public Rectangle2D.Double getTank() {
		return tank;
	}// fin méthode

	public void setTank(Rectangle2D.Double tank) {
		this.tank = tank;
	}// fin méthode

	public double getPixelsParMetre() {
		return pixelsParMetre;
	}// fin méthode

	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}// fin méthode

	public double getTheta() {
		return theta;
	}// fin méthode

	public void setTheta(double theta) {
		this.theta = theta;
	}// fin méthode
}// fin classe
