package com.kit.pulse.factory;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.kit.pulse.dbutil.DBUtil;
import com.kit.pulse.entity.Doctor;
import com.kit.pulse.entity.User;

public class DoctorFactory {

	private static DoctorFactory doctorFactory = new DoctorFactory();
	
	public static DoctorFactory instance() {
		return doctorFactory;
	}
	
	public Doctor getDoctorByUserId(User user) {
		Datastore datastore = DBUtil.getDatastore(Doctor.class);

		Query<Doctor> query = datastore.createQuery(Doctor.class).filter("user_id", user.getId());
		
		return query.get();
	}
}
