package com.mx.service.mqttService;

public interface MessageListener {
	
	public void receiveMessage(String deviceID, String type, String data1 , String data2 );

}
