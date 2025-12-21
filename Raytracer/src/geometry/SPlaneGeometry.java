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
 * La classe <b>SPlaneGeometry</b> repr�sente la g�om�trie d'un plan infini.
 * 
 * @author Simon V�zina
 * @since 2015-01-19
 * @version 2017-12-20 (version labo � Le ray tracer v2.1)
 */
public class SPlaneGeometry extends SAbstractGeometry {

	// --------------
	// CONSTANTES //
	// --------------

	/**
	 * La constante <b>KEYWORD_PARAMETER</b> correspond � un tableau contenant
	 * l'ensemble des mots cl�s � utiliser reconnus lors de la d�finition de l'objet
	 * par une lecture en fichier.
	 */
	private static final String[] KEYWORD_PARAMETER = { SKeyWordDecoder.KW_POSITION, SKeyWordDecoder.KW_NORMAL };

	/**
	 * La constante <b>DEFAULT_POSITION</b> correspond � la position par d�faut du
	 * plan �tant � l'origine.
	 */
	protected static SVector3d DEFAULT_POSITION = new SVector3d(0.0, 0.0, 0.0);

	/**
	 * La constante <b>DEFAULT_SURFACE_NORMAL</b> correspond � la normale � la
	 * surface du plan par d�faut �tant orient�e selon l'axe z.
	 */
	protected static SVector3d DEFAULT_SURFACE_NORMAL = new SVector3d(0.0, 0.0, 1.0);

	// -------------
	// VARIABLES //
	// -------------

	/**
	 * La variable <b>position</b> correspond � la position du <b>centre</b> du
	 * plan.
	 */
	protected SVector3d position;

	/**
	 * La variable <b>surface_normal</b> correspond � la normale � la surface du
	 * plan.
	 */
	protected SVector3d surface_normal;

	// -----------------
	// CONSTRUCTEURS //
	// -----------------

	/**
	 * Constructeur d'un plan infini par d�faut.
	 */
	public SPlaneGeometry() {
		this(DEFAULT_POSITION, DEFAULT_SURFACE_NORMAL);
	}

	/**
	 * Constructeur d'un plan infini avec la position de r�f�rence et la normale �
	 * la surface.
	 * 
	 * @param position - La position de r�f�rence du plan.
	 * @param normal   - La normale � la surface du plan.
	 */
	public SPlaneGeometry(SVector3d position, SVector3d normal) {
		this(position, normal, null);
	}

	/**
	 * Constructeur d'un plan infini avec primitive comme parent en r�f�rence.
	 * 
	 * @param position - La position de r�f�rence du plan.
	 * @param normal   - La normale � la surface du plan.
	 * @param parent   - La primitive parent � cette g�om�trie.
	 * @throws SConstructorException Si une erreur est survenue durant la
	 *                               construction de la g�om�trie.
	 */
	public SPlaneGeometry(SVector3d position, SVector3d normal, SPrimitive parent) throws SConstructorException {
		super(parent);

		this.position = position;
		surface_normal = normal;

		try {
			initialize();
		} catch (SInitializationException e) {
			throw new SConstructorException("Erreur SPlaneGeometry 001 : Une erreur d'initialisation est survenue."
					+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(), e);
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
	 * @throws IOException Si une erreur de l'objet SBufferedWriter est lanc�e.
	 * @see SBufferedReader
	 * @see SPrimitive
	 */
	public SPlaneGeometry(SBufferedReader sbr, SPrimitive parent) throws IOException {
		this(DEFAULT_POSITION, DEFAULT_SURFACE_NORMAL, parent);

		try {
			read(sbr);
		} catch (SInitializationException e) {
			throw new SConstructorException("Erreur SPlaneGeometry 002 : Une erreur d'initialisation est survenue."
					+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(), e);
		}
	}

	// ------------
	// M�THODES //
	// ------------

	/**
	 * M�thode pour obtenir la position du plan. Cette position correspond au
	 * <b>centre</b> du plan.
	 * 
	 * @return la position du centre du plan.
	 */
	public SVector3d getPosition() {
		return position;
	}

	/**
	 * M�thode pour obtenir la normale � la surface du plan.
	 * 
	 * @return la normale � la surface du plan.
	 */
	public SVector3d getNormal() {
		return surface_normal;
	}

	@Override
	public int getCodeName() {
		return SAbstractGeometry.PLANE_CODE;
	}

	@Override
	public SRay intersection(SRay ray) throws SAlreadyIntersectedRayException {
		double[] inter = SGeometricIntersection.planeIntersection(ray, this.position, this.surface_normal);
		boolean superieur = false;
		int j = 0;
		double valeur = 0;
		if (inter.length == 0) {
			return ray;
		}
		for (int i = 0; i <= inter.length; i++) {
			valeur = inter[i];
			if (valeur > SRay.getEpsilon()) {
				superieur = true;
				j = i;
				return ray.intersection(this, evaluateIntersectionNormal(ray, valeur), inter[i]);
			}
			if (valeur < SRay.getEpsilon()) {
				return ray;
			}
		}
		if (superieur) {
			return ray.intersection(this, this.surface_normal, inter[j]);
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

	@Override
	public void write(BufferedWriter bw) throws IOException {
		bw.write(SKeyWordDecoder.KW_PLANE);
		bw.write(SStringUtil.END_LINE_CARACTER);

		// �crire les propri�t�s de la classe SSphereGeometry et ses param�tres h�rit�s
		writeSPlaneGeometryParameter(bw);

		bw.write(SKeyWordDecoder.KW_END);
		bw.write(SStringUtil.END_LINE_CARACTER);
		bw.write(SStringUtil.END_LINE_CARACTER);

	}

	/**
	 * M�thode pour �crire les param�tres associ�s � la classe SSphereGeometry et
	 * ses param�tres h�rit�s.
	 * 
	 * @param bw - Le BufferedWriter �crivant l'information dans un fichier txt.
	 * @throws IOException Si une erreur I/O s'est produite.
	 * @see IOException
	 */
	protected void writeSPlaneGeometryParameter(BufferedWriter bw) throws IOException {
		bw.write(SKeyWordDecoder.KW_POSITION);
		bw.write("\t");
		position.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);

		bw.write(SKeyWordDecoder.KW_NORMAL);
		bw.write("\t\t");
		surface_normal.write(bw);
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
			surface_normal = surface_normal.normalize();
		} catch (SImpossibleNormalizationException e) {
			throw new SInitializationException(
					"Erreur SPlaneGeometry 004 : Une erreur est survenue lors de la normalisation de la normale � la surface. Cette op�ration est impossible."
							+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(),
					e);
		}
	}

	@Override
	protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException {
		switch (code) {
		case SKeyWordDecoder.CODE_POSITION:
			position = new SVector3d(remaining_line);
			return true;

		case SKeyWordDecoder.CODE_NORMAL:
			surface_normal = new SVector3d(remaining_line);
			return true;

		default:
			return false;
		}
	}

	@Override
	protected SVector3d evaluateIntersectionNormal(SRay ray, double intersection_t) {
		// L'orientation par d�faut du plan est consid�r� comme �tant son c�t�
		// ext�rieur.
		return surface_normal;
	}

	@Override
	protected SVectorUV evaluateIntersectionUV(SRay ray, double intersection_t) {
		throw new SNoImplementationException("Erreur SPlaneGeometry 005 : La m�thode n'a pas encore �t� impl�ment�e.");
	}

	@Override
	protected void readingInitialization() throws SInitializationException {
		super.readingInitialization();

		initialize();
	}

	@Override
	public String getReadableName() {
		return SKeyWordDecoder.KW_PLANE;
	}

	@Override
	public String[] getReadableParameterName() {
		String[] other_parameters = super.getReadableParameterName();

		return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
	}

}// fin de la classe SPlaneGeometry
