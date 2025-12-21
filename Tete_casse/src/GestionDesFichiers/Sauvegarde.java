package GestionDesFichiers;

import java.io.Serializable;

import badges.DonnesBadges;
import char_assaut.DonnesTank;
import circuit.DonnesCircuit;
import classements.DonnesClassements;
import curling.DonnesCurling;
import peche.DonnesPeche;
import selecteur_niveau.DonnesEtoiles;

/**
 * Classe avec les données des jeux
 * 
 * @author Liangchen Liu
 */
public class Sauvegarde implements Serializable {
    /** Version de séralisation de la classe */
    private static final long serialVersionUID = 100L;

    private int niveau;
    private DonnesCircuit donnesCircuit;
    private DonnesTank donnesTank;
    private DonnesCurling donnesCurling;
    private DonnesPeche donnesPeche;
    private DonnesClassements donnesClassements;
    private DonnesBadges donnesBadges;
    private DonnesEtoiles donnesEtoiles;

    public DonnesEtoiles getDonnesEtoiles() {
        return donnesEtoiles;
    }

    public void setDonnesEtoiles(DonnesEtoiles donnesEtoiles) {
        this.donnesEtoiles = donnesEtoiles;
    }

    public DonnesBadges getDonnesBadges() {
        return donnesBadges;
    }

    public void setDonnesBadges(DonnesBadges donnesBadges) {
        this.donnesBadges = donnesBadges;
    }

    public DonnesClassements getDonnesClassements() {
        return donnesClassements;
    }

    public void setDonnesClassements(DonnesClassements donnesClassements) {
        this.donnesClassements = donnesClassements;
    }



    public int getNiveau() {
        return niveau;
    }



    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }



    public DonnesPeche getDonnesPeche() {
        return donnesPeche;
    }



    public void setDonnesPeche(DonnesPeche donnesPeche) {
        this.donnesPeche = donnesPeche;
    }



    public DonnesTank getDonnesTank() {
        return donnesTank;
    }




    public void setDonnesTank(DonnesTank donnesTank) {
        this.donnesTank = donnesTank;
    }




    public DonnesCircuit getDonnesCircuit() {
        return donnesCircuit;
    }




    public void setDonnesCircuit(DonnesCircuit donnesCircuit) {
        this.donnesCircuit = donnesCircuit;
    }

    public DonnesCurling getDonnesCurling() {
        return donnesCurling;
    }

    public void setDonnesCurling(DonnesCurling donnesCurling) {
        this.donnesCurling = donnesCurling;
    }

    

}// fin classe
