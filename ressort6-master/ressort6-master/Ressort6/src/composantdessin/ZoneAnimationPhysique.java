package composantdessin;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import enumeration.TypeBloc;
import geometrie.Block;
import geometrie.FeuDeCirculation;
import geometrie.FlecheVectorielle;
import geometrie.Ressort;
import geometrie.Vecteur2D;
import physique.MoteurPhysique;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Classe qui étend la classe JPanel et implémente l'interface Runnable. Permet
 * de créer une scène qui contient un objet en forme de bloc et un ressort qui
 * sont animés selon des lois de la physique.
 * 
 * @author Natael Lavoie
 * @author Caroline Houle
 * @author Nokto Lapointe
 */
public class ZoneAnimationPhysique extends JPanel implements Runnable {
	/** Identifiant de sérialisation de la classe. */
	private static final long serialVersionUID = 1L;
	/** Hauteur du sol en mètres. */
	private static double hateurSol = 0.28;
	/** Largeur du bloc en mètres. */
	private static double largeurBlock = 0.3;
	/** Valeur minimale de la constante de ressort en N/m. */
	private static double constanteRessortMin = 50.0;
	/** Valeur maximale de la constante de ressort en N/m. */
	private static double constanteRessortMax = 800.0;
	/** Valeur par défaut de la constante de ressort en N/m. */
	private static double constanteRessortDefaut = 500.0;
	/** Valeur maximale absolue de l'étirement du ressort en mètres. */
	private static double etirementRessortMaxAbs = 1.36;
	/** Valeur par défaut de l'étirement du ressort en mètres. */
	private static double etirementRessortDefaut = 0.0;
	/** Valeur maximale de la masse d'un bloc en kg. */
	private static double massBlocMax = 4.0;
	/** Valeur minimale de la masse d'un bloc en kg. */
	private static double massBlocMin = 0.1;
	/** Valeur par défaut de la masse d'un bloc en kg. */
	private static double massBlocDefaut = 0.7;
	/** Valeur maximale du pas de simulation en s. */
	private static double pasDeSimulationMax = 10.0;
	/** Valeur minimale du pas de simulation en s. */
	private static double pasDeSimulationMin = 3.0;
	/** Saut minimal de la position d'un bloc en mètres. */
	private static final double SAUT_DE_BLOC = 0.01;
	/** Type de bloc initial utilisé dans la simulation */
	private static final TypeBloc TYPE_BLOC_INITIAL = TypeBloc.NICKEL_ACIER;
	/** Pas de simulation initial utilisé dans la simulation */
	private static final double DELTA_T_INITIAL = 0.008;
	/** Valeur d'étirement du ressort utilisée pour les tests */
	private static final double ETIREMENT_VALEUR_TEST = 0.8;
	/** Objet utilisé pour gérer les notifications de changement de propriété */
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	/** Position finale de réinitialisation utilisée dans la simulation */
	private final double POSITION_FINALE_REINITIALISATION = 0.001;
	/** Vitesse finale de réinitialisation utilisée dans la simulation */
	private double vitesseFinaleReinitialisation = 0.01;
	/** Largeur du feu de circulation utilisée dans la simulation */
	private double largeurFeuCircu = 200;
	/** Masse du bloc en kg. */
	private double masse = massBlocDefaut;
	/** Pas de simulation en s. */
	private double deltaT = 0.008;
	/** Largeur du composant en mètres. */
	private double largeurDuComposantEnMetres = 3;
	/** Hauteur du composant en mètres. */
	private double hauteurDuComposantEnMetres;
	/** Constante de ressort en N/m. */
	private double k = constanteRessortDefaut;

	private FeuDeCirculation feuDeCirculation;
	/** Bloc principal de l'animation. */
	private Block blockPrincipale;
	/** Ressort de l'animation. */
	private Ressort ressort;
	/** Sol de l'animation. */
	private Rectangle2D.Double ground = new Rectangle2D.Double();
	/** Flèche vectorielle de l'animation. */
	private FlecheVectorielle fleche = null;

	/** Vecteur nul. */
	private Vecteur2D vecZero = new Vecteur2D(0, 0);
	/** Vitesse initiale du bloc en m/s. */
	private Vecteur2D vitesseInitiale = new Vecteur2D(-0.0000001, 0);

	/** Position initiale du bloc en mètres. */
	private Vecteur2D posInitBloc = new Vecteur2D(0, 0);
	/** Position actuelle du bloc en mètres. */
	private Vecteur2D pos = new Vecteur2D(0, 0);
	/** Force exercée par le ressort en N. */
	private Vecteur2D fRessort = new Vecteur2D(0, 0);
	/** Force totale exercée sur le bloc en N. */
	private Vecteur2D fTotale = new Vecteur2D(0, 0);
	/** Force de frottement exercée sur le bloc en N. */
	private Vecteur2D fFrottement = new Vecteur2D(0, 0);
	/** Position du bloc selon la ligne en mètres. */
	private Vecteur2D posSelonLaLigne = new Vecteur2D(0, 0);
	/** Vitesse actuelle du bloc en m/s. */
	private Vecteur2D vit = new Vecteur2D(-0.0000001, 0);
	/** Accélération actuelle du bloc en m/s². */
	private Vecteur2D acc = new Vecteur2D(0, 0);
	/** Gravité en N. */
	private Vecteur2D grav = new Vecteur2D(MoteurPhysique.calculForceGrav(masse));
	/** Vecteur normal en N. */
	private Vecteur2D norm = new Vecteur2D(MoteurPhysique.calculForceGrav(-masse));

	/** Indique si l'animation est en cours d'exécution. */
	private boolean enCoursDAnimation = false;
	/** Temps total écoulé depuis le début de l'animation en s. */
	private double tempsTotalEcoule = 0;
	/** Durée d'un cycle de l'animation en ms. */
	private int tempsDuSleep = 25;
	/** Nombre de pixels par mètre. */
	private double pixelsParMetre;
	/** Indique si c'est la première fois que l'animation est exécutée. */
	private boolean premiereFois = true;

	private HashMap<String, Vecteur2D> proprietes = new HashMap<String, Vecteur2D>();

	/**
	 * Cree une scene qui contient (pour l'instant) une seule balle au repos
	 */
	// Natael Lavoie
	public ZoneAnimationPhysique() {
		proprietes.put("grav", grav);
		proprietes.put("norm", norm);
		proprietes.put("fRessort", fRessort);
		proprietes.put("fFrottement", fFrottement);
		proprietes.put("fTotale", fTotale);
		proprietes.put("acc", acc);
		proprietes.put("vit", vit);
		proprietes.put("posSelonLaLigne", posSelonLaLigne);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!enCoursDAnimation) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_RIGHT:
						changerEtirementsRessort(
								Math.min(largeurDuComposantEnMetres - ressort.getLongueurNat() - largeurBlock,
										ressort.getE() + SAUT_DE_BLOC));
						break;
					case KeyEvent.VK_LEFT:
						changerEtirementsRessort(Math.max(-ressort.getLongueurNat(), ressort.getE() - SAUT_DE_BLOC));
						break;
					default:
					}
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!feuDeCirculation.OnMousePressed(e) && blockPrincipale.OnMousePressed(e)) {

				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				blockPrincipale.OnMouseReleased(e);
				feuDeCirculation.OnMouseReleased(e);
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				double yPos = blockPrincipale.getPosition().getY();
				if (blockPrincipale.OnMouseDragged(e)) {
					blockPrincipale.setPositionY(yPos);
					double xPos = blockPrincipale.getPosition().getX();
					double clampedXPos = Math.max(largeurBlock / 2.0,
							Math.min(largeurDuComposantEnMetres - largeurBlock / 2.0, xPos));
					arreter();
					tempsTotalEcoule = 0.0;
					pos.setComposantes(
							new Vecteur2D(largeurDuComposantEnMetres / 2.0, hauteurDuComposantEnMetres / 2.0));
					vit.setComposantes(vitesseInitiale);
					changerEtirementsRessort(clampedXPos - largeurBlock / 2.0 - ressort.getLongueurNat());
				}
				if (feuDeCirculation.OnMouseDragged(e)) {
					repaint();
				}
			}
		});
		setBackground(Color.lightGray);

	}// fin du constructeur

	/**
	 * Permet de dessiner une scene qui inclut ici une simple balle en mouvement
	 * 
	 * @param g Contexte graphique
	 */
	// Natael Lavoie
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (premiereFois) {
			pixelsParMetre = getWidth() / largeurDuComposantEnMetres;
			hauteurDuComposantEnMetres = getHeight() / pixelsParMetre;
			reinitialiser();
			ground.setRect(0, getHeight() - hateurSol * pixelsParMetre, getWidth(), hateurSol * pixelsParMetre);
			premiereFois = false;
		}

		g2d.fill(ground);

		blockPrincipale.dessiner(g2d);
		g2d.setColor(Color.red);
		ressort.dessiner(g2d);

		feuDeCirculation.dessiner(g2d);

		if (vit.module() > vitesseInitiale.module())
			fleche.dessiner(g2d);

		// Appliquez le style de trait pointillé à l'objet Graphics2D
		g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[] { 10 }, 0));

		// Dessinez une ligne en utilisant l'objet Graphics2D
		g2d.draw(new Line2D.Double(getWidth() / 2, getHeight(), getWidth() / 2, 0.0));

	}// fin paintComponent

	/**
	 * Animation du block et du ressort
	 */
	// Nokto Lapointe
	@Override
	public void run() {
		while (enCoursDAnimation) {
			prochaineImage();
			feuDeCirculation.setCouleur(2);
			try {
				Thread.sleep(tempsDuSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();

			} // fin while
		}
		if (posSelonLaLigne.getX() == 0) {
			feuDeCirculation.setCouleur(0);
		} else {
			feuDeCirculation.setCouleur(1);
		}

		repaint();
		System.out.println("Le thread est mort...!");
	}

	/**
	 * Calcul des nouvelles positions pour tous les objets de la sc�ne
	 * 
	 * @throws Exception si la masse est nulle ou non
	 */
	// Nokto Lapointe
	private void calculerUneIterationPhysique(double deltaT) throws Exception {
		tempsTotalEcoule += deltaT;

		fFrottement = MoteurPhysique.calculForceFrottement(blockPrincipale.getCoefficientFrotement(), masse, vit);
		fRessort = MoteurPhysique.calculForceRessort(posSelonLaLigne.getX(), k);
		fTotale = new Vecteur2D(fFrottement.additionne(fRessort));
		acc = MoteurPhysique.calculAcceleration(fTotale, masse);
		vit = MoteurPhysique.calculVitesse(deltaT, vit, acc);
		posSelonLaLigne = MoteurPhysique.calculPosition(deltaT, posSelonLaLigne, vit);
		proprietes.put("fRessort", fRessort);
		proprietes.put("fFrottement", fFrottement);
		proprietes.put("fTotale", fTotale);
		proprietes.put("acc", acc);
		proprietes.put("vit", vit);
		proprietes.put("posSelonLaLigne", posSelonLaLigne);
		pos = posInitBloc.additionne(posSelonLaLigne);
		blockPrincipale.setPosition(pos);
		ressort.setLongueur(pos.getX() - blockPrincipale.getDimension().getX() / 2.0);
		Vecteur2D force = posInitBloc.soustrait(blockPrincipale.getPosition());
		fleche = new FlecheVectorielle(blockPrincipale.getPosition(), force);
		fleche.setPixelsParMetre(pixelsParMetre);
		fleche.setLongueurTraitDeTete(force.module() / 10.0);
		pcs.firePropertyChange("tempsTotalEcoule", -1, tempsTotalEcoule);
	}

	/**
	 * Demarre le thread s'il n'est pas deja demarre
	 */
	// Caroline Houle
	public void demarrer() {
		if (!enCoursDAnimation) {
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
		}
	}// fin methode

	/**
	 * Demande l'arret du thread (prochain tour de boucle)
	 */
	// Nokto Lapointe
	public void arreter() {
		enCoursDAnimation = false;
		pcs.firePropertyChange("arret", -1, 1);
		repaint();
	}// fin methode

	/**
	 * Reinitialise la position et la vitesse de la balle
	 */
	// Natael Lavoie
	public void reinitialiser() {
		arreter();
		tempsTotalEcoule = 0.0;
		posInitBloc.setComposantes(new Vecteur2D(largeurDuComposantEnMetres / 2.0, hauteurDuComposantEnMetres / 2.0));
		pos.setComposantes(new Vecteur2D(largeurDuComposantEnMetres / 2.0, hauteurDuComposantEnMetres / 2.0));
		blockPrincipale = new Block(new Vecteur2D(posInitBloc), new Vecteur2D(largeurBlock, largeurBlock));
		blockPrincipale.setPixelsParMetre(pixelsParMetre);

		feuDeCirculation = new FeuDeCirculation(getWidth() - largeurFeuCircu, 0);

		ressort = new Ressort(hauteurDuComposantEnMetres - hateurSol,
				largeurDuComposantEnMetres / 2.0 - blockPrincipale.getDimension().getX() / 2.0,

				largeurDuComposantEnMetres / 2.0 - blockPrincipale.getDimension().getX() / 2.0, pixelsParMetre, 0.2);
		vit.setComposantes(vitesseInitiale);
		fleche = new FlecheVectorielle(posInitBloc.getX(), posInitBloc.getY(), vitesseInitiale);
		fleche.setPixelsParMetre(pixelsParMetre);
		fleche.setLongueurTraitDeTete(vitesseInitiale.getX() / 10.0);
		fRessort = new Vecteur2D(0, 0);
		fTotale = new Vecteur2D(0, 0);
		fFrottement = new Vecteur2D(0, 0);
		posSelonLaLigne = new Vecteur2D(0, 0);
		vit.setX(vitesseInitiale.getX());
		acc = new Vecteur2D(0, 0);
		masse = massBlocDefaut;
		deltaT = DELTA_T_INITIAL;
		k = constanteRessortDefaut;
		blockPrincipale.setTypeDeBloc(TYPE_BLOC_INITIAL);
		repaint();
	}

	/**
	 * Avance la simulation d'une unique image
	 */
	// Nokto Lapointe
	public void prochaineImage() {

		if ((Math.abs(posSelonLaLigne.getX()) > POSITION_FINALE_REINITIALISATION)
				|| Math.abs(vit.getX()) > vitesseFinaleReinitialisation) {
			try {

				calculerUneIterationPhysique(deltaT);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			repaint();
		} else {
			fRessort = new Vecteur2D(0, 0);
			fTotale = new Vecteur2D(0, 0);
			fFrottement = new Vecteur2D(0, 0);
			posSelonLaLigne = new Vecteur2D(0, 0);
			vit.setX(vitesseInitiale.getX());
			acc = new Vecteur2D(0, 0);
			proprietes.put("fRessort", fRessort);
			proprietes.put("fFrottement", fFrottement);
			proprietes.put("fTotale", fTotale);
			proprietes.put("acc", acc);
			proprietes.put("vit", vit);
			proprietes.put("posSelonLaLigne", posSelonLaLigne);
			ressort.setE(0);
			arreter();

		}
		pcs.firePropertyChange("proprietes", null, proprietes);
		pcs.firePropertyChange("etirements", null, ressort.getE());
	}

	/**
	 * Retourne le temps de sleep actuel
	 * 
	 * @return temps du sleep actuel
	 */
	// Caroline Houle
	public int getTempsDuSleep() {
		return tempsDuSleep;
	}

	/**
	 * Définit le type de bloc principal de la zone d'animation physique.
	 *
	 * @param type le nouveau type de bloc à utiliser
	 */
	// Natael Lavoie
	public void setTypeDeBloc(TypeBloc type) {
		if (blockPrincipale != null) {
			blockPrincipale.setTypeDeBloc(type);
			repaint();
		}
	}

	/**
	 * Ajoute un écouteur de changement de propriété à la liste des écouteurs de
	 * changement de propriété.
	 *
	 * @param listener l'écouteur de changement de propriété à ajouter
	 */
	// Natael Lavoie
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	/**
	 * Modifie les étirements du ressort et met à jour les propriétés associées.
	 *
	 * @param etirements les nouveaux étirements du ressort
	 */
	// Natael Lavoie
	public void changerEtirementsRessort(double etirements) {
		blockPrincipale.setPositionX(ressort.getLongueurNat() + etirements + largeurBlock / 2.0);
		double etirement = etirements;
		ressort.setLongueur(ressort.getLongueurNat() + etirement);
		pcs.firePropertyChange("etirements", null, etirement);
		posSelonLaLigne.setX(etirement);
		if (etirement != 0) {
			feuDeCirculation.setCouleur(1);
		} else {
			feuDeCirculation.setCouleur(0);
		}
		repaint();
	}

	/**
	 * Renvoie la valeur de la constante de ressort minimale.
	 *
	 * @return la constante de ressort minimale
	 */
	// Natael Lavoie
	public static double getConstanteRessortMin() {
		return constanteRessortMin;
	}

	/**
	 * Renvoie la valeur de la constante de ressort maximale.
	 *
	 * @return la constante de ressort maximale
	 */
	// Natael Lavoie
	public static double getConstanteRessortMax() {
		return constanteRessortMax;
	}

	/**
	 * Renvoie la valeur de la constante de ressort par défaut.
	 *
	 * @return la constante de ressort par défaut
	 */
	// Natael Lavoie
	public static double getConstanteRessortDefaut() {
		return constanteRessortDefaut;
	}

	/**
	 * Renvoie la valeur maximale absolue de l'étirement du ressort.
	 *
	 * @return la valeur maximale absolue de l'étirement du ressort
	 */
	// Natael Lavoie
	public static double getEtirementRessortMaxAbs() {
		return etirementRessortMaxAbs;
	}

	/**
	 * Renvoie la valeur par défaut de l'étirement du ressort.
	 *
	 * @return la valeur par défaut de l'étirement du ressort
	 */
	// Natael Lavoie
	public static double getEtirementRessortDefaut() {
		return etirementRessortDefaut;
	}

	/**
	 * Renvoie la masse maximale du bloc.
	 *
	 * @return la masse maximale du bloc
	 */
	// Natael Lavoie
	public static double getMassBlocMax() {
		return massBlocMax;
	}

	/**
	 * Renvoie la masse minimale du bloc.
	 *
	 * @return la masse minimale du bloc
	 */
	// Natael Lavoie
	public static double getMassBlocMin() {
		return massBlocMin;
	}

	/**
	 * Renvoie la valeur maximale du pas de simulation.
	 *
	 * @return la valeur maximale du pas de simulation
	 */
	// Natael Lavoie
	public static double getPasDeSimulationMax() {
		return pasDeSimulationMax;
	}

	/**
	 * Renvoie la valeur minimale du pas de simulation.
	 *
	 * @return la valeur minimale du pas de simulation
	 */
	// Natael Lavoie
	public static double getPasDeSimulationMin() {
		return pasDeSimulationMin;
	}

	/**
	 * Méthode permettant de définir la valeur de l'intervalle de temps deltaT.
	 * 
	 * @param deltaT la valeur de l'intervalle de temps à définir
	 */
	// Nokto Lapointe
	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}

	/**
	 * Méthode permettant de définir la valeur de la constante k en N/m.
	 * 
	 * @param k la valeur de la constante k à définir
	 */
	// Nokto Lapointe
	public void setK(double k) {
		this.k = k;
	}

	/**
	 * Renvoie la valeur de sautDeBlock.
	 *
	 * @return la valeur de sautDeBlock
	 */
	// Natael Lavoie
	public static double getSautDeBlock() {
		return SAUT_DE_BLOC;
	}

	/**
	 * Renvoie la valeur de massBlocDefaut.
	 *
	 * @return la valeur de massBlocDefaut
	 */
	// Natael Lavoie
	public static double getMassBlocDefaut() {
		return massBlocDefaut;
	}

	/**
	 * Retourne la masse du bloc en kg.
	 * 
	 * @return Masse du bloc en kg.
	 */
	//Nokto Lapointe
	public double getMasse() {
		return masse;
	}

	/**
	 * Modifie la masse du bloc en kg.
	 * 
	 * @param masse Nouvelle masse du bloc en kg.
	 */
	//Nokto Lapointe
	public void setMasse(double masse) {
		this.masse = masse;
	}

	/**
	 * Réinitialise l'objet et change l'étirement du ressort à la valeur de test et
	 * le met à jour l'affichage avec la methode repaint()
	 */
	// Nokto Lapointe
	public void toValeursTests() {
		reinitialiser();
		changerEtirementsRessort(ETIREMENT_VALEUR_TEST);
		repaint();

	}

	/**
	 * Retourne la valeur par défaut du pas de simulation.
	 * 
	 * @return la valeur par défaut du pas de simulation
	 */
	public static double defaultPasDeSimulation() {
		return DELTA_T_INITIAL;
	}

}// fin classe