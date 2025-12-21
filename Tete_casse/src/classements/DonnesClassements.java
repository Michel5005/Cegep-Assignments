package classements;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe des données des classements
 * 
 * @author Liangchen Liu
 */
public class DonnesClassements implements Serializable {

    private ArrayList<InformationEssais> circuitArrayList = new ArrayList<InformationEssais>();
    private ArrayList<InformationEssais> curlingArrayList = new ArrayList<InformationEssais>();
    private ArrayList<InformationEssais> pecheArrayList = new ArrayList<InformationEssais>();
    private ArrayList<InformationEssais> tankArrayList = new ArrayList<InformationEssais>();
    private ArrayList<InformationEssais> meilleursTemps = new ArrayList<InformationEssais>();

    public ArrayList<InformationEssais> getCircuitArrayList() {
        return circuitArrayList;
    }
    public void setCircuitArrayList(ArrayList<InformationEssais> circuitArrayList) {
        this.circuitArrayList = circuitArrayList;
    }
    public ArrayList<InformationEssais> getCurlingArrayList() {
        return curlingArrayList;
    }
    public void setCurlingArrayList(ArrayList<InformationEssais> curlingArrayList) {
        this.curlingArrayList = curlingArrayList;
    }
    public ArrayList<InformationEssais> getPecheArrayList() {
        return pecheArrayList;
    }
    public void setPecheArrayList(ArrayList<InformationEssais> pecheArrayList) {
        this.pecheArrayList = pecheArrayList;
    }
    public ArrayList<InformationEssais> getTankArrayList() {
        return tankArrayList;
    }
    public void setTankArrayList(ArrayList<InformationEssais> tankArrayList) {
        this.tankArrayList = tankArrayList;
    }
    public ArrayList<InformationEssais> getMeilleursTemps() {
        return meilleursTemps;
    }
    public void setMeilleursTemps(ArrayList<InformationEssais> meilleursTemps) {
        this.meilleursTemps = meilleursTemps;
    }



}