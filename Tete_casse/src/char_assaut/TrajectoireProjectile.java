package char_assaut;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

/**
 * Créer la trajector d'un projectile dans un mouvement ballistique
 * @author Jason Yin
 *
 */

public class TrajectoireProjectile {

	private double gravite;
	private double vitesse;
	private double vitesseX;
	private double vitesseY;
	private Path2D.Double ligneBrisee;
	private Area aireTest;
	private int nbSegmentsPourApproximer;
	private double pixelsParMetre=1;
	private double xMin;
	private double y;
	private double x;
	private double xInit;
	private double yInit;
	private double angle;
	private double temps;
	private boolean estDehors =false;

	/**
	 * Créer une trajectoire d'un projectile en mouvement ballistique
	 * @param x Position initial en x
	 * @param y Position initial en y
	 * @param angle Angle de tir
	 * @param gravite Gravité appliqué sur la planète
	 * @param vitesse Vitesse initial du projectile
	 * @param pixelsParMetre Facteur de mise à l'échelle
	 */
	public TrajectoireProjectile(double x,double y,double angle,
			double gravite,double vitesse,double pixelsParMetre) {
		this.x = x;
		this.y=y;
		this.xInit=x
				;
		this.yInit=y;
		this.gravite = gravite;
		this.vitesse = vitesse;
		this.angle=angle;
		this.vitesseX=Math.cos(Math.toRadians(angle))*vitesse;
		this.vitesseY=Math.sin(Math.toRadians(angle))*vitesse;
		this.pixelsParMetre = pixelsParMetre;
		this.temps=0;
	}
	/**
	 * Créer la géométrie de la trajectoir
	 */
	private void creerLaGeometrie() {
		double x, y,xMax;

		ligneBrisee = new Path2D.Double();
		x = 0; // on commence a l'extreme gauche
		y = evalFonction(x);
		xMax=100;
		ligneBrisee.moveTo(x, y);

		for (int k = 1; k < nbSegmentsPourApproximer + 1; k++) {

			x = xMin + k * (xMax - xMin) / nbSegmentsPourApproximer; // on calcule le nouveau x, un peu plus loin,
			// où on évaluera la fonction de nouveau

			y = evalFonction(x); // modifier cette ligne!!!
			ligneBrisee.lineTo(x, y); // modifier cette ligne!!!

		} // fin for
	}// fin méthode

	/**
	 * Calcul une parabole qui sera la trajectoire
	 * @param x le temps dans la fonction
	 * @return une position dans un temps donné
	 */
	public double evalFonction(double x) {
		double y;

		y = (yInit)-((gravite/-2)*(x*x)- (vitesseY) * x );

		return (y);
	}// fin méthode

	/**
	 * Permet de dessiner la balle, sur le contexte graphique passe en parametre.
	 * @param g2d contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		//estDehors = true;
		
		for(int i =0;i<10;i++){
			//if(estDehors) {
				Ellipse2D.Double cerclePredict = new Ellipse2D.Double(x,y,0.5,0.5);
				AffineTransform mat= new AffineTransform();
				mat.scale(pixelsParMetre, pixelsParMetre);
				g2dCopie.setColor(Color.black);
				g2dCopie.fill(mat.createTransformedShape(cerclePredict));
				temps +=0.1;
				x=xInit+ vitesseX*temps;
				y= evalFonction(temps);
				//Area aireInterieur = new Area (cerclePredict);
				//Area aireTestSub = aireTest;
			//System.out.println("dessine " + i+ " fois");
				//aireInterieur.subtract(aireTestSub);
				//if(aireInterieur.isEmpty()) {
					//System.out.println("stopped");
					//estDehors=false;
			
		}
	}// fin méthode

	public Area getAireTest() {
		return aireTest;
	}
	public void setAireTest(Area aireTest) {
		this.aireTest = aireTest;
	}
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}

}
