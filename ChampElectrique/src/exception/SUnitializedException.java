/**
 * 
 */
package sim.exception;

/**
 * La classe <b>SUnitializedException</b> représente une exception lancée lorsqu'un objet est utilisée avant d'être initialisé.
 * 
 * @author Simon Vézina
 * @since 2018-06-14
 * @version 2018-06-14
 */
public class SUnitializedException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -7201691197032093462L;

  /**
   * Constructeur de l'exception avec message d'erreur.
   * 
   * @param message Le message de l'erreur.
   */
  public SUnitializedException(String message)
  {
    super(message);
  }

  /**
   * Constructeur de l'exception avec message d'erreur et cause de l'exception.
   * 
   * @param message Le message de l'erreur.
   * @param cause La cause de l'erreur.
   */
  public SUnitializedException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
}
