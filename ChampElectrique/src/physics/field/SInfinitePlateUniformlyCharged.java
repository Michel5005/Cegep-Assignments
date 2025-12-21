/**
 * 
 */
package sim.physics.field;

import sim.exception.SConstructorException;
import sim.math.SImpossibleNormalizationException;
import sim.math.SVector3d;
import sim.math.field.SVectorField;
import sim.physics.SElectrostatics;

/**
 * La classe <b>SInfinitePlaneUniformlyCharged</b> représente une plaque infinie uniformément chargée (PPIUC).
 * 
 * @author Simon Vezina
 * @since 2022-01-24
 * @version 2022-01-24
 */
public class SInfinitePlateUniformlyCharged implements SVectorField {

  /**
   * ...
   */
  private final SVector3d position;
  
  /**
   * ...
   */
  private final SVector3d normal;
  
  /**
   * ...
   */
  private final double sigma;
  
  /**
   * ...
   * 
   * @param position
   * @param normal
   * @param sigma
   * @throws SConstructorException
   */
  public SInfinitePlateUniformlyCharged(SVector3d position, SVector3d normal, double sigma) throws SConstructorException
  {
    this.position = position;
    
    try {
      this.normal = normal.normalize();
    }catch(SImpossibleNormalizationException e) {
      throw new SConstructorException(e);
    }
    
    this.sigma = sigma;
  }
  
  @Override
  public SVector3d get(SVector3d r)
  {
    return SElectrostatics.infinitePlateElectricField(this.position, this.normal, sigma, r);
  }

}
