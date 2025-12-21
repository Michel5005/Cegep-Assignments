/**
 * 
 */
package sim.application;

import sim.math.SMatrix;

/**
 * L'application <b>SIMKirchhoff</b> est une application permettant d'�valuer les courants circulants dans un circuit
 * en utilisant la m�thode globale de Kirchhoff pour r�soudre le circuit.
 * 
 * @author Simon V�zina
 * @since 2017-05-17
 * @version 2017-06-12 (labo v1.0)
 */
public class SIMKirchhoff {

	//--------------
	// CONSTANTES //
	//--------------

	// VERSION LABORATOIRE :
	// D�finir les électromotances des sources.
	private static final double E1 = 4.9;
	private static final double E2 = 7.0;

	// D�finir les r�sistances des r�sisteurs.
	private static final double R1 = 469.2;
	private static final double R2 = 2022;
	private static final double R3 = 467.9;

	//----------------
	// M�THODE MAIN //
	//----------------

	/**
	 * Méthode lançant l'application SIMKirchhoff.
	 *  
	 * @param args
	 */
	public static void main(String[] args)
	{
		printInitialData();


		// À vous d'ajouter le reste ...

		double [] eq1 = {1,1,-1,0};
		double [] eq2 = {-R1,0,-R3,E1};
		double [] eq3 = {0,-R2,-R3,E2};
		printEquationsSystem(eq1,eq2,eq3);
		
		SMatrix donnees = new SMatrix(eq1, eq2, eq3);
		printSolutions(donnees.solvingLinearEquationsSystem());
		
	}

	//---------------------
	// MÉTHODE UTILITAIRE //
	//---------------------

	/**
	 * Méthode pour faire l'affichage des données initiales.
	 */
	private static void printInitialData()
	{
		System.out.println("E1 = " + E1 + " V");
		System.out.println("E2 = " + E2 + " V");
		System.out.println();

		System.out.println("R1 = " + R1 + " ohm");
		System.out.println("R2 = " + R2 + " ohm");
		System.out.println("R3 = " + R3 + " ohm");
		System.out.println();
	}

	/**
	 * M�thode qui effectue l'affichage de la solution � la r�solution du circuit.
	 * On y affiche l'�lectromotance des sources, la r�sistance des r�sisteurs ainsi que les courants.
	 * 
	 * @param currents Les courants circulant dans les branches du circuit.
	 */
	private static void printSolutions(double[] currents)
	{
		// Afficher les courants
		for(int i = 0 ; i < currents.length; i++)
			System.out.println("Courant I" + (i+1) + " = " + currents[i] + " A");   
	}

	/**
	 * M�thode qui effectue l'affichage du syst�me d'�quation du circuit.
	 * 
	 * @param equations Le syst�me d'�quation.
	 */
	private static void printEquationsSystem(double[]... equations)
	{
		// Faire l'it�ration des �quations.
		for(double[] eq : equations)
		{
			// Faire l'it�ration des coefficients.
			for(int i = 0; i < eq.length; i++)
			{    
				// Affichage du 1ier param�tre.
				if(i == 0)
					if(eq[i] >= 0)
						System.out.print(eq[i] +" I" + (i+1) + "  ");
					else
						System.out.print("- " + Math.abs(eq[i]) +" I" + (i+1) + "  ");
				// Affichage du 2i�me au (N-1)i�me param�tre.
				else
					if(i != eq.length-1)
						if(eq[i] >= 0)
							System.out.print(" + " + eq[i] + " I" + (i+1) + "  ");
						else
							System.out.print(" - " + Math.abs(eq[i]) + " I" + (i+1) + "  ");
				// Affichage du Ni�me param�tre.
					else
						if(eq[i] >= 0)
							System.out.print(" + " + eq[i] + " = 0");
						else
							System.out.print(" - " + Math.abs(eq[i]) + " = 0");
			}
			System.out.println();
			System.out.println();
		}
	}

}// fin de la classe SIMKirchhoff
