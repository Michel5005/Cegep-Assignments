package peche.gestion_des_images;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 
 * Classe qui est responsable de l'image de l'aimant
 * 
 * @author Tin Pham
 *
 */
public class ImageAimant {
	/** Position de l'aimant en x */
	private double x;
	/** Position de l'aimant en y */
	private double y;
	/** Largeur de l'aimant */
	private double largeur;
	/** Hauteur de l'aimant */
	private double hauteur;
	/** L'image de l'aimant */
	private static Image image;
	/** Le lien vers l'image de l'aimant */
	private URL lienImage;
	/** Pixel par mètre */
	private double pixelsParMetre;
	/** Angle de rotation */
	private double theta = 35;

	/**
	 * Constructeur
	 * 
	 * @param x       Position de l'image en x
	 * @param y       Position de l'image en y
	 * @param largeur Largeur de l'image
	 * @param hauteur Hauteur de l'image
	 * @param image   Le chemin d'accès à l'image
	 */
	public ImageAimant(double x, double y, double largeur, double hauteur, URL image) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.lienImage = image;

		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Créer l'image de l'aimant
	 */
	private void creerLaGeometrie() {
		try {
			if (image == null) {
				image = ImageIO.read(lienImage);
			}
		} // fin try
		catch (Exception e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");

		} // fin catch

	}// fin méthode

	/**
	 * Permet de dessiner le trésor sur le contexte graphique qui est passé en
	 * paramètre
	 * 
	 * @param g2d Le contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);
		// mat.rotate(Math.toRadians(theta));
		mat.translate(x, y);
		mat.scale(largeur / image.getWidth(null), hauteur / image.getHeight(null));
		g2dPrive.drawImage(image, mat, null);
	}// fin méthode

	/**
	 * Retourne la position en x de l'aimant
	 * 
	 * @return La position en x de l'aimant
	 */
	public double getX() {
		return x;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x de l'aimant
	 * 
	 * @param x Position de l'aimant en x
	 */
	public void setX(double x) {
		this.x = x;
	}// fin méthode

	/**
	 * Retourne la position en y de l'aimant
	 * 
	 * @return La position en y de l'aimant
	 */
	public double getY() {
		return y;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y de l'aimant
	 * 
	 * @param y Position de l'aimant en y
	 */
	public void setY(double y) {
		this.y = y;
	}// fin méthode

	/**
	 * Retourne la largeur de l'aimant
	 * 
	 * @return La largeur de l'aimant
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur de l'aimant
	 * 
	 * @param largeur Largeur de l'aimant
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}// fin méthode

	/**
	 * Retourne la hauteur de l'aimant
	 * 
	 * @return La hauteur de l'aimant
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur de l'aimant
	 * 
	 * @param hauteur hauteur de l'aimant
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
