package com.kit.pulse.factory;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.kit.pulse.dbutil.DBUtil;
import com.kit.pulse.entity.CareTaker;
import com.kit.pulse.entity.Doctor;
import com.kit.pulse.entity.User;

public class CareTakerFactory {
	
	private static CareTakerFactory careTakerFactory = new CareTakerFactory();
	
	public static CareTakerFactory instance() {
		return careTakerFactory;
	}
	
	public CareTaker getDoctorByUserId(User user) {
		Datastore datastore = DBUtil.getDatastore(CareTaker.class);

		Query<CareTaker> query = datastore.createQuery(CareTaker.class).filter("user_id", user.getId());
		
		return query.get();
	}
}
