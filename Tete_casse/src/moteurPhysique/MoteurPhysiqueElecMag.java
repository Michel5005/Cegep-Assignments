package moteurPhysique;

import java.util.Iterator;

import peche.Aimant;
import peche.ObjetEau;

/**
 * Cette classe regroupera les calculs physiques lié au notions d'électricité et
 * de magnétisme lié au divers jeux
 * 
 * @author Jason Yin
 * @author Tin Pham
 *
 */

public class MoteurPhysiqueElecMag {

	private static final double EPSILON = 1e-10;
	private static final double K = 9e9;

	/**
	 * Calcul et retourne la vitesse d'une particule entre deux plaques chargés
	 * électriquement et un champ magnétique
	 * 
	 * @param champMag  Le champs magnétique en Z
	 * @param champElec Le champ magnétiqeu en Y
	 * @param charge    Charge de la particule
	 * @return La vitesse de la particule
	 */
	// Jason Yin
	public static SVector3d calculSelecteurDeVitesse(SVector3d champMag, SVector3d champElec, double charge) {
		SVector3d vitesse;

		vitesse = champElec.multiply(charge);

		return vitesse;
	}// fin méthode

	// /**
	// * Calcul et retourne la vitesse d'une particule entre deux plaques chargés
	// * électriquement et
	// * un champ magnétique
	// *
	// * @param champMag Le champs magnétique en Z
	// * @param champElec Le champ magnétiqeu en Y
	// * @param charge Charge de la particule
	// * @return La vitesse de la particule
	// */
	// public static SVector3d calculSelecteurDeVitesse(SVector3d champMag,
	// SVector3d champElec, double charge) {
	// SVector3d vitesse;
	// if (charge < EPSILON) {
	// vitesse = (champMag.cross(champElec)).multiply(-1);
	// } // fin if
	// else if (charge > EPSILON) {
	// vitesse = champMag.cross(champElec);
	// } // fin else if
	// else {
	// vitesse = new SVector3d(0, 0, 0);
	// } // fin else
	// return vitesse;
	// }// fin méthode

	/**
	 * Calcul et retourne la résistance équivalente de plusieurs résisteurs
	 * identiques
	 * 
	 * @param nbRes      Le nombre de résisteurs identiques
	 * @param resistance Valeur de la résistance
	 * @return La résistance équivalente
	 */
	// Jason Yin
	public static double calculResSerie(double nbRes, double resistance) {
		return nbRes * resistance;
	}// fin méthode

	/**
	 * Calcul et retourne le courant qui traverse le circuit en série
	 * 
	 * @param voltSource L'électromotance de la source
	 * @param resEq      Résistance équivalente du circuit
	 * @return Le courant qui traverse le circuit
	 */
	// Jason Yin
	public static double calculAmpereCircuit(double voltSource, double resEq) {
		return voltSource / resEq;
	}// fin méthode

	/**
	 * Calcul et retourne la force magnétique sur un objet
	 * 
	 * @param aimant Aimant qui exerce une force sur un objet
	 * @param objet  L'objet qui reçoit la force
	 * @return La force magnétique sur un objet
	 */
	// Tin Pham
	public static SVector3d calculForceElectrique(Aimant aimant, ObjetEau objet) {

		/*SVector3d posAimant = aimant.getPosition();
		SVector3d posObjet = objet.getPosition();

		SVector3d rayon = new SVector3d();
		rayon = posObjet.substract(posAimant);

		double moduleRayon = rayon.modulus();

		double chargeProduct = aimant.getCharge() * objet.getCharge();
		double forceElec = K * chargeProduct * (1 / Math.pow(moduleRayon, 2));

		double angle = Math.atan2(rayon.getY(), rayon.getX());

		double sign = Math.signum(chargeProduct);

		SVector3d force = new SVector3d(sign * forceElec * Math.cos(angle), sign * forceElec * Math.sin(angle), 0);

		return force;*/
		
		SVector3d rayon = objet.getPosition().substract(aimant.getPosition());
		
		double m = rayon.modulus();
		double m3 = m*m*m;
		
		return rayon.multiply(K * aimant.getCharge() * objet.getCharge() / m3);

	}// fin méthode

	/**
	 * Cette méthode calcule l'accélération subie par un objet immergé dans l'eau,
	 * sous l'influence de la force électromagnétique exercée par un aimant.
	 * 
	 * @param force  Un vecteur force, représentant la force électromagnétique
	 *               exercée sur l'objet par l'aimant.
	 * @param aimant Un objet Aimant représentant l'aimant qui exerce la force
	 *               électromagnétique.
	 * @param objet  Un objet ObjetEau représentant l'objet immergé dans l'eau sur
	 *               lequel s'applique la force.
	 * @return Un vecteur SVector3d représentant l'accélération subie par l'objet,
	 *         calculée en utilisant la formule : acceleration = force / masse de
	 *         l'objet.
	 */
	// Tin Pham
	public static SVector3d calculAcceleration(SVector3d force, Aimant aimant, ObjetEau objet) {

		return new SVector3d(force.getX() / objet.getMasse(), force.getY() / objet.getMasse(), 0);
	}

	/**
	 * Cette méthode calcule la nouvelle vitesse d'un objet en fonction de son
	 * ancienne vitesse et de l'accélération qu'il subit pendant un intervalle de
	 * temps deltaT.
	 * 
	 * @param deltaT  l'intervalle de temps
	 * @param vitesse la vitesse initiale de l'objet
	 * @param accel   l'accélération subie par l'objet
	 * @return la nouvelle vitesse de l'objet
	 */
	// Tin Pham
	public static SVector3d calculVitesse(double deltaT, SVector3d vitesse, SVector3d accel) {

		SVector3d dVitesse = accel.multiply(deltaT);
		SVector3d resultVit = vitesse.add(dVitesse);

		return new SVector3d(resultVit.getX(), resultVit.getY(), 0);
	}

	/**
	 * Calcule la nouvelle position de l'objet en fonction de sa vitesse et du temps
	 * écoulé.
	 * 
	 * @param deltaT   le temps écoulé depuis la dernière itération physique
	 * @param position la position initiale de l'objet
	 * @param vitesse  la vitesse de l'objet
	 * @return la nouvelle position de l'objet
	 */
	// Tin Pham
	public static SVector3d calculPosition(double deltaT, SVector3d position, SVector3d vitesse) {

		SVector3d dPosition = vitesse.multiply(deltaT);
		SVector3d resultPos = vitesse.add(dPosition);

		return new SVector3d(resultPos.getX(), resultPos.getY(), 0);
	}

}
