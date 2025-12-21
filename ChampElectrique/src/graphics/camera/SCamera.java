/**
 * 
 */
package sim.graphics.camera;

import sim.exception.SConstructorException;
import sim.math.SImpossibleNormalizationException;
import sim.math.SVector3d;

/**
 * La classe <b>SCamera</b> représente une caméra en 3d.
 * 
 * @author Simon Vézina
 * @since 2014-12-26
 * @version 2022-05-29
 */
public class SCamera {
	
  /**
   * La constante <b>DEFAULT_VIEW_ANGLE</b> correspond à l'angle d'ouverture vertical de la caméra par défaut étant égal à {@value} degrés.
   */
  protected static final double DEFAULT_VIEW_ANGLE = 60.0;
  
  /**
   * <p>
   * La constante <b>DEFAULT_ZNEAR</b> correspond à distance à partir de laquelle la caméra <b>peut voir</b> par défaut étant égale à {@value}.
   * </p>
   * <p>
   * <b>REMARQUE</b> : Il est important de déclarer que le z-near, ne peut pas être une valeur infinitésimal. Le système semble être non fonctionnel lorsque z-near < 0.00001.
   * Il est donc important de ne pas avoir une valeur trop faible.
   * </p>
   * <p>
   * Référence : https://www.khronos.org/opengl/wiki/Depth_Buffer_Precision
   * <ul>
   * You may have configured your zNear and zFar clipping planes in a way that severely limits your depth buffer precision. 
   * Generally, this is caused by a zNear clipping plane value that's too close to 0.0. As the zNear clipping plane is set increasingly closer to 0.0, the effective precision of the depth buffer decreases dramatically. 
   * Moving the zFar clipping plane further away from the eye always has a negative impact on depth buffer precision, but it's not one as dramatic as moving the zNear clipping plane.
   * </ul>
   * </p>
   */
  protected static final double DEFAULT_ZNEAR = 0.01;
  
  /**
   * La constante <b>DEFAULT_ZFAR</b> correspond à distance à partir de laquelle la caméra <b>ne peut plus voir</b> par défaut étant égale à {@value}.
   */
  protected static final double DEFAULT_ZFAR = 1000.0;
  
	/**
	 * La variable <b>position</b> représente la position de la caméra.
	 */
	protected SVector3d position;	
	
	/**
	 * ...		
	 */
	protected SVector3d front;
	
	/**
   * ...
   */
  protected SVector3d right;
  
  /**
   * La variable <b>up</b> correspond à la définition du haut pour la caméra.
   */
  protected SVector3d up;   
  
	/**
	 * La variable <b>view_angle</b> correspond à l'angle d'ouverture de la caméra dans la direction verticale (up).
	 * Cet élément se retrouve entre autre dans la fonction gluPerspective (paramètre <i>fovy</i>)de la librairie OpenGl pour faire la construction de la pyramide de vue.
	 * Cette valeur doit être en <b>degré</b>.
	 */
	protected double view_angle;	
	
	/**
	 * La variable <b>z_near</b> correspond à la distance <b>la plus près</b> pouvant être observée par la caméra.
	 * Cette valeur doit toujours être <b>positive</b>. Elle correspond également la distance entre la caméra et le <i>near clipping plane</i> de la pyramide de vue.
	 */
	protected double z_near;			
	
	/**
	 * Specifies the distance from the viewer to the far clipping plane (always positive). 
	 */
	protected double z_far;
		
	//----------------
	// CONSTRUCTEUR //
	//----------------
		
	/**
	 * Construction d'une camera par défaut.
	 */
	public SCamera()
	{
	  this.position = new SVector3d(4.0, 0.0, 0.0);
    this.front = SVector3d.UNITARY_X.multiply(-1.0);
    this.up = SVector3d.UNITARY_Z;
    this.right = SVector3d.UNITARY_Y;
    
	  this.view_angle = DEFAULT_VIEW_ANGLE;
    this.z_near = DEFAULT_ZNEAR;
    this.z_far = DEFAULT_ZFAR;
	}
	
	/**
   * Constructeur de la caméra avec paramètre de positionnement. 
   * @param position La position de la caméra.
   * @param look_at L'endroit où regarde la caméra. La distance entre position et look_at n'a pas besoin d'être unitaire.
   * @param up L'orientation du haut de la caméra
   * @throws SConstructorException Si les paramètres de la caméra ne permettent une construction complète de celle-ci. 
   */
  public SCamera(SVector3d position, SVector3d look_at, SVector3d up) throws SConstructorException
  {
    this(position, look_at, up, DEFAULT_VIEW_ANGLE, DEFAULT_ZNEAR, DEFAULT_ZFAR);
  }
  
	/**
	 * Constructeur de la caméra avec paramètre de positionnement. 
	 * @param position La position de la caméra.
	 * @param look_at L'endroit où regarde la caméra. La distance entre position et look_at n'a pas besoin d'être unitaire.
	 * @param up L'orientation du haut de la caméra
	 * @throws SConstructorException Si les paramètres de la caméra ne permettent une construction complète de celle-ci. 
	 */
	public SCamera(SVector3d position, SVector3d look_at, SVector3d up, double view_angle, double z_near, double z_far) throws SConstructorException
	{
		if(position.equals(look_at))
		  throw new SConstructorException("Erreur SCamera 001 : Le vecteur position = " + position + " et le vecteur look_at = " + look_at + " sont identique ce qui ne permet pas de définir l'orientation de la caméra.");
		
	  if(up.equals(SVector3d.ZERO))
		  throw new SConstructorException("Erreur SCamera 002 : Le vecteur up = " + up + " ne peut pas être égal à l'origine.");
		
	  if(z_far < z_near)
      throw new SConstructorException("Erreur SCamera 003 : La distance à l'écran de fond '" + z_far + "' ne peut pas être plus près que la distance à l'écran de face '" + z_near + "'.");  
  
	  if(z_near < 0 || z_far < 0)
	    throw new SConstructorException("Erreur SCamera 004 : Les paramètre z_near = " + z_near + " et z_far = " + z_far + " ne peuvent pas être négatif.");  
	  
    this.position = position;
	  
	  try {
	    setViewAngle(view_angle);
	  }catch(IllegalArgumentException e) {
	    throw new SConstructorException("Erreur SCamera 005 : Une erreur est survenue avec l'angle de vue", e);  
	  }
	  
		this.z_near = z_near;
		this.z_far = z_far;
		
		// Définition du vecteur front à partir du look-at.
		try {
		  
		  this.front = look_at.substract(this.position).normalize();
		  
		}catch(SImpossibleNormalizationException e){
      throw new SConstructorException("Erreur SCamera 006 : Le front de la camera ne peut pas être défini.", e);
    }
		
		// Défintion du vecteur up à partir du front et de l'ancien up.
		try{
           
      // Ajustement du vecteur "up" à partir des données initiatiale.
      // Traditionnellement, c'est le vecteur "front" qui est bien défini.
      // Ainsi, nous allons corriger si nécessaire l'orientation du "up afin qu'il soit purement perpendiculaire au "front".
      this.up = front.cross(up).cross(front).normalize();
      
    }catch(SImpossibleNormalizationException e){
      throw new SConstructorException("Erreur SCamera 006 : Le up de la camera ne peut pas être bien défini.", e);
    }
    
		// Définition du vecteur right à partir du front et du up.
		this.right = this.front.cross(up).normalize();
	}

	/**
	 * Méthode pour construire une camera à partir de l'ensemble de ses paramètres.
	 * 
	 * @param postiion
	 * @param front
	 * @param right
	 * @param up
	 * @param view_angle
	 * @param z_near
	 * @param z_far
	 */
	protected SCamera(SVector3d position, SVector3d front, SVector3d right, SVector3d up, double view_angle, double z_near, double z_far)
	{
	  this.position = position;
	  this.front = front;
	  this.right = right;
	  this.up = up;
	  this.view_angle = view_angle;
	  this.z_near = z_near;
	  this.z_far = z_far;
	}
	
	@Override
	public SCamera clone()
	{
	  return new SCamera(this.position, this.front, this.right, this.up, this.view_angle, this.z_near, this.z_far);
	}
	
		
	/**
	 * Méthode pour obtenir la position de la camera.
	 * @return La position de la caméra.
	 */
	public SVector3d getPosition()
	{
		return this.position;
	}
	
	/**
	 * Méthode pour obtenir la position où regarde la camera.
	 * 
	 * @return La position où regarde la camera.
	 */
	public SVector3d getLookAt()
	{
	  return this.position.add(this.front);
	}
		
	/**
	 * Méthode pour obtenir l'orientation du devant de la caméra. 
	 * En d'autres mots, la camera point dans la direction de ce vecteur.
	 * @return L'orientation du devant de la caméra.
	 */
	public SVector3d getFront()
	{ 
	  return this.front;
	}

	/**
	 * ...
	 * 
	 * @return
	 */
	public SVector3d getRight()
	{
	  return this.right;
	}
	
	/**
	 * Méthode pour obtenir l'orientation du haut de la caméra.
	 * @return L'orientation du haut de la caméra.
	 */
	public SVector3d getUp()
	{ 
		return this.up;
	}
	
	/**
	 * Méthode pour obtenir l'angle d'ouverture de vue de la caméra dans la direction verticale.
	 * @return L'angle d'ouverture de la caméra (direction verticale).
	 */
	public double getViewAngle()
	{
		return this.view_angle;
	}
	
	/**
	 * Méthode pour obtenir la distance entre la caméra et la position du devant de la pyramide de vue (near clipping plane). 
	 * @return La distance entre la caméra et le devant de la pyramide de vue.
	 */
	public double getZNear()
	{
		return this.z_near;
	}
	
	/**
	 * Méthode pour obtenir la distance entre la caméra et la position de l'arrière de la pyramide de vue (far clipping plane). 
	 * @return La distance entre la caméra et l'arrière de la pyramide de vue.
	 */
	public double getZFar()
	{
		return this.z_far;
	}

	/**
	 * Méthode pour modifier la position de la camera.
	 * 
	 * @param position La position de la camera.
	 */
	public void setPosition(SVector3d position)
	{
	  this.position = position;
	}
	
	/**
   * Méthode pour définir l'angle de vue de la camera.
   * 
   * @param view_angle L'angle de vue.
   * @throws IllegalArgumentException Si l'angle n'est pas situé entre 10 et 170 degrés.
   */
  public void setViewAngle(double view_angle) throws IllegalArgumentException
  {
    if(view_angle < 10.0 || view_angle > 170.0)
      throw new IllegalArgumentException("Erreur SCamera 001 : L'angle " + view_angle + " n'est pas entre 10 et 170 degré (valeur = " + view_angle + ").");
    
    this.view_angle = view_angle;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SCamera other = (SCamera) obj;
    if (front == null) {
      if (other.front != null)
        return false;
    } else if (!front.equals(other.front))
      return false;
    if (position == null) {
      if (other.position != null)
        return false;
    } else if (!position.equals(other.position))
      return false;
    if (right == null) {
      if (other.right != null)
        return false;
    } else if (!right.equals(other.right))
      return false;
    if (up == null) {
      if (other.up != null)
        return false;
    } else if (!up.equals(other.up))
      return false;
    if (Double.doubleToLongBits(view_angle) != Double.doubleToLongBits(other.view_angle))
      return false;
    if (Double.doubleToLongBits(z_far) != Double.doubleToLongBits(other.z_far))
      return false;
    if (Double.doubleToLongBits(z_near) != Double.doubleToLongBits(other.z_near))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SCamera [position=" + position + ", front=" + front + ", right=" + right + ", up=" + up + ", view_angle="
        + view_angle + ", z_near=" + z_near + ", z_far=" + z_far + "]";
  }

}
