/**
 * 
 */
package sim.graphics.camera;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.math.SVector3d;
import sim.util.SLog;

/**
 * JUnit Test de la classe <b>SCamera</b>.
 * 
 * @author Simon Vézina
 * @since 2015-11-28
 * @version 2022-05-29
 */
public class SCameraTest {

  /**
   * Test de la méthode <b>getUp</b> dans le cas où le <i>up</i> est selon l'axe z.
   */
  @Test
  public void getUpTest()
  {
    try {
      
      SCamera camera = new SCamera(new SVector3d(0.0, 0.0, 0.0), new SVector3d(1.0, 0.0, 0.0), new SVector3d(1.0, 0.0, 1.0));
    
      Assert.assertEquals(new SVector3d(0.0, 0.0, 1.0), camera.getUp());
    }catch(SNoImplementationException e) {
      SLog.logWriteLine("SCameraTest ---> Test non effectué : public void public void getUpTest()");
    }
  }

}
