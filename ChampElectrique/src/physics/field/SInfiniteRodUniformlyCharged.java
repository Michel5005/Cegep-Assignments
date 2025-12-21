/**
 * 
 */
package sim.physics.field;

import sim.exception.SConstructorException;
import sim.math.SImpossibleNormalizationException;
import sim.math.SVector3d;
import sim.math.field.SUndefinedFieldException;
import sim.math.field.SVectorField;
import sim.physics.SElectrostatics;

/**
 * La classe <b>SInfiniteRodUniformlyCharged</b> représente une tige infinie uniformément chargée (TRIUC).
 * 
 * @author Simon Vezina
 * @since 2022-03-04
 * @version 2022-03-04
 */
public class SInfiniteRodUniformlyCharged implements SVectorField {

  /**
   * ...
   */
  private final SVector3d position;
  
  /**
   * ...
   */
  private final SVector3d orientation;
  
  /**
   * ...
   */
  private final double lamda;
  
  /**
   * ...
   * 
   * @param position
   * @param orientation
   * @param lamda
   * @throws SConstructorException
   */
  public SInfiniteRodUniformlyCharged(SVector3d position, SVector3d orientation, double lamda) throws SConstructorException
  {
    this.position = position;
    this.lamda = lamda;
    
    try {
      this.orientation = orientation.normalize();
    }catch(SImpossibleNormalizationException e) {
      throw new SConstructorException("Erreur SInfiniteRodUniformlyCharged 001 : L'orientation ne peut pas être normalisée.", e);
    }
  }

  @Override
  public SVector3d get(SVector3d r) throws SUndefinedFieldException 
  {
    return SElectrostatics.infiniteRodElectricField(this.position, this.orientation, this.lamda, r);
  }

}
