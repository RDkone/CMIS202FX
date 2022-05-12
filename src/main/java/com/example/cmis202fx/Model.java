package com.example.cmis202fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Model implements Serializable {
    private ScoutTroops scoutTroops; // For Troop and Girl Scout Keeping
    private ObservableList<Troop> troopList; // For Troop Listing
    private ObservableList<GirlScout> gsList; // For Girl Scout Listing
    private final List<String> ageLvlList; // For Age Level Input on Troop Creation
    private final List<String> cookieOrderList; // For Cookie on Order Creation
    private final List<String> cookieBoxList; // For Getting NumberBoxes of Cookies

    public Model() throws InvalidTroopException {
        this.scoutTroops = ScoutTroops.getScoutTroopsSingleInstance();
        this.troopList = FXCollections.observableArrayList();
        this.gsList = FXCollections.observableArrayList();
        this.ageLvlList = new ArrayList<>();
        this.cookieOrderList = new ArrayList<>();
        this.cookieBoxList = new ArrayList<>();
        setAgeLvls(this.ageLvlList);
        setCookies(this.cookieOrderList);
        setCookieNumBox(this.cookieBoxList);
//        createModelTroops();
    }
    // Test Method for adding troops and scouts to project view from model.
//    public void createModelTroops() throws InvalidTroopException {
//        Troop t1 = new Troop("Waning Suns", "Susie", "JUNIORS", 9483);
//        Troop t2 = new Troop("Waxing Moons", "Ally", "CADETTES", 2531);
//        Troop t3 = new Troop("Icy Wavers", "Megan", "DAISIES", 6913);
//        Troop t4 = new Troop("Kingdom Sets", "Reena", "SENIORS", 5142);
//        scoutTroops.addTroop(t1);
//        scoutTroops.addTroop(t2);
//        scoutTroops.addTroop(t3);
//        scoutTroops.addTroop(t4);
//        createdTroop();
//        GirlScout gs1 = new GirlScout("Ella", 2531);
//        gs1.addScoutToTroop();
//        createdGS(gs1);
//        GirlScout gs2 = new GirlScout("Shawna", "Waxing Moons");
//        gs2.addScoutToTroop();
//        createdGS(gs2);
//        GirlScout gs3 = new GirlScout("Ally", 9483);
//        gs3.addScoutToTroop();
//        createdGS(gs3);
//        GirlScout gs4 = new GirlScout("Hannah", "Icy Wavers");
//        gs4.addScoutToTroop();
//        createdGS(gs4);
//    }
    // Getters for model
    public ScoutTroops getScoutTroops() {
        return scoutTroops;
    }
    public ObservableList<Troop> getTroopList() {
        FXCollections.sort(troopList);
        return troopList;
    }
    public ObservableList<GirlScout> getGsList() {
        FXCollections.sort(gsList);
        return gsList;
    }
    public List<String> getAgeLvlList() {
        return ageLvlList;
    }
    public List<String> getCookieOrderList() {
        return cookieOrderList;
    }
    public List<String> getCookieBoxList() {
        return cookieBoxList;
    }
    // Setters for list models
    public void setAgeLvls(List<String> list){
        list.add("Daisies");
        list.add("Brownies");
        list.add("Juniors");
        list.add("Cadettes");
        list.add("Seniors");
        list.add("Ambassadors");
    }
    public void setCookies(List<String> list){
        list.add("Lemon Ups");
        list.add("Toffee-Tastic");
        list.add("Thin Mints");
        list.add("Caramel Chocolate Chip");
        list.add("Do-si-dos");
        list.add("Lemonades");
        list.add("Trefoils");
        list.add("Samoas");
        list.add("Tagalongs");
    }
    public void setCookieNumBox(List<String> list){
        list.add("Lemonups");
        list.add("Toffee_Tastic");
        list.add("Thin_Mints");
        list.add("Caramel_Chocolate_Chip");
        list.add("Do_si_dos");
        list.add("Lemonades");
        list.add("Trefoils");
        list.add("Samoas");
        list.add("Tagalongs");
    }
    // Add Troop to main database and ListView
    public void addTroop(Troop troop){
        scoutTroops.addTroop(troop);
        createdTroop();
    }
    // Add Troop to list for ListView (If not added)
    public void createdTroop(){
        for (Integer i : scoutTroops) {
            Troop trp = scoutTroops.getTroopFromTroopNumber(i);
            int idx = BinarySearch(troopList, trp);
            if (idx < 0){
                troopList.add(trp);
                FXCollections.sort(troopList);
            }
        }
    }
    // Remove Troop from database and ListView
    public void removeTroop(Troop troop){
        troop.removeMembers();
        scoutTroops.deleteTroop(troop);
        deletedTroop(troop);
    }
    // Remove Troop from listView (If not already removed)
    public void deletedTroop(Troop troop){
        int idx = BinarySearch(troopList, troop);
        troopList.remove(idx);
        FXCollections.sort(troopList);
    }
    // Add Assigned Girl Scout to ListView (If not added)
    public void createdGS(GirlScout scout){
        int idx = BinarySearch(gsList, scout);
        if (idx < 0){
            gsList.add(scout);
            FXCollections.sort(gsList);
        }
    }
    // Remove Girl Scout from listView (If not already removed)
    public void deletedGS(GirlScout scout){
        int idx = BinarySearch(gsList, scout);
        if (idx >= 0){
            gsList.remove(idx);
            FXCollections.sort(gsList);
        }
    }
    // Returns the number of troops in ListView/Database
    public String getTroopListSize(){
        return String.valueOf(troopList.size());
    }
    // Returns the number of girl scouts in ListView/Database
    public String getTotalGS(){
        return String.valueOf(gsList.size());
    }
    // Saves the project to a data file with location from FileChooser
    public boolean saveModelToFile(File file){
        boolean status = false;
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            ScoutTroops modelSC = scoutTroops;
            out.writeObject(modelSC);
            status = true;
        }
        catch (Exception exc){
            System.out.println(exc.getMessage());
        }
        return status;
    }
    // Loads a previous data file from FileChooser
    public boolean loadModelFromFile(File file) throws InterruptedException {
        boolean status = false;
        ScoutTroops loadedModelScouts = null;
        try (ObjectInputStream inp = new ObjectInputStream(new FileInputStream(file))) {
            loadedModelScouts = (ScoutTroops) inp.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (loadedModelScouts != null){
            setLoadedModel(loadedModelScouts);
            status = true;
        }
        return status;
    }
    // Gets the loaded database model and updates the contents of the other models
    public void setLoadedModel(ScoutTroops loadedSC) throws InterruptedException {
        this.scoutTroops = loadedSC;
        troopList.clear();
        gsList.clear();
//        createdTroop();
//        for (Integer id:
//             scoutTroops) {
//            Troop t = scoutTroops.getTroopFromTroopNumber(id);
//            List<GirlScout> gsL = t.getTroopMembers();
//            for (GirlScout g: gsL) {
//                createdGS(g);
//            }
//        }
        RunTask trpTask = new RunTask(1);
        RunTask gsTask = new RunTask(2);
        Thread t1 = new Thread(trpTask);
        Thread t2 = new Thread(gsTask);
        t1.start();
        t1.join();
        // wait for Troops to load then load the scouts
        t2.start();
        t2.join();

    }
    // Binary Search for searching through list models
    private static <T> int BinarySearch(List<? extends Comparable<? super T>> list, T key) {
        int low = 0;
        int high = list.size()-1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Comparable<? super T> midValue = list.get(mid);
            int compareTo = midValue.compareTo(key);

            if (compareTo < 0)
                low = mid + 1;
            else if (compareTo > 0)
                high = mid - 1;
            else
                return mid; // Found Key
        }
        return -(low + 1);  // Did Not Find Key
    }

    class RunTask implements Runnable {

        private int taskNumber;

        public RunTask(int taskNum){
            this.taskNumber = taskNum;
        }

        @Override
        public void run() {
            if (taskNumber == 1){
                createdTroop();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (taskNumber == 2){
                for (Integer id:
                        scoutTroops) {
                    Troop t = scoutTroops.getTroopFromTroopNumber(id);
                    List<GirlScout> gsL = t.getTroopMembers();
                    for (GirlScout g: gsL) {
                        createdGS(g);
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }

}

