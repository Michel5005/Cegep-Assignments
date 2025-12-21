package circuit.kirchhof.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import circuit.kirchhof.SMath;
import circuit.kirchhof.exception.*;

/**
 * La classe <b>SArrayutil</b> permet d'effectuer des op�rations sur des tableaux (Array) de type primitif.
 * 
 * @author Simon V�zina
 * @since 2017-12-15
 * @version 2018-10-18 (version labo v1.0.4 : Les lois de Kirchhoff)
 */
public final class SArrays {

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
      if(!SMath.nearlyEquals(tab1[i], tab2[i], epsilon))
        return false;
    
    // Puisque l'ensemble des valeurs indexe par indexe sont "nearlyEquals", le tableau doit l'�tre. 
    return true;
  }
  
  /**
   * M�thode pour effectuer une copie d'un tableau.
   * 
   * @param tab Le tableau � copier.
   * @param copy Le tableau qui va recevoir la copie.
   * @throws IllegalArgumentException Si les deux tableaux n'ont pas la m�me longueur.
   */
  public static void copy(int[] tab, int[] copy) throws IllegalArgumentException
  {
    if(tab.length != copy.length)
      throw new IllegalArgumentException("Erreur SArrays 001 : Les deux tableaux n'ont pas la m�me longueur (" + tab.length + " et " + copy + ").");
    
    System.arraycopy(tab, 0, copy, 0, tab.length);
  }
  
  /**
   * M�thode pour effectuer une copie d'un tableau.
   * 
   * @param tab Le tableau � copier.
   * @param copy Le tableau qui va recevoir la copie.
   * @throws IllegalArgumentException Si les deux tableaux n'ont pas la m�me longueur.
   */
  public static void copy(int[] tab, byte[] copy) throws IllegalArgumentException
  {
    if(tab.length != copy.length)
      throw new IllegalArgumentException("Erreur SArrays 002 : Les deux tableaux n'ont pas la m�me longueur (" + tab.length + " et " + copy + ").");
    
    System.arraycopy(tab, 0, copy, 0, tab.length);
  }
  
  /**
   * M�thode pour effectuer une copie d'un tableau.
   * 
   * @param tab Le tableau � copier.
   * @param copy Le tableau qui va recevoir la copie.
   * @throws IllegalArgumentException Si les deux tableaux n'ont pas la m�me longueur.
   */
  public static void copy(byte[] tab, byte[] copy) throws IllegalArgumentException
  {
    if(tab.length != copy.length)
      throw new IllegalArgumentException("Erreur SArrays 003 : Les deux tableaux n'ont pas la m�me longueur (" + tab.length + " et " + copy + ").");
    
    System.arraycopy(tab, 0, copy, 0, tab.length);
  }
  
  /**
   * M�thode pour effectuer une copie d'un tableau.
   * 
   * @param tab Le tableau � copier.
   * @param copy Le tableau qui va recevoir la copie.
   * @throws ArrayIndexOutOfBoundsException Si l'index est plus grand que la taille du tableau.
   */
  public static void copy(double[] tab, double[] copy) throws ArrayIndexOutOfBoundsException
  {
    if(tab.length != copy.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 004 : Les deux tableaux n'ont pas la m�me longueur (" + tab.length + " et " + copy + ").");
    
    System.arraycopy(tab, 0, copy, 0, tab.length);
  }
  
  /**
   * M�thode pour r�aliser <u>l'addition</u> d'une valeur � un tableau.
   * Un nouveau tableau sera retourn� contenant le r�sultat de l'op�ration math�matique.
   * 
   * @param tab Le 1er tableau.
   * @param a La valeur � ajouter.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   */
  public static double[] add(double[] tab, double a) 
  {
    double[] result = new double[tab.length];
    
    for(int i = 0; i < tab.length; i++)
      result[i] = tab[i] + a;
    
    return result;
  }
  
  /**
   * M�thode pour r�aliser <u>l'addition</u> entre deux tableaux de m�me dimension.
   * Un nouveau tableau sera retourn� contenant le r�sultat de l'op�ration math�matique.
   * 
   * @param tab1 Le 1er tableau.
   * @param tab2 Le 2e tableau.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la m�me taille.
   */
  public static double[] add(double[] tab1, double[] tab2) throws ArrayIndexOutOfBoundsException
  {
    // V�rification de la taille des tableaux.
    if(tab1.length != tab2.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 005 : Les deux tableaux n'ont pas la m�me longueur (" + tab1.length + " et " + tab2 + ").");
    
    throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
  }
  
  /**
   * M�thode pour r�aliser <u>la soustraction</u> d'une valeur � un tableau.
   * Un nouveau tableau sera retourn� contenant le r�sultat de l'op�ration math�matique.
   * 
   * @param tab Le 1er tableau.
   * @param a La valeur � soustraire.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   */
  public static double[] substract(double[] tab, double a) 
  {
    return add(tab, -1*a);
  }
  
  /**
   * M�thode pour r�aliser <u>la soustraction</u> entre deux tableaux de m�me dimension.
   * Un nouveau tableau sera retourn� contenant le r�sultat de l'op�ration math�matique.
   * 
   * @param tab1 Le 1er tableau.
   * @param tab2 Le 2e tableau.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la m�me taille.
   */
  public static double[] substract(double[] tab1, double[] tab2) throws ArrayIndexOutOfBoundsException
  {
    if(tab1.length != tab2.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 006 : Les deux tableaux n'ont pas la m�me longueur (" + tab1.length + " et " + tab2 + ").");
    
    throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
  }
  
  /**
   * M�thode pour r�aliser <u>la multiplication</u> d'une valeur � un tableau.
   * Un nouveau tableau sera retourn� contenant le r�sultat de l'op�ration math�matique.
   * 
   * @param tab Le 1er tableau.
   * @param a La valeur � multiplier.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   */
  public static double[] multiply(double[] tab, double a) 
  {
    throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
  }
  
  /**
   * M�thode pour r�aliser <u>la multiplication</u> entre deux tableaux de m�me dimension.
   * Un nouveau tableau sera retourn� contenant le r�sultat de l'op�ration math�matique.
   * 
   * @param tab1 Le 1er tableau.
   * @param tab2 Le 2e tableau.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la m�me taille.
   */
  public static double[] multiply(double[] tab1, double[] tab2) throws ArrayIndexOutOfBoundsException
  {
    if(tab1.length != tab2.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 007 : Les deux tableaux n'ont pas la m�me longueur (" + tab1.length + " et " + tab2.length + ").");
    
    throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
  }
  
  /**
   * M�thode pour r�aliser <u>le produit scalaire</u> entre deux tableaux de m�me dimension.
   * 
   * @param A Le 1er tableau.
   * @param B Le 2e tableau.
   * @return Le r�sultat de l'op�ration math�matique.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la m�me taille.
   */
  public static double dot(double[] A, double[] B) throws ArrayIndexOutOfBoundsException
  {
    if(A.length != B.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 007 : Les deux tableaux A et B n'ont pas la m�me longueur (A = " + A.length + " et B = " + B.length + ").");
       
    throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
  }
  
  /**
   * M�thode pour r�aliser <u>la division</u> entre deux tableaux de m�me dimension.
   * Un nouveau tableau sera retourn� contenant le r�sultat de l'op�ration math�matique.
   * 
   * @param tab1 Le 1er tableau.
   * @param tab2 Le 2e tableau.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la m�me taille.
   */
  public static double[] divide(double[] tab1, double[] tab2) throws ArrayIndexOutOfBoundsException
  {
    if(tab1.length != tab2.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 008 : Les deux tableaux n'ont pas la m�me longueur (" + tab1.length + " et " + tab2 + ").");
    
    throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
  }
  
  /**
   * M�thode pour r�aliser <u>l'exponentiel</u> d'un tableau.
   * Un nouveau tableau sera retourn� contenant le r�sultat de l'op�ration math�matique.
   * 
   * @param tab Le tableau.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   */
  public static double[] exp(double[] tab)
  {
    double[] result = new double[tab.length];
    
    for(int i = 0; i < tab.length; i++)
      result[i] = Math.exp(tab[i]);
    
    return result;
  }
  
  /**
   * M�thode pour r�aliser <u>la puissance</u> d'un tableau.
   * Un nouveau tableau sera retourn� contenant le r�sultat de l'op�ration math�matique.
   * 
   * @param tab Le tableau.
   * @param n La puissance.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   */
  public static double[] pow(double[] tab, double n)
  {
    throw new SNoImplementationException("La m�thode n'a pas �t� impl�ment�e.");
  }
  
  /**
   * M�thode pour r�aliser <u>le sinus</u> d'un tableau. Les valeurs du tableau seront consid�r�es en <b>radian</b>.
   * Un nouveau tableau sera retourn� contenant le r�sultat de l'op�ration math�matique.
   * 
   * @param tab Le tableau.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   */
  public static double[] sin(double[] tab)
  {
    double[] result = new double[tab.length];
    
    for(int i = 0; i < tab.length; i++)
      result[i] = Math.sin(tab[i]);
    
    return result;
  }
  
  /**
   * M�thode pour r�aliser <u>le cosinus</u> d'un tableau. Les valeurs du tableau seront consid�r�es en <b>radian</b>.
   * Un nouveau tableau sera retourn� contenant le r�sultat de l'op�ration math�matique.
   * 
   * @param tab Le tableau.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   */
  public static double[] cos(double[] tab)
  {
    double[] result = new double[tab.length];
    
    for(int i = 0; i < tab.length; i++)
      result[i] = Math.cos(tab[i]);
    
    return result;
  }
  
  /**
   * M�thode pour r�aliser la fonction ReLU d'un tableau. 
   * 
   * @param tab Le tableau.
   * @return Le tableau contenant le r�sultat de l'op�ration math�matique.
   */
  public static double[] relu(double[] tab)
  {
    double[] result = new double[tab.length];
    
    for(int i = 0; i < tab.length; i++)
      result[i] = Math.max(0.0, tab[i]);
    
    return result;
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
   * <p>
   * M�thode pour obtenir la <b>plus grande valeur sans �tre z�ro</b> d'un tableau.
   * </p>
   * <p>
   * Cependant, si le <u>tableau contient uniquement des z�ros</u>, la valeur retourn�e sera z�ro.
   * </p>
   * 
   * @param tab Le tableau.
   * @return La plus grande valeurs (sauf z�ro) du tableau.
   */
  public static double findMaxNotZero(double[] tab)
  {
    double max = 0.0;
    int i = 0;
    
    // Obtenir le 1er chiffre pas �gale � z�ro.
    while(max == 0.0 && i < tab.length)
    {
      if(tab[i] != 0.0)
        max = tab[i];
      
      i++;
    }
     
    // V�rifier si l'on a pas trouv� de chiffre autre que z�ro.
    if(i == tab.length)
      return 0.0;
    
    // Chercher le chiffre le plus grand sauf pour z�ro.
    for(i = 0; i < tab.length; i++)
      if(tab[i] != 0.0)
        if(tab[i] > max)
          max = tab[i];
    
    return max;
  }
  
  /**
   * M�thode pour obtenir la <b>plus grande valeur absolue</b> d'un tableau.
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
   * M�thode pour obtenir la plus grande valeur d'un tableau en explorant uniquement des indices du tableau autoris�.
   * Les indices autoris�s sont d�termin� par le second tableau o� 1.0 signifie que l'indice peut �tre autoris�e et 0.0 n'aurorisant pas.
   * Si aucun indice est autoris�, une exception sera lanc�e.
   * 
   * @param array Le tableau.
   * @param authorization Le tableau d'autorisation.
   * @throws IllegalArgumentException Si aucun indice est autoris� dans la recherche.
   * @throws ArrayIndexOutOfBoundsException Si les deux tableaux n'ont pas la m�me taille.
   * @return La valeur maximale autoris�e dans le tableau.
   */
  public static double findMaxInAllowedIndex(double[] array, boolean[] authorization) throws IllegalArgumentException, ArrayIndexOutOfBoundsException
  {
    if(array.length != authorization.length)
      throw new ArrayIndexOutOfBoundsException("Erreur SArrays 009 : Les deux tableaux n'ont pas la m�me longueur (" + array.length + " et " + authorization + ").");
    
    double value = Double.NEGATIVE_INFINITY;
    
    for(int i = 0; i < array.length; i++)
      if(authorization[i])
        if(array[i] > value)
          value = array[i];
    
    // V�rification s'il n'y a pas eu d'autorisation.
    if(Double.isInfinite(value))
      throw new IllegalArgumentException("Error SArrays 010 : Il n'y a pas eu de recherche dans le tableau " + Arrays.toString(array) + " dont l'autorisation est " + Arrays.toString(authorization));
    
    return value;
  }
  
  /**
   * M�thode pour obtenir l'indice dans le tableau de la <b>valeur la plus grande</b>.
   * 
   * @param tab Le tableau.
   * @return L'indice dans le tableau o� l'on trouve la valeur la plus grande.
   */
  public static int findIndexOfMax(double[] tab)
  {
    // Choisir la meilleure cote.
    int index = 0;
    double value = tab[0];
    
    // Parcourir le tableau � la recherche de la plus grande valeur.
    for(int i = 1; i < tab.length; i++)
      if(tab[i] > value)
      {
        value = tab[i];
        index = i;
      }
    
    return index;
  }
  
  /**
   * M�thode pour obtenir l'indice dans le tableau de la <b>valeur absolue la plus grande</b>.
   * 
   * @param tab Le tableau.
   * @return L'indice dans le tableau o� l'on trouve la valeur absolue la plus grande.
   */
  public static int findIndexOfAbsoluteMax(double[] tab)
  {
    // Choisir la meilleure cote.
    int index = 0;
    double value = Math.abs(tab[0]);
    
    // Parcourir le tableau � la recherche de la plus grande valeur.
    for(int i = 1; i < tab.length; i++)
      if(Math.abs(tab[i]) > value)
      {
        value = Math.abs(tab[i]);
        index = i;
      }
    
    return index;
  }
  
  /**
   * M�thode pour obtenir la <b>plus petite valeur</b> d'un tableau.
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
   * M�thode pour obtenir la <b>plus petite valeur</b> d'un tableau.
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
      throw new SRuntimeException("Error SArrays 011 : La borne minimale " + min + " et la borne maximale " + max + " sont mal d�finies.");
    
    int minValue = findMin(data);
    int maxValue = findMax(data);
    
    double[] result = new double[data.length];
    
    // It�rer sur l'ensemble des �l�ments du tableau.
    for(int i = 0; i < result.length; i++)
    {
      double t = SMath.reverseLinearInterpolation((double)data[i], (double)minValue, (double)maxValue);
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
   * @throws IllegalArgumentException Si les bornes sont mal d�finies.
   */
  public static int[] mappingDoubleToInt(double[] data, int min, int max) throws IllegalArgumentException
  {
    return mappingDoubleToInt(data, findMin(data), findMax(data), min, max);
  }
  
  /**
   * ...
   * 
   * @param data Les donn�es
   * @param double_min La borne minimale des donn�es
   * @param double_max La borne maximale des donn�es
   * @param int_min La borne minimale des entiers
   * @param int_max La borne maximale des entiers
   * @return Le tableau converti
   * @throws IllegalArgumentException Si les bornes sont mal d�finies
   */
  public static int[] mappingDoubleToInt(double[] data, double double_min, double double_max, int int_min, int int_max) throws IllegalArgumentException
  {
    if(int_min > int_max)
      throw new IllegalArgumentException("Error SArrays 012 : La borne enti�re minimale " + int_min + " et la borne enti�re maximale " + int_max + " sont mal d�finies.");
    
    if(double_min > double_max)
      throw new IllegalArgumentException("Error SArrays 013 : La borne double minimale " + double_min + " et la borne double maximale " + double_max + " sont mal d�finies.");
       
    int[] result = new int[data.length];
    
    // It�rer sur l'ensemble des �l�ments du tableau.
    for(int i = 0; i < result.length; i++)
    {
      double t = SMath.reverseLinearInterpolation(data[i], double_min, double_max);
      result[i] = (int)SMath.linearInterpolation(int_min, int_max, t);
    }
    
    return result;
  }
  
  /**
   * <p>
   * M�thode permettant de g�n�rer un tableau contenant les �l�ments identique de deux tableaux de valeur <ul>pr�alablement tri�</ul>.
   * </p>
   * 
   * <p>
   * <b>REMARQUE</b> : Le fonctionnement de cette m�thode ne sera pas valide si les deux tableaux pass�s en param�tre ne sont pas p�alablement tri�.
   * </p>
   * R�f�rence : https://stackoverflow.com/questions/32676381/find-intersection-of-two-arrays
   * 
   * @param tab1 Le 1ier tableau � comparer.
   * @param tab2 Le 2i�me tableau � comparer.
   * @return Un tableau comprenant les �l�ments identiques de deux tableaux (l'intersection des deux tableaux).
   */
  public static double[] intersectionSortedArray(double[] tab1, double[] tab2)
  {
    return intersectionSortedArray(tab1, tab2, SMath.EPSILON);
  }
  
  /**
   * <p>
   * M�thode permettant de g�n�rer un tableau contenant les �l�ments identique de deux tableaux de valeur <ul>pr�alablement tri�</ul>.
   * </p>
   * 
   * <p>
   * <b>REMARQUE</b> : Le fonctionnement de cette m�thode ne sera pas valide si les deux tableaux pass�s en param�tre ne sont pas p�alablement tri�.
   * </p>
   * R�f�rence : https://stackoverflow.com/questions/32676381/find-intersection-of-two-arrays
   * 
   * @param tab1 Le 1ier tableau � comparer.
   * @param tab2 Le 2i�me tableau � comparer.
   * @param epsilon La pr�cision de la comparaison.
   * @return Un tableau comprenant les �l�ments identiques de deux tableaux (l'intersection des deux tableaux).
   */
  public static double[] intersectionSortedArray(double[] tab1, double[] tab2, double epsilon)
  {
    double intersection[] = new double[Math.min(tab1.length, tab2.length)];
    int count = 0;

    int i = 0; int j = 0;
    while (i < tab1.length && j < tab2.length)
    {
      // V�rifier s'il y a �galit�.
      if(SMath.nearlyEquals(tab1[i], tab2[j], epsilon)) 
      {
        intersection[count] = tab1[i];
        count++;
        i++;
        j++;
      }
      else
        // Avancer dans la recherche des �l�ments.
        if (tab1[i] < tab2[j]) 
          i++;                    // avancer dans le tableau 1
        else
          j++;                    // avancer dans le tableau 2
    }

    // Construire un tableau avec l'espace m�moire minimum.
    double[] result = new double[count];
    for(int k = 0; k < result.length; k++)
      result[k] = intersection[k];
    
    return result;
  }
  
  /**
   * M�thode pour changer la dimension d'un tableau. Si la nouvelle taille est inf�rieure � la pr�c�dente, les derniers �l�ments ne seront pas copi�.
   * Si la nouvelle taille est sup�rieure � la pr�c�dente, des valeurs nulles sont attribu�es aux positions sup�rieures. 
   * 
   * @param array Le tableau � copier.
   * @param new_size La nouvelle dimension du tableau.
   * @return Un tableau avec une copie des donn�es avec une taille diff�rente.
   */
  public static double[] resize(double[] array, int new_size)
  {
      // Remplir un nouveau tableau ayant seulement la taille demand�.
      // Tous les �l�ments du tableau pr�c�dent 
      double[] result = new double[new_size];
      
      for(int i = 0; i < new_size; i++)
        result[i] = array[i];
     
      return result;
  }
  
  /**
   * M�thode pour changer la dimension d'un tableau. Si la nouvelle taille est inf�rieure � la pr�c�dente, les derniers �l�ments ne seront pas copi�.
   * Si la nouvelle taille est sup�rieure � la pr�c�dente, des valeurs nulles sont attribu�es aux positions sup�rieures. 
   * 
   * @param array Le tableau � copier.
   * @param new_size La nouvelle dimension du tableau.
   * @return Un tableau avec une copie des donn�es avec une taille diff�rente.
   */
  public static int[] resize(int[] array, int new_size)
  {
      // Remplir un nouveau tableau ayant seulement la taille demand�.
      // Tous les �l�ments du tableau pr�c�dent 
      int[] result = new int[new_size];
      
      for(int i = 0; i < new_size; i++)
        result[i] = array[i];
     
      return result;
  }
  
  /**
   * M�thode pour faire l'�criture d'un tableau dans un fichier texte.
   * 
   * @param file_name Le nom du fichier.
   * @param values Le tableau des valeurs � �crire.
   */
  public static void write(String file_name, double[] values)
  {
    try{
      
      FileWriter fw = new FileWriter(file_name);
      BufferedWriter bw = new BufferedWriter(fw);
    
      // Faire l'�criture de chaque valeur et changer de ligne.
      for(int i = 0; i < values.length; i++)
      {
        bw.write(Double.toString(values[i]));
        bw.newLine();
        bw.flush();
      }
      
      bw.close(); //  fermer celui-ci en premier, sinon, ERROR !!!
      fw.close();   
      
    }catch(IOException ioe){
      ioe.printStackTrace();
    }
  }
  
  /**
   * M�thode pour convertir les donn�es d'un tableau de donn�e en un tableau de probabilit�.
   * Les donn�es seront <u>trait�es en valeur absolue</u>.
   * Si le tableau contient uniquement des z�ros, il restera avec des z�ros et la probablitit� totale du tableau sera z�ro (et non 1.0).
   * 
   * @param array Le tableau � convertir en probabilit�.
   * @throws IllegalArgumentException Si la somme des �l�ments du tableau est �gale � z�ro.
   */
  public static void convertToAbsoluteProbability(double[] array) throws IllegalArgumentException
  {
    // Pour calculer la somme des �l�ments du tableau.
    double sum = 0.0;
    
    // Faire la somme du tableau et v�rifier qu'il n'y ait pas de valeur n�gative.
    for(int i = 0; i < array.length; i++)
      sum += Math.abs(array[i]);
    
    // Faire la normalisation en probabilit�.
    if(sum == 0.0)
      throw new IllegalArgumentException("Error SArrays 014 : Le tableau suivant ne peut pas �tre convertie en probabilit� : " + Arrays.toString(array));
    
    for(int i = 0; i < array.length; i++)
      array[i] = Math.abs(array[i]) / sum;
  }
    
}
