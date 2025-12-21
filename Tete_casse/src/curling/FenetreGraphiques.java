package curling;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Fenêtre des graphiques
 * 
 * @author Liangchen Liu
 */
public class FenetreGraphiques extends JFrame {

	private JPanel contentPane;

	private Graphiques graphiqueVitX;
	private Graphiques graphiquesAccX;
	private Graphiques graphiqueVitY;
	private Graphiques graphiquesAccY;

	/**
	 * Création de la fenêtre.
	 */
	public FenetreGraphiques() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1000, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		graphiqueVitX = new Graphiques();
		graphiqueVitX.setBounds(10, 50, 464, 300);
		graphiqueVitX.setGraphiqueNum(1);
		contentPane.add(graphiqueVitX);

		graphiquesAccX = new Graphiques();
		graphiquesAccX.setBounds(10, 400, 464, 300);
		graphiquesAccX.setGraphiqueNum(2);
		contentPane.add(graphiquesAccX);

		graphiqueVitY = new Graphiques();
		graphiqueVitY.setBounds(510, 50, 464, 300);
		graphiqueVitY.setGraphiqueNum(3);
		contentPane.add(graphiqueVitY);

		graphiquesAccY = new Graphiques();
		graphiquesAccY.setBounds(510, 400, 464, 300);
		graphiquesAccY.setGraphiqueNum(4);
		contentPane.add(graphiquesAccY);

		JLabel lblVitX = new JLabel("<html><font size = '4'><center>" + "Vitesse X (m/s) selon le temps (s)"
		+ "</center></font></html>");
		lblVitX.setHorizontalAlignment(SwingConstants.CENTER);
		lblVitX.setBounds(10, 11, 464, 28);
		contentPane.add(lblVitX);

		JLabel lblVitY = new JLabel("<html><font size = '4'><center>" + "Vitesse Y (m/s) selon le temps (s)"
		+ "</center></font></html>");
		lblVitY.setHorizontalAlignment(SwingConstants.CENTER);
		lblVitY.setBounds(510, 11, 464, 28);
		contentPane.add(lblVitY);

		JLabel lblAccX = new JLabel("<html><font size = '4'><center>" + "Accélération X (m/s²) selon le temps (s)"
				+ "</center></font></html>");
		lblAccX.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccX.setBounds(10, 361, 464, 28);
		contentPane.add(lblAccX);

		JLabel lblAccY = new JLabel("<html><font size = '4'><center>" + "Accélération Y (m/s²) selon le temps (s)"
				+ "</center></font></html>");
		lblAccY.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccY.setBounds(510, 361, 464, 28);
		contentPane.add(lblAccY);
	}

	/**
	 * Mise à jour des données des graphiques
	 * 
	 * @param temps le temps en secondes
	 * @param vitesseX la vitesse en X en m/s
	 * @param vitesseY la vitesse en Y en m/s
	 * @param accelerationX l'accélération en X en m/s²
	 * @param accelerationY l'accélération en Y en m/s²
	 */
	public void setDonnes(double temps, double vitesseX, double vitesseY, double accelerationX, double accelerationY) {
		graphiqueVitX.setPosY(vitesseX);
		graphiqueVitX.setTemps(temps);

		graphiquesAccX.setPosY(accelerationX);
		graphiquesAccX.setTemps(temps);

		graphiqueVitY.setPosY(vitesseY);
		graphiqueVitY.setTemps(temps);

		graphiquesAccY.setPosY(accelerationY);
		graphiquesAccY.setTemps(temps);

		graphiqueVitX.repaint();
		graphiquesAccX.repaint();
		graphiqueVitY.repaint();
		graphiquesAccY.repaint();

	}

	/**
	 * Réinitialisation des graphiques
	 */
	public void reini(){
		graphiqueVitX.reini();
		graphiquesAccX.reini();
		graphiqueVitY.reini();
		graphiquesAccY.reini();

		graphiqueVitX.repaint();
		graphiquesAccX.repaint();
		graphiqueVitY.repaint();
		graphiquesAccY.repaint();

	}
}
