/**
 * 
 */
package sim.util;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.math.SMath;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SArrayUtil</b>.
 * 
 * @author Simon Vézina
 * @since 2018-01-23
 * @version 2021-10-10
 */
public class SArraysTest {

  /**
   * Test de la méthode <b>clear</b> pour un tableau de taille 7.
   */
  @Test
  public void clearTest()
  {
    double[] array = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0 };
    double[] expecteds = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    
    Assert.assertArrayEquals(expecteds, SArrays.clear(array), 0.000001);
  }
  
  /**
   * Test de la méthode <b>add</b> où l'on vérifie qu'il y a eu allocation de mémoire pour retourner le résultat.
   */
  @Test
  public void addTest1a()
  {
    try{
      
      double[] A = {  };
      double[] B = {  };
      
      double[] calculated = SArrays.add(A, B);
      
      // Vérification de la nouvelle allocation de mémoire
      Assert.assertNotEquals(calculated, A);
      Assert.assertNotEquals(calculated, B);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void addTest1a()");
    }
    
  }
  
  /**
   * Test de la méthode <b>add</b> où l'on vérifie que les deux tableaux passés en paramètre dans la fonction n'ont pas été modifiés.
   */
  @Test
  public void addTest1b()
  {
    try{
      
      double[] A = { 1.0, 2.0, 3.0 };
      double[] A_original = { 1.0, 2.0, 3.0 };
      
      double[] B = { 4.0, 5.0, 6.0 };
      double[] B_original = { 4.0, 5.0, 6.0 };
      
      //------------------
      // Méthode à tester.
      //------------------
      double[] calculated = SArrays.add(A, B);
      
      // Vérification de la nouvelle allocation de mémoire.
      Assert.assertNotEquals(calculated, A);
      Assert.assertNotEquals(calculated, B);
      
      // Vérifier que le contenu de A n'a pas été modifié.
      Assert.assertArrayEquals(A_original, A, 0.000001);
      
      // Vérifier que le contenu de B n'a pas été modifié.
      Assert.assertArrayEquals(B_original, B, 0.000001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void addTest1b()");
    }
    
  }
  
  /**
   * Test de la méthode <b>add</b> dans un cas simple où la somme des deux tableaux donne zéro pour l'ensemble des éléments.
   */
  @Test
  public void addTest2a()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
      double[] B = { 0.0, -1.0, -2.0, -3.0, -4.0, -5.0 };
      
      double[] calculateds = SArrays.add(A, B);
      double[] expecteds = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
      
      Assert.assertArrayEquals(expecteds, calculateds, 0.0001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void addTest2a()");
    }
    
  }
  
  /**
   * Test de la méthode <b>add</b> dans un cas simple où les deux tableaux sont identiques.
   */
  @Test
  public void addTest2b()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
            
      double[] calculateds = SArrays.add(A, A);
      double[] expecteds = { 0.0, 2.0, 4.0, 6.0, 8.0, 10.0 };
      
      Assert.assertArrayEquals(expecteds, calculateds, 0.0001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void addTest2b()");
    }
    
  }
  
  /**
   * Test de la méthode <b>add</b> dans un cas quelconque.
   */
  @Test
  public void addTest3()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
      double[] B = { 5.0, 7.0, 9.0, 12.0, 16.0, 22.0 };
      
      double[] calculateds = SArrays.add(A, B);
      double[] expecteds = { 5.0, 8.0, 11.0, 15.0, 20.0, 27.0 };
      
      Assert.assertArrayEquals(expecteds, calculateds, 0.0001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void addTest3()");
    }
  }
  
  /**
   * Test de la méthode <b>add</b> dans un cas à 3 tableaux.
   */
  @Test
  public void addTest4()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
      double[] B = { 5.0, 7.0, 9.0, 12.0, 16.0, 22.0 };
      double[] C = new double[6]; 
      
      double[] expecteds = { 5.0, 8.0, 11.0, 15.0, 20.0, 27.0 };
      
      Assert.assertArrayEquals(expecteds, SArrays.add(A, B, C), 0.000001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void addTest4()");
    }
  }
  
  /**
   * Test de la méthode <b>substract</b> où l'on vérifie qu'il y a eu allocation de mémoire pour retourner le résultat.
   */
  @Test
  public void substractTest1()
  {
    try{
      
      double[] A = {  };
      double[] B = {  };
      
      double[] calculated = SArrays.substract(A, B);
      
      // Vérification de la nouvelle allocation de mémoire
      Assert.assertNotEquals(calculated, A);
      Assert.assertNotEquals(calculated, B);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void substractTest1()");
    }
    
  }
  
  
  /**
   * Test de la méthode <b>substract</b> dans un cas simple.
   */
  @Test
  public void substractTest2()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
      double[] B = { 0.0, -1.0, -2.0, -3.0, -4.0, -5.0 };
      
      double[] calculateds = SArrays.substract(A, B);
      double[] expecteds = { 0.0, 2.0, 4.0, 6.0, 8.0, 10.0 };
      
      Assert.assertArrayEquals(expecteds, calculateds, 0.0001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void substractTest2()");
    }
    
  }
  
  /**
   * Test de la méthode <b>multiply</b> pour une <b>scalaire</b> où l'on vérifie qu'il y a eu allocation de mémoire pour retourner le résultat.
   */
  @Test
  public void multiply_forScalarTest1()
  {
    try{
      
      double[] A = {  };
            
      double[] calculated = SArrays.multiply(A, 5.6);
      
      // Vérification de la nouvelle allocation de mémoire
      Assert.assertNotEquals(calculated, A);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void multiply_forScalarTest1()");
    }
    
  }
  
  /**
   * Test de la méthode <b>multiply</b> pour une <b>scalaire</b> avec un cas simple.
   */
  @Test
  public void multiply_forScalarTest2()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
            
      double[] calculateds = SArrays.multiply(A, 3.0);
      double[] expecteds = { 0.0, 3.0, 6.0, 9.0, 12.0, 15.0 };
      
      Assert.assertArrayEquals(expecteds, calculateds, 0.0001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void multiply_forScalarTest2()");
    }
    
  }
  
  /**
   * Test de la méthode <b>multiply</b> pour une <b>scalaire</b> avec un cas simple.
   */
  @Test
  public void multiply_forScalarTest3()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
      double a = 3.0;
      
      double[] result = new double[6];
      
      double[] expecteds = { 0.0, 3.0, 6.0, 9.0, 12.0, 15.0 };
      
      Assert.assertArrayEquals(expecteds, SArrays.multiply(A, a, result), 0.0001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void multiply_forScalarTest3()");
    }
    
  }
  
  /**
   * Test de la méthode <b>multiply</b> pour des <b>Array</b> où l'on vérifie qu'il y a eu allocation de mémoire pour retourner le résultat.
   */
  @Test
  public void multiply_forArrayTest1()
  {
    try{
      
      double[] A = {  };
      double[] B = {  };
      
      double[] calculated = SArrays.multiply(A, B);
      
      // Vérification de la nouvelle allocation de mémoire
      Assert.assertNotEquals(calculated, A);
      Assert.assertNotEquals(calculated, B);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void multiply_forArrayTest1()");
    }
    
  }
  
  /**
   * Test de la méthode <b>multiply</b> pour des <b>Array</b> dans un cas simple.
   */
  @Test
  public void multiply_forArrayTest2()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
      double[] B = { 0.0, -1.0, -2.0, -3.0, -4.0, -5.0 };
      
      double[] calculateds = SArrays.multiply(A, B);
      double[] expecteds = { 0.0, -1.0, -4.0, -9.0, -16.0, -25.0 };
      
      Assert.assertArrayEquals(expecteds, calculateds, 0.0001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void multiply_forArrayTest2()");
    }
    
  }
  
  /**
   * Test de la méthode <b>divide</b> où l'on vérifie qu'il y a eu allocation de mémoire pour retourner le résultat.
   */
  @Test
  public void divideTest1()
  {
    try{
      
      double[] A = {  };
      double[] B = {  };
      
      double[] calculated = SArrays.divide(A, B);
      
      // Vérification de la nouvelle allocation de mémoire
      Assert.assertNotEquals(calculated, A);
      Assert.assertNotEquals(calculated, B);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void divideTest1()");
    }
    
  }
  
  /**
   * Test de la méthode <b>divide</b> dans un cas simple.
   */
  @Test
  public void divideTest2()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
      double[] B = { 6.0, 5.0, 4.0, 3.0, 2.0, 1.0 };
      
      double[] calculateds = SArrays.divide(A, B);
      double[] expecteds = { 0.0, 0.2, 0.5, 1.0, 2.0, 5.0 };
      
      Assert.assertArrayEquals(expecteds, calculateds, 0.0001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void divideTest2()");
    }
    
  }
  
  /**
   * Test de la méthode <b>pow</b> où l'on vérifie qu'il y a eu allocation de mémoire pour retourner le résultat.
   */
  @Test
  public void powTest1()
  {
    try{
      
      double[] A = {  };
           
      double[] calculated = SArrays.pow(A, 4.6);
      
      // Vérification de la nouvelle allocation de mémoire
      Assert.assertNotEquals(calculated, A);
            
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void powTest1()");
    }
    
  }
  
  /**
   * Test de la méthode <b>pow</b> dans un cas simple.
   */
  @Test
  public void powTest2()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
            
      double[] calculateds = SArrays.pow(A, 3.0);
      double[] expecteds = { 0.0, 1.0, 8.0, 27.0, 64.0, 125.0 };
      
      Assert.assertArrayEquals(expecteds, calculateds, 0.0001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void powTest2()");
    }
    
  }
  
  /**
   * Test de la méthode <b>dot</b> dans un cas simple.
   */
  @Test
  public void dotTest1()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
      double[] B = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
      
      double calculated = SArrays.dot(A, B);
      double expected = 15.0;
      
      Assert.assertEquals(expected, calculated, 0.0001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void dotTest1()");
    }
    
  }
  
  /**
   * Test de la méthode <b>dot</b> dans un cas quelconque.
   */
  @Test
  public void dotTest2()
  {
    try{
      
      double[] A = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };
      double[] B = { 4.0, -2.0, 6.0, -8.0, 7.0, 2.0 };
      
      double calculated = SArrays.dot(A, B);
      double expected = 24.0;
      
      Assert.assertEquals(expected, calculated, 0.0001);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void dotTest2()");
    }
    
  }
  
  /**
   * Test de la méthode <b>softmax<b> dans un cas général.
   * Référence : https://en.wikipedia.org/wiki/Softmax_function
   */
  @Test
  public void softmaxTest1()
  {
    try{
      
      double[] x = { 1.0, 2.0, 3.0, 4.0, 1.0, 2.0, 3.0 };
      double[] result = new double[x.length];
      
      double[] expecteds = { 0.024, 0.064, 0.175, 0.475, 0.024, 0.064, 0.175 };
      
      Assert.assertArrayEquals(expecteds, SArrays.softmax(x, result), 0.001);
      
    }catch(SNoImplementationException e){ 
      SLog.logWriteLine("SArraysTest ---> Test non effectué : public void softmaxTest1()");
    }
  }
  
  /**
   * JUnit Test de la méthode <b>findMax</b>.
   */
  @Test
  public void findMaxTest()
  {
    int[] tab = { 45, 76, 23, -45, -123, 32, 67 };
    
    Assert.assertEquals(76, SArrays.findMax(tab));
  }
  
  /**
   * JUnit Test de la méthode findMin.
   */
  @Test
  public void findMinTest()
  {
    int[] tab = { 45, 76, 23, -45, -123, 32, 67 };
    
    Assert.assertEquals(-123, SArrays.findMin(tab));
  }
  
  /**
   * JUnit Test de la méthode intersectionSortedArray.
   */
  @Test
  public void intersectionSortedArrayTest1()
  {
   double[] tab1 = { 2.3, 4.5, 7.4, 9.4, 12.3 };
   double[] tab2 = { 2.6, 4.5, 7.2, 9.4, 14.3 };
   
   double[] calculated_solution = SArrays.intersectionSortedArray(tab1, tab2);
   double[] expected_solution = { 4.5, 9.4 };
   
   Assert.assertArrayEquals(expected_solution, calculated_solution, SMath.EPSILON);
  }

}// fin de la classe SArrayUtilTest
