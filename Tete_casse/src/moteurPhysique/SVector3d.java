package moteurPhysique;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * La classe <b>SVector3d</b> représente une vecteur <i>x</i>, <i>y</i> et
 * <i>z</i> à trois dimensions.
 * </p>
 * 
 * <p>
 * Les opérations mathématiques supportées sont les suivantes :
 * <ul>
 * - L'addition.
 * </ul>
 * <ul>
 * - La soustraction.
 * </ul>
 * <ul>
 * - La multiplication par un scalaire.
 * </ul>
 * <ul>
 * - Le produit scalaire (<i>dot product</i>).
 * </ul>
 * <ul>
 * - Le produit vectoriel (<i>cross product</i>).
 * </ul>
 * <ul>
 * - La normalisation (vecteur de module 1).
 * </ul>
 * </p>
 * 
 * @author Simon Vezina
 * @since 2014-12-16
 * @version 2017-12-20 (version labo : Le ray tracer v2.1)
 */
public class SVector3d implements Serializable {

	// ---------------
	// CONSTANTES //
	// ---------------

	/**
	 * <p>
	 * La constante <b>EPSILON</b> représentante un nombre très petit positif, mais
	 * non nul. Ce chiffre peut être utilisé
	 * pour comparer une valeur de type double avec le chiffre zéro. Puisqu'un
	 * double égale zéro
	 * est difficile d'obtenir numériquement après un calcul (sauf si l'on multiplie
	 * par zéro), il est préférable de
	 * comparer un "pseudo zéro" avec cette constante.
	 * </p>
	 * <p>
	 * Avec une valeur de EPSILON = 1e-10, cette valeur permet de comparer
	 * adéquatement des nombres autour de '1' avec suffisamment de chiffres
	 * significatifs.
	 * </p>
	 */
	public static double EPSILON = 1e-10;

	/**
	 * La constante 'DEFAULT_COMPONENT' correspond la composante par d�faut des
	 * variables x,y et z du vecteur étant égale à {@value}.
	 */
	private static final double DEFAULT_COMPONENT = 0.0;

	/**
	 * La constant <b>ZERO</b> correspond au vecteur nul correspondant à la
	 * coordonnée (0.0, 0.0, 0.0).
	 */
	public static final SVector3d ZERO = new SVector3d(0.0, 0.0, 0.0);

	/**
	 * La constant <b>UNITARY_X</b> correspond au vecteur unitaire parallèle à l'axe
	 * x correspondant (1.0, 0.0, 0.0).
	 * On utilise également la notation <b><i>i</i></b> pour le repr�senter.
	 */
	public static final SVector3d UNITARY_X = new SVector3d(1.0, 0.0, 0.0);

	/**
	 * La constant <b>UNITARY_Y</b> correspond au vecteur unitaire parallèle à l'axe
	 * y correspondant (0.0, 1.0, 0.0).
	 * On utilise également la notation <b><i>j</i></b> pour le repr�senter.
	 */
	public static final SVector3d UNITARY_Y = new SVector3d(0.0, 1.0, 0.0);

	/**
	 * La constant <b>UNITARY_Z</b> correspond au vecteur unitaire parallèle à l'axe
	 * z correspondant (0.0, 0.0, 1.0).
	 * On utilise également la notation <b><i>k</i></b> pour le représenter.
	 */
	public static final SVector3d UNITARY_Z = new SVector3d(0.0, 0.0, 1.0);

	/**
	 * La constante <b>ORIGIN</b> représente un vecteur origine tant situé à la
	 * coordonnée (0.0, 0.0, 0.0).
	 * Il est égal au vecteur <b>ZERO</b>.
	 */
	public static final SVector3d ORIGIN = ZERO;

	// --------------
	// VARIABLES //
	// --------------

	/**
	 * La variable <b>x</b> correspond à la composante x du vecteur 3d.
	 */
	private final double x;

	/**
	 * La variable <b>y</b> correspond à la composante y du vecteur 3d.
	 */
	private final double y;

	/**
	 * La variable <b>z</b> correspond à la composante z du vecteur 3d.
	 */
	private final double z;

	// -----------------
	// CONSTRUCTEURS //
	// -----------------
	/**
	 * Constructeur représentant un vecteur 3d à l'origine d'un système d'axe xyz.
	 */
	public SVector3d() {
		this(DEFAULT_COMPONENT, DEFAULT_COMPONENT, DEFAULT_COMPONENT);
	}

	/**
	 * Constructeur avec composante <i>x</i>,<i>y</i> et <i>z</i> du vecteur 3d.
	 * 
	 * @param x La composante <i>x</i> du vecteur.
	 * @param y La composante <i>y</i> du vecteur.
	 * @param z La composante <i>z</i> du vecteur.
	 */
	public SVector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// ------------
	// M�THODES //
	// ------------

	/**
	 * Méthode qui donne accès à la coordonnée x du vecteur.
	 * 
	 * @return La coordonnée x.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Méthode qui donne accès à la coordonnée y du vecteur.
	 * 
	 * @return La coordonnée y.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Méthode qui donne accès à la coordonnée z du vecteur.
	 * 
	 * @return La coordonnée z.
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Méthode qui calcule <b>l'addition</b> de deux vecteurs.
	 * 
	 * @param v Le vecteur à ajouter.
	 * @return La somme des deux vecteurs.
	 */
	public SVector3d add(SVector3d v) {
		return new SVector3d(x + v.x, y + v.y, z + v.z);
	}

	/**
	 * Méthode qui calcul la <b>soustraction</b> de deux vecteurs.
	 * 
	 * @param v - Le vecteur à soustraire au vecteur présent.
	 * @return La soustraction des deux vecteurs.
	 */
	public SVector3d substract(SVector3d v) {
		return new SVector3d(x - v.x, y - v.y, z - v.z);
	}

	/**
	 * Méthode qui effectue la <b>multiplication d'un vecteur par une scalaire</b>.
	 * 
	 * @param m Le scalaire.
	 * @return La multiplication d'un vecteur par un scalaire.
	 */
	public SVector3d multiply(double m) {
		return new SVector3d(m * x, m * y, m * z);
	}

	/**
	 * Méthode pour obtenir le <b>module</b> d'un vecteur.
	 * 
	 * @return Le module du vecteur.
	 */
	public double modulus() {
		return Math.sqrt((x * x) + (y * y) + (z * z));
	}

	/**
	 * Méthode pour <b>normaliser</b> un vecteur à trois dimensions.
	 * Un vecteur normalisé possède la même orientation, mais possède une <b>
	 * longeur unitaire </b>.
	 * Si le <b>module du vecteur est nul</b>, le vecteur normalisé sera le <b>
	 * vecteur nul </b> (0.0, 0.0, 0.0).
	 * 
	 * @return Le vecteur normalisé.
	 * @throws SImpossibleNormalizationException Si le vecteur ne peut pas être
	 *                                           normalisé étant trop petit
	 *                                           (modulus() < SMath.EPSILON) ou de
	 *                                           longueur nulle.
	 */
	public SVector3d normalize() {

		// Obtenir le module du vecteur
		double mod = modulus();

		// Vérification du module. S'il est trop petit, nous ne pouvons pas
		// numériquement normaliser ce vecteur
		if (mod < 0)
			throw new SImpossibleNormalizationException("Erreur SVector3d 002 : Le vecteur " + this.toString()
					+ " �tant nul ou pr�sque nul ne peut pas �tre num�riquement normalis�.");
		else
			return new SVector3d(x / mod, y / mod, z / mod);
	}

	/**
	 * Méthode pour effectuer le <b>produit scalaire</b> entre deux vecteurs.
	 * 
	 * @param v Le vecteur à mettre en produit scalaire.
	 * @return Le produit scalaire entre les deux vecteurs.
	 */
	public double dot(SVector3d v) {
		return (x * v.x + y * v.y + z * v.z);
	}

	/**
	 * Méthode pour effectuer le <b>produit vectoriel</b> avec un autre vecteur v.
	 * <p>
	 * Cette version du produit vectoriel est implémenté en respectant la <b> règle
	 * de la main droite </b>.
	 * Il est important de rappeler que le produit vectoriel est <b> anticommutatif
	 * </b> (AxB = -BxA) et que l'ordre des vecteurs doit être adéquat pour obtenir
	 * le résultat désiré.
	 * Si l'un des deux vecteurs est <b> nul </b> ou les deux vecteurs sont <b>
	 * parallèles </b>, le résultat sera un <b> vecteur nul </b>.
	 * </p>
	 * 
	 * @param v Le second vecteur dans le produit vectoriel.
	 * @return Le résultat du produit vectoriel.
	 */
	public SVector3d cross(SVector3d v) {
		return new SVector3d(y * v.z - z * v.y,
				-1 * (x * v.z - z * v.x),
				x * v.y - y * v.x);
	}

	/**
	 * Méthode pour obtenir un vecteur avec les coordonn�e (x,y,z) les plus petites
	 * (en considérant le signe) parmi un ensemble de vecteurs.
	 * 
	 * @param value_list La liste des vecteurs à analyser.
	 * @return Un vecteur ayant comme coordonnée les plus petites valeurs (x,y,z)
	 *         trouvées.
	 */
	public static SVector3d findMinValue(List<SVector3d> value_list) {
		return findMinValue(value_list.toArray(new SVector3d[value_list.size()]));
	}

	/**
	 * Méthode pour obtenir un vecteur avec les coordonn�e (x,y,z) les plus petites
	 * (en considérant le signe) parmi un ensemble de vecteurs.
	 * 
	 * @param tab - Le tableau des vecteurs à analyser.
	 * @return Un vecteur ayant comme coordonnée les plus petites valeurs (x,y,z)
	 *         trouvées.
	 */
	public static SVector3d findMinValue(SVector3d[] tab) {
		double x_min = tab[0].getX();

		for (int i = 1; i < tab.length; i++)
			if (x_min > tab[i].getX())
				x_min = tab[i].getX();

		double y_min = tab[0].getY();

		for (int i = 1; i < tab.length; i++)
			if (y_min > tab[i].getY())
				y_min = tab[i].getY();

		double z_min = tab[0].getZ();

		for (int i = 1; i < tab.length; i++)
			if (z_min > tab[i].getZ())
				z_min = tab[i].getZ();

		return new SVector3d(x_min, y_min, z_min);
	}

	/**
	 * Méthode pour obtenir un vecteur avec les coordonnée (x,y,z) les plus grandes
	 * (en considérant le signe) parmi un ensemble de vecteurs.
	 * 
	 * @param value_list La liste des vecteurs à analyser.
	 * @return Un vecteur ayant comme coordonnée les plus grandes valeurs (x,y,z)
	 *         trouvées.
	 */
	public static SVector3d findMaxValue(List<SVector3d> value_list) {
		return findMaxValue(value_list.toArray(new SVector3d[value_list.size()]));
	}

	/**
	 * Méthode pour obtenir un vecteur avec les coordonnée (x,y,z) les plus grandes
	 * (en considérant le signe) parmi un ensemble de vecteurs.
	 * 
	 * @param tab - Le tableau des vecteurs à analyser.
	 * @return Un vecteur ayant comme coordonnée les plus grandes valeurs (x,y,z)
	 *         trouvées.
	 */
	public static SVector3d findMaxValue(SVector3d[] tab) {
		double x_max = tab[0].getX();

		for (int i = 1; i < tab.length; i++)
			if (x_max < tab[i].getX())
				x_max = tab[i].getX();

		double y_max = tab[0].getY();

		for (int i = 1; i < tab.length; i++)
			if (y_max < tab[i].getY())
				y_max = tab[i].getY();

		double z_max = tab[0].getZ();

		for (int i = 1; i < tab.length; i++)
			if (z_max < tab[i].getZ())
				z_max = tab[i].getZ();

		return new SVector3d(x_max, y_max, z_max);
	}

	/**
	 * Méthode pour �crire les paramètres xyz du vecteur dans un fichier en format
	 * txt. Le format de l'�criture est "x" "y" "z" comme l'exemple suivant : 0.6
	 * 0.2 0.5
	 * 
	 * @param bw Le buffer d'�criture.
	 * @throws IOException S'il y a une erreur avec le buffer d'�criture.
	 * @see BufferedWriter
	 */
	public void write(BufferedWriter bw) throws IOException {
		bw.write(toString());
	}

	// ----------------------------------------------------------------------------------------------
	// Méthodes pour calcul spécialisé (afin de réduire l'allocation de mémoire lors
	// des calculs)
	// ----------------------------------------------------------------------------------------------

	/**
	 * Méthode pour obtenir la distance entre deux points.
	 * 
	 * @param A Le premier point.
	 * @param B Le deuxième point.
	 * @return La distance entre le point A et B.
	 */
	public static double distance(SVector3d A, SVector3d B) {
		throw new SNoImplementationException("La m�thode n'est pas impl�ment�e.");
	}

	/**
	 * Méthode permettant d'obtenir l'angle entre deux vecteurs A et B. L'angle sera
	 * obtenu en radian.
	 * 
	 * @param A Le premier vecteur.
	 * @param B Le second vecteur.
	 * @return L'angle entre les deux vecteurs.
	 */
	public static double angleBetween(SVector3d A, SVector3d B) {
		double cosO = A.dot(B) / (A.modulus() * B.modulus());

		return Math.acos(cosO);
	}

	// ----------------------
	// M�THODE OVERLOADED //
	// ----------------------

	/**
	 * 
	 */
	public String toString() {
		return "[" + String.format("%." + 3 + "f", x) + "\n, " + String.format("%." + 3 + "f", y) + "\n, "
				+ String.format("%." + 3 + "f", z) + "]";
	}

	/**
	 * Méthode qui retourne si un objet est égal à un autre
	 * 
	 * @return
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof SVector3d))
			return false;

		SVector3d other = (SVector3d) obj;

		// Comparaison des valeurs x,y et z en utilisant la m�thode de comparaison de
		// SMath
		if (nearlyEquals(x, other.x))
			return false;

		if (nearlyEquals(y, other.y))
			return false;

		if (nearlyEquals(z, other.z))
			return false;

		return true;
	}

	/**
	 * Méthode qui compare deux valeurs et retourne le résultat de la comparaison
	 * 
	 * @param a première valeur à comparer
	 * @param b deuxième valeur à comparer
	 * @return le résultat de la comparaison entre les deux valeurs
	 */
	public static boolean nearlyEquals(double a, double b) {
		double absA = Math.abs(a);
		double absB = Math.abs(b);
		double diff = Math.abs(a - b);

		// Vérification du cas particulier : 0 = 0 et infinty = infinty (mais pas
		// certain ...)
		if (a == b)
			return true;

		// Cas des petites chiffres : Vérifier si les deux chiffres sont très près l'un
		// de l'autre
		if (diff < EPSILON)
			return true;

		// Cas général
		double positive_max;

		if (absA > absB)
			positive_max = absA;
		else
			positive_max = absB;

		if (diff < positive_max * EPSILON)
			return true;
		else
			return false;
	}

}// fin de la classe SVector3d
