package org.fog.offloading.FuzzyLogic;

import java.util.List;
import org.fog.entities.FogDevice;

public class AHP_TopsisCalculator {
	private static Fuzzy getFuzzyBw(double fogdeviceBw) {
		if(fogdeviceBw > 2000) return Fuzzy.VERY_HIGH;
		else if(fogdeviceBw >= 1800) return Fuzzy.HIGH;
		else if(fogdeviceBw >= 1400) return Fuzzy.AVERAGE;
		else if(fogdeviceBw >= 1200) return Fuzzy.LOW;
		return Fuzzy.VERY_LOW;
	}
	
	private static Fuzzy getFuzzyCpu(double fogdeviceCpuSpeed) {
		if(fogdeviceCpuSpeed > 2000) return Fuzzy.VERY_HIGH;
		else if(fogdeviceCpuSpeed > 1800) return Fuzzy.HIGH;
		else if(fogdeviceCpuSpeed > 1400) return Fuzzy.AVERAGE;
		else if(fogdeviceCpuSpeed > 1200) return Fuzzy.LOW;	
		return Fuzzy.VERY_LOW;
	}
	
	private static Fuzzy getFuzzyCost(double fogdeviceCostPerMips) {
		if(fogdeviceCostPerMips > 1) return Fuzzy.VERY_HIGH;
		else if(fogdeviceCostPerMips > 0.7) return Fuzzy.HIGH;
		else if(fogdeviceCostPerMips > 0.3) return Fuzzy.AVERAGE;
		else if(fogdeviceCostPerMips > 0.1) return Fuzzy.LOW;
		return Fuzzy.VERY_LOW;
	}
	
	private static Fuzzy getFuzzyDeadline(double deadline) {
		if(deadline > 500) return Fuzzy.VERY_HIGH;
		else if(deadline > 350) return Fuzzy.HIGH;
		else if(deadline > 200) return Fuzzy.AVERAGE;
		else if(deadline > 50) return Fuzzy.LOW;
		return Fuzzy.VERY_LOW;
	}
	
	private static Fuzzy getFuzzyTaskLength(double taskLength) {
		if(taskLength > 13000) return Fuzzy.VERY_HIGH;
		else if(taskLength > 10000) return Fuzzy.HIGH;
		else if(taskLength > 5000) return Fuzzy.AVERAGE;
		else if(taskLength > 1000) return Fuzzy.LOW;
		return Fuzzy.VERY_LOW;
	}
	
	static public String getRequiredDatacenter(List<FogDevice> fogDevices,double deadline,long taskLength) {
        double Bandwidth[] = new double[3];
        double CostperMips[] = new double[3];
        double CPUspeed[] = new double[3];
        
        
        for(FogDevice fogdevice:fogDevices) {
        	String fogdeviceName = fogdevice.getName();
        	double fogdeviceAverageMips = fogdevice.getAverageMips();
        	double fogdeviceBw = fogdevice.getUplinkBandwidth();
        	double fogdeviceCostPerMips = fogdevice.getRatePerMips();
        	if(fogdeviceName.equals("cloud")) {
//        		Config.bandwidth[0] = getFuzzyBw(fogdeviceBw);
        		Config.bandwidth[0] = Fuzzy.HIGH;
        		Bandwidth[0] = fogdeviceBw;
        		Config.CPUspeed[0] = getFuzzyCpu(fogdeviceAverageMips); 
        		CPUspeed[0] = fogdeviceAverageMips;
        		Config.CostPerMips[0] = getFuzzyCost(fogdeviceCostPerMips); 
        		CostperMips[0] = fogdeviceCostPerMips; 
        		Config.deadline[0] = getFuzzyDeadline(deadline);
        		Config.taskLength[0] = getFuzzyTaskLength(taskLength);
        	}
        	else if(fogdeviceName.equals("f-0")) {
//        		Config.bandwidth[1] = getFuzzyBw(fogdeviceBw); 
        		Config.bandwidth[1] = Fuzzy.AVERAGE;
        		Bandwidth[1] = fogdeviceBw;
        		Config.CPUspeed[1] = getFuzzyCpu(fogdeviceAverageMips); 
        		CPUspeed[1] = fogdeviceAverageMips;
        		Config.CostPerMips[1] = getFuzzyCost(fogdeviceCostPerMips); 
        		CostperMips[1] = fogdeviceCostPerMips; 
        		Config.deadline[1] = getFuzzyDeadline(deadline);
        		Config.taskLength[1] = getFuzzyTaskLength(taskLength);
        	}
        	else if(fogdeviceName.equals("m-0-0")) {
//        		Config.bandwidth[2] = getFuzzyBw(fogdeviceBw);
        		Config.bandwidth[2] = Fuzzy.LOW;
        		Bandwidth[2] = fogdeviceBw;
        		Config.CPUspeed[2] = getFuzzyCpu(fogdeviceAverageMips); 
        		CPUspeed[2] = fogdeviceAverageMips;
        		Config.CostPerMips[2] = getFuzzyCost(fogdeviceCostPerMips); 
        		CostperMips[2] = fogdeviceCostPerMips; 
        		Config.deadline[2] = getFuzzyDeadline(deadline);
        		Config.taskLength[2] = getFuzzyTaskLength(taskLength);
        	}
        }
        
		
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
