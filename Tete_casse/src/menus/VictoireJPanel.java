package menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import outils.OutilsImage;

/**
 * Classe représentant le paneau de victoire.
 * @author Ismaïl Boukari
 * @auhtor Caroline Houle
 */
public class VictoireJPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private Graphics2D g2d;
	private final Color TRANSPARENT_COLOR = new Color(0,0,0,0);
	private int nbEtoiles = 1;
	private Image imageVictoire;

	/**
	 * Création du paneau
	 * @param nbEtoiles Le nombre d'étoiles à afficher
	 */
	public VictoireJPanel(int nbEtoiles) {
		this.nbEtoiles = nbEtoiles;
		setBackground(TRANSPARENT_COLOR);
		setForeground(TRANSPARENT_COLOR);
		
	}
	
	/**
	 * Méthode de dessin du paneau
	 * @param g Le contexte graphique
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		imageVictoire = lireImage("victoire"+nbEtoiles%4+".gif");

		g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(imageVictoire,0,0, this);
	}
	
	/**
	 * Lit le fichier d'image donne en parametre et retourne
	 * un objet Image correspondant
	 * 
	 * @param nomFichier Le nom du fichier d'image
	 * @return Un objet Image pour cette image
	 */
	// Caroline Houle
	public static Image lireImage(String nomFichier) {
		Image img = null;
		URL urlFichier = OutilsImage.class.getClassLoader().getResource(nomFichier);
		if (urlFichier == null) {
			JOptionPane.showMessageDialog(null,	"Fichier d'image introuvable: " + nomFichier);
		} 
		img = new ImageIcon(urlFichier).getImage();
		return(img);
	}//fin methode

	public void setNbEtoiles(int nbEtoiles) {
		this.nbEtoiles = nbEtoiles;
	}
}
