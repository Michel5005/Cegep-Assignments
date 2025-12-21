package menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import outils.OutilsImage;

/**
 * Classe représentant l'interface principale des jeux.
 * 
 * @author Liangchen Liu
 */
public class InterfaceGenerale extends JPanel {
	private ImageIcon iconeReturn;
	private ImageIcon iconeSauvegarder;
	private ImageIcon iconeInformations;
	private ImageIcon iconeAide;
	private ImageIcon iconeReinitialiser;
	private ImageIcon iconeExProf;
	private ImageIcon iconeProchImg;
	private JSeparator separatorVerticale;
	private JSeparator separatorHoriontale;
	private JButton btnReturn;
	protected JButton btnProchImg;
	protected JButton btnExProf;
	protected JButton btnReinitialiser;
	protected JButton btnAide;
	protected JButton btnInformations;
	protected JButton btnSauvegarder;

	// private String instructions = "";

	/**
	 * Constructeur de la scène
	 */
	public InterfaceGenerale() {
		setLayout(null);

		separatorVerticale = new JSeparator();
		separatorVerticale.setBounds(1125, 0, 2, 710);
		separatorVerticale.setForeground(Color.BLACK);
		separatorVerticale.setOrientation(SwingConstants.VERTICAL);
		separatorVerticale.setBackground(Color.BLACK);
		add(separatorVerticale);

		separatorHoriontale = new JSeparator();
		separatorHoriontale.setBounds(0, 560, 1125, 2);
		separatorHoriontale.setBackground(Color.BLACK);
		separatorHoriontale.setForeground(Color.BLACK);
		add(separatorHoriontale);

		btnReturn = new JButton("");
		btnReturn.setBounds(40, 25, 45, 45);
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firePropertyChange("selecNiveau", 1, -1);

			}// fin méthode
		});// fin actionListener
		iconeReturn = new ImageIcon(OutilsImage.lireImageEtRedimensionner("returndark.png", 46, 46));
		btnReturn.setIcon(iconeReturn);
		btnReturn.setBackground(new Color(0, 0, 0, 0));
		btnReturn.setOpaque(false);
		btnReturn.setBorder(null);
		add(btnReturn);

		btnSauvegarder = new JButton("");
		btnSauvegarder.setBounds(40, 81, 45, 45);
		iconeSauvegarder = new ImageIcon(OutilsImage.lireImageEtRedimensionner("sauvegarde_icone.png", 46, 46));
		btnSauvegarder.setIcon(iconeSauvegarder);
		btnSauvegarder.setBackground(new Color(0, 0, 0, 0));
		btnSauvegarder.setOpaque(false);
		btnSauvegarder.setBorder(null);
		add(btnSauvegarder);

		btnInformations = new JButton("");
		btnInformations.setBounds(40, 137, 45, 45);
		btnInformations.setBorder(null);
		iconeInformations = new ImageIcon(OutilsImage.lireImageEtRedimensionner("information.png", 46, 46));
		btnInformations.setIcon(iconeInformations);
		btnInformations.setOpaque(false);
		btnInformations.setBackground(new Color(0, 0, 0, 0));
		add(btnInformations);

		btnAide = new JButton("");
		btnAide.setBounds(40, 503, 45, 45);
		btnAide.setBorder(null);
		iconeAide = new ImageIcon(OutilsImage.lireImageEtRedimensionner("aide.png", 46, 46));
		btnAide.setIcon(iconeAide);
		btnAide.setBackground(new Color(0, 0, 0, 0));
		btnAide.setOpaque(false);
		add(btnAide);

		btnReinitialiser = new JButton("");
		btnReinitialiser.setBounds(95, 503, 45, 45);
		btnReinitialiser.setBorder(null);
		iconeReinitialiser = new ImageIcon(OutilsImage.lireImageEtRedimensionner("reinitialiser.png", 46, 46));
		btnReinitialiser.setIcon(iconeReinitialiser);
		btnReinitialiser.setBackground(new Color(0, 0, 0, 0));
		btnReinitialiser.setOpaque(false);
		add(btnReinitialiser);

		btnExProf = new JButton("");

		btnExProf.setBounds(150, 503, 45, 45);
		iconeExProf = new ImageIcon(OutilsImage.lireImageEtRedimensionner("professeur.png", 60, 60));
		btnExProf.setIcon(iconeExProf);
		btnExProf.setBackground(new Color(0, 0, 0, 0));
		btnExProf.setOpaque(false);
		btnExProf.setBorder(null);
		add(btnExProf);

		btnProchImg = new JButton("");

		btnProchImg.setBounds(205, 503, 45, 45);
		btnProchImg.setBorder(null);
		iconeProchImg = new ImageIcon(OutilsImage.lireImageEtRedimensionner("proch_img.png", 46, 46));
		btnProchImg.setIcon(iconeProchImg);
		btnProchImg.setBackground(new Color(0, 0, 0, 0));
		btnProchImg.setOpaque(false);
		add(btnProchImg);

	}// fin constructeur
}// fin classe
