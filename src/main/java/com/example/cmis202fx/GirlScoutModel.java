package com.example.cmis202fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GirlScoutModel {

    private ObservableList<String> girlScouts;
    private ObservableList<String> ageLevels;

    public GirlScoutModel(){

        girlScouts = FXCollections.observableArrayList("Elise",
                "Jill", "Lucy", "Maddie", "Mary", "Nora", "Penny", "Sandra");
        ageLevels = FXCollections.observableArrayList("Daisies", "Brownies",
                "Juniors", "Cadettes", "Seniors", "Ambassadors");
    }

    public ObservableList<String> getGirlScouts() {
        return girlScouts;
    }

    public ObservableList<String> getAgeLevels() {
        return ageLevels;
    }

    public void removeGirlScoutOnceAssigned(String scoutName){
        girlScouts.remove(scoutName);
    }
    public boolean addGirlScoutToList(String scoutName){
        boolean result = false;
        if (!girlScouts.contains(scoutName)){
            girlScouts.add(scoutName);
            result = true;
        }
        return result;
    }
}
