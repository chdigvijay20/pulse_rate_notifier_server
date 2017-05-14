package com.kit.pulse.firebase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;

public class FCMNotification {

    // Method to send Notifications from server to client end.
    public final static String AUTH_KEY_FCM = "AAAA31oHL7Q:APA91bHr9ILIrFkmNuDvPJukRAolLOhRglrVRTs3Oc4htFVuy2-zlt33nf1s6nTBI62ncUfIk2XQjkegC-ipLDiE6iAduqnSnCRsjYtPZ4X0Oa17pe0EhymYhJ9j5Y2HTPUlaLPyBAk7";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public void pushFCMNotification(String name, String contactNumber, String DeviceIdKey) throws Exception {

        String authKey = AUTH_KEY_FCM;
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JsonObject info = new JsonObject();
        info.addProperty("title", "Emergency!!!");
        info.addProperty("body", "It seems like " + name +  " needs immediate attention.");
        info.addProperty("contactNumber", contactNumber);
        
        JsonObject data = new JsonObject();
        data.addProperty("to", DeviceIdKey.trim());
        data.add("data", info);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data.toString());
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        	
        System.out.println(response);
    }
}