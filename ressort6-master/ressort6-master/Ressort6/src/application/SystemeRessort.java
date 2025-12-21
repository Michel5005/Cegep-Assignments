package application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import composantdessin.ZoneAnimationPhysique;
import enumeration.TypeBloc;
import geometrie.Vecteur2D;
import images.OutilsImage;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * Cette classe représente une fenêtre de l'application qui affiche l'animation
 * d'un ressort et d'un bloc et qui permet à l'utilisateur de changer certaines
 * de ses caractéristiques, telles que la masse, l'étirement, le pas de
 * simulation, et le matériau. La classe contient également des éléments tels
 * que des boutons pour arrêter, réinitialiser et avancer d'une image dans
 * l'animation, ainsi que des étiquettes qui affichent les différentes forces et
 * les valeurs de l'accélération, de la vitesse et de la position.
 * 
 * @author Michel Vuu
 * @author Natael Lavoie
 *
 */
public class SystemeRessort extends JFrame {
	/** représente le contenu de la fenêtre */
	private JPanel contentPane;
	/** permet de demarrerl'animation */
	private JButton btnDmarrer;
	/** représente la version de la classe */
	private static final long serialVersionUID = 1L;
	/** représente la fenêtre de l'application */
	private static SystemeRessort frame;
	/** représente l'animation du ressort */
	private ZoneAnimationPhysique animRessort;
	/** permet d'arrêter l'animation */
	private JButton btnArrter;
	/** permet de réinitialiser l'animation */
	private JButton btnReinit;
	/** permet d'avancer d'une image dans l'animation */
	private JButton btnProchaineImage;
	/**
	 * modèle la valeur de la masse,la valeur de l'étirement et la valeur du pas de
	 * simulation
	 */
	private SpinnerNumberModel modelMasse, modelEtirement, modelPasSim;
	/**
	 * permet de régler la valeur de l'étirement,la valeur de la constante du
	 * ressort,la valeur de la masse et la valeur du pas de simulation
	 */
	private JSpinner spnEtirement, spnConstante, spnMasse, spnPasSim;
	/** contient les différents matériaux disponibles pour le ressort */
	private JList listMateriau;
	/** affiche le temps total de l'animation */
	private JLabel lblTempsTot;
	/** affiche la valeur de la force de gravité sur l'axe X */
	private JLabel lblForceGravX;
	/** affiche la valeur de la force de gravité sur l'axe Y */
	private JLabel lblForceGravY;
	/** affiche la valeur de la force normale sur l'axe X */
	private JLabel lblForceNormX;
	/** affiche la valeur de la force normale sur l'axe Y */
	private JLabel lblForceNormY;
	/** affiche la valeur de la force de rappel sur l'axe X */
	private JLabel lblForceRapX;
	/** affiche la valeur de la force de rappel sur l'axe Y */
	private JLabel lblForceRapY;
	/** qui affiche la valeur de la force de frottement sur l'axe X */
	private JLabel lblForceFrotteX;
	/** affiche la valeur de la force de frottement sur l'axe Y */
	private JLabel lblForceFrotteY;
	/** affiche la valeur de la somme des forces sur l'axe X */
	private JLabel lblSommeForcesX;
	/** affiche la valeur de la somme des forces sur l'axe Y */
	private JLabel lblSommeForcesY;
	/** affiche la valeur de l'accélération sur l'axe X */
	private JLabel lblValAccelX;
	/** affiche la valeur de l'accélération sur l'axe Y */
	private JLabel lblValAccelY;
	/** affiche la valeur de la vitesse sur l'axe X */
	private JLabel lblValVitX;
	/** affiche la valeur de la vitesse sur l'axe Y */
	private JLabel lblValVitY;
	/** affiche la valeur de la position sur l'axe X */
	private JLabel lblValPosX;
	/** affiche la valeur de la position sur l'axe Y */
	private JLabel lblValPosY;
	/**
	 * booléen qui indique si le changement de valeur est effectué par l'animation
	 * ou par l'utilisateur
	 */
	private boolean bChangeFromAnimation = false;

	/**
	 * Lance l'application.
	 */
	// Michel Vuu
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SystemeRessort frame = new SystemeRessort();
					frame.setVisible(true);
					frame.animRessort.requestFocusInWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Création de l'interface.
	 */
	// Michel Vuu
	@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
	public SystemeRessort() {
		setTitle("TP2 - Lapointe Nokto, Lavoie Natael et Vuu Michel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnDmarrer = new JButton(" ");
		btnDmarrer.setFont(new Font("Arial", Font.BOLD, 11));
		btnDmarrer.setBackground(new Color(176, 224, 230));
		btnDmarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// debut
				if ((double) spnEtirement.getValue() != 0.0) {
					btnProchaineImage.setEnabled(false);
					btnDmarrer.setEnabled(false);
					animRessort.demarrer();
					animRessort.requestFocusInWindow();
				}

				// fin
			}
		});
		btnDmarrer.setBounds(340, 433, 135, 130);
		contentPane.add(btnDmarrer);
		OutilsImage.lireImageEtPlacerSurBouton("1Animer.jpg", btnDmarrer);

		btnArrter = new JButton(" ");
		btnArrter.setFont(new Font("Arial", Font.BOLD, 11));
		btnArrter.setBackground(new Color(176, 224, 230));
		btnArrter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				animRessort.arreter();
				btnProchaineImage.setEnabled(true);
				btnDmarrer.setEnabled(true);
				animRessort.requestFocusInWindow();
				// fin
			}
		});
		btnArrter.setBounds(667, 433, 135, 130);
		contentPane.add(btnArrter);
		OutilsImage.lireImageEtPlacerSurBouton("2Arreter.jpg", btnArrter);

		btnReinit = new JButton(" ");
		btnReinit.setBackground(new Color(176, 224, 230));
		btnReinit.setFont(new Font("Arial", Font.BOLD, 11));
		btnReinit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				animRessort.reinitialiser();
				spnEtirement.setValue(animRessort.getEtirementRessortDefaut());
				spnConstante.setValue(animRessort.getConstanteRessortDefaut());
				spnMasse.setValue(animRessort.getMassBlocDefaut());
				spnPasSim.setValue((int) (animRessort.defaultPasDeSimulation() * 1000));
				listMateriau.setSelectedIndex(0);
				animRessort.requestFocusInWindow();
				// fin
			}
		});
		btnReinit.setBounds(340, 585, 135, 128);
		contentPane.add(btnReinit);
		OutilsImage.lireImageEtPlacerSurBouton("4Recommencer.jpg", btnReinit);

		btnProchaineImage = new JButton(" ");
		btnProchaineImage.setFont(new Font("Arial", Font.BOLD, 11));
		btnProchaineImage.setBackground(new Color(176, 224, 230));
		btnProchaineImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				animRessort.prochaineImage();
				animRessort.requestFocusInWindow();
				// fin
			}
		});
		btnProchaineImage.setBounds(507, 433, 135, 130);
		contentPane.add(btnProchaineImage);
		OutilsImage.lireImageEtPlacerSurBouton("3ProchaineImage.jpg", btnProchaineImage);

		animRessort = new ZoneAnimationPhysique();
		animRessort.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				catchFires(evt);
			}
		});
		animRessort.setBounds(38, 11, 1400, 400);
		contentPane.add(animRessort);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Valeurs modifiables", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 433, 303, 280);
		contentPane.add(panel);
		panel.setLayout(null);

		spnConstante = new JSpinner();
		spnConstante.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				animRessort.requestFocusInWindow();
				animRessort.setK((double) spnConstante.getValue());
			}
		});
		spnConstante.setModel(new SpinnerNumberModel(animRessort.getConstanteRessortDefaut(),
				animRessort.getConstanteRessortMin(), animRessort.getConstanteRessortMax(), 10));
		spnConstante.setBounds(166, 14, 52, 20);
		panel.add(spnConstante);

		spnEtirement = new JSpinner();
		spnEtirement.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!bChangeFromAnimation) {
					animRessort.changerEtirementsRessort((double) spnEtirement.getValue());
				}
				animRessort.requestFocusInWindow();
				bChangeFromAnimation = false;
			}
		});
		spnEtirement.setBounds(179, 45, 52, 20);
		panel.add(spnEtirement);
		modelEtirement = new SpinnerNumberModel(animRessort.getEtirementRessortDefaut(),
				animRessort.getEtirementRessortDefaut(), animRessort.getEtirementRessortMaxAbs(), 0.01);
		spnEtirement.setModel(modelEtirement);

		spnMasse = new JSpinner();
		spnMasse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				animRessort.requestFocusInWindow();
				animRessort.setMasse((double) spnMasse.getValue());
			}
		});
		spnMasse.setBounds(166, 76, 52, 20);
		panel.add(spnMasse);
		modelMasse = new SpinnerNumberModel(animRessort.getMassBlocDefaut(), animRessort.getMassBlocMin(),
				animRessort.getMassBlocMax(), 0.1);
		spnMasse.setModel(modelMasse);

		spnPasSim = new JSpinner();
		spnPasSim.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				animRessort.requestFocusInWindow();
				animRessort.setDeltaT((double) (Integer) spnPasSim.getValue() / 1000.0);
			}
		});
		spnPasSim.setBounds(179, 107, 52, 20);
		modelPasSim = new SpinnerNumberModel(8, 3, 10, 1);
		spnPasSim.setModel(modelPasSim);
		panel.add(spnPasSim);

		JLabel lblConstantRessort = new JLabel("K (Constante de ressort) : ");
		lblConstantRessort.setBounds(10, 17, 159, 14);
		panel.add(lblConstantRessort);

		JLabel lblRessortEtire = new JLabel("Étirement du ressort (en m) : ");
		lblRessortEtire.setBounds(10, 48, 168, 14);
		panel.add(lblRessortEtire);

		JLabel lblMasseBloc = new JLabel("Masse du bloc (en kg) : ");
		lblMasseBloc.setBounds(10, 79, 146, 14);
		panel.add(lblMasseBloc);

		JLabel lblPasSimu = new JLabel("Pas de simulation (en ms) : ");
		lblPasSimu.setBounds(10, 110, 159, 14);
		panel.add(lblPasSimu);

		JLabel lblKilogramme = new JLabel("kg");
		lblKilogramme.setBounds(226, 79, 46, 14);
		panel.add(lblKilogramme);

		JLabel lblMetre = new JLabel("m");
		lblMetre.setBounds(241, 48, 25, 14);
		panel.add(lblMetre);

		JLabel lblMilliSec = new JLabel("ms");
		lblMilliSec.setBounds(236, 110, 46, 14);
		panel.add(lblMilliSec);

		listMateriau = new JList();

		listMateriau.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				switch (listMateriau.getSelectedIndex()) {
				case (0):
					animRessort.setTypeDeBloc(TypeBloc.NICKEL_ACIER);
					break;
				case (1):
					animRessort.setTypeDeBloc(TypeBloc.BRONZE_FER);
					break;
				case (2):
					animRessort.setTypeDeBloc(TypeBloc.ACIER_PLOMB);
					break;
				}
				animRessort.requestFocusInWindow();
			}
		});

		listMateriau.setModel(new AbstractListModel() {
			String[] values = new String[] { "0.64 (nickel-acier)", "0.22 (bronze-fer) ", "0.95 (acier-plomb)" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listMateriau.setSelectedIndex(0);
		listMateriau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMateriau.setBounds(179, 135, 114, 57);
		panel.add(listMateriau);

		JLabel lblCoeffCinetique = new JLabel("Coefficient de frottement µc : ");
		lblCoeffCinetique.setBounds(10, 135, 168, 14);
		panel.add(lblCoeffCinetique);

		JButton btnValeurTest = new JButton(" ");
		btnValeurTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animRessort.toValeursTests();
				spnConstante.setValue(animRessort.getConstanteRessortDefaut());
				spnMasse.setValue(animRessort.getMassBlocDefaut());
				spnPasSim.setValue((int) (animRessort.defaultPasDeSimulation() * 1000));
				listMateriau.setSelectedIndex(0);
				animRessort.requestFocusInWindow();
			}
		});
		btnValeurTest.setFont(new Font("Arial", Font.BOLD, 11));
		btnValeurTest.setBackground(new Color(176, 224, 230));
		btnValeurTest.setBounds(507, 583, 135, 130);
		contentPane.add(btnValeurTest);
		OutilsImage.lireImageEtPlacerSurBouton("5ValeursDeTest.jpg", btnValeurTest);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Valeurs ",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(812, 422, 662, 333);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblTemps = new JLabel("Temps total simulé: ");
		lblTemps.setBounds(6, 16, 159, 26);
		panel_1.add(lblTemps);

		lblTempsTot = new JLabel("0");
		lblTempsTot.setBounds(175, 16, 97, 26);
		panel_1.add(lblTempsTot);

		JLabel lblSeconde = new JLabel("s");
		lblSeconde.setBounds(333, 16, 35, 26);
		panel_1.add(lblSeconde);

		JLabel lblForceGraviteX = new JLabel("Force gravitationnelle en x: ");
		lblForceGraviteX.setBounds(6, 42, 159, 31);
		panel_1.add(lblForceGraviteX);

		JLabel lblForceGraviteY = new JLabel("Force gravitationnelle en y: ");
		lblForceGraviteY.setBounds(6, 70, 159, 31);
		panel_1.add(lblForceGraviteY);

		lblForceGravX = new JLabel("0");
		lblForceGravX.setBounds(175, 44, 97, 26);
		panel_1.add(lblForceGravX);

		lblForceGravY = new JLabel("0");
		lblForceGravY.setBounds(175, 72, 97, 26);
		panel_1.add(lblForceGravY);

		JLabel lblNewtonX = new JLabel("N");
		lblNewtonX.setBounds(333, 44, 35, 26);
		panel_1.add(lblNewtonX);

		JLabel lblNewtonY = new JLabel("N");
		lblNewtonY.setBounds(333, 72, 35, 26);
		panel_1.add(lblNewtonY);

		JLabel lblForceNormalX = new JLabel("Force normale en x: ");
		lblForceNormalX.setBounds(6, 101, 159, 31);
		panel_1.add(lblForceNormalX);

		JLabel lblForceNormalY = new JLabel("Force normale en y: ");
		lblForceNormalY.setBounds(6, 132, 159, 31);
		panel_1.add(lblForceNormalY);

		lblForceNormX = new JLabel("0");
		lblForceNormX.setBounds(175, 103, 97, 26);
		panel_1.add(lblForceNormX);

		lblForceNormY = new JLabel("0");
		lblForceNormY.setBounds(175, 134, 97, 26);
		panel_1.add(lblForceNormY);

		JLabel lblNewtonForceNormX = new JLabel("N");
		lblNewtonForceNormX.setBounds(333, 103, 35, 26);
		panel_1.add(lblNewtonForceNormX);

		JLabel lblNewtonForceNormY = new JLabel("N");
		lblNewtonForceNormY.setBounds(333, 132, 35, 26);
		panel_1.add(lblNewtonForceNormY);

		JLabel lblForceRappelX = new JLabel("Force de rappel en x: ");
		lblForceRappelX.setBounds(6, 159, 159, 31);
		panel_1.add(lblForceRappelX);

		JLabel lblForceRappelY = new JLabel("Force de rappel en y: ");
		lblForceRappelY.setBounds(6, 187, 159, 30);
		panel_1.add(lblForceRappelY);

		lblForceRapX = new JLabel("0");
		lblForceRapX.setBounds(175, 159, 97, 26);
		panel_1.add(lblForceRapX);

		lblForceRapY = new JLabel("0");
		lblForceRapY.setBounds(175, 189, 97, 26);
		panel_1.add(lblForceRapY);

		JLabel lblNewtonForceRapX = new JLabel("N");
		lblNewtonForceRapX.setBounds(333, 159, 97, 26);
		panel_1.add(lblNewtonForceRapX);

		JLabel lblNewtonForceRapY = new JLabel("N");
		lblNewtonForceRapY.setBounds(333, 187, 97, 26);
		panel_1.add(lblNewtonForceRapY);

		JLabel lblForceFrottementX = new JLabel("Force de frottement en x: ");
		lblForceFrottementX.setBounds(6, 219, 159, 26);
		panel_1.add(lblForceFrottementX);

		JLabel lblForceFrottementY = new JLabel("Force de frottement en y: ");
		lblForceFrottementY.setBounds(6, 244, 159, 26);
		panel_1.add(lblForceFrottementY);

		lblForceFrotteX = new JLabel("0");
		lblForceFrotteX.setBounds(175, 219, 97, 26);
		panel_1.add(lblForceFrotteX);

		lblForceFrotteY = new JLabel("0");
		lblForceFrotteY.setBounds(175, 244, 97, 26);
		panel_1.add(lblForceFrotteY);

		JLabel lblNewtonForceFrotteX = new JLabel("N");
		lblNewtonForceFrotteX.setBounds(333, 219, 97, 26);
		panel_1.add(lblNewtonForceFrotteX);

		JLabel lblNewtonForceFrotteY = new JLabel("N");
		lblNewtonForceFrotteY.setBounds(333, 250, 97, 26);
		panel_1.add(lblNewtonForceFrotteY);

		JLabel lblSommeDesForcesX = new JLabel("Somme des forces en x: ");
		lblSommeDesForcesX.setBounds(6, 269, 159, 32);
		panel_1.add(lblSommeDesForcesX);

		JLabel lblSommeDesForcesY = new JLabel("Somme des forces en y: ");
		lblSommeDesForcesY.setBounds(6, 302, 159, 26);
		panel_1.add(lblSommeDesForcesY);

		lblSommeForcesX = new JLabel("0");
		lblSommeForcesX.setBounds(175, 272, 97, 26);
		panel_1.add(lblSommeForcesX);

		lblSommeForcesY = new JLabel("0");
		lblSommeForcesY.setBounds(175, 302, 97, 26);
		panel_1.add(lblSommeForcesY);

		JLabel lblNewtonSommeForcesX = new JLabel("N");
		lblNewtonSommeForcesX.setBounds(333, 275, 97, 26);
		panel_1.add(lblNewtonSommeForcesX);

		JLabel lblNewtonSommeForcesY = new JLabel("N");
		lblNewtonSommeForcesY.setBounds(333, 302, 97, 26);
		panel_1.add(lblNewtonSommeForcesY);

		JLabel lblAccelerationX = new JLabel("Accélération en x:");
		lblAccelerationX.setBounds(374, 19, 123, 23);
		panel_1.add(lblAccelerationX);

		lblValAccelX = new JLabel("0");
		lblValAccelX.setBounds(507, 19, 67, 20);
		panel_1.add(lblValAccelX);

		JLabel lblUniteAccelX = new JLabel("m/s²");
		lblUniteAccelX.setBounds(584, 19, 68, 20);
		panel_1.add(lblUniteAccelX);

		JLabel lblAccelerationY = new JLabel("Accélération en x:");
		lblAccelerationY.setBounds(374, 50, 123, 23);
		panel_1.add(lblAccelerationY);

		lblValAccelY = new JLabel("0");
		lblValAccelY.setBounds(507, 50, 67, 20);
		panel_1.add(lblValAccelY);

		JLabel lblUniteAccelY = new JLabel("m/s²");
		lblUniteAccelY.setBounds(584, 50, 68, 20);
		panel_1.add(lblUniteAccelY);

		JLabel lblVitesseX = new JLabel("Vitesse en x: ");
		lblVitesseX.setBounds(374, 78, 123, 20);
		panel_1.add(lblVitesseX);

		JLabel lblVitesseY = new JLabel("Vitesse en y: ");
		lblVitesseY.setBounds(374, 109, 123, 20);
		panel_1.add(lblVitesseY);

		lblValVitX = new JLabel("0");
		lblValVitX.setBounds(507, 78, 67, 20);
		panel_1.add(lblValVitX);

		lblValVitY = new JLabel("0");
		lblValVitY.setBounds(507, 109, 67, 20);
		panel_1.add(lblValVitY);

		JLabel lblUniteVitX = new JLabel("m/s");
		lblUniteVitX.setBounds(584, 78, 68, 20);
		panel_1.add(lblUniteVitX);

		JLabel lblUniteVitY = new JLabel("m/s");
		lblUniteVitY.setBounds(584, 109, 68, 20);
		panel_1.add(lblUniteVitY);

		JLabel lblPositionX = new JLabel("Position en x: ");
		lblPositionX.setBounds(374, 140, 123, 20);
		panel_1.add(lblPositionX);

		JLabel lblPositionY = new JLabel("Position en y: ");
		lblPositionY.setBounds(374, 169, 123, 20);
		panel_1.add(lblPositionY);

		lblValPosX = new JLabel("0");
		lblValPosX.setBounds(507, 140, 67, 20);
		panel_1.add(lblValPosX);

		lblValPosY = new JLabel("0");
		lblValPosY.setBounds(507, 167, 67, 20);
		panel_1.add(lblValPosY);

		JLabel lblUnitePosX = new JLabel("m");
		lblUnitePosX.setBounds(584, 140, 68, 20);
		panel_1.add(lblUnitePosX);

		JLabel lblUnitePosY = new JLabel("m");
		lblUnitePosY.setBounds(584, 167, 68, 20);
		panel_1.add(lblUnitePosY);
	}

	/**
	 * Catch les event fire de la zone d'animation et fait les remplacements
	 * necessaire
	 * 
	 * @param evt levent fire
	 */
	// Natael Lavoie
	private void catchFires(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case ("etirements"):
			double etirement = (double) evt.getNewValue();
			spnEtirement.setValue(etirement);
			bChangeFromAnimation = true;
			break;
		case ("proprietes"):
			HashMap<String, Vecteur2D> proprietes = (HashMap<String, Vecteur2D>) evt.getNewValue();
			Vecteur2D grav = proprietes.get("grav");
			Vecteur2D norm = proprietes.get("norm");
			Vecteur2D fRessort = proprietes.get("fRessort");
			Vecteur2D fFrottement = proprietes.get("fFrottement");
			Vecteur2D fTotale = proprietes.get("fTotale");
			Vecteur2D acc = proprietes.get("acc");
			Vecteur2D vit = proprietes.get("vit");
			Vecteur2D posSelonLaLigne = proprietes.get("posSelonLaLigne");
			lblForceGravX.setText(String.format("%.3f", grav.getX()));
			lblForceGravY.setText(String.format("%.3f", grav.getY()));
			lblForceNormX.setText(String.format("%.3f", norm.getX()));
			lblForceNormY.setText(String.format("%.3f", norm.getY()));
			lblForceRapX.setText(String.format("%.3f", fRessort.getX()));
			lblForceRapY.setText(String.format("%.3f", fRessort.getY()));
			lblForceFrotteX.setText(String.format("%.3f", fFrottement.getX()));
			lblForceFrotteY.setText(String.format("%.3f", fFrottement.getY()));
			lblSommeForcesX.setText(String.format("%.3f", fTotale.getX()));
			lblSommeForcesY.setText(String.format("%.3f", fTotale.getY()));
			lblValAccelX.setText(String.format("%.3f", acc.getX()));
			lblValAccelY.setText(String.format("%.3f", acc.getY()));
			lblValVitX.setText(String.format("%.3f", vit.getX()));
			lblValVitY.setText(String.format("%.3f", vit.getY()));
			lblValPosX.setText(String.format("%.3f", posSelonLaLigne.getX()));
			lblValPosY.setText(String.format("%.3f", posSelonLaLigne.getY()));

			break;
		case "tempsTotalEcoule":
			lblTempsTot.setText(String.format("%.3f", (double) evt.getNewValue()));
			break;
		case "arret":
			btnDmarrer.setEnabled(true);
			btnProchaineImage.setEnabled(true);
			break;

		}

	}
}
