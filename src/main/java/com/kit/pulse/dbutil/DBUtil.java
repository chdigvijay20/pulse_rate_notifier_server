package com.kit.pulse.dbutil;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class DBUtil {
	
	private static MongoClient mongoClient;
	
	public static MongoClient getMongoClient() {
		if(mongoClient == null) {
			mongoClient = new MongoClient(DBConstants.LOCAL_IP, DBConstants.PORT);
		}
		
		return mongoClient;
	}
	
	public static Datastore getDatastore(Class mappedClass) {
		Morphia morphia = new Morphia();
		morphia.map(mappedClass);
		
		return morphia.createDatastore(getMongoClient(), DBConstants.DB_NAME);
	}
}
