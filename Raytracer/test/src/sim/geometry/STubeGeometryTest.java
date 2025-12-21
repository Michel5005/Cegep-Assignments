/**
 * 
 */
package sim.geometry;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.math.SVector3d;
import sim.util.SLog;


/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>STubeGeometry</b>.
 * 
 * @author Simon Vézina
 * @since 2016-04-18
 * @version 2019-03-05
 */
public class STubeGeometryTest {

  /**
   * Test de la méthode <b>isInsideExtremity</b> dans le cas d'un tube aligné selon l'axe z.
   */
  @Test
  public void isInsideExtremityTest1()
  {
    try{
      STubeGeometry geometry = new STubeGeometry(new SVector3d(0.0, 0.0, -1.0), new SVector3d(0.0, 0.0, 1.0), 1.0);
      
      Assert.assertTrue(geometry.isInsideExtremity(new SVector3d(0.0, 0.0, 0.0)));
      Assert.assertTrue(geometry.isInsideExtremity(new SVector3d(0.0, 0.0, 0.5)));
      Assert.assertTrue(geometry.isInsideExtremity(new SVector3d(0.0, 0.0, -0.5)));
      
      Assert.assertFalse(geometry.isInsideExtremity(new SVector3d(0.0, 0.0, 1.2)));
      Assert.assertFalse(geometry.isInsideExtremity(new SVector3d(0.0, 0.0, -1.2)));
      
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("STubeGeometryTest ---> Test non effectué : public void isInsideExtremityTest1()");
    } 
  }
  
  /**
   * Test de la méthode <b>isInsideExtremity</b> dans le cas d'un tube aligné sur un axe quelconque.
   */
  @Test
  public void isInsideExtremityTest2()
  {
    try{
      
      SVector3d P1 = new SVector3d(5.0, 5.0, -2.0);
      SVector3d P2 = new SVector3d(6.0, 7.0, 10.0);
      
      STubeGeometry geometry = new STubeGeometry(P1, P2, 3.0);
      
      Assert.assertTrue(geometry.isInsideExtremity(P1.add(P2.substract(P1).multiply(0.2))));
      Assert.assertTrue(geometry.isInsideExtremity(P1.add(P2.substract(P1).multiply(0.6))));
      Assert.assertTrue(geometry.isInsideExtremity(P1.add(P2.substract(P1).multiply(0.9))));
      
      Assert.assertFalse(geometry.isInsideExtremity(P1.add(P2.substract(P1).multiply(-1.2))));
      Assert.assertFalse(geometry.isInsideExtremity(P1.add(P2.substract(P1).multiply(1.2))));
      
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("STubeGeometryTest ---> Test non effectué : public void isInsideExtremityTest2()");
    } 
  }
  
  /**
   * Test de l'intersection entre un rayon à l'origine et le tube aligné selon l'axe z. 
   * Le rayon voyage selon l'axe x. L'intersection sera composé d'un temps négatif (à rejeter) et un temps positif. 
   */
  @Test
  public void intersectionTest1()
  {
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 0.0), new SVector3d(1.0, 0.0, 0.0), 1.0);
      
    STubeGeometry geometry = new STubeGeometry(new SVector3d(0.0, 0.0, -1.0), new SVector3d(0.0, 0.0, 1.0), 1.0);
      
    SRay calculated_solution = geometry.intersection(ray);
      
    // Orientation de la normale à la surface à l'extérieur du tube.
    SVector3d expected_normal = new SVector3d(1.0, 0.0, 0.0);
    
    SRay expected_solution = ray.intersection(geometry, expected_normal, 1.0);
      
    Assert.assertEquals(expected_solution, calculated_solution);
  }

  /**
   * Test de l'intersection <u>avec 2 succès</u> entre un rayon et le tube dans un configuration quelconque. Le test est réalisé avec un tube est assez long.
   */
  @Test
  public void intersectionTest2a()
  {
    SRay ray = new SRay(new SVector3d(1.0, 2.0, 3.0), new SVector3d(2.0, 2.0, 1.0), 1.0);
      
    STubeGeometry geometry = new STubeGeometry(new SVector3d(5.0, 5.0, -2.0), new SVector3d(6.0, 7.0, 10.0), 3.0);
      
    SRay calculated_solution = geometry.intersection(ray);
      
    SRay expected_solution = ray.intersection(geometry, new SVector3d(-0.7809976398562594, -0.6022192201512141, 0.16545300667989066), 1.0603359106580434);
      
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon et le tube dans un configuration quelconque. Le test est réalisé avec un tube qui n'est pas assez long.
   */
  @Test
  public void intersectionTest2b()
  {
    SRay ray = new SRay(new SVector3d(1.0, 2.0, 3.0), new SVector3d(2.0, 2.0, 1.0), 1.0);
      
    STubeGeometry geometry = new STubeGeometry(new SVector3d(5.0, 5.0, -2.0), new SVector3d(6.0, 7.0, 2.0), 3.0);
      
    SRay calculated_solution = geometry.intersection(ray);
      
    SRay expected_solution = ray;
      
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon et le tube dans un configuration quelconque. Le test est réalisé avec un rayon qui voyage dans la mauvaise direction.
   */
  @Test
  public void intersectionTest2c()
  {
    SRay ray = new SRay(new SVector3d(1.0, 2.0, 3.0), new SVector3d(-2.0, -2.0, -1.0), 1.0);
      
    STubeGeometry geometry = new STubeGeometry(new SVector3d(5.0, 5.0, -2.0), new SVector3d(6.0, 7.0, 10.0), 3.0);
      
    SRay calculated_solution = geometry.intersection(ray);
      
    SRay expected_solution = ray;
          
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de l'intersection <u>avec 2 succès</u> entre un rayon et le tube. Le 1ier temps est négatif. Le 2ième temps est positif à l'intérieur des extrémités.
   */
  @Test
  public void intersectionTest3()
  {
    SRay ray = new SRay(new SVector3d(-2.0, 0.0, -2.0), new SVector3d(1.0, 0.0, 2.0), 1.0);
    
    STubeGeometry geometry = new STubeGeometry(new SVector3d(0.0, 0.0, 0.0), new SVector3d(5.0, 0.0, 0.0), 5.0);
      
    SRay calculated_solution = geometry.intersection(ray);
      
    // Orientation de la normale à la surface à l'extérieur du tube.
    SVector3d expected_normal = new SVector3d(0.0, 0.0, 1.0);
    
    SRay expected_solution = ray.intersection(geometry, expected_normal, 3.5);
          
    Assert.assertEquals(expected_solution, calculated_solution);

  }
  
  /**
   * Test de l'intersection <u>avec 2 succès</u> entre un rayon et le tube. Le 1ier temps est positif, mais à l'extérieur des extrémités. Le 2ième temps est positif et à l'intérieur des extrémités.
   */
  @Test
  public void intersectionTest4()
  {
    SRay ray = new SRay(new SVector3d(-3.0, 0.0, -1.0), new SVector3d(2.0, 0.0, 1.0), 1.0);
      
    STubeGeometry geometry = new STubeGeometry(new SVector3d(0.0, 0.0, 0.0), new SVector3d(0.0, 0.0, 5.0), 2.0);
      
    SRay calculated_solution = geometry.intersection(ray);
      
    // Orientation de la normale à la surface à l'extérieur du tube.
    SVector3d expected_normal = new SVector3d(1.0, 0.0, 0.0);
    
    SRay expected_solution = ray.intersection(geometry, expected_normal, 2.5);
          
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de l'intersection <u>avec 1 succès</u> entre un rayon et le tube où le rayon par initialement sur la surface du tube vers l'intérieur du tube.
   *
   * Le test sera satisfait sous le critère t > 0, car t = 0-.
   */
  @Test
  public void intersectionTest5a()
  {
    SRay ray = new SRay(new SVector3d(5.0, 2.0, 0.0), new SVector3d(0.0, 2.0, 0.0), 1.0);
      
    STubeGeometry geometry = new STubeGeometry(new SVector3d(5.0, 5.0, -2.0), new SVector3d(5.0, 5.0, 2.0), 3.0);
      
    SRay calculated_solution = geometry.intersection(ray);
      
    // Orientation de la normale à la surface à l'extérieur du tube.
    SVector3d expected_normal = new SVector3d(0.0, 1.0, 0.0);
    
    SRay expected_solution = ray.intersection(geometry, expected_normal, 3.0);
      
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de l'intersection <u>avec 1 succès</u> entre un rayon et le tube où le rayon par initialement sur la surface du tube vers l'intérieur du tube.
   *
   * Le test ne sera pas satisfait sous le critère t > 0, car t = 0+.
   */
  @Test
  public void intersectionTest5b()
  {
    // Mettre la position du rayon légèrement à l'extérieur du tube et le lancer vers l'intérieur.
    SRay ray = new SRay(new SVector3d(5.0, 2.0-SRay.getEpsilon()*0.001, 0.0), new SVector3d(0.0, 2.0, 0.0), 1.0);
      
    STubeGeometry geometry = new STubeGeometry(new SVector3d(5.0, 5.0, -2.0), new SVector3d(5.0, 5.0, 2.0), 3.0);
      
    SRay calculated_solution = geometry.intersection(ray);
      
    // Orientation de la normale à la surface à l'extérieur du tube.
    SVector3d expected_normal = new SVector3d(0.0, 1.0, 0.0);
    
    SRay expected_solution = ray.intersection(geometry, expected_normal, 3.0);
      
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
   
}//fin de la classe STubeGeometryTest
