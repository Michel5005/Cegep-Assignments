/**
 * 
 */
package sim.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * La classe <b>SWriter</b> représente un compositeur de fichier texte dans le but d'écrire des informations appartenant à un objet 
 * implémentant l'interface <b>SWriteable</b>.
 *
 * @author Simon Vézina
 * @since 2018-01-16
 * @version 2018-01-16
 */
public class SWriter {

  /**
   * Constructeur d'un compositeur.
   */
  public SWriter()
  {
    
  }

  /**
   * Méthode permettant d'écrire dans un fichier texte le contenu d'un objet.
   * 
   * @param file_name Le nom du fichier à écrire.
   * @param obj L'objet dont le contenu sera écrit dans le fichier.
   */
  public void write(String file_name, SWriteable obj)
  {
    try{
      FileWriter fw = new FileWriter(file_name);
      BufferedWriter bw = new BufferedWriter(fw);
    
      obj.write(bw);
    
      bw.close(); //  fermer celui-ci en premier, sinon, ERROR !!!
      fw.close();   
    }catch(IOException ioe){
      SLog.logWriteLine("Message SWriter - Une erreur de type I/O est survenue.");
      ioe.printStackTrace();
    }
  }
  
}// fin de la classe SWriter
