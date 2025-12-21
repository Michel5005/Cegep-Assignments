package sim.util;

import sim.exception.SRuntimeException;

/**
 * La classe SChronometer permet d'évaluer le temps écoulé avec une résolution en milliseconde.
 * @author Simon Vezina
 * @version 2014-12-08
 * @version 2021-08-22
 */
public class SChronometer 
{
	private long start_time;	// temps écoulé en milliseconde depuis January 1, 1970, 00:00:00 GMT pour signaler le début du chronomètre.
	private boolean isStarted;
	
	private long stop_time;		// temps écoulé en milliseconde depuis January 1, 1970, 00:00:00 GMT pour signaler l'arrêt du chronomètre.
	private boolean isStoped;
	
	/**
	 * Constructeur du chronometre.
	 */
	public SChronometer()
	{
		reset();
	}
	
	/**
	 * Méthode pour initialiser le chronometre.
	 */
	public void start()
	{	
		start_time = System.currentTimeMillis();	// Temps à partir de January 1, 1970, 00:00:00 GMT
		isStarted = true;
	}
	
	/**
	 * Méthode pour arrêter la mesure de l'écoulement du temps depuis l'appel de la méthode start().
	 */
	public void stop()
	{
		stop_time = System.currentTimeMillis();		// Temps à partir de January 1, 1970, 00:00:00 GMT
		isStoped = true;
	}
	
	/**
	 * Méthode pour réinitialiser le chronomètre.
	 */
	public void reset()
	{
		start_time = 0;
		isStarted = false;
		
		stop_time = 0;
		isStoped = false;
	}
	
	/**
	 * Méthode pour déterminer si le chronomètre a été initialisé.
	 * 
	 * @return <b>true</b> si le chronomètre a été intialisé et <b>false</b> sinon.
	 */
	public boolean asStart()
	{
	  return isStarted;
	}
	
	/**
	 * Méthode pour obtenir l'écoulement du temps entre l'appel de la fonction start() et stop()
	 * ou bien le temps écoulé entre l'appel de la fonction start() et cette fonction courante.
	 * 
	 * @return Temps écoulé en seconde.
	 * @throws SRuntimeException S'il l'appel de la méthode start() n'a pas encore initialisée le chronomètre.
	 */
	public double getTime()throws SRuntimeException
	{
		return (double) getTimeMilliseconde() / 1000;
	}
	
	/**
	 * Méthode pour obtenir l'écoulement du temps entre l'appel de la fonction start() et stop().
	 * ou bien le temps écoulé entre l'appel de la fonction start() et cette fonction courante.
	 * 
	 * @return Temps écoulé en milliseconde.
	 * @throws SRuntimeException S'il l'appel de la méthode start() n'a pas encore initialisée le chronomètre.
	 */
	public long getTimeMilliseconde()throws SRuntimeException
	{
		if(!isStarted)
			throw new SRuntimeException("Erreur SChronometer 001 : La méthode start() du chronomètre n'a pas encore initialisée le chronomètre.");
		
		if(isStoped)
			return stop_time - start_time;
		else
		{
			//Si le chronomètre n'a pas été stoppé, retourner le temps écoulé entre le start() et l'appel de la fonction getTimeMilliseconde()
			long current_time = System.currentTimeMillis();
			return current_time - start_time;
		}
	}

  @Override
  public String toString()
  {
    return "SChronometer [TEMPS ÉCOULÉ : " + getTime() + " s ]";
  }
	
	
}
