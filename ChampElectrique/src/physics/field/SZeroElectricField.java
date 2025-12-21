/**
 * 
 */
package sim.physics.field;

import sim.math.SVector3d;
import sim.math.field.SUndefinedFieldException;
import sim.math.field.SVectorField;
import sim.physics.SElectrostatics;

/**
 * La classe <b>SZeroElectricField</b> représente un champ électrique entièrement nul en tout point de l'espace.
 * 
 * @author Simon Vezina
 * @since 2022-10-06
 * @version 2022-10-06
 */
public class SZeroElectricField implements SVectorField {

  /**
   * Constructeur d'un champ électrique nul.
   */
  public SZeroElectricField() 
  {
    
  }

  @Override
  public SVector3d get(SVector3d r) throws SUndefinedFieldException 
  {
    return SElectrostatics.NO_ELECTRIC_FIELD;
  }

}
