package selecteur_niveau;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GestionDesFichiers.GestionnaireDesDonnes;
import outils.OutilsImage;
import javax.swing.SwingConstants;
/**
 * 
 * Classe gérant la sélection  un niveau.
 * 
 * @author Ismaïl Boukari
 *
 */
public class SelecteurDeNiveau extends JPanel {
	
	/**
	 * Contexte graphique
	 */
	private Graphics2D g2d;
	/**
	 * 
	 */
	private boolean premiereFois = true;
	/**
	 * Largeur du rectangle sélectionnable (un pour chaque jeux)
	 */
	private int largeurDuRectangle = 425;
	/**
	 * Hauteur du rectangle sélectionnable (un pour chaque jeux)
	 */
	private int hauteurDuRectangle = 275;
	/**
	 * Espacement horizontal entre chaque rectangle sélectionnable
	 */
	private int espacementHorizontalRectangle = (1300-2*largeurDuRectangle)/3;
	/**
	 * Espacement vertical entre chaque rectangle sélectionnable
	 */
	private int espacementVerticalRectangle = (700-2*hauteurDuRectangle)/4;
	/**
	 * Rectangle représentant le jeu "Circuit"
	 */
	private RectangleNiveau rectangleCircuit;
	/**
	 * Rectangle représentant le jeu "Curling"
	 */
	private RectangleNiveau rectangleCurling;
	/**
	 * Rectangle représentant le jeu "Peche"
	 */
	private RectangleNiveau rectanglePeche;
	/**
	 * Rectangle représentant le jeu "Tank"
	 */
	private RectangleNiveau rectangleTank;
	/**
	 * Étiquette représentant l'image "étoile"
	 */
	private JLabel lblImageEtoiles;
	/**
	 * Étiquette représentant le nombre d'étoiles
	 */
	private JLabel lblNbEtoiles;
	/**
	 * Icone de l'étoile
	 */
	private ImageIcon iconeEtoile;
	/**
	 * Icone du "return"
	 */
	private ImageIcon iconeReturn;
	/**
	 * Icone du "return"
	 */
	private JButton btnReturn;
	/**
	 * Image du jeu "Tank".
	 */
	private Image imageTank;
	/**
	 * Image du jeu "Circuit".
	 */
	private Image imageCircuit;
	/**
	 * Image du jeu "Curling".
	 */
	private Image imageCurling;
	/**
	 * Image du jeu "Peche".
	 */
	private Image imagePeche;
	
	/**
	 * Image du jeu "Tank" lorsqu'il n'est pas sélectionné.
	 */
	private Image imageTankNonSelectionne;
	/**
	 * Image du jeu "Tank" lorsqu'il est sélectionné.
	 */
	private Image imageTankSelectionne;
	/**
	 * Image d'un jeu non débloqué.
	 */
	private Image imageLevelLocked;
	/**
	 * Image d'un jeu non débloqué qui est sélectionné.
	 */
	private Image imageLevelLockedSelected;
	
	private DonnesEtoiles donneesEtoiles;

	/**
	 * Creation du panneau.
	 */
	public SelecteurDeNiveau() {
		donneesEtoiles = GestionnaireDesDonnes.getInstance().getDonnesEtoiles();
		int nbEtoiles = donneesEtoiles.getNbEtoiles();
		setLayout(null);
		
		lblNbEtoiles = new JLabel(nbEtoiles+"/12");
		lblNbEtoiles.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNbEtoiles.setForeground(new Color(10, 10, 10));
		lblNbEtoiles.setFont(new Font("Arial Black", Font.PLAIN, 25));
		lblNbEtoiles.setBounds(1084, 25, 131, 46);
		add(lblNbEtoiles);
		
		lblImageEtoiles = new JLabel("New label");
		lblImageEtoiles.setBounds(1219, 25, 46, 46);
		iconeEtoile = new ImageIcon(OutilsImage.lireImageEtRedimensionner("star.png", 46,46));
		lblImageEtoiles.setIcon(iconeEtoile);
		add(lblImageEtoiles);
		
		
		btnReturn = new JButton("");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firePropertyChange("menuAccueil",1,-1);
			}//fin methode
		});//fin actionListener
		btnReturn.setBounds(40, 25, 45, 45);
		iconeReturn = new ImageIcon(OutilsImage.lireImageEtRedimensionner("returndark.png", 46,46));
		btnReturn.setIcon(iconeReturn);
		btnReturn.setBackground(new Color(0,0,0,0));
		btnReturn.setOpaque(false);
		btnReturn.setBorder(null);
		add(btnReturn);
		
		rectangleCircuit = new RectangleNiveau(largeurDuRectangle, hauteurDuRectangle);
		rectangleCurling = new RectangleNiveau(largeurDuRectangle, hauteurDuRectangle);
		rectanglePeche = new RectangleNiveau(largeurDuRectangle, hauteurDuRectangle);
		rectangleTank = new RectangleNiveau(largeurDuRectangle, hauteurDuRectangle);
		initialiserLesImages();
		addMouseListeners();
		
	}//fin constructeur
	
	/**
	 * On redéfini la méthode de dessin
	 * @param g Contexte graphique
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
    	g2d = (Graphics2D) g;
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
	        RenderingHints.VALUE_RENDER_QUALITY);
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	        RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    if (premiereFois) {
			
			rectangleCircuit.setPosition(espacementHorizontalRectangle+50, espacementVerticalRectangle);
			rectangleCurling.setPosition(2*espacementHorizontalRectangle+largeurDuRectangle-50, espacementVerticalRectangle);
			rectanglePeche.setPosition(espacementHorizontalRectangle+50, 2*espacementVerticalRectangle+hauteurDuRectangle);
			rectangleTank.setPosition(2*espacementHorizontalRectangle+largeurDuRectangle-50, 2*espacementVerticalRectangle+hauteurDuRectangle);		
			
			rectangleCircuit.creationDeLaGeometrie();
			rectangleCurling.creationDeLaGeometrie();
			rectanglePeche.creationDeLaGeometrie();
			rectangleTank.creationDeLaGeometrie();
			premiereFois = false;
	    }//fin if
	    g2d.drawImage(imageCircuit,espacementHorizontalRectangle+50, espacementVerticalRectangle, null);
	    g2d.drawImage(imageCurling, 2*espacementHorizontalRectangle+largeurDuRectangle-50, espacementVerticalRectangle, null);
	    g2d.drawImage(imagePeche,espacementHorizontalRectangle+50, 2*espacementVerticalRectangle+hauteurDuRectangle, null);
		g2d.drawImage(imageTank,2*espacementHorizontalRectangle+largeurDuRectangle-50,2*espacementVerticalRectangle+hauteurDuRectangle,null);
		
		
	    
	}//fin paintComponent
	

	/**
	 * Méthode permettant de faire la mise à jour du nombre d'étoiles
	 */
	public void majNbEtoiles() {
		int nbEtoiles = GestionnaireDesDonnes.getInstance().getDonnesEtoiles().getNbEtoiles();
		
		lblNbEtoiles.setText(nbEtoiles + "/12");

	}//fin methode

	/**
	 * Méthode initialisant les images
	 */
	public void initialiserLesImages() {
		
		imageTank = OutilsImage.lireImage("selecteur_de_niveau/Tank/tank0.png");
		imageCircuit =  OutilsImage.lireImage("selecteur_de_niveau/Circuit/circuit0.png");;
		imageCurling =  OutilsImage.lireImage("selecteur_de_niveau/Curling/curling0.png");;
		imagePeche =  OutilsImage.lireImage("selecteur_de_niveau/Poisson/poisson0.png");;
		

	}//fin methode
	
	/**
	 * Actualise l'affichage des étoiles sur les niveaux.
	 */
	public void actualiserLesEtoiles() {
		donneesEtoiles = GestionnaireDesDonnes.getInstance().getDonnesEtoiles();
		imageTank = OutilsImage.lireImage("selecteur_de_niveau/Tank/tank"+donneesEtoiles.getNbEtoilesTank()+".png");
		imageCircuit =  OutilsImage.lireImage("selecteur_de_niveau/Circuit/circuit"+donneesEtoiles.getNbEtoilesCircuit()+".png");;
		imageCurling =  OutilsImage.lireImage("selecteur_de_niveau/Curling/curling"+donneesEtoiles.getNbEtoilesCurling()+".png");;
		imagePeche =  OutilsImage.lireImage("selecteur_de_niveau/Poisson/poisson"+donneesEtoiles.getNbEtoilesPeche()+".png");;
		repaint();
	}
	
	/**
	 * Méthode ajoutant les écouteurs de souris
	 * 
	 */
	public void addMouseListeners() {
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				donneesEtoiles = GestionnaireDesDonnes.getInstance().getDonnesEtoiles();
				int nbEtoiles = donneesEtoiles.getNbEtoiles();
				if (rectangleCircuit.contient(e.getX(), e.getY())) {
					firePropertyChange("circuit",1,-1);
				}//fin if
				if (rectangleCurling.contient(e.getX(), e.getY())) {
					if (nbEtoiles >= 3) {
						firePropertyChange("curling",1,-1);
					} else
					JOptionPane.showMessageDialog(null, "Oops.. On dirait que vous n'avez pas assez d'étoiles...", "Erreur",JOptionPane.ERROR_MESSAGE);
				}//fin if
				if (rectanglePeche.contient(e.getX(), e.getY())) {
					if (nbEtoiles >= 6) {
						firePropertyChange("peche",1,-1);
					} else
					JOptionPane.showMessageDialog(null, "Oops.. On dirait que vous n'avez pas assez d'étoiles...", "Erreur",JOptionPane.ERROR_MESSAGE);
				}//fin if	
				if (rectangleTank.contient(e.getX(), e.getY())) {
					if (nbEtoiles >= 6) {
						firePropertyChange("tank",1,-1);
					} else
					JOptionPane.showMessageDialog(null, "Oops.. On dirait que vous n'avez pas assez d'étoiles...", "Erreur",JOptionPane.ERROR_MESSAGE);
				}//fin if
			}//fin methode
		});//fin addMouseListener
//		addMouseMotionListener(new MouseMotionAdapter() {
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				if (rectangleCircuit.contient(e.getX(), e.getY())) {
//					if (imageCircuit.equals(imageLevelLocked)) {
//						imageCircuit = imageLevelLockedSelected;
//						repaint();
//					} else {
//						// a faire
//					}//fin if else
//				} else {
//					if (imageCircuit.equals(imageLevelLockedSelected)) {
//						imageCircuit = imageLevelLocked;
//						repaint();
//					}//fin if
//				}//fin if else
//				if (rectangleCurling.contient(e.getX(), e.getY())) {
//					if (imageCurling.equals(imageLevelLocked)) {
//						imageCurling = imageLevelLockedSelected;
//						repaint();
//					} else {
//						// a faire
//					}//fin if else
//				} else {
//					if (imageCurling.equals(imageLevelLockedSelected)) {
//						imageCurling = imageLevelLocked;
//						repaint();
//					}//fin if
//				}//fin if else
//				
//				if (rectanglePeche.contient(e.getX(), e.getY())) {
//					if (imagePeche.equals(imageLevelLocked)) {
//						imagePeche = imageLevelLockedSelected;
//						repaint();
//					} else {
//						// a faire
//					}//fin if else
//				} else {
//					if (imagePeche.equals(imageLevelLockedSelected)) {
//						imagePeche = imageLevelLocked;
//						repaint();
//					}//fin if
//				}//fin if else
//				
//				if (rectangleTank.contient(e.getX(), e.getY())) {
//					if (imageTank.equals(imageLevelLocked)) {
//						imageTank = imageLevelLockedSelected;
//						repaint();
//					} else {
//						imageTank = imageTankSelectionne;
//						repaint();
//					}//fin if else
//				} else {
//					imageTank = imageTankNonSelectionne;
//					repaint();
//				}//fin if else
//			}
//		});//fin addMouseMotionListener
	}//fin methode
}//fin classe
