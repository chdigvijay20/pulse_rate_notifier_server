package com.kit.pulse.actions;

import com.kit.pulse.response.Data;
import com.kit.pulse.response.ErrorData;
import com.kit.pulse.response.Response;
import com.kit.pulse.response.ResponseConstants;
import com.kit.pulse.response.UserData;
import com.kit.pulse.serviceimpl.UserServiceImpl;
import com.kit.pulse.vo.UserVO;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateFirebaseTokenAction {
	
	private String email;
	private String firebaseToken;
	
	private Response response;
	
	public UpdateFirebaseTokenAction() {
		response = new Response();
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirebaseToken() {
		return firebaseToken;
	}
	
	public void setFirebaseToken(String firebaseToken) {
		this.firebaseToken = firebaseToken;
	}
	
	public Response getResponse() {
		return response;
	}
	
	private void validate() throws Exception {
		if(email == null) {
			throw new Exception("Email not provided.");
		}
		
		if(firebaseToken == null) {
			throw new Exception("Firebase token not provided.");
		}
	}
	
	public String execute() throws Exception {
		
		validate();
		
		UserVO userVO = new UserServiceImpl().updateFireBaseToken(email, firebaseToken);

		if(userVO != null) {
					
			UserData userData = new UserData();
			userData.setFullName(userVO.getFullName());
			userData.setEmail(userVO.getEmail());
			userData.setContactNumber(userVO.getContactNumber());
			userData.setRole(userVO.getRole());
			userData.setFirebaseToken(userVO.getFirebaseToken());
			
			response.setStatus(ResponseConstants.SUCCESS);
			Data data = new Data();
			data.setUserData(userData);
			response.setData(data);
			
		} else {
			
			ErrorData errorData = new ErrorData();
			errorData.setErrorMessage("Could not update firebase token.");
			
			response.setStatus(ResponseConstants.ERROR);
			Data data = new Data();
			data.setErrorData(errorData);
			response.setData(data);
			
		}
		
		return ActionSupport.SUCCESS;
	}

}
