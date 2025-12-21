package char_assaut;

/**
 * L'énumération TypeAmelioration représente différents types d'ameliorations.
 * Chaque type de d'amelioration a un nom d'amelioration et un nombre associé.
 * 
 * @author Michel Vuu
 *
 */
public enum TypeAmelioration {

	PLUS_DEGAT(25), PLUS_CABURANT(50), PLUS_VIE(100), BOUCLIER(50), DEUXIEME_TIR(2);

	TypeAmelioration(int montant) {
		this.montant = montant;

	}

	public int getMontant() {
		return montant;
	}
	/** Valeur pour chaque amelioration*/
	private final int montant;
}// fin classe
