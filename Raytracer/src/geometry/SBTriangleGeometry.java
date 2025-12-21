/**
 * 
 */
package sim.geometry;

import java.io.BufferedWriter;
import java.io.IOException;

import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.graphics.SPrimitive;
import sim.math.SImpossibleNormalizationException;
import sim.math.SLinearAlgebra;
import sim.math.SMatrix4x4;
import sim.math.SVector;
import sim.math.SVector3d;
import sim.math.SVectorUV;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.readwrite.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SBTriangleGeometry</b> repr�sentant la g�om�trie d'un triangle
 * en <b>coordonn�e barycentrique</b>. Ce triangle permet la d�finition d'une
 * normale � chaque sommet du triangle ce qui permet d'�valuer une normale
 * locale � la surface du triangle pour chaque coordonn�e barycentrique du
 * triangle. Cela aura pour effet de donner un "effet de courbure" au triangle
 * lorsque celui-ci sera dessin� � l'�cran.
 * 
 * @author Simon V�zina
 * @since 2015-03-11
 * @version 2020-01-08 (Version : Le ray tracer v2.107)
 */
public class SBTriangleGeometry extends STriangleGeometry {

	// --------------
	// CONSTANTES //
	// --------------

	/**
	 * La constante <b>KEYWORD_PARAMETER</b> correspond � un tableau contenant
	 * l'ensemble des mots cl�s � utiliser reconnus lors de la d�finition de l'objet
	 * par une lecture en fichier.
	 */
	private static final String[] KEYWORD_PARAMETER = { SKeyWordDecoder.KW_NORMAL, SKeyWordDecoder.KW_UV };

	/**
	 * La constante <b>DEFAULT_UV</b> correspond � la coordonn�e uv associ�e par
	 * d�faut au trois points du triangle.
	 */
	private static final SVectorUV DEFAULT_UV = new SVectorUV(0.0, 0.0);

	// -------------
	// VARIABLES //
	// -------------

	/**
	 * La variable <b>N0</b> correspond � la normale � la surface associ�e au point
	 * P0 du triangle.
	 */
	private SVector3d N0;

	/**
	 * La variable <b>N1</b> correspond � la normale � la surface associ�e au point
	 * P1 du triangle.
	 */
	private SVector3d N1;

	/**
	 * La variable <b>N2</b> correspond � la normale � la surface associ�e au point
	 * P2 du triangle.
	 */
	private SVector3d N2;

	/**
	 * La variable <b>UV0</b> correspond � la coordonn�e uv associ�e au point P0 du
	 * triangle.
	 */
	private SVectorUV UV0;

	/**
	 * La variable <b>UV1</b> correspond � la coordonn�e uv associ�e au point P1 du
	 * triangle.
	 */
	private SVectorUV UV1;

	/**
	 * La variable <b>UV2</b> correspond � la coordonn�e uv associ�e au point P2 du
	 * triangle.
	 */
	private SVectorUV UV2;

	/**
	 * La variable <b>reading_normal</b> correspond au num�ro de la normale � la
	 * surface � effectuer en lecture.
	 */
	private int reading_normal;

	/**
	 * La variable <b>reading_uv</b> correspond au num�ro de la coordonn�e uv �
	 * effectuer en lecture.
	 */
	private int reading_uv;

	// ---------------------------------------------------------
	// PARAM�TRES DE PR�CALCUL pour coordonn�e barycentrique //
	// ---------------------------------------------------------

	// -----------------
	// CONSTRUCTEURS //
	// -----------------

	/**
	 * Constructeur d'un triangle barycentrique <b>sans normale</b> ni <b>sans
	 * coordonn�es uv</b>.
	 * 
	 * @param p0 La position du point P0 du triangle.
	 * @param p1 La position du point P1 du triangle.
	 * @param p2 La position du point P2 du triangle.
	 * @throws SConstructorExeception Si les trois points ne sont pas ad�quats pour
	 *                                d�finir un triangle (ex: colin�aire) ou une
	 *                                normale � un point est nulle.
	 */
	public SBTriangleGeometry(SVector3d p0, SVector3d p1, SVector3d p2) throws SConstructorException {
		// Mettre � "null" une normale permettra lors de l'initialisation � la d�finir
		// comme �tant la normale g�om�trie du triangle.
		this(p0, p1, p2, null, null, null, DEFAULT_UV, DEFAULT_UV, DEFAULT_UV, null);
	}

	/**
	 * Constructeur d'un triangle barycentrique <b>sans coordonn�es uv</b>.
	 * 
	 * @param p0 - La position du point P0 du triangle.
	 * @param p1 - La position du point P1 du triangle.
	 * @param p2 - La position du point P2 du triangle.
	 * @param n0 - La normale associ�e au point P0 du triangle.
	 * @param n1 - La normale associ�e au point P1 du triangle.
	 * @param n2 - La normale associ�e au point P2 du triangle.
	 * @throws SConstructorExeception Si les trois points ne sont pas ad�quats pour
	 *                                d�finir un triangle (ex: colin�aire) ou une
	 *                                normale � un point est nulle.
	 */
	public SBTriangleGeometry(SVector3d p0, SVector3d p1, SVector3d p2, SVector3d n0, SVector3d n1, SVector3d n2)
			throws SConstructorException {
		this(p0, p1, p2, n0, n1, n2, DEFAULT_UV, DEFAULT_UV, DEFAULT_UV, null);
	}

	/**
	 * Constructeur d'un triangle barycentrique <b>sans normale</b>.
	 * 
	 * @param p0  - La position du point P0 du triangle.
	 * @param p1  - La position du point P1 du triangle.
	 * @param p2  - La position du point P2 du triangle.
	 * @param uv0 - La coordonn�e uv associ�e au point P0 du triangle.
	 * @param uv1 - La coordonn�e uv associ�e au point P1 du triangle.
	 * @param uv2 - La coordonn�e uv associ�e au point P2 du triangle.
	 * @throws SConstructorExeception Si les trois points ne sont pas ad�quats pour
	 *                                d�finir un triangle (ex: colin�aire) ou une
	 *                                normale � un point est nulle.
	 */
	public SBTriangleGeometry(SVector3d p0, SVector3d p1, SVector3d p2, SVectorUV uv0, SVectorUV uv1, SVectorUV uv2)
			throws SConstructorException {
		// Mettre � "null" une normale permettra lors de l'initialisation � la d�finir
		// comme �tant la normale g�om�trie du triangle.
		this(p0, p1, p2, null, null, null, uv0, uv1, uv2, null);
	}

	/**
	 * Constructeur d'un triangle barycentrique. Si l'un des param�tres n0, n1, n2
	 * est 'null', une normale � la surface par d�faut �tant la normale g�om�trique
	 * du triangle sera affect�e au param�tre.
	 * 
	 * @param p0  - La position du point P0 du triangle.
	 * @param p1  - La position du point P1 du triangle.
	 * @param p2  - La position du point P2 du triangle.
	 * @param n0  - La normale associ�e au point P0 du triangle.
	 * @param n1  - La normale associ�e au point P1 du triangle.
	 * @param n2  - La normale associ�e au point P2 du triangle.
	 * @param uv0 - La coordonn�e uv associ�e au point P0 du triangle.
	 * @param uv1 - La coordonn�e uv associ�e au point P1 du triangle.
	 * @param uv2 - La coordonn�e uv associ�e au point P2 du triangle.
	 * @throws SConstructorExeception Si les trois points ne sont pas ad�quats pour
	 *                                d�finir un triangle (ex: colin�aire) ou une
	 *                                normale � un point est nulle.
	 */
	public SBTriangleGeometry(SVector3d p0, SVector3d p1, SVector3d p2, SVector3d n0, SVector3d n1, SVector3d n2,
			SVectorUV uv0, SVectorUV uv1, SVectorUV uv2) throws SConstructorException {
		this(p0, p1, p2, n0, n1, n2, uv0, uv1, uv2, null);
	}

	/**
	 * Constructeur d'un triangle barycentrique avec une primitive comme parent en
	 * r�f�rence. Si l'un des param�tres n0, n1, n2 est 'null', une normale � la
	 * surface par d�faut �tant la normale g�om�trique du triangle sera affect�e au
	 * param�tre.
	 * 
	 * @param p0     - La position du point P0 du triangle.
	 * @param p1     - La position du point P1 du triangle.
	 * @param p2     - La position du point P2 du triangle.
	 * @param n0     - La normale associ�e au point P0 du triangle.
	 * @param n1     - La normale associ�e au point P1 du triangle.
	 * @param n2     - La normale associ�e au point P2 du triangle.
	 * @param uv0    - La coordonn�e uv associ�e au point P0 du triangle.
	 * @param uv1    - La coordonn�e uv associ�e au point P1 du triangle.
	 * @param uv2    - La coordonn�e uv associ�e au point P2 du triangle.
	 * @param parent - La primitive parent � cette g�om�trie.
	 * @throws SConstructorExeception Si les trois points ne sont pas ad�quats pour
	 *                                d�finir un triangle (ex: colin�aire) ou une
	 *                                normale � un point est nulle.
	 */
	public SBTriangleGeometry(SVector3d p0, SVector3d p1, SVector3d p2, SVector3d n0, SVector3d n1, SVector3d n2,
			SVectorUV uv0, SVectorUV uv1, SVectorUV uv2, SPrimitive parent) throws SConstructorException {
		super(p0, p1, p2, parent);

		// Si une normale est 'null', elle sera recalcul� dans la m�thode initialize()
		N0 = n0;
		N1 = n1;
		N2 = n2;

		UV0 = uv0;
		UV1 = uv1;
		UV2 = uv2;

		reading_point = 3;
		reading_normal = 3;
		reading_uv = 3;

		try {
			initialize();
		} catch (SInitializationException e) {
			// Les trois points sont colin�aire ce qui ne permet pas de d�finir une normale
			// � la surface au triangle.
			throw new SConstructorException("Erreur SBTriangleGeometry 002 : Les normales {" + N0 + "," + N1 + "," + N2
					+ "} ne sont pas ad�quats pour d�finir un triangle barycentrique." + SStringUtil.END_LINE_CARACTER
					+ "\t" + e.getMessage(), e);
		}
	}

	/**
	 * Constructeur d'une g�om�trie � partir d'information lue dans un fichier de
	 * format txt. Puisqu'une g�om�trie est construite � l'int�rieure d'une
	 * primitive, une r�f�rence � celle-ci doit �tre int�gr�e au constructeur pour y
	 * a voir acc�s.
	 * 
	 * @param sbr    - Le BufferedReader cherchant l'information dans le fichier
	 *               txt.
	 * @param parent - La primitive qui fait la construction de cette g�om�trie (qui
	 *               est le parent).
	 * @throws IOException            Si une erreur de l'objet SBufferedWriter est
	 *                                lanc�e.
	 * @throws SConstructorExeception Si les trois points lus ne sont pas ad�quats
	 *                                pour d�finir un triangle (ex: colin�aire) ou
	 *                                une normale lue � un point est nulle.
	 * @see SBufferedReader
	 * @see SPrimitive
	 */
	public SBTriangleGeometry(SBufferedReader sbr, SPrimitive parent) throws IOException, SConstructorException {
		this(DEFAULT_P0, DEFAULT_P1, DEFAULT_P2, null, null, null, DEFAULT_UV, DEFAULT_UV, DEFAULT_UV, parent);

		// Puisque la construction par d�faut d'un SBTriangleGeometry donne un triangle
		// fonctionnel
		// avec une d�finition des vecteurs normales aux trois points du triangle, il
		// faut
		// obligatoirement r�initialiser ces normales � 'null' et recalculer � nouveau
		// celles qui
		// seront apr�s la lecture encore 'null' afin d'avoir une bonne d�finition de
		// ces normales
		// apr�s avoir effectu� la lecture

		// R�initialisation au droit de lecture
		reading_point = 0;
		reading_normal = 0;
		reading_uv = 0;

		// R�initialisation des normales � la surface
		N0 = null;
		N1 = null;
		N2 = null;

		// Faire la lecture
		try {
			read(sbr);
		} catch (SInitializationException e) {
			// Les trois points sont colin�aire ce qui ne permet pas de d�finir une normale
			// � la surface au triangle.
			throw new SConstructorException("Erreur SBTriangleGeometry 003 : Les points {" + P0 + "," + P1 + "," + P2
					+ "} avec normale {" + N0 + "," + N1 + "," + N2
					+ "} qui ont �t� lus ne sont pas ad�quats pour d�finir un triangle barycentrique."
					+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(), e);
		}
	}

	// ------------
	// M�THODES //
	// ------------

	@Override
	public int getCodeName() {
		return SAbstractGeometry.BTRIANGLE_CODE;
	}

	/**
	 * M�thode pour obtenir la normale associ�e au point P0 du triangle
	 * barycentrique.
	 * 
	 * @return La normale au point P0 du triangle barycentrique.
	 */
	public SVector3d getN0() {
		return N0;
	}

	/**
	 * M�thode pour obtenir la normale associ�e au point P1 du triangle
	 * barycentrique.
	 * 
	 * @return La normale au point P1 du triangle barycentrique.
	 */
	public SVector3d getN1() {
		return N1;
	}

	/**
	 * M�thode pour obtenir la normale associ�e au point P2 du triangle
	 * barycentrique.
	 * 
	 * @return La normale au point P2 du triangle barycentrique.
	 */
	public SVector3d getN2() {
		return N2;
	}

	/**
	 * M�thode pour obtenir la coordonn�e uv associ�e au point P0 du triangle
	 * barycentrique.
	 * 
	 * @return La coordonn�e uv au point P0 du triangle barycentrique.
	 */
	public SVectorUV getUV0() {
		return UV0;
	}

	/**
	 * M�thode pour obtenir la coordonn�e uv associ�e au point P1 du triangle
	 * barycentrique.
	 * 
	 * @return La coordonn�e uv au point P1 du triangle barycentrique.
	 */
	public SVectorUV getUV1() {
		return UV1;
	}

	/**
	 * M�thode pour obtenir la coordonn�e uv associ�e au point P2 du triangle
	 * barycentrique.
	 * 
	 * @return La coordonn�e uv au point P2 du triangle barycentrique.
	 */
	public SVectorUV getUV2() {
		return UV2;
	}

	@Override
	public SRay intersection(SRay ray) throws SAlreadyIntersectedRayException {
		double[] t = SGeometricIntersection.planeIntersection(ray, P0, normal);

		if (t.length == 1) {

			double[] coord = SLinearAlgebra.triangleBarycentricCoordinates(P0, P1, P2, ray.getPosition(t[0]));
			SVector3d normall = (SVector3d) SVector.linearBarycentricInterpolation(N0, N1, N2, coord[0], coord[1]);
			SVectorUV normalu = (SVectorUV) SVector.linearBarycentricInterpolation(UV0, UV1, UV2, coord[0], coord[1]);

			if (SLinearAlgebra.isBarycentricCoordinatesInsideTriangle(coord)) {

				if (t[0] > SRay.getEpsilon()) {
					return ray.intersection(this, normall, normalu, t[0]);
				}

			}

		}
		if (t.length == 2) {

			double[] coord = SLinearAlgebra.triangleBarycentricCoordinates(P0, P1, P2, ray.getPosition(t[1]));
			SVector3d normall = (SVector3d) SVector.linearBarycentricInterpolation(N0, N1, N2, coord[0], coord[1]);
			SVectorUV normalu = (SVectorUV) SVector.linearBarycentricInterpolation(UV0, UV1, UV2, coord[0], coord[1]);

			if (SLinearAlgebra.isBarycentricCoordinatesInsideTriangle(coord)) {

				if (t[1] > SRay.getEpsilon()) {
					return ray.intersection(this, normall, normalu, t[1]);
				}
			}
			return ray;
		}
		return ray;
	}

	@Override
	public SBTriangleGeometry transform(SMatrix4x4 transformation) {
		throw new SNoImplementationException();

	}

	@Override
	public void write(BufferedWriter bw) throws IOException {
		bw.write(SKeyWordDecoder.KW_BTRIANGLE);
		bw.write(SStringUtil.END_LINE_CARACTER);

		// �crire les propri�t�s de la classe SSphereGeometry et ses param�tres h�rit�s
		writeSBTriangleGeometryParameter(bw);

		bw.write(SKeyWordDecoder.KW_END);
		bw.write(SStringUtil.END_LINE_CARACTER);
		bw.write(SStringUtil.END_LINE_CARACTER);
	}

	/**
	 * M�thode pour �crire les param�tres associ�s � la classe SBTriangleGeometry et
	 * ses param�tres h�rit�s.
	 * 
	 * @param bw - Le BufferedWriter �crivant l'information dans un fichier txt.
	 * @throws IOException Si une erreur I/O s'est produite.
	 */
	protected void writeSBTriangleGeometryParameter(BufferedWriter bw) throws IOException {
		writeSTriangleGeometryParameter(bw);

		bw.write(SKeyWordDecoder.KW_NORMAL);
		bw.write("\t");
		N0.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);

		bw.write(SKeyWordDecoder.KW_NORMAL);
		bw.write("\t");
		N1.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);

		bw.write(SKeyWordDecoder.KW_NORMAL);
		bw.write("\t");
		N2.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);

		bw.write(SKeyWordDecoder.KW_UV);
		bw.write("\t");
		UV0.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);

		bw.write(SKeyWordDecoder.KW_UV);
		bw.write("\t");
		UV1.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);

		bw.write(SKeyWordDecoder.KW_UV);
		bw.write("\t");
		UV2.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);
	}

	/**
	 * M�thode pour faire l'initialisation de l'objet apr�s sa construction.
	 * 
	 * @throws SInitializationException Si une erreur est survenue lors de
	 *                                  l'initialisation.
	 */
	private void initialize() throws SInitializationException {
		// Affectation d'une normale aux trois points s'il n'y en avait pas d�j�.
		// � pr�ciser que 'normal' a d�j� �t� d�finie par la m�thode initialize() de
		// STriangleGeometry
		if (N0 == null)
			N0 = normal;

		if (N1 == null)
			N1 = normal;

		if (N2 == null)
			N2 = normal;

		// Normalisation des normales au points (si elles n'�taitent pas normalis�es
		// lors de la lecture)
		try {
			N0 = N0.normalize();
			N1 = N1.normalize();
			N2 = N2.normalize();
		} catch (SImpossibleNormalizationException e) {
			// Une erreur lors de la normalisation d'un vecteur est survenue.
			throw new SInitializationException(
					"Erreur SBTriangleGeometry 006 - La d�finition d'une normale en un point ne peut pas �tre normalis�e."
							+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(),
					e);
		}

	}

	@Override
	protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException {
		switch (code) {
		case SKeyWordDecoder.CODE_NORMAL:
			readNormal(remaining_line);
			return true;

		case SKeyWordDecoder.CODE_UV:
			readUV(remaining_line);
			return true;

		default:
			return super.read(sbr, code, remaining_line);
		}
	}

	/**
	 * M�thode pour faire la lecture d'une normale associ�e � un point et
	 * l'affectation d�pendra du num�ro de normale en lecture d�termin� par la
	 * variable <i>reading_normal</i>.
	 * 
	 * @param remaining_line - L'expression en string du vecteur normale � un point
	 *                       du triangle.
	 * @throws SReadingException S'il y a une erreur de lecture.
	 */
	private void readNormal(String remaining_line) throws SReadingException {
		try {

			switch (reading_normal) {
			case 0:
				N0 = new SVector3d(remaining_line);
				N0 = N0.normalize();
				break;
			case 1:
				N1 = new SVector3d(remaining_line);
				N1 = N1.normalize();
				break;
			case 2:
				N2 = new SVector3d(remaining_line);
				N2 = N2.normalize();
				break;

			default:
				throw new SReadingException(
						"Erreur SBTriangleGeometry 008 : Il y a d�j� 3 normales de d�fini dans ce triangle.");
			}

			reading_normal++;

		} catch (SImpossibleNormalizationException e) {
			// Si une normale ne peut pas �tre normalis�e
			throw new SReadingException(
					"Erreur SBTriangleGeometry 009 : Une normale est mal d�finie ce qui rend la normalisation impossible."
							+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(),
					e);
		}
	}

	/**
	 * M�thode pour faire la lecture d'une coordonn�e uv associ�e � un point et
	 * l'affectation d�pendra du num�ro de coordonn�e uv en lecture d�termin� par la
	 * variable <i>reading_uv</i>.
	 * 
	 * @param remaining_line - L'expression en string du vecteur normale � un point
	 *                       du triangle.
	 * @throws SReadingException S'il y a une erreur de lecture.
	 */
	private void readUV(String remaining_line) throws SReadingException {
		switch (reading_uv) {
		case 0:
			UV0 = new SVectorUV(remaining_line);
			break;
		case 1:
			UV1 = new SVectorUV(remaining_line);
			break;
		case 2:
			UV2 = new SVectorUV(remaining_line);
			break;

		default:
			throw new SReadingException(
					"Erreur SBTriangleGeometry 010 : Il y a d�j� 3 coordonn�e uv de d�fini dans ce triangle.");
		}

		reading_uv++;
	}

	@Override
	protected SVector3d evaluateIntersectionNormal(SRay ray, double intersection_t) {
		throw new SNoImplementationException(
				"Erreur SBTriangleGeometry 011 : La m�thode n'est pas d�finie pour cette g�om�trie. On doit effectuer ce calcul via les coordonn�es barycentriques.");
	}

	@Override
	protected void readingInitialization() throws SInitializationException {
		super.readingInitialization();

		initialize();
	}

	@Override
	public String getReadableName() {
		return SKeyWordDecoder.KW_BTRIANGLE;
	}

	@Override
	public String[] getReadableParameterName() {
		String[] other_parameters = super.getReadableParameterName();

		return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!super.equals(obj)) {
			return false;
		}

		if (!(obj instanceof SBTriangleGeometry)) {
			return false;
		}

		SBTriangleGeometry other = (SBTriangleGeometry) obj;

		if (N0 == null) {
			if (other.N0 != null) {
				return false;
			}
		} else if (!N0.equals(other.N0)) {
			return false;
		}
		if (N1 == null) {
			if (other.N1 != null) {
				return false;
			}
		} else if (!N1.equals(other.N1)) {
			return false;
		}
		if (N2 == null) {
			if (other.N2 != null) {
				return false;
			}
		} else if (!N2.equals(other.N2)) {
			return false;
		}
		if (UV0 == null) {
			if (other.UV0 != null) {
				return false;
			}
		} else if (!UV0.equals(other.UV0)) {
			return false;
		}
		if (UV1 == null) {
			if (other.UV1 != null) {
				return false;
			}
		} else if (!UV1.equals(other.UV1)) {
			return false;
		}
		if (UV2 == null) {
			if (other.UV2 != null) {
				return false;
			}
		} else if (!UV2.equals(other.UV2)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "SBTriangleGeometry [name=" + getReadableName() + ", id=" + this.getID() + " P0=" + P0 + ", P1=" + P1
				+ ", P2=" + P2 + "  N0=" + N0 + ", N1=" + N1 + ", N2=" + N2 + " UV0=" + UV0 + ", UV1=" + UV1 + ", UV2="
				+ UV2 + "]";
	}
}
