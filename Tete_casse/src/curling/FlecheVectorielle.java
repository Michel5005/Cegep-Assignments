package curling;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import interfaces.Dessinable;
import moteurPhysique.SVector3d;

/**
 * Cette classe permet de dessiner une fleche, dans un contexte ou le
 * programmeur d�sire utiliser cette fleche pour illustrer visuellement un
 * vecteur. La position de la tete de la fleche est trouvee grace aux
 * composantes fournies dans le constructeur.
 * 
 * Notez qu'il est possible de modifier la longueur du trait de tete ainsi que
 * l'angle entre les traits qui forment la tete.
 * 
 * Il est aussi possible de modifier la longueur du corps de la fleche tout en
 * conservant son origine, en specifiant un facteur de redimensionnement. Ceci
 * est parfois necessaire quand le module du vecteur est trop grand/trop petit
 * pour donner un resultat visuel interessant.
 * 
 * @author Caroline Houle
 *
 */
public class FlecheVectorielle implements Dessinable {

	// Position initiale de l'origine de la fl�che en x et en y
	private double x1, y1;
	// Positon finale de la t�te de la fl�che en x et en y
	private double x2, y2;
	// Objets de type Line2D.Double repr�sentant le corps de la fl�che(ligne) et la
	// t�te de la fl�che
	private Line2D.Double corps, traitDeTete; // geometrie necessaire
	// Angle initiale entre les deux droites formant la pointe de la fl�che
	private double angleTete = 30; // en degres, angle par defaut entre les deux segments formant la tete de fleche
	// Longeur des traits servants � faire la pointe de la fl�che
	private double longueurTraitDeTete = 1; // longueur par defaut des segments formant la tete
	// Convertion de pixels en m�tres
	private double pixelsParMetre = 1;

	private Color couleurFleche;

	private double longueurFleche;

	/**
	 * Cree une fleche en specifiant son origine ainsi qu'un vecteur qui indique ses
	 * composantes (longueurs des d�placements en x et en y pour d�terminer ou se
	 * trouve la pointe de la fleche)
	 * 
	 * @param x1  origine en x
	 * @param y1  origine en y
	 * @param vec vecteur qui specifie les composantes dx et dy du vecteur
	 */
	public FlecheVectorielle(double x1, double y1, SVector3d vec) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + vec.getX();
		this.y2 = y1 + vec.getY();
		creerLaGeometrie();
	}

	/**
	 * Cree une fleche en specifiant son origine et la dimension de ses composantes
	 * (longueurs des d�placements en x et en y pour d�terminer ou se trouve la
	 * pointe de la fleche)
	 * 
	 * @param x1 origine en x
	 * @param y1 origine en y
	 * @param dx longueur de la composante en x
	 * @param dy longueur de la composante en y
	 */
	public FlecheVectorielle(double x1, double y1, double dx, double dy) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + dx;
		this.y2 = y1 + dy;
		creerLaGeometrie();
	}

	public void setOrigine(double x, double y) {
		this.x1 = x;
		this.y1 = y;
		creerLaGeometrie();
	}

	/**
	 * Cree les formes geometriques utiles pour dessiner la fleche
	 */
	private void creerLaGeometrie() {

		// le corps de la fleche
		corps = new Line2D.Double(x1, y1, x2, y2);

		/*
		 * En utilisant la theorie des triangles semblables, cr�er un petit trait qui se
		 * confond avec le corps de la fl�che. En ajoutant des rotations a ce trait au
		 * moment du dessin on obtiendra la positionde la pointe de la fleche.
		 */
		longueurFleche = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		double dxPetitTrait = longueurTraitDeTete * (x2 - x1) / longueurFleche;
		double dyPetitTrait = longueurTraitDeTete * (y2 - y1) / longueurFleche;
		double x3 = x2 - dxPetitTrait;
		double y3 = y2 - dyPetitTrait;
		traitDeTete = new Line2D.Double(x2, y2, x3, y3);
	}

	/**
	 * Dessiner la fl�che
	 * 
	 * @param g2d Le contexte graphique
	 */
	@Override
	public void dessiner(Graphics2D g2d) {
		AffineTransform mat = new AffineTransform();
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		g2dCopie.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		mat.scale(pixelsParMetre, pixelsParMetre);

		g2dCopie.setColor(couleurFleche);
		g2dCopie.draw(mat.createTransformedShape(corps)); // corps de la fleche

		mat.rotate(Math.toRadians(angleTete / 2), x2, y2);
		g2dCopie.draw(mat.createTransformedShape(traitDeTete)); // un des deux traits qui formeront la tete de la fleche
		mat.rotate(Math.toRadians(-(angleTete)), x2, y2);
		g2dCopie.draw(mat.createTransformedShape(traitDeTete)); // deuxieme trait qui formeront la tete de la fleche

	}// fin

	public double getPixelsParMetre() {
		return pixelsParMetre;
	}

	public double getLongueurFleche() {
		return longueurFleche;
	}

	public void setLongueurFleche(double longueurFleche) {
		this.longueurFleche = longueurFleche;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

	/**
	 * Permet de modifier la longueur du corps en multipliant le tout par un facteur
	 * sp�cifi�. L'orgine de la fleche demeure la m�me, mais sa deuxieme extremite
	 * (positionde la pointe) se trouvera modifiee!
	 * 
	 * @param facteurRedim Facteur multiplicatif pour la longueur du corps. Un
	 *                     facteur 1 ne changera rien.
	 */
	public void redimensionneCorps(double facteurRedim) {
		this.x2 = x1 + (x2 - x1) * facteurRedim;
		this.y2 = y1 + (y2 - y1) * facteurRedim;
		creerLaGeometrie(); // esentiel!
	}

	/**
	 * Retourne la valeur de l'angle entre les deux traits formant la tete de la
	 * fleche
	 * 
	 * @return L'angle entre les deux pointes de la fl�che, en degres
	 */
	public double getAngleTete() {
		return angleTete;
	}

	/**
	 * Modifie l'angle entre les deux traits formant la tete de la fleche
	 * 
	 * @param angleTete Angle entre les deux traits formant la tete de la fleche, en
	 *                  degres
	 */
	public void setAngleTete(double angleTete) {
		this.angleTete = angleTete;
		creerLaGeometrie();
	}

	/**
	 * Retourne la longueur du segment utilis� pour tracer la tete de la fl�che
	 * 
	 * @return Longueur du segment
	 */
	public double getLongueurTraitDeTete() {
		return longueurTraitDeTete;
	}

	/**
	 * Modifie la longueur du segment utilis� pour tracer la tete de la fl�che
	 * 
	 * @param longueurTete longueur du segment de tete
	 */
	public void setLongueurTraitDeTete(double longueurTete) {
		this.longueurTraitDeTete = longueurTete;
		creerLaGeometrie();
	}

	/**
	 * Modifie de facteur mutiplicatif permettant de passer des metres aux pixels
	 * 
	 * @param pixelsParMetre facteur mutiplicatif permettant de passer des metres
	 *                       aux pixels
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}

	public void setCouleurFleche(Color couleurFleche) {
		this.couleurFleche = couleurFleche;
	}

}
