/**
 * 
 */
package sim.physics;

import sim.exception.SNoImplementationException;
import sim.math.SImpossibleNormalizationException;
import sim.math.SMath;
import sim.math.SVector3d;
import sim.math.field.SUndefinedFieldException;

/**
 * La classe <b>SElectrostatics</b> permet d'effectuer des calculs en physique
 * en lien avec l'�lectrostatique.
 * 
 * @author Simon V�zina
 * @since 2017-05-30
 * @version 2022-06-02 (version labo � Le champ �lectrique v1.1.0)
 */
public class SElectrostatics {

	/**
	 * La constante <b>k</b> repr�sente la <b>constante de Coulomb</b> �tant �gale �
	 * <b>{@value} N.m^2.C^-2</b>.
	 */
	public static final double k = SPhysics.k;

	/**
	 * La constante <b>EPSILON_ZERO</b> repr�sente la <b>constante �lectrique</b>
	 * �tant �gale � {@value}.
	 */
	public static final double EPSILON_ZERO = SPhysics.EPSILON_ZERO;

	/**
	 * La constante <b>NO_ELECTRIC_FIELD</b> repr�sente une composante d'un champ
	 * �lectrique �tant �gal � z�ro.
	 */
	public static final SVector3d NO_ELECTRIC_FIELD = SVector3d.ZERO;

	/**
	 * M�thode permettant d'�valuer la loi de Coulomb entre deux charges
	 * �lectriques.
	 * 
	 * @param Q   La charge qui applique la force �lectrique.
	 * @param r_Q La position de la charge Q.
	 * @param q   La charge qui subit la force �lectrique.
	 * @param r_q La position de la charge q.
	 * @return La force �lectrique appliqu�e par la charge Q sur la charge q.
	 */
	public static SVector3d coulombLaw(double Q, SVector3d r_Q, double q, SVector3d r_q) {

		// Vecteur d�placement entre Q et q.
		SVector3d r_Qq = r_q.substract(r_Q);

		// �valuer le d�placement au cube.
		double m = r_Qq.modulus();
		double r3 = m * m * m;

		// Retourner la force �lectrique.
		return r_Qq.multiply(k * Q * q / r3);
	}

	/**
	 * M�thode permettant d'�valuer la force �lectrique appliqu�e sur une charge q
	 * plong�e dans un champ �lectrique E.
	 * 
	 * @param q La charge qui subit la force �lectrique en coulombs (C).
	 * @param E Le champ �lectrique qui applique la force �lectrique (N/C).
	 * @return La force �lectrique appliqu�e sur la charge (N).
	 */
	public static SVector3d electricForce(double q, SVector3d E) {

		return E.multiply(q);
	}

	/**
	 * M�thode pour obtenir le champ �lectrique g�n�r� par une particule ponctuelle.
	 * 
	 * @param r_p La position de la particule.
	 * @param Q   La charge de la particule.
	 * @param r   La position o� le champ �lectrique est �valu�.
	 * @return Le champ �lectrique.
	 */
	public static SVector3d particleElectricField(SVector3d r_p, double Q, SVector3d r) {

		// �valuer la distance entre les deux points.
		SVector3d r_Q = r.substract(r_p);
		double m = r_Q.modulus();

		// Cas particulier : Calcul du champ �lectrique sur une particule.
		if (SMath.nearlyZero(m))
			return NO_ELECTRIC_FIELD;

		// Cas g�n�ral :
		double r3 = m * m * m;

		return r_Q.multiply(k * Q / r3);
	}

	/**
	 * M�thode permettant d'�valuer le champ �lectrique g�n�r� par une sph�re
	 * uniform�ment charg�e.
	 * 
	 * @param r_S La position de la sph�re en m�tres (m).
	 * @param R   Le rayon de la sph�re en m�tres (m).
	 * @param Q La charge de la sph�re en coulombs (C).
	 * @param r La position o� le champ �lectrique est �valu� en m�tre (m).
	 * @return Le champ �lectrique g�n�r� par la sph�re (N/C).
	 */
	public static SVector3d sphereElectricField(SVector3d r_S, double R, double Q, SVector3d r) {
		SVector3d vectorE;
		SVector3d distance = r.substract(r_S);
		double moduleDistance = r.substract(r_S).modulus();

		if (R < (moduleDistance)) {
			vectorE = distance.multiply(Math.pow(moduleDistance, -3)).multiply(k * Q);
			return vectorE;
		} else {
			return NO_ELECTRIC_FIELD;
		}
	}

	/**
	 * M�thode permettant d'�valuer le champ �lectrique g�n�r� par une tige infinie
	 * uniform�ment charg�e (TRIUC).
	 * 
	 * @param r_R    La position d'un point appartenant � la tige en m�tres (m).
	 * @param axis   L'axe de la tige en m�tres (m).
	 * @param lambda La densit� de charge de la tige (C/m).
	 * @param r      La position o� le champ �lectrique est �valu� en m�tres (m).
	 * @return Le champ �lectrique g�n�r� par la tige infinie (N/C).
	 * @throws SImpossibleNormalizationException Si l'axe de la tige ne peut par
	 *                                           �tre normalis�.
	 */
	public static SVector3d infiniteRodElectricField(SVector3d r_R, SVector3d axis, double lambda, SVector3d r) {
		// Faire la normalisation de l'axe de la tige.
		axis = axis.normalize();
		SVector3d vectorE;
		SVector3d vectorR_T = axis.cross(r.substract(r_R)).cross(axis);
		double modulusVectorR_T = vectorR_T.modulus();
		double k_lambda_on_R = k * lambda * Math.pow(modulusVectorR_T, -2);
		if (SMath.nearlyZero(modulusVectorR_T)) {
			return NO_ELECTRIC_FIELD;
		} else {
			vectorE = vectorR_T.multiply(2 * k_lambda_on_R);
		}
		return vectorE;
	}

	/**
	 * M�thode pour �valuer le champ �lectrique g�n�r� par une tige uniform�ment
	 * charg�e <u>hors axe</u>.
	 * 
	 * @param r_A    La position de l'extr�mit� A de la tige.
	 * @param r_B    La position de l'extr�mit� B de la tige.
	 * @param lambda La densit� de charge sur la tige.
	 * @param r      La position o� est �valu� le champ �lectrique.
	 * @return Le champ �lectrique.
	 * @throws SImpossibleNormalizationException Si un vecteur ne peut pas �tre
	 *                                           normalis�.
	 * @throws SUndefinedFieldException          Si le champ �lectrique n'est pas
	 *                                           d�fini.
	 */
	public static SVector3d finiteRodElectricFieldOutsideAxis(SVector3d r_A, SVector3d r_B, double lambda, SVector3d r)
			throws SUndefinedFieldException {
		SVector3d vectorE;
		SVector3d axisNormalize = r_B.substract(r_A).multiply(Math.pow((r_B.substract(r_A)).modulus(), -1)).normalize();
		SVector3d vectorR_P_A = r_A.substract(r);
		SVector3d vectorR_P_B = r_B.substract(r);

		SVector3d normalizeVectorR_P_A = vectorR_P_A.normalize();
		SVector3d normalizeVectorR_P_B = vectorR_P_B.normalize();

		double modulusVectorR_P_A = vectorR_P_A.modulus();
		double modulusVectorR_P_B = vectorR_P_B.modulus();

		normalizeVectorR_P_A = vectorR_P_A.multiply(Math.pow(modulusVectorR_P_A, -1));
		normalizeVectorR_P_B = vectorR_P_B.multiply(Math.pow(modulusVectorR_P_B, -1));

		SVector3d vectorN = vectorR_P_A.cross(axisNormalize);
		double modulusVectorN = vectorN.modulus();
		SVector3d normalizeVectorN = vectorN.multiply(Math.pow(modulusVectorN, -1)).normalize();
				
		SVector3d vectorR_T = vectorN.cross(axisNormalize);
		double modulusVectorR_T = vectorR_T.modulus();
		SVector3d normalizeVectorR_T = vectorR_T.multiply(Math.pow(modulusVectorR_T, -1)).normalize();
		

		double theta_A = (normalizeVectorR_T.cross(normalizeVectorR_P_A)).dot(normalizeVectorN);
		double alphaA = Math.asin(theta_A);
		double theta_B = (normalizeVectorR_T.cross(normalizeVectorR_P_B)).dot(normalizeVectorN);
		double alphaB = Math.asin(theta_B);

		if (SMath.nearlyZero(modulusVectorR_T)) {
			throw new SUndefinedFieldException();
		} else {
			double E;
			double k_lambda_on_R = k * lambda * Math.pow(modulusVectorR_T, -1);
			E = (Math.sqrt(2)) * k_lambda_on_R * Math.sqrt(1 - Math.cos(alphaA - alphaB));
			vectorE = axisNormalize.multiply(E * Math.sin((alphaA + alphaB) / 2))
					.add(normalizeVectorR_T.multiply(E * Math.cos((alphaA + alphaB) / 2)));
		}
		return vectorE;
	}

	/**
	 * M�thode pour obtenir le champ �lectrique g�n�r� par une tige uniform�ment
	 * charg�e (sur l'axe ou hors axe).
	 * 
	 * @param r_A La position de l'extr�mit� A de la tige.
	 * @param r_B La position de l'extr�mit� B de la tige.
	 * @param Q   La charge sur la tige.
	 * @param r   La position o� est �valu� le champ �lectrique.
	 * @return Le champ �lectrique.
	 */
	public static SVector3d finiteRodElectricField(SVector3d r_A, SVector3d r_B, double Q, SVector3d r) {
		SVector3d d_A = r.substract(r_A);
		SVector3d d_B = r.substract(r_B);
		SVector3d normalizeVectorD = d_A.multiply(Math.pow(d_A.modulus(), -1)).normalize();
		double modulus_rA_rB = r_A.substract(r_B).modulus();
		SVector3d E = null;
		
		if(SMath.nearlyZero(d_A.modulus()) || SMath.nearlyZero(d_B.modulus())) {
			E = NO_ELECTRIC_FIELD;
			
		} else if(SMath.nearlyZero(r_B.modulus() - r_A.modulus())) {
			E = sphereElectricField(r_A, 0, Q, r);
			
		} else if(SMath.nearlyEquals(d_A.dot(d_B) * (Math.pow(d_A.modulus() * d_B.modulus(), -1)), 1)) {
			E = normalizeVectorD.multiply((k*Q)/(d_A.modulus() * d_B.modulus()));
			
		} else if(SMath.nearlyEquals(d_A.dot(d_B) * (Math.pow(d_A.modulus() * d_B.modulus(), -1)), -1)) {
			E = NO_ELECTRIC_FIELD;
			
		}else if(d_A.dot(d_B) * (Math.pow(d_A.modulus() * d_B.modulus(), -1)) != 1) {
			E = finiteRodElectricFieldOutsideAxis(r_A, r_B, Q/modulus_rA_rB, r);
		}
		return E;

	}

	/**
	 * M�thode permettant d'�valuer le champ �lectrique g�n�r� par une plaque
	 * infinie uniform�ment charg�e (PPIUC).
	 * 
	 * @param r_P   La position d'un point appartenant � la plaque en m�tres (m).
	 * @param n_P   La normale � la surface de la plaque en m�tres (m).
	 * @param sigma La densit� de charge surfacique de la plaque (C/m^2).
	 * @param r     Le champ �lectrique g�n�r� par la plaque infinie en m�tres (m).
	 * @return Le champ �lectrique g�n�r� par la plaque infinie (N/C).
	 * @throws SImpossibleNormalizationException Si la normale au plan de la plaque
	 *                                           ne peut par �tre normalis�e.
	 */
	public static SVector3d infinitePlateElectricField(SVector3d r_P, SVector3d n_P, double sigma, SVector3d r) {
		// Faire la normalisation de la normale � la surface de la plaque.
		n_P = n_P.normalize();
		SVector3d vectorE;
		SVector3d distance = r.substract(r_P);
		double s = n_P.dot(distance);

		if (SMath.nearlyZero(s)) {
			return vectorE = NO_ELECTRIC_FIELD;
		} else {
			s = Math.signum(s);
			vectorE = n_P.multiply(s * sigma * Math.pow(2 * EPSILON_ZERO, -1));
		}

		return vectorE;

	}

	/**
	 * M�thode pour obtenir le potentiel �lectrique g�n�r� par une particule
	 * ponctuelle.
	 * 
	 * @param r_p La position de la particule.
	 * @param Q   La charge �lectrique de la particule.
	 * @param r   La position o� sera �valu� le potentiel �lectrique.
	 * @return Le potentiel �lectrique.
	 */
	public static double particleElectricPotential(SVector3d r_p, double Q, SVector3d r) {
		throw new SNoImplementationException();

	}

}
