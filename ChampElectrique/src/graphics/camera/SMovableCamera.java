/**
 * 
 */
package sim.graphics.camera;

import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.math.SVector3d;

/**
 * La classe abstraite <b>SMovableCamera</b> repr�sente une camera pouvant effectuer des mouvement de d�placement et de rotation.
 * Cette classe est abstraite puisque l'impl�mentation de la rotation d�pendra du type de camera.
 * 
 * @author Simon Vezina
 * @since 2022-05-29
 * @version 2022-06-02 (version labo � Le champ �lectrique v1.1.0)
 */
public abstract class SMovableCamera extends SCamera {

  /**
   * ...
   * 
   */
  public SMovableCamera()
  { 
    super();
  }
  
  /**
   * ...
   * 
   * @param position
   * @param look_at
   * @param up
   * @throws SConstructorException
   */
  public SMovableCamera(SVector3d position, SVector3d look_at, SVector3d up) throws SConstructorException
  {
    super(position, look_at, up);
  }

  /**
   * ...
   * 
   * @param position
   * @param look_at
   * @param up
   * @param view_angle
   * @param z_near
   * @param z_far
   * @throws SConstructorException
   */
  public SMovableCamera(SVector3d position, SVector3d look_at, SVector3d up, double view_angle, double z_near, double z_far) throws SConstructorException
  {
    super(position, look_at, up, view_angle, z_near, z_far);
  }

  /**
   * ...
   * 
   * @param c
   */
  public SMovableCamera(SCamera c)
  {
    this(c.position, c.front, c.right, c.up, c.view_angle, c.z_near, c.z_far);
  }
  
  /**
   * M�thode pour construire une camera � partir de l'ensemble de ses param�tres.
   * 
   * @param postiion
   * @param front
   * @param right
   * @param up
   * @param view_angle
   * @param z_near
   * @param z_far
   */
  protected SMovableCamera(SVector3d position, SVector3d front, SVector3d right, SVector3d up, double view_angle, double z_near, double z_far)
  {
    super(position, front, right, up, view_angle, z_near, z_far);
  }
  
  /**
   * M�thode pour d�placement la cam�ra vers l'avant.
   * 
   * @param distance La distance du d�placement.
   */
  public void moveFront(double distance)
  {
    move(this.front, distance);
  }
  
  /**
   * ...
   * @param distance
   */
  public void moveBack(double distance)
  {
    moveFront(distance*-1.0);
  }
  
  /**
   * ...
   * 
   * @param distance
   */
  public void moveRight(double distance)
  {
    move(this.right, distance);
  }
  
  /**
   * ...
   * 
   * @param distance
   */
  public void moveLeft(double distance)
  {
    moveRight(distance*-1.0);
  }
  
  /**
   * ...
   * 
   * @param distance
   */
  public void moveUp(double distance)
  {
    move(this.up, distance);
  }
  
  /**
   * ...
   * 
   * @param distance
   */
  public void moveDown(double distance)
  {
    moveUp(distance*-1.0);
  }
    
  /**
   * Rotation autour de l'axe down.
   * 
   * @param degrees Le nombre de degr�es dans la rotation.
   */
  public abstract void rotationYaw(double degrees);
  
  /**
   * Rotation autour de l'axe right.
   * 
   * @param degrees Le nombre de degr�es dans la rotation.
   */
  public abstract void rotationPitch(double degrees);
  
  /**
   * Rotation autour de l'axe front.
   * 
   * @param degrees Le nombre de degr�es dans la rotation.
   */
  public abstract void rotationRoll(double degrees);
    
  /**
   * M�thode pour effectuer un d�placement dans un direction quelconque.
   * 
   * @param direction La direction du d�placement.
   * @param distance La grandeur du d�placement.
   */
  protected void move(SVector3d direction, double distance){
    
	this.position = getPosition().add(direction.multiply(distance));
  }
  
}
