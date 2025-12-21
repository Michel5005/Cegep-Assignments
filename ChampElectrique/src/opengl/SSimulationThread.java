/**
 * 
 */
package sim.opengl;

import java.util.function.DoubleConsumer;

import sim.util.SChronometer;

/**
 * La classe <b>SSimulationThread</b> est un objet représentant une thread réalisant des simulations.
 *  
 * @author Simon Vezina
 * @since 2021-08-15
 * @version 2022-01-27
 */
public class SSimulationThread extends Thread {

  /**
   * ...
   */
  private static boolean DEFAULT_PLAY = true;
  
  /**
   * La constante <b>MINIMUM_SLEEP</b> représente le temps d'arrêt minimale dans la thread à effectuer peut importe le temps de calcul requis.
   */
  private static final long MINIMUM_SLEEP = 5;
  
  /**
   * ...
   */
  static final double DEFAULT_TIME_STEP = 0.01;
  
  /**
   * ...
   */
  final DoubleConsumer simulation_function;
  
  /**
   * ...
   */
  protected double time_step;
  
  /**
   * ...
   */
  protected double simulation_time;
  
  /**
   * ...
   */
  protected SChronometer real_time_chrono;
  
  /**
   * ...
   */
  protected double step_time_required;
  
  /**
   * ...
   */
  protected boolean is_simulation_finished;
  
  /**
   * La variable <b>play</b> détermine s'il y aura évolution de la simulation. 
   * Lorsque ce paramètre sera <i>false</i>, la simulation sera stoppé.
   */
  protected boolean play;
  
  /**
   * ...
   * 
   * @param function
   */
  public SSimulationThread(DoubleConsumer function)
  {
    this.play = DEFAULT_PLAY;
    
    this.simulation_function = function;
    
    this.time_step = DEFAULT_TIME_STEP;
       
    this.simulation_time = 0.0;
    this.real_time_chrono = new SChronometer();
    
    this.step_time_required = Double.POSITIVE_INFINITY;
    
    this.is_simulation_finished = false;
  }

  /**
   * ...
   * 
   * @return
   */
  public double getTimeStep()
  {
    return this.time_step;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public double getSimulationTime()
  {
    return this.simulation_time;
  }
  
  /**
   * Méthode pour obtenir le temps réel écoulé depuis le début du calcul de la simulation.
   * 
   * @return Le temps réel écoulé en seconde.
   */
  public double getRealTime()
  {
    if(this.real_time_chrono.asStart())
      return this.real_time_chrono.getTime();
    else
      return 0.0;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public double getStepTimeRequired()
  {
    return this.step_time_required;
  }
  
  /**
   * Méthode pour définir le temps de simulation par itération.
   * 
   * @param time Le temps de simulation.
   */
  public void setTimeStep(double time)
  {
    this.time_step = time;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public boolean isPlaying()
  {
    return this.play;
  }
  
  /**
   * ...
   * 
   * @param play
   */
  public void setPlaying(boolean play)
  {
    this.play = play;
  }
  
  /**
   * Méthode pour inverser le mode de la simulation (action <--> arrêté).
   */
  public void switchPlaying()
  {
    this.play = !this.play;
  }
  
  /**
   * Méthode pour terminer la simulation. L'usage de cette méthode forcera la fin de la boucle de la méthode run().
   */
  public void endSimulation()
  {
    is_simulation_finished = true;
  }
  
  @Override
  public void run()
  {
    try {
      
      // Initialiser le chronomètre mesurant le temps réel utilisé pour calculer la simulation.
      this.real_time_chrono.start();
      
      while(!is_simulation_finished) 
      {
        // Outil pour mesurer le temps requis pour réaliser la simulation.
        SChronometer chrono = new SChronometer();
        chrono.start();
        
        //--------------------------------------------------
        // Réaliser la simulation si le mode play est activé
        //--------------------------------------------------
        if(this.play)
        {
          simulation_function.accept(this.time_step);
        
          chrono.stop();
        
          // Enregistrer le temps requis pour réaliser la simulation.
          step_time_required = (double) (chrono.getTime());
        
          // Modifier le temps de simulation.
          this.simulation_time += this.time_step;
        }
        
        // Réaliser la pause.
        Thread.sleep(MINIMUM_SLEEP);  
      }
    } catch(InterruptedException e) {
      e.printStackTrace();
    } 
  }
  
}
