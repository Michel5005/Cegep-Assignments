package sim.sound;

public class WavFileException extends Exception
{
	/**
   * 
   */
  private static final long serialVersionUID = -6410332944176290211L;

  public WavFileException()
	{
		super();
	}

	public WavFileException(String message)
	{
		super(message);
	}

	public WavFileException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public WavFileException(Throwable cause) 
	{
		super(cause);
	}
}
