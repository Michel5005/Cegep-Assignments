package sim.math.field;

import sim.math.SVector3d;

/**
 * La classe <b>SUndefinedElectricFieldException</b> représente une exception lorsqu'un champ électrique n'est pas défini à l'endroit calculé.
 * 
 * @author Simon Vezina
 * @since 2020-06-16
 * @version 2022-04-08
 */
public class SUndefinedFieldException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 985478302009607108L;

  /**
   * La constante <b>DEFAULT_MESSAGE_WITH_POSITION</b> représente le message d'erreur par défaut avec l'usage d'une coordonnée.
   */
  private static final String DEFAULT_MESSAGE_WITH_POSITION = "Le champ électrique n'est pas défini à cette position : ";
  
  /**
   * La constante <b>DEFAULT_MESSAGE</b> représente le message d'erreur par défaut sans l'usage d'une coordonnée.
   */
  private static final String DEFAULT_MESSAGE = "Le champ électique n'est pas défini.";
  
  /**
   * Constructeur d'une exception stipulant qu'un champ ne peut pas être évalué.
   */
  public SUndefinedFieldException()
  {
    super(DEFAULT_MESSAGE);
  }
  
  /**
   * Constructeur d'une exception stipulant qu'un champ ne peut pas être évalué à une certaine coordonnée.
   * 
   * @param message Le message détaillant pourquoi le champ ne peut pas être défini.
   * @param r La position où le champ n'est pas défini.
   */
  public SUndefinedFieldException(String message, SVector3d r)
  {
    super(message + ". " + DEFAULT_MESSAGE_WITH_POSITION + r.toString());
  }
  
  /**
   * Constructeur d'une exception stipulant qu'un champ ne peut par être évalué à une certaine coordonnée.
   * 
   * @param r La position où le champ n'est pas défini.
   */
  public SUndefinedFieldException(SVector3d r)
  {
    super(DEFAULT_MESSAGE_WITH_POSITION + r.toString());
  }
    
}
