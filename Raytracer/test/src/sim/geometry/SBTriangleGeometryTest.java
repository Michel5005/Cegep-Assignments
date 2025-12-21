/**
 * 
 */
package sim.geometry;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.math.SMatrix4x4;
import sim.math.SVector3d;
import sim.math.SVectorUV;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SBTriangleGeometry</b>.
 * 
 * @author Simon Vezina
 * @since 2016-05-13
 * @version 2020-02-11
 */
public class SBTriangleGeometryTest {

  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon aligné selon l'axe z et un triangle dans le plan xy.
   * Le rayon voyage en s'éloignant du triangle
   */
  @Test
  public void intersectionTest1a()
  {
    try {
      
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 1.0), new SVector3d(0.0, 0.0, 1.0), 1.0);
    
    SVector3d P0 = new SVector3d(1.0, 1.0, 0.0);
    SVector3d P1 = new SVector3d(1.0, -1.0, 0.0);
    SVector3d P2 = new SVector3d(-1.0, 0.0, 0.0);
    
    SVector3d N0 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N1 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N2 = new SVector3d(0.0, 0.0, 1.0);
    
    SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.    
    SRay expected_solution = ray;
        
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest1a()");
    }
  }
  
  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon aligné selon l'axe z et un triangle dans le plan xy.
   * Le rayon voyage parallèlement au plan du triangle.
   */
  @Test
  public void intersectionTest1b()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 1.0), new SVector3d(1.0, 0.0, 0.0), 1.0);
    
    SVector3d P0 = new SVector3d(1.0, 1.0, 0.0);
    SVector3d P1 = new SVector3d(1.0, -1.0, 0.0);
    SVector3d P2 = new SVector3d(-1.0, 0.0, 0.0);
    
    SVector3d N0 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N1 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N2 = new SVector3d(0.0, 0.0, 1.0);
    
    SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.    
    SRay expected_solution = ray;
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest1b()");
    }
  }
  
  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon et un triangle dans le plan xy.
   * Le rayon passe à l'extérieur du segment 0-1.
   */
  @Test
  public void intersectionTest2a()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(1.2, 0.0, 1.0), new SVector3d(0.0, 0.0, -1.0), 1.0);
    
    SVector3d P0 = new SVector3d(1.0, 1.0, 0.0);
    SVector3d P1 = new SVector3d(1.0, -1.0, 0.0);
    SVector3d P2 = new SVector3d(-1.0, 0.0, 0.0);
    
    SVector3d N0 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N1 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N2 = new SVector3d(0.0, 0.0, 1.0);
    
    SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.    
    SRay expected_solution = ray;
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest2a()");
    }
  }
  
  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon et un triangle dans le plan xy.
   * Le rayon passe à l'extérieur du segment 1-2.
   */
  @Test
  public void intersectionTest2b()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, -1.0, 1.0), new SVector3d(0.0, 0.0, -1.0), 1.0);
    
    SVector3d P0 = new SVector3d(1.0, 1.0, 0.0);
    SVector3d P1 = new SVector3d(1.0, -1.0, 0.0);
    SVector3d P2 = new SVector3d(-1.0, 0.0, 0.0);
    
    SVector3d N0 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N1 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N2 = new SVector3d(0.0, 0.0, 1.0);
    
    SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.    
    SRay expected_solution = ray;
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest2b()");
    }
  }
  
  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon et un triangle dans le plan xy.
   * Le rayon passe à l'extérieur du segment 2-0.
   */
  @Test
  public void intersectionTest2c()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, 1.0, 1.0), new SVector3d(0.0, 0.0, -1.0), 1.0);
    
    SVector3d P0 = new SVector3d(1.0, 1.0, 0.0);
    SVector3d P1 = new SVector3d(1.0, -1.0, 0.0);
    SVector3d P2 = new SVector3d(-1.0, 0.0, 0.0);
    
    SVector3d N0 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N1 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N2 = new SVector3d(0.0, 0.0, 1.0);
    
    SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.    
    SRay expected_solution = ray;
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest2c()");
    }
  }
  
   /**
   * Test de l'intersection <u>sans succès</u> entre un rayon et un triangle dans le plan xy.
   * Ce test est fait avec une vitesse non parallèle à l'axe z.
   */
  @Test
  public void intersectionTest2d()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 1.0), new SVector3d(-1.0, -1.0, -1.0), 1.0);
    
    SVector3d P0 = new SVector3d(1.0, 1.0, 0.0);
    SVector3d P1 = new SVector3d(1.0, -1.0, 0.0);
    SVector3d P2 = new SVector3d(-1.0, 0.0, 0.0);
    
    SVector3d N0 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N1 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N2 = new SVector3d(0.0, 0.0, 1.0);
    
    SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.    
    SRay expected_solution = ray;
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest2d()");
    }
  }
  
  /**
   * Test de l'intersection <u>avec succès</u> entre un rayon aligné selon l'axe z et un triangle dans le plan xy.
   * Dans ce test, la normale à la surface n'est pas interpolée et il n'y a pas de coordonnée uv.
   */
  @Test
  public void intersectionTest3a()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 1.0), new SVector3d(0.0, 0.0, -1.0), 1.0);
    
    SVector3d P0 = new SVector3d(1.0, 1.0, 0.0);
    SVector3d P1 = new SVector3d(1.0, -1.0, 0.0);
    SVector3d P2 = new SVector3d(-1.0, 0.0, 0.0);
    
    SVector3d N0 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N1 = new SVector3d(0.0, 0.0, 1.0);
    SVector3d N2 = new SVector3d(0.0, 0.0, 1.0);
    
    SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    SRay expected_solution = ray.intersection(geometry, new SVector3d(0.0, 0.0, 1.0), new SVectorUV(0.0, 0.0), 1.0);
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest3a()");
    }
  }

  /**
   * Test de l'intersection <u>avec succès</u> entre un rayon aligné selon l'axe z et un triangle dans le plan xy.
   * Dans ce test, il y aura interpolation de la normale et il n'y a pas de coordonnée uv.
   */
  @Test
  public void intersectionTest3b()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 1.0), new SVector3d(0.0, 0.0, -1.0), 1.0);
    
    SVector3d P0 = new SVector3d(1.0, 1.0, 0.0);
    SVector3d P1 = new SVector3d(1.0, -1.0, 0.0);
    SVector3d P2 = new SVector3d(-1.0, 0.0, 0.0);
    
    SVector3d N0 = new SVector3d(0.0, 0.0, 1.0).normalize();
    SVector3d N1 = new SVector3d(1.0, 1.0, 1.0).normalize();
    SVector3d N2 = new SVector3d(0.0, 1.0, 1.0).normalize();
    
    SVectorUV default_interpolated_uv = new SVectorUV(0.0, 0.0);
    
    SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    SVector3d interpolated_normal = new SVector3d(0.14433756729740646, 0.4978909578906803, 0.7478909578906803);
    
    SRay expected_solution = ray.intersection(geometry, interpolated_normal, default_interpolated_uv, 1.0);
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest3b()");
    }
  }
  
  /**
   * Test de l'intersection <u>avec succès</u> entre un rayon aligné selon l'axe z et un triangle dans le plan xy.
   * Dans ce test, il y aura interpolation de la normale et de la coordonnée de texture uv.
   */
  @Test
  public void intersectionTest3c()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 1.0), new SVector3d(0.0, 0.0, -1.0), 1.0);
    
    SVector3d P0 = new SVector3d(1.0, 1.0, 0.0);
    SVector3d P1 = new SVector3d(1.0, -1.0, 0.0);
    SVector3d P2 = new SVector3d(-1.0, 0.0, 0.0);
    
    SVector3d N0 = new SVector3d(0.0, 0.0, 1.0).normalize();
    SVector3d N1 = new SVector3d(1.0, 1.0, 1.0).normalize();
    SVector3d N2 = new SVector3d(0.0, 1.0, 1.0).normalize();
    
    SVectorUV UV0 = new SVectorUV(0.0, 0.0);
    SVectorUV UV1 = new SVectorUV(1.0, 1.0);
    SVectorUV UV2 = new SVectorUV(0.0, 1.0);
    
    SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2, UV0, UV1, UV2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    SVector3d interpolated_normal = new SVector3d(0.14433756729740646, 0.4978909578906803, 0.7478909578906803);
    SVectorUV interpolated_uv = new SVectorUV(0.25, 0.75);
    
    SRay expected_solution = ray.intersection(geometry, interpolated_normal, interpolated_uv, 1.0);
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest3c()");
    }
  }
  
  /**
   * Test de l'intersection <u>avec succès</u> entre un rayon et un triangle quelconque.
   * Dans ce test, on n'effectue pas d'interpolation sur la normale ni les coordonnées UV.
   */
  @Test
  public void intersectionTest4a()
  {
    try {
    
    // Position de l'intersection.
    SVector3d r_int = new SVector3d(3.4, 6.7, 3.5);
    
    // Temps de l'intersection.
    double t = 5.9;
    
    // Orientation du rayon.
    SVector3d ray_direction = new SVector3d(-7.5, 3.8, -4.2);
      
    // Base du triangle.
    SVector3d b1 = new SVector3d(-7.8, 3.1, 4.9);
    SVector3d b2 = new SVector3d(1.7, -2.6, 9.3);
    
    // Points du triangle.
    SVector3d P0 = r_int.add(b1.multiply(-2.5)).add(b2.multiply(-1.8));
    SVector3d P1 = r_int.add(b1.multiply(-1.5)).add(b2.multiply(2.8));
    SVector3d P2 = r_int.add(b1.multiply(3.7)).add(b2.multiply(0.7));
    
    SRay ray = new SRay(r_int.add(ray_direction.multiply(-t)), ray_direction, 1.0);
    
    SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Ces valeurs de normale et UV ne sont pas le fruit d'une interpolation, mais les valeurs par défaut attribuable au triangle dans le test.
    SVector3d interpolated_normal = new SVector3d(-0.45106718384945754, -0.8775030829421608, -0.16287030140919792);
    SVectorUV interpolated_uv = new SVectorUV(0.0, 0.0);
    
    SRay expected_solution = ray.intersection(geometry, interpolated_normal, interpolated_uv, t);
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest4a()");
    }
  }
  
  /**
   * Test de l'intersection <u>avec succès</u> entre un rayon et un triangle quelconque.
   * Dans ce test, on vérifie que la normale et les coordonnées UV sont bien interpolées.
   */
  @Test
  public void intersectionTest4b()
  {
    try {
    
    // Position de l'intersection.
  	SVector3d r_int = new SVector3d(3.4, 6.7, 3.5);
  	
  	// Temps de l'intersection.
  	double t = 5.9;
  	
  	// Orientation du rayon.
  	SVector3d ray_direction = new SVector3d(-7.5, 3.8, -4.2);
  		
  	// Base du triangle.
  	SVector3d b1 = new SVector3d(-7.8, 3.1, 4.9);
  	SVector3d b2 = new SVector3d(1.7, -2.6, 9.3);
  	
  	// Points du triangle.
  	SVector3d P0 = r_int.add(b1.multiply(-2.5)).add(b2.multiply(-1.8));
  	SVector3d P1 = r_int.add(b1.multiply(-1.5)).add(b2.multiply(2.8));
  	SVector3d P2 = r_int.add(b1.multiply(3.7)).add(b2.multiply(0.7));
  	
  	SVector3d N0 = new SVector3d(0.0, 0.0, 1.0).normalize();
    SVector3d N1 = new SVector3d(1.0, 1.0, 1.0).normalize();
    SVector3d N2 = new SVector3d(0.0, 1.0, 1.0).normalize();
    
    SVectorUV UV0 = new SVectorUV(0.0, 0.0);
    SVectorUV UV1 = new SVectorUV(1.0, 1.0);
    SVectorUV UV2 = new SVectorUV(0.0, 1.0);
	
    SRay ray = new SRay(r_int.add(ray_direction.multiply(-t)), ray_direction, 1.0);
    
    SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2, UV0, UV1, UV2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Valeur de la normale et UV après interpolation.
    SVector3d interpolated_normal = new SVector3d(0.10894657270257797, 0.3725490237982545, 0.8110578631525966);
    SVectorUV interpolated_uv = new SVectorUV(0.18870099923136155, 0.5614911606456578);
    
    SRay expected_solution = ray.intersection(geometry, interpolated_normal, interpolated_uv, t);
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest4b()");
    }
  }
  
  /**
   * Test de la méthode <b>intersection</b> dans le cas d'un rayon lancé depuis la surface du triangle.
   * 
   * Ce test sera validé sous la condition t > 0.
   */
  @Test 
  public void intersectionTest5a()
  {
    try {
      
      SVector3d P0 = new SVector3d(1.0, 1.0, 1.0);
      SVector3d P1 = new SVector3d(2.0, 1.0, 1.0);
      SVector3d P2 = new SVector3d(2.0, 2.0, 1.0);
      
      SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2);
      
      SRay ray = new SRay(new SVector3d(1.5, 1.2, 1.0), new SVector3d(0.0, 0.0, 1.0), 1.0);
      
      Assert.assertEquals(ray, geometry.intersection(ray));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest5a()");
    }
  }
  
  /**
   * Test de la méthode <b>intersection</b> dans le cas d'un rayon lancé depuis la surface du triangle.
   * 
   * Ce test sera non validé sous la condition t > 0, car la solution est t > 0+.
   */
  @Test 
  public void intersectionTest5b()
  {
    try {
      
      SVector3d P0 = new SVector3d(1.0, 1.0, 1.0);
      SVector3d P1 = new SVector3d(2.0, 1.0, 1.0);
      SVector3d P2 = new SVector3d(2.0, 2.0, 1.0);
      
      SBTriangleGeometry geometry = new SBTriangleGeometry(P0, P1, P2);
      
      SRay ray = new SRay(new SVector3d(1.5, 1.2, 1.0-SRay.getEpsilon()*0.01), new SVector3d(0.0, 0.0, 1.0), 1.0);
      
      Assert.assertEquals(ray, geometry.intersection(ray));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void intersectionTest5b()");
    }
  }
  
  /**
   * Test de la méthode <b>transform</b> dans le cas de la matrice identité avec un triangle quelconque.
   * Le résultat attendu sera un triangle sans changement.
   */
  @Test
  public void transformTest1()
  {
    try{
      
      SMatrix4x4 transformation = SMatrix4x4.identity();
      
      SVector3d P0 = new SVector3d(4.5, 3.4, 7.8);
      SVector3d P1 = new SVector3d(1.8, -3.2, 2.8);
      SVector3d P2 = new SVector3d(2.8, -1.8, -3.9);
      
      SVector3d N0 = new SVector3d(1.0, 0.0, 0.0);
      SVector3d N1 = new SVector3d(0.0, 1.0, 0.0);
      SVector3d N2 = new SVector3d(0.0, 0.0, 1.0);
      
      SVectorUV UV0 = new SVectorUV(1.0, 0.0);
      SVectorUV UV1 = new SVectorUV(0.0, 1.0);
      SVectorUV UV2 = new SVectorUV(1.0, 1.0);
      
      SBTriangleGeometry triangle = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2, UV0, UV1, UV2);
      
      // Vérifier que le triangle reste inchangé.
      Assert.assertEquals(triangle, triangle.transform(transformation));   
      
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void transformTest1()");
    }
  }
  
  /**
   * Test de la méthode <b>transform</b> dans le cas de la matrice de translation avec un triangle quelconque.
   * Le résultat attendu sera un triangle déplacé par la translation sans changer l'orientaion des normales ni coordonnée uv.
   */
  @Test
  public void transformTest2()
  {
    try{
      
      SVector3d translation = new SVector3d(3.4, 2.3, 7.8);
      SMatrix4x4 transformation = SMatrix4x4.translation(translation);
      
      SVector3d P0 = new SVector3d(4.5, 3.4, 7.8);
      SVector3d P1 = new SVector3d(1.8, -3.2, 2.8);
      SVector3d P2 = new SVector3d(2.8, -1.8, -3.9);
      
      SVector3d N0 = new SVector3d(1.0, 0.0, 0.0);
      SVector3d N1 = new SVector3d(0.0, 1.0, 0.0);
      SVector3d N2 = new SVector3d(0.0, 0.0, 1.0);
      
      SVectorUV UV0 = new SVectorUV(1.0, 0.0);
      SVectorUV UV1 = new SVectorUV(0.0, 1.0);
      SVectorUV UV2 = new SVectorUV(1.0, 1.0);
      
      SBTriangleGeometry triangle = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2, UV0, UV1, UV2);
      SBTriangleGeometry expected_triangle = new SBTriangleGeometry(P0.add(translation), P1.add(translation), P2.add(translation), N0, N1, N2, UV0, UV1, UV2);
          
      Assert.assertEquals(expected_triangle, triangle.transform(transformation));
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void transformTest2()");
    }
  }
  
  /**
   * Test de la méthode <b>transform</b> dans le cas d'une matrice prédéfinie quelconque avec un triangle quelconque (back coverage).
   */
  @Test
  public void transformTest3()
  {
    try{
      
      SMatrix4x4 transformation = new SMatrix4x4(1.0, 2.0, 3.0, 4.0, 
                                                 5.0, 6.0, 7.0, 8.0,
                                                 9.0, 10.0, 11.0, 12.0,
                                                 13.0, 14.0, 15.0, 16.0);
      
      SVector3d P0 = new SVector3d(4.5, 3.4, 7.8);
      SVector3d P1 = new SVector3d(1.8, -3.2, 2.8);
      SVector3d P2 = new SVector3d(2.8, -1.8, -3.9);
      
      SVector3d N0 = new SVector3d(1.0, 1.0, 0.0);
      SVector3d N1 = new SVector3d(0.0, 1.0, -1.0);
      SVector3d N2 = new SVector3d(1.0, 0.0, 1.0);
      
      SVectorUV UV0 = new SVectorUV(1.0, 0.0);
      SVectorUV UV1 = new SVectorUV(0.0, 1.0);
      SVectorUV UV2 = new SVectorUV(1.0, 1.0);
      
      SVector3d expected_P0 = new SVector3d(38.7, 105.5, 172.3);
      SVector3d expected_P1 = new SVector3d(7.80, 17.40, 27.0);
      SVector3d expected_P2 = new SVector3d(-8.5, -16.1, -23.7);
      
      SVector3d expected_N0 = new SVector3d(0.13538810472195723, 0.4964230506471766, 0.857457996572396);
      SVector3d expected_N1 = new SVector3d(-0.5773502691896263, -0.577350269189626, -0.5773502691896252);
      SVector3d expected_N2 = new SVector3d(0.1690308509470333, 0.5070925528371099, 0.8451542547285167);
      
      SBTriangleGeometry triangle = new SBTriangleGeometry(P0, P1, P2, N0, N1, N2, UV0, UV1, UV2);
      SBTriangleGeometry expected_triangle = new SBTriangleGeometry(expected_P0, expected_P1, expected_P2, expected_N0, expected_N1, expected_N2, UV0, UV1, UV2);   
          
      Assert.assertEquals(expected_triangle, triangle.transform(transformation));
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("SBTriangleGeometryTest ---> Test non effectué : public void transformTest3()");
    }
  }
  
}//fin de la classe SBTriangleGeometryTest
