/**
 * 
 */
package sim.math.field;

import sim.exception.SConstructorException;
import sim.math.SVector3d;

/**
 * La classe <b>SVoxelVectorField</b> représente un champ de vecteur. Le champ de vecteur représentera une région de forme cubique.
 * 
 * @author Simon Vézina
 * @since 2018-03-23
 * @version 2022-01-24
 */
public class SVoxelVectorField {

  private static final int X = 0;
  private static final int Y = 1;
  private static final int Z = 2;
 
  /**
   * La variable <b>dl</b> représente l'élément de longueur entre deux éléments de la grille régulière.
   */
  private final double dl;
 
  /**
   * ...
   */
  private final double max_position;
  
  /**
   * ...
   */
  private final double min_position;
  
  /**
   * ...
   */
  private final SVector3d[][][] field;
  
  /**
   * 
   * @param max
   * @param N
   */
  public SVoxelVectorField(double half_size, int N) throws SConstructorException
  {
    if(half_size <= 0)
      throw new SConstructorException();
    
    if(N < 2)
      throw new SConstructorException();
    
    this.max_position = half_size;
    this.min_position = -half_size;
    
    this.dl = 2.0*half_size / (N-1);
    
    // Allocation de la mémoire au champ vectoriel.
    field = new SVector3d[N][][];
    
    for(int i = 0; i < N; i++)
    {
      field[i] = new SVector3d[N][];
      
      for(int j = 0; j < N; j++)
        field[i][j] = new SVector3d[N];
    } 
  
  }
  
  /**
   * ...
   * 
   * @return
   */
  public double getMaxPosition()
  {
    return this.max_position;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public double getMinPosition()
  {
    return this.min_position;
  }
  
  /**
   * ...
   * 
   * @param i
   * @param j
   * @param k
   * @return
   */
  public SVector3d getPosition(int i, int j, int k) 
  {
    return new SVector3d(min_position + i*dl, min_position + j*dl, min_position + k*dl);
  }
  
  /**
   * ...
   * 
   * @param i
   * @param j
   * @param k
   * @return
   */
  public SVector3d get(int i, int j, int k) throws IndexOutOfBoundsException
  {
    return field[i][j][k];
  }
  
  /**
   * ...
   * 
   * @param i
   * @param j
   * @param k
   * @param v
   */
  public void set(int i, int j, int k, SVector3d v) throws IndexOutOfBoundsException
  {
    field[i][j][k] = v;
  }
  
  /**
   * 
   * @param x
   * @param y
   * @param z
   * @return
   * @throws IndexOutOfBoundsException
   */
  public SVector3d get(SVector3d r) throws IndexOutOfBoundsException, SUndefinedFieldException
  {
    // Obtenir les index de la coordonnée xyz dans le tableau.
    int[] index = getIndex(r);
    
    // Retourner la valeur du champ à l'endroit xyz.
    SVector3d value = field[index[X]][index[Y]][index[Z]];
    
    // Validation de la valeur du champ.
    if(value == null)
      throw new SUndefinedFieldException(r);
    else
      return value;
  }
  
  /**
   * ...
   * 
   * @param r
   * @param value
   * @throws IndexOutOfBoundsException
   */
  public void set(SVector3d r, SVector3d value) throws IndexOutOfBoundsException
  {
    // Obtenir les index de la coordonnée xyz dans le tableau.
    int[] index = getIndex(r);
    
    // Effectuer l'affectation de la valeur.
    field[index[X]][index[Y]][index[Z]] = value;
  }
  
  /**
   * ...
   * 
   * @param r
   * @return
   * @throws IndexOutOfBoundsException
   */
  public int[] getIndex(SVector3d r) throws IndexOutOfBoundsException
  {
    int[] index = new int[3];
    
    index[X] = (int)Math.round((r.getX() - this.min_position)/this.dl); 
    index[Y] = (int)Math.round((r.getY() - this.min_position)/this.dl); 
    index[Z] = (int)Math.round((r.getZ() - this.min_position)/this.dl); 
    
    return index;
  }
  
}
