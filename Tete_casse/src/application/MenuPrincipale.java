package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import GestionDesFichiers.GestionnaireDesDonnes;
import menus.FenetreAideInstructions;
import outils.OutilsImage;

/**
 * Classe représentant l'interface principale du jeu.
 * 
 * @author Ismaïl Boukari
 * @author Liangchen Liu
 * 
 */
public class MenuPrincipale extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Instance de la classe chargerLesFichiers */
	private GestionnaireDesDonnes chargerLesFichiers;

	private JLabel titreMenuPrincipal;
	private JButton btnNouvellePartie;
	private JButton btnContinuerPartie;
	private JButton btnClassement;
	private JButton btnBadges;
	private JButton btnSettings;
	private ImageIcon settingsIcon;
	private FenetreAideInstructions fenetreAideInstructions;

	/** Variable pour la largeur du titre */
	private int largeurTitreMenuPrincipal = 458;
	/** Variable pour la hauteur du titre */
	private int hauteurTitreMenuPrincipal = 64;
	/** Variable pour la postion y du titre */
	private int posYTitreMenuPrincipal = 100;

	/** Variable pour la largeur des boutons */
	private int largeurBoutons = 300;
	/** Variable pour la hauteur des boutons */
	private int hauteurBoutons = 75;
	// /** Variable pour la postion y des boutons */
	// private int posYInitBoutons = 220;
	// /** Variable pour l'espacement entre les boutons */
	// private int espacementBoutons = 20;
	/** Variable pour la largeur du bouton des paramètres */
	private int largeurBoutonSettings = 80;
	/** Les instructions lié à la touche "p" du clavier */
	private String instructions = "- Cliquer sur le bouton \"Nouvelle partie\" pour commencer une nouvelle partie où l'utilisateur va être redirigé vers de sélecteur de niveaux. \n"
			+ "- Cliquer sur le bonton \"Continuer une partie\" pour ouvrir l'écran de JFileChooser et choisir un fichier de sauvegarde, qui est disponible su le bureau qui se nomme \"Tête cassée\". Si le fichier est bon, l'application emmnènera directement l'utilisateur vers le sélecteur de niveaux\n"
			+ "- Cliquer sur le bouton image (en haut à droite) pour ouvrir l'écran des paramètres. \n";
	private JButton btnApropos;
	private JButton btnAide;

	/**
	 * Constructeur de la scène
	 */
	// Par Ismaïl Boukari
	public MenuPrincipale() {
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

		chargerLesFichiers = GestionnaireDesDonnes.getInstance();

		setFocusable(true);
		requestFocusInWindow();

		initialiserLesComposants();
	}// fin du constructeur

	/**
	 * Méthode qui initialise les composants de la scène
	 */
	// Par Ismaïl Boukari
	private void initialiserLesComposants() {
		setLayout(null);

		titreMenuPrincipal = new JLabel("");
		titreMenuPrincipal.setIcon(new ImageIcon(OutilsImage.lireImage("logotitre.png")));
		titreMenuPrincipal.setForeground(new Color(10, 10, 10));
		titreMenuPrincipal.setHorizontalAlignment(SwingConstants.CENTER);
		titreMenuPrincipal.setFont(new Font("Arial Black", Font.PLAIN, 65));
		titreMenuPrincipal.setBounds(421, -52,
				459, 468);
		add(titreMenuPrincipal);

		btnNouvellePartie = new JButton("Jouer");
		btnNouvellePartie.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnNouvellePartie.setSelected(false);
				nouvellePartie();
			}// fin méthode
		});// fin listener
		btnNouvellePartie.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNouvellePartie.setBounds(335, 324, largeurBoutons, hauteurBoutons);
		add(btnNouvellePartie);

		btnContinuerPartie = new JButton("Charger une sauvegarde");
		btnContinuerPartie.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnContinuerPartie.setSelected(false);
				continuerPartie();
			}// fin méthode
		});// fin listener
		btnContinuerPartie.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnContinuerPartie.setBounds(670, 324, largeurBoutons,
				hauteurBoutons);
		add(btnContinuerPartie);

		btnClassement = new JButton("Classement");
		btnClassement.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnClassement.setSelected(false);
				classement();
			}// fin méthode
		});// fin listener
		btnClassement.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnClassement.setBounds(335, 410, largeurBoutons,
				hauteurBoutons);
		add(btnClassement);

		btnBadges = new JButton("Badges");
		btnBadges.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnBadges.setSelected(false);
				badges();
			}// fin méthode
		});// fin listener
		btnBadges.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnBadges.setBounds(670, 410, largeurBoutons,
				hauteurBoutons);
		add(btnBadges);

		btnSettings = new JButton("");
		btnSettings.setBounds(1194, 11, largeurBoutonSettings, largeurBoutonSettings);
		settingsIcon = new ImageIcon(
				OutilsImage.lireImageEtRedimensionner("settings.png", largeurBoutonSettings, largeurBoutonSettings));
		btnSettings.setIcon(settingsIcon);
		btnSettings.setBackground(new Color(0, 0, 0, 0));
		btnSettings.setOpaque(false);
		btnSettings.setBorder(null);
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings();

			}// fin méthode
		});// fin listener
		add(btnSettings);

		btnApropos = new JButton("À propos");
		btnApropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnApropos.setSelected(false);
				aPropos();
				requestFocusInWindow();
			}
		});
		btnApropos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnApropos.setBounds(335, 496, 300, 75);
		add(btnApropos);

		btnAide = new JButton("Aide");
		btnAide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenetreAideInstructions();
				btnAide.setSelected(false);
			}
		});
		btnAide.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAide.setBounds(670, 496, 300, 75);
		add(btnAide);

	}// fin méthode

	/**
	 * Méthode qui ouvre une fenêtre pour des instructions.
	 */
	// Liangchen Liu
	private void fenetreAideInstructions() {
		fenetreAideInstructions = new FenetreAideInstructions(new String[]{"Instructions/Menus/page1.png", "Instructions/Menus/page2.png", "Instructions/Menus/page3.png" , "Instructions/Menus/page4.png" ,"Instructions/Menus/page5.jpeg"});
		fenetreAideInstructions.setVisible(true);
	}

	/**
	 * Méthode qui ouvre un panel montrant les crédits du jeu.
	 */
	// Liangchen Liu
	private void aPropos() {
		firePropertyChange("aPropos", 0, 1);
	}

	/**
	 * Méthode qui lance une nouvelle partie.
	 */
	// Liangchen Liu
	private void nouvellePartie() {
		firePropertyChange("nouvellePartie", 0, -1);

	}// fin méthode

	/**
	 * Méthode qui ouvre un menu permettant de selectioner une partie sauvegardée à
	 * continuer.
	 */
	// Liangchen Liu
	private void continuerPartie() {
		chargerLesFichiers.chargerLesFichiers();

		if (chargerLesFichiers.getReponse() == JFileChooser.APPROVE_OPTION) {
			int niveau = GestionnaireDesDonnes.getInstance().getNiveau();

			switch (niveau) {
				case 1:
					firePropertyChange("circuit", 0, 1);

					break;
				case 2:
					firePropertyChange("curling", 0, 1);

					break;
				case 3:
					firePropertyChange("peche", 0, 1);

					break;
				case 4:
					firePropertyChange("tank", 0, 1);

					break;

			}

		} // fin if

	}// fin méthode

	/**
	 * Méthode ouvrant un menu qui montre le classement des joueurs.
	 */
	// Liangchen Liu
	private void classement() {
		firePropertyChange("classements", 0, 1);
	}// fin méthode

	/**
	 * Méthode ouvrant un menu montrant les badges amassés par le joueur.
	 */
	// Liangchen Liu
	private void badges() {
		firePropertyChange("badges", 0, 1);
	}// fin méthode

	/**
	 * Méthode ouvrant le menu des paramètres.
	 */
	// Liangchen Liu
	private void settings() {
		firePropertyChange("settings", 0, -1);

	}// fin méthode
}// fin classe
