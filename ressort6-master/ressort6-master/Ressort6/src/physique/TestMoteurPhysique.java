package physique;

import javax.swing.text.Position;

import geometrie.Vecteur2D;

/**
 * Classe permettant de tester le moteur physique de l'application
 * SANS OBJET BALLE et SANS DESSIN 
 * 
 * @author Nokto Lapointe
 *
 */
public class TestMoteurPhysique { 
	
	/**
	 * Methode principale : activer le test
	 * Les affichages se font a la console
	 * @param args Arguments du programme
	 * @throws Exception 
	 */

	public static void main(String[] args) throws Exception {
		double masse = 0.7;
		double deltaT =0.008;
		double tempsTotal = 0;
		int nbIterationATester = 3;
		double k = 500.0;
		double coeff = 0.64;
		double e = 0.8;
		Vecteur2D fRessort = MoteurPhysique.calculForceRessort(e, k);
		Vecteur2D fTotale;
		
		//vecteurs avec valeurs initiales
		Vecteur2D pos = new  Vecteur2D(0.8, 0);  
		Vecteur2D vit = new Vecteur2D(-0.0000001,0); 
		Vecteur2D acc = new Vecteur2D(0,0);
		Vecteur2D grav = MoteurPhysique.calculForceGrav(masse);
		Vecteur2D norm = MoteurPhysique.calculForceGrav(-masse);
		Vecteur2D fFrottement = MoteurPhysique.calculForceFrottement(coeff, masse, vit);
		
		//initialement
		
		System.out.println("\nDonnees fixes:");
		System.out.println("   pas de simulation= \t" + deltaT + " secondes");
		System.out.println("   masse balle= \t\t" + masse + " Kg");
		
		fTotale = new Vecteur2D( fFrottement.additionne(grav).additionne(norm).additionne(fRessort) ); //ici il n'y a pas d'autres forces � considerer 
		try {
			acc = MoteurPhysique.calculAcceleration(fTotale, masse);
		} catch (Exception e1) {
			System.out.println("Erreur calcul de l'acc�l�ration, masse nulle!");
		}
		
		System.out.println("\nSituation initiale");
		System.out.println("   force normale= \t" + norm.toString(3));
		System.out.println("   force gravit.= \t" + grav.toString(3));
		System.out.println("   force rappel= \t" + fRessort.toString(3));
		System.out.println("   force frottement= \t" + fFrottement.toString(3));
		System.out.println("   somme des forces= \t" + fTotale.toString(3));
		System.out.println("   acceleration= \t" + acc.toString(3));
		System.out.println("   vitesse=  	 	" + vit.toString(8));
		System.out.println("   position= 	 	" + pos.toString(3));

		
		//on avance
		for (int j=1; j<=nbIterationATester; j++) {
			tempsTotal += deltaT;
			System.out.println("\nTour= " + j + " au temps " + tempsTotal + " ms");
			fFrottement = MoteurPhysique.calculForceFrottement(coeff, masse, vit);
			fRessort = MoteurPhysique.calculForceRessort(pos.getX(), k);
			fTotale = new Vecteur2D( fFrottement.additionne(grav).additionne(norm).additionne(fRessort) );
			acc = MoteurPhysique.calculAcceleration(fTotale, masse);
			vit = MoteurPhysique.calculVitesse(deltaT, vit, acc);
			pos = MoteurPhysique.calculPosition(deltaT, pos, vit);
			
			
			
			
			System.out.println("   force normale= \t" + norm.toString(3));
			System.out.println("   force gravit.= \t" + grav.toString(3));
			System.out.println("   force rappel= \t" + fRessort.toString(3));
			System.out.println("   force frottement= \t" + fFrottement.toString(3));
			System.out.println("   somme des forces= \t" + fTotale.toString(3));
			System.out.println("   acceleration= \t" + acc.toString(3));
			System.out.println("   vitesse= " + "\t\t" + vit.toString(3));
			System.out.println("   position= " + "\t\t" + pos.toString(3));

		}//fin boucle de test
		

	}//fin maint

}
