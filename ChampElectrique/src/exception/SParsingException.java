/**
 * 
 */
package sim.exception;

/**
 * ...
 * 
 * @author Simon Vezina
 * @since 2019-11-24
 * @version 2019-11-24
 */
public class SParsingException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -1205999317156656553L;

  /**
   * 
   */
  public SParsingException() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message
   */
  public SParsingException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param cause
   */
  public SParsingException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message
   * @param cause
   */
  public SParsingException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public SParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    // TODO Auto-generated constructor stub
  }

}
