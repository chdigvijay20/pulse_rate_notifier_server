package com.kit.pulse.factory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.kit.pulse.dbutil.DBUtil;
import com.kit.pulse.entity.PulseRateLog;
import com.kit.pulse.vo.PatientVO;

public class PulseRateDataFactory {
	
	private static PulseRateDataFactory pulseRateDataFactory = new PulseRateDataFactory();
	
	public static PulseRateDataFactory instance() {
		return pulseRateDataFactory;
	}
	
	public void createPulseRate(PulseRateLog beatLog) {
		Datastore datastore = DBUtil.getDatastore(PulseRateLog.class);
		datastore.save(beatLog);
	}
	
	public List<PulseRateLog> getPulseRateDataForPatient(PatientVO patientVO) {
		
		Datastore datastore = DBUtil.getDatastore(PulseRateLog.class);
		
		Query<PulseRateLog> query = datastore.createQuery(PulseRateLog.class).field("patient_id").equal(patientVO.getId());
		
		return query.asList();
	}

	public List<PulseRateLog> getPulseRateDataForPatient(PatientVO patientVO, Date date) {
		
		Datastore datastore = DBUtil.getDatastore(PulseRateLog.class);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		
		Date nextDate = calendar.getTime();
		
		Query<PulseRateLog> query = datastore.createQuery(PulseRateLog.class)
											 .field("patient_id").equal(patientVO.getId())
											 .field("pulse_rate_log_time").greaterThanOrEq(date)
											 .field("pulse_rate_log_time").lessThan(nextDate);
		
		System.out.println("Pulse rate data between " + date + " " + nextDate);
		
		return query.asList();
	}
}
