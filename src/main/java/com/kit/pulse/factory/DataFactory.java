package com.kit.pulse.factory;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.kit.pulse.dbutil.DBUtil;
import com.kit.pulse.entity.NormalRestingHeartRate;
import com.kit.pulse.vo.NormalRestingHeartRateVO;

public class DataFactory {
	
	private static DataFactory dataFactory = new DataFactory();
	
	public static DataFactory instance() {
		return dataFactory;
	}
	
	public NormalRestingHeartRate getNormalPulseRate(NormalRestingHeartRateVO normalRestingHeartRateVO) {
		
		Datastore datastore = DBUtil.getDatastore(NormalRestingHeartRate.class);
		Query<NormalRestingHeartRate> query = datastore.createQuery(NormalRestingHeartRate.class)
				 .field("minimum_age").lessThanOrEq(normalRestingHeartRateVO.getAge())
				 .field("maximum_age").greaterThanOrEq(normalRestingHeartRateVO.getAge());
		
		return query.get();
	}
}
