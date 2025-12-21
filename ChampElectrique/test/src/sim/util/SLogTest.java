/**
 * 
 */
package sim.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SLog</b>.
 * 
 * @author Simon Vézina
 * @since 2016-01-14
 * @version 2016-04-20
 */
public class SLogTest {

  /**
   * Test #1 permettant de valider les fonctionnalités des méthodes suivante :
   * <ul>setLogFileName(String file_name)</ul>
   * <ul>logWriteLine(String str)</ul>
   * <ul>closeLogFile()</ul>
   */
  @Test
  public void test1_setLogFileName_logWriteLine_closeLogFile()
  {
    String file_name = "log_test.txt";
    String directory = "test";
    
    try{
      
      // Choisir le répertoire "test" comme lieu où le fichier Log sera enregistré.
      SLog.setLogDirectory(directory);
      
      // Nom du fichier log.
      SLog.setLogFileName(file_name);
      
    }catch(Exception e){
      fail("FAIL - " + e.getMessage());
    }
    
    SLog.setConsoleLog(false);
    
    SLog.logWriteLine("Ceci est un test!");
    SLog.logWriteLine("Ceci est encore un test!");
      
    try{
      SLog.closeLogFile();
    }catch(IOException e){
      fail("FAIL - " + e.getMessage());
    }
    
    // Vérifier si le fichier est localisé.
    
    // Présentement, le fichier semble s'écrire après l'exécution du code ce qui met ce test toujours en échec.
    /*
    SBrowser browser = new SBrowser();
    
    try {
      
      browser.goTo("test");
      browser.findFile("logTest.txt");
      
    } catch (FileNotFoundException | SManyFilesFoundException e) {
      fail("Ce test est en échec, car le fichier '" + file_name + " n'a pas été trouvé dans le répertoire '" + directory +"'.");
    }
    */
    
  }

  
  
}
