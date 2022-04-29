package com.example.cmis202fx;

import java.io.Serializable;

public class GirlScout implements Comparable<GirlScout>, Serializable {

    private String scoutName;
    private Troop troop;
    private ScoutTroops troops;
    private CookieOrderManager orderManager;

    public GirlScout(String scoutName, int troopNumber) throws InvalidTroopException {
        super();
        this.scoutName = scoutName;
        this.troop = ScoutTroops.getScoutTroopsSingleInstance().getTroopFromTroopNumber(troopNumber);
        this.troops = ScoutTroops.getScoutTroopsSingleInstance();
        this.orderManager = new CookieOrderManager();

        if (troop == null){
            throw new InvalidTroopException("The troop number "+ troopNumber +" does not exist.");
        }

    }
    public GirlScout(String scoutName, String troopName) throws InvalidTroopException {
        super();
        this.scoutName = scoutName;
        this.troops = ScoutTroops.getScoutTroopsSingleInstance();
        this.troop = ScoutTroops.getScoutTroopsSingleInstance().getTroopFromTroopName(troopName);
        this.orderManager = new CookieOrderManager();
        if (troop == null){

            throw new InvalidTroopException("the Troop Name does not exist.");
        }

    }

    public boolean addScoutToTroop(){
        return troop.addMember(this);
    }

    public String getScoutName() {
        return scoutName;
    }

    public Troop getTroop() {
        return troop;
    }
    public String getTroopName(){
        return troop.getTroopName();
    }
    public int getTroopNumber(){
        return troop.getTroopNumber();
    }
    public String getAgeLevel(){
        return troop.getAgeLevel();
    }

    @Override
    public int compareTo( GirlScout o) {
        return this.scoutName.compareTo(o.getScoutName());
    }
    public String getDenMother(){
        return troop.getDenMother();
    }
    public CookieOrderManager getCookieOrderManager(){
        return orderManager;
    }

    public String toListView(){

        if (getTroop() == null){
            return "Scout Name: " + scoutName + "\n" +
                    "Assigned To: N/A";
        }
        return "Scout Name: " + scoutName + "\n" +
                "Assigned To: " + getTroopName() + "\n" +
                "Troop ID: " + getTroopNumber();
    }

    @Override
    public String toString() {
//        return "GirlScout [" +
//                "scoutName=" + scoutName + ", " +
//                "troop=" + getTroopNumber() + ", " +
//                "denMother=" + getDenMother() + ", " +
//                "AgeLevel='" + getAgeLevel() +
//                ']';
        return toListView();
    }
}
