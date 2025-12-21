package sim.physics;

/**
 * La classe <b>SUndefinedElectricFieldException</b> représente une exception lorsqu'un champ électrique n'est pas défini à l'endroit calculé.
 * 
 * @author Simon Vezina
 * @since 2020-06-16
 * @version 2020-06-16
 */
public class SUndefinedElectricFieldException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 985478302009607108L;

  private static final String DEFAULT_MESSAGE = "Le champ électrique n'est pas défini à cette position.";
  
  
  public SUndefinedElectricFieldException()
  {
    super(DEFAULT_MESSAGE);
  }
  
}
