/**
 * 
 */
package sim.physics;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.math.SMath;
import sim.math.SVector3d;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SElectrostatics</b>.
 * 
 * @author Simon Vézina
 * @since 2017-06-02
 * @version 2019-09-04
 */
public class SElectrostaticsTest {

  /**
   * JUnit Test de la méthode <b>coulombLaw</b> afin de vérifier si le module de la force est adéquat.
   */
  @Test
  public void coulombLawTest1()
  {
    try{
      
      SVector3d r_Q = SVector3d.ORIGIN;
      SVector3d r_q = new SVector3d(3.0, 4.0, 0.0);
      
      double q = 2.0;
      double Q = 3.0;
      
      double F = SElectrostatics.k*q*Q/25.0;
      
      Assert.assertEquals(F, SElectrostatics.coulombLaw(Q, r_Q, q, r_q).modulus(), 0.001);       
      
      }catch(SNoImplementationException e){
        SLog.logWriteLine("SElectrostaticsTest ---> Test non effectué : public void coulombLawTest1()");
      }
  }
  
  /**
   * JUnit Test de la méthode <b>coulombLaw</b> dans le cas le plus simple où les charges sont 1 C et la distance est 1 m le long de l'axe x.
   * Ce test simple permet de vérifier la définition de la source et la cible de la force.
   * La chaque Q est à l'origine et la charque q est à (1,0,0).
   */
  @Test
  public void coulombLawTest2a() 
  {
    try{
    
    SVector3d r_q = new SVector3d(1.0, 0.0, 0.0);
    SVector3d r_Q = SVector3d.ORIGIN;
    
    double q = 1.0;
    double Q = 1.0;

    double F = SElectrostatics.k;
    
    // Test du module.
    Assert.assertEquals(F, SElectrostatics.coulombLaw(Q, r_Q, q, r_q).modulus(), SMath.EPSILON);        	

    // Test avec charge +Q.
    Assert.assertEquals(new SVector3d(F, 0.0, 0.0), SElectrostatics.coulombLaw(Q, r_Q, q, r_q));        	

    // Test avec charge -Q.
    Assert.assertEquals(new SVector3d(-1.0*F, 0.0, 0.0), SElectrostatics.coulombLaw(-1.0*Q, r_Q, q, r_q));  
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectué : public void coulombLawTest2a()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>coulombLaw</b> dans le cas le plus simple où les charges sont 1 C et la distance est 1 m le long de l'axe x.
   * Ce test simple permet de vérifier la définition de la source et la cible de la force.
   * La chaque Q est à (1,0,0) et la charque q est à l'origine.
   */
  @Test
  public void coulombLawTest2b() 
  {
    try{
    
    SVector3d r_Q = new SVector3d(1.0, 0.0, 0.0);
    SVector3d r_q = SVector3d.ORIGIN;
    
    double q = 1.0;
    double Q = 1.0;

    double F = SElectrostatics.k;
    
    // Test du module.
    Assert.assertEquals(F, SElectrostatics.coulombLaw(Q, r_Q, q, r_q).modulus(), SMath.EPSILON);        	

    // Test avec charge +Q.
    Assert.assertEquals(new SVector3d(-1.0*F, 0.0, 0.0), SElectrostatics.coulombLaw(Q, r_Q, q, r_q));        	

    // Test avec charge -Q.
    Assert.assertEquals(new SVector3d(F, 0.0, 0.0), SElectrostatics.coulombLaw(-1.0*Q, r_Q, q, r_q));  
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectué : public void coulombLawTest2b()");
    }
  }

  /**
   * JUnit Test de la méthode <b>coulombLaw</b> dans le cas simple où les charges sont 1 C et la distance est 3 m le long de l'axe x.
   * Ce test simple permet de vérifier si la distance entre les charges a été bien considérée.
   */
  @Test
  public void coulombLawTest2c() 
  {
    try{
    
    SVector3d r_q = new SVector3d(3.0, 0.0, 0.0);
    SVector3d r_Q = SVector3d.ORIGIN;
    
    double q = 1.0;
    double Q = 1.0;
    
    double F = SElectrostatics.k/9.0;
    
    // Avec +Q
    Assert.assertEquals(new SVector3d(F, 0.0, 0.0), SElectrostatics.coulombLaw(Q, r_Q, q, r_q));       
    
    // Avec -Q
    Assert.assertEquals(new SVector3d(-1.0*F, 0.0, 0.0), SElectrostatics.coulombLaw(-1.0*Q, r_Q, q, r_q));   
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectué : public void coulombLawTest2c()");
    }
  }
  
  
  
  /**
   * JUnit Test de la méthode <b>coulombLaw</b> dans le cas venant des notes de cours 203-NYB - Chapitre 1.2 (auteur Simon Vézina).
   */
  @Test
  public void coulombLawTest3() 
  {
    // Référence du test : http://profs.cmaisonneuve.qc.ca/svezina/nyb/note_nyb/NYB_XXI_Chap%201.2.pdf
    
    try{
      
    SVector3d rQA = new SVector3d(1.0, 2.0, 0.0);
    SVector3d rQB = new SVector3d(3.0, 1.0, 0.0);
    
    double QA = 7e-6;
    double QB = 3e-6;
    
    SVector3d expected = (new SVector3d(2.0, -1.0, 0.0)).multiply(SElectrostatics.k*7e-6*3e-6/(Math.pow(5, 1.5)));
    
    Assert.assertEquals(expected, SElectrostatics.coulombLaw(QA, rQA, QB, rQB));						// sans changement de signe
    Assert.assertEquals(expected.multiply(-1.0), SElectrostatics.coulombLaw(-1.0*QA, rQA, QB, rQB));    // avec Q change signe
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectué : public void coulombLawTest3()");
    }
  }
    
}// fin de la classe SElectrostaticsTest
