/**
 * 
 */
package sim.exception;

/**
 * La classe <b>SIllegalNegativeValueException</b> représente une exception lancée lorsqu'une
 * valeur est utilisé dans une méthode et que cette valeur n'est pas acceptable dans l'implémentation de celle-ci.
 * 
 * @author Simon Vézina
 * @since 2018-03-03
 * @version 2018-03-03
 */
public class SIllegalValueException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -4113687450435778013L;

  /**
   * Constructeur de l'exception avec message d'erreur.
   * 
   * @param message Le message de l'erreur.
   */
  public SIllegalValueException(String message)
  {
    super(message);
  }

  /**
   * Constructeur de l'exception avec message d'erreur et cause de l'exception.
   * 
   * @param message Le message de l'erreur.
   * @param cause La cause de l'erreur.
   */
  public SIllegalValueException(String message, Throwable cause)
  {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

}// fin de la classe SIllegalValueException
