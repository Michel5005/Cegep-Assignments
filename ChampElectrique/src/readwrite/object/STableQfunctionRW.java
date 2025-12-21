/**
 * 
 */
package sim.readwrite.object;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.readwrite.SAbstractRW;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * ...
 * 
 * @author Simon Vézina
 * @since 2018-05-30
 * @version 2022-04-24
 */
public class STableQfunctionRW extends SAbstractRW {

  //--------------
  // CONSTANTES //
  //--------------
  
  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = { 
      SKeyWordDecoder.KW_STATE, 
      SKeyWordDecoder.KW_VALUE
  };
  
  //-------------
  // VARIABLES //
  //-------------
  
  /**
   * ...
   */
  private final Map<int[] , double[]> state_map;
  
  /**
   * ...
   */
  private int[] state;
  
  /**
   * ...
   */
  public STableQfunctionRW()
  {
    this.state_map = new HashMap<int[], double[]>();
    state = null;
  }

  /**
   * ...
   * 
   * @param file_name
   */
  public STableQfunctionRW(String file_name) 
  {
    super(file_name);
    
    this.state_map = new HashMap<int[] , double[]>();
    state = null;
  }

  /**
   * ...
   * 
   * @param file_name
   * @param state_map
   */
  public STableQfunctionRW(String file_name, Map<int[] , double[]> state_map) 
  {
    super(file_name);
    
    this.state_map = state_map;
  }
  
  /**
   * ...
   * 
   * @param sbr
   * @throws IOException
   * @throws SConstructorException
   */
  public STableQfunctionRW(SBufferedReader sbr) throws IOException, SConstructorException 
  {
    this();
    
    try{
      read(sbr);
    }catch(SInitializationException e){
      throw new SConstructorException("Erreur STableQfunctionRW 001 : Une erreur d'initialisation est survenue.", e);
    }
  }

  @Override
  protected void initialization() throws SInitializationException
  {
    
  }
  
  @Override
  public String getRWName()
  {
    throw new SNoImplementationException();
  }

  @Override
  public String[] getRWParameterName()
  {
    String[] other_parameters = super.getRWParameterName();
    
    return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
  }
  
 
  @Override
  public void writeInformation(BufferedWriter bw) throws IOException
  {
    ArrayList<int[]> list = new ArrayList<int[]>(this.state_map.keySet());
    
    // Faire le trie des tableaux selon le plus petit nombre de zéro dans les tableaux.
    Collections.sort(list, new Comparator<int[]>() {
      
      public int compare(int[] array, int[] otherArray) {
        
        // Compter le nombre de 0.
        int array_count = 0;
        int otherArray_count = 0;
        
        for(int i = 0; i < array.length; i++)
        {
          if(array[i] == 0)
            array_count++;
          
          if(otherArray[i] == 0)
            otherArray_count++;
        }
        
        return Integer.compare(otherArray_count, array_count);
      }
      });
      
    // Affichage statistique du nombre de state-action dans la table.
    bw.write(SKeyWordDecoder.KW_COMMENT);
    bw.write("Nombre de state-action : " + list.size());
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    // Faire l'écriture pour toutes les états-valeurs.
    for(int[] s : list)
    {
      bw.write(SKeyWordDecoder.KW_STATE);
      bw.write(SStringUtil.TAB_CARACTER);
      
      // Écrire tous les éléments de l'état.
      for(int i = 0; i < s.length; i++)
        bw.write(Integer.toString(s[i]) + "  ");
      bw.write(SStringUtil.END_LINE_CARACTER);
      
      bw.write(SKeyWordDecoder.KW_VALUE);
      bw.write(SStringUtil.TAB_CARACTER);
      
      // Écrire tous les valeurs d'un état
      double[] values = state_map.get(s);
      
      for(int i = 0; i < values.length; i++)
        bw.write(Double.toString(values[i]) + SStringUtil.TAB_CARACTER);
      bw.write(SStringUtil.END_LINE_CARACTER);
      
      bw.write(SStringUtil.END_LINE_CARACTER);
    }    
    
    bw.write(SStringUtil.END_LINE_CARACTER);
    
  }
  
  @Override
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_STATE : state = this.readIntArray(remaining_line, SKeyWordDecoder.KW_STATE); return true;
      
      case SKeyWordDecoder.CODE_VALUE : 
        
        if(state == null)
          throw new SReadingException("Erreur STableQfunction 001 : Une valeur est en lecture sans qu'un état ait été lu précédemment.");
        
        double[] values = this.readDoubleArray(remaining_line, SKeyWordDecoder.KW_VALUE);
        state_map.put(state, values);
        state = null;
        return true;
      
      default : return super.read(sbr, code, remaining_line);
    }
  }
  
}
