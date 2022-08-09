package org.fog.offloading.FuzzyLogic2;

import java.text.DecimalFormat;

public class Config {

    // Set your alternatives here
    static String[] alternatives = new String[]{"End Device", "Fog Node", "Cloud"};
//    static String[] alternatives = new String[]{"End User-1", "End User-2", "End User-3", "Fog Node-1", "Fog Node-2", "Fog Node-3", "Cloud-1", "Cloud-2", "Cloud-3"};

    // Set your criteria here
    static String[] criteria = new String[]{"Time", "Energy", "Cost", "Availability", "CPU speed"};

    // Set true for benefit criterion, false for cost criterion
    static boolean[] costCriteria = new boolean[]{false, false, true, false, false}; // price is cost

    static Double[] ahpWeights = new Double[Config.criteria.length];

    // 0 -> Cloud
    // 1 -> Fog
    // 2 -> End device
    
    static Fuzzy time[] = new Fuzzy[] {Fuzzy.LOW,Fuzzy.AVERAGE,Fuzzy.HIGH};
    static Fuzzy energy[] = new Fuzzy[] {Fuzzy.AVERAGE,Fuzzy.HIGH,Fuzzy.LOW};
    static Fuzzy cost[] = new Fuzzy[]{Fuzzy.HIGH,Fuzzy.AVERAGE,Fuzzy.LOW};
    static Fuzzy availability[] = new Fuzzy[]{Fuzzy.HIGH,Fuzzy.AVERAGE,Fuzzy.LOW};
    static Fuzzy CPUspeed[] = new Fuzzy[]{Fuzzy.HIGH,Fuzzy.AVERAGE,Fuzzy.LOW};
    // AHP criteria weights in respect to each other
    static Double TIME_ENERGY = 1.0/9.0;
    static Double TIME_COST = 1.0;
    static Double TIME_AVAILABILITY = 1.0;
    static Double TIME_CPUSPEED = 1.0;
    static Double ENERGY_COST = 9.0/1.0;
    static Double ENERGY_AVAILABILITY = 9.0/1.0;
    static Double ENERGY_CPUSPEED = 9.0/1.0;
    static Double COST_AVAILABILITY = 1.0;
    static Double COST_CPUSPEED = 1.0;
    static Double AVAILABILITY_CPUSPEED = 1.0;
    
    // The following values are obtained in profiling stage prior to offloading
    // Here, we just use static fuzzy values for each alternative

    // These values can also be computed from max and min of weighted decision matrix
    static double[] idealSolution = {0.75, 1.0, 1.0};
    static double[] antiIdealSolution = {0.0, 0.0, 0.25};

    // Number of decimal points for float number formatting
    static DecimalFormat df = new DecimalFormat("0.0000");
}
