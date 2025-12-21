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
 * JUnit test de la classe <b>SGeometricUtil</b>.
 * 
 * @author Simon Vézina
 * @since 2015-11-24
 * @version 2022-01-12
 */
public class SGeometricUtilTest {

  /**
   * Test de la méthode <b>isOneRectanglePerimeter</b> dans un cas simple.
   */
  @Test
  public void isOneRectanglePerimeterTest() 
  {
    Assert.assertEquals(1, SGeometricUtil.isOnRectanglePerimeter(0, 0, 2.0, 2.0, 1.1, 0.9));
    Assert.assertEquals(1, SGeometricUtil.isOnRectanglePerimeter(0, 0, 2.0, 2.0, 0.9, 1.1));
    Assert.assertEquals(-1, SGeometricUtil.isOnRectanglePerimeter(0, 0, 2.0, 2.0, 0.9, 0.9));
    Assert.assertEquals(-1, SGeometricUtil.isOnRectanglePerimeter(0, 0, 5.0, 4.0, 2.0, 1.5));
    Assert.assertEquals(0, SGeometricUtil.isOnRectanglePerimeter(0, 0, 2.0, 3.0, 1.0, 0.5));
    Assert.assertEquals(0, SGeometricUtil.isOnRectanglePerimeter(0, 0, 2.0, 3.0, 0.5, 1.5));
  }
  
  /**
   * Test de la méthode <b>isOnCerclePerimeter</b> dans un cas simple.
   */
  @Test
  public void isOnCerclePerimeterTest()
  {
    Assert.assertEquals(0, SGeometricUtil.isOnCerclePerimeter(0, 0, 1.0, 1.0, 0.0));
    Assert.assertEquals(0, SGeometricUtil.isOnCerclePerimeter(0, 0, 1.0, 0.0, 1.0));
    Assert.assertEquals(1, SGeometricUtil.isOnCerclePerimeter(0, 0, 1.0, 2.0, 0.0));
    Assert.assertEquals(1, SGeometricUtil.isOnCerclePerimeter(0, 0, 1.0, 0.0, 2.0));
    Assert.assertEquals(-1, SGeometricUtil.isOnCerclePerimeter(0, 0, 1.0, 0.5, 0.0));
    Assert.assertEquals(-1, SGeometricUtil.isOnCerclePerimeter(0, 0, 1.0, 0.0, 0.5));
  }
  
  /**
   * Test de la méthode <b>isOnEllipsePerimeter</b> dans un cas simple.
   */
  @Test
  public void isOnEllipsePerimeterTest()
  {
    Assert.assertEquals(0, SGeometricUtil.isOnEllipsePerimeter(0, 0, 4.0, 2.0, 2.0, 0.0));
    Assert.assertEquals(0, SGeometricUtil.isOnEllipsePerimeter(0, 0, 4.0, 2.0, 0.0, 1.0));
    Assert.assertEquals(1, SGeometricUtil.isOnEllipsePerimeter(0, 0, 4.0, 2.0, 2.5, 0.0));
    Assert.assertEquals(1, SGeometricUtil.isOnEllipsePerimeter(0, 0, 4.0, 2.0, 0.0, 1.5));
    Assert.assertEquals(-1, SGeometricUtil.isOnEllipsePerimeter(0, 0, 4.0, 2.0, 1.5, 0.0));
    Assert.assertEquals(-1, SGeometricUtil.isOnEllipsePerimeter(0, 0, 4.0, 2.0, 0.0, 0.5));
  }
  
  /**
   * Test de la méthode <b>isOnTwoParallelsPlanesSurface</b> dans un cas simple.
   */
  @Test
  public void isOnTwoParallelsPlanesSurfaceTest()
  {
    SVector3d r_plane1 = new SVector3d(0.0, 0.0, 0.0);
    SVector3d r_plane2 = new SVector3d(5.0, 0.0, 0.0);
    SVector3d axis_1to2 = new SVector3d(1.0, 0.0, 0.0).normalize();
    
    Assert.assertEquals(0, SGeometricUtil.isOnTwoParallelsPlanesSurface(r_plane1, r_plane2, axis_1to2, new SVector3d(0.0, 5.0, 5.0)));
    Assert.assertEquals(0, SGeometricUtil.isOnTwoParallelsPlanesSurface(r_plane1, r_plane2, axis_1to2, new SVector3d(5.0, 5.0, 5.0)));
    
    Assert.assertEquals(1, SGeometricUtil.isOnTwoParallelsPlanesSurface(r_plane1, r_plane2, axis_1to2, new SVector3d(-1.0, 5.0, 5.0)));
    Assert.assertEquals(1, SGeometricUtil.isOnTwoParallelsPlanesSurface(r_plane1, r_plane2, axis_1to2, new SVector3d(6.0, 5.0, 5.0)));
    
    Assert.assertEquals(-1, SGeometricUtil.isOnTwoParallelsPlanesSurface(r_plane1, r_plane2, axis_1to2, new SVector3d(1.0, 5.0, 5.0)));
    Assert.assertEquals(-1, SGeometricUtil.isOnTwoParallelsPlanesSurface(r_plane1, r_plane2, axis_1to2, new SVector3d(4.0, 5.0, 5.0)));
  }
  
  /**
   * Test de la méthode <b>isOnSphereSurface</b> dans un cas simple.
   */
  @Test
  public void isOnSphereSurfaceTest() 
  {
    SVector3d r_s = new SVector3d(0.0, 0.0, 0.0);
    double R = 1.0;
    
    SVector3d outside = new SVector3d(2.0, 2.0, 2.0);
    SVector3d on = new SVector3d(3.4, 5.58, -4.932).normalize();
    SVector3d inside = new SVector3d(-32.5, -4.78, 36.2).normalize().multiply(0.7);
    
    Assert.assertEquals(1, SGeometricUtil.isOnSphereSurface(r_s, R, outside));
    Assert.assertEquals(0, SGeometricUtil.isOnSphereSurface(r_s, R, on));
    Assert.assertEquals(-1, SGeometricUtil.isOnSphereSurface(r_s, R, inside));
  }

  /**
   * Test de la méthode <b>outsideSphereNormal</b> dans un cas simple.
   */
  @Test
  public void outsideSphereNormalTest()
  {
    // Test sur la surface.
    Assert.assertEquals(new SVector3d(1.0, 0.0, 0.0), SGeometricUtil.outsideSphereNormal(SVector3d.ORIGIN, 2.0, SVector3d.UNITARY_X.multiply(2.0)));
  
    // Test à l'extérieur de la surface.
    try {
      SGeometricUtil.outsideSphereNormal(SVector3d.ORIGIN, 2.0, SVector3d.UNITARY_X.multiply(3.0));
    }catch(IllegalArgumentException e) {
      // le test est un succès.
    }
    
    // Test à l'intérieur de la surface.
    try {
      SGeometricUtil.outsideSphereNormal(SVector3d.ORIGIN, 2.0, SVector3d.UNITARY_X.multiply(1.0));
    }catch(IllegalArgumentException e) {
      // le test est un succès.
    }
  }
  
  
  @Test
  public void isOnInfiniteTubeSurfaceTest()
  {
    try {
      throw new SNoImplementationException();
    }catch(SNoImplementationException e) {
      SLog.logWriteLine("SGeometricUtilTest ---> Test non effectué : public void isOnInfiniteTubeSurfaceTest()");
    }
  }
  
  @Test
  public void outsideInfiniteTubeNormalTest()
  {
    try {
      throw new SNoImplementationException();
    }catch(SNoImplementationException e) {
      SLog.logWriteLine("SGeometricUtilTest ---> Test non effectué : public void outsideInfiniteTubeNormalTest()");
    }
  }
  
  @Test
  public void inOnTwoInfinitesConesSurfaceTest()
  {
    try {
      throw new SNoImplementationException();
    }catch(SNoImplementationException e) {
      SLog.logWriteLine("SGeometricUtilTest ---> Test non effectué : public void inOnTwoInfinitesConesSurfaceTest()");
    }
  }
  
  @Test
  public void isOnCylinderSurfaceTest()
  {
    try {
      throw new SNoImplementationException();
    }catch(SNoImplementationException e) {
      SLog.logWriteLine("SGeometricUtilTest ---> Test non effectué : public void isOnCylinderSurfaceTest()");
    }
  }
  
  @Test
  public void isOnConeSurfaceTest()
  {
    try {
      throw new SNoImplementationException();
    }catch(SNoImplementationException e) {
      SLog.logWriteLine("SGeometricUtilTest ---> Test non effectué : public void isOnConeSurfaceTest()");
    }
  }
  
  @Test
  public void isOnTorusSurfaceTest() 
  {
    try {
      throw new SNoImplementationException();
    }catch(SNoImplementationException e) {
      SLog.logWriteLine("SGeometricUtilTest ---> Test non effectué : public void isOnTorusSurfaceTest()");
    }
  }
  
  @Test
  public void outsideTorusNormalTest() 
  {
    try {
      throw new SNoImplementationException();
    }catch(SNoImplementationException e) {
      SLog.logWriteLine("SGeometricUtilTest ---> Test non effectué : public void outsideTorusNormalTest()");
    }
  }
  
  @Test
  public void isOnAlignedCubeSurfaceTest()
  {
    Assert.assertEquals(-1, SGeometricUtil.isOnAlignedCubeSurface(new SVector3d(3.0, 0.0, 0.0), 2.0, new SVector3d(3.0,0.0,0.0)));
    Assert.assertEquals(-1, SGeometricUtil.isOnAlignedCubeSurface(new SVector3d(3.0, 0.0, 0.0), 2.0, new SVector3d(2.1,0.0,0.0)));
    Assert.assertEquals(0, SGeometricUtil.isOnAlignedCubeSurface(new SVector3d(3.0, 0.0, 0.0), 2.0, new SVector3d(2.0,0.0,0.0)));
    Assert.assertEquals(1, SGeometricUtil.isOnAlignedCubeSurface(new SVector3d(3.0, 0.0, 0.0), 2.0, new SVector3d(0.0,0.0,0.0)));
    Assert.assertEquals(1, SGeometricUtil.isOnAlignedCubeSurface(new SVector3d(3.0, 0.0, 0.0), 2.0, new SVector3d(1.0,0.0,0.0)));
    
    Assert.assertEquals(-1, SGeometricUtil.isOnAlignedCubeSurface(new SVector3d(3.0, 3.0, 3.0), 2.0, new SVector3d(3.9,2.9,3.9)));
    Assert.assertEquals(0, SGeometricUtil.isOnAlignedCubeSurface(new SVector3d(3.0, 3.0, 3.0), 2.0, new SVector3d(4.0,3.5,3.5)));
    Assert.assertEquals(1, SGeometricUtil.isOnAlignedCubeSurface(new SVector3d(3.0, 3.0, 3.0), 2.0, new SVector3d(4.0,4.0,5.0)));
  }
  
  /**
   * Test de la méthode <b>spericalCoordinate</b> dans le cas d'une sphère à l'origine où l'on localise les vecteurs unitaire.
   */
  @Test
  public void spericalCoordinateTest1()
  {
    Assert.assertEquals(SVector3d.UNITARY_X, SGeometricUtil.sphericalCoordinate(SVector3d.ORIGIN, 1.0, 0.0, Math.PI/2.0));
    Assert.assertEquals(SVector3d.UNITARY_Y, SGeometricUtil.sphericalCoordinate(SVector3d.ORIGIN, 1.0, Math.PI/2.0, Math.PI/2.0));
    Assert.assertEquals(SVector3d.UNITARY_Z, SGeometricUtil.sphericalCoordinate(SVector3d.ORIGIN, 1.0, 0.0, 0.0));
    
    Assert.assertEquals(SVector3d.UNITARY_X.multiply(-1.0), SGeometricUtil.sphericalCoordinate(SVector3d.ORIGIN, 1.0, Math.PI, Math.PI/2.0));
    Assert.assertEquals(SVector3d.UNITARY_Y.multiply(-1.0), SGeometricUtil.sphericalCoordinate(SVector3d.ORIGIN, 1.0, 3.0*Math.PI/2.0, Math.PI/2.0));
    Assert.assertEquals(SVector3d.UNITARY_Z.multiply(-1.0), SGeometricUtil.sphericalCoordinate(SVector3d.ORIGIN, 1.0, 0.0, Math.PI));
  }
  
}