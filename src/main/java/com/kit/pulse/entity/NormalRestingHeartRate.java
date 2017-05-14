package com.kit.pulse.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * @author digvijay
 *
 */
@Entity("normal_resting_heart_rate")
public class NormalRestingHeartRate {
	
	@Id
	@Property("_id")
	ObjectId id;
	
	@Property("minimum_age")
	Integer minimumAge;
	
	@Property("maximum_age")
	Integer maximumAge;
	
	@Property("minimum_heart_rate")
	Integer minimumHeartRate;
	
	@Property("maximum_heart_rate")
	Integer maximumHeartRate;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Integer getMinimumAge() {
		return minimumAge;
	}

	public void setMinimumAge(Integer minimumAge) {
		this.minimumAge = minimumAge;
	}

	public Integer getMaximumAge() {
		return maximumAge;
	}

	public void setMaximumAge(Integer maximumAge) {
		this.maximumAge = maximumAge;
	}

	public Integer getMinimumHeartRate() {
		return minimumHeartRate;
	}

	public void setMinimumHeartRate(Integer minimumHeartRate) {
		this.minimumHeartRate = minimumHeartRate;
	}

	public Integer getMaximumHeartRate() {
		return maximumHeartRate;
	}

	public void setMaximumHeartRate(Integer maximumHeartRate) {
		this.maximumHeartRate = maximumHeartRate;
	}

}
