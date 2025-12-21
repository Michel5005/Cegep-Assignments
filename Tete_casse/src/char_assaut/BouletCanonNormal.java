package char_assaut;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import interfaces.Dessinable;
import moteurPhysique.*;


/**
 * Classe qui crée un objet de type boulet de canon normal
 * @author Jason Yin
 *
 */

public class BouletCanonNormal extends BouletDeCanon implements Dessinable {

	private double diametre = 1;
	private Ellipse2D.Double boulet;
	private double angle;
	private double pixelsParMetre = 1;
	private double vitesseInit;
	private Area aireBoulet;
	private Shape bouletTransfo;

	private double facteurRedim = 0.5;
	private double tailleTeteFleche = 0.4;
	private boolean vecVitesseSelectionne = false;
	private boolean vecAccelerationSelectionne = false;
	private boolean vecSommeForceSelectionne = false;
	private boolean etatTire = false;

	/**
	 * Crée un boulet de canon normal
	 * @param position Position du boulet de canon
	 * @param vitesse Vitesse du boulet de canon
	 * @param acceleration Accélération du boulet de canon
	 * @param sommeDesForce Somme des forces appliquées du boulet de canon
	 * @param masse Masse du boulet de canon
	 * @param diametre Diametre du boulet de canon
	 * @param angle Angle de tir du canon
	 * @param vitesseInit Vitesse de tir initial du boulet de canon
	 * @param pixelsParMetre Facteur de conversion entre les pixels et les mètres
	 */
	public BouletCanonNormal(SVector3d position, SVector3d vitesse, SVector3d acceleration, SVector3d sommeDesForce,
			double masse, double dt, double diametre, double angle, double vitesseInit,double pixelsParMetre) {
		super(position, vitesse, acceleration, sommeDesForce, masse);
		this.diametre = diametre;
		this.angle = angle;
		this.vitesseInit = vitesseInit;
		this.pixelsParMetre = pixelsParMetre;
		creerLaGeometrie();

	}//fin du constructeur

	/**
	 * Crée la géométrie du boulet de canon
	 */

	private void creerLaGeometrie() {
		boulet = new Ellipse2D.Double(position.getX(), position.getY(), diametre, diametre);
	}//fin de méthode

	/**
	 * Permet de dessiner la balle, sur le contexte graphique passe en parametre.
	 * @param g2d contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);
		bouletTransfo=mat.createTransformedShape(boulet);
		aireBoulet= new Area(bouletTransfo);
		g2dPrive.fill(bouletTransfo);

		FlecheVectorielle flecheVitBouletTotal = new FlecheVectorielle(position.getX() + diametre /2,position.getY() + diametre / 2, vitesse);
		g2dPrive.setStroke(new BasicStroke(2));
		flecheVitBouletTotal.setCouleurFleche(Color.red);
		flecheVitBouletTotal.redimensionneCorps(facteurRedim);
		flecheVitBouletTotal.setAngleTete(90);
		flecheVitBouletTotal.setLongueurTraitDeTete(tailleTeteFleche);
		flecheVitBouletTotal.setPixelsParMetre(pixelsParMetre);

		FlecheVectorielle flecheVitBouletX = new FlecheVectorielle(position.getX() + diametre /2,position.getY() + diametre / 2,
				new SVector3d(vitesse.getX(), 0, 0));
		flecheVitBouletX.setCouleurFleche(Color.BLUE);
		flecheVitBouletX.redimensionneCorps(facteurRedim);
		flecheVitBouletX.setAngleTete(90);
		flecheVitBouletX.setLongueurTraitDeTete(tailleTeteFleche);
		flecheVitBouletX.setPixelsParMetre(pixelsParMetre);

		FlecheVectorielle flecheVitBouletY = new FlecheVectorielle(position.getX() + diametre /2,position.getY() + diametre / 2,
				new SVector3d(0, vitesse.getY(), 0));
		flecheVitBouletY.setCouleurFleche(Color.GREEN);
		flecheVitBouletY.redimensionneCorps(facteurRedim);
		flecheVitBouletY.setAngleTete(90);
		flecheVitBouletY.setLongueurTraitDeTete(tailleTeteFleche);
		flecheVitBouletY.setPixelsParMetre(pixelsParMetre);

		FlecheVectorielle flecheAccBouletTotal = new FlecheVectorielle(position.getX() + diametre / 2,
			position.getY() + diametre / 2,acceleration );

		flecheAccBouletTotal.setCouleurFleche(Color.BLACK);
		flecheAccBouletTotal.redimensionneCorps(facteurRedim);
		flecheAccBouletTotal.setAngleTete(90);
		flecheAccBouletTotal.setLongueurTraitDeTete(tailleTeteFleche);
		flecheAccBouletTotal.setPixelsParMetre(pixelsParMetre);

	FlecheVectorielle flecheAccBouletY = new FlecheVectorielle(position.getX() + diametre / 2,
		position.getY() + diametre / 2,
		new SVector3d(0, acceleration.getY(), 0));

		flecheAccBouletY.setCouleurFleche(Color.BLACK);
		flecheAccBouletY.redimensionneCorps(facteurRedim);
		flecheAccBouletY.setAngleTete(90);
		flecheAccBouletY.setLongueurTraitDeTete(tailleTeteFleche);
		flecheAccBouletY.setPixelsParMetre(pixelsParMetre);

	FlecheVectorielle flecheAccBouletX = new FlecheVectorielle(position.getX() + diametre / 2,
		position.getY() + diametre / 2,
		new SVector3d(acceleration.getX(),0, 0));

		flecheAccBouletX.setCouleurFleche(Color.BLACK);
		flecheAccBouletX.redimensionneCorps(facteurRedim);
		flecheAccBouletX.setAngleTete(90);
		flecheAccBouletX.setLongueurTraitDeTete(tailleTeteFleche);
		flecheAccBouletX.setPixelsParMetre(pixelsParMetre);

	FlecheVectorielle flecheForce = new FlecheVectorielle(position.getX() + diametre / 2,
		position.getY() + diametre / 2, sommeDesForces);

		flecheForce.setCouleurFleche(Color.BLACK);
		flecheForce.redimensionneCorps(facteurRedim/2);
		flecheForce.setAngleTete(90);
		flecheForce.setLongueurTraitDeTete(tailleTeteFleche);
		flecheForce.setPixelsParMetre(pixelsParMetre);
		if(etatTire){
		if(vecVitesseSelectionne) {
			flecheVitBouletTotal.dessiner(g2dPrive);
			flecheVitBouletX.dessiner(g2dPrive);
			flecheVitBouletY.dessiner(g2dPrive);
		}
		else if(vecAccelerationSelectionne) {
			flecheAccBouletTotal.dessiner(g2dPrive);
			flecheAccBouletX.dessiner(g2dPrive);
			flecheAccBouletY.dessiner(g2dPrive);
		}
		else if(vecSommeForceSelectionne){
			flecheForce.dessiner(g2dPrive);
		}
	}
	}//fin de méthode

	/**
	 * Calcule la nouvelle vitesse et la nouvelle position de la balle apres
	 * cet nouvel intervalle de temps.
	 * @param deltaT intervalle de temps (pas)
	 */
	public void avancerUnPas(double deltaT) {
		RK4.step(this,deltaT);
		creerLaGeometrie();
	}//fin méthode
	
	public SVector3d getPosition() {
		return position;
	}

	public void setPosition(SVector3d position) {
		this.position = position;
		creerLaGeometrie();
	}

	public SVector3d getVitesse() {
		return vitesse;
	}

	public void setVitesse(SVector3d vitesse) {
		this.vitesse = vitesse;
		creerLaGeometrie();
	}

	public SVector3d getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(SVector3d acceleration) {
		this.acceleration = acceleration;
	}

	public SVector3d getSommeDesForces() {
		return sommeDesForces;
	}

	public void setSommeDesForces(SVector3d sommeDesForce) {
		this.sommeDesForces = sommeDesForce;
	}

	public double getMasse() {
		return masse;
	}

	public void setMasse(double masse) {
		this.masse = masse;
	}


	public Area getAireBoulet() {
		return aireBoulet;
	}

	public void setAireBoulet(Area aireBoulet) {
		this.aireBoulet = aireBoulet;
	}

	public double getPixelsParMetre() {
		return pixelsParMetre;
	}

	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}	

	public boolean getVecVitesseSelectionne() {
		return vecVitesseSelectionne;
	}
	public void setVecVitesseSelectionne(boolean vecVitesseSelectionne) {
		this.vecVitesseSelectionne = vecVitesseSelectionne;
	}
	public boolean getVecAccelerationSelectionne() {
		return vecAccelerationSelectionne;
	}
	public void setVecAccelerationSelectionne(boolean vecAccelerationSelectionne) {
		this.vecAccelerationSelectionne = vecAccelerationSelectionne;
	}
	public boolean getVecSommeForceSelectionne() {
		return vecSommeForceSelectionne;
	}
	public void setVecSommeForceSelectionne(boolean vecSommeForceSelectionne) {
		this.vecSommeForceSelectionne = vecSommeForceSelectionne;
	}
	public boolean getEtatTire() {
		return etatTire;
	}
	public void setEtatTire(boolean etatTire) {
		this.etatTire = etatTire;
	}

	/**
	 * Permet de retourner la position du boulet de canon sous forme de String
	 * @return  la position du boulet de canon
	 */
	public String positionToString() {
		return String.format("[%.2f",position.getX())+" , "+String.format("%.2f]",position.getY());
	}
	/**
	 * Permet de retourner la vitesse du boulet de canon sous forme de String
	 * @return  la vitesse du boulet de canon
	 */
	public String vitesseToString() {
		return String.format("[%.2f",vitesse.getX())+" , "+String.format("%.2f]",-vitesse.getY());
	}
	/**
	 * Permet de retourner l'accélération du boulet de canon sous forme de String
	 * @return  l'accélération du boulet de canon
	 */
	public String accelToString() {
		return String.format("[%.2f",acceleration.getX())+" , "+String.format("%.2f]",-acceleration.getY());
	}
	/**
	 * Permet de retourner la somme des forces du boulet de canon sous forme de String
	 * @return  la somme des forces du boulet de canon
	 */
	public String sommeForcesToString() {
		return String.format("[%.2f",sommeDesForces.getX())+" , "+String.format("%.2f]",sommeDesForces.getY());
	}
	
}//fin classe
