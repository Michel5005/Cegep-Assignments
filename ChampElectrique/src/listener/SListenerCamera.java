/**
 * 
 */
package sim.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import sim.exception.SNoImplementationException;
import sim.graphics.camera.SMovableCamera;
import sim.util.SLog;
import sim.util.SMouseEventHandler;

/**
 * La classe <b>SListenerCamera</b> est une caméra qui implémente l'interface <b>MouseMotionListener</b> et <b>MouseListener</b>.
 * 
 * @author Simon Vezina
 * @since 2020-06-15
 * @version 2022-05-30
 */
public class SListenerCamera implements  MouseMotionListener, MouseListener {

  private static final boolean REVERSE_MODE = false;
  
  private static final double DEFAULT_MOVEMENT_SENSIBILITY = 0.1;
  private static final double DEFAULT_ROTATION_SENSIBILITY = 0.2;
  
  /**
   * La variable <b>mouse</b> représente les informations obtenues par la souris qui sera communiquée à la caméra.
   */
  private final SMouseEventHandler mouse;
  
  /**
   * La variable <b>camera</b> représente la caméra pouvant être manipulée.
   */
  private SMovableCamera camera;
  
  /**
   * La variable <b>movement_sensibility</b> représente le nombre d'unité de déplacement associé à chaque pixel de mouvement de la souris.
   */
  private double movement_sensibility;
  
  /**
   * La variable <b>rotation_sensibility</b> représente le nombre de degré de rotation associé à chaque pixel de mouvement de la souris.
   */
  private double rotation_sensibility;
  
  /**
   * 
   */
  public SListenerCamera(SMovableCamera camera) 
  {
    this.mouse = new SMouseEventHandler();
    this.camera = camera;
    
    this.movement_sensibility = DEFAULT_MOVEMENT_SENSIBILITY;
    this.rotation_sensibility = DEFAULT_ROTATION_SENSIBILITY;
  }

  /**
   * Méthode pour obtenir la camera du listener.
   * 
   * @return La camera
   */
  public SMovableCamera getCamera()
  {
    return this.camera;
  }
  
  /**
   * Méthode pour définir la camera du listener.
   * 
   * @param camera La camera.
   */
  public void setCamera(SMovableCamera camera)
  {
    this.camera = camera;
  }
  
  /**
   * Méthode pour définir la sensibilité du mouvement de la caméra.
   * Cette valeur correspondra au déplacement unitaire de la caméra par pixel de déplacement de la souris.
   * 
   * @param sensibility La sensibilité.
   */
  public void setMovementSensibility(double sensibility)
  {
    this.movement_sensibility = sensibility;
  }
  
  /**
   * Méthode pour définir la sensibilité de rotation de la camera.
   * Cette valeur correspondra au nombre de degré de rotation de la caméra par pixel de déplacement de la souris.
   * 
   * @param sensibility La sensibilité.
   */
  public void setRotationSensibility(double sensibility)
  {
    this.rotation_sensibility = sensibility;
  }
  
  @Override
  public void mouseClicked(MouseEvent e) 
  {
    mouse.clicked(e);
  }

  @Override
  public void mousePressed(MouseEvent e) 
  {
    mouse.pressed(e);
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    mouse.released(e);
  }

  @Override
  public void mouseEntered(MouseEvent e)
  {
        
  }

  @Override
  public void mouseExited(MouseEvent e)
  {
        
  }

  @Override
  public void mouseDragged(MouseEvent e)
  {
    mouse.dragged(e);
    
    // En raison d'un laboratoire, nous allons introduire un try/catch sur l'exécution du mouvement.
    try {
      
      // Effectuer le déplacement de la caméra en fonction du mode.
      if(REVERSE_MODE)
        reverseCameraMovement();
      else
        cameraMovement();
      
    }catch(SNoImplementationException exception) {
      SLog.logWriteLine("Message SListenerCamera --- Une opération sur la caméra n'est pas admissible, car une méthode requise n'a pas été implémentée.");
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) 
  {
    mouse.moved(e);    
  }

  /**
   * Méthode pour réaliser le mouvement de la camera en mode normal.
   * @param e L'événement de souris.
   */
  protected void cameraMovement()
  {
    // BOUTON 1 : Enfoncé.
    if(mouse.isButtonPressed(1))
    {
      // BOUTON 3 : Non enfoncé 
      if(!mouse.isButtonPressed(3))
      {
        // Rotation Yaw (gauche-droite) 
        // Remarque : Lorque la souris effectue un déplacement vers la droite, la rotation est positif.
        camera.rotationYaw(mouse.getDeltaX()*rotation_sensibility);
        
        // Rotation Pitch(haut-bas).
        // Remarque : Lorque la souris effectue un déplacement vers le haut, la rotation est négative.
        camera.rotationPitch(mouse.getDeltaY()*rotation_sensibility*-1.0);
      }
      else
      {
        // Rotation Roll (autour de l'axe avant).
        camera.rotationRoll(mouse.getDeltaX()*rotation_sensibility);
      }
    }else
    {
      // BOUTON 3 : Enfoncé.
      if(mouse.isButtonPressed(3))
      {
        // Déplacement vers l'avant-arrière.
        // Remarque : Lorque la souris effectue un déplacement vers le haut, le déplacement est négatif.
        camera.moveBack(mouse.getDeltaY()*movement_sensibility);
        
        // Déplacement vers la droite-gauche.
        // Remarque : Lorque la souris effectue un déplacement vers la droite, le déplacement est positive.
        camera.moveRight(mouse.getDeltaX()*movement_sensibility);
      }
    }
  }
  
  /**
   * Méthode pour réaliser le mouvement de la camera en mode inverse (mode touchpad).
   * 
   * @param e L'événement de souris.
   */
  protected void reverseCameraMovement()
  {
    // BOUTON 1 : Enfoncé.
    if(mouse.isButtonPressed(1))
    {
      // BOUTON 3 : Non enfoncé 
      if(!mouse.isButtonPressed(3))
      {
        // Rotation Yaw (gauche-droite) 
        // Remarque : Lorque la souris effectue un déplacement vers la droite, la rotation est positif.
        // On va inverser.
        camera.rotationYaw(mouse.getDeltaX()*rotation_sensibility*-1.0);
        
        // Rotation Pitch(haut-bas).
        // Remarque : Lorque la souris effectue un déplacement vers le haut, la rotation est négative.
        // On va inverser.
        camera.rotationPitch(mouse.getDeltaY()*rotation_sensibility);
      }
      else
      {
        // Rotation Roll (autour de l'axe avant).
        camera.rotationRoll(mouse.getDeltaX()*rotation_sensibility);
      }
    }else
      // BOUTON 3 : Enfoncé.
      if(mouse.isButtonPressed(3))
      {
        // Déplacement vers l'avant-arrière.
        // Remarque : Lorque la souris effectue un déplacement vers le haut, le déplacement est négatif.
        // On va inverser.
        camera.moveFront(mouse.getDeltaY()*movement_sensibility);
        
        // Déplacement vers la droite-gauche.
        // Remarque : Lorque la souris effectue un déplacement vers la droite, le déplacement est positive.
        // On va inverser.
        camera.moveLeft(mouse.getDeltaX()*movement_sensibility);
      }
  }
  
}
