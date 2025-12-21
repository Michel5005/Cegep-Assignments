/**
 * 
 */
package circuit.kirchhof;

import java.util.List;

import circuit.kirchhof.exception.*;

/**
 * La classe <b>SMatrix</b> repr�sente une matrice. Cette classe utilise les fonctionnalit�s de la classe <b>SBufferedMatrix</b>
 * sauf qu'elle est sous une version <u>immuable</u> (les donn�es internes sont non modifiables).
 * 
 * @author Simon Vezina
 * @since 2017-03-11
 * @version 2017-09-26 (labo - Les lois de Kirchhoff v1.0) 
 */
public class SMatrix {

  /**
   * La variable <b>matrix</b> repr�sente les donn�es de la matrice. 
   */
  private final SBufferedMatrix matrix;
  
  /**
   * La variable <b>is_augmented</b> pr�cise si les donn�es � l'int�rieur de la matrice corresponde � la d�finition d'une matrice augment�e.
   */
  private final boolean is_augmented;
  
  //-------------------------
  // CONSTRUCTEUR PUBLIQUE //
  //-------------------------
  
  /**
   * Constructeur d'une matrice à partir d'une liste de données.
   * @param data Les données de la matrice.
   * @throws SInvalidMatrixSizeException Si les données ne permettent pas de construire une matrice valide.
   */
  
  public SMatrix(List<double[]> data) throws SInvalidMatrixSizeException
  {
    this.matrix = new SBufferedMatrix(data);
    this.is_augmented = false;
  }

  /**
   * Constructeur d'une matrice � partir d'un tableau de valeur.
   * Les donn�e pass�s en param�tre seront copi� et ind�pendante sous 
   * 
   * @param data Les donn�es de la matrice.
   * @throws SInvalidMatrixSizeException Si les donn�es ne permettent pas de construire une matrice valide.
   */
  public SMatrix(double[]... data) throws SInvalidMatrixSizeException 
  {
    this(false, data);
  }
  
  /**
   * Constructeur d'une matrice � partir d'un tableau de valeur.
   * Les donn�e pass�s en param�tre seront copi� et ind�pendante sous 
   * 
   * @param data Les donn�es de la matrice.
   * @param isAugmented Pr�cise si les donn�es de la matrice doivent �tre interpr�t�es comme �tant une matrice augment�e.
   * @throws SInvalidMatrixSizeException Si les donn�es ne permettent pas de construire une matrice valide.
   */
  public SMatrix(boolean isAugmented, double[]... data) throws SInvalidMatrixSizeException 
  {
    this.matrix = new SBufferedMatrix(data);
    this.is_augmented = isAugmented;
  }
  
  //----------------------
  // CONSTRUCTEUR PRIV� //
  //----------------------
  
  /**
   * Constructeur d'une matrice en prenant <u>comme r�f�rence</u> un buffer de matrice.
   * 
   * Il est important d'utiliser ce constructeur apr�s la cr�ation interne d'un nouveau buffer de matrice.
   * Puisque la construction d'une SMatrix prot�ge l'acc�s � ses donn�es, il ne faut aucun acc�s � SBufferedMatrix
   * � l'ext�rieur de la matrice nouvellement construite.
   * 
   * @param buffer Le buffer qui sera pris en r�f�rence.
   * @param isAugmented Pr�cise si les donn�es de la matrice doivent �tre interpr�t�es comme �tant une matrice augment�e.
   */
  private SMatrix(SBufferedMatrix buffer, boolean isAugmented)
  {
    this.matrix = buffer;
    this.is_augmented = isAugmented;
  }
  
  //------------
  // M�THODES //
  //------------
  
  /**
   * M�thode pour obtenir la valeur d'une matrice tel que 
   * le 1ier param�tre correspond � la ligne 
   * et le 2i�me param�tre correspond � la colonne.  
   * 
   * @param l Le num�ro de la ligne de la matrice.
   * @param c Le num�ro de la colonne de la matrice.
   * @return La valeur de la matrice � la ligne et � la colonne exig�e.
   * @throws ArrayIndexOutOfBoundsException Si le num�ro de ligne ou de colonne n'est pas admissible dans la matrice.
   */
  public double get(int l, int c) throws ArrayIndexOutOfBoundsException
  {
    return matrix.get(l, c);
  }
  
  /**
   * M�thode pour d�terminier si la matrice est consid�r�e comme une matrice augment�e.
   * Cette nuance est n�cessaire � pr�ciser lorsque l'on veut utiliser une matrice pour r�soudre un syst�me d'�quation lin�aire.
   * 
   * @return <b>true</b> si la matrice est augment�e et <b>false</b> sinon.
   */
  public boolean isAugmented()
  {
    return is_augmented;
  }
  
  /**
   * M�thode pour obtenir le nombre de lignes de la matrice.
   * 
   * @return Le nombre de lignes de la matrice.
   */
  public int getNbLine()
  {
    return matrix.getNbLine();
  }
  
  /**
   * M�thode pour obtenir le nombre de colonne de la matrice.
   * 
   * @return Le nombre de colonnes de la matrice.
   */
  public int getNbColumn()
  {
    return matrix.getNbColumn();
  }
  
  /**
   * M�thode qui g�n�re une nouvelle matrice �tant la matrice augment�e de la pr�c�dente.
   * Si la matrice d'origine �tait <u>non augment�e</u>, la nouvelle sera �tiquet�e <b>augment�e</b>.
   * Si la matrice d'origine �tait <u>augment�e</u>, la nouvelle sera �tiquet�e <b>non augment�e</b>.
   * 
   * @return Une nouvelle matrice augment�e.
   */
  public SMatrix toAugmented()
  {
    // Effectuer une copie du buffer de la matrice.
    SBufferedMatrix m = new SBufferedMatrix(matrix.getData());
    
    // Augmenter la matrice. Elle sera alors "augment�e" ou "d�saugment�e".
    m.toAugmented();
    
    // Retourner une nouvelle matrice dans un �tat "augment�" chang�.
    return new SMatrix(m, !is_augmented);
  }
  
  /**
   * M�thode qui effectue le calcul de la r�duction de Gauss-Jordan de la matrice.
   * 
   * @return Une nouvelle matrice correspondant � la r�duction de Gauss-Jordan de la matrice d'origine.
   * @throws SSingularMatrixException Si durant la r�duction de Gauss-Jordan, on d�termine que la matrice est singuli�re. 
   */
  public SMatrix gaussJordanReduction() throws SSingularMatrixException
  {
    // Effectuer une copie du buffer de la matrice.
    SBufferedMatrix m = new SBufferedMatrix(matrix.getData());
    
    // Modifier le buffer afin d'y r�aliser la r�duction de Gauss-Jordan.
    m.gaussJordanReduction();
    
    // Retourner la matrice r�sultat.
    return new SMatrix(m, is_augmented);
  }
    
  /**
   * <p>
   * M�thode pour r�soudre un syst�me d'�quation lin�aire repr�sent� sous forme matricielle.
   * Les �l�ments de la matrice correspondent aux coefficients des variables des �quations.
   * </p>
   * <p>
   * Par exemple, on peut faire correspondre le syst�me d'�quations
   * <ul> A0x + B0y + C0z + D0 = 0  </ul>
   * <ul> A1x + B1y + C1z + D1 = 0  </ul>
   * <ul> A2x + B2y + C2z + D2 = 0  </ul>
   * � la matrice 3x4 
   * <ul> | A0  B0  C0  D0  | </ul>
   * <ul> | A1  B1  C1  D1  | </ul>
   * <ul> | A2  B2  C2  D2  | </ul> 
   * correspondant au format normal (non augment�). 
   * </p>
   * <p>
   * Si les �quations ne sont pas �gales � <b>z�ro</b>,
   * mais � la valeur -D, on dit alors que la matrice est dans le format <b>augment�</b>
   * ce qui influence le signe des solutions g�n�r�es par l'algorithme.
   * </p>
   * @return La solution au syst�me d'�quations lin�aires.
   * @throws SInvalidMatrixSizeException Si le format de la matrice ne permet pas d'obtenir une solution d�termin�e.
   * @throws SSingularMatrixException Si la matrice est singuli�re.
   */
  public double[] solvingLinearEquationsSystem() throws SInvalidMatrixSizeException, SSingularMatrixException
  {
    // V�rifier si le nombre de ligne et le nombre de colonne de la matrice est ad�quat pour r�soudre le syst�me d'�quation
    if(getNbLine() != getNbColumn() - 1)
      throw new SInvalidMatrixSizeException("Erreur SMatrix 001 : Le nombre de lignes l = " + getNbLine() + " et le nombre de colonnes c = " + getNbColumn() + " ne respectent par la contrainte l = c - 1.");
    
    // G�n�rer une matrice correspondant � la r�duction de Gauss-Jordan.
    SMatrix reduce_matrix = gaussJordanReduction();
     
    //------------------------------------------------------------------------------------------------------------------
    // � VOUS DE MANIPULER l'objet "reduce_matrix" afin d'obtenir les solutions au syst�me d'�quation.
    //
    // Il est important de manipuler "reduce_matrix" avec les m�thodes
    //      reduce_matrix.get(int l, int c)
    //      reduce_matrix.isAugmented()
    // car c'est cette matrice qui contient les r�sultats de la r�duction Gass-Jordan (raison : objet SMatrix immuable).
    //-------------------------------------------------------------------------------------------------------------------
    double[] solutions = new double[reduce_matrix.getNbLine()];
    int colonnes = reduce_matrix.getNbColumn();
    int lignes = reduce_matrix.getNbLine();
    if (reduce_matrix.isAugmented()) {
    	for (int i =0;i<lignes;i++) {
    		solutions[i] = reduce_matrix.get(i,colonnes-1);
    	}
    }
    else {
    	for (int i =0;i<lignes;i++) {
    		solutions[i] = -reduce_matrix.get(i,colonnes-1);
    	}
    }
    return solutions;
  }
  
  //----------------------
  // M�THODE OVERLOADED //
  //----------------------
  
  @Override
  public String toString() 
  {
    return "SMatrix [matrix=" + matrix + "]";
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((matrix == null) ? 0 : matrix.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    
    if (obj == null)
      return false;
    
    if (getClass() != obj.getClass())
      return false;
    
    SMatrix other = (SMatrix) obj;
    
    // V�rfier l'�tat de la matrice (normale ou augment�e).
    if(is_augmented != other.is_augmented)
      return false;
    
    if (matrix == null)
    {
      if (other.matrix != null)
        return false;
    } 
    else 
      if (!matrix.equals(other.matrix))   // v�rifier le contenu de la matrice.
        return false;
      
    return true;
  }
  
}// fin de la classe SMatrix
