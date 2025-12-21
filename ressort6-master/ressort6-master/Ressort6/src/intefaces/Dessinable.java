package intefaces;

import java.awt.Graphics2D;

/**
 * Interface qui définit la méthode (ou possiblement les méthodes) qu'un objet
 * dessinable doit implémenter.
 * 
 * @author Caroline Houle
 *
 */
public interface Dessinable {
	/**
	 * Dessine les formes constituant l'objet. Doit s'assurer de ne pas modifier le
	 * contexte grahique
	 * 
	 * @param g2d Contexte graphique du composant sur lequel dessiner
	 */
	public void dessiner(Graphics2D g2d);
}
