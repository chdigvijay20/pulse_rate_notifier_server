package com.kit.pulse.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.kit.pulse.constants.RolesConstants;
import com.kit.pulse.dbutil.DBUtil;
import com.kit.pulse.entity.CareTaker;
import com.kit.pulse.entity.Doctor;
import com.kit.pulse.entity.Patient;
import com.kit.pulse.entity.User;
import com.kit.pulse.factory.PatientFactory;
import com.kit.pulse.factory.UserFactory;
import com.kit.pulse.response.Response;
import com.kit.pulse.vo.PatientVO;
import com.kit.pulse.vo.UserVO;

public class UserServiceImpl {
	
	public void createUser(UserVO userVO) {
		User user = getUserFromUserVO(userVO);
		UserFactory.instance().createUser(user);
	}
	
	public ObjectId getUserId(User user) {
		ObjectId userId = null;
		
		User tempUser = UserFactory.instance().getUserByEmail(user);
		
		if (tempUser != null) {
			userId = tempUser.getId();
		}
		
		return userId;
	}
	
	public List<String> getInvalidEmails(List<User> users) {
		
		List<String> invalidEmails = new ArrayList<String>();
		
		int i = 0;
		for (User user : users) {
			User tempUser = UserFactory.instance().getUserByEmail(user);
			if(tempUser == null) {
				invalidEmails.add(user.getEmail());
			} else {
				users.set(i, tempUser);
			}
			
			++i;
		}
		
		return invalidEmails;
	}

	public List<String> addPatients(String userEmail, String patientEmails) {
		
		String[] emails = patientEmails.split(";");

		List<User> users = new ArrayList<User>();
		for (String email : emails) {
			User user = new User();
			user.setEmail(email);
			
			users.add(user);
		}
		
		User loggedInUser = new User();
		loggedInUser.setEmail(userEmail);
		loggedInUser = UserFactory.instance().getUserByEmail(loggedInUser);
		
		List<String> invalidEmails = null;
		if(loggedInUser != null) {
			invalidEmails = getInvalidEmails(users);
			
			System.out.println("No invalid emails.");
			if(invalidEmails.isEmpty()) {
				
				System.out.println("User is " + loggedInUser.getRole());
				
				if(loggedInUser.getRole().equals(RolesConstants.CARETAKER)) {
					
					savePatientEmailsInCaretaker(loggedInUser, users);
					System.out.println("saved patient email in caretaker.");
					
					saveCaretakerEmailInPatient(loggedInUser, users);
					System.out.println("saved caretaker email in patient.");
					
				} else if(loggedInUser.getRole().equals(RolesConstants.DOCTOR)) {
					
					savePatientEmailsInDoctor(loggedInUser, users);
					System.out.println("saved patient email in caretaker.");
					
					saveDoctorEmailInPatient(loggedInUser, users);
					System.out.println("saved caretaker email in patient.");
					
				}
			}
		}
		
		return invalidEmails;
	}


	private void savePatientEmailsInCaretaker(User loggedInUser, List<User> users) {
		List<ObjectId> objectIds = new ArrayList<ObjectId>();
		
		for (User user : users) {
			objectIds.add(user.getId());
		}
		
		CareTaker careTaker = new CareTaker();
		careTaker.setUserId(loggedInUser.getId());
		careTaker.setPatients(objectIds);
		
		Datastore datastore = DBUtil.getDatastore(CareTaker.class);
		datastore.save(careTaker);
	}
	
	private void saveCaretakerEmailInPatient(User loggedInUser, List<User> users) {
		for (User user : users) {
			Patient patient = PatientFactory.instance().getPatientByUserId(user);
			List<ObjectId> observers = patient.getObservers();
			if (observers == null) {
				observers = new ArrayList<ObjectId>();
			}
			observers.add(loggedInUser.getId());
			patient.setObservers(observers);
			
			PatientFactory.instance().updateObservers(patient);
		}
	}
	
	private void savePatientEmailsInDoctor(User loggedInUser, List<User> users) {
		
		List<ObjectId> objectIds = new ArrayList<ObjectId>();
		
		for (User user : users) {
			objectIds.add(user.getId());
		}
		
		Doctor doctor = new Doctor();
		doctor.setUserId(loggedInUser.getId());
		doctor.setPatients(objectIds);
		
		Datastore datastore = DBUtil.getDatastore(Doctor.class);
		datastore.save(doctor);
	}
	
	private void saveDoctorEmailInPatient(User loggedInUser, List<User> users) {
		for (User user : users) {
			Patient patient = PatientFactory.instance().getPatientByUserId(user);
			List<ObjectId> observers = patient.getObservers();
			if (observers == null) {
				observers = new ArrayList<ObjectId>();
			}
			observers.add(loggedInUser.getId());
			patient.setObservers(observers);
			
			PatientFactory.instance().updateObservers(patient);
		}
	}

	public PatientVO getPatientFromEmail(User user) {
		
		user = UserFactory.instance().getUserByEmail(user);
		Patient patient = PatientFactory.instance().getPatientByUserId(user);
		
		PatientVO patientVO = getPatientVOFromPatient(patient);
		
		return patientVO;
	}
	
	private PatientVO getPatientVOFromPatient(Patient patient) {

		PatientVO patientVO = new PatientVO();
		
		patientVO.setId(patient.getId());
		patientVO.setUserId(patient.getUserId());
		patientVO.setDateOfBirth(patient.getDateOfBirth());
		patientVO.setDeviceId(patient.getDeviceId());
		patientVO.setGender(patient.getGender());
		
		return patientVO;
	}

	private UserVO getUserVOFromUser(User tempUser) {
		
		UserVO tempUserVO = new UserVO();
		
		tempUserVO.setEmail(tempUser.getEmail());
		tempUserVO.setFullName(tempUser.getFullName());
		tempUserVO.setId(tempUser.getId());
		tempUserVO.setPassword(tempUser.getPassword());
		tempUserVO.setRole(tempUser.getRole());
		tempUserVO.setContactNumber(tempUser.getContactNumber());
		tempUserVO.setFirebaseToken(tempUser.getFirebaseToken());
		
		return tempUserVO;
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

	public void savePatientDetails(String email, String deviceId, Long dateOfBirth, String gender) throws Exception {
		
		User user = new User();
		user.setEmail(email);
		user = UserFactory.instance().getUserByEmail(user);
		
		if(user == null) {
			throw new Exception("No account exists with given email Id.");
		}
		
		Patient patient = new Patient();
		patient.setDeviceId(deviceId);
		patient.setDateOfBirth(new Date(dateOfBirth));
		patient.setGender(gender);
		patient.setUserId(user.getId());
		
		PatientFactory.instance().createPatient(patient);
	}

	public UserVO loginUser(String email, String password) {
		
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		
		User tempUser = UserFactory.instance().getUserByEmail(user);
		if(tempUser == null || (!user.getPassword().equals(tempUser.getPassword()))) {
			return null;
		}
		
		UserVO loggedInUserVO = getUserVOFromUser(tempUser);
		
		return loggedInUserVO;
	}

	public UserVO updateFireBaseToken(String email, String firebaseToken) {
		UserFactory.instance().updateFirebaseToken(email, firebaseToken);
		
		User user = new User();
		user.setEmail(email);
		
		user = UserFactory.instance().getUserByEmail(user);
		if(user == null) {
			return null;
		}
		
		return getUserVOFromUser(user);
	}
	
	public UserVO getUser(String email) {
		User user = new User();
		user.setEmail(email);
		
		user = UserFactory.instance().getUserByEmail(user);
		
		return getUserVOFromUser(user);
	}
}
