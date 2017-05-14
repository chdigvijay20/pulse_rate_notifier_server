package com.kit.pulse.actions;

import com.kit.pulse.response.Data;
import com.kit.pulse.response.Response;
import com.kit.pulse.response.ResponseConstants;
import com.kit.pulse.response.UserData;
import com.kit.pulse.serviceimpl.UserServiceImpl;
import com.kit.pulse.vo.UserVO;
import com.opensymphony.xwork2.Action;

public class CreateUserAction {
	
	private String fullName;
	private String email;
	private String contactNumber;	
	private String password;
	private String role;
	
	private Response response;
	
	public CreateUserAction() {
		response = new Response();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
	
	public void validate() throws Exception {
		if(email == null) {
			throw new Exception("Email not provided.");
		}
		
		if(contactNumber == null) {
			throw new Exception("Contact number not provided.");
		}
		
		if(password == null) {
			throw new Exception("Password not provided.");
		}
		
		if(role == null) {
			throw new Exception("Role not provided.");
		}
	}
	
	public String execute() throws Exception {
		
		validate();
		
		UserVO userVO = new UserVO();
		userVO.setFullName(fullName);
		userVO.setEmail(email);
		userVO.setContactNumber(contactNumber);
		userVO.setPassword(password);
		userVO.setRole(role);
		
		new UserServiceImpl().createUser(userVO);
		
		UserData userData = new UserData();
		userData.setFullName(fullName);
		userData.setEmail(email);
		userData.setContactNumber(contactNumber);
		userData.setRole(role);
		
		response.setStatus(ResponseConstants.SUCCESS);
		Data data = new Data();
		data.setUserData(userData);
		response.setData(data);
		
		return Action.SUCCESS;
	}
}
