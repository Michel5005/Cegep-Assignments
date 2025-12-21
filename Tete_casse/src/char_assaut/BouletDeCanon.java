package char_assaut;

import moteurPhysique.*;

/**
 * Classe qui possède toute les propriétés qu'un boulet de canon possède
 * @author Jason Yin
 *
 */

public class BouletDeCanon extends Objet{

	/**
	 * Crée un boulet de canon
	 * @param position Position du boulet de canon
	 * @param vitesse Vitesse du boulet de canon
	 * @param acceleration Accélération du boulet de canon
	 * @param sommeDesForce Somme des forces appliquées du boulet de canon
	 * @param masse Masse du boulet de canon
	 */
	public BouletDeCanon(SVector3d position, SVector3d vitesse, SVector3d acceleration, SVector3d sommeDesForce,
			double masse) {
		super(position, vitesse, acceleration, sommeDesForce, masse);
	}//fin constructeur

	public SVector3d getPosition() {
		return position;
	}
	public void setPosition(SVector3d position) {
		this.position = position;
	}
	public SVector3d getVitesse() {
		return vitesse;
	}
	public void setVitesse(SVector3d vitesse) {
		this.vitesse = vitesse;
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
	public void setMasse(float masse) {
		this.masse = masse;
	}
}// fin classe
