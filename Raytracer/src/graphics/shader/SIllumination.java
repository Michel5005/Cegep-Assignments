/**
 * 
 */
package sim.graphics.shader;

import java.util.List;

import sim.exception.SNoImplementationException;
import sim.graphics.SColor;
import sim.graphics.material.SMaterial;
import sim.math.SVector3d;
import sim.physics.SGeometricalOptics;

/**
 * La classe <b>SIllumination</b> repr�sente une classe utilitaire qui effectue
 * les calculs d'illuminations selon le type d'algorithme utilis�. On y retrouve
 * des mod�les d'illumination � r�flexion (ambiante, diffuse et sp�culaire) et �
 * transmission (filtrage d'une source de lumi�re au travers des mat�riaux
 * transparents).
 * 
 * @author Simon V�zina
 * @since 2015-01-16
 * @version 2017-12-20 (version labo � Le ray tracer v2.1)
 */
public class SIllumination {

	// --------------
	// CONSTANTES //
	// --------------

	/**
	 * La constante <b>NO_ILLUMINATION</b> correspond � la couleur par d�faut sans
	 * calcul d'illumination. Cette couleur est <b>noire</b>.
	 */
	public static final SColor NO_ILLUMINATION = new SColor(0.0, 0.0, 0.0);

	// ------------
	// M�THODES //
	// ------------

	/**
	 * <p>
	 * M�thode qui effectue le calcul de la <u>r�flexion ambiante</u> d'une source
	 * de lumi�re <b><i>L</i>a</b> sur une surface dont la couleur r�fl�chie de
	 * fa�on ambiante est <b><i>S</i>a</b>.
	 * </p>
	 * <p>
	 * La formule calcul�e est <b><i>L</i>amb = <i>L</i>a*<i>S</i>a</b>.
	 * </p>
	 * 
	 * @param La - La couleur ambiante <b><i>L</i>a</b> de la source de lumi�re.
	 * @param Sa - La couleur r�fl�chie de fa�on ambiante <b><i>S</i>a</b> par la
	 *           surface.
	 * @return La couleur ambient <b><i>L</i>amb</b> de la lumi�re r�fl�chie par la
	 *         surface.
	 */
	public static SColor ambientReflexion(SColor La, SColor Sa) {

		return La.multiply(Sa);
	}

	/**
	 * <p>
	 * M�thode qui effectue le calcul de la <u>r�flexion diffuse</u> selon le
	 * <b>mod�le de r�fexion Lambertienne</b> d'une source de lumi�re
	 * <b><i>L</i>d</b> sur une surface dont la couleur r�fl�chie de fa�on diffuse
	 * est <b><i>S</i>d</b></b>.
	 * </p>
	 * <p>
	 * La formule calcul�e est
	 * <ul>
	 * <b><i>L</i>dif = <i>L</i>d*<i>S</i>d*<i>N</i>dot<i>L</i></b>
	 * </ul>
	 * </p>
	 * <p>
	 * o� <b><i>N</i></b> est la normale � la surface et <b><i>L</i></b> est
	 * l'orientation <u>inverse</u> de la source de lumi�re.
	 * </p>
	 * 
	 * @param Ld - La couleur diffuse <b><i>L</i>d</b> de la source de lumi�re.
	 * @param Sd - La couleur r�fl�chie de fa�on diffuse <b><i>S</i>d</b> par la
	 *           surface.
	 * @param N  - La normale <b><i>N</i></b> � la surface (unitaire).
	 * @param d  - L'orientation <b><i>d</i></b> de la source de lumi�re (unitaire).
	 * @return La couleur diffuse <b><i>L</i>dif</b> de la lumi�re r�fl�chie par la
	 *         surface.
	 */
	public static SColor lambertianReflexion(SColor Ld, SColor Sd, SVector3d N, SVector3d d) {
		if ((N.dot(d.multiply(-1)) <= 0)) {
			return NO_ILLUMINATION;
		} else {
			return Ld.multiply(Sd).multiply(N.dot(d.multiply(-1)));
		}
	}

	/**
	 * <p>
	 * M�thode qui effectue le calcul de la <u>r�flexion sp�culaire</u> selon le
	 * <b>mod�le sp�culaire de <i>Phong</i></b> d'une source de lumi�re
	 * <b><i>L</i>s</b> sur une surface dont la couleur r�fl�chie de fa�on
	 * sp�culaire est <b><i>S</i>s</b></b>.
	 * </p>
	 * <p>
	 * La formule calcul�e est
	 * <ul>
	 * <b><i>L</i>spe = <i>L</i>s*<i>S</i>s*(<i>R</i>dot<i>E</i>)^<i>n</i></b>
	 * </ul>
	 * </p>
	 * <p>
	 * o� <b><i>R</i></b> est l'orientation de la r�flexion de la lumi�re,
	 * <b><i>E</i></b> est l'orientation vers l'oeil (l'inverse de l'orientation du
	 * rayon) et <b><i>n</i></b> est le niveau de brillance.
	 * </p>
	 * 
	 * @param Ls - La couleur sp�culaire <b><i>L</i>s</b> de la source de lumi�re.
	 * @param Ss - La couleur r�fl�chie de fa�on sp�culaire <b><i>S</i>s</b> par la
	 *           surface.
	 * @param N  - La normale <b><i>N</i></b> � la surface (unitaire).
	 * @param v  - L'orientation <b><i>v</i></b> du rayon (unitaire).
	 * @param d  - L'orientation <b><i>d</i></b> de la source de lumi�re (unitaire).
	 * @param n  - Le niveau de brillance <b><i>n</i></b> (� quel point la surface
	 *           est polie).
	 * @return La couleur sp�culaire <b><i>L</i>spe</b> de la lumi�re r�fl�chie par
	 *         la surface.
	 */
	public static SColor phongSpecularReflexion(SColor Ls, SColor Ss, SVector3d N, SVector3d v, SVector3d d, double n) {
		throw new SNoImplementationException("La m�thode n'est pas impl�ment�e.");
	}

	/**
	 * <p>
	 * M�thode qui effectue le calcul de la <u>r�flexion sp�culaire</u> selon le
	 * <b>mod�le sp�culaire de <i>Blinn</i></b> d'une source de lumi�re
	 * <b><i>L</i>s</b> sur une surface dont la couleur r�fl�chie de fa�on
	 * sp�culaire est <b><i>S</i>s</b></b>.
	 * </p>
	 * <p>
	 * La formule calcul�e est
	 * <ul>
	 * <b><i>L</i>spe = <i>L</i>s*<i>S</i>s*(<i>N</i>dot<i>H</i>)^<i>n</i></b>
	 * </ul>
	 * </p>
	 * <p>
	 * o� <b><i>N</i></b> est l'orientation de la normale � la surface,
	 * <b><i>H</i></b> est l'orientation du vecteur bisecteur entre <b><i>E</i></b>
	 * et <b><i>L</i></b> et <b><i>n</i></b> est le niveau de brillance.
	 * </p>
	 * 
	 * @param Ls - La couleur sp�culaire <b><i>L</i>s</b> de la source de lumi�re.
	 * @param Ss - La couleur r�fl�chie de fa�on sp�culaire <b><i>S</i>s</b> par la
	 *           surface.
	 * @param N  - La normale <b><i>N</i></b> � la surface (unitaire).
	 * @param v  - L'orientation <b><i>v</i></b> du rayon (unitaire).
	 * @param d  - L'orientation <b><i>d</i></b> de la source de lumi�re (unitaire).
	 * @param n  - Le niveau de brillance <b><i>n</i></b> (� quel point la surface
	 *           est polie).
	 * @return La couleur sp�culaire <b><i>L</i>spe</b> de la lumi�re r�fl�chie par
	 *         la surface.
	 */
	public static SColor blinnSpecularReflexion(SColor Ls, SColor Ss, SVector3d N, SVector3d v, SVector3d d, double n) {
		SColor ret;
		SVector3d vPlusD = v.add(d);
		double norme = Math
				.sqrt(vPlusD.getX() * vPlusD.getX() + vPlusD.getY() * vPlusD.getY() + vPlusD.getZ() * vPlusD.getZ());
		SVector3d H = vPlusD.multiply(1 / norme);
		if (N.dot(H) < 0) {
			ret = Ls.multiply(Ss).multiply(Math.pow((N.dot(H) * -1), n));
		} else {
			ret = NO_ILLUMINATION;
		}
		return ret;
	}

	/**
	 * M�thode pour �valuer la couleur d'une source de lumi�re apr�s son passage au
	 * travers plusieurs mat�riaux transparents.
	 * 
	 * @param transparent_material_list - La liste des mat�riaux transparents
	 *                                  travers�s par la lumi�re.
	 * @param light_color               - La couleur de la source de lumi�re.
	 * @return La couleur de la source de la lumi�re filtr� par son passage au
	 *         travers les mat�riaux transparents.
	 */
	public static SColor filteredTransparencyLight(List<SMaterial> transparent_material_list, SColor light_color) {
		SColor filtered_light = light_color; // couleur de la lumi�re filtr�e par la transparence des g�om�tries
												// travers�es

		// Nous allons utiliser la couleur diffuse pour d�terminer la couleur pouvant
		// traverser un mat�riel.
		// Les couleurs des mat�riaux vont jouer le r�le de filtre laissant passer
		// uniquement la couleur du mat�riel
		// et pond�r� par leur niveau de transparence.
		for (SMaterial m : transparent_material_list)
			filtered_light = filtered_light.multiply(m.transparencyColor());

		return filtered_light;
	}

}// fin de la classe SIllumination
