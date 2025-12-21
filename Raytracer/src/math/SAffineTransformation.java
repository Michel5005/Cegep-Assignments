/**
 * 
 */
package sim.math;

import sim.exception.SNoImplementationException;

/**
 * La classe <b>SAffineTransformation</b> repr�sente une classe pouvant r�aliser
 * des transformations affines sur des vecteurs.
 * 
 * @author Simon Vezina
 * @since 2017-08-21
 * @version 2017-12-20 (version labo � Le ray tracer v2.1)
 */
public class SAffineTransformation {

	/**
	 * M�thode qui effectue la transformation d'un <i>origine</i> (vecteur position
	 * d�signant l'origine d'une syst�me d'axe) en 3D � l'aide d'une transformation
	 * lin�aire d�finit � l'aide d'une matrice 4x4. Un vecteur 4d repr�sentant un
	 * origine 3d correspond au vecteur (0,0,0,1).
	 * 
	 * @param transformation La matrice de transformation.
	 * @return Un vecteur origine transform� � l'aide d'une matrice de
	 *         transformation.
	 */
	public static SVector3d transformOrigin(SMatrix4x4 transformation) {
		SVector4d ro4 = transformation.multiply(new SVector3d(0, 0, 0));
		SVector3d ro = ro4.getSVector3d();

		return ro;
	}

	/**
	 * M�thode qui effectue la transformation d'un <i>vecteur position</i> en 3D
	 * (converti en vecteur 4d o� la 4i�me composante est 1), � l'aide d'une
	 * transformation lin�aire d�finit � l'aide d'une matrice 4x4.
	 * 
	 * @param transformation La matrice de transformation.
	 * @param v              Le vecteur position � transformer.
	 * @return Un vecteur position transform� � l'aide d'une matrice de
	 *         transformation.
	 */
	public static SVector3d transformPosition(SMatrix4x4 transformation, SVector3d v) {

		SVector4d x = transformation.multiply(v);

		return x.getSVector3d();
	}

	/**
	 * M�thode qui effectue la transformation d'un <i>vecteur orientation</i> en 3D
	 * (converti en vecteur 4d o� la 4i�me composante est 1), � l'aide d'une
	 * transformation lin�aire d�finit � l'aide d'une matrice 4x4.
	 * 
	 * @param transformation La matrice de transformation.
	 * @param v              Le vecteur orientation � transformer.
	 * @return Un vecteur orientation transform� � l'aide d'une matrice de
	 *         transformation.
	 */
	public static SVector3d transformOrientation(SMatrix4x4 transformation, SVector3d v) {
		SVector4d r_o4 = transformation.multiply(new SVector3d(0, 0, 0));
		SVector3d r_o = r_o4.getSVector3d();
		SVector4d vTemp4 = transformation.multiply(v);
		SVector3d vTemp = vTemp4.getSVector3d();

		return vTemp.substract(r_o);
	}

	/**
	 * M�thode qui effectue la transformation d'un <i>vecteur orientation</i> en 3D
	 * (converti en vecteur 4d o� la 4i�me composante est 1), � l'aide d'une
	 * transformation lin�aire d�finit � l'aide d'une matrice 4x4. Puisque cette
	 * transformation n�cessite la transformation d'un origine de r�f�rence au
	 * vecteur orientation, cette version d'impl�mentation utilise une origine
	 * transform�e calcul�e � l'ext�rieur de l'appel de cette m�thode. Un usage
	 * r�p�titif de cette transformation avec le m�me origine transform�e permettra
	 * d'optenir un �conomie d'allocation de m�moire et une r�duction de calcul
	 * r�p�titif.
	 * 
	 * @param transformation     La matrice de transformation.
	 * @param transformed_origin L'origine transform�e du vecteur orientation.
	 * @param v                  Le vecteur orientation � transformer.
	 * @return Un vecteur orientation transform� � l'aide d'une matrice de
	 *         transformation.
	 */
	public static SVector3d transformOrientation(SMatrix4x4 transformation, SVector3d transformed_origin, SVector3d v) {
		throw new SNoImplementationException("Erreur SAffineTransformation : La m�thode n'est pas impl�ment�e.");
	}

	/**
	 * M�thode qui effectue la transformation d'un <i>vecteur normale</i> � l'aide
	 * d'une transformation lin�aire.
	 * 
	 * @param transformation La matrice de transformation.
	 * @param v              Le vecteur normale � transformer.
	 * @return Un vecteur normale transform� � l'aide d'une matrice de
	 *         transformation.
	 */
	public static SVector3d transformNormal(SMatrix4x4 transformation, SVector3d v) {
		SVector4d r_o4 = transformation.multiply(new SVector3d(0, 0, 0));
		SVector3d r_o = r_o4.getSVector3d();
		SVector4d vTemp4 = transformation.multiply(v);
		SVector3d vTemp3 = vTemp4.getSVector3d();

		return vTemp3.substract(r_o).normalize();

	}

	/**
	 * M�thode qui effectue la transformation d'un <i>vecteur normale</i> � l'aide
	 * d'une transformation lin�aire. Puisque cette transformation n�cessite la
	 * transformation d'un origine de r�f�rence au vecteur orientation, cette
	 * version d'impl�mentation utilise une origine transform�e calcul�e �
	 * l'ext�rieur de l'appel de cette m�thode. Un usage r�p�titif de cette
	 * transformation avec le m�me origine transform�e permettra d'optenir un
	 * �conomie d'allocation de m�moire et une r�duction de calcul r�p�titif.
	 * 
	 * @param transformation     La matrice de transformation.
	 * @param transformed_origin L'origine transform�e du vecteur orientation.
	 * @param v                  Le vecteur normale � transformer.
	 * @return Un vecteur normale transform� � l'aide d'une matrice de
	 *         transformation.
	 */
	public static SVector3d transformNormal(SMatrix4x4 transformation, SVector3d transformed_origin, SVector3d v) {
		throw new SNoImplementationException("Erreur SAffineTransformation : La m�thode n'est pas impl�ment�e.");
	}

}// fin de la classe SLinearTransformation
