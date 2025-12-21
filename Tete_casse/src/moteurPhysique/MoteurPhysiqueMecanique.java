package moteurPhysique;

/**
 * Cette classe regroupera les calculs physiques nécessaires au mouvement des objets
 * des divers objets dans la scène.
 * Utilise la méthode n'intégration numérique d'Euler semi-implicite. 
 *
 * @author Caroline Houle
 *
 */

public class MoteurPhysiqueMecanique {
	
	
	private static final double EPSILON = 1e-10; //tolerance utilisee dans les comparaisons reelles avec zero

	/**
	 * Calcule et retourne l'acceleration en utilisant F=ma
	 * @param sommeDesForces Somme des forces appliquees
	 * @param masse Masse del'objet
	 * @return l'acceletation F/m
	 * @throws Exception Erreur si la masse est pratiquement nulle
	 */
	//Caroline Houle
	public static SVector3d calculAcceleration(SVector3d sommeDesForces, double masse) throws Exception { 
			return new SVector3d( sommeDesForces.getX()/masse , sommeDesForces.getY()/masse,sommeDesForces.getZ()/masse );	
	}//fin méthode

	/**
	 * Calcule et retourne la nouvelle vitesse, deltaT secondes plus tard, en utilisant l'algorithme
	 * d'Euler semi-implicite.
	 * @param deltaT L'intervalle de temps (petit!) en secondes
	 * @param vitesse La vitesse initiale au debut de l'intervalle de temps, en m/s
	 * @param accel L'acceleration initiale au debut de l'intervalle de temps, en m/s2
	 * @return La nouvelle vitesse (a la fin de l'intervalle)
	 */
	//Caroline Houle
	public static SVector3d calculVitesse(double deltaT, SVector3d vitesse, SVector3d accel) {
		SVector3d deltaVitesse = accel.multiply( deltaT);
		SVector3d resultVit = vitesse.add( deltaVitesse );
		return new SVector3d(resultVit.getX(), resultVit.getY(),resultVit.getZ());

	}//fin méthode
	/**
	 * Calcule et retourne la nouvelle position, deltaT secondes plus tard, en utilisant l'algorithme
	 * d'Euler semi-implicite.
	 * @param deltaT L'intervalle de temps (petit!) en secondes
	 * @param position La position initiale au debut de l'intervalle de temps, en m
	 * @param vitesse La vitesse initiale au debut de l'intervalle de temps, en m/s
	 * @return La nouvelle position (a la fin de l'intervalle)
	 */
	//Caroline Houle
	public static SVector3d calculPosition(double deltaT, SVector3d position, SVector3d vitesse) {
		SVector3d deltaPosition = vitesse.multiply(deltaT);
		SVector3d resultPos = position.add(deltaPosition); 
		return new SVector3d(resultPos.getX(), resultPos.getY(), resultPos.getZ());

	}//fin méthode
}
