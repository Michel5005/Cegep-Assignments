/**
 * 
 */
package circuit.kirchhof;

import java.util.Arrays;

import circuit.kirchhof.exception.*;


/**
 * La classe <b>SMath</b> contient des m�thodes de calcul qui sont compl�mentaire � la classe java.lang.Math. 
 * Elle perment entre autre d'�valuer les racines r�elles d'un polyn�me de degr� 1, 2, 3 et 4.
 * 
 * @author Simon V�zina
 * @since 2015-02-19
 * @version 2017-06-12 (labo v1.0)
 */
public final class SMath {

	/**
	 * <p>
	 * La constante <b>EPSILON</b> repr�sentante un nombre tr�s petit, mais non nul. Ce chiffre peut �tre utilis� 
	 * pour comparer une valeur de type double avec le chiffre z�ro. Puisqu'un double �gale � z�ro
	 * est difficile � obtenir num�riquement apr�s un calcul (sauf si l'on multiplie par z�ro), il est pr�f�rable de 
	 * comparer un "pseudo z�ro" avec cette constante.
	 * </p>
	 * <p>
	 * Avec une valeur de EPSILON = 1e-10, cette valeur permet de comparer ad�quatement des nombres autour de '1' avec suffisamment de chiffres significatifs.
	 * </p>
	 */
	public static double EPSILON = 1e-10;           
	
	/**
   * La constante <b>NEGATIVE_EPSILON</b> repr�sentante un nombre tr�s petit, mais non nul qui est <b>negatif</b>. Ce chiffre peut �tre utilis� 
   * pour comparer une valeur arbiraire de type double avec le chiffre z�ro, mais qui sera n�gatif. Puisqu'un double �gale � z�ro
   * est difficile � obtenir num�riquement apr�s un calcul (sauf si l'on multiplie par z�ro), il est pr�f�rable de 
   * comparer un "pseudo z�ro" avec cette constante.
   */
	public static double NEGATIVE_EPSILON = -1.0*EPSILON;  
	
	/**
	 * La constante <b>ONE_PLUS_EPSILON</b> repr�sente une constante �gale � <b>1 + EPSILON</b> ce qui correspond � un nombre l�g�rement sup�rieur � 1.
	 */
	public static double ONE_PLUS_EPSILON = 1 + EPSILON;
	
	/**
	 * La constante <b>ONE_MINUS_EPSILON</b> repr�sente une constant �gale � <b>1 - EPSILON</b> ce qui correspond � un nombre l�g�rement inf�rieur � 1.
	 */
	public static double ONE_MINUS_EPSILON = 1 - EPSILON;
	
	/**
   * La constante <b>ONE_PLUS_1000EPSILON</b> repr�sente une constante �gale � <b>1 + 1000*EPSILON</b> ce qui correspond � un nombre l�g�rement sup�rieur � 1.
   */
  public static double ONE_PLUS_1000EPSILON = 1 + 1000*EPSILON;
  
  /**
   * La constante <b>ONE_MINUS_EPSILON</b> repr�sente une constant �gale � <b>1 - 1000*EPSILON</b> ce qui correspond � un nombre l�g�rement inf�rieur � 1.
   */
  public static double ONE_MINUS_1000EPSILON = 1 - 1000*EPSILON;
  
	/**
	 * La constante <b>INFINITY</b> repr�sente un nombre positif �gale � l'infini. Cette valeur est obtenue �
	 * partir de la classe java.lang.Double.
	 * @see java.lang.Double
	 */
	public static double INFINITY = Double.POSITIVE_INFINITY;
	
	/**
   * M�thode pour d�terminer si deux nombres de type double sont <b>relativement �gaux</b>. 
   * En utilisant une approche de calcul de diff�rence, on v�rifie si
   * <ul>a - b < EPSILON*ref</ul>  
   * afin de <b>valid� l'�galit�</b> entre a et b (a == b). EPSILON est un seuil de pr�cision 
   * et ref est une base de r�f�rence (la valeur absolue la plus �lev�e parmis a et b). 
   * <p>Cependant, si les deux chiffres sont inf�rieurs � EPSILON, ils seront consid�r�s comme �gaux.</p>
   * 
   * @param a - Le 1ier nombre � comparer.
   * @param b - Le 2i�me nombre � comparer.
   * @return <b>true</b> si les deux nombres sont <b>relativement �gaux</b> et <b>false</b> sinon.
   */
	public static boolean nearlyEquals(double a, double b)
	{
	  return nearlyEquals(a, b, EPSILON);
	}
	
	/**
	 * M�thode pour d�terminer si deux nombres de type double sont <b>relativement �gaux</b>. 
	 * En utilisant une approche de calcul de diff�rence, on v�rifie si
	 * <ul>a - b < EPSILON*ref</ul>  
	 * afin de <b>valid� l'�galit�</b> entre a et b (a == b). EPSILON est un seuil de pr�cision 
	 * et ref est une base de r�f�rence (la valeur absolue la plus �lev�e parmis a et b).
	 * <p>Cenpendant, si les deux chiffres sont inf�rieurs � EPSILON, ils seront consid�r�s comme �gaux.</p>
	 * 
	 * @param a - Le 1ier nombre � comparer.
	 * @param b - Le 2i�me nombre � comparer.
	 * @param epsilon - La pr�cision acceptable.
	 * @return <b>true</b> si les deux nombres sont <b>relativement �gaux</b> et <b>false</b> sinon.
	 */
	public static boolean nearlyEquals(double a, double b, double epsilon)
	{
	  double absA = Math.abs(a);
    double absB = Math.abs(b);
    double diff = Math.abs(a - b);
    
    // V�rification du cas particulier : 0 = 0 et infiny = infiny (mais pas certain ...) 
    if(a == b)                            
      return true;
     
    // Cas des petites chiffres : V�rifier si les deux chiffres sont tr�s pr�s l'un de l'autre
    if(diff < epsilon)  
      return true;
      
    // Cas g�n�ral
    double positive_max;
        
    if(absA > absB)
      positive_max = absA;
    else
      positive_max = absB;
      
    if(diff < positive_max*epsilon)
      return true;
    else
      return false;
	}
	
	/**
	 * M�thpde pour d�terminer si une valeur est relativement pr�s de z�ro.
	 * Cette m�thode est n�cessaire, car une op�ration math�matique menant au chiffre 0 peut �tre 0.0 et -0.0 ce qui n'est pas �gal selon JAVA.
	 * 
	 * @param value La valeur � comparer avec 0.
	 * @return <b>true</b> si la valeur est <b>relativement �gal</b> � z�ro et <b>false</b> sinon.
	 */
	public static boolean nearlyZero(double value)
	{
	  return nearlyEquals(value, 0.0);
	}
	
	/**
	 * M�thode pour d�terminer si deux tableau de nombres de type double sont <b>relativement �gaux</b>. 
   * En utilisant une approche de calcul de diff�rence, on v�rifie si pour chaque �l�ment du tableau
   * <ul>a - b < EPSILON*ref</ul>  
   * afin de <b>valid� l'�galit�</b> entre a et b (a == b). EPSILON est un seuil de pr�cision 
   * et ref est une base de r�f�rence (la valeur absolue la plus �lev�e parmis a et b).
   * <p>Cenpendant, si les deux chiffres sont inf�rieurs � EPSILON, ils seront consid�r�s comme �gaux.</p>
   * 
	 * @param tab1 Le premier tableau � comparer.
	 * @param tab2 Le deuxi�me tableau � comparer.
	 * @param epsilon La pr�cision acceptable.
	 * @return <b>true</b> si les deux tableaux sont <b>relativement �gaux</b> et <b>false</b> sinon.
	 */
	public static boolean nearlyEquals(double[] tab1, double[] tab2, double epsilon)
	{
	  // V�rifier que les deux tableaux ont la m�me taille.
	  if(tab1.length != tab2.length)
	    return false;
	  
	  // V�rifier que l'ensemble des donn�e indexe par indexe sont "nearlyEquals".
	  for(int i = 0; i < tab1.length; i++)
	    if(!nearlyEquals(tab1[i], tab2[i], epsilon))
	      return false;
	  
	  // Puisque l'ensemble des valeurs indexe par indexe sont "nearlyEquals", le tableau doit l'�tre. 
	  return true;
	}
	
	/**
	 * M�thode permettant d'�valuer la racine r�elle d'un polyn�me de degr� '1' de la forme
	 * <ul>Ax + B = 0.</ul>
	 * <p>Un polyn�me de degr� '1' poss�de au maximum <b>une</b> racine r�elle.</p>
	 *  
	 * @param A - Le coefficient devant le terme de puissance '1' (x).
	 * @param B - Le coefficient devant le terme de puissance '0' (1).
	 * @return La racine r�elle d'un polyn�me de degr� '1' (s'il y en a).
	 */
	public static double[] linearRealRoot(double A, double B)
	{
	  // V�rifier si le polyn�me n'est pas d'un degr� inf�rieur.
	  // Dans ce cas, la fonction serait constante, donc avec aucune racine possible.
	  if(A == 0.0)
	    return new double[0];
	 
	  throw new SNoImplementationException("Erreur SMath : C'est m�thode n'a pas �t� impl�ment�e.");
	}
	
	/**
   * M�thode permettant d'�valuer les racines r�elles d'un polyn�me de degr� '2' de la forme
   * <ul>Ax^2 + Bx + C = 0.</ul>
   * <p>Un polyn�me de degr� '2' poss�de au maximum <b>deux</b> racines r�elles.</p>
   *  
   * @param A - Le coefficient devant le terme de puissance '2' (x^2).
   * @param B - Le coefficient devant le terme de puissance '1' (x).
   * @param C - Le coefficient devant le terme de puissance '0' (1).
   * @return Les racines r�elles de d'un polyn�me de degr� '2' (s'il y en a). Les solutions retourn�es dans un tableau sont <b>tri�es en ordre croissant</b>. 
   */
	public static double[] quadricRealRoot(double A, double B, double C)
	{
	  // V�rifier si le polyn�me n'est pas d'un degr� inf�rieur
	  if(A == 0.0)
	    return linearRealRoot(B,C);
	  
	  throw new SNoImplementationException("Erreur SMath : C'est m�thode n'a pas �t� impl�ment�e.");
	}
	
	/**
   * M�thode permettant d'�valuer les racines r�elles d'un polyn�me de degr� '3' de la forme
   * <ul>Ax^3 + Bx^2 + Cx + D = 0.</ul>
   * <p>Un polyn�me de degr� '3' poss�de au maximum <b>trois</b> racines r�elles.</p>
   *  
   * @param A - Le coefficient devant le terme de puissance '3' (x^3).
   * @param B - Le coefficient devant le terme de puissance '2' (x^2).
   * @param C - Le coefficient devant le terme de puissance '1' (x).
   * @param D - Le coefficient devant le terme de puissance '0' (1).
   * @return Les racines r�elles de d'un polyn�me de degr� '3' (s'il y en a). Les solutions retourn�es dans un tableau sont <b>tri�es en ordre croissant</b>. 
   */
  public static double[] cubicRealRoot(double A, double B, double C, double D)
  {
    // V�rifier si le polyn�me n'est pas d'un degr� inf�rieur
    if(A == 0.0)
      return quadricRealRoot(B,C,D);
    
    throw new SNoImplementationException("Erreur SMath : C'est m�thode n'a pas �t� impl�ment�e.");
  }
  
	/**
   * M�thode permettant d'�valuer les racines r�elles d'un polyn�me de degr� '4' de la forme
   * <ul>Ax^4 + Bx^3 + Cx^2 + Dx + E = 0.</ul>
   * <p>Un polyn�me de degr� '4' poss�de au maximum <b>quatre</b> racines r�elles.</p>
   *  
   * @param A - Le coefficient devant le terme de puissance '4' (x^4).
   * @param B - Le coefficient devant le terme de puissance '3' (x^3).
   * @param C - Le coefficient devant le terme de puissance '2' (x^2).
   * @param D - Le coefficient devant le terme de puissance '1' (x).
   * @param E - Le coefficient devant le terme de puissance '0' (1).
   * @return Les racines r�elles de d'un polyn�me de degr� '4' (s'il y en a). Les solutions retourn�es dans un tableau sont <b>tri�es en ordre croissant</b>. 
   */
	public static double[] quarticRealRoot(double A, double B, double C, double D, double E)
	{
	  // V�rifier si le polyn�me n'est pas d'un degr� inf�rieur.
	  if(A == 0.0)
	    return cubicRealRoot(B,C,D,E);
	  
	  throw new SNoImplementationException("Erreur SMath : C'est m�thode n'a pas �t� impl�ment�e.");
	}
	
	/**
   * M�thode qui effectue le calcul inverse de l'interpolation lin�aire d'une valeur num�rique.
   * Cela sigifie que l'on cherche la valeur du param�tre d'interpolation t � partir d'une valeur interpol�e
   * ainsi que des deux valeurs extr�mes.
   * 
   * @param v La valeur interpol�e dont la valeur de t doit �tre calcul�e.
   * @param v0 La valeur de r�f�rence pond�r�e par 1 - t.
   * @param v1 La valeur de r�f�rence pond�r�e par le facteur t.
   * @return La facteur t d'interpolation lin�aire.
   */
  public static double reverseLinearInterpolation(double v, double v0, double v1) 
  {
    throw new SNoImplementationException("Erreur SMath : C'est m�thode n'a pas �t� impl�ment�e.");
  }
  
  /**
   * M�thode qui effectue le calcul de l'interpolation lin�aire d'une valeur num�rique.
   * 
   * @param v0 La valeur de r�f�rence pond�r�e par 1 - t.
   * @param v1 La valeur de r�f�rence pond�r�e par le facteur t.
   * @param t Le param�tre de pond�ration.
   * @return La valeur interpol�e.
   * @throws SRuntimeException Si la contrainte sur t n'est pas respect�e (0 <= t <= 1).
   */
  public static double linearInterpolation(double v0, double v1, double t) throws SRuntimeException
  {
    // Doit satisfaire la contrainte sur t
    if(t < 0.0 || t > 1.0)
      throw new SRuntimeException("Erreur SMath 002 : Le param�tre d'interpolation t = " + t + " n'est pas compris entre 0 et 1.");
    
    throw new SNoImplementationException("Erreur SMath : C'est m�thode n'a pas �t� impl�ment�e.");
  }
  
  /**
   * ...
   * 
   * @param array
   * @return
   */
  public static double strategicArraySum(double[] array)
  {
    // M�thode #1 : Additionner les termes petits ensemble
    double[] tab_positive = new double[array.length];
    double[] tab_negative = new double[array.length];
    
    int index_positive = 0;
    int index_negative = 0;
    
    // Trier les valeurs positives et n�gatives
    for(int i = 0; i < array.length; i++)
    {
      if(array[i] < 0)
      {
        tab_negative[index_negative] = array[i];
        index_negative++;
      }
      else
      {
        tab_positive[index_positive] = array[i];
        index_positive++;
      }
    }
    
    // Ordonner les valeus en ordre croisssant
    Arrays.sort(tab_negative);    // tous les n�gatifs gros sont au d�but du tableau
    Arrays.sort(tab_positive);    // tous les z�ros non affect� sont au d�but du tableau
    
    // Faire la somme des nombres n�gatifs
    double negative_sum = 0.0;
    
    for(int i = index_negative-1; i >= 0; i--)
      negative_sum += tab_negative[i];
      
    // Faire la somme des nombre positifs
    double positive_sum = 0.0;
    
    for(int i = tab_positive.length - index_positive; i < tab_positive.length; i++)
      positive_sum += tab_positive[i];
    
    // Retourner la somme des positifs avec les n�gatifs
    return positive_sum + negative_sum;
  }
  
  /**
   * M�thode d�terminant le signe d'un nombre. Les r�sultats sont
   * <ul> -1 si a < 0</ul>
   * <ul> 0 si a = 0</ul>
   * <ul> 1 si a > 0</ul>
   * @param a Le nombre.
   * @return Le signe du nombre.
   */
  public static double sgn(double a)
  {
    if(a > 0)
      return 1.0;
    else
      if(a < 0)
        return -1.0;
      else
        return 0.0;
  }
  
  /**
   * M�thode pour obtenir la <b>plus grande valeur</b> d'un tableau.
   * 
   * @param tab Le tableau.
   * @return La plus grande valeur du tableau.
   */
  public static int findMax(int[] tab)
  {
    int max = tab[0]; 
    
    for(int v : tab)
      if(v > max)
        max = v;
    
    return max;
  }
  
  /**
   * M�thode pour obtenir la <b>plus grande valeur</b> d'un tableau.
   * 
   * @param tab Le tableau.
   * @return La plus grande valeur du tableau.
   */
  public static double findMax(double[] tab)
  {
    double max = tab[0]; 
    
    for(double v : tab)
      if(v > max)
        max = v;
    
    return max;
  }
  
  /**
   * M�thode pour obtenir la <b>plus petite valeur</b> d'un tableau.
   * @param tab Le tableau.
   * @return La plus petite valeur du tableau.
   */
  public static int findMin(int[] tab)
  {
    int min = tab[0]; 
    
    for(int v : tab)
      if(v < min)
        min = v;
    
    return min;
  }
  
  /**
   * M�thode pour obtenir la <b>plus petite valeur</b> d'un tableau.
   * @param tab Le tableau.
   * @return La plus petite valeur du tableau.
   */
  public static double findMin(double[] tab)
  {
    double min = tab[0]; 
    
    for(double v : tab)
      if(v < min)
        min = v;
    
    return min;
  }
  
  /**
   * M�thode pour convertir un tableau d'entier vers un tableau de nombre r�el entre une valeur minimale et maximale.
   * La correspondance entre les valeurs converties sera lin�aire.
   * 
   * @param data Les entiers � convertir.
   * @param min La borne minimale.
   * @param max La borne maximale.
   * @return Le tableau converti.
   * @throws SRuntimeException Si les bornes sont mal d�finies.
   */
  public static double[] mappingIntToDouble(int[] data, double min, double max) throws SRuntimeException
  {
    if(min > max)
      throw new SRuntimeException("Erreur SMath 003 : La borne minimale " + min + " et la borne maximale " + max + " sont mal d�finies.");
    
    int min_value = SMath.findMin(data);
    int max_value = SMath.findMax(data);
    
    double[] result = new double[data.length];
    
    // It�rer sur l'ensemble des �l�ments du tableau.
    for(int i = 0; i < result.length; i++)
    {
      double t = SMath.reverseLinearInterpolation((double)data[i], (double)min_value, (double)max_value);
      result[i] = SMath.linearInterpolation(min, max, t);
    }
    
    return result;
  }
  
  /**
   * M�thode pour convertir un tableau de nombre r�el vers un tableau d'entier entre une valeur minimale et maximale.
   * La correspondance entre les valeurs converties sera lin�aire.
   * 
   * @param data Les donn�es � convertir.
   * @param min La borne minimale.
   * @param max La borne maximale.
   * @return Le tableau converti.
   * @throws SRuntimeException Si les bornes sont mal d�finies.
   */
  public static int[] mappingDoubleToInt(double[] data, int min, int max)
  {
    if(min > max)
      throw new SRuntimeException("Erreur SMath 004 : La borne minimale " + min + " et la borne maximale " + max + " sont mal d�finies.");
    
    double min_value = SMath.findMin(data);
    double max_value = SMath.findMax(data);
    
    int[] result = new int[data.length];
    
    // It�rer sur l'ensemble des �l�ments du tableau.
    for(int i = 0; i < result.length; i++)
    {
      double t = SMath.reverseLinearInterpolation(data[i], min_value, max_value);
      result[i] = (int)SMath.linearInterpolation(min, max, t);
    }
    
    return result;
  }
  
}//fin de la classe SMath
