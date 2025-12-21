/**
 * 
 */
package sim.application;


import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.math.SVector3d;
import sim.physics.SElectrostatics;
import sim.physics.SParticle;
import sim.physics.SParticlesSystem;

/**
 * L'application <b>SIMCoulomb</b> représente un calculateur de force électrique.
 * Le calcul peut étre réalisé é partir de :
 * <ul> - un fichier texte oé sont précisées les propriétés des particules chargées </ul>
 * <ul> - la spécfication d'une géométrie (particules ponctuelles, TRIUC, PPIUC) </ul> 
 * 
 * @author Simon Vézina
 * @since 2017-05-30
 * @version 2019-09-05 (Version labo v1.1.4 : La loi de Coulomb)
 */
public class SIMCoulomb {	//Complété par Tin Pham, Michel Vuu, Nicolas Morales  

	/**
	 * La constante <b>FILE_NAME</b> correspond au nom du fichier en lecture utilisé pour définir un système de particule analysé lors de la 3ième loi de Newton.
	 */
	private static final String FILE_NAME = "systeme_particule.txt";

	/**
	 * La méthode main.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		// Affichage résultat : Mise en situation - Question 1. (prélaboratoire)
		miseEnSituationQ1();

		// Affichage résultat : Mise en situation - Question 2. (prélaboratoire)
		miseEnSituationQ2();

		// Affichage résultat : Mise en situation - Question 3. et 4. (prélaboratoire)
		miseEnSituationQ3Q4();

		// Affichage résultat : Mise en situation - Question 5. (prélaboratoire)
		miseEnSituationQ5();

		// Vérification de la 3iéme loi de Newton avec la lecture d'un fichier contenant la description du système de particules.   
		newtonThirdLaw(FILE_NAME);

		//------------------------------------------------------------------------
		// Effectuer la comparaison des forces électriques (Sphére, TRIUC, PPIUC).
		//------------------------------------------------------------------------
		double Q = 3e-6;   // en Coulomb
		double q = 1e-6;   // en Coulomb
		double L = 2;     // en métre
		double d = 0.04;  // en métre
		int N = 1000000;       // nombre d'élément discret par dimension

		comparaisonDistribution(Q, q, L, d, N);   // VOUS DEVEZ CHANGER les valeurs pour effectuer d'autres comparaisons.
		
	
	}

	//------------------------
	// MéTHODES UTILITAIRES //
	//------------------------

	/**
	 * Méthode réalisant l'affichage de la mise en situation - Question 1. (prélaboratoire)
	 */
	public static void miseEnSituationQ1()
	{
		SVector3d r1 = new SVector3d(0.0, 1.0, 0.0);
		SVector3d r2 = new SVector3d(4.0, -1.0, 0.0);
		SVector3d r3 = new SVector3d(2.0, 3.0, 0.0);
		System.out.println("Vecteur r1 = " + r1);
		System.out.println("Vecteur r2 = " + r2);
		System.out.println("Vecteur r3 = " + r3);

	}

	/**
	 * Méthode réalisant l'affichage de la mise en situation - Question 2. (prélaboratoire)
	 */
	public static void miseEnSituationQ2()
	{
		SVector3d r1 = new SVector3d(0.0, 1.0, 0.0);
		SVector3d r2 = new SVector3d(4.0, -1.0, 0.0);
		SVector3d r3 = new SVector3d(2.0, 3.0, 0.0);

		//Vecteur déplacement r13
		SVector3d r13 = new SVector3d();
		r13 = r3.substract(r1);
		System.out.println("Vecteur déplacement r13 = " +r13);

		//Vecteur déplacement r23
		SVector3d r23 = new SVector3d();
		r23 = r3.substract(r2);
		System.out.println("Vecteur déplacement r23 = " + r23);

	}

	/**
	 * Méthode réalisant l'affichage de la mise en situation - Question 3. et 4. (prélaboratoire)
	 */
	public static void miseEnSituationQ3Q4()
	{
		double q3 = -4*(Math.pow(10, -6));
		double Q1 = 5*(Math.pow(10, -6));
		double Q2 = 6*(Math.pow(10, -6));		
		
		SVector3d r1 = new SVector3d(0.0, 1.0, 0.0);
		SVector3d r2 = new SVector3d(4.0, -1.0, 0.0);
		SVector3d r3 = new SVector3d(2.0, 3.0, 0.0);
		
		SVector3d forceE13 = new SVector3d();
		forceE13 = SElectrostatics.coulombLaw(Q1, r1, q3, r3);
		
		SVector3d forceE23 = new SVector3d();
		forceE23 = SElectrostatics.coulombLaw(Q2, r2, q3, r3);
		
		System.out.println("La force électrique que la sphère Q1 applique sur Q3 = " + forceE13);
		System.out.println("La force électrique que la sphère Q2 applique sur Q3 = " + forceE23);
		
	}

	/**
	 * Méthode réalisant l'affichage de la mise en situation - Question 5. (prélaboratoire)
	 */
	public static void miseEnSituationQ5()
	{
		double q3 = -4*(Math.pow(10, -6));
		double Q1 = 5*(Math.pow(10, -6));
		double Q2 = 6*(Math.pow(10, -6));		
		
		SVector3d r1 = new SVector3d(0.0, 1.0, 0.0);
		SVector3d r2 = new SVector3d(4.0, -1.0, 0.0);
		SVector3d r3 = new SVector3d(2.0, 3.0, 0.0);
		
		SVector3d forceE13 = new SVector3d();
		forceE13 = SElectrostatics.coulombLaw(Q1, r1, q3, r3);
		
		SVector3d forceE23 = new SVector3d();
		forceE23 = SElectrostatics.coulombLaw(Q2, r2, q3, r3);
		
		SVector3d forceE3 = new SVector3d();
		forceE3 = forceE13.add(forceE23);
		
		System.out.println("La force électrique appliquée sur Q3 = " + forceE3);
	

	}

	/**
	 * Méthode pour effectuer la vérification de la 3iéme loi de Newton.
	 * 
	 * @param file_name Le nom du fichier en lecture pour définir le système de particule.
	 */
	public static void newtonThirdLaw(String file_name)
	{
		try{

			// Construction d'un système de particules à partir du nom d'un fichier.
			SParticlesSystem system = new SParticlesSystem(file_name);

			// Calcul pour évaluer la 3iéme loi de Newton appliquée au système de particule.
			SVector3d sum = SVector3d.ZERO;

			for(int i = 0; i < system.getNbParticles(); i++)
				sum = sum.add(system.evaluateForce(i));

			// Affichage du résultat.
			System.out.println("Nombre de particules dans le système est N = " + system.getNbParticles());
			System.out.println("Liste des particules du système : ");
			System.out.println(system);
			System.out.println("La somme des forces du système : F = " + sum + " N");
			System.out.println();

		}catch(SConstructorException e){
			System.out.println("La 3ième loi de Newton n'a pas pu étre évaluée, car le fichier '" + file_name + "' n'a pas été trouvé.");
		}catch(SNoImplementationException e){
			System.out.println("La 3ième loi de Newton n'a pas pu étre évaluée, car une méthode nécessaire au calcul n'a pas été implémentée.");
		}

	}

	/**
	 * <p>
	 * Méthode pour faire la comparaison de la force électrique qu'applique différents systèmes sur une particule chargée.
	 * Les systèmes en comparaison sont :
	 * <ul> - La charge ponctuelle. </ul>
	 * <ul> - La tige infinie uniformément chargée (TRIUC) avec N éléments discrets. </ul>
	 * <ul> - La plaque plane infinie uniformément chargée (PPIUC) avec N éléments discrets. </ul>
	 * </p>
	 * 
	 * @param Q La charge qui applique la force en coulombs (C).
	 * @param q La charge qui subit la force électrique en coulombs (C).
	 * @param L La taille de la géométrie en métres (m).
	 * @param d La distance entre la distribution de charge et la charge qui subit la force en métres (m).
	 * @param N Le nombre d'éléments discrets dans les géométries.
	 */
	public static void comparaisonDistribution(double Q, double q, double L, double d, int N)
	{
		try{

			// 1er système : La charge ponctuelle.
			SParticlesSystem sys1 = new SParticlesSystem();
			sys1.add(new SParticle(Q, new SVector3d(0.0, 0.0, 0.0)));
			sys1.add(new SParticle(q, new SVector3d(0.0, 0.0, d)));

			// 2e système : La TRIUC. 
			SParticlesSystem sys2 = SParticlesSystem.buildLineX(Q, 2.0, N);
			sys2.add(new SParticle(q, new SVector3d(L/2.0, 0.0, d)));

			// 3e système : La PPIUC.
			SParticlesSystem sys3 = SParticlesSystem.buildSquareXY(Q, 2.0, N);
			sys3.add(new SParticle(q, new SVector3d(L/2.0, L/2.0, d)));

			// Calcul des forces électriques des systèmes sur une charge ponctuelle.
			// P.S. La charque q est située é la fin du système (en index = N-1).
			SVector3d force_ponctuelle = sys1.evaluateForce(sys1.getNbParticles()-1);
			SVector3d force_TRIUC = sys2.evaluateForce(sys2.getNbParticles()-1);
			SVector3d force_PPIUC = sys3.evaluateForce(sys3.getNbParticles()-1);

			// Affichage des résultats.
			System.out.println("Comparaison des distributions de charges :");
			System.out.println("------------------------------------------");
			System.out.println("Q = " + Q + " C , q = " + q + " C");
			System.out.println("L = " + L + " m , N = " + N + " particules");
			System.out.println("d = " + d + " m");

			System.out.println("Force appliquée par une charge PONCTUELLE sur une particule : F = " + force_ponctuelle + " N");
			System.out.println("Force appliquée par une TRIUC sur une particule             : F = " + force_TRIUC + " N");
			System.out.println("Force appliquée par une PPIUC sur une particule             : F = " + force_PPIUC + " N");
			
		}catch(SNoImplementationException e){ 
			System.out.println("La comparaison des distributions des charges n'est pas possible, car certaines méthodes nécessaires au calcul n'ont pas été implémentées.");
		}
	}

}
