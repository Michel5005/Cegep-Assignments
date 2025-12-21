package parametres;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GestionDesFichiers.GestionnaireDesDonnes;

/**
 * Classe qui affiche et effectue les méthodes liées au volume des différents
 * sons et le mode triche
 * 
 * @author Jason Yin
 * @author Liangchen Liu
 * @author Caroline Houle
 *
 */

public class Parametres extends JPanel {

	/** Les instructions lié à la touche "p" du clavier */
	private String instructions = "- Cliquer sur les 3 boutons de musique pour jouer 3 clip audio différent en simultanée (possède des bugs) \n"
			+ "- Les trois sons fonctionnent lorsque l'utilisateur n'est pas dans le menu paramètres";

	private static final long serialVersionUID = 1L;
	private JSlider sldMusique;
	private JCheckBox chckbxModeTriche;
	private JLabel lblNiveauMusique;
	private JLabel lblTitreMusique;
	private JButton btnRetour;
	private Clip leClipMusique = null;

	private final String MUSIQUE_FICHIER = "musique.wav";
	private AudioInputStream audioStrMus;
	private String pathDeFichierMusique = null;
	private File objetFichier = null;
	private double volumeEntre0Et1 = 1.0;
	private JButton btnContinuerrMusique;
	private JButton btnArreterMusique;

	/**
	 * Crée le panneau des paramètres
	 */
	// Jason Yin
	public Parametres() {
		instProf();

		setBounds(0, 0, 1300, 700);
		setLayout(null);

		sldMusique = new JSlider();
		sldMusique.setValue(100);
		sldMusique.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblNiveauMusique.setText(sldMusique.getValue() + "");
				volumeSldMusique();

			}
		});
		sldMusique.setBounds(163, 98, 900, 60);
		add(sldMusique);

		chckbxModeTriche = new JCheckBox("Activer le mode triche (professeur)");
		chckbxModeTriche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mettreEtoilesTriche();

			}
		});
		chckbxModeTriche.setFont(new Font("Tahoma", Font.PLAIN, 20));
		chckbxModeTriche.setBounds(450, 550, 340, 40);
		add(chckbxModeTriche);

		lblNiveauMusique = new JLabel(sldMusique.getValue() + "");
		lblNiveauMusique.setBounds(1073, 118, 56, 16);
		add(lblNiveauMusique);

		lblTitreMusique = new JLabel("Volume de la musique");
		lblTitreMusique.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitreMusique.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitreMusique.setBounds(488, 74, 266, 33);
		add(lblTitreMusique);

		btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firePropertyChange("menuAccueil", 1, -1);
			}
		});
		btnRetour.setBounds(35, 37, 85, 21);
		add(btnRetour);

		btnContinuerrMusique = new JButton("Démarrer musique");
		btnContinuerrMusique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continuerMusique();

			}
		});
		btnContinuerrMusique.setBounds(450, 399, 340, 60);
		add(btnContinuerrMusique);

		btnArreterMusique = new JButton("Arrêter musique");
		btnArreterMusique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arreterMusique();

			}
		});
		btnArreterMusique.setBounds(450, 483, 340, 60);
		add(btnArreterMusique);
		this.setVisible(false);

	}// fin constructeur

	/**
	 * Permet de commencer la musique
	 */
	// Liangchen Liu
	public void demarrerMusique() {
		chargerMusique();
		if (leClipMusique != null) {
			leClipMusique.start();
			leClipMusique.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	/**
	 * Permet de continuer la musique
	 */
	// Liangchen Liu
	private void continuerMusique() {
		if (leClipMusique != null) {
			leClipMusique.loop(Clip.LOOP_CONTINUOUSLY);
			leClipMusique.start();
		}
	}

	/**
	 * Permet d'arrêter la musique
	 */
	// Liangchen Liu
	private void arreterMusique() {
		if (leClipMusique != null) {
			leClipMusique.stop();

		}

	}

	/**
	 * Modifie le volume de la musique
	 */
	// Liangchen Liu
	private void volumeSldMusique() {
		float valeurEntre0Et1 = (float) sldMusique.getValue() / 100;
		modifierVolume(valeurEntre0Et1, leClipMusique);

	}

	/**
	 * Pour la gestion du volume si d�sire
	 * 
	 * @param valeurEntre0Et1 valeur du volume, 1=volume original du son 0=aucun
	 *                        volume
	 * @param leClip          le clip audio
	 */
	// Caroline Houle
	private void modifierVolume(double valeurEntre0Et1, Clip leClip) {
		this.volumeEntre0Et1 = valeurEntre0Et1;
		if (leClip != null && leClip.isRunning()) {
			FloatControl volume = (FloatControl) leClip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(20f * (float) Math.log10((float) valeurEntre0Et1));

		}
	}

	/**
	 * Méthode pour faire apparaitre les instructions pour le professeur
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
	 * Méthode qui charge la musique(à complété)
	 */
	// Caroline Houle
	private void chargerMusique() {
		try {

			// si ce n'est pas la premiere fois, on evite de reacceder au fichier sur disque
			// (consomme du temps)
			if (audioStrMus == null) {
				URL pathDeFichierMusique = getClass().getClassLoader().getResource(MUSIQUE_FICHIER);
				if (pathDeFichierMusique == null) {
					JOptionPane.showMessageDialog(null,	"Fichier de son introuvable: " + MUSIQUE_FICHIER);
				} else {
					try {
						audioStrMus = AudioSystem.getAudioInputStream(pathDeFichierMusique);
					} 
					catch (IOException e) {
						System.out.println("Erreur de lecture du fichier de son: " + MUSIQUE_FICHIER);
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Incapable d'ouvrir le fichier de son ");
			e.printStackTrace();
			return;
		} // fin try/catch
		try {
			leClipMusique = AudioSystem.getClip();
			leClipMusique.open(audioStrMus);

			// ces 2 lignes sont necessaires seulement si on souhaite gerer le volume
			FloatControl volume = (FloatControl) leClipMusique.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(20f * (float) Math.log10((float) volumeEntre0Et1));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Probl�me � la cr�ation du clip (son)! " + MUSIQUE_FICHIER);
			e.printStackTrace();
			return;
		} // fin try/catch
	}// fin methode

	/**
	 * Méthode qui donne tous les étoiles
	 */
	// Liangchen Liu
	private void mettreEtoilesTriche() {
		if (chckbxModeTriche.isSelected()) {
			GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesCircuit(3);
			GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesCurling(3);
			GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesPeche(3);
			GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesTank(3);
			GestionnaireDesDonnes.getInstance().getDonnesCurling().setModeTriche(true);
			GestionnaireDesDonnes.getInstance().getDonnesTank().setModeTriche(true);
			GestionnaireDesDonnes.getInstance().getDonnesCircuit().setModeTriche(true);
			GestionnaireDesDonnes.getInstance().getDonnesPeche().setModeTriche(true);

		} else {
			GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesCircuit(0);
			GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesCurling(0);
			GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesPeche(0);
			GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesTank(0);
			GestionnaireDesDonnes.getInstance().getDonnesCurling().setModeTriche(false);
			GestionnaireDesDonnes.getInstance().getDonnesTank().setModeTriche(false);
			GestionnaireDesDonnes.getInstance().getDonnesCircuit().setModeTriche(false);
			GestionnaireDesDonnes.getInstance().getDonnesPeche().setModeTriche(false);

		}

	}
}// fin classe
