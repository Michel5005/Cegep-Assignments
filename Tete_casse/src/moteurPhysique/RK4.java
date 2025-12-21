package moteurPhysique;

/**
 * 
 * Classe représentant l'implémentation de l'algorithme Runge-Kutta d’ordre 4
 * (RK4).
 * 
 * @author Ismaïl Boukari
 *
 */
public abstract class RK4 {

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


	/**
	 * 
	 * Calcule l'accélération de l'objet en fonction de la somme des forces
	 * appliquées et de sa masse.
	 * 
	 * @param sommeDesForces La somme des forces appliquées à l'objet.
	 * @param masse La masse de l'objet.
	 * @return La nouvelle accélération de l'objet.
	 */
	private static SVector3d calculAccel(SVector3d sommeDesForces, double masse) {
		SVector3d nouvelleAccel = null;
		try {
			nouvelleAccel = MoteurPhysiqueMecanique.calculAcceleration(sommeDesForces, masse);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erreur dans le calcul de l'acceleration");
		}// fin try catch
		return nouvelleAccel;
	}// fin methode

	/**
	 * 
	 * Calcule la nouvelle vitesse de l'objet en fonction du pas de temps, sa
	 * vitesse actuelle et son accélération actuelle.
	 * 
	 * @param dt           Le pas de temps utilisé pour le calcul.
	 * @param vitesse      La vitesse actuelle de l'objet.
	 * @param acceleration L'accélération actuelle de l'objet.
	 * @return La nouvelle vitesse de l'objet.
	 */
	private static SVector3d calculVitesse(double dt, SVector3d vitesse, SVector3d acceleration) {
		SVector3d nouvelleVitesse = null;
		try {
			nouvelleVitesse = MoteurPhysiqueMecanique.calculVitesse(dt, vitesse, acceleration);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erreur dans le calcul de l'acceleration");
		}// fin try catch
		return nouvelleVitesse;
	}// fin methode

	/**
	 * 
	 * Calcule la nouvelle position de l'objet en utilisant la vitesse actuelle et
	 * le pas de temps.
	 * 
	 * @param dt       le pas de temps pour le calcul.
	 * @param position la position actuelle de l'objet.
	 * @param vitesse  la vitesse actuelle de l'objet.
	 * @return la nouvelle position de l'objet.
	 */
	private static SVector3d calculPos(double dt, SVector3d position, SVector3d vitesse) {
		SVector3d nouvellePos = null;
		try {

			nouvellePos = MoteurPhysiqueMecanique.calculPosition(dt, position, vitesse);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erreur dans le calcul de l'acceleration");
		}// fin try catch
		return nouvellePos;
	}// fin methode

	/**
	 * Fonction représentant la dérivée de la vitesse (accélération). Cette dernière
	 * calcul l'accélération de l'objet avec la somme des forces appliquée sur
	 * celui-ci.
	 * Cependant, cette méthode pourrait être redéfinie dans les sous-classes si la
	 * force dépend de la position, de la vitesse, de l'accélération ou d'autres
	 * facteurs.
	 * 
	 * @param objet        L'objet sur lequel on applique l'algorithme.
	 * @param dt           Le pas de temps pour le calcul.
	 * @param position     La position actuelle de l'objet.
	 * @param vitesse      La vitesse actuelle de l'objet.
	 * @param acceleration L'accélération actuelle de l'objet.
	 * @return La nouvelle accélération calculée a partir de la somme des forces.
	 */
	private static SVector3d f(Objet objet, double dt, SVector3d position, SVector3d vitesse, SVector3d acceleration) {
		SVector3d nouvelAccel = calculAccel(objet.getSommeDesForce(), objet.getMasse());
		objet.setAcceleration(nouvelAccel);
		return nouvelAccel;
	}// fin methode

	/**
	 * Méthode faisant une itération de l'algorithme RK4. Elle évalue les pentes
	 * k1,k2,k3,k4 et calcule la vitesse et la position à partir de ces dernières.
	 * @param objet L'objet sur lequel on applique l'algorithme.
	 * @param dt Le pas de temps utilisé pour le calcul.
	 */
	public static void step(Objet objet, double dt) {
		SVector3d vitesseTemp, posTemp;
		k1 = f(objet, 0, objet.getPosition(), objet.getVitesse(), objet.getAcceleration());
		vitesseTemp = objet.getVitesse().add(k1.multiply(dt / 2));
		posTemp = objet.getPosition().add(vitesseTemp.multiply(dt / 2));
		k2 = f(objet, dt / 2, posTemp, vitesseTemp, objet.getAcceleration());
		vitesseTemp = objet.getVitesse().add(k2.multiply(dt / 2));
		posTemp = objet.getPosition().add(vitesseTemp.multiply(dt / 2));
		k3 = f(objet, dt / 2, posTemp, vitesseTemp, objet.getAcceleration());
		vitesseTemp = objet.getVitesse().add(k3.multiply(dt));
		posTemp = objet.getPosition().add(vitesseTemp.multiply(dt));
		k4 = f(objet, dt, posTemp, vitesseTemp, objet.getAcceleration());

		objet.setVitesse(objet.getVitesse()
				.add(k1
						.add(k2.multiply(2))
						.add(k3.multiply(2))
						.add(k4)
						.multiply(1.0 / 6)
						.multiply(dt)));
		objet.setPosition(objet.getPosition().add(objet.getVitesse().multiply(dt)));
	}// fin methode

}// fin classe
