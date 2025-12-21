/**
 * 
 */
package sim.math;

import java.util.Arrays;

import sim.exception.SNoImplementationException;

/**
 * La classe <b>SMatrix4x4</b> repr�sentant une matrice de '4' lignes et '4' colonnes. 
 * Les composantes de la matrice respectent la notation suivante :
 * <p>M[i][j] = </p> 
 * <p>| m00  m01  m02  m03 |</p>
 * <p>| m10  m11  m12  m13 |</p>
 * <p>| m20  m21  m22  m23 |</p>
 * <p>| m30  m31  m32  m33 |</p>
 *  
 * Puisque l'impl�mentation de cette matrice se fera � l'aide d'un tableau � une dimension, on retrouve l'indexation suivante :
 * <p>M[i][j] = </p> 
 * <p>|  0    1    2    3  |</p>
 * <p>|  4    5    6    7  |</p>
 * <p>|  8    9   10   11  |</p>
 * <p>| 12   13   14   15  |</p>
 * 
 * @author Simon V�zina
 * @since 2015-05-27
 * @version 2017-12-20 (version labo � Le ray tracer v2.1)
 */
public class SMatrix4x4 {

  //-------------
  // VARIABLES //
  //-------------
  
	/**
	 * La variable 'matrix' correspond � la matrice 4x4 contenant les 16 �l�ments.
	 */
	private final double[] matrix;
	
	//-----------------
	// CONSTRUCTEURS //
	//-----------------
	
	/**
	 * Constructeur de la matrice nulle (tous les �l�ments M[i][j] seront nuls.)  
	 */
	public SMatrix4x4()
	{
		this(
			
			0.0, 0.0, 0.0, 0.0,
			0.0, 0.0, 0.0, 0.0,
			0.0, 0.0, 0.0, 0.0,
			0.0, 0.0, 0.0, 0.0
			
			);
	}

	/**
	 * Constructeur d'une matrice 4x4 avec param�tres en coordonn�e M[i][j] tel que i correspond � la ligne
	 * et j correspond � la colonne. Visuellement, l'organisation des donn�es correspond � la matrice suivante :
	 * <p>M = | m00  m01  m02  m03 |</p>
	 * <ul>| m10  m11  m12  m13 |</ul>
	 * <ul>| m20  m21  m22  m23 |</ul>
	 * <ul>| m30  m31  m32  m33 |</ul>
	 * 
	 * @param m00 - L'�l�ment 00 de la matrice (colonne 1 : ligne 1)
	 * @param m01 - L'�l�ment 01 de la matrice (colonne 2 : ligne 1)
	 * @param m02 - L'�l�ment 02 de la matrice (colonne 3 : ligne 1)
	 * @param m03 - L'�l�ment 03 de la matrice (colonne 4 : ligne 1)
	 * @param m10 - L'�l�ment 10 de la matrice (colonne 1 : ligne 2)
   * @param m11 - L'�l�ment 11 de la matrice (colonne 2 : ligne 2)
   * @param m12 - L'�l�ment 12 de la matrice (colonne 3 : ligne 2)
   * @param m13 - L'�l�ment 13 de la matrice (colonne 4 : ligne 2)
   * @param m20 - L'�l�ment 20 de la matrice (colonne 1 : ligne 3)
   * @param m21 - L'�l�ment 21 de la matrice (colonne 2 : ligne 3)
   * @param m22 - L'�l�ment 22 de la matrice (colonne 3 : ligne 3)
   * @param m23 - L'�l�ment 23 de la matrice (colonne 4 : ligne 3)
   * @param m30 - L'�l�ment 30 de la matrice (colonne 1 : ligne 4)
   * @param m31 - L'�l�ment 31 de la matrice (colonne 2 : ligne 4)
   * @param m32 - L'�l�ment 32 de la matrice (colonne 3 : ligne 4)
   * @param m33 - L'�l�ment 33 de la matrice (colonne 4 : ligne 4) 
	 */
	public SMatrix4x4(double m00, double m01, double m02, double m03,
        			      double m10, double m11, double m12, double m13,
        			      double m20, double m21, double m22, double m23,
        			      double m30, double m31, double m32, double m33)
	{
	  matrix = new double[]{
      m00,	  m01,	  m02,	 m03,
      m10,	  m11,	  m12,	 m13,
      m20,	  m21,	  m22,	 m23,
      m30,	  m31,	  m32,	 m33  };
	}
	
	/**
	 * Constructeur d'une matrice avec tableau des 16 composantes de la matrice.
	 * 
	 * @param m - Le tableau des 16 composantes de la matrice.
	 */
  private SMatrix4x4(double[] m)
  {
    matrix = m;
  }
	
  //------------
  // M�THODES //
  //------------
  
	/**
	 * M�thode qui effectue le produit matriciel entre deux matrices.
	 * La <b>matrice M1</b> qui lance l'appel de cette m�thode sera la matrice de <b>gauche</b> et la <b>matrice M2</b>
	 * pass�e en param�tre sera la matrice de <b>droite</b>. Cette pr�cision est n�cessaire �tant donn�
	 * que le <b>produit matriciel n'est pas commutatif</b>.
	 * 
	 * <p>Le r�sutat du produit matriciel M = M1*M2 correspond au calcul</p>
	 * <ul>M[i][j] = SOMME sur k : M1[i][k]*M2[k][j]</ul>
	 * <p>o� M[i][j] est une composantes de la matrice M.</p>
	 * 
	 * @param M - La matrice M2 de droite � multiplier avec la matrice M1 lan�ant l'appel de la m�thode.
	 * @return Le r�sultat M du produit matriciel.
	 */
	public SMatrix4x4 multiply(SMatrix4x4 M) 
	{
		double[] m = M.matrix;
		
		return new SMatrix4x4(	
				
				// Premiere ligne
				matrix[0]*m[0] + matrix[1]*m[4] + matrix[2]*m[8] + matrix[3]*m[12],         
				matrix[0]*m[1] + matrix[1]*m[5] + matrix[2]*m[9] + matrix[3]*m[13],
				matrix[0]*m[2] + matrix[1]*m[6] + matrix[2]*m[10] + matrix[3]*m[14],
				matrix[0]*m[3] + matrix[1]*m[7] + matrix[2]*m[11] + matrix[3]*m[15],

				// Deuxieme ligne
				matrix[4]*m[0] + matrix[5]*m[4] + matrix[6]*m[8] + matrix[7]*m[12],         
				matrix[4]*m[1] + matrix[5]*m[5] + matrix[6]*m[9] + matrix[7]*m[13],
				matrix[4]*m[2] + matrix[5]*m[6] + matrix[6]*m[10] + matrix[7]*m[14],
				matrix[4]*m[3] + matrix[5]*m[7] + matrix[6]*m[11] + matrix[7]*m[15],

				// Troisieme ligne
				matrix[8]*m[0]	+ matrix[9]*m[4] + matrix[10]*m[8] + matrix[11]*m[12],        
				matrix[8]*m[1]	+ matrix[9]*m[5] + matrix[10]*m[9] + matrix[11]*m[13],
				matrix[8]*m[2]	+ matrix[9]*m[6] + matrix[10]*m[10] + matrix[11]*m[14],
				matrix[8]*m[3]	+ matrix[9]*m[7] + matrix[10]*m[11] + matrix[11]*m[15],

				// Quatrieme ligne
				matrix[12]*m[0]	+ matrix[13]*m[4] + matrix[14]*m[8]	+ matrix[15]*m[12], 				
				matrix[12]*m[1]	+ matrix[13]*m[5] + matrix[14]*m[9]	+ matrix[15]*m[13],
				matrix[12]*m[2]	+ matrix[13]*m[6] + matrix[14]*m[10] + matrix[15]*m[14],
				matrix[12]*m[3]	+ matrix[13]*m[7] + matrix[14]*m[11] + matrix[15]*m[15]
						
				);
	}

	/**
	 * M�thode qui effectue le produit entre une matrice 4x4 et un vecteur colonne en 4d.
	 * La <b>matrice M</b> qui lance l'appel de cette m�thode sera � <b>gauche</b> et le <b>vecteur colonne</b> pass�
	 * en param�tre sera � <b>droite</b>. Cette pr�cision est n�cessaire �tant donn�
	 * que le <b>produit entre une matrice et un vecteur n'est pas commutatif</b>.
	 * 
	 * <p>Le r�sutat du produit matriciel u = M*v correspond au calcul</p>
	 * <ul>u[i] = SOMME sur k : M[i][k]*v[k]</ul>
	 * <p>o� u[i] est une composantes du vecteur colonne u.</p>
	 * 
	 * @param v - Le vecteur de droite � multiplier avec la matrice qui lance l'appel de la m�thode.
	 * @return Le vecteur colonne u �tant le r�sultat du produit de la matrice M avec le vecteur colonne v.
	 */
	public SVector4d multiply(SVector4d v)
	{
		return new SVector4d(	
				
				matrix[0]*v.getX() + matrix[1]*v.getY() + matrix[2]*v.getZ() + matrix[3]*v.getT(),
				matrix[4]*v.getX() + matrix[5]*v.getY() + matrix[6]*v.getZ() + matrix[7]*v.getT(),
				matrix[8]*v.getX() + matrix[9]*v.getY() + matrix[10]*v.getZ() + matrix[11]*v.getT(),
				matrix[12]*v.getX() + matrix[13]*v.getY() + matrix[14]*v.getZ() + matrix[15]*v.getT()
				
				);
	}
	
	/**
	 * M�thode qui effectue le produit entre une matrice 4x4 et un vecteur colonne en 3d dont une 4i�me dimension t = 1.0 est ajout�e.
	 * La <b>matrice M</b> qui lance l'appel de cette m�thode sera � <b>gauche</b> et le <b>vecteur colonne</b> pass�
	 * en param�tre sera � <b>droite</b>. Cette pr�cision est n�cessaire �tant donn�
	 * que le <b>produit entre une matrice et un vecteur n'est pas commutatif</b>.
	 * 
	 * <p>Le r�sutat du produit matriciel u = M*v correspond au calcul</p>
	 * <ul>u[i] = SOMME sur k : M[i][k]*v[k]</ul>
	 * <p>o� u[i] est une composantes du vecteur colonne u.</p>
	 * 
	 * @param v - Le vecteur de droite � multiplier avec la matrice qui lance l'appel de la m�thode.
	 * @return Le vecteur colonne u �tant le r�sultat du produit de la matrice M avec le vecteur colonne v.
	 */
  public SVector4d multiply(SVector3d v)
  {
    return multiply(new SVector4d(v));
  }
  
	/**
	 * <p>M�thode pour obtenir la matrice identit� I.</p> 
	 * <p>Soit une matrice identit� I et une
	 * matrice A quelconque, alors</p> 
	 * 
	 *  <ul> A = A*I = I*A </ul>
	 *  <p>ce qui rend la <b>matrice identit� commutative</b>.</p>
	 * 
	 * @return La matrice identit� I.
	 */
	public static SMatrix4x4 identity()
	{
		return new SMatrix4x4(
				
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
				
				);
	}
	
	/**
	 * M�thode pour obtenir la matrice transpos�e de la matrice qui lance l'appel de cette m�thode.
	 * La matrice transpos�e B s'obtient � partir d'une matrice A gr�ce au calcul
   *
   * <ul> B[j][i] = A[i][j]</ul>
   * 
	 * @return La matrice transpos�e.
	 */
	public SMatrix4x4 transpose()
	{
		return new SMatrix4x4(
		    
		    matrix[0],  matrix[4],   matrix[8], matrix[12],
        matrix[1],  matrix[5],   matrix[9], matrix[13],
        matrix[2],  matrix[6],  matrix[10], matrix[14],
        matrix[3],  matrix[7],  matrix[11], matrix[15]
      
		    );
	}
	
	/**
	 * M�thode pour obtenir une matrice de transformation lin�aire de translation Tr <b>par rapport
	 * � l'origine</b> d'un syst�me d'axe cart�sien <i>xyz</i> si la matrice est utilis�e dans l'ordre
	 * <ul>u = Tr * v</ul>
	 * <p>o� v est le vecteur � transformer et u est le vecteur transform�.</p>
	 * 
	 * @param v - Le vecteur de translation en coordonn�e cart�sienne.
	 * @return La matrice de translation T.
	 */
	public static SMatrix4x4 translation(SVector3d v)
	{
		return SMatrix4x4.translation(v.getX(), v.getY(), v.getZ());
	}
	
	/**
	 * M�thode pour obtenir une matrice de transformation lin�aire de translation Tr <b>par rapport
	 * � l'origine</b> d'un syst�me d'axe cart�sien <i>xyz</i> si la matrice est utilis�e dans l'ordre
	 * <ul>u = Tr * v</ul>
	 * <p>o� v est le vecteur � transformer et u est le vecteur transform�.</p>
	 * 
	 * @param tr_x - La translation selon l'axe <i>x</i>.
	 * @param tr_y - La translation selon l'axe <i>y</i>.
	 * @param tr_z - La translation selon l'axe <i>z</i>.
	 * @return La matrice de translation T.
	 */
	public static SMatrix4x4 translation(double tr_x, double tr_y, double tr_z)
	{
		return new SMatrix4x4(
				
				1, 0, 0, tr_x,
			  0, 1, 0, tr_y,
			  0, 0, 1, tr_z,
			  0, 0, 0,  1
	  
				);
	}
	
	/**
	 * M�thode pour obtenir une matrice de transformation lin�aire d'homoth�tie Sc (scale) <b>par rapport
	 * � l'origine</b> d'un syst�me d'axe cart�sien <i>xyz</i> si la matrice est utilis�e dans l'ordre
	 * <ul>u = Sc * v</ul>
	 * <p>o� v est le vecteur � transformer et u est le vecteur transform�.</p>
	 * 
	 * @param v - Le vecteur d'homoth�tie en coordonn�e cart�sienne.
	 * @return La matrice d'homoth�tie Sc (scale).
	 */
	public static SMatrix4x4 scale(SVector3d v)
	{ 
		return SMatrix4x4.scale(v.getX(), v.getY(), v.getZ());
	}

	/**
	 * M�thode pour obtenir une matrice de transformation lin�aire d'homoth�tie Sc (scale) <b>par rapport
	 * � l'origine</b> d'un syst�me d'axe cart�sien <i>xyz</i> si la matrice est utilis�e dans l'ordre
	 * <ul>u = Sc * v</ul>
	 * <p>o� v est le vecteur � transformer et u est le vecteur transform�.</p>
	 * 
	 * @param sx - L'homoth�tie selon l'axe <i>x</i>.
	 * @param sy - L'homoth�tie selon l'axe <i>y</i>.
	 * @param sz - L'homoth�tie selon l'axe <i>z</i>.
	 * @return La matrice de transformation d'homoth�tie Sc (scale).
	 */
	public static SMatrix4x4 scale(double sc_x,double sc_y,double sc_z)
	{
	  return new SMatrix4x4(	
			  
			  sc_x,    0,    0,   0,
			     0, sc_y,    0,   0,
			     0,    0, sc_z,   0,
			     0,    0,    0,   1
			   
			  );
	}

	/**
	 * M�thode pour obtenir une matrice de transformation lin�aire de rotation Rx autour de l'axe <i>x</i> 
	 * <b>par rapport � l'origine</b> d'un syst�me d'axe cart�sien <i>xyz</i> si la matrice est utilis�e dans l'ordre
	 * <ul>u = Rx * v</ul>
	 * <p>o� v est le vecteur � transformer et u est le vecteur transform�.</p>  
	 * 
	 * @param degree - L'angle de rotation en degr�.
	 * @return La matrice de rotation Rx autour de l'axe <i>x</i>.
	 */
	public static SMatrix4x4 rotationX(double degree)
	{
	  double angle = Math.toRadians(degree);
	  double sinO = Math.sin(angle);
	  double cosO = Math.cos(angle);

	  return new SMatrix4x4(	
			  
			  1,    0,     0, 0,
			  0, cosO, -sinO, 0,
			  0, sinO,  cosO, 0,
			  0,    0,     0, 1
			  
			  );
	}
	
	/**
	 * M�thode pour obtenir une matrice de transformation lin�aire de rotation Ry autour de l'axe <i>y</i> 
	 * <b>par rapport � l'origine</b> d'un syst�me d'axe cart�sien <i>xyz</i> si la matrice est utilis�e dans l'ordre
	 * <ul>u = Ry * v</ul>
	 * <p>o� v est le vecteur � transformer et u est le vecteur transform�.</p>  
	 * 
	 * @param degree - L'angle de rotation en degr�.
	 * @return La matrice de rotation Ry autour de l'axe <i>y</i>.
	 */
	public static SMatrix4x4 rotationY(double degree)
	{
	  double angle = Math.toRadians(degree);
	  double sinO = Math.sin(angle);
	  double cosO = Math.cos(angle);

	  return new SMatrix4x4(	
			  
          cosO,	  0, sinO,   0,
				     0, 	1,    0,   0,
				 -sinO, 	0, cosO,   0,
             0, 	0,    0,   1

	      );
	}
	
	/**
	 * M�thode pour obtenir une matrice de transformation lin�aire de rotation Rz autour de l'axe <i>z</i> 
	 * <b>par rapport � l'origine</b> d'un syst�me d'axe cart�sien <i>xyz</i> si la matrice est utilis�e dans l'ordre
	 * <ul>u = Rz * v</ul>
	 * <p>o� v est le vecteur � transformer et u est le vecteur transform�.</p> 
	 *    
	 * @param degree - L'angle de rotation en degr�.
	 * @return La matrice de rotation Rz autour de l'axe <i>z</i>.
	 */
	public static SMatrix4x4 rotationZ(double degree)
	{
	  double angle = Math.toRadians(degree);
	  double sinO = Math.sin(angle);
	  double cosO = Math.cos(angle);

	  return new SMatrix4x4(	
			 
			  cosO, -sinO,   0,   0,
			  sinO,  cosO,   0,   0,
			     0,     0,   1,   0,
			     0,     0,   0,   1

			  );
	}
	
	/**
   * <p>
   * M�thode pour obtenir une matrice de transformation lin�aire de rotation Ru autour d'un axe <i>u</i> quelconque
   * <b>par rapport � l'origine</b> d'un syst�me d'axe cart�sien <i>xyz</i> si la matrice est utilis�e dans l'ordre
   * <ul>v1 = Ru * v0</ul>
   * o� v0 est le vecteur � transformer et v1 est le vecteur transform�. Dans la litt�rature, cette matrice porte le
   * nom de <b>matrice des cosinus directionnels</b> (<b><i>direction cosine matrix</i></b>). 
   * </p>
   * 
   * @param u L'axe de rotation (doit �tre unitaire). 
   * @param angle L'angle de rotation en degr�.
   * @return La matrice de rotation autour de l'axe <i>u</i>.
   * @throws SImpossibleNormalizationException Si l'axe de rotation <i>u</i> ne peut pas �tre normalis�.
   */
  public static SMatrix4x4 rotationU(SVector3d u, double angle) throws SImpossibleNormalizationException
  {
    // V�rification de la normalisation.
    u = u.normalize();
    
    double ux = u.getX();
    double uy = u.getY();
    double uz = u.getZ();
    
    double angle_u = Math.toRadians(angle);
    
    double cu = Math.cos(angle_u);
    double su = Math.sin(angle_u);
    
    // Repr�sentation de la matrice de rotation autour de l'axe u
    double[] m = new double[]{
        
        ux*ux + (1 - ux*ux)*cu, ux*uy*(1 - cu) - uz*su, ux*uz*(1 - cu) + uy*su, 0,
        ux*uy*(1 - cu) + uz*su, uy*uy + (1 - uy*uy)*cu, uy*uz*(1 - cu) - ux*su, 0,
        ux*uz*(1 - cu) - uy*su, uy*uz*(1 - cu) + ux*su, uz*uz + (1 - uz*uz)*cu, 0,
                             0,                      0,                      0, 1 };
    
    return new SMatrix4x4(m);
  }
  
	/**
	 * <p>M�thode pour obtenir une matrice de transformation lin�aire de rotation Rzyx autour des 
	 * axes successifs <i>x</i>, <i>y</i> et <i>z</i> <b>par rapport � l'origine</b> 
	 * d'un syst�me d'axe cart�sien <i>xyz</i> si la matrice est utilis�e dans l'ordre
	 * <ul>u = Rzyx * v</ul>
	 * <p>o� v est le vecteur � transformer et u est le vecteur transform�.</p> 
	 * <p>La matrice Rzyx est le r�sultat de l'op�ration</p>
	 * <ul> Rzyx = Rz*Ry*Rx</ul>
	 * <p>o� Rx est la matrice de rotation autour de l'axe <i>x</i>, 
	 *       Ry est la matrice de rotation autour de l'axe <i>y</i> et
	 *       Rz est la matrice de rotation autour de l'axe <i>z</i> et
	 * 
	 * @param v - Le vecteur de rotation selon l'axe x,y et z en <b>degr�</b>.
	 * @return La matrice de rotation Rzyx dans l'ordre <i>x</i>, <i>y</i> et <i>z</i> si elle est utilis�e tel que u = Rzyx*v.
	 */
	public static SMatrix4x4 Rzyx(SVector3d v)
	{
	  throw new SNoImplementationException("Erreur SMatrix4x4 : La m�thode n'est pas impl�ment�e.");
	}
	
	/**
   * <p>M�thode pour obtenir une matrice de transformation lin�aire de rotation Rxyz autour des 
   * axes successifs <i>z</i>, <i>y</i> et <i>x</i> <b>par rapport � l'origine</b> 
   * d'un syst�me d'axe cart�sien <i>xyz</i> si la matrice est utilis�e dans l'ordre
   * <ul>u = Rxyz * v</ul>
   * <p>o� v est le vecteur � transformer et u est le vecteur transform�.</p> 
   * <p>La matrice Rxyz est le r�sultat de l'op�ration</p>
   * <ul> Rxyz = Rx*Ry*Rz</ul>
   * <p>o� Rx est la matrice de rotation autour de l'axe <i>x</i>, 
   *       Ry est la matrice de rotation autour de l'axe <i>y</i> et
   *       Rz est la matrice de rotation autour de l'axe <i>z</i> et
   * 
   * @param v - Le vecteur de rotation selon l'axe x,y et z en <b>degr�</b>.
   * @return La matrice de rotation Rxyz dans l'ordre <i>z</i>, <i>y</i> et <i>x</i> si elle est utilis�e tel que u = Rxyz*v.
   */
  public static SMatrix4x4 Rxyz(SVector3d v)
  {
    throw new SNoImplementationException("Erreur SMatrix4x4 : La m�thode n'est pas impl�ment�e.");
  }
  
	/**
	 * <p>M�thode pour obtenir une matrice de transformation lin�aire d'homoth�tie Sc (<i>scale</i>), de rotation Rzyx autour des 
   * axes successifs <i>x</i>, <i>y</i> et <i>z</i> puis de translation Tr <b>par rapport � l'origine</b> 
   * d'un syst�me d'axe cart�sien <i>xyz</i>. La matrice TRzyxS est le r�sultat de l'op�ration
   * <ul> TrRzyxSc = Tr*Rz*Ry*Rx*Sc</ul>
   * et elle doit �tre utilis�e sous la repr�sentation
   * <ul> u = TrRzyxSc*v </ul>
   * o� v est le vecteur � transformer et u est le vecteur transform�.</p>
	 * @param translation - Le vecteur repr�sentant la translation selon l'axe <i>x</i>, <i>y</i> et <i>z</i>.
	 * @param rotation - Le vecteur rotation autour des avec <i>x</i>, <i>y</i> et <i>z</i> successivement.
	 * @param scale - Le vecteur d'homoth�tie (<i>scale</i>) � appliquer.
	 * @return La matrice de transformation lin�aire TrRzyxSc.
	 */
	public static SMatrix4x4 TrRzyxSc(SVector3d translation, SVector3d rotation, SVector3d scale)
	{
		return translation(translation).multiply(rotationZ(rotation.getZ())).multiply(rotationY(rotation.getY())).multiply(rotationX(rotation.getX())).multiply(scale(scale));
	}
	
	/**
   * <p>M�thode pour obtenir une matrice de transformation lin�aire de translation Tr, de rotation Rxyz autour des 
   * axes successifs <i>z</i>, <i>y</i> et <i>x</i> puis d'homoth�tie Sc (<i>scale</i>) <b>par rapport � l'origine</b> 
   * d'un syst�me d'axe cart�sien <i>xyz</i>. La matrice ScRxyzTr est le r�sultat de l'op�ration
   * <ul> ScRxyzTr = Sc*Rx*Ry*Rz*Tr</ul>
   * et elle doit �tre utilis�e sous la repr�sentation
   * <ul> u = ScRxyzTr*v .</ul>
   * o� v est le vecteur � transformer et u est le vecteur transform�.</p>
   * @param scale - Le vecteur d'homoth�tie (<i>scale</i>) � appliquer.
   * @param rotation - Le vecteur rotation autour des avec <i>x</i>, <i>y</i> et <i>z</i> successivement.
   * @param translation - Le vecteur repr�sentant la translation selon l'axe <i>x</i>, <i>y</i> et <i>z</i>.
   * @return La matrice de transformation lin�aire ScRxyzTr.
   */
  public static SMatrix4x4 ScRxyzTr(SVector3d scale, SVector3d rotation, SVector3d translation)
  {
	  return scale(scale).multiply(rotationX(rotation.getX())).multiply(rotationY(rotation.getY())).multiply(rotationZ(rotation.getZ())).multiply(translation(translation));
  }
	
  @Override
  public String toString()
  {
    return  "|" + matrix[0] + "\t" +  matrix[1] + "\t" +  matrix[2] + "\t" +  matrix[3] + "|"  + "\n" +  
            "|" + matrix[4] + "\t" +  matrix[5] + "\t" +  matrix[6] + "\t" +  matrix[7] + "|"  + "\n" +
            "|" + matrix[8] + "\t" +  matrix[9] + "\t" +  matrix[10] + "\t" +  matrix[11] + "|"  + "\n" +
            "|" + matrix[12] + "\t" +  matrix[13] + "\t" +  matrix[14] + "\t" +  matrix[15] + "|"  + "\n";
  }
	
	@Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(matrix);
    return result;
  }

  @Override
  public boolean equals(Object obj) 
  {
    if(this == obj)
      return true;
    
    if(obj == null)
      return false;
    
    if(!(obj instanceof SMatrix4x4))
      return false;
    
    SMatrix4x4 other = (SMatrix4x4) obj;

    //Adressage des tableaux en m�moire
    if(matrix == other.matrix)
      return true;

    //Comparaison des valeurs dans le tableau en utilisant un calcul de comparaison SMath.nearlyEquals(a,b)
    for(int i = 0; i < 16; i++)
      if(!SMath.nearlyEquals(matrix[i], other.matrix[i]))
          return false;
    
    return true;
  }
  
}//fin de la classe SMatrix4x4
