package org.fog.offloading.FuzzyLogic2;

import java.util.ArrayList;
import java.util.TreeMap;

class Profiler {

    private TreeMap<String, ArrayList<Fuzzy>> availableSites = new TreeMap<>();

    TreeMap<String, ArrayList<Fuzzy>> start(){
        for (String alternative : Config.alternatives) {
            ArrayList<Fuzzy> criteriaImportance = profileNode(alternative);
            availableSites.put(alternative, criteriaImportance);
        }

        return availableSites;
    }

    private ArrayList<Fuzzy> profileNode(String node) {

        ArrayList<Fuzzy> siteCriteria = new ArrayList<>();
 
        if(node.equals("Cloud")) {
          siteCriteria.add(Config.time[0]);
          siteCriteria.add(Config.energy[0]);
          siteCriteria.add(Config.cost[0]);
          siteCriteria.add(Config.availability[0]);
          siteCriteria.add(Config.CPUspeed[0]);
        }
        else if(node.equals("Fog Node")) {
            siteCriteria.add(Config.time[1]);
            siteCriteria.add(Config.energy[1]);
            siteCriteria.add(Config.cost[1]);
            siteCriteria.add(Config.availability[1]);        	
            siteCriteria.add(Config.CPUspeed[1]);
        }
        else if(node.equals("End Device")) {
            siteCriteria.add(Config.time[2]);
            siteCriteria.add(Config.energy[2]);
            siteCriteria.add(Config.cost[2]);
            siteCriteria.add(Config.availability[2]);
            siteCriteria.add(Config.CPUspeed[2]);
        }


        return siteCriteria;
    }
}
