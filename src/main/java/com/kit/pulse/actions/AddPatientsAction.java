package com.kit.pulse.actions;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.kit.pulse.entity.User;
import com.kit.pulse.response.Data;
import com.kit.pulse.response.Response;
import com.kit.pulse.response.ResponseConstants;
import com.kit.pulse.serviceimpl.UserServiceImpl;
import com.opensymphony.xwork2.ActionSupport;

public class AddPatientsAction {
	
	private String userEmail;
	private String patientEmails;
	private Response response;
	
	public AddPatientsAction() {
		response = new Response();
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getPatientEmails() {
		return patientEmails;
	}
	
	public void setPatientEmails(String patientEmails) {
		this.patientEmails = patientEmails;
	}
	
	public Response getResponse() {
		return response;
	}

	private void validate() throws Exception {
		if(userEmail == null) {
			throw new Exception("User Email not provided.");
		}
		
		if(patientEmails == null) {
			throw new Exception("Patient emails not provided.");
		}
	}
	
	public String execute() throws Exception {
		System.out.println("Here");
		validate();
		
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		List<String> invalidEmails  = userServiceImpl.addPatients(userEmail, patientEmails);
		
		System.out.println(invalidEmails + " " + invalidEmails.size());
		if(invalidEmails == null || invalidEmails.isEmpty()) {
			response.setStatus(ResponseConstants.SUCCESS);
		} else {
			response.setStatus(ResponseConstants.ERROR);
			
			Data data = new Data();
			data.setInvalidEmails(invalidEmails);
			response.setData(data);
		}
		
		System.out.println(new Gson().toJson(response));
				
		return ActionSupport.SUCCESS;
	}

}
