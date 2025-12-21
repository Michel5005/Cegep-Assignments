/**
 * 
 */
package sim.exception;

/**
 * La classe <b>SNaNValueException</b> représente une exception lancée lorsqu'une valeur calculée est égal à NaN (not a number).
 * Puisque ce type de valeur peut être propagée dans un calcul complexe, une exception de lancée permettra de mieux identifier la source du problème.
 * 
 * @author Simon Vezina
 * @since 2019-03-06
 * @version 2019-06-06
 */
public class SNaNValueException extends RuntimeException {

  /**
   * La constante <b>DEFAULT_MESSAGE</b> représente le message d'erreur par défaut.
   */ 
  private static final String DEFAULT_MESSAGE = "Une valeur NaN est survenue lors d'un calcul.";
  
  /**
   * 
   */
  private static final long serialVersionUID = -383718906325383742L;

  /**
   * Constructeur de l'exception.
   */
  public SNaNValueException()
  {
    super(DEFAULT_MESSAGE);
  }

  /**
   * Constructeur de l'exception avec message d'erreur.
   * @param message
   */
  public SNaNValueException(String message)
  {
    super(message);
  }
  
  /**
   * Constructeur de l'exception avec message d'erreur et cause de l'exception.
   * 
   * @param cause La cause de l'erreur.
   */
  public SNaNValueException(Throwable cause)
  {
    super(cause);
  }
  
}
