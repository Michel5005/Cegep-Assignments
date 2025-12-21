package sim.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import sim.exception.SNoImplementationException;
import sim.exception.SRuntimeException;
import sim.math.SMath;

/**
 * La classe <b>SArrays</b> permet d'effectuer des opérations sur des tableaux (Array) de type primitif.
 * 
 * @author Simon Vézina
 * @since 2017-12-15
 * @version 2022-06-02 (version labo – Le champ électrique v1.1.0)
 */
public final class SArrays {

  /**
   * La constante <b>EPSILON</b> représente un nombre très petit positif, mais non nul.
   * Il est égal à la valeur {@value} .
   */
  private static final double EPSILON = SMath.EPSILON;
  
  /**
   * <p>
   * Méthode pour déterminer si deux tableau de nombres de type double sont <b>relativement égaux</b>. 
   * En utilisant une approche de calcul de différence, on vérifie si pour chaque élément du tableau
   * <ul>a - b < EPSILON*ref</ul>  
   * afin de <b>validé l'égalité</b> entre a et b (a == b). 
   * </p>
   * <p>
   * EPSILON est un seuil de précision (voir EPSILON, mais traditionnelement 1e-10) et ref est une base de référence (la valeur absolue la plus élevée parmis a et b).
   * Cependant, si les deux chiffres sont inférieurs à EPSILON, ils seront considérés comme égaux.
   * </p>
   * 
   * @param tab1 Le premier tableau à comparer.
   * @param tab2 Le deuxième tableau à comparer.
   * @return <b>true</b> si les deux tableaux sont <b>relativement égaux</b> et <b>false</b> sinon.
   */
  public static boolean nearlyEquals(double[] tab1, double[] tab2)
  {
    return nearlyEquals(tab1, tab2, SArrays.EPSILON);
  }
  
  /**
   * <p>
   * Méthode pour déterminer si deux tableau de nombres de type double sont <b>relativement égaux</b>. 
   * En utilisant une approche de calcul de différence, on vérifie si pour chaque élément du tableau
   * <ul>a - b < EPSILON*ref</ul>  
   * afin de <b>validé l'égalité</b> entre a et b (a == b). 
   * </p>
   * <p>
   * EPSILON est un seuil de précision et ref est une base de référence (la valeur absolue la plus élevée parmis a et b).
   * Cependant, si les deux chiffres sont inférieurs à EPSILON, ils seront considérés comme égaux.
   * </p>
   * 
   * @param tab1 Le premier tableau à comparer.
   * @param tab2 Le deuxième tableau à comparer.
   * @param epsilon La précision acceptable.
   * @return <b>true</b> si les deux tableaux sont <b>relativement égaux</b> et <b>false</b> sinon.
   */
  public static boolean nearlyEquals(double[] tab1, double[] tab2, double epsilon) 
  {
    // Vérifier que les deux tableaux ont la même taille.
    if(tab1.length != tab2.length)
      return false;
    
    // Vérifier que l'ensemble des donnée indexe par indexe sont "nearlyEquals".
    for(int i = 0; i < tab1.length; i++)
      if(!SMath.nearlyEquals(tab1[i], tab2[i], epsilon))
        return false;
    
    // Puisque l'ensemble des valeurs indexe par indexe sont "nearlyEquals", le tableau doit l'être. 
    return true;
  }
  
  /**
   * Méthode pour effectuer une copie d'un tableau.
   * 
   * @param tab Le tableau à copier.
   * @param copy Le tableau qui va recevoir la copie.
   * @return Le tableau qui a reçu la copie. 
   * @throws IllegalArgumentException Si les deux tableaux n'ont pas la même longueur.
   */
  public static int[] copy(int[] tab, int[] copy) throws IllegalArgumentException
  {
    if(tab.length != copy.length)
      throw new IllegalArgumentException("Erreur SArrays 001 : Les deux tableaux n'ont pas la même longueur (" + tab.length + " et " + copy + ").");
    
    System.arraycopy(tab, 0, copy, 0, tab.length);
    
    return copy;
  }
  
  /**
   * Méthode pour effectuer une copie d'un tableau.
   * 
   * @param tab Le tableau à copier.
   * @param copy Le tableau qui va recevoir la copie.
   * @throws IllegalArgumentException Si les deux tableaux n'ont pas la même longueur.
   */
  public static void copy(int[] tab, byte[] copy) throws IllegalArgumentException
  {
    if(tab.length != copy.length)
      throw new IllegalArgumentException("Erreur SArrays 002 : Les deux tableaux n'ont pas la même longueur (" + tab.length + " et " + copy + ").");
    
    System.arraycopy(tab, 0, copy, 0, tab.length);
  }
  
  /**
   * Méthode pour effectuer une copie d'un tableau.
   * 
   * @param tab Le tableau à copier.
   * @param copy Le tableau qui va recevoir la copie.
   * @throws IllegalArgumentException Si les deux tableaux n'ont pas la même longueur.
   */
  public static void copy(byte[] tab, byte[] copy) throws IllegalArgumentException
  {
    if(tab.length != copy.length)
      throw new IllegalArgumentException("Erreur SArrays 003 : Les deux tableaux n'ont pas la même longueur (" + tab.length + " et " + copy + ").");
    
    System.arraycopy(tab, 0, copy, 0, tab.length);
  }
  
  /**
   * Méthode pour effectuer une copie d'un tableau.
   * 
   * @param tab Le tableau à copier.
   * @param copy Le tableau qui va recevoir la copie.
   * @throws SRuntimeException Si les deux tableaux n'ont pas la même longueur.
   */
  public static void copy(double[] tab, double[] copy) throws ArrayIndexOutOfBoundsException
  {
    if(tab.length != copy.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 004 : Les deux tableaux n'ont pas la même longueur (" + tab.length + " et " + copy + ").");
    
    System.arraycopy(tab, 0, copy, 0, tab.length);
  }
  
  /**
   * Méthode pour mettre à zéro l'ensemble des données d'un tableau.
   * 
   * @param array Le tableau.
   * @return Une référence sur le tableau passé en paramètre.
   */
  public static double[] clear(double[] array)
  {
    for(int i = 0; i < array.length; i++)
      array[i] = 0.0;
    
    return array;
  }
  
  /**
   * Méthode pour effectuer la concaténation de deux tableaux.
   * 
   * </u>Reference :</u> https://stackoverflow.com/questions/80476/how-can-i-concatenate-two-arrays-in-java 
   * @param first Le premier tableau.
   * @param second Le deuxieme tableau.
   * @return Le tableau concatené.
   */
  public static <T> T[] concatenate(T[] first, T[] second) 
  {
    T[] result = Arrays.copyOf(first, first.length + second.length);
    System.arraycopy(second, 0, result, first.length, second.length);
    
    return result;
  }
  
  /**
   * Méthode pour effectuer la concaténation de deux tableaux.
   * 
   * </u>Reference :</u> https://stackoverflow.com/questions/80476/how-can-i-concatenate-two-arrays-in-java 
   * @param first Le premier tableau.
   * @param second Le deuxieme tableau.
   * @return Le tableau concatené.
   */
  public static double[] concatenate(double[] first, double[] second) 
  {
    double[] result = Arrays.copyOf(first, first.length + second.length);
    System.arraycopy(second, 0, result, first.length, second.length);
    
    return result;
  }
  
  /**
   * Méthode pour réaliser <u>l'addition</u> d'une valeur à un tableau.
   * Un nouveau tableau sera retourné contenant le résultat de l'opération mathématique.
   * 
   * @param tab Le 1er tableau.
   * @param a La valeur à ajouter.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   */
  public static double[] add(double[] tab, double a) 
  {
    double[] result = new double[tab.length];
    
    for(int i = 0; i < tab.length; i++)
      result[i] = tab[i] + a;
    
    return result;
  }
  
  /**
   * Méthode pour réaliser <u>l'addition</u> entre deux tableaux de même dimension.
   * Un nouveau tableau sera retourné contenant le résultat de l'opération mathématique.
   * 
   * @param tab1 Le 1er tableau.
   * @param tab2 Le 2e tableau.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la même taille.
   */
  public static double[] add(double[] tab1, double[] tab2) throws ArrayIndexOutOfBoundsException
  {
    // Vérification de la taille des tableaux.
    if(tab1.length != tab2.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 005 : Les deux tableaux n'ont pas la même longueur (" + tab1.length + " et " + tab2.length + ").");
    
    //------------------------------------------------
    // VERSION LABORATOIRE : Les fonctions discrètes.
    //------------------------------------------------
    //throw new SNoImplementationException();
    
    return add(tab1, tab2, new double[tab1.length]);
  }
  
  /**
   * Méthode pour réaliser <u>l'addition</u> entre deux tableaux de même dimension.
   * Le résultat de l'addition sera enregistré dans le 3e tableau passé en paramètre de la méthode.
   * 
   * @param tab1 Le 1er tableau.
   * @param tab2 Le 2e tableau.
   * @param result Le tableau contenant le résultat.
   * @return Le tableau contenant le résultat.
   * @throws ArrayIndexOutOfBoundsException Si les 3 tableaux ne sont pas de même taille.
   */
  public static double[] add(double[] tab1, double[] tab2, double[] result) throws ArrayIndexOutOfBoundsException
  {
    //-------------------------------------------------------------------------------------------------------
    // VERSION LABO : Les fonctions discrètes.
    //
    // P.S. Ce code n'est pas pour le labo fonction discrètes, mais ne peut pas être disponible pour ce labo.
    //      À l'origine, ceci devrait servir de tâche pour le labo Apprentissage par réseau de neurones, 
    //      mais l'idée a été abandonné. Cependant, le code est exploité.
    //-------------------------------------------------------------------------------------------------------
    //throw new SNoImplementationException();
    
    // Vérification de la taille des tableaux.
    if(tab1.length != tab2.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 006 : Le tableau 1 et 2 n'ont pas la même longueur (" + tab1.length + " et " + tab2.length + ").");
    
    if(tab2.length != result.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 007 : Le tableau 2 et 3 n'ont pas la même longueur (" + tab2.length + " et " + result.length + ").");
    
    for(int i = 0; i < tab1.length; i++)
      result[i] = tab1[i] + tab2[i];
    
    return result;
  }
  
  /**
   * Méthode pour réaliser <u>la soustraction</u> d'une valeur à un tableau.
   * Un nouveau tableau sera retourné contenant le résultat de l'opération mathématique.
   * 
   * @param tab Le 1er tableau.
   * @param a La valeur à soustraire.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   */
  public static double[] substract(double[] tab, double a) 
  {
    return add(tab, -1*a);
  }
  
  /**
   * Méthode pour réaliser <u>la soustraction</u> entre deux tableaux de même dimension.
   * Un nouveau tableau sera retourné contenant le résultat de l'opération mathématique.
   * 
   * @param tab1 Le 1er tableau.
   * @param tab2 Le 2e tableau.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la même taille.
   */
  public static double[] substract(double[] tab1, double[] tab2) throws ArrayIndexOutOfBoundsException
  {
    if(tab1.length != tab2.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 008 : Les deux tableaux n'ont pas la même longueur (" + tab1.length + " et " + tab2.length + ").");
    
    //----------------------------------------------
    // VERSION LABO : Les fonctions discrètes.
    //------------------------------------------------
    //throw new SNoImplementationException();
    
    return substract(tab1, tab2, new double[tab1.length]);
  }
  
  /**
   * Méthode pour réaliser la <u>soustraction</u> entre deux tableaux de même dimension.
   * Le résultat de la soustraction sera enregistrée dans le 3e tableau passé en paramètre de la méthode.
   * 
   * @param tab1 Le 1er tableau.
   * @param tab2 Le 2e tableau.
   * @param result Le tableau contenant le résultat.
   * @return Le tableau contenant le résultat.
   * @throws ArrayIndexOutOfBoundsException Si les 3 tableaux ne sont pas de même taille.
   */
  public static double[] substract(double[] tab1, double[] tab2, double[] result) throws ArrayIndexOutOfBoundsException
  {
    //-------------------------------------------------------------------------------------------------------
    // VERSION LABO : Les fonctions discrètes.
    //
    // P.S. Ce code n'est pas pour le labo fonction discrètes, mais ne peut pas être disponible pour ce labo.
    //      À l'origine, ceci devrait servir de tâche pour le labo Apprentissage par réseau de neurones, 
    //      mais l'idée a été abandonné. Cependant, le code est exploité.
    //-------------------------------------------------------------------------------------------------------
    //throw new SNoImplementationException();
    
    // Vérification de la taille des tableaux.
    if(tab1.length != tab2.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 009 : Le tableau 1 et 2 n'ont pas la même longueur (" + tab1.length + " et " + tab2.length + ").");
    
    if(tab2.length != result.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 010 : Le tableau 2 et 3 n'ont pas la même longueur (" + tab2.length + " et " + result.length + ").");
    
    for(int i = 0; i < tab1.length; i++)
      result[i] = tab1[i] - tab2[i];
    
    return result;
  }
  
  /**
   * Méthode pour réaliser <u>la multiplication</u> d'un tableau par un scalaire.
   * Un nouveau tableau sera retourné contenant le résultat de l'opération mathématique.
   * 
   * @param tab Le 1er tableau.
   * @param a La valeur à multiplier.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   */
  public static double[] multiply(double[] tab, double a) 
  {
    //----------------------------------------------
    // VERSION LABO : Les fonctions discrètes.
    //------------------------------------------------
    //throw new SNoImplementationException();
    
    return multiply(tab, a,  new double[tab.length]);
  }
  
  /**
   * Méthode pour réaliser <u>la multiplication</u> d'un tableau par un scalaire.
   * Le résultat de la multiplication sera enregistré dans le 2e tableau passé en paramètre.
   * 
   * @param tab Le tableau.
   * @param a Le scalaire.
   * @param result Le résultat de la multiplication du tableau par un scalaire.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   */
  public static double[] multiply(double[] tab, double a, double[] result) throws ArrayIndexOutOfBoundsException
  {
    if(tab.length != result.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 009 : Les deux tableaux n'ont pas la même longueur (" + tab.length + " et " + result.length + ").");
    
    //-------------------------------------------------------------------------------------------------------
    // VERSION LABO : Les fonctions discrètes.
    //
    // P.S. Ce code n'est pas pour le labo fonction discrètes, mais ne peut pas être disponible pour ce labo.
    //      À l'origine, ceci devrait servir de tâche pour le labo Apprentissage par réseau de neurones, 
    //      mais l'idée a été abandonné. Cependant, le code est exploité.
    //-------------------------------------------------------------------------------------------------------
    //throw new SNoImplementationException();
    
    for(int i = 0; i < tab.length; i++)
      result[i] = a*tab[i];
    
    return result;
  }
  
  /**
   * Méthode pour réaliser <u>la multiplication</u> entre deux tableaux de même dimension.
   * Un nouveau tableau sera retourné contenant le résultat de l'opération mathématique.
   * 
   * @param tab1 Le 1er tableau.
   * @param tab2 Le 2e tableau.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la même taille.
   */
  public static double[] multiply(double[] tab1, double[] tab2) throws ArrayIndexOutOfBoundsException
  {
    // Vérification de la taille des tableaux.
    if(tab1.length != tab2.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 010 : Les deux tableaux n'ont pas la même longueur (" + tab1.length + " et " + tab2.length + ").");
    
    //----------------------------------------------
    // VERSION LABO : Les fonctions discrètes.
    //------------------------------------------------
    //throw new SNoImplementationException();
    
    return multiply(tab1, tab2, new double[tab1.length]);
  }
  
  /**
   * Méthode pour réaliser <u>la multiplication</u> entre deux tableaux de même dimension.
   * Le résultat de la multiplication sera enregistré dans le 3e tableau passé en paramètre de la méthode.
   * 
   * @param tab1 Le 1er tableau.
   * @param tab2 Le 2e tableau.
   * @param result Le tableau contenant le résultat.
   * @return Le tableau contenant le résultat.
   * @throws ArrayIndexOutOfBoundsException Si les 3 tableaux ne sont pas de même taille.
   */
  public static double[] multiply(double[] tab1, double[] tab2, double[] result) throws ArrayIndexOutOfBoundsException
  {
    // Vérification de la taille des tableaux.
    if(tab1.length != tab2.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 011 : Le tableau 1 et 2 n'ont pas la même longueur (" + tab1.length + " et " + tab2.length + ").");
    
    if(tab2.length != result.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 012 : Le tableau 2 et 3 n'ont pas la même longueur (" + tab2.length + " et " + result.length + ").");
   
    //-------------------------------------------------------------------------------------------------------
    // VERSION LABO : Les fonctions discrètes.
    //
    // P.S. Ce code n'est pas pour le labo fonction discrètes, mais ne peut pas être disponible pour ce labo.
    //      À l'origine, ceci devrait servir de tâche pour le labo Apprentissage par réseau de neurones, 
    //      mais l'idée a été abandonné. Cependant, le code est exploité.
    //-------------------------------------------------------------------------------------------------------
    //throw new SNoImplementationException();
    
    for(int i = 0; i < tab1.length; i++)
      result[i] = tab1[i] * tab2[i];
    
    return result;
  }
  /**
   * Méthode pour réaliser <u>le produit scalaire</u> entre deux tableaux de même dimension.
   * 
   * @param A Le 1er tableau.
   * @param B Le 2e tableau.
   * @return Le résultat de l'opération mathématique.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la même taille.
   */
  public static double dot(double[] A, double[] B) throws ArrayIndexOutOfBoundsException
  {
    if(A.length != B.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 013 : Les deux tableaux A et B n'ont pas la même longueur (A = " + A.length + " et B = " + B.length + ").");
       
    throw new SNoImplementationException();
    
  }
  
  /**
   * Méthode pour réaliser <u>la division</u> entre deux tableaux de même dimension.
   * Un nouveau tableau sera retourné contenant le résultat de l'opération mathématique.
   * 
   * @param tab1 Le 1er tableau.
   * @param tab2 Le 2e tableau.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la même taille.
   */
  public static double[] divide(double[] tab1, double[] tab2) throws ArrayIndexOutOfBoundsException
  {
    if(tab1.length != tab2.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 014 : Les deux tableaux n'ont pas la même longueur (" + tab1.length + " et " + tab2 + ").");
    
    //----------------------------------------------
    // VERSION LABO : Les fonctions discrètes.
    //------------------------------------------------
    //throw new SNoImplementationException("La méthode n'a pas été implémentée.");
    
    double[] result = new double[tab1.length];
    
    for(int i = 0; i < tab1.length; i++)
      result[i] = tab1[i] / tab2[i];
    
    return result;
  }
  
  /**
   * Méthode pour réaliser <u>l'exponentiel</u> d'un tableau.
   * Un nouveau tableau sera retourné contenant le résultat de l'opération mathématique.
   * 
   * @param tab Le tableau.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   */
  public static double[] exp(double[] tab)
  {
    double[] result = new double[tab.length];
    
    for(int i = 0; i < tab.length; i++)
      result[i] = Math.exp(tab[i]);
    
    return result;
  }
  
  /**
   * Méthode pour réaliser <u>la puissance</u> d'un tableau.
   * Un nouveau tableau sera retourné contenant le résultat de l'opération mathématique.
   * 
   * @param tab Le tableau.
   * @param n La puissance.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   */
  public static double[] pow(double[] tab, double n)
  {
    //----------------------------------------------
    // VERSION LABO : Les fonctions discrètes.
    //------------------------------------------------
    //throw new SNoImplementationException();
    
    double[] result = new double[tab.length];
    
    for(int i = 0; i < tab.length; i++)
      result[i] = Math.pow(tab[i], n);
    
    return result;
  }
  
  /**
   * Méthode pour réaliser <u>le sinus</u> d'un tableau. Les valeurs du tableau seront considérées en <b>radian</b>.
   * Un nouveau tableau sera retourné contenant le résultat de l'opération mathématique.
   * 
   * @param tab Le tableau.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   */
  public static double[] sin(double[] tab)
  {
    double[] result = new double[tab.length];
    
    for(int i = 0; i < tab.length; i++)
      result[i] = Math.sin(tab[i]);
    
    return result;
  }
  
  /**
   * Méthode pour réaliser <u>le cosinus</u> d'un tableau. Les valeurs du tableau seront considérées en <b>radian</b>.
   * Un nouveau tableau sera retourné contenant le résultat de l'opération mathématique.
   * 
   * @param tab Le tableau.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   */
  public static double[] cos(double[] tab)
  {
    double[] result = new double[tab.length];
    
    for(int i = 0; i < tab.length; i++)
      result[i] = Math.cos(tab[i]);
    
    return result;
  }
  
  /**
   * Méthode pour réaliser la fonction ReLU d'un tableau. 
   * 
   * @param tab Le tableau.
   * @return Le tableau contenant le résultat de l'opération mathématique.
   */
  public static double[] relu(double[] tab)
  {
    //-------------------------------------------------------
    // VERSION LABO : L'apprentissage par réseau de neurones.
    //-------------------------------------------------------
    //throw new SNoImplementationException();
    
    double[] result = new double[tab.length];
    
    for(int i = 0; i < tab.length; i++)
      result[i] = Math.max(0.0, tab[i]);
    
    return result;
  }
  
  /**
   * Méthode pour évaluer la fonction softmax sur les composantes d'un tableau.
   * Le résultat de la fonction sera enregistré dans le tableau result.
   * 
   * @param tab Le tableau des données sur lequel la fonction softmax sera effectuée.
   * @param result Le résultat de la fonction softmax.
   * @return Le tableau contenant les composantes du résultat de la fonction softmax. 
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la même taille.
   */
  public static double[] softmax(double[] tab, double[] result) throws ArrayIndexOutOfBoundsException
  {
    // Vérification de la taille des tableaux.
    if(tab.length != result.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 015 : Le tableau n'a pas la même taille que le tableau du résultat (" + tab.length + " et " + result.length + ").");
      
    throw new SNoImplementationException();
    
  }
    
  /**
   * Méthode pour obtenir la <b>plus grande valeur</b> d'un tableau.
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
   * Méthode pour obtenir la <b>plus grande valeur</b> d'un tableau.
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
   * <p>
   * Méthode pour obtenir la <b>plus grande valeur sans être zéro</b> d'un tableau.
   * </p>
   * <p>
   * Cependant, si le <u>tableau contient uniquement des zéros</u>, la valeur retournée sera zéro.
   * </p>
   * 
   * @param tab Le tableau.
   * @return La plus grande valeurs (sauf zéro) du tableau.
   */
  public static double findMaxNotZero(double[] tab)
  {
    double max = 0.0;
    int i = 0;
    
    // Obtenir le 1er chiffre pas égale à zéro.
    while(max == 0.0 && i < tab.length)
    {
      if(tab[i] != 0.0)
        max = tab[i];
      
      i++;
    }
     
    // Vérifier si l'on a pas trouvé de chiffre autre que zéro.
    if(i == tab.length)
      return 0.0;
    
    // Chercher le chiffre le plus grand sauf pour zéro.
    for(i = 0; i < tab.length; i++)
      if(tab[i] != 0.0)
        if(tab[i] > max)
          max = tab[i];
    
    return max;
  }
  
  /**
   * Méthode pour obtenir la <b>plus grande valeur absolue</b> d'un tableau.
   * 
   * @param tab Le tableau.
   * @return La plus grande valeur absolue du tableau.
   */
  public static double findAbsoluteMax(double[] tab)
  {
    double max = Math.abs(tab[0]); 
    
    for(double v : tab)
      if(Math.abs(v) > max)
        max = Math.abs(v);
    
    return max;
  }
  
  /**
   * Méthode pour obtenir la plus grande valeur d'un tableau en explorant uniquement des indices du tableau autorisé.
   * Les indices autorisés sont déterminé par le second tableau où 1.0 signifie que l'indice peut être autorisée et 0.0 n'aurorisant pas.
   * Si aucun indice est autorisé, une exception sera lancée.
   * 
   * @param array
   * @param authorization
   * @throws IllegalArgumentException Si aucun indice est autorisé dans la recherche.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la même taille.
   * @return La valeur maximale autorisée dans le tableau.
   */
  public static double findMaxInAllowedIndex(double[] array, boolean[] authorization) throws IllegalArgumentException, ArrayIndexOutOfBoundsException
  {
    if(array.length != authorization.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 009 : Les deux tableaux n'ont pas la même longueur (" + array.length + " et " + authorization + ").");
    
    double value = Double.NEGATIVE_INFINITY;
    
    for(int i = 0; i < array.length; i++)
      if(authorization[i])
        if(array[i] > value)
          value = array[i];
    
    // Vérification s'il n'y a pas eu d'autorisation.
    if(Double.isInfinite(value))
      throw new IllegalArgumentException("Error SArrays 010 : Il n'y a pas eu de recherche dans le tableau " + Arrays.toString(array) + " dont l'autorisation est " + Arrays.toString(authorization));
    
    return value;
  }
  
  /**
   * Méthode pour obtenir l'indice dans le tableau de la <b>valeur la plus grande</b>.
   * 
   * @param tab Le tableau.
   * @return L'indice dans le tableau où l'on trouve la valeur la plus grande.
   */
  public static int findIndexOfMax(double[] tab)
  {
    // Choisir la meilleure cote.
    int index = 0;
    double value = tab[0];
    
    // Parcourir le tableau à la recherche de la plus grande valeur.
    for(int i = 1; i < tab.length; i++)
      if(tab[i] > value)
      {
        value = tab[i];
        index = i;
      }
    
    return index;
  }
  
  /**
   * Méthode pour obtenir l'indice dans le tableau de la <b>valeur absolue la plus grande</b>.
   * 
   * @param tab Le tableau.
   * @return L'indice dans le tableau où l'on trouve la valeur absolue la plus grande.
   */
  public static int findIndexOfAbsoluteMax(double[] tab)
  {
    // Choisir la meilleure cote.
    int index = 0;
    double value = Math.abs(tab[0]);
    
    // Parcourir le tableau à la recherche de la plus grande valeur.
    for(int i = 1; i < tab.length; i++)
      if(Math.abs(tab[i]) > value)
      {
        value = Math.abs(tab[i]);
        index = i;
      }
    
    return index;
  }
  
  /**
   * Méthode pour obtenir la <b>plus petite valeur</b> d'un tableau.
   * 
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
   * Méthode pour obtenir la <b>plus petite valeur</b> d'un tableau.
   * 
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
   * Méthode pour convertir un tableau d'entier vers un tableau de nombre réel entre une valeur minimale et maximale.
   * La correspondance entre les valeurs converties sera linéaire.
   * 
   * @param data Les entiers à convertir.
   * @param min La borne minimale.
   * @param max La borne maximale.
   * @return Le tableau converti.
   * @throws SRuntimeException Si les bornes sont mal définies.
   */
  public static double[] mappingIntToDouble(int[] data, double min, double max) throws SRuntimeException
  {
    if(min > max)
      throw new SRuntimeException("Error SArrays 011 : La borne minimale " + min + " et la borne maximale " + max + " sont mal définies.");
    
    int min_value = findMin(data);
    int max_value = findMax(data);
    
    double[] result = new double[data.length];
    
    // Itérer sur l'ensemble des éléments du tableau.
    for(int i = 0; i < result.length; i++)
    {
      double t = SMath.reverseLinearInterpolation((double)data[i], (double)min_value, (double)max_value);
      result[i] = SMath.linearInterpolation(min, max, t);
    }
    
    return result;
  }
  
  /**
   * Méthode pour convertir un tableau de nombre réel vers un tableau d'entier entre une valeur minimale et maximale.
   * La correspondance entre les valeurs converties sera linéaire.
   * 
   * @param data Les données à convertir.
   * @param min La borne minimale.
   * @param max La borne maximale.
   * @return Le tableau converti.
   * @throws IllegalArgumentException Si les bornes sont mal définies.
   */
  public static int[] mappingDoubleToInt(double[] data, int min, int max) throws IllegalArgumentException
  {
    return mappingDoubleToInt(data, findMin(data), findMax(data), min, max);
  }
  
  /**
   * ...
   * 
   * @param data
   * @param double_min
   * @param double_max
   * @param int_min
   * @param int_max
   * @return
   * @throws IllegalArgumentException
   */
  public static int[] mappingDoubleToInt(double[] data, double double_min, double double_max, int int_min, int int_max) throws IllegalArgumentException
  {
    if(int_min > int_max)
      throw new IllegalArgumentException("Error SArrays 012 : La borne entière minimale " + int_min + " et la borne entière maximale " + int_max + " sont mal définies.");
    
    if(double_min > double_max)
      throw new IllegalArgumentException("Error SArrays 013 : La borne double minimale " + double_min + " et la borne double maximale " + double_max + " sont mal définies.");
       
    int[] result = new int[data.length];
    
    // Itérer sur l'ensemble des éléments du tableau.
    for(int i = 0; i < result.length; i++)
    {
      double t = SMath.reverseLinearInterpolation(data[i], double_min, double_max);
      result[i] = (int)SMath.linearInterpolation(int_min, int_max, t);
    }
    
    return result;
  }
  
  /**
   * <p>
   * Méthode permettant de générer un tableau contenant les éléments identique de deux tableaux de valeur <ul>préalablement trié</ul>.
   * </p>
   * 
   * <p>
   * <b>REMARQUE</b> : Le fonctionnement de cette méthode ne sera pas valide si les deux tableaux passés en paramètre ne sont pas péalablement trié.
   * </p>
   * Référence : https://stackoverflow.com/questions/32676381/find-intersection-of-two-arrays
   * 
   * @param tab1 Le 1ier tableau à comparer.
   * @param tab2 Le 2ième tableau à comparer.
   * @return Un tableau comprenant les éléments identiques de deux tableaux (l'intersection des deux tableaux).
   */
  public static double[] intersectionSortedArray(double[] tab1, double[] tab2)
  {
    return intersectionSortedArray(tab1, tab2, SMath.EPSILON);
  }
  
  /**
   * <p>
   * Méthode permettant de générer un tableau contenant les éléments identique de deux tableaux de valeur <ul>préalablement trié</ul>.
   * </p>
   * 
   * <p>
   * <b>REMARQUE</b> : Le fonctionnement de cette méthode ne sera pas valide si les deux tableaux passés en paramètre ne sont pas péalablement trié.
   * </p>
   * Référence : https://stackoverflow.com/questions/32676381/find-intersection-of-two-arrays
   * 
   * @param tab1 Le 1ier tableau à comparer.
   * @param tab2 Le 2ième tableau à comparer.
   * @param epsilon La précision de la comparaison.
   * @return Un tableau comprenant les éléments identiques de deux tableaux (l'intersection des deux tableaux).
   */
  public static double[] intersectionSortedArray(double[] tab1, double[] tab2, double epsilon)
  {
    double intersection[] = new double[Math.min(tab1.length, tab2.length)];
    int count = 0;

    int i = 0; int j = 0;
    while (i < tab1.length && j < tab2.length)
    {
      // Vérifier s'il y a égalité.
      if(SMath.nearlyEquals(tab1[i], tab2[j], epsilon)) 
      {
        intersection[count] = tab1[i];
        count++;
        i++;
        j++;
      }
      else
        // Avancer dans la recherche des éléments.
        if (tab1[i] < tab2[j]) 
          i++;                    // avancer dans le tableau 1
        else
          j++;                    // avancer dans le tableau 2
    }

    // Construire un tableau avec l'espace mémoire minimum.
    double[] result = new double[count];
    for(int k = 0; k < result.length; k++)
      result[k] = intersection[k];
    
    return result;
  }
  
  /**
   * Méthode pour changer la dimension d'un tableau. Si la nouvelle taille est inférieure à la précédente, les derniers éléments ne seront pas copié.
   * Si la nouvelle taille est supérieure à la précédente, des valeurs nulles sont attribuées aux positions supérieures. 
   * 
   * @param array Le tableau à copier.
   * @param new_size La nouvelle dimension du tableau.
   * @return Un tableau avec une copie des données avec une taille différente.
   */
  public static double[] resize(double[] array, int new_size)
  {
      // Remplir un nouveau tableau ayant seulement la taille demandé.
      // Tous les éléments du tableau précédent 
      double[] result = new double[new_size];
      
      for(int i = 0; i < new_size; i++)
        result[i] = array[i];
     
      return result;
  }
  
  /**
   * Méthode pour changer la dimension d'un tableau. Si la nouvelle taille est inférieure à la précédente, les derniers éléments ne seront pas copié.
   * Si la nouvelle taille est supérieure à la précédente, des valeurs nulles sont attribuées aux positions supérieures. 
   * 
   * @param array Le tableau à copier.
   * @param new_size La nouvelle dimension du tableau.
   * @return Un tableau avec une copie des données avec une taille différente.
   */
  public static int[] resize(int[] array, int new_size)
  {
      // Remplir un nouveau tableau ayant seulement la taille demandé.
      // Tous les éléments du tableau précédent 
      int[] result = new int[new_size];
      
      for(int i = 0; i < new_size; i++)
        result[i] = array[i];
     
      return result;
  }
  
  /**
   * Méthode pour faire l'écriture d'un tableau dans un fichier texte.
   * 
   * @param file_name Le nom du fichier.
   * @param values Le tableau des valeurs à écrire.
   */
  public static void write(String file_name, double[] values)
  {
    try{
      
      FileWriter fw = new FileWriter(file_name);
      BufferedWriter bw = new BufferedWriter(fw);
    
      // Faire l'écriture de chaque valeur et changer de ligne.
      for(int i = 0; i < values.length; i++)
      {
        bw.write(Double.toString(values[i]));
        bw.newLine();
        bw.flush();
      }
      
      bw.close(); //  fermer celui-ci en premier, sinon, ERROR !!!
      fw.close();   
      
    }catch(IOException ioe){
      SLog.logWriteLine("Message SArrays - Une erreur de type I/O est survenue.");
      ioe.printStackTrace();
    }
  }
  
  /**
   * Méthode pour convertir les données d'un tableau de donnée en un tableau de probabilité.
   * Les données seront <u>traitées en valeur absolue</u>.
   * Si le tableau contient uniquement des zéros, il restera avec des zéros et la probablitité totale du tableau sera zéro (et non 1.0).
   * 
   * @param array Le tableau à convertir en probabilité.
   */
  public static void convertToAbsoluteProbability(double[] array) throws IllegalArgumentException
  {
    // Pour calculer la somme des éléments du tableau.
    double sum = 0.0;
    
    // Faire la somme du tableau et vérifier qu'il n'y ait pas de valeur négative.
    for(int i = 0; i < array.length; i++)
      sum += Math.abs(array[i]);
    
    // Faire la normalisation en probabilité.
    if(sum == 0.0)
      throw new IllegalArgumentException("Error SArrays 014 : Le tableau suivant ne peut pas être convertie en probabilité : " + Arrays.toString(array));
    
    for(int i = 0; i < array.length; i++)
      array[i] = Math.abs(array[i]) / sum;
  }
    
  /**
   * Méthode pour faire la somme de tous les éléments d'un tableau.
   * 
   * @param array Le tableau.
   * @return La somme de tous les éléments du tableau.
   */
  public static double sumElementsArray(double[] array)
  {
    double v = 0.0;
    
    for(int i = 0; i < array.length; i++)
      v += array[i];
    
    return v;
  }
  
  /**
   * Méthode pour fractionner une valeur dans un tableau. La valeur sera la même dans chaque élément du tableau
   * et la somme des éléments sera égal à la valeur à fractionner.
   * 
   * @param value La valeur à fractionner.
   * @param array_size La taille du tableau.
   * @return Le tableau de valeur fractionner.
   * @throws IllegalArgumentException Si le tableau a une taille inférieur à 1.
   */
  public static double[] splitValueInArray(double value, int array_size) throws IllegalArgumentException
  {
    if(array_size < 1)
      throw new IllegalArgumentException("Erreur SArrays 15 : Le tableau de fractionnement de valeur ne peut pas avoir une taille inférieur à 1. (array_size = " + array_size + ").");
    
    double[] array = new double[array_size];
    
    double v = value / (double) array_size;
    
    for(int i = 0 ; i < array_size; i++)
      array[i] = v;
    
    return array;
  }
  
  /**
   * Méthode pour mettre bout à bout des tableaux de type double.
   * 
   * Référence : https://www.techiedelight.com/merge-multiple-arrays-java/
   * 
   * @param arrays Les tableaux.
   * @return Le tableau contenant la fusion des tableaux.
   */
  public static double[] merge(double[]... arrays)
  {
    int finalLength = 0;
    for (double[] array : arrays) {
      finalLength += array.length;
    }

    double[] dest = null;
    int destPos = 0;

    for (double[] array : arrays)
    {
      if (dest == null) {
        dest = Arrays.copyOf(array, finalLength);
        destPos = array.length;
      } else {
        System.arraycopy(array, 0, dest, destPos, array.length);
        destPos += array.length;
      }
    }
    
    return dest;
  }
  
}
