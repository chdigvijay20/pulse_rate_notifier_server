package com.kit.pulse.factory;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.kit.pulse.dbutil.DBUtil;
import com.kit.pulse.entity.Patient;
import com.kit.pulse.entity.User;
import com.kit.pulse.vo.PatientVO;
import com.mongodb.operation.UpdateOperation;

public class PatientFactory {
	
	private static PatientFactory patientFactory = new PatientFactory();
	
	public static PatientFactory instance() {
		return patientFactory;
	}
	
	public void createPatient(Patient patient) {
		Datastore datastore = DBUtil.getDatastore(Patient.class);
		datastore.save(patient);
	}

	public ObjectId getPatientId(Patient patient) {
		Datastore datastore = DBUtil.getDatastore(Patient.class);
		Query<Patient> query = datastore.createQuery(Patient.class)
					.field("machine_id").equal(patient.getDeviceId());
		
		Patient tempPatient = query.get();
		if (tempPatient == null) {
			return null;
		}
		
		return tempPatient.getId();
	}

	public Patient getPatientByUserId(User user) {
		
		Datastore datastore = DBUtil.getDatastore(Patient.class);
		
		Query<Patient> query = datastore.createQuery(Patient.class).field("user_id").equal(user.getId());
		
		return query.get();
	}

	public Patient getPatientByDeviceId(PatientVO patientVO) {
		Datastore datastore = DBUtil.getDatastore(Patient.class);
		
		Query<Patient> query = datastore.createQuery(Patient.class).field("device_id").equal(patientVO.getDeviceId());
		
		return query.get();
	}

	public void updateObservers(Patient patient) {
		
		Datastore datastore = DBUtil.getDatastore(Patient.class);
		
		Query<Patient> query = datastore.createQuery(Patient.class).field("_id").equal(patient.getId());
		UpdateOperations<Patient> updateOperation = datastore.createUpdateOperations(Patient.class).set("observers", patient.getObservers());
	
		datastore.update(query, updateOperation);
	}
}
