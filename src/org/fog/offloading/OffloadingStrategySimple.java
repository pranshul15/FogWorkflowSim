package org.fog.offloading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cloudbus.cloudsim.core.CloudSim;
import org.fog.entities.FogDevice;
import org.fog.utils.FogLinearPowerModel;
import org.workflowsim.FileItem;
import org.workflowsim.Job;
import org.workflowsim.utils.Parameters.FileType;

public class OffloadingStrategySimple extends OffloadingStrategy{
	
	double LAN_Bandwidth = 100;//Mbps
	double WAN_Bandwidth = 40;//Mbps
	final double parameter = 10000;//Tuning parameters for calculating the transmission time of the transmitted data
	double deadline;
	private HashMap<String, List<Integer>> fileToDatacenter;
	private static FogLinearPowerModel powerModel;
	List<Integer> IdList = new ArrayList<Integer>();
	
	public OffloadingStrategySimple(List<FogDevice> fogdevices) {
		super(fogdevices);
		// TODO Auto-generated constructor stub
		deadline = getOffloadingEngine().getWorkflowEngine().DeadLine;
		fileToDatacenter = new HashMap<String, List<Integer>>();
	}
	public OffloadingStrategySimple() {
		fileToDatacenter = new HashMap<String, List<Integer>>();
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
			else{//Do not uninstall
				time3 = job.getCloudletLength() / fd.getAverageMips();
				energy3 = powerModel.getMaxPower() * job.getCloudletLength() / fd.getAverageMips();
			}
		}
		System.out.println("deadline : "+deadline+"; cloud : "+time1+"; fog : "+time2+"; mobile : "+time3);
//		System.out.println(energy1+",  "+energy2+",  "+energy3);
		if(deadline < Math.min(time1, time2)){//do not meet the time constraints
			System.out.println("Unloading does not meet time constraints");
			job.setoffloading(getmobile().getId());//Do not uninstall
		}
		else if(deadline > Math.max(time1, time2)){//meet the time constraints
			if(Math.min(energy3, Math.min(energy1, energy2)) == energy1){
				System.out.println("meet the time constraints，And offloading to the cloud consumes the least energy");
				job.setoffloading(getcloud().getId());//Offload to cloud
			}
			else if(Math.min(energy3, Math.min(energy1, energy2)) == energy2){
				System.out.println("meet the time constraints，And unloading to fog with minimal energy consumption");
				job.setoffloading(getFogNode().getId());//Offload to fog node
			}
			else{
				System.out.println("meet the time constraints，but not unloading the minimum energy consumption");
				job.setoffloading(getmobile().getId());//Do not uninstall
			}
		}
		else{//deadline is between time1 and time2
			if(time1 < time2 && energy1 < energy3){//Offloading to the cloud meets time constraints and consumes less energy
				System.out.println("Cloud meets time constraints，and low energy consumption");
				job.setoffloading(getcloud().getId());//Offload to cloud
			}
			else if(time1 > time2 && energy2 < energy3){//Unloading to fog nodes satisfies time constraints and consumes less energy
				System.out.println("Fog meets time constraints，and low energy consumption");
				job.setoffloading(getFogNode().getId());//Offload to fog node
			}
			else{
				System.out.println("There is a time constraint that is satisfied，but not unloading the minimum energy consumption");
				job.setoffloading(getmobile().getId());//Do not uninstall
			}
		}
//		if(deadline < Math.min(time1, time2))
//			job.setoffloading(getmobile().getId());//Do not uninstall
//		else{
//			if(time1 < time2){
//				job.setoffloading(getcloud().getId());//Offload to cloud
//				addFileToDatacenter(getcloud(), job);
//			}
//			else{
//				job.setoffloading(getFogNode().getId());//Offload to fog node
//				addFileToDatacenter(getFogNode(), job);
//			}
//		}
		System.out.println("job"+job.getCloudletId()+"Uninstall decision results: "+job.getoffloading()+":"+CloudSim.getEntityName(job.getoffloading()));//Output unloading decision results
		return time3;
	}
	
	/*
	@Override
	public double SelectDatacenter(Job job, double deadline) {
		// TODO Auto-generated method stub
		double time1 = 0, time2 = 0, time3 = 0;
		double energy1 = 0, energy2 = 0, energy3 = 0;
		powerModel = (FogLinearPowerModel) getmobile().getHost().getPowerModel();
		
		for(FogDevice fd: getFogDeviceLists()){
			if(fd.getName().equalsIgnoreCase("cloud")){ //Calculate the time required for offloading to the cloud
				time1 = job.getCloudletLength() / fd.getAverageMips()
						                  + getJobInputFileSize(fd, job) / parameter / WAN_Bandwidth;
				//Energy consumption required for offloading = idle power * cloud execution time + transmission power * (sending data size + receiving data size) / WAN bandwidth
				energy1 = powerModel.getStaticPower() * job.getCloudletLength() / fd.getAverageMips()
						     + powerModel.getSendPower() * (getJobInputFileSize(fd, job) + getJobOutputFileSize(job)) 
						                                                     / parameter / WAN_Bandwidth;
			}
			else if(fd.getName().contains("f")){ //Calculate time to unload to fog
				time2 = job.getCloudletLength() / fd.getAverageMips()
		                  + getJobInputFileSize(fd, job) / parameter / LAN_Bandwidth;
				//Energy consumption required for offloading = idle power * fog execution time + transmission power * (sending data size + receiving data size) / LAN bandwidth
				energy2 = powerModel.getStaticPower() * job.getCloudletLength() / fd.getAverageMips()
						+ powerModel.getSendPower() * (getJobInputFileSize(fd, job) + getJobOutputFileSize(job)) 
                                                                    / parameter / LAN_Bandwidth;
//				System.out.print("energy2 = ");
//				System.out.print(powerModel.getStaticPower()+" * "+(job.getCloudletLength() / fd.getHost().getTotalMips()));
//				System.out.print(" + "+ powerModel.getSendPower() +" * ("); 
//				System.out.print(getJobInputFileSize(fd, job));
//				System.out.print(" + "+getJobOutputFileSize(job)+" ) * 8/ 100 /");
//                System.out.println(LAN_Bandwidth);
//                System.out.println("energy2 = "+powerModel.getStaticPower() * job.getCloudletLength() / fd.getHost().getTotalMips() + " + "
//                                                  +powerModel.getSendPower() +" * "+(getJobInputFileSize(fd, job) + getJobOutputFileSize(job)) * 8 /100 / LAN_Bandwidth);
			}
			else{//Do not uninstall
				time3 = job.getCloudletLength() / fd.getAverageMips();
				energy3 = powerModel.getMaxPower() * job.getCloudletLength() / fd.getAverageMips();
			}
		}
		System.out.println("deadline : "+deadline+"; cloud : "+time1+"; fog : "+time2+"; mobile : "+time3);
//		System.out.println(energy1+",  "+energy2+",  "+energy3);
		if(deadline < Math.min(time1, time2)){//do not meet the time constraints
			System.out.println("Unloading does not meet time constraints");
			job.setoffloading(getmobile().getId());//Do not uninstall
		}
		else if(deadline > Math.max(time1, time2)){//meet the time constraints
			if(Math.min(energy3, Math.min(energy1, energy2)) == energy1){
				System.out.println("meet the time constraints，And offloading to the cloud consumes the least energy");
				job.setoffloading(getcloud().getId());//Offload to cloud
				addFileToDatacenter(getcloud(), job);
			}
			else if(Math.min(energy3, Math.min(energy1, energy2)) == energy2){
				System.out.println("meet the time constraints，And unloading to fog with minimal energy consumption");
				job.setoffloading(getFogNode().getId());//Offload to fog node
				addFileToDatacenter(getFogNode(), job);
			}
			else{
				System.out.println("meet the time constraints，but not unloading the minimum energy consumption");
				job.setoffloading(getmobile().getId());//Do not uninstall
			}
		}
		else{//deadline is between time1 and time2
			if(time1 < time2 && energy1 < energy3){//Offloading to the cloud meets time constraints and consumes less energy
				System.out.println("Cloud meets time constraints，and low energy consumption");
				job.setoffloading(getcloud().getId());//Offload to cloud
				addFileToDatacenter(getcloud(), job);
			}
			else if(time1 > time2 && energy2 < energy3){//Unloading to fog nodes satisfies time constraints and consumes less energy
				System.out.println("Fog meets time constraints，and low energy consumption");
				job.setoffloading(getFogNode().getId());//Offload to fog node
				addFileToDatacenter(getFogNode(), job);
			}
			else{
				System.out.println("There is a time constraint that is satisfied，but not unloading the minimum energy consumption");
				job.setoffloading(getmobile().getId());//Do not uninstall
			}
		}
//		if(deadline < Math.min(time1, time2))
//			job.setoffloading(getmobile().getId());//Do not uninstall
//		else{
//			if(time1 < time2){
//				job.setoffloading(getcloud().getId());//Offload to cloud
//				addFileToDatacenter(getcloud(), job);
//			}
//			else{
//				job.setoffloading(getFogNode().getId());//Offload to fog node
//				addFileToDatacenter(getFogNode(), job);
//			}
//		}
		System.out.println("job"+job.getCloudletId()+"unloading decision result: "+job.getoffloading()+":"+CloudSim.getEntityName(job.getoffloading()));//output offloading decision result
		return time3;
	}*/
	
	public double getJobInputFileSize(FogDevice device, Job job){
		double sendsize = 0;
		for(FileItem file : job.getFileList()){
			if(file.getType() == FileType.INPUT)
				if(!fileToDatacenter.containsKey(file.getName())|| !fileToDatacenter.get(file.getName()).contains(device.getId())){
					sendsize += file.getSize();
				}
		}
		return sendsize;
	}
	
	public double getJobOutputFileSize(Job job){
		double sendsize = 0;
		for(FileItem file : job.getFileList()){
			if(file.getType() == FileType.OUTPUT)
				sendsize += file.getSize();
		}
		return sendsize;
	}
	
	public void addFileToDatacenter(FogDevice device, Job job){
		job.setInputsize(getJobInputFileSize(device, job));
		job.setOutputsize(getJobOutputFileSize(job));
		for(FileItem file : job.getFileList()){
			//The job input and output files are placed in the data center
			if(fileToDatacenter.containsKey(file.getName()))
				IdList.addAll(fileToDatacenter.get(file.getName()));
			if(!IdList.contains(device.getId()))
				IdList.add(device.getId());
			fileToDatacenter.put(file.getName(), IdList);
			IdList.clear();
//			if(file.getType() == FileType.INPUT){//The job input file is placed in the data center
//				list = fileToDatacenter.get(file.getName());
//				if(!list.contains(device.getId()))
//					list.add(device.getId());
//				fileToDatacenter.put(file.getName(), list);
//			}
//			if(file.getType() == FileType.OUTPUT){//The job output file is placed in the data center
//				fileToDatacenter.put(file.getName(), new ArrayList<Integer>(device.getId()));
//			}
		}
	}
	
	public double getJobFileSize(Job job){
		double sendsize = 0;
		for(FileItem file : job.getFileList()){
			if(file.getType() == FileType.INPUT)
				sendsize += file.getSize();
		}
		return sendsize;
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
