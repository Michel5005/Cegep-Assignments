package curling;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GestionDesFichiers.GestionnaireDesDonnes;
import application.AppPrincipale7;
import application.Temps;
import classements.DonnesClassements;
import classements.InformationEssais;
import curling.gestion_des_images.ImageBut;
import curling.gestion_des_images.ImageDirectionChampE;
import curling.gestion_des_images.ImageDirectionChampM;
import curling.gestion_des_images.ImageSignes;
import menus.FenetreAideInstructions;
import menus.InterfaceGenerale;
import menus.MenuFinJeu;
import moteurPhysique.SVector3d;

/**
 * 
 * Classe d'animation pour le jeu de curling
 * 
 * @author Liangchen Liu
 * @author Jason Yin
 */
public class ZoneAnimationCurling extends InterfaceGenerale implements Runnable {
    /** Les instructions lié à la touche "p" du clavier */
    private final String INSTRUCTIONS = " CHOSES IMPLÉMENTÉES: \n"
            + "-Cliquez sur la particule (cercle bleu cyan) et le tirer avec la souris avec le bouton gauche d'enfocé pour tirer ce dernier avec une vitesse initialle  \n"
            + "- Tirez sur les cibles (les rectangles magentas avec une image) avec la particule \n"
            + "- Modifiez le champ électrique et magnétique avec les deux trouniquets et glissières pour observer une trajectoir différente de la particule \n"
            + "- Cliquez sur les boutons \"Inverser ...\" pour inverser la charge du champ magnétique ou électrique \n"
            + "- Modifiez la charge de la particule avec le trouniquet pour observer une trajectoire différente \n"
            + "- Modifiez la masse de la particule avec le trouniquet pour observer une trajectoire différente \n"
            + "- Cliquez sur les boutons radons pour changer les vecteurs affichés lorsque la particule est lancée \n"
            + "- Les images montrant les directions du champ électrique,du champ magnétique et du signes des plaques vont être changées dépendament de la charge des champs et des plaques\n"
            + "- Lciquez la touche \"s\" pour arrêter l'animation de la particule pour utiliser le bouton tour par tour \n"
            + "- Cliquez sur les boutons \"Aide\" et \"Professeur\" pour obtenir plus d'informations \n"
            + "- Survolez le bouton \"Informations\" pour voir les instructions \n"
            + "CHOSES NON IMPLÉMENTÉES: \n"
            + "- Les éléments à réinitialiser ne sont pas tous présents \n"
            + "- Les éléments à sauvegarder ne sont pas tous présents \n"
            + "- Les donnés scientifiques, le temps, le nombre de tirs restants et le nombre de points \n"
            + " -Les graphiques pour les vitesses et les accélérations ne sont pas encore implémentés, mais présentes sous les étiquettes de vitesses et d'accélérations\n"
            + "- Le pixel par mètre n'est pas implémentée, mais l'échelle est présente \n";

    private JLabel lblMasseParticule;
    private JLabel lblPointsNb;
    private JLabel lblTirsRestantsTxt;
    private JLabel lblPointsTxt;
    private JLabel lblTirsRestantsNb;
    private JLabel lblInfoEtudiant;
    private JLabel lblChampMTexte;
    private JLabel lblChampETexte;
    private JLabel lblChargeParticule;
    private JLabel lblTemps;
    private JButton btnInverserPlaques;
    private JButton btnInverserChampM;
    private JSpinner spinnerChampM;
    private JSpinner spinnerChampE;
    private JSpinner spinnerChargeParticule;
    private JSpinner spinnerMasseParticule;
    private JRadioButton rdbtnVecteurAccel;
    private JRadioButton rdbtnVecteurVitesse;
    private final ButtonGroup BUTTON_GROUP = new ButtonGroup();
    private JSlider sliderChampM;
    private JSlider sliderChampE;
    private JTextPane textPaneChampM;
    private JPanel panelBordureChampM;
    private JTextPane textPaneChampE;
    private JPanel panelBordureChampE;
    private JTextPane textPaneForceE;
    private JPanel panelForceE;
    private JTextPane textPaneForceM;
    private JPanel panelForceM;
    private JPanel panelAccY;
    private JTextPane textPaneAccY;
    private JPanel panelVitX;
    private JTextPane textPaneVitX;
    private JPanel panelVitY;
    private JTextPane textPaneVitY;
    private JTextPane textPaneAccX;
    private JPanel panelAccX;
    private JButton btnGraphiques;
    private JTextPane textPaneAvertissement;

    /** Temps de repos de l'animation */
    private int tempsDuSleep = 2;
    /** Indique si l'animation est en cours d'exécution. */
    private boolean enCoursDAnimation;
    /** L'intervalle de temps */
    private double deltaT = 0.05;
    /** Facteur de mise à l'échelle utilisé lors du dessin. */
    private double pixelsParMetre;
    /** Instance du contexte graphique */
    private Graphics2D g2d;

    // Instances des éléments visuels
    /** Instance d'une particule */
    private Particule particule;
    /** Instance de la plaque haut */
    private Plaque plaqueHaut;
    /** Instance de la plaque bas */
    private Plaque plaqueBas;
    /** Instance de la zone de champ électrique */
    private ZoneChampElectrique zoneChampElectrique;
    /** Instance de la fèche de vitesse appliquée par l'utilisateur */
    private FlecheVectorielle flecheVit;

    // Instances des classes à fonctions divers
    /** Instance de la classe qui gère les données */
    private DonnesCurling donnesCurling;

    // Instances des variables pour la particule
    /** Position x de la particule */
    private double xParticule;
    /** Position y de la particule */
    private double yParticule;
    /** Vitesse initiale de la particule */
    private double vitesseInit;
    /** Charge de la particule */
    private double chargeQ;
    /** Masse de la particule */
    private double masse;
    /** Charge de la particule initiale */
    private final double CHARGE_Q_INITIALE = 1;
    /** Masse initiale de la particule */
    private final double MASSE_INITIALE = 80;
    /** Position de la particule en vecteur */
    private SVector3d posParticule;
    /** Vitesse de la particule en vecteur */
    private SVector3d vitParticule = new SVector3d(0, 0, 0);
    /** Accélération de la particule en vecteur */
    private SVector3d acceleration = new SVector3d(0, 0, 0);
    /** Force de la particule en vecteur */
    private SVector3d sommeDesForces = new SVector3d(0, 0, 0);
    /** Géométrie de la particule */
    private Ellipse2D.Double particuleForme;

    // Instances des variables pour la fleche
    /** Instance de la fleche vectorielle pour la vitesse totale de la particule */
    private FlecheVectorielle flecheVitParticuleTotal;
    /** Instance de la fleche vectorielle pour la vitesse en x de la particule */
    private FlecheVectorielle flecheVitParticuleX;
    /** Instance de la fleche vectorielle pour la vitesse en y de la particule */
    private FlecheVectorielle flecheVitParticuleY;
    /**
     * Instance de la fleche vectorielle pour l'accélération totale de la particule
     */
    private FlecheVectorielle flecheAccParticuleTotal;
    /**
     * Instance de la fleche vectorielle pour l'accélération en y de la particule
     */
    private FlecheVectorielle flecheAccParticuleY;
    /**
     * Instance de la fleche vectorielle pour l'accélération en x de la particule
     */
    private FlecheVectorielle flecheAccParticuleX;
    /** Variable pour la distance entre deux points d'une flèche */
    private double distanceFleche;
    /** Variable pour la position x1 de la flèche */
    private double x1Fleche;
    /** Variable pour la position x2 de la flèche */
    private double x2Fleche;
    /** Variable pour la position y1 de la flèche */
    private double y1Fleche;
    /** Variable pour la position y2 de la flèche */
    private double y2Fleche;
    /** Variable pour la longueur x3 de la flèche */
    private double x3Fleche;
    /** Variable pour la longueur y3 de la flèche */
    private double y3Fleche;

    /** Booléen déterminant si les flèches de vitesses sont affichées ou pas */
    private boolean afficherFlecheVitesse = true;
    /** Booléen déterminant si les flèches d'accélérations sont affichées ou pas */
    private boolean afficherFlecheAccel = false;

    // Instances des variables pour la plaque

    /** Booléen déterminant si la particule est sélectionné ou pas */
    private boolean particuleSelectionnee = false;
    /** Booléen déterminant si la particule est lancée ou pas */
    private boolean particuleLancee = false;

    // Instances des variables pour la zone de champ électrique et magnétique
    /** Géométrie du zone de champ électrique */
    private Rectangle2D.Double zoneChampElectriqueForme;
    /** Géométrie de la plaque du haut */
    private Rectangle2D.Double plaqueHautForme;
    /** Géométrie de la plaque du bas */
    private Rectangle2D.Double plaqueBasForme;

    // Instances de variables pour le but
    private Rectangle2D.Double zoneButFormeExt;
    private Rectangle2D.Double zoneButFormeMilieu;

    private ZoneBut zoneButExt;
    private ZoneBut zoneButMilieu;

    private ImageDirectionChampE imageDirectionChampE;
    private ImageDirectionChampM imageDirectionChampM;
    private ImageSignes imageSingesPlaqueHaut;
    private ImageSignes imageSingesPlaqueBas;
    private ImageSignes imageSignesParticule;
    private ImageBut imageButMilieu;
    private ImageBut imageButExt;
    private URL lienImageChampE = getClass().getClassLoader().getResource("fleche_champE.png");
    private URL lienImageChampMnegatif = getClass().getClassLoader().getResource("fleche_champM_negatif.png");
    private URL lienImageChampMpositif = getClass().getClassLoader().getResource("fleche_champM_positif.png");
    private URL lienImageParticulePositif = getClass().getClassLoader().getResource("particule_positif.png");
    private URL lienImageParticuleNegatif = getClass().getClassLoader().getResource("particule_negatif.png");
    private URL lienImageCible = getClass().getClassLoader().getResource("cibles_curling.png");

    /** Vecteur du champ magnétique */
    private SVector3d champM;
    /** Vecteur du champ électrique */
    private SVector3d champE;

    private final SVector3d CHAMP_M_INITIALE = new SVector3d(0, 0, 40);
    private final SVector3d CHAMP_E_INITIALE = new SVector3d(0, 4, 0);

    private SVector3d forceM;
    private SVector3d forceE;
    private double points;
    private double tirsRestants;

    private long tempsEcoule;

    private boolean butExtTouche = false;
    private boolean butMilieuTouche = false;

    private String htmlTexteChampM;
    private String htmlTexteChampE;
    private String htmlTexteForceM;
    private String htmlTexteForceE;
    private String htmlTexteVitesseX;
    private String htmlTexteVitesseY;
    private String htmlTexteAccelerationX;
    private String htmlTexteAccelerationY;
    private String htmlTexteAvertissement;

    private FenetreGraphiques fenetreGraphiques;

    private Area aireParticule;
    private Area aireZoneButExt;
    private Area aireZoneButMilieu;

    private Temps classeTemps;

    /** Largeur du composant en mètres */
    private double largeurDuComposantEnMetres = 12.84;
    /** Position x initiale de la particule */
    private final double X_PARTICULE_INITIALE = 1.5;
    /** Position y initiale de la particule */
    private final double Y_PARTICULE_INITIALE = 2.5;
    /** Dianètre de la particule */
    private double diametreParticule = 0.3;
    /** Position x des plaques horizontales */
    private double xPlaqueHorizontale = 3;
    /** Position y de la plaque du haut */
    private double yPlaqueHaut = 1.5;
    /** Position y de la plaque du bas */
    private double yPlaqueBas = 3.5;
    /** Largeur des plaques */
    private double largeurPlaque = 5;
    /** Hauteur des plaques */
    private double hauteurPlaque = 0.2;

    private double hauteurZoneAnimation = 5.6;
    private double largeurZoneAnimation = 11.25;

    private double hauteurExtBut1 = 1.88;
    private double hauteurExtBut2 = 3.72;

    private double hauteurExtButMilieu = 1.84;

    private double hauteurButMin = 0.3;
    private double hauteurButMoyen = 0.6;
    private double hauteurButMax = 0.9;

    private double xBut;
    private double yBut;
    private double largeurBut;
    private double hauteurBut;

    private double dimensionImage = 0.5;

    /** La position maximale en x du séparateur en pixels. */
    private double xMaxSeparator = 1125;
    /** La position maximale en x du séparateur en mètres */
    private double xMaxSeparatorMetres = 11.25;

    // private double yMaxSeperatorMetres = 5.6;

    private double xMaxEchelle;
    private double xMinEchelle;
    private double xMilieuEchelle;

    private int nbEssais;
    private MenuFinJeu menuFinJeu;

    private boolean modeTriche;

    private int conditionVictoire;

    /**
     * Constructeur de la scène
     */
    // Liangchen Liu
    public ZoneAnimationCurling() {
        donnesCurling = GestionnaireDesDonnes.getInstance().getDonnesCurling();
        chargerLesDonnees();

        classeTemps = new Temps(2);

        largeurBut = 0.2;
        xBut = largeurZoneAnimation - largeurBut;
        yBut = 0.3;
        hauteurBut = 0.3;

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (particuleSelectionnee &&
                        particule.getPosition().getX() == X_PARTICULE_INITIALE
                        && !enCoursDAnimation
                        && tirsRestants > 0) {
                    pixelsParMetre = getWidth() / largeurDuComposantEnMetres;
                    // hauteurDuComposantEnMetres = getHeight() / pixelsParMetre;
                    flecheVit.setPixelsParMetre(pixelsParMetre);
                    flecheVit.setLongueurTraitDeTete(5 / pixelsParMetre);

                    particule.setPosition(new SVector3d(X_PARTICULE_INITIALE, Y_PARTICULE_INITIALE, 0));

                    x2Fleche = particule.getPosition().getX() + diametreParticule / 2;
                    y2Fleche = particule.getPosition().getY() + diametreParticule / 2;
                    x1Fleche = e.getX() / pixelsParMetre;
                    y1Fleche = e.getY() / pixelsParMetre;

                    flecheVit.setX2(x2Fleche);
                    flecheVit.setY2(y2Fleche);
                    flecheVit.setX1(x1Fleche);
                    flecheVit.setY1(y1Fleche);

                    x3Fleche = x2Fleche - e.getX() / pixelsParMetre;
                    y3Fleche = y2Fleche - e.getY() / pixelsParMetre;

                    distanceFleche = Math.sqrt(Math.pow(x3Fleche, 2) + Math.pow(y3Fleche, 2));

                    if (distanceFleche >= 1) {
                        double angle = Math.atan2(y3Fleche, x3Fleche);

                        double xTemp = Math.cos(angle);
                        double yTemp = Math.sin(angle);

                        flecheVit.setX1(x2Fleche - xTemp);
                        flecheVit.setY1(y2Fleche - yTemp);

                        vitParticule = new SVector3d(xTemp * 0.2, yTemp * 0.2, 0);
                        particule.setVitesse(vitParticule);

                    } else {
                        vitParticule = new SVector3d(x3Fleche * 0.2, y3Fleche * 0.2, 0);
                        particule.setVitesse(vitParticule);

                    } // fin if else

                    particuleLancee = true;

                } // fin if

            }// fin méthode

        });// fin MotionListener

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (particule.contient(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre)
                        && particule.getPosition().getX() == X_PARTICULE_INITIALE && !enCoursDAnimation
                        && tirsRestants > 0
                        && (double) spinnerChargeParticule.getValue() != 0
                        && (double) spinnerChampE.getValue() != 0
                        && (double) spinnerChampM.getValue() != 0
                        && e.getButton() == MouseEvent.BUTTON1) {

                    particule.setVitesse(new SVector3d(0, 0, 0));
                    particuleSelectionnee = true;
                    particule.setParticuleSelectionne(particuleSelectionnee);

                } // fin if

            } // fin pressed

            @Override
            public void mouseReleased(MouseEvent arg0) {
                if (!enCoursDAnimation && particule.getPosition().getX() == X_PARTICULE_INITIALE && particuleLancee
                        && tirsRestants > 0) {
                    particuleSelectionnee = false;
                    particule.setParticuleSelectionne(particuleSelectionnee);

                    flecheVit.setLongueurFleche(0);
                    flecheVit = new FlecheVectorielle(0, 0, 0, 0);

                    demarrer();
                } else if (!enCoursDAnimation && particule.getPosition().getX() == X_PARTICULE_INITIALE) {
                    particuleSelectionnee = false;
                    particule.setParticuleSelectionne(particuleSelectionnee);

                } // fin if

            } // fin released
        });

        instProf();
        toucheArreter();
        infoJoueur();
        sauv();
        btnReini();
        aide();
        exProf();
        prochImg();

        donnesCurling.setChampM(champM);
        donnesCurling.setChampE(champE);
        donnesCurling.setChargeQ(chargeQ);
        donnesCurling.setMasse(masse);

        flecheVit = new FlecheVectorielle(0, 0, 0, 0);
        posParticule = new SVector3d(xParticule, yParticule, 0);

        vitParticule = new SVector3d(0, 0, 0);
        particule = new Particule(posParticule, vitParticule, acceleration, sommeDesForces, diametreParticule,
                vitesseInit, masse, deltaT, pixelsParMetre);

        plaqueHaut = new Plaque(xPlaqueHorizontale, yPlaqueHaut, largeurPlaque, hauteurPlaque, pixelsParMetre);
        plaqueBas = new Plaque(xPlaqueHorizontale, yPlaqueBas, largeurPlaque, hauteurPlaque, pixelsParMetre);

        zoneChampElectrique = new ZoneChampElectrique(xPlaqueHorizontale, yPlaqueHaut + hauteurPlaque,
                largeurPlaque, yPlaqueBas - (yPlaqueHaut + hauteurPlaque), pixelsParMetre);

        zoneButExt = new ZoneBut(xBut, yBut, diametreParticule, hauteurBut);
        zoneButMilieu = new ZoneBut(xBut, yBut + hauteurBut, diametreParticule, hauteurBut);

        particuleForme = particule.getParticule();
        zoneChampElectriqueForme = zoneChampElectrique.getZoneChampElectrique();
        plaqueHautForme = plaqueHaut.getPlaques();
        plaqueBasForme = plaqueBas.getPlaques();

        particule.setChampM(new SVector3d(0, 0, 0));
        particule.setChampE(new SVector3d(0, 0, 0));

        htmlTexteChampM = new String("<html><font color='red'><center>" + donnesCurling.getChampM().toString()
                + "</center></font></html>");

        htmlTexteChampE = new String("<html><font color='red'><center>" + donnesCurling.getChampE().toString()
                + "</center></font></html>");

        htmlTexteForceE = new String(
                "<html><font color='red'><center>" + new SVector3d(0, 0, 0).toString() + "</center></font></html>");

        htmlTexteForceM = new String(
                "<html><font color='red'><center>" + new SVector3d(0, 0, 0).toString() + "</center></font></html>");

        htmlTexteAccelerationX = new String(
                "<html><font color='red'><center>" + String.format("%.3f", particule.getAcceleration().getX())
                        + "</center></font></html>");

        htmlTexteAccelerationY = new String(
                "<html><font color='red'><center>" + String.format("%.3f", particule.getAcceleration().getY())
                        + "</center></font></html>");

        htmlTexteVitesseX = new String(
                "<html><font color='red'><center>" + String.format("%.3f", particule.getVitesse().getX())
                        + "</center></font></html>");

        htmlTexteVitesseY = new String(
                "<html><font color='red'><center>" + String.format("%.3f", particule.getVitesse().getY())
                        + "</center></font></html>");

        fenetreGraphiques = new FenetreGraphiques();

        composantsJSwing();

        menuFinJeu = new MenuFinJeu();
        menuFinJeuPropChange();

        imageDirectionChampE();
        imageDirectionChampM();
        imagesSignesPlaques();
        imageSignesParticule();

        determinerPositionHauteurBut();

    }// fin constructeur

    /**
     * Permet de dessiner les éléments sur le contexte graphique qui passe en
     * parametre.
     * 
     * @param g Le contexte graphique
     */
    // Liangchen Liu
    @Override
    public void paintComponent(Graphics g) {
        donnesCurling = GestionnaireDesDonnes.getInstance().getDonnesCurling();
        chargerLesDonnees();

        spinnerChampE.setValue(champE.getY());
        spinnerChampM.setValue(champM.getZ());
        spinnerChargeParticule.setValue(chargeQ);
        spinnerMasseParticule.setValue(masse);
        lblPointsNb.setText(String.valueOf(String.format("%.0f", points)));
        lblTirsRestantsNb.setText(String.valueOf(String.format("%.0f", tirsRestants)));

        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        pixelsParMetre = getWidth() / largeurDuComposantEnMetres;
        // hauteurDuComposantEnMetres = getHeight() / pixelsParMetre;

        particule.setPixelsParMetre(pixelsParMetre);
        plaqueHaut.setPixelsParMetre(pixelsParMetre);
        plaqueBas.setPixelsParMetre(pixelsParMetre);
        zoneChampElectrique.setPixelsParMetre(pixelsParMetre);
        zoneButExt.setPixelsParMetre(pixelsParMetre);
        zoneButMilieu.setPixelsParMetre(pixelsParMetre);
        imageDirectionChampE.setPixelsParMetre(pixelsParMetre);
        imageDirectionChampM.setPixelsParMetre(pixelsParMetre);
        imageSingesPlaqueBas.setPixelsParMetre(pixelsParMetre);
        imageSingesPlaqueHaut.setPixelsParMetre(pixelsParMetre);
        imageSignesParticule.setPixelsParMetre(pixelsParMetre);
        imageButExt.setPixelsParMetre(pixelsParMetre);
        imageButMilieu.setPixelsParMetre(pixelsParMetre);

        plaqueHaut.dessiner(g2d);
        plaqueBas.dessiner(g2d);
        zoneChampElectrique.dessiner(g2d);

        particule.setChargeQ(chargeQ);
        particule.setMasse(masse);
        particule.dessiner(g2d);

        imageDirectionChampE.dessiner(g2d);
        imageDirectionChampM.dessiner(g2d);
        imageSingesPlaqueBas.dessiner(g2d);
        imageSingesPlaqueHaut.dessiner(g2d);

        dessinerButs();

        if (enCoursDAnimation || particuleSelectionnee) {
            spinnerChampE.setEnabled(false);
            spinnerChampM.setEnabled(false);

        } else {
            spinnerChampE.setEnabled(true);
            spinnerChampM.setEnabled(true);
        } // fin if else

        particuleForme = particule.getParticule();
        zoneChampElectriqueForme = zoneChampElectrique.getZoneChampElectrique();

        plaqueHautForme = plaqueHaut.getPlaques();
        plaqueBasForme = plaqueBas.getPlaques();

        calculColliZoneChamps();
        calculColliPlaques();
        calculColliBut();

        imageSignesParticule.setX(particule.getPosition().getX());
        imageSignesParticule.setY(particule.getPosition().getY());
        imageSignesParticule.dessiner(g2d);

        if (particuleSelectionnee || particuleLancee) {
            dessinerFlechVecIni();
            dessinerFlechVecLance();

        } // fin if

        if (particule.getPosition().getX() > 11.25 - diametreParticule || particule.getPosition().getX() < 0
                || particule.getPosition().getY() > 5.6 - diametreParticule || particule.getPosition().getY() < 0) {

            reiniPartielle();

        } // fin if

        Path2D.Double echelle = creerEchelle();
        g2d.drawString("1 mètre = 100 pixels ", (int) (xMaxSeparator - 135), 556);
        g2d.setColor(Color.magenta);
        g2d.draw(echelle);

        // donnesVitAcc();

        repaint();

    }// fin methode

    /**
     * Méthode permettand d'envoyer les données nécessaires aux graphiques.
     * 
     */
    // Liangchen Liu
    private void donnesGraphiques() {
        fenetreGraphiques.setDonnes(tempsEcoule / 1000000000.0, particule.getVitesse().getX(),
                particule.getVitesse().getY(),
                particule.getAcceleration().getX(), particule.getAcceleration().getY());

    }

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
                            lblTemps.setText(evt.getNewValue() + "");

                            break;

                        case "tempsEcoule":

                            tempsEcoule = (long) evt.getNewValue();

                            donnesGraphiques();

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
     * Méthode qui permet de dessiner l'image des signes de la particule
     */
    // Liangchen Liu
    private void imageSignesParticule() {
        if ((double) spinnerChargeParticule.getValue() > 0) {
            imageSignesParticule = new ImageSignes(particule.getPosition().getX(), particule.getPosition().getY(),
                    diametreParticule, diametreParticule, lienImageParticulePositif);

        } else if ((double) spinnerChargeParticule.getValue() < 0) {
            imageSignesParticule = new ImageSignes(particule.getPosition().getX(), particule.getPosition().getY(),
                    diametreParticule, diametreParticule, lienImageParticuleNegatif);
        }

    }

    /**
     * Méthode qui permet de dessiner l'image de la direction du champ électrique
     */
    // Liangchen Liu
    private void imageDirectionChampE() {
        imageDirectionChampE = new ImageDirectionChampE(
                ((xPlaqueHorizontale + largeurPlaque / 2) - (dimensionImage / 2)),
                (((yPlaqueBas - (yPlaqueHaut + hauteurPlaque)) / 2 + (yPlaqueHaut +
                        hauteurPlaque)) - (dimensionImage / 2)),
                dimensionImage, dimensionImage, lienImageChampE);

    }// fin méthode

    /**
     * Méthode qui permet de dessiner l'image de la direction du champ magnétique
     */
    // Liangchen Liu
    private void imageDirectionChampM() {
        imageDirectionChampM = new ImageDirectionChampM(
                ((xPlaqueHorizontale + largeurPlaque / 2) - (dimensionImage / 2)),
                ((yPlaqueHaut / 2) - (dimensionImage / 2)),
                dimensionImage, dimensionImage, lienImageChampMpositif);

    }// fin méthode

    /**
     * Méthode qui permet de dessiner l'image du but au milieu
     * 
     * @param x              coordonnée x du but
     * @param y              coordonnée y du but
     * @param largeur        largeur du but
     * @param hauteur        hauteur du but
     * @param lienImageCible lien de l'image du but
     */
    // Liangchen Liu
    private void imagesButMilieu(double x, double y, double largeur, double hauteur, URL lienImageCible) {
        imageButMilieu = new ImageBut(x, y, largeur, hauteur, lienImageCible);
    }

    /**
     * Méthode qui permet de dessiner l'image des buts aux extrémités
     * 
     * @param x              coordonnée x du but
     * @param y              coordonnée y du but
     * @param largeur        largeur du but
     * @param hauteur        hauteur du but
     * @param lienImageCible lien de l'image du but
     */
    // Liangchen Liu
    private void imagesButExt(double x, double y, double largeur, double hauteur, URL lienImageCible) {
        imageButExt = new ImageBut(x, y, largeur, hauteur, lienImageCible);
    }

    /**
     * Méthode qui permet de dessiner l'image des singes sur les plaques
     */
    // Liangchen Liu
    private void imagesSignesPlaques() {
        imageSingesPlaqueHaut = new ImageSignes(
                (xPlaqueHorizontale + largeurPlaque / 2) - dimensionImage / 2,
                yPlaqueHaut + hauteurPlaque / 2 - dimensionImage / 2,
                dimensionImage, dimensionImage, lienImageParticulePositif);

        imageSingesPlaqueBas = new ImageSignes(
                (xPlaqueHorizontale + largeurPlaque / 2) - dimensionImage / 2,
                yPlaqueBas + hauteurPlaque / 2 - dimensionImage / 2,
                dimensionImage, dimensionImage, lienImageParticuleNegatif);
    }// fin méthode

    /**
     * Méthode permettant de calculer la collision avec un but
     * 
     */
    // Liangchen Liu
    private void calculColliBut() {
        aireParticule = new Area(particuleForme);
        aireZoneButExt = new Area(zoneButFormeExt);
        aireZoneButMilieu = new Area(zoneButFormeMilieu);

        Area totalExt = new Area(aireParticule);
        totalExt.subtract(aireZoneButExt);

        Area zoneButExt = new Area(aireParticule);
        zoneButExt.intersect(aireZoneButExt);

        Area totalMilieu = new Area(aireParticule);
        totalMilieu.subtract(aireZoneButMilieu);

        Area zoneButMilieu = new Area(aireParticule);
        zoneButMilieu.intersect(aireZoneButMilieu);

        if (!zoneButExt.isEmpty() && !butExtTouche) {
            points += 1;

            reiniPartielle();
            donnesCurling.setPoints(points);
            lblPointsNb.setText("" + points);

            butExtTouche = true;

        } else if (!zoneButMilieu.isEmpty() && !butMilieuTouche) {
            points += 2;

            reiniPartielle();
            donnesCurling.setPoints(points);

            lblPointsNb.setText("" + points);

            butMilieuTouche = true;

        } // fin if else

    }// fin méthode

    /**
     * Méthode permettant de calculer la collision avec une zone de champ
     */
    // Liangchen Liu
    private void calculColliZoneChamps() {
        Area aireParticule = new Area(particuleForme);
        Area aireZoneChampElectriqueMagnetique = new Area(zoneChampElectriqueForme);
        Area total = new Area(aireParticule);
        total.subtract(aireZoneChampElectriqueMagnetique);

        Area zoneParticule = new Area(aireParticule);
        zoneParticule.intersect(aireZoneChampElectriqueMagnetique);

        if (!zoneParticule.isEmpty()) {
            particule.setParticule(particuleForme);

            afficherLesValeursScientifiquesForces();

            particule.setChampM(champM);
            particule.setChampE(new SVector3d(champE.getX(), champE.getY() * -1, champE.getZ()));
            // particule.setChampE(champE);

            particule.setCouleur(new Color(146, 24, 228));
            particule.dessiner(g2d);

        } else if (total.isEmpty()) {
            particule.setParticule(particuleForme);

            afficherLesValeursScientifiquesForces();

            particule.setChampM(champM);
            particule.setChampE(new SVector3d(champE.getX(), champE.getY() * -1, champE.getZ()));
            // particule.setChampE(champE);

            particule.setCouleur(new Color(146, 24, 228));
            particule.dessiner(g2d);
        } else {
            particule.setChampM(new SVector3d(0, 0, 0));
            particule.setChampE(new SVector3d(0, 0, 0));

            forceM = new SVector3d(0, 0, 0);
            forceE = new SVector3d(0, 0, 0);

            htmlTexteForceM = new String("<html><font color='red'><center>" +
                    forceM.toString()
                    + "</center></font></html>");

            textPaneForceM.setText(htmlTexteForceM);

            htmlTexteForceE = new String("<html><font color='red'><center>" +
                    forceE.toString()
                    + "</center></font></html>");

            textPaneForceE.setText(htmlTexteForceE);

        }

        particule.setCouleur(new Color(30, 144, 245));

    }// fin méthode

    /**
     * Méthode permettant de calculer la collision avec une plaque
     */
    // Liangchen Liu
    private void calculColliPlaques() {
        Area aireParticule = new Area(particuleForme);
        Area airePlaqueBasForme = new Area(plaqueBasForme);
        Area total = new Area(aireParticule);
        total.subtract(airePlaqueBasForme);

        Area zonePlaqueBas = new Area(aireParticule);
        zonePlaqueBas.intersect(airePlaqueBasForme);

        if (!zonePlaqueBas.isEmpty()) {
            xParticule = X_PARTICULE_INITIALE;
            yParticule = Y_PARTICULE_INITIALE;

            reiniPartielle();

        } // fin if

        Area airePlaqueHautForme = new Area(plaqueHautForme);
        total.subtract(airePlaqueHautForme);

        Area zonePlaqueHaut = new Area(aireParticule);
        zonePlaqueHaut.intersect(airePlaqueHautForme);

        if (!zonePlaqueHaut.isEmpty()) {
            xParticule = X_PARTICULE_INITIALE;
            yParticule = Y_PARTICULE_INITIALE;

            reiniPartielle();
        } // fin if

    }// fin méthode

    /**
     * Méthode permettant de dessiner les buts
     */
    // Liangchen Liu
    private void dessinerButs() {
        if (butExtTouche == false && butMilieuTouche == false) {
            zoneButExt.dessiner(g2d);
            zoneButMilieu.dessiner(g2d);

            imageButMilieu.dessiner(g2d);
            imageButExt.dessiner(g2d);

            calculColliBut();

        } else if (!butExtTouche) {
            zoneButExt.dessiner(g2d);
            imageButExt.dessiner(g2d);

            calculColliBut();

        } else if (!butMilieuTouche) {
            zoneButMilieu.dessiner(g2d);
            imageButMilieu.dessiner(g2d);

            calculColliBut();

        } else { // fin if else
            determinerPositionHauteurBut();
            butExtTouche = false;
            butMilieuTouche = false;

        }

    }

    /**
     * Méthode qui effectue l'animation du projectile
     */
    // Liangchen Liu
    @Override
    public void run() {
        while (enCoursDAnimation) {
            calculerUneIterationPhysique(deltaT);
            donnesGraphiques();

            try {
                Thread.sleep(tempsDuSleep);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } // fin try-catch
        } // fin while
        System.out.println("Le thread est mort...!");

    }// fin methode

    /**
     * Demarre le thread s'il n'est pas deja demarre
     * 
     */
    // Liangchen Liu
    public void demarrer() {
        if (!enCoursDAnimation) {
            Thread proc = new Thread(this);
            proc.start();
            enCoursDAnimation = true;

        } // fin if

    }// fin methode

    /**
     * Calcul des nouvelles positions pour tous les objets de la scène
     * 
     * @param deltaT L'intervalle de temps
     */
    // Liangchen Liu
    private void calculerUneIterationPhysique(double deltaT) {
        particule.avancerUnPas(deltaT);

        afficherLesValeursScientifiquesVitAcc();

        donnesCurling.setXParticule(particule.getX());
        donnesCurling.setYParticule(particule.getY());

    }// fin méthode

    /**
     * Demande l'arret du thread (prochain tour de boucle)
     */
    // Liangchen Liu
    public void arreter() {
        enCoursDAnimation = false;

    }// fin methode

    /**
     * Méthode pour charger les données de la classe DonneesTank
     */
    // Liangchen Liu
    private void chargerLesDonnees() {
        modeTriche = donnesCurling.isModeTriche();

        if (!modeTriche) {
            xParticule = donnesCurling.getXParticule();
            yParticule = donnesCurling.getYParticule();
            champE = donnesCurling.getChampE();
            champM = donnesCurling.getChampM();

            points = donnesCurling.getPoints();

            tirsRestants = donnesCurling.getTirsRestants();

            chargeQ = donnesCurling.getChargeQ();
            masse = donnesCurling.getMasse();

            nbEssais = donnesCurling.getNbEssais();

            conditionVictoire = donnesCurling.getConditionVictoire();
        } else {
            xParticule = donnesCurling.getXParticule();
            yParticule = donnesCurling.getYParticule();
            champE = donnesCurling.getChampE();
            champM = donnesCurling.getChampM();

            points = donnesCurling.getPoints();

            chargeQ = donnesCurling.getChargeQ();

            nbEssais = donnesCurling.getNbEssais();

            masse = donnesCurling.getMasseT();
            tirsRestants = donnesCurling.getTirsRestantsT();

            conditionVictoire = donnesCurling.getConditionVictoireT();
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
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    JOptionPane.showMessageDialog(null, INSTRUCTIONS, "Fonctionnalités présentes",
                            JOptionPane.INFORMATION_MESSAGE);
                    requestFocusInWindow();
                } // fin if

                if (e.getKeyCode() == KeyEvent.VK_S) {
                    arreter();
                } // fin if

            }// fin méthode

        });// fin listener
    }// fin méthode

    /**
     * Méthode pour faire arrêter l'animation
     */
    // Liangchen Liu
    private void toucheArreter() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    arreter();
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
                if (!enCoursDAnimation) {
                    donnesCurling.setXParticule(xParticule);
                    donnesCurling.setYParticule(yParticule);
                    donnesCurling.setChampE(champE);
                    donnesCurling.setChampM(champM);
                    donnesCurling.setChargeQ(chargeQ);
                    donnesCurling.setMasse(masse);

                    donnesCurling.setPoints(points);
                    donnesCurling.setTirsRestants(tirsRestants);
                    donnesCurling.setTempsEcoule(tempsEcoule);

                    GestionnaireDesDonnes.getInstance().setNiveau(2);
                    GestionnaireDesDonnes.getInstance().setDonnesCurling(donnesCurling);
                    GestionnaireDesDonnes.getInstance().sauvegarder();

                    requestFocusInWindow();

                } else {
                    JOptionPane.showMessageDialog(null,
                            "Veuillez attendre que l'animation termine avant de sauvegarder", "Notification",
                            JOptionPane.INFORMATION_MESSAGE);

                    requestFocusInWindow();
                } // fin if else

            }// fin méthode
        });// fin listener

    }// fin méthode

    /**
     * Méthode pour faire réinitialiser le niveau lors du clic sur le bouton
     */
    // Liangchen Liu
    private void btnReini() {
        btnReinitialiser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                reini();
            }// fin méthode
        });// fin listener

    }// fin méthode

    /**
     * Méthode pour réinitialiser la position de la particule
     */
    // Liangchen Liu
    private void reiniPartielle() {
        enCoursDAnimation = false;
        particuleLancee = false;
        particuleSelectionnee = false;

        xParticule = X_PARTICULE_INITIALE;
        yParticule = Y_PARTICULE_INITIALE;

        donnesCurling.setXParticule(xParticule);
        donnesCurling.setYParticule(yParticule);

        vitParticule = new SVector3d(0, 0, 0);
        acceleration = new SVector3d(0, 0, 0);
        sommeDesForces = new SVector3d(0, 0, 0);

        posParticule = new SVector3d(donnesCurling.getXParticule(), donnesCurling.getYParticule(), 0);
        particule.setPosition(posParticule);
        particule.setVitesse(vitParticule);
        particule.setAcceleration(acceleration);
        particule.setSommeDesForces(sommeDesForces);

        particule = new Particule(posParticule, vitParticule, acceleration, sommeDesForces, diametreParticule, 0, masse,
                deltaT, pixelsParMetre);

        htmlTexteVitesseX = new String("<html><font color='red'><center>" +
                String.format("%.3f", 0.0) + "</center></font></html>");

        textPaneVitX.setText(htmlTexteVitesseX);

        htmlTexteVitesseY = new String("<html><font color='red'><center>" +
                String.format("%.3f", 0.0) + "</center></font></html>");

        textPaneVitY.setText(htmlTexteVitesseY);

        tirsRestants -= 1;
        donnesCurling.setTirsRestants(tirsRestants);

        nbEssais += 1;
        donnesCurling.setNbEssais(nbEssais);

        if (tirsRestants == 0) {
            defaite();

        }

        if (points >= conditionVictoire) {
            victoire();
        }

        lblTirsRestantsNb.setText(String.valueOf(tirsRestants));

        imageSignesParticule();

        requestFocusInWindow();
        repaint();
    }

    /**
     * Méthode pour faire afficher l'écran de victoire
     */
    // Liangchen Liu
    private void victoire() {
        menuFinJeu.setVisible(true);
        classeTemps.arreter();

        DonnesClassements donnesClassements = GestionnaireDesDonnes.getInstance().getDonnesClassements();
        ArrayList<InformationEssais> tempCurling = donnesClassements.getCurlingArrayList();
        tempCurling.add(new InformationEssais("Curling", nbEssais, Math.round(tempsEcoule / 1000000000.0)));
        GestionnaireDesDonnes.getInstance().getDonnesClassements().setCurlingArrayList(tempCurling);

        GestionnaireDesDonnes.getInstance().getDonnesBadges().setBadgeGcurling6(true);

        if (Math.round(tempsEcoule / 1000000000.0) <= 120) {
            if (GestionnaireDesDonnes.getInstance().getDonnesCurling().isModeTriche() == false) {
                System.out.println("3 étoiles");
                GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesCurling(3);
                menuFinJeu.getBtnSuivant().setEnabled(true);
                menuFinJeu.majEtoilesAffiches(2);

            }

        } else if (Math.round(tempsEcoule / 1000000000.0) <= 180 && Math.round(tempsEcoule / 1000000000.0) > 120) {
            if (GestionnaireDesDonnes.getInstance().getDonnesCurling().isModeTriche() == false) {
                GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesCurling(2);
                menuFinJeu.getBtnSuivant().setEnabled(true);
                menuFinJeu.majEtoilesAffiches(2);

            }

        } else if (Math.round(tempsEcoule / 1000000000.0) <= 240 && Math.round(tempsEcoule / 1000000000.0) > 180) {
            if (GestionnaireDesDonnes.getInstance().getDonnesCurling().isModeTriche() == false) {
                GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesCurling(1);
                menuFinJeu.getBtnSuivant().setEnabled(true);
                menuFinJeu.majEtoilesAffiches(2);

            }

        } else {
            if (GestionnaireDesDonnes.getInstance().getDonnesCurling().isModeTriche() == false) {
                GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesCurling(0);
                menuFinJeu.getBtnSuivant().setEnabled(false);
                menuFinJeu.majEtoilesAffiches(2);
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
        menuFinJeu.getMessageLabel().setText("Vous n'avez plus assez de tirs!");
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
                    	if (GestionnaireDesDonnes.getInstance().getDonnesEtoiles().getNbEtoilesCurling()>=3) {
							firePropertyChange("suivant", 0, 1);
							menuFinJeu.setVisible(false);
						} else {
								JOptionPane.showMessageDialog(null, "Oops.. On dirait que vous n'avez pas assez d'étoiles pour passez au prochain jeu...", "Erreur",JOptionPane.ERROR_MESSAGE);
						}
                        break;
                    case "continuer":
    					JOptionPane.showMessageDialog(null,"Vous voulez rejouer à ce jeu? Appuyez sur le bouton 'recommencer'!" , "Info",
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

            }
        });

    }

    /**
     * Méthode pour faire réinitialiser les données du niveau
     */
    // Liangchen Liu
    public void reini() {
        determinerPositionHauteurBut();
        classeTemps.reiniTemps();

        butExtTouche = false;
        butMilieuTouche = false;

        enCoursDAnimation = false;
        particuleLancee = false;
        particuleSelectionnee = false;

        xParticule = X_PARTICULE_INITIALE;
        yParticule = Y_PARTICULE_INITIALE;
        champE = CHAMP_E_INITIALE;
        champM = CHAMP_M_INITIALE;
        chargeQ = CHARGE_Q_INITIALE;
        masse = MASSE_INITIALE;
        points = 0;
        tirsRestants = 15;
        nbEssais = 0;

        afficherFlecheVitesse = true;
        afficherFlecheAccel = false;
        rdbtnVecteurVitesse.setSelected(true);
        rdbtnVecteurAccel.setSelected(false);

        particule.setVitesse(new SVector3d(0, 0, 0));
        particule.setChampM(champM);
        particule.setChampE(champE);
        particule.setChargeQ(CHARGE_Q_INITIALE);
        particule.setMasse(MASSE_INITIALE);

        posParticule = new SVector3d(xParticule, yParticule, 0);

        particule = new Particule(posParticule, vitParticule, acceleration, sommeDesForces, diametreParticule,
                vitesseInit, masse, deltaT, pixelsParMetre);

        donnesCurling.setXParticule(xParticule);
        donnesCurling.setYParticule(yParticule);
        donnesCurling.setChampE(champE);
        donnesCurling.setChampM(champM);
        donnesCurling.setChargeQ(chargeQ);
        donnesCurling.setMasse(masse);
        donnesCurling.setPoints(points);
        donnesCurling.setTirsRestants(tirsRestants);
        donnesCurling.setNbEssais(nbEssais);

        GestionnaireDesDonnes.getInstance().setDonnesCurling(donnesCurling);

        spinnerChampE.setValue(champE.getY());
        spinnerChampM.setValue(champM.getZ());
        spinnerChargeParticule.setValue(chargeQ);
        spinnerMasseParticule.setValue(masse);

        htmlTexteVitesseX = new String("<html><font color='red'><center>" +
                String.format("%.3f", 0.0) + "</center></font></html>");

        textPaneVitX.setText(htmlTexteVitesseX);

        htmlTexteVitesseY = new String("<html><font color='red'><center>" +
                String.format("%.3f", 0.0) + "</center></font></html>");

        textPaneVitY.setText(htmlTexteVitesseY);

        lblPointsNb.setText(String.valueOf(points));
        lblTirsRestantsNb.setText(String.valueOf(tirsRestants));

        repaint();
        requestFocusInWindow();

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
		FenetreAideInstructions fenetreAideInstructions = new FenetreAideInstructions(
                new String[] { "Instructions/Curling/page1.jpeg", "Instructions/Curling/page2.jpeg",
                        "Instructions/Curling/page3.jpeg", "Instructions/Curling/page4.jpeg",
                        "Instructions/Curling/page5.jpeg" });
        fenetreAideInstructions.setVisible(true);
	}
	
    /**
     * Méthode pour ouvrir le menu d'instructions au professeur avec lors du clic
     * sur le bouton
     */
    // Liangchen Liu
    private void exProf() {
        btnExProf.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent arg0) {
                JOptionPane.showMessageDialog(null, INSTRUCTIONS, "Fonctionnalités présentes",
                        JOptionPane.INFORMATION_MESSAGE);

                repaint();
                requestFocusInWindow();
            }// fin méthode
        });// fin listener

    }// fin méthode

    /**
     * Méthode pour ouvrir le menu avec des instructions lors du clic sur le bouton
     */
    // Liangchen Liu
    private void infoJoueur() {
        btnInformations.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                lblInfoEtudiant.setVisible(true);
            }

            public void mouseExited(MouseEvent evt) {
                lblInfoEtudiant.setVisible(false);
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
                calculerUneIterationPhysique(deltaT);
                repaint();
                requestFocusInWindow();
            }// fin méthode
        });// fin listener

    }// fin méthode

    /**
     * Méthode pour affichier les valeurs scientifiques des forces
     */
    // Liangchen Liu
    private void afficherLesValeursScientifiquesForces() {
        forceM = particule.getChampM().cross(particule.getVitesse()).multiply(donnesCurling.getChargeQ());
        forceE = champE.multiply(chargeQ);

        htmlTexteForceM = new String("<html><font color='red'><center>" +
                forceM.toString() + "</center></font></html>");

        textPaneForceM.setText(htmlTexteForceM);

        htmlTexteForceE = new String("<html><font color='red'><center>" +
                forceE.toString() + "</center></font></html>");

        textPaneForceE.setText(htmlTexteForceE);

    }

    /**
     * Méthode pour affichier les valeurs scientifiques de la vitesse et de
     * l'accélération
     */
    // Liangchen Liu
    private void afficherLesValeursScientifiquesVitAcc() {
        if (champE.getY() == 0 && champM.getZ() == 0) {
            htmlTexteVitesseX = new String("<html><font color='red'><center>" +
                    String.format("%.3f", vitParticule.getX()) + "</center></font></html>");

            textPaneVitX.setText(htmlTexteVitesseX);

            htmlTexteVitesseY = new String("<html><font color='red'><center>" +
                    String.format("%.3f", vitParticule.getY()) + "</center></font></html>");

            textPaneVitY.setText(htmlTexteVitesseY);

        } else {
            htmlTexteVitesseX = new String("<html><font color='red'><center>" +
                    String.format("%.3f", particule.getVitesse().getY()) + "</center></font></html>");

            textPaneVitX.setText(htmlTexteVitesseX);

            htmlTexteVitesseY = new String("<html><font color='red'><center>" +
                    String.format("%.3f", particule.getVitesse().getY()) + "</center></font></html>");

            textPaneVitY.setText(htmlTexteVitesseY);

            htmlTexteAccelerationX = new String("<html><font color='red'><center>" +
                    String.format("%.3f", particule.getAcceleration().getX()) + "</center></font></html>");

            textPaneAccX.setText(htmlTexteAccelerationX);

            htmlTexteAccelerationY = new String("<html><font color='red'><center>" +
                    String.format("%.3f", particule.getAcceleration().getY()) + "</center></font></html>");

            textPaneAccY.setText(htmlTexteAccelerationY);
        }
    }

    /**
     * Les composants JSwing
     */
    // Liangchen Liu
    private void composantsJSwing() {
        btnProchImg.setBounds(205, 503, 45, 45);
        btnExProf.setBounds(150, 503, 45, 45);
        btnReinitialiser.setBounds(95, 503, 45, 45);
        btnAide.setBounds(40, 503, 45, 45);
        btnInformations.setBounds(40, 137, 45, 45);
        btnSauvegarder.setBounds(40, 81, 45, 45);

        setBackground(Color.white);
        setPreferredSize(new Dimension(1500, 700));

        lblInfoEtudiant = new JLabel("Obtenez 5 points en tirant sur les cibles");
        lblInfoEtudiant.setBounds(117, 137, 290, 45);
        lblInfoEtudiant.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblInfoEtudiant.setEnabled(true);
        lblInfoEtudiant.setVisible(false);
        setLayout(null);
        add(lblInfoEtudiant);

        spinnerChampM = new JSpinner();
        spinnerChampM.setBounds(30, 610, 190, 35);
        spinnerChampM.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                champM = new SVector3d(0, 0, (Double) spinnerChampM.getValue());
                donnesCurling.setChampM(champM);
                sliderChampM.setValue((int) champM.getZ());

                if (donnesCurling.getChampM().getZ() > 0) {
                    imageDirectionChampM = new ImageDirectionChampM(
                            ((xPlaqueHorizontale + largeurPlaque / 2) - (dimensionImage / 2)),
                            ((yPlaqueHaut / 2) - (dimensionImage / 2)),
                            dimensionImage, dimensionImage, lienImageChampMpositif);

                } else if (donnesCurling.getChampM().getZ() < 0) {
                    imageDirectionChampM = new ImageDirectionChampM(
                            ((xPlaqueHorizontale + largeurPlaque / 2) - (dimensionImage / 2)),
                            ((yPlaqueHaut / 2) - (dimensionImage / 2)),
                            dimensionImage, dimensionImage, lienImageChampMnegatif);
                }

            }// fin méthode
        });// fin listener
        spinnerChampM.setModel(new SpinnerNumberModel(0.0, -40.0, 40.0, 1d));
        add(spinnerChampM);

        spinnerChampE = new JSpinner();
        spinnerChampE.setBounds(430, 610, 190, 35);
        spinnerChampE.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                champE = new SVector3d(0, (Double) spinnerChampE.getValue(), 0);
                donnesCurling.setChampE(champE);
                sliderChampE.setValue((int) champE.getY());

                if (donnesCurling.getChampE().getY() > 0) {
                    imageDirectionChampE();
                    imageDirectionChampE.setZoneChampNegatif(false);

                    imageSingesPlaqueHaut = new ImageSignes(
                            (xPlaqueHorizontale + largeurPlaque / 2) - dimensionImage / 2,
                            yPlaqueHaut + hauteurPlaque / 2 - dimensionImage / 2,
                            dimensionImage, dimensionImage, lienImageParticuleNegatif);

                    imageSingesPlaqueBas = new ImageSignes(
                            (xPlaqueHorizontale + largeurPlaque / 2) - dimensionImage / 2,
                            yPlaqueBas + hauteurPlaque / 2 - dimensionImage / 2,
                            dimensionImage, dimensionImage, lienImageParticulePositif);

                } else if (donnesCurling.getChampE().getY() < 0) {
                    imageDirectionChampE();
                    imageDirectionChampE.setZoneChampNegatif(true);

                    imageSingesPlaqueHaut = new ImageSignes(
                            (xPlaqueHorizontale + largeurPlaque / 2) - dimensionImage / 2,
                            yPlaqueHaut + hauteurPlaque / 2 - dimensionImage / 2,
                            dimensionImage, dimensionImage, lienImageParticulePositif);

                    imageSingesPlaqueBas = new ImageSignes(
                            (xPlaqueHorizontale + largeurPlaque / 2) - dimensionImage / 2,
                            yPlaqueBas + hauteurPlaque / 2 - dimensionImage / 2,
                            dimensionImage, dimensionImage, lienImageParticuleNegatif);

                }

            }
        });
        spinnerChampE.setModel(new SpinnerNumberModel(0.0, -500.0, 500.0, 1.0));
        add(spinnerChampE);

        rdbtnVecteurVitesse = new JRadioButton("Vecteur Vitesse");
        BUTTON_GROUP.add(rdbtnVecteurVitesse);
        rdbtnVecteurVitesse.setBounds(975, 586, 127, 21);
        rdbtnVecteurVitesse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                afficherFlecheVitesse = rdbtnVecteurVitesse.isSelected();
                afficherFlecheAccel = rdbtnVecteurAccel.isSelected();
                requestFocusInWindow();
            }// fin méthode
        });// fin listener
        rdbtnVecteurVitesse.setSelected(true);
        add(rdbtnVecteurVitesse);

        rdbtnVecteurAccel = new JRadioButton("Vecteur Accélération");
        BUTTON_GROUP.add(rdbtnVecteurAccel);
        rdbtnVecteurAccel.setBounds(975, 622, 127, 21);
        rdbtnVecteurAccel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                afficherFlecheVitesse = rdbtnVecteurVitesse.isSelected();
                afficherFlecheAccel = rdbtnVecteurAccel.isSelected();
                requestFocusInWindow();
            }// fin méthode
        });// fin listener
        add(rdbtnVecteurAccel);

        lblChampMTexte = new JLabel("Champ Magnétique (T)");
        lblChampMTexte.setHorizontalAlignment(SwingConstants.CENTER);
        lblChampMTexte.setBounds(30, 590, 190, 13);
        add(lblChampMTexte);

        lblChampETexte = new JLabel("Champ Électrique (N/C)");
        lblChampETexte.setHorizontalAlignment(SwingConstants.CENTER);
        lblChampETexte.setBounds(430, 590, 190, 13);
        add(lblChampETexte);

        sliderChampM = new JSlider();
        sliderChampM.setPaintTicks(true);
        sliderChampM.setMinorTickSpacing(2);
        sliderChampM.setMajorTickSpacing(10);
        sliderChampM.setMinimum(-40);
        sliderChampM.setMaximum(40);
        sliderChampM.setBounds(30, 650, 190, 30);
        sliderChampM.setValue((int) donnesCurling.getChampM().getZ());
        add(sliderChampM);
        sliderChampM.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                donnesCurling.setChampM(new SVector3d(0, 0, sliderChampM.getValue()));
                spinnerChampM.setValue((double) sliderChampM.getValue());

                htmlTexteChampM = new String("<html><font color='red'><center>" + donnesCurling.getChampM().toString()
                        + "</center></font></html>");

                textPaneChampM.setText(htmlTexteChampM);

                if ((double) sliderChampM.getValue() == 0) {
                    textPaneAvertissement.setVisible(true);
                    htmlTexteAvertissement = new String(
                            "<html><font color='red'><b><center><h1 style='font-size:15px'>"
                                    + "Le champ magnétique ne peut pas être nulle"
                                    + "</h1></center></b></font></html>");

                    textPaneAvertissement.setText(htmlTexteAvertissement);
                } else {

                    textPaneAvertissement.setVisible(false);
                }
            }// fin méthode
        });// fin listener

        sliderChampE = new JSlider();
        sliderChampE.setPaintTicks(true);
        sliderChampE.setMinorTickSpacing(20);
        sliderChampE.setMajorTickSpacing(100);
        sliderChampE.setMinimum(-500);
        sliderChampE.setMaximum(500);
        sliderChampE.setBounds(430, 650, 190, 30);
        sliderChampE.setValue((int) donnesCurling.getChampE().getY());
        add(sliderChampE);
        sliderChampE.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                donnesCurling.setChampE(new SVector3d(0, sliderChampE.getValue(), 0));
                spinnerChampE.setValue((double) sliderChampE.getValue());

                htmlTexteChampE = new String(
                        "<html><font color='red'><center>" + donnesCurling.getChampE().toString()
                                + "</center></font></html>");

                textPaneChampE.setText(htmlTexteChampE);

                if ((double) sliderChampE.getValue() == 0) {
                    textPaneAvertissement.setVisible(true);
                    htmlTexteAvertissement = new String(
                            "<html><font color='red'><b><center><h1 style='font-size:15px'>"
                                    + "Le champ électrique de la particule ne peut pas être nulle"
                                    + "</h1></center></b></font></html>");

                    textPaneAvertissement.setText(htmlTexteAvertissement);
                } else {

                    textPaneAvertissement.setVisible(false);
                }
            }// fin méthode
        });// fin listener

        btnInverserChampM = new JButton("Inverser le champ magnétique");
        btnInverserChampM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                champM = new SVector3d(0, 0, donnesCurling.getChampM().getZ() * -1);
                donnesCurling.setChampM(champM);
                spinnerChampM.setValue((double) donnesCurling.getChampM().getZ());
                sliderChampM.setValue((int) donnesCurling.getChampM().getZ());
            }// fin méthode
        });// fin listener
        btnInverserChampM.setToolTipText("");
        btnInverserChampM.setBounds(230, 590, 177, 90);
        add(btnInverserChampM);

        btnInverserPlaques = new JButton("Inverser les plaques");
        btnInverserPlaques.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnInverserPlaques.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                champE = new SVector3d(0, donnesCurling.getChampE().getY() * -1, 0);
                donnesCurling.setChampE(champE);
                spinnerChampE.setValue((double) donnesCurling.getChampE().getY());
                sliderChampE.setValue((int) donnesCurling.getChampE().getY());

            }// fin méthode
        });// fin listener
        btnInverserPlaques.setToolTipText("");
        btnInverserPlaques.setBounds(630, 587, 177, 90);
        add(btnInverserPlaques);

        spinnerChargeParticule = new JSpinner();
        spinnerChargeParticule.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                donnesCurling.setChargeQ((double) spinnerChargeParticule.getValue());

                imageSignesParticule();

                if ((double) spinnerChargeParticule.getValue() == 0) {
                    textPaneAvertissement.setVisible(true);
                    htmlTexteAvertissement = new String(
                            "<html><font color='red'><b><center><h1 style='font-size:15px'>"
                                    + "La charge de la particule ne peut pas être nulle"
                                    + "</h1></center></b></font></html>");

                    textPaneAvertissement.setText(htmlTexteAvertissement);
                } else {

                    textPaneAvertissement.setVisible(false);
                }

            }// fin méthode
        });// fin listener
        spinnerChargeParticule.setModel(new SpinnerNumberModel(chargeQ, null, null, 0.5));
        spinnerChargeParticule.setBounds(817, 604, 117, 25);
        add(spinnerChargeParticule);

        lblChargeParticule = new JLabel("Charge Particule (C)");
        lblChargeParticule.setHorizontalAlignment(SwingConstants.CENTER);
        lblChargeParticule.setBounds(817, 584, 117, 13);
        add(lblChargeParticule);

        lblTemps = new JLabel();
        lblTemps.setFont(AppPrincipale7.interBold.deriveFont(12f));
        lblTemps.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTemps.setBounds(852, 8, 249, 21);
        lblTemps.setText(tempsEcoule + "");
        add(lblTemps);

        lblMasseParticule = new JLabel("Masse Particule (kg)");
        lblMasseParticule.setHorizontalAlignment(SwingConstants.CENTER);
        lblMasseParticule.setBounds(817, 640, 117, 13);
        add(lblMasseParticule);

        spinnerMasseParticule = new JSpinner();
        spinnerMasseParticule.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                donnesCurling.setMasse((double) spinnerMasseParticule.getValue());

            }// fin méthode
        });// fin listener
        spinnerMasseParticule.setModel(new SpinnerNumberModel((double) masse, 0.0, null, 1d));
        spinnerMasseParticule.setBounds(817, 655, 117, 25);
        add(spinnerMasseParticule);

        lblPointsTxt = new JLabel("points");
        lblPointsTxt.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPointsTxt.setBounds(1028, 40, 73, 21);
        add(lblPointsTxt);

        lblPointsNb = new JLabel("0");
        lblPointsNb.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPointsNb.setBounds(1005, 40, 61, 21);
        lblPointsNb.setText(String.valueOf(points));
        add(lblPointsNb);

        lblTirsRestantsTxt = new JLabel("tirs restants");
        lblTirsRestantsTxt.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTirsRestantsTxt.setBounds(1028, 72, 73, 14);
        add(lblTirsRestantsTxt);

        lblTirsRestantsNb = new JLabel("0");
        lblTirsRestantsNb.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTirsRestantsNb.setBounds(983, 72, 52, 14);
        lblTirsRestantsNb.setText(String.valueOf(tirsRestants));
        add(lblTirsRestantsNb);

        panelBordureChampM = new JPanel();
        panelBordureChampM
                .setBorder(new TitledBorder(
                        new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                        "<html><font size = '2'><center>" + "Champ Magnétique (T)"
                                + "</center></font></html>",
                        TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelBordureChampM.setBounds(1139, 19, 137, 67);
        // panelBordureChampMActionPerformed(null);
        add(panelBordureChampM);
        panelBordureChampM.setLayout(null);

        textPaneChampM = new JTextPane();
        textPaneChampM.setEditable(false);
        textPaneChampM.setContentType("text/html");
        textPaneChampM.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPaneChampM.setBounds(6, 16, 125, 45);
        textPaneChampM.setText(htmlTexteChampM);
        panelBordureChampM.add(textPaneChampM);

        panelBordureChampE = new JPanel();
        panelBordureChampE.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "<html><font size = '2'><center>" + "Champ électrique (N/C)"
                        + "</center></font></html>",
                TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelBordureChampE.setBounds(1139, 99, 137, 67);
        add(panelBordureChampE);
        panelBordureChampE.setLayout(null);

        textPaneChampE = new JTextPane();
        textPaneChampE.setEditable(false);
        textPaneChampE.setContentType("text/html");
        textPaneChampE.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPaneChampE.setBounds(6, 16, 125, 45);
        textPaneChampE.setText(htmlTexteChampE);
        panelBordureChampE.add(textPaneChampE);

        panelForceE = new JPanel();
        panelForceE.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "<html><font size = '2'><center>" + "Force électrique (N)"
                        + "</center></font></html>",
                TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelForceE.setBounds(1139, 180, 137, 66);
        add(panelForceE);
        panelForceE.setLayout(null);

        textPaneForceE = new JTextPane();
        textPaneForceE.setEditable(false);
        textPaneForceE.setBounds(6, 15, 125, 45);
        textPaneForceE.setContentType("text/html");
        textPaneForceE.setAlignmentX(0.5f);
        textPaneForceE.setText(htmlTexteForceE);
        panelForceE.add(textPaneForceE);

        panelForceM = new JPanel();
        panelForceM.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "<html><font size = '2'><center>" + "Force magnétique (N)"
                        + "</center></font></html>",
                TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelForceM.setBounds(1139, 259, 137, 67);
        add(panelForceM);
        panelForceM.setLayout(null);

        textPaneForceM = new JTextPane();
        textPaneForceM.setBounds(6, 16, 125, 45);
        textPaneForceM.setEditable(false);
        textPaneForceM.setText((String) null);
        textPaneForceM.setContentType("text/html");
        textPaneForceM.setAlignmentX(0.5f);
        textPaneForceM.setText(htmlTexteForceM);
        panelForceM.add(textPaneForceM);

        panelAccX = new JPanel();
        panelAccX.setLayout(null);
        panelAccX.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "<html><font size = '2'><center>" + "Accélération X (m/s²)"
                        + "</center></font></html>",
                TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelAccX.setBounds(1139, 337, 137, 67);
        add(panelAccX);

        textPaneAccX = new JTextPane();
        textPaneAccX.setText((String) null);
        textPaneAccX.setEditable(false);
        textPaneAccX.setContentType("text/html");
        textPaneAccX.setAlignmentX(0.5f);
        textPaneAccX.setBounds(6, 16, 125, 45);
        textPaneAccX.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPaneAccX.setText(htmlTexteAccelerationX);
        panelAccX.add(textPaneAccX);

        panelAccY = new JPanel();
        panelAccY.setLayout(null);
        panelAccY.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "<html><font size = '2'><center>" + "Accélération Y (m/s²)"
                        + "</center></font></html>",
                TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelAccY.setBounds(1139, 415, 137, 67);
        add(panelAccY);

        textPaneAccY = new JTextPane();
        textPaneAccY.setText((String) null);
        textPaneAccY.setEditable(false);
        textPaneAccY.setContentType("text/html");
        textPaneAccY.setAlignmentX(0.5f);
        textPaneAccY.setBounds(6, 16, 125, 45);
        textPaneAccY.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPaneAccY.setText(htmlTexteAccelerationY);
        panelAccY.add(textPaneAccY);

        panelVitX = new JPanel();
        panelVitX.setLayout(null);
        panelVitX.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "<html><font size = '2'><center>" + "Vitesse X (m/s)"
                        + "</center></font></html>",
                TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelVitX.setBounds(1139, 493, 137, 67);
        add(panelVitX);

        textPaneVitX = new JTextPane();
        textPaneVitX.setText((String) null);
        textPaneVitX.setEditable(false);
        textPaneVitX.setContentType("text/html");
        textPaneVitX.setAlignmentX(0.5f);
        textPaneVitX.setBounds(6, 16, 125, 45);
        textPaneVitX.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPaneVitX.setText(htmlTexteVitesseX);
        panelVitX.add(textPaneVitX);

        panelVitY = new JPanel();
        panelVitY.setLayout(null);
        panelVitY.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "<html><font size = '2'><center>" + "Vitesse Y (m/s)"
                        + "</center></font></html>",
                TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelVitY.setBounds(1139, 571, 137, 67);
        add(panelVitY);

        textPaneVitY = new JTextPane();
        textPaneVitY.setText((String) null);
        textPaneVitY.setEditable(false);
        textPaneVitY.setContentType("text/html");
        textPaneVitY.setAlignmentX(0.5f);
        textPaneVitY.setBounds(6, 16, 125, 45);
        textPaneVitY.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPaneVitY.setText(htmlTexteVitesseY);
        panelVitY.add(textPaneVitY);

        btnGraphiques = new JButton("Graphiques");

        btnGraphiques.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetreGraphiques.reini();
                fenetreGraphiques.setVisible(true);

            }
        });
        btnGraphiques.setBounds(1140, 650, 135, 40);
        add(btnGraphiques);

        textPaneAvertissement = new JTextPane();
        textPaneAvertissement.setBackground(Color.WHITE);
        textPaneAvertissement.setContentType("text/html");
        textPaneAvertissement.setEditable(false);
        textPaneAvertissement.setEnabled(true);
        textPaneAvertissement.setVisible(false);
        textPaneAvertissement.setBounds(287, 503, 595, 45);
        add(textPaneAvertissement);

    }// fin méthode

    /**
     * Méthode pour dessiner la flèche de la vitesse initialle
     */
    // Liangchen Liu
    private void dessinerFlechVecIni() {
        g2d.setStroke(new BasicStroke(2));
        flecheVit.setCouleurFleche(Color.red);
        flecheVit.redimensionneCorps(1);
        flecheVit.setAngleTete(90);
        flecheVit.setLongueurTraitDeTete(5 / pixelsParMetre);
        flecheVit.setPixelsParMetre(pixelsParMetre);

        flecheVit.dessiner(g2d);

    }// fin méthode

    /**
     * Méthode qui détermine la position de la plaque et de sa hauteur
     */
    // Liangchen Liu
    private void determinerPositionHauteurBut() {
        double rExtremiteSection = (double) (Math.ceil(Math.random() * 2));
        double rExtremiteSection1 = (double) (Math.random() * hauteurExtBut1);
        double rExtremiteSection2 = rExtremiteSection1 + hauteurExtBut2;

        double rLongueurSectionMilieu = (double) (Math.random() * hauteurExtButMilieu + hauteurExtBut1);

        if (rLongueurSectionMilieu + hauteurButMin > hauteurExtBut2) {
            zoneButMilieu = new ZoneBut(xBut,
                    rLongueurSectionMilieu - (rLongueurSectionMilieu + hauteurButMin - hauteurExtBut2), largeurBut,
                    hauteurButMin);
            imagesButMilieu(xBut,
                    rLongueurSectionMilieu - (rLongueurSectionMilieu + hauteurButMin - hauteurExtBut2), largeurBut,
                    hauteurButMin, lienImageCible);

        } else {
            zoneButMilieu = new ZoneBut(xBut, rLongueurSectionMilieu, largeurBut, hauteurButMin);
            imagesButMilieu(xBut, rLongueurSectionMilieu, largeurBut, hauteurButMin, lienImageCible);

        } // fin if else

        double rLongueur = (double) (Math.ceil(Math.random() * 2));
        if (rExtremiteSection == 1) {
            if (rLongueur == 1) {
                if (rExtremiteSection1 + hauteurButMoyen > hauteurExtBut1) {
                    zoneButExt = new ZoneBut(xBut,
                            rExtremiteSection1 - (rExtremiteSection1 + hauteurButMoyen - hauteurExtBut1),
                            largeurBut, hauteurButMoyen);
                    imagesButExt(xBut,
                            rExtremiteSection1 - (rExtremiteSection1 + hauteurButMoyen - hauteurExtBut1),
                            largeurBut, hauteurButMoyen, lienImageCible);

                } else {
                    zoneButExt = new ZoneBut(xBut, rExtremiteSection1, largeurBut, hauteurButMoyen);
                    imagesButExt(xBut, rExtremiteSection1, largeurBut, hauteurButMoyen, lienImageCible);

                } // fin if else

            } else {
                if (yBut + hauteurButMax > rExtremiteSection1) {
                    zoneButExt = new ZoneBut(xBut,
                            rExtremiteSection1 - (rExtremiteSection1 + hauteurButMax - hauteurExtBut1),
                            largeurBut, hauteurButMax);
                    imagesButExt(xBut, rExtremiteSection1 - (rExtremiteSection1 + hauteurButMax - hauteurExtBut1),
                            largeurBut, hauteurButMax, lienImageCible);

                } else {
                    zoneButExt = new ZoneBut(xBut, rExtremiteSection1, largeurBut, hauteurButMax);
                    imagesButExt(xBut, rExtremiteSection1, largeurBut, hauteurButMax, lienImageCible);

                } // fin if else

            } // fin if else

        } else {
            if (rLongueur == 2) {
                if (rExtremiteSection2 + hauteurButMoyen > hauteurZoneAnimation) {
                    zoneButExt = new ZoneBut(xBut,
                            rExtremiteSection2 - (rExtremiteSection2 + hauteurButMoyen - hauteurZoneAnimation),
                            largeurBut, hauteurButMoyen);
                    imagesButExt(xBut,
                            rExtremiteSection2 - (rExtremiteSection2 + hauteurButMoyen - hauteurZoneAnimation),
                            largeurBut, hauteurButMoyen, lienImageCible);

                } else {
                    zoneButExt = new ZoneBut(xBut, rExtremiteSection2, largeurBut, hauteurButMoyen);
                    imagesButExt(xBut, rExtremiteSection2, largeurBut, hauteurButMoyen, lienImageCible);

                } // fin if else

            } else {
                if (rExtremiteSection2 + hauteurButMax > hauteurZoneAnimation) {
                    zoneButExt = new ZoneBut(xBut, yBut - (yBut + hauteurButMax - hauteurZoneAnimation), largeurBut,
                            hauteurButMax);
                    imagesButExt(xBut, yBut - (yBut + hauteurButMax - hauteurZoneAnimation), largeurBut,
                            hauteurButMax, lienImageCible);

                } else {
                    zoneButExt = new ZoneBut(xBut, rExtremiteSection2, largeurBut, hauteurButMax);
                    imagesButExt(xBut, rExtremiteSection2, largeurBut, hauteurButMax, lienImageCible);

                } // fin if else

            } // fin if else

        } // fin if else
        zoneButFormeMilieu = zoneButMilieu.getZoneBut();
        zoneButFormeExt = zoneButExt.getZoneBut();

    }// fin méthode

    /**
     * Méthode pour dessiner la flèche de la vitesse initialle
     */
    // Par Jason Yin
    private void dessinerFlechVecLance() {
        flecheVitParticuleTotal = new FlecheVectorielle(particule.getPosition().getX() + diametreParticule / 2,
                particule.getPosition().getY() + diametreParticule / 2, particule.getVitesse());

        flecheVitParticuleTotal.setPixelsParMetre(pixelsParMetre);
        flecheVitParticuleTotal.setCouleurFleche(Color.RED);
        flecheVitParticuleTotal.redimensionneCorps(3);
        flecheVitParticuleTotal.setAngleTete(90);
        flecheVitParticuleTotal.setLongueurTraitDeTete(5 / pixelsParMetre);

        flecheVitParticuleX = new FlecheVectorielle(particule.getPosition().getX() + diametreParticule / 2,
                particule.getPosition().getY() + diametreParticule / 2,
                new SVector3d(particule.getVitesse().getX(), 0, 0));

        flecheVitParticuleX.setPixelsParMetre(pixelsParMetre);
        flecheVitParticuleX.setCouleurFleche(Color.BLUE);
        flecheVitParticuleX.redimensionneCorps(3);
        flecheVitParticuleX.setAngleTete(90);
        flecheVitParticuleX.setLongueurTraitDeTete(5 / pixelsParMetre);

        flecheVitParticuleY = new FlecheVectorielle(particule.getPosition().getX() + diametreParticule / 2,
                particule.getPosition().getY() + diametreParticule / 2,
                new SVector3d(0, particule.getVitesse().getY(), 0));

        flecheVitParticuleY.setPixelsParMetre(pixelsParMetre);
        flecheVitParticuleY.setCouleurFleche(Color.GREEN);
        flecheVitParticuleY.redimensionneCorps(3);
        flecheVitParticuleY.setAngleTete(90);
        flecheVitParticuleY.setLongueurTraitDeTete(5 / pixelsParMetre);

        flecheAccParticuleTotal = new FlecheVectorielle(particule.getPosition().getX() + diametreParticule / 2,
                particule.getPosition().getY() + diametreParticule / 2, particule.getAcceleration());

        flecheAccParticuleTotal.setPixelsParMetre(pixelsParMetre);
        flecheAccParticuleTotal.setCouleurFleche(Color.YELLOW);
        flecheAccParticuleTotal.redimensionneCorps(10);
        flecheAccParticuleTotal.setAngleTete(90);
        flecheAccParticuleTotal.setLongueurTraitDeTete(5 / pixelsParMetre);

        flecheAccParticuleX = new FlecheVectorielle(particule.getPosition().getX() + diametreParticule / 2,
                particule.getPosition().getY() + diametreParticule / 2,
                new SVector3d(particule.getAcceleration().getX(), 0, 0));

        flecheAccParticuleX.setPixelsParMetre(pixelsParMetre);
        flecheAccParticuleX.setCouleurFleche(Color.CYAN);
        flecheAccParticuleX.redimensionneCorps(10);
        flecheAccParticuleX.setAngleTete(90);
        flecheAccParticuleX.setLongueurTraitDeTete(5 / pixelsParMetre);

        flecheAccParticuleY = new FlecheVectorielle(particule.getPosition().getX() + diametreParticule / 2,
                particule.getPosition().getY() + diametreParticule / 2,
                new SVector3d(0, particule.getAcceleration().getY(), 0));

        flecheAccParticuleY.setPixelsParMetre(pixelsParMetre);
        flecheAccParticuleY.setCouleurFleche(Color.ORANGE);
        flecheAccParticuleY.redimensionneCorps(10);
        flecheAccParticuleY.setAngleTete(90);
        flecheAccParticuleY.setLongueurTraitDeTete(5 / pixelsParMetre);

        if (afficherFlecheVitesse) {
            flecheVitParticuleX.dessiner(g2d);
            flecheVitParticuleY.dessiner(g2d);
            flecheVitParticuleTotal.dessiner(g2d);
        } // fin if
        if (afficherFlecheAccel) {
            flecheAccParticuleX.dessiner(g2d);
            flecheAccParticuleY.dessiner(g2d);
            flecheAccParticuleTotal.dessiner(g2d);
        } // fin if

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

    // Liangchen Liu
    public FenetreGraphiques getFenetreGraphiques() {
        return fenetreGraphiques;
    }

    // Liangchen Liu
    public void setFenetreGraphiques(FenetreGraphiques fenetreGraphiques) {
        this.fenetreGraphiques = fenetreGraphiques;
    }
}// fin classe
