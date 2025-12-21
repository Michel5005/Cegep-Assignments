/**
 * 
 */
package sim.math;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SAffineTransformation</b>.
 * 
 * @author Simon Vézina
 * @since 2017-08-21
 * @version 2022-03-18
 */
public class SAffineTransformationTest {

  /**
   * JUnit Test de la méthode <b>transformOrigin</b> dans le case d'une translation.
   */
  @Test
  public void transformOriginTest1() 
  {
    try{
      
    SMatrix4x4 m = SMatrix4x4.translation(5.0, 4.0, 3.0);
    
    SVector3d calculated_solution = SAffineTransformation.transformOrigin(m);
    
    SVector3d expected_solution = new SVector3d(5.0, 4.0, 3.0);
   
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformOriginTest1()"); 
    }
  }

  /**
   * JUnit Test de la méthode <b>transformOrigin</b> dans le case d'une rotation autour de l'axe x.
   */
  @Test
  public void transformOriginTest2() 
  {
    try{
    
      SMatrix4x4 m = SMatrix4x4.rotationX(30.0);
        
    SVector3d calculated_solution = SAffineTransformation.transformOrigin(m);
    
    SVector3d expected_solution = new SVector3d(0.0, 0.0, 0.0);
   
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformOriginTest2()"); 
    }
  }
    
  /**
   * JUnit Test de la méthode <b>transformPosition</b> d'une translation.
   */
  @Test
  public void transformPositionTest1() 
  {
    try{
      
    SMatrix4x4 m = SMatrix4x4.translation(5.0, 4.0, 3.0);
    SVector3d v = new SVector3d(2.0, -4.0, -8.0);
    
    SVector3d calculated_solution = SAffineTransformation.transformPosition(m, v);
    
    SVector3d expected_solution = new SVector3d(7.0, 0.0, -5.0);
   
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformPositionTest1()"); 
    }
  }
  
  /**
   * JUnit Test de la méthode <b>transformPosition</b> d'une rotation de 90 degrés autour de l'axe z.
   */
  @Test
  public void transformPositionTest2() 
  {
    try{
    
    SMatrix4x4 m = SMatrix4x4.rotationZ(90.0);
    SVector3d v = new SVector3d(5.0, 0.0, 9.0);
    
    SVector3d calculated_solution = SAffineTransformation.transformPosition(m, v);
    
    SVector3d expected_solution = new SVector3d(0.0, 5.0, 9.0);
   
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformPositionTest2()"); 
    }
  }
  
  /**
   * JUnit Test de la méthode <b>transformOrientation</b> dans le cas d'une translation. L'orientation doit restée inchangée.
   */
  @Test
  public void transformOrientationTest1a() 
  {
    try{
    
    SMatrix4x4 m = SMatrix4x4.translation(5.0, 4.0, 3.0);
    SVector3d v = new SVector3d(2.0, -4.0, -8.0);
    
    SVector3d calculated_solution = SAffineTransformation.transformOrientation(m, v);
    
    SVector3d expected_solution = v;  // sans changement.
   
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformOrientationTest1a()"); 
    }
  }
  
  /**
   * JUnit Test de la méthode <b>transformOrientation</b> dans sa <u>version avec réduction d'allocation de mémoire</u> dans le cas d'une translation. L'orientation doit restée inchangée.
   * 
   * d'une rotation de 90 degrés autour de l'axe z.
   */
  @Test
  public void transformOrientationTest1b()
  {
    try{
      
      SMatrix4x4 m = SMatrix4x4.translation(5.0, 4.0, 3.0);
      SVector3d transformed_origin = new SVector3d(5.0, 4.0, 3.0);
      SVector3d v = new SVector3d(2.0, -4.0, -8.0);
      
      SVector3d calculated_solution = SAffineTransformation.transformOrientation(m, transformed_origin, v);
      
      SVector3d expected_solution = v;  // sans changement.
     
      Assert.assertEquals(expected_solution, calculated_solution);
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformOrientationTest1b() (VERSION réduction allocation mémoire)"); 
      }
  }
  
  /**
   * JUnit Test de la méthode <b>transformOrientation</b> pour une rotation de 90 degrés autour de l'axe z.
   */
  @Test
  public void transformOrientationTest2a() 
  {
    try{
    
    SMatrix4x4 m = SMatrix4x4.rotationZ(90.0);
    SVector3d v = new SVector3d(5.0, 0.0, 9.0);
    
    SVector3d calculated_solution = SAffineTransformation.transformOrientation(m, v);
    
    SVector3d expected_solution = new SVector3d(0.0, 5.0, 9.0);
   
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformOrientationTest2a()"); 
    }
  }
    
  /**
   * JUnit Test de la méthode <b>transformOrientation</b> dans sa <u>version avec réduction d'allocation de mémoire</u> pour une rotation de 90 degrés autour de l'axe z.
   */
  @Test
  public void transformOrientationTest2b() 
  {
    try{
    
    SMatrix4x4 m = SMatrix4x4.rotationZ(90.0);
    SVector3d transformed_origin = new SVector3d(0.0, 0.0, 0.0);
    SVector3d v = new SVector3d(5.0, 0.0, 9.0);
    
    SVector3d calculated_solution = SAffineTransformation.transformOrientation(m, transformed_origin, v);
    
    SVector3d expected_solution = new SVector3d(0.0, 5.0, 9.0);
   
    Assert.assertEquals(expected_solution, calculated_solution);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformOrientationTest2b() (VERSION réduction allocation mémoire)"); 
    }
  }
  
  /**
   * JUnit Test de la méthode <b>transformNormal</b> dans le cas d'une translation. La normale doit restée inchangée.
   */
  @Test
  public void transformNormalTest1a() 
  {
    try{
      
      SMatrix4x4 m = SMatrix4x4.translation(5.0, 4.0, 3.0);
      SVector3d v = new SVector3d(0.0, 0.0, 1.0);
      
      SVector3d calculated_solution = SAffineTransformation.transformNormal(m, v);
      
      SVector3d expected_solution = v;  // sans changement
     
      Assert.assertEquals(expected_solution, calculated_solution);
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformNormalTest1a()"); 
      }
  }
  
  /**
   * JUnit Test de la méthode <b>transformNormal</b> dans sa <u>version avec réduction d'allocation de mémoire</u> dans le cas d'une translation. La normale doit restée inchangée.
   */
  @Test
  public void transformNormalTest1b() 
  {
    try{
      
      SMatrix4x4 m = SMatrix4x4.translation(5.0, 4.0, 3.0);
      SVector3d transformed_origin = new SVector3d(5.0, 4.0, 3.0);
      SVector3d v = new SVector3d(0.0, 0.0, 1.0);
      
      SVector3d calculated_solution = SAffineTransformation.transformNormal(m, transformed_origin, v);
      
      SVector3d expected_solution = v;  // sans changement
     
      Assert.assertEquals(expected_solution, calculated_solution);
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformNormalTest1b() (VERSION réduction allocation mémoire)"); 
      }
  }
  
  /**
   * JUnit Test de la méthode <b>transformNormal</b> dans le cas d'une translation. La normale doit restée inchangée, mais doit être normalisée.
   */
  @Test
  public void transformNormalTest2a() 
  {
    try{
      
      SMatrix4x4 m = SMatrix4x4.translation(5.0, 4.0, 3.0);
      SVector3d v = new SVector3d(5.0, -3.0, -8.0);
      
      SVector3d calculated_solution = SAffineTransformation.transformNormal(m, v);
      
      SVector3d expected_solution = v.normalize();  // sans changement, mais normalisé
     
      Assert.assertEquals(expected_solution, calculated_solution);
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformNormalTest2a()"); 
      }
  }
  
  /**
   * JUnit Test de la méthode <b>transformNormal</b> dans sa <u>version avec réduction d'allocation de mémoire</u> dans le cas d'une translation. La normale doit restée inchangée, mais doit être normalisée.
   */
  @Test
  public void transformNormalTest2b() 
  {
    try{
      
      SMatrix4x4 m = SMatrix4x4.translation(5.0, 4.0, 3.0);
      SVector3d transformed_origin = new SVector3d(5.0, 4.0, 3.0);
      SVector3d v = new SVector3d(5.0, -3.0, -8.0);
      
      SVector3d calculated_solution = SAffineTransformation.transformNormal(m, transformed_origin, v);
      
      SVector3d expected_solution = v.normalize();  // sans changement, mais normalisé
     
      Assert.assertEquals(expected_solution, calculated_solution);
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformNormalTest2b() (VERSION réduction allocation mémoire)"); 
      }
  }
  
  /**
   * JUnit Test de la méthode <b>transformNormal</b> dans le cas d'une rotation autour de l'axe z.
   */
  @Test
  public void transformNormalTest3a() 
  {
    try{
      
      SMatrix4x4 m = SMatrix4x4.rotationZ(90.0);
      SVector3d v = new SVector3d(1.0, 0.0, 0.0);
      
      SVector3d calculated_solution = SAffineTransformation.transformNormal(m, v);
      
      SVector3d expected_solution = new SVector3d(0.0, 1.0, 0.0);
     
      Assert.assertEquals(expected_solution, calculated_solution);
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformNormalTest3a()"); 
      }
  }
  
  /**
   * JUnit Test de la méthode <b>transformNormal</b> dans sa <u>version avec réduction d'allocation de mémoire</u> dans le cas d'une rotation autour de l'axe z.
   */
  @Test
  public void transformNormalTest3b() 
  {
    try{
      
      SMatrix4x4 m = SMatrix4x4.rotationZ(90.0);
      SVector3d transformed_origin = new SVector3d(0.0, 0.0, 0.0);
      SVector3d v = new SVector3d(1.0, 0.0, 0.0);
      
      SVector3d calculated_solution = SAffineTransformation.transformNormal(m, transformed_origin, v);
      
      SVector3d expected_solution = new SVector3d(0.0, 1.0, 0.0);
     
      Assert.assertEquals(expected_solution, calculated_solution);
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformNormalTest3b() (VERSION réduction allocation mémoire)"); 
      }
  }
  
  /**
   * JUnit Test de la méthode <b>transformNormal</b> dans le cas d'une rotation autour de l'axe z. Le résultat doit être normalisé.
   */
  @Test
  public void transformNormalTest4a() 
  {
    try{
      
      SMatrix4x4 m = SMatrix4x4.rotationZ(90.0);
      SVector3d v = new SVector3d(5.0, 0.0, 0.0);
      
      SVector3d calculated_solution = SAffineTransformation.transformNormal(m, v);
      
      SVector3d expected_solution = new SVector3d(0.0, 1.0, 0.0);
     
      Assert.assertEquals(expected_solution, calculated_solution);
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformNormalTest4a()"); 
      }
  }
  
  /**
   * JUnit Test de la méthode <b>transformNormal</b> <u>version avec réduction d'allocation de mémoire</u> dans le cas d'une rotation autour de l'axe z. Le résultat doit être normalisé.
   */
  @Test
  public void transformNormalTest4b() 
  {
    try{
      
      SMatrix4x4 m = SMatrix4x4.rotationZ(90.0);
      SVector3d transformed_origin = new SVector3d(0.0, 0.0, 0.0);
      SVector3d v = new SVector3d(5.0, 0.0, 0.0);
      
      SVector3d calculated_solution = SAffineTransformation.transformNormal(m, transformed_origin, v);
      
      SVector3d expected_solution = new SVector3d(0.0, 1.0, 0.0);
     
      Assert.assertEquals(expected_solution, calculated_solution);
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformNormalTest4b() (VERSION réduction allocation mémoire)"); 
      }
  }
  
  /**
   * JUnit Test de la méthode <b>transformNormal</b> dans le cas d'une homothétie. Le résultat doit être normalisé (backcoverage).
   */
  @Test
  public void transformNormalTest5a() 
  {
    try{
      
      SMatrix4x4 m = SMatrix4x4.scale(new SVector3d(2.3, -7.9, 4.8));
      SVector3d v = new SVector3d(2.3, 5.6, -3.7);
      
      SVector3d calculated_solution = SAffineTransformation.transformNormal(m, v);
      
      SVector3d expected_solution = new SVector3d(0.1102902365336985, -0.9223516189510063, -0.3702749718031164);
     
      Assert.assertEquals(expected_solution, calculated_solution);
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformNormalTest5a()"); 
      }
  }
  
  /**
   * JUnit Test de la méthode <b>transformNormal</b> <u>version avec réduction d'allocation de mémoire</u> dans le cas d'une homothétie. Le résultat doit être normalisé (backcoverage).
   */
  @Test
  public void transformNormalTest5b() 
  {
    try{
      
      SMatrix4x4 m = SMatrix4x4.scale(new SVector3d(2.3, -7.9, 4.8));
      SVector3d transformed_origin = new SVector3d(0.0, 0.0, 0.0);
      SVector3d v = new SVector3d(2.3, 5.6, -3.7);
      
      SVector3d calculated_solution = SAffineTransformation.transformNormal(m, transformed_origin, v);
      
      SVector3d expected_solution = new SVector3d(0.1102902365336985, -0.9223516189510063, -0.3702749718031164);
      
      Assert.assertEquals(expected_solution, calculated_solution);
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SAffineTransformationTest ---> Test non effectué : public void transformNormalTest5b() (VERSION réduction allocation mémoire)"); 
      }
  }
  
}
