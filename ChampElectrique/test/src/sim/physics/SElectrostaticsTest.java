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
import sim.math.field.SUndefinedFieldException;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalit�s de la classe <b>SElectrostatics</b>.
 * 
 * @author Simon V�zina
 * @since 2017-06-02
 * @version 2022-09-10
 */
public class SElectrostaticsTest {

  /**
   * JUnit Test de la m�thode <b>coulombLaw</b> afin de v�rifier si le module de la force est ad�quat.
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
        SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void coulombLawTest1()");
      }
  }
  
  /**
   * JUnit Test de la m�thode <b>coulombLaw</b> dans le cas le plus simple o� les charges sont 1 C et la distance est 1 m le long de l'axe x.
   * Ce test simple permet de v�rifier la d�finition de la source et la cible de la force.
   * La chaque Q est � l'origine et la charque q est � (1,0,0).
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
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void coulombLawTest2a()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>coulombLaw</b> dans le cas le plus simple o� les charges sont 1 C et la distance est 1 m le long de l'axe x.
   * Ce test simple permet de v�rifier la d�finition de la source et la cible de la force.
   * La chaque Q est � (1,0,0) et la charque q est � l'origine.
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
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void coulombLawTest2b()");
    }
  }

  /**
   * JUnit Test de la m�thode <b>coulombLaw</b> dans le cas simple o� les charges sont 1 C et la distance est 3 m le long de l'axe x.
   * Ce test simple permet de v�rifier si la distance entre les charges a �t� bien consid�r�e.
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
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void coulombLawTest2c()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>coulombLaw</b> dans le cas venant des notes de cours 203-NYB - Chapitre 1.2 (auteur Simon V�zina).
   */
  @Test
  public void coulombLawTest3() 
  {
    // R�f�rence du test : http://profs.cmaisonneuve.qc.ca/svezina/nyb/note_nyb/NYB_XXI_Chap%201.2.pdf
    
    try{
      
    SVector3d rQA = new SVector3d(1.0, 2.0, 0.0);
    SVector3d rQB = new SVector3d(3.0, 1.0, 0.0);
    
    double QA = 7e-6;
    double QB = 3e-6;
    
    SVector3d expected = (new SVector3d(2.0, -1.0, 0.0)).multiply(SElectrostatics.k*7e-6*3e-6/(Math.pow(5, 1.5)));
    
    Assert.assertEquals(expected, SElectrostatics.coulombLaw(QA, rQA, QB, rQB));						// sans changement de signe
    Assert.assertEquals(expected.multiply(-1.0), SElectrostatics.coulombLaw(-1.0*QA, rQA, QB, rQB));    // avec Q change signe
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void coulombLawTest3()");
    }
  }
   
  /**
   * JUnit Test de la m�thode <b>coulombLaw</b> dans un cas quelconque (backcoverage).
   */
  @Test
  public void coulombLawTest4() 
  {
    try{
      
    SVector3d rQA = new SVector3d(65.4, 34.2, -72.9);
    SVector3d rQB = new SVector3d(-13.2, -27.9, 31.3);
    
    double QA = 7.65e-6;
    double QB = -3.13e-6;
    
    SVector3d expected = new SVector3d(5.601436113377263e-6, 4.425562120111043e-6, -7.425822430202426e-6);
    
    Assert.assertEquals(expected, SElectrostatics.coulombLaw(QA, rQA, QB, rQB));           
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void coulombLawTest4()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>electricForce</b> dans un cas quelconque (backcoverage).
   */
  @Test
  public void electricForceTest1() 
  {
    try{
      
    SVector3d E = new SVector3d(65.4, 34.2, -72.9);
    double q = 7.65e-6;
   
    
    SVector3d expected = new SVector3d(5.0031e-4, 2.6163e-4, -5.57685e-4);
    
    Assert.assertEquals(expected, SElectrostatics.electricForce(q, E));           
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void electricForceTest1()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>particleElectricField</b> dans un cas quelconque (backcoverage).
   */
  @Test
  public void particleElectricFieldTest1() 
  {
    try{
      
    SVector3d r_p = new SVector3d(65.4, 34.2, -72.9);
    SVector3d r = new SVector3d(21.6, -27.5, -36.5);
    
    double Q1 = 3.65e-6;
    double Q2 = -3.65e-6;
    
    SVector3d expected_1 = new SVector3d(-2.4271572208902654, -3.419077637646789, 2.017089562566339);
    SVector3d expected_2 = new SVector3d(2.4271572208902654, 3.419077637646789, -2.017089562566339);
    
    Assert.assertEquals(expected_1, SElectrostatics.particleElectricField(r_p, Q1, r));
    Assert.assertEquals(expected_2, SElectrostatics.particleElectricField(r_p, Q2, r));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void particleElectricFieldTest1()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>particleElectricField</b> dans un cas quelconque (backcoverage).
   */
  @Test
  public void sphereElectricFieldTest1a() 
  {
    try{
      
    SVector3d r_p = new SVector3d(65.4, 34.2, -72.9);
    SVector3d r = new SVector3d(21.6, -27.5, -36.5);
    double R = 12.0;
    
    double Q1 = 3.65e-6;
    double Q2 = -3.65e-6;
    
    SVector3d expected_1 = new SVector3d(-2.4271572208902654, -3.419077637646789, 2.017089562566339);
    SVector3d expected_2 = new SVector3d(2.4271572208902654, 3.419077637646789, -2.017089562566339);
    
    Assert.assertEquals(expected_1, SElectrostatics.sphereElectricField(r_p, R, Q1, r));
    Assert.assertEquals(expected_2, SElectrostatics.sphereElectricField(r_p, R, Q2, r));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void sphereElectricFieldTest1a()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>particleElectricField</b> dans un cas quelconque o� r est � l'int�rieur de la sph�re (backcoverage).
   */
  @Test
  public void sphereElectricFieldTest1b() 
  {
    try{
      
    SVector3d r_p = new SVector3d(65.4, 34.2, -72.9);
    SVector3d r = new SVector3d(21.6, -27.5, -36.5);
    double R = 125.0;
    
    double Q1 = 3.65e-6;
        
    // La position r est � l'int�rieur de la sph�re.
    Assert.assertEquals(SVector3d.ZERO, SElectrostatics.sphereElectricField(r_p, R, Q1, r));
        
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void sphereElectricFieldTest1b()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>infiniteRodElectric</b> dans un cas quelconque (backcoverage).
   */
  @Test
  public void infiniteRodElectricFieldTest1a() 
  {
    try{
      
    SVector3d r_T = new SVector3d(65.4, 34.2, -72.9);
    SVector3d axis = new SVector3d(21.6, -27.5, -36.5).normalize();
    double lambda = 1.65e-6;
    
    SVector3d r = new SVector3d(42.0, -17.8, -25.6);
    
    SVector3d expected = new SVector3d(-94.12697752915913, -343.3605038477009, 202.9937298953956);
    
    Assert.assertEquals(expected, SElectrostatics.infiniteRodElectricField(r_T, axis, lambda, r));
        
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void infiniteRodElectricFieldTest1a()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>infiniteRodElectric</b> dans un cas quelconque o� r est sur l'axe de la TRIUC (backcoverage).
   */
  @Test
  public void infiniteRodElectricFieldTest1b() 
  {
    try{
      
    SVector3d r_T = new SVector3d(65.4, 34.2, -72.9);
    SVector3d axis = new SVector3d(21.6, -27.5, -36.5).normalize();
    double lambda = 1.65e-6;
    
    // Point sur l'axe de la TRIUC.
    SVector3d r = r_T.add(axis.multiply(46.8));
    
    Assert.assertEquals(SVector3d.ZERO, SElectrostatics.infiniteRodElectricField(r_T, axis, lambda, r));
        
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void infiniteRodElectricFieldTest1b()");
    }
  }
    
  /**
   * JUnit Test de la m�thode <b>infinitePlateElectricField</b> dans un cas de l'exercice 1.9.2 : Le champ g�n�r� par deux PPIUC de charges diff�rentes.
   * Le calcul sera effectu� pour valider le module du champ �lectrique.
   */
  @Test
  public void infinitePlateElectricFieldTest1a() 
  {
    try{
      
    SVector3d r_P = new SVector3d(2.0, 2.0, 2.0);
    SVector3d n_z = new SVector3d(0.0, 0.0, 1.0);
    double sigma = 6e-9;
    
    SVector3d r = new SVector3d(0.0, 0.0, 4.0);
    
    Assert.assertEquals(338.8227, SElectrostatics.infinitePlateElectricField(r_P, n_z, sigma, r).modulus(), 1e-3);
        
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void infinitePlateElectricFieldTest1a()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>infinitePlateElectricField</b> dans un cas de l'exercice 1.9.2 : Le champ g�n�r� par deux PPIUC de charges diff�rentes.
   * Le calcul sera effectu� pour valider les orientations du champ �lectrique.
   */
  @Test
  public void infinitePlateElectricFieldTest1b() 
  {
    try{
      
    SVector3d r_P = new SVector3d(2.0, 2.0, 2.0);
    SVector3d n_x = new SVector3d(1.0, 0.0, 0.0);
    SVector3d n_y = new SVector3d(0.0, 1.0, 0.0);
    SVector3d n_z = new SVector3d(0.0, 0.0, 1.0);
    
    double sigma = 6e-9;
    
    double E = 338.8227199363837;
    
    SVector3d r = new SVector3d(4.0, 4.0, 4.0);
    
    Assert.assertEquals(new SVector3d(E, 0.0, 0.0), SElectrostatics.infinitePlateElectricField(r_P, n_x, sigma, r));
    Assert.assertEquals(new SVector3d(-E, 0.0, 0.0), SElectrostatics.infinitePlateElectricField(r_P, n_x, sigma*-1.0, r));
    
    Assert.assertEquals(new SVector3d(0.0, E, 0.0), SElectrostatics.infinitePlateElectricField(r_P, n_y, sigma, r));
    Assert.assertEquals(new SVector3d(0.0, -E, 0.0), SElectrostatics.infinitePlateElectricField(r_P, n_y, sigma*-1.0, r));
    
    Assert.assertEquals(new SVector3d(0.0, 0.0, E), SElectrostatics.infinitePlateElectricField(r_P, n_z, sigma, r));
    Assert.assertEquals(new SVector3d(0.0, 0.0, -E), SElectrostatics.infinitePlateElectricField(r_P, n_z, sigma*-1.0, r));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void infinitePlateElectricFieldTest1a()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>infinitePlateElectricField</b> dans un cas quelconque (backcoverage).
   */
  @Test
  public void infinitePlateElectricFieldTest2a() 
  {
    try{
      
    SVector3d r_P = new SVector3d(65.4, 34.2, -72.9);
    SVector3d n = new SVector3d(21.6, -27.5, -36.5).normalize();
    double lambda = 1.65e-6;
    
    SVector3d r = new SVector3d(42.0, -17.8, -25.6);
    
    SVector3d expected = new SVector3d(-39816.07244561675, 50691.758900669476, 67281.78908634312);
    
    Assert.assertEquals(expected, SElectrostatics.infinitePlateElectricField(r_P, n, lambda, r));
        
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void infinitePlateElectricFieldTest2a()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>infinitePlateElectricField</b> dans un cas quelconque o� r est sur r_P de la PPIUC (backcoverage).
   */
  @Test
  public void infinitePlateElectricFieldTest2b() 
  {
    try{
      
    SVector3d r_P = new SVector3d(65.4, 34.2, -72.9);
    SVector3d n = new SVector3d(21.6, -27.5, -36.5).normalize();
    double lambda = 1.65e-6;
    
    // Point sur r_P de la PPIUC.
    SVector3d r = r_P;
    
    Assert.assertEquals(SVector3d.ZERO, SElectrostatics.infinitePlateElectricField(r_P, n, lambda, r));
        
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void infinitePlateElectricFieldTest2b()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>infinitePlateElectricField</b> dans un cas quelconque o� r est dans le plan de la PPIUC (backcoverage).
   */
  @Test
  public void infinitePlateElectricFieldTest2c() 
  {
    try{
      
    SVector3d r_P = new SVector3d(65.4, 34.2, -72.9);
    SVector3d base_1 = new SVector3d(21.6, -27.5, -36.5).normalize();
    SVector3d base_2 = new SVector3d(-67.3, 46.8, -13.4).normalize();
    SVector3d n = base_1.cross(base_2).normalize();
    
    double lambda = 1.65e-6;
    
    // Point sur le plan de la PPIUC.
    SVector3d r = r_P.add(base_1.multiply(34.6)).add(base_2.multiply(-17.6));
    
    System.out.println(r.substract(r_P).dot(n));
    
    Assert.assertEquals(SVector3d.ZERO, SElectrostatics.infinitePlateElectricField(r_P, n, lambda, r));
        
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void infinitePlateElectricFieldTest2c()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricFieldOutsideAxis</b> pour une tige align� sur l'axe x 
   * dont le point P est centr� sur la <u>tige charg�e positivement</u>.
   */
  @Test
  public void finiteRodElectricFieldOutsideAxisTest1a()
  {
    try{
      
      double lambda = 3.0;
      
      SVector3d r_A = SVector3d.UNITARY_X.multiply(-1.0);
      SVector3d r_B = SVector3d.UNITARY_X;
      
      SVector3d r = SVector3d.UNITARY_Y;
      
      // L'angle pour d�limit� les extr�mit�s A et B (45 degr�).
      double theta_A = Math.toRadians(45.0);
      double theta_B = Math.toRadians(-45.0);
    
      // Module attendu n�cessite uniquement la composante perpentidulaire du champ �lectrique.
      double E = SPhysics.k * lambda / 1.0 * (Math.sin(theta_A) - Math.sin(theta_B));
      
      SVector3d expected = SVector3d.UNITARY_Y.multiply(E);
      
      // Validation de la m�thode.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldOutsideAxisTest1a()");
    }
    
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricFieldOutsideAxis</b> pour une tige align� sur l'axe x 
   * dont le point P est centr� sur la <u>tige charg�e n�gativement</u>.
   */
  @Test
  public void finiteRodElectricFieldOutsideAxisTest1b()
  {
    try{
      
      double lambda = -3.0;
      
      SVector3d r_A = SVector3d.UNITARY_X.multiply(-1.0);
      SVector3d r_B = SVector3d.UNITARY_X;
      
      SVector3d r = SVector3d.UNITARY_Y;
      
      // L'angle pour d�limit� les extr�mit�s A et B (45 degr�).
      double theta_A = Math.toRadians(45.0);
      double theta_B = Math.toRadians(-45.0);
    
      // Module attendu n�cessite uniquement la composante perpentidulaire du champ �lectrique.
      double E = Math.abs( SPhysics.k * lambda / 1.0 * (Math.sin(theta_A) - Math.sin(theta_B)) );
      
      SVector3d expected = SVector3d.UNITARY_Y.multiply(E).multiply(-1.0);
      
      // Validation de la m�thode.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldOutsideAxisTest1b()");
    }
    
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricFieldOutsideAxis</b> � partir d'un l'exercice A du chapitre 1.8b.
   */
  @Test
  public void finiteRodElectricFieldOutsideAxisTest2()
  {
    try{
      
      double lambda = -2.0e-5;
      
      SVector3d r_A = new SVector3d(0.0, 0.4, 0.0);
      SVector3d r_B = new SVector3d(0.0, 0.8, 0.0);
      SVector3d r = new SVector3d(1.0, 1.0, 0.0);
      
      double theta_A = Math.atan(0.2 / 1.0);
      double theta_B = Math.atan(0.6 / 1.0);
      
      double k_labda_on_R = SPhysics.k*lambda/1.0;
      
      // Valeur obtenue avec approximation : E = (-57298.00374, -22149.68, 0.0)
      SVector3d expected = new SVector3d(
          - Math.abs(  k_labda_on_R*Math.abs((Math.sin(theta_A) - Math.sin(theta_B)))  ),
          - Math.abs(  k_labda_on_R*Math.abs((Math.cos(theta_A) - Math.cos(theta_B)))  ),
          0.0
      );
            
      // Validation du module du vecteur.
      Assert.assertEquals(expected.modulus(), SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r).modulus(), 0.0001);
      
      
      // Validation du vecteur.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldOutsideAxisTest1()");
    }
    
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricFieldOutsideAxis</b> avec une tige parall�le � l'axe y 
   * o� alpha_A est un angle positif et alpha_B est un angle n�gatif.
   * 
   * Dans cette situation, le point p est dans la <u>partie inf�rieure</u> de la <u>tige charg�e positivement</u>.
   */
  @Test
  public void finiteRodElectricFieldOutsideAxisTest3a()
  {
    try{
      
      double lambda = 3.0e-5;
      
      SVector3d r_A = new SVector3d(0.0, 4.0, 0.0);
      SVector3d r_B = new SVector3d(0.0, -2.0, 0.0);
      SVector3d r = new SVector3d(3.0, 0.0, 0.0);
      
      double theta_A = Math.atan(4.0 / 3.0);
      double theta_B = Math.atan(-2.0 / 3.0);
      
      double k_labda_on_R = SPhysics.k*lambda/3.0;
      
      SVector3d expected = new SVector3d(
          + Math.abs(  k_labda_on_R*Math.abs((Math.sin(theta_A) - Math.sin(theta_B)))  ),
          - Math.abs(  k_labda_on_R*Math.abs((Math.cos(theta_A) - Math.cos(theta_B)))  ),
          0.0
      );
      
      // Validation du module du vecteur.
      Assert.assertEquals(expected.modulus(), SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r).modulus(), 0.0001);
      
      
      // Validation du vecteur.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldOutsideAxisTest3a()");
    }
    
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricFieldOutsideAxis</b> avec une tige parall�le � l'axe y 
   * o� alpha_A est un angle positif et alpha_B est un angle n�gatif.
   * 
   * Dans cette situation, le point p est dans la <u>partie sup�rieure</u> de la <u>tige charg�e positivement</u>.
   */
  @Test
  public void finiteRodElectricFieldOutsideAxisTest3b()
  {
    try{
      
      double lambda = 3.0e-5;
      
      SVector3d r_A = new SVector3d(0.0, 2.0, 0.0);
      SVector3d r_B = new SVector3d(0.0, -4.0, 0.0);
      SVector3d r = new SVector3d(3.0, 0.0, 0.0);
      
      double theta_A = Math.atan(2.0 / 3.0);
      double theta_B = Math.atan(-4.0 / 3.0);
      
      double k_labda_on_R = SPhysics.k*lambda/3.0;
      
      SVector3d expected = new SVector3d(
          + Math.abs(  k_labda_on_R*Math.abs((Math.sin(theta_A) - Math.sin(theta_B)))  ),
          + Math.abs(  k_labda_on_R*Math.abs((Math.cos(theta_A) - Math.cos(theta_B)))  ),
          0.0
      );
      
      // Validation du module du vecteur.
      Assert.assertEquals(expected.modulus(), SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r).modulus(), 0.0001);
      
      
      // Validation du vecteur.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldOutsideAxisTest3b()");
    }
    
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricFieldOutsideAxis</b> avec une tige parall�le � l'axe y 
   * o� alpha_A est un angle positif et alpha_B est un angle n�gatif.
   * 
   * Dans cette situation, le point p est dans la <u>partie inf�rieure</u> de la <u>tige charg�e n�gativement</u>.
   */
  @Test
  public void finiteRodElectricFieldOutsideAxisTest3c()
  {
    try{
      
      double lambda = -3.0e-5;
      
      SVector3d r_A = new SVector3d(0.0, 4.0, 0.0);
      SVector3d r_B = new SVector3d(0.0, -2.0, 0.0);
      SVector3d r = new SVector3d(3.0, 0.0, 0.0);
      
      double theta_A = Math.atan(4.0 / 3.0);
      double theta_B = Math.atan(-2.0 / 3.0);
      
      double k_labda_on_R = SPhysics.k*lambda/3.0;
      
      SVector3d expected = new SVector3d(
          - Math.abs( k_labda_on_R*Math.abs((Math.sin(theta_A) - Math.sin(theta_B))) ),
          + Math.abs( k_labda_on_R*Math.abs((Math.cos(theta_A) - Math.cos(theta_B))) ),
          0.0
      );
      
      // Validation du module du vecteur.
      Assert.assertEquals(expected.modulus(), SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r).modulus(), 0.0001);
      
      // Validation du vecteur.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldOutsideAxisTest3c()");
    }
    
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricFieldOutsideAxis</b> avec une tige parall�le � l'axe y 
   * o� alpha_A est un angle positif et alpha_B est un angle n�gatif.
   * 
   * Dans cette situation, le point p est dans la <u>partie sup�rieure</u> de la <u>tige charg�e n�gativement</u>.
   */
  @Test
  public void finiteRodElectricFieldOutsideAxisTest3d()
  {
    try{
      
      double lambda = -3.0e-5;
      
      SVector3d r_A = new SVector3d(0.0, 2.0, 0.0);
      SVector3d r_B = new SVector3d(0.0, -4.0, 0.0);
      SVector3d r = new SVector3d(3.0, 0.0, 0.0);
      
      double theta_A = Math.atan(2.0 / 3.0);
      double theta_B = Math.atan(-4.0 / 3.0);
      
      double k_labda_on_R = SPhysics.k*lambda/3.0;
      
      SVector3d expected = new SVector3d(
          - Math.abs(  k_labda_on_R*Math.abs((Math.sin(theta_A) - Math.sin(theta_B)))  ),
          - Math.abs(  k_labda_on_R*Math.abs((Math.cos(theta_A) - Math.cos(theta_B)))  ),
          0.0
      );
      
      // Validation du module du vecteur.
      Assert.assertEquals(expected.modulus(), SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r).modulus(), 0.0001);
      
      
      // Validation du vecteur.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, lambda, r));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldOutsideAxisTest3d()");
    }  
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricFieldOutsideAxis</b> dans le cas d'une tige sur l'axe y 
   * et on �value le champ �lectique sur l'axe y. Ainsi, une exception devra �tre lanc�e.
   */
  @Test
  public void finiteRodElectricFieldOutsideAxisTest4()
  {
    try{
      
      double Q = -5e-6;
      SVector3d r_A = new SVector3d(0.0, 5e-2, 0.0);
      SVector3d r_B = new SVector3d(0.0, 20e-2, 0.0);
      SVector3d r = SVector3d.ORIGIN;
      
      try {      
        
        SElectrostatics.finiteRodElectricFieldOutsideAxis(r_A, r_B, Q/0.15, r);
        fail("Ce test est en �chec, car le champ �lectrique sur l'axe de la tige n'est pas d�fini pour le champ �lectrique hors axe de la TRUC.");
      
      }catch(SUndefinedFieldException e) {
        // Le test est un succ�s.
      }
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldOutsideAxisTest4()");
    }  
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricField</b> pour une tige align�e sur l'axe y o� r est sur l'extr�mit� de la tige.
   * selon les donn�es de la <b>Situation A : Tirer avec une tige charg�e</b>, mais en choisissant la position r = (0 cm, r_A, 0 cm).
   */
  @Test
  public void finiteRodElectricFieldTest1()
  {
    try{
      
      double Q = -5e-6;
      SVector3d r_A = new SVector3d(0.0, 5e-2, 0.0);
      SVector3d r_B = new SVector3d(0.0, 20e-2, 0.0);
           
      // Test avec r = r_A.
      Assert.assertEquals(SVector3d.ZERO, SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r_A));
       
      // Test avec r = r_B.
      Assert.assertEquals(SVector3d.ZERO, SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r_B));
            
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldTest1()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricField</b> pour une tige align�e sur l'axe y o� la longueur de la tige est nulle et r est sur la charge.
   */
  @Test
  public void finiteRodElectricFieldTest2a()
  {
    try{
      
      double Q = -5e-6;
      SVector3d r_A = new SVector3d(0.0, 5e-2, 0.0);
      SVector3d r_B = r_A;
      
      SVector3d r = r_A;
           
      // Test avec r = r_A = r_B, donc E = 0.
      Assert.assertEquals(SVector3d.ZERO, SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r));
           
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldTest2a()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricField</b> pour une tige align�e sur l'axe y o� la longueur de la tige est nulle et r est sur l'axe y (Backcoverage).
   */
  @Test
  public void finiteRodElectricFieldTest2b()
  {
    try{
      
      double Q = -5e-6;
      SVector3d r_A = new SVector3d(0.0, 5e-2, 0.0);
      SVector3d r_B = r_A;
      
      SVector3d r = new SVector3d(0.0, 7e-2, 0.0);
           
      SVector3d expected = new SVector3d(0.0, -1.1234439734210216e8, 0.0);
      
      // Test avec r_A = r_B et r est autre.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r));
           
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldTest2b()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricField</b> pour une tige align�e sur l'axe y o� la longueur de la tige est nulle et r est quelconque (Backcoverage).
   */
  @Test
  public void finiteRodElectricFieldTest2c()
  {
    try{
      
      double Q = -5e-6;
      SVector3d r_A = new SVector3d(3.1e-2, 5.9e-2, -4.5e-2);
      SVector3d r_B = r_A;
      
      SVector3d r = new SVector3d(4.8e-2, -3.2e-2, 2.4e-2);
           
      SVector3d expected = new SVector3d(-496325.10020133184 , 2656799.0657836, -2014495.9949348175);
      
      // Test avec r_A = r_B et r est autre.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r));
           
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldTest2c()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricField</b> pour une tige align�e sur l'axe y dans le cas valid� dans les notes de cours
   * selon les donn�es de la <b>Situation A : Tirer avec une tige charg�e</b>. 
   */
  @Test
  public void finiteRodElectricFieldTest3a()
  {
    try{
      
      double Q = -5e-6;
      SVector3d r_A = new SVector3d(0.0, 5e-2, 0.0);
      SVector3d r_B = new SVector3d(0.0, 20e-2, 0.0);
      SVector3d r = SVector3d.ORIGIN;
      
      // Avec la pr�cision du nombre k.
      SVector3d expected = new SVector3d(0.0, 4493775.893684088, 0.0);
      
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldTest3a()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricField</b> pour une tige align�e sur l'axe y dans le cas valid� dans les notes de cours
   * selon les donn�es de la <b>Situation A : Tirer avec une tige charg�e</b>, mais en choisissant la position r = (0 cm, 25 cm, 0 cm).
   */
  @Test
  public void finiteRodElectricFieldTest3b()
  {
    try{
      
      double Q = -5e-6;
      SVector3d r_A = new SVector3d(0.0, 5e-2, 0.0);
      SVector3d r_B = new SVector3d(0.0, 20e-2, 0.0);
      SVector3d r = new SVector3d(0.0, 25e-2, 0.0);
      
      // Avec la pr�cision du nombre k.
      SVector3d expected = new SVector3d(0.0, -4493775.893684088, 0.0);
      
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldTest3b()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricField</b> pour une tige align�e sur l'axe y dans un cas o� r est sur la tige.
   * selon les donn�es de la <b>Situation A : Tirer avec une tige charg�e</b>, mais en choisissant la position r = (0 cm, 10 cm, 0 cm).
   */
  @Test
  public void finiteRodElectricFieldTest3c()
  {
    try{
      
      double Q = -5e-6;
      SVector3d r_A = new SVector3d(0.0, 5e-2, 0.0);
      SVector3d r_B = new SVector3d(0.0, 20e-2, 0.0);
      SVector3d r = new SVector3d(0.0, 10e-2, 0.0);
     
      // Validation que le champ est bien nul.
      Assert.assertEquals(SElectrostatics.NO_ELECTRIC_FIELD, SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldTest3c()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricField</b> pour une tige align�e sur l'axe y o� r est hors axe
   * selon les donn�es de la <b>Situation A : Tirer avec une tige charg�e</b>, mais en choisissant la position r = (0 cm, 10 cm, 10 cm) (Backcoverage).
   */
  @Test
  public void finiteRodElectricFieldTest3d()
  {
    try{
      
      double Q = -5e-6;
      SVector3d r_A = new SVector3d(0.0, 5e-2, 0.0);
      SVector3d r_B = new SVector3d(0.0, 20e-2, 0.0);
      SVector3d r = new SVector3d(0.0, 10e-2, 20e-2);
     
      SVector3d expected = new SVector3d(0.0, 113415.87700683625, -1033192.8066444575);
            
      // Validation du champ hors axe.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r));
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldTest3d()");
    }
  }
    
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricField</b> avec la situation A du chapitre 1.8b.
   */
  @Test
  public void finiteRodElectricFieldTest4()
  {
    try{
      
      double Q = -8.0e-6;
      SVector3d r_A = new SVector3d(0.0, 0.4, 0.0);
      SVector3d r_B = new SVector3d(0.0, 0.8, 0.0);
      
      SVector3d r = new SVector3d(1.0, 1.0, 0.0);
     
      SVector3d expected = new SVector3d(-57229.06650786162, -22125.150546425608, 0.0);
      
      // Validation du champ.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldTest4()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>finiteRodElectricField</b> pour une tige quelconque en une position quelconque (Backcoverage).
   */
  @Test
  public void finiteRodElectricFieldTest5()
  {
    try{
      
      double Q = -7.3e-6;
      SVector3d r_A = new SVector3d(1.3, -2.6, 6.8);
      SVector3d r_B = new SVector3d(-4.3, 7.9, -2.4);
      
      SVector3d r = new SVector3d(8.4, -3.8, -7.2);
     
      SVector3d expected = new SVector3d(-167.73505090240081, 105.45447833075163, 167.3732834869497);
            
      // Validation du champ.
      Assert.assertEquals(expected, SElectrostatics.finiteRodElectricField(r_A, r_B, Q, r));
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SElectrostaticsTest ---> Test non effectu� : public void finiteRodElectricFieldTest5()");
    }
  }
  
}
