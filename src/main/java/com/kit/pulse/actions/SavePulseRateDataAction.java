package com.kit.pulse.actions;

import com.kit.pulse.response.Response;
import com.kit.pulse.response.ResponseConstants;
import com.kit.pulse.serviceimpl.PulseRateDataService;
import com.opensymphony.xwork2.ActionSupport;

public class SavePulseRateDataAction {

	private String pulseRateDataJsonString;

	private Response response;
	
	public SavePulseRateDataAction() {
		response = new Response();
	}

	public String getPulseRateDataJsonString() {
		return pulseRateDataJsonString;
	}

	public void setPulseRateDataJsonString(String pulseRateDataJsonString) {
		this.pulseRateDataJsonString = pulseRateDataJsonString;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	private void validate() throws Exception {
		if(pulseRateDataJsonString == null) {
			throw new Exception("Pulse Rate Data not provided.");
		}
	}

	public String execute() throws Exception {
		System.out.println("inside execute..." + pulseRateDataJsonString);
		validate();
		System.out.println("validated...");
		new PulseRateDataService().savePulseRateData(pulseRateDataJsonString);
		System.out.println("after save...");
		response.setStatus(ResponseConstants.SUCCESS);
		System.out.println("status " + response.getStatus());
		
		return ActionSupport.SUCCESS;
	}
}
