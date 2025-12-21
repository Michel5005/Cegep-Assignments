/**
 * 
 */
package sim.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * La classe <b>SReader</b> représente un lecteur de fichier texte dans le but de charger des informations dans un objet 
 * implémentant l'interface <b>SReadable</b>.
 * 
 * @author Simon Vézina
 * @since 2018-01-16
 * @version 2018-01-16
 */
public class SReader {

  /**
   * Constructeur d'un lecteur. 
   */
  public SReader()
  {
    
  }

  /**
   * Méthode permettant de faire l'affectation d'information dans un objet à l'aide de la lecture d'un fichier texte.
   * 
   * @param file_name Le nom du fichier en lecture.
   * @param obj L'objet qui sera chargé en information.
   * @throws FileNotFoundException Si le fichier en lecture n'a pas été trouvé.
   * @throws SInitializationException Si une erreur d'initialisation est survenue lors de la lecture.
   * @throws IOException Si une erreur de type I/O est survenue lors de la lecture.
   */
  public void read(String file_name, SReadable obj) throws FileNotFoundException, SInitializationException, IOException
  {
    // Trouver le fichier à partir du répertoire où l'exécution de l'application est réalisée
    SFileSearch search = new SFileSearch("", file_name);
    
    if(!search.isFileFound())
      throw new FileNotFoundException("Erreur SReader 001 - Le fichier '" + file_name + "' n'est pas trouvé.");
    
    if(search.isManyFileFound())
      throw new SInitializationException("Erreur SReader 002 - Le fichier '" + file_name + "' a été trouvé plus d'une fois dans les différents sous-répertoires. Veuillez en garder qu'une seule version.");
    
    // Lecture de la scène à partir d'un fichier
    FileReader fr = new FileReader(search.getFileFoundList().get(0));
    SBufferedReader sbr = new SBufferedReader(fr);
    
    obj.read(sbr);
   
    sbr.close();
    fr.close();
  }
  
}// fin de la classe SReader
