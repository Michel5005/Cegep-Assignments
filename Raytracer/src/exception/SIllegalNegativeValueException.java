/**
 * 
 */
package sim.exception;

/**
 * La classe <b>SIllegalNegativeValueException</b> représente une exception lancée lorsqu'une
 * valeur négative est utilisé dans une méthode et que cette valeur n'est pas acceptable dans
 * l'implémentation de celle-ci.
 * 
 * @author Simon Vézina
 * @since 2018-01-21
 * @version 2018-01-21
 */
public class SIllegalNegativeValueException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 7628867286723824967L;

  /**
   * Constructeur de l'exception avec message d'erreur.
   * 
   * @param message Le message de l'erreur.
   */
  public SIllegalNegativeValueException(String message)
  {
    super(message);
  }

  /**
   * Constructeur de l'exception avec message d'erreur et cause de l'exception.
   * 
   * @param message Le message de l'erreur.
   * @param cause La cause de l'erreur.
   */
  public SIllegalNegativeValueException(String message, Throwable cause)
  {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

}//fin de la classe SIllegalNegativeValueException
