/**
 * 
 */
package sim.graphics;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.exception.SRuntimeException;
import sim.geometry.SAbstractGeometry;
import sim.geometry.SBTriangleGeometry;
import sim.geometry.SGeometry;
import sim.geometry.STransformableGeometry;
import sim.geometry.STriangleGeometry;
import sim.graphics.material.SMaterial;
import sim.graphics.material.STextureMaterial;
import sim.loader.SLoaderException;
import sim.loader.model.SModelLoader;
import sim.math.SAffineTransformation;
import sim.math.SImpossibleNormalizationException;
import sim.math.SMatrix4x4;
import sim.math.SVector3d;
import sim.readwrite.SAbstractReadable;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.readwrite.SKeyWordDecoder;
import sim.util.SLog;
import sim.util.SReader;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SModelReader</b> repr�sente un lecteur de mod�le 3d. L'objectif
 * de ce lecteur sera la lecture des propri�t�s du mod�le dans le fichier de
 * sc�ne comme le nom du fichier principalement.
 * 
 * @author Simon V�zina
 * @since 2015-07-22
 * @version 2020-01-08 (Version : Le ray tracer v2.107)
 */
public class SModelReader extends SAbstractReadable implements SReader {

	// --------------
	// CONSTANTES //
	// --------------

	/**
	 * La constante <b>KEYWORD_PARAMETER</b> correspond � un tableau contenant
	 * l'ensemble des mots cl�s � utiliser reconnus lors de la d�finition de l'objet
	 * par une lecture en fichier.
	 */
	private static final String[] KEYWORD_PARAMETER = { SKeyWordDecoder.KW_FILE, SKeyWordDecoder.KW_SCALE,
			SKeyWordDecoder.KW_ROTATION, SKeyWordDecoder.KW_TRANSLATION, SKeyWordDecoder.KW_UV_FORMAT };

	/**
	 * La constante <b>DEFAULT_MODEL</b> correspond au mod�le par d�faut �tant
	 * inexistant. Sa valeur est alors <b>null</b>.
	 */
	private static final SModel DEFAULT_MODEL = null; // mod�le par d�faut �tant non charg�

	// -------------
	// VARIABLES //
	// -------------

	/**
	 * La variable <b>model_map</b> correspond � la carte des mod�les d�j� lu et
	 * charg� en m�moire. On peut ainsi les r�utiliser et leur appliquer de
	 * nouvelles transformation pour les visualiser diff�remment. La <b>cl� de
	 * recherche</b> est le <b>nom du fichier</b>.
	 */
	private static final Map<String, SModel> model_map = new HashMap<String, SModel>();

	/**
	 * La variable <b>file_name</b> correspond au nom du fichier comprenant les
	 * informations d�finissant le mod�le.
	 */
	private String file_name;

	/**
	 * La variable <b>scale</b> correspond au vecteur comprenant les informations de
	 * la transformation d'hom�th�tie (<i>scale</i>).
	 */
	private SVector3d scale;

	/**
	 * La variable <b>rotation</b> correspond au vecteur comprenant les informations
	 * de la transformation de rotation.
	 */
	private SVector3d rotation;

	/**
	 * La variable <b>translation</b> correspond au vecteur comprenant les
	 * informations de la transformation de translation.
	 */
	private SVector3d translation;

	/**
	 * La variable <b>uv_format</b> correspond au code d'interpr�tation des
	 * coordonn�es uv que l'on doit appliquer au texture afin que chaque coordonn�e
	 * uv corresponde au bon texel des textures.
	 */
	private int uv_format;

	/**
	 * La variable <b>model</b> correspond au mod�le lu par le lecteur.
	 */
	SModel model;

	/**
	 * La varaible <b>is_read</b> d�termine si un mod�le a �t� lu avec succ�s par le
	 * lecteur.
	 */
	boolean is_read;

	// --------------------------------------------------
	// Param�tres de pr�calculs pour les transformations
	// --------------------------------------------------

	// -----------------
	// CONSTRUCTEURS //
	// -----------------

	/**
	 * Constructeur d'un lecteur de mod�le par d�faut.
	 */
	public SModelReader() {
		file_name = SModel.DEFAULT_FILE_NAME;

		scale = SModel.DEFAULT_SCALE;
		rotation = SModel.DEFAULT_ROTATION;
		translation = SModel.DEFAULT_TRANSLATION;

		uv_format = STexture.UV_DEFAULT;

		model = DEFAULT_MODEL;
		is_read = false;
	}

	/**
	 * Constructeur d'un constructeur de g�om�trie � partir d'information lue dans
	 * un fichier de format txt.
	 * 
	 * @param sbr - Le BufferedReader cherchant l'information dans le fichier txt.
	 * @throws IOException           Si une erreur de l'objet SBufferedWriter est
	 *                               lanc�e.
	 * @throws SConstructorException Si une erreur est survenue lors de la
	 *                               construction.
	 */
	public SModelReader(SBufferedReader sbr) throws IOException, SConstructorException {
		this();

		try {
			read(sbr);
		} catch (SInitializationException e) {
			throw new SConstructorException("Erreur SModelReader 001 : Une erreur d'initialisation est survenue."
					+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(), e);
		}
	}

	@Override
	public SModel getValue() throws SRuntimeException {
		if (is_read)
			return model;
		else
			throw new SRuntimeException("Erreur SModelReader 002 : Le mod�le '" + file_name + "' n'a pas �t� lu.");
	}

	@Override
	public boolean asRead() {
		return is_read;
	}

	/**
	 * M�thode pour faire l'initialisation de l'objet apr�s sa construction.
	 * 
	 * @throws SInitializationException Si une erreur est survenue lors de
	 *                                  l'initialisation.
	 */
	private void initialize() throws SInitializationException {

		try {

			// V�rifier si le mod�le a d�j� �t� lu
			if (model_map.containsKey(file_name))
				model = model_map.get(file_name);
			else {
				SModelLoader model_loader = new SModelLoader();

				SLog.logWriteLine("Message SModelReader : Lecture du mod�le '" + file_name + "'.");

				model = model_loader.loadModel(file_name); // lecture du mod�le (exception lanc�e s'il y a eu erreur)

				model_map.put(file_name, model); // mettre le mod�le lu dans la carte des mod�les
			}

			// Ex�cuter la transformation du mod�le par la construction d'un nouveau.
			model = transformModel(model);

			is_read = true;

		} catch (SLoaderException e) {
			SLog.logWriteLine("Erreur SModelReader 003 : Le chargement du mod�le '" + file_name + "' est impossible. "
					+ SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage());
		}

	}

	@Override
	protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException, IOException {
		// Lecture des diff�rentes g�om�trie reconnues par le lecteur
		switch (code) {
		case SKeyWordDecoder.CODE_FILE:
			file_name = readStringNotEmpty(remaining_line, SKeyWordDecoder.KW_FILE);
			return true;

		case SKeyWordDecoder.CODE_SCALE:
			scale = new SVector3d(remaining_line);
			return true;

		case SKeyWordDecoder.CODE_ROTATION:
			rotation = new SVector3d(remaining_line);
			return true;

		case SKeyWordDecoder.CODE_TRANSLATION:
			translation = new SVector3d(remaining_line);
			return true;

		case SKeyWordDecoder.CODE_UV_FORMAT:
			uv_format = readIntOrExpression(remaining_line, SKeyWordDecoder.KW_UV_FORMAT, STexture.UV_FORMAT);
			return true;

		default:
			return false;
		}
	}

	/**
	 * M�thode pour transformer l'int�gralit� des g�om�tries contenues dans le
	 * mod�le en fonction des matrices de transformation. Un nouveau mod�le avec
	 * l'applications des matrices de transformation sera g�n�r�.
	 * 
	 * @param model Le mod�le � transformer.
	 * @return Un nouveau mod�le o� l'application des matrices de transformation a
	 *         �t� r�alis�e.
	 */
	private SModel transformModel(SModel model) {
		// Construction d'un nouveau mod�le
		SModel transformed_model = new SModel(file_name, scale, rotation, translation, uv_format);

		// Obtenir la liste des primitives du mod�le
		List<SPrimitive> list = model.getPrimitiveList();

		// Param�tre d�terminant si une transformation de la g�om�trie de la primitive
		// est n�cessaire
		boolean transformation_required = !(scale.equals(SModel.DEFAULT_SCALE)
				&& rotation.equals(SModel.DEFAULT_ROTATION) && translation.equals(SModel.DEFAULT_TRANSLATION));

		if (!transformation_required)
			SLog.logWriteLine("Message SModelReader : Le mod�le " + file_name + " sera charg� sans transformation.");

		// G�om�trie en erreur de transformation
		int geometry_error = 0;

		// It�rer sur l'ensemble des g�om�tries
		for (SPrimitive p : list) {
			SGeometry geometry; // la primitive transform�e � ajouter � la liste
			SMaterial material = p.getMaterial(); // le mat�riel de la g�om�trie

			// Modification des g�om�tries en fonction du type qu'elle est
			// Des erreurs sont possibles si la transformation est impossible.
			// Par exemple : Un mod�le peut �tre mal d�fini pour certains triangles.
			try {

				// V�rifier si la transformation de la g�om�trie est n�cessaire.
				if (transformation_required) {
					switch (p.getGeometry().getCodeName()) {
					case SAbstractGeometry.TRIANGLE_CODE:
						geometry = transformTriangleGeometry((STriangleGeometry) p.getGeometry());
						break;

					case SAbstractGeometry.BTRIANGLE_CODE:
						geometry = transformBTriangleGeometry((SBTriangleGeometry) p.getGeometry());
						break;

					default:
						geometry = new STransformableGeometry(p.getGeometry(), scale, rotation, translation);
						break;
					}
				} else
					geometry = p.getGeometry(); // g�om�trie non transform�e

				// Modifier le format d'interpr�tation des coordonn�es uv de texture pour un
				// mat�riel avec texture.
				// Cependant, plusieurs instance du mod�le peuvent �tre construite.
				// Une seule interpr�tation des coordonn�e uv sera possible. Ce sera la premi�re
				// d�finition qui sera retenue.
				if (material.asTexture()) {
					STextureMaterial texture_material = (STextureMaterial) material;

					if (!texture_material.isUVFormatSelected())
						texture_material.setUVFormat(uv_format);
				}

				// Ajouter la nouvelle g�om�trie dans une nouvelle primitive et l'injecter dans
				// le mod�le.
				if (transformation_required)
					transformed_model.addPrimitive(new SPrimitive(geometry, material));
				else
					transformed_model.addPrimitive(p); // remettre l'ancienne primitive dans la nouveau mod�le

			} catch (SConstructorException e) {
				// S'il y a des g�om�tries en erreur de construction apr�s l'application des
				// transformations
				geometry_error++;
			} catch (SImpossibleNormalizationException e) {
				geometry_error++;
			}
		} // fin for

		// Afficher un message sur le nombre de g�om�trie en erreur de transformation
		if (geometry_error > 0)
			SLog.logWriteLine("Message SModelReader : Il y a '" + geometry_error
					+ "' g�om�tries en erreur de transformation. Ils ne seront pas disponibles pour l'affichage.");

		return transformed_model;
	}

	/**
	 * M�thode pour faire la transformation d'un STriangleGeometry dans l'espace
	 * objet (unitaire) vers l'espace monde (transform�) � partir des vecteurs de
	 * transformation lus par le lecteur.
	 * 
	 * @param triangle Le triangle � transform�
	 * @return La g�om�trie d'un triangle transform�.
	 */
	private SGeometry transformTriangleGeometry(STriangleGeometry triangle) {
		// -----------------------------------------------------------------------------------
		// POUR LABORATOIRE :
		// Vous devrez retourner un nouveau triangle transform�.
		// Par example, un nouveau triangle "non transform�" correspondrait
		// � l'instruction suivante :
		// return new STriangleGeometry(triangle.getP0(), triangle.getP1(),
		// triangle.getP2());
		// ------------------------------------------------------------------------------------
		SMatrix4x4 matrix = new SMatrix4x4();
		matrix = matrix.TrRzyxSc(translation, rotation, scale);
		return new STriangleGeometry(SAffineTransformation.transformPosition(matrix, triangle.getP0()),
				SAffineTransformation.transformPosition(matrix, triangle.getP1()),
				SAffineTransformation.transformPosition(matrix, triangle.getP2()));

	}

	/**
	 * M�thode pour faire la transformation d'un SBTriangleGeometry dans l'espace
	 * objet (unitaire) vers l'espace monde (transform�) � partir des vecteurs de
	 * transformation lus par le lecteur.
	 * 
	 * @param triangle Le triangle � transform�.
	 * @return La g�om�trie d'un triangle barycentrique transform�.
	 */
	private SGeometry transformBTriangleGeometry(SBTriangleGeometry triangle) {
		// -----------------------------------------------------------------------------------------
		// POUR LABORATOIRE :
		// Vous devrez retourner un nouveau triangle barycentrique transform�.
		// Par example, un nouveau triangle barycentrique "non transform�"
		// correspondrait
		// � l'instruction suivante :
		// return new SBTriangleGeometry(triangle.getP0(), triangle.getP1(),
		// triangle.getP2(),
		// triangle.getN0(), triangle.getN1(), triangle.getN2(),
		// triangle.getUV0(), triangle.getUV1(), triangle.getUV2());
		// -----------------------------------------------------------------------------------------
		SMatrix4x4 matrix = new SMatrix4x4();
		matrix = SMatrix4x4.TrRzyxSc(translation, rotation, scale);
		return new SBTriangleGeometry(SAffineTransformation.transformPosition(matrix, triangle.getP0()), SAffineTransformation.transformPosition(matrix, triangle.getP1()), SAffineTransformation.transformPosition(matrix, triangle.getP2()), SAffineTransformation.transformNormal(matrix, triangle.getN0()), SAffineTransformation.transformNormal(matrix, triangle.getN1()), SAffineTransformation.transformNormal(matrix, triangle.getN2()), triangle.getUV0(), triangle.getUV1(), triangle.getUV2());

	}

	@Override
	protected void readingInitialization() throws SInitializationException {
		initialize();
	}

	@Override
	public String getReadableName() {
		return SKeyWordDecoder.KW_MODEL;
	}

	@Override
	public String[] getReadableParameterName() {
		String[] other_parameters = super.getReadableParameterName();

		return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
	}

}
