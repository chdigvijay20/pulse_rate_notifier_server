package com.kit.pulse.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import com.google.gson.Gson;

@Entity("doctor")
public class Doctor {
	
	@Id
	@Property("_id")
	ObjectId id;

	@Property("user_id")
	ObjectId userId;
	
	@Property("patients")
	List<ObjectId> patients;

	public Doctor() {
		
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public List<ObjectId> getPatients() {
		return patients;
	}

	public void setPatients(List<ObjectId> patients) {
		this.patients = patients;
	}

	@Override
	public String toString() {
		return (new Gson()).toJson(this);
	}
}
