package curling;

import moteurPhysique.MoteurPhysiqueMecanique;
import moteurPhysique.Objet;
import moteurPhysique.RK4;
import moteurPhysique.SVector3d;

/**
 * 
 * Classe représentant l'implémentation de l'algorithme Runge-Kutta d’ordre 4
 * (RK4) pour le jeu curling.
 * 
 * @author Ismaïl Boukari
 */
public class RK4Curling extends RK4 {

	/**
	 * Pente (accélération) évaluée au début de l'interval.
	 */
	private static SVector3d k1;
	/**
	 * Pente (accélération) évaluée à la moitié de l'interval (utilise k1).
	 */
	private static SVector3d k2;
	/**
	 * Pente (accélération) évaluée à la moitié de l'interval (utilise k2).
	 */
	private static SVector3d k3;
	/**
	 * Pente (accélération) évaluée à la fin de l'interval (utilise k3).
	 */
	private static SVector3d k4;

	private static SVector3d forceMagnetique;

	private static SVector3d forceElectrique;

	/**
	 * 
	 * Calcule l'accélération de l'objet en fonction de la somme des forces
	 * appliquées et de sa masse.
	 * 
	 * @param sommeDesForces  La somme des forces appliquées à l'objet.
	 * @param masse           La masse de l'objet.
	 * @param vitesse         La vitesse de l'objet.
	 * @param champElectrique Le champ électrique appliqué à l'objet.
	 * @param champMagnetique Le champ magnétique appliqué à l'objet.
	 * @param chargeParticule La charge de l'objet.
	 * @return La nouvelle accélération de l'objet.
	 */
	private static SVector3d calculAccel(SVector3d sommeDesForces, double masse, SVector3d vitesse,
			SVector3d champElectrique, SVector3d champMagnetique, double chargeParticule) {
		SVector3d nouvelleAccel = null;
		try {
			forceMagnetique = champMagnetique.cross(vitesse).multiply(chargeParticule);
			forceElectrique = champElectrique.multiply(chargeParticule);

			sommeDesForces = forceMagnetique.add(forceElectrique);
			nouvelleAccel = MoteurPhysiqueMecanique.calculAcceleration(sommeDesForces, masse);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erreur dans le calcul de l'acceleration");
		} // fin try catch
		return nouvelleAccel;
	}// fin methode

	/**
	 * Fonction représentant la dérivée de la vitesse (accélération). Cette dernière
	 * calcul l'accélération de l'objet avec la somme des forces appliquée sur
	 * celui-ci.
	 * Cependant, cette méthode pourrait être redéfinie dans les sous-classes si la
	 * force dépend de la position, de la vitesse, de l'accélération ou d'autres
	 * facteurs.
	 * 
	 * @param objet           L'objet dont on veut calculer l'accélération.
	 * @param dt              Le pas de temps pour le calcul.
	 * @param position        La position actuelle de l'objet.
	 * @param vitesse         La vitesse actuelle de l'objet.
	 * @param acceleration    L'accélération actuelle de l'objet.
	 * @param champElectrique Le champ électrique appliqué à l'objet.
	 * @param champMagnetique Le champ magnétique appliqué à l'objet.
	 * @param chargeParticule La charge de l'objet.
	 * @return La nouvelle accélération calculée a partir de la somme des forces.
	 */
	private static SVector3d f(Objet objet, double dt, SVector3d position, SVector3d vitesse, SVector3d acceleration,
			SVector3d champElectrique, SVector3d champMagnetique, double chargeParticule) {
		SVector3d nouvelAccel = calculAccel(objet.getSommeDesForce(), objet.getMasse(), vitesse, champElectrique,
				champMagnetique, chargeParticule);
		objet.setAcceleration(nouvelAccel);
		return nouvelAccel;
	}// fin methode

	/**
	 * Méthode faisant une itération de l'algorithme RK4. Elle évalue les pentes
	 * k1,k2,k3,k4 et calcule la vitesse et la position à partir de ces dernières.
	 * 
	 * @param objet L'objet dont on veut calculer la nouvelle position
	 *              et la nouvelle vitesse.
	 * @param dt    Le pas de temps pour le calcul.
	 * @param champElectrique Le champ électrique appliqué à l'objet.
	 * @param champMagnetique Le champ magnétique appliqué à l'objet.
	 * @param chargeParticule La charge de l'objet.
	 * 
	 */
	public static void step(Objet objet, double dt, SVector3d champElectrique, SVector3d champMagnetique,
			double chargeParticule) {
		SVector3d vitesseTemp, posTemp;
		k1 = f(objet, 0, objet.getPosition(), objet.getVitesse(), objet.getAcceleration(), champElectrique,
				champMagnetique, chargeParticule);
		vitesseTemp = objet.getVitesse().add(k1.multiply(dt / 2));
		posTemp = objet.getPosition().add(vitesseTemp.multiply(dt / 2));
		k2 = f(objet, dt / 2, posTemp, vitesseTemp, objet.getAcceleration(), champElectrique, champMagnetique,
				chargeParticule);
		vitesseTemp = objet.getVitesse().add(k2.multiply(dt / 2));
		posTemp = objet.getPosition().add(vitesseTemp.multiply(dt / 2));
		k3 = f(objet, dt / 2, posTemp, vitesseTemp, objet.getAcceleration(), champElectrique, champMagnetique,
				chargeParticule);
		vitesseTemp = objet.getVitesse().add(k3.multiply(dt));
		posTemp = objet.getPosition().add(vitesseTemp.multiply(dt));
		k4 = f(objet, dt, posTemp, vitesseTemp, objet.getAcceleration(), champElectrique, champMagnetique,
				chargeParticule);

		objet.setVitesse(objet.getVitesse()
				.add(k1.add(k2.multiply(2)).add(k3.multiply(2)).add(k4).multiply(1.0 / 6).multiply(dt)));
		objet.setPosition(objet.getPosition().add(objet.getVitesse().multiply(dt)));

	}// fin methode

	public static SVector3d getForceMagnetique() {
		return forceMagnetique;
	}

	public static void setForceMagnetique(SVector3d forceMagnetique) {
		RK4Curling.forceMagnetique = forceMagnetique;
	}

	public static SVector3d getForceElectrique() {
		return forceElectrique;
	}

	public static void setForceElectrique(SVector3d forceElectrique) {
		RK4Curling.forceElectrique = forceElectrique;
	}

}// fin classe
