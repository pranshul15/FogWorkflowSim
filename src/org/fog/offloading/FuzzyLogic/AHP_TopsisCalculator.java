package org.fog.offloading.FuzzyLogic;

import java.util.List;
import org.fog.entities.FogDevice;
import org.fog.entities.OffloadingEngine;

public class AHP_TopsisCalculator {	
	

//	private static Fuzzy getFuzzyCpu(double fogdeviceCpuSpeed) {
//		if(fogdeviceCpuSpeed > 2000) return Fuzzy.VERY_HIGH;
//		else if(fogdeviceCpuSpeed > 1800) return Fuzzy.HIGH;
//		else if(fogdeviceCpuSpeed > 1400) return Fuzzy.AVERAGE;
//		else if(fogdeviceCpuSpeed > 1200) return Fuzzy.LOW;	
//		return Fuzzy.VERY_LOW;
//	}
//	
//	private static Fuzzy getFuzzyCost(double fogdeviceCostPerMips) {
//		if(fogdeviceCostPerMips > 1) return Fuzzy.VERY_HIGH;
//		else if(fogdeviceCostPerMips > 0.7) return Fuzzy.HIGH;
//		else if(fogdeviceCostPerMips > 0.3) return Fuzzy.AVERAGE;
//		else if(fogdeviceCostPerMips > 0.1) return Fuzzy.LOW;
//		return Fuzzy.VERY_LOW;
//	}
//	
//	private static Fuzzy getFuzzyDeadline(double deadline) {
//		if(deadline > 500) return Fuzzy.VERY_HIGH;
//		else if(deadline > 350) return Fuzzy.HIGH;
//		else if(deadline > 200) return Fuzzy.AVERAGE;
//		else if(deadline > 50) return Fuzzy.LOW;
//		return Fuzzy.VERY_LOW;
//	}
//	
	private static Fuzzy getFuzzyTaskLength(double percent) {
		if(percent < 10) return Fuzzy.VERY_HIGH;
		else if(percent < 50) return Fuzzy.HIGH;
		return Fuzzy.AVERAGE;
	}
	
	
	
	static public String getRequiredDatacenter(List<FogDevice> fogDevices,double deadline,long taskLength) {
 
		long percentTasklengthCloud = (OffloadingEngine.tasklengthOffloadedToCloud*100)/OffloadingEngine.totalTasklength;
		long percentTasklengthFog = (OffloadingEngine.tasklengthOffloadedToFog*100)/OffloadingEngine.totalTasklength;
		long percentTasklengthEnd = (OffloadingEngine.tasklengthOffloadedToEnd*100)/OffloadingEngine.totalTasklength;
		System.out.println();
		System.out.println("Cloud: "+percentTasklengthCloud+"\nFog: "+percentTasklengthFog+"\nEnd: "+percentTasklengthEnd);
		
        for(FogDevice fogdevice:fogDevices) {
        	String fogdeviceName = fogdevice.getName();
//        	double fogdeviceAverageMips = fogdevice.getAverageMips();
//        	double fogdeviceBw = fogdevice.getUplinkBandwidth();
//        	double fogdeviceCostPerMips = fogdevice.getRatePerMips();
        	if(fogdeviceName.equals("cloud")) {
//        		Config.bandwidth[0] = Fuzzy.LOW;
//        		Config.CPUspeed[0] = getFuzzyCpu(fogdeviceAverageMips); 
//        		Config.CostPerMips[0] = getFuzzyCost(fogdeviceCostPerMips); 
//        		Config.deadline[0] = getFuzzyDeadline(deadline);
        		Config.taskLength[0] = getFuzzyTaskLength(percentTasklengthCloud);
        	}
        	else if(fogdeviceName.equals("f-0")) {
//        		Config.bandwidth[1] = Fuzzy.AVERAGE;
//        		Config.CPUspeed[1] = getFuzzyCpu(fogdeviceAverageMips); 
//        		Config.CostPerMips[1] = getFuzzyCost(fogdeviceCostPerMips);  
//        		Config.deadline[1] = getFuzzyDeadline(deadline);
        		Config.taskLength[1] = getFuzzyTaskLength(percentTasklengthFog);
        	}
        	else if(fogdeviceName.equals("m-0-0")) {
//        		Config.bandwidth[2] = Fuzzy.HIGH;
//        		Config.CPUspeed[2] = getFuzzyCpu(fogdeviceAverageMips); 
//        		Config.CostPerMips[2] = getFuzzyCost(fogdeviceCostPerMips); 
//        		Config.deadline[2] = getFuzzyDeadline(deadline);
        		Config.taskLength[2] = getFuzzyTaskLength(percentTasklengthEnd);
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
