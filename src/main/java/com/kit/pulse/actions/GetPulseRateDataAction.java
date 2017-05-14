package com.kit.pulse.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kit.pulse.response.Data;
import com.kit.pulse.response.PulseRateData;
import com.kit.pulse.response.PulseRateDataMultiple;
import com.kit.pulse.response.Response;
import com.kit.pulse.response.ResponseConstants;
import com.kit.pulse.serviceimpl.PulseRateDataService;
import com.kit.pulse.vo.PulseRateDataVO;
import com.opensymphony.xwork2.ActionSupport;

public class GetPulseRateDataAction {
	
	private String email;
	// MM/dd/yyyy
//	private Date date;
	private String date;
	
	private Response response;
	
	public GetPulseRateDataAction() {
		response = new Response();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Response getResponse() {
		return response;
	}

	private void validate() throws Exception {
		if(email == null) {
			throw new Exception("Email not provided.");
		}
	}
	
	public String execute() throws Exception {
	
		validate();
	
		Date dateToSend;
		Calendar calendar = Calendar.getInstance();
		if(date == null) {
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			dateToSend = calendar.getTime();
		} else {
			dateToSend = new SimpleDateFormat("MM/dd/yyyy").parse(date);
		}
		System.out.println("date set to " + dateToSend);

		List<PulseRateDataVO> pulseRateDataVOs = new PulseRateDataService().getPulseRateData(email, dateToSend);
		
		ArrayList<PulseRateData> pulseRateDatas = new ArrayList<PulseRateData>();
		
		for (PulseRateDataVO pulseRateDataVO : pulseRateDataVOs) {
			PulseRateData pulseRateData = new PulseRateData();
			pulseRateData.setPulseRateCount(pulseRateDataVO.getPulseRateCount());
			pulseRateData.setPulseRateLogTime(pulseRateDataVO.getPulseRateLogTime().getTime());
			
			pulseRateDatas.add(pulseRateData);
		}
		
		PulseRateDataMultiple pulseRateDataMultiple = new PulseRateDataMultiple();
		pulseRateDataMultiple.setPulseRateDataList(pulseRateDatas);
		
		response.setStatus(ResponseConstants.SUCCESS);
		Data data = new Data();
		data.setPulseRateDataMultiple(pulseRateDataMultiple);
		response.setData(data);
		
		return ActionSupport.SUCCESS;
	}

}
