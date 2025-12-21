/**
 * 
 */
package sim.readwrite.object;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sim.exception.SConstructorException;
import sim.readwrite.SAbstractRW;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SMatrixRW</b> représente une matrice initialisée lors d'une lecture en fichier et pouvant être écrite dans un fichier texte.
 * @author Simon Vézina
 * @since 2018-03-14
 * @version 2021-09-28
 */
public class SMatrixRW extends SAbstractRW {

  //--------------
  // CONSTANTES //
  //--------------
  
  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = { SKeyWordDecoder.KW_LINE };
  
  //-------------
  // VARIABLES //
  //-------------
  
  private List<double[]> matrix;
  
  /**
   * Constructeur d'une lecture d'une couche de neurones
   */
  public SMatrixRW()
  {
    this.matrix = new ArrayList<double[]>();
  }
  
  /**
   * ...
   * 
   * @param matrix
   * @throws SConstructorException
   */
  public SMatrixRW(List<double[]> matrix) throws SConstructorException
  {
    this.matrix = matrix;
    
    try{
      initialization();
    }catch(SInitializationException e){
      throw new SConstructorException("Erreur SMatrixRW 001 : Une erreur d'initialisation est survenue.", e);
    }
  }
  
  /**
   * Constructeur d'une lecture d'une matrice à partir d'information lue dans un fichier de format txt.
   * 
   * @param sbr Le BufferedReader cherchant l'information de le fichier .txt.
   * @throws IOException Si une erreur de l'objet SBufferedWriter est lancée.
   * @throws SReadingException Si un paramètre lors de la lecture rend l'objet invalide.
   * @see SBufferedReader
   */
  public SMatrixRW(SBufferedReader sbr) throws SReadingException
  {
    this();   
    
    try{
      read(sbr);
    }catch(SInitializationException e){
      throw new SReadingException("Erreur SMatrixRW 002 : Une erreur d'initialisation est survenue.", e);
    }
  }
  
  /**
   * Méthode pour obtenir la liste des lignes de la matrice.
   * 
   * @return La liste des lignes de la matrice.
   */
  public List<double[]> getMatrix()
  {
    return matrix;
  }
  
  /**
   * Méthode pour obtenir le nombre de ligne dans la matrice.
   * 
   * @return Le nombre de ligne.
   */
  public int getNbLine()
  {
    return this.matrix.size();
  }
  
  /**
   * Méthode pour obtenir le nombre de colonnes dans la matrice.
   *  
   * @return Le nombre de colonne.
   */
  public int getNbColumn()
  {
    return this.matrix.get(0).length;
  }
  
  /**
   * Méthode pour écrire les paramètres associés à la classe SMatrixRW et ses paramètres hérités.
   * @param bw Le BufferedWriter écrivant l'information dans un fichier txt.
   * @throws IOException Si une erreur I/O s'est produite.
   * @see IOException
   */
  @Override
  public void writeInformation(BufferedWriter bw) throws IOException
  {
    // Écrire toutes les lignes de la matrice.
    for(double[] l : matrix)
    {
      bw.write(SKeyWordDecoder.KW_LINE);
      bw.write("\t");
      
      // Écrire tous les composants de la ligne.
      for(int i = 0; i < l.length; i++)
      {
        bw.write(Double.toString(l[i]));
        bw.write("\t");
      }
      bw.write(SStringUtil.END_LINE_CARACTER);
    }
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
  
  @Override
  public String getRWName()
  {
    return SKeyWordDecoder.KW_MATRIX;
  }

  @Override
  protected void initialization() throws SInitializationException
  {
    super.initialization();
    
    // Vérifier que la matrice n'est pas vide.
    if(matrix.size() > 0)
    {
    
      // Vérification que toutes les lignes ont le même nombre d'éléments.
      int reference = matrix.get(0).length;
      
      // Itération sur l'ensemble des lignes.
      for(int i = 0; i < matrix.size(); i++)
        if(matrix.get(i).length != reference)
          throw new SInitializationException("Erreur SMatrixRW 004 : La ligne '" + i + "' ne contient pas exactement '" + reference + "' éléments (nombre de composant la la ligne 0).");
    }
  }

  @Override
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_LINE : matrix.add(readDoubleArray(remaining_line, SKeyWordDecoder.KW_LINE)); return true; 
      
      default : return super.read(sbr, code, remaining_line);
    }
  }
  
  @Override
  public String[] getRWParameterName()
  {
    String[] other_parameters = super.getRWParameterName();
    
    return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
  }
  
}
