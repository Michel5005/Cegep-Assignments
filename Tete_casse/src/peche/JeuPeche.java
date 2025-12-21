package peche;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Classe qui contient le jeu de pêche
 * 
 * @author Michel Vuu
 *
 */
public class JeuPeche extends JFrame {
	/** Le panneau qui contient le jeu de pêche */
	private JPanel contentPane;
	/** La zone d'animation du jeu de pêche */
	private Peche peche;

	/**
	 * Lance l'application.
	 * 
	 * @param args L'argument
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JeuPeche frame = new JeuPeche();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}// fin méthode

	/**
	 * Création du cadre.
	 */
	public JeuPeche() {
		peche = new Peche();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(peche);
		contentPane.setLayout(null);

	}// fin constructeur
}// fin classe
