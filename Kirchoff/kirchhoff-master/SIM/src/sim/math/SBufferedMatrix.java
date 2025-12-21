/**
 * 
 */
package sim.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sim.exception.SNoImplementationException;
import sim.math.SMath;
import sim.util.SArrays;

/**
 * La classe <b>SBufferedMatrix</b> un buffer de matrice. Le contenu de cette
 * matrice sera transform�e selon les op�rations qui lui seront appliqu�es. Il
 * est alors inportant de copier l'�tat de la matrice si l'on d�sire garder son
 * contenu d'origine ou bien utiliser la classe <b>SMatrix</b> qui est immuable.
 * 
 * @author Simon Vezina
 * @since 2017-03-11
 * @version 2018-10-18 (version labo v1.0.4 : Les lois de Kirchhoff)
 */
public class SBufferedMatrix {

	/**
	 * La variable <b>data</b> représente l'information contenu dans la matrice. La
	 * première référence sera la ligne, la deuxième sera la colonne.
	 */
	private final double[][] data;

	/**
	 * La variable <b>nb_line</b> repr�sente le nombre de lignes contenus dans la
	 * matrice.
	 */
	private final int nb_line;

	/**
	 * La variable <b>nb_column</b> représente le nombre de colonnes contenus dans
	 * la matrice.
	 */
	private final int nb_column;

	// ----------------
	// CONSTRUCTEUR //
	// ----------------

	/**
	 * Constructeur d'un buffer de matrice vide ayant un nombre de lignes et de
	 * colonnes déterminées.
	 * 
	 * @param nb_line   Le nombre de ligne dans la matrice.
	 * @param nb_column Le nombre de colonne dans la matrice.
	 * @throws SInvalidMatrixSizeException Si la matrice est d�finie avec un nombre
	 *                                     de ligne ou de colonne invalide.
	 */
	public SBufferedMatrix(int nb_line, int nb_column) throws SInvalidMatrixSizeException {
		if (nb_line < 0)
			throw new SInvalidMatrixSizeException(
					"Erreur SBufferedMatrix 001 : La matrice contient un nombre n�gatif de ligne.");

		if (nb_column < 0)
			throw new SInvalidMatrixSizeException(
					"Erreur SBufferedMatrix 002 : La matrice contient un nombre n�gatif de colonne.");

		this.nb_line = nb_line;
		this.nb_column = nb_column;

		// Faire l'allocation de m�moire � la dimension des lignes.
		data = memoryAllocation(nb_line, nb_column);
	}

	/**
	 * Constructeur d'un buffer de matrice. Les informations pass�es dans le
	 * constructeur seront copi�es. Ainsi, une modification des donn�es par des
	 * op�rations math�matiques apport�es � l'objet <b>SBufferedMatrix</b> n'aura
	 * pas d'effet sur les objets utilis�es lors de l'appel de ce constructeur.
	 * 
	 * @param matrix Les informations � indroduire dans le buffer de matrice.
	 * @throws SInvalidMatrixSizeException Si le nombre de colonnes n'est pas unique
	 *                                     pour l'ensemble des lignes de la matrice.
	 * @throws SInvalidMatrixSizeException Si la matrice ne contient pas de ligne.
	 */
	public SBufferedMatrix(double[]... matrix) throws SInvalidMatrixSizeException {
		if (matrix.length == 0)
			throw new SInvalidMatrixSizeException("Erreur SBufferedMatrix 003 : La matrice ne contient pas de ligne.");

		// �tablir le nombre de colonnes et le nombre de lignes officielles de la
		// matrice.
		this.nb_line = matrix.length;
		this.nb_column = matrix[0].length;

		// V�rification du nombre de colonne dans chaque ligne
		for (int l = 0; l < nb_line; l++)
			if (matrix[l].length != nb_column)
				throw new SInvalidMatrixSizeException(
						"Erreur SBufferedMatrix 004 : La ligne " + l + " contient " + matrix[l].length
								+ " colonnes ce qui n'est pas en accord avec la d�finition de la matrice �tant de "
								+ nb_column + " colonnes.");

		// Faire l'allocation de m�moire � la dimension des lignes.
		data = memoryAllocation(nb_line, nb_column);

		// Effectuer la copie des donn�es des matrices.
		copyData(matrix, data, nb_line, nb_column);
	}

	/**
	 * Constructeur d'un buffer de matrice. Les informations pass�es dans le
	 * constructeur seront copi�es. Ainsi, une modification des donn�es par des
	 * op�rations math�matiques apport�es � l'objet <b>SBufferedMatrix</b> n'aura
	 * pas d'effet sur les objets utilis�es lors de l'appel de ce constructeur.
	 * 
	 * @param matrix Les informations � indroduire dans le buffer de matrice.
	 * @throws SInvalidMatrixSizeException Si le nombre de colonnes n'est pas unique
	 *                                     pour l'ensemble des lignes de la matrice.
	 * @throws SInvalidMatrixSizeException Si la matrice ne contient pas de ligne.
	 */
	public SBufferedMatrix(List<double[]> eq_list) throws SInvalidMatrixSizeException {
		// V�rifier que la liste d'�quation n'est pas vide.
		if (eq_list.size() == 0)
			throw new SInvalidMatrixSizeException("Erreur SBufferedMatrix 005 : La matrice ne contient pas de ligne.");

		// �tablir le nombre de colonnes et le nombre de lignes officielles de la
		// matrice.
		nb_line = eq_list.size();
		nb_column = eq_list.get(0).length;

		// V�rification du nombre de colonne dans chaque ligne
		for (int l = 0; l < nb_line; l++)
			if (eq_list.get(l).length != nb_column)
				throw new SInvalidMatrixSizeException(
						"Erreur SBufferedMatrix 006 : La ligne " + l + " contient " + eq_list.get(l).length
								+ " colonnes ce qui n'est pas en accord avec la d�finition de la matrice �tant de "
								+ nb_column + " colonnes.");

		// Faire l'allocation de m�moire � la dimension des lignes.
		data = memoryAllocation(nb_line, nb_column);

		// Effectuer la copie de l'information.
		copyData(eq_list, data, nb_line, nb_column);
	}

	// ------------
	// M�THODES //
	// ------------

	@Override
	public SBufferedMatrix clone() {
		return new SBufferedMatrix(this.data);
	}

	/**
	 * M�thode pour obtenir l'ensemble des donn�es de la matrice.
	 * 
	 * @return Les donn�es de la matrice.
	 */
	public double[][] getData() {
		return data;
	}

	/**
	 * M�thode pour obtenir le nombre de lignes de la matrice.
	 * 
	 * @return Le nombre de lignes de la matrice.
	 */
	public int getNbLine() {
		return this.nb_line;
	}

	/**
	 * M�thode pour obtenir le nombre de colonne de la matrice.
	 * 
	 * @return Le nombre de colonnes de la matrice.
	 */
	public int getNbColumn() {
		return this.nb_column;
	}

	/**
	 * M�thode pour obtenir la valeur d'une matrice. L'indexage se fait par une
	 * r�f�rence � la ligne ainsi qu'� une colonne.
	 * 
	 * @param l Le num�ro de la ligne de la matrice.
	 * @param c Le num�ro de la colonne de la matrice.
	 * @return La valeur de la matrice � la ligne et � la colonne exig�e.
	 * @throws ArrayIndexOutOfBoundsException Si le num�ro de ligne ou de colonne
	 *                                        n'est pas admissible dans la matrice.
	 */
	public double get(int l, int c) throws ArrayIndexOutOfBoundsException {
		return this.data[l][c];
	}

	/**
	 * M�thode pour obtenir acc�s � une ligne de la matrice. Effectuer une op�ration
	 * math�matique sur le contenu de ce tableau influencera le contenu de la
	 * matrice.
	 * 
	 * @param l La ligne � obtenir
	 * @return Une ligne de la matrice.
	 * @throws ArrayIndexOutOfBoundsException Si le num�ro de ligne n'est pas
	 *                                        admissible dans la matrice.
	 */
	public double[] getLine(int l) throws ArrayIndexOutOfBoundsException {
		return data[l];
	}

	/**
	 * M�thode pour obtenir la liste de toutes les lignes de la matrice.
	 * 
	 * @return La liste des lignes de la matrice.
	 */
	public List<double[]> getLineList() {
		List<double[]> list = new ArrayList<double[]>();

		for (int i = 0; i < getNbLine(); i++)
			list.add(getLine(i));

		return list;
	}

	/**
	 * Méthode pour obtenir le nombre de pivots disponibles dans la matrice.
	 * 
	 * @return Le nombre de pivot de la matrice.
	 */
	public int getNbPivot() {
		int nbPivot;
		int nbPivotL = 0;
		int nbPivotC = 0;

		for (int i = 0; i < this.nb_line; i++) {
			for (int j = 0; j < this.nb_column; j++) {
				if(get(i, j) != 0) {
					nbPivotL ++;
					break;
				}
			}
		}
		for (int i = 0; i < this.nb_column; i++) {
			for (int j = 0; j < this.nb_line; j++) {
				if(get(j, i) != 0) {
					nbPivotC ++;
					break;
				}
			}
		}
		
		if(nbPivotL < nbPivotC) {
			nbPivot = nbPivotL;
		}
		else {
			nbPivot = nbPivotC;
		}

		return nbPivot;
	}

	/**
	 * Méthode pour modifier le contenu d'une cellule de la matrice.
	 * 
	 * @param l     Le numéro de la ligne de la matrice.
	 * @param c     Le numéro de la colonne de la matrice.
	 * @param value La valeur à changer dans la matrice.
	 * @throws ArrayIndexOutOfBoundsException Si le numéro de ligne ou de colonne
	 *                                        n'est pas admissible dans la matrice.
	 */
	public void set(int l, int c, double value) throws ArrayIndexOutOfBoundsException {
		data[l][c] = value;
	}

	/**
	 * Méthode pour affecter une nouvelle ligne à la matrice.
	 * 
	 * @param l    L'indice de la ligne change par la nouvelle ligne.
	 * @param line La nouvelle ligne de la matrice.
	 * @throws SInvalidMatrixSizeException    Si la nouvelle ligne introduite dans
	 *                                        la matrice n'a pas le bon nombre de
	 *                                        colonne.
	 * @throws ArrayIndexOutOfBoundsException Si le num�ro de ligne n'est pas
	 *                                        admissible dans la matrice.
	 */
	public void setLine(int l, double[] line) throws SInvalidMatrixSizeException, ArrayIndexOutOfBoundsException {
		// V�rifier que la nouvelle ligne introduite contient le bon nombre de colonne
		if (line.length != nb_column)
			throw new SInvalidMatrixSizeException(
					"Erreur SBufferedMatrix 007 : La nouvelle ligne affect�e � la matrice contient " + line.length
							+ " colonnes ce qui n'est pas en accord avec la d�finition de la matrice �tant de "
							+ nb_column + " colonnes.");

		data[l] = line;
	}

	/**
	 * M�thode pour faire la permutation d'une ligne avec une autre dans la matrice.
	 * 
	 * @param l1 La 1er ligne � permutter.
	 * @param l2 La 2e ligne � permutter.
	 * @throws ArrayIndexOutOfBoundsException Si le num�ro de ligne n'est pas
	 *                                        admissible dans la matrice.
	 */
	public void swapLine(int l1, int l2) {

		double[] temp = getLine(l1);
		setLine(l1, getLine(l2));
		setLine(l2, temp);

	}

	/**
	 * Méthode pour mettre à zéro tous les termes de la matrice.
	 */
	public void clear() {
		for (int i = 0; i < nb_line; i++)
			for (int j = 0; j < nb_column; j++)
				data[i][j] = 0.0;
	}

	/**
	 * M�thode pour ajouter une valeur � une position sp�cifier dans la matrice.
	 * 
	 * @param l     L'index de la ligne.
	 * @param c     L'index de la colonne.
	 * @param value La valeur ajout�e.
	 */
	public void add(int l, int c, double value) {
		double v = get(l, c) + value;
		set(l, c, v);
	}

	/**
	 * M�thode pour faire l'addition de deux lignes d'une matrice et d'introduire le
	 * résultat en remplaçant une ligne existante par celle calculée.
	 * 
	 * @param A        Multiplication appliquée à la ligne 1 avant l'addition.
	 * @param l1       L'index de la ligne 1.
	 * @param B        Multiplicateur appliquée à la ligne 2 avant l'addition.
	 * @param l2       L'index de la ligne 2.
	 * @param l_result L'indexe de la ligne o� sera introduit le r�sultat de
	 *                 l'addition des deux lignes. Cette indexe <u>peut �tre
	 *                 �gal</u> � <b>l1</b> ou <b>l2</b>.
	 * @throws ArrayIndexOutOfBoundsException Si le num�ro de ligne n'est pas
	 *                                        admissible dans la matrice.
	 */
	public void addLines(double A, int l1, double B, int l2, int l_result) {

		for (int i = 0; i < nb_column; i++) {
			double tempA = get(l1, i);
			tempA = A * tempA;

			double tempB = get(l2, i);
			tempB = B * tempB;

			double tempR = get(l_result, i);
			tempR = tempA + tempB;

			set(l_result, i, tempR);
		}
	}

	/**
	 * M�thode pour faire la multiplication d'une ligne par une scalaire. Le
	 * r�sultat de l'op�ration sera affect� sur la ligne courante.
	 * 
	 * @param A Le scalaire en multiplication avec la ligne.
	 * @param l L'index de la ligne qui va subir la multiplication par un scalaire.
	 * @throws ArrayIndexOutOfBoundsException Si le num�ro de ligne n'est pas
	 *                                        admissible dans la matrice.
	 */
	public void multiplyLine(double A, int l) {
		double[] temp = getLine(l);
		double[] produit = new double[this.nb_column];
		for (int i = 0; i < this.nb_column; i++) {
			produit[i] = A * temp[i];
		}
		setLine(l, produit);
	}

	/**
	 * Méthode pour effectuer la réduction de Gauss-Jordan à la matrice. Le résultat
	 * sera interrompu si la matrice est singulière.
	 * 
	 * @throws SSingularMatrixException Si durant la réduction de Gauss-Jordan, on
	 *                                  détermine que la matrice est singulière. La
	 *                                  matrice sera alors laiss�e dans un <u>état
	 *                                  invalide</u> de réduction Gauss-Jordan
	 *                                  puisque l'opération aura été interrompue.
	 */
	public void gaussJordanReduction() throws SSingularMatrixException {

		int pivot = getNbPivot();
		int k;

		for (int i = 0; i < pivot; i++) {
			k = findLineWithMaxValueForPivot(i);
			swapLine(k, i);

			if (SMath.nearlyZero(get(i, i))) {
				throw new SSingularMatrixException("La matrice est singulière! ");

			} else {
				gaussJordanColumnReduction(i);
			}
		}
	}

	/**
	 * Méthode qui effectue la réduction de Gauss-Jordan sur une colonne <u>lançant
	 * uniquement</u> la valeur 1 à la position du pivot.
	 * 
	 * @param pivot La position du pivot dans la réduction de type Gauss-Jordan.
	 * @throws ArrayIndexOutOfBoundsException Si le numéro du pivot n'est pas
	 *                                        admissible dans la matrice.
	 */
	public void gaussJordanColumnReduction(int pivot) {
		
		double nbTemp = get(pivot, pivot);

		multiplyLine( (1/nbTemp), pivot);

		for (int i = 0; i < this.nb_line; i++) {
			if (i != pivot) {
				double c = get(i, pivot);
				addLines(1, i, -c, pivot, i);
			}
		}
	}

	/**
	 * Méthode pour <b>augmenter</b> la matrice. Cette opération consiste à inverser
	 * le signe de la dernière colonne ce qui transforme les lignes de la matrice de
	 * la forme
	 * <ul>
	 * A*x1 + B*x2 + C*x3 + ... + Z = 0
	 * </ul>
	 * dans la forme
	 * <ul>
	 * A*x1 + B*x2 + C*x3 + ... = -Z
	 * </ul>
	 * 
	 * @return La matrice augment�e (comparativement � sa version pr�c�dente).
	 */
	public void toAugmented() {
		// Faire l'it�ration des lignes et changer le signe de la donn�e de la derni�re
		// colonne.
		for (int l = 0; l < nb_line; l++)
			data[l][nb_column - 1] = -1 * data[l][nb_column - 1];
	}

	/**
	 * Méthode pour obtenir l'indice de la ligne ayant la plus grande valeur (en
	 * valeur absolue) à la position du pivot. La recherche va s'effectuer
	 * uniquement chez les lignes dont l'indice est supérieur à l'indice du pivot
	 * (donc ligne inférieur).
	 * 
	 * @param pivot L'indice du pivot.
	 * @return L'indice de la ligne ayant la plus grande valeur (en valeur absolue)
	 *         à la position du pivot.
	 * @throws ArrayIndexOutOfBoundsException Si le numéro du pivot n'est pas
	 *                                        admissible dans la matrice.
	 */
	public int findLineWithMaxValueForPivot(int pivot) {
		int ligneMax = pivot;
		double courant;
		double max = Math.abs(get(pivot, pivot));

		for (int i = pivot+1; i < this.nb_line; i++) {
			courant = Math.abs(get(i, pivot));
			if (courant > max) {
				max = courant;
				ligneMax = i;
			}
		}
		
		return ligneMax;
	}

	// ----------------------
	// MÉTHODES STATIQUES //
	// ----------------------

	/**
	 * Méthode pour générer une matrice identité d'une dimension quelconque.
	 * 
	 * @param size La dimension de la matrice identité.
	 * @return Une matrice identité.
	 */
	public static SBufferedMatrix identity(int size) {
		SBufferedMatrix buffer = new SBufferedMatrix(size, size);

		for (int i = 0; i < buffer.getNbLine(); i++)
			buffer.set(i, i, 1.0);

		return buffer;
	}

	/**
	 * M�thode pour construire un vecteur � partir d'un tableau de valeur. Il est
	 * important de pr�ciser qu'un vecteur correspond � une matrice � n lignes et 1
	 * colonne.
	 * 
	 * @param v Les valeurs du vecteur.
	 * @return Le vecteur (matrice � une colonne) associ� aux donn�es.
	 */
	public static SBufferedMatrix columnVector(double[] v) {
		// Construire une matrice � une colonne.
		SBufferedMatrix buffer = new SBufferedMatrix(v.length, 1);

		// Remplir la matrice � une colonne.
		for (int i = 0; i < v.length; i++)
			buffer.set(i, 0, v[i]);

		return buffer;
	}

	// ------------------------
	// M�THODES UTILITAIRES //
	// ------------------------

	/**
	 * M�thode pour faire l'allocation de la m�moire � la matrice.
	 * 
	 * @param nb_line   Le nombre de ligne de la matrice.
	 * @param nb_column Le nombre de colonne de la matrice.
	 * @return Le tableau � deux dimensions correspondant � l'allocation de la
	 *         m�moire de la matrice.
	 */
	private double[][] memoryAllocation(int nb_line, int nb_column) {
		// Faire l'allocation de m�moire � la dimension des lignes.
		double[][] m = new double[nb_line][];

		// Faire l'allocation de m�moire � la dimension des colonnes.
		for (int l = 0; l < nb_line; l++)
			m[l] = new double[nb_column];

		return m;
	}

	/**
	 * M�thode pour effectuer la copy des donn�es d'une matrice � une autre.
	 * 
	 * @param data      La matrice � copier.
	 * @param copy      La matrice copi�e.
	 * @param nb_line   Le nombre de ligne des matrices.
	 * @param nb_column Le nombre de colonne des matrices.
	 */
	private void copyData(double[][] data, double[][] copy, int nb_line, int nb_column) {
		// Effectuer la copie de l'information.
		for (int l = 0; l < nb_line; l++)
			for (int c = 0; c < nb_column; c++)
				copy[l][c] = data[l][c];
	}

	/**
	 * M�thode pour effectuer la copy des donn�es d'une matrice � une autre.
	 * 
	 * @param data      La liste de tableau correspondant � la matrice � copier.
	 * @param copy      La matrice copi�e.
	 * @param nb_line   Le nombre de ligne des matrices.
	 * @param nb_column Le nombre de colonne des matrices.
	 */
	private void copyData(List<double[]> data, double[][] copy, int nb_line, int nb_column) {
		// Effectuer la copie de l'information.
		for (int l = 0; l < nb_line; l++)
			for (int c = 0; c < nb_column; c++)
				copy[l][c] = data.get(l)[c];
	}

	// ----------------------
	// M�THODE OVERLOADED //
	// ----------------------

	@Override
	public String toString() {
		String expression = "\n"; // d�buter avec un saut de ligne

		for (int l = 0; l < getNbLine(); l++)
			for (int c = 0; c < getNbColumn(); c++) {
				if (c == 0)
					expression = expression.concat("|  ");

				expression = expression.concat(Double.toString(get(l, c)) + "  ");

				if (c == getNbColumn() - 1)
					expression = expression.concat("|\n"); // avec saut de ligne
			}

		return "SBufferedMatrix [data = " + expression + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + nb_column;
		result = prime * result + nb_line;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		SBufferedMatrix other = (SBufferedMatrix) obj;

		if (nb_column != other.nb_column)
			return false;

		if (nb_line != other.nb_line)
			return false;

		// Comparaison des valeurs � l'int�rieur de la matrice.
		// Nous allons it�rer sur l'ensemble des lignes et v�rifier si les tableaux (�
		// 1D) des lignes sont "nearlyEquals".
		// � cet �tape, nous avons la garantie que les deux SBufferedMatrix � comparer
		// ont les m�me dimensions.
		for (int l = 0; l < nb_line; l++)
			if (!SArrays.nearlyEquals(getLine(l), other.getLine(l), SMath.EPSILON))
				return false;

		return true;
	}

}// fin de la classe SBufferedMatrix
