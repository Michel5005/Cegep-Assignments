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
import sim.math.SVector3d;
import sim.math.SVectorUV;
import sim.readwrite.SKeyWordDecoder;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>STubeGeometry</b> repr�sente la g�om�trie d'un tube. Un tube
 * correspond � un cylindre sans les extr�mit�s en forme de cercle. Il est donc
 * un rectangle referm� sur lui-m�me sans �paisseur. Il n'est donc pas une
 * g�om�trie volumique (avec zone int�rieure). Le tube sera d�finit � l'aide de
 * deux points correspondant � la ligne passant par le centre du tube et d'un
 * rayon.
 * 
 * @author Simon V�zina
 * @since 2015-06-21
 * @version 2017-12-20 (version labo � Le ray tracer v2.1)
 */
public class STubeGeometry extends SAbstractGeometry {

	// --------------
	// CONSTANTES //
	// --------------

	/**
	 * La constante <b>KEYWORD_PARAMETER</b> correspond � un tableau contenant
	 * l'ensemble des mots cl�s � utiliser reconnus lors de la d�finition de l'objet
	 * par une lecture en fichier.
	 */
	private static final String[] KEYWORD_PARAMETER = { SKeyWordDecoder.KW_POSITION, SKeyWordDecoder.KW_RAY };

	/**
	 * La constante <b>DEFAULT_P1</b> correspond � la position du d�but du tube par
	 * d�faut.
	 */
	protected static final SVector3d DEFAULT_P1 = new SVector3d(0.0, 0.0, 0.0);

	/**
	 * La constante <b>DEFAULT_P1</b> correspond � la position de fin du tube par
	 * d�faut.
	 */
	protected static final SVector3d DEFAULT_P2 = new SVector3d(0.0, 0.0, 1.0);

	/**
	 * La constante <b>DEFAULT_R</b> correspond au rayon du tube par d�faut.
	 */
	protected static final double DEFAULT_R = 1.0;

	// -------------
	// VARIABLES //
	// -------------

	/**
	 * La variable <b>P1</b> correspond � la position du d�but du tube.
	 */
	protected SVector3d P1;

	/**
	 * La variable <b>P1</b> correspond � la position de fin du tube.
	 */
	protected SVector3d P2;

	/**
	 * La variable <b>R</b> correspond au rayon du tube.
	 */
	protected double R;

	/**
	 * La variable <b>reading_point</b> correspond au num�ro du point qui sera en
	 * lecture.
	 */
	protected int reading_point;

	/**
	 * La variable <b>S12</b> correspond � l'axe du tube orient� de P1 � P2 (d�but
	 * vers la fin du tube). L'axe du tube se doit d'�tre <b>normalis�</b>.
	 */
	protected SVector3d S12;

	// ----------------
	// CONSTRUCTEUR //
	// ----------------

	/**
	 * Construction d'un tube par d�faut.
	 */
	public STubeGeometry() {
		this(DEFAULT_P1, DEFAULT_P2, DEFAULT_R);
	}

	/**
	 * Constructeur d'un tube.
	 * 
	 * @param P1 - Le 1ier point du tube.
	 * @param P2 - Le 2i�me point du tube.
	 * @param R  - Le rayon du tube.
	 */
	public STubeGeometry(SVector3d P1, SVector3d P2, double R) {
		this(P1, P2, R, null);
	}

	/**
	 * Constructeur d'un tube avec une primitive comme parent.
	 * 
	 * @param P1     - Le 1ier point du tube.
	 * @param P2     - Le 2i�me point du tube.
	 * @param R      - Le rayon du tube.
	 * @param parent - La primitive en parent.
	 * @throws SConstructorException Si une erreur est survenue lors de la
	 *                               construction.
	 */
	public STubeGeometry(SVector3d P1, SVector3d P2, double R, SPrimitive parent) throws SConstructorException {
		super(parent);

		// V�rification que le rayon soit positif
		if (R < 0.0)
			throw new SConstructorException(
					"Erreur STubeGeometry 001 : Le tube se fait affecter un rayon R = " + R + " qui est n�gatif.");

		if (P1.equals(P2))
			throw new SConstructorException(
					"Erreur STubeGeometry 002 : Le point P1 = " + P1 + " et le point P2 = " + P2 + " sont �gaux.");

		this.P1 = P1;
		this.P2 = P2;
		this.R = R;

		reading_point = 2; // pas d'autres points � lire

		try {
			initialize();
		} catch (SInitializationException e) {
			throw new SConstructorException("Erreur STubeGeometry 003 : Une erreur d'initialisation est survenue."
					+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(), e);
		}
	}

	/**
	 * Constructeur du tube � partir d'information lue dans un fichier de format
	 * txt. Puisqu'une g�om�trie est construite � l'int�rieure d'une primitive, une
	 * r�f�rence � celle-ci doit �tre int�gr�e au constructeur pour y a voir acc�s.
	 * 
	 * @param sbr    - Le BufferedReader cherchant l'information dans le fichier
	 *               txt.
	 * @param parent - La primitive qui fait la construction de cette g�om�trie (qui
	 *               est le parent).
	 * @throws IOException           Si une erreur de de type I/O est lanc�e.
	 * @throws SConstructorException Si une erreur est survenue � la construction.
	 * @see SBufferedReader
	 * @see SPrimitive
	 */
	public STubeGeometry(SBufferedReader sbr, SPrimitive parent) throws IOException, SConstructorException {
		this(DEFAULT_P1, DEFAULT_P2, DEFAULT_R, parent);

		reading_point = 0; // aucune point lu

		try {
			read(sbr);
		} catch (SInitializationException e) {
			throw new SConstructorException("Erreur STubeGeometry 004 : Une erreur � l'initialisation est survenue."
					+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(), e);
		}
	}

	// ------------
	// M�THODES //
	// ------------

	/**
	 * M�thode pour obtenir le 1ier point d�finissant l'extr�mit� du tube.
	 * 
	 * @return le 1ier point du tube.
	 */
	public SVector3d getP1() {
		return P1;
	}

	/**
	 * M�thode pour obtenir le 2i�me point d�finissant l'extr�mit� du tube.
	 * 
	 * @return le 2i�me point du tube.
	 */
	public SVector3d getP2() {
		return P2;
	}

	/**
	 * M�thode pour obtenir le rayon du tube.
	 * 
	 * @return le rayon du tube.
	 */
	public double getRay() {
		return R;
	}

	@Override
	public int getCodeName() {
		return SAbstractGeometry.TUBE_CODE;
	}

	@Override
	public SRay intersection(SRay ray) throws SAlreadyIntersectedRayException {
		double[] tab = SGeometricIntersection.infiniteTubeIntersection(ray, P1, S12.normalize(), R);

		if (tab.length > 0) {
			if (tab[0] > SRay.getEpsilon()) {
				if (isInsideExtremity(ray.getPosition(tab[0]))) {
					return ray.intersection(this, evaluateIntersectionNormal(ray, tab[0]), tab[0]);
				}
			}
			if (tab[1] > SRay.getEpsilon()) {
				if (isInsideExtremity(ray.getPosition(tab[1]))) {
					return ray.intersection(this, evaluateIntersectionNormal(ray, tab[1]), tab[1]);
				}
			}
			return ray;

		} else {
			return ray;
		}
	}

	@Override
	public boolean isClosedGeometry() {
		return false;
	}

	@Override
	public boolean isInside(SVector3d v) {
		return false;
	}

	/**
	 * M�thode qui d�termine si le vecteur <i>v</i> se retrouve � l'int�rieur des
	 * deux extr�mit�s du tube. Ce teste correspond � v�rifier si le vecteur
	 * <i>v</i> se retrouve � l'int�rieur du tube s'il avait un <b>rayon infini</b>.
	 * 
	 * @param v - Le vecteur de position � v�rifier.
	 * @return <b>true</b> si le vecteur <i>v</i> est � l'int�rieur des extr�mit�s
	 *         et <b>false</b> sinon.
	 */
	protected boolean isInsideExtremity(SVector3d v) {
		if ((v.substract(P1).dot(S12) > 0) && (v.substract(P2).dot(S12) < 0)) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void write(BufferedWriter bw) throws IOException {
		bw.write(SKeyWordDecoder.KW_TUBE);
		bw.write(SStringUtil.END_LINE_CARACTER);

		// �crire les propri�t�s de la classe SCercleGeometry et ses param�tres h�rit�s
		writeSTubeGeometryParameter(bw);

		bw.write(SKeyWordDecoder.KW_END);
		bw.write(SStringUtil.END_LINE_CARACTER);
		bw.write(SStringUtil.END_LINE_CARACTER);
	}

	/**
	 * M�thode pour �crire les param�tres associ�s � la classe STubeGeometry et ses
	 * param�tres h�rit�s.
	 * 
	 * @param bw - Le BufferedWriter �crivant l'information dans un fichier txt.
	 * @throws IOException Si une erreur I/O s'est produite.
	 * @see IOException
	 */
	protected void writeSTubeGeometryParameter(BufferedWriter bw) throws IOException {
		bw.write(SKeyWordDecoder.KW_POSITION);
		bw.write("\t\t");
		P1.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);

		bw.write(SKeyWordDecoder.KW_POSITION);
		bw.write("\t\t");
		P2.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);

		bw.write(SKeyWordDecoder.KW_RAY);
		bw.write("\t\t");
		bw.write(Double.toString(R));
		bw.write(SStringUtil.END_LINE_CARACTER);
	}

	/**
	 * M�thode pour faire l'initialisation de l'objet apr�s sa construction.
	 * 
	 * @throws SInitializationException Si une erreur est survenue lors de
	 *                                  l'initialisation.
	 */
	private void initialize() throws SInitializationException {
		try {
			// Former l'axe du tube et le normaliser
			S12 = P2.substract(P1).normalize();
		} catch (SImpossibleNormalizationException e) {
			throw new SInitializationException("Erreur STubeGeometry 004 : Le point P1 = " + P1 + " et P2 =" + P2
					+ " ne peuvent pas former un axe pouvant �tre normalis�." + SStringUtil.END_LINE_CARACTER + "\t"
					+ e.getMessage());
		}
	}

	@Override
	protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException {
		switch (code) {
		// On accepte ici deux type de mots cl� pour la lecture du d�but et la fin du
		// cylindre
		case SKeyWordDecoder.CODE_POINT:
		case SKeyWordDecoder.CODE_POSITION:
			readPosition(remaining_line);
			return true;

		case SKeyWordDecoder.CODE_RAY:
			R = readDoubleGreaterThanZero(remaining_line, SKeyWordDecoder.KW_RAY);
			return true;

		default:
			return false;
		}
	}

	/**
	 * M�thode pour faire la lecture d'un point et l'affectation d�pendra du num�ro
	 * du point en lecture d�termin� par la variable <i>reading_point</i>.
	 * 
	 * @param remaining_line - L'expression en string du vecteur positionnant le
	 *                       point du triangle.
	 * @throws SReadingException - S'il y a une erreur de lecture.
	 */
	private void readPosition(String remaining_line) throws SReadingException {
		switch (reading_point) {
		case 0:
			P1 = new SVector3d(remaining_line);
			break;

		case 1:
			SVector3d v = new SVector3d(remaining_line);

			if (v.equals(P1))
				throw new SReadingException("Erreur STubeGeometry 004 : Le point P2 = " + v
						+ " est identique au point P1 ce qui n'est pas acceptable.");

			P2 = v;
			break;

		default:
			throw new SReadingException("Erreur STubeGeometry 005 : Il y a d�j� 2 points de d�fini.");
		}

		reading_point++;
	}

	@Override
	protected SVector3d evaluateIntersectionNormal(SRay ray, double intersection_t) {
		return SGeometricUtil.outsideInfiniteTubeNormal(P1, S12, R, ray.getPosition(intersection_t));
	}

	@Override
	protected SVectorUV evaluateIntersectionUV(SRay ray, double intersection_t) {
		throw new SNoImplementationException("Erreur STubeGeometry 006 : La m�thode n'a pas �t� impl�ment�e.");
	}

	@Override
	protected void readingInitialization() throws SInitializationException {
		super.readingInitialization();

		initialize();
	}

	@Override
	public String getReadableName() {
		return SKeyWordDecoder.KW_TUBE;
	}

	@Override
	public String[] getReadableParameterName() {
		String[] other_parameters = super.getReadableParameterName();

		return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
	}

}// fin classe STubeGeometry
