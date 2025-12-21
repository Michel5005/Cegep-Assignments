/**
 * 
 */
package sim.geometry;

import sim.exception.SNoImplementationException;
import sim.exception.SRuntimeException;
import sim.math.SMath;
import sim.math.SVector3d;

/**
 * La classe <b>SGeometricIntersection</b> repr�sente une classe utilitaire
 * permettant d'�valuer des intersections entre un rayon et diff�rentes
 * g�om�tries.
 * <p>
 * Le rayon �tant param�tris� dans le temps, la solution au intersection est le
 * temps requis afin qu'un rayon intersection la g�om�trie. Un ensemble solution
 * vide signifie qu'aucune intersection n'a �t� r�alis� entre le rayon et la
 * g�om�trie et un ensemble solution multiple signifie que le rayon peut
 * intersecter la g�om�trie � plusieurs endroits (dont plusieurs temps).
 * </p>
 * 
 * @author Simon V�zina
 * @since 2015-11-08
 * @version 2017-12-20 (version labo � Le ray tracer v2.1)
 */
public class SGeometricIntersection {

	/**
	 * M�thode permettant d'�valuer l'intersection entre un rayon et un plan. Un
	 * rayon peut r�aliser jusqu'� <b>une intersection</b> avec un plan.
	 * 
	 * @param ray     - Le rayon � intersecter avec le plan.
	 * @param r_plane - La position de r�f�rence du plan.
	 * @param n_plane - La normale � la surface du plan.
	 * @return L'ensemble solution des temps pour r�aliser l'intersection entre le
	 *         rayon et le plan <u>dans un tableau tri�</u>. L'ensemble solution
	 *         contient 0 ou 1 �l�ment.
	 */
	public static double[] planeIntersection(SRay ray, SVector3d r_plane, SVector3d n_plane) {
		double A = n_plane.dot(ray.getDirection());
		double B = n_plane.dot(ray.getOrigin().substract(r_plane));
		double[] t = SMath.linearRealRoot(A, B);

		return t;

	}

	/**
	 * M�thode permettant d'�valuer l'intersection entre un rayon et une sph�re. Un
	 * rayon peut r�aliser jusqu'� <b>deux intersections</b> avec une sph�re.
	 * 
	 * @param ray      - Le rayon � intersecter avec la sph�re.
	 * @param r_sphere - La position de la sph�re.
	 * @param R        - Le rayon de la sph�re (doit �tre positif).
	 * @return L'ensemble solution des temps pour r�aliser l'intersection entre le
	 *         rayon et la sph�re <u>dans un tableau tri�</u>. L'ensemble solution
	 *         contient 0, 1 ou 2 �l�ments.
	 */
	public static double[] sphereIntersection(SRay ray, SVector3d r_sphere, double R) {
		SVector3d r_o = ray.getOrigin();
		SVector3d r_so = r_o.substract(r_sphere);
		SVector3d v = ray.getDirection();
		double A = v.dot(v);
		double B = 2 * (v.dot(r_so));
		double C = (r_so.dot(r_so)) - (R * R);
		double[] t = SMath.quadricRealRoot(A, B, C);

		return t;
	}

	/**
	 * M�thode permettant d'�valuer l'intersection entre un rayon et un tube infini.
	 * Un rayon peut r�aliser jusqu'� <b>deux intersections</b> avec le tube infini.
	 * 
	 * @param ray    - Le rayon � intersecter avec le tube infini.
	 * @param r_tube - Une position sur l'axe central du tube infini.
	 * @param axis   - L'axe du tube (doit �tre normalis�).
	 * @param R      - Le rayon du tube (doit �tre positif).
	 * @return L'ensemble solution des temps pour r�aliser l'intersection entre le
	 *         rayon et le tube infini <u>dans un tableau tri�</u>. L'ensemble
	 *         solution contient 0, 1 ou 2 �l�ments.
	 */
	public static double[] infiniteTubeIntersection(SRay ray, SVector3d r_tube, SVector3d axis, double R) {
		double rcalcul;
		SVector3d axisNormal = axis.normalize();
		if (R < 0) {
			rcalcul = R * -1;
		} else {
			rcalcul = R;
		}
		SVector3d va = axisNormal.cross(axisNormal.cross(ray.getDirection()));
		SVector3d rao = axisNormal.cross(axisNormal.cross(ray.getOrigin().substract(r_tube)));
		double A = va.dot(va);
		double B = va.dot(rao.multiply(2));
		double C = rao.dot(rao) - rcalcul * rcalcul;

		return SMath.quadricRealRoot(A, B, C);

	}

	/**
	 * M�thode permettant d'�valuer l'intersection entre un rayon et deux c�nes
	 * infinis reli� par leur pointe. Un rayon peut r�aliser jusqu'� <b>deux
	 * intersections</b> avec les deux c�nes infinis.
	 * 
	 * @param ray    - Le rayon � intersecter avec les deux c�nes.
	 * @param r_cone - Une position sur l'axe central des deux c�nes o� le rayon
	 *               <i>R</i> a �t� d�fini.
	 * @param axis   - L'axe des deux c�nes dans la direction localisant la pointe
	 *               du c�ne � partir de la position <i>r_cone</i> (doit �tre
	 *               normalis�).
	 * @param R      - Le rayon du c�ne � la position <i>r_cone</i> (doit �tre
	 *               positif).
	 * @param H      - La hauteur du c�ne �tant d�finie comme la distance entre la
	 *               position <i>r_cone</i> et la pointe des c�nes (doit �tre
	 *               positif).
	 * @return L'ensemble solution des temps pour r�aliser l'intersectin entre le
	 *         rayon et les deux c�nes infinis <u>dans un tableau tri�</u>.
	 *         L'ensemble solution contient 0, 1 ou 2 �l�ments.
	 */
	public static double[] infiniteTwoConeIntersection(SRay ray, SVector3d r_cone, SVector3d axis, double R, double H) {
		throw new SNoImplementationException("Erreur SGeometricIntersection : La m�thode n'a pas �t� impl�ment�e.");
	}

	/**
	 * M�thode permettant d'�valuer l'intersection entre un rayon et un tore
	 * (beigne). Un rayon peut r�aliser jusqu'� <b>quatre intersections</b> avec un
	 * tore.
	 * 
	 * @param ray     Le rayon � intersection avec le tore.
	 * @param r_torus La position centrale du tore.
	 * @param n_torus La normale au plan du tore.
	 * @param R       Le rayon de r�volution du cylindre formant le tore (doit �tre
	 *                positif).
	 * @param r       Le rayon de la partie cylindrique du tore (doit �tre positif)
	 * @return L'ensemble solution des temps pour r�aliser l'intersection entre le
	 *         rayon et le tore <u>dans un tableau tri�</u>. L'ensemble solution
	 *         contient 0, 1, 2, 3 ou 4 �l�ments.
	 * @throws SRuntimeException Si R ou r est n�gatif.
	 */
	public static double[] torusIntersection(SRay ray, SVector3d r_torus, SVector3d n_torus, double R, double r)
			throws SRuntimeException {
		if (R < 0)
			throw new SRuntimeException(
					"Erreur SGeometricIntersection 001 : Le rayon du tore R = " + R + " est n�gatif.");

		if (r < 0)
			throw new SRuntimeException(
					"Erreur SGeometricIntersection 002 : Le rayon du cylindre du tore r = " + r + " est n�gatif.");

		throw new SNoImplementationException("Erreur SGeometricIntersection : La m�thode n'a pas �t� impl�ment�e.");
	}

}// fin de la classe SGeometricIntersection
