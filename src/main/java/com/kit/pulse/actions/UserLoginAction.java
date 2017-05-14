package com.kit.pulse.actions;

import com.google.gson.Gson;
import com.kit.pulse.constants.RolesConstants;
import com.kit.pulse.response.Data;
import com.kit.pulse.response.ErrorData;
import com.kit.pulse.response.PatientData;
import com.kit.pulse.response.Response;
import com.kit.pulse.response.ResponseConstants;
import com.kit.pulse.response.UserData;
import com.kit.pulse.serviceimpl.PatientService;
import com.kit.pulse.serviceimpl.UserServiceImpl;
import com.kit.pulse.vo.PatientVO;
import com.kit.pulse.vo.UserVO;
import com.opensymphony.xwork2.ActionSupport;

public class UserLoginAction {

	private String email;
	private String password;
	
	private Response response;
	
	public UserLoginAction() {
		response = new Response();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Response getResponse() {
		return response;
	}

	private void validate() throws Exception {
		if(email == null) {
			throw new Exception("Email not provided.");
		}
		
		if(password == null) {
			throw new Exception("Password not provided.");
		}		
	}
	
	public String execute() throws Exception {
		
		validate();
		
		System.out.println(email + " " + password);
		
		UserVO userVO = new UserServiceImpl().loginUser(email, password);
		System.out.println(userVO.getEmail());
		if(userVO != null) {
			
			UserData userData = new UserData();
			userData.setFullName(userVO.getFullName());
			userData.setEmail(userVO.getEmail());
			userData.setContactNumber(userVO.getContactNumber());
			userData.setRole(userVO.getRole());
			userData.setFirebaseToken(userVO.getFirebaseToken());
			System.out.println("1");
			
			Data data = new Data();
			data.setUserData(userData);
			
			if(userVO.getRole().equals(RolesConstants.PATIENT)) {
				PatientVO patientVO = new PatientService().getPatientByUserId(userVO);
				PatientData patientData = new PatientData();
				patientData.setDateOfBirth(patientVO.getDateOfBirth().getTime());
				patientData.setDeviceId(patientVO.getDeviceId());
				patientData.setGender(patientVO.getGender());
				data.setPatientData(patientData);
			}
			
			System.out.println(email);
			response.setStatus(ResponseConstants.SUCCESS);
			
			response.setData(data);
			System.out.println(new Gson().toJson(response, Response.class));
		} else {
			
			ErrorData errorData = new ErrorData();
			errorData.setErrorMessage("Login credentials are invalid.");
			
			response.setStatus(ResponseConstants.ERROR);
			Data data = new Data();
			data.setErrorData(errorData);
			response.setData(data);
			
		}
		
		return ActionSupport.SUCCESS;
	}
}
