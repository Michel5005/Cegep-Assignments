/**
 * 
 */
package sim.geometry;

import java.lang.Comparable;

import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.exception.SRuntimeException;
import sim.math.SAffineTransformation;
import sim.math.SMath;
import sim.math.SMatrix4x4;
import sim.math.SVector3d;
import sim.math.SVectorUV;

/**
 * <p>
 * La classe <b>SRay</b> repr�sente un rayon pouvant r�aliser une intersection
 * avec une g�om�trie. Un rayon est un objet <u>immuable</u> qui peut �tre dans
 * un �tant <b>intersect�</b> ou <b>non intersect�</b>.
 * </p>
 * 
 * <p>
 * Lorsqu'un rayon est dans un �tant <b>intersect�</b>, le rayon donne acc�s �
 * la g�om�trie intersect�e, qui elle donne acc�s � une primitive qui donne
 * finalement acc�s au mat�riel appliqu� sur la g�om�trie. C'est le param�tre
 * <i>t</i> repr�sentant le temps pour r�aliser l'intersection qui permet
 * d'ordonner la visibilit� des g�om�tries de la sc�ne.
 * </p>
 * 
 * <p>
 * Cette classe impl�mente l'interface <b>Comparable</b> (permettant les usages
 * des m�thodes de triage des librairies de base de java). La comparaison sera
 * effectu�e sur la <b>valeur du champ local t</b> qui repr�sente le <b>temps
 * afin d'intersecter une g�om�trie</b>. S'il n'y a <b>pas d'intersection</b>,
 * la valeur de t sera �gale � <b>l'infini</b>.
 * </p>
 *
 * @author Simon V�zina
 * @since 2014-12-30
 * @version 2017-12-20 (version labo � Le ray tracer v2.1)
 */
public class SRay implements Comparable<SRay> {

	// --------------
	// CONSTANTES //
	// --------------

	/**
	 * La constante <b>MINIMUM_EPSILON</b> correspond � <b>la plus petite valeur du
	 * temps mininal de parcours</b> qu'un rayon doit effectuer avant de pouvoir
	 * intersecter une g�om�trie. Cette contrainte est n�cessaire pour une raison de
	 * stabilit� num�rique. Elle est pr�sentement d�finie � {@value} elle et doit
	 * <b>toujours �tre sup�rieure � SMath.EPSILON</b>.
	 */
	public static final double MINIMUM_EPSILON = 1e-8;

	/**
	 * La constante <b>MAXIMAM_T</b> correspond � un temps signifiant que le rayon
	 * n'a pas r�ussi � intersecter de g�om�trie. Cette valeur correspond � un
	 * <b>temps infini</b>.
	 */
	private static final double MAXIMUM_T = SMath.INFINITY;

	/**
	 * La constante <b>DEFAULT_REFRACTIV_INDEX</b> correspond � la valeur de
	 * l'indice de r�fraction par d�faut o� un rayon voyage. Cet indice de
	 * r�fraction est �gal � celui du vide (n = {@value}).
	 */
	public static final double DEFAULT_REFRACTIVE_INDEX = 1.0;

	// -----------------------
	// VARIABLES STATIQUES //
	// -----------------------

	/**
	 * La variable <b>epsilon</b> correspond au temps mininal de parcours qu'un
	 * rayon doit effectuer avant de pouvoir intersecter une g�om�trie.
	 */
	private static double epsilon = MINIMUM_EPSILON;

	// -------------
	// VARIABLES //
	// -------------

	/**
	 * La variable <b>origin</b> correspond � l'origine du rayon (d'o� est lanc� le
	 * rayon).
	 */
	private final SVector3d origin;

	/**
	 * La variable <b>direction</b> correspond � la direction du rayon (dans quelle
	 * direction le rayon voyagera). On peut �galement comparer cette variable � la
	 * vitesse du rayon.
	 */
	private final SVector3d direction;

	/**
	 * La variable <b>as_intersected</b> d�termine si le rayon a d�j� r�alis� une
	 * intersection.
	 */
	private final boolean as_intersected;

	/**
	 * La varibale <b>geometry</b> correspond � la g�om�trie intersect� par le
	 * rayon.
	 */
	private final SGeometry geometry;

	/**
	 * La variable <b>normal</b> correspond � la normale � la surface sur la
	 * g�om�trie intersect�e � l'endroit o� l'intersection a �t� r�alis�e. Cette
	 * normale correspond � la <u>normale ext�rieure</u> � la g�om�trie.
	 */
	private final SVector3d normal;

	/**
	 * La variable <b>uv</b> correspond � la coordonn�e <i>uv</i> de texture
	 * associ�e � la g�om�trie intersect�e par le rayon.
	 */
	private final SVectorUV uv;

	/**
	 * La variable <b>int_t</b> correspond au temps requis au rayon pour intersecter
	 * la g�ometrie.
	 */
	private final double int_t;

	/**
	 * La variable <b>refractive_index</b> correspond � l'indice de r�fraction du
	 * milieu o� voyage le rayon.
	 */
	private final double refractive_index;

	/**
	 * La variable <b>previous_ray</b> correspond au rayon pr�c�dent au rayon
	 * pr�sent dans la <i>hi�rarchie r�cursive</i> du processus de lanc� de rayon.
	 */
	private final SRay previous_ray;

	// -----------------------
	// CONSTRUCTEUR PUBLIC //
	// -----------------------

	/**
	 * Constructeur d'un rayon dont l'objectif sera de tenter d'intersecter une
	 * g�om�trie.
	 * 
	 * @param origin           L'origine du rayon.
	 * @param direction        La direction du rayon.
	 * @param refractive_index L'indice de r�fraction du milieu o� voyage le rayon.
	 * @throws SConstructorException Si la direction du rayon correspond � un
	 *                               vecteur nul (pas d'orientation).
	 */
	public SRay(SVector3d origin, SVector3d direction, double refractive_index) throws SConstructorException {
		this(origin, direction, refractive_index, null);
	}

	// ----------------------
	// CONSTRUCTEUR PRIV� //
	// ----------------------

	/**
	 * Constructeur d'un rayon dont l'objectif sera de tenter d'intersection une
	 * g�om�trie. Ce rayon poss�de cependant un rayon parent pr�c�dent d�finissant
	 * l'ordre r�cursif du lanc� des rayons.
	 * 
	 * @param origin           L'origine du rayon.
	 * @param direction        La direction du rayon.
	 * @param refractive_index L'indice de r�fraction du milieu o� voyage le rayon.
	 * @param previous_ray     Le rayon pr�c�dent dans la hi�rarchie r�cursive du
	 *                         lanc� des rayons.
	 * @throws SConstructorException Si un param�tre est invalide lors de la
	 *                               construction du rayon.
	 */
	private SRay(SVector3d origin, SVector3d direction, double refractive_index, SRay previous_ray)
			throws SConstructorException {
		// V�rifier que nous n'avons pas de valeur nulle pour l'origine et
		// l'orientation.
		if (origin == null || direction == null)
			throw new SConstructorException("Erreur SRay 001 : Le rayon est mal d�fini, car origin = " + origin
					+ " et direction = " + direction + " .");

		// V�rification de l'orientation du rayon.
		if (direction.modulus() < SMath.EPSILON)
			throw new SConstructorException(
					"Erreur SRay 001 : La direction du rayon �gale � " + direction + " poss�de un module inf�rieur � "
							+ SMath.EPSILON + " ce qui ne peut pas lui octroyer d'orientation.");

		this.origin = origin;
		this.direction = direction;

		this.as_intersected = false;

		this.geometry = null;
		this.normal = null;
		this.uv = null;
		this.int_t = MAXIMUM_T;

		this.refractive_index = refractive_index;

		this.previous_ray = previous_ray;
	}

	/**
	 * Constructeur d'un rayon ayant r�alis� une intersection avec une g�om�trie
	 * (sans coordonn�e de texture uv).
	 * 
	 * @param origin           L'origine du rayon.
	 * @param direction        La direction du rayon.
	 * @param geometry         La g�om�trie intersect�e.
	 * @param normal           L'orientation de la normale � la surface.
	 * @param t                Le temps pour le rayon afin d'atteindre la g�om�trie.
	 * @param refractive_index L'indice de r�fraction du milieu o� voyage le rayon.
	 * @param previous_ray     Le rayon pr�c�dent dans la hi�rarchie r�cursive du
	 *                         lanc� des rayons.
	 * @throws SConstructorException Si le temps du rayon est inf�rieur �
	 *                               <i>epsilon</i>.
	 * @throws SConstructorException Si un param�tre est invalide lors de la
	 *                               construction du rayon.
	 */
	private SRay(SVector3d origin, SVector3d direction, SGeometry geometry, SVector3d normal, double t,
			double refractive_index, SRay previous_ray) throws SConstructorException {
		this(origin, direction, geometry, normal, null, t, refractive_index, previous_ray);
	}

	/**
	 * Constructeur d'un rayon ayant r�alis� une intersection avec une g�om�trie
	 * avec coordonn�e de testure uv.
	 * 
	 * @param origin           L'origine du rayon.
	 * @param direction        La direction du rayon.
	 * @param geometry         La g�om�trie intersect�e.
	 * @param normal           L'orientation de la normale � la surface.
	 * @param uv               La coordonn�e de texture. Une valeur <b>null</b>
	 *                         signifie qu'il n'y aura pas de coordonn�e
	 *                         d'attribu�e.
	 * @param t                Le temps pour le rayon afin d'atteindre la g�om�trie.
	 * @param refractive_index L'indice de r�fraction du milieu o� voyage le rayon.
	 * @param previous_ray     Le rayon pr�c�dent dans la hi�rarchie r�cursive du
	 *                         lanc� des rayons.
	 * @throws SConstructorException Si le temps du rayon est inf�rieur �
	 *                               <i>epsilon</i>.
	 * @throws SConstructorException Si un param�tre est invalide lors de la
	 *                               construction du rayon.
	 */
	private SRay(SVector3d origin, SVector3d direction, SGeometry geometry, SVector3d normal, SVectorUV uv, double t,
			double refractive_index, SRay previous_ray) throws SConstructorException {
		// V�rifier que nous n'avons pas de valeur nulle pour l'origine et
		// l'orientation.
		if (origin == null || direction == null || normal == null)
			throw new SConstructorException("Erreur SRay 001 : Le rayon est mal d�fini, car origin = " + origin
					+ ", direction = " + direction + " et normal = " + normal + " .");

		// V�rification que la g�om�trie n'est pas NULL.
		if (geometry == null)
			throw new SConstructorException("Erreur SRay 003 : La g�om�trie intersect�e ne peut pas �tre 'null'.");

		// V�rification pour teste num�rique
		if (t < epsilon)
			throw new SConstructorException(
					"Erreur SRay 002 : Pour des raisons num�riques, le temps de l'intersection t = " + t
							+ " ne peut pas �tre inf�rieur � epsilon = " + epsilon + " ou n�gatif.");

		// V�rification de l'orientation du rayon
		if (direction.modulus() < SMath.EPSILON)
			throw new SConstructorException(
					"Erreur SRay 003 : La direction du rayon �gale � " + direction + " poss�de un module inf�rieur � "
							+ SMath.EPSILON + " ce qui ne peut pas lui octroyer d'orientation.");

		this.origin = origin;
		this.direction = direction;
		this.geometry = geometry;
		this.normal = normal;
		this.int_t = t;
		this.as_intersected = true;
		this.refractive_index = refractive_index;
		this.previous_ray = previous_ray;

		// Le param�tre uv peut �tre 'null'. Sous cette condition, il n'y aura pas de
		// coordonn�e uv d'affectable � l'intersection.
		this.uv = uv;
	}

	// ------------
	// M�THODES //
	// ------------

	/**
	 * M�thode pour d�finir le temps/distance minimal que doit effectuer un rayon
	 * afin de valider une intersection. Cette valeur est n�cessaire pour des
	 * raisons num�riques. Elle doit �tre positive et sup�rieure � un valeur minimal
	 * de 1e-6. Il peut �tre affect� en fonction de la position du front clipping
	 * plane d'une pyramide de vue (view Frustum).
	 * 
	 * @param e - La valeur d'epsilon.
	 * @throws SRuntimeException Si la nouvelle valeur d'epsilon est inf�rieure � la
	 *                           valeur minimale accept�e par la classe.
	 * @see MINIMUM_EPSILON
	 */
	public static void setEpsilon(double e) throws SRuntimeException {
		// Mettre la valeur d'epsilon positive
		if (e < MINIMUM_EPSILON)
			throw new SRuntimeException("Erreur SRay 004 : Pour des raisons num�riques, la nouvelle valeur d'epsilon '"
					+ e + "' doit �tre sup�rieure � " + MINIMUM_EPSILON + ".");

		epsilon = e;
	}

	/**
	 * M�thode pour obtenir la valeur d'epsilon pr�sentement en vigueur pour les
	 * calculs d'intersection entre les rayons et les g�om�tries.
	 * 
	 * @return La valeur d'epsilon.
	 */
	public static double getEpsilon() {
		return epsilon;
	}

	/**
	 * M�thode pour obtenir l'origine du rayon.
	 * 
	 * @return L'origine du rayon.
	 */
	public SVector3d getOrigin() {
		return origin;
	}

	/**
	 * M�thode pour obtenir la direction du rayon.
	 * 
	 * @return La direction du rayon.
	 */
	public SVector3d getDirection() {
		return direction;
	}

	/**
	 * M�thode pour obtenir la position d'un rayon apr�s un d�placement de celui-ci
	 * d'un temps <i>t</i>.
	 * 
	 * @param t Le temps � �couler dans le d�placement du rayon.
	 * @return Le vecteur position du rayon apr�s d�placement.
	 */
	public SVector3d getPosition(double t) {

		SVector3d vec = new SVector3d();
		vec = origin.add(direction.multiply(t));

		return vec;
	}

	/**
	 * M�thode pour d�terminer si le rayon a effectu� une intersection avec une
	 * g�om�trie.
	 * 
	 * @return <b>true</b> s'il y a eu une intersection et <b>false</b> sinon.
	 */
	public boolean asIntersected() {
		return as_intersected;
	}

	/**
	 * M�thode pour d�terminer si le rayon courant poss�de hi�rarchiquement un rayon
	 * avant lui.
	 * 
	 * @return <b>true</b> si un rayon a �t� lanc� hi�rarchiement avant celui-ci et
	 *         <b>false</b> sinon.
	 */
	public boolean asPreviousRay() {
		return previous_ray != null;
	}

	/**
	 * M�thode pour d�terminer si le rayon a effectu� une intersection de
	 * l'int�rieur de la g�om�trie.
	 * <p>
	 * Cette m�thode ne fait que comparer la direction du rayon avec la normale � la
	 * surface ext�rieur de la g�om�trie. M�me si la g�om�trie n'est pas ferm�e
	 * (donc pas d'int�rieur), cette m�thode peut quand m�me retourner <b>true</b>.
	 * 
	 * @return <b>true</b> s'il y a eu une intersection � l'int�rieur de la
	 *         g�om�trie et <b>false</b> sinon.
	 * @throws SNotIntersectedRayException Si le rayon n'a pas effectu�
	 *                                     d'intersection.
	 */
	public boolean isInsideIntersection() throws SNotIntersectedRayException {
		if (!as_intersected)
			throw new SNotIntersectedRayException(
					"Erreur SRay 006 : Le rayon n'a pas effectu� d'intersection avec une g�om�trie.");

		// Afin de d�terminer si une intersection est r�alis�e par l'int�rieur de la
		// g�om�trie,
		// nous comparons l'orientation du rayon avec la normal (dite ext�rieure).
		// Si les orientations sont identique (produit scalaire positif),
		// alors l'intersection est int�rieur.
		return direction.dot(normal) > 0;
	}

	/**
	 * M�thode pour obtenir le temps (ou la distance puisque la direction est
	 * unitaire) afin de r�aliser une intersection entre le rayon et une g�om�trie.
	 * Si le rayon n'a pas �t� intersect�, la valeur sera <b> l'infini </b>.
	 * 
	 * @return Le temps pour effectuer l'intersection (s'il y a intersection) et
	 *         <b>l'infini</b> s'il n'y a <b>pas eu d'intersection</b>.
	 */
	public double getT() {
		return int_t;
	}

	/**
	 * M�thode pour obtenir l'indice de r�fraction du milieu dans lequel le rayon
	 * voyage.
	 * 
	 * @return l'indice de r�fraction du milieu o� voyage le rayon.
	 */
	public double getRefractiveIndex() {
		return refractive_index;
	}

	/**
	 * M�thode pour obtenir la position de l'intersection entre le rayon et la
	 * g�om�trie.
	 * 
	 * @return La position de l'intersection
	 * @throws SNotIntersectedRayException S'il n'y a pas eu d'intersection avec ce
	 *                                     rayon, le point d'intersection est
	 *                                     ind�termin�.
	 */
	public SVector3d getIntersectionPosition() throws SNotIntersectedRayException {
		if (!as_intersected)
			throw new SNotIntersectedRayException(
					"Erreur SRay 006 : Le rayon n'a pas effectu� d'intersection avec une g�om�trie.");

		// S'il y a intersection, la valeur de "t" correspond au temps de l'intersection
		return getPosition(int_t);
	}

	/**
	 * M�thode pour obtenir la g�om�trie qui est en intersection avec le rayon.
	 * 
	 * @return La g�om�trie en intersection.
	 * @throws SNotIntersectedRayException S'il n'y a pas eu d'intersection avec ce
	 *                                     rayon, la g�om�trie est ind�termin�e.
	 */
	public SGeometry getGeometry() throws SNotIntersectedRayException {
		if (!as_intersected)
			throw new SNotIntersectedRayException(
					"Erreur SRay 006 : Le rayon n'a pas effectu� d'intersection avec une g�om�trie.");

		return geometry;
	}

	/**
	 * M�thode pour obtenir la normale � la surface de la g�om�trie en intersection
	 * avec le rayon. Cette normale sera orient�e dans le <u>sens oppos� � la
	 * direction du rayon</u>.
	 * <p>
	 * Cette d�finition de la normale permet d'�valuer le bon c�t� de la surface de
	 * la g�om�trie lorsqu'il est temps d'�valuer l'illumination (shading).
	 * </p>
	 * 
	 * @return La normale � la surface � l'endroit de l'intersection.
	 * @throws SNotIntersectedRayException S'il n'y a pas eu d'intersection avec ce
	 *                                     rayon, la normale est ind�termin�e.
	 */
	public SVector3d getShadingNormal() throws SNotIntersectedRayException {
		if (!as_intersected)
			throw new SNotIntersectedRayException(
					"Erreur SRay 007 : Le rayon n'a pas effectu� d'intersection avec une g�om�trie.");

		// Comparer l'orientation de la normale � la surface avec l'orientation du
		// rayon.
		// Retourner l'orientation tel que la normale est dans le sens oppos� � la
		// direction du rayon.
		if (direction.dot(normal) < 0.0)
			return normal; // orientation oppos�e, donc intersection venant de l'ext�rieur
		else
			return normal.multiply(-1.0); // orientation dans le m�me sens, donc intersection venant de l'int�rieur
	}

	/**
	 * M�thode pour obtenir la normale � la surface d'une g�om�trie en intersection
	 * avec le rayon. Cette normale sera orient�w dans le <u>sens ext�rieur de la
	 * surface de la g�om�trie</u>.
	 * <p>
	 * Cette d�finition est exactement celle fix�e lors de l'intersection du rayon
	 * avec la g�om�trie.
	 * </p>
	 * 
	 * @return La normale � la surface ext�rieur de la g�om�trie intersect�e par le
	 *         rayon.
	 * @throws SNotIntersectedRayException S'il n'y a pas eu d'intersection avec ce
	 *                                     rayon, la normale est ind�termin�e.
	 */
	public SVector3d getOutsideNormal() throws SNotIntersectedRayException {
		if (!as_intersected)
			throw new SNotIntersectedRayException(
					"Erreur SRay 008 : Le rayon n'a pas effectu� d'intersection avec une g�om�trie.");

		return normal;
	}

	/**
	 * M�thode pour obtenir la coordonn�e <i>uv</i> associ�e � l'intersection sur la
	 * g�om�trie.
	 * 
	 * @return La coordonn�e uv associ� � l'intersection sur la g�om�trie. S'il n'y
	 *         a pas de coordonn�e uv associ�e � l'intersection, la valeur
	 *         <b>null</b> sera retourn�e.
	 * @throws SNotIntersectedRayException S'il n'y a pas eu d'intersection avec ce
	 *                                     rayon, la coordonn�e <i>uv</i> ne peut
	 *                                     pas avoir �t� d�termin�e.
	 * @throws SRuntimeException           Le rayon n'a pas de coordonn�e de texture
	 *                                     d'attribu�.
	 */
	public SVectorUV getUV() throws SNotIntersectedRayException, SRuntimeException {
		// V�rifier que le rayon poss�de une coordonn�e UV.
		if (!asUV())
			throw new SRuntimeException("Erreur SRay 010 : Il n'y a pas de coordonn�e de texture associ�e � ce rayon.");

		return uv;
	}

	/**
	 * M�thode pour d�terminer si le rayon intersect� poss�de une coordonn�e de
	 * <i>uv</i> de texture.
	 * 
	 * @return <b>true</b> s'il y a une coordonn�e <i>uv</i> et <b>false</b> sinon.
	 * @throws SNotIntersectedRayException Si le rayon n'a pas �t� intersect�, il ne
	 *                                     peut pas y avoir de coordonn�e <i>uv</i>
	 *                                     d'attribu�e au rayon.
	 */
	public boolean asUV() throws SNotIntersectedRayException {
		// V�rifier que le rayon a �t� intersect�.
		if (!as_intersected)
			throw new SRuntimeException(
					"Erreur SRay 010 : Le rayon n'a pas effectu� d'intersection avec une g�om�trie.");

		// Retourner "vrai" si uv n'est pas "null".
		return uv != null;
	}

	/**
	 * M�thode pour g�n�rer un rayon intersect� � partir d'un rayon lanc�e et de ses
	 * caract�ristiques d�finissant l'intersection.
	 * 
	 * @param geometry La g�om�trie qui est en intersection avec le rayon.
	 * @param normal   La normale � la surface de la g�om�trie intersect�e.
	 * @param t        Le temps requis pour se rendre au lieu de l'intersection sur
	 *                 la g�om�trie.
	 * @return Le rayon avec les caract�ristiques de l'intersection.
	 * @throws SAlreadyIntersectedRayException S'il y a d�j� eu une intersection
	 *                                         avec ce rayon.
	 */
	public SRay intersection(SGeometry geometry, SVector3d normal, double t) throws SAlreadyIntersectedRayException {
		if (as_intersected)
			throw new SAlreadyIntersectedRayException(
					"Erreur SRay 011 : Ce rayon ne peut pas se faire intersecter, car il est pr�sentement d�j� intersect�.");

		// Construire un nouveau rayon intersect� avec les m�mes caract�ristique que le
		// rayon courant (sauf pour l'�tat d'intersection).
		return new SRay(this.origin, this.direction, geometry, normal, t, this.refractive_index, this.previous_ray);
	}

	/**
	 * M�thode pour g�n�rer un rayon intersect� � partir d'un rayon lanc�e et de ses
	 * caract�ristiques d�finissant l'intersection.
	 * 
	 * @param geometry La g�om�trie qui est en intersection avec le rayon.
	 * @param normal   La normale � la surface de la g�om�trie intersect�e.
	 * @param uv       La coordonn�e uv associ�e � l'intersection.
	 * @param t        Le temps requis pour se rendre au lieu de l'intersection sur
	 *                 la g�om�trie.
	 * @return Le rayon avec les caract�ristiques de l'intersection.
	 * @throws SAlreadyIntersectedRayException S'il y a d�j� eu une intersection
	 *                                         avec ce rayon.
	 */
	public SRay intersection(SGeometry geometry, SVector3d normal, SVectorUV uv, double t)
			throws SAlreadyIntersectedRayException {
		if (as_intersected)
			throw new SAlreadyIntersectedRayException(
					"Erreur SRay 012 : Ce rayon ne peut pas se faire intersecter, car il est pr�sentement d�j� intersect�.");

		// Construire un nouveau rayon intersect� avec les m�mes caract�ristique que le
		// rayon courant (sauf pour l'�tat d'intersection avec coordonn�e de texture
		// UV).
		return new SRay(this.origin, this.direction, geometry, normal, uv, t, this.refractive_index, this.previous_ray);
	}

	/**
	 * M�thode pour construire un rayon r�cursif au rayon courant. L'origine du
	 * lanc� du nouveau rayon correspondra � la position de l'intersection du rayon
	 * courant.
	 * 
	 * @param direction        La nouvelle direction du rayon r�cursif.
	 * @param refractive_index L'indice de r�fraction o� voyagera le rayon r�cursif.
	 * @return Un rayon r�cursif au rayon courant.
	 * @throws SNotIntersectedRayException Si le rayon n'a pas d�j� r�alis� une
	 *                                     intersection.
	 */
	public SRay castRecursiveRay(SVector3d direction, double refractive_index) throws SNotIntersectedRayException {
		// V�rifier que le rayon courant a d�j� r�alis� une intersection.
		if (!as_intersected)
			throw new SNotIntersectedRayException(
					"Erreur SRay 013 : Ce rayon n'a pas �t� intersect�, il ne peut pas y avoir de lanc� r�cursif de ce rayon");

		// Construire le nouveau rayon r�cursif avec le rayon courant comme �tant le
		// parent du rayon relanc�.
		return new SRay(getIntersectionPosition(), direction, refractive_index, this);
	}

	/**
	 * M�hode qui effectue la transformation d'un rayon <b>non intersect�</b> �
	 * partir d'une matrice de transformation lin�aire de format 4x4.
	 * 
	 * @param transformation La matrice de transformation.
	 * @return Le rayon transform� par la matrice de transformation.
	 * @throws SAlreadyIntersectedRayException Si le rayon a �t� intersect�, il ne
	 *                                         peut pas �tre transform�.
	 */
	public SRay transformNotIntersectedRay(SMatrix4x4 transformation) throws SAlreadyIntersectedRayException {
		if (as_intersected)
			throw new SAlreadyIntersectedRayException(
					"Erreur SRay 014 : Puisque ce rayon a été intersecté, il ne peut pas être transformé.");

		return new SRay(SAffineTransformation.transformPosition(transformation, origin), SAffineTransformation.transformOrientation(transformation, direction), refractive_index, previous_ray);

	}

	/**
	 * M�hode qui effectue la transformation d'un rayon <b>non intersect�</b> �
	 * partir d'une matrice de transformation lin�aire de format 4x4. Cette version
	 * utilile le param�tre <i>transformed_axis_origin</i> qui repr�sente l'origine
	 * du syst�me d'axe transform� par la matrice de transformation. Cette version
	 * permet la transformation du rayon avec moins d'allocation de m�moire. Cette
	 * version est pratique si la transformation de l'origine a �t� pr�alablement
	 * calcul�.
	 * 
	 * @param transformation     La matrice de transformation.
	 * @param transformed_origin L'origine du syst�me d'axe transform� par la
	 *                           matrice de transformation.
	 * @return Le rayon transform� par la matrice de transformation.
	 * @throws SAlreadyIntersectedRayException Si le rayon a �t� intersect�, il ne
	 *                                         peut pas �tre transform�.
	 */
	public SRay transformNotIntersectedRay(SMatrix4x4 transformation, SVector3d transformed_axis_origin)
			throws SAlreadyIntersectedRayException {
		if (as_intersected)
			throw new SAlreadyIntersectedRayException(
					"Erreur SRay 014 : Puisque ce rayon a �t� intersect�, il ne peut pas �tre transform�.");

		throw new SNoImplementationException("Erreur SRay : La m�thode n'est pas impl�ment�e.");
	}

	@Override
	public int compareTo(SRay ray) {
		return Double.compare(this.int_t, ray.int_t);
	}

	@Override
	public String toString() {
		return "SRay [origin=" + origin + ", direction=" + direction + ", as_intersected=" + as_intersected
				+ ", geometry=" + geometry + ", int_t=" + int_t + ", normal=" + normal + ", uv=" + uv
				+ ", refractive_index=" + refractive_index + "]";

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof SRay)) {
			return false;
		}

		SRay other = (SRay) obj;
		if (as_intersected != other.as_intersected) {
			return false;
		}

		if (direction == null) {
			if (other.direction != null) {
				return false;
			}
		} else if (!direction.equals(other.direction)) {
			return false;
		}

		if (geometry == null) {
			if (other.geometry != null) {
				return false;
			}
		} else if (!geometry.equals(other.geometry)) {
			return false;
		}

		if (normal == null) {
			if (other.normal != null) {
				return false;
			}
		} else if (!normal.equals(other.normal)) {
			return false;
		}

		if (uv == null) {
			if (other.uv != null) {
				return false;
			}
		} else if (!uv.equals(other.uv)) {
			return false;
		}

		if (origin == null) {
			if (other.origin != null) {
				return false;
			}
		} else if (!origin.equals(other.origin)) {
			return false;
		}

		if (!SMath.nearlyEquals(refractive_index, other.refractive_index)) {
			return false;
		}

		if (!SMath.nearlyEquals(int_t, other.int_t)) {
			return false;
		}

		return true;
	}

}// fin classe SRay
