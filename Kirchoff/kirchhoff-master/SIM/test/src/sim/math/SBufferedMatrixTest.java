package sim.math;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.util.SLog;

/**
 * <p>JUnit test permettant de valider les fonctionnalit�s de la classe <b>SBufferedMatrix</b>.</p>
 *
 * Voici quelques sites utilis�s pour r�aliser les tests :
 * <ul>https://fr.wikipedia.org/wiki/%C3%89limination_de_Gauss-Jordan#cite_note-4</ul>
 * <ul>https://matrix.reshish.com/gaussSolution.php</ul>
 * 
 * @author Simon Vezina
 * @since 2017-03-15
 * @version 2019-10-01
 */
public class SBufferedMatrixTest {

  /**
   * Test de la m�thode <b>swapLine</b> dans le cas simple de la permutation de deux lignes.
   */
  @Test
  public void swapLineTest1()
  {
    try{
    
      double[] eq1 = { 1.0, 2.0, 3.0, 4.0 };
      double[] eq2 = { 5.0, 6.0, 7.0, 8.0 };
      double[] eq3 = { 9.0, 10.0, 11.0, 12.0 };
      double[] eq4 = { 13.0, 14.0, 15.0, 16.0 };
      
      double[][] calculated_equations = { eq1, eq2, eq3, eq4 };
      double[][] expected_equations = { eq1, eq4, eq3, eq2 };
      
      // Faire le test de la permutation de la ligne 1 avec la ligne 3 (la 1i�re �tant d'indice 0)
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(calculated_equations);
      calculated_matrix.swapLine(1, 3);
      
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      Assert.assertEquals(expected_matrix, calculated_matrix);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void swapLineTest1()");
    }
  }

  /**
   * Test de la m�thode <b>addLines</b> dans le cas d'une matrice 3x2.
   */
  @Test
  public void addLinesTest1a()
  {
    try{
      
      double[] eq1 = { 1.0, 2.0 };
      double[] eq2 = { 5.0, 6.0 };
      double[] eq3 = { 9.0, 10.0 };
      double[][] calculated_equations = { eq1, eq2, eq3 };
      
      // Faire l'addition de la ligne eq1 avec la ligne eq2.
      double[] eq_addition = { 6.0 , 8.0 }; 
      
      double[][] expected_equations = { eq1, eq_addition, eq3 };
      
      // Faire le test de l'addition de la ligne 1 (no0) avec la ligne 2 (no1) et mettre le r�sultat � la ligne 2 (no1).
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(calculated_equations);
      calculated_matrix.addLines(1.0, 0, 1.0, 1, 1);
      
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      Assert.assertEquals(expected_matrix, calculated_matrix);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void addLinesTest1a()");
    }
  }
  
  /**
   * Test de la m�thode <b>addLines</b> dans le cas d'une matrice 2x3.
   */
  @Test
  public void addLinesTest1b()
  {
    try{
      
      double[] eq1 = { 1.0, 2.0, 3.0 };
      double[] eq2 = { 5.0, 6.0, 7.0 };
      double[][] calculated_equations = { eq1, eq2 };
      
      // Faire l'addition de la ligne eq1 avec la ligne eq2.
      double[] eq_addition = { 6.0 , 8.0, 10.0 }; 
      
      double[][] expected_equations = { eq1, eq_addition };
      
      // Faire le test de l'addition de la ligne 1 (no0) avec la ligne 2 (no1) et mettre le r�sultat � la ligne 2 (no1).
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(calculated_equations);
      calculated_matrix.addLines(1.0, 0, 1.0, 1, 1);
      
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      Assert.assertEquals(expected_matrix, calculated_matrix);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void addLinesTest1b()");
    }
  }
  
  /**
   * Test de la m�thode <b>addLines</b> dans le cas de l'addition de la ligne 0 et 1 et le r�sultat est dans la ligne 1.
   */
  @Test
  public void addLinesTest2()
  {
    try{
      
      double[] eq1 = { 1.0, 2.0, 3.0, 4.0 };
      double[] eq2 = { 5.0, 6.0, 7.0, 8.0 };
      double[] eq3 = { 9.0, 10.0, 11.0, 12.0 };
      double[] eq4 = { 13.0, 14.0, 15.0, 16.0 };
      double[][] calculated_equations = { eq1, eq2, eq3, eq4 };
      
      // Faire l'addition de la ligne eq1 avec la ligne eq2.
      double[] eq_addition = { 6.0 , 8.0 , 10.0 , 12.0 }; 
      
      double[][] expected_equations = { eq1, eq_addition, eq3, eq4 };
      
      // Faire le test de l'addition de la ligne 1 (no0) avec la ligne 2 (no1) et mettre le r�sultat � la ligne 2 (no1).
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(calculated_equations);
      calculated_matrix.addLines(1.0, 0, 1.0, 1, 1);
      
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      Assert.assertEquals(expected_matrix, calculated_matrix);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void addLinesTest2()");
    }
  }
  
  /**
   * Test de la m�thode <b>addLines</b> dans le cas de la soustraction de la ligne 0 et 1 et le r�sultat est dans la ligne 1.
   */
  @Test
  public void addLinesTest3()
  {
    try{
    
      double[] eq1 = { 1.0, 2.0, 3.0, 4.0 };
      double[] eq2 = { 5.0, 6.0, 7.0, 8.0 };
      double[] eq3 = { 9.0, 10.0, 11.0, 12.0 };
      double[] eq4 = { 13.0, 14.0, 15.0, 16.0 };
      double[][] calculated_equations = { eq1, eq2, eq3, eq4 };
      
      // Faire la soustraction de la ligne eq1 avec la ligne eq2.
      double[] eq_soustraction = { -4.0 , -4.0 , -4.0 , -4.0 }; 
          
      double[][] expected_equations = { eq1, eq_soustraction, eq3, eq4 };
      
      // Faire le test de l'addition de la ligne 1 (no0) avec la ligne 2 (no1) et mettre le r�sultat � la ligne 2 (no1).
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(calculated_equations);
      calculated_matrix.addLines(1.0, 0, -1.0, 1, 1);
      
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      Assert.assertEquals(expected_matrix, calculated_matrix);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void addLinesTest3()");
    }
  }
  
  /**
   * Test de la m�thode <b>addLines</b> dans le cas o� 5*L1 - 3*L2 --> L2
   */
  @Test
  public void addLinesTest4()
  {
    try{
      double[] eq1 = { 1.0, 2.0, 3.0, 4.0 };
      double[] eq2 = { 5.0, 6.0, 7.0, 8.0 };
      double[] eq3 = { 9.0, 10.0, 11.0, 12.0 };
      double[] eq4 = { 13.0, 14.0, 15.0, 16.0 };
      double[][] calculated_equations = { eq1, eq2, eq3, eq4 };
      
      // Faire la soustraction de la ligne eq1 avec la ligne eq2.
      double[] eq_soustraction = { 5.0*eq1[0] - 3.0*eq2[0] , 5.0*eq1[1] - 3.0*eq2[1] , 5.0*eq1[2] - 3.0*eq2[2] , 5.0*eq1[3] - 3.0*eq2[3] }; 
          
      double[][] expected_equations = { eq1, eq_soustraction, eq3, eq4 };
      
      // Faire le test de l'addition de la ligne 1 (no0) avec la ligne 2 (no1) et mettre le r�sultat � la ligne 2 (no1).
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(calculated_equations);
      calculated_matrix.addLines(5.0, 0, -3.0, 1, 1);
      
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      Assert.assertEquals(expected_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void addLinesTest4()");
    }
  }
  
  /**
   * Test de la m�thode <b>addLines</b> dans le cas o� 5*L1 - 3*L2 --> L4
   */
  @Test
  public void addLinesTest5()
  {
    try{
      double[] eq1 = { 1.0, 2.0, 3.0, 4.0 };
      double[] eq2 = { 5.0, 6.0, 7.0, 8.0 };
      double[] eq3 = { 9.0, 10.0, 11.0, 12.0 };
      double[] eq4 = { 13.0, 14.0, 15.0, 16.0 };
      double[][] calculated_equations = { eq1, eq2, eq3, eq4 };
      
      // Faire la soustraction de la ligne eq1 avec la ligne eq2.
      double[] eq_soustraction = { 5.0*eq1[0] - 3.0*eq2[0] , 5.0*eq1[1] - 3.0*eq2[1] , 5.0*eq1[2] - 3.0*eq2[2] , 5.0*eq1[3] - 3.0*eq2[3] }; 
          
      double[][] expected_equations = { eq1, eq2, eq3, eq_soustraction };
      
      // Faire le test de l'addition de la ligne 1 (no0) avec la ligne 2 (no1) et mettre le r�sultat � la ligne 2 (no1).
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(calculated_equations);
      calculated_matrix.addLines(5.0, 0, -3.0, 1, 3);
      
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      Assert.assertEquals(expected_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void addLinesTest5()");
    }
  }
  
  /**
   * Test de la m�thode <b>multiplyLine</b> dans le cas d'une matrice 3x2.
   */
  @Test
  public void multiplyLineTest1a()
  {
    try{
      
      double scalar = 5.0;
      
      double[] eq1 = { 1.0, 2.0 };
      double[] eq2 = { 5.0, 6.0 };
      double[] eq3 = { 9.0, 10.0 };
      double[][] calculated_equations = { eq1, eq2, eq3 };
      
      double[] eq_multiply = { eq2[0]*scalar , eq2[1]*scalar }; 
      
      double[][] expected_equations = { eq1, eq_multiply, eq3 };
      
      // Faire le test de de la multiplication d'une ligne avec un scalaire.
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(calculated_equations);
      calculated_matrix.multiplyLine(scalar, 1);
      
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      Assert.assertEquals(expected_matrix, calculated_matrix);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void multiplyLineTest1a()");
    }
  }
  
  /**
   * Test de la m�thode <b>multiplyLine</b> dans le cas d'une matrice 2x4.
   */
  @Test
  public void multiplyLineTest1b()
  {
    try{
      
      double scalar = 5.0;
      
      double[] eq1 = { 1.0, 2.0, 3.0, 4.0 };
      double[] eq2 = { 5.0, 6.0, 7.0, 8.0 };
      double[][] calculated_equations = { eq1, eq2 };
      
      double[] eq_multiply = { eq2[0]*scalar , eq2[1]*scalar , eq2[2]*scalar , eq2[3]*scalar }; 
      
      double[][] expected_equations = { eq1, eq_multiply };
      
      // Faire le test de de la multiplication d'une ligne avec un scalaire.
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(calculated_equations);
      calculated_matrix.multiplyLine(scalar, 1);
      
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      Assert.assertEquals(expected_matrix, calculated_matrix);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void multiplyLineTest1b()");
    }
  }
  
  /**
   * Test de la m�thode <b>multiplyLine</b> dans le cas de la multiplication de la ligne 1 avec un scalaire.
   */
  @Test
  public void multiplyLineTest1()
  {
    try{
      
      double scalar = 5.0;
      
      double[] eq1 = { 1.0, 2.0, 3.0, 4.0 };
      double[] eq2 = { 5.0, 6.0, 7.0, 8.0 };
      double[] eq3 = { 9.0, 10.0, 11.0, 12.0 };
      double[] eq4 = { 13.0, 14.0, 15.0, 16.0 };
      double[][] calculated_equations = { eq1, eq2, eq3, eq4 };
      
      double[] eq_multiply = { eq2[0]*scalar , eq2[1]*scalar , eq2[2]*scalar , eq2[3]*scalar }; 
      
      double[][] expected_equations = { eq1, eq_multiply, eq3, eq4 };
      
      // Faire le test de de la multiplication d'une ligne avec un scalaire.
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(calculated_equations);
      calculated_matrix.multiplyLine(scalar, 1);
      
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      Assert.assertEquals(expected_matrix, calculated_matrix);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void multiplyLineTest1()");
    }
  }
  
  /**
   * Test de la m�thode <b>multiplyLine</b> dans le cas de la multiplication de la ligne 1 avec un scalaire.
   */
  @Test
  public void multiplyLineTest2()
  {
    try{
    
      double scalar = -2.8;
    
      double[] eq1 = { 1.0, 2.0, 3.0, 4.0 };
      double[] eq2 = { 5.0, 6.0, 7.0, 8.0 };
      double[] eq3 = { 9.0, 10.0, 11.0, 12.0 };
      double[] eq4 = { 13.0, 14.0, 15.0, 16.0 };
      double[][] calculated_equations = { eq1, eq2, eq3, eq4 };
      
      double[] eq_multiply = { eq2[0]*scalar , eq2[1]*scalar , eq2[2]*scalar , eq2[3]*scalar }; 
      
      double[][] expected_equations = { eq1, eq_multiply, eq3, eq4 };
      
      // Faire le test de de la multiplication d'une ligne avec un scalaire.
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(calculated_equations);
      calculated_matrix.multiplyLine(scalar, 1);
      
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      Assert.assertEquals(expected_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void multiplyLineTest2()");
    }
  }
    
  /**
   * Test de la méthode <b>getNbPivot()</b> pour une matrice 5x6.
   * Le test vise à vérifier que le nombre de pivot sera le terme le plus petit entre 5 lignes et 6 colonnes �tant 5.
   */
  @Test 
  public void getNbPivotTest1()
  {
    try{
      
      double[] eq1 = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0 };
      double[] eq2 = { 7.0, 8.0, 9.0, 10.0, 11.0, 12.0 };
      double[] eq3 = { 13.0, 14.0, 15.0, 16.0, 17.0, 18.0 };
      double[] eq4 = { 19.0, 20.0, 21.0, 22.0, 23.0, 24.0 };
      double[] eq5 = { 25.0, 26.0, 27.0, 28.0, 29.0, 30.0 };
      double[][] data = { eq1, eq2, eq3, eq4, eq5 };
      
      // Construction de la matrice
      SBufferedMatrix matrix = new SBufferedMatrix(data);
      
      // Faire le test du nombre de pivot.
      Assert.assertEquals(5, matrix.getNbPivot());
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectué : public void getNbPivotTest1()");
    }
  }
    
  /**
   * Test de la méthode <b>getNbPivot()</b> pour une matrice 6x5.
   * Le test vise à vérifier que le nombre de pivot sera le terme le plus petit entre 6 lignes et 5 colonnes �tant 5.
   */
  @Test 
  public void getNbPivotTest2()
  {
    try{
      
      double[] eq1 = { 1.0, 2.0, 3.0, 4.0, 5.0 };
      double[] eq2 = { 7.0, 8.0, 9.0, 10.0, 11.0 };
      double[] eq3 = { 13.0, 14.0, 15.0, 16.0, 17.0 };
      double[] eq4 = { 19.0, 20.0, 21.0, 22.0, 23.0 };
      double[] eq5 = { 25.0, 26.0, 27.0, 28.0, 29.0 };
      double[] eq6 = { 35.0, 36.0, 37.0, 38.0, 39.0 };
      double[][] data = { eq1, eq2, eq3, eq4, eq5, eq6 };
      
      // Construction de la matrice
      SBufferedMatrix matrix = new SBufferedMatrix(data);
      
      // Faire le test du nombre de pivot.
      Assert.assertEquals(5, matrix.getNbPivot());
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void getNbPivotTest2()");
    }
  }
  
  /**
   * Test de la m�thode <b>findLineWithMaxValueForPivot</b> avec uniquement des valeurs positives.
   */
  @Test
  public void findLineWithMaxValueForPivotTest1()
  {
    try{
      
      double[] eq1 = { 10.0, 5.0, 25.0, 35.0 };
      double[] eq2 = { 0.0, 0.0, 2.0, 40.0 };
      double[] eq3 = { 15.0, 4.0, 8.0, 12.0 };
      double[] eq4 = { 0.0, 8.0, 24.0, 32.0 };
      double[][] matrix_data = { eq1, eq2, eq3, eq4 };
      
      SBufferedMatrix matrix = new SBufferedMatrix(matrix_data);
      
      // Faire le test
      Assert.assertEquals(2, matrix.findLineWithMaxValueForPivot(0));
      Assert.assertEquals(3, matrix.findLineWithMaxValueForPivot(1));
      Assert.assertEquals(3, matrix.findLineWithMaxValueForPivot(2));    // assurer que la recherche est sous le pivot
      Assert.assertEquals(3, matrix.findLineWithMaxValueForPivot(3));    // assurer que la recherche est sous le pivot
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void findLineWithMaxValueForPivotTest1()");
    }
  }
  
  /**
   * Test de la m�thode <b>findLineWithMaxValueForPivot</b> avec valeurs n�gatives.
   */
  @Test
  public void findLineWithMaxValueForPivotTest2()
  {
    try{
      
      double[] eq1 = { 10.0, 5.0, 25.0, 35.0 };
      double[] eq2 = { 0.0, 0.0, 2.0, 40.0 };
      double[] eq3 = { -15.0, 4.0, 8.0, 12.0 };
      double[] eq4 = { 0.0, -8.0, -24.0, 32.0 };
      double[][] matrix_data = { eq1, eq2, eq3, eq4 };
      
      SBufferedMatrix matrix = new SBufferedMatrix(matrix_data);
      
      // Faire le test
      Assert.assertEquals(2, matrix.findLineWithMaxValueForPivot(0));
      Assert.assertEquals(3, matrix.findLineWithMaxValueForPivot(1));
      Assert.assertEquals(3, matrix.findLineWithMaxValueForPivot(2));    // assurer que la recherche est sous le pivot
      Assert.assertEquals(3, matrix.findLineWithMaxValueForPivot(3));    // assurer que la recherche est sous le pivot
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void findLineWithMaxValueForPivotTest2()");
    }
  }
  
  /**
   * Test de la m�thode <b>findLineWithMaxValueForPivot</b> qui vise � s'assurer que la ligne de pivot maximal est toujours sous la ligne du pivot de r�f�rence.
   * Dans ce test, la valeur la plus grand de la colonne sera sous le pivot, mais pas le dernier. 
   */
  @Test
  public void findLineWithMaxValueForPivotTest3a()
  {
    try{
      
      double[] eq1 = { 0.0, 0.0, 4.0 };
      double[] eq2 = { 0.0, 0.0, 5.0 };
      double[] eq3 = { 0.0, 0.0, 2.0 };     // <-- Le pivot en �tude.
      double[] eq4 = { 0.0, 0.0, 7.0 };     // <-- La ligne � obtenir.
      double[] eq5 = { 0.0, 0.0, 3.0 };
      double[] eq6 = { 0.0, 0.0, 1.0 };
      double[] eq7 = { 0.0, 0.0, 4.0 };     // <-- PAS BON.
      double[][] matrix_data = { eq1, eq2, eq3, eq4, eq5, eq6, eq7 };
      
      SBufferedMatrix matrix = new SBufferedMatrix(matrix_data);
      
      // Faire le test
      Assert.assertEquals(3, matrix.findLineWithMaxValueForPivot(2));
          
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void findLineWithMaxValueForPivotTest3a()");
    }
  }
  
  /**
   * Test de la m�thode <b>findLineWithMaxValueForPivot</b> qui vise � s'assurer que la ligne de pivot maximal est toujours sous la ligne du pivot de r�f�rence.
   * Dans ce test, la valeur la plus grand de la colonne sera audessus du pivot 
   */
  @Test
  public void findLineWithMaxValueForPivotTest3b()
  {
    try{
      
      double[] eq1 = { 0.0, 0.0, 7.0 };     // <-- PAS BON.
      double[] eq2 = { 0.0, 0.0, 5.0 };
      double[] eq3 = { 0.0, 0.0, 2.0 };     // <-- Le pivot en �tude.
      double[] eq4 = { 0.0, 0.0, 4.0 };     
      double[] eq5 = { 0.0, 0.0, 6.0 };     // <-- La ligne � obtenir.
      double[] eq6 = { 0.0, 0.0, 1.0 };
      double[] eq7 = { 0.0, 0.0, 3.0 };     // <-- PAS BON.
      double[][] matrix_data = { eq1, eq2, eq3, eq4, eq5, eq6, eq7 };
      
      SBufferedMatrix matrix = new SBufferedMatrix(matrix_data);
      
      // Faire le test
      Assert.assertEquals(4, matrix.findLineWithMaxValueForPivot(2));
          
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void findLineWithMaxValueForPivotTest3b()");
    }
  }
  
  /**
   * Test de la m�thode <b>findLineWithMaxValueForPivot</b> qui vise � s'assurer que la ligne de pivot maximal est toujours sous la ligne du pivot de r�f�rence.
   * Dans ce test, la valeur la plus grand sera la position du pivot lui-meme �tant z�ro.
   */
  @Test
  public void findLineWithMaxValueForPivotTest3c()
  {
    try{
      
      double[] eq1 = { 0.0, 0.0, 7.0 };     // <-- PAS BON.
      double[] eq2 = { 0.0, 0.0, 5.0 };
      double[] eq3 = { 0.0, 0.0, 0.0 };     // <-- Le pivot en étude et celui à choisir.
      double[] eq4 = { 0.0, 0.0, 0.0 };     
      double[] eq5 = { 0.0, 0.0, 0.0 };
      double[] eq6 = { 0.0, 0.0, 0.0 };
      double[] eq7 = { 0.0, 0.0, 0.0 };     
      double[][] matrix_data = { eq1, eq2, eq3, eq4, eq5, eq6, eq7 };
      
      SBufferedMatrix matrix = new SBufferedMatrix(matrix_data);
      
      // Faire le test
      Assert.assertEquals(2, matrix.findLineWithMaxValueForPivot(2));
          
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void findLineWithMaxValueForPivotTest3c()");
    }
  }
  
  /**
   * Test de la m�thode <b>gaussJordanReduction</b> dans le cas o� la matrice est l'identit�. Il n'y a pas de changement.
   */
  @Test
  public void gaussJordanReductionTest1()
  {
    try{
      
      // �quations initiales
      double[] eq1 = { 1.0, 0.0, 0.0, 0.0 };
      double[] eq2 = { 0.0, 1.0, 0.0, 0.0 };
      double[] eq3 = { 0.0, 0.0, 1.0, 0.0 };
      double[] eq4 = { 0.0, 0.0, 0.0, 1.0 };
      double[][] equations = { eq1, eq2, eq3, eq4};
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
      SBufferedMatrix copy_matrix = new SBufferedMatrix(equations);
      
      calculated_matrix.gaussJordanReduction();
      
      // V�rification que la matrice identit� n'est pas chang�e sous l'action de la r�duction de Gauss-Jordan.
      Assert.assertEquals(copy_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void gaussJordanReductionTest1()");
    }
  }
   
  /**
   * Test de la m�thode <b>gaussJordanReduction</b> dans le cas o� la matrice est l'identit� poss�de 3 lignes et 4 colonnes. Il n'y a pas de changement.
   */
  @Test
  public void gaussJordanReductionTest2()
  {
    try{
      
      // �quations initiales
      double[] eq1 = { 1.0, 0.0, 0.0, 0.0 };
      double[] eq2 = { 0.0, 1.0, 0.0, 0.0 };
      double[] eq3 = { 0.0, 0.0, 1.0, 0.0 };
      double[][] equations = { eq1, eq2, eq3};
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
      SBufferedMatrix copy_matrix = new SBufferedMatrix(equations);
      
      calculated_matrix.gaussJordanReduction();
      
      // V�rification que la matrice identit� n'est pas chang�e sous l'action de la r�duction de Gauss-Jordan.
      Assert.assertEquals(copy_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void gaussJordanReductionTest2()");
    }catch(ArrayIndexOutOfBoundsException e){
		fail("Ce test v�rifie que la r�duction de Gauss-Jordan s'effectue sur le bon nombre de pivot. Pour r�ussir ce test, l'exception ArrayIndexOutOfBoundsException ne doit pas �tre lanc�e.");
	}
  }
  
  /**
   * Test de la m�thode <b>gaussJordanReduction</b> dans le cas o� la matrice est l'identit� poss�de 4 lignes et 3 colonnes. Il n'y a pas de changement.
   */
  @Test
  public void gaussJordanReductionTest3()
  {
    try{
      
      // �quations initiales
      double[] eq1 = { 1.0, 0.0, 0.0 };
      double[] eq2 = { 0.0, 1.0, 0.0 };
      double[] eq3 = { 0.0, 0.0, 1.0 };
      double[] eq4 = { 0.0, 0.0, 0.0 };
      double[][] equations = { eq1, eq2, eq3, eq4};
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
      SBufferedMatrix copy_matrix = new SBufferedMatrix(equations);
      
      calculated_matrix.gaussJordanReduction();
      
      // V�rification que la matrice identit� n'est pas chang�e sous l'action de la r�duction de Gauss-Jordan.
      Assert.assertEquals(copy_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void gaussJordanReductionTest3()");
    }catch(ArrayIndexOutOfBoundsException e){
		fail("Ce test v�rifie que la r�duction de Gauss-Jordan s'effectue sur le bon nombre de pivot. Pour r�ussir ce test, l'exception ArrayIndexOutOfBoundsException ne doit pas �tre lanc�e.");
	}
  }
  
  /**
   * Test de la méthode <b>gaussJordanReduction</b> dans le cas o� la matrice est l'identit�, mais avec des changements de ligne nécessaire.
   * Ce test permet de vérifier s'il y a recherche de la ligne avec le pivot le plus grand.
   */
  @Test
  public void gaussJordanReductionTest4()
  {
    try{
    
      // Équations initiales
      double[] eq1 = { 1.0, 0.0, 0.0, 0.0 };
      double[] eq2 = { 0.0, 0.0, 1.0, 0.0 };
      double[] eq3 = { 0.0, 0.0, 0.0, 1.0 };
      double[] eq4 = { 0.0, 1.0, 0.0, 0.0 };
          
      double[][] equations = { eq1, eq2, eq3, eq4};
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
      calculated_matrix.gaussJordanReduction();
      
      // Éléments attendus
      double[][] expected_equations = { eq1, eq4, eq2, eq3 };
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      // Vérification que la matrice identité est retrouvée.
      Assert.assertEquals(expected_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void gaussJordanReductionTest4()");
    }catch(SSingularMatrixException e){
    	fail("Ce test v�rifie s'il y a permuttation des lignes lors de la recherche du pivot de plus grande valeur. Pour r�ussir ce test, l'exception de la matrice singuli�re (SSingularMatrixException) ne doit pas �tre lanc�e.");
    }
  }
  
  /**
   * Test de la m�thode <b>gaussJordanReduction</b> pour une matrice 4x3.
   */
  @Test
  public void gaussJordanReductionTest5()
  {
    try{
      
      // Test venant du site : http://web.thu.edu.tw/wenwei/www/Courses/linalgebra/sec1.5.pdf
      
      // �l�ments calcul�s
      double[] eq1 = { 1.0, 2.0, 3.0, 9.0 };
      double[] eq2 = { 2.0, -1.0, 1.0, 8.0 };
      double[] eq3 = { 3.0, 0.0, -1.0, 3.0 };
      double[][] equations = { eq1, eq2, eq3 };
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
      calculated_matrix.gaussJordanReduction();
      
      // �l�ments attendus
      double[] expected_eq1 = { 1.0, 0.0, 0.0, 2.0 };
      double[] expected_eq2 = { 0.0, 1.0, 0.0, -1.0 };
      double[] expected_eq3 = { 0.0, 0.0, 1.0, 3.0 };
      double[][] expected_equations = { expected_eq1, expected_eq2, expected_eq3 };
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      // V�rification de l'�galit�
      Assert.assertEquals(expected_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void gaussJordanReductionTest5()");
    } 
  }
  
  /**
   * Test de la m�thode <b>gaussJordanReduction</b> pour une matrice 5x4 o� il y aura formation d'une matrice singuli�re.
   */
  @Test
  public void gaussJordanReductionTest6()
  {
    try{
      
      // Test venant du site : http://web.thu.edu.tw/wenwei/www/Courses/linalgebra/sec1.5.pdf
      
      // �l�ments calcul�s
      double[] eq1 = { 1.0, 1.0, 2.0, -5.0, 3.0 };
      double[] eq2 = { 2.0, 5.0, -1.0, -9.0, -3.0 };
      double[] eq3 = { 2.0, 1.0, -1.0, 3.0, -11.0 };
      double[] eq4 = { 1.0, -3.0, 2.0, 7.0, -5.0 };
      double[][] equations = { eq1, eq2, eq3, eq4 };
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
          
      // Le r�sultat attendu serait une matrice singuli�re. Dans cette impl�mentation de la r�duction de Gauss-Jordan,
      // ce r�sultat n'est pas acceptable et une exception doit �tre lanc�e.
      // �l�ments attendus (si l'on accepte les matrices non singuli�re).
      
      // double[] expected_eq1 = { 1.0, 0.0, 0.0, 2.0, -5.0 };
      // double[] expected_eq2 = { 0.0, 1.0, 0.0, -3.0, 2.0 };
      // double[] expected_eq3 = { 0.0, 0.0, 1.0, -2.0, 3.0 };
      // double[] expected_eq4 = { 0.0, 0.0, 0.0, 0.0, 0.0 };
      // double[][] expected_equations = { expected_eq1, expected_eq2, expected_eq3, expected_eq4 };
      // SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
    
      try{
        calculated_matrix.gaussJordanReduction();  
        
    	// �chec si l'exception n'est pas lanc�e !
        fail("Ce test v�rifie si la r�duction de Gauss-Jordan menne � une matrice singuli�re. Pour r�ussir le test, il faut que la m�thode lance un exception de type SSingularMatrixException."); 
      }catch(SSingularMatrixException e){
        // c'est un succ�s !
      }
     
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void gaussJordanReductionTest6()");
    } 
  }
  
  /**
   * Test de la m�thode <b>gaussJordanColumnReduction</b> dans le cas o� la matrice est l'identit�.
   */
  @Test
  public void gaussJordanColumnReductionTest1()
  {
    try{
    
      // �quations intiiales
      double[] eq1 = { 1.0, 0.0, 0.0, 0.0 };
      double[] eq2 = { 0.0, 1.0, 0.0, 0.0 };
      double[] eq3 = { 0.0, 0.0, 1.0, 0.0 };
      double[] eq4 = { 0.0, 0.0, 0.0, 1.0 };
      double[][] equations = { eq1, eq2, eq3, eq4};
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
      
      SBufferedMatrix copy_matrix = new SBufferedMatrix(equations);
      
      // Faire les r�ductions "inutiles".
      calculated_matrix.gaussJordanColumnReduction(0);
      calculated_matrix.gaussJordanColumnReduction(1);
      calculated_matrix.gaussJordanColumnReduction(2);
      calculated_matrix.gaussJordanColumnReduction(3);
      
      // V�rification que la matrice identit� n'est pas chang�e sous l'action de la r�duction de Gauss-Jordan.
      Assert.assertEquals(copy_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void gaussJordanColumnReductionTest1()");
    }
  }
  
  /**
   * Test de la m�thode <b>gaussJordanColumnReduction</b> dans le cas o� la matrice ne comporte qu'une seule ligne.
   */
  @Test
  public void gaussJordanColumnReductionTest2()
  {
    try{
    
      // �quations intiiales
      double[] eq1 = { 5.0, 10.0, 15.0, 20.0 };
      double[][] equations = { eq1 };
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
      
      double[] expected_eq1 = { 1.0, 2.0, 3.0, 4.0 };
      double[][] expected_equations = { expected_eq1 };
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      // Faire les r�ductions "inutiles".
      calculated_matrix.gaussJordanColumnReduction(0);
            
      // V�rification que la matrice identit� n'est pas chang�e sous l'action de la r�duction de Gauss-Jordan.
      Assert.assertEquals(expected_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void gaussJordanColumnReductionTest2()");
    }
  }
  
  /**
   * Test de la m�thode <b>gaussJordanColumnReduction</b> dans le cas o� la matrice ne comporte qu'une seule colonne.
   */
  @Test
  public void gaussJordanColumnReductionTest3()
  {
    try{
    
      // �quations intiiales
      double[] eq1 = { 8.0 };
      double[] eq2 = { 10.0 };
      double[] eq3 = { -15.0};
      double[] eq4 = { 34.0 };
      double[][] equations = { eq1, eq2, eq3, eq4};
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
      
      double[] expected_eq1 = { 1.0 };
      double[] expected_eq2 = { 0.0 };
      double[] expected_eq3 = { 0.0 };
      double[] expected_eq4 = { 0.0 };
      double[][] expected_equations = { expected_eq1, expected_eq2, expected_eq3, expected_eq4 };
      SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
      
      // Faire les r�ductions "inutiles".
      calculated_matrix.gaussJordanColumnReduction(0);
            
      // V�rification que la matrice identit� n'est pas chang�e sous l'action de la r�duction de Gauss-Jordan.
      Assert.assertEquals(expected_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void gaussJordanColumnReductionTest3()");
    }
  }
  
  /**
   * Test de la m�thode <b>gaussJordanColumnReduction</b> dans un cas complexe.
   */
  @Test
  public void gaussJordanColumnReductionTest4()
  {
    try{
    
      // Test venant du site : https://matrix.reshish.com/gaussSolution.php
    
      // �quations intiiales
      double[] eq1 = { 1.0, 6.0, 5.0, -7.0, 3.0, -2.0, 3.0 };
      double[] eq2 = { -7.0, -5.0, -3.0, 2.0, -9.0, 4.0, 4.0 };
      double[] eq3 = { 5.0, 4.0, 2.0, 1.0, 3.0, 8.0, -6.0 };
      double[] eq4 = { 9.0, -3.0, 3.0, -7.0, 6.0, 7.0, 2.0 };
      double[] eq5 = { 4.0, 5.0, 5.0, 6.0, 5.0, -4.0, 1.0 };
      double[][] equations = { eq1, eq2, eq3, eq4, eq5 };
      SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
      
      // 1i�re r�duction
      calculated_matrix.gaussJordanColumnReduction(0);
      
      double[] expected_eq1_reduction0 = { 1.0, 6.0, 5.0, -7.0, 3.0, -2.0, 3.0 };
      double[] expected_eq2_reduction0 = { 0.0, 37.0, 32.0, -47.0, 12.0, -10.0, 25.0 };
      double[] expected_eq3_reduction0 = { 0.0, -26.0, -23.0, 36.0, -12.0, 18.0, -21.0 };
      double[] expected_eq4_reduction0 = { 0.0, -57.0, -42.0, 56.0, -21.0, 25.0, -25.0 };
      double[] expected_eq5_reduction0 = { 0.0, -19.0, -15.0, 34.0, -7.0, 4.0, -11.0 };
      double[][] equations_reduction0 = { expected_eq1_reduction0, expected_eq2_reduction0, expected_eq3_reduction0, expected_eq4_reduction0, expected_eq5_reduction0 };
      SBufferedMatrix expected_matrix_reduction0 = new SBufferedMatrix(equations_reduction0);
      
      // V�rification de l'�galit� de la 1i�re r�duction
      Assert.assertEquals(expected_matrix_reduction0, calculated_matrix);
      
      // 2i�re r�duction
      calculated_matrix.gaussJordanColumnReduction(1);
      
      double[] expected_eq1_reduction1 = { 1.0, 0.0, -7.0/37.0, 23.0/37.0, 39.0/37.0, -14.0/37.0, -39.0/37.0 };
      double[] expected_eq2_reduction1 = { 0.0, 1.0, 32.0/37.0, -47.0/37.0, 12.0/37.0, -10.0/37.0, 25.0/37.0 };
      double[] expected_eq3_reduction1 = { 0.0, 0.0, -19.0/37.0, 110.0/37.0, -132.0/37.0, 406.0/37.0, -127.0/37.0 };
      double[] expected_eq4_reduction1 = { 0.0, 0.0, 270.0/37.0, -607.0/37.0, -93.0/37.0, 355.0/37.0, 500.0/37.0 };
      double[] expected_eq5_reduction1 = { 0.0, 0.0, 53.0/37.0, 365.0/37.0, -31.0/37.0, -42.0/37.0, 68.0/37.0 };
      double[][] equations_reduction1 = { expected_eq1_reduction1, expected_eq2_reduction1, expected_eq3_reduction1, expected_eq4_reduction1, expected_eq5_reduction1 };
      SBufferedMatrix expected_matrix_reduction1 = new SBufferedMatrix(equations_reduction1);
      
      // V�rification de l'�galit� de la 2i�re r�duction
      Assert.assertEquals(expected_matrix_reduction1, calculated_matrix);
      
      // 3i�re r�duction
      calculated_matrix.gaussJordanColumnReduction(2);
      
      double[] expected_eq1_reduction2 = { 1.0, 0.0, 0.0, -9.0/19.0, 45.0/19.0, -84.0/19.0, 4.0/19.0 };
      double[] expected_eq2_reduction2 = { 0.0, 1.0, 0.0, 71.0/19.0, -108.0/19.0, 346.0/19.0, -97.0/19.0 };
      double[] expected_eq3_reduction2 = { 0.0, 0.0, 1.0, -110.0/19.0, 132.0/19.0, -406.0/19.0, 127.0/19.0 };
      double[] expected_eq4_reduction2 = { 0.0, 0.0, 0.0, 491.0/19.0, -1011.0/19.0, 3145.0/19.0, -670.0/19.0 };
      double[] expected_eq5_reduction2 = { 0.0, 0.0, 0.0, 345.0/19.0, -205.0/19.0, 560.0/19.0, -147.0/19.0 };
      double[][] equations_reduction2 = { expected_eq1_reduction2, expected_eq2_reduction2, expected_eq3_reduction2, expected_eq4_reduction2, expected_eq5_reduction2 };
      SBufferedMatrix expected_matrix_reduction2 = new SBufferedMatrix(equations_reduction2);
      
      // V�rification de l'�galit� de la 3i�re r�duction
      Assert.assertEquals(expected_matrix_reduction2, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SBufferedMatrixTest ---> Test non effectu� : public void gaussJordanColumnReductionTest4()");
    }
  }
  
  /**
   * Test de la m�thode <b>augmented</b> pour une matrice 4x3. 
   */
  @Test
  public void toAugmentedTest1()
  {
    // �l�ments calcul�s
    double[] eq1 = { 1.0, 2.0, 3.0, 9.0 };
    double[] eq2 = { 2.0, -1.0, 1.0, 8.0 };
    double[] eq3 = { 3.0, 0.0, -1.0, 3.0 };
    double[][] equations = { eq1, eq2, eq3 };
    SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
    calculated_matrix.toAugmented();
    
    // �l�ments attendus
    double[] expected_eq1 = { 1.0, 2.0, 3.0, -9.0 };
    double[] expected_eq2 = { 2.0, -1.0, 1.0, -8.0 };
    double[] expected_eq3 = { 3.0, 0.0, -1.0, -3.0 };
    double[][] expected_equations = { expected_eq1, expected_eq2, expected_eq3 };
    SBufferedMatrix expected_matrix = new SBufferedMatrix(expected_equations);
    
    // V�rification de l'�galit�
    Assert.assertEquals(expected_matrix, calculated_matrix);
  }
  
  /**
   * Test de la m�thode <b>augmented</b> apr�s la double application de la m�thode. 
   */
  @Test
  public void toAugmentedTest2()
  {
    // �l�ments calcul�s
    double[] eq1 = { 1.0, 2.0, 3.0, 9.0 };
    double[] eq2 = { 2.0, -1.0, 1.0, 8.0 };
    double[] eq3 = { 3.0, 0.0, -1.0, 3.0 };
    double[][] equations = { eq1, eq2, eq3 };
    SBufferedMatrix calculated_matrix = new SBufferedMatrix(equations);
    
    calculated_matrix.toAugmented();
    calculated_matrix.toAugmented();
    
    // V�rification de l'�galit�
    Assert.assertEquals(calculated_matrix, calculated_matrix);
  }
  
  /**
   * Test de la m�thode <b>identity</b> dans le cas de la matrice identit�.
   */
  @Test
  public void identityTest1()
  {
    double[] eq1 = { 1.0, 0.0, 0.0, 0.0, 0.0 };
    double[] eq2 = { 0.0, 1.0, 0.0, 0.0, 0.0 };
    double[] eq3 = { 0.0, 0.0, 1.0, 0.0, 0.0 };
    double[] eq4 = { 0.0, 0.0, 0.0, 1.0, 0.0 };
    double[] eq5 = { 0.0, 0.0, 0.0, 0.0, 1.0 };
    
    SBufferedMatrix expected_solution = new SBufferedMatrix(eq1, eq2, eq3, eq4, eq5);
    
    SBufferedMatrix calculated_solution = SBufferedMatrix.identity(5);
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  /**
   * Test de la m�thode <b>vector</b> dans un cas g�n�rale.
   */
  @Test
  public void vectorTest1()
  {
    // D�finition de la matrice � partir du constructeur standare.
    double[] eq1 = { 2.3 };
    double[] eq2 = { 4.5 };
    double[] eq3 = { 5.6 };
    double[] eq4 = { 6.7 };
    double[] eq5 = { 7.8 };
    
    SBufferedMatrix expected_solution = new SBufferedMatrix(eq1, eq2, eq3, eq4, eq5);
    
    // D�finition de la matrice colonne � partir de la m�thode � tester.
    double[] v = { 2.3, 4.5, 5.6, 6.7, 7.8 };
    
    SBufferedMatrix calculated_solution = SBufferedMatrix.columnVector(v);
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
    
}//fin de la classe SBufferedMatrixTest
