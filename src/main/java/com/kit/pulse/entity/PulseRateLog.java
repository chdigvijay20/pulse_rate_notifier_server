package com.kit.pulse.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity("pulse_rate_log")
public class PulseRateLog {
	
	@Id
	@Property("_id")
	private ObjectId id;
	
	@Property("pulse_rate_count")
	private Integer pulseRateCount;
	
	@Property("pulse_rate_log_time")
	private Date pulseRateLogTime;

	@Property("patient_id")
	private ObjectId patientId;
	
	public PulseRateLog() {
		
	}
	
	public PulseRateLog(Integer pulseRateCount, Date pulseRateLogTime, ObjectId patientId) {
		this.id = new ObjectId();
		this.pulseRateCount = pulseRateCount;
		this.pulseRateLogTime = pulseRateLogTime;
		this.patientId = patientId;
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Integer getPulseRateCount() {
		return pulseRateCount;
	}

	public void setPulseRateCount(Integer pulseRateCount) {
		this.pulseRateCount = pulseRateCount;
	}

	public Date getPulseRateLogTime() {
		return pulseRateLogTime;
	}

	public void setPulseRateLogTime(Date pulseRateLogTime) {
		this.pulseRateLogTime = pulseRateLogTime;
	}

	public ObjectId getPatientId() {
		return patientId;
	}

	public void setPatientId(ObjectId patientId) {
		this.patientId = patientId;
	}
}
