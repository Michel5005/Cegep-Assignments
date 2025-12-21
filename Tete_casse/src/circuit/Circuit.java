package circuit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GestionDesFichiers.GestionnaireDesDonnes;
import application.AppPrincipale7;
import application.Temps;
import circuit.graph.Edge;
import circuit.graph.Graph;
import classements.DonnesClassements;
import classements.InformationEssais;
import menus.MenuFinJeu;
import outils.OutilsImage;
import outils.Utils;
import outils.VerticalLabelUI;

/**
 * 
 * La classe Circuit étend JPanel pour afficher des objets dessinables tels que
 * des ampoules, des résistances et des sources, ainsi qu'une grille.
 * 
 * @author Tin Pham
 * @author Ismaïl Boukari
 * @author Liangchen Liu
 *
 */
public class Circuit extends InterfaceCircuit implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Le Path2D.Double pour la grille en x */
	private Path2D.Double gridX;
	/** Le Path2D.Double pour la grille en y */
	private Path2D.Double gridY;
	/** Position X d'un nouveau résisteur */
	private int posXRes = 150;
	/** Position y d'un nouveau résisteur */
	private int posYRes = 50;
	/** Les resisteurs */
	private ArrayList<Resistance> lesResistors;

	/** Position x du séparateur vertical */
	private int xMaxSeperator = 1300;
	/** Position y du séparateur horizontal */
	private int yMaxSeperator = 500;
	/** Espacement d'un cellule */
	private int cellSpacing =25;
	/** Cercle associé au point de sélection */
	private Ellipse2D.Double pointDeSelection;
	/** Point de sélection */
	private Point selection = new Point(0, 0);
	/** Point de sélection précédent */
	private Point selectionPrecedente = new Point(0, 0);
	/** Liste des fils */
	private ArrayList<Fil> fils = new ArrayList<Fil>();
	/** Valeur bouléenne associé au premier mouvement de la souris */
	private boolean premiereFoisMouseMoved = true;
	/** Fil en création (actuellement) */
	private Fil filCourant;
	/** Données du ciruit à sauvegarder */
	private DonnesCircuit donnesCircuit;
	/** Nombre de clics */
	private byte clicks = 0;
	/** Valeur bouléenne associé à la sélection d'un fil */
	private boolean filSelec = false;
	/** Valeur bouléenne associé à si le circuit est fermé ou non */
	private boolean circuitFerme = false;
	/**  */
	private JLabel lblTimer;
	/** Liste des noeuds */
	private List<Node> listOfNodes = new ArrayList<Node>();
	/** Liste des arêtes */
	private List<Edge> listOfEdges = new ArrayList<Edge>();
	/** Liste des tensions */
	private List<double[]> listOfVoltage = new ArrayList<double[]>();
	/** Liste des résistances */
	private List<double[]> listOfResistance = new ArrayList<double[]>();
	private Graph graph;
	private JToggleButton tglbtnSource;
	private Fil composantEnAjout = null;
	private JToggleButton tglbtnRes;
	private Fil composantSelectionne = null;
	private JToggleButton tglbtnFil;
	private double resistance = 2;
	private double electromotance = 10;
	private JSpinner spinnerElectromotance;
	private JSpinner spinnerResistance;
	private Point pointDeSelectionPrecedent = new Point(0, 0);
	private Point pointDrag = new Point(0, 0);
	private boolean enAnimation = false;
	private JToggleButton tglbtnAmpoule;
	private boolean circuitValide = false;
	private Temps classeTemps;
	private long tempsEcoule;
	private ImageIcon iconeErreur;
	private ImageIcon iconeErreurDisabled;
	private JButton btnErreur;
	private String messageErreur = "";
	private MenuFinJeu menuFinJeu;
	private ImageIcon iconeFil;
	private ImageIcon iconeRes;
	private ImageIcon iconeSource;
	private ImageIcon iconeAmpoule;
	private int nbEtoiles = 0;
	private Path2D.Double p;
	/**
	 * Crée le panneau.
	 */
	// Tin Pham
	public Circuit() {
		
		
		p = new Path2D.Double();
		p.moveTo(0, 0);
		p.lineTo(0, 5);
		p.moveTo(0,5);
		p.lineTo(cellSpacing, 5);
		p.moveTo(cellSpacing, 5);
		p.lineTo(cellSpacing, 0);

		iconeFil = new ImageIcon(OutilsImage.lireImage("fil.png"));
		iconeRes = new ImageIcon(OutilsImage.lireImage("resicone.png"));
		iconeSource = new ImageIcon(OutilsImage.lireImage("sourceicone.png"));
		iconeAmpoule = new ImageIcon(OutilsImage.lireImage("amp.png"));
		
		donnesCircuit = GestionnaireDesDonnes.getInstance().getDonnesCircuit();
		classeTemps = new Temps(1);
		menuFinJeu = new MenuFinJeu();
		iconeErreur = new ImageIcon(OutilsImage.lireImageEtRedimensionner("erreurenabled.png", 80, 80));
		iconeErreurDisabled = new ImageIcon(OutilsImage.lireImageEtRedimensionner("erreurdisabled.png", 80, 80));
		pointDeSelection = new Ellipse2D.Double(0 - 2.5d, 0 - 2.5d, 5, 5);
		setBackground(Color.white);
		creerObjetsDessinables();
		btnSuppComp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelected();
			}
		});
		tglbtnRes = new JToggleButton("");
		tglbtnRes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tglbtnRes.setIcon(null);
				tglbtnRes.setText("Résisteur");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tglbtnRes.setText("");
				tglbtnRes.setIcon(iconeRes);
			}
		});
		tglbtnRes.setIcon(iconeRes);
		tglbtnRes.setFont(AppPrincipale7.interBold.deriveFont(11f));
		tglbtnRes.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tglbtnRes.isSelected() && composantEnAjout == null) {
					tglbtnFil.setSelected(false);
					cancelWireCreation();
					System.out.println("Pos X res = " + posXRes);
					System.out.println("Pos Y res = " + posYRes);
					composantEnAjout = new Resistance((int) selection.getX(), (int) selection.getY(), resistance);
					lesResistors.add((Resistance) composantEnAjout);
					fils.add((Resistance) composantEnAjout);
					repaint();
				}
				focus();
			}
		});
		tglbtnRes.setBounds(581, 514, 249, 186);
		add(tglbtnRes);

		tglbtnFil = new JToggleButton("");
		tglbtnFil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tglbtnFil.setIcon(null);
				tglbtnFil.setText("Fil");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tglbtnFil.setText("");
				tglbtnFil.setIcon(iconeFil);
			}
		});
		tglbtnFil.setIcon(iconeFil);
		tglbtnFil.setFont(AppPrincipale7.interBold.deriveFont(11f));
		tglbtnFil.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!tglbtnFil.isSelected()) {
					filSelec = false;
					if (clicks > 0) {
						cancelWireCreation();
						clicks = 0;
					}
				} else {
					if (composantSelectionne !=null) {
						int i = fils.indexOf(composantSelectionne);
						fils.get(i).setSelectionne(false);
						composantSelectionne = null;
					}
					filSelec = true;
				}
				focus();
			}
		});
		tglbtnFil.setBounds(10, 514, 120, 186);
		add(tglbtnFil);

		lblTimer = new JLabel();
		lblTimer.setBounds(1192, 13, 249, 21);
		lblTimer.setFont(AppPrincipale7.interBold.deriveFont(12f));
		lblTimer.setText("Temps écoulé : " + tempsEcoule + " s");
		add(lblTimer);

		tglbtnAmpoule = new JToggleButton("");
		tglbtnAmpoule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tglbtnAmpoule.setIcon(null);
				tglbtnAmpoule.setText("Ampoule");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tglbtnAmpoule.setText("");
				tglbtnAmpoule.setIcon(iconeAmpoule);
			}
		});
		tglbtnAmpoule.setIcon(iconeAmpoule);
		tglbtnAmpoule.setFont(AppPrincipale7.interBold.deriveFont(11f));
		tglbtnAmpoule.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tglbtnAmpoule.isSelected() && composantEnAjout == null) {
					tglbtnFil.setSelected(false);
					cancelWireCreation();
					tglbtnFil.setSelected(false);
					composantEnAjout = new Ampoule((int) selection.getX() - cellSpacing,
							(int) selection.getY() - 2 * cellSpacing);
					fils.add(composantEnAjout);
					repaint();
				}
				focus();
			}
		});
		tglbtnAmpoule.setBounds(1033, 514, 239, 186);
		add(tglbtnAmpoule);

		tglbtnSource = new JToggleButton("");
		tglbtnSource.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tglbtnSource.setIcon(null);
				tglbtnSource.setText("Source");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tglbtnSource.setText("");
				tglbtnSource.setIcon(iconeSource);
			}
		});
		tglbtnSource.setIcon(iconeSource);
		tglbtnSource.setFont(AppPrincipale7.interBold.deriveFont(11f));
		tglbtnSource.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tglbtnSource.isSelected() && composantEnAjout == null) {
					tglbtnFil.setSelected(false);
					cancelWireCreation();
					tglbtnFil.setSelected(false);
					composantEnAjout = new Source((int) selection.getX() - cellSpacing,
							(int) selection.getY() - 2 * cellSpacing,
							electromotance);
					fils.add(composantEnAjout);
					repaint();
				}
				focus();
			}
		});
		tglbtnSource.setBounds(140, 514, 243, 186);
		add(tglbtnSource);

		spinnerElectromotance = new JSpinner();
		spinnerElectromotance.setFont(AppPrincipale7.interBold.deriveFont(11f));
		spinnerElectromotance.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (composantSelectionne instanceof Source) {
					electromotance = (double) spinnerElectromotance.getValue();
					composantSelectionne.setPotentiel((double) spinnerElectromotance.getValue());
					if (circuitValide) {
						simulerLeCircuit();
					}
				} else {
					electromotance = (double) spinnerElectromotance.getValue();
				}
			}
		});
		spinnerElectromotance.setModel(new SpinnerNumberModel(Double.valueOf(10), Double.valueOf(0.1),
				Double.valueOf(100), Double.valueOf(0.1)));
		spinnerElectromotance.setToolTipText("Electromotance de la source (V)");
		spinnerElectromotance.setBounds(410, 514, 161, 187);
		add(spinnerElectromotance);

		spinnerResistance = new JSpinner();
		spinnerResistance.setFont(AppPrincipale7.interBold.deriveFont(11f));
		spinnerResistance.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if ((composantSelectionne instanceof Resistance)) {
					resistance = (double) spinnerResistance.getValue();
					composantSelectionne.setResistance((double) spinnerResistance.getValue());
					if (circuitValide) {
						simulerLeCircuit();
					}
				} else {
					resistance = (double) spinnerResistance.getValue();
				}
			}
		});
		spinnerResistance.setModel(new SpinnerNumberModel(Double.valueOf(2), Double.valueOf(0.1), Double.valueOf(100),
				Double.valueOf(0.1)));
		spinnerResistance.setToolTipText("Résistance du résisteur (Ω)");
		spinnerResistance.setBounds(858, 514, 161, 187);
		add(spinnerResistance);

		btnErreur = new JButton("");
		btnErreur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnErreur.getIcon().equals(iconeErreur)) {
					errorMessage(messageErreur);
				}
				focus();
			}
		});
		btnErreur.setBounds(1192, 409, 80, 80);
		btnErreur.setBorder(null);
		btnErreur.setBackground(new Color(0, 0, 0, 0));
		btnErreur.setIcon(iconeErreurDisabled);

		add(btnErreur);

		JLabel lblResistance = new JLabel("Résistance du résisteur (ohms)");
		lblResistance.setFont(AppPrincipale7.interBold.deriveFont(11f));
		lblResistance.setVerticalAlignment(SwingConstants.TOP);
		lblResistance.setUI(new VerticalLabelUI(false));
		lblResistance.setHorizontalAlignment(SwingConstants.CENTER);
		lblResistance.setBounds(840, 514, 13, 186);
		add(lblResistance);

		JLabel lblElectromotance = new JLabel("Electromotance (source - volts)");
		lblElectromotance.setFont(AppPrincipale7.interBold.deriveFont(11f));
		lblElectromotance.setUI(new VerticalLabelUI(false));
		lblElectromotance.setVerticalAlignment(SwingConstants.TOP);
		lblElectromotance.setHorizontalAlignment(SwingConstants.CENTER);
		lblElectromotance.setBounds(393, 514, 14, 186);
		add(lblElectromotance);

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				pointSelection(e.getX(), e.getY());
				if (composantSelectionne != null && !filSelec) {
					if (enAnimation) {
						arreter();
					}
					composantSelectionne
							.setX((int) (pointDrag.getX() + (selection.getX() - pointDeSelectionPrecedent.getX())));
					composantSelectionne
							.setY((int) (pointDrag.getY() + (selection.getY() - pointDeSelectionPrecedent.getY())));
					repaint();
				}
			} // fin drag

			@Override
			public void mouseMoved(MouseEvent e) {
				pointSelection(e.getX(), e.getY());
				gestionDeLAffichageDuVoltageEtCourant(e);
				gestionMouseMoved();
				gestionMouseMovedComposant();
				mouseMovedFilSurvole(e);
				repaint();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// ampeSelec = isAmpeSelec(e.getX(), e.getY());
				// voltSelec = isVoltSelec(e.getX(), e.getY());
				pointDeSelectionPrecedent = new Point(selection);
				if (composantEnAjout != null) {
					placementComposant();
				} else {
					if (filSelec) {
						clicks++;
						try {
							creationFilAuPointDeSelection();
						} catch (Exception e1){
							System.out.println("Erreur");
						}
						repaint();
					} else {
						gestionMousePressed(e);
						if (composantSelectionne != null) {
							pointDrag = new Point(composantSelectionne.getX(), composantSelectionne.getY());
							// composantSelectionne.setOrigineReference(new
							// Point(composantSelectionne.getOrigine()));
							// composantSelectionne.setDestinationReference(new
							// Point(composantSelectionne.getDestination()));
							System.out.println("pointDrag = " + pointDrag);
						}
						cancelWireCreation();
						repaint();
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (composantSelectionne != null) {
					if (isComponentInOtherComponent(composantSelectionne) && pointDrag != null) {
						composantSelectionne.setX((int) pointDrag.getX());
						composantSelectionne.setY((int) pointDrag.getY());
						errorMessage("Vous ne pouvez pas glisser un composant sur un autre! Rappel connectez les composants bout à bout.");
					}
					simulerLeCircuit();
				}
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				gestionKeyPressed(e);
			}
		});

		chargerLesDonnees();

		sauv();
		btnReini();
		menuFinJeuPropChange();
	} // Fin constructeur

	/**
	 * Crée les objets dessinables pour le circuit.
	 */
	// Tin Pham
	private void creerObjetsDessinables() {
		// amp = new Ampoule(posXAmp, posYAmp, IMAGE_SIZE, IMAGE_SIZE, 0, 0,
		// "light-bulb.png");
		lesResistors = new ArrayList<Resistance>();
	} // Fin méthode

	/**
	 * On redéfinit la methode de dessin
	 * 
	 * @param g Le contexte graphique
	 */
	// Tin Pham
	@Override
	public void paintComponent(Graphics g) {
		donnesCircuit = GestionnaireDesDonnes.getInstance().getDonnesCircuit();
		chargerLesDonnees();
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		creerGrid();
		g2d.setColor(Color.lightGray);
		g2d.draw(gridX);
		g2d.draw(gridY);
		// Fil fil = new Fil((7*cellSpacing)-(2), (7*cellSpacing)-(2),
		// (3*cellSpacing)+(4), 4,0,0);
		// fil.dessiner(g2d);
		for (int i = 0; i < fils.size(); i++) {
			fils.get(i).dessiner(g2d);
		} // Fin pour
		for (int i = 0; i < fils.size(); i++) {
			fils.get(i).dessinerCerclesExtremites(g2d);
		}
		g2d.setColor(Color.red);
		g2d.fill(pointDeSelection);
		if (circuitValide && circuitFerme) {
			for (int i = 0; i < fils.size(); i++) {
				fils.get(i).dessinerCourant(g2d);
			} // Fin pour
			for (Node node : listOfNodes) {
				node.dessiner(g2d);
			}
			for (int i = 0; i < fils.size(); i++) { // On veut que le courant apparaisse par dessus les fils
				fils.get(i).dessinerDonnesScientifiques(g2d);
			} // Fin pour
		}

		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, yMaxSeperator + 1, getWidth(), getHeight() - yMaxSeperator + 1);
		dessinEchelle(g);
	} // Fin méthode

	/**
	 * Méthode qui permet de calculer le temps écoulé
	 * 
	 * @param panelOuvert booléen qui détermine si le panel est ouvert ou non
	 */
	// Liangchen Liu
	public void calculerTempsEcoule(boolean panelOuvert) {
		if (panelOuvert) {
			classeTemps.demarrer();
			classeTemps.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					switch (evt.getPropertyName()) {
						case "temps":
							lblTimer.setText("" + evt.getNewValue());

							break;

						case "tempsEcoule":

							tempsEcoule = (long) evt.getNewValue();

							break;

					}// fin switch
				}// fin propertyChange
			});

		} else {
			classeTemps.reiniTemps();
			classeTemps.arreter();

		}

	}

	/**
	 * Méthode chargeant les données du circuit à partir de la sauvegarde.
	 */
	// Liangchen Liu
	private void chargerLesDonnees() {
		fils = donnesCircuit.getFils();
	}

	/**
	 * Méthode pour faire sauvegarder le niveau lors du clic sur le bouton.
	 */
	// Liangchen Liu
	private void sauv() {
		btnSauvegarder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {

				arreter();
				resetNodesOfWires();
				resetCurrents();
				
				donnesCircuit.setFils(fils);
				donnesCircuit.setTempsEcoule(tempsEcoule);

				GestionnaireDesDonnes.getInstance().setNiveau(1);
				GestionnaireDesDonnes.getInstance().setDonnesCircuit(donnesCircuit);
				GestionnaireDesDonnes.getInstance().sauvegarder();
				
				simulerLeCircuit();
				focus();
			}// fin méthode
		});// fin listener

	}// fin méthode

	/**
	 * Méthode pour faire réinitialiser les données du niveau
	 */
	// Liangchen Liu
	public void reini() {
		fils.clear();
		lesResistors.clear();
		clicks = 0;
		classeTemps.reiniTemps();
		donnesCircuit.setFils(fils);
		GestionnaireDesDonnes.getInstance().setDonnesCircuit(donnesCircuit);
		enableAllButtons();
		circuitFerme = false;
		requestFocusInWindow();
		repaint();
	}// fin méthode

	/**
	 * Méthode pour faire réinitialiser le niveau lors du clic sur le bouton.
	 */
	// Liangchen Liu
	private void btnReini() {
		btnReinitialiser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				reini();
			}// fin méthode
		});// fin listener

	}

	/**
	 * Creation du Path2D formant la grille
	 */
	// Tin Pham
	private void creerGrid() {
		cellSpacing = 25;
		gridX = new Path2D.Double();
		for (int k = 0; k <= xMaxSeperator / cellSpacing; k++) {
			gridX.moveTo(k * cellSpacing, 0);
			gridX.lineTo(k * cellSpacing, yMaxSeperator);
		} // fin pour
		gridY = new Path2D.Double();

		for (int k = 0; k < yMaxSeperator / cellSpacing; k++) {
			gridY.moveTo(0, k * cellSpacing);
			gridY.lineTo(xMaxSeperator, k * cellSpacing);
		} // fin pour
	} // Fin méthode

	/**
	 * Méthode ajustant le point de sélection à la position (x,y).
	 * 
	 * @param x postion x
	 * @param y position y
	 */
	// Ismaïl Boukari
	private void pointSelection(int x, int y) {
		int nX, nY;

		if (y > yMaxSeperator) {
			y = yMaxSeperator;
		}
		double a = (double) x / cellSpacing;
		int b = x / cellSpacing;
		if ((a - b) >= 0.5f) {
			nX = (int) (x / cellSpacing) + 1;
		} else {
			nX = (int) (x / cellSpacing);
		}

		a = (double) y / cellSpacing;
		b = y / cellSpacing;
		if ((a - b) >= 0.5f) {
			nY = (int) (y / cellSpacing) + 1;
		} else {
			nY = (int) (y / cellSpacing);
		}

		pointDeSelection.setFrame(nX * cellSpacing - 2.5f, nY * cellSpacing - 2.5f, 5, 5);
		selection.move(nX * cellSpacing, nY * cellSpacing);

	}

	/**
	 * Méthode créant un fil au point de sélection.
	 * 
	 */
	// Ismaïl Boukari
	private void creationFilAuPointDeSelection() {

		for (Fil fil : fils) {
			if (fil.isPointInside(pointDeSelectionPrecedent)) {
				errorMessage("Un fil (ou un autre composant) existe déjà à cet endroit, il faut connecter les fils (ou composants) bout à bout! Si vous voulez"
						+ " sélectionner un composant, désélectionnez le bouton fil.");
				if (clicks > 1) {
					cancelWireCreation();
				}
				clicks = 0;
				return;
			}
		}

		if (clicks == 1) {
			fils.add(new Fil((int) selection.getX() - 2, (int) selection.getY() - 2, 4, 4, 0, 0));
			selectionPrecedente = new Point(selection);
			filCourant = fils.get(fils.size() - 1);
		}
		if (clicks == 2) {
			int dx = (int) (selection.getX() - selectionPrecedente.getX());
			int dy = (int) (selection.getY() - selectionPrecedente.getY());

			fils.set(fils.size() - 1, new Fil(filCourant));
			if (Math.abs(dx) > Math.abs(dy)) {
				if (dx > 0) {
					getLastCreatedWire().setWidth(dx + 4);
					getLastCreatedWire().setOrigine(
							new Point((int) selectionPrecedente.getX(), (int) getLastCreatedWire().getY() + 2));
					getLastCreatedWire()
							.setDestination(new Point((int) selection.getX(), (int) getLastCreatedWire().getY() + 2));

					donnesCircuit.setFils(fils);
					System.out.println("Origine: " + getLastCreatedWire().getOrigine().toString());
					System.out.println("Destination: " + getLastCreatedWire().getDestination().toString());
					getLastCreatedWire().generatePositionPoints();
					getLastCreatedWire().setEnPlacement(false);
					if (isComponentInOtherComponent(getLastCreatedWire())) {
						errorMessage("Un fil (ou un autre composant) existe déjà à cet endroit, il faut connecter les fils (ou composants) bout à bout! Si vous voulez"
						+ " sélectionner un composant, désélectionnez le bouton fil.");
						deleteLastCreatedWire();
					}
					try {
						simulerLeCircuit();
					} catch (Exception e) {
						System.out.print("Erreur dans la simulation du circuit");
					}
					// getLastCreatedWire().generateCurrentRectangles();
					// System.out.println("Liste de points:");
					// for (Point p : getLastCreatedWire().getPoints()) {
					// System.out.println(p.toString());
					// }
				} else if (dx < 0) {
					getLastCreatedWire().setX(getLastCreatedWire().getX() + dx);
					getLastCreatedWire().setWidth(Math.abs(dx) + 4);
					getLastCreatedWire()
							.setDestination(new Point((int) selection.getX(), (int) getLastCreatedWire().getY() + 2));
					getLastCreatedWire().setOrigine(
							new Point((int) selectionPrecedente.getX(), (int) getLastCreatedWire().getY() + 2));

					donnesCircuit.setFils(fils);
					System.out.println("Origine: " + getLastCreatedWire().getOrigine().toString());
					System.out.println("Destination: " + getLastCreatedWire().getDestination().toString());
					getLastCreatedWire().generatePositionPoints();
					getLastCreatedWire().setEnPlacement(false);
					if (isComponentInOtherComponent(getLastCreatedWire())) {
						errorMessage("Un fil (ou un autre composant) existe déjà à cet endroit, il faut connecter les fils (ou composants) bout à bout! Si vous voulez"
						+ " sélectionner un composant, désélectionnez le bouton fil.");
						deleteLastCreatedWire();
					}
					try {
						simulerLeCircuit();
					} catch (Exception e) {
						System.out.print("Erreur dans la simulation du circuit");
					}
					// getLastCreatedWire().generateCurrentRectangles();
					// System.out.println("Liste de points:");
					// for (Point p : getLastCreatedWire().getPoints()) {
					// System.out.println(p.toString());
					// }
				} else {
					deleteLastCreatedWire();
				}
				clicks = 0;
			} else {
				if (dy > 0) {
					getLastCreatedWire().setHeight(dy + 4);
					getLastCreatedWire()
							.setDestination(new Point((int) getLastCreatedWire().getX() + 2, (int) selection.getY()));
					getLastCreatedWire().setOrigine(
							new Point((int) getLastCreatedWire().getX() + 2, (int) selectionPrecedente.getY()));

					donnesCircuit.setFils(fils);
					System.out.println("Origine: " + getLastCreatedWire().getOrigine().toString());
					System.out.println("Destination: " + getLastCreatedWire().getDestination().toString());
					getLastCreatedWire().generatePositionPoints();
					getLastCreatedWire().setEnPlacement(false);
					if (isComponentInOtherComponent(getLastCreatedWire())) {
						errorMessage("Un fil (ou un autre composant) existe déjà à cet endroit, il faut connecter les fils (ou composants) bout à bout! Si vous voulez"
						+ " sélectionner un composant, désélectionnez le bouton fil.");
						deleteLastCreatedWire();
					}
					try {
						simulerLeCircuit();
					} catch (Exception e) {
						System.out.print("Erreur dans la simulation du circuit");
					}
					// getLastCreatedWire().generateCurrentRectangles();
					// System.out.println("Liste de points:");
					// for (Point p : getLastCreatedWire().getPoints()) {
					// System.out.println(p.toString());
					// }
				} else if (dy < 0) {
					getLastCreatedWire().setY(getLastCreatedWire().getY() + dy);
					getLastCreatedWire().setHeight(Math.abs(dy) + 4);
					getLastCreatedWire().setOrigine(
							new Point((int) getLastCreatedWire().getX() + 2, (int) selectionPrecedente.getY()));
					getLastCreatedWire()
							.setDestination(new Point((int) getLastCreatedWire().getX() + 2, (int) selection.getY()));

					donnesCircuit.setFils(fils);
					System.out.println("Origine: " + getLastCreatedWire().getOrigine().toString());
					System.out.println("Destination: " + getLastCreatedWire().getDestination().toString());
					getLastCreatedWire().generatePositionPoints();
					getLastCreatedWire().setEnPlacement(false);
					if (isComponentInOtherComponent(getLastCreatedWire())) {
						errorMessage("Un fil (ou un autre composant) existe déjà à cet endroit, il faut connecter les fils (ou composants) bout à bout! Si vous voulez"
						+ " sélectionner un composant, désélectionnez le bouton fil.");
						deleteLastCreatedWire();
					}
					try {
						simulerLeCircuit();
					} catch (Exception e) {
						System.out.print("Erreur dans la simulation du circuit");
					}
					// getLastCreatedWire().generateCurrentRectangles();
					// System.out.println("Liste de points:");
					// for (Point p : getLastCreatedWire().getPoints()) {
					// System.out.println(p.toString());
					// }
				} else {
					deleteLastCreatedWire();
				}
				clicks = 0;
			}
		}

		premiereFoisMouseMoved = true;

	}

	/**
	 * Méthode qui gère les événements "mouseMouved" pour le dessin des fils.
	 */
	// Ismaïl Boukari
	private void gestionMouseMoved() {
		if (clicks == 1) {
			if (premiereFoisMouseMoved) {
				filCourant = fils.get(fils.size() - 1);
				premiereFoisMouseMoved = false;
			}
			int dx = (int) (selection.getX() - selectionPrecedente.getX());
			int dy = (int) (selection.getY() - selectionPrecedente.getY());
			fils.set(fils.size() - 1, new Fil(filCourant));
			if (Math.abs(dx) > Math.abs(dy)) {
				if (dx > 0) {
					fils.get(fils.size() - 1).setWidth(dx + 4);
				} else if (dx < 0) {
					fils.get(fils.size() - 1).setX(fils.get(fils.size() - 1).getX() + dx);
					fils.get(fils.size() - 1).setWidth(Math.abs(dx) + 4);
				}
			} else {
				if (dy > 0) {
					fils.get(fils.size() - 1).setHeight(dy + 4);
				} else if (dy < 0) {
					fils.get(fils.size() - 1).setY(fils.get(fils.size() - 1).getY() + dy);
					fils.get(fils.size() - 1).setHeight(Math.abs(dy) + 4);
				}
			}
		}
	}

	/**
	 * Méthode retournant le dernier fil créée.
	 * 
	 * @return Le dernier fil créé.
	 */
	// Ismaïl Boukari
	private Fil getLastCreatedWire() {
		return fils.get(fils.size() - 1);
	}

	/**
	 * Méthode supprimant le dernier fil créée.
	 */
	// Ismaïl Boukari
	private void deleteLastCreatedWire() {
		fils.remove(fils.size() - 1);
		repaint();
	}

	/**
	 * Méthode convertissant le circuit en graphe.
	 */
	// Ismaïl Boukari
	private void circuitToGraph() {
		resetNodesOfWires();
		Stack<Node> nodesToVisit = new Stack<Node>();
		List<Node> visitedNodes = new ArrayList<Node>();

		listOfEdges.clear();
		listOfNodes.clear();
		listOfVoltage.clear();
		listOfResistance.clear();

		Fil currentWire = null;
		try {
			currentWire = fils.get(0);

			currentWire.setOrigineNode(new Node(currentWire.getOrigine(), 0, 0));
			currentWire.setDestinationNode(new Node(currentWire.getDestination(), 0, 0));
			listOfNodes.add(currentWire.getOrigineNode());
			listOfNodes.add(currentWire.getDestinationNode());
			listOfEdges.add(new Edge(0, 1));
			currentWire.getDestinationNode().addNeighbour(currentWire.getOrigineNode());
			nodesToVisit.add(currentWire.getDestinationNode());
			nodesToVisit.add(currentWire.getOrigineNode());
			addResistanceOrVoltageSource(currentWire);
			while (!nodesToVisit.isEmpty()) {
				Node currentNode = nodesToVisit.pop();
				Point nodePosition = currentNode.getPosition();
				visitedNodes.add(currentNode);
				for (Fil wire : fils) {
					if (wire.getOrigineNode() == null && wire.getDestinationNode() == null) {
						if (wire.getOrigine().equals(nodePosition)) {
							wire.setOrigineNode(currentNode);
							boolean nodeAlreadyExists = false;
							for (Node node : listOfNodes) {
								if (node.getPosition().equals(wire.getDestination())) { // Si le noeud existe déjà
									wire.setDestinationNode(node);
									nodeAlreadyExists = true;
									break;
								}
							}
							if (!nodeAlreadyExists) {
								wire.setDestinationNode(new Node(wire.getDestination(), 0, 0));
								listOfNodes.add(wire.getDestinationNode());
								nodesToVisit.add(wire.getDestinationNode());
							}
							wire.getOrigineNode().addNeighbour(wire.getDestinationNode());
							listOfEdges.add(new Edge(listOfNodes.indexOf(wire.getOrigineNode()),
									listOfNodes.indexOf(wire.getDestinationNode())));
							addResistanceOrVoltageSource(wire);
						} else if (wire.getDestination().equals(nodePosition)) {
							wire.setOrigineNode(currentNode);
							boolean nodeAlreadyExists = false;
							for (Node node : listOfNodes) {
								if (node.getPosition().equals(wire.getOrigine())) { // Si le noeud existe déjà
									wire.setDestinationNode(node);
									nodeAlreadyExists = true;
									break;
								}
							}
							if (!nodeAlreadyExists) {
								wire.setDestinationNode(new Node(wire.getOrigine(), 0, 0));
								listOfNodes.add(wire.getDestinationNode());
								nodesToVisit.add(wire.getDestinationNode());
							}
							wire.getOrigineNode().addNeighbour(wire.getDestinationNode());
							listOfEdges.add(new Edge(listOfNodes.indexOf(wire.getOrigineNode()),
									listOfNodes.indexOf(wire.getDestinationNode())));
							addResistanceOrVoltageSource(wire);
						}
					}
				}
			}
			graph = new Graph(listOfEdges, listOfNodes.size());
			for (double[] potentiel : listOfVoltage) {
				graph.addVoltage((int) potentiel[0], (int) potentiel[1], potentiel[2]);
			}
			for (double[] resistance : listOfResistance) {
				graph.addResistance((int) resistance[0], (int) resistance[1], resistance[2]);
			}

			graph.findAllPossibleCycles();
			graph.printListOfEdges();
			graph.printCycles();

			if (graph.getCycles().isEmpty()) {
				circuitFerme = false;
			} else {
				circuitFerme = true;
			}
		} catch (IndexOutOfBoundsException e) {
			messageErreur = "Votre circuit est vide. Ajoutez des fils et complétez votre circuit!";
		}
	}

	/**
	 * Méthode calculant les valeurs scientifiques du circuit (différences de
	 * potentiel et courant).
	 */
	// Ismaïl Boukari
	private void calculDesValeursScientifiques() {

		for (Node node : listOfNodes) {
			node.setNumber(listOfNodes.indexOf(node));
		}

		graph.findCyclesForAnalysis();
		graph.printCyclesForAnalysis();
		graph.generateAdjacencyMatrixOfCycles();
		graph.printAdjacencyMatrixOfCycles();
		graph.generateKirchhofEquations();
		graph.solveEquations();
		graph.printArrayCurrents();
		graph.generateCurrentMatrix();
		graph.printCurrentMatrix();

		double maxCourrant = Utils.findHighestValue(graph.getCurrentMatrix());
		System.out.println("Max courrant : " + maxCourrant);
		for (Fil wire : fils) {
			int node1 = listOfNodes.indexOf(wire.getOrigineNode());
			int node2 = listOfNodes.indexOf(wire.getDestinationNode());
			double courant = graph.getCurrent(node1, node2);
			if ((Math.abs(courant) / maxCourrant) < 0.00000001) { // Si le courant est négligeable (court-circuit)
				System.out.println("Courant négligeable");
				courant = 0;
			} else if (courant < 0) {
				Node origine = wire.getOrigineNode();
				wire.setOrigineNode(wire.getDestinationNode());
				wire.setDestinationNode(origine);
			}
			wire.setCourant(Math.abs(courant));
			genPointsAndCurrentRectangles(wire);
		}

		Queue<Node> nodesToVisit = new LinkedList<Node>();
		List<Edge> visitedEdges = new ArrayList<Edge>();
		Node currentNode = null;
		for (Fil wire : fils) {
			if (wire instanceof Source) {
				currentNode = wire.getOrigineNode();
				break;
			}
		}
		if (currentNode == null) {
			currentNode = listOfNodes.get(0);
		}
		nodesToVisit.add(currentNode);

		while (!nodesToVisit.isEmpty()) {
			currentNode = nodesToVisit.remove();
			// visitedNodes.add(currentNode);
			for (Node neighbour : currentNode.getNeighbours()) {
				int node1 = listOfNodes.indexOf(currentNode);
				int node2 = listOfNodes.indexOf(neighbour);
				Edge edge = new Edge(node1, node2);
				if (!visitedEdges.contains(edge)) {
					for (Fil wire : fils) {
						if (wire.getOrigineNode().equals(currentNode) && wire.getDestinationNode().equals(neighbour)) {
							if (wire instanceof Resistance) {
								double resistance = ((Resistance) wire).getResistance();
								neighbour.setVoltage(currentNode.getVoltage() - resistance * wire.getCourant());
								nodesToVisit.add(neighbour);
								visitedEdges.add(edge);
								if (Utils.almostEqual(0, neighbour.getVoltage(), 0.00001d)) {
									neighbour.setVoltage(0);
								} 
							} else if (wire instanceof Source) {
								double electromotance = ((Source) wire).getPotentiel();
								if (wire.getOrigine().equals(currentNode.getPosition())) {
									neighbour.setVoltage(currentNode.getVoltage() + electromotance);
								} else {
									neighbour.setVoltage(currentNode.getVoltage() - electromotance);
								}
								if (Utils.almostEqual(0, neighbour.getVoltage(), 0.00001d)) {
									neighbour.setVoltage(0);
								}
								nodesToVisit.add(neighbour);
								visitedEdges.add(edge);
								currentNode.addNdVisited(neighbour);
							} else {
								if (neighbour.getVoltage() < currentNode.getVoltage()) {
									neighbour.setVoltage(currentNode.getVoltage());
									System.out.println("Vf = " + neighbour.getVoltage());
									nodesToVisit.add(neighbour);
									visitedEdges.add(edge);
								} 
							}
						} else if (wire.getOrigineNode().equals(neighbour) && wire.getDestinationNode().equals(currentNode)) {
							if (wire instanceof Resistance) {
								// double resistance = ((Resistance) wire).getResistanceType().getResistance();
								// neighbour.setVoltage(currentNode.getVoltage() + resistance *
								// wire.getCourant());
							} else if (wire instanceof Source) {
								double electromotance = ((Source) wire).getPotentiel();
								if (wire.getOrigine().equals(neighbour.getPosition())) {
									
									neighbour.setVoltage(currentNode.getVoltage() - electromotance);
								} else {
									neighbour.setVoltage(currentNode.getVoltage() + electromotance);
								}
								if (Utils.almostEqual(0, neighbour.getVoltage(), 0.00001d)) {
									neighbour.setVoltage(0);
								}
								nodesToVisit.add(neighbour);
									visitedEdges.add(edge);
								currentNode.addNdVisited(neighbour);
							} else {
								if (neighbour.getVoltage() < currentNode.getVoltage()) {
									neighbour.setVoltage(currentNode.getVoltage());
									System.out.println("Vf = " + neighbour.getVoltage());
									nodesToVisit.add(neighbour);
									visitedEdges.add(edge);
								}
							}
						}
					}
				}
			}
		}

	}

	/**
	 * Méthode annulant la création d'un nouveau fil.
	 */
	// Ismaïl Boukari
	private void cancelWireCreation() {
		tglbtnFil.setSelected(false);
		if (clicks > 0) {
			deleteLastCreatedWire();
			clicks = 0;
		}
	}

	/**
	 * Méthode rénitialisant les noeuds de tous les fils du circuit.
	 */
	// Ismaïl Boukari
	private void resetNodesOfWires() {
		for (int i = 0; i < fils.size(); i++) {
			fils.get(i).setOrigineNode(null);
			fils.get(i).setDestinationNode(null);
		}
	}

	/**
	 * Méthode qui gère l'affichage du voltage et du courant (pour chaque noeud).
	 * 
	 * @param e l'évènement de la souris.
	 */
	// Ismaïl Boukari
	private void gestionDeLAffichageDuVoltageEtCourant(MouseEvent e) {
		if (circuitFerme) {
			for (Node node : listOfNodes) {
				if (node.contient(e.getX(), e.getY())) {
					node.setAfficherVoltage(true);
				} else {
					node.setAfficherVoltage(false);
				}
			}
		}
	}

	/**
	 * Méthode qui ajoute une source ou un resisteur dans la liste des
	 * tensions/resistances.
	 * 
	 * @param fil le fil contenant la source ou le resisteur à ajouter.
	 */
	// Ismaïl Boukari
	public void addResistanceOrVoltageSource(Fil fil) {
		int node1 = listOfNodes.indexOf(fil.getOrigineNode());
		int node2 = listOfNodes.indexOf(fil.getDestinationNode());
		if (Source.class.isInstance(fil)) {
			double[] voltageSource = new double[3];
			if (fil.getOrigine().equals(fil.getOrigineNode().getPosition())) {
				voltageSource[0] = node1; // {0,1,((Source) currentWire).getPotentiel()};
				voltageSource[1] = node2;
				voltageSource[2] = ((Source) fil).getPotentiel();
			} else {
				voltageSource[0] = node2;
				voltageSource[1] = node1;
				voltageSource[2] = ((Source) fil).getPotentiel();
			}
			listOfVoltage.add(voltageSource);
		} else {
			double[] resistance = { node1, node2, fil.getResistance() };
			listOfResistance.add(resistance);
		}
	}

	/**
	 * Méthode qui gère les évènements de la souris lors de la création d'un nouveau
	 * composant.
	 */
	// Ismaïl Boukari
	public void gestionMouseMovedComposant() {
		if (composantEnAjout != null) {
			composantEnAjout.setX(selection.x - 25);
			composantEnAjout.setY(selection.y - 25);
			repaint();
		}
	}

	/**
	 * Méthode qui effectue le placement d'un composant.
	 */
	// Ismaïl Boukari
	public void placementComposant() {
		if (composantEnAjout != null) {
			if (isComponentInOtherComponent(composantEnAjout)) {
				errorMessage(
						"Vous ne pouvez pas placer un composant sur un autre composant. Rappel: connectez les composants bout à bout.");
			} else {
				composantEnAjout.setEnPlacement(false);
				composantEnAjout.generatePositionPoints();
				unselectAllTglButtons();
				composantEnAjout = null;
				simulerLeCircuit();
				repaint();
			}
		}
	}

	/**
	 * Méthode qui met le focus sur le panneau.
	 */
	// Ismaïl Boukari
	public void focus() {
		this.requestFocusInWindow();
	}

	/**
	 * Méthode qui gère les évènements du clavier.
	 * 
	 * @param e l'évènement du clavier.
	 */
	// Ismaïl Boukari
	public void gestionKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (composantEnAjout != null) {
				System.out.println("Rotation de +90");
				composantEnAjout.rotate90Deg();
			} else if (composantSelectionne != null) {
				System.out.println("Rotation de +90");
				composantSelectionne.rotate90Deg();
				simulerLeCircuit();
			}
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (composantEnAjout != null) {
				System.out.println("Rotation de -90");
				composantEnAjout.rotateMinus90Deg();
			} else if (composantSelectionne != null) {
				System.out.println("Rotation de -90");
				composantSelectionne.rotateMinus90Deg();
				simulerLeCircuit();
			}
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			unselectAllTglButtons();
			cancelCreation();
		} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
			if (deleteSelected()) {
				enableAllButtons();
				unselectAllTglButtons();
			}
			repaint();
		}
	}

	/**
	 * Méthode qui gère les évènements "mousePressed" de la souris.
	 * 
	 * @param e l'évènement de la souris.
	 */
	// Ismaïl Boukari
	public void gestionMousePressed(MouseEvent e) {
		boolean filTrouve = false;
		if (e.getButton() == MouseEvent.BUTTON1) {
			for (Fil fil : fils) {
				if (fil.contient(e.getX(), e.getY()) && !filTrouve) {
					fil.setSelectionne(true);
					composantSelectionne = fil;
					if (composantSelectionne instanceof Ampoule) {
						spinnerResistance.setValue(composantSelectionne.getResistance());
						disableAllTglButtons();
						spinnerElectromotance.setEnabled(false);
						spinnerResistance.setEnabled(false);
					} else if (composantSelectionne instanceof Resistance) {
						spinnerResistance.setValue(composantSelectionne.getResistance());
						disableAllTglButtons();
						spinnerElectromotance.setEnabled(false);
					} else if (composantSelectionne instanceof Source) {
						spinnerElectromotance.setValue(composantSelectionne.getPotentiel());
						disableAllTglButtons();
						spinnerResistance.setEnabled(false);
					}
					filTrouve = true;
				} else {
					fil.setSelectionne(false);
					enableAllButtons();
					spinnerElectromotance.setEnabled(true);
					spinnerResistance.setEnabled(true);
					if (!filTrouve) {
						composantSelectionne = null;
					}
				}
			}
		}
	}

	/**
	 * Méthode qui annule la création d'un composant.
	 */
	// Ismaïl Boukari
	public void cancelCreation() {
		if (composantEnAjout != null) {
			fils.remove(composantEnAjout);
			composantEnAjout = null;
			tglbtnSource.setSelected(false);
			tglbtnRes.setSelected(false);
			repaint();
		}
	}

	/**
	 * Méthode qui supprime le composant sélectionné.
	 * 
	 * @return true si un composant a été supprimé, false sinon.
	 */
	// Ismaïl Boukari
	public boolean deleteSelected() {
		if (composantSelectionne != null) {
			fils.remove(composantSelectionne);
			composantSelectionne = null;
			simulerLeCircuit();
			repaint();
			return true;
		}
		return false;
	}

	/**
	 * Méthode d'animation du circuit.
	 */
	// Ismaïl Boukari
	@Override
	public void run() {
		while (enAnimation) {
			try {
				for (Fil fil : fils) {
					fil.currentAnimationStep();
				}
				repaint();
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
		System.out.println("Fin de l'animation");
	}

	/**
	 * Méthode qui démarre l'animation du circuit.
	 */
	// Ismaïl Boukari
	public void demarrer() {
		if (!enAnimation) {
			for (Fil fil : fils) {
				genPointsAndCurrentRectangles(fil);
			}
			System.out.println("Démarrage de l'animation");
			enAnimation = true;
			Thread t = new Thread(this);
			t.start();
		}
	}

	/**
	 * Méthode qui arrête l'animation du circuit.
	 */
	// Ismaïl Boukari
	public void arreter() {
		enAnimation = false;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Méthode qui génère les points et les rectangles (courant) pour un fil.
	 * 
	 * @param fil le fil pour lequel on génère les points et les rectangles.
	 */
	// Ismaïl Boukari
	public void genPointsAndCurrentRectangles(Fil fil) {
		fil.generatePoints();
		fil.generateCurrentRectangles();
	}

	/**
	 * Méthode qui déselectionne tous les boutons à deux états.
	 */
	// Ismaïl Boukari
	public void unselectAllTglButtons() {
		tglbtnSource.setSelected(false);
		tglbtnRes.setSelected(false);
		tglbtnFil.setSelected(false);
		tglbtnAmpoule.setSelected(false);
	}

	/**
	 * Méthode qui désactive tous les boutons à deux états.
	 */
	// Ismaïl Boukari
	public void disableAllTglButtons() {
		tglbtnSource.setEnabled(false);
		tglbtnRes.setEnabled(false);
		tglbtnFil.setEnabled(false);
		tglbtnAmpoule.setEnabled(false);
	}

	/**
	 * Méthode qui active tous les boutons en plus des tourniquets.
	 */
	// Ismaïl Boukari
	public void enableAllButtons() {
		tglbtnSource.setEnabled(true);
		tglbtnRes.setEnabled(true);
		tglbtnFil.setEnabled(true);
		tglbtnAmpoule.setEnabled(true);
		spinnerElectromotance.setEnabled(true);
		spinnerResistance.setEnabled(true);
	}

	/**
	 * Méthode qui réinitialise les tous les listes.
	 */
	// Ismaïl Boukari
	public void clearAllLists() {
		fils.clear();
		listOfEdges.clear();
		listOfNodes.clear();
		listOfResistance.clear();
		listOfVoltage.clear();
	}

	/**
	 * Méthode qui convertit simule le circuit
	 */
	// Ismaïl Boukari
	public void simulerLeCircuit() {
		resetCurrents();
		btnErreur.setIcon(iconeErreurDisabled);
		if (enAnimation) {
			arreter();
		}
		circuitToGraph();
		if (circuitFerme) {
			try {
				calculDesValeursScientifiques();
				circuitValide = true;
				demarrer();
				isVictoire();
			} catch (ArrayIndexOutOfBoundsException e1) {
				messageErreur = "CIRCUIT INVALIDE: Vous avez probablement créé deux circuits distincts " +
						"ou il y a des fils qui ne sont pas connecté au circuit principal. Enlevez" +
						" les fils/circuits superflus.";
				btnErreur.setIcon(iconeErreur);
				circuitValide = false;
			}
		} else {
			messageErreur = "CIRCUIT INVALIDE: Le circuit n'est pas fermé.";
			btnErreur.setIcon(iconeErreur);
			circuitValide = false;
		}
	}

	/**
	 * Méthode qui affiche un message d'erreur.
	 * 
	 * @param message le message d'erreur à afficher.
	 */
	// Ismaïl Boukari
	public void errorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Méthode qui détermine si l'utilisateur a gagné.
	 */
	// Ismaïl Boukari
	public void isVictoire() {
		for (Fil fil : fils) {
			if ((fil instanceof Ampoule) && fil.getCourant() > 0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							victoireClassementBadge();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
	}

	/**
	 * Méthode pour faire afficher l'écran de victoire
	 */
	// Liangchen Liu
	private void victoireClassementBadge() {
		int nbCycles = graph.getCyclesForAnalysis().size();
		if (nbCycles>nbEtoiles && nbEtoiles<3) {
			menuFinJeu.setVisible(true);
			nbEtoiles = nbCycles;
			if (nbEtoiles >3) {
				nbEtoiles = 3;
			}
			classeTemps.arreter();

			DonnesClassements donnesClassements = GestionnaireDesDonnes.getInstance().getDonnesClassements();
			ArrayList<InformationEssais> tempCircuit = donnesClassements.getCircuitArrayList();
			tempCircuit.add(new InformationEssais("Circuit", clicks, Math.round(tempsEcoule / 1000000000.0)));
			GestionnaireDesDonnes.getInstance().getDonnesClassements().setCircuitArrayList(tempCircuit);

			GestionnaireDesDonnes.getInstance().getDonnesBadges().setBadgeGciruit5(true);

			if (nbCycles >= 3) {
				if (GestionnaireDesDonnes.getInstance().getDonnesCircuit().isModeTriche() == false) {
					System.out.println("3 étoiles");
					GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesCircuit(3);
					menuFinJeu.getBtnSuivant().setEnabled(true);
					menuFinJeu.majEtoilesAffiches(1);

				}

			} else if (nbCycles == 2) {
				if (GestionnaireDesDonnes.getInstance().getDonnesCircuit().isModeTriche() == false) {
					GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesCircuit(2);
					menuFinJeu.getBtnSuivant().setEnabled(true);
					menuFinJeu.majEtoilesAffiches(1);

				}

			} else {
				if (GestionnaireDesDonnes.getInstance().getDonnesCircuit().isModeTriche() == false) {
					GestionnaireDesDonnes.getInstance().getDonnesEtoiles().setNbEtoilesCircuit(1);
					menuFinJeu.getBtnSuivant().setEnabled(true);
					menuFinJeu.majEtoilesAffiches(1);

				}
			}
		}
	}

	/**
	 * Écouteur pour le menu de fin de jeu
	 */
	// Liangchen Liu
	private void menuFinJeuPropChange() {
		menuFinJeu.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "return":
						firePropertyChange("selecNiveau", 0, 1);
						menuFinJeu.setVisible(false);

						break;

					case "recommencer":
						reini();
						classeTemps.demarrer();
						menuFinJeu.setVisible(false);
						break;

					case "suivant":
						if (nbEtoiles>=3) {
							firePropertyChange("suivant", 0, 1);
							menuFinJeu.setVisible(false);
						} else {
								JOptionPane.showMessageDialog(null, "Oops.. On dirait que vous n'avez pas assez d'étoiles pour passez au prochain jeu...", "Erreur",JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "continuer":
						menuFinJeu.setVisible(false);
						focus();
						classeTemps.demarrer();
						break;

				}// fin switch
			}// fin propertyChange
		});

		menuFinJeu.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				classeTemps.demarrer();
				reini();

			}
		});
	}

	/**
	 * Méthode qui détermine si un composant est dans un autre composant.
	 * 
	 * @param fil le fil (composant) à vérifier.
	 * @return true si le composant est dans un autre composant, false sinon.
	 */
	// Ismaïl Boukari
	private boolean isComponentInOtherComponent(Fil fil) {
		fil.generatePositionPoints();
		List<Point> points = fil.getPositionPoints();
		for (Fil wire : fils) {
			if (wire.equals(fil)) {
				continue;
			}
			if(wire.getDestination()!= null && wire.getOrigine()!=null) {
				if (wire.getDestination().equals(fil.getOrigine()) && wire.getOrigine().equals(fil.getDestination())
					|| wire.getDestination().equals(fil.getDestination())&& wire.getOrigine().equals(fil.getOrigine())) {
					return true;
				} 
			}
			for (Point point : points) {
				if (wire.isPointInside(point)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Méthode qui réinitialise le courant de tous les composants.
	 */
	// Ismaïl Boukari
	public void resetCurrents() {
		for (Fil fil : fils) {
			fil.setCourant(0);
		}
	}

	/**
	 * Méthode qui gère les événements "mouseMoved" de la souris (pour le survol des fil).
	 * @param e L'événement de la souris.
	 */
	// Ismaïl Boukari
	public void mouseMovedFilSurvole(MouseEvent e) {
		boolean filTrouve = false;
		for (int i = 0; i < fils.size(); i++) {
			if (fils.get(i).contient(e.getX(), e.getY()) && clicks == 0 && !filTrouve) {
				fils.get(i).setSurvole(true);
				filTrouve = true;
			} else {
				fils.get(i).setSurvole(false);
			}
		}
	}
	
	/**
	 * Dessine l'échelle
	 * @param g le contexte graphique
	 */
	// Ismaïl Boukari
	public void dessinEchelle(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.translate(xMaxSeperator-cellSpacing-35, yMaxSeperator-115);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.black);
		g2d.draw(p);
		g2d.setFont(AppPrincipale7.interBlack.deriveFont(10f));
		g2d.drawString("25 px", 0, -5);
	}
}
