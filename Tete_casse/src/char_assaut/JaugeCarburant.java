package char_assaut;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.NavigableMap;

import interfaces.Dessinable;

/**
 * Classe qui crée un objet de type jauge de carburant pour le char d'assaut
 * @author Jason Yin
 */
public class JaugeCarburant implements Dessinable{
	
	private int nbCarburant;
	private int nbCarburantMax;
	private int xJauge;
	private int yJauge;
    private int tailleCadre = 110;
    private int largeurBarre = 1;
    private Rectangle2D cadre;
    private Rectangle2D barreJauge;
	
	/**
	 * Crée une jauge de carburant
	 * @param nbCarburant Nombre de carburant restant
	 * @param xJauge Position en x de la jauge
	 * @param yJauge Position en y de la jauge
	 * @param nbCarburantMax Nombre de carburant maximum
	 */
	public JaugeCarburant(int nbCarburant, int xJauge, int yJauge, int nbCarburantMax) {
		this.nbCarburant=nbCarburant;
		this.xJauge=xJauge;
		this.yJauge=yJauge;
		this.nbCarburantMax=nbCarburantMax;
        creerLaGeometrie();
	}
	/**
	 * Créer la géométrie de la jauge de carburant
	 */
	private void creerLaGeometrie() {
		cadre=new Rectangle2D.Double(xJauge, yJauge, tailleCadre,tailleCadre );
        barreJauge=new Rectangle2D.Double(xJauge, yJauge+15, largeurBarre, tailleCadre-15);
	}

	/**
	 * Permet de dessiner la jauge de carburant
	 * @param g2d contexte graphique
	 */
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopie=(Graphics2D) g2d.create();
        AffineTransform mat= new AffineTransform();
		g2dCopie.setColor(Color.BLACK);
        g2dCopie.fill(cadre);

		for (int i = 0; i <= 100; i += 20) {
            double angle = Math.toRadians(-90 * (i / 100.0));
            int x = xJauge+(int) ((tailleCadre-10) * Math.cos(angle));
            int y =	yJauge+tailleCadre+(int) ((tailleCadre-10)* Math.sin(angle));
			g2dCopie.setFont(new Font("Tahoma", Font.PLAIN, 9));
			g2dCopie.setColor(Color.WHITE);
            g2dCopie.drawString(String.format("%d%%", i), x, y);
        }
        mat.rotate(calculAngleRotationPourJauge(),xJauge, yJauge+tailleCadre);
		g2dCopie.setColor(Color.RED);
        g2dCopie.fill(mat.createTransformedShape(barreJauge));
		
	}
	/**
	 * Calcule l'angle de rotation de la jauge
	 * @return L'angle de rotation de la jauge
	 */
    private double calculAngleRotationPourJauge(){
        double carbSurPercent = 100*nbCarburant/nbCarburantMax;
        double angle = 90-(carbSurPercent*90/100);
        return Math.toRadians(angle);
    }
	public int getNbCarburant() {
		return nbCarburant;
	}
	public void setNbCarburant(int nbCarburant) {
		this.nbCarburant = nbCarburant;
	}
	public int getxJauge() {
		return xJauge;
	}
	public void setxJauge(int xJauge) {
		this.xJauge = xJauge;
	}
	public int getyJauge() {
		return yJauge;
	}
	public void setyJauge(int yJauge) {
		this.yJauge = yJauge;
	}
	public int getNbCarburantMax() {
		return nbCarburantMax;
	}
	public void setNbCarburantMax(int nbCarburantMax) {
		this.nbCarburantMax = nbCarburantMax;
	}
    
}
