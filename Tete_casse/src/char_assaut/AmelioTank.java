package char_assaut;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import outils.OutilsImage;

/**
 * Classe qui s'occupe des améliorations
 * 
 * @author Michel Vuu
 * @author Jason Yin
 */
public class AmelioTank {
	private int x;
	private int y;
	private int largeur;
	private int hauteur;
	private TypeAmelioration typeAmelioration;
	private RoundRectangle2D.Double rectangle;
	private final int ARC = 15;
	private Image image = null;
	private Area aireAmelio;

	/**
	 * Constructeur
	 * 
	 * @param x                Position en x de l'amélioration
	 * @param y                Position en y de l'amélioration
	 * @param largeur          Largeur de l'amélioration
	 * @param hauteur          Hauteur de l'amélioration
	 * @param typeAmelioration Le type de l'amélioration
	 * @param image            Le chemin à l'accès de l'image de l'amélioration
	 */
	// Michel Vuu
	public AmelioTank(int x, int y, int largeur, int hauteur, TypeAmelioration typeAmelioration, String image) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.setTypeAmelioration(typeAmelioration);
		this.image = OutilsImage.lireImageEtRedimensionner(image, largeur, hauteur);
		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Créer la géométrie de l'amélioration
	 */
	// Michel Vuu
	private void creerLaGeometrie() {
		rectangle = new RoundRectangle2D.Double(this.x, this.y, this.largeur, this.hauteur, this.ARC, this.ARC);
	}// fin méthode

	/**
	 * Permet de dessiner l'amélioration, sur le contexte graphique passe en
	 * parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Michel Vuu
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		aireAmelio = new Area(rectangle);
		g2dCopie.draw(rectangle);
		g2dCopie.drawImage(image, x, y, null);
	}// fin méthode

	/**
	 * Retourne si l'amélioration est touché par un boulet
	 * 
	 * @param aireBoulet L'aire du boulet qui est tiré
	 * @return Si l'amélioration est touché par un boulet
	 */
	// Jason Yin
	public boolean estTouche(Area aireBoulet) {
		boolean estTouche = false;
		aireBoulet.intersect(aireAmelio);
		if (!aireBoulet.isEmpty()) {
			estTouche = true;
		}
		return estTouche;
	}

	/**
	 * Retourne la valeur de la position en x de l'amélioration
	 * 
	 * @return La position en x de l'amélioration
	 */
	public double getX() {
		return x;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x et recréer l'amélioration avec la
	 * nouvelle position en x
	 * 
	 * @param x La position en x de l'amélioration
	 */
	public void setX(int x) {
		this.x = x;
		creerLaGeometrie();
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y de l'amélioration
	 * 
	 * @return La position en y de l'amélioration
	 */
	public int getY() {
		return y;
	}

	/**
	 * Modifie la valeur de la position en y et recréer l'amélioration avec la
	 * nouvelle position en y
	 * 
	 * @param y La position en y de l'amélioration
	 */
	public void setY(int y) {
		this.y = y;
		creerLaGeometrie();
	}

	/**
	 * Retourne la largeur de l'amélioration
	 * 
	 * @return La largeur de l'amélioration
	 */
	public int getLargeur() {
		return largeur;
	}

	/**
	 * Modifie la valeur de la largeur de l'amélioration et recréer l'amélioration
	 * avec la nouvelle largeur
	 * 
	 * @param hauteur La largeur de l'amélioration
	 */
	public void setLargeur(int largeur) {
		this.largeur = largeur;
		creerLaGeometrie();
	}

	/**
	 * Retourne la hauteur de l'amélioration
	 * 
	 * @return La hauteur de l'amélioration
	 */
	public int getHauteur() {
		return hauteur;
	}

	/**
	 * Modifie la valeur de la hauteur de l'amélioration et recréer l'amélioration
	 * avec la nouvelle hauteur
	 * 
	 * @param hauteur La hauteur de l'amélioration
	 */
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
		creerLaGeometrie();
	}

	/**
	 * Retourne le chemin d'accès à l'image de l'amélioration
	 * 
	 * @return Le chemin d'accès à l'image de l'amélioration
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Modifie le chemin d'accès à l'image de l'amélioration au type correspondant
	 * et recréer l'amélioration avec la nouvelle image
	 * 
	 * @param image Le chemin d'accès à l'image de l'amélioration
	 */
	public void setImage(Image image) {
		this.image = image;
		creerLaGeometrie();
	}

	/**
	 * Retourne le type d'amélioration
	 * 
	 * @return Le type d'amélioration
	 */
	public TypeAmelioration getTypeAmelioration() {
		return typeAmelioration;
	}

	/**
	 * Modifie le type de l'amélioration et recréer l'amélioration avec le nouveau
	 * type
	 *
	 * 
	 * @param typeAmelioration Le type d'amélioration
	 */
	public void setTypeAmelioration(TypeAmelioration typeAmelioration) {
		this.typeAmelioration = typeAmelioration;
		creerLaGeometrie();
	}

}
