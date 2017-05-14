package com.kit.pulse.actions;

import com.kit.pulse.response.Data;
import com.kit.pulse.response.NormalRestingHeartRateData;
import com.kit.pulse.response.Response;
import com.kit.pulse.response.ResponseConstants;
import com.kit.pulse.serviceimpl.DataService;
import com.kit.pulse.vo.NormalRestingHeartRateVO;
import com.opensymphony.xwork2.ActionSupport;

public class GetNormalRestingHeartRateAction {
	Integer age;
	
	private Response response;
	
	public GetNormalRestingHeartRateAction() {
		response = new Response();
	}
	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	private void validate() throws Exception {
		if (age == null) {
			throw new Exception("Age not provided.");
		}
	}
	
	public String execute() throws Exception {
		
		validate();
		
		NormalRestingHeartRateVO normalRestingHeartRateVO = new DataService().getNormalPulseRate(age);
		
		NormalRestingHeartRateData heartRateData = new NormalRestingHeartRateData();
		heartRateData.setAge(normalRestingHeartRateVO.getAge());
		heartRateData.setMinimumPulseRate(normalRestingHeartRateVO.getMinimumPulseRate());
		heartRateData.setMaximumPulseRate(normalRestingHeartRateVO.getMaximumPulseRate());
		
		response.setStatus(ResponseConstants.SUCCESS);
		Data data = new Data();
		data.setNormalRestingHeartRateData(heartRateData);
		response.setData(data);
		
		return ActionSupport.SUCCESS;
	}

}
