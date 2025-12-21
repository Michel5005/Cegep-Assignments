package char_assaut;

/**
 * L'énumération TypePlaneteTerrain représente différents types de terrain.
 * Chaque type de  terrain a un nom de planète et une gravité associé.
 * 
 * @author Michel Vuu
 *
 */
public enum TypePlaneteTerrain {

	MARS("Mars", 3.721), MERCURE("Mercure", 3.7), TERRE("Terre", 9.807), LUNE("Lune", 1.62);

	TypePlaneteTerrain(String name, double gravite) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.gravite = gravite;
	}// fin méthode

	public String getName() {
		return name;
	}

	public double getGravite() {
		return gravite;
	}

	/** Le nom de la planète */
	private final String name;
	/** La gravité de la planète */
	private final double gravite;

}// fin classe
