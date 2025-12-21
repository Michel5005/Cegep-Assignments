/**
 * 
 */
package sim.util;

/**
 * La classe <b>SManyFilesFoundException</b> représente une exception lorsqu'une recherche de fichier se termine avec plusieurs fichiers ayant le même nom.
 * 
 * @author Simon Vézina
 * @since 2018-03-13
 * @version 2018-03-13
 */
public class SManyFilesFoundException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -4647696257827593678L;

  /**
   * Constructeur de l'exception avec message d'erreur.
   * 
   * @param message - Le message de l'erreur.
   */
  public SManyFilesFoundException(String message)
  {
    super(message);
  }

  /**
   * Constructeur de l'exception avec message d'erreur et cause de l'exception.
   * 
   * @param message - Le message de l'erreur.
   * @param cause - La cause de l'erreur.
   */
  public SManyFilesFoundException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
}
