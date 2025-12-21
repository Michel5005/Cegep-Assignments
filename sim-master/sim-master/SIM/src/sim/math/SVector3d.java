package sim.math;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import sim.exception.SNoImplementationException;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * <p>
 * La classe <b>SVector3d</b> repr�sente une vecteur <i>x</i>, <i>y</i> et <i>z</i> � trois dimensions. 
 * </p>
 * 
 * <p>
 * Les opérations math�matiques support�es sont les suivantes : 
 * <ul>- L'addition.</ul>
 * <ul>- La soustraction.</ul>
 * <ul>- La multiplication par un scalaire.</ul>
 * <ul>- Le produit scalaire (<i>dot product</i>).</ul>
 * <ul>- Le produit vectoriel (<i>cross product</i>).</ul>
 * <ul>- La normalisation (vecteur de module 1).</ul>
 * </p>
 * 
 * @author Simon Vezina
 * @since 2014-12-16
 * @version 2017-09-20 (labo - La loi de Coulomb v1.1)
 */
public class SVector3d implements Comparable<SVector3d> {

  //---------------
  // CONSTANTES  //
  //---------------

  /**
   * La constante 'DEFAULT_COMPONENT' correspond � la composante par d�faut des variables x,y et z du vecteur �tant �gale � {@value}.
   */
	private static final double  DEFAULT_COMPONENT = 0.0;

	/**
	 * La constant <b>ZERO</b> correspond au vecteur nul correspondant � la coordonn�e (0.0, 0.0, 0.0).
	 */
	public static final SVector3d ZERO = new SVector3d(0.0, 0.0, 0.0);
	
	/**
	 * La constant <b>UNITARY_X</b> correspond au vecteur unitaire parall�le � l'axe x correspondant � (1.0, 0.0, 0.0).
	 * On utilise �galement la notation <b><i>i</i></b> pour le repr�senter.
	 */
	public static final SVector3d UNITARY_X = new SVector3d(1.0, 0.0, 0.0);
	
	/**
   * La constant <b>UNITARY_Y</b> correspond au vecteur unitaire parall�le � l'axe y correspondant � (0.0, 1.0, 0.0).
   * On utilise �galement la notation <b><i>j</i></b> pour le repr�senter.
   */
	public static final SVector3d UNITARY_Y = new SVector3d(0.0, 1.0, 0.0);
	
	/**
   * La constant <b>UNITARY_Z</b> correspond au vecteur unitaire parall�le � l'axe z correspondant � (0.0, 0.0, 1.0).
   * On utilise �galement la notation <b><i>k</i></b> pour le repr�senter.
   */
	public static final SVector3d UNITARY_Z = new SVector3d(0.0, 0.0, 1.0);
	
	/**
   * La constante <b>ORIGIN</b> repr�sente un vecteur origine �tant situ� � la coordonn�e (0.0, 0.0, 0.0).
   * Il est �gal au vecteur <b>ZERO</b>.
   */
	public static final SVector3d ORIGIN = ZERO;
	
	//--------------
	// VARIABLES  //
	//--------------
	
	/**
	 * La variable <b>x</b> correspond � la composante x du vecteur 3d.
	 */
	private final double x;
	
	/**
   * La variable <b>y</b> correspond � la composante y du vecteur 3d.
   */
	private final double y;	
	
	/**
   * La variable <b>z</b> correspond � la composante z du vecteur 3d.
   */
	private final double z;	
	
	//-----------------
	// CONSTRUCTEURS //
	//-----------------
	/**
	 * Constructeur repr�sentant un vecteur 3d � l'origine d'un syst�me d'axe xyz.
	 */
  public SVector3d()
	{
		this(DEFAULT_COMPONENT, DEFAULT_COMPONENT, DEFAULT_COMPONENT);
	}
	
	/**
	 * Constructeur avec composante <i>x</i>,<i>y</i> et <i>z</i> du vecteur 3d.
	 * 
	 * @param x La composante <i>x</i> du vecteur.
	 * @param y La composante <i>y</i> du vecteur.
	 * @param z La composante <i>z</i> du vecteur.
	 */
	public SVector3d(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Constructeur d'un vecteur 3d en utilisant un string d�crivant les param�tres x, y et z du vecteur. 
	 * Une lecture autoris�e peut prendre la forme suivante : "double x" "double y" "double z" (incluant la notation avec , ( ) [ ] comme d�limieur dans l'expression du string comme par exemple (2.3, 4.3, 5.7) ).
	 * 
	 * @param string - Le string de l'expression du vecteur en param�tres x, y, et z.
	 * @throws SReadingException Si le format de la lecture n'est pas ad�quat.
	 */
	public SVector3d(String string)throws SReadingException
	{
		double[] tab = read(string);
		
		x = tab[0];
		y = tab[1];
		z = tab[2];
	}
	
	//------------
	// M�THODES //
	//------------
	
	/**
	 * M�thode qui donne acc�s � la coordonn�e x du vecteur.
	 * 
	 * @return La coordonn�e x.
	 */
	public double getX()
	{ 
	  return x;
	}
	
	/**
	 * M�thode qui donne acc�s � la coordonn�e y du vecteur.
	 * 
	 * @return La coordonn�e y.
	 */
	public double getY()
	{ 
	  return y;
	}
	
	/**
	 * M�thode qui donne acc�s � la coordonn�e z du vecteur.
	 * 
	 * @return La coordonn�e z.
	 */
	public double getZ()
	{ 
	  return z;
	}
	
	/**
	 * M�thode qui calcule <b>l'addition</b> de deux vecteurs. 
	 * 
	 * @param v Le vecteur � ajouter.
	 * @return La somme des deux vecteurs.
	 */
	public SVector3d add(SVector3d v)
	{	
		SVector3d somme = new SVector3d (x + v.x , y + v.y , z + v.z); 
	  return somme;
	}
	
	/**
	 * M�thode qui calcul la <b>soustraction</b> de deux vecteurs. 
	 * 
	 * @param v - Le vecteur � soustraire au vecteur pr�sent.
	 * @return La soustraction des deux vecteurs.
	 */
	public SVector3d substract(SVector3d v)
	{
		SVector3d soustraction = new SVector3d (x - v.x , y - v.y , z - v.z); 
		  return soustraction;
	}
	
	/**
	 * M�thode qui effectue la <b>multiplication d'un vecteur par une scalaire</b>.
	 * 
	 * @param m Le scalaire.
	 * @return La multiplication d'un vecteur par un scalaire.
	 */
	public SVector3d multiply(double m)
	{
		SVector3d produit= new SVector3d (m*x, m*y, m*z);
    return produit;
	}
	
	/**
	 * M�thode pour obtenir le <b>module</b> d'un vecteur.
	 * 
	 * @return Le module du vecteur.
	 */
	public double modulus()
	{
	double module = Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2) + Math.pow(this.getZ(), 2)); 
		
    return module;
	}
	
	/**
	 * M�thode pour <b>normaliser</b> un vecteur � trois dimensions. 
	 * Un vecteur normalis� poss�de la m�me orientation, mais poss�de une <b> longeur unitaire </b>.
	 * Si le <b>module du vecteur est nul</b>, le vecteur normalis� sera le <b> vecteur nul </b> (0.0, 0.0, 0.0).
	 * 
	 * @return Le vecteur normalis�.
	 * @throws SImpossibleNormalizationException Si le vecteur ne peut pas �tre normalis� �tant trop petit (modulus() <  SMath.EPSILON) ou de longueur nulle.
	 */
	public SVector3d normalize() throws SImpossibleNormalizationException
	{
	  throw new SNoImplementationException("La m�thode n'est pas impl�ment�e.");
   
	}
	
	/**
	 * M�thode pour effectuer le <b>produit scalaire</b> entre deux vecteurs.
	 * 
	 * @param v Le vecteur � mettre en produit scalaire.
	 * @return Le produit scalaire entre les deux vecteurs.
	 */
	public double dot(SVector3d v)
	{
	  throw new SNoImplementationException("La m�thode n'est pas impl�ment�e.");
   
	}
	
	/**
	 * M�thode pour effectuer le <b>produit vectoriel</b> avec un autre vecteur v.
	 * <p>
	 * Cette version du produit vectoriel est impl�ment� en respectant la <b> r�gle de la main droite </b>.
	 * Il est important de rappeler que le produit vectoriel est <b> anticommutatif </b> (AxB = -BxA) et que l'ordre des vecteurs doit �tre ad�quat pour obtenir le r�sultat d�sir�.
	 * Si l'un des deux vecteurs est <b> nul </b> ou les deux vecteurs sont <b> parall�les </b>, le r�sultat sera un <b> vecteur nul </b>.
	 * </p>
	 * 
	 * @param v Le second vecteur dans le produit vectoriel.
	 * @return Le r�sultat du produit vectoriel.
	 */
	public SVector3d cross(SVector3d v)
	{
	  throw new SNoImplementationException("La m�thode n'est pas impl�ment�e.");
    
	}

	
		
	/**
	 * M�thode pour obtenir un vecteur avec les coordonn�e (x,y,z) les plus petites (en consid�rant le signe) parmi un ensemble de vecteurs. 
	 * 
	 * @param value_list La liste des vecteurs � analyser.
	 * @return Un vecteur ayant comme coordonn�e les plus petites valeurs (x,y,z) trouv�es.
	 */
	public static SVector3d findMinValue(List<SVector3d> value_list)
  {
	  return findMinValue(value_list.toArray(new SVector3d[value_list.size()]));
  }
	
	/**
	 * M�thode pour obtenir un vecteur avec les coordonn�e (x,y,z) les plus petites (en consid�rant le signe) parmi un ensemble de vecteurs. 
	 * 
	 * @param tab - Le tableau des vecteurs � analyser.
	 * @return Un vecteur ayant comme coordonn�e les plus petites valeurs (x,y,z) trouv�es.
	 */
	public static SVector3d findMinValue(SVector3d[] tab)
	{
	  double x_min = tab[0].getX();
	  
	  for(int i = 1; i < tab.length; i++)
	    if(x_min > tab[i].getX())
	      x_min = tab[i].getX();
	  
	  double y_min = tab[0].getY();
    
    for(int i = 1; i < tab.length; i++)
      if(y_min > tab[i].getY())
        y_min = tab[i].getY();
    
    double z_min = tab[0].getZ();
    
    for(int i = 1; i < tab.length; i++)
      if(z_min > tab[i].getZ())
        z_min = tab[i].getZ();
    
    return new SVector3d(x_min, y_min, z_min);
	}
	
	/**
   * M�thode pour obtenir un vecteur avec les coordonn�e (x,y,z) les plus grandes (en consid�rant le signe) parmi un ensemble de vecteurs. 
   * 
   * @param value_list La liste des vecteurs � analyser.
   * @return Un vecteur ayant comme coordonn�e les plus grandes valeurs (x,y,z) trouv�es.
   */
  public static SVector3d findMaxValue(List<SVector3d> value_list)
  {
    return findMaxValue(value_list.toArray(new SVector3d[value_list.size()]));
  }
  
	/**
   * M�thode pour obtenir un vecteur avec les coordonn�e (x,y,z) les plus grandes (en consid�rant le signe) parmi un ensemble de vecteurs. 
   * 
   * @param tab - Le tableau des vecteurs � analyser.
   * @return Un vecteur ayant comme coordonn�e les plus grandes valeurs (x,y,z) trouv�es.
   */
  public static SVector3d findMaxValue(SVector3d[] tab)
  {
    double x_max = tab[0].getX();
    
    for(int i = 1; i < tab.length; i++)
      if(x_max < tab[i].getX())
        x_max = tab[i].getX();
    
    double y_max = tab[0].getY();
    
    for(int i = 1; i < tab.length; i++)
      if(y_max < tab[i].getY())
        y_max = tab[i].getY();
    
    double z_max = tab[0].getZ();
    
    for(int i = 1; i < tab.length; i++)
      if(z_max < tab[i].getZ())
        z_max = tab[i].getZ();
    
    return new SVector3d(x_max, y_max, z_max);
  }
  
	/**
	 * M�thode pour �crire les param�tres xyz du vecteur dans un fichier en format txt. Le format de l'�criture est "x"  "y"  "z" comme l'exemple suivant : 0.6  0.2  0.5
	 * 
	 * @param bw Le buffer d'�criture.
	 * @throws IOException S'il y a une erreur avec le buffer d'�criture.
	 * @see BufferedWriter
	 */
	public void write(BufferedWriter bw)throws IOException
	{
		bw.write(toString());
	}
	
	

	//----------------------------------------------------------------------------------------------
	//M�thodes pour calcul sp�cialis� (afin de r�duire l'allocation de m�moire lors des calculs) 
	//----------------------------------------------------------------------------------------------
 
  /**
   * M�thode pour obtenir la distance entre deux points.
   * 
   * @param A Le premier point.
   * @param B Le deuxi�me point.
   * @return La distance entre le point A et B.
   */
  public static double distance(SVector3d A, SVector3d B)
  {
    throw new SNoImplementationException("La m�thode n'est pas impl�ment�e.");
   
  }
  
  /**
   * M�thode permettant d'obtenir l'angle entre deux vecteurs A et B. L'angle sera obtenu en radian.
   * 
   * @param A Le premier vecteur.
   * @param B Le second vecteur. 
   * @return L'angle entre les deux vecteurs.
   */
  public static double angleBetween(SVector3d A, SVector3d B)
  {
    throw new SNoImplementationException("La m�thode n'est pas impl�ment�e.");
    
  }
  
  /**
   * M�thode pour effectuer la <b>soustraction entre deux produits scalaires</b>. Cette op�ration vectorielle est �quivalente � l'expression
   * <ul>(A - B) . C = A . C - B . C </ul>
   * @param A - Le vecteur A de l'expression.
   * @param B - Le vecteur B de l'expression.
   * @param C - Le vecteur C de l'expression.
   * @return Le scalaire repr�sentant la solution de l'�quation.
   */
  public static double AdotCsubstractBdotC(SVector3d A, SVector3d B, SVector3d C)
  {
    throw new SNoImplementationException("La m�thode n'est pas impl�ment�e.");
    
  }
 
 /**
  * M�thode qui effectue le <b>produit mixte</b> de trois vecteurs A, B et C. Cette op�ration vectorielle est �quivalente � l'expression
  * <ul>(A x B) . C = (Ax*By*Cz + Bx*Cy*Az + Cx*Ay*Bz) - (Az*By*Cx + Bx*Cz*Ay + Cy*Ax*Bz)</ul>
  * Il est � remarquer que si deux des trois vecteurs sont �gaux ou colin�aires, le produit mixte est nul.
  * @param A - Le 1ier vecteur dans le produit mixte.
  * @param B - Le 2i�me vecteur dans le produit mixte.
  * @param C - Le 3i�me vecteur dans le produit mixte.
  * @return Le scalaire r�sultant du produit mixte.
  */
  public static double AcrossBdotC(SVector3d A, SVector3d B, SVector3d C)
  {
    throw new SNoImplementationException("M�thode non impl�ment�e.");
  }
 
 /**
  * M�thode qui effectue le <b>triple produit vectoriel</b> de trois vecteurs A, B et C. Cette op�ration vectorielle est �quivalente � l'expression
  * <ul>(A x B) x C = (A . C) B - (B . C) A</ul>  
  * <p>Il est important de pr�ciser que les deux expressions
  * <ul>(A x B) x C != A x (B x C)</ul>
  * ne sont pas �gaux. L'ordre de priorit� des opérations doit �tre bien d�fini.</p>
  *  
  * @param A - Le 1ier vecteur dans le double produit vectoriel.
  * @param B - Le 2i�me vecteur dans le double produit vectoriel.
  * @param C - Le 3i�me vecteur dans le double produit vectoriel.
  * @return Le vecteur r�sultant du produit mixte.
  */
 public static SVector3d AcrossBcrossC(SVector3d A, SVector3d B, SVector3d C)
 {
   throw new SNoImplementationException("La m�thode n'est pas impl�ment�e.");
   
 }
 
 /**
  * <p>M�thode qui effectue le <b>triple produit vectoriel</b> de trois vecteurs A, B et C avec l'ordre de priorit�</p>
  * <ul>D = A x (B x C)</ul>
  * <p>o� D est le r�sultat du triple produit vectoriel. Cette op�ration vectorielle est �quivalente � l'expression</p>
  * <ul>A x (B x C) = (A . C) B - (A . B) C</ul>  
  * <p>et il est important de pr�ciser que les deux expressions</p>
  * <ul>A x (B x C) != (A x B) x C</ul>
  * <p>ne sont pas �gaux puisque le <b>produit vectoriel n'est pas commutatif</b>.</p>
  *  
  * @param A - Le 1ier vecteur dans le double produit vectoriel.
  * @param B - Le 2i�me vecteur dans le double produit vectoriel.
  * @param C - Le 3i�me vecteur dans le double produit vectoriel.
  * @return Le vecteur r�sultant du triple produit vectoriel.
  */
 public static SVector3d Across_BcrossC(SVector3d A, SVector3d B, SVector3d C)
 {
   throw new SNoImplementationException("La m�thode n'est pas impl�ment�e.");
   
 }  
 
 /**
  * <p>M�thode qui effectue le <b>triple produit vectoriel</b> de trois vecteurs A, B et C avec l'ordre de priorit�</p>
  * <ul>D = (A x B) x C</ul>
  * <p>o� D est le r�sultat du triple produit vectoriel. Cette op�ration vectorielle est �quivalente � l'expression</p>
  * <ul>(A x B) x C = ?????</ul>  
  * <p>et il est important de pr�ciser que les deux expressions</p>
  * <ul>(A x B) x C != A x (B x C)</ul>
  * <p>ne sont pas �gaux puisque le <b>produit vectoriel n'est pas commutatif</b>.</p>
  *  
  * @param A - Le 1ier vecteur dans le double produit vectoriel.
  * @param B - Le 2i�me vecteur dans le double produit vectoriel.
  * @param C - Le 3i�me vecteur dans le double produit vectoriel.
  * @return Le vecteur r�sultant du triple produit vectoriel.
  */
 public static SVector3d AcrossB_crossC(SVector3d A, SVector3d B, SVector3d C)
 {
   throw new SNoImplementationException("M�thode non impl�ment�e.");
 }
 
 /**
  * M�thode qui effectue une op�ration sp�cialis�e de multiplication par un scalaire et d'addition vectorielle �quivalente �
  * <ul>V = a*B + C.</ul>
  * @param a - Le scalaire � multiplier avec B.
  * @param B - Le 1ier vecteur dans l'expression vectorielle.
  * @param C - Le 2i�me vecteur dans l'expression vectorielle � ajouter.
  * @return Le vecteur r�sultant de cette op�ration sp�cialis�e.
  */
 public static SVector3d AmultiplyBaddC(double a, SVector3d B, SVector3d C)
 {
   throw new SNoImplementationException("La m�thode n'est pas impl�ment�e.");
   
 }

 //------------------------
 // M�THODES UTILITAIRES //
 //------------------------
 /**
  * M�thode utilisant un string comme param�tre pour d�finir les composantes x, y et z du vecteur. 
  * Une lecture autoris�e peut prendre la forme suivante : "double x" "double y" "double z" 
  * (en incluant la notation avec , ( ) [ ] < > comme d�limieur dans l'expression du string comme par exemple (2.3, 4.3, 5.7) ).
  *  
  * @param string - Le string de l'expression du vecteur en param�tres x, y, et z.
  * @return un tableau de trois �l�ments tel que x = [0], y = [1] et z = [2]. 
  * @throws SReadingException Si le format de lecture n'est pas ad�quat.
  */
 private double[] read(String string)throws SReadingException
 {
   StringTokenizer tokens = new StringTokenizer(string, SStringUtil.REMOVE_CARACTER_TOKENIZER);  
   
   if(tokens.countTokens() != 3)
     throw new SReadingException("Erreur SVector3d 003 : L'expression '" + string + "' ne contient pas exactement 3 param�tres pour les composantes xyz du vecteur 3d.");
   else
   {
     String s = "";          //String � convertir en double pour les composantes x, y et z.
     String comp = "";       //Nom de la composante en lecture utilis�e pour l'envoie d'une Exception s'il y a lieu.
     
     try
     {
       double[] tab = new double[3]; //Tableau des 3 composantes
       
       comp = "x";
       s = tokens.nextToken();
       tab[0] = Double.valueOf(s);
       
       comp = "y";
       s = tokens.nextToken();
       tab[1] = Double.valueOf(s);
       
       comp = "z";
       s = tokens.nextToken();
       tab[2] = Double.valueOf(s);
       
       return tab;
       
     }catch(NumberFormatException e){ 
       throw new SReadingException("Erreur SVector3d 004 : L'expression '" + s +"' n'est pas valide pour d�finir la composante '" + comp + "' du vecteur en cours.");
     }
   } 
 }

 //----------------------
 // M�THODE OVERLOADED //
 //----------------------
 
 @Override
 public String toString()
 {
   return "[" + x + ", " + y + ", " + z + "]";   
 }

 @Override
 public int hashCode()
 {
   //hashcode autog�n�r� par Eclipse avec les param�tres x, y et z
   final int prime = 31;
   int result = 1;
   long temp;
   temp = Double.doubleToLongBits(x);
   result = prime * result + (int) (temp ^ (temp >>> 32));
   temp = Double.doubleToLongBits(y);
   result = prime * result + (int) (temp ^ (temp >>> 32));
   temp = Double.doubleToLongBits(z);
   result = prime * result + (int) (temp ^ (temp >>> 32));
   return result;
 }

 @Override
 public boolean equals(Object obj)
 {
   if(this == obj)
     return true;
   
   if(obj == null)
     return false;
   
   if(!(obj instanceof SVector3d))
     return false;
   
   SVector3d other = (SVector3d) obj;
   
   //Comparaison des valeurs x,y et z en utilisant la m�thode de comparaison de SMath
   if(!SMath.nearlyEquals(x, other.x))
     return false;
   
   if(!SMath.nearlyEquals(y, other.y))
     return false;
   
   if(!SMath.nearlyEquals(z, other.z))
     return false;
   
   return true;
 }
 
 @Override
 public int compareTo(SVector3d o) 
 {
   // Variable d�terminant la comparaison 
   // ( comp < 0 ==> plus petit )
   // ( comp > 0 ==> plus grand )
   // ( comp = 0 ==> �gal )
   int comp;
   
   // Comparaison sur les x
   comp= Double.compare(this.x, o.x);
   
   if(comp != 0)
     return comp;
   
   // Comparaison sur les y
   comp= Double.compare(this.y, o.y);
   
   if(comp != 0)
     return comp;
   
   // Comparaison sur les z
   return Double.compare(this.z, o.z);  
 }
 
}//fin de la classe SVector3d
