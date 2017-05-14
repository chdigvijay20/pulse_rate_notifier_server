package com.kit.pulse.actions;

import java.util.ArrayList;
import java.util.List;

import com.kit.pulse.response.Data;
import com.kit.pulse.response.DoctorData;
import com.kit.pulse.response.Response;
import com.kit.pulse.serviceimpl.DoctorService;
import com.kit.pulse.vo.UserVO;
import com.opensymphony.xwork2.ActionSupport;

public class GetPatientListToObserveAction {
	
	String email;
	
	Response response;
	
	public GetPatientListToObserveAction() {
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

	private void validate() throws Exception {
		if(email == null) {
			throw new Exception("Email not provided.");
		}
	}
	
	public String execute() throws Exception {
		
		validate();
		
		List<UserVO> userVOList = new DoctorService().getPatientListToObserve(email);
		
		List<String> patientsNameList = new ArrayList<String>();
		List<String> patientsEmailList = new ArrayList<String>();
		
		for (UserVO userVO : userVOList) {
			patientsNameList.add(userVO.getFullName());
			patientsEmailList.add(userVO.getEmail());			
		}
		
		DoctorData doctorData = new DoctorData();
		doctorData.setPatientsNameList(patientsNameList);
		doctorData.setPatientsEmailList(patientsEmailList);		
		
		Data data = new Data();
		data.setDoctorData(doctorData);
		response.setData(data);
		
		return ActionSupport.SUCCESS;
	}
	
}
