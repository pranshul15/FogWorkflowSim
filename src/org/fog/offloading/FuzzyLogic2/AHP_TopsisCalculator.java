package org.fog.offloading.FuzzyLogic2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.fog.entities.FogDevice;
import org.fog.entities.OffloadingEngine;
import org.fog.utils.FogLinearPowerModel;
import org.workflowsim.FileItem;
import org.workflowsim.Job;
import org.workflowsim.utils.Parameters.FileType;

public class AHP_TopsisCalculator {	
	
	// low is better
	private static Fuzzy setFuzzyValueL(double t) {
		if(t<25) return Fuzzy.VERY_HIGH;
		else if(t<50) return Fuzzy.HIGH;
		else if(t<75) return Fuzzy.AVERAGE;
		else if(t<90) return Fuzzy.LOW;
		return Fuzzy.VERY_LOW;
	}
	
	// high is better
	private static Fuzzy setFuzzyValueH(double t) {
		if(t<25) return Fuzzy.VERY_LOW;
		else if(t<50) return Fuzzy.LOW;
		else if(t<75) return Fuzzy.AVERAGE;
		else if(t<90) return Fuzzy.HIGH;
		return Fuzzy.VERY_HIGH;
	}
	
	// for setting Time
	private static void setFuzzyTime(double time1,double time2,double time3) {
		double p = Math.max(time1, Math.max(time2, time3));
		double time1_100 = time1*(100/p);
		double time2_100 = time2*(100/p);
		double time3_100 = time3*(100/p);
		System.out.println("time1: "+time1_100+" time2: "+time2_100+" time3: "+time3_100);
		Config.time[0] = setFuzzyValueL(time1_100);
		Config.time[1] = setFuzzyValueL(time2_100);
		Config.time[2] = setFuzzyValueL(time3_100);
	}
	
	
	// for setting energy
	private static void setFuzzyEnergy(double energy1,double energy2,double energy3) {
		double p = Math.max(energy1, Math.max(energy2, energy3));
		double energy1_100 = energy1*(100/p);
		double energy2_100 = energy2*(100/p);
		double energy3_100 = energy3*(100/p);
//		System.out.println("energy1: "+energy1_100+" energy2: "+energy2_100+" energy3: "+energy3_100);
		Config.energy[0] = setFuzzyValueL(energy1_100);
		Config.energy[1] = setFuzzyValueL(energy2_100);
		Config.energy[2] = setFuzzyValueL(energy3_100);
	}
	
	
	// for setting cost
	private static void setFuzzyCost(double cost1,double cost2,double cost3) {
		double cost1_100 = 100;
		double cost2_100 = cost2*(100/cost1);
		double cost3_100 = cost3*(100/cost1);
//		System.out.println("cost1: "+cost1_100+" cost2: "+cost2_100+" cost3: "+cost3_100);
		Config.cost[0] = setFuzzyValueH(cost1_100);
		Config.cost[1] = setFuzzyValueH(cost2_100);
		Config.cost[2] = setFuzzyValueH(cost3_100);
	}
	
	// for setting Availability
	private static Fuzzy setFuzzyAvailability(double percent) {
		if(percent < 10) return Fuzzy.VERY_HIGH;
		else if(percent < 50) return Fuzzy.HIGH;
		else if(percent < 75) return Fuzzy.AVERAGE;
		else if(percent < 90) return Fuzzy.LOW;
		return Fuzzy.VERY_LOW;
	}
	
	// for setting CPUspeed
	private static void setFuzzyCPUspeed(double CPU1,double CPU2,double CPU3) {
		double cpu1_100 = 100;
		double cpu2_100 = CPU2*(100/CPU1);
		double cpu3_100 = CPU3*(100/CPU1);
//		System.out.println("cpu1: "+cpu1_100+" cpu2: "+cpu2_100+" cpu3: "+ cpu3_100);
		Config.CPUspeed[0] = setFuzzyValueH(cpu1_100);
		Config.CPUspeed[1] = setFuzzyValueH(cpu2_100);
		Config.CPUspeed[2] = setFuzzyValueH(cpu3_100);
	}
	
	
	static public String getRequiredDatacenter(List<FogDevice> fogDevices,Job job,double[] time,double[] energy) {
 
		long percentTasklengthCloud = (OffloadingEngine.tasklengthOffloadedToCloud*100)/OffloadingEngine.totalTasklength;
		long percentTasklengthFog = (OffloadingEngine.tasklengthOffloadedToFog*100)/OffloadingEngine.totalTasklength;
		long percentTasklengthEnd = (OffloadingEngine.tasklengthOffloadedToEnd*100)/OffloadingEngine.totalTasklength;
		long taskLength = job.getCloudletLength();
		double CPU1 = 0 , CPU2 = 0 , CPU3 = 0;
		double cost1 = 0 , cost2 = 0 , cost3 = 0;
		double time1 = time[0] , time2 = time[1] , time3 = time[2];
		double energy1 = energy[0] , energy2 = energy[1] , energy3 = energy[2];
//		System.out.println();
//		System.out.println("Cloud: "+percentTasklengthCloud+" Fog: "+percentTasklengthFog+" End: "+percentTasklengthEnd);
		
		
        for(FogDevice fogdevice:fogDevices) {
        	String fogdeviceName = fogdevice.getName();
        	if(fogdeviceName.equals("cloud")) {
        		CPU1 = fogdevice.getAverageMips();
        		cost1 = fogdevice.getRatePerMips();
        		Config.availability[0] = setFuzzyAvailability(percentTasklengthCloud);
        	}
        	else if(fogdeviceName.equals("f-0")) {
        		CPU2 = fogdevice.getAverageMips();
        		cost2 = fogdevice.getRatePerMips();
        		Config.availability[1] = setFuzzyAvailability(percentTasklengthFog);
        	}
        	else if(fogdeviceName.equals("m-0-0")) {
        		CPU3 = fogdevice.getAverageMips();
        		cost3 = fogdevice.getRatePerMips();
        		Config.availability[2] = setFuzzyAvailability(percentTasklengthEnd);
        	}
        }
        
        setFuzzyTime(time1,time2,time3);
        setFuzzyEnergy(energy1,energy2,energy3);
        setFuzzyCost(cost1,cost2,cost3);
        setFuzzyCPUspeed(CPU1,CPU2,CPU3);
        
        
        
        
        
		Test test = new Test();

//        System.out.println("Calculating AHP Criteria weighting: ");
        test.calculateAHP();

//        System.out.println("End of AHP");
//        System.out.println("********************************");
//        System.out.println("Calculating Fuzzy TOPSIS: ");

        String toOffload = test.topsisMethod();

		return toOffload;
	}
}
