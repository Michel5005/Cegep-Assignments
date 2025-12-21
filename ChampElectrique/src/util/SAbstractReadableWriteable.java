/**
 * 
 */
package sim.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * La classe abstraite <b>SAbstractReadableWriteable</b> représente un objet pouvant 
 * <ul>- Être initialisé par une lecture dans un fichier txt.</ul>
 * <ul>- Écrire ses propriétés dans le format de la lecture dans un fichier txt.</ul>
 * 
 * @author Simon Vézina
 * @since 2015-01-10
 * @version 2018-01-16
 */
public abstract class SAbstractReadableWriteable extends SAbstractReadable implements SWriteable {

  /**
   * Méthode pour écrire l'objet <b>SAbstractReadableWriteable</b> dans un fichier txt.
   * Le détail de ce qui sera écrit est défini par l'implémentation de la méthode write(BufferedWriter bw)
   * de l'interface <b>SWriteable</b>.
   * 
   * @param file_name Le nom du fichier où sera écrit l'objet.
   */
  public void write(String file_name)
  {
    try{
      FileWriter fw = new FileWriter(file_name);
      BufferedWriter bw = new BufferedWriter(fw);
    
      write(bw);
    
      bw.close(); //  fermer celui-ci en premier, sinon, ERROR !!!
      fw.close();   
    }catch(IOException ioe){
      SLog.logWriteLine("Message SAbstractReadableWriteable - Une erreur de type I/O est survenue.");
      ioe.printStackTrace();
    }
  }
  
  /**
   * Méthode pour écrire une ligne de commentaire dans le fichier.
   * 
   * @param bw Le buffer d'écriture dans le fichier.
   * @param comment Le commentaire à écrire.
   * @throws IOException S'il y a une erreur de type I/O qui est survenue.
   */
  protected void writeComment(BufferedWriter bw, String comment) throws IOException
  {
    bw.write("/*" + SStringUtil.END_LINE_CARACTER);
    bw.write("   " + comment + SStringUtil.END_LINE_CARACTER);
    bw.write("*/" + SStringUtil.END_LINE_CARACTER);
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
  
}//fin classe abstraite SAbstractReadableWriteable
