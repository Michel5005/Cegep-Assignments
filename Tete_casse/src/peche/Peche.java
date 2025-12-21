package peche;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import GestionDesFichiers.GestionnaireDesDonnes;
import application.AppPrincipale7;
import application.Temps;
import classements.DonnesClassements;
import classements.InformationEssais;
import menus.FenetreAideInstructions;
import menus.InterfaceGenerale;
import menus.MenuFinJeu;
import moteurPhysique.MoteurPhysiqueElecMag;
import moteurPhysique.SVector3d;
import peche.gestion_des_images.ImageAimant;
import peche.gestion_des_images.ImageBateau;
import peche.gestion_des_images.ImageCanneAPeche;
import peche.gestion_des_images.ImageMine;
import peche.gestion_des_images.ImagePecheur;
import peche.gestion_des_images.ImagePoisson;
import peche.gestion_des_images.ImageTresor;

/**
 * Classe qui représente le panneau de jeu pour le jeu de pêche.
 * 
 * @author Michel Vuu
 * @author Tin Pham
 * @author Liangchen Liu
 * @author Jason Yin
 * @author Caroline Houle
 */
public class Peche extends InterfaceGenerale implements Runnable {
	/** Le contexte graphique */
	private Graphics2D g2d;
	/** Le trésor */
	private Tresor tresor;
	/** La canne à peche */
	private CanneAPeche canne;
	/** La barre magnétique */
	private BarreElectrique barreElectrique;
	/** Le pecheur */
	private Pecheur pecheur;
	/** Le bateau */
	private Bateau bateau;
	/** Liste de poissons */
	private ArrayList<Poisson> poissonRandom = new ArrayList<Poisson>();
	/** Liste de mines */
	private ArrayList<Mine> mineRandom = new ArrayList<Mine>();
	/** Fleche pour selectionner force */
	private FlecheAnimeForce fleche;
	/** L'aimant */
	private Aimant aimant;

	/** Premiere fois */
	private boolean premiereFois = true;
	/** Position en x de la fleche de selection */
	private double xFleche = 205;
	/** Position en y de la fleche de selection */
	private double yFleche = 580 + 45;
	/** Largeur de la fleche */
	private double largeurFleche = 15;
	/** Hauteur de la fleche */
	private double hauteurFleche = 20;

	/** Position en y du tresor */
	private double yTresor = 5.15;
	/** Largeur du tresor */
	private double largeurTresor = 0.6;
	/** Hauteur du tresor */
	private double hauteurTresor = 0.6;

	/** Position en x de la canne */
	private double xCanne = 4.2;
	/** Position en y de la canne */
	private double yCanne = 0.415;
	/** Largeur de la canne */
	private double largeurCanne = 0.7;
	/** Hauteur de la canne */
	private double hauteurCanne = 0.7;

	/** Position en y de la mine */
	private double yMine = 4.8;
	/** Largeur de la mine */
	private double largeurMine = 1;
	/** Hauteur de la mien */
	private double hauteurMine = 1;

	/** Position en x de la barre */
	private double xBarreElec = 50;
	/** Position en y de la barre */
	private double yBarreElec = 580;
	/** Largeur de la barre */
	private double largeurBarreElec = 325;
	/** Hauteur de la barre */
	private double hauteurBarreElec = 45;

	/** Position en x du pecheur */
	private double xPecheur = 3.7;
	/** Position en y du pecheur */
	private double yPecheur = 0.5;
	/** Largeur du pecheur */
	private double largeurPecheur = 1;
	/** Hauteur du pecheur */
	private double hauteurPecheur = 1;

	/** Position en x du bateau */
	private double xBateau = 1.0;
	/** Position en y du bateau */
	private double yBateau = 0.50;
	/** Largeur du bateau */
	private double largeurBateau = 3.5;
	/** Hauteur du bateau */
	private double hauteurBateau = 1.3;

	/** Charge de l'aimant */
	private double chargeAimant = 0;
	/** Largeur de l'aimant */
	private double largeurAimant = 0.2;
	/** Hauteur de l'aimant */
	private double hauteurAimant = 0.3;
	/** Position initial en x de l'aimant */
	private double xInitial = xCanne + largeurCanne - largeurAimant;
	/** Position initial en y de l'aimant */
	private double yInitial = yCanne + 0.5;
	/** Position initial en x du bout de la ligne de la canne */
	private double xInitCorde = xCanne + largeurCanne - largeurAimant + 0.07;
	/** Position initial en y du bout de la ligne de la canne */
	private double yInitCorde = yCanne + 0.05;
	/** Position de l'aimant */
	private SVector3d posAimant = new SVector3d(xInitial, yInitial, 0);

	/** Instructions */
	private String instructions = "- L'objectif du jeu: Pêcher le trésor afin de gagner le jeu.\n "
			+ "- Cliquer une fois afin de démarrer l'animation qui bascule la ligne de la canne à pêche.\n"
			+ "- Cliquer une seconde fois pour faire arrêter l'animation qui bascule la ligne de la canne à pêche\n"
			+ "- Dans le cercle en bas à droite, tourner dans le sens horaire afin de faire descendre la ligne de pêche\n "
			+ "- Ensuite, choissisez la force électrique que l'hameçon possède\n Rouge = Force électrique faible \n Jaune = Force électrique moyen\n Vert = Force électrique forte\n"
			+ "- Dépendant de la force électrique sélectionné, l'hameçon attira des objets se trouvant dans l'eau (poissons,mines,trésor)\n "
			+ "- Puis, dans le cercle en bas à droite, tourner dans le sens antihoraire afin de faire remonter la ligne de pêche\n"
			+ "- Si vous pêchez le trésor, vous gagnez le jeu!\n"
			+ "- Si vous rentrez en contact avec une mine, vous perdrez le jeu :(\n"
			+ "- Si vous pêchez un poisson, vous aurez 5 secondes d'enlevé au temps écoulé total qui se situe en haut à droite";
	/** Largeur du composant en metre */
	private double largeurDuComposantEnMetres = 13.00;
	/** Hauteur du composant en metre */
	private double hauteurDuComposantEnMetres;
	/** Pixel par metre */
	private double pixelsParMetre;
	/** Donnees qui sont sauvegarde */
	private DonnesPeche donnesPeche;
	/** Etiquette pour le champ electrique en x */
	private JLabel lblChampElectriqueX;
	/** Etiquette pour le champ electrique en y */
	private JLabel lblChampElectriqueY;
	/** Etiquette pour la valeur du champ electrique en y */
	private JLabel lblValeurChampEY;
	/** Etiquette pour la valeur du champ electrique en x */
	private JLabel lblValeurChampEX;
	/** Etiquette pour la force electrique de la mine en y */
	private JLabel lblForceElectriqueMineY;
	/** Etiquette pour le force electrique de la mine en x */
	private JLabel lblForceElectriqueMineX;
	/** Etiquette pour la valeur de la force electrique en x */
	private JLabel lblValeurForceEMineX;
	/** Etiquette pour la valeur de la force electrique en x */
	private JLabel lblValeurForceEMineY;
	/** Etiquette pour la force electrique du tresor en x */
	private JLabel lblForceElectriqueTresorX;
	/** Etiquette pour la force electrique du tresor en y */
	private JLabel lblForceElectriqueTresorY;
	/** Etiquette pour la valeur de la force electrique en x */
	private JLabel lblValeurForceETresorX;
	/** Etiquette pour la valeur de la force electrique en y */
	private JLabel lblValeurForceETresorY;
	/** Etiquette pour la masse d'une mine */
	private JLabel lblMasseMine;
	/** Etiquette pour la valeur de la masse d'une mine */
	private JLabel lblValeurMasseMine;
	/** Etiquette pour la masse du tresor */
	private JLabel lblMasseTresor;
	/** Etiquette pour la valeur de la masse du tresor */
	private JLabel lblValeurMasseTresor;
	/** Etiquette pour la tension en x */
	private JLabel lblTensionCordeX;
	/** Etiquette pour la valeur de la tension en x */
	private JLabel lblValeurTensionCordeX;
	/** Etiquette pour la tension en y */
	private JLabel lblTensionCordeY;
	/** Etiquette pour la valeur de la tension en y */
	private JLabel lblValeurTensionCordeY;
	/** Etiquette pour la force gravitationnelle en x */
	private JLabel lblForceGravitationnelleX;
	/** Etiquette pour la valeur de la force gravitationnelle en x */
	private JLabel lblValeurForceGravX;
	/** Etiquette pour la force gravitationnelle en y */
	private JLabel lblForceGravitationnelleY;
	/** Etiquette pour la valeur de la force gravitationnelle en y */
	private JLabel lblValeurForceGravY;
	/** Etiquette qui dit l'objectif du jeu */
	private JLabel lblInfoEtudiant;
	/** Position du séparateur en x en pixels */
	private double xMaxSeparator = 1125;

	/** Intervalle des valeurs de x du poisson */
	private double rangeXPoisson;
	/** Valeur minimum de y du poisson */
	private double minY;
	/** Valeur maximum de y du poisson */
	private double maxY;
	/** Intervalle des valeurs de y du poisson */
	private double rangeYPoisson;
	/** Valeur de la position en x aléatoire */
	private double randXPoisson;
	/** Valeur de la position en y aléatoire */
	private double randYPoisson;
	/** Regarde si c'est en cours d'animation ou non */
	private boolean enCoursDAnimation = false;
	/** Le temps total */
	private double tempsTotal;
	/** Le temps de repos de l'animation */
	private long tempsDuSleep = 20;
	/** Le delta t */
	private double deltaT = 0.05;

	/** La position maximale en x du séparateur en mètres */
	private double xMaxSeparatorMetres = 11.25;

	/** La position maximale en y du séparateur en mètres */
	private double yMaxSeperatorMetres = 5.6;

	/** Fin de l'echelle */
	private double xMaxEchelle;
	/** Debut de l'echelle */
	private double xMinEchelle;
	/** Milieu de l'echelle */
	private double xMilieuEchelle;

	/** Direction de la fleche pour la selection de la force electrique */
	private double direction = 1;

	/** Lien vers l'image du poisson */
	private URL lienImagePoisson = getClass().getClassLoader().getResource("poisson.png");
	/** Lien vers l'image du tresor */
	private URL lienImageTresor = getClass().getClassLoader().getResource("trésor.png");
	/** Lien vers l'image de la canne */
	private URL lienImageCanne = getClass().getClassLoader().getResource("canne_a_peche.png");
	/** Lien vers l'image de la mine */
	private URL lienImageMine = getClass().getClassLoader().getResource("obstacle_sous_eau.png");
	/** Lien vers l'image du pecheur */
	private URL lienImagePecheur = getClass().getClassLoader().getResource("pecheur.png");
	/** Lien vers l'image du bateau */
	private URL lienImageBateau = getClass().getClassLoader().getResource("bateau.png");
	/** Lien vers l'image de l'aimant */
	private URL lienImageAimant = getClass().getClassLoader().getResource("crochet.png");

	/** Image du bateau */
	private ImageBateau imageBateau;
	/** Image de la canne */
	private ImageCanneAPeche imageCanne;
	/** Image de la mine */
	private ImageMine imageMine;
	/** Image du pecheur */
	private ImagePecheur imagePecheur;
	/** Image du tresor */
	private ImageTresor imageTresor;
	/** Image du poisson */
	private ImagePoisson imagePoisson;
	/** Image de l'aimant */
	private ImageAimant imageAimant;
	/** Valeur minimum de x du tresor */
	private double minXTresor;
	/** Valeur maximum de x du tresor */
	private double maxXTresor;
	/** Intervalle des x du tresor */
	private double rangeXTresor;
	/** Valeur du x du tresor aleatoire */
	private double randXTresor;
	/** Valeur minimum de x */
	private double minXMine;
	/** Valeur maximum de x */
	private double maxXMine;
	/** Intervalle des valeurs de x */
	private double rangeXMine;
	/** Valeur du x de la mine aléatoire */
	private double randXMine;
	/** Le x du poisson quand il depasse le x du separateur */
	private double xPoisson = -0.7;
	/** Poisson */
	private Poisson poisson;
	/** Centre en x du spinner */
	private double centreX = 2;
	/** Centre en y du spinner */
	private double centreY = 1.27;
	/** Rayon du spinner */
	private double rayon = 0.12;
	/** Facteur de redimesionnement */
	private double facteurRedim = 500;
	/** Angle du point rouge */
	private double angle = 0;
	/** Angle total */
	private double angleTotal = 0;
	/** Nombre de tours fait par l'utilisateur */
	private double tour = 0;
	/** Regarde si le point rouge tourne */
	private boolean tourne = false;
	/** La direction dans lequel on tourne */
	private int directionSpinner = 0;
	/** Etiquette pour le temps */
	private JLabel lblTemps;
	/** Classe temps */
	private Temps classeTemps;
	/** Temps */
	private long tempsEcoule;
	/** Largeur du poisson */
	private double largeurPoisson = 0.7;
	/** Hauteur du poisson */
	private double hauteurPoisson = 0.5;
	/** Masse du poisson */
	private double massePoisson = 4;
	/** Mine */
	private Mine mine;

	/** Nombre de poissons */
	private int nbPoisson = 10;
	/** Nombre de mines */
	private int nbMine = 4;
	/** Rectangle blanc pour cacher le dépassement dans le zone des données */
	private Rectangle2D.Double max;
	/** Minimum de vitesse */
	private double minVitesse;
	/** Maximum de vitesse */
	private double maxVitesse;
	/** Intervalle de vitesse */
	private double rangeVitesse;
	/** Valeur de vitesse aleatoire */
	private double randXVitesse;
	/** L'eau */
	private Rectangle2D.Double eau;
	/** Position d'un poisson */
	private SVector3d posPoisson;
	/** Vitesse d'un poisson */
	private SVector3d vitesse;
	/** Charge d'un poisson */
	private double chargePoisson = -300;
	/** Position d'une mine */
	private SVector3d posMine;
	/** Vitesse d'une mine */
	private SVector3d vitesseMine;
	/** Masse d'une mine */
	private double masseMine = 12;
	/** Charge d'une mine */
	private double chargeMine = -80;

	/** Taille de fenetre */
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	/** Taille de la tete de fleche pour le vecteur vitesse des poissons */
	private double tailleTeteFleche = 0.1;
	/** Recueillie le nombre de clic que l'utilisateur fait */
	private double clicks;
	/** Fenêtre pour quand un jeu est gagné */
	private MenuFinJeu menuFinJeu;
	/** Le nombre de tours fait avec le spinner */
	private int nbTours;
	/** La masse du trésor */
	private double masseTresor = 34;
	/** La charge du trésor */
	private double chargeTresor = -200;
	/** La corde de la canne à pêche */
	private Line2D.Double ligne;

	private SVector3d posTresor;

	private SVector3d vitesseTresor;

	private double angleInit;
	private double xDebut;
	private double yDebut;
	private double rayonArc;
	private double deplacementSurArc;
	private double tempsEntreImages;
	private double angleArc;
	private boolean monte = true;
	private double xFin;
	private double yFin;

	private static final int MIN_ANGLE = 45;
	private static final int MAX_ANGLE = 135;

	private double vitesseArc = 10;
	private double deplacement = 0.008;

	/**
	 * Création du panneau.
	 */
	// Michel Vuu
	public Peche() {

		donnesPeche = GestionnaireDesDonnes.getInstance().getDonnesPeche();
		classeTemps = new Temps(3);
		chargerLesDonnees();

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				Point p = e.getPoint();
				double x = p.x / facteurRedim;
				double y = p.y / facteurRedim;

				double dx = x - centreX;
				double dy = y - centreY;
				double dist = Math.sqrt(dx * dx + dy * dy);

				if (dist < rayon) {

					double nouvelleAngle = Math.atan2(dy, dx);
					if (tourne) {
						double deltaAngle = nouvelleAngle - angle;

						if (deltaAngle > Math.PI) {
							deltaAngle -= Math.PI * 2;
						} else if (deltaAngle < -Math.PI) {
							deltaAngle += Math.PI * 2;
						} // fin si

						int nouvelleDirection;
						if (deltaAngle > 0) {
							nouvelleDirection = 1;
						} else {
							nouvelleDirection = -1;
						} // fin si

						if (directionSpinner != 0 && directionSpinner != nouvelleDirection) {
							deltaAngle *= -1;
						} // fin si
						angleTotal += deltaAngle;
						tour += deltaAngle * rayon;

						deplacementFilEtAimant(deltaAngle * rayon);
						// System.out.println("Delta angle : " + deltaAngle);
						// System.out.println("Position : " + aimant.getX() + " , " + aimant.getY());

					} else {
						tourne = true;
					} // fin si
					angle = nouvelleAngle;
				} else {
					tourne = false;
					directionSpinner = 0;
				} // fin si

				repaint();
			}// fin méthode

		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if (barreElectrique.aireBarre().contains(e.getPoint())) {
					clicks++;
				} // Fin si

				if (clicks == 3) {
					clicks = 0;
				} // Fin si

			}// fin méthode

			@Override
			public void mouseReleased(MouseEvent e) {

				Point p = e.getPoint();
				double x = p.x / facteurRedim;
				double y = p.y / facteurRedim;

				double dx = x - centreX;
				double dy = y - centreY;
				double dist = Math.sqrt(dx * dx + dy * dy);

				if (dist > rayon) {
					tourne = false;
				} // fin si
			}// fin méthode

		});

		instProf();

		sauv();

		aide();

		exProf();

		infoJoueur();

		setBackground(Color.white);
		setPreferredSize(new Dimension(1300, 700));

		lblChampElectriqueX = new JLabel("Champ électrique (x) :");
		lblChampElectriqueX.setBounds(1131, 57, 138, 14);
		add(lblChampElectriqueX);

		lblChampElectriqueY = new JLabel("Champ électrique (y) :");
		lblChampElectriqueY.setBounds(1131, 92, 138, 14);
		add(lblChampElectriqueY);

		lblValeurChampEX = new JLabel("0 N/C");
		lblValeurChampEX.setBounds(1131, 76, 34, 14);
		add(lblValeurChampEX);

		lblValeurChampEY = new JLabel("0 N/C");
		lblValeurChampEY.setBounds(1131, 112, 34, 14);
		add(lblValeurChampEY);

		lblForceElectriqueMineX = new JLabel("Force électrique des mines:");
		lblForceElectriqueMineX.setBounds(1131, 134, 202, 14);
		add(lblForceElectriqueMineX);

		lblForceElectriqueMineY = new JLabel("Force électrique des mines:");
		lblForceElectriqueMineY.setBounds(1131, 180, 202, 14);
		add(lblForceElectriqueMineY);

		lblValeurForceEMineX = new JLabel("0 N");
		lblValeurForceEMineX.setBounds(1176, 205, 34, 14);
		add(lblValeurForceEMineX);

		lblValeurForceEMineY = new JLabel("0 N");
		lblValeurForceEMineY.setBounds(1176, 155, 34, 14);
		add(lblValeurForceEMineY);

		lblForceElectriqueTresorY = new JLabel("Force électrique du trésor:");
		lblForceElectriqueTresorY.setBounds(1131, 280, 169, 14);
		add(lblForceElectriqueTresorY);

		lblForceElectriqueTresorX = new JLabel("Force électrique du trésor:");
		lblForceElectriqueTresorX.setBounds(1131, 235, 202, 14);
		add(lblForceElectriqueTresorX);

		lblValeurForceETresorX = new JLabel("0 N");
		lblValeurForceETresorX.setBounds(1176, 260, 34, 14);
		add(lblValeurForceETresorX);

		lblValeurForceETresorY = new JLabel("0 N");
		lblValeurForceETresorY.setBounds(1176, 297, 34, 14);
		add(lblValeurForceETresorY);

		lblMasseMine = new JLabel("Masse des mines:");
		lblMasseMine.setBounds(1131, 322, 97, 14);
		add(lblMasseMine);

		lblValeurMasseMine = new JLabel((int) masseMine + " kg");
		lblValeurMasseMine.setBounds(1235, 319, 34, 21);
		add(lblValeurMasseMine);

		lblMasseTresor = new JLabel("Masse du trésor :");
		lblMasseTresor.setBounds(1131, 347, 97, 14);
		add(lblMasseTresor);

		lblValeurMasseTresor = new JLabel((int) masseTresor + " kg");
		lblValeurMasseTresor.setBounds(1235, 344, 34, 21);
		add(lblValeurMasseTresor);

		lblTensionCordeX = new JLabel("Tension de la corde (x) :");
		lblTensionCordeX.setBounds(1131, 372, 138, 14);
		add(lblTensionCordeX);

		lblValeurTensionCordeX = new JLabel("0 N");
		lblValeurTensionCordeX.setBounds(1131, 398, 34, 14);
		add(lblValeurTensionCordeX);

		lblTensionCordeY = new JLabel("Tension de la corde (y) :");
		lblTensionCordeY.setBounds(1131, 423, 138, 14);
		add(lblTensionCordeY);

		lblValeurTensionCordeY = new JLabel("0 N");
		lblValeurTensionCordeY.setBounds(1131, 444, 34, 14);
		add(lblValeurTensionCordeY);

		lblForceGravitationnelleX = new JLabel("Force Gravitationnelle (x):");
		lblForceGravitationnelleX.setBounds(1131, 469, 138, 14);
		add(lblForceGravitationnelleX);

		lblValeurForceGravX = new JLabel("0 N");
		lblValeurForceGravX.setBounds(1131, 491, 34, 14);
		add(lblValeurForceGravX);

		lblForceGravitationnelleY = new JLabel("Force Gravitationnelle (y):");
		lblForceGravitationnelleY.setBounds(1131, 516, 138, 14);
		add(lblForceGravitationnelleY);

		lblValeurForceGravY = new JLabel("98 N");
		lblValeurForceGravY.setBounds(1131, 537, 34, 14);
		add(lblValeurForceGravY);

		lblInfoEtudiant = new JLabel("Pêche le trésor afin de gagner le jeu");
		lblInfoEtudiant.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInfoEtudiant.setEnabled(true);
		lblInfoEtudiant.setVisible(false);
		lblInfoEtudiant.setBounds(50, 188, 317, 45);
		add(lblInfoEtudiant);

		lblTemps = new JLabel();
		lblTemps.setText("Temps écoulé : " + tempsEcoule + " s");
		lblTemps.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTemps.setFont(AppPrincipale7.interBold.deriveFont(12f));
		lblTemps.setBounds(852, 8, 249, 21);
		add(lblTemps);

		JLabel lblChampElec = new JLabel("Champ électrique");
		lblChampElec.setBounds(0, 562, 130, 14);
		add(lblChampElec);

		JLabel lblWidget = new JLabel("Tourner pour faire remonter");
		lblWidget.setBounds(759, 580, 169, 14);
		add(lblWidget);

		JLabel lblEnYMines = new JLabel("En Y:");
		lblEnYMines.setBounds(1131, 205, 53, 14);
		add(lblEnYMines);

		JLabel lblEnYTresor = new JLabel("En Y:");
		lblEnYTresor.setBounds(1131, 297, 53, 14);
		add(lblEnYTresor);

		JLabel lblEnXMines = new JLabel("En X:");
		lblEnXMines.setBounds(1131, 155, 53, 14);
		add(lblEnXMines);

		JLabel lblEnXTresor = new JLabel("En X:");
		lblEnXTresor.setBounds(1131, 260, 53, 14);
		add(lblEnXTresor);

		canne = new CanneAPeche(xCanne, yCanne, largeurCanne, hauteurCanne, pixelsParMetre);

		barreElectrique = new BarreElectrique(xBarreElec, yBarreElec, largeurBarreElec, hauteurBarreElec,
				pixelsParMetre);

		pecheur = new Pecheur(xPecheur, yPecheur, largeurPecheur, hauteurPecheur, pixelsParMetre);

		bateau = new Bateau(xBateau, yBateau, largeurBateau, hauteurBateau, pixelsParMetre);

		imageBateau = new ImageBateau(xBateau, yBateau, largeurBateau, hauteurBateau, lienImageBateau);

		imageCanne = new ImageCanneAPeche(xCanne, yCanne, largeurCanne, hauteurCanne, lienImageCanne);

		imagePecheur = new ImagePecheur(xPecheur, yPecheur, largeurPecheur, hauteurPecheur, lienImagePecheur);

		fleche = new FlecheAnimeForce(xFleche, yFleche, hauteurFleche, largeurFleche);

		aimant = new Aimant(posAimant, chargeAimant, hauteurAimant, largeurAimant, pixelsParMetre);

		ligne = new Line2D.Double(xInitCorde, yInitCorde, aimant.getX() + largeurAimant / 2, aimant.getY());

		menuFinJeu = new MenuFinJeu();
		menuFinJeuPropChange();

	}// fin constructeur

	/**
	 * On redéfinit la methode de dessin
	 * 
	 * @param g Le contexte graphique
	 */
	// Michel Vuu
	public void paintComponent(Graphics g) {
		donnesPeche = GestionnaireDesDonnes.getInstance().getDonnesPeche();
		chargerLesDonnees();

		super.paintComponent(g);
		g2d = (Graphics2D) g;

		if (premiereFois) {
			pixelsParMetre = getWidth() / largeurDuComposantEnMetres;
			hauteurDuComposantEnMetres = getHeight() / pixelsParMetre;
			max = new Rectangle2D.Double(xMaxSeparator, 0, getWidth() - xMaxSeparator, yMaxSeperatorMetres * 100);
			eau = new Rectangle2D.Double(0, 165, xMaxSeparator, 398);

			positionPoisson();
			positionMine();
			positionTresor();
			// positionAimant();
			premiereFois = false;

		} // fin si

		g2d.setColor(Color.black);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(new Color(95, 160, 245));
		g2d.fill(eau);

		g2d.setColor(Color.black);

		dessinePoisson();
		dessineMine();
		dessineTresor();

		barreElectrique.setPixelsParMetre(pixelsParMetre);
		bateau.setPixelsParMetre(pixelsParMetre);
		pecheur.setPixelsParMetre(pixelsParMetre);
		canne.setPixelsParMetre(pixelsParMetre);
		imageBateau.setPixelsParMetre(pixelsParMetre);
		imageCanne.setPixelsParMetre(pixelsParMetre);
		imagePecheur.setPixelsParMetre(pixelsParMetre);

		chargerLesDonnees();

		barreElectrique.dessiner(g2d);

		fleche.dessiner(g2d);

		imageCanne.dessiner(g2d);
		imageBateau.dessiner(g2d);
		imagePecheur.dessiner(g2d);

		Path2D.Double echelle = creerEchelle();
		g2d.setColor(Color.magenta);
		g2d.draw(echelle);

		g2d.setColor(Color.BLACK);
		g2d.drawOval((int) ((centreX - rayon) * facteurRedim), (int) ((centreY - rayon) * facteurRedim),
				(int) (rayon * 2 * facteurRedim), (int) (rayon * 2 * facteurRedim));

		// System.out.println(((int) tour + " / " + (int) maxTours));

		g2d.setColor(Color.RED);
		Point p = getMousePosition();

		if (p != null) {
			double x = p.x / facteurRedim;
			double y = p.y / facteurRedim;
			double dx = x - centreX;
			double dy = y - centreY;
			double dist = Math.sqrt(dx * dx + dy * dy);

			if (dist < rayon) {
				g2d.fillOval(p.x - 5, p.y - 5, 10, 10);
			}
			// System.out.println("Angle: " + Math.toDegrees(angle));
			// System.out.println("Angle total: " + Math.toDegrees(angleTotal));
		} // fin si

		flecheVecVitPoisson();
		dessineAimant();

		g2d.setColor(Color.white);
		g2d.fill(max);

		if (clicks == 1) {
			mouvementFleche();

		} // fin si

		if (clicks == 2) {
			forceSelectionner();

		} // fin si

		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);

		g2d.setColor(Color.black);
		g2d.draw(mat.createTransformedShape(ligne));

		// System.out.println("Aimant : " + aimant.getPosition());
		// System.out.println("Tresor : " + tresor.getPosition());

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
			classeTemps.arreter();

		} // fin si

	}// fin méthode

	/**
	 * Méthode qui permet de charger les données du niveau
	 */
	// Liangchen Liu
	private void chargerLesDonnees() {
		// tour = donnesPeche.getNbTours();

		nbTours = donnesPeche.getNbTours();
	}// fin méthode

	/**
	 * Méthode pour faire sauvegarder le niveau lors du clic sur le bouton.
	 */
	// Liangchen Liu
	private void sauv() {
		btnSauvegarder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {

				donnesPeche.setTempsEcoule(tempsEcoule);

				GestionnaireDesDonnes.getInstance().setNiveau(3);
				GestionnaireDesDonnes.getInstance().setDonnesPeche(donnesPeche);
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
		aimant.setPosition(posAimant);
		clicks = 0;
		GestionnaireDesDonnes.getInstance().setDonnesPeche(donnesPeche);

		repaint();
		requestFocusInWindow();
	}// fin méthode

	/**
	 * Méthode pour ouvrir le menu avec des instructions lors du clic sur le bouton
	 */
	// Liangchen Liu
	private void aide() {
		btnAide.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				btnAide.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						instructions();
						requestFocusInWindow();
					}// fin méthode
				});// fin listener

			}// fin méthode
		});// fin listener

	}// fin méthode

	/**
	 * Ouvre la fenêtre d'instructions
	 */
	// Liangchen Liu
	public void instructions() {
		FenetreAideInstructions fenetreAideInstructions = new FenetreAideInstructions(
				new String[] { "Instructions/Peche/page1.png", "Instructions/Peche/page2.png" });

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
	 * Méthode pour ouvrir le menu d'instructions au professeur avec la touche P
	 */
	// Liangchen Liu
	private void instProf() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					JOptionPane.showMessageDialog(null, instructions, "Fonctionnalités présentes",
							JOptionPane.INFORMATION_MESSAGE);
					requestFocusInWindow();
				} // fin if
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
	 * Méthode qui créer l'échelle pour estimer le nombre de mètre que possède le
	 * composant
	 * 
	 * @return un Path2D qui représente le nombre de pixel par mètre
	 */
	// Jason Yin
	private Path2D.Double creerEchelle() {
		Path2D.Double echelle = new Path2D.Double();
		xMaxEchelle = 130 / pixelsParMetre;
		xMinEchelle = 30 / pixelsParMetre;
		xMilieuEchelle = ((130 + 30) / 2) / pixelsParMetre;

		echelle.moveTo((xMaxSeparatorMetres - xMinEchelle) * pixelsParMetre, 538);
		echelle.lineTo((xMaxSeparatorMetres - xMinEchelle) * pixelsParMetre, 545);
		echelle.lineTo((xMaxSeparatorMetres - xMaxEchelle) * pixelsParMetre, 545);
		echelle.lineTo((xMaxSeparatorMetres - xMaxEchelle) * pixelsParMetre, 538);
		echelle.lineTo((xMaxSeparatorMetres - xMaxEchelle) * pixelsParMetre, 545);
		echelle.lineTo((xMaxSeparatorMetres - xMilieuEchelle) * pixelsParMetre, 545);
		echelle.lineTo((xMaxSeparatorMetres - xMilieuEchelle) * pixelsParMetre, 538);
		echelle.lineTo((xMaxSeparatorMetres - xMilieuEchelle) * pixelsParMetre, 545);

		return echelle;
	}// fin méthode

	/**
	 * Méthode qui s'occupe à dessiner l'aimant
	 */
	// Tin Pham
	private void dessineAimant() {
		imageAimant = new ImageAimant(aimant.getX(), aimant.getY(), largeurAimant, hauteurAimant, lienImageAimant);
		imageAimant.setPixelsParMetre(pixelsParMetre);
		aimant.setPixelsParMetre(pixelsParMetre);
		aimant.dessiner(g2d);
		imageAimant.dessiner(g2d);

		// aimant.dessiner(g2d);
	}// fin méthode

	/**
	 * Méthode qui s'occupe de placer l'aimant
	 */
	// Tin Pham
	private void positionAimant() {
		posAimant = new SVector3d(xInitial, yInitial, 0);
	}// fin méthode

	/**
	 * Méthode qui s'occupe de dessiner la ligne de la canne à peche
	 */
	// Tin Pham
	private void ligneCanne() {
		ligne = new Line2D.Double(xInitCorde, yInitCorde, aimant.getX() + largeurAimant / 2, aimant.getY());

		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);

		g2d.setColor(Color.black);
		g2d.draw(mat.createTransformedShape(ligne));

		aimant.setPixelsParMetre(pixelsParMetre);
	}// fin méthode

	/**
	 * Méthode qui s'occupe à dessiner les poissons
	 */
	// Michel Vuu
	private void dessinePoisson() {
		for (Poisson autre : poissonRandom) {
			imagePoisson = new ImagePoisson(autre.getX(), autre.getY(), largeurPoisson, hauteurPoisson,
					lienImagePoisson);
			imagePoisson.setPixelsParMetre(pixelsParMetre);
			imagePoisson.dessiner(g2d);

		} // fin pour
	}// fin méthode

	/**
	 * Méthode qui s'occupe de placer les poissons
	 */
	// Michel Vuu
	private void positionPoisson() {
		while (poissonRandom.size() < nbPoisson) {

			rangeXPoisson = 10;
			minY = 2.5;
			maxY = 3;
			rangeYPoisson = maxY - minY + 1;
			randXPoisson = (Math.random() * rangeXPoisson);
			randYPoisson = (Math.random() * rangeYPoisson) + minY;

			minVitesse = 0.3;
			maxVitesse = 1.2;
			rangeVitesse = maxVitesse - minVitesse + 1;
			randXVitesse = (Math.random() * rangeVitesse) + minVitesse;
			vitesse = new SVector3d(randXVitesse, 0, 0);

			posPoisson = new SVector3d(randXPoisson, randYPoisson, 0);

			poisson = new Poisson(posPoisson, vitesse, largeurPoisson, hauteurPoisson, massePoisson, chargePoisson,
					pixelsParMetre);
			boolean collision = false;
			for (Poisson autre : poissonRandom) {
				if (poisson.getPoisson().intersects(autre.getPoisson())) {
					collision = true;
					break;
				} // fin si
			} // fin pour
			if (!collision) {
				poissonRandom.add(poisson);
			} // fin si
		} // fin while
	}// fin méthode

	/**
	 * Méthode qui s'occupe de dessiner les mines
	 */
	// Michel Vuu
	private void dessineMine() {
		for (Mine autre : mineRandom) {
			imageMine = new ImageMine(autre.getX(), autre.getY(), largeurMine, hauteurMine, lienImageMine);
			imageMine.setPixelsParMetre(pixelsParMetre);
			imageMine.dessiner(g2d);

		} // fin pour
	}// fin méthode

	/**
	 * Méthode qui s'occupe de placer les mines
	 */
	// Michel Vuu
	private void positionMine() {
		while (mineRandom.size() < nbMine) {
			minXMine = 2;
			maxXMine = 9;
			rangeXMine = maxXMine - minXMine + 1;
			randXMine = (Math.random() * rangeXMine) + minXMine;

			posMine = new SVector3d(randXMine, yMine, 0);

			vitesseMine = new SVector3d(0, 0, 0);

			mine = new Mine(posMine, vitesseMine, largeurMine, hauteurMine, masseMine, chargeMine, pixelsParMetre);
			boolean collision = false;
			Area mineAire = new Area(mine.getMine());
			for (Mine autre : mineRandom) {
				Area mineAutre = new Area(autre.getMine());
				mineAutre.intersect(mineAire);
				if (!mineAutre.isEmpty()) {
					collision = true;
					break;
				} // fin si
			} // fin pour
			if (!collision) {
				mineRandom.add(mine);
			} // fin si
		} // fin while

	}// fin méthode

	/**
	 * Méthode qui s'occupe de placer le trésor
	 */
	// Michel Vuu
	private void positionTresor() {

		boolean estValide = false;
		while (!estValide) {
			// minXTresor = 3.5;
			// maxXTresor = 7.5;
			// rangeXTresor = maxXTresor - minXTresor + 1;
			// randXTresor = (Math.random() * rangeXTresor) + minXTresor;

			posTresor = new SVector3d(xInitial, yTresor, 0);
			vitesseTresor = new SVector3d(0, 0, 0);

			tresor = new Tresor(posTresor, vitesseTresor, largeurTresor, hauteurTresor, masseTresor, chargeTresor,
					pixelsParMetre);

			boolean collision = false;
			for (Mine autre : mineRandom) {
				if (tresor.getTresor().intersects(autre.getMine())) {
					collision = true;
					break;
				} // fin si
			} // fin pour

			if (!collision) {
				estValide = true;

			} // fin si

		} // fin while
	}// fin méthode

	/**
	 * Méthode qui s'occupe de dessiner le trésor
	 */
	// Michel Vuu
	private void dessineTresor() {
		imageTresor = new ImageTresor(xInitial, yTresor, largeurTresor, hauteurTresor, lienImageTresor);
		imageTresor.setPixelsParMetre(pixelsParMetre);
		imageTresor.dessiner(g2d);
	}// fin méthode

	/**
	 * Méthode qui se charge du déplacement des poissons
	 */
	// Michel Vuu
	private void mouvementPoisson() {

		for (Poisson poisson : poissonRandom) {
			poisson.deplacement();

			if (poisson.getX() > xMaxSeparatorMetres) {
				poisson.setX(-largeurPoisson);
			} // fin si
		} // fin pour
		repaint();
	}// fin méthode

	/**
	 * Demande l'arret du thread (prochain tour de boucle)
	 */
	// Caroline Houle
	public void arreter() {
		enCoursDAnimation = false;
	}// fin méthode

	/**
	 * Demarre le thread s'il n'est pas deja demarre
	 */
	// Par Caroline Houle
	public void demarrer() {
		if (!enCoursDAnimation) {
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
		} // fin if
	}// fin methode

	/**
	 * Méthode qui effectue l'animation des poissons
	 */
	// Michel Vuu
	@Override
	public void run() {

		while (enCoursDAnimation) {

			mouvementPoisson();

			if (clicks == 2) {
				// calculerUneIterationPhysique(deltaT);
				System.out.println("calcul");
			}

			// tempsPoisson();

			repaint();

			try {
				Thread.sleep(tempsDuSleep);

			} catch (InterruptedException e) {
				e.printStackTrace();
			} // fin try-catch
		} // fin while
		System.out.println("Le thread est mort...!");
	}// fin méthode

	/**
	 * Déplace la flèche horizontalement à une vitesse donnée, tant qu'elle ne
	 * dépasse pas les limites de la barre électrique.
	 */
	// Tin Pham
	private void mouvementFleche() {
		double deplacement = 0.55;
		double vitesse = 1.5;
		double xTemp = fleche.getX();

		if ((fleche.getX() + largeurFleche / 2) >= (barreElectrique.getX() + largeurBarreElec)) {
			direction = -1;
		} else if ((fleche.getX() + largeurFleche / 2) <= barreElectrique.getX()) {
			direction = 1;
		} // fin si
		fleche.setX(xTemp += direction * deplacement * vitesse);
		repaint();

	}// fin méthode

	/**
	 * Effectue un mouvement vertical de l'hameçon en augmentant sa position sur
	 * l'axe des ordonnées. Met à jour la position de l'aimant en conséquence et
	 * redessine l'interface graphique.
	 */
	// Tin Pham
	private void mouvementHameconVertical() {
		double deplacement = 0.003;
		double yTemp = aimant.getY();

		aimant.setY(yTemp += deplacement * direction);
		ligneCanne();
		repaint();
	}

	/**
	 * Calcule une itération physique en utilisant les lois de l'électromagnétisme
	 * pour mettre à jour les positions des poissons et des mines.
	 * 
	 * @param deltaT le temps écoulé depuis la dernière itération.
	 */
	// Tin Pham
	private void calculerUneIterationPhysique(double deltaT) {
		SVector3d forceP, accelP, vitP, posP;
		SVector3d forceM, accelM, vitM, posM;
		SVector3d forceT, accelT, vitT, posT;

		/*
		 * for (Poisson poisson : poissonRandom) { forceP =
		 * MoteurPhysiqueElecMag.calculForceElectrique(aimant, poisson); accelP =
		 * MoteurPhysiqueElecMag.calculAcceleration(forceP, aimant, poisson); vitP =
		 * MoteurPhysiqueElecMag.calculVitesse(deltaT, poisson.getVitesse(), accelP);
		 * posP = MoteurPhysiqueElecMag.calculPosition(deltaT, poisson.getPosition(),
		 * vitP);
		 * 
		 * poisson.setVitesse(vitP); poisson.setPosition(posP); } // fin pour for (Mine
		 * mine : mineRandom) {
		 * 
		 * forceM = MoteurPhysiqueElecMag.calculForceElectrique(aimant, mine); accelM =
		 * MoteurPhysiqueElecMag.calculAcceleration(forceM, aimant, mine); vitM =
		 * MoteurPhysiqueElecMag.calculVitesse(deltaT, mine.getVitesse(), accelM); posM
		 * = MoteurPhysiqueElecMag.calculPosition(deltaT, mine.getPosition(), vitM);
		 * 
		 * mine.setPosition(posM); lblValeurForceEMineX.setText(forceM.getX() + "N"); }
		 * // fin pour
		 */
		forceT = MoteurPhysiqueElecMag.calculForceElectrique(aimant, tresor);
		accelT = MoteurPhysiqueElecMag.calculAcceleration(forceT, aimant, tresor);
		vitT = MoteurPhysiqueElecMag.calculVitesse(deltaT, tresor.getVitesseInit(), accelT);
		posT = MoteurPhysiqueElecMag.calculPosition(deltaT, tresor.getPosition(), vitT);

		System.out.println("Force : " + forceT);
		System.out.println("Accel : " + accelT);
		System.out.println("Vitesse : " + vitT);
		System.out.println("Position : " + posT);

		// tresor.setPosition(posT);

	}// fin méthode

	/**
	 * Déplace l'aimant le long de l'arc défini par la ligne, en faisant un quart de
	 * tour.
	 */
	// Tin Pham
	private void deplacerAimantSurArc() {
		angleInit = Math.toRadians(270);

		double length = Math.sqrt(Math.pow(xFin - xInitCorde, 2) + Math.pow(yFin - yInitCorde, 2));
		xDebut = xInitial;
		yDebut = yInitial;

		rayonArc = ligne.getY2() - ligne.getY1();
		deplacementSurArc = rayon * Math.toRadians(90);

		tempsEntreImages = deltaT / deplacementSurArc;

		angleArc = angleInit;

		if (monte) {
			angleArc += tempsEntreImages;
			if (angleArc >= angleInit + Math.toRadians(90)) {
				monte = false;
			} else {
				angleArc -= tempsEntreImages;
				if (angleArc <= angleInit) {
					monte = true;
				} // fin si
			} // fin sinon
		} // fin si

		xFin = xInitCorde + (int) Math.cos(Math.toRadians(angle) * length);
		yFin = yInitCorde + (int) Math.sin(Math.toRadians(angle) * length);

		SVector3d positionFin = new SVector3d(xFin, yFin, 0);

		aimant.setPosition(positionFin);
		repaint();

	}// fin méthode

	/**
	 * Gère l'action lorsqu'un clic de souris est effectué sur le bouton de
	 * démarrage/arrêt d'animation. Si l'animation est en cours, l'arrête. Sinon, la
	 * démarre.
	 */
	// Caroline Houle
	private void gestionClicSourisDemarrerArreter() {
		if (enCoursDAnimation) {
			arreter();
		} else {
			demarrer();
		} // fin si
	}// fin méthode

	/**
	 * Méthode qui s'occupe d'enlever du 5 secondes au temps total quand
	 * l'utilisateur attrape un poisson
	 */
	// Michel Vuu
	private void tempsPoisson() {
		Area hook = new Area(aimant.getAimant());

		boolean contact = false;
		for (Poisson poisson : poissonRandom) {
			Area poissonFantome = new Area();
			poissonFantome.intersect(hook);
			if (!poissonFantome.isEmpty()) {
				contact = true;
				break;
			} // fin si
			if (!contact) {
				tempsEcoule = tempsEcoule - 5;
			} // fin si
		} // fin pour
	}// fin méthode

	/**
	 * Méthode qui dessine un vecteur vitesse pour chaque poisson
	 */
	// Michel Vuu
	private void flecheVecVitPoisson() {
		for (Poisson autre : poissonRandom) {
			FlecheVectorielle vecVitesse;
			vecVitesse = new FlecheVectorielle(autre.getX() + (largeurPoisson / 2), autre.getY() + (hauteurPoisson / 2),
					vitesse);
			vecVitesse.setCouleurFleche(Color.red);
			vecVitesse.redimensionneCorps(0.3);
			vecVitesse.setAngleTete(90);
			vecVitesse.setLongueurTraitDeTete(tailleTeteFleche);
			vecVitesse.setPixelsParMetre(pixelsParMetre);
			vecVitesse.dessiner(g2d);
		} // fin pour
	}// fin méthode

	/**
	 * Déplace l'aimant d'une distance donnée et met à jour sa position sur l'axe
	 * des ordonnées.
	 * 
	 * @param angle deplacement La distance de déplacement de l'aimant.
	 */
	// Tin Pham
	private void deplacementFilEtAimant(double angle) {

		if (angle < 0) {
			deplacement = -0.008;
		} else {
			deplacement = 0.008;
		} // fin si
		if(aimant.getY() + hauteurAimant > yMaxSeperatorMetres) {
			aimant.setY(yMaxSeperatorMetres-hauteurAimant);
		}
		if(aimant.getY() < yInitial) {
			aimant.setY(yInitial);
		}
		
		aimant.deplacement(deplacement);

		ligne.setLine(xInitCorde, yInitCorde, aimant.getX() + 0.06, aimant.getY() + 0.01);

		repaint();
	}// fin méthode

	/**
	 * Méthode qui regarde si l'hameçon est en contact avec le trésor, si il y a
	 * contact le jeu de peche est gagné
	 */
	// Michel Vuu
	private void win() {
		Area aireTresor = tresor.getAireTresor();
		if (aimant.contact(aireTresor)) {
			System.out.println("collision");
			victoire();
		} // fin si
	}// fin méthode

	/**
	 * Méthode qui regarde si l'hameçon est en contact avec une mine, si il y a
	 * contact le jeu de pêche est perdu
	 */
	// Michel Vuu
	private void lose() {
		Area hook = new Area(aimant.getAimant());
		boolean perdre = false;

		for (Mine autre : mineRandom) {
			Area mineAire = new Area(autre.getMine());
			mineAire.intersect(hook);
			if (!mineAire.isEmpty()) {
				perdre = true;
				defaite();
			} // fin si
		}
	}// fin méthode

	/**
	 * Méthode pour faire afficher l'écran de victoire
	 */
	// Liangchen Liu
	private void victoire() {
		menuFinJeu.setVisible(true);
		classeTemps.arreter();

		DonnesClassements donnesClassements = GestionnaireDesDonnes.getInstance().getDonnesClassements();
		ArrayList<InformationEssais> tempPeche = donnesClassements.getPecheArrayList();
		tempPeche.add(new InformationEssais("Peche", nbTours, Math.round(tempsEcoule / 1000000000.0)));
		GestionnaireDesDonnes.getInstance().getDonnesClassements().setCurlingArrayList(tempPeche);

		GestionnaireDesDonnes.getInstance().getDonnesBadges().setBadgeGpeche7(true);

		if (Math.round(tempsEcoule / 1000000000.0) <= 120) {
			if (GestionnaireDesDonnes.getInstance().getDonnesPeche().isModeTriche() == false) {
				System.out.println("3 étoiles");
				GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesPeche(3);
				menuFinJeu.getBtnSuivant().setEnabled(true);
				menuFinJeu.majEtoilesAffiches(3);

			}

		} else if (Math.round(tempsEcoule / 1000000000.0) <= 180 && Math.round(tempsEcoule / 1000000000.0) > 120) {
			if (GestionnaireDesDonnes.getInstance().getDonnesPeche().isModeTriche() == false) {
				GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesPeche(2);
				menuFinJeu.getBtnSuivant().setEnabled(true);
				menuFinJeu.majEtoilesAffiches(3);

			}

		} else if (Math.round(tempsEcoule / 1000000000.0) <= 240 && Math.round(tempsEcoule / 1000000000.0) > 180) {
			if (GestionnaireDesDonnes.getInstance().getDonnesPeche().isModeTriche() == false) {
				GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesPeche(1);
				menuFinJeu.getBtnSuivant().setEnabled(true);
				menuFinJeu.majEtoilesAffiches(3);

			}

		} else {
			if (GestionnaireDesDonnes.getInstance().getDonnesPeche().isModeTriche() == false) {
				GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesPeche(0);
				menuFinJeu.getBtnSuivant().setEnabled(false);
				menuFinJeu.majEtoilesAffiches(3);

			}

		} // fin si

	}// fin méthode

	/**
	 * Méthode pour faire afficher l'écran de défaite
	 */
	// Liangchen Liu
	private void defaite() {
		menuFinJeu.setVisible(true);
		menuFinJeu.getBtnSuivant().setEnabled(false);

		classeTemps.arreter();

	}// fin méthode

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
					if (GestionnaireDesDonnes.getInstance().getDonnesEtoiles().getNbEtoilesPeche() >= 3) {
						firePropertyChange("suivant", 0, 1);
						menuFinJeu.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null,
								"Oops.. On dirait que vous n'avez pas assez d'étoiles pour passez au prochain jeu...",
								"Erreur", JOptionPane.ERROR_MESSAGE);
					}
					break;
				case "continuer":
					JOptionPane.showMessageDialog(null,
							"Vous voulez rejouer à ce jeu? Appuyez sur le bouton 'recommencer'!", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					break;
				}// fin switch
			}// fin propertyChange
		});

		menuFinJeu.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				classeTemps.demarrer();
				reini();

			}// fin méthode
		});

	}// fin méthode

	/**
	 * Méthode qui se charge de modifier la charge de l'aimant dépendant de la
	 * couleur sur lequel l'utilisateur attérit
	 */
	// Michel Vuu
	private void forceSelectionner() {
		if (fleche.getX() > xBarreElec && fleche.getX() < xBarreElec + ((largeurBarreElec / 5))
				|| fleche.getX() > (xBarreElec + largeurBarreElec) - (largeurBarreElec / 5)
						&& fleche.getX() < (xBarreElec + largeurBarreElec)) {
			aimant.setCharge(500);
		} // fin si
		if (fleche.getX() > (xBarreElec + (largeurBarreElec / 5))
				&& fleche.getX() < (xBarreElec + (2 * (largeurBarreElec / 5)))
				|| fleche.getX() > xBarreElec + (3 * (largeurBarreElec / 5))
						&& fleche.getX() < xBarreElec + largeurBarreElec - (largeurBarreElec / 5)) {
			aimant.setCharge(700);
		} // fin si
		if (fleche.getX() > xBarreElec + (2 * (largeurBarreElec / 5))
				&& fleche.getX() < xBarreElec + (3 * (largeurBarreElec / 5))) {
			aimant.setCharge(1800);

		} // fin si
	}// fin méthode

}// fin classe
