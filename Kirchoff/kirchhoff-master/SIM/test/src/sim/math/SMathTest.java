/**
 * 
 */
package sim.math;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SMath</b>.
 * 
 * @author Simon Vézina
 * @since 2015-09-05
 * @version 2017-02-07
 */
public class SMathTest {

  @Test
  public void test_nearlyEquals()
  {
    final double epsilon = 1e-10;
    
    //Avec choix de la précision quelconque
    Assert.assertEquals(false, SMath.nearlyEquals(1.0, 2.0, 0.001));
    Assert.assertEquals(true, SMath.nearlyEquals(1.0, 2.0, 10));
    
    //Test des chiffres très près de 1
    Assert.assertEquals(false, SMath.nearlyEquals(1.0, 1.0 + 1e-4, epsilon));
    Assert.assertEquals(false, SMath.nearlyEquals(1.0, 1.0 + 1e-8, epsilon));
    Assert.assertEquals(false, SMath.nearlyEquals(1.0, 1.0 + 1e-10, epsilon));
    Assert.assertEquals(true, SMath.nearlyEquals(1.0, 1.0 + 1e-11, epsilon));
    
    //Test des très gros chiffres
    Assert.assertEquals(false, SMath.nearlyEquals(5.000001e14, 5.0e14, epsilon));
    Assert.assertEquals(false, SMath.nearlyEquals(5.0e14, 5.0e14 + 1e5, epsilon));
    Assert.assertEquals(false, SMath.nearlyEquals(5.0e14, 5.0e14 + 6e4, epsilon));
    Assert.assertEquals(true, SMath.nearlyEquals(5.0e14, 5.0e14 + 1e3, epsilon));
    
    //Test des très petits chiffres avec le zéro
    Assert.assertEquals(false, SMath.nearlyEquals(0.0, 1e-9, epsilon));
    Assert.assertEquals(true, SMath.nearlyEquals(0.0, 1e-11, epsilon));
    
    //Test des très petites chiffres sans le zéro
    Assert.assertEquals(false, SMath.nearlyEquals(1e-6, 1e-8, epsilon));
    Assert.assertEquals(true, SMath.nearlyEquals(1e-10, 1e-12, epsilon));
    Assert.assertEquals(true, SMath.nearlyEquals(1e-12, 1e-26, epsilon));
  }

  /**
   * JUnit Test de la méthode linearRealRoot.
   */
  @Test
  public void test_linearRealRoot()
  {
    try{
      
      // Test #1 : Aucune solution
      test_valuesSolutions(new double[0], SMath.linearRealRoot(0.0, 2.0), 0.0001);
      
      // Test #2 : Solution positive
      test_valuesSolutions(new double[] { 2.3333 }, SMath.linearRealRoot(3.0, -7.0), 0.0001);
      
      // Test #3 : Solution négative
      test_valuesSolutions(new double[] { -3.25 }, SMath.linearRealRoot(4.0, 13.0), 0.0001);
      
      // Test #4 : Solution nulle
      test_valuesSolutions(new double[]  { 0.0 }, SMath.linearRealRoot(5.0, 0.0), 0.0001);
          
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SMathTest ---> Test non effectué : public void test_linearRealRoot()"); 
    }
  }
  
  /**
   * JUnit Test de la méthode quadricRealRoot.
   */
  @Test
  public void test_quadricRealRoot()
  {
    try{
      
    // Test #1 : Le polynôme x^2 + 1 = 0 (sans solution)
    test_valuesSolutions(new double[0], SMath.quadricRealRoot(1.0, 0.0, 1.0), 0.0001);
        
    // Test #2 : Scénarios tirés de la résolution d'un problème de physique 
    test_valuesSolutions(new double[] {1.27 , 4.73 }, SMath.quadricRealRoot(-1.0, 6.0, -6.0), 0.01);
    test_valuesSolutions(new double[] {-0.29 , 0.70 }, SMath.quadricRealRoot(-4.9, 2.0, 1.0), 0.01);
    
    // Test #3 : Deux solutions (positive et négative)
    test_valuesSolutions(new double[] {-2.2655 , 6.1117 }, SMath.quadricRealRoot(-0.52, 2.0, 7.2), 0.0001);
    test_valuesSolutions(new double[] {-3.6074 , 4.7111 }, SMath.quadricRealRoot(7.52, -8.3, -127.8), 0.0001);
    test_valuesSolutions(new double[] {-2.0902 , 1.5972 }, SMath.quadricRealRoot(12.82, 6.32, -42.8), 0.0001);
    
    // Test #4 : Deux solutions positives
    test_valuesSolutions(new double[] {0.92380 , 3.7887 }, SMath.quadricRealRoot(0.8, -3.77, 2.8), 0.0001);
    
    // Test #5 : Aucune solution
    test_valuesSolutions(new double[0], SMath.quadricRealRoot(15.82, 9.32, 12.8), 0.0001);
    test_valuesSolutions(new double[0], SMath.quadricRealRoot(-4.29, 4.12, -12.8), 0.0001);
    
    // Test #6 : Réduction à la solution du polynôme du degré inférieur
    test_valuesSolutions(SMath.linearRealRoot(5.8634, -7.3423), SMath.quadricRealRoot(0.0, 5.8634, -7.3423), SMath.EPSILON);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SMathTest ---> Test non effectué : public void test_quadricRealRoot()"); 
    }
    
  }
  
  /**
   * JUnit Test de la méthode cubicRealRoot.
   */
  @Test
  public void test_cubicRealRoot()
  {
    try{
      
      // Test du polynôme x^3 = 0
      test_valuesSolutions(new double[] { 0.0 , 0.0, 0.0 }, SMath.cubicRealRoot(1.0, 0.0, 0.0, 0.0), SMath.EPSILON);
      
      // Test généré à l'aide du calculateur disponible au site suivante :
      // http://www.1728.org/cubic.htm
      test_valuesSolutions(new double[] { -1.6506291914393882 }, SMath.cubicRealRoot(1.0, 2.0, 3.0, 4.0), SMath.EPSILON);
      test_valuesSolutions(new double[] {  -0.26858108140546954 }, SMath.cubicRealRoot(3.0, 2.0, 45.0, 12.0), SMath.EPSILON);
      test_valuesSolutions(new double[] { -3.39955938424035, -0.27126794228276, 4.33749399318978 }, SMath.cubicRealRoot(-3.0, 2.0, 45.0, 12.0), SMath.EPSILON);
      test_valuesSolutions(new double[] { 3.957841714398882 }, SMath.cubicRealRoot(2.0, -4.0, 8.0, -93.0), SMath.EPSILON);
      
      // Test avec le polynôme : (x-2)(x-2)(x-5) = x^3 - 9x^2 + 24x - 20 = 0
      test_valuesSolutions(new double[] { 2.0, 2.0, 5.0 }, SMath.cubicRealRoot(1.0, -9.0, 24.0, -20.0), SMath.EPSILON);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SMathTest ---> Test non effectué : public void test_cubicRealRoot()"); 
    }
  }
  
  /**
   * JUnit Test de la méthode quarticRealRoot.
   */
  @Test
  public void test_quarticRealRoot()
  {
    try{
      
      // Test du polynôme x^4 = 0
      test_valuesSolutions(new double[] { 0.0, 0.0, 0.0, 0.0 }, SMath.quarticRealRoot(1.0, 0.0, 0.0, 0.0, 0.0), SMath.EPSILON);
          
      // Test venant du site suivant : 
      // http://math.stackexchange.com/questions/785/is-there-a-general-formula-for-solving-4th-degree-equations-quartic
      test_valuesSolutions(new double[] { -0.35095, 0.70062 }, SMath.quarticRealRoot(1.0, 2.0, 3.0, -2.0, -1), 0.0001);
      
      // Test venant du site suivant :
      // http://www.1728.org/quartic2.htm
      test_valuesSolutions(new double[] { -6.0, -4.0, 3, 5 }, SMath.quarticRealRoot(3.0, 6.0, -123.0, -126.0, 1080), 0.00001);
      
      
      test_valuesSolutions(new double[] {1.7875072099939513, 2.479417962127269}, SMath.quarticRealRoot(15.0, -61.0, 76.0, -82.0, 99.0), SMath.EPSILON);
      test_valuesSolutions(new double[] {-3.5729103685240444, 0.19229881258065684} , SMath.quarticRealRoot(-150.0, -324.0, 543.0, -731.0, 123.0), SMath.EPSILON);
      test_valuesSolutions(new double[0], SMath.quarticRealRoot(1.0, 1.0, 1.0, 1.0, 1.0), SMath.EPSILON);
      test_valuesSolutions(new double[0], SMath.quarticRealRoot(5.0, 6.0, 7.0, 8.0, 9.0), SMath.EPSILON);  
  
      test_valuesSolutions(new double[0], SMath.quarticRealRoot(-654.98, 127.37, 23.67, -723.32, -564.21), SMath.EPSILON);  
      test_valuesSolutions(new double[0], SMath.quarticRealRoot(65.0, 0.0, 45.9, 0.0, 34.0), SMath.EPSILON);  

      test_valuesSolutions(new double[] {-4.196470244260061, -0.013485035862911942, 5.1121350640621674, 35.43115354939414}, SMath.quarticRealRoot(-12.0, 436.0, -126.0, -9123.0, -123.0), SMath.EPSILON);  
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SMathTest ---> Test non effectué : public void test_quarticRealRoot()"); 
    }
  }
  
  @Test
  public void findMaxTest()
  {
    int[] tab = { 45, 76, 23, -45, -123, 32, 67 };
    
    Assert.assertEquals(76, SMath.findMax(tab));
  }
  
  @Test
  public void findMinTest()
  {
    int[] tab = { 45, 76, 23, -45, -123, 32, 67 };
    
    Assert.assertEquals(-123, SMath.findMin(tab));
  }
  
  /**
   * Méthode pour faire la vérification du nombre de solutions, de l'ordre des solutions et de leurs valeurs.
   * 
   * @param expected_solution Le tableau des solutions attendues.
   * @param calculated_solution Le tableau des solutions calculées.
   * @param epsilon Le niveau de précision des solutions.
   */
  private void test_valuesSolutions(double[] expected_solution, double[] calculated_solution, double epsilon)
  {
    // Vérifier le nombre de solutions
    Assert.assertEquals(expected_solution.length, calculated_solution.length);  
    
    // Vérifier la valeur et l'ordre des solutions avec la taille du tableau attendue
    for(int i = 0; i < expected_solution.length; i++)
      Assert.assertEquals(expected_solution[i], calculated_solution[i], epsilon);
  }
    
}//fin de la classe de test SMathTest
