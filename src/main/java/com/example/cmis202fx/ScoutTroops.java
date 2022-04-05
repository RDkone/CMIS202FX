package com.example.cmis202fx;

import java.io.*;
import java.util.*;

class ScoutTroops implements Iterable<Integer>, Serializable {

    private static ScoutTroops instance = null;

     private HashMap<Integer, Troop> numbersMap;
     private HashMap<String, Troop> namesMap;

    private ScoutTroops(){
        this.numbersMap = new HashMap<>();
        this.namesMap = new HashMap<>();
    }

    public static ScoutTroops getScoutTroopsSingleInstance() {
        if (instance == null){
            instance = new ScoutTroops();
        }
        return instance;

    }
    public HashMap<Integer, Troop> getTroopsList(){
         return numbersMap;
    }
    public String listTroops(){
        String sp = "";
        Map<Integer, Troop> map = new TreeMap<>(numbersMap);
        Set set2 = map.entrySet();
        Iterator itr = set2.iterator();
        while (itr.hasNext()){
            Map.Entry mpEnt = (Map.Entry)itr.next();
            sp += mpEnt.getValue().toString() + "\n";
        }

        return sp;
    }
    public boolean addTroop(Troop troop){
        if (troop != null && !existsTroop(troop.getTroopNumber()) && !existsTroop(troop.getTroopName())){
            numbersMap.put(troop.getTroopNumber(), troop);
            namesMap.put(troop.getTroopName(), troop);
            return true;
        }
        return false;

    }
    public boolean deleteTroop(Troop troop){
        boolean flag = false;
        if (troop != null && troop.getTroopMembers().size() == 0){
            flag = numbersMap.remove(troop.getTroopNumber(), troop) && namesMap.remove(troop.getTroopName(), troop);
        }
        return flag;
    }
    public boolean deleteTroop(int troopNumber){
        Troop tr00p = getTroopFromTroopNumber(troopNumber);
        return deleteTroop(tr00p);
    }
    public boolean deleteTroop(String troopName){
        Troop tr00p = getTroopFromTroopName(troopName);
        return deleteTroop(tr00p);
    }
    public String listTroopMembers(int troopNumber){
        for (Troop t : numbersMap.values()){
            if (t.getTroopNumber() == troopNumber){
                Collections.sort(t.getTroopMembers());
                String sp = "";
                for (int i = 0; i < t.getTroopMembers().size(); i++) {
                    sp += t.getTroopMembers().get(i).getScoutName() + "\n";
                }
                return sp;
            }
        }

        return null;

    }
    public Troop getTroopFromTroopNumber(int troopNumber){
        Troop trp = null;
        for (Troop t : numbersMap.values()){
            if (t.getTroopNumber() == troopNumber){
                trp = t;
            }
        }


        return trp;
    }
    public Troop getTroopFromTroopName(String troopName){
        Troop trp = null;
        for (Troop t : namesMap.values()){
            if (t.getTroopName().equalsIgnoreCase(troopName)){
                trp = t;
            }
        }
        return trp;
    }
    public boolean existsTroop(int troopNumber){
        boolean get = false;
        Set<Map.Entry<Integer, Troop>> entries = numbersMap.entrySet();
        Iterator<Map.Entry<Integer, Troop>> itr = entries.iterator();
        while (itr.hasNext()){
            Map.Entry<Integer, Troop> entry = itr.next();
            int tNum = entry.getValue().getTroopNumber();
            if (troopNumber == tNum){
                get = true;
            }
        }

        return get;
    }
    public boolean existsTroop(String troopName){
        boolean get = false;
        Set<Map.Entry<String, Troop>> entries = namesMap.entrySet();
        Iterator<Map.Entry<String, Troop>> itr = entries.iterator();
        while (itr.hasNext()){
            Map.Entry<String, Troop> entry = itr.next();
            String tStr = entry.getValue().getTroopName();
            if (troopName.equalsIgnoreCase(tStr)){
                get = true;
            }
        }

        return get;
    }


    @Override
    public Iterator<Integer> iterator() {
        List<Integer> troopInts = new ArrayList<>(numbersMap.keySet());
        Collections.sort(troopInts);
        return troopInts.iterator();
    }
    public void exportAll(String file){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            ScoutTroops trps = ScoutTroops.getScoutTroopsSingleInstance();
            out.writeObject(trps);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static ScoutTroops importAll(String file){
        ScoutTroops troops = instance;
        try (ObjectInputStream inp = new ObjectInputStream(new FileInputStream(file))) {
            troops = (ScoutTroops) inp.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return troops;
    }
}

