package moteurPhysique;

/**
 * Classe qui crée un objet particule pour tester le moteur physique d'électricité et magnétisme
 * @author Jason Yin
 *
 */

public class ParticuleTest extends Objet {

	private float charge;

	/**
	 * Crée une particule
	 * @param position Position de la particule
	 * @param vitesse  Vitesse initial de la particule
	 * @param acceleration	Accélération intial de la particule
	 * @param sommeDesForce Somme des forces appliqués sur la particule
	 * @param masse Masse de la particule
	 * @param dt Différence de temps entre les incrémentations
	 * @param charge Charge de la particule
	 */
	public ParticuleTest(SVector3d position, SVector3d vitesse, SVector3d acceleration, SVector3d sommeDesForce,
			float masse, double dt, float charge) {
		super(position, vitesse, acceleration, sommeDesForce, masse);
		this.charge = charge;
	}
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
	public void setMasse(double masse) {
		this.masse = masse;
	}
	public float getCharge() {
		return charge;
	}
	public void setCharge(float charge) {
		this.charge = charge;
	}
	
}
