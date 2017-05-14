package com.kit.pulse.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.kit.pulse.entity.Patient;
import com.kit.pulse.entity.User;
import com.kit.pulse.factory.PatientFactory;
import com.kit.pulse.factory.UserFactory;
import com.kit.pulse.vo.PatientVO;
import com.kit.pulse.vo.UserVO;

public class PatientService {

	public Patient getPatientByUserId(User user) {
		return PatientFactory.instance().getPatientByUserId(user);
	}

	public List<String> getEmergencyContactNumbers(String email) {
		
		User user = new User();
		user.setEmail(email);
		
		user = UserFactory.instance().getUserByEmail(user);
		
		Patient patient = PatientFactory.instance().getPatientByUserId(user);
		
		List<String> contactNumbers = new ArrayList<String>();
		
		List<ObjectId> objectIds = patient.getObservers();
		for (ObjectId objectId : objectIds) {
			User tempUser = new User();
			tempUser.setId(objectId);
			
			tempUser = UserFactory.instance().getUserByObjectId(tempUser);
			
			contactNumbers.add(tempUser.getContactNumber());
		}
		
		return contactNumbers;
	}

	public List<String> getEmergencyFirebaseTokens(String email) {
		
		User user = new User();
		user.setEmail(email);
		
		user = UserFactory.instance().getUserByEmail(user);
		
		Patient patient = PatientFactory.instance().getPatientByUserId(user);
		
		List<String> firebaseTokens = new ArrayList<String>();
		
		List<ObjectId> objectIds = patient.getObservers();
		for (ObjectId objectId : objectIds) {
			User tempUser = new User();
			tempUser.setId(objectId);
			
			tempUser = UserFactory.instance().getUserByObjectId(tempUser);
			
			firebaseTokens.add(tempUser.getFirebaseToken());
		}
		
		return firebaseTokens;
	}

	public PatientVO getPatientByUserId(UserVO userVO) {
		User user = getUserFromUserVO(userVO);
		Patient patient = getPatientByUserId(user);
		PatientVO patientVO = new PatientVO();
		patientVO.setDateOfBirth(patient.getDateOfBirth());
		patientVO.setDeviceId(patient.getDeviceId());
		patientVO.setGender(patient.getGender());
		patientVO.setUserId(patient.getUserId());
		
		return patientVO;
	}
	
	private User getUserFromUserVO(UserVO userVO) {
		
		User tempUser = new User();
		
		tempUser.setEmail(userVO.getEmail());
		tempUser.setFullName(userVO.getFullName());
		tempUser.setId(userVO.getId());
		tempUser.setPassword(userVO.getPassword());
		tempUser.setRole(userVO.getRole());
		tempUser.setContactNumber(userVO.getContactNumber());
		tempUser.setFirebaseToken(userVO.getFirebaseToken());
		
		return tempUser;
	}

}
