package com.kit.pulse.serviceimpl;

import com.kit.pulse.entity.NormalRestingHeartRate;
import com.kit.pulse.factory.DataFactory;
import com.kit.pulse.vo.NormalRestingHeartRateVO;

public class DataService {
	
	public NormalRestingHeartRateVO getNormalPulseRate(Integer age) {
		
		NormalRestingHeartRateVO normalRestingHeartRateVO = new NormalRestingHeartRateVO();
		normalRestingHeartRateVO.setAge(age);
		NormalRestingHeartRate normalRestingHeartRate = DataFactory.instance().getNormalPulseRate(normalRestingHeartRateVO);
		
		normalRestingHeartRateVO.setMinimumPulseRate(normalRestingHeartRate.getMinimumHeartRate());
		normalRestingHeartRateVO.setMaximumPulseRate(normalRestingHeartRate.getMaximumHeartRate());
		
		return normalRestingHeartRateVO;
	}
}
