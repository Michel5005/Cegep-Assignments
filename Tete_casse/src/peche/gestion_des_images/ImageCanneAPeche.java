package peche.gestion_des_images;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Classe qui s'occupe de l'image de la canne à pêche
 * 
 * @author Michel Vuu
 *
 */
public class ImageCanneAPeche {
	/** Position en x de l'image */
	private double x;
	/** Position en y de l'image */
	private double y;
	/** Largeur de l'image */
	private double largeur;
	/** Hauteur de l'image */
	private double hauteur;
	/** L'image du bateau */
	private Image image = null;
	/** Lien de l'image */
	private URL lienImage;
	/** Pixel par mètre */
	private double pixelsParMetre;

	/**
	 * Constructeur
	 * 
	 * @param x       Position de l'image en x
	 * @param y       Position de l'image en y
	 * @param largeur Largeur de l'image
	 * @param hauteur Hauteur de l'image
	 * @param image   Le chemin d'accès à l'image
	 */
	public ImageCanneAPeche(double x, double y, double largeur, double hauteur, URL image) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.lienImage = image;

		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Créer l'image de la canne à pêche
	 */
	private void creerLaGeometrie() {
		image = null;
		try {
			image = ImageIO.read(lienImage);
		} // fin try
		catch (Exception e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");

		} // fin catch

	}

	/**
	 * Permet de dessiner la canne à pêche sur le contexte graphique qui est passé
	 * en paramètre
	 * 
	 * @param g2d Le contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);
		mat.translate(x, y);
		mat.scale(largeur / image.getWidth(null), hauteur / image.getHeight(null));
		g2dPrive.drawImage(image, mat, null);
	}// fin méthode

	/**
	 * Retourne la position en x de la canne à pêche
	 * 
	 * @return La position en x de la canne à pêche
	 */
	public double getX() {
		return x;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x de la canne à pêche
	 * 
	 * @param x Position de la canne à pêche en x
	 */
	public void setX(double x) {
		this.x = x;
	}// fin méthode

	/**
	 * Retourne la position en y de la canne à pêche
	 * 
	 * @return La position en y de la canne à pêche
	 */
	public double getY() {
		return y;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y de la canne à pêche
	 * 
	 * @param y Position de la canne à pêche en y
	 */

	public void setY(double y) {
		this.y = y;
	}// fin méthode

	/**
	 * Retourne la largeur de la canne à pêche
	 * 
	 * @return La largeur de la canne à pêche
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur de la canne à pêche
	 * 
	 * @param largeur Largeur de la canne à pêche
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}// fin méthode

	/**
	 * Retourne la hauteur de la canne à pêche
	 * 
	 * @return La hauteur de la canne à pêche
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur de la canne à pêche
	 * 
	 * @param hauteur hauteur de la canne à pêche
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}// fin méthode

	/**
	 * Retourne le pixel par mètre
	 * 
	 * @return Le pixel par mètre
	 */
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}// fin méthode

	/**
	 * Modifie le pixel par mètre
	 * 
	 * @param pixelsParMetre Pixel par mètre
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}// fin méthode

}// fin classe
