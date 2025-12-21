/**
 * 
 */
package sim.math;

import sim.exception.SNoImplementationException;
import sim.exception.SRuntimeException;

/**
 * La classe <b>SLinearAlgebra</b> repr�sente une classe utilitaire pour
 * effectuer du calcul en lien avec l'alg�bre lin�aire.
 * 
 * @author Simon V�zina
 * @since 2016-12-15
 * @version 2020-01-08 (Version : Le ray tracer v2.107)
 */
public class SLinearAlgebra {

	/**
	 * La constante <b>EPSILON</b> correspond � une valeur num�rique de r�f�rence
	 * pr�s de z�ro. Pour des raisons num�riques, cette valeur doit �tre l�g�rement
	 * sup�rieure � celle propos�e dans la classe <b>SMath</b>.
	 * 
	 * @see SMath
	 */
	private static final double EPSILON = SMath.EPSILON * 2.0;

	/**
	 * <p>
	 * M�thode permettant d'�valuer la normale � la surface d'un plan form� � l'aide
	 * de trois points. L'orientation de la normale est d�termin� par la <u>r�gle de
	 * la main droite</u> en lien avec l'ordre des trois points du plan. Le module
	 * de la normale d�pend de la distance entre les points ainsi que de leur
	 * positionnement angulaire (en lien avec le sin(theta) dans le produit
	 * vectoriel).
	 * </p>
	 * <p>
	 * Si les trois points sont dits <u>colin�aire</u>, la normale � la surface aura
	 * une orientation ind�termin�e de module �gale � z�ro.
	 * </p>
	 * 
	 * @param r0 Le 1ier point du plan.
	 * @param r1 Le 2i�me point du plan.
	 * @param r2 Le 3i�me point du plan.
	 * @return La normale � la surface du plan.
	 */
	public static SVector3d planNormal(SVector3d r0, SVector3d r1, SVector3d r2) {
		SVector3d r01 = r1.substract(r0);
		SVector3d r02 = r2.substract(r0);

		SVector3d n = r01.cross(r02);
		return n;
	}

	/**
	 * <p>
	 * M�thode permettant d'�valuer la normale � la surface d'un plan form� � l'aide
	 * de trois points. L'orientation de la normale est d�termin� par la <u>r�gle de
	 * la main droite</u> en lien avec l'ordre des trois points du plan.
	 * </p>
	 * <p>
	 * La normale retourn�e sera <u>normalis�e</u>.
	 * </p>
	 * 
	 * @param r0 Le 1ier point du plan.
	 * @param r1 Le 2i�em point du plan.
	 * @param r2 Le 3i�me point du plan.
	 * @return La normale � la surface du plan <b>normalis�</b>.
	 * @throws SColinearException Si les trois points sont colin�aires.
	 */
	public static SVector3d normalizedPlanNormal(SVector3d r0, SVector3d r1, SVector3d r2) throws SColinearException {
		// �valuer la normale � la surface d'un plan et normaliser celle-ci (la rendre
		// de taille �gale � 1).
		try {
			return planNormal(r0, r1, r2).normalize();
		} catch (SImpossibleNormalizationException e) {
			throw new SColinearException("Erreur SLinearAlgebra 001 : La normale d'un plan compos� des points r0 = "
					+ r0 + ", r1 = " + r1 + ", r2 = " + r2 + " est ind�termin�, car ces points sont colin�aires.", e);
		}
	}

	/**
	 * M�thode permettant de d�terminer si quatre points p0, p1, p2 et p3 sont
	 * coplanaires (ils sont tous situ�s dans le m�me plan).
	 *
	 * @param r0 Le vecteur position du point p0.
	 * @param r1 Le vecteur position du point p1.
	 * @param r2 Le vecteur position du point p2.
	 * @param r3 Le vecteur position du point p3.
	 * @return <b>true</b> si les quatre points sont coplanaires et <b>false</b>
	 *         sinon.
	 */
	public static boolean isCoplanar(SVector3d r0, SVector3d r1, SVector3d r2, SVector3d r3) {
		throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
	}

	/**
	 * M�thode permettant de d�terminer si trois points p0, p1 et p2 sont colin�aire
	 * (ils sont tous align�s sur la m�me droite).
	 * 
	 * @param r0 Le vecteur position du point p0.
	 * @param r1 Le vecteur position du point p1.
	 * @param r2 Le vecteur position du point p2.
	 * @return <b>true</b> si les trois points sont colin�aire et <b>false</b>
	 *         sinon.
	 */
	public static boolean isCollinear(SVector3d r0, SVector3d r1, SVector3d r2) {
		throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
	}

	/**
	 * <p>
	 * M�thode permettant d'obtenir les coordonn�es barycentriques d'une vecteur r
	 * associ� � une projection dans le plan d'un triangle de coordonn�es P0, P1 et
	 * P2.
	 * </p>
	 * <p>
	 * Les coordonn�es (b1, b2) obtenues correspondent � la projection du vecteur r
	 * dans le plan du triangle :
	 * <ul>
	 * - La coordonn�e (0,0) est associ�e � une projection sur le point P0.
	 * </ul>
	 * <ul>
	 * - La coordonn�e (1,0) est associ�e � une projection sur le point P1.
	 * </ul>
	 * <ul>
	 * - La coordonn�e (0,1) est associ�e � une projection sur le point P2.
	 * </ul>
	 * <ul>
	 * - La coordonn�e (b1,b2) o� b1 > 0, b2 > 0 et b1 + b2 <= 1 est associ�e � une
	 * projection � <u>l'int�rieur du triangle</u>.
	 * </ul>
	 * <ul>
	 * - La coordonn�e (b1,b2) o� b1 > 0, b2 > 0 ou b1 + b2 <= 1 n'est pas respect�e
	 * est associ�e � une projection � <u>l'ext�rieur du triangle</u>.
	 * </ul>
	 * </p>
	 * 
	 * @param P0 Le 1ier point du triangle.
	 * @param P1 Le 2i�me point du triangle.
	 * @param P2 Le 3i�me point du triangle.
	 * @param r  Le vecteur � projeter dans le triangle afin d'y obtenir les
	 *           coordonn�es barycentriques.
	 * @return La coordonn�e barycentrique (b1, b2) de la projection du vecteur r
	 *         dans le triangle o� tab[0] = b1 et tab[1] = b2.
	 */
	public static double[] triangleBarycentricCoordinates(SVector3d P0, SVector3d P1, SVector3d P2, SVector3d r) {
		SVector3d s1 = P1.substract(P0);
		SVector3d s2 = P2.substract(P0);
		SVector3d w = r.substract(P0);

		double[] tab = new double[2];
		tab[0] = ((w.dot(s1)) * (s2.dot(s2)) - (w.dot(s2) * (s1.dot(s2)))) / ((s1.dot(s1)) * (s2.dot(s2)) - (s1.dot(s2)) * (s1.dot(s2)));
		tab[1] = ((w.dot(s2)) * (s1.dot(s1)) - (w.dot(s1) * (s1.dot(s2)))) / ((s1.dot(s1)) * (s2.dot(s2)) - (s1.dot(s2)) * (s1.dot(s2)));

		return tab;
	}

	/**
	 * <p>
	 * M�thode permettant d'obtenir les coordonn�es barycentriques d'une vecteur r
	 * associ� � une projection dans le plan d'un triangle de coordonn�es P0, P1 et
	 * P2.
	 * </p>
	 * <p>
	 * Cette version de l'impl�mentation permet d'effectuer le calcul en r�duisant
	 * l'allocation de m�moire.
	 * </p>
	 * 
	 * @param P0          Le 1ier point du triangle.
	 * @param s1          Le segment du triangle P0 vers P1.
	 * @param s2          Le segment du triangle P0 vers P2.
	 * @param s1Dots1     Le produit scalaire entre s1 et s1.
	 * @param s2Dots2     Le produit sclaire entre s2 et s2.
	 * @param s1Dots2     Le produit scalaire entre s1 et s1.
	 * @param denominator Le d�nominateur du calcul �tant S1dotS1*S2dotS2 -
	 *                    S1dotS2*S1dotS2.
	 * @param r           Le vecteur � projeter dans le triangle afin d'y obtenir
	 *                    les coordonn�es barycentriques.
	 * @return La coordonn�e barycentrique (b1, b2) de la projection du vecteur r
	 *         dans le triangle o� tab[0] = b1 et tab[1] = b2.
	 */
	public static double[] triangleBarycentricCoordinates(SVector3d P0, SVector3d S1, SVector3d S2, double S1dotS1,
			double S2dotS2, double S1dotS2, double denominator, SVector3d r) {
		throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
	}

	/**
	 * M�thode pour d�terminer si un couple de coordonn�e baricentrique de triangle
	 * correspond � un point � l'int�rieur du triangle.
	 * 
	 * @param b_coord Le tableau contenant les deux coordonn�es barycentriques �
	 *                analyser.
	 * @return <b>true</b> si les coordonn�es barycentriques correspondent � �tre �
	 *         l'int�rieur du triangle et <b>false</b> sinon.
	 * @throws SRuntimeException Si le tableau ne contient pas exactement deux
	 *                           valeurs.
	 */
	public static boolean isBarycentricCoordinatesInsideTriangle(double[] b_coord) throws SRuntimeException {
		// V�rification de la taille du tableau.
		if (b_coord.length != 2)
			throw new SRuntimeException(
					"Erreur SLinearAlgebra 002 : Le tableau de coordonn�e barycentrique d'un triangle contient "
							+ b_coord.length + " �l�ment ce qui n'est pas exactement �gal � 2.");

		if ((b_coord[0] >= 0) && (b_coord[1] >= 0) && (b_coord[0] + b_coord[1] <= 1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * M�thode pour �valuer la position de l'intersection de deux droites
	 * coplanaires. Dans ce calcul, les deux droites sont consid�r�es comme �tant
	 * <i>infini</i>.
	 * 
	 * @param r0 Le vecteur position du point p0 associ� � la premi�re droite.
	 * @param r1 Le vecteur position du point p1 associ� � la premi�re droite.
	 * @param r2 Le vecteur position du point p2 associ� � la deuxi�me droite.
	 * @param r3 Le vecteur position du point p3 associ� � la deuxi�me droite.
	 * @throws SNoCoplanarException Si les deux droites ne sont pas coplanaires.
	 * @return La position de l'intersection des deux droites coplanaires.
	 */
	public static SVector3d coplanarEdgeEdgeIntersection(SVector3d r0, SVector3d r1, SVector3d r2, SVector3d r3)
			throws SNoCoplanarException {
		throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
	}

	/**
	 * M�thode pour d�terminer si deux droites sont en intersection. Cette condition
	 * sera satisfaite si
	 * <ul>
	 * - Les deux droites sont coplanaires.
	 * </ul>
	 * <ul>
	 * - Le point de l'intersection est situ� entre les deux droites.
	 * </ul>
	 * 
	 * @param r0 Le vecteur position du point p0 associ� � la premi�re droite.
	 * @param r1 Le vecteur position du point p1 associ� � la premi�re droite.
	 * @param r2 Le vecteur position du point p2 associ� � la deuxi�me droite.
	 * @param r3 Le vecteur position du point p3 associ� � la deuxi�me droite.
	 * @return <b>true</b> si les deux droites sont en intersection et <b>false</b>
	 *         sinon.
	 */
	public static boolean isEdgeEdgeIntersection(SVector3d r0, SVector3d r1, SVector3d r2, SVector3d r3) {
		throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
	}

	/**
	 * M�thode pour d�terminer si un point est en intersection avec un triangle.
	 * Cette condition sera satisfaite si
	 * <ul>
	 * - Le point et le triangle sont coplanaires.
	 * </ul>
	 * <ul>
	 * - Le point en intersection est situ� dans le triangle.
	 * </ul>
	 * 
	 * @param r0  Le 1ier point du triangle.
	 * @param r1  Le 2i�me point du triangle.
	 * @param r2  Le 3i�me point du triangle.
	 * @param r_p Le point � v�rifier.
	 * @return <b>true</b> si le point est en intersection avec le triangle et
	 *         <b>false</b> sinon.
	 */
	public static boolean isPointTriangleIntersection(SVector3d r0, SVector3d r1, SVector3d r2, SVector3d r_p) {
		throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
	}

	/**
	 * M�thode pour obtenir le temps n�cessaire afin que trois points en mouvement �
	 * vitesse constante puissent �tre colin�aire (�tre align� sur une ligne
	 * droite). S'il n'y a pas de temps admissible, la solution sera un tableau
	 * vide. S'il y a une infinit� de solution, une exception sera lanc�e.
	 * 
	 * @param r0 La position du 1ier point.
	 * @param v0 La vitesse du 1ier point.
	 * @param r1 La position de 2i�me point.
	 * @param v1 La vitesse du 2i�me point.
	 * @param r2 La position du 3i�me point.
	 * @param v2 La vitesse du 3i�me point.
	 * @return Un tableau des temps o� les trois points seront colin�aire.
	 * @throws SInfinityOfSolutionsException Si les trois points sont toujours
	 *                                       colin�aire. Il y aura une infinit� de
	 *                                       temps admissible.
	 */
	public static double[] timeToCollinear(SVector3d r0, SVector3d v0, SVector3d r1, SVector3d v1, SVector3d r2,
			SVector3d v2) throws SInfinityOfSolutionsException {
		throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
	}

	/**
	 * M�thode permettant d'�valuer le temps requis afin que quatre points en
	 * mouvement � vitesse constante puissent �tre coplanaire (�tre situ� dans le
	 * m�me plan). Puisque la r�solution de ce probl�me math�matique n�cessite la
	 * r�solution d'un polyn�me du 3i�me degr�, il y aura toujours une solution
	 * r�elle. S'il y a une infinit� de solution, une exception sera lanc�e.
	 * 
	 * @param r0 La position du 1ier point.
	 * @param v0 La vitesse du 1ier point.
	 * @param r1 La position de 2i�me point.
	 * @param v1 La vitesse du 2i�me point.
	 * @param r2 La position du 3i�me point.
	 * @param v2 La vitesse du 3i�me point.
	 * @param r3 La position du 4i�me point.
	 * @param v3 La vitesse du 4i�me point.
	 * @return Un tableau des temps o� les trois points seront colin�aire.
	 * @throws SInfinityOfSolutionsException Si les quatre points sont toujours
	 *                                       coplanaires. Il y aura une infinit� de
	 *                                       temps admissible.
	 */
	public static double[] timeToCoplanar(SVector3d r0, SVector3d v0, SVector3d r1, SVector3d v1, SVector3d r2,
			SVector3d v2, SVector3d r3, SVector3d v3) throws SInfinityOfSolutionsException {
		throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
	}

}
