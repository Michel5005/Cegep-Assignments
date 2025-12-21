package geometrie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import intefaces.Dessinable;

/**
 * La classe Ressort implémente l'interface Dessinable et représente un ressort
 * à dessiner. Un ressort est défini par sa longueur, sa hauteur, sa longueur
 * naturelle et le nombre de pixels par mètre
 * 
 * @author Nokto Lapointe
 *
 */
public class Ressort implements Dessinable {
	/** Nombre de lignes utilisées pour dessiner le ressort. */
	private final int NB_LIGNES = 18;
	/** Etirement du ressort par rapport à sa longueur naturelle. */
	private double e;
	/** Longueur du ressort */
	private double longueur;
	/** Constante de rappel du ressort */
	private double k;
	/** Hauteur du sol */
	private double y;
	/** Nombre de pixels par mètre */
	private double pixelParMetre;
	/** Hauteur du ressort */
	private double hauteur;
	/** Forme du ressort */
	private Path2D.Double ressort;
	/** Longueur naturelle du ressort */
	double longueurNat;

	/**
	 * Constructeur
	 * 
	 * @param hauteurSol    hauteur du sol en mètres
	 * @param longueur      longueur du ressort en mètres
	 * @param longueurNat   longueur naturelle du ressort en mètres
	 * @param pixelParMetre nombre de pixels par mètre
	 * @param hauteur       hauteur du ressort en mètres
	 */
	public Ressort(double hauteurSol, double longueur, double longueurNat, double pixelParMetre, double hauteur) {

		this.y = hauteurSol - hauteur;
		this.longueur = longueur;
		this.pixelParMetre = pixelParMetre;
		this.hauteur = hauteur;
		this.longueurNat = longueurNat;
		e = longueur - longueurNat;
		creerLaGeometrie();
	}

	/**
	 * Méthode qui crée la géométrie du ressort en utilisant le nombre de lignes
	 * défini dans la constante NB_LIGNES
	 */
	private void creerLaGeometrie() {
		e = longueur - longueurNat;
		ressort = new Path2D.Double();

		for (int k = 0; k <= NB_LIGNES / 2; k++) {
			if (k == 0) {
				ressort.moveTo(0, (y + hauteur / 2));
			} else if (k == NB_LIGNES / 2) {
				ressort.lineTo(longueur / (NB_LIGNES) * (k * 2 - 1), y);
				ressort.lineTo(longueur, (y + hauteur / 2));
			} else {
				ressort.lineTo(longueur / (NB_LIGNES) * (k * 2 - 1), y);
				ressort.lineTo(longueur / (NB_LIGNES) * (k * 2), y + hauteur);
			}

		}

	}

	/**
	 * Permet de dessiner le ressort, sur le contexte graphique passe en parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelParMetre, pixelParMetre);
		g2dCopie.draw(mat.createTransformedShape(ressort));
	}

	/**
	 * Retourne l'etirement du ressort
	 * 
	 * @return l'etirement du ressort
	 */
	public double getE() {
		return e;
	}

	/**
	 * Modifie l'etirement du ressort
	 * 
	 * @param e l'etirement du ressort
	 */
	public void setE(double e) {
		this.e = e;
		this.longueur = this.longueurNat + this.e;
	}

	/**
	 * Retourne la longueur du ressort
	 * 
	 * @return la longueur du ressort
	 */
	public double getLongueur() {
		return longueur;
	}

	/**
	 * Modifie la longueur du ressort
	 * 
	 * @param longueur la longueur du ressort
	 */
	public void setLongueur(double longueur) {
		this.longueur = longueur;
		creerLaGeometrie();
	}

	/**
	 * Retourne la hauteur du ressort
	 * 
	 * @return la hauteur du ressort
	 */
	public double getHauteur() {
		return hauteur;
	}

	/**
	 * Retourne la longueur naturelle du ressort
	 * 
	 * @return la longueur naturelle
	 */
	public double getLongueurNat() {
		return longueurNat;
	}

}
