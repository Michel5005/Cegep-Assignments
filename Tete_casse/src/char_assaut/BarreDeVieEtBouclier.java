package char_assaut;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;

/**
 * Classe qui dessine la barre de vie et de bouclier
 * @author Jason Yin
 *
 */

public class BarreDeVieEtBouclier implements Dessinable {
	private int nbPointDeVie;
	private int nbBouclier;
	private final int NB_POINT_DE_VIE_INIT = 100;
	private final int NB_POINT_BOUCLIER_INIT = 50;
	private double xPointDeVie;
	private double yPointDeVie;
	private double xBouclier;
	private double yBouclier;
	private double hauteurBarreVie = 10;
	private double hauteurBarreBouclier = 5;
	private double largeurBarreDeVie;
	private double largeurBarreBouclier;
	private Rectangle2D.Double barreDeVie;
	private Rectangle2D.Double barreBouclier;
	private Rectangle2D.Double barreDeViePerdue;
	private Rectangle2D.Double barreBouclierPerdu;
	private double pixelsParMetre = 1;

	/**
	 * Créer une barre de vie et une barre de bouclier
	 * @param nbPointDeVie Nombre de point de vie
	 * @param nbBouclier Nombre de point de bouclier
	 * @param xPointDeVie Position de la barre de vie en x
	 * @param yPointDeVie Position de la barre de vie en y
	 * @param xBouclier Position de la barre de bouclier en x
	 * @param yBouclier Position de la barre de bouclier en y
	 * @param largeur  Largeur des barres
	 */
	public BarreDeVieEtBouclier ( int nbPointDeVie,int nbBouclier,double xPointDeVie,
			double yPointDeVie, double xBouclier, double yBouclier,double largeur,double pixelsParMetre) {
		this.nbPointDeVie=nbPointDeVie;
		this.nbBouclier=nbBouclier;
		this.xPointDeVie=xPointDeVie;
		this.yPointDeVie=yPointDeVie;
		this.xBouclier=xBouclier;
		this.yBouclier=yBouclier;
		this.largeurBarreDeVie=largeur;
		this.largeurBarreBouclier = largeur;
		this.pixelsParMetre = pixelsParMetre;
		creerLaGeometrie();
	}

	/**
	 * Créer la géométrie des deux barres
	 */
	private void creerLaGeometrie() {
		barreDeVie = new Rectangle2D.Double (xPointDeVie,yPointDeVie,largeurBarreDeVie,(hauteurBarreVie/pixelsParMetre));
		barreBouclier = new Rectangle2D.Double(xBouclier, yBouclier, largeurBarreBouclier,(hauteurBarreBouclier/pixelsParMetre));
		//gererPerteDeVieEtBouclier();
	}

	/**
	 * Permet de dessiner la balle, sur le contexte graphique passe en parametre.
	 * @param g2d contexte graphique
	 */
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = g2d;
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);
		g2dCopie.setColor(Color.green);
		g2dCopie.fill(mat.createTransformedShape(barreDeVie));
		g2dCopie.setColor(Color.blue);
		g2dCopie.fill(mat.createTransformedShape(barreBouclier));

		gererPerteDeVieEtBouclier();
		g2dCopie.setColor(Color.red);
		g2dCopie.fill(mat.createTransformedShape(barreDeViePerdue));
		g2dCopie.setColor(Color.LIGHT_GRAY);
		g2dCopie.fill(mat.createTransformedShape(barreBouclierPerdu));
	}
	/**
	 * Méthode qui gère les pertes de vie et de bouclier et change les barres avec d'autre rectangle pour signifier une perte
	 */
	private void gererPerteDeVieEtBouclier() {
		double largeurBarreDeViePerdue = ((-(nbPointDeVie-NB_POINT_DE_VIE_INIT)*largeurBarreDeVie)/NB_POINT_DE_VIE_INIT);
		double xBarreDeViePerdue = xPointDeVie+largeurBarreDeVie-largeurBarreDeViePerdue;
		barreDeViePerdue= new Rectangle2D.Double(xBarreDeViePerdue,yPointDeVie,largeurBarreDeViePerdue,hauteurBarreVie/pixelsParMetre);
		double largeurBarreBouclierPerdu = ((-(nbBouclier-NB_POINT_BOUCLIER_INIT)*largeurBarreBouclier)/NB_POINT_BOUCLIER_INIT);
		double xBarreBouclierPerdu = xBouclier+largeurBarreBouclier-largeurBarreBouclierPerdu;
		barreBouclierPerdu = new Rectangle2D.Double(xBarreBouclierPerdu,yBouclier,largeurBarreBouclierPerdu,hauteurBarreBouclier/pixelsParMetre);
		
	}
	
	public double getNbPointDeVie() {
		return nbPointDeVie;
	}
	public void setNbPointDeVie(int nbPointDeVie) {
		this.nbPointDeVie = nbPointDeVie;
	}
	public double getNbBouclier() {
		return nbBouclier;
	}
	public void setNbBouclier(int nbBouclier) {
		this.nbBouclier = nbBouclier;

	}
	public double getxPointDeVie() {
		return xPointDeVie;
	}
	public void setxPointDeVie(double xPointDeVie) {
		this.xPointDeVie = xPointDeVie;
	}
	public double getyPointDeVie() {
		return yPointDeVie;
	}
	public void setyPointDeVie(double yPointDeVie) {
		this.yPointDeVie = yPointDeVie;
	}
	public double getxBouclier() {
		return xBouclier;
	}
	public void setxBouclier(double xBouclier) {
		this.xBouclier = xBouclier;
	}
	public double getyBouclier() {
		return yBouclier;
	}
	public void setyBouclier(double yBouclier) {
		this.yBouclier = yBouclier;
	}
	public double getHauteurBarreVie() {
		return hauteurBarreVie;
	}
	public void setHauteurBarreVie(double hauteurBarreVie) {
		this.hauteurBarreVie = hauteurBarreVie;
	}
	public double getHauteurBarreBouclier() {
		return hauteurBarreBouclier;
	}
	public void setHauteurBarreBouclier(double hauteurBarreBouclier) {
		this.hauteurBarreBouclier = hauteurBarreBouclier;
	}

	public double getLargeurBarreDeVie() {
		return largeurBarreDeVie;
	}

	public void setLargeurBarreDeVie(double largeurBarreDeVie) {
		this.largeurBarreDeVie = largeurBarreDeVie;
	}

	public double getLargeurBarreBouclier() {
		return largeurBarreBouclier;
	}

	public void setLargeurBarreBouclier(double largeurBarreBouclier) {
		this.largeurBarreBouclier = largeurBarreBouclier;
	}

    public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
    }
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}
}
