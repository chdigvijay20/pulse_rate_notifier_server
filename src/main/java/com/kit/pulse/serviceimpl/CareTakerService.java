package com.kit.pulse.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.kit.pulse.entity.CareTaker;
import com.kit.pulse.entity.User;
import com.kit.pulse.factory.CareTakerFactory;
import com.kit.pulse.factory.DoctorFactory;
import com.kit.pulse.factory.UserFactory;
import com.kit.pulse.vo.UserVO;

public class CareTakerService {
	
	public List<UserVO> getPatientListToObserve(String email) {
		User user = new User();
		user.setEmail(email);
		user = UserFactory.instance().getUserByEmail(user);

		System.out.println("Got user " + user.getId());
		
		CareTaker careTaker = CareTakerFactory.instance().getDoctorByUserId(user);
		System.out.println("Got careTaker " + careTaker.getPatients());
		
		User patientUser = new User();
		List<ObjectId> objectIds = careTaker.getPatients();
		
		List<UserVO> userVOList = new ArrayList<UserVO>();
		for (ObjectId objectId : objectIds) {
			patientUser.setId(objectId);
			
			patientUser = UserFactory.instance().getUserByObjectId(patientUser);
			
			UserVO userVO = new UserVO();
			userVO.setFullName(patientUser.getFullName());
			userVO.setEmail(patientUser.getEmail());
			
			userVOList.add(userVO);
			
			System.out.println("Patient " + patientUser.getFullName() + patientUser.getEmail());
		}
		
		return userVOList;
	}

}
