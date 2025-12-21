package sim.graphics.camera;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.math.SVector3d;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SVectorCamera</b>.
 * 
 * @author Simon Vézina
 * @since 2022-05-29
 * @version 2022-05-29
 */
public class SVectorCameraTest {

  /**
   * Test de la méthode <b>rotationYaw</b> dans le cas d'une rotation de 30 degrée dans le sens positif, donc vers la droite.
   */
  @Test
  public void rotationYawTest1a() 
  {
    try {
      
      SVectorCamera camera = new SVectorCamera(SVector3d.ORIGIN, SVector3d.UNITARY_X, SVector3d.UNITARY_Z);
      double degrees = 30.0;
            
      SVectorCamera expected = new SVectorCamera(new SVector3d(0.0, 0.0, 0.0), new SVector3d(0.8660254037844387, -0.5, 0.0), SVector3d.UNITARY_Z);
            
      // Méthode à tester.
      camera.rotationYaw(degrees);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SVectorCameraTest ---> Test non effectué : public void rotationYawTest1a()");
    }
  }

  /**
   * Test de la méthode <b>rotationYaw</b> dans le cas d'une rotation de 30 degrée dans le sens négatif, donc vers la gauche.
   */
  @Test
  public void rotationYawTest1b() 
  {
    try {
      
      SVectorCamera camera = new SVectorCamera(SVector3d.ORIGIN, SVector3d.UNITARY_X, SVector3d.UNITARY_Z);
      double degrees = -30.0;
            
      SVectorCamera expected = new SVectorCamera(new SVector3d(0.0, 0.0, 0.0), new SVector3d(0.8660254037844387, 0.5, 0.0), SVector3d.UNITARY_Z);
            
      // Méthode à tester.
      camera.rotationYaw(degrees);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SVectorCameraTest ---> Test non effectué : public void rotationYawTest1b()");
    }
  }

  /**
   * Test de la méthode <b>rotationPitch</b> dans le cas d'une rotation de 30 degrée dans le sens positif, donc vers le haut.
   */
  @Test
  public void rotationPitchTest1a() 
  {
    try {
      
      SVectorCamera camera = new SVectorCamera(SVector3d.ORIGIN, SVector3d.UNITARY_X, SVector3d.UNITARY_Z);
      double degrees = 30.0;
            
      SVectorCamera expected = new SVectorCamera(new SVector3d(0.0, 0.0, 0.0), new SVector3d(0.8660254037844387, 0.0, 0.5), SVector3d.UNITARY_Z);
            
      // Méthode à tester.
      camera.rotationPitch(degrees);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SVectorCameraTest ---> Test non effectué : public void rotationPitchTest1a()");
    }
  }
  
  /**
   * Test de la méthode <b>rotationPitch</b> dans le cas d'une rotation de 30 degrée dans le sens négatif, donc vers le bas.
   */
  @Test
  public void rotationPitchTest1b() 
  {
    try {
      
      SVectorCamera camera = new SVectorCamera(SVector3d.ORIGIN, SVector3d.UNITARY_X, SVector3d.UNITARY_Z);
      double degrees = -30.0;
            
      SVectorCamera expected = new SVectorCamera(new SVector3d(0.0, 0.0, 0.0), new SVector3d(0.8660254037844387, 0.0, -0.5), SVector3d.UNITARY_Z);
            
      // Méthode à tester.
      camera.rotationPitch(degrees);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SVectorCameraTest ---> Test non effectué : public void rotationPitchTest1b()");
    }
  }
  
  /**
   * Test de la méthode <b>rotationRoll</b> dans le cas d'une rotation de 30 degrée dans le sens positif, donc vers la droite.
   */
  @Test
  public void rotationRollTest1a() 
  {
    try {
      
      SVectorCamera camera = new SVectorCamera(SVector3d.ORIGIN, SVector3d.UNITARY_X, SVector3d.UNITARY_Z);
      double degrees = 30.0;
            
      // Remarque : Tourner du côté droit sera du côté négatif de l'axe y.
      SVectorCamera expected = new SVectorCamera(new SVector3d(0.0, 0.0, 0.0), SVector3d.UNITARY_X, new SVector3d(0.0, -0.5, 0.8660254037844387));
            
      // Méthode à tester.
      camera.rotationRoll(degrees);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SVectorCameraTest ---> Test non effectué : public void rotationRollTest1a()");
    }
  }
  
  /**
   * Test de la méthode <b>rotationPitch</b> dans le cas d'une rotation de 30 degrée dans le sens négatif, donc vers la gauche.
   */
  @Test
  public void rotationRollTest1b() 
  {
    try {
      
      SVectorCamera camera = new SVectorCamera(SVector3d.ORIGIN, SVector3d.UNITARY_X, SVector3d.UNITARY_Z);
      double degrees = -30.0;
            
      // Remarque : Tourner du côté gauche sera du côté positif de l'axe y.
      SVectorCamera expected = new SVectorCamera(new SVector3d(0.0, 0.0, 0.0), SVector3d.UNITARY_X, new SVector3d(0.0, 0.5, 0.8660254037844387));
            
      // Méthode à tester.
      camera.rotationRoll(degrees);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SVectorCameraTest ---> Test non effectué : public void rotationRollTest1a()");
    }
  }
  
}
