package classements;

/**
 * Classe des informations des essais
 * 
 * @author Liangchen Liu
 */
public class InformationEssais {
    
    private String jeu;
    private String essais;
    private String temps;

    /**
     * Constructeur de la classe
     * 
     * @param jeu le nom du jeu
     * @param essais le nombre d'essais ou de tours
     * @param temps le temps
     */
    public InformationEssais(String jeu, int essais, double temps) {
        this.jeu = jeu;
        this.essais = String.valueOf(essais);
        this.temps = String.valueOf(temps);
    }

    public String getJeu() {
        return jeu;
    }

    public void setJeu(String jeu) {
        this.jeu = jeu;
    }

    public String getEssais() {
        return essais;
    }

    public void setEssais(String essais) {
        this.essais = essais;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }


}
