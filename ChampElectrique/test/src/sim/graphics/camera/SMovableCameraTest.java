package sim.graphics.camera;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.math.SVector3d;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SMovableCamera</b>.
 * 
 * @author Simon Vézina
 * @since 2022-05-29
 * @version 2022-06-02
 */
public class SMovableCameraTest {

  /**
   * Test de la méthode <b>moveFront</b> dans le cas d'un déplacement vers l'avant selon l'axe x avec un déplacement positif.
   * Ce test ne nécessite pas l'implémentation du produit vectoriel, car on utilise le constructeur vide de la caméra.
   * Dans ce constructeur, la caméra regarde dans la direction -x.
   */
  @Test
  public void moveFrontTest1a()
  {
    try {
      
      SVectorCamera camera = new SVectorCamera();
      double distance = 15.0;
            
      SVectorCamera expected = camera.clone();
      expected.setPosition(new SVector3d(camera.getPosition().getX() - distance, 0.0, 0.0));
      
      // Méthode à tester.
      camera.moveFront(distance);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SMovableCameraTest ---> Test non effectué : public void moveFrontTest1a()");
    }
  }
  
  /**
   * Test de la méthode <b>moveFront</b> dans le cas d'un déplacement vers l'avant selon l'axe x avec un déplacement négatif.
   * Ce test ne nécessite pas l'implémentation du produit vectoriel, car on utilise le constructeur vide de la caméra.
   * Dans ce constructeur, la caméra regarde dans la direction -x.
   */
  @Test
  public void moveFrontTest1b()
  {
    try {
      
      SVectorCamera camera = new SVectorCamera(SVector3d.ORIGIN, SVector3d.UNITARY_X, SVector3d.UNITARY_Z);
      double distance = -15.0;
            
      SVectorCamera expected = camera.clone();
      expected.setPosition(new SVector3d(camera.getPosition().getX() + distance, 0.0, 0.0));
      
      // Méthode à tester.
      camera.moveFront(distance);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SMovableCameraTest ---> Test non effectué : public void moveFrontTest1b()");
    }
  }
  
  /**
   * Test de la méthode <b>moveFront</b> dans le cas d'un déplacement vers l'avant selon l'axe x avec un déplacement positif.
   */
  @Test
  public void moveFrontTest2a()
  {
    try {
      
      SVectorCamera camera = new SVectorCamera(SVector3d.ORIGIN, SVector3d.UNITARY_X, SVector3d.UNITARY_Z);
      double distance = 15.0;
            
      SVectorCamera expected = camera.clone();
      expected.setPosition(new SVector3d(distance, 0.0, 0.0));
      
      // Méthode à tester.
      camera.moveFront(distance);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SMovableCameraTest ---> Test non effectué : public void moveFrontTest2a()");
    }
  }
  
  /**
   * Test de la méthode <b>moveFront</b> dans le cas d'un déplacement vers l'avant selon l'axe x avec un déplacement négatif.
   */
  @Test
  public void moveFrontTest2b()
  {
    try {
      
      SVectorCamera camera = new SVectorCamera(SVector3d.ORIGIN, SVector3d.UNITARY_X, SVector3d.UNITARY_Z);
      double distance = -15.0;
            
      SVectorCamera expected = camera.clone();
      expected.setPosition(new SVector3d(distance, 0.0, 0.0));
      
      // Méthode à tester.
      camera.moveFront(distance);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SMovableCameraTest ---> Test non effectué : public void moveFrontTest2b()");
    }
  }
  
  /**
   * Test de la méthode <b>move</b> dans le cas d'un déplacement vers l'avant selon l'axe x avec un déplacement positif.
   * Ce test ne nécessite pas l'implémentation du produit vectoriel, car on utilise le constructeur vide de la caméra.
   */
  @Test
  public void moveTest1a()
  {
    try {
      
      SVectorCamera camera = new SVectorCamera();
      double distance = 15.0;
            
      SVectorCamera expected = camera.clone();
      expected.setPosition(new SVector3d(camera.getPosition().getX() + distance, 0.0, 0.0));
      
      // Méthode à tester.
      camera.move(SVector3d.UNITARY_X, distance);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SMovableCameraTest ---> Test non effectué : public void moveTest1()");
    }
  }
  
  /**
   * Test de la méthode <b>move</b> dans le cas d'un déplacement vers l'avant selon l'axe x avec un déplacement négatif.
   * Ce test ne nécessite pas l'implémentation du produit vectoriel, car on utilise le constructeur vide de la caméra.
   */
  @Test
  public void moveTest1b()
  {
    try {
      
      SVectorCamera camera = new SVectorCamera();
      double distance = -15.0;
            
      SVectorCamera expected = camera.clone();
      expected.setPosition(new SVector3d(camera.getPosition().getX() + distance, 0.0, 0.0));
      
      // Méthode à tester.
      camera.move(SVector3d.UNITARY_X, distance);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SMovableCameraTest ---> Test non effectué : public void moveTest1()");
    }
  }
  
  /**
   * Test de la méthode <b>move</b> dans le cas d'un déplacement vers l'avant selon l'axe x avec un déplacement positif.
   */
  @Test
  public void moveTest2()
  {
    try {
      
      SVectorCamera camera = new SVectorCamera(SVector3d.ORIGIN, SVector3d.UNITARY_X, SVector3d.UNITARY_Z);
      double distance = 15.0;
            
      SVectorCamera expected = camera.clone();
      expected.setPosition(new SVector3d(distance, 0.0, 0.0));
      
      // Méthode à tester.
      camera.move(SVector3d.UNITARY_X, distance);
      
      Assert.assertEquals(expected, camera);  
    
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SMovableCameraTest ---> Test non effectué : public void moveTest2()");
    }
  }
  
}
