package com.kit.pulse.serviceimpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kit.pulse.dbutil.DBUtil;
import com.kit.pulse.entity.Patient;
import com.kit.pulse.entity.PulseRateLog;
import com.kit.pulse.entity.User;
import com.kit.pulse.factory.PatientFactory;
import com.kit.pulse.factory.PulseRateDataFactory;
import com.kit.pulse.response.PulseRateData;
import com.kit.pulse.response.PulseRateDataMultiple;
import com.kit.pulse.response.Response;
import com.kit.pulse.response.ResponseWrapper;
import com.kit.pulse.vo.PatientVO;
import com.kit.pulse.vo.PulseRateDataVO;
import com.kit.pulse.vo.UserVO;

public class PulseRateDataService {

	public void logPulseRate(String deviceId, Integer pulseRateCount, Long pulseRateLogTime) throws Exception {
		
		PatientVO patientVO = new PatientVO();
		patientVO.setDeviceId(deviceId);
		Patient patient = PatientFactory.instance().getPatientByDeviceId(patientVO);
		
		if(patient == null) {
			throw new Exception("The device has not been attached to any user.");
		}
		
		PulseRateLog pulseRateLog = new PulseRateLog();
		pulseRateLog.setPulseRateCount(pulseRateCount);
		pulseRateLog.setPulseRateLogTime(new Date(pulseRateLogTime));
		pulseRateLog.setPatientId(patient.getId());
		
		PulseRateDataFactory.instance().createPulseRate(pulseRateLog);
		
	}

	public List<PulseRateDataVO> getPulseRateData(String email) {
		
		User user = new User();
		user.setEmail(email);
		
		PatientVO patientVO = new UserServiceImpl().getPatientFromEmail(user);
		System.out.println(patientVO.getId());
		List<PulseRateLog> pulseRateLogs = PulseRateDataFactory.instance().getPulseRateDataForPatient(patientVO);
		
		List<PulseRateDataVO> pulseRateDataVOs = new ArrayList<PulseRateDataVO>();

		for (PulseRateLog pulseRateLog : pulseRateLogs) {
			PulseRateDataVO pulseRateDataVO = getPulseRateDataVOFromPulseRateLog(pulseRateLog);
			System.out.println(pulseRateDataVO.getPulseRateLogTime().getTime());
			pulseRateDataVOs.add(pulseRateDataVO);
		}
		
		System.out.println(pulseRateDataVOs);
		return pulseRateDataVOs;
	}

	private PulseRateDataVO getPulseRateDataVOFromPulseRateLog(PulseRateLog pulseRateLog) {
		
		PulseRateDataVO pulseRateDataVO = new PulseRateDataVO();
		pulseRateDataVO.setPulseRateCount(pulseRateLog.getPulseRateCount());
		pulseRateDataVO.setPulseRateLogTime(pulseRateLog.getPulseRateLogTime());
		
		return pulseRateDataVO;
	}

	public void savePulseRateData(String pulseRateDataJsonString) {

		Gson gson = new Gson();
		
//		PulseRateDataMultiple pulseRateDataMultiple = gson.fromJson(pulseRateDataJsonString, PulseRateDataMultiple.class);
//		List<PulseRateData> pulseRateDatas = pulseRateDataMultiple.getPulseRateDataList();
//		System.out.println(pulseRateDataMultiple);
		
//		Type pulseRateDataListType = new TypeToken<ArrayList<PulseRateData>>(){}.getType();
//		List<PulseRateData> pulseRateDataList = gson.fromJson(pulseRateDataJsonString, pulseRateDataListType);
		
		Response response = gson.fromJson(pulseRateDataJsonString, Response.class);
		List<PulseRateData> pulseRateDataList = response.getPulseRateDataList();
		
		for (PulseRateData pulseRateData : pulseRateDataList) {
			System.out.println(pulseRateData.getDeviceId() + " " + pulseRateData.getPulseRateCount() + " " + pulseRateData.getPulseRateLogTime());
			
			PatientVO patientVO = new PatientVO();
			patientVO.setDeviceId(pulseRateData.getDeviceId());
			Patient patient = PatientFactory.instance().getPatientByDeviceId(patientVO);
			
			PulseRateLog pulseRateLog = new PulseRateLog();
			pulseRateLog.setPatientId(patient.getId());
			pulseRateLog.setPulseRateCount(pulseRateData.getPulseRateCount());
			pulseRateLog.setPulseRateLogTime(new Date(pulseRateData.getPulseRateLogTime()));
			
			PulseRateDataFactory.instance().createPulseRate(pulseRateLog);
		}
		
	}

	public List<PulseRateDataVO> getPulseRateData(String email, Date date) {
		User user = new User();
		user.setEmail(email);
		
		PatientVO patientVO = new UserServiceImpl().getPatientFromEmail(user);
		System.out.println(patientVO.getId());
		List<PulseRateLog> pulseRateLogs = PulseRateDataFactory.instance().getPulseRateDataForPatient(patientVO, date);
		
		List<PulseRateDataVO> pulseRateDataVOs = new ArrayList<PulseRateDataVO>();

		for (PulseRateLog pulseRateLog : pulseRateLogs) {
			PulseRateDataVO pulseRateDataVO = getPulseRateDataVOFromPulseRateLog(pulseRateLog);
//			System.out.println(pulseRateDataVO.getPulseRateLogTime().getTime());
			pulseRateDataVOs.add(pulseRateDataVO);
		}
		
		System.out.println(pulseRateDataVOs);
		return pulseRateDataVOs;
	}
	
}
