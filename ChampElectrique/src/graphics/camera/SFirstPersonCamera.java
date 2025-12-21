/**
 * 
 */
package sim.graphics.camera;

import sim.exception.SConstructorException;
import sim.math.SQuaternion;
import sim.math.SVector3d;

/**
 * La classe <b>SFirstPersonCamera</b> représente une camera effectuant des déplacement de type <u>first-person</u>.
 * 
 * @author Simon Vezina
 * @since 2022-05-31
 * @version 2022-05-31
 */
public class SFirstPersonCamera extends SMovableCamera {

  /**
   * La constante <b>PERSON_MOVEMENT</b> détermine si la camera se déplacement comme une personne ou dans le sens du regard.
   */
  private static final boolean PERSON_MOVEMENT = false;
  
  /**
   * La variable <b>look_at_direction</b> représente la direction dans laquelle la caméra pointera (ce qui n'est pas nécessairement le front).
   */
  protected SVector3d look_at_direction;
  
  /**
   * 
   */
  public SFirstPersonCamera() 
  {
    super();
    
    this.look_at_direction = this.front;
  }

  /**
   * @param position
   * @param look_at
   * @param up
   * @throws SConstructorException
   */
  public SFirstPersonCamera(SVector3d position, SVector3d look_at, SVector3d up) throws SConstructorException 
  {
    super(position, look_at, up);
    
    this.look_at_direction = this.front;
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
  public SFirstPersonCamera(SVector3d position, SVector3d look_at, SVector3d up, double view_angle, double z_near, double z_far) throws SConstructorException 
  {
    super(position, look_at, up, view_angle, z_near, z_far);
    
    this.look_at_direction = this.front;
  }

  /**
   * ...
   * 
   * @param c
   */
  public SFirstPersonCamera(SCamera c)
  {
    this(c.position, c.front, c.right, c.up, c.view_angle, c.z_near, c.z_far);
  }
  
  /**
   * @param position
   * @param front
   * @param right
   * @param up
   * @param view_angle
   * @param z_near
   * @param z_far
   */
  protected SFirstPersonCamera(SVector3d position, SVector3d front, SVector3d right, SVector3d up, double view_angle, double z_near, double z_far)
  {
    super(position, front, right, up, view_angle, z_near, z_far);
    
    this.look_at_direction = this.front;
  }

  @Override
  public SFirstPersonCamera clone()
  {
    return new SFirstPersonCamera(this.position, this.front, this.right, this.up, this.view_angle, this.z_near, this.z_far);
  }
  
  @Override
  public SVector3d getLookAt()
  {
    return this.position.add(this.look_at_direction);
  }
  
  @Override
  public void moveFront(double distance)
  {
    // Définir si le mouvement vers l'avant est dans la direction du front ou du look_at_direction
    if(PERSON_MOVEMENT)
      super.moveFront(distance);
    else
      move(this.look_at_direction, distance);
  }
  
  @Override
  public void rotationYaw(double degrees) 
  {
    // Construction du quaternion réalisant une rotation autour de l'axe -up.
    SQuaternion q_yaw = new SQuaternion(Math.toRadians(degrees), this.up.multiply(-1.0));
 
    // Effectuer la rotation du front.
    this.look_at_direction = q_yaw.rotation(this.look_at_direction).normalize();
    
    // P.S. Le up ne change pas.
    
    // Modification du right.
    this.right = this.front.cross(this.up).normalize();
    
    // Modification du front.
    this.front = this.up.cross(this.right).normalize();
  }

  @Override
  public void rotationPitch(double degrees) 
  {
    // Définir la rotation sur la droite.
    SQuaternion q_pitch = new SQuaternion(Math.toRadians(degrees), this.right);
    
    // Rotation de l'orientation Look at verticalement.
    SVector3d new_look_at_direction = q_pitch.rotation(this.look_at_direction).normalize();
    
    // Changer l'orientation seulement si ce n'est pas trop près du UP et ne change pas de côté.
    double angle = Math.toDegrees(SVector3d.angleBetween(new_look_at_direction, this.up));
    
    // P.S. Le front, up et right ne change pas. Seulement le look_at_direction sous certaine condition.
    
    // Affectation conditionnelle : Nous voulons stopper l'orientation du regard avec la verticale sans le dépasser.
    if(angle > 1.0 && angle < 179.0 && front.dot(new_look_at_direction) > 0.0)
      this.look_at_direction = new_look_at_direction;
  }

  @Override
  public void rotationRoll(double degrees)
  {
    // Définir la rotation sur le front.
    SQuaternion q_roll = new SQuaternion(Math.toRadians(degrees), this.front);
    
    // Rotation de l'orientation up.
    this.up = q_roll.rotation(this.up).normalize();
    
    // Rotation du look_at.
    this.look_at_direction = q_roll.rotation(this.look_at_direction).normalize();
    
    // P.S. Le front ne change pas.
    
    this.right = this.front.cross(this.up).normalize();
  }

}
