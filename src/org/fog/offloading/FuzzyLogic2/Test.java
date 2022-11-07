package org.fog.offloading.FuzzyLogic2;

public class Test {

    public void calculateAHP(){

        AHP ahp = AHP.getInstance(Config.criteria);

        double[] compArray = ahp.getPairwiseComparisonArray();

        // Set the pairwise comparison values

//        double[] dm = {1.0, 5.0, 7.0, 9.0, 5.0, 6.0, 8.0, 3.0, 3.0, 2.0}; // DM1
//        double[] dm = {5.0, 7.0, 9.0, 9.0, 3.0, 7.0, 7.0, 5.0, 5.0, 1.0}; // DM2
//        double[] dm = {1.0/7.0, 1.0/5.0, 5.0, 5.0, 3.0, 9.0, 9.0, 9.0, 9.0, 1.0}; // DM3
//        double[] dm = {1.0, 1.0, 1.0/9.0, 3.0, 3.0, 1.0/9.0, 3.0, 1.0/9.0, 3.0, 9.0}; // DM4
//        double[] dm = {1.0, 3.0, 3.0, 1.0/9.0, 3.0, 3.0, 1.0/9.0, 3.0, 1.0/9.0, 1.0/9.0}; // DM5
        double[] dm = new double[10];
        dm[0] = Config.TIME_ENERGY;
        dm[1] = Config.TIME_COST;
        dm[2] = Config.TIME_AVAILABILITY;
        dm[3] = Config.TIME_CPUSPEED;
        dm[4] = Config.ENERGY_COST;
        dm[5] = Config.ENERGY_AVAILABILITY;
        dm[6] = Config.ENERGY_CPUSPEED;
        dm[7] = Config.COST_AVAILABILITY;
        dm[8] = Config.COST_CPUSPEED;
        dm[9] = Config.AVAILABILITY_CPUSPEED;

        System.arraycopy(dm, 0, compArray, 0, 10);

        ahp.setPairwiseComparisonArray(compArray);
        ahp.setEvd();

//        for (int i = 0; i < ahp.getNrOfPairwiseComparisons(); i++) {
//            System.out.print("Importance of " + Config.criteria[ahp.getIndicesForPairwiseComparison(i)[0]] + " compared to ");
//            System.out.print(Config.criteria[ahp.getIndicesForPairwiseComparison(i)[1]] + "= ");
//            System.out.println(ahp.getPairwiseComparisonArray()[i]);
//        }

//        System.out.println("A: " + ahp.toString());
//
//        System.out.println("Geometric Consistency Index: " + Config.df.format(ahp.getGeometricConsistencyIndex()));
//        System.out.println("Geometric Cardinal Consistency Index: " + Config.df.format(ahp.getGeometricCardinalConsistencyIndex()));
//        System.out.println("Consistency Index: " + Config.df.format(ahp.getConsistencyIndex()));
//        System.out.println("Consistency Ratio: " + Config.df.format(ahp.getConsistencyRatio()) + "%");
//        System.out.println("Weights: ");
        for (int k=0; k<ahp.getWeights().length; k++) {
            Config.ahpWeights[k] = ahp.getWeights()[k];
            System.out.println(Config.criteria[k] + ": " + Config.df.format(ahp.getWeights()[k]));
        }
    }

    public String topsisMethod(){
        Topsis topsis = new Topsis();
        String toOffload = topsis.start();
        return toOffload;
    }
}