/**
 * 
 */
package sim.physics;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe SWaveOpticsTest.
 * 
 * @author Simon Vézina
 * @since 2016-02-14
 * @version 2020-01-08 (Version : Le ray tracer v2.107)
 */
public class SWaveOpticsTest {

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception
  {
  }

  
  
  @Test
  public void test1_phaseBetweenZeroAnd2Pi()
  {
    double phase = 547.976821645636346346346363;
    
    // Nous avons ici une bonne précision avec une petite phase
    Assert.assertEquals(Math.sin(phase), Math.sin(SWaveOptics.phaseBetweenZeroAnd2Pi(phase)), 1e-12);
  }
  
  @Test
  public void test2_phaseBetweenZeroAnd2Pi()
  {
    // Une phase calculé avec une longueur d'onde de 500 nm
    double phase = 2*Math.PI/500e-9*1.8764653223456345;
    
    // Nous avons ici une faible précision avec une grosse phase
    Assert.assertEquals(Math.sin(phase), Math.sin(SWaveOptics.phaseBetweenZeroAnd2Pi(phase)), 1e-8);
  }
  
}//fin de la classe SWaveOpticsTest
