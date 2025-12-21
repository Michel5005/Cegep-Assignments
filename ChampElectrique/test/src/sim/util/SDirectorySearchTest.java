/**
 * 
 */
package sim.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SDirectorySearch</b>.
 * 
 * @author Simon Vézina
 * @since 2018-02-22
 * @version 2018-02-23
 */
public class SDirectorySearchTest {

  /**
   * Test permettant de vérifier la méthode <b>isDirectoryFound</b> dans un scénario où rien n'est trouvé.
   */
  @Test
  public void isDirectoryFoundTest1()
  {
    SDirectorySearch search = new SDirectorySearch("test_repertoire_qui_n_est_pas_trouvable");
    
    Assert.assertEquals(false, search.isDirectoryFound());
  }

  /**
   * Test permettant de vérifier la méthode <b>isDirectoryFound</b> dans un scénario où le répertoire est trouvé.
   */
  @Test
  public void isDirectoryFoundTest2()
  {
    SDirectorySearch search = new SDirectorySearch("test_repertoire_qui_est_pas_trouvable_1_fois");
    
    Assert.assertEquals(true, search.isDirectoryFound());
  }
  
  /**
   * Test permettant de vérifier la méthode <b>isDirectoryFound</b> dans un scénario où le répertoire n'est pas trouvé dont la recherche débute dans un sous-répertoire.
   */
  @Test
  public void isDirectoryFoundTest3()
  {
    String subdirectory = "test" + SStringUtil.FILE_SEPARATOR_CARACTER + "test_data";
    
    SDirectorySearch search = new SDirectorySearch(subdirectory,"test_repertoire_qui_n'est_pas_trouvable");
    
    Assert.assertEquals(false, search.isDirectoryFound());
  }
  
  /**
   * Test permettant de vérifier la méthode <b>isDirectoryFound</b> dans un scénario où le répertoire est trouvé dont la recherche débute dans un sous-répertoire.
   */
  @Test
  public void isDirectoryFoundTest4()
  {
    String subdirectory = "test" + SStringUtil.FILE_SEPARATOR_CARACTER + "test_data";
    
    SDirectorySearch search = new SDirectorySearch(subdirectory,"test_repertoire_qui_est_pas_trouvable_1_fois");
    
    Assert.assertEquals(true, search.isDirectoryFound());
  }
  
  /**
   * Test permettant de vérifier la méthode <b>isManyDirectoryFound</b> dans un scénario où le répertoire est trouvé seulement une fois.
   */
  @Test
  public void isManyDirectoryFoundTest1()
  {
    SDirectorySearch search = new SDirectorySearch("test_repertoire_qui_est_pas_trouvable_1_fois");
    
    Assert.assertEquals(false, search.isManyDirectoryFound());
  }
  
  /**
   * Test permettant de vérifier la méthode <b>isManyDirectoryFound</b> dans un scénario où le répertoire est trouvé deux fois.
   */
  @Test
  public void isManyDirectoryFoundTest2()
  {
    SDirectorySearch search = new SDirectorySearch("test_repertoire_qui_est_pas_trouvable_2_fois");
    
    Assert.assertEquals(true, search.isManyDirectoryFound());
  }
  
  /**
   * Test permettant de vérifier la méthode <b>isManyDirectoryFound</b> dans un scénario où le répertoire est trouvé une fois 
   * étant donnée que la recherche début dans un sous-répertoire où il n'y aura qu'une seule présence du répertoire en recherche.
   */
  @Test
  public void isManyDirectoryFoundTest3()
  {
    String subdirectory = "test" + SStringUtil.FILE_SEPARATOR_CARACTER + "test_data" + SStringUtil.FILE_SEPARATOR_CARACTER + "test_repertoire_qui_est_pas_trouvable_1_fois";
    
    SDirectorySearch search = new SDirectorySearch(subdirectory, "test_repertoire_qui_est_pas_trouvable_2_fois");
    
    Assert.assertEquals(false, search.isManyDirectoryFound());
  }
  
}// fin de la classe SDirectorySearchTest
