/**
 * 
 */
package sim.math;

import sim.exception.SIllegalNegativeValueException;
import sim.exception.SNoImplementationException;
import sim.exception.SRuntimeException;

/**
 * La classe <b>SMath</b> contient des m�thodes de calcul qui sont
 * compl�mentaire � la classe java.lang.Math. Elle perment entre autre d'�valuer
 * les racines r�elles d'un polyn�me de degr� 1, 2, 3 et 4.
 * 
 * @author Simon V�zina
 * @since 2015-02-19
 * @version 2020-01-08 (Version : Le ray tracer v2.107)
 */
public final class SMath {

	/**
	 * La constante <b>NO_ROOT_SOLUTION</b> repr�sente le tableau des solutions au
	 * racine d'un polyn�me o� il n'y a <u>pas de solution r�elle</u>. Le tableau
	 * est donc vide et de taille �gale � z�ro.
	 */
	public final static double[] NO_ROOT_SOLUTION = new double[0];

	/**
	 * <p>
	 * La constante <b>EPSILON</b> repr�sentante un nombre tr�s petit positif, mais
	 * non nul. Ce chiffre peut �tre utilis� pour comparer une valeur de type double
	 * avec le chiffre z�ro. Puisqu'un double �gale � z�ro est difficile � obtenir
	 * num�riquement apr�s un calcul (sauf si l'on multiplie par z�ro), il est
	 * pr�f�rable de comparer un "pseudo z�ro" avec cette constante.
	 * </p>
	 * <p>
	 * Avec une valeur de EPSILON = 1e-10, cette valeur permet de comparer
	 * ad�quatement des nombres autour de '1' avec suffisamment de chiffres
	 * significatifs.
	 * </p>
	 */
	public static double EPSILON = 1e-10;

	/**
	 * La constante <b>EPSILON_RELAXED</b> repr�sente un nombre tr�s petit, mais
	 * mille fois plus grand que EPSILON (1000*EPSILON). Pour des raisons
	 * num�riques, le seuil EPSILON est trop petit et ce nouveau seuil peut �tre
	 * ad�quat.
	 */
	public final static double EPSILON_RELAXED = EPSILON * 1000.0;

	/**
	 * La constante <b>NEGATIVE_EPSILON</b> repr�sentante un nombre tr�s petit, mais
	 * non nul qui est <b>negatif</b>. Ce chiffre peut �tre utilis� pour comparer
	 * une valeur arbiraire de type double avec le chiffre z�ro, mais qui sera
	 * n�gatif. Puisqu'un double �gale � z�ro est difficile � obtenir num�riquement
	 * apr�s un calcul (sauf si l'on multiplie par z�ro), il est pr�f�rable de
	 * comparer un "pseudo z�ro" avec cette constante.
	 */
	public final static double NEGATIVE_EPSILON = -1.0 * EPSILON;

	/**
	 * La constante <b>ONE_PLUS_EPSILON</b> repr�sente une constante �gale � <b>1 +
	 * EPSILON</b> ce qui correspond � un nombre l�g�rement sup�rieur � 1.
	 */
	public final static double ONE_PLUS_EPSILON = 1 + EPSILON;

	/**
	 * La constante <b>ONE_MINUS_EPSILON</b> repr�sente une constant �gale � <b>1 -
	 * EPSILON</b> ce qui correspond � un nombre l�g�rement inf�rieur � 1.
	 */
	public final static double ONE_MINUS_EPSILON = 1 - EPSILON;

	/**
	 * La constante <b>ONE_PLUS_1000EPSILON</b> repr�sente une constante �gale �
	 * <b>1 + 1000*EPSILON</b> ce qui correspond � un nombre l�g�rement sup�rieur �
	 * 1.
	 */
	public final static double ONE_PLUS_1000EPSILON = 1 + 1000 * EPSILON;

	/**
	 * La constante <b>ONE_MINUS_EPSILON</b> repr�sente une constant �gale � <b>1 -
	 * 1000*EPSILON</b> ce qui correspond � un nombre l�g�rement inf�rieur � 1.
	 */
	public final static double ONE_MINUS_1000EPSILON = 1 - 1000 * EPSILON;

	/**
	 * La constante <b>INFINITY</b> repr�sente un nombre positif �gale � l'infini.
	 * Cette valeur est obtenue � partir de la classe java.lang.Double.
	 * 
	 * @see java.lang.Double
	 */
	public final static double INFINITY = Double.POSITIVE_INFINITY;

	/**
	 * M�thode pour d�terminer si deux nombres de type double sont <b>relativement
	 * �gaux</b>. En utilisant une approche de calcul de diff�rence, on v�rifie si
	 * <ul>
	 * a - b < EPSILON*ref
	 * </ul>
	 * afin de <b>valid� l'�galit�</b> entre a et b (a == b). EPSILON est un seuil
	 * de pr�cision et ref est une base de r�f�rence (la valeur absolue la plus
	 * �lev�e parmis a et b).
	 * <p>
	 * Cependant, si les deux chiffres sont inf�rieurs � EPSILON, ils seront
	 * consid�r�s comme �gaux.
	 * </p>
	 * 
	 * @param a Le 1ier nombre � comparer.
	 * @param b Le 2i�me nombre � comparer.
	 * @return <b>true</b> si les deux nombres sont <b>relativement �gaux</b> et
	 *         <b>false</b> sinon.
	 */
	public static boolean nearlyEquals(double a, double b) {
		return nearlyEquals(a, b, EPSILON);
	}

	/**
	 * M�thode pour d�terminer si deux nombres de type double sont <b>relativement
	 * �gaux</b>. En utilisant une approche de calcul de diff�rence, on v�rifie si
	 * <ul>
	 * a - b < EPSILON*ref
	 * </ul>
	 * afin de <b>valid� l'�galit�</b> entre a et b (a == b). EPSILON est un seuil
	 * de pr�cision et ref est une base de r�f�rence (la valeur absolue la plus
	 * �lev�e parmis a et b).
	 * <p>
	 * Cenpendant, si les deux chiffres sont inf�rieurs � EPSILON, ils seront
	 * consid�r�s comme �gaux.
	 * </p>
	 * 
	 * @param a       Le 1ier nombre � comparer.
	 * @param b       Le 2i�me nombre � comparer.
	 * @param epsilon - La pr�cision acceptable.
	 * @return <b>true</b> si les deux nombres sont <b>relativement �gaux</b> et
	 *         <b>false</b> sinon.
	 */
	public static boolean nearlyEquals(double a, double b, double epsilon) {
		double absA = Math.abs(a);
		double absB = Math.abs(b);
		double diff = Math.abs(a - b);

		// V�rification du cas particulier : 0 = 0 et infiny = infiny (mais pas certain
		// ...)
		if (a == b)
			return true;

		// Cas des petites chiffres : V�rifier si les deux chiffres sont tr�s pr�s l'un
		// de l'autre
		if (diff < epsilon)
			return true;

		// Cas g�n�ral
		double positive_max;

		if (absA > absB)
			positive_max = absA;
		else
			positive_max = absB;

		if (diff < positive_max * epsilon)
			return true;
		else
			return false;
	}

	/**
	 * M�thpde pour d�terminer si une valeur est relativement pr�s de z�ro. Cette
	 * m�thode est n�cessaire, car une op�ration math�matique menant au chiffre 0
	 * peut �tre 0.0 et -0.0 ce qui n'est pas �gal selon JAVA.
	 * 
	 * @param value La valeur � comparer avec 0.
	 * @return <b>true</b> si la valeur est <b>relativement �gal</b> � z�ro et
	 *         <b>false</b> sinon.
	 */
	public static boolean nearlyZero(double value) {
		return nearlyEquals(value, 0.0);
	}

	/**
	 * M�thode permettant d'�valuer la racine r�elle d'un polyn�me de degr� '1' de
	 * la forme
	 * <ul>
	 * Ax + B = 0.
	 * </ul>
	 * <p>
	 * Un polyn�me de degr� '1' poss�de au maximum <b>une</b> racine r�elle.
	 * </p>
	 * 
	 * @param A Le coefficient devant le terme de puissance '1' (x).
	 * @param B Le coefficient devant le terme de puissance '0' (1).
	 * @return La racine r�elle d'un polyn�me de degr� '1' (s'il y en a).
	 * @throws SInfinityOfSolutionsException Si le polyn�me contient une infinit� de
	 *                                       solution (0x + 0 = 0).
	 */
	public static double[] linearRealRoot(double A, double B) throws SInfinityOfSolutionsException {
		// V�rifier si le polyn�me n'est pas d'un degr� inf�rieur.
		if (nearlyZero(A)) {
			if (nearlyZero(B)) {
				throw new SInfinityOfSolutionsException("L'équation linéaire 0x + 0 = 0 admet une infinité de solutions.");
			}
			return NO_ROOT_SOLUTION;
		}
		return new double[] {-B/A};
	}

	/**
	 * M�thode permettant d'�valuer les racines r�elles d'un polyn�me de degr� '2'
	 * de la forme
	 * <ul>
	 * Ax^2 + Bx + C = 0.
	 * </ul>
	 * <p>
	 * Un polyn�me de degr� '2' poss�de au maximum <b>deux</b> racines r�elles.
	 * </p>
	 * 
	 * @param A Le coefficient devant le terme de puissance '2' (x^2).
	 * @param B Le coefficient devant le terme de puissance '1' (x).
	 * @param C Le coefficient devant le terme de puissance '0' (1).
	 * @return Les racines r�elles de d'un polyn�me de degr� '2' (s'il y en a). Les
	 *         solutions retourn�es dans un tableau sont <b>tri�es en ordre
	 *         croissant</b>.
	 * @throws SInfinityOfSolutionsException Si le polyn�me contient une infinit� de
	 *                                       solution (0x^2 + 0x + 0 = 0).
	 */
	public static double[] quadricRealRoot(double A, double B, double C) {
		// V�rifier si le polyn�me n'est pas d'un degr� inf�rieur
		double disc = (B * B) - (4 * A * C);
		double[] roots = null;
		if (nearlyZero(A)) {
			roots = linearRealRoot(B, C);
		} else {
			double x1 = (-B + Math.sqrt((B * B) - (4 * A * C))) / (2 * A);
			double x2 = (-B - Math.sqrt((B * B) - (4 * A * C))) / (2 * A);
			int i = 0;
			if (disc < 0.0) {
				i = 0;
				roots = new double[i];
			}
			if (disc == 0.0) {
				i = 1;
				roots = new double[i];
				roots[0] = x1;
			}
			if (disc > 0.0) {
				i = 2;
				roots = new double[i];
				if (x1 < x2) {
					roots[0] = x1;
					roots[1] = x2;
				} else {
					roots[0] = x2;
					roots[1] = x1;
				}
			}
		}
		return roots;

	}

	/**
	 * M�thode permettant d'�valuer les racines r�elles d'un polyn�me de degr� '3'
	 * de la forme
	 * <ul>
	 * Ax^3 + Bx^2 + Cx + D = 0.
	 * </ul>
	 * <p>
	 * Un polyn�me de degr� '3' poss�de au maximum <b>trois</b> racines r�elles.
	 * </p>
	 * 
	 * @param A Le coefficient devant le terme de puissance '3' (x^3).
	 * @param B Le coefficient devant le terme de puissance '2' (x^2).
	 * @param C Le coefficient devant le terme de puissance '1' (x).
	 * @param D Le coefficient devant le terme de puissance '0' (1).
	 * @return Les racines r�elles de d'un polyn�me de degr� '3' (s'il y en a). Les
	 *         solutions retourn�es dans un tableau sont <b>tri�es en ordre
	 *         croissant</b>.
	 * @throws SInfinityOfSolutionsException Si le polyn�me contient une infinit� de
	 *                                       solution (0x^3 + 0x^2 + 0x + 0 = 0).
	 */
	public static double[] cubicRealRoot(double A, double B, double C, double D) throws SInfinityOfSolutionsException {
		// V�rifier si le polyn�me n'est pas d'un degr� inf�rieur
		if (nearlyZero(A))
			return quadricRealRoot(B, C, D);

		throw new SNoImplementationException("Erreur SMath : C'est m�thode n'a pas �t� impl�ment�e.");

	}

	/**
	 * M�thode permettant d'�valuer les racines r�elles d'un polyn�me de degr� '4'
	 * de la forme
	 * <ul>
	 * Ax^4 + Bx^3 + Cx^2 + Dx + E = 0.
	 * </ul>
	 * <p>
	 * Un polyn�me de degr� '4' poss�de au maximum <b>quatre</b> racines r�elles.
	 * </p>
	 * 
	 * @param A Le coefficient devant le terme de puissance '4' (x^4).
	 * @param B Le coefficient devant le terme de puissance '3' (x^3).
	 * @param C Le coefficient devant le terme de puissance '2' (x^2).
	 * @param D Le coefficient devant le terme de puissance '1' (x).
	 * @param E Le coefficient devant le terme de puissance '0' (1).
	 * @return Les racines r�elles de d'un polyn�me de degr� '4' (s'il y en a). Les
	 *         solutions retourn�es dans un tableau sont <b>tri�es en ordre
	 *         croissant</b>.
	 * @throws SInfinityOfSolutionsException Si le polyn�me contient une infinit� de
	 *                                       solution (0x^4 + 0x^3 + 0x^2 + 0x + 0 =
	 *                                       0).
	 */
	public static double[] quarticRealRoot(double A, double B, double C, double D, double E)
			throws SInfinityOfSolutionsException {
		// V�rifier si le polyn�me n'est pas d'un degr� inf�rieur.
		if (nearlyZero(A))
			return cubicRealRoot(B, C, D, E);

		throw new SNoImplementationException("Erreur SMath : C'est m�thode n'a pas �t� impl�ment�e.");

	}

	/**
	 * M�thode qui effectue le calcul inverse de l'interpolation lin�aire d'une
	 * valeur num�rique. Cela sigifie que l'on cherche la valeur du param�tre
	 * d'interpolation t � partir d'une valeur interpol�e ainsi que des deux valeurs
	 * extr�mes.
	 * 
	 * @param v  La valeur interpol�e dont la valeur de t doit �tre calcul�e.
	 * @param v0 La valeur de r�f�rence pond�r�e par 1 - t.
	 * @param v1 La valeur de r�f�rence pond�r�e par le facteur t.
	 * @return La facteur t d'interpolation lin�aire.
	 */
	public static double reverseLinearInterpolation(double v, double v0, double v1) {
		// � partir de la relation v = (1-t)*v0 + t*v1 , on doit isoler t.
		return (v - v0) / (v1 - v0);
	}

	/**
	 * M�thode qui effectue le calcul de l'interpolation lin�aire d'une valeur
	 * num�rique.
	 * 
	 * @param v0 La valeur de r�f�rence pond�r�e par 1 - t.
	 * @param v1 La valeur de r�f�rence pond�r�e par le facteur t.
	 * @param t  Le param�tre de pond�ration.
	 * @return La valeur interpol�e.
	 */
	public static double linearInterpolation(double v0, double v1, double t) {
		// Calcul de l'interpolation : v = v0*(1 - t) + v1*t
		return v0 * (1.0 - t) + (v1 * t);
	}

	/**
	 * M�thode qui effectue le calcul de l'interpolation quadratique d'une valeur
	 * num�rique.
	 * 
	 * @param v0 La valeur de r�f�rence pond�r�e par 1 - t*t.
	 * @param v1 La valeur de r�f�rence pond�r�e par le facteur t*t.
	 * @param t  Le param�tre de pond�ration.
	 * @return La valeur interpol�e.
	 */
	public static double quadricInterpolation(double v0, double v1, double t) {
		// Calcul de l'interpolation : v = v0*(1 - t^2) + v1*t^2
		double t2 = t * t;

		return v0 * (1.0 - t2) + v1 * t2;
	}

	/**
	 * M�thode d�terminant le signe d'un nombre. Les r�sultats sont
	 * <ul>
	 * -1 si a < 0
	 * </ul>
	 * <ul>
	 * 0 si a = 0
	 * </ul>
	 * <ul>
	 * 1 si a > 0
	 * </ul>
	 * 
	 * @param a Le nombre.
	 * @return Le signe du nombre.
	 */
	public static double sgn(double a) {
		if (a > 0)
			return 1.0;
		else if (a < 0)
			return -1.0;
		else
			return 0.0;
	}

	/**
	 * M�thode permettant de g�n�rer un nombre al�atoire entre une valeur minimale
	 * et maximale.
	 * 
	 * @param min La valeur minimale du nombre al�atoire.
	 * @param max La valeur maximale du nombre al�atoire.
	 * @return Le nombre al�atoire.
	 */
	public static double random(double min, double max) {
		return Math.random() * (max - min) + min;
	}

	/**
	 * M�thode permettant de g�n�rer un nombre al�atoire entre une valeur minimale
	 * et maximale.
	 * 
	 * @param min La valeur minimale du nombre al�atoire.
	 * @param max La valeur maximale du nombre al�atoire.
	 * @return Le nombre al�atoire.
	 */
	public static int random(int min, int max) {
		return (int) Math.round((Math.random() * (max - min))) + min;
	}

	/**
	 * M�thode permettant de g�n�rer al�atoirement une valeur vrai ou faux.
	 * 
	 * @return al�atoirement une r�ponse <b>true</b> ou <b>false</b>.
	 */
	public static boolean randomTrueOrFalse() {
		return Math.random() > 0.5;
	}

	/**
	 * M�thode pour g�n�rer un nombre al�atoire appartenant � un tableau de valeurs.
	 * Les choix des �l�ments dans le tableau sont tous �quiprobable.
	 * 
	 * @param possibility Le tableau des valeurs possibles.
	 * @return Une valeur al�atoire appartenant au tableau des possibilit�s.
	 */
	public static int random(int[] possibility) {
		// Choisir un indice du tableau al�atoirement.
		int random_index = (int) (Math.random() * possibility.length);

		return possibility[random_index];
	}

	/**
	 * M�thode pour g�n�rer un nombre al�atoire appartenant � un tableau de valeurs
	 * dont le poids probabiliste n'est pas �quiprobable. La probabilit� de chaque
	 * valeur d�pend d'un poid d�termin� pour chaque valeur. L'association est
	 * effectu�e selon les indices des deux tableaux.
	 * 
	 * @param possibility Le tableau des valeurs possibles.
	 * @param weight      Le tableau des poids du choix al�atoire.
	 * @return Une valeur al�atoire appartenant au tableau des possibilit�s.
	 * @throws SIllegalNegativeValueException Si le tableau des poids contient une
	 *                                        valeur n�gative.
	 */
	public static int random(int[] possibility, double[] weight) throws SIllegalNegativeValueException {
		// V�rification de la longueur des tableaux.
		if (possibility.length != weight.length)
			throw new SRuntimeException("Le tableau possibility poss�de une longueur " + possibility.length
					+ " et le tableau weight poss�de une longueur " + weight.length + " ce qui n'est pas �gal.");

		// �valuer la somme des pond�ratoire.
		double sum = 0.0;

		for (double p : weight)
			if (p < 0.0)
				throw new SIllegalNegativeValueException("Erreur SMath 005 : La valeur n�gative " + p
						+ " n'est pas ad�quate dans le tableau des poids.");
			else
				sum += p;

		// G�n�rer un nombre al�atoire entre 0 et la pond�ratoire total.
		double random = Math.random() * sum;

		// Choisir l'�tat al�atoire pond�r� par le poids des choix.
		int index = 0;

		while (random > weight[index]) {
			random -= weight[index];
			index++;
		}

		// Retourner le choix al�atoire pond�r� par le poids des choix.
		return possibility[index];
	}

}
