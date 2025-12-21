package char_assaut;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GestionDesFichiers.GestionnaireDesDonnes;


/**
 * Classe qui contient le jeu de char d'assault
 * 
 * @author Michel Vuu
 *
 */
public class Jeu_Tank extends JFrame {
	/** Version de séralisation de la classe */
    private static final long serialVersionUID = 104L;
	private JPanel contentPane;
	private ZoneAnimationTank char_assault;

	private DonnesTank donnesTank;

	/**
	 * Lancement de l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Jeu_Tank frame = new Jeu_Tank();
					frame.setVisible(true);
					frame.char_assault.requestFocusInWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}// fin try catch
			}// fin run
		});
	}// fin méthode

	/**
	 * Création du cadre.
	 */
	public Jeu_Tank() {
		donnesTank = new DonnesTank();
		GestionnaireDesDonnes.getInstance().setDonnesTank(donnesTank);

		char_assault = new ZoneAnimationTank();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(char_assault);
		char_assault.setLayout(null);
	}// fin méthode
}// fin classe
