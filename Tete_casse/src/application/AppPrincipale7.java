package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import GestionDesFichiers.GestionnaireDesDonnes;
import badges.Badges;
import badges.DonnesBadges;
import char_assaut.DonnesTank;
import char_assaut.ZoneAnimationTank;
import circuit.Circuit;
import circuit.DonnesCircuit;
import classements.Classements;
import classements.DonnesClassements;
import curling.DonnesCurling;
import curling.FenetreGraphiques;
import curling.ZoneAnimationCurling;
import menus.Apropos;
import parametres.Parametres;
import peche.DonnesPeche;
import peche.Peche;
import selecteur_niveau.DonnesEtoiles;
import selecteur_niveau.SelecteurDeNiveau;

/**
 * Classe qui gère le changement de panel.
 * 
 * @author Ismaïl Boukari
 * @author Liangchen Liu
 * 
 */
public class AppPrincipale7 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Instance du menu principale */
	private MenuPrincipale menuAccueil;
	/** Instance du menu de sélecteur de niveaux */
	private SelecteurDeNiveau selecNiveau;
	/** Instance du menu des paramètres */
	private Parametres params;
	/** Instance de la zone d'animation du tank */
	private ZoneAnimationTank tank;
	/** Instance de la zone d'animation du curling */
	private ZoneAnimationCurling curling;
	/** Instance du panel de circuit */
	private Circuit circuit;
	/** Instance du panel de pêche */
	private Peche peche;
	/** Instance du panel des classements */
	private Classements classements;
	/** Instance du panel des badges */
	private Badges badges;
	/** Instance de la classe des donnés du jeu de tank */
	private DonnesTank donnesTank;
	/** Instance de la classe des donnés du jeu de circuit */
	private DonnesCircuit donnesCircuit;
	/** Instance de la classe des donnés du jeu de curling */
	private DonnesCurling donnesCurling;
	/** Instance de la classe des donnés du jeu de pêche */
	private DonnesPeche donnesPeche;

	private DonnesClassements donnesClassements;

	private DonnesBadges donnesBadges;

	private DonnesEtoiles donnesEtoiles;

	/** Instance de la taille de l'écran */
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private FenetreGraphiques fenetreGraphiques;

	private Apropos aPropos;
	public static Font interBlack;
	public static Font interBold;
	private Rectangle dimensions;
	private boolean premiereFoisInstructionCircuit = true;
	private boolean premiereFoisInstructionCurling = true;
	private boolean premiereFoisInstructionPeche = true;
	private boolean premiereFoisInstructionTank = true;

	/**
	 * Lancer l'application.
	 * 
	 * @param args Arguments de la ligne de commande
	 * @throws FontFormatException Si la police n'est pas valide
	 */
	// Ismaïl Boukari
	public static void main(String[] args) throws FontFormatException {
		try {
			interBlack = Font.createFont(Font.TRUETYPE_FONT,
					AppPrincipale7.class.getClassLoader().getResourceAsStream(("Inter-Black.ttf")));
			interBold = Font.createFont(Font.TRUETYPE_FONT,
					AppPrincipale7.class.getClassLoader().getResourceAsStream(("Inter-Bold.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					try {
						UIManager.setLookAndFeel(new FlatLightLaf());
						UIManager.put("Button.arc", 15);
						UIManager.put("Component.arc", 15);
						UIManager.put("CheckBox.arc", 15);
						UIManager.put("ProgressBar.arc", 15);
					} catch (Exception ex) {
						System.err.println("Failed to initialize LaF");
					} // fin try catch

					AppPrincipale7 frame = new AppPrincipale7();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				} // fin try catch
			}// fin run
		});
	}// fin main

	/**
	 * Constructeur de la scène
	 */
	// Liangchen Liu
	public AppPrincipale7() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AppPrincipale7.class.getResource("/logo/logo.png")));
		donnesTank = new DonnesTank();
		donnesCircuit = new DonnesCircuit();
		donnesCurling = new DonnesCurling();
		donnesPeche = new DonnesPeche();
		donnesClassements = new DonnesClassements();
		donnesBadges = new DonnesBadges();
		donnesEtoiles = new DonnesEtoiles();
		GestionnaireDesDonnes.getInstance().setDonnesCircuit(donnesCircuit);
		GestionnaireDesDonnes.getInstance().setDonnesCurling(donnesCurling);
		GestionnaireDesDonnes.getInstance().setDonnesPeche(donnesPeche);
		GestionnaireDesDonnes.getInstance().setDonnesTank(donnesTank);

		GestionnaireDesDonnes.getInstance().setDonnesClassements(donnesClassements);
		GestionnaireDesDonnes.getInstance().setDonnesBadges(donnesBadges);
		GestionnaireDesDonnes.getInstance().setDonnesEtoiles(donnesEtoiles);

		dimensions = new Rectangle((int) screenSize.getWidth() / 2 - 1300 / 2,
				(int) screenSize.getHeight() / 2 - 700 / 2, 1300, 750);
		setBounds(dimensions);

		tank = new ZoneAnimationTank();
		tank.setBounds(dimensions);
		curling = new ZoneAnimationCurling();
		curling.setBounds(dimensions);

		circuit = new Circuit();

		circuit.setBounds(dimensions);

		peche = new Peche();
		peche.setBounds(100, 100, 1400, 750);

		menuAccueil = new MenuPrincipale();
		menuAccueil.setBounds(dimensions);

		params = new Parametres();
		params.setBounds(dimensions);

		classements = new Classements();
		classements.setBounds(dimensions);

		badges = new Badges();
		badges.setBounds(dimensions);

		selecNiveau = new SelecteurDeNiveau();
		selecNiveau.setBounds(dimensions);

		aPropos = new Apropos();

		fenetreGraphiques = new FenetreGraphiques();
		fenetreGraphiques.setBounds(new Rectangle((int) screenSize.getWidth() / 2 - 1300 / 2 + 1300,
				(int) screenSize.getHeight() / 2 - 700 / 2, 1000, 750));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		params.demarrerMusique();

		menuAccueil.setBorder(new EmptyBorder(5, 5, 5, 5));

		setTitle("Tête-Cassée");
		setResizable(false);
		setContentPane(menuAccueil);
		requestFocusInWindow();
		menuAccueil.setLayout(null);
		menuAccueil.setVisible(true);

		menuAccueilPropChange();
		paramsPropChange();
		selecNiveauPropChange();
		classementsPropChange();
		badgesPropChange();
		aProposPropChange();

		circuitPropChange();
		tankPropChange();
		curlingPropChange();
		pechePropChange();

	}// fin constructeur

	/**
	 * Méthode qui permet de changer de panel dans le menu principal
	 */
	// Liangchen Liu
	private void menuAccueilPropChange() {
		menuAccueil.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "nouvellePartie":
						selecNiveau.majNbEtoiles();

						menuAccueil.setVisible(false);
						selecNiveau.setVisible(true);
						setContentPane(selecNiveau);
						selecNiveau.requestFocusInWindow();

						break;

					case "settings":
						menuAccueil.setVisible(false);
						params.setVisible(true);
						setContentPane(params);
						params.requestFocusInWindow();

						break;

					case "classements":
						classements.organiserLesValeurs();
						GestionnaireDesDonnes.getInstance().getDonnesBadges().setBadgeOuvrirClassement3(true);

						menuAccueil.setVisible(false);
						classements.setVisible(true);
						setContentPane(classements);
						classements.requestFocusInWindow();

						break;

					case "badges":
						badges.majBadges();

						menuAccueil.setVisible(false);
						badges.setVisible(true);
						setContentPane(badges);
						badges.requestFocusInWindow();

						break;

					case "aPropos":
						GestionnaireDesDonnes.getInstance().getDonnesBadges().setBadgeOuvrirApropos4(true);
						menuAccueil.setVisible(false);
						aPropos.setVisible(true);
						aPropos.setBackground(new Color(22, 22, 30));
						setContentPane(aPropos);
						aPropos.requestFocusInWindow();

						break;

					case "circuit":
						selecNiveau.majNbEtoiles();

						selecNiveau.setVisible(false);
						setContentPane(circuit);
						circuit.setVisible(true);
						circuit.requestFocusInWindow();
						circuit.calculerTempsEcoule(true);

						break;

					case "curling":
						selecNiveau.majNbEtoiles();

						selecNiveau.setVisible(false);
						curling.setVisible(true);
						setContentPane(curling);
						curling.requestFocusInWindow();

						curling.calculerTempsEcoule(true);
						break;

					case "peche":
						selecNiveau.majNbEtoiles();

						selecNiveau.setVisible(false);
						peche.setVisible(true);
						setContentPane(peche);
						peche.requestFocusInWindow();

						peche.calculerTempsEcoule(true);
						peche.demarrer();

						break;

					case "tank":
						selecNiveau.majNbEtoiles();

						selecNiveau.setVisible(false);
						tank.setVisible(true);
						setContentPane(tank);
						tank.requestFocusInWindow();

						tank.calculerTempsEcoule(true);
						tank.setConditionCbo(true);

						break;

				}// fin switch
			}// fin propertyChange
		});

	}// fin méthode

	/**
	 * Méthode qui permet de changer de panel dans les crédits
	 */
	// Liangchen Liu
	private void aProposPropChange() {
		aPropos.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "menuAccueil":
						setBounds(dimensions);
						aPropos.setVisible(false);
						menuAccueil.setVisible(true);
						setContentPane(menuAccueil);
						menuAccueil.requestFocusInWindow();

						break;
				}// fin switch
			}// fin propertyChange
		});

	}

	/**
	 * Méthode qui permet de changer de panel dans les paramètres
	 */
	// Liangchen Liu
	private void paramsPropChange() {
		params.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "menuAccueil":
						params.setVisible(false);
						menuAccueil.setVisible(true);
						setContentPane(menuAccueil);
						menuAccueil.requestFocusInWindow();

						break;
				}// fin switch
			}// fin propertyChange
		});

	}// fin méthode

	/**
	 * Méthode qui permet de changer de panel dans le selecteur de niveau
	 */
	// Liangchen Liu
	private void selecNiveauPropChange() {
		selecNiveau.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "menuAccueil":
						selecNiveau.setVisible(false);
						setContentPane(menuAccueil);
						menuAccueil.setVisible(true);
						menuAccueil.requestFocusInWindow();

						break;

					case "circuit":
						selecNiveau.setVisible(false);
						setContentPane(circuit);
						circuit.setVisible(true);
						circuit.requestFocusInWindow();
						if (premiereFoisInstructionCircuit) {
							circuit.instructions();
							premiereFoisInstructionCircuit = false;
						}
						circuit.calculerTempsEcoule(true);
						break;

					case "curling":
						selecNiveau.setVisible(false);
						curling.setVisible(true);
						setContentPane(curling);
						curling.requestFocusInWindow();

						if (premiereFoisInstructionCurling) {
							curling.instructions();
							premiereFoisInstructionCurling = false;
						}
						curling.calculerTempsEcoule(true);
						break;

					case "peche":
						selecNiveau.setVisible(false);
						peche.setVisible(true);
						setContentPane(peche);
						peche.requestFocusInWindow();

						peche.demarrer();

						if (premiereFoisInstructionPeche) {
							peche.instructions();
							premiereFoisInstructionPeche = false;
						}
						peche.calculerTempsEcoule(true);

						break;

					case "tank":
						selecNiveau.setVisible(false);
						tank.setVisible(true);
						setContentPane(tank);
						tank.requestFocusInWindow();

						tank.setConditionCbo(true);
						if (premiereFoisInstructionTank) {
							tank.instructions();
							premiereFoisInstructionTank = false;
						}
						tank.calculerTempsEcoule(true);
						break;

				}// fin switch
			}// fin propertyChange
		});
	}// fin méthode

	/**
	 * Méthode qui permet de changer de panel dans la fenêtre des classements
	 */
	// Liangchen Liu
	private void classementsPropChange() {
		classements.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "menuAccueil":
						classements.setVisible(false);
						menuAccueil.setVisible(true);
						setContentPane(menuAccueil);
						menuAccueil.requestFocusInWindow();

						break;

				}// fin switch
			}// fin propertyChange
		});
	}// fin méthode

	/**
	 * Méthode qui permet de changer de panel dans la fenêtre des badges
	 */
	// Liangchen Liu
	private void badgesPropChange() {
		badges.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "menuAccueil":
						badges.setVisible(false);
						menuAccueil.setVisible(true);
						setContentPane(menuAccueil);
						menuAccueil.requestFocusInWindow();

						break;

				}// fin switch
			}// fin propertyChange
		});
	}// fin méthode

	/**
	 * Méthode qui permet de changer de panel dans jeu de pêche
	 */
	// Liangchen Liu
	private void pechePropChange() {
		peche.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "selecNiveau":
						selecNiveau.majNbEtoiles();
						peche.setVisible(false);
						selecNiveau.setVisible(true);
						setContentPane(selecNiveau);
						selecNiveau.requestFocusInWindow();

						peche.calculerTempsEcoule(false);
						peche.reini();
						peche.arreter();
						selecNiveau.actualiserLesEtoiles();
						break;

					case "suivant":
						peche.calculerTempsEcoule(false);
						peche.reini();
						peche.arreter();

						peche.setVisible(false);
						tank.setVisible(true);
						setContentPane(tank);
						tank.requestFocusInWindow();

						tank.calculerTempsEcoule(true);
						break;

				}// fin switch
			}// fin propertyChange
		});
	}// fin méthode

	/**
	 * Méthode qui permet de changer de panel dans le jeu des chars d'assault
	 */
	// Liangchen Liu
	private void tankPropChange() {
		tank.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "selecNiveau":
						selecNiveau.majNbEtoiles();
						tank.setVisible(false);
						selecNiveau.setVisible(true);
						setContentPane(selecNiveau);
						selecNiveau.requestFocusInWindow();

						tank.calculerTempsEcoule(false);
						tank.reini();
						selecNiveau.actualiserLesEtoiles();
						break;

				}// fin switch
			}// fin propertyChange
		});

	}// fin méthode

	/**
	 * Méthode qui permet de changer de panel dans le jeu de curling
	 */
	// Liangchen Liu
	private void curlingPropChange() {
		curling.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "selecNiveau":
						selecNiveau.majNbEtoiles();
						curling.setVisible(false);
						selecNiveau.setVisible(true);
						setContentPane(selecNiveau);
						selecNiveau.requestFocusInWindow();
						curling.reini();
						curling.getFenetreGraphiques().reini();

						curling.calculerTempsEcoule(false);
						selecNiveau.actualiserLesEtoiles();
						if (curling.getFenetreGraphiques() != null) {
							curling.getFenetreGraphiques().setVisible(false);
							curling.getFenetreGraphiques().reini();

						}

						break;

					case "suivant":
						curling.reini();
						curling.calculerTempsEcoule(false);

						curling.setVisible(false);
						peche.setVisible(true);
						setContentPane(peche);
						peche.requestFocusInWindow();

						peche.calculerTempsEcoule(true);
						peche.demarrer();
						break;

				}// fin switch
			}// fin propertyChange
		});

	}// fin méthode

	/**
	 * Méthode qui permet de changer de panel dans le jeu de curling
	 */
	// Liangchen Liu
	private void circuitPropChange() {
		circuit.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "selecNiveau":
						selecNiveau.majNbEtoiles();
						circuit.setVisible(false);
						selecNiveau.setVisible(true);
						setContentPane(selecNiveau);
						selecNiveau.requestFocusInWindow();

						circuit.calculerTempsEcoule(false);
						selecNiveau.actualiserLesEtoiles();
						circuit.reini();
						break;

					case "suivant":
						circuit.calculerTempsEcoule(false);
						circuit.reini();

						circuit.setVisible(false);
						curling.setVisible(true);
						setContentPane(curling);
						curling.requestFocusInWindow();

						curling.calculerTempsEcoule(true);
						break;
				}// fin switch
			}// fin propertyChange
		});

	}// fin méthode

}// fin classe