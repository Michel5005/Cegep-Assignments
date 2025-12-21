package circuit;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Classe représentant les données du jeu de circuit à sauvegarder.
 * 
 * @author Liangchen Liu
 *
 */
public class DonnesCircuit implements Serializable {
    private static final long serialVersionUID = 101L;
    
    private ArrayList<Fil> fils = new ArrayList<Fil>();

    private long tempsEcoule = 0;
    private double temps = 0;
    private boolean modeTriche = false;

    public boolean isModeTriche() {
        return modeTriche;
    }

    public void setModeTriche(boolean modeTriche) {
        this.modeTriche = modeTriche;
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

    public ArrayList<Fil> getFils() {
        return fils;
    }

    public void setFils(ArrayList<Fil> fils) {
        this.fils = new ArrayList<Fil>(fils);
    }


}
