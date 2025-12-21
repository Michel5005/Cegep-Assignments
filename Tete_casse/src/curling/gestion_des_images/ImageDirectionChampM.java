package curling.gestion_des_images;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Classe qui représente la direction de l'image du champ magnétique
 * 
 * @author Liangchen Liu
 */
public class ImageDirectionChampM {
	/** Position du trésor en x */
	private double x;
	/** Position du trésor en y */
	private double y;
	/** Largeur du trésor */
	private double largeur;
	/** Hauteur du trésor */
	private double hauteur;
	/** L'image du trésor */
	private Image image = null;

	private URL lienImage;

	private double pixelsParMetre;

	/**
	 * Constructeur pour l'image de la direction du champ magnétique
	 * 
	 * @param x       Position du trésor en x
	 * @param y       Position du trésor en y
	 * @param largeur Largeur du trésor
	 * @param hauteur Hauteur du trésor
	 * @param image   Le chemin d'accès à l'image du champ magnétique
	 */
	public ImageDirectionChampM(double x, double y, double largeur, double hauteur, URL image) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.lienImage = image;

		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Permet de créer la géométrie de l'image
	 */
	// Liangchen Liu
	private void creerLaGeometrie() {
		image = null;
		try {
			image = ImageIO.read(lienImage);
		} catch (IOException e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");
		}

	}

	/**
	 * Permet de dessiner l'image du champ magnétique sur le contexte graphique qui
	 * passe en parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Liangchen Liu
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();

		mat.scale(pixelsParMetre, pixelsParMetre);

		mat.translate(x, y);
		mat.scale(largeur / image.getWidth(null),
				hauteur / image.getHeight(null));
		g2dCopie.drawImage(image, mat, null);
	}// fin méthode

	public double getPixelsParMetre() {
		return pixelsParMetre;
	}

	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}

	/**
	 * Retourne la valeur de la position en x du trésor
	 * 
	 * @return La position en x du trésor
	 */
	public double getX() {
		return x;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x et recréer le trésor avec la nouvelle
	 * position en x
	 * 
	 * @param x La position en x du trésor
	 */
	public void setX(double x) {
		this.x = x;
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y du trésor
	 * 
	 * @return La position en y du trésor
	 */
	public double getY() {
		return y;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y et recréer le trésor avec la nouvelle
	 * position en y
	 * 
	 * @param y La position en y du trésor
	 */
	public void setY(double y) {
		this.y = y;
	}// fin méthode

	/**
	 * Retourne la largeur du trésor
	 * 
	 * @return La largeur du trésor
	 */
	public double getLargeur() {
		return largeur;
	}// fin méthode

	/**
	 * Modifie la valeur de la largeur du trésor et recréer le trésor avec la
	 * nouvelle largeur
	 * 
	 * @param largeur La largeur du trésor
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}// fin méthode

	/**
	 * Retourne la hauteur du trésor
	 * 
	 * @return La hauteur du trésor
	 */
	public double getHauteur() {
		return hauteur;
	}// fin méthode

	/**
	 * Modifie la valeur de la hauteur du trésor et recréer le trésor avec la
	 * nouvelle hauteur
	 * 
	 * @param hauteur La hauteur du trésor
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}// fin méthode
}// fin classe
