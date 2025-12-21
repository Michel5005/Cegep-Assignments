package application;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import GestionDesFichiers.GestionnaireDesDonnes;
import char_assaut.DonnesTank;
import circuit.DonnesCircuit;
import curling.DonnesCurling;
import peche.DonnesPeche;

/**
 * 
 * Classe représentant le temps écoulé depuis le début de la partie.
 * 
 * @author Liangchen Liu
 * @author Tin Pham
 */

public class Temps implements Runnable {

    private boolean enCoursDeCalcul;

    private long tempsDebut;
    private long tempsFin;
    private long tempsEcoule;
    private long tempsEcouleSauv;
    private double temps;

    private int niveau;

    private DonnesCircuit donnesCircuit;
    private DonnesCurling donnesCurling;
    private DonnesPeche donnesPeche;
    private DonnesTank donnesTank;

    private Thread proc;

    private final PropertyChangeSupport PCS = new PropertyChangeSupport(this);

    /**
     * Constructeur de la classe
     * 
     * @param niveau le niveau de jeu
     */
    // Liangchen Liu
    public Temps(int niveau) {
        this.niveau = niveau;

        chargerLesDonnees();

    }

    /**
     * Méthode permettant d'effectuer le calcul du temps écoulé.
     */
    // Liangchen Liu
    @Override
    public void run() {
        while (enCoursDeCalcul) {
            chargerLesDonnees();

            tempsFin = System.nanoTime();

            tempsEcoule = tempsFin - (tempsDebut);

            temps = tempsEcoule / 1000000000.0;

            PCS.firePropertyChange("temps", -1, formatTime(tempsEcoule/1000000));
            PCS.firePropertyChange("tempsEcoule", -1, (tempsEcoule));

            setLesDonnees();

            try {
                Thread.sleep(20);

            } catch (InterruptedException e) {
                e.printStackTrace();

            } // fin try-catch

        } // fin while
        System.out.println("Le thread est mort...!");
    }

    /**
     * Méthode permettant de réinitialiser le temps écoulé.
     */
    // Liangchen Liu
    public void reiniTemps(){
        tempsDebut = System.nanoTime();
    }

    /**
     * Méthode permettant de démarrer le thread.
     */
    // Liangchen Liu
    public void demarrer() {
        System.out.println("Le thread est démarré...! (temps)");

        if (!enCoursDeCalcul) {
            proc = new Thread(this);
            tempsDebut = System.nanoTime() - tempsEcouleSauv;
            proc.start();

            enCoursDeCalcul = true;
        }

    }

    /**
     * Méthode permettant de sauvegarder le temps écoulé.
     */
    // Liangchen Liu
    private void setLesDonnees() {
        switch (niveau) {
            case 1:
                donnesCircuit.setTemps(temps);
                GestionnaireDesDonnes.getInstance().setDonnesCircuit(donnesCircuit);

                break;
            case 2:
                donnesCurling.setTemps(temps);
                GestionnaireDesDonnes.getInstance().setDonnesCurling(donnesCurling);

                break;
            case 3:
                donnesPeche.setTemps(temps);
                GestionnaireDesDonnes.getInstance().setDonnesPeche(donnesPeche);

                break;
            case 4:
                donnesTank.setTemps(temps);
                GestionnaireDesDonnes.getInstance().setDonnesTank(donnesTank);

                break;

        }

    }

    /**
     * Méthode permettant de charger les données.
     */
    // Liangchen Liu
    private void chargerLesDonnees() {
        switch (niveau) {
            case 1:
                donnesCircuit = GestionnaireDesDonnes.getInstance().getDonnesCircuit();

                tempsEcouleSauv = donnesCircuit.getTempsEcoule();
                break;
            case 2:
                donnesCurling = GestionnaireDesDonnes.getInstance().getDonnesCurling();

                tempsEcouleSauv = donnesCurling.getTempsEcoule();
                break;
            case 3:
                donnesPeche = GestionnaireDesDonnes.getInstance().getDonnesPeche();

                tempsEcouleSauv = donnesPeche.getTempsEcoule();
                break;
            case 4:
                donnesTank = GestionnaireDesDonnes.getInstance().getDonnesTank();

                tempsEcouleSauv = donnesTank.getTempsEcoule();
                break;

        }

    }

    /**
     * Méthode permettant d'arrêter le thread.
     */
    // Liangchen Liu
    public void arreter() {
        enCoursDeCalcul = false;

        tempsDebut = System.nanoTime();
    }

    /**
     * Support du PropertyChangeListener
     * 
     * @param listener Écouteur
     */
    // Liangchen Liu
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.PCS.addPropertyChangeListener(listener);

    }

    // Liangchen Liu
    public boolean isEnCoursDeCalcul() {
        return enCoursDeCalcul;
    }

    // Liangchen Liu
    public void setEnCoursDeCalcul(boolean panelOuvert) {
        this.enCoursDeCalcul = panelOuvert;
    }

    /**
	 * Méthode qui fait la mise en forme du temps
     * 
     * Originalement créé par: Carlos Roberto de Paula Junior (sur StackOverflow)
     * 
     * @param timeInMillis Le temps en millisecondes
     * @return Le temps formaté
     */
    // Tin Pham
	private String formatTime(long timeInMillis) {
		int hours = (int) (timeInMillis / 3600000);
		int minutes = (int) ((timeInMillis - hours * 3600000) / 60000);
		int seconds = (int) ((timeInMillis - hours * 3600000 - minutes * 60000) / 1000);
		int millis = (int) (timeInMillis - hours * 3600000 - minutes * 60000 - seconds * 1000);
		return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
	}// Fin méthode

}
