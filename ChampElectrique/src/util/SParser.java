/**
 * 
 */
package sim.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * La classe <b>SParser</b> est une classe utilitaire permettant des lires des fichier afin d'en extraire des informations.
 * 
 * @author Simon Vezina
 * @since 2022-03-04
 * @version 2022-03-04
 */
public class SParser {

  /**
   * ...
   * 
   * @param file_name
   * @return
   */
  public static List<String[]> parseCSV(String file_name)
  {
    return parseCSV(new SBrowser(), file_name);
  }
  
  /**
   * ...
   * 
   * @param browser
   * @param file_name
   * @return
   */
  public static List<String[]> parseCSV(SBrowser browser, String file_name) throws IllegalArgumentException
  {
    try{
      
      // Ouverture du fichier.
      File f = browser.findFile(file_name);
      FileReader fr = new FileReader(f);
      SBufferedReader sbr = new SBufferedReader(fr);
      
      // Faire l'allocation de la mémoire pour les éléments lus.
      List<String[]> list = new ArrayList<String[]>();
      
      // Faire la lecture.
      String line = null;
      
      do{
      
        // Lire la ligne.
        line = sbr.readLine();
        
        // Effectuer l'analyse de la ligne si elle est présente.
        if(line != null)
        {
          // Fractionner les élément le la ligne avec le séparateur ",".
          StringTokenizer tokens = new StringTokenizer(line, ","); 
          
          // Allocation de la mémoire pour le tableau des tokens.
          String[] array = new String[tokens.countTokens()];
          
          // Remplir le tableau.
          int i = 0;
          
          while(i != array.length)
          {
            array[i] = tokens.nextToken();
            i++;
          }
      
          // Ajouter le tableau à la liste.
          list.add(array);
        }
      
      }while(line != null);      
      
      // Fermeture du fichier.
      sbr.close();
      fr.close();
      
      // Retourner une exception si la liste est vide.
      if(list.size() == 0)
        throw new IllegalArgumentException("Erreur SParser 001 : Le fichier CSV '" + file_name + "' correspond à une liste qui est vide.");
      
      // Validation que l'ensemble des tableaux ont le même nombre d'élément.
      int n = list.get(0).length;
      
      for(int i = 1; i < list.size(); i++)
        if(list.get(i).length != n)
          throw new IllegalArgumentException("Erreur SParser 002 : Le fichier CSV '" + file_name + "' contient un élément qui ne possède pas n = " + n + " élément sur sa ligne ce qui rend le fichier invalide.");
      
      return list;
      
    }catch(FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }catch(SManyFilesFoundException e) {
      throw new IllegalArgumentException(e);
    }catch(IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

}
