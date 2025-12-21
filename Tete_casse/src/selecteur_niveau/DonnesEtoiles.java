package selecteur_niveau;

import java.io.Serializable;

/**
 * Classe représentant les données des étoiles.
 * 
 * @author Liangchen Liu
 */
public class DonnesEtoiles implements Serializable{
    private int nbEtoiles = 0;

    private int nbEtoilesCircuit = 0;

    private int nbEtoilesCurling = 0;

    private int nbEtoilesPeche = 0;

    private int nbEtoilesTank = 0;

    /**
     * Méthode pour calculer le total des étoiles.
     */
    private void total(){
        nbEtoiles = nbEtoilesCircuit + nbEtoilesCurling + nbEtoilesPeche + nbEtoilesTank;

    }

    public int getNbEtoilesCircuit() {
        return nbEtoilesCircuit;
    }

    public void setNbEtoilesCircuit(int nbEtoilesCircuit) {
        this.nbEtoilesCircuit = nbEtoilesCircuit;
    }

    public int getNbEtoilesCurling() {
        return nbEtoilesCurling;
    }

    public void setNbEtoilesCurling(int nbEtoilesCurling) {
        this.nbEtoilesCurling = nbEtoilesCurling;
    }

    public int getNbEtoilesPeche() {
        return nbEtoilesPeche;
    }

    public void setNbEtoilesPeche(int nbEtoilesPeche) {
        this.nbEtoilesPeche = nbEtoilesPeche;
    }

    public int getNbEtoilesTank() {
        return nbEtoilesTank;
    }

    public void setNbEtoilesTank(int nbEtoilesTank) {
        this.nbEtoilesTank = nbEtoilesTank;
    }

    public int getNbEtoiles() {
        total();
        return nbEtoiles;
    }

    public void setNbEtoiles(int nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

}
