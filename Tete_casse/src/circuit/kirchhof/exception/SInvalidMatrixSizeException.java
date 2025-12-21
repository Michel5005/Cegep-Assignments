/**
 * 
 */
package circuit.kirchhof.exception;

/**
 * La classe <b>SInvalidMatrixSize</b> repr�sente une exception lanc�e lorsqu'une matrice n'a pas les bonnes dimensions en lien avec une op�ration math�matique.
 * 
 * @author Simon Vezina
 * @since 2017-03-11
 * @version 2017-05-16
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

}// fin de la classe SInvalidMatrixSize
