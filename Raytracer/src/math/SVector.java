/**
 * 
 */
package sim.math;

import java.util.List;

import sim.exception.SNoImplementationException;
import sim.exception.SRuntimeException;
import sim.readwrite.SWriteable;

/**
 * <p>
 * L'interface <b>SVector</b> repr�sente un vecteur math�matique o� des
 * op�rations math�matiques de base peuvent �tre effectu�es.
 * </p>
 * 
 * <p>
 * Les op�rations math�matiques support�es sont les suivantes :
 * <ul>
 * - l'addition de vecteurs.
 * </ul>
 * <ul>
 * - la multiplication par un scalaire d'un vecteur.
 * </ul>
 * </p>
 * 
 * @author Simon V�zina
 * @since 2015-09-22
 * @version 2017-12-20 (version labo � Le ray tracer v2.1)
 */
public interface SVector extends SWriteable {

	// ---------------------------
	// M�thodes de l'interface //
	// ---------------------------

	/**
	 * M�thode permettant d'effectuer l'addition math�matique entre deux vecteurs.
	 * 
	 * @param v Le vecteur � additionner
	 * @return Le vecteur r�sultat de l'addition des deux vecteurs.
	 */
	public SVector add(SVector v);

	/**
	 * M�thode permettant d'effectuer la multiplication par un scalaire d'un vecteur
	 * avec un scalaire.
	 * 
	 * @param cst La constante scalaire � multiplier avec le vecteur.
	 * @return Le vecteur r�sultant de la multiplication par un scalaire du vecteur.
	 */
	public SVector multiply(double cst);

	/**
	 * M�thode permettant d'effectuer le <b>produit scalaire</b> entre deux
	 * vecteurs.
	 * 
	 * @param v Le vecteur � mettre en produit scalaire avec le vecteur courant.
	 * @return Le r�sultat du produit scalaire.
	 */
	public double dot(SVector v);

	// ------------
	// M�THODES //
	// ------------

	/**
	 * <p>
	 * M�thode effectuant le calcul de l'interpolation lin�aire entre deux vecteurs
	 * v0 et v1 par le facteur t. L'�quation math�matique correspondant �
	 * l'interpolation est
	 * <ul>
	 * v = v0*(1 - t) + v1*t
	 * </ul>
	 * o� v0 est le vecteur de r�f�rence et v1 est le vecteur pond�r� par le facteur
	 * t.
	 * </p>
	 * 
	 * @param v0 Le 1ier vecteur de r�f�rence pond�r� par 1 - t.
	 * @param v1 Le 2i�me vecteur pond�r� par le facteur t.
	 * @param t  Le param�tre de pond�ration.
	 * @return Le vecteur interpol�.
	 */
	public static SVector linearInterpolation(SVector v0, SVector v1, double t) {
		throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
	}

	/**
	 * <p>
	 * M�thode effectant le calcul de l'interpolation lin�aire en coordonn�e
	 * barycentrique entre trois vecteurs v0, v1 et v2 par le facteur t1 et t2.
	 * L'�quation math�matique correspondant � l'interpolation est
	 * <ul>
	 * v = v0*(1 - t1 - t2) + v1*t1 + v2*t2
	 * </ul>
	 * o� v0 est le vecteur de r�f�rence et v1 est le vecteur pond�r� par le facteur
	 * t1 et v2 est le vecteur pond�r� par le facteur t2.
	 * </p>
	 * 
	 * @param v0 Le 1ier vecteur de r�f�rence pond�r� par 1 - t1 - t2.
	 * @param v1 Le 2i�me vecteur pond�r� par t1.
	 * @param v2 Le 3i�me vecteur pond�r� par t2.
	 * @param t1 Le 1ier param�tre de pond�ration.
	 * @param t2 Le 2i�me param�tre de pond�ration.
	 * @return Le vecteur interpol�.
	 */
	public static SVector linearBarycentricInterpolation(SVector v0, SVector v1, SVector v2, double t1, double t2) {
		
		return v0.multiply(1 - t1 - t2).add(v1.multiply(t1)).add(v2.multiply(t2));
	}

	/**
	 * <p>
	 * M�thode effectant le calcul de l'interpolation lin�aire en coordonn�e
	 * barycentrique entre plusieurs vecteurs v0, v1, ... et v_n par les facteur t1,
	 * t2, ..., t_n. L'�quation math�matique correspondant � l'interpolation est
	 * <ul>
	 * v = v0*(1 - t1 - t2 - ... - t_n) + v1*t1 + v2*t2 + ... + v_n*t_n
	 * </ul>
	 * o� v0 est le vecteur de r�f�rence et les vecteur v_i sont les vecteurs
	 * pond�r�s par les facteurs t_i.
	 * </p>
	 * 
	 * @param vector_list La liste des vecteurs dans l'interpolation. Cette liste
	 *                    contient n+1 vecteur (v0 et les v_i �tant de nombre n).
	 * @param t_list      La liste des facteurs de pond�rations des vecteurs. Cette
	 *                    liste contient n facteurs (les n facteurs des n vecteur
	 *                    v_i).
	 * @return Le vecteur interpol�.
	 * @throws SRuntimeException Si le nombre d'�l�ment des listes n'est pas ad�quat
	 *                           (vector_list.size() != t_list.size()+1).
	 */
	public static SVector linearBarycentricInterpolation(List<SVector> vector_list, List<Double> t_list)
			throws SRuntimeException {
		// V�rifier que les deux listes ont la m�me taille
		if (vector_list.size() != t_list.size() + 1)
			throw new SRuntimeException(
					"Erreur SVectorUtil 007 : L'interpolation est impossible puisque les deux listes pass�es en param�tre n'ont les bonnes tailles.");

		// V�rification que la liste des vecteurs n'est pas vide
		if (vector_list.isEmpty())
			throw new SRuntimeException(
					"Erreur SVectorUtil 008 : L'interpolation est impossible puisque la liste des vecteurs est vide.");

		throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
	}

}// fin de l'interface SVector
