/**
 * 
 */
package sim.geometry;

import java.util.ArrayList;
import java.util.List;

import sim.exception.SNoImplementationException;
import sim.exception.SRuntimeException;
import sim.math.SVector3d;

/**
 * <p>
 * La classe <b>SGeometricDiscretization</b> poss�de des outils permettant de discrétiser une géométrie. 
 * Elle permet entre autre de repr�senter une géométrie à l'aide de plusieurs points.
 * </p>
 * 
 * @author Simon Vézina
 * @since 2017-06-06
 * @version 2018-09-05 (labo - La loi de Coulomb v1.1)
 */
public class SGeometricDiscretization {

  /**
   * Méthode permettant de discrétiser une ligne situé le long de l'axe x positif en une multitude de points tous équitablement espacés
   * 
   * @param size La longueur de la ligne.
   * @param N Le nombre de points le long de la ligne.
   * @return La liste des points composant la ligne.
   * @throws SRuntimeException Si la taille de la ligne est négatif.
   * @throws SRuntimeException Si le nombre de particules N est inférieur à 2.
   */
  public static List<SVector3d> positiveLineXDiscretization(double size, int N) {
	  
    // Allocation de la mémoire de la liste.
    List<SVector3d> list = new ArrayList<SVector3d>();
    
    double dx = size/(N-1);
    for(double i = 0; i <= size; i= i+dx) {
    	list.add(new SVector3d(i,0,0));
    }
    
    
   return list;
  }
      
  /**
   * Méthode permettant de discrétiser un carré situé dans le plan positif xy en une multitude de points tous équitablement espacés
   * 
   * @param size La taille du carré.
   * @param N Le nombre de points par dimension du carré.
   * @return Le tableau des points composant le carré.
   * @throws SRuntimeException Si la taille du carré estnégatif.
   * @throws SRuntimeException Si le nombre de particules N est inférieur à 2.
   */
  public static List<SVector3d> positiveSquareXYDiscretization(double size, int N)
  {
   
    // Allocation de la mémoire de la liste.
    List<SVector3d> list = new ArrayList<SVector3d>();
   
    double dx = size/(N-1);
    double dy = size/(N-1);
    
    for(double i = 0; i <= size; i = i + dx) {
    	for(double j = 0; j <= size; j = j + dy) {
    	list.add(new SVector3d(i,j,0));
    	}
    }
    
    
   return list;
  }
  
}// fin de la classe SGeometricDiscretization
