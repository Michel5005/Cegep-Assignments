/**
 * 
 */
package sim.geometry;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.math.SMatrix4x4;
import sim.math.SVector3d;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>STriangleGeometry</b>.
 * 
 * @author Simon Vezina
 * @since 2016-05-13
 * @version 2020-02-11
 */
public class STriangleGeometryTest {

  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon aligné selon l'axe z et un triangle dans le plan xy.
   * Le rayon voyage tel qu'il s'éloigne du triangle.
   */
  @Test
  public void intersectionTest1a()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 1.0), new SVector3d(0.0, 0.0, 1.0), 1.0);
    
    STriangleGeometry geometry = new STriangleGeometry(new SVector3d(1.0, 1.0, 0.0), new SVector3d(1.0, -1.0, 0.0), new SVector3d(-1.0, 0.0, 0.0));
    
	  // Méthode à tester.
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.
    Assert.assertEquals(ray, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void intersectionTest1a()");
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
    
    STriangleGeometry geometry = new STriangleGeometry(new SVector3d(1.0, 1.0, 0.0), new SVector3d(1.0, -1.0, 0.0), new SVector3d(-1.0, 0.0, 0.0));
    
	  // Méthode à tester.
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.
    Assert.assertEquals(ray, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void intersectionTest1b()");
    }
  }
  
  /**
   * Test de l'intersection <u>avec succès</u> entre un rayon aligné selon l'axe z et un triangle dans le plan xy.
   */
  @Test
  public void intersectionTest2()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 1.0), new SVector3d(0.0, 0.0, -1.0), 1.0);
    
    STriangleGeometry geometry = new STriangleGeometry(new SVector3d(1.0, 1.0, 0.0), new SVector3d(1.0, -1.0, 0.0), new SVector3d(-1.0, 0.0, 0.0));
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Orientation de la normale attendue en lien avec l'ordre des points du triangle.
    SVector3d expected_normal = new SVector3d(0.0, 0.0, -1.0);
    
    SRay expected_solution = ray.intersection(geometry, expected_normal, 1.0);
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void intersectionTest2()");
    }
  }
  
  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon aligné selon l'axe z et un triangle dans le plan xy.
   * Il passe à l'extérieur du segment 0-1.
   */
  @Test
  public void intersectionTest3a()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(1.2, 0.0, 1.0), new SVector3d(0.0, 0.0, -1.0), 1.0);
    
    STriangleGeometry geometry = new STriangleGeometry(new SVector3d(1.0, 1.0, 0.0), new SVector3d(1.0, -1.0, 0.0), new SVector3d(-1.0, 0.0, 0.0));
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.    
    SRay expected_solution = ray;
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void intersectionTest3a()");
    }
  }
  
  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon aligné selon l'axe z et un triangle dans le plan xy.
   * Il passe à l'extérieur du segment 1-2.
   */
  @Test
  public void intersectionTest3b()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, -1.0, 1.0), new SVector3d(0.0, 0.0, -1.0), 1.0);
    
    STriangleGeometry geometry = new STriangleGeometry(new SVector3d(1.0, 1.0, 0.0), new SVector3d(1.0, -1.0, 0.0), new SVector3d(-1.0, 0.0, 0.0));
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.    
    SRay expected_solution = ray;
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void intersectionTest3b()");
    }
  }
  
  /**
   * Test de l'intersection <u>avec succès</u> entre un rayon aligné selon l'axe z et un triangle dans le plan xy.
   * Il passe à l'extérieur du segment 2-0.
   */
  @Test
  public void intersectionTest3c()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, 1.0, 1.0), new SVector3d(0.0, 0.0, -1.0), 1.0);
    
    STriangleGeometry geometry = new STriangleGeometry(new SVector3d(1.0, 1.0, 0.0), new SVector3d(1.0, -1.0, 0.0), new SVector3d(-1.0, 0.0, 0.0));
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.    
    SRay expected_solution = ray;
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void intersectionTest3c()");
    }
  }
  
  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon et un triangle dans le plan xy.
   * Le rayon évite de triangle.
   */
  @Test
  public void intersectionTest4()
  {
    try {
    
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 1.0), new SVector3d(-1.0, -1.0, -1.0), 1.0);
    
    STriangleGeometry geometry = new STriangleGeometry(new SVector3d(1.0, 1.0, 0.0), new SVector3d(1.0, -1.0, 0.0), new SVector3d(-1.0, 0.0, 0.0));
    
    SRay calculated_solution = geometry.intersection(ray);
    
    SRay expected_solution = ray;   // sans intersection
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void intersectionTest4()");
    }
  }
    
  /**
   * Test de l'intersection <u>sans succès</u> entre un rayon et un triangle quelconque.
   */
  @Test
  public void intersectionTest5()
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
    SVector3d P0 = r_int.add(b1.multiply(2.5)).add(b2.multiply(1.8));
    SVector3d P1 = r_int.add(b1.multiply(-1.5)).add(b2.multiply(2.8));
    SVector3d P2 = r_int.add(b1.multiply(3.7)).add(b2.multiply(0.7));
    
    SRay ray = new SRay(r_int.add(ray_direction.multiply(-t)), ray_direction, 1.0);
    
    STriangleGeometry geometry = new STriangleGeometry(P0, P1, P2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // Il n'y a pas d'intersection.
    SRay expected_solution = ray;
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void intersectionTest5()");
    }
  }
  
  /**
   * Test de l'intersection <u>avec succès</u> entre un rayon et un triangle quelconque.
   */
  @Test
  public void intersectionTest6()
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
    
    STriangleGeometry geometry = new STriangleGeometry(P0, P1, P2);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    // JE SUIS À LA RECHERCHE DE LA NORMALE.
    SRay expected_solution = ray.intersection(geometry, new SVector3d(-0.45106718384945743, -0.8775030829421607, -0.1628703014091979), t);
    
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void intersectionTest6()");
    }
  }
  
  /**
   * Test de la méthode <b>intersection</b> dans le cas d'un rayon lancé depuis la surface du triangle.
   * 
   * Ce test sera validé sous la condition t > 0.
   */
  @Test 
  public void intersectionTest7a()
  {
    try {
      
      SVector3d P0 = new SVector3d(1.0, 1.0, 1.0);
      SVector3d P1 = new SVector3d(2.0, 1.0, 1.0);
      SVector3d P2 = new SVector3d(2.0, 2.0, 1.0);
      
      STriangleGeometry geometry = new STriangleGeometry(P0, P1, P2);
      
      SRay ray = new SRay(new SVector3d(1.5, 1.2, 1.0), new SVector3d(0.0, 0.0, 1.0), 1.0);
      
      Assert.assertEquals(ray, geometry.intersection(ray));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void intersectionTest7a()");
    }
  }
  
  /**
   * Test de la méthode <b>intersection</b> dans le cas d'un rayon lancé depuis la surface du triangle.
   * 
   * Ce test sera non validé sous la condition t > 0, car la solution est t > 0+.
   */
  @Test 
  public void intersectionTest7b()
  {
    try {
      
      SVector3d P0 = new SVector3d(1.0, 1.0, 1.0);
      SVector3d P1 = new SVector3d(2.0, 1.0, 1.0);
      SVector3d P2 = new SVector3d(2.0, 2.0, 1.0);
      
      STriangleGeometry geometry = new STriangleGeometry(P0, P1, P2);
      
      SRay ray = new SRay(new SVector3d(1.5, 1.2, 1.0-SRay.getEpsilon()*0.01), new SVector3d(0.0, 0.0, 1.0), 1.0);
      
      Assert.assertEquals(ray, geometry.intersection(ray));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void intersectionTest7b()");
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
      
      STriangleGeometry triangle = new STriangleGeometry(P0, P1, P2);
      
      // Vérifier que le triangle reste inchangé.
      Assert.assertEquals(triangle, triangle.transform(transformation));   
      
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void transformTest1()");
    }
  }
  
  /**
   * Test de la méthode <b>transform</b> dans le cas de la matrice de translation avec un triangle quelconque.
   * Le résultat attendu sera un triangle déplacé par la translation.
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
      
      STriangleGeometry triangle = new STriangleGeometry(P0, P1, P2);
      STriangleGeometry expected_triangle = new STriangleGeometry(P0.add(translation), P1.add(translation), P2.add(translation));
          
          
      Assert.assertEquals(expected_triangle, triangle.transform(transformation));
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void transformTest2()");
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
      
      SVector3d expected_P0 = new SVector3d(38.7, 105.5, 172.3);
      SVector3d expected_P1 = new SVector3d(7.80, 17.40, 27.0);
      SVector3d expected_P2 = new SVector3d(-8.5, -16.1, -23.7);
      
      STriangleGeometry triangle = new STriangleGeometry(P0, P1, P2);
      STriangleGeometry expected_triangle = new STriangleGeometry(expected_P0, expected_P1, expected_P2);
          
      Assert.assertEquals(expected_triangle, triangle.transform(transformation));
    }
    catch(SNoImplementationException e){
      SLog.logWriteLine("STriangleGeometryTest ---> Test non effectué : public void transformTest3()");
    }
  }
  
}
