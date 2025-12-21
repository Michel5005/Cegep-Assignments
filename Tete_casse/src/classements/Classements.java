package classements;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import GestionDesFichiers.GestionnaireDesDonnes;
import outils.OutilsImage;

/**
 * Classe représentant les classements
 * 
 * @author Liangchen Liu
 * 
 */
public class Classements extends JPanel {

	private JButton btnReturn;
	private ImageIcon iconeReturn;

	private JLabel lblTitreClassements;
	private JScrollPane scrollPane;
	private JTable tableCurling;
	private JTable tableCircuit;
	private JTable tablePeche;
	private JTable tableTank;
	private JTable tableMeilleursTemps;
	private JLabel lblPeche;
	private JLabel lblTank;
	private JLabel lblMeilleursTemps;

	private DonnesClassements donnesClassements;
	private ArrayList<InformationEssais> circuitArrayList;
	private ArrayList<InformationEssais> curlingArrayList;
	private ArrayList<InformationEssais> pecheArrayList;
	private ArrayList<InformationEssais> tankArrayList;
	private ArrayList<InformationEssais> meilleursTempsArrayList;

	/** Les instructions lié à la touche "p" du clavier */
	private final String INSTRUCTIONS = "Les éléments ne fonctionnent pas, cela est juste pour montrer ce qui aura l'air dans le futur \n";

	/**
	 * Constructeur de la scène
	 */
	public Classements() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					JOptionPane.showMessageDialog(null, INSTRUCTIONS, "Fonctionnalités présentes",
							JOptionPane.INFORMATION_MESSAGE);
					requestFocusInWindow();
				} // fin if

			}// fin méthode

		});// fin listener
		btnReturn = new JButton("");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firePropertyChange("menuAccueil", 1, -1);
			}// fin methode
		});// fin actionListener
		btnReturn.setBounds(63, 40, 47, 47);
		iconeReturn = new ImageIcon(OutilsImage.lireImageEtRedimensionner("returndark.png", 46, 46));
		setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(175, 465, 257, -237);
		add(scrollPane);
		btnReturn.setIcon(iconeReturn);
		btnReturn.setBackground(new Color(0, 0, 0, 0));
		btnReturn.setOpaque(false);
		btnReturn.setBorder(null);
		add(btnReturn);

		lblTitreClassements = new JLabel("Classements");
		lblTitreClassements.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitreClassements.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblTitreClassements.setBounds(427, 47, 363, 56);
		add(lblTitreClassements);

		tableCircuit = new JTable();
		tableCircuit.setEnabled(false);
		tableCircuit.setFillsViewportHeight(true);
		tableCircuit.setModel(new DefaultTableModel(
				new Object[][] {
						{ "Essai(s)", "Temps (s)" },
						{ "", "" },
						{ "", "" },
						{ "", "" },
						{ "", "" },
						{ "", "" },
				},
				new String[] {
						"Essai(s)", "Temps"
				}) {
			Class[] columnTypes = new Class[] {
					String.class, String.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableCircuit.getColumnModel().getColumn(0).setPreferredWidth(74);
		tableCircuit.setBackground(Color.LIGHT_GRAY);
		tableCircuit.setBounds(175, 148, 238, 96);
		add(tableCircuit);

		tableCurling = new JTable();
		tableCurling.setEnabled(false);
		tableCurling.setBounds(825, 148, 238, 96);
		add(tableCurling);
		tableCurling.setModel(new DefaultTableModel(
				new Object[][] {
						{ "Essai(s)", "Temps (s)" },
						{ "", "" },
						{ "", "" },
						{ "", "" },
						{ "", "" },
						{ "", "" },
				},
				new String[] {
						"Essai(s)", "Temps"
				}));
		tableCurling.setForeground(Color.BLACK);
		tableCurling.setFillsViewportHeight(true);
		tableCurling.setBackground(Color.LIGHT_GRAY);

		tablePeche = new JTable();
		tablePeche.setEnabled(false);
		tablePeche.setModel(new DefaultTableModel(
				new Object[][] {
						{ "Essai(s)", "Temps (s)" },
						{ "", "" },
						{ "", "" },
						{ "", "" },
						{ null, null },
						{ null, null },
				},
				new String[] {
						"Essai(s)", "Temps"
				}) {
			Class[] columnTypes = new Class[] {
					String.class, String.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablePeche.setFillsViewportHeight(true);
		tablePeche.setBackground(Color.LIGHT_GRAY);
		tablePeche.setBounds(175, 305, 238, 96);
		add(tablePeche);

		tableTank = new JTable();
		tableTank.setEnabled(false);
		tableTank.setModel(new DefaultTableModel(
				new Object[][] {
						{ "Essai(s)", "Temps (s)" },
						{ "", "" },
						{ "", "" },
						{ null, null },
						{ null, null },
						{ null, null },
				},
				new String[] {
						"Essai(s)", "Temps"
				}) {
			Class[] columnTypes = new Class[] {
					String.class, String.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableTank.setFillsViewportHeight(true);
		tableTank.setBackground(Color.LIGHT_GRAY);
		tableTank.setBounds(825, 305, 238, 96);
		add(tableTank);

		tableMeilleursTemps = new JTable();
		tableMeilleursTemps.setEnabled(false);
		tableMeilleursTemps.setModel(new DefaultTableModel(
				new Object[][] {
						{ "Jeux", "Essai(s)", "Temps (s)" },
						{ "", "", "" },
						{ "", "", "" },
						{ "", "", "" },
						{ "", "", "" },
						{ "", "", "" },
				},
				new String[] {
						"Jeux", "Essai(s)", "Temps"
				}) {
			Class[] columnTypes = new Class[] {
					String.class, String.class, String.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableMeilleursTemps.setFillsViewportHeight(true);
		tableMeilleursTemps.setBackground(Color.LIGHT_GRAY);
		tableMeilleursTemps.setBounds(485, 519, 238, 96);
		add(tableMeilleursTemps);

		JLabel lblCircuit = new JLabel("Circuit");
		lblCircuit.setHorizontalAlignment(SwingConstants.CENTER);
		lblCircuit.setBounds(268, 125, 45, 13);
		add(lblCircuit);

		JLabel lblCurling = new JLabel("Curling");
		lblCurling.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurling.setBounds(926, 125, 45, 13);
		add(lblCurling);

		lblPeche = new JLabel("Peche");
		lblPeche.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeche.setBounds(268, 282, 45, 13);
		add(lblPeche);

		lblTank = new JLabel("Tank");
		lblTank.setHorizontalAlignment(SwingConstants.CENTER);
		lblTank.setBounds(926, 282, 45, 13);
		add(lblTank);

		lblMeilleursTemps = new JLabel("Meilleurs temps");
		lblMeilleursTemps.setHorizontalAlignment(SwingConstants.CENTER);
		lblMeilleursTemps.setBounds(557, 496, 90, 13);
		add(lblMeilleursTemps);

	}// Fin constructeur

	/**
	 * Méthode qui permet d'organiser en ordre crossant les valeurs dans les
	 * tableaux et de les placer dans les trableaux
	 */
	// Inspiré de https://www.scaler.com/topics/selection-sort-java/
	public void organiserLesValeurs() {
		donnesClassements = GestionnaireDesDonnes.getInstance().getDonnesClassements();
		circuitArrayList = donnesClassements.getCircuitArrayList();
		curlingArrayList = donnesClassements.getCurlingArrayList();
		pecheArrayList = donnesClassements.getPecheArrayList();
		tankArrayList = donnesClassements.getTankArrayList();
		meilleursTempsArrayList = donnesClassements.getMeilleursTemps();

		if (circuitArrayList != null) {
			for (int i = 0; i < circuitArrayList.size() - 1; i++) {

				int indexMin = i;

				for (int j = i + 1; j < circuitArrayList.size(); j++) {

					if (Double.parseDouble(circuitArrayList.get(j).getTemps()) < Double
							.parseDouble((circuitArrayList.get(indexMin).getTemps()))) {
						indexMin = j;

					}

				}

				InformationEssais temp = circuitArrayList.get(i);
				circuitArrayList.set(i, circuitArrayList.get(indexMin));
				circuitArrayList.set(indexMin, temp);

			}

			for (int i = 0; i < circuitArrayList.size(); i++) {
				meilleursTempsArrayList.add(circuitArrayList.get(i));
				GestionnaireDesDonnes.getInstance().getDonnesClassements().setMeilleursTemps(meilleursTempsArrayList);

				if (circuitArrayList.size() < 4) {
					tableCircuit.setValueAt(circuitArrayList.get(i).getEssais(), i + 1, 0);
					tableCircuit.setValueAt(circuitArrayList.get(i).getTemps(), i + 1, 1);

				} else {
					for (int j = circuitArrayList.size(); j > 3; j--) {
						circuitArrayList.remove(j - 1);

					}

					tableCircuit.setValueAt(circuitArrayList.get(i).getEssais(), i + 1, 0);
					tableCircuit.setValueAt(circuitArrayList.get(i).getTemps(), i + 1, 1);

				}
			}
		}

		if (curlingArrayList != null) {
			for (int i = 0; i < curlingArrayList.size() - 1; i++) {

				int indexMin = i;

				for (int j = i + 1; j < curlingArrayList.size(); j++) {

					if (Double.parseDouble(curlingArrayList.get(j).getTemps()) < Double
							.parseDouble((curlingArrayList.get(indexMin).getTemps()))) {
						indexMin = j;

					}

				}

				InformationEssais temp = curlingArrayList.get(i);
				curlingArrayList.set(i, curlingArrayList.get(indexMin));
				curlingArrayList.set(indexMin, temp);

			}

			for (int i = 0; i < curlingArrayList.size(); i++) {
				meilleursTempsArrayList.add(curlingArrayList.get(i));
				GestionnaireDesDonnes.getInstance().getDonnesClassements().setMeilleursTemps(meilleursTempsArrayList);

				if (curlingArrayList.size() < 4) {
					tableCurling.setValueAt(curlingArrayList.get(i).getEssais(), i + 1, 0);
					tableCurling.setValueAt(curlingArrayList.get(i).getTemps(), i + 1, 1);

				} else {
					for (int j = curlingArrayList.size(); j > 3; j--) {
						curlingArrayList.remove(j - 1);

					}

					tableCurling.setValueAt(curlingArrayList.get(i).getEssais(), i + 1, 0);
					tableCurling.setValueAt(curlingArrayList.get(i).getTemps(), i + 1, 1);

				}
			}

		}

		if (pecheArrayList != null) {
			for (int i = 0; i < pecheArrayList.size() - 1; i++) {

				int indexMin = i;

				for (int j = i + 1; j < pecheArrayList.size(); j++) {

					if (Double.parseDouble(pecheArrayList.get(j).getTemps()) < Double
							.parseDouble((pecheArrayList.get(indexMin).getTemps()))) {
						indexMin = j;

					}

				}

				InformationEssais temp = pecheArrayList.get(i);
				pecheArrayList.set(i, pecheArrayList.get(indexMin));
				pecheArrayList.set(indexMin, temp);

			}

			for (int i = 0; i < pecheArrayList.size(); i++) {
				meilleursTempsArrayList.add(pecheArrayList.get(i));
				GestionnaireDesDonnes.getInstance().getDonnesClassements().setMeilleursTemps(meilleursTempsArrayList);

				if (pecheArrayList.size() < 4) {
					tablePeche.setValueAt(pecheArrayList.get(i).getEssais(), i + 1, 0);
					tablePeche.setValueAt(pecheArrayList.get(i).getTemps(), i + 1, 1);

				} else {
					for (int j = pecheArrayList.size(); j > 3; j--) {
						pecheArrayList.remove(j - 1);

					}

					tablePeche.setValueAt(pecheArrayList.get(i).getEssais(), i + 1, 0);
					tablePeche.setValueAt(pecheArrayList.get(i).getTemps(), i + 1, 1);

				}
			}

		}

		if (tankArrayList != null) {
			for (int i = 0; i < tankArrayList.size() - 1; i++) {

				int indexMin = i;

				for (int j = i + 1; j < tankArrayList.size(); j++) {

					if (Double.parseDouble(tankArrayList.get(j).getTemps()) < Double
							.parseDouble((tankArrayList.get(indexMin).getTemps()))) {
						indexMin = j;

					}

				}

				InformationEssais temp = tankArrayList.get(i);
				tankArrayList.set(i, tankArrayList.get(indexMin));
				tankArrayList.set(indexMin, temp);

			}

			for (int i = 0; i < tankArrayList.size(); i++) {
				meilleursTempsArrayList.add(tankArrayList.get(i));
				GestionnaireDesDonnes.getInstance().getDonnesClassements().setMeilleursTemps(meilleursTempsArrayList);

				if (tankArrayList.size() < 4) {
					tableTank.setValueAt(tankArrayList.get(i).getEssais(), i + 1, 0);
					tableTank.setValueAt(tankArrayList.get(i).getTemps(), i + 1, 1);

				} else {
					for (int j = tankArrayList.size(); j > 3; j--) {
						tankArrayList.remove(j - 1);

					}

					tableTank.setValueAt(tankArrayList.get(i).getEssais(), i + 1, 0);
					tableTank.setValueAt(tankArrayList.get(i).getTemps(), i + 1, 1);

				}

			}

		}

		if (meilleursTempsArrayList != null) {
			for (int i = 0; i < meilleursTempsArrayList.size() - 1; i++) {

				int indexMin = i;

				for (int j = i + 1; j < meilleursTempsArrayList.size(); j++) {

					if (Double.parseDouble(meilleursTempsArrayList.get(j).getTemps()) < Double
							.parseDouble((meilleursTempsArrayList.get(indexMin).getTemps()))) {
						indexMin = j;

					}

				}

				InformationEssais temp = meilleursTempsArrayList.get(i);
				meilleursTempsArrayList.set(i, meilleursTempsArrayList.get(indexMin));
				meilleursTempsArrayList.set(indexMin, temp);

			}

			for (int i = 0; i < meilleursTempsArrayList.size(); i++) {

				if (meilleursTempsArrayList.size() < 4) {
					tableMeilleursTemps.setValueAt(meilleursTempsArrayList.get(i).getJeu(), i + 1, 0);
					tableMeilleursTemps.setValueAt(meilleursTempsArrayList.get(i).getEssais(), i + 1, 1);
					tableMeilleursTemps.setValueAt(meilleursTempsArrayList.get(i).getTemps(), i + 1, 2);

				} else {
					for (int j = meilleursTempsArrayList.size(); j > 3; j--) {
						meilleursTempsArrayList.remove(j - 1);

					}

					tableMeilleursTemps.setValueAt(meilleursTempsArrayList.get(i).getJeu(), i + 1, 0);
					tableMeilleursTemps.setValueAt(meilleursTempsArrayList.get(i).getEssais(), i + 1, 1);
					tableMeilleursTemps.setValueAt(meilleursTempsArrayList.get(i).getTemps(), i + 1, 2);

				}

			}

		}

	}

}// Fin classe
