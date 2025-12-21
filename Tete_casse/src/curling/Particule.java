package curling;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import interfaces.Dessinable;
import interfaces.Selectionnable;
import moteurPhysique.Objet;
import moteurPhysique.SVector3d;

/**
 * Classe représentant une particule.
 * 
 * @author Liangchen Liu
 */
public class Particule extends Objet implements Dessinable, Selectionnable {
    /** La géométrie de la particule */
    private Ellipse2D.Double particule;
    /** La position x de la particule */
    private double x;
    /** La position y de la particule */
    private double y;
    /** Le diamètre de la particule */
    private double diametre;
    // /** La vitesse initiale de la particule */
    // private double vitesseInit;
    /** Le nombre de pixels par mètre */
    private double pixelsParMetre;
    /** Le vecteur du champ électrique */
    private SVector3d champE;
    /** Le vecteur du champ magnétique */
    private SVector3d champM;
    /** La charge de la particule */
    private double chargeQ;

    // private SVector3d forceM;

    // private SVector3d forceE;

    /** Variable indiquant si la particule est sélectionnée ou pas */
    private boolean particuleSelectionne;
    /** La couleur de la particule */
    private Color couleur = Color.cyan;

    /**
     * Crée une particule avec les paramètres donnés.
     * 
     * @param position       Position du boulet de canon
     * @param vitesse        Vitesse du boulet de canon
     * @param acceleration   Accélération du boulet de canon
     * @param sommeDesForce  Somme des forces appliquées du boulet de canon
     * @param masse          Masse du boulet de canon
     * @param dt             Différence de temps entre les incrémentations de la
     *                       simulation
     * @param diametre       Diamètre de la particule
     * @param vitesseInit    Vitesse initiale de la particule
     * @param pixelsParMetre Nombre de pixels par mètre
     */
    public Particule(SVector3d position, SVector3d vitesse, SVector3d acceleration, SVector3d sommeDesForce,
            double diametre, double vitesseInit, double masse, double dt, double pixelsParMetre) {

        super(position, vitesse, acceleration, sommeDesForce, masse);
        this.position = position;
        this.vitesse = vitesse;
        this.acceleration = acceleration;
        this.sommeDesForces = sommeDesForce;
        this.diametre = diametre;
        this.pixelsParMetre = pixelsParMetre;

        creerLaGeometrie();
    }// fin constructeur

    /**
     * Méthode permettant la création des géométries
     */
    private void creerLaGeometrie() {

        particule = new Ellipse2D.Double(position.getX(), position.getY(), diametre, diametre);

    }// fin méthode

    /**
     * Permet de dessiner les éléments sur le contexte graphique qui passe en
     * parametre.
     * 
     * @param g2d Le contexte graphique
     */
    public void dessiner(Graphics2D g2d) {
        Graphics2D g2dCopie = (Graphics2D) g2d.create();
        AffineTransform mat = new AffineTransform();
        mat.scale(pixelsParMetre, pixelsParMetre);

        if (particuleSelectionne) {
            g2dCopie.setStroke(new BasicStroke(4));
            g2dCopie.setColor(Color.black);
            g2dCopie.draw(mat.createTransformedShape(particule));
        } // fin if

        g2dCopie.setColor(couleur);
        g2dCopie.fill(mat.createTransformedShape(particule));
        
    }// fin méthode

    /**
     * Calcule la nouvelle vitesse et la nouvelle position de la balle apres
     * cet nouvel intervalle de temps.
     * 
     * @param deltaT intervalle de temps (pas)
     */
    public void avancerUnPas(double deltaT) {
        RK4Curling.step(this, deltaT, champE, champM, chargeQ);
        this.position = getPosition();
        this.vitesse = getVitesse();
        this.acceleration = getAcceleration();

        creerLaGeometrie();

    }// fin méthode

    /**
     * Permet de savoir si la particule est sélectionnée ou pas.
     * 
     * @param xPix La position x du clic de la souris
     * @param yPix La position y du clic de la souris
     * @return true si la particule est sélectionnée, false sinon
     */
    @Override
    public boolean contient(double xPix, double yPix) {
        if (particule.contains(xPix, yPix)) {
            return true;

        } else {
            return false;

        } // fin if else
    }// fin méthode

    public double getPixelsParMetre() {
        return pixelsParMetre;
    }

    public void setPixelsParMetre(double pixelsParMetre) {
        this.pixelsParMetre = pixelsParMetre;
    }

    public SVector3d getSommeDesForces() {
        return sommeDesForces;
    }

    public void setSommeDesForces(SVector3d sommeDesForce) {
        this.sommeDesForces = sommeDesForce;
    }

    public SVector3d getChampE() {
        return champE;
    }

    public void setChampE(SVector3d champE) {
        this.champE = champE;
    }

    public SVector3d getChampM() {
        return champM;
    }

    public void setChampM(SVector3d champM) {
        this.champM = champM;
    }

    public double getChargeQ() {
        return chargeQ;
    }

    public void setChargeQ(double chargeQ) {
        this.chargeQ = chargeQ;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public Ellipse2D.Double getParticule() {
        return particule;
    }

    public void setParticule(Ellipse2D.Double particule) {
        this.particule = particule;
    }

    public boolean getParticuleSelectionne() {
        return particuleSelectionne;
    }

    public void setParticuleSelectionne(boolean selection) {
        this.particuleSelectionne = selection;
    }

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

}// fin classe
