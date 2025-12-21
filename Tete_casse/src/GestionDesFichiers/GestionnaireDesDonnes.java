package GestionDesFichiers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import badges.DonnesBadges;
import char_assaut.DonnesTank;
import circuit.DonnesCircuit;
import classements.DonnesClassements;
import curling.DonnesCurling;
import peche.DonnesPeche;
import selecteur_niveau.DonnesEtoiles;

/**
 * Classe offrant une méthode pour charge les données d'un fichier binaire et
 * une méthode pour sauvegarder les données dans un fichier binaire.
 * 
 * @author Liangchen Liu
 * @author Caroline Houle
 */
public class GestionnaireDesDonnes extends JPanel {
    /** Instance JFileChooser */
    private JFileChooser fileChooser;
    /** Variable offrant le nom du dossier sur le bureau */
    private String sousDossierSurBureau = "Tête cassée";

    private File dossier = new File(System.getProperty("user.home"), "Desktop" + "\\" + sousDossierSurBureau);

    /**
     * Variable pour la réponse du JFileChooser lors du clic sur le bouton ouvrir en
     * 1 ou 0
     */
    private int reponse;

    /** Instance de Sauvegarde */
    private Sauvegarde sauvegarde = new Sauvegarde();

    private DonnesTank donnesTank;
    private DonnesCircuit donnesCircuit;
    private DonnesCurling donnesCurling;
    private DonnesPeche donnesPeche;

    private DonnesBadges donnesBadges;
    private DonnesClassements donnesClassements;
    private DonnesEtoiles donnesEtoiles;

    private static GestionnaireDesDonnes instance = null;

    private int niveau;

    /**
     * Méthode singleton pour appeler l'instance de la classe
     * 
     * @return l'instance de la classe
     */
    // Liangchen Liu
    public static GestionnaireDesDonnes getInstance() {
        if (instance == null) {
            instance = new GestionnaireDesDonnes();
        }

        return instance;

    }

    /**
     * Méthode pour charger les fichiers binaires
     */
    // Caroline Houle
    public void chargerLesFichiers() {
        detFichier();

        reponse = fileChooser.showOpenDialog(null);

        // Vérifier si l'utilisateur a cliqué sur le bouton ouvrir
        if (reponse == JFileChooser.APPROVE_OPTION) {
            ObjectInputStream ois = null;

            File fichierDeTravail = new File(fileChooser.getSelectedFile().getAbsolutePath());

            try {
                ois = new ObjectInputStream(new FileInputStream(fichierDeTravail));
                sauvegarde = (Sauvegarde) ois.readObject();

                detChargement();
            } // fin try

            catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "L'objet lu est d'une classe inconnue");
                e.printStackTrace();
            } // fin catch

            catch (InvalidClassException e) {
                JOptionPane.showMessageDialog(null, "Les classes utilis�es pour l'ecriture et la lecture different!");
                e.printStackTrace();
            } // fin catch

            catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Fichier  " + fichierDeTravail.toString() + "  introuvable!");
                e.printStackTrace();
            } // fin catch

            catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erreur rencontree lors de la lecture");
                e.printStackTrace();
            } // fin catch

            finally {
                try {
                    ois.close();

                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Erreur rencontr�e lors de la fermeture!");
                    e.printStackTrace();

                } // fin try catch
            } // fin finally

        } // fin if

    }// fin de la méthode

    /**
     * Méthode pour déterminer où sauvegarder les données
     */
    // Liangchen Liu
    private void detFichier() {
        if (!dossier.mkdir()) {
            fileChooser = new JFileChooser(dossier);

        } else {
            fileChooser = new JFileChooser();

        }

    }

    /**
     * Méthode pour déterminer quel fichier charger
     */
    // Liangchen Liu
    private void detChargement() {
        niveau = sauvegarde.getNiveau();

        donnesClassements = sauvegarde.getDonnesClassements();
        donnesBadges = sauvegarde.getDonnesBadges();
        donnesEtoiles = sauvegarde.getDonnesEtoiles();

        switch (niveau) {
            case 1:
                donnesCircuit = sauvegarde.getDonnesCircuit();
                break;

            case 2:
                donnesCurling = sauvegarde.getDonnesCurling();
                break;

            case 3:
                donnesPeche = sauvegarde.getDonnesPeche();
                break;

            case 4:
                donnesTank = sauvegarde.getDonnesTank();
                break;

        }

    }

    /**
     * Méthode pour créer une sauvegarde avec des fichiers binaires
     */
    // Liangchen Liu
    public void sauvegarder() {
        File fichierDeTravail;

        if (dossier.mkdir()) {
            System.out.println("\nLe dossier " + dossier.toString() + " a ete cree car il n'existait pas...");
        } // fin if

        ObjectOutputStream oos = null;

        fileChooser = new JFileChooser(dossier);

        reponse = fileChooser.showSaveDialog(null);

        // Vérifier si l'utilisateur a cliqué sur le bouton ouvrir
        if (reponse == JFileChooser.APPROVE_OPTION) {
            fichierDeTravail = new File(fileChooser.getSelectedFile().getAbsolutePath());

            try {
                oos = new ObjectOutputStream(new FileOutputStream(fichierDeTravail));

                detSauvegarde();

                oos.writeObject(sauvegarde);

                System.out.println("\nLes informations ete ecrites avec succes. \nLe fichier "
                        + fichierDeTravail.toString() + " est pret pour la lecture!");

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erreur � l'ecriture:");
                e.printStackTrace();
            } // fin try catch

            finally {
                try {
                    oos.close();
                    JOptionPane.showMessageDialog(null, "Sauvegarde effectuée", "Notification",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Erreur rencontree lors de la fermeture!");
                    e.printStackTrace();
                } // fin try catch
            } // fin finally
        }

    }// fin de la méthode

    /**
     * Méthode pour déterminer quel fichier sauvegarder
     */
    // Liangchen Liu
    private void detSauvegarde() {
        sauvegarde.setNiveau(niveau);

        switch (niveau) {
            case 1:
                sauvegarde.setDonnesCircuit(donnesCircuit);

                break;

            case 2:
                sauvegarde.setDonnesCurling(donnesCurling);

                break;

            case 3:
                sauvegarde.setDonnesPeche(donnesPeche);

                break;

            case 4:
                sauvegarde.setDonnesTank(donnesTank);

                break;

        }

        sauvegarde.setDonnesClassements(donnesClassements);
        sauvegarde.setDonnesBadges(donnesBadges);
        sauvegarde.setDonnesEtoiles(donnesEtoiles);

    }

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

    /**
     * Méthode pour obtenir la classe des données du tank
     * 
     * @return la classe des données du tank
     */
    // Liangchen Liu
    public DonnesTank getDonnesTank() {
        return donnesTank;
    }

    /**
     * Méthode pour définir la classe des données du tank
     * 
     * @param donnesTank la classe des données du tank
     */
    // Liangchen Liu
    public void setDonnesTank(DonnesTank donnesTank) {
        this.donnesTank = donnesTank;
    }

    /**
     * Méthode pour obtenir la réponse du JFileChooser en 1 ou 0
     * 
     * @return la réponse du JFileChooser en int
     */
    // Liangchen Liu
    public int getReponse() {
        return reponse;
    }

    /**
     * Méthode pour définir la réponse du JFileChooser en 1 ou 0
     * 
     * @param reponse la réponse du JFileChooser en int
     */
    // Liangchen Liu
    public void setReponse(int reponse) {
        this.reponse = reponse;
    }

}// fin classe
