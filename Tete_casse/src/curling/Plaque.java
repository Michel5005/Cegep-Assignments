package curling;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;

import interfaces.Dessinable;

/**
 * Classe représentant les plaques de champ.
 * 
 * @author Liangchen Liu
 */
public class Plaque implements Dessinable {
    /** La géométrie d'une plaques */
    private Rectangle2D.Double plaques;
    /** La position x d'une plaque */
    private double x;
    /** La position y d'une plaque */
    private double y;
    /** La largeur d'une plaque */
    private double largeur;
    /** La hauteur d'une plaque */
    private double hauteur;
    /** Le nombre de pixels par mètre */
    private double pixelsParMetre;

    /**
     * Crée une plaque avec les paramètres donnés.
     * 
     * @param x              Position x de la plaque
     * @param y              Position y de la plaque
     * @param largeur        Largeur de la plaque
     * @param hauteur        Hauteur de la plaque
     * @param pixelsParMetre Nombre de pixels par mètre
     */
    public Plaque(double x, double y, double largeur, double hauteur, double pixelsParMetre) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.pixelsParMetre = pixelsParMetre;

        creerLaGeometrie();
    }// fin constructeur

    /**
     * Crée la géométrie de la plaque.
     */
    private void creerLaGeometrie() {
        plaques = new Rectangle2D.Double(x, y, largeur, hauteur);

    }// fin creerLaGeometrie
    
    /**
     * Permet de dessiner les éléments sur le contexte graphique qui passe en
     * parametre.
     * 
     * @param g2d Le contexte graphique
     */
    @Override
    public void dessiner(Graphics2D g2d) {
        Graphics2D g2dCopie = (Graphics2D) g2d.create();
        AffineTransform mat = new AffineTransform();
        mat.scale(pixelsParMetre, pixelsParMetre);

        g2dCopie.setColor(new Color(92, 103, 132));
        g2dCopie.fill(mat.createTransformedShape(plaques));
    }// fin méthode

    public double getPixelsParMetre() {
        return pixelsParMetre;
    }

    public void setPixelsParMetre(double pixelsParMetre) {
        this.pixelsParMetre = pixelsParMetre;
    }

    public Rectangle2D.Double getPlaques() {
        return plaques;
    }

    public void setPlaques(Rectangle2D.Double plaques) {
        this.plaques = plaques;
    }

}// fin classe
