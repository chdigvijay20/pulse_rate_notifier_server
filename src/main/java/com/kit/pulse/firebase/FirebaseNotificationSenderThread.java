package com.kit.pulse.firebase;

import java.util.List;

public class FirebaseNotificationSenderThread extends Thread {

	String patientName;
	String contactNumber;
	List<String> emergencyFirebaseTokens;
	FCMNotification fcmNotification;
	
	public FirebaseNotificationSenderThread(String patientName, String contactNumber, List<String> emergencyFirebaseTokens) {
		this.patientName = patientName;
		this.emergencyFirebaseTokens = emergencyFirebaseTokens;
		this.contactNumber = contactNumber;
		
		fcmNotification = new FCMNotification();
	}
	
	@Override
	public void run() {
		for (String token : emergencyFirebaseTokens) {			
			try {
				if (token != null) {
					fcmNotification.pushFCMNotification(patientName, contactNumber, token);
					System.out.println("Sent notification to token: " + token);					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
