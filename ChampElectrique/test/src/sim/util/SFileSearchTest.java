/**
 * 
 */
package sim.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SFileSearch</b>.
 * 
 * @author Simon Vézina
 * @since 2015-10-03
 * @version 2018-03-13
 */
public class SFileSearchTest {

  /**
   * Test permettant de vérifier la méthode isFileFound() dans un scénario où rien n'est trouvé.
   */
  @Test
  public void test_isFileFound()
  {
    SFileSearch search = new SFileSearch("Fichier qui n'est pas trouvable.");
    
    Assert.assertEquals(false, search.isFileFound());
  }

  /**
   * Test permettant de vérifier la méthode isManyFileFound() dans un scénario où rien n'est trouvé.
   */
  @Test
  public void test_isManyFileFound()
  {
    SFileSearch search = new SFileSearch("Fichier qui n'est pas trouvable.");
    
    Assert.assertEquals(false, search.isManyFileFound());
  }
  
}
