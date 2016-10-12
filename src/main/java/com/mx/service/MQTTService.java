package com.mx.service;

import java.util.ArrayList;

import com.mx.Util;
import com.mx.service.mqttService.EmqttProxy;
import com.mx.service.mqttService.MessageListener;
import org.fusesource.mqtt.client.*;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

@Service
public class MQTTService {
	private static MQTTService mMQTTService = null;

	private org.fusesource.mqtt.client.MQTT mqtt = null;
	private FutureConnection connection = null;
	private ArrayList<MessageListener> messageList = new ArrayList<MessageListener>();
	public static Topic[] topics = {
			//new Topic("china/beijing", QoS.EXACTLY_ONCE),// 2
			//new Topic("china/tianjin", QoS.AT_LEAST_ONCE),// 1
			//new Topic("china/henan", QoS.AT_MOST_ONCE) ;// 0
			new Topic("device/message",QoS.AT_MOST_ONCE)
	};
	private boolean isReceive = true;

	// service 启动的是就启动service
	//static {
	//	getInstance();
	//}
	/*
	public static MQTTService getInstance() {
		if (mMQTTService == null) {
			mMQTTService = new MQTTService();
		}
		return mMQTTService;
	}
	*/

	public MQTTService()
	{
		Init();
	}
	public void Init() {

		mqtt = EmqttProxy.getInstance();
		connection = mqtt.futureConnection();
		connection.connect();
		receiveMessage(); // 启动接收
	}

	
	public void sendMessage(String deviceId, String type, String data) {
		System.out.println("sendMessage");
		String message = type + " " + data;
		String topic = "server/" + deviceId;
		connection.publish(topic, message.getBytes(), QoS.EXACTLY_ONCE, false);
	}

	private void receiveMessage() {
		connection.subscribe(topics);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (isReceive) {
					Future<Message> futrueMessage = connection.receive();
					Message message = null;
					try {
						message = futrueMessage.await();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (message != null) {
						// message.ack();
						System.out.println("MQTTFutureClient.Receive Message "
								+ "Topic Title :" + message.getTopic()
								+ " context :"
								+ String.valueOf(message.getPayloadBuffer()));


						String context = message.getPayloadBuffer().ascii().toString();


						String[] contextArray = context.split(" ");

						if(contextArray.length > 2  )
						{
							String deviceId = contextArray[0]; // 8 bytes

							String type = contextArray[1];

							String data1 = contextArray[2];

							for (int i = 0; i < messageList.size(); i++) {
								messageList.get(i).receiveMessage(deviceId, type, data1, "");
							}
						}
					}
				}
			}
		}).start();
	}

	public void setReceive(boolean isReceive) {
		this.isReceive = isReceive;
	}

	public void registerMessageListener(MessageListener messageListener) {
		boolean isFind = false;
		for (int i = 0; i < messageList.size(); i++) {
			if (messageList.get(i).equals(messageListener)) {
				isFind = true;
				break;
			}
		}
		if (!isFind) {
			// messageListener.setRegister(true);
			messageList.add(messageListener);
		}
	}

	public void unRegisterMessageListener(MessageListener messageListener) {
		messageList.remove(messageListener);
		// messageListener.setRegister(false);
	}
}
