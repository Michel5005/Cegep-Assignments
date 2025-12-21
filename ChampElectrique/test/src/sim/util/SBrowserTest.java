package sim.util;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SFileSearch</b>.
 * 
 * @author Simon Vézina
 * @since 2018-03-13
 * @version 2019-12-01
 */
public class SBrowserTest {

  @Test
  public void goToTest1() throws FileNotFoundException, SManyFilesFoundException 
  {
    SBrowser browser = new SBrowser();
    browser.forward("test");
    browser.forward("test_data");
    
    browser.goTo("test_repertoire_qui_est_pas_trouvable_1_fois");
    
    String expected_solution = System.getProperty("user.dir") + SStringUtil.FILE_SEPARATOR_CARACTER + "test" + SStringUtil.FILE_SEPARATOR_CARACTER + "test_data" + SStringUtil.FILE_SEPARATOR_CARACTER + "test_repertoire_qui_est_pas_trouvable_1_fois";
    
    String calculated_solution = browser.getPath();
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  @Test
  public void goToTest2() throws FileNotFoundException, SManyFilesFoundException 
  {
    SBrowser browser = new SBrowser();
    browser.forward("test");
    browser.forward("test_data");
    
    try{
      
      browser.goTo("test_repertoire_qui_est_pas_trouvable_2_fois");
      
      fail("Ce test devrait être en échec, car le répertoire 'test_repertoire_qui_est_pas_trouvable_2_fois' existe deux fois.");
      
    }catch(SManyFilesFoundException e){
      // C'est un succès !
    }
  }
  
  @Test
  public void forwardTest1() throws FileNotFoundException, SManyFilesFoundException 
  {
    SBrowser browser = new SBrowser();
    browser.forward("test");
    browser.forward("test_data");
    
    String expected_solution = System.getProperty("user.dir") + SStringUtil.FILE_SEPARATOR_CARACTER + "test" + SStringUtil.FILE_SEPARATOR_CARACTER + "test_data";
    
    String calculated_solution = browser.getPath();
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }

  @Test
  public void backTest1() throws FileNotFoundException, SManyFilesFoundException 
  {
    SBrowser browser = new SBrowser();
    
    browser.forward("test");
    browser.forward("test_data");
    browser.back();
    
    String expected_solution = System.getProperty("user.dir") + SStringUtil.FILE_SEPARATOR_CARACTER + "test";
    
    String calculated_solution = browser.getPath();
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
    
  @Test
  public void findFileTest1() throws FileNotFoundException, SManyFilesFoundException
  {
    SBrowser browser = new SBrowser();
    browser.forward("test");
    browser.forward("test_data");   
    
    String expected_solution = "test_fichier_qui_est_trouvable_1_fois.txt";
    
    String calculated_solution = browser.findFile("test_fichier_qui_est_trouvable_1_fois.txt").getName();
    
    Assert.assertEquals(expected_solution, calculated_solution);
  }
  
  @Test
  public void findFileTest2() throws FileNotFoundException, SManyFilesFoundException
  {
    SBrowser browser = new SBrowser();
    browser.forward("test");
    browser.forward("test_data");
    
    try{
      
      browser.findFile("test_fichier_qui_est_trouvable_2_fois.txt").getName();
    
      fail("Ce test devrait être en échec, car le fichier 'test_fichier_qui_est_trouvable_2_fois.txt' existe deux fois.");
      
    }catch(SManyFilesFoundException e){
      // C'est un succès !
    }
  }
    
  @Test
  public void createDirectoryTest() throws FileNotFoundException, SManyFilesFoundException
  {
    SBrowser browser = new SBrowser();
    
    browser.forward("test");
    browser.forward("test_browser");
    
    // Créer le répertoire s'il n'existe pas déjà.
    boolean is_created = browser.createDirectory("createDirectoryTest");
           
    // Validation.
    try{
      browser.findDirectory("createDirectoryTest");
    }catch(Exception e){
      fail("Echec du test, car le répertoire créé n'a pas été trouvé.");
    }
    
    if(is_created)
      browser.deleteDirectory("createDirectoryTest");
  }
  
  @Test
  public void deleteDirectoryTest() throws FileNotFoundException
  {
    SBrowser browser = new SBrowser();
    
    browser.forward("test");
    browser.forward("test_browser");
    
    // Créer le répertoire s'il n'existe pas déjà.
    boolean is_created = browser.createDirectory("deleteDirectoryTest");
    
    if(!is_created)
      fail("Echec du test, car le répertoire dans le jeu de test n'a pas été créé, donc ne pourra pas être effacé.");
    
    // Méthode a tester.
    browser.deleteDirectory("deleteDirectoryTest");
    
    try{
      browser.findDirectory("deleteDirectoryTest");
      fail("Echec du test, car le répertoire n'a pas été effacé.");
    }catch(FileNotFoundException e){
      // C'est un succès !
    }
    
  }
  
  @Test
  public void isEmptyTest1() throws FileNotFoundException, SManyFilesFoundException
  {
    SBrowser browser = new SBrowser();
    
    browser.forward("test");
    browser.forward("test_browser");
    
    browser.createDirectory("isEmptyTest1");
    browser.forward("isEmptyTest1");
    Assert.assertTrue(browser.isEmpty());
  }
  
  @Test
  public void isEmptyTest2() throws FileNotFoundException, SManyFilesFoundException, IOException
  {
    SBrowser browser = new SBrowser();
    
    browser.forward("test");
    browser.forward("test_browser");
    
    browser.createDirectory("isEmptyTest2");
    browser.forward("isEmptyTest2");
    
    browser.writeTextFile("isEmptyTest2File", "txt", "Ceci est un test.");
    
    Assert.assertFalse(browser.isEmpty());
  }
  
  @Test
  public void writeTextFileTest() throws SManyFilesFoundException, IOException
  {
    SBrowser browser = new SBrowser();
    
    browser.forward("test");
    browser.forward("test_browser");
    browser.writeTextFile("writeTextFileTest", "txt", "Allo les amis");
       
    Assert.assertTrue(browser.findFile("writeTextFileTest.txt") != null);
  }
  
  @Test
  public void createFileTest() throws IOException
  {
    SBrowser browser = new SBrowser();
    browser.forward("test");
    browser.forward("test_browser");
    
    File file = browser.createFile("createFileTest", "txt");
    FileWriter fw = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(fw);
    
    bw.write("Ceci est un test.");
    bw.flush();
    
    bw.close();     
    fw.close();
    
    Assert.assertTrue(browser.findFile("createFileTest.txt") != null);
  }
  
  @Test
  public void writeImageFileTest() throws IOException
  {
   BufferedImage buffer = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB); 
    
   SBrowser browser = new SBrowser();
   browser.forward("test");
   browser.forward("test_browser");
   
   browser.writeImageFile("writeImageFileTest", "png", buffer);
   Assert.assertTrue(browser.findFile("writeImageFileTest.png") != null);
  }
  
  @Test
  public void readImageFileTest() throws IOException
  {
    BufferedImage write_buffer = new BufferedImage(70, 60, BufferedImage.TYPE_INT_RGB); 
    
    SBrowser browser = new SBrowser();
    browser.forward("test");
    browser.forward("test_browser");
    
    browser.writeImageFile("readImageFileTest", "png", write_buffer);
    
    // Méthode à tester.
    BufferedImage read_buffer = browser.readImageFile("readImageFileTest.png");
    
    // Test à réaliser.
    Assert.assertEquals(70, read_buffer.getWidth());
    Assert.assertEquals(60, read_buffer.getHeight());
  }
  
}
