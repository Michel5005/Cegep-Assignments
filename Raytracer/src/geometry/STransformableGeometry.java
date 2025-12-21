/**
 * 
 */
package sim.geometry;

import java.io.BufferedWriter;
import java.io.IOException;

import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.graphics.SPrimitive;
import sim.math.SAffineTransformation;
import sim.math.SMatrix4x4;
import sim.math.SVector3d;
import sim.math.SVectorUV;
import sim.readwrite.SKeyWordDecoder;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SLog;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>STransformableGeometry</b> qui repr�sente une g�om�trie pouvant
 * �tre transform�e � l'aide de matrices de transformations lin�aires comme la
 * <b>translation</b>, la <b>rotation</b> et <b>l'homoth�tie</b> (<i>scale</i>).
 * Cette g�om�trie devra contenir une g�om�trie interne donnant la forme de base
 * (sans transformation) � la g�om�trie transformable.
 * 
 * @author Simon V�zina
 * @since 2015-07-17
 * @version 2017-12-20 (version labo � Le ray tracer v2.1)
 */
public class STransformableGeometry extends SAbstractGeometry {

	// --------------
	// CONSTANTES //
	// --------------

	/**
	 * La constante <b>KEYWORD_PARAMETER</b> correspond � un tableau contenant
	 * l'ensemble des mots cl�s � utiliser reconnus lors de la d�finition de l'objet
	 * par une lecture en fichier.
	 */
	private static final String[] KEYWORD_PARAMETER = { SKeyWordDecoder.KW_SCALE, SKeyWordDecoder.KW_ROTATION,
			SKeyWordDecoder.KW_TRANSLATION };

	/**
	 * La constante <b>DEFAULT_GEOMETRY</b> correspond � la g�om�trie par d�faut
	 * �tant <b>null</b>. Cela signifie qu'il n'y a pas de g�om�trie interne � la
	 * g�om�trie transformable.
	 */
	private static final SGeometry DEFAULT_GEOMETRY = null;

	/**
	 * La constante <b>DEFAULT_SCALE</b> correspond au vecteur comprenant les
	 * informations � attribuer � une matrice de transformation d'homoth�tie
	 * (<i>scale</i>). Par d�faut, le vecteur sera (1.0, 1.0, 1.0) ce qui correspond
	 * � aucune d�formation.
	 */
	private static final SVector3d DEFAULT_SCALE = new SVector3d(1.0, 1.0, 1.0);

	/**
	 * La constante <b>DEFAULT_ROTATION</b> correspond au vecteur comprenant les
	 * informations � attribuer � une matrice de transformation de rotation. Par
	 * d�faut, le vecteur sera (0.0, 0.0, 0.0) ce qui correspond � aucune rotation.
	 */
	private static final SVector3d DEFAULT_ROTATION = new SVector3d(0.0, 0.0, 0.0);

	/**
	 * La constante <b>DEFAULT_TRANSLATION</b> correspond au vecteur comprenant les
	 * informations � attribuer � une matrice de transformation de translation. Par
	 * d�faut, le vecteur sera (0.0, 0.0, 0.0) ce qui correspond � aucune
	 * translation.
	 */
	private static final SVector3d DEFAULT_TRANSLATION = new SVector3d(0.0, 0.0, 0.0);

	// -------------
	// VARIABLES //
	// -------------

	/**
	 * La variable <b>geometry</b> correspond � la g�om�trie interne � la g�om�trie
	 * transformable.
	 */
	protected SGeometry geometry;

	/**
	 * La variable <b>scale</b> correspond au vecteur comprenant les informations �
	 * attribuer � une matrice de transformation d'homoth�tie (<i>scale</i>).
	 */
	private SVector3d scale;

	/**
	 * La variable <b>rotation</b> correspond au vecteur comprenant les informations
	 * � attribuer � une matrice de transformation de rotation.
	 */
	private SVector3d rotation;

	/**
	 * La variable <b>translation</b> correspond au vecteur comprenant les
	 * informations � attribuer � une matrice de transformation de translation.
	 */
	private SVector3d translation;

	/**
	 * Constructeur d'une g�om�trie transformable � l'aide d'une g�om�trie interne.
	 * 
	 * @param geometry - La g�om�trie interne.
	 */
	public STransformableGeometry(SGeometry geometry) {
		this(geometry, DEFAULT_SCALE, DEFAULT_ROTATION, DEFAULT_TRANSLATION);

	}

	/**
	 * Constructeur d'une g�om�trie transformable � l'aide d'une g�om�trie interne
	 * et des matrices de transformations lin�aires comme <b>l'homoth�thie</b>
	 * (<i>scale</i>), <b>la rotation</b> et <b>la translation</b>.
	 * 
	 * @param geometry  - La g�om�trie transform�e.
	 * @param scale     - Le vecteur d�finissant la matrice <b>d'homoth�thie</b>
	 *                  (<i>scale</i>).
	 * @param rotation  - Le vecteur d�finissant la matrice <b>de rotation</b> (en
	 *                  degr�).
	 * @param translate - Le vecteur d�finissant la matrice <b>de translation</b>.
	 */
	public STransformableGeometry(SGeometry geometry, SVector3d scale, SVector3d rotation, SVector3d translation) {
		this(geometry, scale, rotation, translation, null);
	}

	/**
	 * Constructeur d'une g�om�trie transformable � l'aide d'une g�om�trie interne
	 * reli� � une primitive parent et des matrices de transformations lin�aires
	 * comme <b>l'homoth�thie</b> (<i>scale</i>), <b>la rotation</b> et <b>la
	 * translation</b>.
	 * 
	 * @param geometry  - La g�om�trie transform�e.
	 * @param scale     - Le vecteur d�finissant la matrice <b>d'homoth�thie</b>
	 *                  (<i>scale</i>).
	 * @param rotation  - Le vecteur d�finissant la matrice <b>de rotation</b> (en
	 *                  degr�).
	 * @param translate - Le vecteur d�finissant la matrice <b>de translation</b>.
	 * @param parent    - La primitive parent � cette g�om�trie.
	 * @throws SConstructorException Si une erreur est survenue lors de la
	 *                               construction de la g�om�trie.
	 */
	public STransformableGeometry(SGeometry geometry, SVector3d scale, SVector3d rotation, SVector3d translation,
			SPrimitive parent) throws SConstructorException {
		super(parent);

		this.geometry = geometry;

		this.scale = scale;
		this.rotation = rotation;
		this.translation = translation;

		try {
			initialize();
		} catch (SInitializationException e) {
			throw new SConstructorException(
					"Erreur STransformableGeometry 001 : Une erreur d'initialisation est survenue."
							+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(),
					e);
		}
	}

	/**
	 * Constructeur d'une g�om�trie transformable � partir d'information lue dans un
	 * fichier de format txt.
	 * 
	 * @param sbr    - Le BufferedReader cherchant l'information dans le fichier
	 *               txt.
	 * @param parent - La primitive qui fait la construction de cette g�om�trie (qui
	 *               est le parent).
	 * @throws IOException           Si une erreur de l'objet SBufferedWriter est
	 *                               lanc�e.
	 * @throws SConstructorException Si une erreur est survenue lors de la
	 *                               construction de la g�om�trie.
	 * @see SBufferedReader
	 */
	public STransformableGeometry(SBufferedReader sbr, SPrimitive parent) throws IOException, SConstructorException {
		this(DEFAULT_GEOMETRY, DEFAULT_SCALE, DEFAULT_ROTATION, DEFAULT_TRANSLATION, parent);

		try {
			read(sbr);
		} catch (SInitializationException e) {
			throw new SConstructorException(
					"Erreur STransformableGeometry 002 : Une erreur d'initialisation est survenue."
							+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(),
					e);
		}
	}

	/**
	 * M�thode pour obtenir la g�om�trie interne � la g�om�trie transformable.
	 * 
	 * @return La g�om�trie interne.
	 */
	public SGeometry getGeometry() {
		return geometry;
	}

	/**
	 * M�thode pour obtenir le vecteur d�finissant l'homoth�tie (<i>scale</i>) de la
	 * g�om�trie.
	 * 
	 * @return Le vecteur d'homoth�tie (<i>scale</i>).
	 */
	public SVector3d getScale() {
		return scale;
	}

	/**
	 * M�thode pour obtenir le vecteur d�finissant la rotation de la g�om�trie.
	 * 
	 * @return Le vecteur de rotation.
	 */
	public SVector3d getRotation() {
		return rotation;
	}

	/**
	 * M�thode pour obtenir le vecteur d�finissant la translation de la g�om�trie.
	 * 
	 * @return Le vecteur de translation.
	 */
	public SVector3d getTranslation() {
		return translation;
	}

	@Override
	public int getCodeName() {
		return SAbstractGeometry.TRANSFORMABLE_CODE;
	}

	/**
	 * M�thode qui d�termine s'il y a une g�om�trie interne � la g�om�trie
	 * transformable.
	 * 
	 * @return <b>true</b> s'il y a une g�om�trie interne et <b>false</b> sinon.
	 */
	private boolean isGeometrySelected() {
		return this.geometry != null;
	}

	@Override
	public boolean isClosedGeometry() {
		// S'il y a une g�om�trie interne � la g�om�trie transformable
		if (geometry != null)
			return geometry.isClosedGeometry();
		else
			return false;
	}

	@Override
	public boolean isInside(SVector3d v) {
		// S'il n'y a pas de g�om�trie interne � la g�om�trie transformable
		if (geometry == null) {
			return false;
		}
		if (isClosedGeometry()) {
			SMatrix4x4 matrix = new SMatrix4x4();

			// matrix.ScRxyzTr(scale, rotation, translation);
			SVector3d trans1 = new SVector3d(translation.getX(), translation.getY(), translation.getZ());
			SVector3d rot1 = new SVector3d(rotation.getX(), rotation.getY(), rotation.getZ());
			SVector3d scale1 = new SVector3d(scale.getX(), scale.getY(), scale.getZ());

			if (translation.getX() != 0) {
				trans1 = new SVector3d(-1 * translation.getX(), trans1.getY(), trans1.getZ());
			}
			if (translation.getY() != 0) {
				trans1 = new SVector3d(trans1.getX(), -1 * translation.getY(), trans1.getZ());
			}
			if (translation.getZ() != 0) {
				trans1 = new SVector3d(trans1.getX(), trans1.getY(), -1 * translation.getZ());
			}
			if (rotation.getX() != 0) {
				rot1 = new SVector3d(-1 * rotation.getX(), rot1.getY(), rot1.getZ());
			}
			if (rotation.getY() != 0) {
				rot1 = new SVector3d(rot1.getX(), -1 * rotation.getY(), rot1.getZ());
			}
			if (rotation.getZ() != 0) {
				rot1 = new SVector3d(rot1.getX(), rot1.getY(), -1 * rotation.getZ());
			}
			if (scale.getX() != 0) {
				scale1 = new SVector3d(1 / scale.getX(), scale1.getY(), scale1.getZ());
			}
			if (scale.getY() != 0) {
				scale1 = new SVector3d(scale1.getX(), 1 / scale.getY(), scale1.getZ());
			}
			if (scale.getZ() != 0) {
				scale1 = new SVector3d(scale1.getX(), scale1.getY(), 1 / scale.getZ());
			}

			matrix = SMatrix4x4.ScRxyzTr(scale1, rot1, trans1);
			SVector3d vTrans = SAffineTransformation.transformPosition(matrix, v);
			System.out.println(this.geometry.isInside(vTrans));
			return this.geometry.isInside(vTrans);
		}
		return false;
	}

	@Override
	public SRay intersection(SRay ray) throws SAlreadyIntersectedRayException {
		// V�rifier qu'il y a une g�om�trie interne � la g�om�trie transformable.
		if (geometry == null)
			return ray;

		// Modifiez à partir d'ici.
		SVector3d trans1 = new SVector3d(translation.getX(), translation.getY(), translation.getZ()).multiply(-1);
		SVector3d rota1 = new SVector3d(rotation.getX(), rotation.getY(), rotation.getZ()).multiply(-1);
		SVector3d scale1 = new SVector3d(scale.getX(), scale.getY(), scale.getZ());
		SMatrix4x4 matrix = new SMatrix4x4();
		if (scale.getX() != 0) {
			scale1 = new SVector3d(1 / scale.getX(), scale1.getY(), scale1.getZ());
		}
		if (scale.getY() != 0) {
			scale1 = new SVector3d(scale1.getX(), 1 / scale.getY(), scale1.getZ());
		}
		if (scale.getZ() != 0) {
			scale1 = new SVector3d(scale1.getX(), scale1.getY(), 1 / scale.getZ());
		}
		matrix = SMatrix4x4.ScRxyzTr(scale1, rota1, trans1);
		SMatrix4x4 matrixObjetMonde = new SMatrix4x4();
		matrixObjetMonde = SMatrix4x4.TrRzyxSc(translation, rotation, scale);
		SRay ray1 = ray.transformNotIntersectedRay(matrix);
		SRay inter = this.geometry.intersection(ray1);

		if (inter.asIntersected()) {
			return ray.intersection(this,
					SAffineTransformation.transformNormal(matrixObjetMonde, inter.getOutsideNormal()), inter.getT());
		}
		if (this.geometry.isTransparent() && this.geometry.isInside(inter.getDirection())) {
			return ray.intersection(this,
					SAffineTransformation.transformNormal(matrixObjetMonde, inter.getOutsideNormal()), inter.getT());
		}
		return ray;
	}

	@Override
	public void write(BufferedWriter bw) throws IOException {
		bw.write(SKeyWordDecoder.KW_GEOMETRY);
		bw.write(SStringUtil.END_LINE_CARACTER);

		// �crire les propri�t�s de la classe SSphereGeometry et ses param�tres h�rit�s
		writeSTransformableGeometryParameter(bw);

		bw.write(SKeyWordDecoder.KW_END);
		bw.write(SStringUtil.END_LINE_CARACTER);
		bw.write(SStringUtil.END_LINE_CARACTER);
	}

	/**
	 * M�thode pour �crire les param�tres associ�s � la classe
	 * STransformableGeometry et ses param�tres h�rit�s.
	 * 
	 * @param bw - Le BufferedWriter �crivant l'information dans un fichier txt.
	 * @throws IOException Si une erreur I/O s'est produite.
	 * @see IOException
	 */
	protected void writeSTransformableGeometryParameter(BufferedWriter bw) throws IOException {
		bw.write(SKeyWordDecoder.KW_SCALE);
		bw.write("\t");
		scale.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);

		bw.write(SKeyWordDecoder.KW_ROTATION);
		bw.write("\t");
		rotation.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);

		bw.write(SKeyWordDecoder.KW_TRANSLATION);
		bw.write("\t");
		translation.write(bw);
		bw.write(SStringUtil.END_LINE_CARACTER);

		if (geometry != null)
			geometry.write(bw);

		bw.write(SStringUtil.END_LINE_CARACTER);
	}

	@Override
	protected SVector3d evaluateIntersectionNormal(SRay ray, double intersection_t) {
		throw new SNoImplementationException(
				"Erreur STransformableGeometry 005 : Cette m�thode n'est pas impl�ment�e, car elle n'est pas utile au fonctionnement de la classe.");
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
	protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException, IOException {
		// Analyser le code sur la lecture d'une g�om�trie � l'aide du lecteur de
		// g�om�trie
		SGeometryReader reader = new SGeometryReader(sbr, code, null);

		// S'il y a eu lecture d'une g�om�trie
		if (reader.isRead())
			if (!isGeometrySelected()) {
				geometry = reader.getGeometry();
				return true;
			} else
				throw new SReadingException(
						"Erreur STranformableGeometry 006 : Une g�om�trie a d�j� �t� s�lectionn�e pour cette g�om�trie transformable.");

		// Analyser le code sur d'autres param�tres
		switch (code) {
		case SKeyWordDecoder.CODE_SCALE:
			scale = new SVector3d(remaining_line);
			return true;

		case SKeyWordDecoder.CODE_ROTATION:
			rotation = new SVector3d(remaining_line);
			return true;

		case SKeyWordDecoder.CODE_TRANSLATION:
			translation = new SVector3d(remaining_line);
			return true;

		default:
			return false;
		}
	}

	@Override
	protected SVectorUV evaluateIntersectionUV(SRay ray, double intersection_t) {
		throw new SNoImplementationException("Erreur STransformableGeometry 007 : La m�thode n'a pas �t� impl�ment�e.");
	}

	@Override
	protected void readingInitialization() throws SInitializationException {
		super.readingInitialization();

		initialize();

		// Affichage de message si la lecture est incompl�te
		if (!isGeometrySelected())
			SLog.logWriteLine(
					"Message STransformableGeometry : Une g�om�trie transformable (Geometry) a �t� lue mais initialis�e sans g�om�trie interne.");
	}

	@Override
	public String getReadableName() {
		return SKeyWordDecoder.KW_GEOMETRY;
	}

	@Override
	public String[] getReadableParameterName() {
		String[] other_parameters = super.getReadableParameterName();

		// Mettre les mots cl� du lecteur de g�om�trie (SGeometryReader)
		other_parameters = SStringUtil.merge(other_parameters, SGeometryReader.KEYWORD_PARAMETER);

		return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
	}

}// fin de la classe STransformableGeometry
