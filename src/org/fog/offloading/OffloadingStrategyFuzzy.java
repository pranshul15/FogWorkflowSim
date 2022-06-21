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

import org.fog.offloading.FuzzyLogic.*;

public class OffloadingStrategyFuzzy extends OffloadingStrategy{
	
	double deadline;
	private HashMap<String,List<Integer>> fileToDatacenter;
	
	public OffloadingStrategyFuzzy(List<FogDevice> fogdevices) {
		super(fogdevices);
		// TODO Auto-generated constructor stub
		deadline = getOffloadingEngine().getWorkflowEngine().DeadLine;
		fileToDatacenter = new HashMap<String, List<Integer>>();
	}
	public OffloadingStrategyFuzzy() {
		fileToDatacenter = new HashMap<String, List<Integer>>();
	}

	public double SelectDatacenter(Job job, double deadline) {
		// TODO Auto-generated method stub
		
		return 0;
	}
	
	
	
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
