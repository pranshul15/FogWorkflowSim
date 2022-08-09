package org.fog.offloading;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.fog.entities.OffloadingEngine;
import org.fog.entities.FogDevice;
import org.workflowsim.FileItem;
import org.workflowsim.Job;
import org.workflowsim.utils.Parameters.FileType;
import org.fog.offloading.FuzzyLogic2.AHP_TopsisCalculator;
import org.fog.utils.FogLinearPowerModel;

public class OffloadingStrategyFuzzy2 extends OffloadingStrategy{
	
	double LAN_Bandwidth = 100;//Mbps
	double WAN_Bandwidth = 40;//Mbps
	final double parameter = 10000;//Tuning parameters for calculating the transmission time of the transmitted data
	double deadline;
	private static FogLinearPowerModel powerModel;
	List<Integer> IdList = new ArrayList<Integer>();
	
	
	public OffloadingStrategyFuzzy2(List<FogDevice> fogdevices) {
		super(fogdevices);
		// TODO Auto-generated constructor stub
	}
	public OffloadingStrategyFuzzy2() {
	}

	public double SelectDatacenter(Job job, double deadline) {
		// TODO Auto-generated method stub
		
		
		double time1 = 0, time2 = 0, time3 = 0;
		double energy1 = 0, energy2 = 0, energy3 = 0;
		powerModel = (FogLinearPowerModel) getmobile().getHost().getPowerModel();
		
		for(FogDevice fd: getFogDeviceLists()){
			if(fd.getName().equalsIgnoreCase("cloud")){ //Calculate the time required for offloading to the cloud
				time1 = job.getCloudletLength() / fd.getAverageMips()
						                  + getJobFileSize(job) / parameter / WAN_Bandwidth;
				//Energy consumption required for unloading = idle power * Cloud execution time + Transmission power * (send data size + received data size ) / WANbandwidth
				energy1 = powerModel.getStaticPower() * job.getCloudletLength() / fd.getAverageMips()
						     + powerModel.getSendPower() * getJobFileSize(job) / parameter / WAN_Bandwidth;
			}
			else if(fd.getName().contains("f")){ //Calculate time to unload to fog
				time2 = job.getCloudletLength() / fd.getAverageMips()
		                  + getJobFileSize(job) / parameter / LAN_Bandwidth;
				//Energy consumption required for unloading = idle power * Cloud execution time + Transmission power * (send data size + received data size ) / LANbandwidth
				energy2 = powerModel.getStaticPower() * job.getCloudletLength() / fd.getAverageMips()
						+ powerModel.getSendPower() * getJobFileSize(job) / parameter / LAN_Bandwidth;
//				System.out.print("energy2 = ");
//				System.out.print(powerModel.getStaticPower()+" * "+(job.getCloudletLength() / fd.getHost().getTotalMips()));
//				System.out.print(" + "+ powerModel.getSendPower() +" * ("); 
//				System.out.print(getJobInputFileSize(fd, job));
//				System.out.print(" + "+getJobOutputFileSize(job)+" ) * 8/ 100 /");
//                System.out.println(LAN_Bandwidth);
//                System.out.println("energy2 = "+powerModel.getStaticPower() * job.getCloudletLength() / fd.getHost().getTotalMips() + " + "
//                                                  +powerModel.getSendPower() +" * "+(getJobInputFileSize(fd, job) + getJobOutputFileSize(job)) * 8 /100 / LAN_Bandwidth);
			}
			else{//Do not offload
				time3 = job.getCloudletLength() / fd.getAverageMips();
				energy3 = powerModel.getMaxPower() * job.getCloudletLength() / fd.getAverageMips();
			}
		}
		
		
		double time[] = {time1,time2,time3};
		double energy[] = {energy1,energy2,energy3};
		
		long taskLength = job.getCloudletLength();
		List<FogDevice> fogdevices = getFogDeviceLists();
		String requiredDataCenter = AHP_TopsisCalculator.getRequiredDatacenter(fogdevices,job,time,energy);
		
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
	
	
	
	public static double getJobFileSize(Job job){
		double sendsize = 0;
		for(FileItem file : job.getFileList()){
			if(file.getType() == FileType.INPUT)
				sendsize += file.getSize();
		}
		return sendsize;
	}
	
	public static double getJobFileSizeOutput(Job job) {
		double sendsize = 0;
		for(FileItem file: job.getFileList()) {
			if(file.getType()==FileType.OUTPUT)
				sendsize += file.getSize();
		}
		return sendsize;
	}
}
