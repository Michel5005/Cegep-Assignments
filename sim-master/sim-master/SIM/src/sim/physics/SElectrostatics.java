/**
 * 
 */
package sim.physics;

import sim.exception.SNoImplementationException;
import sim.math.SImpossibleNormalizationException;
import sim.math.SMath;
import sim.math.SVector3d;

/**
 * La classe <b>SElectrostatics</b> permet d'effectuer des calculs en physique en lien avec l'�lectrostatique.
 * 
 * @author Simon V�zina
 * @since 2017-05-30
 * @version 2021-09-04 (Version labo v1.1.6 : La loi de Coulomb)
 */
public class SElectrostatics {

	/**
	 * La constante <b>k</b> représente la <b>constante de Coulomb</b> �tant �gale � <b>{@value} N.m^2.C^-2</b>.
	 */
	public static final double k = SPhysics.k;

	/**
	 * La constante <b>EPSILON_ZERO</b> représente la <b>constante électrique</b> �tant �gale � {@value}.
	 */
	public static final double EPSILON_ZERO = SPhysics.EPSILON_ZERO;

	/**
	 * méthode permettant d'�valuer la loi de Coulomb entre deux charges électriques.
	 * 
	 * @param Q La charge qui applique la force électrique.
	 * @param r_Q La position de la charge Q.
	 * @param q La charge qui subit la force électrique.
	 * @param r_q La position de la charge q.
	 * @return La force électrique appliqu�e par la charge Q sur la charge q.
	 */
	public static SVector3d coulombLaw(double Q, SVector3d r_Q, double q, SVector3d r_q)
	{
		final SVector3d vecteurD = r_q.substract(r_Q);
		

		SVector3d forceE = new SVector3d ();
		forceE = vecteurD.multiply((k*q*Q)/(Math.pow((vecteurD).modulus(),3)));
		return forceE;
	}


	/**
	 * méthode permettant d'�valuer la force électrique appliqu�e sur une charge q plong�e dans un champ électrique E.
	 * 
	 * @param q La charge qui subit la force électrique en coulombs (C).
	 * @param E Le champ électrique qui applique la force électrique (N/C).
	 * @return La force électrique appliquée sur la charge (N).
	 */
	public static SVector3d electricForce(double q, SVector3d E)
	{
		throw new SNoImplementationException("La méthode n'a pas été implémentée.");    
	}

	/**
	 * ...
	 * 
	 * @param r_p
	 * @param Q
	 * @param r
	 * @return
	 * @throws SUndefinedElectricFieldException
	 */
	public static SVector3d particleElectricField(SVector3d r_p, double Q, SVector3d r) throws SUndefinedElectricFieldException
	{
		throw new SNoImplementationException("La méthode n'a pas été implémentée.");
	}

	/**
	 * méthode permettant d'�valuer le champ électrique g�n�r� par une sph�re uniform�ment charg�e.
	 * 
	 * @param r_S La position de la sph�re en m�tres (m).
	 * @param R Le rayon de la sph�re en m�tres (m).
	 * @oaram Q La charge de la sph�re en coulombs (C).
	 * @param r La position o� le champ électrique est évalué en m�tre (m).
	 * @return Le champ électrique g�n�r� par la sph�re (N/C).
	 */
	public static SVector3d sphereElectricField(SVector3d r_S, double R, double Q, SVector3d r)
	{
		throw new SNoImplementationException("La méthode n'a pas été implémentée.");
	}

	/**
	 * méthode permettant d'�valuer le champ électrique g�n�r� par une tige infinie uniform�ment charg�e (TRIUC).
	 * 
	 * @param r_R La position d'un point appartenant � la tige en m�tres (m).
	 * @param axis L'axe de la tige en m�tres (m).
	 * @param lambda La densit� de charge de la tige (C/m).
	 * @param r La position o� le champ électrique est évalué en m�tres (m).
	 * @return Le champ électrique g�n�r� par la tige infinie (N/C).
	 * @throws SImpossibleNormalizationException Si l'axe de la tige ne peut par �tre normalis�.
	 */
	public static SVector3d infiniteRodElectricField(SVector3d r_R, SVector3d axis, double lambda, SVector3d r) throws SImpossibleNormalizationException
	{
		throw new SNoImplementationException("La méthode n'a pas été implémentée.");
	}

	/**
	 * méthode permettant d'�valuer le champ électrique g�n�r� par une plaque infinie uniform�ment charg�e (PPIUC).
	 * 
	 * @param r_P La position d'un point appartenant � la plaque en m�tres (m).
	 * @param n_P La normale � la surface de la plaque en m�tres (m).
	 * @param sigma La densit� de charge surfacique de la plaque (C/m^2).
	 * @param r Le champ électrique g�n�r� par la plaque infinie en m�tres (m).
	 * @return Le champ électrique g�n�r� par la plaque infinie (N/C).
	 * @throws SImpossibleNormalizationException Si la normale au plan de la plaque ne peut par �tre normalis�e.
	 */
	public static SVector3d infinitePlateElectricField(SVector3d r_P, SVector3d n_P, double sigma, SVector3d r) throws SImpossibleNormalizationException
	{
		throw new SNoImplementationException("La méthode n'a pas été implémentée.");
	}

	/**
	 * méthode pour obtenir le potentiel électrique g�n�r� par une particule ponctuelle.
	 * 
	 * @param r_p La position de la particule.
	 * @param Q La charge électrique de la particule.
	 * @param r La position o� sera évalué le potentiel électrique.
	 * @return
	 */
	public static double particleElectricPotential(SVector3d r_p, double Q, SVector3d r)
	{
		throw new SNoImplementationException("La méthode n'a pas été implémentée.");
	}

}
