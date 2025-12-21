package physique;

import geometrie.Vecteur2D;

/**
 * Cette classe regroupera les calculs physiques n�cessaires au mouvement des
 * objets des divers objets dans la sc�ne. Utilise la m�thode n'int�gration
 * num�rique d'Euler semi-implicite.
 * 
 * @author Caroline Houle
 * @author Nokto Lapointe
 */

public class MoteurPhysique {

	private static final double ACCEL_G = 9.80665;
	private static final double EPSILON = 1e-10; // tolerance utilisee dans les comparaisons reelles avec zero

	/**
	 * Calcule et retourne l'acceleration en utilisant F=ma
	 * 
	 * @param sommeDesForces Somme des forces appliquees
	 * @param masse          Masse del'objet
	 * @return l'acceletation F/m
	 * @throws Exception Erreur si la masse est pratiquement nulle
	 */
	// Caroline Houle
	public static Vecteur2D calculAcceleration(Vecteur2D sommeDesForces, double masse) throws Exception {
		if (masse < EPSILON)
			throw new Exception(
					"Erreur MoteurPhysique: La masse �tant nulle ou presque l'acc�leration ne peut pas etre calcul�e.");
		else
			return new Vecteur2D(sommeDesForces.getX() / masse, sommeDesForces.getY() / masse);
	}

	/**
	 * Calcule et retourne la nouvelle vitesse, deltaT secondes plus tard, en
	 * utilisant l'algorithme d'Euler semi-implicite.
	 * 
	 * @param deltaT  L'intervalle de temps (petit!) en secondes
	 * @param vitesse La vitesse initiale au debut de l'intervalle de temps, en m/s
	 * @param accel   L'acceleration initiale au debut de l'intervalle de temps, en
	 *                m/s2
	 * @return La nouvelle vitesse (a la fin de l'intervalle)
	 */
	// Caroline Houle
	public static Vecteur2D calculVitesse(double deltaT, Vecteur2D vitesse, Vecteur2D accel) {
		Vecteur2D deltaVitesse = Vecteur2D.multiplie(accel, deltaT);
		Vecteur2D resultVit = vitesse.additionne(deltaVitesse);
		return new Vecteur2D(resultVit.getX(), resultVit.getY());

	}

	/**
	 * Calcule et retourne la nouvelle position, deltaT secondes plus tard, en
	 * utilisant l'algorithme d'Euler semi-implicite.
	 * 
	 * @param deltaT   L'intervalle de temps (petit!) en secondes
	 * @param position La position initiale au debut de l'intervalle de temps, en m
	 * @param vitesse  La vitesse initiale au debut de l'intervalle de temps, en m/s
	 * @return La nouvelle position (a la fin de l'intervalle)
	 */
	// Caroline Houle
	public static Vecteur2D calculPosition(double deltaT, Vecteur2D position, Vecteur2D vitesse) {
		Vecteur2D deltaPosition = Vecteur2D.multiplie(vitesse, deltaT);
		Vecteur2D resultPos = position.additionne(deltaPosition);
		return new Vecteur2D(resultPos.getX(), resultPos.getY());

	}

	/**
	 * Forme et retourne un vecteur exprimant la force gravitationnelle s'appliquant
	 * sur un objet dont la masse est passee en parametre
	 * 
	 * @param masse Masse de l'objet
	 * @return Un vecteur repr�sentant la force gravitationnelle exercee
	 */
	// Caroline Houle
	public static Vecteur2D calculForceGrav(double masse) {
		return new Vecteur2D(0, ACCEL_G * masse);
	}

	/**
	 * Permet de calculer la force exercée par un ressort sur un objet
	 * 
	 * @param e etirement du ressort par rapport à sa longueur naturelle
	 * @param k constante de rappel du ressort
	 * @return vecteur représentant la force exercée par le ressort
	 */
	// Nokto Lapointe
	public static Vecteur2D calculForceRessort(double e, double k) {

		return new Vecteur2D(k * -e, 0);
	}

	/**
	 * Permet de calculer la force de frottement exercée sur un objet.
	 * 
	 * @param coeff   coefficient de frottement
	 * @param masse   masse de l'objet en kilogrammes
	 * @param vitesse vitesse de l'objet
	 * @return vecteur représentant la force de frottement exercée sur l'objet
	 * @throws Exception exception si la masse est nulle ou presque nulle
	 */
	// Nokto Lapointe
	public static Vecteur2D calculForceFrottement(double coeff, double masse, Vecteur2D vitesse) throws Exception {
		if (masse < EPSILON)
			throw new Exception(
					"Erreur MoteurPhysique: La masse �tant nulle ou presque l'acc�leration ne peut pas etre calcul�e.");
		else {
			Vecteur2D forceFrottement = new Vecteur2D();
			if (Math.abs(vitesse.getX()) < EPSILON) {
				forceFrottement.setComposantes(0, 0);
			} else if (vitesse.getX() > 0) {
				forceFrottement.setComposantes(-ACCEL_G * masse * coeff, 0);
			} else {
				forceFrottement.setComposantes(ACCEL_G * masse * coeff, 0);
			}
			return forceFrottement;
		}
	}

}
