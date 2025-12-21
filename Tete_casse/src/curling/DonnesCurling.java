package curling;

import java.io.Serializable;

import moteurPhysique.SVector3d;

/**
 * Classe enregistrant les donnés du jeu de curling.
 * 
 * @author Liangchen Liu
 */
public class DonnesCurling implements Serializable {
    /** Version de sérialisation de la classe */
    private static final long serialVersionUID = 102L;
    /** Position de la particule en x */
    private double xParticule = 1.5;
    /** Position de la particule en y */
    private double yParticule = 2.5;
    /** Charge de la particule */
    private double chargeQ = 1;
    /** Masse de la particule */
    private double masse = 80;
    /** Champ magnétique */
    private SVector3d champM = new SVector3d(0, 0, 40);
    /** Champ électrique */
    private SVector3d champE = new SVector3d(0, 4, 0);

    private double points = 0;

    private double temps = 0;

    private long tempsEcoule = 0;

    private double tirsRestants = 15;

    private SVector3d vitesse = new SVector3d(0, 0, 0);

    private SVector3d acceleration = new SVector3d(0, 0, 0);

    private int nbEssais = 0;

    private boolean modeTriche= false;

    private double masseT = 100000000000.0;
    private double tirsRestantsT = 100000000000.0;

    private int conditionVictoire = 5;
    private int conditionVictoireT = 1;
    
    public int getConditionVictoireT() {
        return conditionVictoireT;
    }

    public void setConditionVictoireT(int conditionVictoireT) {
        this.conditionVictoireT = conditionVictoireT;
    }

    public int getConditionVictoire() {
        return conditionVictoire;
    }

    public void setConditionVictoire(int conditionVictoire) {
        this.conditionVictoire = conditionVictoire;
    }

    public double getMasseT() {
        return masseT;
    }

    public void setMasseT(double masseT) {
        this.masseT = masseT;
    }

    public double getTirsRestantsT() {
        return tirsRestantsT;
    }

    public void setTirsRestantsT(double tirsRestantsT) {
        this.tirsRestantsT = tirsRestantsT;
    }

    public boolean isModeTriche() {
        return modeTriche;
    }

    public void setModeTriche(boolean modeTriche) {
        this.modeTriche = modeTriche;
    }

    public int getNbEssais() {
        return nbEssais;
    }

    public void setNbEssais(int nbPoints) {
        this.nbEssais = nbPoints;
    }

    public SVector3d getVitesse() {
        return vitesse;
    }

    public void setVitesse(SVector3d vitesse) {
        this.vitesse = vitesse;
    }

    public SVector3d getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(SVector3d acceleration) {
        this.acceleration = acceleration;
    }

    public long getTempsEcoule() {
        return tempsEcoule;
    }

    public void setTempsEcoule(long tempsEcoule) {
        this.tempsEcoule = tempsEcoule;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getTemps() {
        return temps;
    }

    public void setTemps(double temps) {
        this.temps = temps;
    }

    public double getTirsRestants() {
        return tirsRestants;
    }

    public void setTirsRestants(double tireRestants) {
        this.tirsRestants = tireRestants;
    }

    public double getMasse() {
        return masse;
    }

    public void setMasse(double masse) {
        this.masse = masse;
    }

    public double getChargeQ() {
        return chargeQ;
    }

    public void setChargeQ(double chargeQ) {
        this.chargeQ = chargeQ;
    }

    public double getXParticule() {
        return xParticule;
    }

    public void setXParticule(double x) {
        this.xParticule = x;
    }

    public double getYParticule() {
        return yParticule;
    }

    public void setYParticule(double y) {
        this.yParticule = y;
    }

    public SVector3d getChampM() {
        return champM;
    }

    public void setChampM(SVector3d champM) {
        this.champM = champM;
    }

    public SVector3d getChampE() {
        return champE;
    }

    public void setChampE(SVector3d champE) {
        this.champE = champE;
    }

}// fin classe
