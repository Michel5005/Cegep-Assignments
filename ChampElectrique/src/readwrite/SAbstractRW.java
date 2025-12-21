/**
 * 
 */
package sim.readwrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import sim.exception.SConstructorException;
import sim.exception.SRuntimeException;
import sim.math.SMath;
import sim.math.SVector3d;
import sim.math.SVectorUV;
import sim.util.SBrowser;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SKeyWordDecoder;
import sim.util.SLog;
import sim.util.SManyFilesFoundException;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe abstraite <b>SAbstractRW</b> représente un objet pouvant être initialisé par une lecture dans un fichier txt. 
 * 
 * @author Simon Vézina
 * @since 2015-01-10
 * @version 2022-04-24
 */
public abstract class SAbstractRW implements SRW {

  //--------------
  // CONSTANTES //
  //--------------
  
  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = { 
      SKeyWordDecoder.KW_END, 
      SKeyWordDecoder.KW_FILE_NAME, 
      SKeyWordDecoder.KW_COMMENT 
      };
  
  /**
   * La constante <b>TRUE_EXPRESSION</b> corresond à la liste des mots reconnus par le système comme étant une expression <b>vrai</b>.
   */
  public static final String[] TRUE_EXPRESSION = { "true", "vrai", "yes", "oui", "on" };   
  
  /**
   * La constante <b>FALSE_EXPRESSION</b> correspond à la liste des mots reconnus par le système comme étant une expression <b>fausse</b>.
   */
  public static final String[] FALSE_EXPRESSION = { "false", "faux", "no", "non", "off" }; 
    
  /**
   * La constante <b>COMMENT_LETTER</b> correspond aux premières lettres d'une ligne lues qui seront considérées comme une ligne de commentaire.
   */
  private static final String COMMENT_LETTER = "#/*-!@";
  
  /**
   * La constante <b>OPEN_COMMENT_MODE</b> correspond au mot clé désignant l'ouveture d'un bloc de commentaire.
   */
  private static final String OPEN_COMMENT_MODE = "/*";
  
  /**
   * La constante <b>CLOSE_COMMENT_MODE</b> correspond au mot clé désignant la fermeture d'un bloc de commentaire.
   */
  private static final String CLOSE_COMMENT_MODE = "*/";
  
  //-----------------------
  // VARIABLES STATIQUES //
  //-----------------------
  
  /**
   * La variable static <b>comment_mode</b> détermine si la lecture est en mode commentaire. 
   * En mode commentaire, toute les lignes lues ne sont pas traitées tant que la variable <i>comment_mode</i> n'est pas <b>false</b>.
   */
  private static boolean comment_mode = false;
  
  //-------------
  // VARIABLES //
  //-------------
  
  /**
   * La variable <b>file_name</b> représente le nom du fichier qui sera utilisé si l'objet de type SRW est écrit dans un fichier texte.
   */
  private String file_name;
  
  /**
   * La variable <b>comment_list</b> représente une liste de commentaire qui sera inscrit avec l'écriture de l'objet.
   */
  private final List<String> comment_list;
  
  //----------------
  // CONSTRUCTEUR //
  //----------------
  
  /**
   * ...
   */
  public SAbstractRW()
  {
    file_name = null;
    comment_list = new ArrayList<String>();
  }
  
  /**
   * ...
   * 
   * @param file_name
   */
  public SAbstractRW(String file_name)
  {
    this.file_name = file_name;
    comment_list = new ArrayList<String>();
  }
  
  //------------
  // MÉTHODES //
  //------------
  
  /**
   * Méthode pour obtenir le nom du fichier en lecture.
   * 
   * @return Le nom du fichier.
   */
  public String getFileName()
  {
    return file_name;
  }
  
  /**
   * Méthode pour définir le nom du fichier où sera lu ou écrit le contenu de cet objet.
   * 
   * @param file_name Le nom du fichier.
   */
  public void setFileName(String file_name)
  {
    this.file_name = file_name;
  }
  
  @Override
  public String[] getRWParameterName()
  {
    return KEYWORD_PARAMETER;
  }
  
  @Override
  public final void read() throws SReadingException
  {
    read(new SBrowser());
  }
  
  /**
   * Méthode pour réaliser la lecture dans un fichier de l'objet SRW à partir d'un répertoire définie par le <i>browser</i> à l'aide de sa définition de nom de fichier.
   * 
   * @param browser Le <i>browser</i>
   * @throws SReadingException Si une erreur de lecture est survenue.
   */
  public final void read(SBrowser browser) throws SReadingException
  {
    if(file_name == null)
      throw new SReadingException("Erreur SAbstractRW  001 : L'objet de type '" + this.getClass().getTypeName() + "' ne peut pas être lu dans un fichier txt, car le nom du fichier n'a pas été spécifié.");
    
    try {
      
      File file = browser.findFile(file_name);
      
      FileReader fr = new FileReader(file);
      SBufferedReader sbr = new SBufferedReader(fr);
      
      try{
        read(sbr);
      }catch(SInitializationException e){
        sbr.close();
        fr.close();
        throw new SReadingException("Erreur SAbstractRW 002 : Une erreur à l'initialisation est survenue." + SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(), e);
      }
      
      sbr.close();
      fr.close();
      
    }catch(FileNotFoundException e) {
      throw new SReadingException("Erreur SAbstractRW 003 : Le fichier '" + file_name + "' n'est pas trouvé.", e);
    }catch(SManyFilesFoundException e) {
      throw new SReadingException("Erreur SAbstractRW 004 : Le fichier '" + file_name + "' a été trouvé plus d'une fois dans les différents sous-répertoires. Veuillez en garder qu'une seule version.", e);
    }catch(IOException e) {
      throw new SReadingException("Erreur SAbstractRW 005 : Le fichier '" + file_name + "' a rencontré une exception de type I/O.", e);
    }
    
  }
   
  /**
   * Méthode qui permet la lecture d'un fichier de format txt pour faire l'initialisation des paramètres de l'objet SAbstractReadable.
   * 
   * @param sbr Le buffer cherchant l'information dans le fichier txt.
   * @throws IOException Si une erreur de type I/O est survenue lors de la lecture.
   * @throws SInitializationException Si une erreur est survenue lors de l'initialisation de l'objet.
   */
  protected final void read(SBufferedReader sbr) throws SInitializationException 
  {
    int code;     //code du mot clé à analyser
    String line;  //ligne lu qui sera analysée dont le premier mot correspond au mot clé qui sera analysé
    
    do
    {
      //initialisation de la lecture d'une ligne
      code = SKeyWordDecoder.CODE_NULL;   
      
      // Lecture de la ligne dans le fichier.
      try {
        line = sbr.readLine();
      }catch(IOException e) {
        throw new SInitializationException("Erreur SAbstractRW 006 : Une erreur est survenue lors de la lecture d'une ligne dans le fichier.", e);
      }
      
      //Vérification si le fichier est à sa dernière ligne sans avoir terminé la lecture de l'objet courant.
      if(line == null)
      {
        // forcer la présence du keyWord de fin de lecture de l'objet (oublié dans le fichier)
        code = SKeyWordDecoder.CODE_END;  
        
        // Si la lecture est en mode 'commentaire', il faudra retirer cette option
        // pour ne pas affecter la lecture d'un prochain fichier puisque ce paramètre est statique
        comment_mode = false;
      }
      else
      {
        StringTokenizer tokens = new StringTokenizer(line);   // Obtenir les tokens de la ligne à analyser
        
        //S'il y a des tokens à analyser
        if(tokens.countTokens() > 0)
        {
          String key_word = tokens.nextToken();       //obtenir le 1ier mot clé de la ligne
        
          //Vérifier si le mot début par un caratère signalant une ligne de commentaire
          if(!isCommentExpression(key_word))
          {
            code = SKeyWordDecoder.getKeyWordCode(key_word);  //généré le code de référence
                  
            //Vérifier si la lecture de l'objet n'est pas complété
            if(code != SKeyWordDecoder.CODE_END)    
              //Vérifier si la lecture est un code d'erreur
              if(code == SKeyWordDecoder.CODE_ERROR)  
              {  
                // Afficher un message signalant que le "keyword" n'est pas reconnu par le système (mot qui n'existe pas)
                SLog.logWriteLine("Ligne " + (sbr.atLine()-1) + " --- " + "Le mot clé '" + key_word + "' n'est pas reconnu par le système.");
                
                // Afficher un message signalant une suggestion de mot clé valide dans la lecture courante de l'objet
                SLog.logWriteLine("Lecture en cours : " + getRWName());
                SLog.logWriteLine("Mots clés admissibles en paramètres : " + SStringUtil.join(getRWParameterName(), ", "));
              }
              else
              {
                //Retirer la présence du mot clé à la ligne restante
                String remaining_line = SStringUtil.removeAllFirstSpacerCaracter(line);     //Retirer les 1ier caractères d'espacement de la ligne (s'il y en a) pour obtenir la ligne restante
                remaining_line = remaining_line.substring(key_word.length());               //Retirer le mot clé de la ligne restante  
                remaining_line = SStringUtil.removeAllFirstSpacerCaracter(remaining_line);  //Retirer les caractères d'espacement suivant le mot clé
                
                //Faire le traitement du mot clé :
                try{
                  
                  // Afficher un message d'erreur si le code ne fait pas parti de la définition de l'objet (mot dans le système, mais pas pour cet objet).
                  if(!read(sbr,code,remaining_line))  
                  {
                    SLog.logWriteLine("Ligne " + (sbr.atLine()-1) + " --- " + "Le mot clé '" + key_word + "' ne fait pas parti de la définition de l'objet '" + getRWName() + "'."); 
                    SLog.logWriteLine("Mots clés admissibles en paramètres : " + SStringUtil.join(getRWParameterName(), ", "));
                    SLog.logWriteLine();
                  }
                
                }catch(SReadingException sre){
                  // Afficher un message d'erreur désignant une erreur dans la lecture.
                  SLog.logWriteLine("Ligne " + (sbr.atLine()-1) + " --- " + "Une erreur de lecture est survenue dans le fichier '" + this.getFileName() + "'.");
                  SLog.logException(sre);
                }
              }
          }//fin if !iscommentWord
        }//fin if tokens > 0
      }//fin else line != null
    }while(code != SKeyWordDecoder.CODE_END);
    
    initialization(); 
  } 

  /**
   * Méthode pour initialiser l'objet après avoir complété la lecture. 
   * Cette méthode sera vide s'il n'y a pas de traitement de donnée à effectuer sur les paramètres de lecture pour rendre l'objet opérationnel.
   * 
   * @throws SInitializationException Si une erreur est survenue lors de l'initialisation.
   */
  protected void initialization() throws SInitializationException
  {
    
  }
  
  /**
   * Méthode pour analyser le code du le mot clé lu dans le fichier txt et exécuter la bonne action 
   * sur les paramètres de l'objet en considérant l'information contenue dans le restant 
   * d'une ligne lue ou en continuant la lecture dans le buffer.
   * @param sbr Le buffer de lecture.
   * @param code Le code du mot clé a exécuter.
   * @param remaining_line Le restant de la ligne suivant le dernier mot clé lu.
   * @throws SReadingException Si une erreur de lecture a été détectée dans le fichier.
   * @return <b>true</b> si le code a été traité par l'objet et <b>false</b> sinon.
   */
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_FILE_NAME : file_name = this.readStringNotEmpty(remaining_line, SKeyWordDecoder.KW_FILE_NAME); return true;
                          
      case SKeyWordDecoder.CODE_COMMENT : comment_list.add(this.readStringNotEmpty(remaining_line, SKeyWordDecoder.KW_COMMENT)); return true;
          
      default : return false;
    }
  }
    
  @Override
  public final void write() throws IOException
  {
    write(new SBrowser());
  }
    
  /**
   * Méthode pour écrire un objet dans un fichier texte à partir de son nom de fichier.
   * 
   * @param browser Le browser positionné à l'emplacement où le fichier sera écrit.
   * @throws IOException S'il y a eu une erreur de type I/O.
   */
  public final void write(SBrowser browser) throws IOException
  {
    // Vérification que l'objet de type SRW possède un nom de fichier pour s'écrire dedans.
    if(this.file_name == null)
      throw new IOException("Erreur SAbstractRW  001 : L'objet de type '" + this.getClass().getTypeName() + "' ne peut pas être écrit dans un fichier txt, car le nom du fichier n'a pas été spécifié.");
    
    File file = browser.createFile(this.file_name);
    
    FileWriter fw = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(fw);
    
    // Écrire l'information contenu dans l'objet.
    this.writeInformation(bw);
    
    bw.close(); //  fermer celui-ci en premier, sinon, ERROR !!!
    fw.close(); 
  }
  
  @Override
  public void writeInformation(BufferedWriter bw) throws IOException
  {
    // Écriture du nom du fichier.
    if(this.file_name != null)
    {
      bw.write(SKeyWordDecoder.KW_FILE_NAME);
      bw.write("\t");
      bw.write(this.file_name);
      bw.write(SStringUtil.END_LINE_CARACTER);
    }
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    // Écriture de la liste de commentaire.
    if(!this.comment_list.isEmpty())
    {
      for(String s : comment_list)
      {
        bw.write(SKeyWordDecoder.KW_COMMENT);
        bw.write("\t");
        bw.write(s);
        bw.write(SStringUtil.END_LINE_CARACTER);
      }
    }
        
  }
  
  /**
   * Méthode pour écrire une ligne de commentaire dans le fichier.
   * 
   * @param bw Le buffer d'écriture dans le fichier.
   * @param comment Le commentaire à écrire.
   * @throws IOException Si une erreur de type I/O est survenue.
   */
  //protected void writeComment(BufferedWriter bw, String comment) throws IOException
  //{
  //  bw.write("/*" + SStringUtil.END_LINE_CARACTER);
  //  bw.write("   " + comment + SStringUtil.END_LINE_CARACTER);
  //  bw.write("*/" + SStringUtil.END_LINE_CARACTER);
  //  bw.write(SStringUtil.END_LINE_CARACTER);
  //}
  
  /**
   * Méthode pour écrire un saut de ligne.
   * 
   * @param bw Le buffer d'écriture dans le fichier
   * @throws IOException Si une erreur de type I/O est survenue.
   */
  /*
  protected void writeEndLine(BufferedWriter bw) throws IOException
  {
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
  */
  
  /**
   * Méthode pour déterminer si le mot lue désigne une ligne de commentaire.
   * Un mot de commentaire est <b>vide</b> ou débute par un <b>caractère particulier</b> (#/*-!@)
   * ou bien la lecture est en mode commentaire (<i>comment_mode</i> = <b>true</b>).
   * @param word - Le mot à analyser.
   * @return <b>true</b> si la ligne est considérée comme un commentaire et <b>false</b> sinon.
   */
  protected boolean isCommentExpression(String word)
  {
    //Vérification du mot vide (pour ne pas lire un premier caractère inexistant)
    if(word.equals(""))
      return true;
    
    //Recherche du mot clé "/*" ou "*/" pour changer l'état de lecture (mode commentaire on/off)
    if(word.length() == 2)
      if(word.equals(OPEN_COMMENT_MODE))    // le mot /*
      {      
        SLog.logWriteLine("Message SAbstractReadable - Ouverture d'un bloc de commentaire.");
        comment_mode = true;
        return true;
      }
      else
        if(word.equals(CLOSE_COMMENT_MODE)) // le mot */
        {
          SLog.logWriteLine("Message SAbstractReadable - Fermeture d'un bloc de commentaire.");
          comment_mode = false;
          return true;
        }
    
    // Analyse du premier caractère de la ligne pour vérifier si seulement cette ligne est retirée de l'analyse
    // seulement si la lecture n'est pas en mode commentaire. Dans ce cas, la ligne sera considérée comme un commentaire.
    if(comment_mode)
      return true;
    else
    {
      char first_letter = word.charAt(0);
        
      for(int i = 0; i < COMMENT_LETTER.length(); i++)
        if(first_letter == COMMENT_LETTER.charAt(i))
          return true;
    }
        
    return false;
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner une valeur de type int.
   * @param remaining_line L'expression en String de la valeur.
   * @param value_name Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return La valeur numérique.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final int readInt(String remaining_line, String value_name)throws SReadingException
  {
    StringTokenizer tokens = new StringTokenizer(remaining_line, SStringUtil.REMOVE_CARACTER_TOKENIZER);  
    
    if(tokens.countTokens() == 0)
      throw new SReadingException("Erreur SAbstractRW 015 : Il n'y a pas de valeur numérique affectée au '" + value_name + "'.");
    
    String s = tokens.nextToken();
      
    try{

      return Integer.valueOf(s);

    }catch(NumberFormatException e){ 
      throw new SReadingException("Erreur SAbstractRW 016 : L'expression '" + s + "' n'est pas un nombre de type int pouvant être affecté à la définition d'un '" + value_name + "'.", e);
    } 
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner une valeur de type int supérieure ou égale à 0.
   * @param remaining_line - L'expression en String de la valeur.
   * @param value_name - Le nom en mots correspondant à la définition de la valeur. Cette valeur sera utilisée s'il y a une exception de lancée.
   * @return La valeur numérique.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final int readIntEqualOrGreaterThanZero(String remaining_line, String value_name)throws SReadingException
  {
    StringTokenizer tokens = new StringTokenizer(remaining_line, SStringUtil.REMOVE_CARACTER_TOKENIZER);  
    
    if(tokens.countTokens() == 0)
      throw new SReadingException("Erreur SAbstractReadable 001 : Il n'y a pas de valeur numérique affectée au " + value_name +".");
    
    String s = tokens.nextToken();
        
    try{
      int value = Integer.valueOf(s);
      
      if(value < 0)
        throw new SReadingException("Erreur SAbstractReadable 002 : Le " + value_name + " '" + value + "' doit être égal ou supérieur à 0.");
      else
        return value;
    }catch(NumberFormatException e){ 
      throw new SReadingException("Erreur SAbstractReadable 003 : L'expression '" + s + "' n'est pas un nombre de type int pouvant être affecté à la définition d'un " + value_name + ".");
    } 
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner une valeur de type int supérieure à 0.
   * 
   * @param remaining_line - L'expression en String de la valeur.
   * @param value_name - Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return La valeur numérique.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final int readIntGreaterThanZero(String remaining_line, String value_name)throws SReadingException
  {
    StringTokenizer tokens = new StringTokenizer(remaining_line, SStringUtil.REMOVE_CARACTER_TOKENIZER);
    
    if(tokens.countTokens() == 0)
      throw new SReadingException("Erreur SAbstractReadable 004 : Il n'y a pas de valeur numérique affectée au '" + value_name +"'.");
    
    String s = tokens.nextToken();
        
    try{
      int value = Integer.valueOf(s);
      
      if(value < 1)
        throw new SReadingException("Erreur SAbstractReadable 005 : Le '" + value_name + "' de valeur '" + value + "' doit être supérieur à 0.");
      else
        return value;
      
    }catch(NumberFormatException e){ 
      throw new SReadingException("Erreur SAbstractReadable 006 : L'expression '" + s + "' n'est pas un nombre de type int pouvant être affecté à la définition d'un '" + value_name + "'.");
    } 
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner une valeur de type int supérieure ou égale à une valeur de référence.
   * 
   * @param remaining_line L'expression en String de la valeur.
   * @param value La valeur de référence.
   * @param value_name Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return La valeur numérique.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final int readIntEqualOrGreaterThanValue(String remaining_line, int value, String value_name) throws SReadingException
  {
    int cst = readInt(remaining_line, value_name);
  
    if(cst < value)
      throw new SReadingException("Erreur SAbstractRW 018 : La valeur '" + cst +"' est inférieure à la valeur minimale acceptable étant '" + value +" pouvant être affecté à la définition d'un '" + value_name + "'.");
    
    return cst;
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner une valeur de type integer supérieure ou égale à une valeur de référence et inférieure ou égale à une valeur de référence.
   *
   * @param remaining_line L'expression en String de la valeur.
   * @param min_value La valeur de référence minimale.
   * @param max_value La valeur de référence maximale.
   * @param value_name Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return La valeur numérique.
   * @throws SReadingException S'il y a une erreur de lecture.
   * @throws SRuntimeException Si la valeur de référence minimale et maximal ne sont pas cohérentes.
   */
  protected final int readIntEqualOrGreaterThanValueAndEqualOrSmallerThanValue(String remaining_line, int min_value, int max_value, String value_name) throws SReadingException, SRuntimeException
  {
    if(min_value > max_value)
      throw new SRuntimeException("Erreur SAbstractReadable 019 : L'usage de la méthode est erroné. La valeur min_value = " + min_value + " est supérieure à max_value = " + max_value + ".");
    
    int value = readIntEqualOrGreaterThanValue(remaining_line, min_value, value_name);
    
    if(value > max_value)
      throw new SReadingException("Erreur SAbstractReadable 020 : La valeur '" + value +"' est supérieure à la valeur maximale acceptable étant '" + max_value +" pouvant être affecté à la définition d'un '" + value_name + "'.");
    
    return value;
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner une valeur de type int égale ou supérieure à 0. 
   * Une expression en String peut être également reconnue pour définir la valeur de type int désirée.
   * @param remaining_line - L'expression en String de la valeur.
   * @param value_name - Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @param valid_expression - Le tableau des String pouvant être reconnue. Leur position dans le tableau définisse également leur valeur attitrée.
   * @return La valeur numérique.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final int readIntOrExpression(String remaining_line, String value_name, String[] valid_expression)throws SReadingException
  {
    StringTokenizer tokens = new StringTokenizer(remaining_line, SStringUtil.REMOVE_CARACTER_TOKENIZER);  
    
    if(tokens.countTokens() == 0)
      throw new SReadingException("Erreur SAbstractReadable 007 : Il n'y a pas de valeur numérique affectée au '" + value_name + "'.");
   
    String s = tokens.nextToken();
      
    try{ 
      
      //Analyse par une valeur numérique :
      int value = Integer.valueOf(s);
      
      if(value < 0)
        throw new SReadingException("Erreur SAbstractReadable 008 : Le " + value_name + " '" + value + "' doit être égal ou supérieur à 0.");
      else
        if(value >= valid_expression.length) //ne pas dépasser le nombre d'expression valide
          throw new SReadingException("Erreur SAbstractReadable 009 : Le " + value_name + " '" + value + "' doit être inférieur à '" + valid_expression.length + "'. Les expressions suivantes sont égalements valides : " + SStringUtil.join(valid_expression, ", "));
        else
          return value;
      
    }catch(NumberFormatException e){ 
      
      //Analyse par mot clé : Recherche du mot clé dans la liste des expressions valides dont l'index correspond à la valeur numérique attitrée
      for(int i=0; i<valid_expression.length; i++)
        if(s.equalsIgnoreCase(valid_expression[i]))
          return i;
      
      throw new SReadingException("Erreur SAbstractReadable 010 : L'expression '" + s + "' n'est pas reconnue pour définir la valeur '" + value_name + "'. Les expressions valides sont les suivantes : " + SStringUtil.join(valid_expression, ", "));
    }
  }
        
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner un string <b>non vide</b>.
   * @param remaining_line - L'expression en String de la valeur.
   * @param value_name - Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return Le string non vide.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final String readStringNotEmpty(String remaining_line, String value_name)throws SReadingException
  {
    if(remaining_line.length() == 0)
      throw new SReadingException("Erreur SAbstractReadable 011 : Le string à analyser est '" + remaining_line + "' et n'est pas une expression adéquate à affecteur à la valeur '" + value_name + "'.");
    else
      return remaining_line;
  }
  
  /**
   * Méthode pour faire l'analyse d'un string désignant <b>vrai</b> ou <b>faux</b>. 
   * Un nombre entier peut également être lu où <b>0</b> désigne <b>faux</b> et les <b>autres entiers positifs</b> désigne <b>vrai</b>.
   * @param remaining_line - L'expression en String de la valeur.
   * @param value_name - Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return <b>true</b> si la lecture est interprétée comme <b>vrai</b> et <b>false</b> si la lecture est interprétée comme étant <b>faux</b>.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final boolean readTrueFalseExpressionOrInt(String remaining_line, String value_name) throws SReadingException
  {
    StringTokenizer tokens = new StringTokenizer(remaining_line, SStringUtil.REMOVE_CARACTER_TOKENIZER);  
    
    if(tokens.countTokens() == 0)
      throw new SReadingException("Erreur SAbstractReadable 012 : Il n'y a pas de valeur numérique affectée au '" + value_name + "'.");
    
    String s = tokens.nextToken();
    
    try{ 
      
      //Analyse par une valeur numérique :
      int value = Integer.valueOf(s);
      
      if(value < 0)
        throw new SReadingException("Erreur SAbstractReadable 013 : Le " + value_name + " '" + value + "' doit être égal ou supérieur à 0.");
      else
        if(value == 0)
          return false;
        else
          return true;
      
    }catch(NumberFormatException e){ 
      
      //Analyse par mot clé : true/vrai/yes/oui et false/faux/no/non
      s = s.toLowerCase(Locale.ENGLISH);
      
      for(int i = 0; i < TRUE_EXPRESSION.length; i++)
        if(s.equals(TRUE_EXPRESSION[i]))
          return true;
      
      for(int i = 0; i < FALSE_EXPRESSION.length; i++)
        if(s.equals(FALSE_EXPRESSION[i]))
          return false;
      
      throw new SReadingException("Erreur SAbstractReadable 014 : L'expression '" + s + "' n'est pas reconnue pour définir la valeur '" + value_name + "'. Les expressions valides sont les suivantes : " + SStringUtil.join(TRUE_EXPRESSION, ", ") + " et " + SStringUtil.join(FALSE_EXPRESSION, ", "));
    }
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner une valeur de type double.
   * @param remaining_line - L'expression en String de la valeur.
   * @param value_name - Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return La valeur numérique.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final double readDouble(String remaining_line, String value_name)throws SReadingException
  {
    StringTokenizer tokens = new StringTokenizer(remaining_line, SStringUtil.REMOVE_CARACTER_TOKENIZER);  
    
    if(tokens.countTokens() == 0)
      throw new SReadingException("Erreur SAbstractReadable 015 : Il n'y a pas de valeur numérique affectée au '" + value_name + "'.");
    
    String s = tokens.nextToken();
      
    try{

      return Double.valueOf(s);

    }catch(NumberFormatException e){ 
      throw new SReadingException("Erreur SAbstractReadable 016 : L'expression '" + s + "' n'est pas un nombre de type double pouvant être affecté à la définition d'un '" + value_name + "'.", e);
    } 
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner une valeur de type double supérieure à 0 (et non nul).
   * @param remaining_line - L'expression en String de la valeur.
   * @param value_name - Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return La valeur numérique.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final double readDoubleGreaterThanZero(String remaining_line, String value_name)throws SReadingException
  {
    double value = readDouble(remaining_line, value_name);
    
    if(value < SMath.EPSILON)
      throw new SReadingException("Erreur SAbstractReadable 017 : Le '" + value_name + "' de valeur '" + value + "' doit être supérieur à '" + SMath.EPSILON + "' pour être valide.");
     
    return value;
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner une valeur de type double supérieur ou égale à une valeur de référence.
   * @param remaining_line L'expression en String de la valeur.
   * @param value La valeur de référence.
   * @param value_name Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return La valeur numérique.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final double readDoubleEqualOrGreaterThanValue(String remaining_line, double value, String value_name) throws SReadingException
  {
    double cst = readDouble(remaining_line, value_name);
  
    if(cst < value)
      throw new SReadingException("Erreur SAbstractReadable 018 : La valeur '" + cst +"' est inférieure à la valeur minimale acceptable étant '" + value +" pouvant être affecté à la définition d'un '" + value_name + "'.");
    
    return cst;
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner une valeur de type double supérieure ou égale à une valeur de référence et inférieure ou égale à une valeur de référence.
   *
   * @param remaining_line L'expression en String de la valeur.
   * @param min_value La valeur de référence minimale.
   * @param max_value La valeur de référence maximale.
   * @param value_name Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return La valeur numérique.
   * @throws SReadingException S'il y a une erreur de lecture.
   * @throws SRuntimeException Si la valeur de référence minimale et maximal ne sont pas cohérentes.
   */
  protected final double readDoubleEqualOrGreaterThanValueAndEqualOrSmallerThanValue(String remaining_line, double min_value, double max_value, String value_name) throws SReadingException, SRuntimeException
  {
    if(min_value > max_value)
      throw new SRuntimeException("Erreur SAbstractReadable 019 : L'usage de la méthode est erroné. La valeur min_value = " + min_value + " est supérieure à max_value = " + max_value + ".");
    
    double value = readDoubleEqualOrGreaterThanValue(remaining_line, min_value, value_name);
    
    if(value > max_value)
      throw new SReadingException("Erreur SAbstractReadable 020 : La valeur '" + value +"' est supérieure à la valeur maximale acceptable étant '" + max_value +" pouvant être affecté à la définition d'un '" + value_name + "'.");
    
    return value;
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner un tableau de type double.
   * @param remaining_line L'expression en String de la valeur.
   * @param value_name Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return Le tableau de type double.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final double[] readDoubleArray(String remaining_line, String value_name)throws SReadingException
  {
    StringTokenizer tokens = new StringTokenizer(remaining_line, SStringUtil.REMOVE_CARACTER_TOKENIZER);  
    
    if(tokens.countTokens() == 0)
      throw new SReadingException("Erreur SAbstractReadable 021 : Il n'y a pas de valeur numérique affectée au '" + value_name + "'.");
    
    double[] array = new double[tokens.countTokens()];
    
    String s;
    int i = 0;
    
    while(tokens.hasMoreTokens())      
    {
      s = tokens.nextToken();
      
      try{
        array[i] = Double.valueOf(s);
        i++;
      }catch(NumberFormatException e){ 
        throw new SReadingException("Erreur SAbstractReadable 022 : L'expression '" + s + "' n'est pas un nombre de type double pouvant être affecté à la définition d'un '" + value_name + "'.", e);
      } 
    }
    
    return array; 
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner un tableau de type int.
   * 
   * @param remaining_line L'expression en String de la valeur.
   * @param value_name Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return Le tableau de type int.
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final int[] readIntArray(String remaining_line, String value_name)throws SReadingException
  {
    StringTokenizer tokens = new StringTokenizer(remaining_line, SStringUtil.REMOVE_CARACTER_TOKENIZER);  
    
    if(tokens.countTokens() == 0)
      throw new SReadingException("Erreur SAbstractRW 022 : Il n'y a pas de valeur numérique affectée au '" + value_name + "'.");
    
    int[] array = new int[tokens.countTokens()];
    
    String s;
    int i = 0;
    
    while(tokens.hasMoreTokens())      
    {
      s = tokens.nextToken();
      
      try{
        array[i] = Integer.valueOf(s);
        i++;
      }catch(NumberFormatException e){ 
        throw new SReadingException("Erreur SAbstractRW 024 : L'expression '" + s + "' n'est pas un nombre de type int pouvant être affecté à la définition d'un '" + value_name + "'.", e);
      } 
    }
    
    return array; 
  }
  
  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner un objet de type SVector3d.
   * 
   * @param remaining_line L'expression en String de la valeur.
   * @param value_name Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return Le vecteur
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final SVector3d readSVector3d(String remaining_line, String value_name) throws SReadingException
  {
    try {
      
      return new SVector3d(remaining_line);
      
    }catch(SConstructorException e) {
      throw new SReadingException("Erreur SAbstractRW 025 : L'expression '" + remaining_line + "' n'est pas valide pour définir un vecteur", e);
    }
  }

  /**
   * Méthode pour faire l'analyse d'un string afin d'y retourner un objet de type SVectorUV.
   * 
   * @param remaining_line L'expression en String de la valeur.
   * @param value_name Le nom en mots correspondant à la définition de la valeur. Cette information sera utilisée s'il y a une exception de lancée.
   * @return Le vecteur
   * @throws SReadingException S'il y a une erreur de lecture.
   */
  protected final SVectorUV readSVectorUV(String remaining_line, String value_name) throws SReadingException
  {
    try {
      
      return new SVectorUV(remaining_line);
      
    }catch(SConstructorException e) {
      throw new SReadingException("Erreur SAbstractRW 025 : L'expression '" + remaining_line + "' n'est pas valide pour définir un vecteur", e);
    }
  }
  
}
