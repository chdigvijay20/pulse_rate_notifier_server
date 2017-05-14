package com.kit.pulse.actions;

import com.google.gson.Gson;
import com.kit.pulse.response.Data;
import com.kit.pulse.response.PatientData;
import com.kit.pulse.response.Response;
import com.kit.pulse.response.ResponseConstants;
import com.kit.pulse.serviceimpl.UserServiceImpl;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class SavePatientDetailsAction {

	private String email;
	private String deviceId;
	private Long dateOfBirth;
	private String gender;
	
	private Response response;
	
	public SavePatientDetailsAction() {
		response = new Response();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Long getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void validate() throws Exception {
		if(deviceId == null) {
			throw new Exception("Device Id not provided.");
		}
		
		if(gender == null) {
			throw new Exception("Gender not provided.");
		}
		
		if(dateOfBirth == null) {
			throw new Exception("Date of Birth not provided.");
		}
		
		if(email == null) {
			throw new Exception("Email Id not provided.");
		}
	}

	public String execute() throws Exception {

		validate();
		
		new UserServiceImpl().savePatientDetails(email, deviceId, dateOfBirth, gender);
		
		PatientData patientData = new PatientData();
		patientData.setDeviceId(deviceId);
		patientData.setDateOfBirth(dateOfBirth);
		patientData.setGender(gender);
		
		response.setStatus(ResponseConstants.SUCCESS);
		Data data = new Data();
		data.setPatientData(patientData);
		response.setData(data);
		
		return Action.SUCCESS;
	}
}
