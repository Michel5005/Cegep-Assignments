/**
 * 
 */
package moteurPhysique;

/**
 * La classe <b>SNoImplementationException</b> repr�sente une exception lanc� lorsqu'une m�thode n'est pas impl�ment�e.
 * 
 * @author Simon V�zina
 * @since 2015-01-25
 * @version 2018-05-18
 */
public class SNoImplementationException extends RuntimeException {

  /**
   * La constante <b>DEFAULT_MESSAGE</b> repr�sente le message par d�faut d'une exception de type <b>SNoImplementationException</b>.
   */
  private static final String DEFAULT_MESSAGE = "La m�thode n'a pas �t� impl�ment�e.";
  
  /**
   * La variable <b>serialVersionUID<b> correspond � un code d'identification de l'exception.
   */
	private static final long serialVersionUID = -9055388404780943775L;

	/**
   * Constructeur de l'exception avec un message d'erreur par d�faut.
   */
  public SNoImplementationException()
  {
    super(DEFAULT_MESSAGE);
  }
	
	/**
   * Constructeur de l'exception avec message d'erreur.
   * 
   * @param message - Le message de l'erreur.
   */
	public SNoImplementationException(String message)
	{
		super(message);
	}

	/**
   * Constructeur de l'exception avec message d'erreur et cause de l'exception.
   * 
   * @param message - Le message de l'erreur.
   * @param cause - La cause de l'erreur.
   */
	public SNoImplementationException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
}//fin de la classe SNoImplementationException
