package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import outils.OutilsImage;

/**
 * Classe pour le panel des crédits
 * 
 * @author Liangchen Liu
 * @author Caroline Houle
 * 
 */
public class Apropos extends JPanel {
	private ImageIcon iconeReturn;
	private JButton btnReturn;
	private JTabbedPane tabOnglets;
	private JPanel pnlAuteurs;

	/**
	 * Create the panel.
	 */
	// Liangchen Liu
	public Apropos() {

		btnReturn = new JButton("");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firePropertyChange("menuAccueil", 1, -1);

			}// fin méthode

		});// fin actionListener
		iconeReturn = new ImageIcon(OutilsImage.lireImageEtRedimensionner("return.png", 46, 46));
		setLayout(null);
		btnReturn.setIcon(iconeReturn);
		btnReturn.setOpaque(false);
		btnReturn.setBorder(null);
		btnReturn.setBackground(new Color(0, 0, 0, 0));
		btnReturn.setBounds(40, 25, 45, 45);
		add(btnReturn);

		methodePanelProf();
		source();


	}

	/**
	 * Méthode qui permet de créer les onglets d'information
	 */
	// Caroline Houle
	private void methodePanelProf() {
		tabOnglets = new JTabbedPane(JTabbedPane.LEFT);
		tabOnglets.setBackground(new Color(26, 27, 38));
		tabOnglets.setForeground(new Color(59, 62, 82));
		tabOnglets.setBounds(40, 81, 1220, 658);
		add(tabOnglets);
		pnlAuteurs = new JPanel();
		pnlAuteurs.setBackground(new Color(26, 27, 38));
		pnlAuteurs.setForeground(new Color(185, 196, 230));
		tabOnglets.addTab("Auteurs", null, pnlAuteurs, null);
		pnlAuteurs.setLayout(null);

	}

	/**
	 * Méthode qui permet de créer les onglets de sources
	 */
	// Liangchen Liu
	private void source() {
		JLabel lblAuteurs = new JLabel("<html><center>" +
				"Équipe 8 " +
				"<br>" +
				"<br>Ismaïl Boukari" +
				"<br>Liangchen Liu" +
				"<br>Tin Pham" +
				"<br>Michel Vuu" +
				"<br>Jason Yin" +
				"<br><br>Cours 420-SCD" +
				"<br>Intégration des apprentissages en SIM" +
				"<br>Hiver 2023</center></html>", SwingConstants.CENTER);
		lblAuteurs.setBounds(16, 13, 1130, 627);
		lblAuteurs.setVerticalAlignment(SwingConstants.TOP);
		lblAuteurs.setForeground(new Color(185, 196, 230));
		lblAuteurs.setFont(new Font("Arial Black", Font.PLAIN, 15));

		pnlAuteurs.add(lblAuteurs);

		JPanel pnlSources = new JPanel();
		pnlSources.setBackground(new Color(26, 27, 38));
		tabOnglets.addTab("Sources", null, pnlSources, null);
		pnlSources.setLayout(null);

		JLabel lblSources = new JLabel("<html><center>" + "SOURCES IMAGES" +
				"<br>Image de la flèche pour aller à la prochaine partie: https://thenounproject.com/icon/arrow-2732431/"
				+
				"<br>Image de la flèche de retour: https://thenounproject.com/icon/back-5153388/" +
				"<br>Image de la canne à pêche: https://thenounproject.com/icon/fishing-reel-5048314/" +
				"<br>Image de la mine sous marine: https://thenounproject.com/icon/mine-37069/" +
				"<br>Image de l'étoile: https://thenounproject.com/icon/star-1043734/" +
				"<br>Image du trésor: https://thenounproject.com/icon/treasure-2410498/" +
				"<br>Image du l'icone de professeur: https://thenounproject.com/icon/teacher-1050477/" +
				"<br>Image de l'icone de sauvegarde: https://thenounproject.com/icon/save-1050704/" +
				"<br>Image de l'icone d'information: https://thenounproject.com/icon/info-5544689/" +
				"<br>Image de l'icone d'aide: https://thenounproject.com/icon/help-4871854/" +
				"<br>Image de l'icone de réinitialiser: https://thenounproject.com/icon/restart-1921262/" +
				"<br>Image de l'icone de poubelle: https://thenounproject.com/icon/trash-1371974/" +
				"<br>Image du champ électrique: https://thenounproject.com/icon/arrow-double-2881688/" +
				"<br>Image du champ magnétique vers l'intérieur de l'écran: https://thenounproject.com/icon/cancel-861785/"
				+
				"<br>Image du champ magnétique vers l'extérieur de l'écran: https://thenounproject.com/icon/circle-2486900/"
				+
				"<br>Image de la particule positive: https://thenounproject.com/icon/plus-2310784/" +
				"<br>Image de la particule négative: https://thenounproject.com/icon/minus-2310783/" +
				"<br>Image des points: https://thenounproject.com/icon/points-3187865/" +
				"<br>Musique de fond: https://www.youtube.com/watch?v=x23I8f9PwlI" +

				"<br><br>SOURCES CODE"
				+ "<br>Code pour mettre en ordre croissant la liste d'objets pour les classements: https://www.scaler.com/topics/selection-sort-java/"
				+ "<br>Code pour le théorie des graphes: https://www.researchgate.net/publication/351869800_Using_Graph_Theory_for_Automated_Electric_Network_Solving_and_Analysis"
				+ "<br>Code pour le circuit: https://diposit.ub.edu/dspace/bitstream/2445/170548/1/170548.pdf"
				+ "<br>Code pour le circuit: https://core.ac.uk/download/53745212.pdf"
				+ "<br>Code pour la théorie des graphes: https://www.programiz.com/java-programming/examples/graph-implementation "
				+
				"</center></html>", SwingConstants.CENTER);
		lblSources.setBounds(1, 5, 1161, 649);
		lblSources.setVerticalAlignment(SwingConstants.TOP);
		lblSources.setForeground(new Color(185, 196, 230));
		lblSources.setFont(new Font("Arial Black", Font.PLAIN, 15));
		pnlSources.add(lblSources);

	}

}
