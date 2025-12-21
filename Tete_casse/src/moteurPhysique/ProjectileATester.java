package moteurPhysique;

/**
 * Classe qui créer un projectile qui testera la fonctionnalité de l'algortithme rk4
 * @author Jason Yin
 *
 */

public class ProjectileATester extends Objet {

	/**
	 * Crée un projectile
	 * @param position Position de la projectile
	 * @param vitesse  Vitesse initial de la projectile
	 * @param acceleration	Accélération intial de la projectile
	 * @param sommeDesForces Somme des forces appliqués sur la projectile
	 * @param masse Masse de la projectile
	 * @param dt Différence de temps entre les incrémentations
	 */
	public ProjectileATester(SVector3d position, SVector3d vitesse, SVector3d acceleration, SVector3d sommeDesForces, float masse, double dt) {
		super(position, vitesse, acceleration, sommeDesForces,masse);
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
	
}