/**
 * 
 */
package sim.math;

import sim.exception.SIllegalNegativeValueException;
import sim.exception.SNaNValueException;
import sim.exception.SNoImplementationException;
import sim.exception.SRuntimeException;

/**
 * La classe <b>SMath</b> contient des méthodes de calcul qui sont complémentaire à la classe java.lang.Math. 
 * Elle perment entre autre d'évaluer les racines réelles d'un polynôme de degré 1, 2, 3 et 4.
 * 
 * @author Simon Vézina
 * @since 2015-02-19
 * @version 2022-06-02 (version labo – Le champ électrique v1.1.0)
 */
public final class SMath {

  /**
   * La constante <b>NO_ROOT_SOLUTION</b> représente le tableau des solutions au racine d'un polynôme où il n'y a <u>pas de solution réelle</u>.
   * Le tableau est donc vide et de taille égale à zéro.
   */
  public final static double[] NO_ROOT_SOLUTION = new double[0];
  
	/**
	 * <p>
	 * La constante <b>EPSILON</b> représentante un nombre très petit positif, mais non nul. Ce chiffre peut être utilisé 
	 * pour comparer une valeur de type double avec le chiffre zéro. Puisqu'un double égale à zéro
	 * est difficile à obtenir numériquement après un calcul (sauf si l'on multiplie par zéro), il est préférable de 
	 * comparer un "pseudo zéro" avec cette constante.
	 * </p>
	 * <p>
	 * Avec une valeur de EPSILON = 1e-10, cette valeur permet de comparer adéquatement des nombres autour de '1' avec suffisamment de chiffres significatifs.
	 * </p>
	 */
	public static double EPSILON = 1e-10;           
	
	/**
	 * La constante <b>EPSILON_RELAXED</b> représente un nombre très petit, mais mille fois plus grand que EPSILON (1000*EPSILON).
	 * Pour des raisons numériques, le seuil EPSILON est trop petit et ce nouveau seuil peut être adéquat. 
	 */
	public final static double EPSILON_RELAXED = EPSILON*1000.0;
	
	/**
   * La constante <b>NEGATIVE_EPSILON</b> représentante un nombre très petit, mais non nul qui est <b>negatif</b>. Ce chiffre peut être utilisé 
   * pour comparer une valeur arbiraire de type double avec le chiffre zéro, mais qui sera négatif. Puisqu'un double égale à zéro
   * est difficile à obtenir numériquement après un calcul (sauf si l'on multiplie par zéro), il est préférable de 
   * comparer un "pseudo zéro" avec cette constante.
   */
	public final static double NEGATIVE_EPSILON = -1.0*EPSILON;  
	
	/**
	 * La constante <b>ONE_PLUS_EPSILON</b> représente une constante égale à <b>1 + EPSILON</b> ce qui correspond à un nombre légèrement supérieur à 1.
	 */
	public final static double ONE_PLUS_EPSILON = 1 + EPSILON;
	
	/**
	 * La constante <b>ONE_MINUS_EPSILON</b> représente une constant égale à <b>1 - EPSILON</b> ce qui correspond à un nombre légèrement inférieur à 1.
	 */
	public final static double ONE_MINUS_EPSILON = 1 - EPSILON;
	
	/**
   * La constante <b>ONE_PLUS_1000EPSILON</b> représente une constante égale à <b>1 + 1000*EPSILON</b> ce qui correspond à un nombre légèrement supérieur à 1.
   */
  public final static double ONE_PLUS_1000EPSILON = 1 + 1000*EPSILON;
  
  /**
   * La constante <b>ONE_MINUS_EPSILON</b> représente une constant égale à <b>1 - 1000*EPSILON</b> ce qui correspond à un nombre légèrement inférieur à 1.
   */
  public final static double ONE_MINUS_1000EPSILON = 1 - 1000*EPSILON;
  
	/**
	 * La constante <b>INFINITY</b> représente un nombre positif égale à l'infini. Cette valeur est obtenue à
	 * partir de la classe java.lang.Double.
	 * @see java.lang.Double
	 */
	public final static double INFINITY = Double.POSITIVE_INFINITY;
	
	/**
	 * La constante <b>INVERSE_E</b> représente le nombre 1 / e où e = 2.718182.
	 */
	public final static double INVERSE_E = 1.0/Math.E;
	
	/**
   * Méthode pour déterminer si deux nombres de type double sont <b>relativement égaux</b>. 
   * En utilisant une approche de calcul de différence, on vérifie si
   * <ul>a - b < EPSILON*ref</ul>  
   * afin de <b>validé l'égalité</b> entre a et b (a == b). EPSILON est un seuil de précision 
   * et ref est une base de référence (la valeur absolue la plus élevée parmis a et b). 
   * <p>Cependant, si les deux chiffres sont inférieurs à EPSILON, ils seront considérés comme égaux.</p>
   * 
   * @param a Le 1ier nombre à comparer.
   * @param b Le 2ième nombre à comparer.
   * @return <b>true</b> si les deux nombres sont <b>relativement égaux</b> et <b>false</b> sinon.
   */
	public static boolean nearlyEquals(double a, double b)
	{
	  return nearlyEquals(a, b, EPSILON);
	}
	
	/**
	 * Méthode pour déterminer si deux nombres de type double sont <b>relativement égaux</b>. 
	 * En utilisant une approche de calcul de différence, on vérifie si
	 * <ul>a - b < EPSILON*ref</ul>  
	 * afin de <b>validé l'égalité</b> entre a et b (a == b). EPSILON est un seuil de précision 
	 * et ref est une base de référence (la valeur absolue la plus élevée parmis a et b).
	 * <p>Cenpendant, si les deux chiffres sont inférieurs à EPSILON, ils seront considérés comme égaux.</p>
	 * 
	 * @param a Le 1ier nombre à comparer.
	 * @param b Le 2ième nombre à comparer.
	 * @param epsilon - La précision acceptable.
	 * @return <b>true</b> si les deux nombres sont <b>relativement égaux</b> et <b>false</b> sinon.
	 */
	public static boolean nearlyEquals(double a, double b, double epsilon)
	{
	  double absA = Math.abs(a);
    double absB = Math.abs(b);
    double diff = Math.abs(a - b);
    
    // Vérification du cas particulier : 0 = 0 et infiny = infiny (mais pas certain ...) 
    if(a == b)                            
      return true;
     
    // Cas des petites chiffres : Vérifier si les deux chiffres sont très près l'un de l'autre
    if(diff < epsilon)  
      return true;
      
    // Cas général
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
	 * Méthpde pour déterminer si une valeur est relativement près de zéro.
	 * Cette méthode est nécessaire, car une opération mathématique menant au chiffre 0 peut être 0.0 et -0.0 ce qui n'est pas égal selon JAVA.
	 * 
	 * @param value La valeur à comparer avec 0.
	 * @return <b>true</b> si la valeur est <b>relativement égal</b> à zéro et <b>false</b> sinon.
	 */
	public static boolean nearlyZero(double value)
	{
	  return nearlyEquals(value, 0.0);
	}
		
	/**
	 * Méthode permettant d'évaluer la racine réelle d'un polynôme de degré '1' de la forme
	 * <ul>Ax + B = 0.</ul>
	 * <p>Un polynôme de degré '1' possède au maximum <b>une</b> racine réelle.</p>
	 *  
	 * @param A Le coefficient devant le terme de puissance '1' (x).
	 * @param B Le coefficient devant le terme de puissance '0' (1).
	 * @return La racine réelle d'un polynôme de degré '1' (s'il y en a).
	 * @throws SInfinityOfSolutionsException Si le polynôme contient une infinité de solution (0x + 0 = 0).
	 */
	public static double[] linearRealRoot(double A, double B) throws SInfinityOfSolutionsException
	{
	  // Vérifier si le polynôme n'est pas d'un degré inférieur.
	  if(nearlyZero(A))
	  {
	    // Vérifier si l'équation n'est pas 0*t + B = 0.
	    // Si B == 0, l'équation admettra une infinité de solutions.
	    // Autrement, l'équation n'admettra aucune solution.
	    if(nearlyZero(B))
	      throw new SInfinityOfSolutionsException("L'équation linéaire 0x + 0 = 0 admet une infinité de solutions.");
	    
	    return NO_ROOT_SOLUTION;
	  }
	  
	  throw new SNoImplementationException("Erreur SMath : C'est méthode n'a pas été implémentée.");
   
	}
	
	/**
   * Méthode permettant d'évaluer les racines réelles d'un polynôme de degré '2' de la forme
   * <ul>Ax^2 + Bx + C = 0.</ul>
   * <p>Un polynôme de degré '2' possède au maximum <b>deux</b> racines réelles.</p>
   *  
   * @param A Le coefficient devant le terme de puissance '2' (x^2).
   * @param B Le coefficient devant le terme de puissance '1' (x).
   * @param C Le coefficient devant le terme de puissance '0' (1).
   * @return Les racines réelles de d'un polynôme de degré '2' (s'il y en a). Les solutions retournées dans un tableau sont <b>triées en ordre croissant</b>. 
   * @throws SInfinityOfSolutionsException Si le polynôme contient une infinité de solution (0x^2 + 0x + 0 = 0).
   */
	public static double[] quadricRealRoot(double A, double B, double C) throws SInfinityOfSolutionsException
	{
	  // Vérifier si le polynôme n'est pas d'un degré inférieur
	  if(nearlyZero(A))
	    return linearRealRoot(B,C);
	  	  
	  throw new SNoImplementationException("Erreur SMath : C'est méthode n'a pas été implémentée.");
	  
	}
	
	/**
   * Méthode permettant d'évaluer les racines réelles d'un polynôme de degré '3' de la forme
   * <ul>Ax^3 + Bx^2 + Cx + D = 0.</ul>
   * <p>Un polynôme de degré '3' possède au maximum <b>trois</b> racines réelles.</p>
   *  
   * @param A Le coefficient devant le terme de puissance '3' (x^3).
   * @param B Le coefficient devant le terme de puissance '2' (x^2).
   * @param C Le coefficient devant le terme de puissance '1' (x).
   * @param D Le coefficient devant le terme de puissance '0' (1).
   * @return Les racines réelles de d'un polynôme de degré '3' (s'il y en a). Les solutions retournées dans un tableau sont <b>triées en ordre croissant</b>. 
   * @throws SInfinityOfSolutionsException Si le polynôme contient une infinité de solution (0x^3 + 0x^2 + 0x + 0 = 0).
   */
  public static double[] cubicRealRoot(double A, double B, double C, double D) throws SInfinityOfSolutionsException
  {
    // Vérifier si le polynôme n'est pas d'un degré inférieur
    if(nearlyZero(A))
      return quadricRealRoot(B,C,D);
    
    throw new SNoImplementationException("Erreur SMath : C'est méthode n'a pas été implémentée.");
    
  }
  
	/**
   * Méthode permettant d'évaluer les racines réelles d'un polynôme de degré '4' de la forme
   * <ul>Ax^4 + Bx^3 + Cx^2 + Dx + E = 0.</ul>
   * <p>Un polynôme de degré '4' possède au maximum <b>quatre</b> racines réelles.</p>
   *  
   * @param A Le coefficient devant le terme de puissance '4' (x^4).
   * @param B Le coefficient devant le terme de puissance '3' (x^3).
   * @param C Le coefficient devant le terme de puissance '2' (x^2).
   * @param D Le coefficient devant le terme de puissance '1' (x).
   * @param E Le coefficient devant le terme de puissance '0' (1).
   * @return Les racines réelles de d'un polynôme de degré '4' (s'il y en a). Les solutions retournées dans un tableau sont <b>triées en ordre croissant</b>. 
   * @throws SInfinityOfSolutionsException Si le polynôme contient une infinité de solution (0x^4 + 0x^3 + 0x^2 + 0x + 0 = 0).
   */
	public static double[] quarticRealRoot(double A, double B, double C, double D, double E) throws SInfinityOfSolutionsException
	{
	  throw new SNoImplementationException("Erreur SMath : C'est méthode n'a pas été implémentée.");
   
	}
	
	/**
   * Méthode qui effectue le calcul inverse de l'interpolation linéaire d'une valeur numérique.
   * Cela sigifie que l'on cherche la valeur du paramètre d'interpolation t à partir d'une valeur interpolée
   * ainsi que des deux valeurs extrêmes.
   * 
   * @param v La valeur interpolée dont la valeur de t doit être calculée.
   * @param v0 La valeur de référence pondérée par 1 - t.
   * @param v1 La valeur de référence pondérée par le facteur t.
   * @return La facteur t d'interpolation linéaire.
   */
  public static double reverseLinearInterpolation(double v, double v0, double v1) 
  {
    // À partir de la relation v = (1-t)*v0 + t*v1 , on doit isoler t.
    return (v - v0)/(v1 - v0);
  }
  
  /**
   * Méthode qui effectue le calcul de l'interpolation linéaire d'une valeur numérique.
   * 
   * @param v0 La valeur de référence pondérée par 1 - t.
   * @param v1 La valeur de référence pondérée par le facteur t.
   * @param t Le paramètre de pondération.
   * @return La valeur interpolée.
   */
  public static double linearInterpolation(double v0, double v1, double t) 
  {   
    // Calcul de l'interpolation : v = v0*(1 - t) + v1*t
    return v0*(1.0 - t) + (v1*t);
  }
  
  /**
   * Méthode qui effectue le calcul de l'interpolation quadratique d'une valeur numérique.
   * 
   * @param v0 La valeur de référence pondérée par 1 - t*t.
   * @param v1 La valeur de référence pondérée par le facteur t*t.
   * @param t Le paramètre de pondération.
   * @return La valeur interpolée.
   */
  public static double quadricInterpolation(double v0, double v1, double t) 
  {
    // Calcul de l'interpolation : v = v0*(1 - t^2) + v1*t^2
    double t2 = t*t;
    
    return v0*(1.0 - t2) + v1*t2;
  }
  
  /**
   * <p>
   * Méthode déterminant le signe d'un nombre. Les résultats sont
   * <ul> -1 si a < 0</ul>
   * <ul> 0 si a = 0</ul>
   * <ul> 1 si a > 0</ul>
   * </p>
   * <p>
   * Cette implémentation exploite la méthode java.Math.signum() de JAVA.
   * </p>
   * 
   * @param a Le nombre.
   * @return Le signe du nombre.
   */
  public static double signum(double a) throws SNaNValueException
  {
    if(Double.isNaN(a))
      throw new SNaNValueException();
    else
      return Math.signum(a);
  }
  
  /**
   * Méthode permettant de générer un nombre aléatoire entre une valeur minimale et maximale.
   *  
   * @param min La valeur minimale du nombre aléatoire.
   * @param max La valeur maximale du nombre aléatoire.
   * @return Le nombre aléatoire.
   */
  public static double random(double min, double max)
  {
    return Math.random()*(max - min) + min;
  }
  
  /**
   * Méthode permettant de générer un nombre aléatoire entre une valeur minimale et maximale.
   * 
   * @param min La valeur minimale du nombre aléatoire.
   * @param max La valeur maximale du nombre aléatoire.
   * @return Le nombre aléatoire.
   */
  public static int random(int min, int max)
  {
    return (int)Math.round((Math.random()*(max - min))) + min;
  }
  /**
   * Méthode permettant de générer aléatoirement une valeur vrai ou faux.
   * 
   * @return aléatoirement une réponse <b>true</b> ou <b>false</b>.
   */
  public static boolean randomTrueOrFalse()
  {
    return Math.random() > 0.5;
  }
  
  /**
   * Méthode pour générer aléatoirement un signe positif ou négatif.
   * Ainsi, cette fonction à 1 chance sur 2 de générer le nombre +1.0 et 1 chance sur 2 de générer le nombre -1.0.
   * 
   * @return La valeur +1.0 ou -1.0 dans la probabilité 50/50.
   */
  public static double randomSign()
  {
    if(SMath.randomTrueOrFalse())
      return 1.0;
    else
      return -1.0;
  }
  
  /**
   * Méthode pour générer un nombre aléatoire appartenant à un tableau de valeurs.
   * Les choix des éléments dans le tableau sont tous équiprobable.
   * 
   * @param possibility Le tableau des valeurs possibles.
   * @return Une valeur aléatoire appartenant au tableau des possibilités. 
   */
  public static int random(int[] possibility)
  {
    // Choisir un indice du tableau aléatoirement.
    int random_index = (int)(Math.random()*possibility.length);
    
    return possibility[random_index];
  }
  
  /**
   * Méthode pour générer un nombre aléatoire appartenant à un tableau de valeurs dont le poids probabiliste
   * n'est pas équiprobable. La probabilité de chaque valeur dépend d'un poid déterminé pour chaque valeur.
   * L'association est effectuée selon les indices des deux tableaux. 
   * 
   * @param possibility Le tableau des valeurs possibles.
   * @param weight Le tableau des poids du choix aléatoire.
   * @return Une valeur aléatoire appartenant au tableau des possibilités. 
   * @throws SIllegalNegativeValueException Si le tableau des poids contient une valeur négative.
   */
  public static int random(int[] possibility, double[] weight) throws SIllegalNegativeValueException
  {
    // Vérification de la longueur des tableaux.    
    if(possibility.length != weight.length)
      throw new SRuntimeException("Le tableau possibility possède une longueur " + possibility.length  + " et le tableau weight possède une longueur " + weight.length + " ce qui n'est pas égal.");
    
    // Évaluer la somme des pondératoire.
    double sum = 0.0;
    
    for(double p : weight)
      if(p < 0.0)
        throw new SIllegalNegativeValueException("Erreur SMath 005 : La valeur négative " + p + " n'est pas adéquate dans le tableau des poids.");
      else
        sum += p;
    
    // Générer un nombre aléatoire entre 0 et la pondératoire total.
    double random = Math.random()*sum;
    
    // Choisir l'état aléatoire pondéré par le poids des choix.
    int index = 0;
    
    while(random > weight[index])
    {
      random -= weight[index];
      index++;
    }
    
    // Retourner le choix aléatoire pondéré par le poids des choix.  
    return possibility[index];
  }
  
  /**
   * <p>
   * Méthode pour obtenir un nombre aléatoire entre 0.0 et 1.0 selon une distribution gaussienne de variance égale à 1.
   * </p>
   * <p>
   * Cette version d'implémentation donne une valeur moyenne pour les nombre aléatoire de 0.85.
   * Cette version n'est pas équivalente à la version de l'objet java.util.random
   * où la méthode randomGaussian donne un nombre aléatoire entre 0.0 et 1.0 de moyenne égal à 0.80.
   * </p>
   *
   * @return Une nombre aléatoire entre 0.0 et 1.0 selon une distribution gaussienne de variance égal à 1.
   */
  public static double randomGaussian()
  {
    // Soit : y = 1 * e^-( (x-u)^2 / 2*s^2)
    // où s est la variance qui sera égal à 1 
    // et u est la moyenne qui sera égal à 0. 
    
    // Choisisons des valeurs pour y entre 1 et 1/e afin que x soit situé entre 0 et 1.
    double y = SMath.random(INVERSE_E, 1.0);
        
    // Isolons x et interprétons là comme valeur aléatoire de distribution gaussienne.
    // Cette version propose une moyenne de 0.85
    return Math.sqrt(-2.0*Math.log(y));
    
    //----------------------------------------------------------------
    // REMARQUE : L'IMPLANTATION SUITANT propose une moyenne de 0.8000
    //----------------------------------------------------------------
    
    //java.util.Random ran = new java.util.Random();
    //return ran.nextGaussian();
  }
     
}
