package com.kit.pulse.factory;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.kit.pulse.dbutil.DBUtil;
import com.kit.pulse.entity.User;
import com.kit.pulse.vo.UserVO;

public class UserFactory {

	private static UserFactory userFactory = new UserFactory();

	public static UserFactory instance() {
		return userFactory;
	}

	public void createUser(User user) {
		Datastore datastore = DBUtil.getDatastore(User.class);
		datastore.save(user);
	}
	
	public User getUserByEmail(User user) {
		Datastore datastore = DBUtil.getDatastore(User.class);
		
		User tempUser = datastore.createQuery(User.class)
								.field("email").equal(user.getEmail())
								.get();
		
		return tempUser;
	}

	public void updateFirebaseToken(String email, String firebaseToken) {
		
		Datastore datastore = DBUtil.getDatastore(User.class);
		
		Query<User> query = datastore.createQuery(User.class).field("email").equal(email);
		UpdateOperations<User> updateOperations = datastore.createUpdateOperations(User.class).set("firebase_token", firebaseToken);
		
		datastore.update(query, updateOperations);
	}

	public User getUserByObjectId(User user) {
		Datastore datastore = DBUtil.getDatastore(User.class);
		
		User tempUser = datastore.createQuery(User.class)
								.field("_id").equal(user.getId())
								.get();
		
		return tempUser;
	}
}
