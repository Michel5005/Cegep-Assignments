package sim.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * La classe <b>SKeyboard</b> permet la lecture d'une donnée à l'aide du clavier.
 * 
 * @author Simon Vezina
 * @version 2014-12-06
 * @version 2018-05-13
 */
public class SKeyboard
{
  /**
   * La constante <b>ERROR_MESSAGE</b> correspond au message d'erreur associé à une erreur de l'utilisation de la classe.
   */
  private static final String ERROR_MESSAGE = "Erreur de lecture! Recommencez! ";

  /**
   *  Méthode pour lire un int au clavier a partir d'une console.
   *  
   *  @return La valeur lue au clavier.
   */
  public static String readString()
  {
    return readString("");
  }
  
  /**
   *  Méthode pour lire un String au clavier a partir d'une console.
   *  @param message Un message expliquant la nature de la lecture à effectuer au clavier.
   *  @return La valeur lue au clavier.
   */
  public static String readString(String message)
  {
    String  entree = null;
    boolean done = false;
    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

    while(!done)
    {
      try{
        System.out.println(message + SStringUtil.TAB_CARACTER );
        entree = keyboard.readLine();
        done = true;
      }catch(IOException e){
        System.out.println(ERROR_MESSAGE);
      }
    }
    return entree;
  }//fin readString

  /**
   *  Méthode pour lire un int au clavier a partir d'une console.
   *  
   *  @return La valeur lue au clavier.
   */
  public static int readInt()
  {
    return readInt("");
  }
  
  /**
   *  Méthode pour lire un int au clavier a partir d'une console.
   *  
   *  @param message Un message expliquant la nature de la lecture à effectuer au clavier.
   *  @return La valeur lue au clavier.
   */
  public static int readInt(String message)
  {
    String  entree = null;
    int sortie = 0;
    boolean done = false;
    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

    while( !done )
    {
      try{
        System.out.println(message + SStringUtil.TAB_CARACTER );
        entree = keyboard.readLine();
        sortie = Integer.parseInt(entree);
        done = true;
      }catch(IOException e){ 
        System.out.println(ERROR_MESSAGE);
      }catch(NumberFormatException e) {
        System.out.println(ERROR_MESSAGE);
      }
    }

    return sortie;
  }//fin readInt

  /**
   *  Méthode pour lire un float au clavier a partir d'une console.
   *  
   *  @return La valeur lue au clavier.
   */
  public static float readFloat()
  {
    String  entree = null;
    float sortie = 0;
    boolean done = false;
    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

    while( !done )
    {
      try{
      entree = keyboard.readLine();
      sortie = Float.parseFloat(entree);
      done = true;
      }catch(IOException e){
        System.out.println(ERROR_MESSAGE);
      }catch(NumberFormatException e) {
        System.out.println(ERROR_MESSAGE);
      }
    }

    return sortie;
  }//fin readFloat

  
 
}//end SKeyboard