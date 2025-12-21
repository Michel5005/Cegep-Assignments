/**
 * 
 */
package sim.util;

import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.stream.Stream;

/**
 * La classe <b>SStringUtil</b> permet d'effectuer quelques opérations pratiques sur un String.
 * 
 * @author Simon Vézina
 * @since 2015-03-21
 * @version 2019-12-08
 */
public class SStringUtil {
  
  /**
   * La constante <b>END_LINE_CARACTER</b> correspond à un caractère de changement de ligne.
   * Il est déterminé à l'aide de l'instruction <i>System.getProperty("line.separator")</i> ce qui permet d'avoir le bon caractère pour la bonne plateforme d'exécution. 
   */
  public static final String END_LINE_CARACTER = System.getProperty("line.separator"); 	
  
  /**
   * La constante <b>FILE_SEPARATOR_CARACTER</b> correspond à un caractère de séparation dans l'adresse de localisation d'un fichier.
   * Il est déterminé à l'aide de l'instruction <i>System.getProperty("file.separator")</i>.
   * Sur Windows, le caractère est '\'. Sur Linux, le caractère est '/'.
   */
  public static final String FILE_SEPARATOR_CARACTER = System.getProperty("file.separator");
  
  /**
   * La constante <b>TAB_CARACTER</b> correspond à un caractère d'espacement de type "tab".
   */
  public static final String TAB_CARACTER = "\t";
    
  /**
   * La constante <b>REMOVE_CARACTER_TOKENIZER</b> correspond à l'ensemble des caractères à retirer lorsque l'on désire séparé en sous-mot une longue chaîne de caractère.
   * Les caractères sont les suivants : {@value}.
   */
  public static final String REMOVE_CARACTER_TOKENIZER = " \t\n\r\f,()[]<>";
		
  
  /**
	 * Méthode pour obtenir l'extension du nom du fichier. 
	 * Ceci correspond aux derniers caractères après le dernier "." dans le nom du fichier.
	 * La valeur retournée sera en caratère minuscule.
	 * Si le nom du fichier ne contient pas d'extension, le String "" sera retourné.
	 * 
	 * @param file_name Le nom du fichier
	 * @return L'extension du fichier en caratères minuscules.
	 */
	public static String extensionFileLowerCase(String file_name)
	{
	  String extension = "";
	  int i = file_name.lastIndexOf('.');
	    
	  if (i > 0)
	    extension = file_name.substring(i+1);
	    
	  return extension.toLowerCase(Locale.ENGLISH);
	}
	
	/**
	 * Méthode pour obtenir le nom d'une fichier sans la référence à des répertoires de localisation inclus dans le nom du fichier.
	 * 
	 * @param file_name - Le nom du fichier (incluant peut-être des répertoires de localisation).
	 * @return Le nom du fichier uniquement.
	 */
	public static String getFileNameWithoutDirectory(String file_name)
	{
	  StringTokenizer tokens = new StringTokenizer(file_name, "/\\");  //faire token avec séparateur '/' et '\'
	  
	  String last_token = null;
	  
	  while(tokens.hasMoreTokens())
	    last_token = tokens.nextToken();
	  
	  return last_token;
	}
	
	/**
	 * Méthode pour obtenir le nom d'un fichier dans l'extension.
	 * 
	 * @param file_name Le nom du fichier.
	 * @return Le nom du fichier sans extension.
	 */
	public static String getFileNameWithoutExtension(String file_name)
	{
	
    int index = file_name.lastIndexOf('.');
    
    if (index == -1)
        return file_name;
    else
      return file_name.substring(0, index);
	}
	
	/**
	 * Méthode pour compter le nombre de fois qu'un caractère particulier se retrouve dans un string.
	 * @param string - Le string en analyse.
	 * @param caracter - Le caractère à identifier et à compter.
	 * @return Le nombre de fois qu'un caractère précis a été compté dans le string.
	 */
	public static int countCaracterInString(String string, char caracter)
	{
	  int count = 0;
		for(int i = 0; i < string.length(); i++)
		  if(string.charAt(i) == caracter)
		    count++;
		  
		return count;
	}
	  
	/**
	 * Méthode pour faire la jonction entre plusieurs String contenu dans un tableau de String. 
	 * L'expression finale comprendra un caractère d'espacement entre les String. 
	 * 
	 * @param strings Le tableau de String.
	 * @param spacer L'expression d'espacement.
	 * @return L'expression comprenant tous les String du tableau de String.
	 */
	public static String join(String[] strings, String spacer)
	{
	  String expression = "";
	  
	  for(int i = 0; i < strings.length; i++)
	  {
	    if(!strings[i].equals(""))
	      expression = expression + strings[i];
	      
	    if(i < strings.length-1)
	      expression = expression + spacer;
	  }
	    
	  return expression;
	}

	/**
	 * Méthode effectuant la fusions de deux tableaux de String. 
	 * L'ordre des éléments sera la même quand dans les tableaux précédent 
	 * où le dernier élément du tableau 1 sera suivit par le 1ier élément du tableau 2.
	 *
	 * @param strings1 - Le 1ier tableau de String à fusionner.
	 * @param strings2 - Le 2ième tableau de String à fusionner.
	 * @return Un tableau de String fusionné.
	 */
	public static String[] merge(String[] strings1, String[] strings2)
	{
	  /*
	  // Version traditionnelle :
	  String[] result = new String[strings1.length + strings2.length];
    
    // Mettre les éléments du 1ier tableau dans le tableau résultant
	  for(int i = 0; i < strings1.length; i++)
	    result[i] = strings1[i];
    
    // Mettre les éléments du 2ier tableau dans le tableau résultant
	  for(int i = 0; i < strings2.length; i++)
	    result[strings1.length + i] = strings2[i];
	  */
	  
	  // Version Stream :
    return Stream.concat(Arrays.stream(strings1), Arrays.stream(strings2)).toArray(String[]::new);
	}
	
	/**
	 * Méthode pour générer un String en retirant l'ensemble des caratères d'espacement
	 * situés au début du String passé en paramètre.
	 *   
	 * @param string - Le mot dont l'on désirer retirer les premiers caractères d'espacement.
	 * @return Le mot sans les premiers caractères d'espacement.
	 */
  public static String removeAllFirstSpacerCaracter(String string)
	{
	  if(string == null)
	    return null;
	  
    // Retirer les 1ier caractères uniquement faisant parti de la liste
    while(string.length() > 0)
      switch(string.charAt(0))
      {
        case ' '  : 
        case '\t' : 
        case '\n' :
        case '\r' : 
        case '\f' : string = string.substring(1); break;
          	          
        default : return string;
      }
      
    return string;   //le caractère sera alors ""
	}
	  
  /**
   * Méthode permettant d'obtenir un message d'erreur à partir d'une exception et de ses causes.
   * 
   * Le format de l'affichage est le suivant :
   * ...
   * 
   * @param e L'exception.
   * @return Le message de l'exception avec ses causes.
   */
  public static String exceptionMessage(Exception e)
  {
    String error_message = "Erreur : ";
    
    // Affichage du message principal de l'erreur.
    if(e != null)
      error_message = error_message + e.getMessage() + SStringUtil.END_LINE_CARACTER;
    else
      error_message = error_message + "L'exception est NULL." + SStringUtil.END_LINE_CARACTER;
    
    // Affichage des causes de l'erreur.
    while(e != null)
    {
      Throwable t = e.getCause();
      
      if(t instanceof Exception)
      {
        Exception cause = (Exception)t;
        
        if(cause.equals(e))
          e = null;         // condition d'arrêt, si la cause et elle-même l'exception.
        else
        {
          String cause_message = cause.getMessage();
          
          if(cause_message != null)
            error_message = error_message + SStringUtil.TAB_CARACTER + "Cause : " + cause_message + SStringUtil.END_LINE_CARACTER;
        }
        
        e = cause;  // Faire l'analyse de la cause.
      }  
      else
        e = null;           // condition d'arrêt, si la cause n'est pas un objet de type exception.  
    }
       
    return error_message;
  }
   
}//fin de la classe SStringUtil
