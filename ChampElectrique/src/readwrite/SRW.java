/**
 * 
 */
package sim.readwrite;

import java.io.BufferedWriter;
import java.io.IOException;

import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * L'interface <b>SRW</b> permet à un objet de pouvoir être lu et écrit à partir d'un fichier texte. 
 * 
 * @author Simon Vézina
 * @since 2018-03-24
 * @version 2022-04-23
 */
public interface SRW {

  /**
   * Méthode qui permet la lecture d'un fichier de format txt à partir d'un objet de type SRW.
   * 
   * @throws SReadingException S'il y a eu une erreur lors de la lecture.
   */
  public void read() throws SReadingException;
   
  /**
   * Méthode pour écrire un objet <b>SRW</b> dans un fichier texte dont le nom doit être déterminé par l'objet lui-même.
   * 
   * @throws IOException Si une erreur de type I/O est survenue.
   */
  public void write() throws IOException;
  
  /**
   * Méthode pour écrire un objet <b>SRW</b> dans un fichier texte en utilisant un <b>BufferedWriter</b>.
   * 
   * @param bw Le buffer pour l'écriture.
   * @throws IOException Si une erreur de type I/O est survenue.
   */
  public default void write(BufferedWriter bw) throws IOException
  {
    bw.write(this.getRWName());
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    writeInformation(bw);
    
    bw.write(SKeyWordDecoder.KW_END);
    bw.write(SStringUtil.END_LINE_CARACTER);
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
  
  /**
   * Méthode permettant d'écrire les information d'un objet SRW dans un fichier texte.
   * 
   * @param bw Le buffer d'écriture.
   * @throws IOException Si une erreur de type I/O est survenue.
   */
  public void writeInformation(BufferedWriter bw) throws IOException;
   
  /**
   * Méthode pour obtenir le nom de l'objet implémentant l'interface <b>SReadable</b>.
   * Ce nom correspond également au <b>mot clé</b> à rechercher lors d'une lecture avec un <b>SBufferedReader</b> pour en faire la construction de l'objet en question.
   *    
   * @return Le nom de l'objet lisible.
   */
  public String getRWName();
  
  /**
   * Méthode pour obtenir un tableau des <b>mots clés</b> permettant de définir les paramètres de l'objet implémentant l'interface <b>SReadable</b> lors de sa lecture.
   * 
   * @return Un tableau des <b>mots clés</b> des paramètres.
   */
  public String[] getRWParameterName();
  
}
