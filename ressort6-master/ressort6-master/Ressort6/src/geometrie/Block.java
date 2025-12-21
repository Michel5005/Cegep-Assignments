package geometrie;

import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import enumeration.TypeBloc;
import intefaces.Dessinable;
import intefaces.Selectionnable;

/**
 * La classe Block représente un bloc de physique, qui peut être dessiné et
 * sélectionné dans une interface graphique.
 *
 * @author Caroline Houle
 * @author Natael Lavoie
 */
public class Block implements Dessinable, Selectionnable {
	/**
	 * Tableau de BufferedImage utilisé pour stocker les images des différents types
	 * de blocs. Cette variable est statique, ce qui signifie qu'elle est partagée
	 * par toutes les instances de la classe.
	 */
	static BufferedImage images[] = null;
	/** La dimension du bloc, sous forme de vecteur 2D. */
	private Vecteur2D dimension = null;
	/** La géométrie du bloc, sous forme d'un objet Rectangle2D.Double. */
	private Rectangle2D.Double block = null;
	/** La position du bloc, sous forme de vecteur 2D. */
	private Vecteur2D position; // sera specifiee dans le constructeur
	/** Facteur de mise à l'échelle utilisé lors du dessin du bloc. */
	private double pixelsParMetre = 1;
	/** Indique si le bloc est sélectionné ou non. */
	private boolean selectionner;
	/** Décalage de sélection du bloc, sous forme de vecteur 2D. */
	private Vecteur2D selectedOffset = new Vecteur2D();
	/** Le type de bloc, sous forme d'un élément de l'énumération TypeBloc. */
	private TypeBloc typeDeBloc = TypeBloc.NICKEL_ACIER;

	/**
	 * Constructeur ou la position initiale est specifiee par un vecteur La vitesse
	 * et acceleration sont mises � zero.
	 * 
	 * @param position Vecteur incluant les positions en x et y du coin
	 *                 superieur-gauche
	 * @param diametre Diametre (en metres)
	 */
	// Caroline Houle
	public Block(Vecteur2D position, Vecteur2D diametre) {
		if (images == null) {
			InitialiseImage();
		}
		this.position = new Vecteur2D(position); // on m�morise la position
		this.dimension = diametre;
		creerLaGeometrie();
	}

	/**
	 * Permet de creer le block qui seras afficher
	 */
	// Natael Lavoie
	private void creerLaGeometrie() {
		block = new Rectangle2D.Double(-dimension.x / 2.0, -dimension.y / 2.0, dimension.x, dimension.y);
	}

	/**
	 * Permet de dessiner le bloc, sur le contexte graphique passe en parametre.
	 * 
	 * @param g2d contexte graphique
	 */
	// Natael Lavoie
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre, pixelsParMetre);
		mat.translate(position.x, position.y);

		TexturePaint texturePaintBrique = new TexturePaint(images[typeDeBloc.getImageIndex()],
				new Rectangle2D.Double((position.x - dimension.x) * pixelsParMetre, 0 * pixelsParMetre,
						images[typeDeBloc.getImageIndex()].getWidth(), images[typeDeBloc.getImageIndex()].getHeight()));

		g2dPrive.setPaint(texturePaintBrique);

		// g2dPrive.drawImage(images[0], block.getBounds2D());
		g2dPrive.fill(mat.createTransformedShape(block));
	}// fin methode

	/**
	 * Modifie le facteur permettant de passer des metres aux pixels lors du dessin
	 * Ainsi on peut exprimer tout en m, m/s et m/s2
	 * 
	 * @param pixelsParMetre Facteur de mise � l'�chelle lors du dessin
	 */
	// Caroline Houle
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}

	/**
	 * Modifie la position de la balle Note: ici on decide de simplement refaire la
	 * forme sous-jacente!
	 * 
	 * @param pos Vecteur incluant les positions en x et y
	 */
	// Natael Lavoie
	public void setPosition(Vecteur2D pos) {
		// on fait une copie du vecteur passe en param�tre
		this.position.setX(pos.getX());
		this.position.setY(pos.getY());
	}

	/**
	 * Définit la position de l'objet en utilisant les valeurs de xPos et yPos.
	 *
	 * @param xPos la valeur de la coordonnée x de la position
	 * @param yPos la valeur de la coordonnée y de la position
	 */
	// Natael Lavoie
	public void setPosition(double xPos, double yPos) {
		this.position.setX(xPos);
		this.position.setY(yPos);
	}

	/**
	 * Définit la valeur de la coordonnée y de la position de l'objet.
	 *
	 * @param yPos la nouvelle valeur de la coordonnée y de la position
	 */
	// Natael Lavoie
	public void setPositionY(double yPos) {
		this.position.setY(yPos);
	}

	/**
	 * Définit la valeur de la coordonnée x de la position de l'objet.
	 *
	 * @param xPos la nouvelle valeur de la coordonnée x de la position
	 */
	// Natael Lavoie
	public void setPositionX(double xPos) {
		this.position.setX(xPos);
	}

	/**
	 * Retourne la position courante
	 * 
	 * @return la position courante
	 */
	// Caroline Houle
	public Vecteur2D getPosition() {
		return (position);
	}

	/**
	 * Cette méthode vérifie si le bloc contient les coordonnées spécifiées.
	 *
	 * @param xPos La coordonnée x à vérifier.
	 * @param yPos La coordonnée y à vérifier.
	 * @return true si le bloc contient les coordonnées spécifiées, false sinon.
	 */
	// Natael Lavoie
	@Override
	public boolean contient(double xPos, double yPos) {
		return block.contains(xPos - position.x, yPos - position.y);

	}

	/**
	 * Cette méthode vérifie si l'objet est sélectionné.
	 *
	 * @return true si l'objet est sélectionné, false sinon.
	 */
	// Natael Lavoie
	public boolean isSelectionner() {
		return selectionner;
	}

	/**
	 * Cette méthode modifie l'état de sélection de l'objet.
	 *
	 * @param selectionner Le nouvel état de sélection de l'objet (true pour
	 *                     sélectionné, false pour non sélectionné).
	 */
	// Natael Lavoie
	public void setSelectiooner(boolean selectionner) {
		this.selectionner = selectionner;
	}

	/**
	 * Cette méthode retourne les dimensions de l'objet.
	 *
	 * @return Un objet Vecteur2D contenant les dimensions de l'objet (largeur et
	 *         hauteur).
	 */
	// Natael Lavoie
	public Vecteur2D getDimension() {
		return dimension;
	}

	/**
	 * Traite un événement de clic de souris en vérifiant si l'objet est sélectionné
	 * ou non. Si l'objet est sélectionné, enregistre l'offset de sélection.
	 *
	 * @param e l'événement de souris à traiter
	 * @return vrai si l'objet est sélectionné, faux sinon
	 */
	// Natael Lavoie
	public boolean OnMousePressed(MouseEvent e) {
		double xPos = e.getX() / pixelsParMetre;
		double yPos = e.getY() / pixelsParMetre;
		boolean estContenu = contient(xPos, e.getY() / pixelsParMetre);
		selectionner = estContenu;
		if (estContenu) {
			selectedOffset = new Vecteur2D(position.x - xPos, position.y - yPos);
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
	// Natael Lavoie
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
	// Natael Lavoie
	public boolean OnMouseDragged(MouseEvent e) {
		if (selectionner) {
			position.setX(e.getX() / pixelsParMetre + selectedOffset.x);
			position.setY(e.getY() / pixelsParMetre + selectedOffset.y);
		}
		return selectionner;
	}

	/**
	 * Renvoie le type de bloc associé à l'objet.
	 *
	 * @return le type de bloc associé à l'objet
	 */
	// Natael Lavoie
	public TypeBloc getTypeDeBloc() {
		return typeDeBloc;
	}

	/**
	 * Définit le type de bloc associé à l'objet.
	 *
	 * @param typeDeBloc le nouveau type de bloc associé à l'objet
	 */
	// Natael Lavoie
	public void setTypeDeBloc(TypeBloc typeDeBloc) {
		this.typeDeBloc = typeDeBloc;
	}

	/**
	 * Renvoie le coefficient de frottement du type de bloc associé à l'objet.
	 *
	 * @return le coefficient de frottement du type de bloc associé à l'objet
	 */
	// Natael Lavoie
	public double getCoefficientFrotement() {
		return typeDeBloc.getCoefficient();
	}

	/**
	 * 
	 * Initialise les images utilisées pour représenter les différents types de
	 * blocs.
	 */
	// Natael Lavoie
	public void InitialiseImage() {
		images = new BufferedImage[3];
		try {
			images[0] = ImageIO.read(getClass().getClassLoader().getResource("acier.jpg"));
			images[1] = ImageIO.read(getClass().getClassLoader().getResource("fer.jpg"));
			images[2] = ImageIO.read(getClass().getClassLoader().getResource("plomb.jpg"));
		} catch (Exception e) {
			System.out.println("Erreur de lecture du fichier de texture");
		}
	}

}// fin classe
