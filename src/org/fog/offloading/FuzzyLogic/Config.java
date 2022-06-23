package org.fog.offloading.FuzzyLogic;

import java.text.DecimalFormat;

class Config {

    // Set your alternatives here
//    static String[] alternatives = new String[]{"Mobile", "Edge", "Public"};
    static String[] alternatives = new String[]{"End User-1", "End User-2", "End User-3", "Fog Node-1", "Fog Node-2", "Fog Node-3", "Cloud-1", "Cloud-2", "Cloud-3"};

    // Set your criteria here
    static String[] criteria = new String[]{"Network Bandwidth", "CPU Speed", "VM Utilisation", "Energy Efficiency", "Cost"};

    // Set true for benefit criterion, false for cost criterion
    static boolean[] costCriteria = new boolean[]{false, false, false, false, true}; // price is cost

    static Double[] ahpWeights = new Double[Config.criteria.length];

    // AHP criteria weights in respect to each other
//    static final Double BANDWIDTH_SPEED = 1.0;
//    static final Double BANDWIDTH_AVAILABILITY = 5.0;
//    static final Double BANDWIDTH_SECURITY = 7.0;
//    static final Double BANDWIDTH_PRICE = 9.0;
//    static final Double SPEED_AVAILABILITY = 5.0;
//    static final Double SPEED_SECURITY = 6.0;
//    static final Double SPEED_PRICE = 8.0;
//    static final Double AVAILABLITY_SECURITY = 3.0;
//    static final Double AVAIALABILITY_PRICE = 3.0;
//    static final Double SECURITY_PRICE = 2.0;
    static final Double NETWORKBANDWIDTH_CPUSPEED = 1.0;
    static final Double NETWORKBANDWIDTH_AVAILABILITY = 5.0;
    static final Double NETWORKBANDWIDTH_VMUTILISATION = 7.0;
    static final Double NETWORKBANDWIDTH_COST = 9.0;
    static final Double CPUSPEED_VMUTILISATION = 5.0;
    static final Double CPUSPEED_SECURITY = 6.0;
    static final Double CPUSPEED_COST = 8.0;
    static final Double VMUTILISATION_ENERGYEFFICIENCY = 3.0;
    static final Double VMUTILISATION_PRICE = 3.0;
    static final Double ENERGYEFFICIENCY_COST = 2.0;
    
    // The following values are obtained in profiling stage prior to offloading
    // Here, we just use static fuzzy values for each alternative
//    static final Fuzzy MOBILE_NETWORKBANDWIDTH = Fuzzy.VERY_HIGH;
//    static final Fuzzy MOBILE_SPEED = Fuzzy.GOOD;
//    static final Fuzzy MOBILE_AVAILABILITY = Fuzzy.HIGH;
//    static final Fuzzy MOBILE_SECURITY = Fuzzy.HIGH;
//    static final Fuzzy MOBILE_PRICE = Fuzzy.VERY_LOW;
    static final Fuzzy ENDUSER_NETWORKBANDWIDTH = Fuzzy.VERY_HIGH;
    static final Fuzzy ENDUSER_CPUSPEED = Fuzzy.GOOD;
    static final Fuzzy ENDUSER_VMUTILISATION = Fuzzy.HIGH;
    static final Fuzzy ENDUSER_ENERGYEFFICIENCY = Fuzzy.HIGH;
    static final Fuzzy ENDUSER_COST = Fuzzy.VERY_LOW;
    
    static final Fuzzy FOG_NETWORKBANDWIDTH = Fuzzy.VERY_HIGH;
    static final Fuzzy FOG_CPUSPEED = Fuzzy.HIGH;
    static final Fuzzy FOG_VMUTILISATION = Fuzzy.HIGH;
    static final Fuzzy FOG_ENERGYEFFICIENCY = Fuzzy.HIGH;
    static final Fuzzy FOG_COST = Fuzzy.LOW;

    static final Fuzzy CLOUD_NETWORKBANDWIDTH = Fuzzy.LOW;
    static final Fuzzy CLOUD_CPUSPEED = Fuzzy.VERY_HIGH;
    static final Fuzzy CLOUD_VMUTILISATION = Fuzzy.VERY_HIGH;
    static final Fuzzy CLOUD_ENERGYEFFICIENCY = Fuzzy.GOOD;
    static final Fuzzy CLOUD_COST = Fuzzy.VERY_HIGH;

    // These values can also be computed from max and min of weighted decision matrix
    static double[] idealSolution = {0.75, 1.0, 1.0};
    static double[] antiIdealSolution = {0.0, 0.0, 0.25};

    // Number of decimal points for float number formatting
    static DecimalFormat df = new DecimalFormat("0.0000");

}
