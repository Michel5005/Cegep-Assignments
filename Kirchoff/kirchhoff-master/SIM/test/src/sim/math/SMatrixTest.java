/**
 * 
 */
package sim.math;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SMatrix</b>.
 * 
 * @author Simon Vezina
 * @since 2017-03-14
 * @version 2017-05-31
 */
public class SMatrixTest {

  /**
   * Test de la méthode <b>gaussJordanReduction</b> pour une matrice quelconque.
   * On vérifie qu'il y a bien une copie des informations de la matrice et que la matrice d'origine
   * reste inchangé après le calcul et le renvoie d'une nouvelle matrice correspondant à la 
   * réduction Gauss-Jordan de la matrice d'origine.
   */
  @Test
  public void gaussJordanReductionTest()
  {
    try{
    
      // Test venant du site : http://web.thu.edu.tw/wenwei/www/Courses/linalgebra/sec1.5.pdf
    
      // Éléments calculés
      double[] eq1 = { 1.0, 6.0, 5.0, -7.0, 3.0, -2.0, 3.0 };
      double[] eq2 = { -7.0, -5.0, -3.0, 2.0, -9.0, 4.0, 4.0 };
      double[] eq3 = { 5.0, 4.0, 2.0, 1.0, 3.0, 8.0, -6.0 };
      double[] eq4 = { 9.0, -3.0, 3.0, -7.0, 6.0, 7.0, 2.0 };
      double[] eq5 = { 4.0, 5.0, 5.0, 6.0, 5.0, -4.0, 1.0 };
      double[][] equations = { eq1, eq2, eq3, eq4, eq5 };
      SMatrix initial_matrix = new SMatrix(equations);      // il faut que "initial_matrix.equals(use_matrix) == true"
      SMatrix use_matrix = new SMatrix(equations);
      
      // Vérifier qu'il y a renvoie d'une nouvelle matrice et non une référence à la matrice initiale
      Assert.assertTrue(use_matrix != use_matrix.gaussJordanReduction());
      
      SMatrix calculated_matrix = use_matrix.gaussJordanReduction();
       
      // Vérifier que "initial_matrix" et "use_matrix sont égaux (donc non modifié), même après un appel de gaussJordanReduction()
      Assert.assertEquals(initial_matrix, use_matrix);
      
      // Éléments attendus
      SBufferedMatrix b_matrix = new SBufferedMatrix(equations);
      b_matrix.gaussJordanReduction();
      SMatrix expected_matrix = new SMatrix(b_matrix.getData());
      
      // Vérification de l'égalité
      Assert.assertEquals(expected_matrix, calculated_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SMatrixTest ---> Test non effectué : public void gaussJordanReductionTest()");
    }
  }
  
  /**
   * Test de la méthode <b>solvingLinearEquationSystem</b> pour une matrice 3x4 en format non augmenté.
   */
  @Test
  public void solvingLinearEquationSystemTest1()
  {
    try{
      
      // Test venant du site : http://web.thu.edu.tw/wenwei/www/Courses/linalgebra/sec1.5.pdf
      
      // Éléments calculés
      double[] eq1 = { 1.0, 2.0, 3.0, -9.0 };
      double[] eq2 = { 2.0, -1.0, 1.0, -8.0 };
      double[] eq3 = { 3.0, 0.0, -1.0, -3.0 };
      double[][] equations = { eq1, eq2, eq3 };
      double[] calculated_solution = new SMatrix(equations).solvingLinearEquationsSystem();
      
      // Éléments attendus
      double[] expected_solution = { 2.0, -1.0, 3.0 };
      
      // Vérification de l'égalité
      valuesSolutionsTest(expected_solution, calculated_solution, SMath.EPSILON);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SMatrixTest ---> Test non effectué : public void solvingLinearEquationSystemTest1()");
    }
  }

  /**
   * Test de la méthode <b>solvingLinearEquationSystem</b> pour une matrice 4x5 en format augmenté.
   */
  @Test
  public void solvingLinearEquationSystemTest2()
  {
    try{
    
      // Test venant du site : http://matrix.reshish.com/gauss-jordanElimination.php
    
      // Éléments calculés
      double[] eq1 = { 2.0, -3.0, 7.0, 2.0, 2.0 };
      double[] eq2 = { 4.0, 5.0, -3.0, 1.0, 6.0 };
      double[] eq3 = { -6.0, 4.0, 2.0, 8.0, 7.0 };
      double[] eq4 = { 2.0, 9.0, -6.0, -3.0, 8.0 };
      double[][] equations = { eq1, eq2, eq3, eq4 };
      double[] calculated_solution = new SMatrix(true, equations).solvingLinearEquationsSystem();
      
      // Éléments attendus
      double[] expected_solution = { 577.0/2122.0, 1478.0/1061.0, 795.0/1061.0, 207.0/1061.0 };
      
      // Vérification de l'égalité
      valuesSolutionsTest(expected_solution, calculated_solution, SMath.EPSILON);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SMatrixTest ---> Test non effectué : public void solvingLinearEquationSystemTest2()");
    }
  }
  
  /**
   * Test de la méthode <b>solvingLinearEquationSystem</b> pour vérifier que l'état initiale de la matrice d'origine n'a pas été changé.
   */
  @Test
  public void solvingLinearEquationSystemTest3()
  {
    try{
      
      // Test venant du site : https://matrix.reshish.com/gauss-jordanElimination.php
      
      // Éléments calculés
      double[] eq1 = { 2.0, -3.0, 7.0, 2.0, 2.0 };
      double[] eq2 = { 4.0, 5.0, -3.0, 1.0, 6.0 };
      double[] eq3 = { -6.0, 4.0, 2.0, 8.0, 7.0 };
      double[] eq4 = { 2.0, 9.0, -6.0, -3.0, 8.0 };
      double[][] equations = { eq1, eq2, eq3, eq4 };
      
      // Faire deux copies identiques.
      SMatrix ini_matrix = new SMatrix(true, equations);
      SMatrix mod_matrix = new SMatrix(true, equations);
      
      // Faire la résolution, mais pas besoin d'analyser le résultat.
      mod_matrix.solvingLinearEquationsSystem();
      
      // Vérification que l'état de la matrice n'a pas changé.
      Assert.assertEquals(ini_matrix, mod_matrix);
      
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SMatrixTest ---> Test non effectué : public void solvingLinearEquationSystemTest3()");
    } 
  }
  
  /**
   * Test de la méthode <btoAumgented</b> pour un cas quelconque.
   */
  @Test
  public void toAumgentedTest()
  {
    // Éléments calculés
    double[] eq1 = { 2.0, -3.0, 7.0, 2.0, 2.0 };
    double[] eq2 = { 4.0, 5.0, -3.0, 1.0, 6.0 };
    double[] eq3 = { -6.0, 4.0, 2.0, 8.0, 7.0 };
    double[] eq4 = { 2.0, 9.0, -6.0, -3.0, 8.0 };
    double[][] equations = { eq1, eq2, eq3, eq4 };
    
    SMatrix initial_matrix = new SMatrix(equations);
    
    // Vérifier qu'il y a renvoie d'une nouvelle matrice et non une référence à la matrice initiale
    Assert.assertTrue(initial_matrix != initial_matrix.toAugmented());
    
    // Vérifier qu'une matrice augmentée n'est plus égale à celle d'avant.
    Assert.assertTrue(!initial_matrix.equals(initial_matrix.toAugmented()));
    
    // Vérification qu'une matrice doublement augmenté revient à elle même.
    Assert.assertEquals(initial_matrix, initial_matrix.toAugmented().toAugmented());
  }
  
  /**
   * Méthode pour faire la vérification du nombre de solutions, de l'ordre des solutions et de leurs valeurs.
   * 
   * @param expected_solution Le tableau des solutions attendues.
   * @param calculated_solution Le tableau des solutions calculées.
   * @param epsilon Le niveau de précision des solutions.
   */
  private void valuesSolutionsTest(double[] expected_solution, double[] calculated_solution, double epsilon)
  {
    // Vérifier le nombre de solutions
    Assert.assertEquals(expected_solution.length, calculated_solution.length);  
    
    // Vérifier la valeur et l'ordre des solutions avec la taille du tableau attendue
    for(int i = 0; i < expected_solution.length; i++)
      Assert.assertEquals(expected_solution[i], calculated_solution[i], epsilon);
  }
  
}// fin de la classe SMatrixTest
