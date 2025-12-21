/**
 * 
 */
package sim.physics;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.math.SMath;
import sim.math.SVector3d;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SGeometricalOptics</b>.
 * 
 * @author Simon Vézina
 * @since 2016-03-07
 * @version 2022-03-13
 */
public class SGeometricalOpticsTest {

  /**
   * JUnit Test de la méthode <b>reflexion</b> dans un scénario où le rayon est parallèle à la normale à la surface (surface sens contraire au rayon).
   */
  @Test
  public void reflexionTest1a()
  {
    try{
      
    // Test #1 : Orientation orientation parallèle à la normale à la surface
    SVector3d v = (new SVector3d(4.0, -2.0, 3.0)).normalize();
    SVector3d N = v.multiply(-1);
    
    SVector3d expected_solution = v.multiply(-1);
    
    Assert.assertEquals(expected_solution, SGeometricalOptics.reflexion(v, N));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void reflexionTest1a()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>reflexion</b> dans un scénario où le rayon est parallèle à la normale à la surface (surface même sens que le rayon).
   */
  @Test
  public void reflexionTest1b()
  {
    try{
      
    // Test #1 : Orientation orientation parallèle à la normale à la surface
    SVector3d v = (new SVector3d(4.0, -2.0, 3.0)).normalize();
    SVector3d N = v;
    
    SVector3d expected_solution = v.multiply(-1);
    
    Assert.assertEquals(expected_solution, SGeometricalOptics.reflexion(v, N));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void reflexionTest1b()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>reflexion</b> dans un scénario où on vérifie qu'il y a respect de la loi de la réflexion theta initiale égale theta finale.
   * 
   * Les données prises sont quelconques.
   */
  @Test
  public void reflexionTest2a()
  {
    try{
      
    SVector3d v = (new SVector3d(4.0, 5.0, -3.0)).normalize();
    SVector3d N = (new SVector3d(-1.0, 2.0, 5.0)).normalize();
      
    SVector3d E = v.multiply(-1.0);
    
    double theta_i = SVector3d.angleBetween(E, N);                    
    double theta_f = SVector3d.angleBetween(SGeometricalOptics.reflexion(v, N) ,N);
    
    Assert.assertEquals(theta_i, theta_f, SMath.EPSILON);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void reflexionTest2a()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>reflexion</b> dans un scénario où on vérifie qu'il y a respect de la loi de la réflexion theta initiale égale theta finale.
   * 
   * Les données prises sont quelconques.
   */
  @Test
  public void reflexionTest2b()
  {
    try{
      
    SVector3d v = (new SVector3d(-6.0, 9.0, -3.0)).normalize();
    SVector3d N = (new SVector3d(-4.0, 7.0, 2.0)).normalize();
      
    SVector3d E = v.multiply(-1.0);
    
    double theta_i = SVector3d.angleBetween(E, N);                    
    double theta_f = SVector3d.angleBetween(SGeometricalOptics.reflexion(v, N) ,N);
    
    Assert.assertEquals(theta_i, theta_f, SMath.EPSILON);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void reflexionTest2b()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>reflexion</b> dans un scénario où la normale est face au rayon incident (backcoverage)
   */
  @Test
  public void reflexionTest3a()
  {
    try{
      
    SVector3d v = (new SVector3d(4.0, 5.0, 7.0)).normalize();
    SVector3d N = (new SVector3d(-4.0, -8.0, -9.0)).normalize();
      
    SVector3d expected_solution = new SVector3d(-0.2016524884745054, -0.7195327429658487, -0.664536609745529);
    
    Assert.assertEquals(expected_solution, SGeometricalOptics.reflexion(v, N));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void reflexionTest3a()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>reflexion</b> dans un scénario où la normale est même sens que le rayon incident (backcoverage)
   */
  @Test
  public void reflexionTest3b()
  {
    try{
      
    SVector3d v = (new SVector3d(4.0, 5.0, 7.0)).normalize();
    SVector3d N = (new SVector3d(4.0, 8.0, 9.0)).normalize();
      
    SVector3d expected_solution = new SVector3d(-0.2016524884745054, -0.7195327429658487, -0.664536609745529);
    
    Assert.assertEquals(expected_solution, SGeometricalOptics.reflexion(v, N));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void reflexionTest3a()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>isTotalInternalReflection</b> dans le cas ou n1 = 1.3 et n2 = 1.8 <u>ne donnera pas</u> de réflexion totale interne, car n1 < n2.
   */
  @Test
  public void isTotalInternalReflectionTest1()
  {
    try{
      
    // Test #1 : Orientation parallèle à la normale
    SVector3d v = (new SVector3d(2.3, -2.5, 7.9)).normalize();
    SVector3d N = v.multiply(-1);
    
    double n1 = 1.3;
    double n2 = 1.8;
    
    Assert.assertEquals(false, SGeometricalOptics.isTotalInternalReflection(v, N, n1, n2)); 
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void isTotalInternalReflectionTest1()");
    }
  }

  /**
   * JUnit Test de la méthode <b>isTotalInternalReflection</b> dans le cas quelconque où
   * <ul> n1 = 1.3 et n2 = 1.8 : Pas de réflexion totale interne.</ul>
   * <ul> n2 = 1.8 et n1 = 1.3 : Il y aura réflexion totale interne.</ul>
   */
  @Test
  public void isTotalInternalReflectionTest2()
  {
    try{
      
    // Test #2 : Orientation quelconque de v et N
    SVector3d v = (new SVector3d(2.3, -2.5, 0.0)).normalize();
    SVector3d N = (new SVector3d(1.1, 2.9, 0.0)).normalize();
    
    double n1 = 1.3;
    double n2 = 1.8;
    
    // n1 < n2 : impossible 
    Assert.assertEquals(false, SGeometricalOptics.isTotalInternalReflection(v, N, n1, n2)); 
    
    // n1 > n2 : possible (dans ce cas ... il y a réflexion totale interne)
    Assert.assertEquals(true, SGeometricalOptics.isTotalInternalReflection(v, N, n2, n1));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void isTotalInternalReflectionTest2()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>isTotalInternalReflection</b> dans cas quelconque où
   * <ul> n1 = 1.3 et n2 = 1.7 : Pas de réflexion totale interne.</ul>
   * <ul> n2 = 1.7 et n1 = 1.3 : Il y aura réflexion totale interne.</ul>
   */
  @Test
  public void isTotalInternalReflectionTest3()
  {
    try{
      
    // Test #3 : Orientation quelconque de v et N
    SVector3d v = (new SVector3d(-1.8, -0.7, 0.0)).normalize();
    SVector3d N = (new SVector3d(2.8, 3.1, 0.0)).normalize();
    
    double n1 = 1.3;
    double n2 = 1.7;
    
    // n1 < n2 : impossible 
    Assert.assertEquals(false, SGeometricalOptics.isTotalInternalReflection(v, N, n1, n2)); 
    
    // n1 > n2 : possible (dans ce cas ... pas de réflexion totale interne)
    Assert.assertEquals(false, SGeometricalOptics.isTotalInternalReflection(v, N, n2, n1));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void isTotalInternalReflectionTest3()");
    }
  }
    
  /**
   * JUnit Test de la méthode <b>isTotalInternalReflection</b> dans un cas très particulier où une équipe (H19)
   * a réussit l'ensemble des tests tout en ayant une erreur dans les images générées. Ce test a réussit à les prendre en défaut.
   * L'erreur était finalement dans l'écriture de l'équation (un terme au carré de trop).
   */
  @Test
  public void isTotalInternalReflectionTest4()
  {
    try{
      
      // Test #3 : Orientation quelconque de v et N
      SVector3d v = (new SVector3d(-1.8, -0.7, 0.0)).normalize();
      SVector3d N = (new SVector3d(2.8, 3.1, 0.0)).normalize();
      
      double n1 = 1.3;
      double n2 = 2.2;    // À partir de n2 = 2.2, l'algorithme des étudiants a été pris en défaut.
      
      // n1 < n2 : impossible 
      Assert.assertEquals(false, SGeometricalOptics.isTotalInternalReflection(v, N, n1, n2)); 
      
      // n1 > n2 : possible (dans ce cas ... pas de réflexion totale interne)
      Assert.assertEquals(false, SGeometricalOptics.isTotalInternalReflection(v, N, n2, n1));
      
    }catch(SNoImplementationException e){
        SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void isTotalInternalReflectionTest4()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>refraction</b> dans le cas d'un rayon parallèle à la normale à la surface.
   * 
   * Dans ce cas particulier, le rayon ne change pas de direction.
   */
  @Test
  public void refractionTest1()
  {
    try{
    
    // Test #1 : Orientation parallèle à la normale
    SVector3d v = (new SVector3d(2.3, -2.5, 7.9)).normalize();
    SVector3d N = v.multiply(-1);
    
    double n1 = 1.3;
    double n2 = 1.8;
    
    Assert.assertEquals(v, SGeometricalOptics.refraction(v, N, n1, n2));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void refractionTest1()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>refraction</b> dans un cas quelconque (backcoverage).
   */
  @Test
  public void refractionTest2()
  {
    try{
      
    // Test #2 : Orientation quelconque de v et N
    SVector3d v = (new SVector3d(2.3, -2.5, 0.0)).normalize();
    SVector3d N = (new SVector3d(1.1, 2.9, 0.0)).normalize();
    
    double n1 = 1.3;
    double n2 = 1.8;
    
    Assert.assertEquals(new SVector3d(0.3329180135506553, -0.942955776403902, 0.0), SGeometricalOptics.refraction(v, N, n1, n2));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void refractionTest2()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>refraction</b> dans deux cas quelconques (backcoverage).
   */
  @Test
  public void refractionTest3()
  {
    try{
      
    // Test #3 : Orientation quelconque de v et N
    SVector3d v = (new SVector3d(-1.8, -0.7, 0.0)).normalize();
    SVector3d N = (new SVector3d(2.8, 3.1, 0.0)).normalize();
    
    double n1 = 1.3;
    double n2 = 1.7;
    
    // Tester les deux combinaisons de n1 et n2 donnant deux réfractions distinctes
    Assert.assertEquals(new SVector3d(-0.8842263948388124, -0.4670585430867921, 0.0), SGeometricalOptics.refraction(v, N, n1, n2));
    Assert.assertEquals(new SVector3d(-0.9782082673419917, -0.20762607183053589, 0.0), SGeometricalOptics.refraction(v, N, n2, n1));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void refractionTest3()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>refraction</b> dans le cas où il aura réflexion totale interne.
   * 
   * Dans cette condition, il y aura une exception de type STotalInternalReflectionException qui sera lancée.
   */
  @Test
  public void refractionTest4()
  {
    try{
      
      // Test #2 : Orientation quelconque de v et N
      SVector3d v = (new SVector3d(2.3, -2.5, 0.0)).normalize();
      SVector3d N = (new SVector3d(1.1, 2.9, 0.0)).normalize();
      
      double n1 = 1.8;
      double n2 = 1.3;
      
      try {
        // Méthode a exécuter pour le test.  
        SVector3d refraction = SGeometricalOptics.refraction(v, N, n1, n2);
        
        // Le test est en échec !!!
        fail("Ce test est en échec, car il y a présentement réflexion totale interne et l'exception n'a pas été lancée.");
        
        // Pour retirer le warning !
        refraction.normalize();
        
      }catch(STotalInternalReflectionException e) {
        // C'est un succès !!!
      }
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SGeometricalOpticsTest ---> Test non effectué : public void isTotalInternalReflectionTest2()");
      }
  }
  
}
