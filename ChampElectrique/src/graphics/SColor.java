package sim.graphics;

import java.awt.Color;
import java.util.Locale;
import java.util.StringTokenizer;
import java.io.BufferedWriter;
import java.io.IOException;

import sim.exception.SConstructorException;
import sim.exception.SRuntimeException;
import sim.math.SMath;
import sim.math.SVector;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * <p>
 * La classe <b>SColor</b> représente une couleur à 4 composantes (r,g,b,a) rouge, vert, bleu et alpha (transparence). 
 * </p>
 * <p>
 * Cette classe est complémentaire à la classe java.awt.Color puisqu'elle pourra supporter des opérations d'addition et de multiplication par un scalaire. 
 * Cette classe permet l'initialisation de ses paramètres à partir d'un String.
 * Cette classe permet une écriture dans un fichier de format txt à l'aide de la classe BufferedWriter.
 * </p>
 * 
 * @author Simon Vezina
 * @since 2014-12-18
 * @version 2022-03-21
 */
public class SColor implements SVector {

	
  //--------------
  // CONSTANTES //
  //--------------
  
	/**
	 * La constant <b>NEGATIVE_COLOR_LIMITE</b> représente une limite négative à laquelle une couleur sera considérée comme étant nulle.
	 */
	private static final double NEGATIVE_COLOR_LIMITE = -1e-8;
	
  /**
   * La constante <b>COLOR_NORMALIZATION</b> correspond à l'ensemble des mots clés qui sont reconnus pour identifier un code de référence pour déterminer le choix de l'algorithme de normalisation des couleurs.
   */
  public static final String[] COLOR_NORMALIZATION = {"clamp_channel", "division_factor"};
  
  /**
   * La constante <b>CLAMP_CHANNEL_NORMALIZATION</b> correspond au code de référence pour utiliser l'algorithme de normalisation des couleurs par coupure de canal.
   * Cette approche réduit à une valeur égale à 1.0 tout canal supérieur à 1.0. 
   */
  public static final int CLAMP_CHANNEL_NORMALIZATION = 0;
  
  /**
   * La constante <b>DIVISION_FACTOR_NORMALIZATION</b> correspond au code de référence pour utiliser l'algorithme de normalisation des couleurs par facteur de division.
   * Cette approche réduit tous les canaux d'un certain facteur selon l'amplitude maximale de l'un d'eux. 
   * Si un canal est supérieur à 1.0 et qu'il est le plus élevé parmis les canaux rgb, il sera divisé par sa grandeur le réduisant à 1.0 et tous les autres canaux rgb seront divisés par ce même facteur les réduisant à une valeur inférieure au canal de valeur maximal.
   */
  public static final int DIVISION_FACTOR_NORMALIZATION = 1;
	
  /**
   * La constante <b>CHANNEL_MAX_VALUE</b> correspond à la valeur maximale d'un canal d'une couleur étant égale à 1.0.
   */
  private static double CHANNEL_MAX_VALUE = 1.0;                
  
  /**
   * La constante <b>DEFAULT_CHANNEL</b> correspond à la valeur par défaut d'un canal d'une couleur étant égale à 1.0.
   */
  private static double DEFAULT_CHANNEL = 1.0;            
  
  /**
   * La constante <b>BLACK</b> correspond à la couleur <u>noire</u>.
   */
  public static SColor BLACK = new SColor(0.0, 0.0, 0.0, 1.0);
  
  /**
   * La constante <b>WHITE</b> correspond à la couleur <u>blanche</u>.
   */
  public static SColor WHITE = new SColor(1.0, 1.0, 1.0, 1.0);
  
  /**
   * La constante <b>RED</b> correspond à la couleur <u>rouge</u>.
   */
  public static SColor RED = new SColor(1.0, 0.0, 0.0, 1.0);
  
  /**
   * La constante <b>GREEN</b> correspond à la couleur <u>verte</u>.
   */
  public static SColor GREEN = new SColor(0.0, 1.0, 0.0, 1.0);
  
  /**
   * La constante <b>BLUE</b> correspond à la couleur <u>bleu</u>.
   */
  public static SColor BLUE = new SColor(0.0, 0.0, 1.0, 1.0);
  
  /**
   * La constante <b>YELLOW</b> correspond à la couleur <u>jaune</u>.
   */
  public static SColor YELLOW = new SColor(1.0, 0.965, 0.0);
  
  /**
   * La constante <b>ORANGE</b> correspond à la couleur <u>orange</u>.
   */
  public static SColor ORANGE = new SColor(1.0, 0.647, 0.0);
  
  //-----------------------
  // VARIABLES STATIQUES //
  //-----------------------
  
  /**
   * La variable <b>color_normalization</b> correspond au code de référence utilisé pour choisir l'algorithme de normalisation des couleurs.
   */
  private static int color_normalization = CLAMP_CHANNEL_NORMALIZATION;   
	
  //-------------
  // VARIABLES //
  //-------------
  
	/**
	 * La variable <b>r</b> correspond au canal de couleur rouge (<i>red</i>). Cette valeur doit être toujours positive.
	 */
  final private double r;	
  
  /**
   * La variable <b>g</b> correspond au canal de couleur verte (<i>green</i>). Cette valeur doit être toujours positive.
   */
	final private double g;	
	
	/**
   * La variable <b>r</b> correspond au canal de couleur bleu (<i>blue</i>). Cette valeur doit être toujours positive.
   */
	final private double b;	
	
	/**
	 * La variable <b>a</b> correspond au canal de couleur alpha étant la transparence. Cette valeur doit être toujours positive.
	 * La valeur <b>0.0</b> correspond à une transparence à 100% et une valeur <b>1.0</b> correspond à une transparence à 0% (opacité complète).
	 */
	final private double a;	
	
	//----------------
	// CONSTRUCTEUR //
	//----------------
	
	/**
	 * Constructeur vide correpondant à la couleur blanche et transparence nulle (alpha = 1).
	 */
	public SColor()
	{
		this(DEFAULT_CHANNEL, DEFAULT_CHANNEL, DEFAULT_CHANNEL);
	}
	
	/**
	 * Constructeur d'une couleur avec transparance nulle (complètement opaque). La valeur de alpha est de 1.
	 * 
	 * @param red - La couleur rouge.
	 * @param green - La couleur verte.
	 * @param blue - La couleur bleu.
	 * @throws SConstructorException Si la couleur est définie avec un paramètre négatif.
	 */
	public SColor(double red, double green, double blue) throws SConstructorException
	{ 
		this(red, green, blue, 1.0);
	}
	
	/**
	 * Constructeur d'une couleur avec transparence alpha.
	 * 
	 * @param red - La couleur rouge.
	 * @param green - La couleur verte.
	 * @param blue - La couleur bleu.
	 * @param alpha - La transparence.
	 * @throws SConstructorException Si la couleur est définie avec un paramètre négatif.
	 */
	public SColor(double red, double green, double blue, double alpha) throws SConstructorException
	{
		// Vérifier si la couleur possède uniquement des composantes positives.
	  // Pour des raisons numériques, il est possible qu'un canal ait une valeur très près de zéro, mais négativement (-0).
	  // Cette couleur sera alors interprétée comme étant près de zéro, mais transformée positivement (-0 vers +0) par la suite.
	  if(red < NEGATIVE_COLOR_LIMITE || green < NEGATIVE_COLOR_LIMITE || blue < NEGATIVE_COLOR_LIMITE || alpha < NEGATIVE_COLOR_LIMITE)
		  throw new SConstructorException("Erreur SColor 001 : La couleur [" + red + "," + green + "," + blue + "," + alpha + "] possède un paramètre négatif et inférieur au seuil acceptable de " + NEGATIVE_COLOR_LIMITE + ".");
		
	  // Affecter la valeur absolue aux différents canaux de couleur (pour gérer le cas particulier précédent)
	  r = Math.abs(red);
		g = Math.abs(green);
		b = Math.abs(blue);
		a = Math.abs(alpha);
	}
	
	/**
	 * <p>
	 * Constructeur d'une couleur en utilisant un String comme paramètre fractionné dans un StringTokenizer. Une lecture autorisée peut prendre la forme suivante en fonction du nombre de tokens dans le StringTokenizer.
	 * </p>
	 * <p>Format (1) : "nom couleur"</p>
	 * <p>Format (2) : "nom couleur" "float alpha"</p>
	 * <p>Format (3) : "float r" "float g" "float b"</p>
	 * <p>Format (4) : "float r" "float g" "float b" "float a"</p>
	 * @param string - L'expression donnant accès aux paramètres r, g, b et a de la couleur.
	 * @throws SReadingException Si le format de la lecture n'est pas adéquat.
	 * @throws SConstructorException Si la couleur est définie avec un paramètre négatif.
	 */
	public SColor(String string)throws SReadingException, SConstructorException
	{
		double[] tab = read(string);
		
		//Vérifier si la couleur possède uniquement des composantes positives
		if(tab[0] < 0.0 || tab[1] < 0.0 || tab[2] < 0.0 || tab[3] < 0.0)
      throw new SConstructorException("Erreur SColor 002 : L'expression de la La couleur égale à [" + tab[0] + "," + tab[1] + "," + tab[2] + "," + tab[3] + "] possède un paramètre négatif.");
    
		r = tab[0];
		g = tab[1];
		b = tab[2];
		a = tab[3];
	}
	
	/**
	 * Constructeur d'une couleur à partir d'une couleur de type <b>Color</b> définit à l'aide de canaux entier compris dans l'intervalle [0, 255]. 
	 * @param color La couleur à convertir.
	 */
	public SColor(Color color)
	{
	  float[] compArray = new float[4];
	  compArray = color.getColorComponents(compArray);
	  
	  r = (double) compArray[0];
	  g = (double) compArray[1];
	  b = (double) compArray[2];
	  a = (double) compArray[3];
	}
	
	/**
	 * Constructeur d'une couleur à partir d'une couleur de type <b>Color</b> définit à l'aide de canaux entier compris dans l'intervalle [0, 255]. 
	 * Cependant, le canal alpha est redéfini à partir d'une nouvelle valeur passée en paramètre. 
	 * Cette subtilité est nécessaire, car il existe plusieurs conventions de paramètre alpha (0.0 -> opaque ou bien 1.0 -> opaque).
	 * En choisissant ce constructeur, nous pouvons changer une convention et l'adapter à une autre.
	 *   
	 * @param color La couleur à convertir.
	 * @param alpha Le nouveau paramètre de transparence. 
	 */
	public SColor(Color color, double alpha)
	{
	  float[] compArray = new float[4];
    compArray = color.getColorComponents(compArray);
    
    r = (double) compArray[0];
    g = (double) compArray[1];
    b = (double) compArray[2];
    a = alpha;
	}
	
	//------------
	// MÉTHODES //
	//------------
	
	/**
	 * Méthode qui donne la couleur du canal rouge.
	 * @return La couleur rouge.
	 */
	public double getRed()
	{ 
	  return r;
	}
	
	/**
	 * Méthode qui donne la couleur du canal vert.
	 * @return La couleur verte.
	 */
	public double getGreen()
	{ 
	  return g;
	}
	
	/**
	 * Méthode qui donne la couleur du canal blue.
	 * @return La couleur bleu.
	 */
	public double getBlue()
	{ 
	  return b;
	}
	
	/**
	 * Méthode qui donne la transparence de la couleur.
	 * @return La transparence dans l'intervalle [0,1].
	 */
	public double getAlpha()
	{ 
	  return a;
	}
	
	/**
	 * Méthode pour obtenir le code correspondant au type de normalisation appliquée aux couleurs dont un canal ou plus dépasse la valeur de 1.0.
	 * @return Le code du type de normalisation.
	 */
	public static int getColorNormalization()
	{
	  return color_normalization;
	}
	
	/**
	 * Méthode pour définir le type de normalisation qui sera appliquée aux couleurs dont un canal au plus dépasse la valeur de 1.0.
	 * @param code - Le code associé au type d'algorithme de normalisation de la couleur.
	 * @throws SRuntimeException Si le code de référence n'est pas reconnu par le système.
	 */
	public static void setColorNormalization(int code)throws SRuntimeException
	{
	  switch(code)
    {
      case CLAMP_CHANNEL_NORMALIZATION : 
      
      case DIVISION_FACTOR_NORMALIZATION : color_normalization = code; break;
    
      default : throw new SRuntimeException("Erreur SColor 003 : Le type de normalisation '" + code + "' n'est pas reconnu par le système.");
    }
	}
	
	/**
	 * Méthode pour obtenir la couleur opposée à la couleur actuelle. 
	 * La couleur opposée correspond à la couleur blanche que l'on soustrait à la couleur actuelle.
	 * 
	 * @return La couleur opposée.
	 */
	public SColor oppositeColor()
	{
	  return new SColor(1.0 - r, 1.0 - g, 1.0 - b);
	}
	
	@Override
  public SVector add(SVector v) 
	{
	  return add((SColor) v);
  }
	
	/**
	 * <p>
	 * Méthode qui retourne l'addition de la couleur de l'objet. avec une nouvelle couleur. 
	 * </p>
	 * <p>
	 * Il est important de noter qu'une nouvelle référence sera retournée par la méthode,
	 * car une couleur est non-modifiable (<i>immutable</i>). 
	 * </p>
	 * 
	 * @param c La couleur à ajouter.
	 * @return La somme des deux couleurs (sans influencer la transparence).
	 */
	public SColor add(SColor c)
	{	
		return new SColor(r + c.r, g + c.g, b + c.b, a);
	}
	
	/**
	 * Méthode qui retourne le résultat de la multiplication par un scalaire de chaque canal de la couleur sauf le canal alpha restant inchangé. Ce calcul correcpond à la multiplication d'un vecteur par un scalaire sans affecter la 4ième composante.
	 * @param m - Le muliplicateur de chaque canal (sauf le alpha).
	 * @return La couleur mulipliée par un scalaire.
	 */
	public SColor multiply(double m)
	{
		return new SColor(m*r, m*g, m*b, a);
	}
	
	/**
	 * Méthode pour multiplier deux couleurs ensembles. Le calcul correspond à muliplier les canaux semblables ensemble (sauf le canal alpha).
	 * @param c La couleur à multiplier
	 * @return Un nouvelle couleur qui est le produit de deux couleurs précédentes.
	 */
	public SColor multiply(SColor c)
	{
		return new SColor(r * c.r, g * c.g, b * c.b, a);
	}
	
	@Override
  public double dot(SVector v)
  {
	  return dot((SColor)v);
  }
	
	/**
   * Méthode pour effectuer le <b>produit scalaire</b> entre deux vecteurs.
   * 
   * @param c Le vecteur à mettre en produit scalaire.
   * @return Le produit scalaire entre les deux vecteurs.
   */
	public double dot(SColor c)
  {
    return r*c.r + g*c.g + b*c.b + a*c.a;
  }
	
	/**
	 * Méthode pour normaliser une couleur. Le type de normalisation dépend du choix de l'algorythme choisi. 
	 * @return La couleur après normalisation.
	 * @throws SRuntimeException Si le type de normalisation n'est pas reconnu par le système.
	 * @see normalizeClampChannel
	 * @see normalizeDivisionFactor
	 */
	public SColor normalize()throws SRuntimeException
	{
		switch(color_normalization)
		{
		  case CLAMP_CHANNEL_NORMALIZATION : return normalizeClampChannel();
		  
		  case DIVISION_FACTOR_NORMALIZATION : return normalizeDivisionFactor();
		
		  default : throw new SRuntimeException("Erreur SColor 005 : Le type de normalisation '" + color_normalization + "' n'est pas reconnu par le système.");
		}
	}
	
	/**
	 * Méthode pour normaliser une couleur en appliquant un seuil minimal et maximal à chaque canal de la couleur sauf pour le canal alpha restant inchangé.
	 * Ainsi, un canal ayant une valeur inférieure ou supérieure aux valeurs de seuil sera réduit à la valeur de seuil approprié.
	 * Habituellement, le seuil est limité entre 0.0 et 1.0 en raison des standards des librairies graphiques. 
	 * @return La couleur normalisée selon les seuils (sauf pour le alpha).
	 */
	private SColor normalizeClampChannel()
	{
		double tmp_r = Math.min(r, CHANNEL_MAX_VALUE);
				
		double tmp_g = Math.min(g, CHANNEL_MAX_VALUE);
				
		double tmp_b = Math.min(b, CHANNEL_MAX_VALUE);
				
		double tmp_a = Math.min(a, CHANNEL_MAX_VALUE);
		
		return new SColor(tmp_r, tmp_g, tmp_b, tmp_a);
	}
	
	/**
	 * Méthode pour normaliser une couleur en divisant l'amplitude des canaux par l'amplitude du plus grand 
	 * seulement si un canal possède une amplitude supérieure à 1.0 .
	 * @return
	 */
	private SColor normalizeDivisionFactor()
	{
	  //Trouver le canal ayant la valeur la plus grande
	  double max = r;
	  
	  if(max < g)
	    max = g;
	  
	  if(max < b)
	    max = b;
	  
	  //S'assurer de ne pas augmenter les canaux de couleur
	  if(max < 1.0)
	    max = 1.0;
	  
	  //Diviser tous les canaux par la valeur maximale ce qui limite à 1.0 le plus grand canal et les autres seront également réduit par le même facteur
	  return new SColor(r/max, g/max, b/max, a);
	}
	
	/**
	 * Méthode qui retourne un objet Color convertible en format RGB [0..255]. Si un canal rgb dépasse l'intervalle [0..1], il est alors maximisé à 1.0f (ou 255). Cette couleur est alors normalisée.
	 * @return La couleur après normalisation.
	 * @see Color
	 */
	public Color normalizeColor()
	{
		SColor nor_color = normalize();
		
		return new Color((float)nor_color.r, (float)nor_color.g, (float)nor_color.b, (float)nor_color.a);
	}
	
	@Override
	public String toString()
	{
		return "[ " + r + ", " + g + ", " + b + ", " + a + " ]";		
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other == null) 							//Test du null
			return false;
		    
		if (other == this)							//Test de la même référence 
			return true;
		    
		if (!(other instanceof SColor))	//Test d'un type différent
		    	return false;
		    
		//Vérification de chaque canal de la couleur (si identique)
		SColor o = (SColor)other;
		
		if(!SMath.nearlyEquals(r, o.r))
		  return false;
		
		if(!SMath.nearlyEquals(g, o.g))
      return false;
		
		if(!SMath.nearlyEquals(b, o.b))
      return false;
		
		if(!SMath.nearlyEquals(a, o.a))
      return false;
		
		return true;    
	}
	
	/**
	 * Méthode pour lire une couleur en utilisant un String comme paramètre fractionné dans un StringTokenizer. Une lecture autorisée peut prendre la forme suivante en fonction du nombre de tokens dans le StringTokenizer.
	 *  Format (1) : "nom couleur"
	 *  Format (2) : "nom couleur" "double alpha"
	 *  Format (3) : "double r" "double g" "double b"
	 *  Format (4) : "double r" "double g" "double b" "double a"
	 *  Remarque : Les caractèses , ( ) [ ] < > seront utilisés comme délimieur dans l'expression.
	 * @param string L'expression de la couleur en format texte.
	 * @return Un tableau donnant accès au couleur rgba tel que r = [0], g = [1], b = [2] et a = [3].
	 * @throws SReadingException Si le format de la lecture n'est pas adéquat avec l'un des quatre modes de lecture.
	 */
	private double[] read(String string) throws SReadingException
	{
		double[] tab = new double[4];
		double[] tmp;
		
		StringTokenizer tokens = new StringTokenizer(string,SStringUtil.REMOVE_CARACTER_TOKENIZER);	
		
		switch(tokens.countTokens())
		{
			case 0 : 	throw new SReadingException("Erreur SColor 006 : Il n'y a pas d'élément dans la définition de la couleur."); 
			
			case 1 : 	tmp = readColorName(tokens.nextToken()); 
    						tab[0] = tmp[0];
    						tab[1] = tmp[1];
    						tab[2] = tmp[2];
    						tab[3] = 1.0; 
    						break;
			
			case 2 :	tmp = readColorName(tokens.nextToken()); 
    						tab[0] = tmp[0];
    						tab[1] = tmp[1];
    						tab[2] = tmp[2];
    						tab[3] = readChannel(tokens.nextToken());
    						break;
			
			case 3 :	tab[0] = readChannel(tokens.nextToken());
    						tab[1] = readChannel(tokens.nextToken());
    						tab[2] = readChannel(tokens.nextToken());
    						tab[3] = 1.0;
    						break;
			
			case 4 :	tab[0] = readChannel(tokens.nextToken());
    						tab[1] = readChannel(tokens.nextToken());
    						tab[2] = readChannel(tokens.nextToken());
    						tab[3] = readChannel(tokens.nextToken());
    						break;
			
			default :	throw new SReadingException("Erreur SColor 007 : L'expression '" + string + "' contient plus de 4 éléments dans la lecture de la couleur ce qui est invalide."); 
		} 
		
		return tab;
	}
	
	/**
	 * Méthode pour faire l'analyser d'un String afin d'affecter les canaux rgb à une valeur correspondant au choix de la couleur nommé par le String.
	 * @param s Le nom de la couleur.
	 * @return Un tableau donnant accès au couleur rgb tel que r = [0], g = [1] et b = [2].
	 * @throws SReadingException Si le nom de la couleur ne fait pas partie de la liste disponible dans la classe SColor. Les noms disponibles sont : blanc/white, noir/black.
	 */
	private double[] readColorName(String s) throws SReadingException
	{
		double[] tab = new double[3];
		
		String color_name = s.toLowerCase(Locale.ENGLISH);
		
		//Analyser le texte de la couleur
		if(color_name.equals("black") || color_name.equals("noir"))
		{	tab[0] = BLACK.r;	tab[1] = BLACK.g;	tab[2] = BLACK.b;	}
		else 
		if(color_name.equals("white") || color_name.equals("blanc"))
		{	tab[0] = WHITE.r;	tab[1] = WHITE.g;	tab[2] = WHITE.b;	}
		else 
		if(color_name.equals("red") || color_name.equals("rouge"))
		{	tab[0] = 1.0;	tab[1] = 0.0;	tab[2] = 0.0;	}
		else 
		if(color_name.equals("green") || color_name.equals("vert"))
		{	tab[0] = 0.0;	tab[1] = 1.0;	tab[2] = 0.0;	}
		else 
		if(color_name.equals("blue") || color_name.equals("bleu"))
		{	tab[0] = 0.0;	tab[1] = 0.0;	tab[2] = 1.0;	}
		else
			throw new SReadingException("Erreur SColor 008 : La couleur ‘" + color_name + "’ n’est pas reconnue.");
	
		return tab;
	}
	
	/**
	 * Méthode pour faire l'analyse d'un string afin d'y retourner un type double pour affecter cette valeur à un canal d'une couleur.
	 * @param s Le String à analyser.
	 * @return La valeur du String en format double.
	 * @throws SReadingException Si l'expression du String ne peut pas être convertie en type double.
	 */
	private double readChannel(String s) throws SReadingException
	{
		try{
			return Double.valueOf(s);
		}catch(NumberFormatException e){ 
			throw new SReadingException("Erreur SColor 009 : L'expression '" + s + "' n'est pas un nombre de type float pouvant être affecté à un canal r, g, b ou a d'une couleur."); 
		}	
	}
	
	/**
	 * Méthode pour écrire les paramètres rgba de la couleur dans un fichier en format txt. Le format de l'écriture est "rouge"  "vert"  "bleu"  "alpha" comme l'exemple suivant : 0.6  0.2  0.5  1.0
	 * @param bw Le buffer d'écriture.
	 * @throws IOException S'il y a une erreur avec le buffer d'écriture.
	 * @see BufferedWriter
	 */
	public void write(BufferedWriter bw)throws IOException
	{
		bw.write(toString());
	}
	
}//fin classe SColor
