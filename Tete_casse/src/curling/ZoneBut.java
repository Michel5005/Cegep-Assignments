package curling;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;

/**
 * Classe représentant la zone de but.
 * 
 * @author Liangchen Liu
 */
public class ZoneBut implements Dessinable {
    /** Instance de la géométrie */
    private Rectangle2D.Double zoneBut;
    /** Position x de la zone de but */
    private double x;
    /** Position y de la zone de but */
    private double y;
    /** Largeur de la zone de but */
    private double largeur;
    /** Hauteur de la zone de but */
    private double hauteur;
    /** Nombre de pixels par mètre */
    private double pixelsParMetre;

    /**
     * Crée une zone de but avec les paramètres donnés.
     * 
     * @param x              Position x de la zone de but
     * @param y              Position y de la zone de but
     * @param largeur        Largeur de la zone de but
     * @param hauteur        Hauteur de la zone de but
     */
    public ZoneBut(double x, double y, double largeur, double hauteur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;

        creerLaGeometrie();
    }// fin constructeur

    /**
     * Crée la géométrie de la zone de but.
     */
    private void creerLaGeometrie() {
        zoneBut = new Rectangle2D.Double(x, y, largeur, hauteur);

    }// fin méthode

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

        g2dCopie.setColor(Color.magenta);
        g2dCopie.fill(mat.createTransformedShape(zoneBut));
    }// fin méthode

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public double getPixelsParMetre() {
        return pixelsParMetre;
    }

    public void setPixelsParMetre(double pixelsParMetre) {
        this.pixelsParMetre = pixelsParMetre;
    }

    public Rectangle2D.Double getZoneBut() {
        return zoneBut;
    }

    public void setZoneBut(Rectangle2D.Double zoneBut) {
        this.zoneBut = zoneBut;
    }

}
