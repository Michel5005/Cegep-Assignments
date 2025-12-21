/**
 * 
 */
package sim.opengl;

import com.jogamp.opengl.GL2;

import sim.graphics.SColor;
import sim.math.SVector3d;
import sim.math.field.SUndefinedFieldException;
import sim.math.field.SVectorField;

/**
 * La classe <b>SGLFieldDisplay</b> représente une classe utilitaire permettant l'affichage grâce à OpenGL de champ scalaire et vectoriel en 3d.
 * 
 * @author Simon Vezina
 * @since 2022-04-12
 * @version 20220-04-15
 */
public class SGLFieldDisplay {

  public static final int DEFAULT_MODULUS_DIVISION = 10;
  
  /**
   * Méthode pour dessiner un champ vectoriel à l'aide de dessin de flèche.
   * 
   * @param gl L'objet OpenGL.
   * @param field Le champ de vecteur.
   * @param color La couleur des flèches.
   * @param max_vector_modulus Le module maximal d'un vecteur du champ.
   * @param center Le centre du champ vectoriel.
   * @param size La largeur du champ vectoriel couvert en x, y et z.
   * @param N Le nombre d'élément discret dans la largeur du champ vectoriel.
   * @throws IllegalArgumentException Si un argument illégal a été introduit dans l'appel de la méthode.
   */
  public static void displayVectorField(GL2 gl, SVectorField field, SColor color, double max_vector_modulus, SVector3d center, double size, int N) throws IllegalArgumentException
  {
    if(N < 2)
      throw new IllegalArgumentException("Erreur SGLDisplay 001 : Le nombre N = " + N + " doit être supérieur à 1.");
    
    double length = size/(double)(N-1);
    
    SVector3d dx = SVector3d.UNITARY_X.multiply(length);
    SVector3d dy = SVector3d.UNITARY_Y.multiply(length);
    SVector3d dz = SVector3d.UNITARY_Z.multiply(length);
    
    for(int N_x = -N/2; N_x <= N/2; N_x++)
      for(int N_y = -N/2; N_y <= N/2; N_y++)
        for(int N_z = -N/2; N_z <= N/2; N_z++)
        {
          // Évaluons la position du vecteur affiché à partir de la position centrale d'affichage.
          SVector3d r = center.add(dx.multiply(N_x)).add(dy.multiply(N_y)).add(dz.multiply(N_z));
          
          // Dessiner la composante du champ.
          try {
            SGLDisplay.displayVector(gl, r, color, max_vector_modulus, DEFAULT_MODULUS_DIVISION, length, field.get(r));
          }catch(SUndefinedFieldException e) {
            double ray = length / DEFAULT_MODULUS_DIVISION;
            SGLDisplay.displaySphere(gl, SColor.RED, new SVector3d(ray, ray, ray), SVector3d.ZERO, r);
          }
        }
  }
  
  /**
   * Méthode pour dessiner un champ vectoriel à l'aide de dessin de flèche <u>uniquement</u> dans le plan <i>xy</i>.
   * 
   * @param gl L'objet OpenGL.
   * @param field Le champ de vecteur.
   * @param color La couleur des flèches.
   * @param max_vector_modulus Le module maximal d'un vecteur du champ.
   * @param center Le centre du champ vectoriel.
   * @param size La largeur du champ vectoriel couvert en x, y et z.
   * @param N Le nombre d'élément discret dans la largeur du champ vectoriel.
   * @throws IllegalArgumentException Si un argument illégal a été introduit dans l'appel de la méthode.
   */
  public static void displayVectorFieldXY(GL2 gl, SVectorField field, SColor color, double max_vector_modulus, SVector3d center, double size, int N) throws IllegalArgumentException
  {
    if(N < 2)
      throw new IllegalArgumentException("Erreur SGLDisplay 002 : Le nombre N = " + N + " doit être supérieur à 1.");
    
    double length = size/(double)(N-1);
    
    SVector3d dx = SVector3d.UNITARY_X.multiply(length);
    SVector3d dy = SVector3d.UNITARY_Y.multiply(length);
    
    // Parcourir le plan XY.
    for(int N_x = -N/2; N_x <= N/2; N_x++)
      for(int N_y = -N/2; N_y <= N/2; N_y++)
      {
        // Évaluons la position du vecteur affiché à partir de la position centrale d'affichage.
        SVector3d r = center.add(dx.multiply(N_x)).add(dy.multiply(N_y));
      
        // Dessiner la composante du champ.
        try {
          SGLDisplay.displayVector(gl, r, color, max_vector_modulus, DEFAULT_MODULUS_DIVISION, length, field.get(r));
        }catch(SUndefinedFieldException e) {
          double ray = length / DEFAULT_MODULUS_DIVISION;
          SGLDisplay.displaySphere(gl, SColor.RED, new SVector3d(ray, ray, ray), SVector3d.ZERO, r);
        }
     }
  }
  
  /**
   * Méthode pour dessiner un champ vectoriel à l'aide de dessin de flèche <u>uniquement</u> dans le plan <i>xz</i>.
   * 
   * @param gl L'objet OpenGL.
   * @param field Le champ de vecteur.
   * @param color La couleur des flèches.
   * @param max_vector_modulus Le module maximal d'un vecteur du champ.
   * @param center Le centre du champ vectoriel.
   * @param size La largeur du champ vectoriel couvert en x, y et z.
   * @param N Le nombre d'élément discret dans la largeur du champ vectoriel.
   * @throws IllegalArgumentException Si un argument illégal a été introduit dans l'appel de la méthode.
   */
  public static void displayVectorFieldXZ(GL2 gl, SVectorField field, SColor color, double max_vector_modulus, SVector3d center, double size, int N) throws IllegalArgumentException
  {
    if(N < 2)
      throw new IllegalArgumentException("Erreur SGLDisplay 001 : Le nombre N = " + N + " doit être supérieur à 1.");
    
    double length = size/(double)(N-1);
    
    SVector3d dx = SVector3d.UNITARY_X.multiply(length);
    SVector3d dz = SVector3d.UNITARY_Z.multiply(length);
    
    for(int N_x = -N/2; N_x <= N/2; N_x++)
      for(int N_z = -N/2; N_z <= N/2; N_z++)
      {
        // Évaluons la position du vecteur affiché à partir de la position centrale d'affichage.
        SVector3d r = center.add(dx.multiply(N_x)).add(dz.multiply(N_z));
          
        // Dessiner la composante du champ.
        try {
          SGLDisplay.displayVector(gl, r, color, max_vector_modulus, DEFAULT_MODULUS_DIVISION, length, field.get(r));
        }catch(SUndefinedFieldException e) {
          double ray = length / DEFAULT_MODULUS_DIVISION;
          SGLDisplay.displaySphere(gl, SColor.RED, new SVector3d(ray, ray, ray), SVector3d.ZERO, r);
        }
     }
  }
  
  /**
   * Méthode pour dessiner un champ vectoriel à l'aide de dessin de flèche <u>uniquement</u> dans le plan <i>yz</i>.
   * 
   * @param gl L'objet OpenGL.
   * @param field Le champ de vecteur.
   * @param color La couleur des flèches.
   * @param max_vector_modulus Le module maximal d'un vecteur du champ.
   * @param center Le centre du champ vectoriel.
   * @param size La largeur du champ vectoriel couvert en x, y et z.
   * @param N Le nombre d'élément discret dans la largeur du champ vectoriel.
   * @throws IllegalArgumentException Si un argument illégal a été introduit dans l'appel de la méthode.
   */
  public static void displayVectorFieldYZ(GL2 gl, SVectorField field, SColor color, double max_vector_modulus, SVector3d center, double size, int N) throws IllegalArgumentException
  {
    if(N < 2)
      throw new IllegalArgumentException("Erreur SGLDisplay 001 : Le nombre N = " + N + " doit être supérieur à 1.");
    
    double length = size/(double)(N-1);
    
    SVector3d dy = SVector3d.UNITARY_Y.multiply(length);
    SVector3d dz = SVector3d.UNITARY_Z.multiply(length);
    
    for(int N_y = -N/2; N_y <= N/2; N_y++)
      for(int N_z = -N/2; N_z <= N/2; N_z++)
      {
        // Évaluons la position du vecteur affiché à partir de la position centrale d'affichage.
        SVector3d r = center.add(dy.multiply(N_y)).add(dz.multiply(N_z));
          
        // Dessiner la composante du champ.
        try {
          SGLDisplay.displayVector(gl, r, color, max_vector_modulus, DEFAULT_MODULUS_DIVISION, length, field.get(r));
        }catch(SUndefinedFieldException e) {
          double ray = length / DEFAULT_MODULUS_DIVISION;
          SGLDisplay.displaySphere(gl, SColor.RED, new SVector3d(ray, ray, ray), SVector3d.ZERO, r);
        }
     }
  }
    
}
