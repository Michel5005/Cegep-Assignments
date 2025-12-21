/**
 * 
 */
package sim.graphics.camera;

import java.text.Normalizer;

import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.math.SVector3d;

/**
 * La classe <b>SVectorCamera</b> repr�sente une cam�ra en 3d r�alisant des rotation � l'aide d'op�ration sur des vecteurs.
 * 
 * @author Simon Vezina
 * @since 2022-05-29
 * @version 2022-06-02 (version labo � Le champ �lectrique v1.1.0)
 */
public class SVectorCamera extends SMovableCamera {

  /**
   * Constructeur d'une camera par d�faut.
   */
  public SVectorCamera()
  {
    super();
  }
  
  /**
   * @param position
   * @param look_at
   * @param up
   * @throws SConstructorException
   */
  public SVectorCamera(SVector3d position, SVector3d look_at, SVector3d up) throws SConstructorException 
  {
    super(position, look_at, up);
  }

  /**
   * @param position
   * @param look_at
   * @param up
   * @param view_angle
   * @param z_near
   * @param z_far
   * @throws SConstructorException
   */
  public SVectorCamera(SVector3d position, SVector3d look_at, SVector3d up, double view_angle, double z_near, double z_far) throws SConstructorException 
  {
    super(position, look_at, up, view_angle, z_near, z_far);
  }

  /**
   * ...
   * 
   * @param c
   */
  public SVectorCamera(SCamera c)
  {
    this(c.position, c.front, c.right, c.up, c.view_angle, c.z_near, c.z_far);
  }
  
  /**
   * M�thode pour construire une camera � partir de l'ensemble de ses param�tres.
   * 
   * @param position
   * @param front
   * @param right
   * @param up
   * @param view_angle
   * @param z_near
   * @param z_far
   */
  protected SVectorCamera(SVector3d position, SVector3d front, SVector3d right, SVector3d up, double view_angle, double z_near, double z_far)
  {
    super(position, front, right, up, view_angle, z_near, z_far);
  }
  
  @Override
  public SVectorCamera clone()
  {
    return new SVectorCamera(this.position, this.front, this.right, this.up, this.view_angle, this.z_near, this.z_far);
  }
  
  @Override
  public void rotationYaw(double degrees) 
  {
    rotationYawOrPitch(this.right, degrees);
  }

  @Override
  public void rotationPitch(double degrees) 
  {
    rotationYawOrPitch(this.up, degrees);
  }

  @Override
  public void rotationRoll(double degrees) throws IllegalArgumentException
  {
    if(degrees > 80)
      throw new IllegalArgumentException("Erreur SVectorCamera 001 : Cette version d'impl�mentation de la rotation d'une camera ne permet pas des rotations sup�rieures � 80 degr�e (pr�sentement de " + degrees + ").");
    
    throw new SNoImplementationException();
    
  }

  /**
   * M�thode pour effectuer la rotation du vecteur front par l'addition du vecteur causant la rotation.
   * 
   * @param degrees Le nombre de degr� causant la rotation.
   * @param adding_vector L'orientation du vecteur d'ajout. 
   * @throws IllegalArgumentException Si l'angle est sup�rieur � 80 degr�s rendant l'algorithme non fonctionnel.
   */
  protected void rotationYawOrPitch(SVector3d adding_vector, double degrees) throws IllegalArgumentException
  {
    if(degrees > 80)
      throw new IllegalArgumentException("Erreur SVectorCamera 001 : Cette version d'impl�mentation de la rotation d'une camera ne permet pas des rotations sup�rieures � 80 degr�e (pr�sentement de " + degrees + ").");
    
    this.front = this.front.add(adding_vector.multiply(Math.tan(Math.toRadians(degrees)))).normalize();
    this.right = this.front.cross(this.up).normalize();
    this.up = this.right.cross(this.front).normalize();
    
  }
      
}
