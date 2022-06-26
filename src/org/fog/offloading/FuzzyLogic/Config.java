package org.fog.offloading.FuzzyLogic;

import java.text.DecimalFormat;

class Config {

    // Set your alternatives here
    static String[] alternatives = new String[]{"End Device", "Fog Node", "Cloud"};
//    static String[] alternatives = new String[]{"End User-1", "End User-2", "End User-3", "Fog Node-1", "Fog Node-2", "Fog Node-3", "Cloud-1", "Cloud-2", "Cloud-3"};

    // Set your criteria here
    static String[] criteria = new String[]{"Network Bandwidth", "CPU Speed", "TaskLength", "Deadline", "Cost"};

    // Set true for benefit criterion, false for cost criterion
    static boolean[] costCriteria = new boolean[]{false, false, false, false, true}; // price is cost

    static Double[] ahpWeights = new Double[Config.criteria.length];

    static Fuzzy bandwidth[] = new Fuzzy[3];
    static Fuzzy CPUspeed[] = new Fuzzy[3];
    static Fuzzy taskLength[] = new Fuzzy[3];
    static Fuzzy deadline[] = new Fuzzy[3];
    static Fuzzy CostPerMips[] = new Fuzzy[3];
    // AHP criteria weights in respect to each other
    static final Double NETWORKBANDWIDTH_CPUSPEED = 1.0;
    static final Double NETWORKBANDWIDTH_TASKLENGTH = 1.0;
    static final Double NETWORKBANDWIDTH_DEADLINE = 1.0;
    static final Double NETWORKBANDWIDTH_COST = 1.0;
    static final Double CPUSPEED_TASKLENGTH = 1.0;
    static final Double CPUSPEED_DEADLINE = 1.0;
    static final Double CPUSPEED_COST = 1.0;
    static final Double TASKLENGTH_DEADLINE = 1.0;
    static final Double TASKLENGTH_COST = 1.0;
    static final Double DEADLINE_COST = 1.0;
    
    // The following values are obtained in profiling stage prior to offloading
    // Here, we just use static fuzzy values for each alternative

    // These values can also be computed from max and min of weighted decision matrix
    static double[] idealSolution = {0.75, 1.0, 1.0};
    static double[] antiIdealSolution = {0.0, 0.0, 0.25};

    // Number of decimal points for float number formatting
    static DecimalFormat df = new DecimalFormat("0.0000");

}
