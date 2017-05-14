package com.kit.pulse.actions;

import com.kit.pulse.response.Data;
import com.kit.pulse.response.PulseRateData;
import com.kit.pulse.response.Response;
import com.kit.pulse.response.ResponseConstants;
import com.kit.pulse.serviceimpl.PulseRateDataService;
import com.opensymphony.xwork2.ActionSupport;

public class LogPulseRateAction {
	
	private String deviceId;
	private Integer pulseRateCount;
	private Long pulseRateLogTime;

	private Response response;
	
	public LogPulseRateAction() {
		response = new Response();
	}

	public Integer getPulseRateCount() {
		return pulseRateCount;
	}

	public void setPulseRateCount(Integer pulseRateCount) {
		this.pulseRateCount = pulseRateCount;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Long getPulseRateLogTime() {
		return pulseRateLogTime;
	}

	public void setPulseRateLogTime(Long pulseRateLogTime) {
		this.pulseRateLogTime = pulseRateLogTime;
	}
	
	public Response getResponse() {
		return response;
	}
	
	private void validate() throws Exception {
		if(deviceId == null) {
			throw new Exception("Device Id not provided.");
		}
		
		if(pulseRateCount == null) {
			throw new Exception("Pulse rate count not provided.");
		}
		
		if(pulseRateLogTime == null) {
			throw new Exception("Pulse rate time not provided.");
		}		
	}

	public String execute() throws Exception {
		
		validate();
		
		new PulseRateDataService().logPulseRate(deviceId, pulseRateCount, pulseRateLogTime);;

		PulseRateData pulseRateData = new PulseRateData();
		pulseRateData.setPulseRateCount(pulseRateCount);
		pulseRateData.setPulseRateLogTime(pulseRateLogTime);
		
		response.setStatus(ResponseConstants.SUCCESS);
		Data data = new Data();
		data.setPulseRateData(pulseRateData);
		response.setData(data);
		
		return ActionSupport.SUCCESS;
	}
}
