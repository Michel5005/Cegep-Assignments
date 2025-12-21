/**
 * 
 */
package moteurPhysique;

/**
 * La classe <b>SImpossibleNormalizationException</b> repr�sente une exception qui est lanc�e lorsqu'une normalisation est impossible � �tre r�alis�e.
 * Par exemple, un vecteur ayant une longueur nulle ne peut pas �tre normalis� � une longueur unitaire �tant donn� qu'il n'a pas d'orientation.
 * 
 * @author Simon V�zina
 * @since 2015-08-03
 * @version 2016-01-13
 */
public class SImpossibleNormalizationException extends RuntimeException {

  /**
   * La variable <b>serialVersionUID<b> correspond � un code d'identification de l'exception.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur de l'exception avec message d'erreur.
   * 
   * @param message - Le message de l'erreur.
   */
  public SImpossibleNormalizationException(String message)
  {
    super(message);
  }

  /**
   * Constructeur de l'exception avec message d'erreur et cause de l'exception.
   * 
   * @param message - Le message de l'erreur.
   * @param cause - La cause de l'erreur.
   */
  public SImpossibleNormalizationException(String message, Throwable cause)
  {
    super(message, cause);
  }

}//fin de la classe SImpossibleNormalisationException
