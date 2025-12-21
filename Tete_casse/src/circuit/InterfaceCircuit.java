package circuit;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import menus.FenetreAideInstructions;
import outils.OutilsImage;

/**
 * Classe représentant l'interface du jeux des circuits.
 * 
 * @author Liangchen Liu
 * @author Ismaïl Boukari
 */
public class InterfaceCircuit extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JButton btnSuppComp;
	protected JButton btnReinitialiser;
	protected JButton btnSauvegarder;
	private ImageIcon iconeReturn;
	private ImageIcon iconeSauvegarder;
	private ImageIcon iconeInformations;
	private ImageIcon iconeAide;
	private ImageIcon iconeReinitialiser;
	private ImageIcon iconeSupprimer;
	private JSeparator separatorVerticale;
	private JSeparator separatorHoriontale;
	private JButton btnAide;
	private JButton btnInformations;
	private JButton btnReturn;
	private String indice = "Le but du jeu est d'alimenter une ampoule. Toutefois, le nombre d'étoiles gagnées dépend de la complexité de votre circuit."
	+ "Plus il y a des mailles, plus vous gagnez d'étoiles! Qu'est-ce qu'une maille? Une maille est une boucle fermé qui ne passe pas deux fois par la même branche (ou fil).";
	/**
	 * Constructeur de la scène
	 */
	// Liangchen Liu
	public InterfaceCircuit() {
		setLayout(null);

		separatorVerticale = new JSeparator();
		separatorVerticale.setForeground(Color.BLACK);
		separatorVerticale.setOrientation(SwingConstants.VERTICAL);
		separatorVerticale.setBackground(Color.BLACK);
		separatorVerticale.setBounds(1300, 0, 2, 700);
		add(separatorVerticale);

		separatorHoriontale = new JSeparator();
		separatorHoriontale.setBackground(Color.BLACK);
		separatorHoriontale.setForeground(Color.BLACK);
		separatorHoriontale.setBounds(0, 500, 1300, 2);
		add(separatorHoriontale);

		btnReturn = new JButton("");
		btnReturn.setToolTipText("Retourner au menu principal");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firePropertyChange("selecNiveau", -1, 1);
			}// fin méthode
		});// fin listener

		btnReturn.setBounds(40, 25, 45, 45);
		iconeReturn = new ImageIcon(OutilsImage.lireImageEtRedimensionner("returndark.png", 46, 46));
		btnReturn.setIcon(iconeReturn);
		btnReturn.setBackground(new Color(0, 0, 0, 0));
		btnReturn.setOpaque(false);
		btnReturn.setBorder(null);
		add(btnReturn);

		btnSauvegarder = new JButton("");
		btnSauvegarder.setToolTipText("Sauvegarder");
		btnSauvegarder.setBorder(null);
		btnSauvegarder.setBounds(40, 81, 45, 45);
		iconeSauvegarder = new ImageIcon(OutilsImage.lireImageEtRedimensionner("sauvegarde_icone.png", 46, 46));
		btnSauvegarder.setIcon(iconeSauvegarder);
		btnSauvegarder.setBackground(new Color(0, 0, 0, 0));
		btnSauvegarder.setOpaque(false);
		add(btnSauvegarder);

		btnInformations = new JButton("");
		btnInformations.setToolTipText("Indice");
		btnInformations.setBorder(null);
		iconeInformations = new ImageIcon(OutilsImage.lireImageEtRedimensionner("information.png", 46, 46));
		btnInformations.setIcon(iconeInformations);
		btnInformations.setBounds(40, 137, 45, 45);
		btnInformations.setBackground(new Color(0, 0, 0, 0));
		btnInformations.setOpaque(false);
		add(btnInformations);

		btnAide = new JButton("");
		btnAide.setToolTipText("Aide/Instructions");
		btnAide.setBorder(null);
		iconeAide = new ImageIcon(OutilsImage.lireImageEtRedimensionner("aide.png", 46, 46));
		btnAide.setIcon(iconeAide);
		btnAide.setBackground(new Color(0, 0, 0, 0));
		btnAide.setOpaque(false);
		btnAide.setBounds(40, 444, 45, 45);
		add(btnAide);

		btnReinitialiser = new JButton("");
		btnReinitialiser.setToolTipText("Recommencer");
		btnReinitialiser.setBorder(null);
		iconeReinitialiser = new ImageIcon(OutilsImage.lireImageEtRedimensionner("reinitialiser.png", 46, 46));
		btnReinitialiser.setIcon(iconeReinitialiser);
		btnReinitialiser.setBackground(new Color(0, 0, 0, 0));
		btnReinitialiser.setOpaque(false);
		btnReinitialiser.setBounds(95, 444, 45, 45);
		add(btnReinitialiser);

		btnSuppComp = new JButton("");
		btnSuppComp.setToolTipText("Supprimer un composant");
		btnSuppComp.setBorder(null);
		iconeSupprimer = new ImageIcon(OutilsImage.lireImageEtRedimensionner("poubelle.png", 46, 46));
		btnSuppComp.setIcon(iconeSupprimer);
		btnSuppComp.setBackground(new Color(0, 0, 0, 0));
		btnSuppComp.setOpaque(false);
		btnSuppComp.setBounds(150, 444, 45, 45);
		add(btnSuppComp);

		aide();
		indice();
	}// fin constructeur

	/**
	 * Méthode pour ouvrir le menu avec des instructions lors du clic sur le bouton
	 */
	// Liangchen Liu
	private void aide() {
		btnAide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instructions();
				focus();
	            }// fin méthode
	        });// fin listener
	}// fin méthode
	
	/**
	 * Ouvre la fenêtre d'instructions
	 */
	// Liangchen Liu
	public void instructions() {
		FenetreAideInstructions fenetreAideInstructions = new FenetreAideInstructions(
				new String[] { "Instructions/Circuit/page1.jpg", "Instructions/Circuit/page2.jpg", "Instructions/Circuit/page3.jpg", "Instructions/Circuit/page4.jpg" });
		fenetreAideInstructions.setVisible(true);
		focus();
	}
	/**
	 * Méthode pour ouvrir une fenêtre d'indices lors du clic sur le bouton
	 */
	// Ismaïl Boukari
	public void indice() {
		btnInformations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,indice, "Indices",
						JOptionPane.INFORMATION_MESSAGE);
				focus();
			}// fin méthode
		});// fin listener
	}// fin méthode

	/**
	 * Méthode ajustant le focus sur la fenêtre
	 */
	// Ismaïl Boukari
	public void focus() {
		this.requestFocusInWindow();
	}
}// fin classe
