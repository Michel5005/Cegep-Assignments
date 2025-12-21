package outils;

import java.awt.Point;
import java.util.Collection;
import java.util.List;

/**
 * Classe contenant des méthodes utiles pour l'implémentation de certains algorithmes
 * @author Ismaïl Boukari
 * @auhtor Simon Vézina
 */
public abstract class Utils {

    /**
     * Méthode permettant de savoir si deux nombres réels sont approximativement égaux
     * @param a le premier nombre réel
     * @param b le deuxième nombre réel
     * @param eps la précision
     * @return true si les deux nombres sont égaux, false sinon
     */
    // Ismaïl Boukari
	public static boolean almostEqual(double a, double b, double eps){
	    return Math.abs(a-b)<eps;
	}
    
   /**
    * Méthode permettant d'effectuer une rotation d'un point autour d'un autre point
    * @param point le point à faire tourner
    * @param pointDeRotation le point autour duquel on effectue la rotation
    * @param angle l'angle de rotation
    */
    // Ismaïl Boukari
    public static void rotatePoint(Point point, Point pointDeRotation, double angle)
    {
        // ON ADORE LES MATHS! :)
        if (angle == 0) return;
        double sinus = Math.sin(angle);
        double cosinus = Math.cos(angle);
        double x = point.getX();
        double y = point.getY();
        
        double nouveauX = (int) (cosinus*(x-pointDeRotation.getX()) - sinus*(y-pointDeRotation.getY()) + pointDeRotation.getX());
        double nouveauY = (int) (sinus*(x-pointDeRotation.getX()) + cosinus*(y-pointDeRotation.getY()) + pointDeRotation.getY());

        if ((nouveauX - (int)nouveauX)>=0.5) {
            nouveauX = (int)nouveauX + 1;
        }
        else {
            nouveauX = (int)nouveauX;
        }
        if ((nouveauY - (int)nouveauY)>=0.5) {
            nouveauY = (int)nouveauY + 1;
        }
        else {
            nouveauY = (int)nouveauY;
        }

        point.setLocation(nouveauX, nouveauY);
    }

    /**
     * Méthode permettant d'ajouter tous les éléments d'une collection à une autre collection sans ajouter les doublons.
     * @param <E> le type des éléments de la collection
     * @param collection la collection à laquelle on veut ajouter les éléments
     * @param collectionToAdd la collection dont on veut ajouter les éléments
     * @return la collection à laquelle on a ajouté les éléments
     */
    // Ismaïl Boukari
    public static <E> Collection<E> addAllWithoutDuplicates(Collection<E> collection, Collection<E> collectionToAdd) {
        for (E element : collectionToAdd) {
            if (!collection.contains(element)) {
                collection.add(element);
            }
        }
        return collection;
    }

    /**
     * Méthode permettant de trouver la valeur maximale d'une matrice
     * @param matrix la matrice dont on veut trouver la valeur maximale
     * @return la valeur maximale de la matrice
     */
    public static double findHighestValue(double[][] matrix) {
        double valeurMaximale = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > valeurMaximale) {
                    valeurMaximale = matrix[i][j];
                }
            }
        }
        return valeurMaximale;
    }

    /**
   * M�thode qui effectue l'affichage du syst�me d'�quation du circuit.
   * 
   * @param equations Le syst�me d'�quation.
   */
  // Simon Vézina
  public static void printEquationsSystem(List<double[]> equations)
  {
    // Faire l'it�ration des �quations.
    for(double[] eq : equations)
    {
      // Faire l'it�ration des coefficients.
      for(int i = 0; i < eq.length; i++)
      {    
        // Affichage du 1ier param�tre.
        if(i == 0)
          if(eq[i] >= 0)
            System.out.print(eq[i] +" I" + (i+1) + "  ");
          else
            System.out.print("- " + Math.abs(eq[i]) +" I" + (i+1) + "  ");
        // Affichage du 2i�me au (N-1)i�me param�tre.
        else
          if(i != eq.length-1)
            if(eq[i] >= 0)
              System.out.print(" + " + eq[i] + " I" + (i+1) + "  ");
            else
              System.out.print(" - " + Math.abs(eq[i]) + " I" + (i+1) + "  ");
          // Affichage du Ni�me param�tre.
          else
            if(eq[i] >= 0)
              System.out.print(" + " + eq[i] + " = 0");
            else
              System.out.print(" - " + Math.abs(eq[i]) + " = 0");
      }
      System.out.println();
      System.out.println();
    }
  }
}
