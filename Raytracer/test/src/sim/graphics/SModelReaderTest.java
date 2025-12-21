/**
 * 
 */
package sim.graphics;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.geometry.SBTriangleGeometry;
import sim.geometry.STriangleGeometry;
import sim.math.SMath;
import sim.math.SVector3d;
import sim.math.SVectorUV;
import sim.util.SBrowser;
import sim.util.SBufferedReader;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SModelReader</b>.
 * @author Simon Vezina
 * @since 2020-01-07
 * @version 2020-01-08
 */
public class SModelReaderTest {

  /**
   * Test de la méthode <b>transformTriangleGeometry</b> pour un modèle à 1 triangle où il y aura uniquement une translation.
   * 
   * @throws FileNotFoundException
   * @throws SConstructorException
   * @throws IOException
   */
  @Test
  public void transformTriangleGeometryTest1() throws FileNotFoundException, SConstructorException, IOException
  {
    try{
      
      SLog.setConsoleLog(false);
      
      SBrowser browser = new SBrowser();
      browser.forward("test");
      browser.forward("test_graphics");
     
      FileReader fr = new FileReader(browser.findFile("triangleTest1.txt"));
      SBufferedReader sbr = new SBufferedReader(fr);
      
      // La méthode a tester sera exécutée lors de cette lecture.
      SModelReader reader = new SModelReader(sbr);
      
     // Voici le triangle attendu.
      SVector3d P0 = new SVector3d(4.5+2.0, 3.4+2.0, 7.8+2.0);
      SVector3d P1 = new SVector3d(1.8+2.0, -3.2+2.0, 2.8+2.0);
      SVector3d P2 = new SVector3d(2.8+2.0, -1.8+2.0, -3.9+2.0);
      STriangleGeometry expected_triangle = new STriangleGeometry(P0, P1, P2);
      
      // Définir la précision du Equals.
      SMath.EPSILON = 1e-5;
      SModel model = (SModel) reader.getValue();
      
      Assert.assertEquals(expected_triangle, (STriangleGeometry)model.getPrimitiveList().get(0).getGeometry());
    }
    catch(SNoImplementationException e){
      SLog.setConsoleLog(true);
      SLog.logWriteLine("SModelReaderTest ---> Test non effectué : public void transformTriangleGeometryTest1()");
    }
  }

  /**
   * Test de la méthode <b>transformTriangleGeometry</b> pour un modèle à 1 triangle où il y aura une translation, rotation et homothésie (back coverage).
   * 
   * @throws FileNotFoundException
   * @throws SConstructorException
   * @throws IOException
   */
  @Test
  public void transformTriangleGeometryTest2() throws FileNotFoundException, SConstructorException, IOException
  {
    try{
      
      SLog.setConsoleLog(false);
      
      SBrowser browser = new SBrowser();
      browser.forward("test");
      browser.forward("test_graphics");
     
      FileReader fr = new FileReader(browser.findFile("triangleTest2.txt"));
      SBufferedReader sbr = new SBufferedReader(fr);
      
      // La méthode a tester sera exécutée lors de cette lecture.
      SModelReader reader = new SModelReader(sbr);
      
     // Voici le triangle attendu.
      SVector3d P0 = new SVector3d(16.212846571589434, -5.8488855409105085, 8.800000190734863);
      SVector3d P1 = new SVector3d(7.232590079628015, -0.687005734791402, -4.400000095367432);
      SVector3d P2 = new SVector3d(-1.5355340745201311, 9.49533198172984, -1.5999999046325688);
      STriangleGeometry expected_triangle = new STriangleGeometry(P0, P1, P2);
         
      // Définir la précision du Equals.
      SModel model = (SModel) reader.getValue();
      
      Assert.assertEquals(expected_triangle, (STriangleGeometry)model.getPrimitiveList().get(0).getGeometry());
    }
    catch(SNoImplementationException e){
      SLog.setConsoleLog(true);
      SLog.logWriteLine("SModelReaderTest ---> Test non effectué : public void transformTriangleGeometryTest2()");
    }
  }
  
  /**
   * Test de la méthode <b>transformBTriangleGeometry</b> pour un modèle à 1 triangle où il y aura uniquement une translation.
   * 
   * @throws FileNotFoundException
   * @throws SConstructorException
   * @throws IOException
   */
  @Test
  public void transformBTriangleGeometryTest1() throws FileNotFoundException, SConstructorException, IOException
  {
    try{
      
      SLog.setConsoleLog(false);
      
      SBrowser browser = new SBrowser();
      browser.forward("test");
      browser.forward("test_graphics");
     
      FileReader fr = new FileReader(browser.findFile("btriangleTest1.txt"));
      SBufferedReader sbr = new SBufferedReader(fr);
      
      // La méthode a tester sera exécutée lors de cette lecture.
      SModelReader reader = new SModelReader(sbr);
      
     // Voici le triangle attendu.
      SVector3d P0 = new SVector3d(4.5+2.0, 3.4+2.0, 7.8+2.0);
      SVector3d P1 = new SVector3d(1.8+2.0, -3.2+2.0, 2.8+2.0);
      SVector3d P2 = new SVector3d(2.8+2.0, -1.8+2.0, -3.9+2.0);
      
      SVector3d N0 = new SVector3d(1.0, 0.0, 0.0);
      SVector3d N1 = new SVector3d(0.0, 1.0, 0.0);
      SVector3d N2 = new SVector3d(0.0, 0.0, 1.);
      
      SVectorUV UV0 = new SVectorUV(0.0, 0.0);
      SVectorUV UV1 = new SVectorUV(0.0, 1.0);
      SVectorUV UV2 = new SVectorUV(1.0, 1.0);
      
      SBTriangleGeometry expected_triangle = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2, UV0, UV1, UV2);
      
      // Définir la précision du Equals.
      SMath.EPSILON = 1e-5;
      SModel model = (SModel) reader.getValue();
      
      Assert.assertEquals(expected_triangle, (SBTriangleGeometry)model.getPrimitiveList().get(0).getGeometry());
    }
    catch(SNoImplementationException e){
      SLog.setConsoleLog(true);
      SLog.logWriteLine("SModelReaderTest ---> Test non effectué : public void transformBTriangleGeometryTest1()");
    }
  }
  
  /**
   * Test de la méthode <b>transformBTriangleGeometry</b> pour un modèle à 1 triangle où il y aura une translation, rotation et homothésie (back coverage).
   * 
   * @throws FileNotFoundException
   * @throws SConstructorException
   * @throws IOException
   */
  @Test
  public void transformBTriangleGeometryTest2() throws FileNotFoundException, SConstructorException, IOException
  {
    try{
      
      SLog.setConsoleLog(false);
      
      SBrowser browser = new SBrowser();
      browser.forward("test");
      browser.forward("test_graphics");
     
      FileReader fr = new FileReader(browser.findFile("btriangleTest2.txt"));
      SBufferedReader sbr = new SBufferedReader(fr);
      
      // La méthode a tester sera exécutée lors de cette lecture.
      SModelReader reader = new SModelReader(sbr);
      
     // Voici le triangle attendu.
      SVector3d P0 = new SVector3d(16.212846571589434, -5.8488855409105085, 8.800000190734863);
      SVector3d P1 = new SVector3d(7.232590079628015, -0.687005734791402, -4.400000095367432);
      SVector3d P2 = new SVector3d(-1.5355340745201311, 9.49533198172984, -1.5999999046325688);
      
      SVector3d N0 = new SVector3d(0.7071067811865476, 0.7071067811865476, 0.0);
      SVector3d N1 = new SVector3d(0.0, 0.0, 1.0);
      SVector3d N2 = new SVector3d(0.7071067811865475, -0.7071067811865476, 0.0);
      
      SVectorUV UV0 = new SVectorUV(0.0, 0.0);
      SVectorUV UV1 = new SVectorUV(0.0, 1.0);
      SVectorUV UV2 = new SVectorUV(1.0, 1.0);
      
      SBTriangleGeometry expected_triangle = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2, UV0, UV1, UV2);
         
      // Définir la précision du Equals.
      SModel model = (SModel) reader.getValue();
      
      Assert.assertEquals(expected_triangle, (SBTriangleGeometry)model.getPrimitiveList().get(0).getGeometry());
    }
    catch(SNoImplementationException e){
      SLog.setConsoleLog(true);
      SLog.logWriteLine("SModelReaderTest ---> Test non effectué : public void transformBTriangleGeometryTest2()");
    }
  }
  
  
}
