/**
 * 
 */
package sim.math;

import java.util.Arrays;

import sim.exception.SConstructorException;
import sim.util.SArrays;

/**
 * La classe <b>SQuaternion</b> représente un quaternion.
 * 
 * @author Simon Vezina
 * @since 2020-04-10
 * @version 2022-06-02 (version labo – Le champ électrique v1.1.0)
 */
public class SQuaternion {

  /**
   * La constante <b>ZERO_ROTATION_AXIS</b> représente l'axe de rotation d'un quaternion sans rotation.
   */
  private static final SVector3d ZERO_ROTATION_AXIS = SVector3d.UNITARY_X;
  
  /**
   * La constante <b>ZERO</b> correspond à un quaternion nul où l'ensemble de ses composants sont zéro (0.0, 0.0, 0.0, 0.0).
   */
  public static final SQuaternion ZERO = new SQuaternion(0.0, 0.0, 0.0, 0.0);
  
  /**
   * La constante <b>ZERO_ROTATION</b> correspond à un quaternion de rotation (module égale à 1), mais dont la rotation est nulle.
   */
  public static final SQuaternion ZERO_ROTATION = new SQuaternion(0.0, ZERO_ROTATION_AXIS);
  
  /**
   * La variable <b>q</b> représente un tableau contenant les 4 composantes du quaternion équant q = q0 + qx*i + qy*j + qz*k = (q0, qx, qy, qz).
   */
  private final double[] q;
  
  //----------------
  // CONSTRUCTEUR //
  //----------------
  
  /**
   * Constructeur d'un quaternion.
   * 
   * @param q_w La composante constante du quaternion (w).
   * @param q_x La composante x du quaternion (x).
   * @param q_y La composante y du quaternion (y).
   * @param q_z La composante z du quaternion (z).
   */
  public SQuaternion(double q_w, double q_x, double q_y, double q_z)
  {
    this.q = new double[4];
    
    this.q[0] = q_w;
    this.q[1] = q_x;
    this.q[2] = q_y;
    this.q[3] = q_z;
  }
  
  /**
   * Constructeur d'une quaternion à partir d'un tableau de 4 données.
   * 
   * @param q Le tableau de 4 données
   * @throws SConstructorException Si le tableau ne contient pas exactement 4 données.
   */
  public SQuaternion(double... q) throws SConstructorException
  {
    if(q.length != 4)
      throw new SConstructorException("Erreur SQuaternion 001 : La taille du tableau définissant le quaternion est de " + q.length + " ce qui n'est pas égal à 4.");
    
    this.q = new double[4];
    
    this.q[0] = q[0];
    this.q[1] = q[1];
    this.q[2] = q[2];
    this.q[3] = q[3];
  }
  
  /**
   * Constructeur d'un quaternion permettant de constuire un quaternion représentant une rotation 
   * d'un angle donné autour d'un axe donné. Pour que la construction du quaternion puisse corresponde
   * à une rotation, l'angle doit être en radiant et l'axe de rotation doit être unitaire.
   * 
   * @param angle L'angle de rotation <u>en radian</u>.
   * @param axis L'axe de la rotation sous la représentation d'un vecteur unitaire (longueur égal à 1).
   * @throws SconstructorException Si l'axe de rotation n'est pas un vecteur unitaire.
   */
  public SQuaternion(double angle, SVector3d axis) throws SConstructorException
  {
    // Cas particulier : Aucune angle de rotation, alors définir un axe par défaut.
    // Validation : Si aucun axe de rotation, alors le quaternion n'est pas valide.
    if(angle == 0.0)
      axis = ZERO_ROTATION_AXIS;
    else
      if(axis.isZero())
        throw new SConstructorException("Erreur SQuaternion 002 : Le quaternion ne peut pas être défini, car l'angle de rotation égal " + angle + ", mais l'axe de rotation est nul.");
        
    // Validation : Si l'axe de rotation n'est pas unitaire, alors le quaternion n'est pas valide.
    if(!axis.isUnitVector())
      throw new SConstructorException("Erreur SQuaternion 003 : Le quaternion ne peut pas représenter une rotation, car l'axe de rotation axis = " + axis + " n'est pas unitaire.");
          
    //  Si l'angle de rotation est négatif, il faut inverser l'axe de rotation.
    if(angle < 0)
    {
      angle *= -1;
      axis = axis.multiply(-1.0);
    }
      
    // Allocation de la mémoire.
    this.q = new double[4];
    
    // Définir les compostantes du quaternion.
    double sin = Math.sin(angle/2.0);
    
    this.q[0] = Math.cos(angle/2.0);
    this.q[1] = sin * axis.getX();
    this.q[2] = sin * axis.getY();
    this.q[3] = sin * axis.getZ();
  }
  
  
   
  //------------
  // MÉTHODES //
  //------------
  
  /**
   * Méthode pour obtenir un quaternion unitaire à partir d'un vecteur 3d contenant les trois composante (x, y, z) du quaternion.
   * Puisque le quaternion calculé sera unitaire, nous pouvons déduire la composante w de (w, x, y, z) afin de reconstruire le quaternion à partir de 3 composantes.
   *  
   * @param v Le vecteur utilisé pour reconstruire le quaternion.
   * @return Le quaternion unitaire.
   * @throws Si le vecteur ne permet pas de générer un quaternion unitaire.
   */
  public static SQuaternion toUnitQuaternion(SVector3d v) throws IllegalArgumentException
  {
    double w_2 = 1.0 - v.getX()*v.getX() - v.getY()*v.getY() - v.getZ()*v.getZ();
    
    if(w_2 < 0.0)
      throw new IllegalArgumentException("Erreur SQuaternion 003 : Le vecteur v = " + v + " ne permet pas de générer un quaterion unitaire, car w*w = " + w_2 + " est négatif.");
    
    return new SQuaternion(Math.sqrt(w_2), v.getX(), v.getY(), v.getZ());
  }
  
  /**
   * Méthode pour obtenir un quaternion à partir d'un vecteur 3d où la composante q_w du quaternion sera zéro
   * et les composante q_x, q_y et q_z seront égales aux composante du vecteur. 
   * Ainsi, le quaternion construit sera q = (0, v_x, v_y, v_z).
   * Cette construction de quaternion permet d'obtenir un quaternion de type position.
   * 
   * @param v Le vecteur.
   * @return Le quaternion de type position.
   */
  public static SQuaternion toPositionQuaternion(SVector3d v)
  {
    return new SQuaternion(0.0, v.getX(), v.getY(), v.getZ());
  }
  
  /**
   * Méthode pour obtenir un quaternion de rotation à partir d'un angle de rotation et d'un axe de rotation.
   * Le vecteur représentant l'axe de rotation sera normalisé dans le processus de création du quaternion.
   * Seul l'angle en radians sera considéré pour établir l'angle de rotation du quaternion.
   * 
   * @param radians L'angle de rotation.
   * @param v L'axe de rotation.
   * @return Le quaternion de type rotation.
   */
  public static SQuaternion toRotationQuaternion(double radians, SVector3d v) throws IllegalArgumentException
  {
    // Cas particulier : Aucun axe de rotation.
    if(v.isZero())
      if(radians == 0.0)
        return new SQuaternion(radians, v);
      else
        throw new IllegalArgumentException("Erreur SQuaternion 004 : Le quaternion ne peut pas être défini, car l'angle de rotation égal " + radians + ", mais l'axe de rotation est nul.");
      
    // Cas général : Construire un quanternion avec un axe de rotation normalisé.
    return new SQuaternion(radians, v.normalize());
  }
  
  /**
   * Méthode pour obtenir le quaternion de la rotation permettant d'orienter le vecteur itnial dans l'orientation du vecteur final.
   * 
   * @param initial_vector Le vecteur initial.
   * @param final_vector Le vecteur final.
   * @return Le quaternion de la rotation.
   */
  public static SQuaternion toRotationQuaternion(SVector3d initial_vector, SVector3d final_vector) throws IllegalArgumentException
  {
    // Vérification que l'on peut normaliser nos vecteurs.
    try {
      
      initial_vector = initial_vector.normalize();
      final_vector = final_vector.normalize();
    
    }catch(SImpossibleNormalizationException e) {
      throw new IllegalArgumentException(e);
    }
    
    // Test du produit scalaire.
    double dot = initial_vector.dot(final_vector);
    
    // Cas particulier : Est-ce que est orientation son parallèle ou anti-parallèle.
    if(SMath.nearlyEquals(dot, 1.0))
      return ZERO_ROTATION;
    else
      if(SMath.nearlyEquals(dot, -1.0))
      {
        // Sous cas particulier : Est-ce que le vecteur de référence est l'axe z, dont le vecteur final sera -z.
        if(initial_vector.equals(SVector3d.UNITARY_Z))
          return new SQuaternion(Math.PI, SVector3d.UNITARY_X);
        else
          throw new IllegalArgumentException("Ca l'aire qu'il faut gèrer un super cas de rotation de 180 degré");
      }
    
    // Définir l'angle avec le produit scalaire entre les deux vecteurs normalisé.
    // Définir l'axe de rotation avec le produit vectoriel des deux vecteurs dont le résultat sera normalisé.
    return new SQuaternion(Math.acos(dot), initial_vector.cross(final_vector).normalize());    
  }
  
  /**
   * ...
   * 
   * @return
   */
  public double getW()
  {
    return q[0];
  }
  
  /**
   * ...
   * 
   * @return
   */
  public double getX()
  {
    return q[1];
  }
  
  /**
   * ...
   * 
   * @return
   */
  public double getY()
  {
    return q[2];
  }
  
  /**
   * ...
   * 
   * @return
   */
  public double getZ()
  {
    return q[3];
  }
  
  /**
   * Méthode pour obtenir la partie vectorielle du quaternion. Le vecteur correspond à (qx, qy, qz).
   * 
   * @return La partie vectorielle (qx, qy, qz) du quaternion (qw, qx, qy, qz).
   */
  public SVector3d getVector()
  {
    return new SVector3d(this.q[1], this.q[2], this.q[3]);
  }
  
  /**
   * Méthode pour évaluer le module d'un quaternion.
   * 
   * @return Le module du quaternion.
   */
  public double modulus()
  {
    return Math.sqrt(this.q[0]*this.q[0] + this.q[1]*this.q[1] + this.q[2]*this.q[2] + this.q[3]*this.q[3]);
  }
  
  /**
   * Méthode pour normaliser le quaternion.
   * 
   * @return Le quaternion normalisé.
   */
  public SQuaternion normalize()
  {
    double norm = this.modulus();
    
    return new SQuaternion(this.q[0] / norm, this.q[1] / norm, this.q[2] / norm, this.q[3] / norm);
  }
  
  /**
   * 
   * @param q
   * @return
   */
  public SQuaternion add(SQuaternion q)
  {
    SQuaternion empty = new SQuaternion();
    
    SArrays.add(this.q, q.q, empty.q);
    
    return empty;
  }
  
  /**
   * 
   * @param q
   * @return
   */
  public SQuaternion substract(SQuaternion q)
  {
    SQuaternion empty = new SQuaternion();
    
    SArrays.substract(this.q, q.q, empty.q);
    
    return empty;
  }
  
  /**
   * ...
   * 
   * @param s
   * @return
   */
  public SQuaternion multiply(double s)
  {
    SQuaternion empty = new SQuaternion();
    
    SArrays.multiply(this.q, s, empty.q);
    
    return empty;
  }
  
  /**
   * Méthode pour effectuer le produit de deux quaternion.
   * 
   * @param q Le quaternion à multiplier.
   * @return Le résultat de la multiplication de deux quaternions.
   */
  public SQuaternion multiply(SQuaternion q)
  {
    double q0 = this.q[0]*q.q[0] - this.q[1]*q.q[1] - this.q[2]*q.q[2] - this.q[3]*q.q[3];
    double q1 = this.q[0]*q.q[1] + this.q[1]*q.q[0] + this.q[2]*q.q[3] - this.q[3]*q.q[2];
    double q2 = this.q[0]*q.q[2] - this.q[1]*q.q[3] + this.q[2]*q.q[0] + this.q[3]*q.q[1];
    double q3 = this.q[0]*q.q[3] + this.q[1]*q.q[2] - this.q[2]*q.q[1] + this.q[3]*q.q[0];
    
    return new SQuaternion(q0, q1, q2, q3);
  }
  
  /**
   * Méthode pour obtenir le conjugé d'un quaternion.
   * 
   * @return
   */
  public SQuaternion conjugate()
  {
    return new SQuaternion(this.q[0], -1*this.q[1], -1*this.q[2], -1*this.q[3]);
  }
  
  /**
   * Méthode pour obtenir l'inverse d'un quaternion.
   * 
   * @return L'inverse du quaternion.
   */
  public SQuaternion inverse() throws IllegalArgumentException
  {
    double norm = this.modulus();
    
    if(SMath.nearlyZero(norm))
      throw new IllegalArgumentException("Erreur 004 : L'inversion du quaternion est impossible, car le module est nul.");
    
    double div = norm*norm;
    
    return this.conjugate().multiply(1.0 / div); 
  }
  
  
  
  /**
   * Méthode pour déterminer si le quaternion est normalisé.
   * 
   * @return <b>true</b> si le quaternion est normalisé et <b>false</b> sinon.
   */
  public boolean isNormalized()
  {
    return SMath.nearlyEquals(this.modulus(), 1.0);
  }
  
  /**
   * Méthode pour déterminer si le quaternion est de type <i>rotation</i>.
   * Un quaternion pourra représente une rotation (type rotation) si son module est égal à 1.
   * 
   * @return <b>true</b> si le quaternion est de type rotation et <b>false</b> sinon.
   */
  public boolean isRotationQuaternion()
  {
    return this.isNormalized();
  }
  
  /**
   * Méthode pour déterminer si le quaternion est de type <i>position</i>.
   * Un quaternion pourra représenter une position (type position) si sa compostante q_w (composante constante) est égal à 0.
   * 
   * @return <b>true</b> si le quaternion est de type position et <b>false</b> sinon.
   */
  public boolean isPositionQuaternion()
  {
    return SMath.nearlyZero(this.q[0]);
  }
  
  /**
   * Méthode pour obtenir l'angle de rotation associé au quaternion. Celui-ci doit être normalisé pour représenter une matrice de rotation.
   * 
   * @return L'angle de rotation <u>en radian</u> du quaternion.
   * @throws IllegalArgumentException Si le quaternion n'est pas normalisé.
   */
  public double rotationAngle() throws IllegalArgumentException
  {
    if(!this.isNormalized())
      throw new IllegalArgumentException("Erreur SQuaternion 002 : L'angle de rotation ne peut pas être défini pour ce quaternion, car celui-ci n'est pas normalisé.");
    
    // Dans le cas d'un quaternion normalisé, nous avons q0 = cos(theta/2).
    // Nous allons prendre la valeur positive de l'arc cosius.
    return 2.0*Math.acos(this.q[0]);
  }
  
  /**
   * Méthode pour obtenir l'axe de rotation associé au quaternion. Celui-ci doit être normalisé pour représenter une matrice de rotation.
   * 
   * @return L'axe de rotation du quaternion (normalisé).
   * @throws IllegalArgumentException Si le quaternion n'est pas normalisé.
   */
  public SVector3d rotationAxis() throws IllegalArgumentException
  {
    if(!this.isNormalized())
      throw new IllegalArgumentException("Erreur SQuaternion 003 : L'axe de rotation ne peut pas être défini pour ce quaternion, car celui-ci n'a pas été normalisé. Le module actuel est égal à " + this.modulus() + ".");
  
    // Obtenir l'angle de rotation en radian.
    double angle = this.rotationAngle();
    
    // Dans le cas d'un quaternion normalisé, nous avons v = sin(theta/2)*u  où u est l'axe de rotation normalisé et v = (qx, qy, qz).
    return this.getVector().multiply(1.0 / Math.sin(angle/2.0));
  }
  
  /**
   * Méthode pour appliquer une rotation sur un vecteur v.
   * 
   * @param v
   * @param q
   * @return
   * @throws IllegalArgumentException Si le quaternion n'est pas normalisé.
   */
  public SVector3d rotation(SVector3d v) throws IllegalArgumentException
  {
    if(!this.isNormalized())
      throw new IllegalArgumentException("Erreur SQuaternion 004 : La rotation du vecteur ne peut pas être réalisée, car le quaternion n'a pas été normalisé.");
  
    return this.multiply(SQuaternion.toPositionQuaternion(v)).multiply(this.conjugate()).getVector();
  }

 
  
  
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(q);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    
    if (this == obj)
      return true;
    
    if (obj == null)
      return false;
    
    if (getClass() != obj.getClass())
      return false;
    
    SQuaternion other = (SQuaternion) obj;
    
    if (!SArrays.nearlyEquals(q, other.q))
      return false;
    
    return true;
  }

  @Override
  public String toString() 
  {
    return "[" + this.q[0] + ", " + this.q[1] + ", " + this.q[2] + ", " + this.q[3] +"]"; 
  }
  
}
