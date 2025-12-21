/**
 * 
 */
package sim.math;

/**
 * La classe <b>SInvalidMatrixSizeException</b> représente une exception lancée lorsqu'une matrice n'a pas les bonnes dimensions en lien avec une opération mathématique.
 * 
 * @author Simon Vezina
 * @since 2017-03-11
 * @version 2021-10-02
 */
public class SInvalidMatrixSizeException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur de l'exception avec message d'erreur.
   * 
   * @param message Le message de l'erreur.
   */
  public SInvalidMatrixSizeException(String message) 
  {
    super(message);
  }

}
