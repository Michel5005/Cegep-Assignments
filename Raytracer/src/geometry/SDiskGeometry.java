/**
 * 
 */
package sim.geometry;

import java.io.BufferedWriter;
import java.io.IOException;

import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.graphics.SPrimitive;
import sim.math.SVector3d;
import sim.math.SVectorUV;
import sim.readwrite.SKeyWordDecoder;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SDiskGeometry</b> repr�sente la g�om�trie d'un disque.
 * 
 * @author Simon V�zina
 * @since 2015-06-20
 * @version 2017-12-20 (version labo � Le ray tracer v2.1)
 */
public class SDiskGeometry extends SPlaneGeometry {

	// --------------
	// CONSTANTES //
	// --------------

	/**
	 * La constante <b>KEYWORD_PARAMETER</b> correspond � un tableau contenant
	 * l'ensemble des mots cl�s � utiliser reconnus lors de la d�finition de l'objet
	 * par une lecture en fichier.
	 */
	private static final String[] KEYWORD_PARAMETER = { SKeyWordDecoder.KW_RAY };

	/**
	 * La constante 'DEFAULT_R' correspond au rayon par d�faut d'un disque.
	 */
	protected static final double DEFAULT_R = 1.0;

	// -------------
	// VARIABLES //
	// -------------

	/**
	 * La variable 'R' correspond au rayon du disque.
	 */
	protected double R;

	// -----------------
	// CONSTRUCTEURS //
	// -----------------

	/**
	 * Constructeur avec param�tre par d�faut d'un disque.
	 */
	public SDiskGeometry() {
		this(DEFAULT_POSITION, DEFAULT_SURFACE_NORMAL, DEFAULT_R);
	}

	/**
	 * Constructeur d'un disque.
	 * 
	 * @param position - Le centre du disque.
	 * @param normal   - L'orientation de la normale � la surface du disque.
	 * @param R        - Le rayon du disque.
	 */
	public SDiskGeometry(SVector3d position, SVector3d normal, double R) {
		this(position, normal, R, null);
	}

	/**
	 * Constructeur d'un disque avec primitive comme parent.
	 * 
	 * @param position - Le centre du disque.
	 * @param normal   - L'orientation de la normale � la surface du disque.
	 * @param R        - Le rayon du disque.
	 * @param parent   - La primitive en parent.
	 * @throws SConstructorException Si le rayon du disque est n�gatif.
	 */
	public SDiskGeometry(SVector3d position, SVector3d normal, double R, SPrimitive parent)
			throws SConstructorException {
		super(position, normal, parent);

		// V�rification que le rayon soit positif
		if (R < 0.0)
			throw new SConstructorException(
					"Erreur SDiskGeometry 001 : Le disque se fait affecter un rayon R = " + R + " qui est n�gatif.");

		this.R = R;

		try {
			initialize();
		} catch (SInitializationException e) {
			throw new SConstructorException(
					"Erreur SDiskGeometry 002 : Une erreur lors de l'initialisation est survenue."
							+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(),
					e);
		}
	}

	/**
	 * Constructeur du disque � partir d'information lue dans un fichier de format
	 * txt. Puisqu'une g�om�trie est construite � l'int�rieure d'une primitive, une
	 * r�f�rence � celle-ci doit �tre int�gr�e au constructeur pour y a voir acc�s.
	 * 
	 * @param sbr    - Le BufferedReader cherchant l'information dans le fichier
	 *               txt.
	 * @param parent - La primitive qui fait la construction de cette g�om�trie (qui
	 *               est le parent).
	 * @throws IOException           Si une erreur de de type I/O est lanc�e.
	 * @throws SConstructorException Si une erreur lors de la construction de la
	 *                               g�om�trie est survenue.
	 * @see SBufferedReader
	 * @see SPrimitive
	 */
	public SDiskGeometry(SBufferedReader sbr, SPrimitive parent) throws IOException, SConstructorException {
		this(DEFAULT_POSITION, DEFAULT_SURFACE_NORMAL, DEFAULT_R, parent);

		try {
			read(sbr);
		} catch (SInitializationException e) {
			throw new SConstructorException("Erreur SDiskGeometry 003 : Une erreur d'initialisation est survenue."
					+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(), e);
		}
	}

	// ------------
	// M�THODES //
	// ------------

	/**
	 * M�thode pour obtenir le rayon du disque.
	 * 
	 * @return le rayon du disque.
	 */
	public double getRay() {
		return R;
	}

	@Override
	public int getCodeName() {
		return SAbstractGeometry.DISK_CODE;
	}

	@Override
	public SRay intersection(SRay ray) throws SAlreadyIntersectedRayException {
		// Faire l'intersection avec le plan du disque par l'h�ritage de l'intersection
		// avec un plan
		SRay ray_plane_intersection = super.intersection(ray);
		
		// V�rifier s'il y a eu intersection entre le rayon et le plan du disque
		if (!ray_plane_intersection.asIntersected()) {
			return ray;
		} else {
			boolean inter = false;
			SVector3d pos = position;
			SVector3d pos2 = ray_plane_intersection.getIntersectionPosition();
			double d = Math.sqrt((pos.getX() - pos2.getX()) * (pos.getX() - pos2.getX())+ (pos.getY() - pos2.getY()) * (pos.getY() - pos2.getY()));
			System.out.println(d);
			System.out.println(R);
			if (d < R) {
				inter = true;
			}
			if (inter) {
				return ray_plane_intersection;
			} else {
				return ray;
			}
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

	@Override
	public void write(BufferedWriter bw) throws IOException {
		bw.write(SKeyWordDecoder.KW_DISK);
		bw.write(SStringUtil.END_LINE_CARACTER);

		// �crire les propri�t�s de la classe SDiskGeometry et ses param�tres h�rit�s
		writeSDiskGeometryParameter(bw);

		bw.write(SKeyWordDecoder.KW_END);
		bw.write(SStringUtil.END_LINE_CARACTER);
		bw.write(SStringUtil.END_LINE_CARACTER);
	}

	/**
	 * M�thode pour �crire les param�tres associ�s � la classe SDiskGeometry et ses
	 * param�tres h�rit�s.
	 * 
	 * @param bw - Le BufferedWriter �crivant l'information dans un fichier txt.
	 * @throws IOException Si une erreur I/O s'est produite.
	 * @see IOException
	 */
	protected void writeSDiskGeometryParameter(BufferedWriter bw) throws IOException {
		// �crire les param�tres h�rit�s de la classe SPlaneGeometry
		writeSPlaneGeometryParameter(bw);

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

	}

	@Override
	protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException {
		switch (code) {
		case SKeyWordDecoder.CODE_RAY:
			R = readDoubleGreaterThanZero(remaining_line, SKeyWordDecoder.KW_RAY);
			return true;

		default:
			return super.read(sbr, code, remaining_line);
		}
	}

	@Override
	protected SVectorUV evaluateIntersectionUV(SRay ray, double intersection_t) {
		throw new SNoImplementationException("Erreur SDiskGeometry 004 : La m�thode n'a pas encore �t� impl�ment�e.");
	}

	@Override
	protected void readingInitialization() throws SInitializationException {
		super.readingInitialization();

		initialize();
	}

	@Override
	public String getReadableName() {
		return SKeyWordDecoder.KW_DISK;
	}

	@Override
	public String[] getReadableParameterName() {
		String[] other_parameters = super.getReadableParameterName();

		return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
	}

}// fin de la classe SDiskGeometry
