package com.kit.pulse.entity;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import com.google.gson.Gson;

/**
 * @author digvijay
 *
 */
@Entity("patient")
public class Patient {
	
	@Id
	@Property("_id")
	ObjectId id;
	
	@Property("device_id")
	String deviceId;
	
	@Property("user_id")
	ObjectId userId;
	
	@Property("date_of_birth")
	Date dateOfBirth;
	
	@Property("gender")
	String gender;
	
	@Property("observers")
	List<ObjectId> observers;
	
	public Patient() {
		
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

	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<ObjectId> getObservers() {
		return observers;
	}

	public void setObservers(List<ObjectId> observers) {
		this.observers = observers;
	}

	@Override
	public String toString() {
		return (new Gson()).toJson(this);
	}
}