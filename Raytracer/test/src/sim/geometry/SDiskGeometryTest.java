/**
 * 
 */
package sim.geometry;

import org.junit.Assert;
import org.junit.Test;
import sim.math.SVector3d;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SDiskGeometry</b>.
 * 
 * @author Simon Vézina
 * @since 2017-01-23
 * @version 2022-03-13
 */
public class SDiskGeometryTest {

  /**
   * Test de la méthode <b>intersection</b> entre un rayon à voyageant parallèlement au disque en étant situé au-dessus. Il n'y a pas d'intersection.
   */
  @Test
  public void intersectionTest1a()
  {
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 1.0), new SVector3d(1.0, 0.0, 0.0), 1.0);
      
    SDiskGeometry geometry = new SDiskGeometry(new SVector3d(0.0, 0.0, 0.0), new SVector3d(0.0, 0.0, 1.0), 2.0);
      
    SRay calculated_solution = geometry.intersection(ray);
      
    SRay expected_solution = ray; // sans intersection
      
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de la méthode <b>intersection</b> entre un rayon à voyageant parallèlement au disque en étant situé dans le plan du disque. Il y a une infinité de solutions.
   */
  /*
  @Test
  public void intersectionTest1b()
  {
    SRay ray = new SRay(new SVector3d(0.0, 0.0, 0.0), new SVector3d(1.0, 0.0, 0.0), 1.0);
      
    SDiskGeometry geometry = new SDiskGeometry(new SVector3d(0.0, 0.0, 0.0), new SVector3d(0.0, 0.0, 1.0), 2.0);
   
    try{
   
      SRay calculated_solution = geometry.intersection(ray);
      fail("Ce test devrait donner une infinité de solutions. Présentement, votre solution de temps est t = " + calculated_solution.getT());
 
    }catch(SInfinityOfSolutionsException e){
      // c'est un succès
    }  
  }
  */
  
  /**
   * Test de la méthode <b>intersection</b> entre un rayon et un disque où le rayon est initialement sur le disque. Il n'y a pas d'intersection.
   */
  @Test
  public void intersectionTest2()
  {
    // Un point du disque
    SVector3d r_p = new SVector3d(-1.0, 3.0, -7.0);
    double R = 3.0;
    
    // La base du plan
    SVector3d u1 = new SVector3d(7.0, 3.0, 2.0);
    SVector3d u2 = new SVector3d(-4.0, 2.0, -1.0);
    
    // La normale à la surface du plan
    SVector3d n_p = u1.cross(u2).normalize();
    
    // Origine du rayon (sur le plan)
    SVector3d r0 = r_p.add(u1.multiply(3.0)).add(u2.multiply(6.0));
    
    // Orientation du rayon (quelconque)
    SVector3d v = new SVector3d(8.0, -9.0, 2.0);
    
    // Le calcul à tester
    SRay ray = new SRay(r0, v, 1.0);
    
    SDiskGeometry geometry = new SDiskGeometry(r_p, n_p, R);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    SRay expected_solution = ray; // sans intersection
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }

  /**
   * Test de la méthode <b>intersection</b> entre un rayon et un disque où le rayon où le temps de l'intersection est positif. Le rayon est à <u>l'intérieur du disque</u>.
   */
  @Test
  public void intersectionTest3()
  {
    // Un point du plan
    SVector3d r_p = new SVector3d(2.0, 2.0, 5.0);
    double R = 3.0;
    
    // La normale à la surface du plan
    SVector3d n_p = new SVector3d(1.0, 1.0, 1.0).normalize();
    
    // Origine du rayon 
    SVector3d r0 = new SVector3d(4.0, 4.0, 10.0);
    
    // Orientation du rayon 
    SVector3d v = new SVector3d(-1.0, 0.0, -2.0);
    
    // Le calcul à tester
    SRay ray = new SRay(r0, v, 1.0);
    
    SDiskGeometry geometry = new SDiskGeometry(r_p, n_p, R);
    
    SRay calculated_solution = geometry.intersection(ray);
    
    SRay expected_solution = ray.intersection(geometry, n_p, 3.0);   // intersection à l'intérieur du disque
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de la méthode <b>intersection</b> entre un rayon et un disque où le rayon où le temps de l'intersection est positif, mais le rayon est à <u>l'extérieur du disque</u>.
   */
  @Test
  public void intersectionTest4a()
  {
    // Un point du plan
    SVector3d r_p = new SVector3d(2.0, 2.0, 5.0);
    double R = 2.0;   // Remarque : 2.4490 et plus petit --> out  2.4499 et plus grand --> in
    
    // La normale à la surface du plan
    SVector3d n_p = new SVector3d(1.0, 1.0, 1.0).normalize();
    
    // Origine du rayon 
    SVector3d r0 = new SVector3d(4.0, 4.0, 10.0);
    
    // Orientation du rayon 
    SVector3d v = new SVector3d(-1.0, 0.0, -2.0);
    
    // Le calcul à tester
    SRay ray = new SRay(r0, v, 1.0);
    
    SDiskGeometry geometry = new SDiskGeometry(r_p, n_p, R);
    
    // Réaliser le test :
    SRay calculated_solution = geometry.intersection(ray);
    SRay expected_solution = ray; // intersection à l'extérieur du disque
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de la méthode <b>intersection</b> entre un rayon et un disque où le rayon où le temps de l'intersection est positif, mais le rayon est à <u>l'intérieur du disque</u>.
   */
  @Test
  public void intersectionTest4b()
  {
    // Un point du plan
    SVector3d r_p = new SVector3d(2.0, 2.0, 5.0);
    double R = 2.8;   // Remarque : 2.4490 et plus petit --> out  2.4499 et plus grand --> in
    
    // La normale à la surface du plan
    SVector3d n_p = new SVector3d(1.0, 1.0, 1.0).normalize();
    
    // Origine du rayon 
    SVector3d r0 = new SVector3d(4.0, 4.0, 10.0);
    
    // Orientation du rayon 
    SVector3d v = new SVector3d(-1.0, 0.0, -2.0);
    
    // Le calcul à tester
    SRay ray = new SRay(r0, v, 1.0);
    
    SDiskGeometry geometry = new SDiskGeometry(r_p, n_p, R);
    
    // Réaliser le test :
    SRay calculated_solution = geometry.intersection(ray);
    SRay expected_solution = ray.intersection(geometry, n_p, 3.0); 
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de la méthode <b>intersection</b> entre un rayon et un plan où le rayon où le temps de l'intersection est négatif. Il n'y a donc pas d'intersection.
   */
  @Test
  public void intersectionTest4c()
  {
    // Un point du plan
    SVector3d r_p = new SVector3d(2.0, 2.0, 5.0);
    double R = 2.0;
    
    // La normale à la surface du plan
    SVector3d n_p = new SVector3d(1.0, 1.0, 1.0).normalize();
    
    // Origine du rayon 
    SVector3d r0 = new SVector3d(0.0, 2.0, -15.0);
    
    // Orientation du rayon 
    SVector3d v = new SVector3d(-1.0, 0.0, -2.0);
    
    // Le calcul à tester
    SRay ray = new SRay(r0, v, 1.0);
    
    SDiskGeometry geometry = new SDiskGeometry(r_p, n_p, R);
    
    // Réaliser le test :
    SRay calculated_solution = geometry.intersection(ray);
    SRay expected_solution = ray;   // pas d'intersection
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de la méthode <b>intersection</b> avec un rayon très éloigné du plan menant à une intersection à un temps positif (où t > R) à l'intérieur du disque. Il y a donc intersection.
   */
  @Test
  public void intersectionTest5a()
  {
    // Le disque :
    SVector3d r_p = new SVector3d(2.0, 2.0, 2.0);
    SVector3d n_p = new SVector3d(0.0, 0.0, 1.0);
    double R = 5.0;
    SDiskGeometry geometry = new SDiskGeometry(r_p, n_p, R);
    
    // Le rayon :
    SVector3d r0 = new SVector3d(3.0, 2.0, 10.0);  
    SVector3d v = new SVector3d(0.0, 0.0, -1.0);
    SRay ray = new SRay(r0, v, 1.0);
    
    // Réaliser le test :
    SRay calculated_solution = geometry.intersection(ray);
    SRay expected_solution = ray.intersection(geometry, n_p, 8.0);
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de la méthode <b>intersection</b> avec un rayon très éloigné du plan menant à une intersection à un temps positif (où t > R) à l'extérieur du disque. Il n'y a donc pas d'intersection.
   */
  @Test
  public void intersectionTest5b()
  {
    // Le disque :
    SVector3d r_p = new SVector3d(2.0, 2.0, 2.0);
    SVector3d n_p = new SVector3d(0.0, 0.0, 1.0);
    double R = 5.0;
    SDiskGeometry geometry = new SDiskGeometry(r_p, n_p, R);
    
    // Le rayon :
    SVector3d r0 = new SVector3d(8.0, 2.0, 10.0);  
    SVector3d v = new SVector3d(0.0, 0.0, -1.0);
    SRay ray = new SRay(r0, v, 1.0);
    
    // Réaliser le test :
    SRay calculated_solution = geometry.intersection(ray);
    SRay expected_solution = ray;
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de la méthode <b>intersection</b> pour prendre en défaut une équipe (H22) qui ne générait pas les bonnes images de la scène intro_disque.txt (backcoverage)
   * 
   * Nous allons reproduire le test d'un scénario de calcul avec intersection.
   */
  @Test
  public void intersectionTest6a()
  {
    // Le disque :
    SVector3d r_p = new SVector3d(0.0, 0.0, 0.0);
    SVector3d n_p = new SVector3d(0.0, 0.0, 1.0);
    double R = 2.0;
    SDiskGeometry geometry = new SDiskGeometry(r_p, n_p, R);
    
    // Le rayon :
    SVector3d r0 = new SVector3d(-5.0, 0.0, 2.0);  
    SVector3d v = new SVector3d(5.0, 0.0, -2.0).normalize();
    SRay ray = new SRay(r0, v, 1.0);
    
    // Réaliser le test :
 // Réaliser le test :
    SRay calculated_solution = geometry.intersection(ray);
    SRay expected_solution = ray.intersection(geometry, n_p, 5.385164807134504);
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de la méthode <b>intersection</b> pour prendre en défaut une équipe (H22) qui ne générait pas les bonnes images de la scène intro_disque.txt (backcoverage).
   * 
   * Nous allons reproduire le test d'un scénario de calcul avec intersection.
   */
  @Test
  public void intersectionTest6b()
  {
 // Le disque :
    SVector3d r_p = new SVector3d(0.0, 0.0, 0.0);
    SVector3d n_p = new SVector3d(0.0, 0.0, 1.0);
    double R = 2.0;
    SDiskGeometry geometry = new SDiskGeometry(r_p, n_p, R);
    
    // Le rayon :
    SVector3d r0 = new SVector3d(-5.0, 0.0, 2.0);  
    SVector3d v = new SVector3d(5.0, 0.0, -0.25).normalize();
    SRay ray = new SRay(r0, v, 1.0);
    
    // Réaliser le test :
    SRay calculated_solution = geometry.intersection(ray);
    SRay expected_solution = ray;
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
}
