package com.example.cmis202fx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Troop implements Comparable<Troop>,Iterable<GirlScout>, Serializable {
    private String troopName;
    private String denMother;
    private int troopNumber;
    public Level ageLevel;
    private List<GirlScout> troopMembers;

    public Troop(String troopName, String denMother, String ageLevel, int troopNumber) throws InvalidTroopException {
        this.troopName = troopName;
        this.denMother = denMother;
        this.troopNumber = troopNumber;
        try {
            this.ageLevel = Level.valueOf(ageLevel.toUpperCase());

        } catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new InvalidTroopException("Invalid age level.");
        }

        this.troopMembers = new ArrayList<>();
        ScoutTroops checkTroops = ScoutTroops.getScoutTroopsSingleInstance();

        if (this.troopNumber <= 0){

            throw new InvalidTroopException("Illegal troop number. Must be > 0" + "\n" +
                    "Troop " + this.troopNumber + " was not created.");
        }
        else if (this.troopName == null){
            throw new InvalidTroopException("the troopName is null");
        }
        else if (checkTroops.existsTroop(this.troopNumber)){
            throw new InvalidTroopException("Troop number " + troopNumber + " already exists");
        }
        else if (checkTroops.existsTroop(this.troopName)){
            throw new InvalidTroopException("Troop name " + troopName + " already exists");
        }

    }


    public String getTroopName() {
        return troopName;
    }

    public String getDenMother() {
        return denMother;
    }

    public int getTroopNumber() {
        return troopNumber;
    }

    public String getAgeLevel() {
        return ageLevel.toString();
    }

    public List<GirlScout> getTroopMembers() {
        return troopMembers;
    }

    public void setDenMother(String denMother) {
        this.denMother = denMother;
    }
    public List<String> listTroopMemberNames(){
        List<String> gsList = new ArrayList<>();
        for (int i = 0; i < troopMembers.size(); i++){
            gsList.add(troopMembers.get(i).getScoutName());
        }
        return gsList;
    }

    public String listTroopMembers(){
        String sp = "";
        for (int i = 0; i < troopMembers.size(); i++) {
            sp += troopMembers.get(i).getScoutName() + "\n";
        }
        return sp.trim();
    }

    public boolean addMember(GirlScout scout){
        if (scout == null || troopMembers.contains(scout)){
            return false;
        }
        troopMembers.add(scout);
        Collections.sort(troopMembers);
        return true;
    }

    public boolean removeMember(String scoutName){
        boolean status = false;
        for(int i = 0; i < troopMembers.size(); i++) {
            if (troopMembers.get(i).getScoutName().equalsIgnoreCase(scoutName)) {
                troopMembers.remove(i);
                i--;
                status = true;
            }
        }
        return status;

    }
    public void removeMembers(){
        List<String> list = new ArrayList<>();
        for (GirlScout g : this){
            list.add(g.getScoutName());
        }
        for (String s:
             list) {
            this.removeMember(s);
        }
    }

    @Override
    public int compareTo(Troop t) {
        if (t!= null){
            if (troopNumber > t.getTroopNumber()){
                return 1;
            }
            else if (troopNumber < t.getTroopNumber()){
                return -1;
            }
            else {
                return 0;
            }
        }
        else {
            throw new NullPointerException();
        }
    }

    @Override
    public Iterator<GirlScout> iterator() {
        return troopMembers.iterator();
    }

    public String toListView(){
        return troopName + " -- " + "[" + troopNumber + "]";
    }

    @Override
    public String toString() {
//        return  "[Troop]\n" +
//                "Name: " + troopName + "\n" +
//                "Den Mother: " + denMother + "\n" +
//                "AgeLevel: " + ageLevel + "\n" +
//                "Troop Number: " + troopNumber + "\n" +
//                "------------------------------------";
        return toListView();
    }
}
