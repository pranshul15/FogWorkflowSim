package org.fog.offloading.FuzzyLogic;

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
          siteCriteria.add(Config.bandwidth[0]);
          siteCriteria.add(Config.CPUspeed[0]);
          siteCriteria.add(Config.taskLength[0]);
          siteCriteria.add(Config.deadline[0]);
          siteCriteria.add(Config.CostPerMips[0]);
        }
        else if(node.equals("Fog Node")) {
            siteCriteria.add(Config.bandwidth[1]);
            siteCriteria.add(Config.CPUspeed[1]);
            siteCriteria.add(Config.taskLength[1]);
            siteCriteria.add(Config.deadline[1]);
            siteCriteria.add(Config.CostPerMips[1]);        	
        }
        else if(node.equals("End Device")) {
            siteCriteria.add(Config.bandwidth[2]);
            siteCriteria.add(Config.CPUspeed[2]);
            siteCriteria.add(Config.taskLength[2]);
            siteCriteria.add(Config.deadline[2]);
            siteCriteria.add(Config.CostPerMips[2]);
        }


        return siteCriteria;
    }
}
