/**
 * 
 */
package sim.physics;

import sim.math.SVector3d;

/**
 * La classe <b>SGeometricalOptics</b> repr�sente une classe utilisaire pouvante
 * effectuer des calculs en lien avec la <b>l'optique g�om�trique</b>.
 * 
 * @author Simon V�zina
 * @since 2015-01-16
 * @version 2020-02-10 (version labo 2.109 : Le ray tracer)
 */
public class SGeometricalOptics {

	/**
	 * M�thode qui �value la <b>r�flexion</b> d'un rayon <b><i>v</i></b> sur une
	 * normale � la surface <b><i>N</i></b>. La solution � la r�flexion est un rayon
	 * r�fl�chi <b><i>R</i></b>. Ce calcul respecte la <u>loi de la r�flexion</u>.
	 * 
	 * @param v Le rayon <b><i>v</i></b> incident.
	 * @param N La normale � la surface <b><i>N</i></b>.
	 * @return Le rayon r�fl�chi <b><i>R</i></b>.
	 */
	public static SVector3d reflexion(SVector3d v, SVector3d N) {
		SVector3d E = v.multiply(-1);
		return v.add(N.multiply(E.dot(N)).multiply(2));
	}

	/**
	 * M�thode qui �value la <b>r�fraction</b> d'un rayon <b><i>v</i></b> par
	 * rapport � une normale � la surface <b><i>N</i></b>. La solution � la
	 * r�fraction est un rayon transmis <b><i>T</i></b>. Ce calcul respecte la
	 * <u>loi de la r�fraction</u>.
	 * 
	 * @param v  Le rayon <b><i>v</i></b> incident.
	 * @param N  La normale � la surface <b><i>N</i></b>.
	 * @param n1 L'indice de r�fraction du milieu incident.
	 * @param n2 L'indice de r�fraction du milieu r�fract�.
	 * @return Le rayon transmis <b><i>T</i></b>.
	 * @throws STotalInternalReflectionException S'il y a r�flexion totale interne
	 *                                           ce qui interdit la transmission
	 *                                           d'un rayon selon la loi de la
	 *                                           r�fraction.
	 */
	public static SVector3d refraction(SVector3d v, SVector3d N, double n1, double n2)
			throws STotalInternalReflectionException {
		SVector3d E = v.multiply(-1);
		double n = n1 / n2;
		System.out.println(
				v.multiply(n).add(N.multiply((E.dot(N) * n - Math.sqrt(1 - n * n * (1 - (E.dot(N) * (E.dot(N)))))))));
		if (isTotalInternalReflection(v, N, n1, n2)) {
			throw new STotalInternalReflectionException();
		} else {
			return v.multiply(n).add(N.multiply((E.dot(N) * n - Math.sqrt(1 - n * n * (1 - (E.dot(N) * (E.dot(N))))))));
		}
	}

	/**
	 * <p>
	 * M�thode qui d�termine s'il y aura r�flexion totale interne.
	 * </p>
	 * <p>
	 * Une r�flexion totale interne est <u>uniquement possible</u> si n1 > n2.
	 * </p>
	 * 
	 * @param v  Le rayon <b><i>v</i></b> incident.
	 * @param N  La normale � la surface <b><i>N</i></b>.
	 * @param n1 L'indice de r�fraction du milieu incident.
	 * @param n2 L'indice de r�fraction du milieu r�fract�.
	 * @return <b>true</b> s'il y a r�flexion totale interne et <b>false</b> sinon.
	 */
	public static boolean isTotalInternalReflection(SVector3d v, SVector3d N, double n1, double n2) {
		if (v.dot(N) * v.dot(N) + (n2 / n1) * (n2 / n1) <= 1) {
			return true;
		} else {
			return false;
		}
	}

}// fin classe SGeometricalOptics
