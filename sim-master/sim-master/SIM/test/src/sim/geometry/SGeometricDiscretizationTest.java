package sim.geometry;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.math.SVector3d;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SGeometricDiscretization</b>.
 * 
 * @author Simon Vezina
 * @since 2017-06-06
 * @version 2017-06-08
 */ 
public class SGeometricDiscretizationTest {

  /**
   * JUnit Test de la méthode <b>positiveLineXDiscretization</b> dans le cas de deux particules.
   */
  @Test
  public void positiveLineXDiscretizationTest1() 
  {
    try{
      
      // Points attendus.
      SVector3d p0 = new SVector3d(0.0, 0.0, 0.0);
      SVector3d p1 = new SVector3d(3.0, 0.0, 0.0);
      
      List<SVector3d> expected_list = new ArrayList<SVector3d>();
      expected_list.add(p0);
      expected_list.add(p1);
      Collections.sort(expected_list);
          
      List<SVector3d> calculated_list = SGeometricDiscretization.positiveLineXDiscretization(3.0, 2);
      Collections.sort(calculated_list);
      
      Assert.assertEquals(expected_list, calculated_list);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricDiscretizationTest ---> Test non effectué : public void positiveLineXDiscretizationTest1()");
    }
  }

  /**
   * JUnit Test de la méthode <b>positiveLineXDiscretization</b> dans le cas de trois particules.
   */
  @Test
  public void positiveLineXDiscretizationTest2() 
  {
    try{
      
    // Points attendus.
    SVector3d p0 = new SVector3d(0.0, 0.0, 0.0);
    SVector3d p1 = new SVector3d(1.0, 0.0, 0.0);
    SVector3d p2 = new SVector3d(2.0, 0.0, 0.0);
    
    List<SVector3d> expected_list = new ArrayList<SVector3d>();
    expected_list.add(p0);
    expected_list.add(p1);
    expected_list.add(p2);
    Collections.sort(expected_list);
        
    List<SVector3d> calculated_list = SGeometricDiscretization.positiveLineXDiscretization(2.0, 3);
    Collections.sort(calculated_list);
    
    Assert.assertEquals(expected_list, calculated_list);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricDiscretizationTest ---> Test non effectué : public void positiveLineXDiscretizationTest2()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>positiveLineXDiscretization</b> dans le cas de cinq particules.
   */
  @Test
  public void positiveLineXDiscretizationTest3() 
  {
    try{
      
    // Points attendus.
    SVector3d p0 = new SVector3d(0.0, 0.0, 0.0);
    SVector3d p1 = new SVector3d(0.2, 0.0, 0.0);
    SVector3d p2 = new SVector3d(0.4, 0.0, 0.0);
    SVector3d p3 = new SVector3d(0.6, 0.0, 0.0);
    SVector3d p4 = new SVector3d(0.8, 0.0, 0.0);
    
    List<SVector3d> expected_list = new ArrayList<SVector3d>();
    expected_list.add(p0);
    expected_list.add(p1);
    expected_list.add(p2);
    expected_list.add(p3);
    expected_list.add(p4);
    Collections.sort(expected_list);
        
    List<SVector3d> calculated_list = SGeometricDiscretization.positiveLineXDiscretization(0.8, 5);
    Collections.sort(calculated_list);
    
    Assert.assertEquals(expected_list, calculated_list);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricDiscretizationTest ---> Test non effectué : public void positiveLineXDiscretizationTest3()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>positiveSquareXYDiscretization</b> dans le cas de deux particules par dimension.
   */
  @Test
  public void positiveSquareXYDiscretizationTest1() 
  {
    try{
    
    // Points attendus.
    SVector3d p0 = new SVector3d(0.0, 0.0, 0.0);
    SVector3d p1 = new SVector3d(0.0, 1.0, 0.0);
    
    SVector3d p2 = new SVector3d(1.0, 0.0, 0.0);
    SVector3d p3 = new SVector3d(1.0, 1.0, 0.0);
    
    List<SVector3d> expected_list = new ArrayList<SVector3d>();
    expected_list.add(p0);
    expected_list.add(p1);
    expected_list.add(p2);
    expected_list.add(p3);
    Collections.sort(expected_list);
        
    List<SVector3d> calculated_list = SGeometricDiscretization.positiveSquareXYDiscretization(1.0, 2);
    Collections.sort(calculated_list);
    
    Assert.assertEquals(expected_list, calculated_list);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricDiscretizationTest ---> Test non effectué : public void positiveSquareXYDiscretizationTest1()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>positiveSquareXYDiscretization</b> dans le cas de trois particules par dimension.
   */
  @Test
  public void positiveSquareXYDiscretizationTest2() 
  {
    try{
      
    // Points attendus.
    SVector3d p0 = new SVector3d(0.0, 0.0, 0.0);
    SVector3d p1 = new SVector3d(0.0, 1.0, 0.0);
    SVector3d p2 = new SVector3d(0.0, 2.0, 0.0);
    
    SVector3d p3 = new SVector3d(1.0, 0.0, 0.0);
    SVector3d p4 = new SVector3d(1.0, 1.0, 0.0);
    SVector3d p5 = new SVector3d(1.0, 2.0, 0.0);
    
    SVector3d p6 = new SVector3d(2.0, 0.0, 0.0);
    SVector3d p7 = new SVector3d(2.0, 1.0, 0.0);
    SVector3d p8 = new SVector3d(2.0, 2.0, 0.0);
    
    List<SVector3d> expected_list = new ArrayList<SVector3d>();
    expected_list.add(p0);
    expected_list.add(p1);
    expected_list.add(p2);
    expected_list.add(p3);
    expected_list.add(p4);
    expected_list.add(p5);
    expected_list.add(p6);
    expected_list.add(p7);
    expected_list.add(p8);
    
    Collections.sort(expected_list);
        
    List<SVector3d> calculated_list = SGeometricDiscretization.positiveSquareXYDiscretization(2.0, 3);
    Collections.sort(calculated_list);
    
    Assert.assertEquals(expected_list, calculated_list);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SGeometricDiscretizationTest ---> Test non effectué : public void positiveSquareXYDiscretizationTest2()");
    }
  }
  
}// fin de la classe SGeometricDiscretizationTest
