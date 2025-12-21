package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import intefaces.Dessinable;
import intefaces.Selectionnable;

/**
 * La classe FeuDeCirculation est une implémentation de l'interface Dessinable
 * qui permet de dessiner un feu de circulation sur un objet Graphics2D. Elle
 * implémente également l'interface Selectionnable qui permet de sélectionner
 * l'objet et de déplacer ses coordonnées en fonction de l'offset de sélection.
 * 
 * @author Michel Vuu
 *
 */
public class FeuDeCirculation implements Dessinable, Selectionnable {
	/** le diamètre des cercles du feu de circulation (55 par défaut) */
	private double diametre = 55;
	/** les coordonnées du coin supérieur gauche du feu de circulation */
	private double x, y;
	/** la largeur du feu de circulation */
	private double largeur = 200;
	/** la hauteur du feu de circulation */
	private double hauteur = 65;
	/**
	 * un objet de type Rectangle2D.Double qui représente la forme du feu de
	 * circulation
	 */
	private Rectangle2D.Double rect;
	/**
	 * un ArrayList d'objets de type Ellipse2D.Double qui représentent les cercles
	 * du feu de circulation
	 */
	private ArrayList<Ellipse2D.Double> tableauCercle;
	/**
	 * l'indice de la couleur actuelle du feu de circulation (0 pour rouge, 1 pour
	 * jaune, 2 pour vert)
	 */
	private int couleur = 0;
	/**
	 * un objet de type Vecteur2D qui représente l'offset de sélection de l'objet
	 */
	private Vecteur2D selectedOffset = new Vecteur2D();
	/** un booléen qui indique si l'objet est sélectionné ou non */
	private boolean selectionner;

	/**
	 * Constructeur
	 * 
	 * @param x       Coordonnée du coin supérieur gauche en x
	 * @param y       Coordonnée du coin supérieur gauche en y
	 * @param largeur Largeur du feu de circulation
	 * @param hauteur Hauteur du feu de circulation
	 */
	public FeuDeCirculation(double x, double y, double largeur, double hauteur) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		creerLaGeometrie();
	}

	/**
	 * Constructeur (les champs 'largeur' et 'hauteur' sont initialisés à des
	 * valeurs par défaut)
	 * 
	 * @param x
	 * @param y
	 */
	public FeuDeCirculation(double x, double y) {
		this.x = x;
		this.y = y;

		creerLaGeometrie();
	}

	/**
	 * Crée la forme du feu de circulation et les cercles de couleur en fonction des
	 * champs de la classe
	 */
	private void creerLaGeometrie() {
		double cst = 9;
		rect = new Rectangle2D.Double(x, y, largeur, hauteur);
		tableauCercle = new ArrayList<Ellipse2D.Double>();
		for (int i = 0; i < 3; i++) {
			tableauCercle.add(new Ellipse2D.Double(x + (diametre + cst) * i + cst, y + (hauteur / 2.0 - diametre / 2.0),
					diametre, diametre));

		}
	}

	/**
	 * Cette méthode vérifie si le bloc contient les coordonnées spécifiées.
	 * 
	 * @param xPos La coordonnée x à vérifier.
	 * @param yPos La coordonnée y à vérifier.
	 * @return true si le feu de circulation contient les coordonnées spécifiées,
	 *         false sinon.
	 */
	@Override
	public boolean contient(double xPos, double yPos) {
		return rect.contains(xPos, yPos);
	}

	/**
	 * Permet de dessiner le feu de circulation, sur le contexte graphique passe en
	 * parametre.
	 * 
	 * @param g2d le contexte graphique
	 */
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie = (Graphics2D) g2d.create();
		g2dCopie.setColor(Color.black);
		g2dCopie.draw(rect);

		if (couleur == 0) {
			g2dCopie.setColor(Color.red);
		} else {
			g2dCopie.setColor(Color.gray);
		}
		g2dCopie.fill(tableauCercle.get(0));
		if (couleur == 1) {
			g2dCopie.setColor(Color.yellow);
		} else {
			g2dCopie.setColor(Color.gray);
		}
		g2dCopie.fill(tableauCercle.get(1));
		if (couleur == 2) {
			g2dCopie.setColor(Color.green);
		} else {
			g2dCopie.setColor(Color.gray);
		}
		g2dCopie.fill(tableauCercle.get(2));

	}

	/**
	 * Cette méthode modifie la couleur du feu de circulation
	 * 
	 * @param couleur La couleur du feu de circulation
	 */
	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	/**
	 * Traite un événement de clic de souris en vérifiant si l'objet est sélectionné
	 * ou non. Si l'objet est sélectionné, enregistre l'offset de sélection.
	 *
	 * @param e l'événement de souris à traiter
	 * @return vrai si l'objet est sélectionné, faux sinon
	 */
	public boolean OnMousePressed(MouseEvent e) {
		double xPos = e.getX();
		double yPos = e.getY();
		boolean estContenu = contient(xPos, e.getY());
		selectionner = estContenu;
		if (estContenu) {
			selectedOffset = new Vecteur2D(x - xPos, y - yPos);
		}
		return estContenu;
	}

	/**
	 * Traite un événement de relâchement de souris en désélectionnant l'objet s'il
	 * était sélectionné.
	 *
	 * @param e l'événement de souris à traiter
	 * @return vrai si l'objet a été désélectionné, faux sinon
	 */
	public boolean OnMouseReleased(MouseEvent e) {
		selectedOffset.setX(0.0);
		selectedOffset.setY(0.0);

		boolean wasSelected = selectionner;
		selectionner = false;
		return wasSelected != selectionner;
	}

	/**
	 * Traite un événement de déplacement de souris en déplaçant l'objet si celui-ci
	 * est sélectionné.
	 *
	 * @param e l'événement de souris à traiter
	 * @return vrai si l'objet est sélectionné, faux sinon
	 */
	public boolean OnMouseDragged(MouseEvent e) {
		if (selectionner) {
			x = (e.getX() + selectedOffset.x);
			y = (e.getY() + selectedOffset.y);
			creerLaGeometrie();
		}
		return selectionner;
	}

}
