/**
 * 
 */
package sim.physics.field;

import sim.exception.SConstructorException;
import sim.math.SMath;
import sim.math.SVector3d;
import sim.math.field.SVectorField;
import sim.physics.SElectrostatics;

/**
 * La classe <b>SFiniteRodUniformlyCharged</b> représente une tige uniformément chargée générant un champ électrique.
 * 
 * @author Simon Vezina
 * @since 2022-01-21
 * @version 2022-01-21
 */
public class SFiniteRodUniformlyCharged implements SVectorField {

  /**
   * ...
   */
  private final SVector3d r_A;
  
  /**
   * ...
   */
  private final SVector3d r_B;
  
  /**
   * ...
   */
  private final double Q;
  
  /**
   * Constructeur d'un générateur de champ correspondant à une tige finie uniformément chargée.
   * 
   * @param r_A La postion A de la tige.
   * @param r_B La position B de la tige.
   * @param Q La charge sur la tige.
   * @throws SConstructorException S'il y a eu une erreur lors la construction de l'objet.
   * 
   */
  public SFiniteRodUniformlyCharged(SVector3d r_A, SVector3d r_B, double Q) throws SConstructorException
  {
    this.r_A = r_A;
    this.r_B = r_B;
    this.Q = Q;
    
    // Vérification que la tige n'est pas de longueur nulle.
    if(SMath.nearlyZero(r_A.substract(r_B).modulus()))
      throw new SConstructorException();
  }
  
  @Override
  public SVector3d get(SVector3d r)
  {
    return SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r);
  }

}
