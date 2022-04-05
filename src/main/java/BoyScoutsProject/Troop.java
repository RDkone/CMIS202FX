package BoyScoutsProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Troop implements  Comparable<Troop>, Iterable<BoyScout>, Serializable {
    private String troopName;
    private Rank scoutRank;
    private int troopNum;
    private List<BoyScout> troopMembers;

    public Troop(String troopName, String rank, int troopNum) throws Exception {
        scoutRank = Rank.valueOf(rank.toUpperCase());
        if (troopNum <= 0){
            throw new Exception("Illegal Troop ID. Must be > 0." +
                    "\nTroop by ID: " + troopNum + " was not created.");
        }
        if (troopName == null){
            throw new Exception("Troop name is empty.");
        }
        BScoutTroops scoutTroops = BScoutTroops.getBSTroopsSingleInstance();
        if (scoutTroops.troopExists(troopNum)){
            throw new Exception("Troop ID " + troopNum + " already exists.");
        }
        if (scoutTroops.troopExists(troopName)){
            throw new Exception("Troop Name " + troopName + " already exists.");
        }

        this.troopName = troopName;
        this.troopNum = troopNum;
        troopMembers = new ArrayList<>();
    }

    public String getTroopName() {
        return troopName;
    }
    public String getRank(){
        return scoutRank.toString();
    }

    public int getTroopNum() {
        return troopNum;
    }

    public List<BoyScout> getTroopMembers() {
        return troopMembers;
    }

    public boolean addBSMember(BoyScout scout){
        if (scout != null && !troopMembers.contains(scout)){
            troopMembers.add(scout);
            //Using Collections Sort to sort the scouts with its Comparator
            Collections.sort(troopMembers);
            return true;
        }
        return false;
    }
    public boolean removeBSMember(String scoutName){
        boolean delStatus = false;
        for(int i = 0; i < troopMembers.size(); i++){
            if (troopMembers.get(i).getScoutName().equalsIgnoreCase(scoutName)){
                troopMembers.remove(i);
                i--;
                delStatus = true;
            }
        }
        return delStatus;
    }
    public String listBSTroopMembers(){
        String output = "";
        for (int i=0; i< troopMembers.size(); i++){
            output += troopMembers.get(i).getScoutName() + "\n";
        }
        return output.trim();
    }

    @Override
    public String toString(){
        return "Troop [ID=" + getTroopNum() + ", Name=" + getTroopName() + ", Rank Level="
                + getRank() + "]";
    }

    @Override
    public int compareTo(Troop o) {
        if (o != null){
            if (troopNum > o.getTroopNum()) return 1;
            else if (troopNum < o.getTroopNum()) return -1;
            else return 0;
        }
        else {
            throw new NullPointerException();
        }
    }

    @Override
    public Iterator<BoyScout> iterator() {
        return troopMembers.iterator();
    }
}
