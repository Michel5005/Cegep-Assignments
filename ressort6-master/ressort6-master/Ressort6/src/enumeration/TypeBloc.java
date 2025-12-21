package enumeration;

/**
 * L'énumération TypeBloc représente différents types de blocs. Chaque type de
 * bloc a un nom et un coefficient associé.
 * 
 * @author Natael Lavoie
 */
public enum TypeBloc {
	NICKEL_ACIER("nickel-acier", 0.64, 0),
	BRONZE_FER("bronze-fer", 0.22, 1),
	ACIER_PLOMB("acier-plomb", 0.95, 2);

	/**
	 * Constructeur prenant un nom et un coefficient en argument.
	 *
	 * @param name le nom du type de bloc
	 * @param coef le coefficient associé au type de bloc
	 * @param index 
	 */
	TypeBloc(String name, double coef, int index) {
		this.name = name;
		this.coefficient = coef;
		this.imageIndex = index;
	}

	/**
	 * Renvoie le coefficient associé au type de bloc.
	 *
	 * @return le coefficient associé au type de bloc
	 */
	public double getCoefficient() {
		return coefficient;
	}

	/**
	 * Renvoie le nom du type de bloc.
	 *
	 * @return le nom du type de bloc
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Renvoie l'index de l'image du type de block
	 * 
	 * @return l'index de l'image du type de block
	 */
	public int getImageIndex() {
		return imageIndex;
	}
	
	/** Le nom du type de bloc. */
	private final double coefficient;
	/** Le coefficient associé au type de bloc. */
	private final String name;
	/** l'index de l'image associé au type de bloc. */
	private final int imageIndex;
}
