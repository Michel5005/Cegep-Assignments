package char_assaut;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;

/**
 * Créer un Canon qui change selon l'angle de tir
 * 
 * @author Jason Yin
 *
 */
public class Canon implements Dessinable {

	private double x, y;
	private double largeur;
	private double longueur;
	private double angle;
	private double pixelsParMetre = 1;

	private Rectangle2D.Double canon = null;

	/**
	 * Créer un objet canon
	 * 
	 * @param x        Position du tank en x
	 * @param y        Position du tank en y
	 * @param largeur  Largeur du tank
	 * @param longueur Longueur du tank
	 * @param angle    Angle de tir
	 */
	public Canon(double x, double y, double largeur, double longueur, double angle, double pixelsParMetre) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.longueur = longueur;
		this.angle = angle;
		this.pixelsParMetre = pixelsParMetre;
		creerLaGeometrie();
	}

	/**
	 * Créer la géométrie du canon
	 */
	private void creerLaGeometrie() {
		canon = new Rectangle2D.Double(x, y, longueur, largeur);
	}

	/**
	 * Calcul l'angle de tir en radian
	 * 
	 * @return l'angle de tir en radian
	 */
	private double calculPositionCanon() {
		return angle * (Math.PI / 180);
	}

	/**
	 * Permet de dessiner la balle, sur le contexte graphique passe en parametre.
	 * 
	 * @param g2d contexte graphique
	 */
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);
		mat.rotate(angle - calculPositionCanon(), x, y);
		g2dCopie.setColor(Color.ORANGE);
		g2dCopie.fill(mat.createTransformedShape(canon));
	}

	public double getX() {
		return x;

	}

	public void setX(double x) {
		this.x = x;
		creerLaGeometrie();
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		creerLaGeometrie();
	}

	public double getLargeur() {
		return largeur;
	}

	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}

	public double getLongueur() {
		return longueur;
	}

	public void setLongueur(double longueur) {
		this.longueur = longueur;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
		creerLaGeometrie();
	}

	public double getPixelsParMetre() {
		return pixelsParMetre;
	}

	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}
}
