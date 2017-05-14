package com.kit.pulse.vo;

import java.util.Date;

public class PulseRateDataVO {
	
	private Date pulseRateLogTime;
	private Integer pulseRateCount;
	
	public Date getPulseRateLogTime() {
		return pulseRateLogTime;
	}
	
	public void setPulseRateLogTime(Date pulseRateLogTime) {
		this.pulseRateLogTime = pulseRateLogTime;
	}
	
	public Integer getPulseRateCount() {
		return pulseRateCount;
	}
	
	public void setPulseRateCount(Integer pulseRateCount) {
		this.pulseRateCount = pulseRateCount;
	}
	
}
