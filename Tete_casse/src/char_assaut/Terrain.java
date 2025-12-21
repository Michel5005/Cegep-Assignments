package char_assaut;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.io.Serializable;

/**
 * La classe Terrain représente un terrain, qui peut être dessiné dans une
 * interface graphique.
 * 
 * @author Michel Vuu
 *
 */
public class Terrain implements Serializable {
	/** Version de séralisation de la classe */
	private static final long serialVersionUID = 100L;
	/** La gravité qui appartient au terrain */
	private double gravite;
	/** Déclaration */
	private Double ligneBrisee;
	/** Nombre de segments pour approximer lié à la fonction dessiné */
	private int nbSegmentsPourApproximer;
	/** Facteur de mise à l'échelle utilisé lors du dessin du terrain. */
	private double pixelsParMetre;
	/** La position minimale du terrain courbé en x */
	private double xMin;
	/** La position maximale du terrain courbé en x */
	private double xMax;
	/** La position du terrain courbé en y */
	private double y;
	/** L'amplitude de la fonction utilisée pour la création du terrain courbé */
	private double amplitude = 10;
	/** La période de la fonction utilisée pour la création du terrain courbé */
	private double periode = 45;
	/** Aire du terrain */
	private Area aireTerrain;
	
	private TypePlaneteTerrain typeDeTerrain = TypePlaneteTerrain.TERRE;

	/**
	 * Constructeur
	 * 
	 * @param xMin                     La position minimale en x
	 * @param xMax                     La position maximale en x
	 * @param y                        La position en y
	 * @param nbSegmentsPourApproximer Le nombre de segments qui est utilisé pour
	 *                                 faire le terrain courbé
	 * @param gravite                  La gravité appartenant au terrain
	 * @param pixelsParMetre
	 * @param pixelsParMetre           Le facteur de mise à l'échelle utilisé lors
	 */
	public Terrain(double xMin, double xMax, double y, int nbSegmentsPourApproximer, double gravite,
			double pixelsParMetre) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.y = y;
		this.nbSegmentsPourApproximer = nbSegmentsPourApproximer;
		this.gravite = gravite;
		this.pixelsParMetre = pixelsParMetre;
		creerLaGeometrie();
	}// fin constructeur

	/**
	 * Creation de l'approximation de la courbe sous la forme d'un Path2D
	 */
	private void creerLaGeometrie() {
		double x, y;

		ligneBrisee = new Path2D.Double();
		x = xMin; // on commence a l'extreme gauche
		y = evalFonction(x);
		ligneBrisee.moveTo(x, y);

		for (int k = 1; k < nbSegmentsPourApproximer + 1; k++) {

			x = xMin + k * (xMax - xMin) / nbSegmentsPourApproximer; // on calcule le nouveau x, un peu plus loin,
																		// où on évaluera la fonction de nouveau

			y = evalFonction(x); // modifier cette ligne!!!
			ligneBrisee.lineTo(x, y); // modifier cette ligne!!!

		} // fin for
		ligneBrisee.lineTo(x, y+(150/pixelsParMetre));
		ligneBrisee.lineTo(0, y+(150/pixelsParMetre));
		ligneBrisee.closePath();
	}// fin méthode

	/**
	 * Evaluation de la valeur de la fonction en vigueur pour un x donné
	 * 
	 * @param x Valeur de x
	 * @return Valeur de la fonction pour ce x, en fonction de la fonction en
	 *         vigueur
	 * 
	 */
	public double evalFonction(double x) {
		double y;
		
		y = amplitude/pixelsParMetre * Math.cos(x / (periode/pixelsParMetre)) + 400/pixelsParMetre;

		return (y);
	}// fin méthode

	/**
	 * Permet de dessiner le terrain, sur le contexte graphique passe en parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);
		mat.translate(-xMin, y);
		if(typeDeTerrain==TypePlaneteTerrain.TERRE){
			g2dCopie.setColor(Color.GREEN);
		}
		else if (typeDeTerrain==TypePlaneteTerrain.LUNE){
			g2dCopie.setColor(Color.LIGHT_GRAY);
		}
		else if (typeDeTerrain==TypePlaneteTerrain.MARS){
			g2dCopie.setColor(Color.RED);
		}else if (typeDeTerrain==TypePlaneteTerrain.MERCURE){
			g2dCopie.setColor(Color.DARK_GRAY);
		}
		g2dCopie.fill(mat.createTransformedShape(ligneBrisee));
		aireTerrain = new Area (mat.createTransformedShape(ligneBrisee));
		//g2dCopie.fill(ligneBrisee);
	}// fin méthode

	/**
	 * Retourne la valeur de pixelsParMetre
	 * 
	 * @return Facteur de mise à l'échelle.
	 */
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}// fin méthode

	/**
	 * Modifie la valeur du pixelsParMetre
	 * 
	 * @param pixelsParMetre Facteur de mise à l'échelle.
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}// fin méthode

	/**
	 * Retourne la valeur de xMin
	 * 
	 * @return xMin
	 */
	public double getxMin() {
		return xMin;
	}// fin méthode

	/**
	 * Modifie la valeur de xMin et
	 * 
	 * @param xMin
	 */
	public void setxMin(double xMin) {
		this.xMin = xMin;
		creerLaGeometrie();
	}// fin méthode

	/**
	 * Retourne la valeur de xMax
	 * 
	 * @return La position maximale de la fonction en x
	 */
	public double getxMax() {
		return xMax;
	}// fin méthode

	/**
	 * Modifie la valeur de xMax
	 * 
	 * @param xMax La position maximale de la fonction en x
	 */
	public void setxMax(double xMax) {
		this.xMax = xMax;
		creerLaGeometrie();
	}// fin méthode

	/**
	 * Retourne la valeur de y
	 * 
	 * @return La position en y de la fonction
	 */
	public double getY() {
		return y;
	}// fin méthode

	/**
	 * Modifie la valeur de y
	 * 
	 * @param y La position en y de la fonction
	 */
	public void setY(double y) {
		this.y = y;
	}// fin méthode

	/**
	 * Retourne la valeur de l'amplitude
	 * 
	 * @return L'amplitude de la fonction utilisée pour le terrain courbé
	 */
	public double getAmplitude() {
		return amplitude;
	}// fin méthode

	/**
	 * Modifie la valeur de l'amplitude
	 * 
	 * @param amplitude L'amplitude de la fonction utilisée pour le terrain courbé
	 */
	public void setAmplitude(double amplitude) {
		this.amplitude = amplitude;
	}// fin méthode

	public double getGravite() {
		return gravite;
	}

	public void setGravite(double gravite) {
		this.gravite = gravite;
	}

	public TypePlaneteTerrain getTypeDeTerrain() {
		return typeDeTerrain;
	}

	public void setTypeDeTerrain(TypePlaneteTerrain typeDeTerrain) {
		this.typeDeTerrain = typeDeTerrain;
	}

	public Area getAireTerrain() {
		return aireTerrain;
	}

	public void setAireTerrain(Area aireTerrain) {
		this.aireTerrain = aireTerrain;
	}
	

}// fin classe
