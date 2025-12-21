package char_assaut;

import java.io.Serializable;

/**
 * Classe enregistrant les données du jeu de char d'assaut.
 * 
 * @author Liangchen Liu
 */

public class DonnesTank implements Serializable {
	/** Version de sérialisation de la classe */
	private static final long serialVersionUID = 104L;

	private int nbTour = 0;
	private long tempsEcoule = 0;
	private double temps = 0;

	private final double LARGEUR_DU_TANK = 40;

	private double hauteurDuComposantEnMetres;
	/** Position du char d'assaut en x */
	private double xTank = 1;
	/** Position du char d'assaut en y */
	private double yTank = (hauteurDuComposantEnMetres * 6) - 10;
	/** La position en x du premier coin du char d'assaut. */
	private double x1 = 1;
	/** La position en x du deuxième coin du char d'assaut. */
	private double x2 = 1 + 40;
	/** La position en y du premier coin du char d'assaut. */
	private double y1 = 1;
	/** La position en y du deuxième coin du char d'assaut. */
	private double y2 = 1 + 40;
	/** Angle de rotation lié au déplacement du char d'assaut. */
	private double theta = 0;
	private double angleTir = 20;

	private final double HAUTEUR_DU_TANK = 20;
	private double x3 = 1000;
	private double x4 = x3 + LARGEUR_DU_TANK;
	private double y3;
	private double y4;
	private double thetaEnnemi = 0;
	private double xTankEnnemi = x3 + (HAUTEUR_DU_TANK * Math.sin(thetaEnnemi));
	private double yTankEnnemi = y3 - (HAUTEUR_DU_TANK * Math.cos(thetaEnnemi));

	private double vitesseInit = 100;

	private int carburantBonusJoueur = 0;
	private int carburantBonusEnnemi = 0;
	private int carburantJoueur = 75 + carburantBonusJoueur;
	private int carburantEnnemi = 75 + carburantBonusEnnemi;

	private boolean tirNormalSelectionne = true;
	private boolean tirRebondSelectionne = false;
	private boolean tirEparpilleSelectionne = false;

	private TypePlaneteTerrain typePlaneteTerrain = TypePlaneteTerrain.TERRE;

	private int espaceEntreTankEtBarre = 50;

	private int nbPointDeVieJoueur = 100;
	private int nbBouclierJoueur = 50;
	private double xPointDeVieJoueur = xTank;
	private double yPointDeVieJoueur = yTank - espaceEntreTankEtBarre;
	private double xBouclierJoueur = xTank;
	private double yBouclierJoueur = yTank - espaceEntreTankEtBarre - 5;

	private int nbPointDeVieEnnemi = 100;
	private int nbBouclierEnnemi = 50;
	private double xPointDeVieEnnemi = xTankEnnemi;
	private double yPointDeVieEnnemi = yTankEnnemi - espaceEntreTankEtBarre;
	private double xBouclierEnnemi = xTankEnnemi;
	private double yBouclierEnnemi = yTankEnnemi - espaceEntreTankEtBarre - 5;
	private int randAmelio;

	private boolean modeTriche = false;

	private int tirEparp = 5;
	private int tirEparpT = 1000;
	private int carburantJoueurT = 10000000;
	private int nbPointDeVieEnnemiT = 1;
	private int nbBouclierEnnemiT = 0;

	private int tirRebond = 5;
	private int tirRebondT = 1000;

	private boolean premiereFois = true;
	
	public boolean isPremiereFois() {
		return premiereFois;
	}

	public void setPremiereFois(boolean premiereFois) {
		this.premiereFois = premiereFois;
	}

	public int getTirRebond() {
		return tirRebond;
	}

	public void setTirRebond(int tirRebond) {
		this.tirRebond = tirRebond;
	}

	public int getTirRebondT() {
		return tirRebondT;
	}

	public void setTirRebondT(int tirRebondT) {
		this.tirRebondT = tirRebondT;
	}

	public int getTirEparp() {
		return tirEparp;
	}

	public void setTirEparp(int tirEparp) {
		this.tirEparp = tirEparp;
	}

	public int getTirEparpT() {
		return tirEparpT;
	}

	public void setTirEparpT(int tirEparpT) {
		this.tirEparpT = tirEparpT;
	}

	public int getCarburantJoueurT() {
		return carburantJoueurT;
	}

	public void setCarburantJoueurT(int carburantBonusJoueurT) {
		this.carburantJoueurT = carburantBonusJoueurT;
	}

	public int getNbPointDeVieEnnemiT() {
		return nbPointDeVieEnnemiT;
	}

	public void setNbPointDeVieEnnemiT(int nbPointDeVieEnnemiT) {
		this.nbPointDeVieEnnemiT = nbPointDeVieEnnemiT;
	}

	public int getNbBouclierEnnemiT() {
		return nbBouclierEnnemiT;
	}

	public void setNbBouclierEnnemiT(int nbBouclierEnnemiT) {
		this.nbBouclierEnnemiT = nbBouclierEnnemiT;
	}

	public boolean isModeTriche() {
		return modeTriche;
	}

	public void setModeTriche(boolean modeTriche) {
		this.modeTriche = modeTriche;
	}

	public int getRandAmelio() {
		return randAmelio;
	}

	public void setRandAmelio(int randAmelio) {
		this.randAmelio = randAmelio;
	}

	public int getNbPointDeVieEnnemi() {
		return nbPointDeVieEnnemi;
	}

	public void setNbPointDeVieEnnemi(int nbPointDeVieEnnemi) {
		this.nbPointDeVieEnnemi = nbPointDeVieEnnemi;
	}

	public int getNbBouclierEnnemi() {
		return nbBouclierEnnemi;
	}

	public void setNbBouclierEnnemi(int nbBouclierEnnemi) {
		this.nbBouclierEnnemi = nbBouclierEnnemi;
	}

	public double getxPointDeVieEnnemi() {
		return xPointDeVieEnnemi;
	}

	public void setxPointDeVieEnnemi(double xPointDeVieEnnemi) {
		this.xPointDeVieEnnemi = xPointDeVieEnnemi;
	}

	public double getyPointDeVieEnnemi() {
		return yPointDeVieEnnemi;
	}

	public void setyPointDeVieEnnemi(double yPointDeVieEnnemi) {
		this.yPointDeVieEnnemi = yPointDeVieEnnemi;
	}

	public double getxBouclierEnnemi() {
		return xBouclierEnnemi;
	}

	public void setxBouclierEnnemi(double xBouclierEnnemi) {
		this.xBouclierEnnemi = xBouclierEnnemi;
	}

	public double getyBouclierEnnemi() {
		return yBouclierEnnemi;
	}

	public void setyBouclierEnnemi(double yBouclierEnnemi) {
		this.yBouclierEnnemi = yBouclierEnnemi;
	}

	public int getNbPointDeVieJoueur() {
		return nbPointDeVieJoueur;
	}

	public void setNbPointDeVieJoueur(int nbPointDeVieJoueur) {
		this.nbPointDeVieJoueur = nbPointDeVieJoueur;
	}

	public int getNbBouclierJoueur() {
		return nbBouclierJoueur;
	}

	public void setNbBouclierJoueur(int nbBouclierJoueur) {
		this.nbBouclierJoueur = nbBouclierJoueur;
	}

	public double getxPointDeVieJoueur() {
		return xPointDeVieJoueur;
	}

	public void setxPointDeVieJoueur(double xPointDeVieJoueur) {
		this.xPointDeVieJoueur = xPointDeVieJoueur;
	}

	public double getyPointDeVieJoueur() {
		return yPointDeVieJoueur;
	}

	public void setyPointDeVieJoueur(double yPointDeVieJoueur) {
		this.yPointDeVieJoueur = yPointDeVieJoueur;
	}

	public double getxBouclierJoueur() {
		return xBouclierJoueur;
	}

	public void setxBouclierJoueur(double xBouclierJoueur) {
		this.xBouclierJoueur = xBouclierJoueur;
	}

	public double getyBouclierJoueur() {
		return yBouclierJoueur;
	}

	public void setyBouclierJoueur(double yBouclierJoueur) {
		this.yBouclierJoueur = yBouclierJoueur;
	}

	public TypePlaneteTerrain getTypePlaneteTerrain() {
		return typePlaneteTerrain;
	}

	public void setTypePlaneteTerrain(TypePlaneteTerrain typePlaneteTerrain) {
		this.typePlaneteTerrain = typePlaneteTerrain;
	}

	public boolean isTirNormalSelectionne() {
		return tirNormalSelectionne;
	}

	public void setTirNormalSelectionne(boolean tirNormalSelectionne) {
		this.tirNormalSelectionne = tirNormalSelectionne;
	}

	public boolean isTirRebondSelectionne() {
		return tirRebondSelectionne;
	}

	public void setTirRebondSelectionne(boolean tirRebondSelectionne) {
		this.tirRebondSelectionne = tirRebondSelectionne;
	}

	public boolean isTirEparpilleSelectionne() {
		return tirEparpilleSelectionne;
	}

	public void setTirEparpilleSelectionne(boolean tirEparpilleSelectionne) {
		this.tirEparpilleSelectionne = tirEparpilleSelectionne;
	}

	public int getCarburantBonusJoueur() {
		return carburantBonusJoueur;
	}

	public void setCarburantBonusJoueur(int carburantBonusJoueur) {
		this.carburantBonusJoueur = carburantBonusJoueur;
	}

	public int getCarburantBonusEnnemi() {
		return carburantBonusEnnemi;
	}

	public void setCarburantBonusEnnemi(int carburantBonusEnnemi) {
		this.carburantBonusEnnemi = carburantBonusEnnemi;
	}

	public int getCarburantJoueur() {
		return carburantJoueur;
	}

	public void setCarburantJoueur(int carburantJoueur) {
		this.carburantJoueur = carburantJoueur;
	}

	public int getCarburantEnnemi() {
		return carburantEnnemi;
	}

	public void setCarburantEnnemi(int carburantEnnemi) {
		this.carburantEnnemi = carburantEnnemi;
	}

	public double getVitesseInit() {
		return vitesseInit;
	}

	public void setVitesseInit(double vitesseInt) {
		this.vitesseInit = vitesseInt;
	}

	public double getTemps() {
		return temps;
	}

	public void setTemps(double temps) {
		this.temps = temps;
	}

	public long getTempsEcoule() {
		return tempsEcoule;
	}

	public void setTempsEcoule(long tempsEcoule) {
		this.tempsEcoule = tempsEcoule;
	}

	public double getxTankEnnemi() {
		return xTankEnnemi;
	}

	public void setxTankEnnemi(double xTankEnnemi) {
		this.xTankEnnemi = xTankEnnemi;
	}

	public double getyTankEnnemi() {
		return yTankEnnemi;
	}

	public void setyTankEnnemi(double yTankEnnemi) {
		this.yTankEnnemi = yTankEnnemi;
	}

	public double getX3() {
		return x3;
	}

	public void setX3(double x3) {
		this.x3 = x3;
	}

	public double getY3() {
		return y3;
	}

	public void setY3(double y3) {
		this.y3 = y3;
	}

	public double getX4() {
		return x4;
	}

	public void setX4(double x4) {
		this.x4 = x4;
	}

	public double getY4() {
		return y4;
	}

	public void setY4(double y4) {
		this.y4 = y4;
	}

	public double getThetaEnnemi() {
		return thetaEnnemi;
	}

	public void setThetaEnnemi(double thetaEnnemi) {
		this.thetaEnnemi = thetaEnnemi;
	}

	public double getXTank() {
		return xTank;
	}

	public void setXTank(double xTank) {
		this.xTank = xTank;
	}

	public double getYTank() {
		return yTank;
	}

	public void setYTank(double yTank) {
		this.yTank = yTank;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getAngleTir() {
		return angleTir;
	}

	public void setAngleTir(double angleTir) {
		this.angleTir = angleTir;
	}

	public int getNbTour() {
		return nbTour;
	}

	public void setNbTour(int nbTour) {
		this.nbTour = nbTour;
	}

}
