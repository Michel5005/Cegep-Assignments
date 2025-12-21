/**
 * 
 */
package sim.physics.field;

import sim.math.SVector3d;
import sim.math.field.SVectorField;
import sim.physics.SElectrostatics;

/**
 * La classe <b>SSphereUniformlyCharged</b> représente une sphère uniformément chargée en surface.
 * 
 * @author Simon Vezina
 * @since 2022-01-25
 * @version 2022-02-02
 */
public class SSphereUniformlyCharged implements SVectorField {

  /**
   * ...
   */
  private final SVector3d position;
  
  /**
   * ...
   */
  private final double ray;
  
  /**
   * ...
   */
  private final double charge;
  
  /**
   * Méthode pour construire une sphère uniformément chargée à sa surface.
   * Cette sphère  possèdera toujours la même densité surfacique de charge même s'il y a d'autres champs
   * dans le voisinage.
   * 
   * @param position La position centrale de la sphère.
   * @param ray Le rayon de la sphère.
   * @param charge La charge électrique de la sphère. 
   */
  public SSphereUniformlyCharged(SVector3d position, double ray, double charge)
  {
    this.position = position;
    this.ray = ray;
    this.charge = charge;
  }
  
  @Override
  public SVector3d get(SVector3d r) 
  {
    return SElectrostatics.sphereElectricField(this.position, this.ray, this.charge, r);
  }

}
