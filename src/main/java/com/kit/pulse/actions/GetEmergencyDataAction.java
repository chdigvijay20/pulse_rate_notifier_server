package com.kit.pulse.actions;

import java.util.List;

import com.google.gson.Gson;
import com.kit.pulse.firebase.FirebaseNotificationSenderThread;
import com.kit.pulse.response.Data;
import com.kit.pulse.response.PatientData;
import com.kit.pulse.response.Response;
import com.kit.pulse.response.ResponseConstants;
import com.kit.pulse.response.UserData;
import com.kit.pulse.serviceimpl.PatientService;
import com.kit.pulse.serviceimpl.UserServiceImpl;
import com.kit.pulse.vo.UserVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author digvijay
 *
 */
public class GetEmergencyDataAction {
	
	private String email;

	private Response response;

	public GetEmergencyDataAction() {
		response = new Response();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
	public String execute() {

		PatientService patientService = new PatientService();
		
		List<String> emergencyContacts = patientService.getEmergencyContactNumbers(email);
		
		List<String> emergencyFirebaseTokens = patientService.getEmergencyFirebaseTokens(email);
		
		PatientData patientData = new PatientData();
		patientData.setEmergencyContacts(emergencyContacts);
		patientData.setEmergencyFirebaseTokens(emergencyFirebaseTokens);

		UserVO userVO = new UserServiceImpl().getUser(email);
		UserData userData = new UserData();
		userData.setContactNumber(userVO.getContactNumber());
		userData.setEmail(userData.getEmail());
		userData.setFullName(userVO.getFullName());
		
		Data data = new Data();
		data.setPatientData(patientData);
		data.setUserData(userData);
		
		response.setStatus(ResponseConstants.SUCCESS);
		response.setData(data);
		
		FirebaseNotificationSenderThread senderThread = new FirebaseNotificationSenderThread(userData.getFullName(), userData.getContactNumber(), emergencyFirebaseTokens);
		senderThread.start();
		
		System.out.println(new Gson().toJson(response));
		return ActionSupport.SUCCESS;
	}
}
