package curling;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;

/**
 * Classe représentant la zone de champ électrique.
 * 
 * @author Liangchen Liu
 */
public class ZoneChampElectrique implements Dessinable {

    /** Instance de la géométrie */
    private Rectangle2D.Double zoneChampElectrique;
    /** Position x de la zone de champ électrique */
    private double x;
    /** Position y de la zone de champ électrique */
    private double y;
    /** Largeur de la zone de champ électrique */
    private double largeur;
    /** Hauteur de la zone de champ électrique */
    private double hauteur;
    /** Nombre de pixels par mètre */
    private double pixelsParMetre;

    /**
     * Crée une zone de champ électrique avec les paramètres donnés.
     * 
     * @param x              Position x de la zone de champ électrique
     * @param y              Position y de la zone de champ électrique
     * @param largeur        Largeur de la zone de champ électrique
     * @param hauteur        Hauteur de la zone de champ électrique
     * @param pixelsParMetre Nombre de pixels par mètre
     */
    public ZoneChampElectrique(double x, double y, double largeur, double hauteur, double pixelsParMetre) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.pixelsParMetre = pixelsParMetre;

        creerLaGeometrie();
    }// fin constructeur

    /**
     * Crée la géométrie de la zone de champ électrique.
     */
    private void creerLaGeometrie() {
        zoneChampElectrique = new Rectangle2D.Double(x, y, largeur, hauteur);

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

        g2dCopie.setColor(Color.LIGHT_GRAY);
        g2dCopie.fill(mat.createTransformedShape(zoneChampElectrique));

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

    public Rectangle2D.Double getZoneChampElectrique() {
        return zoneChampElectrique;
    }

    public void setZoneChampElectrique(Rectangle2D.Double zoneChampElectrique) {
        this.zoneChampElectrique = zoneChampElectrique;
    }

}
