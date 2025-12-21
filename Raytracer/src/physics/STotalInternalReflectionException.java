/**
 * 
 */
package sim.physics;

/**
 * La classe <b>STotalInternalReflectionException</b> représente une exception lorsqu'un calcul de réfraction n'est pas possible puisqu'il y a réflexion totale interne.
 * 
 * @author Simon Vezina
 * @since 2020-02-10
 * @version 2020-02-10
 */
public class STotalInternalReflectionException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -1861070746469065998L;

  private static final String DEFAULT_MESSAGE = "Il y a une exception puisqu'il y a eu une réflexion totale interne. La réfraction n'est pas défini.";
  
  /**
   * 
   */
  public STotalInternalReflectionException() 
  {
    super(DEFAULT_MESSAGE);
  }

  /**
   * @param message
   */
  public STotalInternalReflectionException(String message)
  {
    super(message);
  }
  
}
