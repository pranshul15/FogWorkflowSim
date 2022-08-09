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



public class OffloadingStrategyFuzzyOff extends OffloadingStrategy{
	

	
	public OffloadingStrategyFuzzyOff(List<FogDevice> fogdevices) {
		super(fogdevices);
		// TODO Auto-generated constructor stub
	}
	public OffloadingStrategyFuzzyOff() {
	}

	public double SelectDatacenter(Job job, double deadline) {
		// TODO Auto-generated method stub
		double inputFileSize = getJobFileSize(job);
		double outputFileSize = getJobOutputFileSize(job);
		double cloudletLength = job.getCloudletLength();
		System.out.println("inputFileSize = " + inputFileSize + " " + "outputFileSize = " + outputFileSize + "cloudletLength = " + cloudletLength);
		job.setoffloading(getFogNode().getId());
		if(cloudletLength<5000) {
			if(inputFileSize<2000000) {
				if(outputFileSize<4000000) {
					job.setoffloading(getmobile().getId());
				}
				else if(outputFileSize<7000000) {
					job.setoffloading(getFogNode().getId());
				}
				else {
					job.setoffloading(getFogNode().getId());
				}
			}
			else if(inputFileSize<4000000) {
				if(outputFileSize<4000000) {
					job.setoffloading(getFogNode().getId());
				}
				else if(outputFileSize<7000000) {
					job.setoffloading(getFogNode().getId());
				}
				else {
					job.setoffloading(getFogNode().getId());
				}
			}
			else {
				if(outputFileSize<4000000) {
					job.setoffloading(getmobile().getId());
				}
				else if(outputFileSize<7000000) {
					job.setoffloading(getmobile().getId());
				}
				else {
					job.setoffloading(getmobile().getId());
				}
			}
		}
		else if(cloudletLength<10000) {
			if(inputFileSize<2000000) {
				if(outputFileSize<4000000) {
					job.setoffloading(getFogNode().getId());
				}
				else if(outputFileSize<7000000) {
					job.setoffloading(getFogNode().getId());
				}
				else {
					job.setoffloading(getcloud().getId());
				}
			}
			else if(inputFileSize<4000000) {
				if(outputFileSize<4000000) {
					job.setoffloading(getFogNode().getId());
				}
				else if(outputFileSize<7000000) {
					job.setoffloading(getFogNode().getId());
				}
				else {
					job.setoffloading(getcloud().getId());
				}
			}
			else {
				if(outputFileSize<4000000) {
					job.setoffloading(getmobile().getId());
				}
				else if(outputFileSize<7000000) {
					job.setoffloading(getFogNode().getId());
				}
				else {
					job.setoffloading(getFogNode().getId());
				}
			}
		}
		else {
			if(inputFileSize<2000000) {
				if(outputFileSize<4000000) {
					job.setoffloading(getcloud().getId());
				}
				else if(outputFileSize<7000000) {
					job.setoffloading(getcloud().getId());
				}
				else {
					job.setoffloading(getcloud().getId());
				}
			}
			else if(inputFileSize<4000000) {
				if(outputFileSize<4000000) {
					job.setoffloading(getcloud().getId());
				}
				else if(outputFileSize<7000000) {
					job.setoffloading(getcloud().getId());
				}
				else {
					job.setoffloading(getFogNode().getId());
				}
			}
			else {
				if(outputFileSize<4000000) {
					job.setoffloading(getFogNode().getId());
				}
				else if(outputFileSize<7000000) {
					job.setoffloading(getFogNode().getId());
				}
				else {
					job.setoffloading(getFogNode().getId());
				}
			}
		}
		return 0;
	}
	
	
	public double getJobOutputFileSize(Job job){
		double sendsize = 0;
		for(FileItem file : job.getFileList()){
			if(file.getType() == FileType.OUTPUT)
				sendsize += file.getSize();
		}
		return sendsize;
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

