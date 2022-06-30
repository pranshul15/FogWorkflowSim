package org.fog.offloading;

//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import org.fog.entities.OffloadingEngine;
//import org.cloudbus.cloudsim.core.CloudSim;
import org.fog.entities.FogDevice;
//import org.fog.utils.FogLinearPowerModel;
//import org.workflowsim.FileItem;
import org.workflowsim.Job;
//import org.workflowsim.utils.Parameters.FileType;

import org.fog.offloading.FuzzyLogic.AHP_TopsisCalculator;

public class OffloadingStrategyFuzzy extends OffloadingStrategy{
	
	public OffloadingStrategyFuzzy(List<FogDevice> fogdevices) {
		super(fogdevices);
		// TODO Auto-generated constructor stub
	}
	public OffloadingStrategyFuzzy() {
	}

	public double SelectDatacenter(Job job, double deadline) {
		// TODO Auto-generated method stub
//		System.out.println("\t\toffloading " + job.getCloudletId() + " " + job.getCloudletLength());
		long taskLength = job.getCloudletLength();
		List<FogDevice> fogdevices = getFogDeviceLists();
		String requiredDataCenter = AHP_TopsisCalculator.getRequiredDatacenter(fogdevices,deadline,taskLength);
		if(requiredDataCenter.equals("Cloud")) {
			OffloadingEngine.tasklengthOffloadedToCloud += taskLength;
			System.out.println(OffloadingEngine.tasklengthOffloadedToCloud);
			job.setoffloading(getcloud().getId());
		}
		else if(requiredDataCenter.equals("End Device")) {
			OffloadingEngine.tasklengthOffloadedToEnd += taskLength;
			System.out.println(OffloadingEngine.tasklengthOffloadedToEnd);
			job.setoffloading(getmobile().getId());
		}
		else {
			OffloadingEngine.tasklengthOffloadedToFog += taskLength;
			System.out.println(OffloadingEngine.tasklengthOffloadedToFog);
			job.setoffloading(getFogNode().getId());
		}
		return 0;
	}
	
	
	
	@Override
	public void SelectDatacenter(Job job) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void BeforeOffloading(double deadline) {
		// TODO Auto-generated method stub
		
	}
}
