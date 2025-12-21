package menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import GestionDesFichiers.GestionnaireDesDonnes;
import outils.OutilsImage;
import selecteur_niveau.DonnesEtoiles;

/**
 * Classe représentant l'interface de fin des jeux.
 * 
 * @author Tin Pham
 * @author Liangchen Liu
 */
public class MenuFinJeu extends JFrame {
	private VictoireJPanel contentPane;
	private final Color TRANSPARENT_COLOR = new Color(0,0,0,0);

	private ImageIcon iconeReturn;
	private ImageIcon iconeReinitialiser;
	private ImageIcon iconeFleche;
	private JButton btnReturn;
	private JButton btnRecommencer;
	private JButton btnSuivant;
	private int largeurBouton =45;
	private DonnesEtoiles donnesEtoiles;

	private final PropertyChangeSupport PCS = new PropertyChangeSupport(this);
	private Rectangle dimensions;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel lblMessage;
	private JButton btnCont;
	/**
	 * Constructeur de la scène.
	 */
	// Tin Pham
	public MenuFinJeu() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setForeground(TRANSPARENT_COLOR);
		setBackground(TRANSPARENT_COLOR);
		dimensions = new Rectangle((int) screenSize.getWidth() / 2 - 400 / 2,
				(int) screenSize.getHeight() / 2 - 300 / 2, 400, 300);
		setBounds(dimensions);
		contentPane = new VictoireJPanel(1);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		

		btnRecommencer = new JButton("");
		btnRecommencer.setToolTipText("Recommencez le jeu");
		btnRecommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recommencer();
			}
		});
		btnRecommencer.setBounds(148, 220, largeurBouton, largeurBouton);
		iconeReinitialiser = new ImageIcon(OutilsImage.lireImageEtRedimensionner("reinitialiser.png", 45, 45));
		btnRecommencer.setIcon(iconeReinitialiser);
		btnRecommencer.setBackground(TRANSPARENT_COLOR);
		btnRecommencer.setOpaque(false);
		btnRecommencer.setBorder(null);
		getContentPane().add(btnRecommencer);
		
		btnReturn = new JButton("");
		btnReturn.setToolTipText("Retourner au selecteur de niveau");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retour();
			}
		});
		btnReturn.setBounds(93, 220, largeurBouton, largeurBouton);
		iconeReturn = new ImageIcon(OutilsImage.lireImageEtRedimensionner("returndark.png", 46, 46));
		btnReturn.setIcon(iconeReturn);
		btnReturn.setBackground(TRANSPARENT_COLOR);
		btnReturn.setOpaque(false);
		btnReturn.setBorder(null);
		getContentPane().add(btnReturn);
		
		btnSuivant = new JButton("");
		btnSuivant.setToolTipText("Niveau suivant");
		btnSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				suivant();
			}
		});
		btnSuivant.setBounds(203, 220, largeurBouton, largeurBouton);
		iconeFleche = new ImageIcon(OutilsImage.lireImageEtRedimensionner("Flèche.png", 45, 45));
		btnSuivant.setIcon(iconeFleche);
		btnSuivant.setBackground(TRANSPARENT_COLOR);
		btnSuivant.setOpaque(false);
		btnSuivant.setBorder(null);
		getContentPane().add(btnSuivant);		
		lblMessage = new JLabel("");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setBounds(243, 350, 397, 14);
		contentPane.add(lblMessage);
		
		btnCont = new JButton("");
		btnCont.setIcon(new ImageIcon(OutilsImage.lireImageEtRedimensionner("continuer.png", 45, 45)));
		btnCont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("continuer", -1, 1);
			}
		});
		btnCont.setToolTipText("Continuer de jouer au jeu");
		btnCont.setOpaque(false);
		btnCont.setBorder(null);
		btnCont.setBackground(new Color(0, 0, 0, 0));
		btnCont.setBounds(258, 220, 45, 45);
		contentPane.add(btnCont);

	}// Fin constructeur

	/**
	 * Méthode qui permet de retourner au sélecteur de niveaux.
	 */
	// Liangchen Liu
	private void retour() {
		PCS.firePropertyChange("return", -1, 1);

	}

	/**
	 * Méthode qui permet de recommencer le jeu.
	 */
	// Liangchen Liu
	private void recommencer() {
		PCS.firePropertyChange("recommencer", -1, 1);

	}

	/**
	 * Méthode qui permet de passer au niveau suivant.
	 */
	// Liangchen Liu
	private void suivant() {
		PCS.firePropertyChange("suivant", -1, 1);

	}
	
	/**
	 * Méthode qui permet de continuer de jouer au jeu
	 */
	// Liangchen Liu
	private void continuer() {
		PCS.firePropertyChange("continuer", -1, 1);
	}
	
	/**
	 * Méthode qui permet de mettre à jour les étoiles affichées.
	 * 
	 * @param niveau  Le niveau du jeu.
	 */
	// Liangchen Liu
	public void majEtoilesAffiches(int niveau) {
		donnesEtoiles = GestionnaireDesDonnes.getInstance().getDonnesEtoiles();
		switch (niveau) {
			case 1:
				int nbEtoilesCircuit = donnesEtoiles.getNbEtoilesCircuit();

				switch (nbEtoilesCircuit) {
					case 0:
						contentPane.setNbEtoiles(0);
						btnSuivant.setEnabled(false);

						break;

					case 1:
						contentPane.setNbEtoiles(1);
						btnSuivant.setEnabled(false);

						break;

					case 2:
						contentPane.setNbEtoiles(2);
						btnSuivant.setEnabled(true);

						break;
					
					case 3:
						contentPane.setNbEtoiles(3);
						btnSuivant.setEnabled(true);

						break;
				}

				break;

			case 2:
				int nbEtoilesCurling = donnesEtoiles.getNbEtoilesCurling();

				switch (nbEtoilesCurling) {
					case 0:
						contentPane.setNbEtoiles(1);
						btnSuivant.setEnabled(false);

						break;
					case 1:
						contentPane.setNbEtoiles(1);
						btnSuivant.setEnabled(false);

						break;

					case 2:
						contentPane.setNbEtoiles(2);
						btnSuivant.setEnabled(true);

						break;
					
					case 3:
						contentPane.setNbEtoiles(3);
						btnSuivant.setEnabled(true);

						break;
				}

				break;

			case 3:
				int nbEtoilesPeche = donnesEtoiles.getNbEtoilesPeche();

				switch (nbEtoilesPeche) {
					case 0:
						contentPane.setNbEtoiles(1);
						btnSuivant.setEnabled(false);

						break;
					case 1:
						contentPane.setNbEtoiles(1);
						btnSuivant.setEnabled(false);

						break;

					case 2:
						contentPane.setNbEtoiles(2);
						btnSuivant.setEnabled(true);


						break;
					
					case 3:
						contentPane.setNbEtoiles(3);
						btnSuivant.setEnabled(true);

						break;
				}

				break;

			case 4:
				int nbEtoilesTank = donnesEtoiles.getNbEtoilesTank();

				switch (nbEtoilesTank) {
					case 0:
						contentPane.setNbEtoiles(1);
						btnSuivant.setEnabled(false);

						break;
					case 1:
						contentPane.setNbEtoiles(1);
						btnSuivant.setEnabled(false);
						break;

					case 2:
						contentPane.setNbEtoiles(2);
						btnSuivant.setEnabled(true);


						break;
					
					case 3:
						contentPane.setNbEtoiles(3);
						btnSuivant.setEnabled(true);
						break;
				}
				break;
		}
		contentPane.repaint();
	}

	// Tin Pham
	public JButton getBtnSuivant() {
		return btnSuivant;
	}

	// Tin Pham
	public void setBtnSuivant(JButton btnSuivant) {
		this.btnSuivant = btnSuivant;
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

	// Tin Pham
	public JLabel getMessageLabel() {
		return lblMessage;
	}
}// Fin classe
