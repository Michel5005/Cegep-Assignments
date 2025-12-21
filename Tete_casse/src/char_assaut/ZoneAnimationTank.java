package char_assaut;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GestionDesFichiers.GestionnaireDesDonnes;
import application.AppPrincipale7;
import application.Temps;
import classements.DonnesClassements;
import classements.InformationEssais;
import menus.FenetreAideInstructions;
import menus.InterfaceGenerale;
import menus.MenuFinJeu;
import moteurPhysique.SVector3d;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;

/**
 * Classe qui représente le panneau de jeu pour le jeu char d'assaut.
 * 
 * @author Michel Vuu
 * @author Liangchen Liu
 * @author Jason Yin
 * @author Ismaïl Boukari
 * @author Caroline Houle
 *
 */
public class ZoneAnimationTank extends InterfaceGenerale implements Runnable {
	/** Version de séralisation de la classe */
	private static final long serialVersionUID = 100L;
	/** Numéro du niveau. */
	private static final int NIVEAU = 4;
	/** Création du char d'assaut. */
	private Tank charAssaut;
	/** Nombre de segments pour le terrain courbé. */
	private int nbSegmentsPourApproximer = 300;
	/** Position en x du terrain. */
	private int x = 0;
	/** Incrémentation pour le déplacement du char d'assaut. */
	private double deplacement = 0.3;
	/** Création du terrain. */
	private Terrain terrain;
	/** Le temps total écoulé. */
	private double tempsTotalEcoule = 0;
	/** Temps de repos de l'animation */
	private int tempsDuSleep = 8;
	/** Indique si l'animation est en cours d'exécution. */
	private boolean enCoursDAnimation = false;
	/** L'intervalle de temps */
	private double deltaT = 0.015;
	/** Indique si c'est la première fois que l'animation est exécutée. */
	private boolean premiereFois;
	/** Largeur du composant en mètres */
	private double largeurDuComposantEnMetres = 100;
	/** Facteur de mise à l'échelle utilisé lors du dessin. */
	private double pixelsParMetre;
	/** Hauteur du composant en mètres */
	private double hauteurDuComposantEnMetres;
	/** Hauteur du char d'assaut */
	private final double hauteurDuTank = 1.6;// 20;
	/** Largeur du char d'assaut */
	private final double largeurDuTank = 3.2;// 40;
	/** Position du char d'assaut en x */
	private double xTank;
	/** Position du char d'assaut en y */
	private double yTank;
	/** La position en x du premier coin du char d'assaut. */
	private double x1;
	/** La position en x du deuxième coin du char d'assaut. */
	private double x2;
	/** La position en y du premier coin du char d'assaut. */
	private double y1;
	/** La position en y du deuxième coin du char d'assaut. */
	private double y2;
	/** Angle de rotation lié au déplacement du char d'assaut. */
	private double theta;

	/** Objet boulet de canon normal */

	private BouletCanonNormal bouletCanonNormal;
	/** État du jeu où on observe le boulet de canon se faire tirer */
	private boolean etatTire = false;
	/** Accéleration de la gravité terreste */
	private double gravite = TypePlaneteTerrain.TERRE.getGravite();
	/** masseJoueur du boulet de canon */
	private final double MASSE_INIT = 6;
	private double masseJoueur = MASSE_INIT;
	private double masseEnnemi = MASSE_INIT;
	/** Diametre du boulet de canon */
	private double diametre = 0.77;
	/** Angle de tir du boulet de canon */
	private double angleTir = 20;
	/** Vitesse initial du boulet de canon */
	private double vitesseInit;
	private final double VITESSE_INITIALE = 20;
	/** Position vectoriel du char d'assaut */
	private SVector3d posTank = new SVector3d(xTank, yTank, 0);
	/** Vitesse vectoriel du boulet de canon */
	private SVector3d vitesse = new SVector3d(0, 0, 0);
	/** Acceleration vectoriel du boulet de canon */
	private SVector3d acceleration = new SVector3d(0, 0, 0);
	/** Somme des forces appliqué sur le boulet de canon */
	private SVector3d sommeDesForces = new SVector3d(0, gravite * masseJoueur, 0);

	private boolean afficherFlecheVitesse = true;
	private boolean afficherFlecheAccel = false;
	private boolean afficherFlecheForce = false;
	private double facteurRedim = 0.5;
	private double tailleTeteFleche = 0.4;

	private TrajectoireProjectile trajectoireProjectile;

	/** objet canon */
	private Canon canon;
	private double xCanon = xTank;
	private double yCanon = yTank;
	private double largeurCanon = 0.7;
	private double longueurCanon = 2.33;

	/** Position du char d'assaut en x initial */
	private final double POSITION_INI_TANK_X = 1;
	/** Position du char d'assaut en y initial */
	private final double POSITION_INI_TANK_Y = (hauteurDuComposantEnMetres * 6) - 10;
	/** Position en x du premier coin du char d'assaut */
	private final double X1 = 1;
	/** Position en x du deuxième coin du char d'assaut */
	private final double X2 = 1 + largeurDuTank;
	/** Position en y du premier coin du char d'assaut */
	private final double Y1 = 1;
	/** Position en y du deuxième coin du char d'assaut */
	private final double Y2 = 1 + hauteurDuTank;
	/** Angle relié au déplacement du char d'assaut initial */
	private final double THETA_INI = 0;

	/** La position maximale en x du séparateur. */
	private double xMaxSeparator = 1125;
	/** Temps total écoulé depuis le début de la simulation */
	private double tempsTotal = 0;
	/** Étiquette d'indice */
	private JLabel lblInfoEtudiant;
	/** Bouton qui arrête l'état de tir */
	private JButton btnArret;
	/** Bouton qui début l'état de tir */
	private JButton btnTire;

	/** Objet de type Graphics2D permettant de pouvoir dessiner plusieurs choses */
	private Graphics2D g2d;

	/** Les instructions lié à la touche "p" du clavier */
	private String instructions = "- Bouger le char d'aussaut avec les touches directionnelles (gauches/droite) du clavier selon la limite du carburant\n "
			+ "- Cliquer sur le bouton retour (première flèche en haut à gauche) pour revenir au menu principal \n "
			+ "- Cliquer sur le bouton sauvegarder pour sauvegarder votre progression \n "
			+ "- Cliquer sur le bouton reinitialiser pour réinitialiser la position du char d'assaut \n "
			+ "- Survoler le bouton informations (troisième en haut à gauche) où les informations vont être affichées dans une fenêtre pop-up qui disparait quand l'utilisateur enlève la souris de l'icône d'information."
			+ "- Vous pouvez bouger le char d'assaut à un autre endroit et cliquer sur sauvegarder. Quand vous allez charger la sauvegarde, le char devrait être au même endroit que vous l'avez sauvegardé \n "
			+ "- La position du char d'aussault ne sera pas réinitialisé si vous quittez et revenez dans le panel \n "
			+ "- Cliquer sur le bouton \"TIRER\" ou la barre d'espace pour tirer le boulet \n "
			+ "- Cliquer sur le bouton \"Arret\" pour arrêter le boulet \n "
			+ "- Cliquer sur le bouton prochaine image (fleche avec la barre) pour jouer une prochaine image quand le boulet est dans les airs et qu'elle ne bouge pas \n "
			+ "- Bug notable:fleche vectorielle qui ne disparaisse pas,fleche du clavier ne fonctionne pas des fois, les boutons Arreter et Avancer un pas ne fonctionne pas pour le tir du tank ennemi et les apparitions des animations ne fonctionne pas comme attendu \n ";

	private AmelioTank pointsDeVie;
	private JComboBox cboTypeTerrain;
	private JSpinner spnVitesse;
	private final ButtonGroup buttonGroupVecteur = new ButtonGroup();
	private JRadioButton rdbtnVecteurForce;
	private JRadioButton rdbtnVecteurAccel;
	private JRadioButton rdbtnVecteurVitesse;
	private AmelioTank bouclier;
	private AmelioTank carburant;
	private AmelioTank tirs;
	private AmelioTank degat;
	private int randAmelio = new Random().nextInt((5));
	private int randX = new Random().nextInt((int) xMaxSeparator + 1);
	private int maxY = 360;
	private int randY = new Random().nextInt(maxY + 1);
	private JComboBox cboTypeTirs;
	private TankEnnemi char_ennemi;
	private boolean tirNormalSelectionne;
	private boolean tirRebondSelectionne;
	private boolean tirEparpilleSelectionne;
	private int nbTirEparp;
	private int nbTirRebond;
	private boolean voirDonnePetitBoulet = true;
	private boolean voirDonneMoyenBoulet = false;
	private boolean voirDonneGrandBoulet = false;

	private BouletCanonNormal bouletCanonRebond;
	private int nbRebond = 0;
	private int incrementationDepuisCollision = 0;
	private BouletCanonNormal bouletCanonEparpillePetit;
	private BouletCanonNormal bouletCanonEparpilleMoyen;
	private BouletCanonNormal bouletCanonEparpilleGrand;
	private SVector3d ajustEparpPetit = new SVector3d(1, 0, 0);
	private SVector3d ajustEparpGrand = new SVector3d(-1, 0, 0);
	private boolean arretTirEparpPetit = false;
	private boolean arretTirEparpMoyen = false;
	private boolean arretTirEparpGrand = false;

	private final int CARBURANT_INIT = 75;
	private final int CARBURANT_BONUS = 0;
	private int carburantBonusJoueur;
	private int carburantBonusEnnemi;
	private int carburantJoueur;
	private int carburantEnnemi;
	private JaugeCarburant jaugeCarburant;

	private double xTankEnnemi;
	private double yTankEnnemi;
	private double vitesseInitEnnemi = vitesseInit;
	private boolean tireFini = false;
	private boolean tourJoueur = true;
	private boolean tourFini = false;
	private int nbTourDepuisTouche = 0;
	private final int NB_DEGAT_INIT = 15;
	private int nbDegatJoueur = NB_DEGAT_INIT;
	private int nbDegatEnnemi = NB_DEGAT_INIT;

	private int espaceEntreTankEtBarre = 5;
	private final int NB_POINT_DE_VIE_INIT = 100;
	private final int NB_POINT_BOUCLIER_INIT = 50;
	private int nbPointDeVieJoueur = NB_POINT_DE_VIE_INIT;
	private int nbBouclierJoueur = NB_POINT_BOUCLIER_INIT;
	private double xPointDeVieJoueur;
	private double yPointDeVieJoueur;
	private double xBouclierJoueur;
	private double yBouclierJoueur;
	private double largeurBarreVieBouclier = largeurDuTank;

	private int nbPointDeVieEnnemi = NB_POINT_DE_VIE_INIT;
	private int nbBouclierEnnemi = NB_POINT_BOUCLIER_INIT;
	private double xPointDeVieEnnemi;
	private double yPointDeVieEnnemi;
	private double xBouclierEnnemi;
	private double yBouclierEnnemi;

	private BarreDeVieEtBouclier barreDeVieEtBouclierJoueur;
	private BarreDeVieEtBouclier barreDeVieEtBouclierEnnemi;
	private BarreDeVieEtBouclier barreDeVieEtBouclierJoueurInterface;
	private BarreDeVieEtBouclier barreDeVieEtBouclierEnnemiInterface;
	private int  posBarreVieBoucInterfaceX=415;
	private int  posBarreVieBoucInterfaceY=595;
	private int  hauteurBarreVieBoucInterface=20;
	private int  largeurBarreVieBoucInterface=150;

	private int nbTour;
	private DonnesTank donnesTank;
	private double X3 = 80;
	private double X4 = X3 + largeurDuTank;
	private double x3 = X3;
	private double x4 = X4;
	private double y3;
	private double y4;
	private double thetaEnnemi;
	private Canon canonEnnemi;
	private double xCanonEnnemi = xTankEnnemi;
	private double yCanonEnnemi = yTankEnnemi;
	private double angleTirEnnemi = 160;
	private JLabel lblAjusterVitesse;
	private boolean amelioDessine = false;

	private double petitAjustementCanonEnnemi = 0.05;
	private BouletCanonNormal bouletCanonEnnemi;
	private int petitAjustementBouletEnnemi = 5;
	private SVector3d posTankEnnemi = new SVector3d(xTankEnnemi, yTankEnnemi, 0);

	private JLabel lblCarburantJoueur;

	private SVector3d vitesseEnnemi = new SVector3d(0, 0, 0);
	private JLabel lblPointDeVieJoueur;
	private JLabel lblBouclierJoueur;
	private JLabel lblBouclierEnnemi;
	private JLabel lblPointDeVieEnnemi;
	private JLabel lblTemps;

	private Temps classeTemps;
	private long tempsEcoule;

	private boolean conditionCbo = false;
	private JSpinner spnmasseJoueur;

	private MenuFinJeu menuFinJeu;
	private JPanel panelBordurePositionTankJoueur;
	private JTextPane textPanePositionTankJoueur;
	private JPanel panelBordurePositionTankEnnemi;
	private JTextPane textPanePositionTankEnnemi;
	private JPanel panelBordurePosNormal;
	private JTextPane textPanePosNormal;
	private JLabel lblTitreTank;
	private JTextPane textPaneVitNormal;
	private JTextPane textPaneAccNormal;
	private JTextPane textPaneForcesNormal;
	private JPanel panelBordurePosRebond;
	private JTextPane textPanePosRebond;
	private JPanel panelBordureVitRebond;
	private JTextPane textPaneVitRebond;
	private JPanel panelBordureAccNormal;
	private JPanel panelBordureForcesNormal;
	private JTextPane textPaneAccRebond;
	private JPanel panelBordureAccRebond;
	private JPanel panelBordureForcesRebond;
	private JTextPane textPaneForcesRebond;
	private JPanel panelBordureVitNormal;
	private JRadioButton rdbtnDonneMoyenBoulet;
	private JRadioButton rdbtnDonneGrosBoulet;
	private final ButtonGroup buttonGroupDonneEparp = new ButtonGroup();
	private JPanel panelDonneTirNormal;
	private JPanel panelDonneTirRebond;
	private JPanel panelDonneTirEparpPetit;
	private JPanel panelBordurePosEparpPetit;
	private JPanel panelBordureVitEparpPetit;
	private JPanel panelBordureAccEparpPetit;
	private JPanel panelBordureForcesEparpPetit;
	private JTextPane textPanePosEparpPetit;
	private JTextPane textPaneVitEparpPetit;
	private JTextPane textPaneAccEparpPetit;
	private JTextPane textPaneForcesEparpPetit;
	private JRadioButton rdbtnDonnePetitBoulet;
	private JPanel panelDonneTirEparpMoyen;
	private JPanel panelBordurePosEparpMoyen;
	private JTextPane textPanePosEparpMoyen;
	private JPanel panelBordureVitEparpMoyen;
	private JTextPane textPaneVitEparpMoyen;
	private JPanel panelBordureAccEparpMoyen;
	private JTextPane textPaneAccEparpMoyen;
	private JPanel panelBordureForcesEparpMoyen;
	private JTextPane textPaneForcesEparpMoyen;
	private JPanel panelDonneTirEparpGrand;
	private JPanel panelBordurePosEparpGrand;
	private JTextPane textPanePosEparpGrand;
	private JPanel panelBordureVitEparpGrand;
	private JTextPane textPaneVitEparpGrand;
	private JPanel panelBordureAccEparpGrand;
	private JTextPane textPaneAccEparpGrand;
	private JPanel panelBordureForcesEparpGrand;
	private JTextPane textPaneForcesEparpGrand;
	private JLabel lblNbRestantTirEparpille;
	private JLabel lblNbRestantTirRebond;

	private boolean modeTriche;

	/**
	 * Création du constructeur.
	 */
	// Par Michel Vuu
	public ZoneAnimationTank() {
		donnesTank = GestionnaireDesDonnes.getInstance().getDonnesTank();

		classeTemps = new Temps(4);
		menuFinJeu = new MenuFinJeu();

		instProf();
		infoJoueur();
		sauv();
		btnReini();
		aide();
		exProf();
		prochImg();

		setBackground(Color.white);
		setPreferredSize(new Dimension(1300, 700));

		btnTire = new JButton("Continuer");
		btnTire.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
					etatTire = true;
					spnVitesse.setEnabled(false);
					spnmasseJoueur.setEnabled(false);
					cboTypeTerrain.setEnabled(false);
					cboTypeTirs.setEnabled(false);
					demarrer();
				requestFocusInWindow();
			}// fin methode
		});// fin actionListener
		btnTire.setEnabled(true);
		btnTire.setBounds(1018, 586, 97, 84);
		add(btnTire);

		btnArret = new JButton("Arret");
		btnArret.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				arreter();
				requestFocusInWindow();
			}// fin methode
		});// fin actionListener
		btnArret.setBounds(909, 586, 97, 84);
		add(btnArret);

		lblInfoEtudiant = new JLabel("Éliminez le char ennemi en lui tirant dessus");
		lblInfoEtudiant.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInfoEtudiant.setEnabled(true);
		lblInfoEtudiant.setVisible(false);
		lblInfoEtudiant.setBounds(112, 140, 317, 69);
		add(lblInfoEtudiant);

		cboTypeTerrain = new JComboBox();
		cboTypeTerrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				typeTerrainSelectionne();
				requestFocusInWindow();
			}
		});// fin actionListener
		cboTypeTerrain.setModel(new DefaultComboBoxModel(new String[] { "Terre (9.807 m/s²)", "Mars (3.721 m/s²)",
				"Mercure (3.700 m/s²)", "Lune ( 1.620 m/s²)" }));
		cboTypeTerrain.setBounds(4, 601, 174, 22);

		add(cboTypeTerrain);

		spnVitesse = new JSpinner();
		spnVitesse.setModel(new SpinnerNumberModel(20, 5, 50, 1));
		spnVitesse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				vitesseInit = (int) spnVitesse.getValue();
				donnesTank.setVitesseInit(vitesseInit);
				vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
						((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
				if (tirNormalSelectionne)
					bouletCanonNormal.setVitesse(vitesse);
				if (tirEparpilleSelectionne) {
					bouletCanonEparpillePetit.setVitesse(vitesse.add(ajustEparpPetit));
					bouletCanonEparpilleGrand.setVitesse(vitesse.add(ajustEparpGrand));
					bouletCanonEparpilleMoyen.setVitesse(vitesse);
				}
				trajectoireProjectile = new TrajectoireProjectile(xTank, yTank, -angleTir, gravite, vitesseInit,
						pixelsParMetre);
				repaint();
				requestFocusInWindow();
			}
		});// fin actionListener
		spnVitesse.setBounds(802, 602, 72, 21);
		add(spnVitesse);

		rdbtnVecteurVitesse = new JRadioButton("Vecteur Vitesse");
		rdbtnVecteurVitesse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherFlecheVitesse = rdbtnVecteurVitesse.isSelected();
				afficherFlecheAccel = rdbtnVecteurAccel.isSelected();
				afficherFlecheForce = rdbtnVecteurForce.isSelected();
				requestFocusInWindow();
			}
		});
		buttonGroupVecteur.add(rdbtnVecteurVitesse);
		rdbtnVecteurVitesse.setSelected(true);
		rdbtnVecteurVitesse.setBounds(599, 586, 127, 21);
		add(rdbtnVecteurVitesse);

		rdbtnVecteurAccel = new JRadioButton("Vecteur Accélération");
		rdbtnVecteurAccel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherFlecheVitesse = rdbtnVecteurVitesse.isSelected();
				afficherFlecheAccel = rdbtnVecteurAccel.isSelected();
				afficherFlecheForce = rdbtnVecteurForce.isSelected();
				requestFocusInWindow();
			}
		});// fin actionListener
		buttonGroupVecteur.add(rdbtnVecteurAccel);
		rdbtnVecteurAccel.setBounds(599, 618, 127, 21);
		add(rdbtnVecteurAccel);

		rdbtnVecteurForce = new JRadioButton("Vecteur Forces");
		rdbtnVecteurForce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherFlecheVitesse = rdbtnVecteurVitesse.isSelected();
				afficherFlecheAccel = rdbtnVecteurAccel.isSelected();
				afficherFlecheForce = rdbtnVecteurForce.isSelected();
				requestFocusInWindow();
			}
		});// fin actionListener
		buttonGroupVecteur.add(rdbtnVecteurForce);
		rdbtnVecteurForce.setBounds(599, 652, 127, 21);
		add(rdbtnVecteurForce);
		cboTypeTirs = new JComboBox();
		cboTypeTirs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				typeDeTirSelectionne();
				if ((tirEparpilleSelectionne && nbTirEparp == 0) || (tirRebondSelectionne && nbTirRebond == 0)) {
					btnTire.setEnabled(false);
				} else {
					btnTire.setEnabled(true);
				}
				requestFocusInWindow();
			}
		});
		cboTypeTirs
			.setModel(new DefaultComboBoxModel(new String[] { ("Tir normal"+" (\u221e x)"), ("Tir rebondissant"+" ("+nbTirRebond+"x)"), ("Tir éparpillé"+" ("+nbTirEparp+"x)") }));
		cboTypeTirs.setBounds(4, 648, 174, 22);
		add(cboTypeTirs);

		lblAjusterVitesse = new JLabel("Vitesse Initiale (m/s):");
		lblAjusterVitesse.setBounds(775, 580, 144, 25);
		add(lblAjusterVitesse);

		lblCarburantJoueur = new JLabel("Niveau de carburant: 100 ");
		lblCarburantJoueur.setBounds(200, 686, 151, 14);
		add(lblCarburantJoueur);

		lblPointDeVieJoueur = new JLabel("Point de Vie Joueur:100 ");
		lblPointDeVieJoueur.setHorizontalAlignment(SwingConstants.LEFT);
		lblPointDeVieJoueur.setBounds(414, 577, 151, 14);
		add(lblPointDeVieJoueur);

		JLabel lblTypeTerrain = new JLabel("Type de terrain :");
		lblTypeTerrain.setBounds(4, 581, 130, 23);
		add(lblTypeTerrain);

		JLabel lblTypeDeTir = new JLabel("Type de tir :");
		lblTypeDeTir.setBounds(4, 629, 176, 22);
		add(lblTypeDeTir);

		lblBouclierJoueur = new JLabel("Point de bouclier Joueur:50");
		lblBouclierJoueur.setBounds(414, 594, 162, 13);
		add(lblBouclierJoueur);

		lblPointDeVieEnnemi = new JLabel("Point de Vie ennemi:100 ");
		lblPointDeVieEnnemi.setHorizontalAlignment(SwingConstants.LEFT);
		lblPointDeVieEnnemi.setBounds(414, 618, 162, 14);
		add(lblPointDeVieEnnemi);

		lblBouclierEnnemi = new JLabel("Point de bouclier ennemi:50");
		lblBouclierEnnemi.setBounds(414, 638, 162, 13);
		add(lblBouclierEnnemi);

		lblTemps = new JLabel();
        lblTemps.setFont(AppPrincipale7.interBold.deriveFont(12f));
		lblTemps.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTemps.setBounds(852, 20, 249, 21);
		lblTemps.setText("" + tempsEcoule);
		add(lblTemps);

		spnmasseJoueur = new JSpinner();
		spnmasseJoueur.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				masseJoueur = (int) spnmasseJoueur.getValue();
				if (tirNormalSelectionne) {
					bouletCanonNormal.setMasse(masseJoueur);
					bouletCanonNormal.setSommeDesForce(new SVector3d(0, gravite * masseJoueur, 0));
				} else if (tirRebondSelectionne) {
					bouletCanonRebond.setMasse(masseJoueur);
					bouletCanonRebond.setSommeDesForce(new SVector3d(0, gravite * masseJoueur, 0));
				} else if (tirEparpilleSelectionne) {
					bouletCanonEparpilleGrand.setMasse(masseJoueur);
					bouletCanonEparpillePetit.setMasse(masseJoueur);
					bouletCanonEparpilleMoyen.setMasse(masseJoueur);
					bouletCanonEparpilleGrand.setSommeDesForce(new SVector3d(0, gravite * masseJoueur, 0));
					bouletCanonEparpillePetit.setSommeDesForce(new SVector3d(0, gravite * masseJoueur, 0));
					bouletCanonEparpilleMoyen.setSommeDesForce(new SVector3d(0, gravite * masseJoueur, 0));
				}
				requestFocusInWindow();
			}
		});
		spnmasseJoueur.setModel(new SpinnerNumberModel(6, 3, 12, 1));
		spnmasseJoueur.setBounds(802, 648, 72, 22);
		add(spnmasseJoueur);

		JLabel lblAjustermasseJoueur = new JLabel("Masse de votre boulet (kg):");
		lblAjustermasseJoueur.setBounds(734, 628, 174, 25);
		add(lblAjustermasseJoueur);

		panelBordurePositionTankJoueur = new JPanel();
		panelBordurePositionTankJoueur.setLayout(null);
		panelBordurePositionTankJoueur.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Position du Joueur(m)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordurePositionTankJoueur.setBounds(1136, 50, 137, 67);
		add(panelBordurePositionTankJoueur);

		textPanePositionTankJoueur = new JTextPane();
		textPanePositionTankJoueur.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPanePositionTankJoueur.setEditable(false);
		textPanePositionTankJoueur.setContentType("text/html");
		textPanePositionTankJoueur.setAlignmentX(0.5f);
		textPanePositionTankJoueur.setBounds(6, 16, 125, 38);
		panelBordurePositionTankJoueur.add(textPanePositionTankJoueur);

		panelBordurePositionTankEnnemi = new JPanel();
		panelBordurePositionTankEnnemi.setLayout(null);
		panelBordurePositionTankEnnemi.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Position de l'ennemi (m)" + "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordurePositionTankEnnemi.setBounds(1136, 115, 137, 67);
		add(panelBordurePositionTankEnnemi);

		textPanePositionTankEnnemi = new JTextPane();
		textPanePositionTankEnnemi.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPanePositionTankEnnemi.setEditable(false);
		textPanePositionTankEnnemi.setContentType("text/html");
		textPanePositionTankEnnemi.setAlignmentX(0.5f);
		textPanePositionTankEnnemi.setBounds(6, 16, 125, 38);
		panelBordurePositionTankEnnemi.add(textPanePositionTankEnnemi);

		panelDonneTirNormal = new JPanel();
		panelDonneTirNormal.setBorder(
				new TitledBorder(null, "Donnés du tir Normal", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDonneTirNormal.setBounds(1130, 203, 149, 340);
		add(panelDonneTirNormal);
		panelDonneTirNormal.setLayout(null);

		panelBordurePosNormal = new JPanel();
		panelBordurePosNormal.setBounds(6, 18, 137, 78);
		panelDonneTirNormal.add(panelBordurePosNormal);
		panelBordurePosNormal.setLayout(null);
		panelBordurePosNormal.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center><dynamic>" + "Position du boulet normal \n(m)"
						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPanePosNormal = new JTextPane();
		textPanePosNormal.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPanePosNormal.setEditable(false);
		textPanePosNormal.setContentType("text/html");
		textPanePosNormal.setAlignmentX(0.5f);
		textPanePosNormal.setBounds(6, 32, 125, 33);
		panelBordurePosNormal.add(textPanePosNormal);

		panelBordureVitNormal = new JPanel();
		panelBordureVitNormal.setBounds(6, 98, 137, 78);
		panelDonneTirNormal.add(panelBordureVitNormal);
		panelBordureVitNormal.setLayout(null);
		panelBordureVitNormal.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Vitesse du boulet normal \n(m/s)"
						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPaneVitNormal = new JTextPane();
		textPaneVitNormal.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneVitNormal.setEditable(false);
		textPaneVitNormal.setContentType("text/html");
		textPaneVitNormal.setAlignmentX(0.5f);
		textPaneVitNormal.setBounds(6, 30, 125, 35);
		panelBordureVitNormal.add(textPaneVitNormal);

		panelBordureAccNormal = new JPanel();
		panelBordureAccNormal.setBounds(6, 176, 137, 78);
		panelDonneTirNormal.add(panelBordureAccNormal);
		panelBordureAccNormal.setLayout(null);
		panelBordureAccNormal.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Accelération du boulet normal \n(m/s^2)"
						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPaneAccNormal = new JTextPane();
		textPaneAccNormal.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneAccNormal.setEditable(false);
		textPaneAccNormal.setContentType("text/html");
		textPaneAccNormal.setAlignmentX(0.5f);
		textPaneAccNormal.setBounds(6, 31, 125, 34);
		panelBordureAccNormal.add(textPaneAccNormal);

		panelBordureForcesNormal = new JPanel();
		panelBordureForcesNormal.setBounds(6, 256, 137, 78);
		panelDonneTirNormal.add(panelBordureForcesNormal);
		panelBordureForcesNormal.setLayout(null);
		panelBordureForcesNormal.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Forces appliqués sur le boulet normal \n(N)"
						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPaneForcesNormal = new JTextPane();
		textPaneForcesNormal.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneForcesNormal.setEditable(false);
		textPaneForcesNormal.setContentType("text/html");
		textPaneForcesNormal.setAlignmentX(0.5f);
		textPaneForcesNormal.setBounds(6, 33, 125, 32);
		panelBordureForcesNormal.add(textPaneForcesNormal);

		lblTitreTank = new JLabel("Données sur les tanks");
		lblTitreTank.setBounds(1136, 21, 137, 16);
		add(lblTitreTank);

		panelDonneTirRebond = new JPanel();
		panelDonneTirRebond.setBorder(new TitledBorder(null, "Donnés du tir rebondissant", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelDonneTirRebond.setBounds(1130, 203, 149, 340);
		add(panelDonneTirRebond);
		panelDonneTirRebond.setLayout(null);

		panelBordurePosRebond = new JPanel();
		panelBordurePosRebond.setBounds(6, 18, 137, 78);
		panelDonneTirRebond.add(panelBordurePosRebond);
		panelBordurePosRebond.setLayout(null);
		panelBordurePosRebond.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center><dynamic>" + "Position du boulet rebondissant \n(m)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPanePosRebond = new JTextPane();
		textPanePosRebond.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPanePosRebond.setEditable(false);
		textPanePosRebond.setContentType("text/html");
		textPanePosRebond.setAlignmentX(0.5f);
		textPanePosRebond.setBounds(6, 32, 125, 33);
		panelBordurePosRebond.add(textPanePosRebond);

		panelBordureVitRebond = new JPanel();
		panelBordureVitRebond.setBounds(6, 98, 137, 78);
		panelDonneTirRebond.add(panelBordureVitRebond);
		panelBordureVitRebond.setLayout(null);
		panelBordureVitRebond.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Vitesse du boulet rebondissant \n(m/s)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPaneVitRebond = new JTextPane();
		textPaneVitRebond.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneVitRebond.setEditable(false);
		textPaneVitRebond.setContentType("text/html");
		textPaneVitRebond.setAlignmentX(0.5f);
		textPaneVitRebond.setBounds(6, 30, 125, 35);
		panelBordureVitRebond.add(textPaneVitRebond);

		panelBordureAccRebond = new JPanel();
		panelBordureAccRebond.setBounds(6, 176, 137, 78);
		panelDonneTirRebond.add(panelBordureAccRebond);
		panelBordureAccRebond.setLayout(null);
		panelBordureAccRebond.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Accelération du boulet rebondissant \n(m/s^2)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPaneAccRebond = new JTextPane();
		textPaneAccRebond.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneAccRebond.setEditable(false);
		textPaneAccRebond.setContentType("text/html");
		textPaneAccRebond.setAlignmentX(0.5f);
		textPaneAccRebond.setBounds(6, 31, 125, 34);
		panelBordureAccRebond.add(textPaneAccRebond);

		panelBordureForcesRebond = new JPanel();
		panelBordureForcesRebond.setBounds(6, 256, 137, 78);
		panelDonneTirRebond.add(panelBordureForcesRebond);
		panelBordureForcesRebond.setLayout(null);
		panelBordureForcesRebond.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Forces appliqués sur le boulet rebondissant \n(N)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPaneForcesRebond = new JTextPane();
		textPaneForcesRebond.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneForcesRebond.setEditable(false);
		textPaneForcesRebond.setContentType("text/html");
		textPaneForcesRebond.setAlignmentX(0.5f);
		textPaneForcesRebond.setBounds(6, 33, 125, 32);
		panelBordureForcesRebond.add(textPaneForcesRebond);

		rdbtnDonnePetitBoulet = new JRadioButton("Petit Boulet");
		rdbtnDonnePetitBoulet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voirDonnePetitBoulet = rdbtnDonnePetitBoulet.isSelected();
				voirDonneMoyenBoulet = rdbtnDonneMoyenBoulet.isSelected();
				voirDonneGrandBoulet = rdbtnDonneGrosBoulet.isSelected();
				requestFocusInWindow();
			}
		});
		buttonGroupDonneEparp.add(rdbtnDonnePetitBoulet);
		rdbtnDonnePetitBoulet.setSelected(true);
		rdbtnDonnePetitBoulet.setBounds(1136, 579, 139, 25);
		add(rdbtnDonnePetitBoulet);

		rdbtnDonneMoyenBoulet = new JRadioButton("Moyen Boulet");
		rdbtnDonneMoyenBoulet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voirDonnePetitBoulet = rdbtnDonnePetitBoulet.isSelected();
				voirDonneMoyenBoulet = rdbtnDonneMoyenBoulet.isSelected();
				voirDonneGrandBoulet = rdbtnDonneGrosBoulet.isSelected();
				requestFocusInWindow();
			}
		});
		buttonGroupDonneEparp.add(rdbtnDonneMoyenBoulet);
		rdbtnDonneMoyenBoulet.setBounds(1136, 616, 139, 25);
		add(rdbtnDonneMoyenBoulet);

		rdbtnDonneGrosBoulet = new JRadioButton("Gros Boulet");
		rdbtnDonneGrosBoulet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voirDonnePetitBoulet = rdbtnDonnePetitBoulet.isSelected();
				voirDonneMoyenBoulet = rdbtnDonneMoyenBoulet.isSelected();
				voirDonneGrandBoulet = rdbtnDonneGrosBoulet.isSelected();
				requestFocusInWindow();
			}
		});
		buttonGroupDonneEparp.add(rdbtnDonneGrosBoulet);
		rdbtnDonneGrosBoulet.setBounds(1136, 650, 139, 25);
		add(rdbtnDonneGrosBoulet);

		panelDonneTirEparpPetit = new JPanel();
		panelDonneTirEparpPetit.setBorder(new TitledBorder(null, "Donnés dupanelDonneTirNormal.setVisible(false);\r\n"
				+ "				panelDonneTirRebond.setVisible(false);\r\n"
				+ "				panelDonneTirEparpPetit.setVisible(true);\r\n"
				+ "				panelDonneTirEparpMoyen.setVisible(false);\r\n" + "				\r\n"
				+ "				textPanePosEparpPetit.setText(\"<html><font color='black'><center>\" + bouletCanonEparpillePetit.positionToString()+ \"</center></font></html>\");\r\n"
				+ "				textPaneVitEparpPetit.setText(\"<html><font color='black'><center>\" + bouletCanonEparpillePetit.vitesseToString()+ \"</center></font></html>\");\r\n"
				+ "				textPaneAccEparpPetit.setText(\"<html><font color='black'><center>\" + bouletCanonEparpillePetit.accelToString()+ \"</center></font></html>\");\r\n"
				+ "				textPaneForcesEparpPetit.setText(\"<html><font color='black'><center>\" + bouletCanonEparpillePetit.sommeForcesToString()+ \"</center></font></html>\"); le tir éparpillé(petit boulet)",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDonneTirEparpPetit.setBounds(1130, 203, 149, 340);
		add(panelDonneTirEparpPetit);
		panelDonneTirEparpPetit.setLayout(null);

		panelBordurePosEparpPetit = new JPanel();
		panelBordurePosEparpPetit.setBounds(6, 18, 137, 78);
		panelDonneTirEparpPetit.add(panelBordurePosEparpPetit);
		panelBordurePosEparpPetit.setLayout(null);
		panelBordurePosEparpPetit.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center><dynamic>" + "Position du petit boulet éparpillé \n(m)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPanePosEparpPetit = new JTextPane();
		textPanePosEparpPetit.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPanePosEparpPetit.setEditable(false);
		textPanePosEparpPetit.setContentType("text/html");
		textPanePosEparpPetit.setAlignmentX(0.5f);
		textPanePosEparpPetit.setBounds(6, 32, 125, 33);
		panelBordurePosEparpPetit.add(textPanePosEparpPetit);

		panelBordureVitEparpPetit = new JPanel();
		panelBordureVitEparpPetit.setBounds(6, 98, 137, 78);
		panelDonneTirEparpPetit.add(panelBordureVitEparpPetit);
		panelBordureVitEparpPetit.setLayout(null);
		panelBordureVitEparpPetit.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Vitesse du petit boulet éparpillé \n(m/s)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPaneVitEparpPetit = new JTextPane();
		textPaneVitEparpPetit.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneVitEparpPetit.setEditable(false);
		textPaneVitEparpPetit.setContentType("text/html");
		textPaneVitEparpPetit.setAlignmentX(0.5f);
		textPaneVitEparpPetit.setBounds(6, 30, 125, 35);
		panelBordureVitEparpPetit.add(textPaneVitEparpPetit);

		panelBordureAccEparpPetit = new JPanel();
		panelBordureAccEparpPetit.setBounds(6, 176, 137, 78);
		panelDonneTirEparpPetit.add(panelBordureAccEparpPetit);
		panelBordureAccEparpPetit.setLayout(null);
		panelBordureAccEparpPetit.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Accelération du boulet petit éparpillé \n(m/s^2)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPaneAccEparpPetit = new JTextPane();
		textPaneAccEparpPetit.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneAccEparpPetit.setEditable(false);
		textPaneAccEparpPetit.setContentType("text/html");
		textPaneAccEparpPetit.setAlignmentX(0.5f);
		textPaneAccEparpPetit.setBounds(6, 31, 125, 34);
		panelBordureAccEparpPetit.add(textPaneAccEparpPetit);

		panelBordureForcesEparpPetit = new JPanel();
		panelBordureForcesEparpPetit.setBounds(6, 256, 137, 78);
		panelDonneTirEparpPetit.add(panelBordureForcesEparpPetit);
		panelBordureForcesEparpPetit.setLayout(null);
		panelBordureForcesEparpPetit.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Forces appliqués sur le petit boulet éparpillé \n(N)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textPaneForcesEparpPetit = new JTextPane();
		textPaneForcesEparpPetit.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneForcesEparpPetit.setEditable(false);
		textPaneForcesEparpPetit.setContentType("text/html");
		textPaneForcesEparpPetit.setAlignmentX(0.5f);
		textPaneForcesEparpPetit.setBounds(6, 33, 125, 32);
		panelBordureForcesEparpPetit.add(textPaneForcesEparpPetit);

		panelDonneTirEparpMoyen = new JPanel();
		panelDonneTirEparpMoyen.setLayout(null);
		panelDonneTirEparpMoyen.setBorder(new TitledBorder(null, "Donnés du tir éparpillé (boulet moyen)",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDonneTirEparpMoyen.setBounds(1130, 203, 149, 340);
		add(panelDonneTirEparpMoyen);

		panelBordurePosEparpMoyen = new JPanel();
		panelBordurePosEparpMoyen.setLayout(null);
		panelBordurePosEparpMoyen.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center><dynamic>" + "Position du moyen boulet éparpillé \n(m)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordurePosEparpMoyen.setBounds(6, 18, 137, 78);
		panelDonneTirEparpMoyen.add(panelBordurePosEparpMoyen);

		textPanePosEparpMoyen = new JTextPane();
		textPanePosEparpMoyen.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPanePosEparpMoyen.setEditable(false);
		textPanePosEparpMoyen.setContentType("text/html");
		textPanePosEparpMoyen.setAlignmentX(0.5f);
		textPanePosEparpMoyen.setBounds(6, 32, 125, 33);
		panelBordurePosEparpMoyen.add(textPanePosEparpMoyen);

		panelBordureVitEparpMoyen = new JPanel();
		panelBordureVitEparpMoyen.setLayout(null);
		panelBordureVitEparpMoyen.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Vitesse du moyen boulet éparpillé \n(m/s)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureVitEparpMoyen.setBounds(6, 98, 137, 78);
		panelDonneTirEparpMoyen.add(panelBordureVitEparpMoyen);

		textPaneVitEparpMoyen = new JTextPane();
		textPaneVitEparpMoyen.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneVitEparpMoyen.setEditable(false);
		textPaneVitEparpMoyen.setContentType("text/html");
		textPaneVitEparpMoyen.setAlignmentX(0.5f);
		textPaneVitEparpMoyen.setBounds(6, 30, 125, 35);
		panelBordureVitEparpMoyen.add(textPaneVitEparpMoyen);

		panelBordureAccEparpMoyen = new JPanel();
		panelBordureAccEparpMoyen.setLayout(null);
		panelBordureAccEparpMoyen.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Accelération du moyen boulet éparpillé \n(m/s^2)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureAccEparpMoyen.setBounds(6, 176, 137, 78);
		panelDonneTirEparpMoyen.add(panelBordureAccEparpMoyen);

		textPaneAccEparpMoyen = new JTextPane();
		textPaneAccEparpMoyen.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneAccEparpMoyen.setEditable(false);
		textPaneAccEparpMoyen.setContentType("text/html");
		textPaneAccEparpMoyen.setAlignmentX(0.5f);
		textPaneAccEparpMoyen.setBounds(6, 31, 125, 34);
		panelBordureAccEparpMoyen.add(textPaneAccEparpMoyen);

		panelBordureForcesEparpMoyen = new JPanel();
		panelBordureForcesEparpMoyen.setLayout(null);
		panelBordureForcesEparpMoyen.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Forces appliqués sur le moyen boulet éparpillé \n(N)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureForcesEparpMoyen.setBounds(6, 256, 137, 78);
		panelDonneTirEparpMoyen.add(panelBordureForcesEparpMoyen);

		textPaneForcesEparpMoyen = new JTextPane();
		textPaneForcesEparpMoyen.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneForcesEparpMoyen.setEditable(false);
		textPaneForcesEparpMoyen.setContentType("text/html");
		textPaneForcesEparpMoyen.setAlignmentX(0.5f);
		textPaneForcesEparpMoyen.setBounds(6, 33, 125, 32);
		panelBordureForcesEparpMoyen.add(textPaneForcesEparpMoyen);

		panelDonneTirEparpGrand = new JPanel();
		panelDonneTirEparpGrand.setLayout(null);
		panelDonneTirEparpGrand.setBorder(new TitledBorder(null, "Donnés du tir éparpillé (gros)", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelDonneTirEparpGrand.setBounds(1130, 203, 149, 340);
		add(panelDonneTirEparpGrand);

		panelBordurePosEparpGrand = new JPanel();
		panelBordurePosEparpGrand.setLayout(null);
		panelBordurePosEparpGrand.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center><dynamic>" + "Position du gros boulet éparpillé \n(m)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordurePosEparpGrand.setBounds(6, 18, 137, 78);
		panelDonneTirEparpGrand.add(panelBordurePosEparpGrand);

		textPanePosEparpGrand = new JTextPane();
		textPanePosEparpGrand.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPanePosEparpGrand.setEditable(false);
		textPanePosEparpGrand.setContentType("text/html");
		textPanePosEparpGrand.setAlignmentX(0.5f);
		textPanePosEparpGrand.setBounds(6, 32, 125, 33);
		panelBordurePosEparpGrand.add(textPanePosEparpGrand);

		panelBordureVitEparpGrand = new JPanel();
		panelBordureVitEparpGrand.setLayout(null);
		panelBordureVitEparpGrand.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Vitesse du gros boulet éparpillé \n(m/s)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureVitEparpGrand.setBounds(6, 98, 137, 78);
		panelDonneTirEparpGrand.add(panelBordureVitEparpGrand);

		textPaneVitEparpGrand = new JTextPane();
		textPaneVitEparpGrand.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneVitEparpGrand.setEditable(false);
		textPaneVitEparpGrand.setContentType("text/html");
		textPaneVitEparpGrand.setAlignmentX(0.5f);
		textPaneVitEparpGrand.setBounds(6, 30, 125, 35);
		panelBordureVitEparpGrand.add(textPaneVitEparpGrand);

		panelBordureAccEparpGrand = new JPanel();
		panelBordureAccEparpGrand.setLayout(null);
		panelBordureAccEparpGrand.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Accelération du gros boulet éparpillé \n(m/s^2)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureAccEparpGrand.setBounds(6, 176, 137, 78);
		panelDonneTirEparpGrand.add(panelBordureAccEparpGrand);

		textPaneAccEparpGrand = new JTextPane();
		textPaneAccEparpGrand.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneAccEparpGrand.setEditable(false);
		textPaneAccEparpGrand.setContentType("text/html");
		textPaneAccEparpGrand.setAlignmentX(0.5f);
		textPaneAccEparpGrand.setBounds(6, 31, 125, 34);
		panelBordureAccEparpGrand.add(textPaneAccEparpGrand);

		panelBordureForcesEparpGrand = new JPanel();
		panelBordureForcesEparpGrand.setLayout(null);
		panelBordureForcesEparpGrand.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"<html><font size = '2'><center>" + "Forces appliqués sur le gros boulet éparpillé \n(N)"

						+ "</center></font></html>",

				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureForcesEparpGrand.setBounds(6, 256, 137, 78);
		panelDonneTirEparpGrand.add(panelBordureForcesEparpGrand);

		textPaneForcesEparpGrand = new JTextPane();
		textPaneForcesEparpGrand.setText("<html><font color='red'><center><dynamic></center></font></html>");
		textPaneForcesEparpGrand.setEditable(false);
		textPaneForcesEparpGrand.setContentType("text/html");
		textPaneForcesEparpGrand.setAlignmentX(0.5f);
		textPaneForcesEparpGrand.setBounds(6, 33, 125, 32);
		panelBordureForcesEparpGrand.add(textPaneForcesEparpGrand);

		lblNbRestantTirRebond = new JLabel("Nombre restant de tir rebondissant:");
		lblNbRestantTirRebond.setBounds(41, 476, 224, 16);
		add(lblNbRestantTirRebond);

		lblNbRestantTirEparpille = new JLabel("Nombre restant de tir éparpillé:");
		lblNbRestantTirEparpille.setBounds(41, 458, 224, 16);
		add(lblNbRestantTirEparpille);

		terrain = new Terrain(0, largeurDuComposantEnMetres, -getHeight() / largeurDuComposantEnMetres,
				nbSegmentsPourApproximer, gravite, pixelsParMetre);

		trajectoireProjectile = new TrajectoireProjectile(xTank, yTank, -angleTir, gravite, vitesseInit,
				pixelsParMetre);

		chargerLesDonnees();

		charAssaut = new Tank(xTank, yTank, largeurDuTank, hauteurDuTank, nbPointDeVieJoueur, carburantJoueur,
				nbBouclierJoueur, 3, pixelsParMetre);

		char_ennemi = new TankEnnemi(xTankEnnemi, yTankEnnemi, largeurDuTank, hauteurDuTank, nbPointDeVieEnnemi,
				carburantEnnemi, nbBouclierEnnemi, 3, pixelsParMetre);

		vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
				((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);

		bouletCanonNormal = new BouletCanonNormal(posTank, vitesse, acceleration, sommeDesForces, masseJoueur, deltaT,
				diametre, angleTir, vitesseInit, pixelsParMetre);

		canon = new Canon(xCanon, yCanon, largeurCanon, longueurCanon, angleTir, pixelsParMetre);

		canonEnnemi = new Canon(xCanonEnnemi, yCanonEnnemi, largeurCanon, longueurCanon, angleTirEnnemi,
				pixelsParMetre);
		vitesseEnnemi = new SVector3d(((Math.cos(angleTirEnnemi * Math.PI / 180)) * vitesseInit),
				((Math.sin(angleTirEnnemi * Math.PI / 180)) * vitesseInit * -1), 0);
		bouletCanonEnnemi = new BouletCanonNormal(posTankEnnemi, vitesseEnnemi, acceleration, sommeDesForces,
				masseEnnemi, deltaT, diametre, angleTir, vitesseInit, pixelsParMetre);
		adjustTankOnCurve();
		carburantJoueur = charAssaut.getCarburant();
		carburantEnnemi = char_ennemi.getCarburant();

		barreDeVieEtBouclierJoueur = new BarreDeVieEtBouclier(nbPointDeVieJoueur, nbBouclierJoueur, xPointDeVieJoueur,
				yPointDeVieJoueur, xBouclierJoueur, yBouclierJoueur, largeurBarreVieBouclier, pixelsParMetre);
		barreDeVieEtBouclierEnnemi = new BarreDeVieEtBouclier(nbPointDeVieEnnemi, nbBouclierEnnemi, xPointDeVieEnnemi,
				yPointDeVieEnnemi, xBouclierEnnemi, yBouclierEnnemi, largeurBarreVieBouclier, pixelsParMetre);

		bouletCanonEparpilleGrand = new BouletCanonNormal(posTank, vitesse, acceleration, sommeDesForces, masseJoueur,
				deltaT, diametre + 0.1, angleTir, vitesseInit, pixelsParMetre);
		bouletCanonEparpillePetit = new BouletCanonNormal(posTank, vitesse, acceleration, sommeDesForces, masseJoueur,
				deltaT, diametre - 0.1, angleTir, vitesseInit, pixelsParMetre);
		bouletCanonEparpilleMoyen = new BouletCanonNormal(posTank, vitesse, acceleration, sommeDesForces, masseJoueur,
				deltaT, diametre, angleTir, vitesseInit, pixelsParMetre);

		bouletCanonRebond = new BouletCanonNormal(posTank, vitesse, acceleration, sommeDesForces, masseJoueur, deltaT,
				diametre, angleTir, vitesseInit, pixelsParMetre);

		jaugeCarburant = new JaugeCarburant(carburantJoueur,205,570,carburantJoueur);
		menuFinJeuPropChange();
		repaint();
	}// fin constructeur

	/**
	 * On redéfinit la methode de dessin
	 * 
	 * @param g Le contexte graphique
	 */
	// Michel Vuu
	@Override
	public void paintComponent(Graphics g) {
		donnesTank = GestionnaireDesDonnes.getInstance().getDonnesTank();

		super.paintComponent(g);
		g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (premiereFois) {
			pixelsParMetre = xMaxSeparator / largeurDuComposantEnMetres;
			hauteurDuComposantEnMetres = getHeight() / pixelsParMetre;
			terrain.setPixelsParMetre(pixelsParMetre);
			terrain.setxMin(x);
			terrain.setxMax(largeurDuComposantEnMetres);
			charAssaut.setPixelsParMetre(pixelsParMetre);
			bouletCanonNormal.setPixelsParMetre(pixelsParMetre);
			bouletCanonEparpilleGrand.setPixelsParMetre(pixelsParMetre);
			bouletCanonEparpilleMoyen.setPixelsParMetre(pixelsParMetre);
			bouletCanonEparpillePetit.setPixelsParMetre(pixelsParMetre);
			bouletCanonRebond.setPixelsParMetre(pixelsParMetre);
			canon.setPixelsParMetre(pixelsParMetre);
			char_ennemi.setPixelsParMetre(pixelsParMetre);
			bouletCanonEnnemi.setPixelsParMetre(pixelsParMetre);
			canonEnnemi.setPixelsParMetre(pixelsParMetre);
			trajectoireProjectile.setPixelsParMetre(pixelsParMetre);
			barreDeVieEtBouclierJoueur.setPixelsParMetre(pixelsParMetre);
			barreDeVieEtBouclierEnnemi.setPixelsParMetre(pixelsParMetre);
			premiereFois = false;
			donnesTank.setPremiereFois(premiereFois);
			amelioApparition();
			reini();
			chargerLesDonnees();
		} // fin si
		terrain.dessiner(g2d);
		adjustTankOnCurve();
		charAssaut.dessiner(g2d);

		canon.dessiner(g2d);
		actuEtiquettes();

		char_ennemi.dessiner(g2d);

		canonEnnemi.dessiner(g2d);

		barreDeVieEtBouclierJoueur.dessiner(g2d);
		barreDeVieEtBouclierEnnemi.dessiner(g2d);

		adjustTankOnCurve();
		dessinTrajectoireProjectile();

		g2d.setColor(Color.black);
		g2d.setFont(AppPrincipale7.interBold.deriveFont(36f));
		g2d.drawString("Tour: " + nbTour,(int)(xMaxSeparator/2), 60);

		Path2D.Double echelle = creerEchelle();
		g2d.setColor(Color.magenta);
		g2d.draw(echelle);

		g2d.setColor(Color.black);
		amelioApparition();

		jaugeCarburant.dessiner(g2d);

		if (tirNormalSelectionne) {
			g2d.setColor(Color.cyan);
			bouletCanonNormal.dessiner(g2d);
		}
		if (tirEparpilleSelectionne && nbTirEparp > 0) {
			g2d.setColor(new Color(148,0,211));
			bouletCanonEparpilleGrand.dessiner(g2d);
			bouletCanonEparpilleMoyen.dessiner(g2d);
			bouletCanonEparpillePetit.dessiner(g2d);
		}
		if (tirRebondSelectionne && nbTirRebond > 0) {
			g2d.setColor(Color.pink);
			bouletCanonRebond.dessiner(g2d);
		}
		if (etatTire) {
			if (tourJoueur) {
				if (tirNormalSelectionne)
					tirDuJoueurNormal();
				else if (tirEparpilleSelectionne && nbTirEparp > 0)
					tirDuJoueurEparp();
				else if (tirRebondSelectionne && nbTirRebond > 0)
					tirDuJoueurRebond();

			}
			if (!tourJoueur) {
				
				g2d.setColor(Color.red);
				bouletCanonEnnemi.dessiner(g2d);
				tirEnnemi();
			} // fin si
		} // fin si

	}// fin méthode

	/**
	 * Méthode qui créer l'échelle pour estimer le nombre de mètre que possède le
	 * composant
	 * 
	 * @return un Path2D qui représente le nombre de pixel par mètre
	 */
	// Jason Yin
	private Path2D.Double creerEchelle() {
		Path2D.Double echelle = new Path2D.Double();
		echelle.moveTo(xMaxSeparator - 30, 538);
		echelle.lineTo(xMaxSeparator - 30, 545);
		echelle.lineTo(xMaxSeparator - (30 + pixelsParMetre), 545);
		echelle.lineTo(xMaxSeparator - (30 + pixelsParMetre), 538);
		echelle.lineTo(xMaxSeparator - (30 + pixelsParMetre), 545);
		echelle.lineTo(xMaxSeparator - (30 + 2 * pixelsParMetre), 545);
		echelle.lineTo(xMaxSeparator - (30 + 2 * pixelsParMetre), 538);
		echelle.lineTo(xMaxSeparator - (30 + 2 * pixelsParMetre), 545);
		echelle.lineTo(xMaxSeparator - (30 + 3 * pixelsParMetre), 545);
		echelle.lineTo(xMaxSeparator - (30 + 3 * pixelsParMetre), 538);
		echelle.lineTo(xMaxSeparator - (30 + 3 * pixelsParMetre), 545);
		echelle.lineTo(xMaxSeparator - (30 + 4 * pixelsParMetre), 545);
		echelle.lineTo(xMaxSeparator - (30 + 4 * pixelsParMetre), 538);
		echelle.lineTo(xMaxSeparator - (30 + 4 * pixelsParMetre), 545);
		return echelle;
	}// fin méthode

	/**
	 * Méthode qui permet de calculer le temps écoulé
	 * 
	 * @param panelOuvert booléen qui détermine si le panel est ouvert ou non
	 */
	// Liangchen Liu
	public void calculerTempsEcoule(boolean panelOuvert) {
		if (panelOuvert) {
			classeTemps.demarrer();
			classeTemps.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					switch (evt.getPropertyName()) {
					case "temps":
						lblTemps.setText("" + evt.getNewValue());

						break;

					case "tempsEcoule":

						tempsEcoule = (long) evt.getNewValue();

						break;

					}// fin switch
				}// fin propertyChange
			});

		} else {
			classeTemps.reiniTemps();
			classeTemps.arreter();

		}

	}

	/**
	 * Méthode pour gérer les déplacements du char en réponse aux touches de clavier
	 * appuyées
	 * 
	 * @param e L'évènement de clavier
	 */
	// Michel Vuu
	private void deplacementChar(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_RIGHT:

			if ((x2 + deplacement) < largeurDuComposantEnMetres && carburantJoueur > 0) {
				x1 += deplacement;
				x2 += deplacement;
				trajectoireProjectile = new TrajectoireProjectile(x1, yTank, -angleTir, gravite, vitesseInit,
						pixelsParMetre);

				xPointDeVieJoueur = xTank;
				yPointDeVieJoueur = yTank - espaceEntreTankEtBarre;
				xBouclierJoueur = xTank;
				yBouclierJoueur = yTank - espaceEntreTankEtBarre;
				barreDeVieEtBouclierJoueur = new BarreDeVieEtBouclier(nbPointDeVieJoueur, nbBouclierJoueur,
						xPointDeVieJoueur, yPointDeVieJoueur, xBouclierJoueur, yBouclierJoueur, largeurBarreVieBouclier,
						pixelsParMetre);
				donnesTank.setX1(x1);
				donnesTank.setX2(x2);
				donnesTank.setxPointDeVieJoueur(xPointDeVieJoueur);
				donnesTank.setyPointDeVieJoueur(yPointDeVieJoueur);
				donnesTank.setxBouclierJoueur(xBouclierJoueur);
				donnesTank.setyBouclierJoueur(yBouclierJoueur);

				firePropertyChange("x1", null, x1);
				firePropertyChange("x2", null, x2);
				gereurCarburant();
				amelioTouche();
			} // fin si
			break;
		case KeyEvent.VK_LEFT:

			if ((x1 - deplacement) > 0 && carburantJoueur > 0) {
				x1 -= deplacement;
				x2 -= deplacement;

				trajectoireProjectile = new TrajectoireProjectile(x1, yTank, -angleTir, gravite, vitesseInit,
						pixelsParMetre);
				xPointDeVieJoueur = xTank;
				yPointDeVieJoueur = yTank - espaceEntreTankEtBarre;
				xBouclierJoueur = xTank;
				yBouclierJoueur = yTank - espaceEntreTankEtBarre;
				barreDeVieEtBouclierJoueur = new BarreDeVieEtBouclier(nbPointDeVieJoueur, nbBouclierJoueur,
						xPointDeVieJoueur, yPointDeVieJoueur, xBouclierJoueur, yBouclierJoueur, largeurBarreVieBouclier,
						pixelsParMetre);
				donnesTank.setX1(x1);
				donnesTank.setX2(x2);
				donnesTank.setX1(x1);
				donnesTank.setX2(x2);
				donnesTank.setxPointDeVieJoueur(xPointDeVieJoueur);
				donnesTank.setyPointDeVieJoueur(yPointDeVieJoueur);
				donnesTank.setxBouclierJoueur(xBouclierJoueur);
				donnesTank.setyBouclierJoueur(yBouclierJoueur);

				firePropertyChange("x1", null, x1);
				firePropertyChange("x2", null, x2);

				gereurCarburant();
				amelioTouche();

			} // fin si
			break;
		case KeyEvent.VK_UP:

			changerAngleTirHaut();
			break;

		case KeyEvent.VK_DOWN:
			changerAngleTirBas();
			break;
		case KeyEvent.VK_SPACE:
			if (tourJoueur || (tirEparpilleSelectionne && nbTirEparp != 0)
					|| (tirRebondSelectionne && nbTirRebond != 0)) {
				etatTire = true;
				spnVitesse.setEnabled(false);
				spnmasseJoueur.setEnabled(false);
				cboTypeTerrain.setEnabled(false);
				cboTypeTirs.setEnabled(false);
				demarrer();
			} // fin si

		}// fin switch
	}// fin méthode

	/**
	 * Méthode qui effectue un mouvement de rotation vers le haut sur le canon et le
	 * boulet
	 * 
	 */
	// Jason Yin
	private void changerAngleTirHaut() {
		angleTir += 5;
		if (angleTir < 80) {
			canon.setAngle(angleTir);
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			if (tirNormalSelectionne)
				bouletCanonNormal.setVitesse(vitesse);
			if (tirEparpilleSelectionne) {
				bouletCanonEparpillePetit.setVitesse(vitesse.add(ajustEparpPetit));
				bouletCanonEparpilleGrand.setVitesse(vitesse.add(ajustEparpGrand));
				bouletCanonEparpilleMoyen.setVitesse(vitesse);
			}
			if (tirRebondSelectionne) {
				bouletCanonRebond.setVitesse(vitesse);
			}
		} // fin if
		else {
			angleTir = 80;
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			if (tirNormalSelectionne)
				bouletCanonNormal.setVitesse(vitesse);
			if (tirEparpilleSelectionne) {
				bouletCanonEparpillePetit.setVitesse(vitesse.add(ajustEparpPetit));
				bouletCanonEparpilleGrand.setVitesse(vitesse.add(ajustEparpGrand));
				bouletCanonEparpilleMoyen.setVitesse(vitesse);
			}
			if (tirRebondSelectionne) {
				bouletCanonRebond.setVitesse(vitesse);
			}
			canon.setAngle(angleTir);
		} // fin else
		trajectoireProjectile = new TrajectoireProjectile(xTank, yTank, -angleTir, gravite, vitesseInit,
				pixelsParMetre);
		donnesTank.setAngleTir(angleTir);
	}// fin méthode

	/**
	 * Méthode qui effectue un mouvement de rotation vers le bas sur le canon et le
	 * boulet
	 * 
	 */
	// Jason Yin
	private void changerAngleTirBas() {
		angleTir -= 5;
		if (angleTir > 20) {
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			if (tirNormalSelectionne)
				bouletCanonNormal.setVitesse(vitesse);
			if (tirEparpilleSelectionne) {
				bouletCanonEparpillePetit.setVitesse(vitesse.add(ajustEparpPetit));
				bouletCanonEparpilleGrand.setVitesse(vitesse.add(ajustEparpGrand));
				bouletCanonEparpilleMoyen.setVitesse(vitesse);
			}
			if (tirRebondSelectionne) {
				bouletCanonRebond.setVitesse(vitesse);
			}
			canon.setAngle(angleTir);
		} // fin if
		else {
			angleTir = 20;
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			if (tirNormalSelectionne)
				bouletCanonNormal.setVitesse(vitesse);
			if (tirEparpilleSelectionne) {
				bouletCanonEparpillePetit.setVitesse(vitesse.add(ajustEparpPetit));
				bouletCanonEparpilleGrand.setVitesse(vitesse.add(ajustEparpGrand));
				bouletCanonEparpilleMoyen.setVitesse(vitesse);
			}
			if (tirRebondSelectionne) {
				bouletCanonRebond.setVitesse(vitesse);
			}
			canon.setAngle(angleTir);
		} // fin else
		trajectoireProjectile = new TrajectoireProjectile(xTank, yTank, -angleTir, gravite, vitesseInit,
				pixelsParMetre);
		donnesTank.setAngleTir(angleTir);
	}// fin méthode

	/**
	 * Méthode qui effectue l'animation du projectile
	 */
	// Jason Yin
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (enCoursDAnimation) {
			calculerUneIterationPhysique(deltaT);

			try {
				Thread.sleep(tempsDuSleep);

			} catch (InterruptedException e) {
				e.printStackTrace();
			} // fin try-catch
		} // fin while
		System.out.println("Le thread est mort...!");
	}// fin méthode

	/**
	 * Demarre le thread s'il n'est pas deja demarre
	 */
	// Caroline Houle
	public void demarrer() {
		if (!enCoursDAnimation) {
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
			etatTire = true;
		} // fin if
	}// fin methode

	/**
	 * Calcul des nouvelles positions pour tous les objets de la scène
	 * 
	 * @param deltaT L'intervalle de temps
	 */
	// Jason Yin
	private void calculerUneIterationPhysique(double deltaT) {
		tempsTotal += deltaT;
		if (tourJoueur && tirNormalSelectionne) {
			bouletCanonNormal.avancerUnPas(deltaT);
		}
		if (tourJoueur && tirEparpilleSelectionne) {
			if (!arretTirEparpGrand)
				bouletCanonEparpilleGrand.avancerUnPas(deltaT);
			if (!arretTirEparpPetit)
				bouletCanonEparpillePetit.avancerUnPas(deltaT);
			if (!arretTirEparpMoyen)
				bouletCanonEparpilleMoyen.avancerUnPas(deltaT);
		}
		if (tourJoueur && tirRebondSelectionne) {
			bouletCanonRebond.avancerUnPas(deltaT);
		}
		if (!tourJoueur) {
			bouletCanonEnnemi.avancerUnPas(deltaT);
		}
		repaint();
	}// fin méthode

	/**
	 * Méthode qui dessine la vitesse du projectile du joueur sous forme de flèche
	 * vectorielle
	 */
	// Jason Yin
	private void dessinerFlecheVecJoueur() {
			if(tirNormalSelectionne){
			bouletCanonNormal.setEtatTire(etatTire);
			bouletCanonNormal.setVecVitesseSelectionne(afficherFlecheVitesse);
			bouletCanonNormal.setVecAccelerationSelectionne(afficherFlecheAccel);
			bouletCanonNormal.setVecSommeForceSelectionne(afficherFlecheForce);
			}
			else if(tirRebondSelectionne){
			bouletCanonRebond.setEtatTire(etatTire);
			bouletCanonRebond.setVecVitesseSelectionne(afficherFlecheVitesse);
			bouletCanonRebond.setVecAccelerationSelectionne(afficherFlecheAccel);	
			bouletCanonRebond.setVecSommeForceSelectionne(afficherFlecheForce);
			}
			else if(tirEparpilleSelectionne){
			bouletCanonEparpilleGrand.setEtatTire(etatTire);
			bouletCanonEparpilleMoyen.setEtatTire(etatTire);
			bouletCanonEparpillePetit.setEtatTire(etatTire);
			bouletCanonEparpillePetit.setVecVitesseSelectionne(afficherFlecheVitesse);
			bouletCanonEparpilleGrand.setVecVitesseSelectionne(afficherFlecheVitesse);
			bouletCanonEparpilleMoyen.setVecVitesseSelectionne(afficherFlecheVitesse);
			bouletCanonEparpillePetit.setVecAccelerationSelectionne(afficherFlecheAccel);
			bouletCanonEparpilleGrand.setVecAccelerationSelectionne(afficherFlecheAccel);
			bouletCanonEparpilleMoyen.setVecAccelerationSelectionne(afficherFlecheAccel);
			bouletCanonEparpillePetit.setVecSommeForceSelectionne(afficherFlecheForce);
			bouletCanonEparpilleGrand.setVecSommeForceSelectionne(afficherFlecheForce);
			bouletCanonEparpilleMoyen.setVecSommeForceSelectionne(afficherFlecheForce);
			}
	}

	/**
	 * Méthode qui dessine la vitesse du projectile de l'ennemi sous forme de flèche
	 * vectorielle
	 */
	// Jason Yin
	private void dessinerFlecheVecEnnemi() {

		bouletCanonEnnemi.setEtatTire(etatTire);
		if (afficherFlecheVitesse) {
			bouletCanonEnnemi.setVecVitesseSelectionne(afficherFlecheVitesse);
			bouletCanonEnnemi.setVecAccelerationSelectionne(afficherFlecheAccel);
			bouletCanonEnnemi.setVecSommeForceSelectionne(afficherFlecheForce);
		} else if (afficherFlecheAccel) {
			bouletCanonEnnemi.setVecVitesseSelectionne(afficherFlecheVitesse);
			bouletCanonEnnemi.setVecAccelerationSelectionne(afficherFlecheAccel);
			bouletCanonEnnemi.setVecSommeForceSelectionne(afficherFlecheForce);
		} else if (afficherFlecheForce) {
			bouletCanonEnnemi.setVecVitesseSelectionne(afficherFlecheVitesse);
			bouletCanonEnnemi.setVecAccelerationSelectionne(afficherFlecheAccel);
			bouletCanonEnnemi.setVecSommeForceSelectionne(afficherFlecheForce);
		}
	
	}

	/**
	 * Demande l'arret du thread (prochain tour de boucle)
	 */
	// Caroline Houle
	public void arreter() {
		enCoursDAnimation = false;
		gereurTour();
	}// fin methode

	/**
	 * Ajuste la position du tank pour que ce dernier soit collé sur la courbe
	 */
	// Ismaïl Boukari
	private void adjustTankOnCurve() {
		y1 = terrain.evalFonction(x1);
		y2 = terrain.evalFonction(x2);

		SVector3d p1p2 = new SVector3d(x2 - x1, y2 - y1, 0).normalize(); // Vecteur unitaire du point (x1,y1) au point
		// (x2,y2)
		if (p1p2.getY() > 0) { // Pente positive
			theta = SVector3d.angleBetween(new SVector3d(1, 0, 0), p1p2);

			firePropertyChange("theta", null, theta);
		} else { // Pente negative ou nulle
			theta = -SVector3d.angleBetween(new SVector3d(1, 0, 0), p1p2);
			firePropertyChange("theta", null, theta);
		} // fin if-else

		xTank = x1 + (largeurDuTank * Math.sin(theta)); // un peu de trigonométrie (hauteur)
		yTank = y1 - (hauteurDuTank * Math.cos(theta)); // un peu de trigonométrie (hauteur)

		donnesTank.setTheta(theta);
		donnesTank.setXTank(xTank);
		donnesTank.setYTank(yTank);

		firePropertyChange("xTank", null, xTank);
		firePropertyChange("yTank", null, yTank);
		charAssaut.setAngleOfRotation(theta);
		charAssaut.setX(xTank);
		charAssaut.setY(yTank);
		posTank = new SVector3d(charAssaut.getX(), charAssaut.getY(), 0);
		canon.setAngle(theta - angleTir * (Math.PI / 180));
		canon.setX(xTank);
		canon.setY(yTank);
		if (!etatTire) {
			if (tirNormalSelectionne)
				bouletCanonNormal.setPosition(posTank);
			if (tirEparpilleSelectionne) {
				bouletCanonEparpillePetit.setPosition(posTank);
				bouletCanonEparpilleMoyen.setPosition(posTank);
				bouletCanonEparpilleGrand.setPosition(posTank);
			}
			if (tirRebondSelectionne) {
				bouletCanonRebond.setPosition(posTank);
			}

		} // fin si

		y3 = terrain.evalFonction(x3);
		y4 = terrain.evalFonction(x4);

		SVector3d p3p4 = new SVector3d(x4 - x3, y4 - y3, 0).normalize();

		if (p3p4.getY() > 0) {
			thetaEnnemi = SVector3d.angleBetween(new SVector3d(1, 0, 0), p3p4);

		} else {
			thetaEnnemi = -SVector3d.angleBetween(new SVector3d(1, 0, 0), p3p4);
		} // fin si-sinon

		xTankEnnemi = x3 + (hauteurDuTank * Math.sin(thetaEnnemi));
		yTankEnnemi = y3 - (hauteurDuTank * Math.cos(thetaEnnemi));

		donnesTank.setTheta(thetaEnnemi);
		donnesTank.setXTank(xTankEnnemi);
		donnesTank.setYTank(yTankEnnemi);

		char_ennemi.setAngleOfRotation(thetaEnnemi);
		char_ennemi.setX(xTankEnnemi);
		char_ennemi.setY(yTankEnnemi);

		canonEnnemi.setAngle(thetaEnnemi - angleTirEnnemi * (Math.PI / 180));
		canonEnnemi.setX(xTankEnnemi+largeurDuTank);
		canonEnnemi.setY(yTankEnnemi);
		posTankEnnemi = new SVector3d(canonEnnemi.getX(), char_ennemi.getY(), 0);
		if (!etatTire) {
			bouletCanonEnnemi.setPosition(posTankEnnemi);
		} // fin si

	}// fin méthode

	/**
	 * Méthode pour charger les données de la classe DonneesTank
	 */
	// Liangchen Liu
	private void chargerLesDonnees() {
		if (!donnesTank.isModeTriche()) {
			premiereFois = donnesTank.isPremiereFois();
			xTank = donnesTank.getXTank();
			yTank = donnesTank.getYTank();
			x1 = donnesTank.getX1();
			y1 = donnesTank.getY1();
			x2 = donnesTank.getX2();
			y2 = donnesTank.getY2();
			theta = donnesTank.getTheta();

			xTankEnnemi = donnesTank.getxTankEnnemi();
			yTankEnnemi = donnesTank.getyTankEnnemi();
			x3 = donnesTank.getX3();
			y3 = donnesTank.getY3();
			x4 = donnesTank.getX4();
			y4 = donnesTank.getY4();
			thetaEnnemi = donnesTank.getThetaEnnemi();

			vitesseInit = donnesTank.getVitesseInit();
			spnVitesse.setValue((int) vitesseInit);
			nbTour = donnesTank.getNbTour();
			nbTirEparp = donnesTank.getTirEparp();
			nbTirRebond = donnesTank.getTirRebond();

			carburantBonusEnnemi = donnesTank.getCarburantBonusEnnemi();
			carburantBonusJoueur = donnesTank.getCarburantBonusJoueur();
			carburantEnnemi = donnesTank.getCarburantEnnemi();
			carburantJoueur = donnesTank.getCarburantJoueur();
			lblCarburantJoueur.setText("Niveau de carburant: " + carburantJoueur);

			if (conditionCbo) {
				if (donnesTank.isTirNormalSelectionne()) {
					cboTypeTirs.setSelectedIndex(0);
				}
				if (donnesTank.isTirRebondSelectionne()) {
					cboTypeTirs.setSelectedIndex(1);
				}
				if (donnesTank.isTirEparpilleSelectionne()) {
					cboTypeTirs.setSelectedIndex(2);
				}

				switch (donnesTank.getTypePlaneteTerrain()) {
				case TERRE:
					cboTypeTerrain.setSelectedIndex(0);
					break;
				case MARS:
					cboTypeTerrain.setSelectedIndex(1);
					break;
				case MERCURE:
					cboTypeTerrain.setSelectedIndex(2);
					break;
				case LUNE:
					cboTypeTerrain.setSelectedIndex(3);
					break;

				}

				conditionCbo = false;
			}

			nbPointDeVieJoueur = donnesTank.getNbPointDeVieJoueur();
			nbBouclierJoueur = donnesTank.getNbBouclierJoueur();
			xPointDeVieJoueur = donnesTank.getxPointDeVieJoueur();
			yPointDeVieJoueur = donnesTank.getyPointDeVieJoueur();
			xBouclierJoueur = donnesTank.getxBouclierJoueur();
			yBouclierJoueur = donnesTank.getyBouclierJoueur();
			barreDeVieEtBouclierJoueur = new BarreDeVieEtBouclier(nbPointDeVieJoueur, nbBouclierJoueur,
					xPointDeVieJoueur, yPointDeVieJoueur, xBouclierJoueur, yBouclierJoueur, largeurBarreVieBouclier,
					pixelsParMetre);

			nbPointDeVieEnnemi = donnesTank.getNbPointDeVieEnnemi();
			nbBouclierEnnemi = donnesTank.getNbBouclierEnnemi();
			xPointDeVieEnnemi = donnesTank.getxPointDeVieEnnemi();
			yPointDeVieEnnemi = donnesTank.getyPointDeVieEnnemi();
			xBouclierEnnemi = donnesTank.getxBouclierEnnemi();
			yBouclierEnnemi = donnesTank.getyBouclierEnnemi();
			barreDeVieEtBouclierEnnemi = new BarreDeVieEtBouclier(nbPointDeVieEnnemi, nbBouclierEnnemi,
					xPointDeVieEnnemi, yPointDeVieEnnemi, xBouclierEnnemi, yBouclierEnnemi, largeurBarreVieBouclier,
					pixelsParMetre);

		} else {
			premiereFois = donnesTank.isPremiereFois();
			xTank = donnesTank.getXTank();
			yTank = donnesTank.getYTank();
			x1 = donnesTank.getX1();
			y1 = donnesTank.getY1();
			x2 = donnesTank.getX2();
			y2 = donnesTank.getY2();
			theta = donnesTank.getTheta();

			xTankEnnemi = donnesTank.getxTankEnnemi();
			yTankEnnemi = donnesTank.getyTankEnnemi();
			x3 = donnesTank.getX3();
			y3 = donnesTank.getY3();
			x4 = donnesTank.getX4();
			y4 = donnesTank.getY4();
			thetaEnnemi = donnesTank.getThetaEnnemi();

			vitesseInit = donnesTank.getVitesseInit();
			spnVitesse.setValue((int) vitesseInit);
			nbTour = donnesTank.getNbTour();
			nbTirEparp = donnesTank.getTirEparpT();
			nbTirRebond = donnesTank.getTirRebondT();

			carburantBonusEnnemi = donnesTank.getCarburantBonusEnnemi();
			carburantBonusJoueur = donnesTank.getCarburantBonusJoueur();
			carburantEnnemi = donnesTank.getCarburantEnnemi();
			carburantJoueur = donnesTank.getCarburantJoueurT();
			lblCarburantJoueur.setText("Niveau de carburant: " + carburantJoueur);

			if (conditionCbo) {
				if (donnesTank.isTirNormalSelectionne()) {
					cboTypeTirs.setSelectedIndex(0);
				}
				if (donnesTank.isTirRebondSelectionne()) {
					cboTypeTirs.setSelectedIndex(1);
				}
				if (donnesTank.isTirEparpilleSelectionne()) {
					cboTypeTirs.setSelectedIndex(2);
				}

				switch (donnesTank.getTypePlaneteTerrain()) {
				case TERRE:
					cboTypeTerrain.setSelectedIndex(0);
					break;
				case MARS:
					cboTypeTerrain.setSelectedIndex(1);
					break;
				case MERCURE:
					cboTypeTerrain.setSelectedIndex(2);
					break;
				case LUNE:
					cboTypeTerrain.setSelectedIndex(3);
					break;

				}

				conditionCbo = false;
			}

			nbPointDeVieJoueur = donnesTank.getNbPointDeVieJoueur();
			nbBouclierJoueur = donnesTank.getNbBouclierJoueur();
			xPointDeVieJoueur = donnesTank.getxPointDeVieJoueur();
			yPointDeVieJoueur = donnesTank.getyPointDeVieJoueur();
			xBouclierJoueur = donnesTank.getxBouclierJoueur();
			yBouclierJoueur = donnesTank.getyBouclierJoueur();
			barreDeVieEtBouclierJoueur = new BarreDeVieEtBouclier(nbPointDeVieJoueur, nbBouclierJoueur,
					xPointDeVieJoueur, yPointDeVieJoueur, xBouclierJoueur, yBouclierJoueur, largeurBarreVieBouclier,
					pixelsParMetre);

			nbPointDeVieEnnemi = donnesTank.getNbPointDeVieEnnemiT();
			nbBouclierEnnemi = donnesTank.getNbBouclierEnnemiT();
			xPointDeVieEnnemi = donnesTank.getxPointDeVieEnnemi();
			yPointDeVieEnnemi = donnesTank.getyPointDeVieEnnemi();
			xBouclierEnnemi = donnesTank.getxBouclierEnnemi();
			yBouclierEnnemi = donnesTank.getyBouclierEnnemi();
			barreDeVieEtBouclierEnnemi = new BarreDeVieEtBouclier(nbPointDeVieEnnemi, nbBouclierEnnemi,
					xPointDeVieEnnemi, yPointDeVieEnnemi, xBouclierEnnemi, yBouclierEnnemi, largeurBarreVieBouclier,
					pixelsParMetre);

		}

	}// fin méthode

	/**
	 * Méthode pour mettre à jour les étiquettes de données scientifiques
	 */
	// Jason Yin
	private void actuEtiquettes() {

		textPanePositionTankJoueur.setText(
				"<html><font color='black'><center>" + charAssaut.positionToString() + "</center></font></html>");
		textPanePositionTankEnnemi.setText(
				"<html><font color='black'><center>" + char_ennemi.positionToString() + "</center></font></html>");
		lblNbRestantTirRebond.setText("Nombre restant de tir rebondissant:" + nbTirRebond);
		lblNbRestantTirEparpille.setText("Nombre restant de tir éparpillé:" + nbTirEparp);
		lblPointDeVieEnnemi.setText("Point de Vie Ennemi: " + nbPointDeVieEnnemi);
		lblBouclierEnnemi.setText("Point de bouclier Ennemi:" + nbBouclierEnnemi);
		lblCarburantJoueur.setText("Niveau de carburant: " + carburantJoueur);
		lblBouclierJoueur.setText("Point de bouclier Joueur:" + nbBouclierJoueur);
		lblPointDeVieJoueur.setText("Point de Vie Joueur: " + nbPointDeVieJoueur);
		jaugeCarburant.setNbCarburant(carburantJoueur);

		if (tirNormalSelectionne) {
			panelDonneTirRebond.setVisible(false);
			panelDonneTirNormal.setVisible(true);
			panelDonneTirEparpPetit.setVisible(false);
			panelDonneTirEparpMoyen.setVisible(false);
			panelDonneTirEparpGrand.setVisible(false);
			rdbtnDonnePetitBoulet.setVisible(false);
			rdbtnDonneMoyenBoulet.setVisible(false);
			rdbtnDonneGrosBoulet.setVisible(false);

			textPanePosNormal.setText("<html><font color='black'><center>" + bouletCanonNormal.positionToString()
					+ "</center></font></html>");
			textPaneVitNormal.setText("<html><font color='black'><center>" + bouletCanonNormal.vitesseToString()
					+ "</center></font></html>");
			textPaneAccNormal.setText("<html><font color='black'><center>" + bouletCanonNormal.accelToString()
					+ "</center></font></html>");
			textPaneForcesNormal.setText("<html><font color='black'><center>" + bouletCanonNormal.sommeForcesToString()
					+ "</center></font></html>");
		}
		if (tirRebondSelectionne) {

			panelDonneTirNormal.setVisible(false);
			panelDonneTirRebond.setVisible(true);
			panelDonneTirEparpPetit.setVisible(false);
			panelDonneTirEparpMoyen.setVisible(false);
			panelDonneTirEparpGrand.setVisible(false);
			rdbtnDonnePetitBoulet.setVisible(false);
			rdbtnDonneMoyenBoulet.setVisible(false);
			rdbtnDonneGrosBoulet.setVisible(false);

			textPanePosRebond.setText("<html><font color='black'><center>" + bouletCanonRebond.positionToString()
					+ "</center></font></html>");
			textPaneVitRebond.setText("<html><font color='black'><center>" + bouletCanonRebond.vitesseToString()
					+ "</center></font></html>");
			textPaneAccRebond.setText("<html><font color='black'><center>" + bouletCanonRebond.accelToString()
					+ "</center></font></html>");
			textPaneForcesRebond.setText("<html><font color='black'><center>" + bouletCanonRebond.sommeForcesToString()
					+ "</center></font></html>");
		}
		if (tirEparpilleSelectionne) {
			rdbtnDonnePetitBoulet.setVisible(true);
			rdbtnDonneMoyenBoulet.setVisible(true);
			rdbtnDonneGrosBoulet.setVisible(true);
			if (voirDonnePetitBoulet) {
				panelDonneTirNormal.setVisible(false);
				panelDonneTirRebond.setVisible(false);
				panelDonneTirEparpPetit.setVisible(true);
				panelDonneTirEparpMoyen.setVisible(false);
				panelDonneTirEparpGrand.setVisible(false);

				textPanePosEparpPetit.setText("<html><font color='black'><center>"
						+ bouletCanonEparpillePetit.positionToString() + "</center></font></html>");
				textPaneVitEparpPetit.setText("<html><font color='black'><center>"
						+ bouletCanonEparpillePetit.vitesseToString() + "</center></font></html>");
				textPaneAccEparpPetit.setText("<html><font color='black'><center>"
						+ bouletCanonEparpillePetit.accelToString() + "</center></font></html>");
				textPaneForcesEparpPetit.setText("<html><font color='black'><center>"
						+ bouletCanonEparpillePetit.sommeForcesToString() + "</center></font></html>");
			} else if (voirDonneMoyenBoulet) {
				panelDonneTirNormal.setVisible(false);
				panelDonneTirRebond.setVisible(false);
				panelDonneTirEparpPetit.setVisible(false);
				panelDonneTirEparpMoyen.setVisible(true);
				panelDonneTirEparpGrand.setVisible(false);

				textPanePosEparpMoyen.setText("<html><font color='black'><center>"
						+ bouletCanonEparpilleMoyen.positionToString() + "</center></font></html>");
				textPaneVitEparpMoyen.setText("<html><font color='black'><center>"
						+ bouletCanonEparpilleMoyen.vitesseToString() + "</center></font></html>");
				textPaneAccEparpMoyen.setText("<html><font color='black'><center>"
						+ bouletCanonEparpilleMoyen.accelToString() + "</center></font></html>");
				textPaneForcesEparpMoyen.setText("<html><font color='black'><center>"
						+ bouletCanonEparpilleMoyen.sommeForcesToString() + "</center></font></html>");

			} else if (voirDonneGrandBoulet) {
				panelDonneTirNormal.setVisible(false);
				panelDonneTirRebond.setVisible(false);
				panelDonneTirEparpPetit.setVisible(false);
				panelDonneTirEparpMoyen.setVisible(false);
				panelDonneTirEparpGrand.setVisible(true);

				textPanePosEparpGrand.setText("<html><font color='black'><center>"
						+ bouletCanonEparpilleGrand.positionToString() + "</center></font></html>");
				textPaneVitEparpGrand.setText("<html><font color='black'><center>"
						+ bouletCanonEparpilleGrand.vitesseToString() + "</center></font></html>");
				textPaneAccEparpGrand.setText("<html><font color='black'><center>"
						+ bouletCanonEparpilleGrand.accelToString() + "</center></font></html>");
				textPaneForcesEparpGrand.setText("<html><font color='black'><center>"
						+ bouletCanonEparpilleGrand.sommeForcesToString() + "</center></font></html>");

			}
		}
	}// fin méthode

	/**
	 * Méthode pour faire apparaitre les instructions pour le professeur
	 */
	// Liangchen Liu
	private void instProf() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tourJoueur && !etatTire) {
					deplacementChar(e);
					repaint();
				}
				if (e.getKeyCode() == KeyEvent.VK_P) {
					JOptionPane.showMessageDialog(null, instructions, "Fonctionnalités présentes",
							JOptionPane.INFORMATION_MESSAGE);
					requestFocusInWindow();
				} // fin if
			}// fin méthode

		});// fin listener
	}// fin méthode

	/**
	 * Méthode pour faire sauvegarder le niveau lors du clic sur le bouton
	 */
	// Liangchen Liu
	private void sauv() {
		btnSauvegarder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {

				donnesTank.setVitesseInit(vitesseInit);
				donnesTank.setTempsEcoule(tempsEcoule);
				donnesTank.setNbTour(nbTour);
				donnesTank.setCarburantBonusEnnemi(carburantBonusEnnemi);
				donnesTank.setCarburantBonusJoueur(carburantBonusJoueur);
				donnesTank.setCarburantEnnemi(carburantEnnemi);
				donnesTank.setCarburantJoueur(carburantJoueur);
				donnesTank.setTirEparpilleSelectionne(tirEparpilleSelectionne);
				donnesTank.setTirNormalSelectionne(tirNormalSelectionne);
				donnesTank.setTirRebondSelectionne(tirRebondSelectionne);

				switch (cboTypeTerrain.getSelectedIndex()) {
				case 0:
					donnesTank.setTypePlaneteTerrain(TypePlaneteTerrain.TERRE);
					break;

				case 1:
					donnesTank.setTypePlaneteTerrain(TypePlaneteTerrain.MARS);

					break;
				case 2:
					donnesTank.setTypePlaneteTerrain(TypePlaneteTerrain.MERCURE);

					break;
				case 3:
					donnesTank.setTypePlaneteTerrain(TypePlaneteTerrain.LUNE);

					break;

				}

				switch (cboTypeTirs.getSelectedIndex()) {
				case 0:
					donnesTank.setTirNormalSelectionne(tirNormalSelectionne);
					donnesTank.setTirRebondSelectionne(tirRebondSelectionne);
					donnesTank.setTirEparpilleSelectionne(tirEparpilleSelectionne);
					break;

				case 1:
					donnesTank.setTirNormalSelectionne(tirNormalSelectionne);
					donnesTank.setTirRebondSelectionne(tirRebondSelectionne);
					donnesTank.setTirEparpilleSelectionne(tirEparpilleSelectionne);
					break;
				case 2:
					donnesTank.setTirNormalSelectionne(tirNormalSelectionne);
					donnesTank.setTirRebondSelectionne(tirRebondSelectionne);
					donnesTank.setTirEparpilleSelectionne(tirEparpilleSelectionne);
					break;
				}

				GestionnaireDesDonnes.getInstance().setDonnesTank(donnesTank);
				GestionnaireDesDonnes.getInstance().setNiveau(4);
				GestionnaireDesDonnes.getInstance().sauvegarder();

				requestFocusInWindow();
			}// fin méthode
		});// fin listener

	}// fin méthode

	/**
	 * Méthode pour faire réinitialiser les données du niveau
	 */
	// Liangchen Liu
	public void reini() {
		classeTemps.reiniTemps();
		xTank = POSITION_INI_TANK_X;
		yTank = POSITION_INI_TANK_Y;
		x1 = X1;
		x2 = X2;
		y1 = Y1;
		y2 = Y2;
		theta = THETA_INI;

		x3 = X3;
		x4 = X4;

		donnesTank.setXTank(xTank);
		donnesTank.setYTank(yTank);
		donnesTank.setTheta(theta);
		donnesTank.setX1(x1);
		donnesTank.setX2(x2);
		donnesTank.setY1(y1);
		donnesTank.setY2(y2);
		donnesTank.setX3(x3);
		donnesTank.setX4(x4);

		vitesseInit = VITESSE_INITIALE;
		donnesTank.setVitesseInit(vitesseInit);

		nbTour = 0;
		donnesTank.setNbTour(nbTour);

		carburantEnnemi = CARBURANT_INIT;
		carburantJoueur = CARBURANT_INIT;
		carburantBonusEnnemi = CARBURANT_BONUS;
		carburantBonusJoueur = CARBURANT_BONUS;
		donnesTank.setCarburantBonusEnnemi(carburantBonusEnnemi);
		donnesTank.setCarburantBonusJoueur(carburantBonusJoueur);
		donnesTank.setCarburantEnnemi(carburantEnnemi);
		donnesTank.setCarburantJoueur(carburantJoueur);
		lblCarburantJoueur.setText("Niveau de carburant: " + carburantJoueur);

		cboTypeTirs.setSelectedIndex(0);
		donnesTank.setTirNormalSelectionne(tirNormalSelectionne);
		donnesTank.setTirEparpilleSelectionne(tirEparpilleSelectionne);
		donnesTank.setTirRebondSelectionne(tirRebondSelectionne);

		etatTire = false;
		arreter();
		angleTir = 20;
		bouletCanonNormal.setVitesse(new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
				((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0));
		spnVitesse.setValue((int) VITESSE_INITIALE);
		spnVitesse.setEnabled(true);
		spnmasseJoueur.setEnabled(true);
		cboTypeTerrain.setEnabled(true);
		cboTypeTirs.setEnabled(true);

		terrain.setTypeDeTerrain(TypePlaneteTerrain.TERRE);
		cboTypeTerrain.setSelectedIndex(0);
		cboTypeTirs.setSelectedIndex(0);
		adjustTankOnCurve();

		nbPointDeVieJoueur = NB_POINT_DE_VIE_INIT;
		nbBouclierJoueur = NB_POINT_BOUCLIER_INIT;
		xPointDeVieJoueur = xTank;
		yPointDeVieJoueur = yTank - espaceEntreTankEtBarre;
		xBouclierJoueur = xTank;
		yBouclierJoueur = yTank - espaceEntreTankEtBarre;
		donnesTank.setNbPointDeVieJoueur(nbPointDeVieJoueur);
		donnesTank.setNbBouclierJoueur(nbBouclierJoueur);
		donnesTank.setxPointDeVieJoueur(xPointDeVieJoueur);
		donnesTank.setyPointDeVieJoueur(yPointDeVieJoueur);
		donnesTank.setxBouclierJoueur(xBouclierJoueur);
		donnesTank.setyBouclierJoueur(yBouclierJoueur);
		barreDeVieEtBouclierJoueur = new BarreDeVieEtBouclier(nbPointDeVieJoueur, nbBouclierJoueur, xPointDeVieJoueur,
				yPointDeVieJoueur, xBouclierJoueur, yBouclierJoueur, largeurBarreVieBouclier, pixelsParMetre);

		nbPointDeVieEnnemi = NB_POINT_DE_VIE_INIT;
		nbBouclierEnnemi = NB_POINT_BOUCLIER_INIT;
		xPointDeVieEnnemi = xTankEnnemi;
		yPointDeVieEnnemi = yTankEnnemi - espaceEntreTankEtBarre;
		xBouclierEnnemi = xTankEnnemi;
		yBouclierEnnemi = yTankEnnemi - espaceEntreTankEtBarre;
		donnesTank.setNbPointDeVieEnnemi(nbPointDeVieEnnemi);
		donnesTank.setNbBouclierEnnemi(nbBouclierEnnemi);
		donnesTank.setxPointDeVieEnnemi(xPointDeVieEnnemi);
		donnesTank.setyPointDeVieEnnemi(yPointDeVieEnnemi);
		donnesTank.setxBouclierEnnemi(xBouclierEnnemi);
		donnesTank.setyBouclierEnnemi(yBouclierEnnemi);
		barreDeVieEtBouclierEnnemi = new BarreDeVieEtBouclier(nbPointDeVieEnnemi, nbBouclierEnnemi, xPointDeVieEnnemi,
				yPointDeVieEnnemi, xBouclierEnnemi, yBouclierEnnemi, largeurBarreVieBouclier, pixelsParMetre);
		lblCarburantJoueur.setText("Niveau de carburant: " + carburantJoueur);
		nbTour = 0;
		tourJoueur = true;
		nbTirEparp = 5;
		nbTirRebond = 5;
		nbDegatJoueur = NB_DEGAT_INIT;
		GestionnaireDesDonnes.getInstance().setDonnesTank(donnesTank);
		repaint();
		requestFocusInWindow();
	}

	/**
	 * Méthode pour faire réinitialiser le niveau lors du clic sur le bouton
	 */
	// Liangchen Liu
	private void btnReini() {
		btnReinitialiser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				reini();
			}// fin méthode
		});// fin listener

	}// fin méthode

	/**
	 * Méthode pour ouvrir le menu avec des instructions lors du clic sur le bouton
	 */
	// Liangchen Liu
	private void aide() {
		btnAide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instructions();
            	requestFocusInWindow();
			}// fin méthode
		});// fin listener

	}// fin méthode
	
	/**
	 * Ouvre la fenêtre d'instructions
	 */
	// Liangchen Liu
	public void instructions() {
		FenetreAideInstructions fenetreAideInstructions = new FenetreAideInstructions(new String[] {
				"Instructions/Tank/page1.jpg", "Instructions/Tank/page2.jpg", "Instructions/Tank/page3.jpg" });
		fenetreAideInstructions.setVisible(true);
	}
	
	/**
	 * Méthode pour ouvrir le menu avec des instructions lors du clic sur le bouton
	 */
	// Liangchen Liu
	private void infoJoueur() {
		btnInformations.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				lblInfoEtudiant.setVisible(true);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				lblInfoEtudiant.setVisible(false);
			}// fin méthode
		});// fin listener

	}// fin méthode

	/**
	 * Méthode pour ouvrir le menu d'instructions au professeur avec lors du clic
	 * sur le bouton
	 */
	// Liangchen Liu
	private void exProf() {
		btnExProf.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, instructions, "Fonctionnalités présentes",
						JOptionPane.INFORMATION_MESSAGE);

				repaint();
				requestFocusInWindow();
			}// fin méthode
		});// fin listener

	}// fin méthode

	/**
	 * Méthode pour passer au prochaine image lors du clic sur le bouton
	 */
	// Liangchen Liu
	private void prochImg() {
		btnProchImg.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				if (enCoursDAnimation) {
					arreter();
				}
				calculerUneIterationPhysique(deltaT);
				repaint();
				requestFocusInWindow();
			}// fin méthode
		});// fin listener

	}// fin méthode

	/**
	 * Retourne l'instance de bouletCanonNormal
	 */
	// Liangchen Liu
	public BouletCanonNormal getBouletCanonNormal() {
		return bouletCanonNormal;
	}// fin méthode

	/**
	 * Modifie la valeur du boulet du canon normal
	 * 
	 * @param bouletCanonNormal Le boulet du canon normal
	 */
	// Liangchen Liu
	public void setBouletCanonNormal(BouletCanonNormal bouletCanonNormal) {
		this.bouletCanonNormal = bouletCanonNormal;
	}// fin méthode

	/**
	 * Retourne la valeur de la position en x du premier coin du char d'assaut
	 * 
	 * @return La position en x du premier coin du char d'assaut
	 */
	// Liangchen Liu
	public double getX1() {
		return x1;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x du premier coin du char d'assaut et
	 * redessine le char d'assaut avec la nouvelle position en x du premier coin
	 * 
	 * @param x1 La position en x du premier coin du char d'assaut
	 */
	// Liangchen Liu
	public void setX1(double x1) {
		this.x1 = x1;
		repaint();
	}// fin méthode

	/**
	 * Retourne la valeur de la position en x du deuxième coin du char d'assaut
	 * 
	 * @return La position en x du deuxième coin du char d'assaut
	 */
	// Liangchen Liu
	public double getX2() {
		return x2;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x du deuxième coin du char d'assaut et
	 * redessine le char d'assaut avec la nouvelle position en x du deuxième coin
	 * 
	 * @param x2 La position en x du deuxième coin du char d'assaut
	 */
	public void setX2(double x2) {
		this.x2 = x2;
		repaint();
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y du premier coin du char d'assaut
	 * 
	 * @return La position en y du premier coin du char d'assaut
	 */
	// Liangchen Liu
	public double getY1() {
		return y1;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y du premier coin du char d'assaut et
	 * redessine le char d'assaut avec la nouvelle position en y du premier coin
	 * 
	 * @param y1 La position en y du premier coin du char d'assaut
	 */
	// Liangchen Liu
	public void setY1(double y1) {
		this.y1 = y1;
		repaint();
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y du deuxième coin du char d'assaut
	 * 
	 * @return La position en y du deuxième coin du char d'assaut
	 */
	// Liangchen Liu
	public double getY2() {
		return y2;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y du deuxième coin du char d'assaut et
	 * redessine le char d'assaut avec la nouvelle position en y du deuxième coin
	 * 
	 * @param y2 La position en y du deuxième coin du char d'assaut
	 */
	// Liangchen Liu
	public void setY2(double y2) {
		this.y2 = y2;
		repaint();
	}// fin méthode

	/**
	 * Retourne la valeur de la position en x du char d'assaut
	 * 
	 * @return La position en x du char d'assaut
	 */
	// Liangchen Liu
	public double getXTank() {
		return xTank;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en x du char d'assaut et redessine le char
	 * d'assaut avec la nouvelle position en x
	 * 
	 * @param xTank La position en x du char d'assaut
	 */
	// Liangchen Liu
	public void setXTank(double xTank) {
		this.xTank = xTank;
		repaint();
	}// fin méthode

	/**
	 * Retourne la valeur de la position en y du char d'assaut
	 * 
	 * @return La position en y du char d'assaut
	 */
	// Liangchen Liu
	public double getYTank() {
		return yTank;
	}// fin méthode

	/**
	 * Modifie la valeur de la position en y du char d'assaut et redessine le char
	 * d'assaut avec la nouvelle position en y
	 * 
	 * @param yTank La position en y du char d'assaut
	 */
	// Liangchen Liu
	public void setYTank(double yTank) {
		this.yTank = yTank;
		repaint();
	}// fin méthode

	/**
	 * Retourne la valeur de l'angle de rotation lié au déplacement du char d'assaut
	 * 
	 * @return L'angle de rotation lié au déplacement du char d'assaut
	 */
	// Liangchen Liu
	public double getTheta() {
		return theta;
	}// fin méthode

	/**
	 * Modifie la valeur de l'angle de rotation lié au déplacement du char d'assaut
	 * 
	 * @param theta L'angle de rotation lié au déplacement du char d'assaut
	 */
	// Liangchen Liu
	public void setTheta(double theta) {
		this.theta = theta;
	}// fin méthode

	/**
	 * Retourne si une amélioration est dessinée ou non
	 * 
	 * @return Si une amélioration est dessinée ou non
	 */
	// Liangchen Liu
	public boolean getAmelioDessine() {
		return amelioDessine;
	}// fin méthode

	/**
	 * Modifie si une amélioration est dessinée ou non
	 * 
	 * @param amelioDessine Si une amélioration est dessinée ou non
	 */
	// Liangchen Liu
	public void setAmelioDes(boolean amelioDessine) {
		this.amelioDessine = amelioDessine;
	}// fin méthode

	public boolean isConditionCbo() {
		return conditionCbo;
	}

	public void setConditionCbo(boolean conditionCbo) {
		this.conditionCbo = conditionCbo;
	}

	/**
	 * Méthode qui permet de changer le type de terrain à celui d'une autre planète,
	 * qui a une gravité associé
	 */
	// Par Michel Vuu
	private void typeTerrainSelectionne() {
		switch (cboTypeTerrain.getSelectedIndex()) {
		case (0):
			terrain.setTypeDeTerrain(TypePlaneteTerrain.TERRE);
			terrain.setGravite(TypePlaneteTerrain.TERRE.getGravite());
			donnesTank.setTypePlaneteTerrain(TypePlaneteTerrain.TERRE);

			gravite = TypePlaneteTerrain.TERRE.getGravite();

			repaint();
			break;

		case (1):
			terrain.setTypeDeTerrain(TypePlaneteTerrain.MARS);
			terrain.setGravite(TypePlaneteTerrain.MARS.getGravite());
			donnesTank.setTypePlaneteTerrain(TypePlaneteTerrain.MARS);

			gravite = TypePlaneteTerrain.MARS.getGravite();
			bouletCanonNormal.setSommeDesForce(new SVector3d(0, gravite * masseJoueur, 0));
			repaint();
			break;
		case (2):
			terrain.setTypeDeTerrain(TypePlaneteTerrain.MERCURE);
			terrain.setGravite(TypePlaneteTerrain.MERCURE.getGravite());
			donnesTank.setTypePlaneteTerrain(TypePlaneteTerrain.MERCURE);

			gravite = TypePlaneteTerrain.MERCURE.getGravite();
			repaint();
			break;
		case (3):
			terrain.setTypeDeTerrain(TypePlaneteTerrain.LUNE);
			terrain.setGravite(TypePlaneteTerrain.LUNE.getGravite());
			donnesTank.setTypePlaneteTerrain(TypePlaneteTerrain.LUNE);

			gravite = TypePlaneteTerrain.LUNE.getGravite();
			repaint();
			break;
		}// fin switch
		if (tirNormalSelectionne) {
			bouletCanonNormal.setSommeDesForce(new SVector3d(0, gravite * masseJoueur, 0));
		} else if (tirEparpilleSelectionne) {
			bouletCanonEparpillePetit.setSommeDesForce(new SVector3d(0, gravite * masseJoueur, 0));
			bouletCanonEparpilleMoyen.setSommeDesForce(new SVector3d(0, gravite * masseJoueur, 0));
			bouletCanonEparpilleGrand.setSommeDesForce(new SVector3d(0, gravite * masseJoueur, 0));
		} else if (tirRebondSelectionne) {
			bouletCanonRebond.setSommeDesForce(new SVector3d(0, gravite * masseJoueur, 0));
		}
	}

	/**
	 * Méthode qui s'occupe de l'apparition des améliorations
	 */
	// Par Michel Vuu
	private void amelioApparition() {
		ArrayList<AmelioTank> spawn = new ArrayList<AmelioTank>();
		spawn.add(bouclier);
		spawn.add(degat);
		spawn.add(pointsDeVie);
		spawn.add(carburant);
		spawn.add(tirs);

		if (!amelioDessine) {
			randAmelio = new Random().nextInt((5));

			randX = new Random().nextInt((int) (xMaxSeparator-80) + 1);
			randY = new Random().nextInt(maxY + 1);

		}

		if (randAmelio == spawn.indexOf(bouclier))

		{
			bouclier = new AmelioTank(randX, randY, 45, 45, TypeAmelioration.BOUCLIER, "bouclier.png");
			bouclier.dessiner(g2d);

			amelioDessine = true;
		}
		else if (randAmelio == spawn.indexOf(degat)) {
			degat = new AmelioTank(randX, randY, 45, 45, TypeAmelioration.PLUS_DEGAT, "degat.png");
			degat.dessiner(g2d);
			amelioDessine = true;

		}
		else if (randAmelio == spawn.indexOf(pointsDeVie)) {
			pointsDeVie = new AmelioTank(randX, randY, 45, 45, TypeAmelioration.PLUS_VIE,
					"Amelioration points de vie.png");
			pointsDeVie.dessiner(g2d);
			amelioDessine = true;

		}
		else if (randAmelio == spawn.indexOf(carburant)) {
			carburant = new AmelioTank(randX, randY, 45, 45, TypeAmelioration.PLUS_CABURANT, "carburant.png");
			carburant.dessiner(g2d);
			amelioDessine = true;

		}
		else if (randAmelio == spawn.indexOf(tirs)) {
			tirs = new AmelioTank(randX, randY, 45, 45, TypeAmelioration.DEUXIEME_TIR, "tir.png");
			tirs.dessiner(g2d);
			amelioDessine = true;

		}
	}

	/**
	 * Méthode qui s'occupe de l'interaction entre le tank et les tir et les
	 * améliorations
	 */
	// Jason Yin
	private void amelioTouche() {
		Area aireBouletAmelio = bouletCanonNormal.getAireBoulet();
		Area aireTank = charAssaut.getAireTank();
		Area aireBouletEparpPetit = bouletCanonEparpillePetit.getAireBoulet();
		Area aireBouletEparpGrand = bouletCanonEparpilleGrand.getAireBoulet();
		Area aireBouletEparpMoyen = bouletCanonEparpilleMoyen.getAireBoulet();
		Area aireBouletCanonRebond = bouletCanonRebond.getAireBoulet();
		if (randAmelio == 0) {
			if (etatTire) {
				if (tirNormalSelectionne) {
					if (bouclier.estTouche(aireBouletAmelio)) {
						nbBouclierJoueur = nbBouclierJoueur + 10;
						amelioDessine = false;
						lblBouclierJoueur.setText("Bouclier: " + nbBouclierJoueur);
						System.out.println("bouclier touché"+ " " + nbBouclierJoueur);
					}
				} else if (tirEparpilleSelectionne) {
					if (bouclier.estTouche(aireBouletEparpPetit) || bouclier.estTouche(aireBouletEparpGrand)
							|| bouclier.estTouche(aireBouletEparpMoyen)) {
						nbBouclierJoueur += 10;
						amelioDessine = false;
						System.out.println("bouclier touché");
					}
				} else if (tirRebondSelectionne) {
					if (bouclier.estTouche(aireBouletCanonRebond)) {
						nbBouclierJoueur += 10;
						amelioDessine = false;
						System.out.println("bouclier touché");
					}
				}
			}
			if (!etatTire) {
				if (bouclier.estTouche(aireTank)) {
					nbBouclierJoueur += 10;
					amelioDessine = false;
					System.out.println("bouclier touché");
				}
			}
		}
		if (randAmelio == 1) {
			if (etatTire) {
				if (tirNormalSelectionne) {
					if (degat.estTouche(aireBouletAmelio)) {
						nbDegatJoueur += 10;
						amelioDessine = false;
					}
				} else if (tirEparpilleSelectionne) {
					if (degat.estTouche(aireBouletEparpPetit) || degat.estTouche(aireBouletEparpGrand)
							|| degat.estTouche(aireBouletEparpMoyen)) {
						nbDegatJoueur += 10;
						amelioDessine = false;
					}
				} else if (tirRebondSelectionne) {
					if (degat.estTouche(aireBouletCanonRebond)) {
						nbDegatJoueur += 10;
						amelioDessine = false;
					}
				}
			}
			if (!etatTire) {
				if (degat.estTouche(aireTank)) {
					nbDegatJoueur += 10;
					amelioDessine = false;
				}
			}
		}
		if (randAmelio == 2) {
			if (etatTire) {
				if (tirNormalSelectionne) {
					if (pointsDeVie.estTouche(aireBouletAmelio)) {
						nbPointDeVieJoueur += 10;
						amelioDessine = false;
						System.out.println("vie touché");
					}
				} else if (tirEparpilleSelectionne) {
					if (pointsDeVie.estTouche(aireBouletEparpPetit) || pointsDeVie.estTouche(aireBouletEparpGrand)
							|| pointsDeVie.estTouche(aireBouletEparpMoyen)) {
						nbPointDeVieJoueur += 10;
						amelioDessine = false;
						System.out.println("vie touché");
					}
				} else if (tirRebondSelectionne) {
					if (pointsDeVie.estTouche(aireBouletCanonRebond)) {
						nbPointDeVieJoueur += 10;
						amelioDessine = false;
						System.out.println("vie touché");
					}
				}
			}
			if (!etatTire) {
				if (pointsDeVie.estTouche(aireTank)) {
					nbPointDeVieJoueur += 10;
					amelioDessine = false;
					System.out.println("vie touché");
				}
			}
		}
		if (randAmelio == 3) {
			if (etatTire) {
				if (tirNormalSelectionne) {
					if (carburant.estTouche(aireBouletAmelio)) {
						carburantBonusJoueur += 10;
						carburantJoueur = CARBURANT_INIT + carburantBonusJoueur;
						donnesTank.setCarburantBonusJoueur(carburantBonusJoueur);
						amelioDessine = false;
						jaugeCarburant.setNbCarburant(carburantJoueur);
						jaugeCarburant.setNbCarburantMax(carburantJoueur);

						System.out.println("carburant touché");
					}
				} else if (tirEparpilleSelectionne) {
					if (carburant.estTouche(aireBouletEparpPetit) || carburant.estTouche(aireBouletEparpGrand)
							|| carburant.estTouche(aireBouletEparpMoyen)) {
						carburantBonusJoueur += 10;
						carburantJoueur = CARBURANT_INIT + carburantBonusJoueur;
						donnesTank.setCarburantBonusJoueur(carburantBonusJoueur);
						amelioDessine = false;
						jaugeCarburant.setNbCarburant(carburantJoueur);
						jaugeCarburant.setNbCarburantMax(carburantJoueur);
						System.out.println("carburant touché");
					}
				} else if (tirRebondSelectionne) {
					if (carburant.estTouche(aireBouletCanonRebond)) {
						carburantBonusJoueur += 10;
						carburantJoueur = CARBURANT_INIT + carburantBonusJoueur;
						donnesTank.setCarburantBonusJoueur(carburantBonusJoueur);
						amelioDessine = false;
						jaugeCarburant.setNbCarburant(carburantJoueur);
						jaugeCarburant.setNbCarburantMax(carburantJoueur);
						System.out.println("carburant touché");
					}
				}
			}
			if (!etatTire) {
				if (carburant.estTouche(aireTank)) {
					carburantBonusJoueur += 10;
					donnesTank.setCarburantBonusJoueur(carburantBonusJoueur);
					amelioDessine = false;
					System.out.println("carburant touché");
				}
			}
		}
		if (randAmelio == 4) {
			if (etatTire) {
				Random rand = new Random();
				if (tirNormalSelectionne) {
					if (tirs.estTouche(aireBouletAmelio)) {
						if (rand.nextBoolean()) {
							nbTirEparp += 1;
						} else {
							nbTirRebond += 1;
						}
						amelioDessine = false;
						System.out.println("vitesse touché");
					}
				} else if (tirEparpilleSelectionne) {
					if (tirs.estTouche(aireBouletEparpPetit) || tirs.estTouche(aireBouletEparpGrand)
							|| tirs.estTouche(aireBouletEparpMoyen)) {
						if (rand.nextBoolean()) {
							nbTirEparp += 1;
						} else {
							nbTirRebond += 1;
						}
						amelioDessine = false;
						System.out.println("vitesse touché");
					}
				} else if (tirRebondSelectionne) {
					if (tirs.estTouche(aireBouletCanonRebond)) {
						if (rand.nextBoolean()) {
							nbTirEparp += 1;
						} else {
							nbTirRebond += 1;
						}
						amelioDessine = false;
						System.out.println("vitesse touché");
					}
				}
			}
			if (!etatTire) {
				if (tirs.estTouche(aireTank)) {
					Random rand = new Random();
					if (rand.nextBoolean()) {
						nbTirEparp += 1;
					} else {
						nbTirRebond += 1;
					}
					amelioDessine = false;
					System.out.println("vitesse touché");
				}
			}
		}
	}

	/**
	 * Méthode qui gère les collisons du tir normal du joueur
	 */
	// Jason Yin
	private void tirDuJoueurNormal() {
		if (etatTire) {
			Area aireBoulet = bouletCanonNormal.getAireBoulet();
			Area aireTerrain = terrain.getAireTerrain();
			Area aireEnnemi = char_ennemi.getAireTank();

			Area aireMouvement = new Area(aireBoulet);
			Area aireTerrainInter = new Area(aireTerrain);
			Area aireTankEnnemi = new Area(aireEnnemi);
			Area aireBouletVersLeTank = new Area(aireBoulet);

			bouletCanonNormal.setPosition(
					new SVector3d(bouletCanonNormal.getPosition().getX(), bouletCanonNormal.getPosition().getY(), 0));
			aireMouvement.intersect(aireTerrainInter);

			if (!aireMouvement.isEmpty() || bouletCanonNormal.getPosition().getX() > 100) {
				vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
						((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
				bouletCanonNormal.setVitesse(vitesse);
				bouletCanonNormal.setPosition(new SVector3d(charAssaut.getX(), charAssaut.getY(), 0));
				bouletCanonNormal.dessiner(g2d);
				etatTire = false;
				tireFini = true;
				cboTypeTirs
			.setModel(new DefaultComboBoxModel(new String[] { ("Tir normal"+" (\u221e x)"), ("Tir rebondissant"+" ("+nbTirRebond+"x)"), ("Tir éparpillé"+" ("+nbTirEparp+"x)") }));
				cboTypeTirs.setSelectedIndex(0);
				bouletCanonNormal.setEtatTire(etatTire);
				nbTourDepuisTouche += 1;
				arreter();
				repaint();
				System.out.println("terrain");
			}

			else if (aireMouvement.isEmpty()) {
				bouletCanonNormal.dessiner(g2d);
				dessinerFlecheVecJoueur();
			}
			aireBouletVersLeTank.intersect(aireTankEnnemi);
			if (!aireBouletVersLeTank.isEmpty()) {
				vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
						((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
				bouletCanonNormal.setVitesse(vitesse);
				bouletCanonNormal.setPosition(new SVector3d(charAssaut.getX(), charAssaut.getY(), 0));
				bouletCanonNormal.dessiner(g2d);
				etatTire = false;
				tireFini = true;
				nbTourDepuisTouche = 0;
				cboTypeTirs
			.setModel(new DefaultComboBoxModel(new String[] { ("Tir normal"+" (\u221e x)"), ("Tir rebondissant"+" ("+nbTirRebond+"x)"), ("Tir éparpillé"+" ("+nbTirEparp+"x)") }));
				cboTypeTirs.setSelectedIndex(0);
				bouletCanonNormal.setEtatTire(etatTire);
				arreter();
				repaint();
				gereurDegatEnnemi();
				System.out.println("Ennemi touche");
			}
			if (amelioDessine) {
				amelioTouche();
			}
			gereurTour();
		}
	}

	/**
	 * Méthode qui gère les collisions du tir éparpillé du joueur
	 */
	// Jason Yin
	private void tirDuJoueurEparp() {
		Area aireEparpPetit = bouletCanonEparpillePetit.getAireBoulet();
		Area aireEparpMoyen = bouletCanonEparpilleMoyen.getAireBoulet();
		Area aireEparpGrand = bouletCanonEparpilleGrand.getAireBoulet();
		Area aireTerrain = terrain.getAireTerrain();
		Area aireEnnemi = char_ennemi.getAireTank();

		Area aireEparpPetitTerrain = new Area(aireEparpPetit);
		Area aireEparpMoyenTerrain = new Area(aireEparpMoyen);
		Area aireEparpGrandTerrain = new Area(aireEparpGrand);
		Area aireTerrainEparpPetit = new Area(aireTerrain);
		Area aireTerrainEparpMoyen = new Area(aireTerrain);
		Area aireTerrainEparpGrand = new Area(aireTerrain);
		Area aireEparpPetitVersTank = new Area(aireEparpPetit);
		Area aireEparpMoyenVersTank = new Area(aireEparpMoyen);
		Area aireEparpGrandVersTank = new Area(aireEparpGrand);
		Area aireTankEnnemiEparpPetit = new Area(aireEnnemi);
		Area aireTankEnnemiEparpMoyen = new Area(aireEnnemi);
		Area aireTankEnnemiEparpGrand = new Area(aireEnnemi);

		boolean ennemiTouche = false;

		bouletCanonEparpillePetit.setPosition(new SVector3d(bouletCanonEparpillePetit.getPosition().getX(),
				bouletCanonEparpillePetit.getPosition().getY(), 0));

		aireEparpPetitTerrain.intersect(aireTerrainEparpPetit);

		if (!aireEparpPetitTerrain.isEmpty() || bouletCanonEparpillePetit.getPosition().getX() > 100) {
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			bouletCanonEparpillePetit.setVitesse(vitesse.add(ajustEparpPetit));
			bouletCanonEparpillePetit.setPosition(new SVector3d(charAssaut.getX(), charAssaut.getY(), 0));
			bouletCanonEparpillePetit.dessiner(g2d);
			arretTirEparpPetit = true;
			repaint();
			System.out.println("terrain Petit");
		} else if (aireEparpPetitTerrain.isEmpty()) {
			bouletCanonEparpillePetit.dessiner(g2d);
			dessinerFlecheVecJoueur();
		}

		bouletCanonEparpilleMoyen.setPosition(new SVector3d(bouletCanonEparpilleMoyen.getPosition().getX(),
				bouletCanonEparpilleMoyen.getPosition().getY(), 0));

		aireEparpMoyenTerrain.intersect(aireTerrainEparpMoyen);
		if (!aireEparpMoyenTerrain.isEmpty() || bouletCanonEparpilleMoyen.getPosition().getX() > 100) {
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			bouletCanonEparpilleMoyen.setVitesse(vitesse);
			bouletCanonEparpilleMoyen.setPosition(new SVector3d(charAssaut.getX(), charAssaut.getY(), 0));
			bouletCanonEparpilleMoyen.dessiner(g2d);
			arretTirEparpMoyen = true;
			repaint();
			System.out.println("terrain Moyen");
		} else if (aireEparpMoyenTerrain.isEmpty()) {
			bouletCanonEparpilleMoyen.dessiner(g2d);
			dessinerFlecheVecJoueur();
		}

		bouletCanonEparpilleGrand.setPosition(new SVector3d(bouletCanonEparpilleGrand.getPosition().getX(),
				bouletCanonEparpilleGrand.getPosition().getY(), 0));
		aireEparpGrandTerrain.intersect(aireTerrainEparpGrand);
		if (!aireEparpGrandTerrain.isEmpty() || bouletCanonEparpilleGrand.getPosition().getX() > 100) {
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			bouletCanonEparpilleGrand.setVitesse(vitesse.add(ajustEparpGrand));
			bouletCanonEparpilleGrand.setPosition(new SVector3d(charAssaut.getX(), charAssaut.getY(), 0));
			bouletCanonEparpilleGrand.dessiner(g2d);
			arretTirEparpGrand = true;
			repaint();
			System.out.println("terrain Grand");
		} else if (aireEparpGrandTerrain.isEmpty()) {
			bouletCanonEparpilleGrand.dessiner(g2d);
			dessinerFlecheVecJoueur();
		}
		aireEparpPetitVersTank.intersect(aireTankEnnemiEparpPetit);
		aireEparpMoyenVersTank.intersect(aireTankEnnemiEparpMoyen);
		aireEparpGrandVersTank.intersect(aireTankEnnemiEparpGrand);
		if (!aireEparpPetitVersTank.isEmpty()) {
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			bouletCanonEparpillePetit.setVitesse(vitesse.add(ajustEparpPetit));
			bouletCanonEparpillePetit.setPosition(new SVector3d(charAssaut.getX(), charAssaut.getY(), 0));
			bouletCanonEparpillePetit.dessiner(g2d);
			arretTirEparpPetit = true;
			ennemiTouche = true;
			repaint();
			gereurDegatEnnemi();
			System.out.println("tank");
		}
		if (!aireEparpMoyenVersTank.isEmpty()) {
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			bouletCanonEparpilleMoyen.setVitesse(vitesse);
			bouletCanonEparpilleMoyen.setPosition(new SVector3d(charAssaut.getX(), charAssaut.getY(), 0));
			bouletCanonEparpilleMoyen.dessiner(g2d);
			arretTirEparpMoyen = true;
			ennemiTouche = true;
			repaint();
			gereurDegatEnnemi();
			System.out.println("tank");
		}
		if (!aireEparpGrandVersTank.isEmpty()) {
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			bouletCanonEparpilleGrand.setVitesse(vitesse);
			bouletCanonEparpilleGrand.setPosition(new SVector3d(charAssaut.getX(), charAssaut.getY(), 0));
			bouletCanonEparpilleGrand.dessiner(g2d);
			arretTirEparpGrand = true;
			ennemiTouche = true;
			repaint();
			gereurDegatEnnemi();
			System.out.println("tank");
		}
		if (arretTirEparpGrand && arretTirEparpMoyen && arretTirEparpPetit) {
			etatTire = false;
			tireFini = true;
			arretTirEparpPetit = false;
			arretTirEparpMoyen = false;
			arretTirEparpGrand = false;
			bouletCanonEparpillePetit.setEtatTire(etatTire);
			bouletCanonEparpilleMoyen.setEtatTire(etatTire);
			bouletCanonEparpilleGrand.setEtatTire(etatTire);
			cboTypeTirs
			.setModel(new DefaultComboBoxModel(new String[] { ("Tir normal"+" (\u221e x)"), ("Tir rebondissant"+" ("+nbTirRebond+"x)"), ("Tir éparpillé"+" ("+nbTirEparp+"x)") }));
			cboTypeTirs.setSelectedIndex(2);
			if (nbTirEparp > 0) {
				nbTirEparp -= 1;
			} else {
				nbTirEparp = 0;
			}
			if (!ennemiTouche) {
				nbTourDepuisTouche += 1;
			} else {
				nbTourDepuisTouche = 0;
			}
			ennemiTouche = false;
			arreter();
			repaint();
		}
		if (amelioDessine) {
			amelioTouche();
		}
		gereurTour();
	}

	/**
	 * Methode qui gere les collisons du tir rebondissant
	 */
	// Jason Yin
	private void tirDuJoueurRebond() {
		Area aireBoulet = bouletCanonRebond.getAireBoulet();
		Area aireTerrain = terrain.getAireTerrain();
		Area aireEnnemi = char_ennemi.getAireTank();
		Area aireTank = charAssaut.getAireTank();

		Area aireMouvement = new Area(aireBoulet);
		Area aireTerrainInter = new Area(aireTerrain);
		Area aireTankEnnemi = new Area(aireEnnemi);
		Area aireBouletVersLeTank = new Area(aireBoulet);
		Area aireTankJoueur = new Area(aireTank);
		Area aireBouletVersTankJoueur = new Area(aireBoulet);

		bouletCanonRebond.setPosition(
				new SVector3d(bouletCanonRebond.getPosition().getX(), bouletCanonRebond.getPosition().getY(), 0));
		aireMouvement.intersect(aireTerrainInter);
		dessinerFlecheVecJoueur();

		if (!aireMouvement.isEmpty() || bouletCanonRebond.getPosition().getX() >= 100
				|| bouletCanonRebond.getPosition().getX() <= 0) {
			if (nbRebond < 2) {
				incrementationDepuisCollision++;
				if(incrementationDepuisCollision>=2){
				bouletCanonRebond.setVitesse(calculRebond());
				nbRebond++;
				System.out.println("rebond");
				dessinerFlecheVecJoueur();
				incrementationDepuisCollision=0;
				}
			} else {
				vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
						((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
				bouletCanonRebond.setVitesse(vitesse);
				bouletCanonRebond.setPosition(new SVector3d(charAssaut.getX(), charAssaut.getY(), 0));
				bouletCanonRebond.dessiner(g2d);
				etatTire = false;
				tireFini = true;
				bouletCanonRebond.setEtatTire(etatTire);
				cboTypeTirs
			.setModel(new DefaultComboBoxModel(new String[] { ("Tir normal"+" (\u221e x)"), ("Tir rebondissant"+" ("+nbTirRebond+"x)"), ("Tir éparpillé"+" ("+nbTirEparp+"x)") }));
				cboTypeTirs.setSelectedIndex(1);
				nbTourDepuisTouche += 1;
				arreter();
				repaint();
				nbRebond = 0;
				if (nbTirRebond > 0) {
					nbTirRebond -= 1;
				} else {
					nbTirRebond = 0;
				}
				System.out.println("rebond fin");
			}
		}

		else if (aireMouvement.isEmpty()) {
			bouletCanonRebond.dessiner(g2d);
		}
		aireBouletVersLeTank.intersect(aireTankEnnemi);
		aireBouletVersTankJoueur.intersect(aireTankJoueur);
		if (!aireBouletVersLeTank.isEmpty() || (!aireBouletVersTankJoueur.isEmpty() && nbRebond >= 1)) {
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			bouletCanonRebond.setVitesse(vitesse);
			bouletCanonRebond.setPosition(new SVector3d(charAssaut.getX(), charAssaut.getY(), 0));
			bouletCanonRebond.dessiner(g2d);
			etatTire = false;
			tireFini = true;
			bouletCanonRebond.setEtatTire(etatTire);
			cboTypeTirs
			.setModel(new DefaultComboBoxModel(new String[] { ("Tir normal"+" (\u221e x)"), ("Tir rebondissant"+" ("+nbTirRebond+"x)"), ("Tir éparpillé"+" ("+nbTirEparp+"x)") }));
			cboTypeTirs.setSelectedIndex(1);
			arreter();
			repaint();
			if (!aireBouletVersLeTank.isEmpty()) {
				gereurDegatEnnemi();
			} else if (!aireBouletVersTankJoueur.isEmpty()) {
				gereurDegatJoueur();
			}
			nbRebond = 0;
			if (nbTirRebond > 0) {
				nbTirRebond -= 1;
			} else {
				nbTirRebond = 0;
			}
			System.out.println("nbRebond = " + nbRebond);
			gereurTour();
			nbTourDepuisTouche = 0;
		}
		if (amelioDessine) {
			amelioTouche();
		}
	}

	/**
	 * Methode qui calcul les rebonds du boulet
	 * 
	 * @return la nouvelle vitesse du boulet
	 */
	// Jason Yin
	private SVector3d calculRebond() {
		double x1Boulet = bouletCanonRebond.getPosition().getX() - (diametre / 2);
		double x2Boulet = bouletCanonRebond.getPosition().getX() + (diametre / 2);
		double y1Boulet = terrain.evalFonction(x1Boulet);
		double y2Boulet = terrain.evalFonction(x2Boulet);

		SVector3d p1p2 = new SVector3d(x2Boulet - x1Boulet, y2Boulet - y1Boulet, 0);
		SVector3d normalP1p2 = new SVector3d(-p1p2.getY(), p1p2.getX(), 0).normalize();
		SVector3d vitesseBoulet = bouletCanonRebond.getVitesse();
		double vitesseBouletNormale = vitesseBoulet.dot(normalP1p2);
		SVector3d vitesseRebond = vitesseBoulet.substract((normalP1p2.multiply(vitesseBouletNormale * 2)));
		if (bouletCanonRebond.getPosition().getX() >= 100 || bouletCanonRebond.getPosition().getX() <= 0) {
			vitesseRebond = new SVector3d(vitesseBoulet.getX() * -1, vitesseBoulet.getY(), 0);
		}
		return vitesseRebond;
	}

	/**
	 * Méthode qui gère les collisons du tir de l'ennemi
	 */
	// Jason Yin
	private void tirEnnemi() {
			if (etatTire) {
			Area aireBouletEnnemi = bouletCanonEnnemi.getAireBoulet();
			Area aireTerrain = terrain.getAireTerrain();
			Area aireJoueur = charAssaut.getAireTank();

			Area aireMouvement = new Area(aireBouletEnnemi);
			Area aireTerrainInter = new Area(aireTerrain);
			Area aireTankEnnemi = new Area(aireJoueur);
			Area aireBouletVersLeTank = new Area(aireBouletEnnemi);

			bouletCanonEnnemi.setPosition(
					new SVector3d(bouletCanonEnnemi.getPosition().getX(), bouletCanonEnnemi.getPosition().getY(), 0));
			aireMouvement.intersect(aireTerrainInter);
			if (aireMouvement.isEmpty() || bouletCanonEnnemi.getPosition().getX() > 0) {
				bouletCanonEnnemi.dessiner(g2d);
				dessinerFlecheVecEnnemi();
			}

			if (!aireMouvement.isEmpty() || bouletCanonEnnemi.getPosition().getX() <= 0) {
				vitesseEnnemi = new SVector3d(((Math.cos(angleTirEnnemi * Math.PI / 180)) * vitesseInitEnnemi),
						((Math.sin(angleTirEnnemi * Math.PI / 180)) * vitesseInitEnnemi * -1), 0);
				bouletCanonEnnemi.setVitesse(vitesseEnnemi);
				bouletCanonEnnemi.setPosition(new SVector3d(char_ennemi.getX(), char_ennemi.getY(), 0));
				bouletCanonEnnemi.dessiner(g2d);
				etatTire = false;
				tireFini = true;
				bouletCanonEnnemi.setEtatTire(etatTire);
				arreter();
				repaint();
				System.out.println("terrainEnnemi");
			}
			aireBouletVersLeTank.intersect(aireTankEnnemi);
			if (!aireBouletVersLeTank.isEmpty()) {
				vitesseEnnemi = new SVector3d(((Math.cos(angleTirEnnemi * Math.PI / 180)) * vitesseInitEnnemi),
						((Math.sin(angleTirEnnemi * Math.PI / 180)) * vitesseInitEnnemi * -1), 0);
				bouletCanonEnnemi.setVitesse(vitesseEnnemi);
				bouletCanonEnnemi.setPosition(new SVector3d(char_ennemi.getX(), char_ennemi.getY(), 0));
				bouletCanonEnnemi.dessiner(g2d);
				etatTire = false;
				tireFini = true;
				bouletCanonEnnemi.setEtatTire(etatTire);
				arreter();
				repaint();
				gereurDegatJoueur();
			}

			gereurTour();
		}

	}

	/**
	 * Méthode qui gère les donnes qui vont dans le classment
	 */
	// Liangchen Liu
	private void ajouterDonnesClassementsEtBadges() {
		DonnesClassements donnesClassements = GestionnaireDesDonnes.getInstance().getDonnesClassements();
		ArrayList<InformationEssais> tempTank = donnesClassements.getTankArrayList();
		tempTank.add(new InformationEssais("Tank", nbTour, Math.round(tempsEcoule / 1000000000.0)));
		GestionnaireDesDonnes.getInstance().getDonnesClassements().setTankArrayList(tempTank);

		GestionnaireDesDonnes.getInstance().getDonnesBadges().setBadgeGtank8(true);

	}

	/**
	 * Méthode qui gère où les points de dégat iront pour l'ennemi (soient dans le
	 * bouclier ou la barre de point de vie
	 */
	// Jason Yin
	private void gereurDegatEnnemi() {
		if (nbBouclierEnnemi > 0) {
			nbBouclierEnnemi -= calculDegatJoueur();
			if(nbBouclierEnnemi < 0) {
				nbPointDeVieEnnemi += nbBouclierEnnemi;
				nbBouclierEnnemi = 0;
			}
		} else if (nbPointDeVieEnnemi > 0 && nbBouclierEnnemi == 0) {
			nbPointDeVieEnnemi -= calculDegatJoueur();
			if (nbPointDeVieEnnemi <= 0) {
				System.out.println("Vous avez gagné");
				victoire();
				barreDeVieEtBouclierEnnemi = new BarreDeVieEtBouclier(0, 0, xPointDeVieEnnemi, yPointDeVieEnnemi,
						xBouclierEnnemi, yBouclierEnnemi, largeurBarreVieBouclier, pixelsParMetre);

				ajouterDonnesClassementsEtBadges();
			}
		}
		donnesTank.setNbPointDeVieEnnemi(nbPointDeVieEnnemi);
		donnesTank.setNbBouclierEnnemi(nbBouclierEnnemi);
		if (nbPointDeVieEnnemi > 0) {
			barreDeVieEtBouclierEnnemi = new BarreDeVieEtBouclier(nbPointDeVieEnnemi, nbBouclierEnnemi,
					xPointDeVieEnnemi, yPointDeVieEnnemi, xBouclierEnnemi, yBouclierEnnemi, largeurBarreVieBouclier,
					pixelsParMetre);
			lblPointDeVieEnnemi.setText("Point de Vie Ennemi: " + nbPointDeVieEnnemi);
		}
		lblBouclierEnnemi.setText("Point de bouclier Ennemi:" + nbBouclierEnnemi);
	}

	/**
	 * Méthode qui gère où les points de dégat iront pour le joueur (soient dans le
	 * bouclier ou la barre de point de vie
	 */
	// Jason Yin
	private void gereurDegatJoueur() {
		if (nbBouclierJoueur > 0) {
			nbBouclierJoueur -= nbDegatEnnemi;
			if(nbBouclierJoueur < 0) {
				nbPointDeVieJoueur += nbBouclierJoueur;
				nbBouclierJoueur = 0;
			}
		} else if (nbPointDeVieJoueur > 0 && nbBouclierJoueur >= 0) {
			nbPointDeVieJoueur -= nbDegatEnnemi;
			if (nbPointDeVieJoueur <= 0) {
				nbPointDeVieJoueur = 0;
				System.out.println("Vous avez perdu");
				defaite();
				barreDeVieEtBouclierJoueur = new BarreDeVieEtBouclier(0, 0, xPointDeVieJoueur, yPointDeVieJoueur,
						xBouclierJoueur, yBouclierJoueur, largeurBarreVieBouclier, pixelsParMetre);
			}
		}
		donnesTank.setNbBouclierJoueur(nbBouclierJoueur);
		donnesTank.setNbPointDeVieJoueur(nbPointDeVieJoueur);
		barreDeVieEtBouclierJoueur = new BarreDeVieEtBouclier(nbPointDeVieJoueur, nbBouclierJoueur, xPointDeVieJoueur,
				yPointDeVieJoueur, xBouclierJoueur, yBouclierJoueur, largeurBarreVieBouclier, pixelsParMetre);
		// donnesTank.setNbBoucJoueur(nbBouclierJoueur);
		// donnesTank.setNbPVJoueur(nbPointDeVieJoueur);
		lblPointDeVieJoueur.setText("Point de Vie Joueur: " + nbPointDeVieJoueur);
		lblBouclierJoueur.setText("Point de bouclier:" + nbBouclierJoueur);
	}

	private int calculDegatJoueur() {
		int degatSelonMasse = nbDegatJoueur + ((int) (2 * masseJoueur));
		return degatSelonMasse;
	}

	/**
	 * Méthode pour faire afficher l'écran de victoire
	 */
	// Liangchen Liu
	private void victoire() {
		menuFinJeu.setVisible(true);
		classeTemps.arreter();

		DonnesClassements donnesClassements = GestionnaireDesDonnes.getInstance().getDonnesClassements();
		ArrayList<InformationEssais> tempTank = donnesClassements.getCircuitArrayList();
		tempTank.add(new InformationEssais("Tank", nbTour, Math.round(tempsEcoule / 1000000000.0)));
		GestionnaireDesDonnes.getInstance().getDonnesClassements().setTankArrayList(tempTank);

		GestionnaireDesDonnes.getInstance().getDonnesBadges().setBadgeGtank8(true);

		if (Math.round(tempsEcoule / 1000000000.0) <= 120) {
			if (GestionnaireDesDonnes.getInstance().getDonnesTank().isModeTriche() == false) {
				System.out.println("3 étoiles");
				GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesTank(3);
				menuFinJeu.getBtnSuivant().setEnabled(true);
				menuFinJeu.majEtoilesAffiches(4);

			}

		} else if (Math.round(tempsEcoule / 1000000000.0) <= 180 && Math.round(tempsEcoule / 1000000000.0) > 120) {
			if (GestionnaireDesDonnes.getInstance().getDonnesTank().isModeTriche() == false) {
				GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesTank(2);
				menuFinJeu.getBtnSuivant().setEnabled(true);
				menuFinJeu.majEtoilesAffiches(4);

			}

		} else if (Math.round(tempsEcoule / 1000000000.0) <= 240 && Math.round(tempsEcoule / 1000000000.0) > 180) {
			if (GestionnaireDesDonnes.getInstance().getDonnesTank().isModeTriche() == false) {
				GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesTank(1);
				menuFinJeu.getBtnSuivant().setEnabled(true);
				menuFinJeu.majEtoilesAffiches(4);

			}

		} else {
			if (GestionnaireDesDonnes.getInstance().getDonnesTank().isModeTriche() == false) {
				GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesTank(0);
				menuFinJeu.getBtnSuivant().setEnabled(false);
				menuFinJeu.majEtoilesAffiches(4);
				menuFinJeu.getMessageLabel().setVisible(true);

			}

		}
	}

	/**
	 * Méthode pour faire afficher l'écran de défaite
	 */
	// Liangchen Liu
	private void defaite() {
		menuFinJeu.setVisible(true);
		menuFinJeu.getBtnSuivant().setEnabled(false);

		menuFinJeu.getMessageLabel().setVisible(true);
		menuFinJeu.getMessageLabel().setText("Vous êtes mort!");
		classeTemps.arreter();

	}

	/**
	 * Écouteur pour le menu de fin de jeu
	 */
	// Liangchen Liu
	private void menuFinJeuPropChange() {
		menuFinJeu.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
				case "return":
					firePropertyChange("selecNiveau", 0, 1);
					menuFinJeu.setVisible(false);

					break;

				case "recommencer":
					reini();
					classeTemps.demarrer();
					menuFinJeu.setVisible(false);
					break;

				case "suivant":
					firePropertyChange("selecNiveau", 0, 1);
					menuFinJeu.setVisible(false);

					break;
				case "continuer":
					JOptionPane.showMessageDialog(null,"Vous voulez rejouer à ce jeu? Appuyez sur le bouton 'recommencer'!" , "Info",
							JOptionPane.INFORMATION_MESSAGE);
					requestFocusInWindow();
					break;
				}// fin switch
			}// fin propertyChange
		});

		menuFinJeu.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				classeTemps.demarrer();
				reini();
			}
		});

	}

	/**
	 * Méthode qui gère le carburant du joueur et mets à jour l'étiquette
	 */
	// Jason Yin
	private void gereurCarburant() {
		if (tourJoueur) {
			carburantJoueur -= 1;
			donnesTank.setCarburantJoueur(carburantJoueur);
			lblCarburantJoueur.setText("Niveau de carburant: " + carburantJoueur);
		}
	}

	/**
	 * Méthode qui dessine la trajectoire du projectile et fait en sorte qu'il ne
	 * dépasse pas le sol(contient des bug)
	 */
	// Jason Yin
	private void dessinTrajectoireProjectile() {
		// trajectoireProjectile.setAireTest(terrain.getAireTerrain());
		trajectoireProjectile.dessiner(g2d);
	}

	/**
	 * Méthode qui gère les tours du joueur et de l'intelligence artificielle et
	 * détermine à qui est le tour
	 * 
	 * @return Le statut du tour
	 */
	// Jason Yin
	private boolean gereurTour() {
		tourFini = false;
		if (tireFini && tourJoueur) {
			tourFini = true;
			tireFini = false;
			tourJoueur = false;
			carburantEnnemi = CARBURANT_INIT;
			donnesTank.setCarburantEnnemi(carburantEnnemi);
			actionTourEnnemi();
			System.out.println("tourJ fini");

		} else if (tireFini && !tourJoueur) {
			tourFini = true;
			tireFini = false;
			tourJoueur = true;
			carburantJoueur = CARBURANT_INIT;
			donnesTank.setCarburantJoueur(carburantJoueur);
			spnVitesse.setEnabled(tourJoueur);
			spnmasseJoueur.setEnabled(tourJoueur);
			cboTypeTerrain.setEnabled(tourJoueur);
			cboTypeTirs.setEnabled(tourJoueur);
			if ((tirEparpilleSelectionne && nbTirEparp == 0) || (tirRebondSelectionne && nbTirRebond == 0)) {
				btnTire.setEnabled(false);
			} else {
				btnTire.setEnabled(true);
			}
			if (nbTourDepuisTouche >= 2) {
				nbBouclierEnnemi += 0;
				barreDeVieEtBouclierEnnemi.setNbBouclier(nbBouclierEnnemi);
				barreDeVieEtBouclierEnnemi = new BarreDeVieEtBouclier(nbPointDeVieEnnemi, nbBouclierEnnemi,
						xPointDeVieEnnemi, yPointDeVieEnnemi, xBouclierEnnemi, yBouclierEnnemi, largeurBarreVieBouclier,
						pixelsParMetre);
				repaint();
			}
			chargerLesDonnees();
		}

		if (tourFini) {
			nbTour++;
			donnesTank.setNbTour(nbTour);
			System.out.println("Fin du tour: " + nbTour);
			System.out.println("statut joueur:" + tourJoueur);
			System.out.println("statut ennemi:" + !tourJoueur);
		}
		return tourFini;
	}

	/**
	 * Méthode qui gère le changement du type de tir que le char d'assaut du joueur
	 * tira
	 */
	// Par Michel Vuu
	private void typeDeTirSelectionne() {
		switch (cboTypeTirs.getSelectedIndex()) {
		case (0):
			tirNormalSelectionne = true;
			tirRebondSelectionne = false;
			tirEparpilleSelectionne = false;
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			bouletCanonNormal.setVitesse(vitesse);

			donnesTank.setTirNormalSelectionne(tirNormalSelectionne);
			donnesTank.setTirRebondSelectionne(tirRebondSelectionne);
			donnesTank.setTirEparpilleSelectionne(tirEparpilleSelectionne);

			break;
		case (1):
			tirNormalSelectionne = false;
			tirRebondSelectionne = true;
			tirEparpilleSelectionne = false;
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			bouletCanonRebond.setVitesse(vitesse);

			donnesTank.setTirNormalSelectionne(tirNormalSelectionne);
			donnesTank.setTirRebondSelectionne(tirRebondSelectionne);
			donnesTank.setTirEparpilleSelectionne(tirEparpilleSelectionne);

			break;
		case (2):
			tirNormalSelectionne = false;
			tirRebondSelectionne = false;
			tirEparpilleSelectionne = true;
			vitesse = new SVector3d(((Math.cos(angleTir * Math.PI / 180)) * vitesseInit),
					((Math.sin(angleTir * Math.PI / 180)) * vitesseInit * -1), 0);
			bouletCanonEparpilleGrand.setVitesse(vitesse.add(ajustEparpGrand));
			bouletCanonEparpilleMoyen.setVitesse(vitesse);
			bouletCanonEparpillePetit.setVitesse(vitesse.add(ajustEparpPetit));

			donnesTank.setTirNormalSelectionne(tirNormalSelectionne);
			donnesTank.setTirRebondSelectionne(tirRebondSelectionne);
			donnesTank.setTirEparpilleSelectionne(tirEparpilleSelectionne);

			break;
		}
		// fin switch
	}// fin méthode

	/**
	 * Méthode qui gère le déplacement de l'ennemi
	 */
	// Jason Yin
	public void deplacementEnnemi() {
		Random random = new Random();
		boolean gaucheOuDroite = random.nextBoolean();
		if (gaucheOuDroite && x4 + deplacement < largeurDuComposantEnMetres) {
			x3 += deplacement;
			x4 += deplacement;

			donnesTank.setX3(x3);
			donnesTank.setX4(x4);
		} else {
			x3 -= deplacement;
			x4 -= deplacement;

			donnesTank.setX3(x3);
			donnesTank.setX4(x4);
		}
		carburantEnnemi -= 1;
		donnesTank.setCarburantEnnemi(carburantEnnemi);

		xPointDeVieEnnemi = xTankEnnemi;
		yPointDeVieEnnemi = yTankEnnemi - espaceEntreTankEtBarre;
		xBouclierEnnemi = xTankEnnemi;
		yBouclierEnnemi = yTankEnnemi - espaceEntreTankEtBarre;
		donnesTank.setxPointDeVieEnnemi(xPointDeVieEnnemi);
		donnesTank.setyPointDeVieEnnemi(yPointDeVieEnnemi);
		donnesTank.setxBouclierEnnemi(xBouclierEnnemi);
		donnesTank.setyBouclierEnnemi(yBouclierEnnemi);
		barreDeVieEtBouclierEnnemi = new BarreDeVieEtBouclier(nbPointDeVieEnnemi, nbBouclierEnnemi, xPointDeVieEnnemi,
				yPointDeVieEnnemi, xBouclierEnnemi, yBouclierEnnemi, largeurBarreVieBouclier, pixelsParMetre);
		repaint();
	}

	/**
	 * Méthode qui gère les actions durant tour de l'ennemi
	 */
	// Jason Yin
	public void actionTourEnnemi() {
		Random rand = new Random();

		while (carburantEnnemi > 0) {
			deplacementEnnemi();
		}
		if (carburantEnnemi <= 0) {
			ArrayList<Integer> listeAngle = new ArrayList<Integer>();
			for (int i = 125; i <= 135; i += 5) {
				listeAngle.add(i);
			}
			angleTirEnnemi = listeAngle.get((rand.nextInt(listeAngle.size() - 1)));
			vitesseInitEnnemi = rand.nextInt(20) + 12;
			vitesseEnnemi = new SVector3d(((Math.cos(angleTirEnnemi * Math.PI / 180)) * vitesseInitEnnemi),
					((Math.sin(angleTirEnnemi * Math.PI / 180)) * vitesseInitEnnemi * -1), 0);
			bouletCanonEnnemi.setVitesse(vitesseEnnemi);
			bouletCanonEnnemi.setPosition(new SVector3d(char_ennemi.getX(), char_ennemi.getY(), 0));
			bouletCanonEnnemi.dessiner(g2d);
			demarrer();
		}
	}
}// fin Classe