package sim.util;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
/**
 * ...
 * 
 * @author Simon Vezina
 * @since 2020-06-03
 * @version 2020-06-04
 */
public class SMouseEventHandler {

  private int last_mouse_x;
  private int last_mouse_y;
  
  private int delta_mouse_x;
  private int delta_mouse_y;
  
  boolean[] mouse_button_pressed;
  
  /**
   * ...
   */
  public SMouseEventHandler()
  {
    mouse_button_pressed = new boolean[MouseInfo.getNumberOfButtons() + 1];
    
    // P.S. Avec cette valeur par défaut, il ne peut pas avoir de "CHECK" pour un delta trop grand initialement.
    this.last_mouse_x = 0;
    this.last_mouse_y = 0;
    
    this.delta_mouse_x = 0;
    this.delta_mouse_y = 0;
  }
  
  /**
   * ...
   * 
   * @param button_id
   * @return
   * @throws IllegalArgumentException
   */
  public boolean isButtonPressed(int button_id) throws IllegalArgumentException
  {
    if(button_id > this.mouse_button_pressed.length-1)
      throw new IllegalArgumentException("Erreur SMouseEvent 001 : Le bouton numéro #" + button_id + " n'est pas valide. Il n'y a que " + this.mouse_button_pressed.length + " de reconnu.");
    
    return this.mouse_button_pressed[button_id];
  }
  
  /**
   * ...
   * 
   * @return
   */
  public int getLastX()
  {
    return this.last_mouse_x;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public int getLastY()
  {
    return this.last_mouse_x;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public int getDeltaX()
  {
    return this.delta_mouse_x;
  }
  
  /**
   * Remarque : Le delta Y peut être négatif si la souris se déplace vers le bas !
   * 
   * @return
   */
  public int getDeltaY()
  {
    return this.delta_mouse_y;
  }
  /**
   * ...
   * 
   * @param e
   */
  public void pressed(MouseEvent e)
  {
    this.mouse_button_pressed[e.getButton()] = true;
  }
  
  /**
   * ...
   * 
   * @param e
   */
  public void released(MouseEvent e)
  {
    this.mouse_button_pressed[e.getButton()] = false;
  }
  
  /**
   * ...
   * 
   * @param e
   */
  public void clicked(MouseEvent e)
  {
    
  }
  
  /**
   * ...
   * 
   * @param e
   */
  public void moved(MouseEvent e)
  {
    recordMouseMovement(e);
  }
  
  /**
   * ...
   * 
   * @param e
   */
  public void dragged(MouseEvent e)
  {
    recordMouseMovement(e);
  }
  
  /**
   * ...
   * 
   * @param e
   */
  private void recordMouseMovement(MouseEvent e)
  {
    // Évaluer le déplacement.
    this.delta_mouse_x = e.getX() - this.last_mouse_x;
    this.delta_mouse_y = e.getY() - this.last_mouse_y;
    
    // Enregistrer la dernière position de la souris.
    this.last_mouse_x = e.getX();
    this.last_mouse_y = e.getY();
  }
  
}
