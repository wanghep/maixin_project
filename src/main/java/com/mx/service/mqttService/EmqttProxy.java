package com.mx.service.mqttService;

import java.net.URISyntaxException;

import org.fusesource.mqtt.client.MQTT;
//对emqtt直接进行操作的类
public class EmqttProxy {
	
	private final static String CONNECTION_STRING = "tcp://113.59.226.244:1883";
	private final static boolean CLEAN_START = true;
	private final static short KEEP_ALIVE = 30;// 低耗网络，但是又需要及时获取数据，心跳30s
	public final static long RECONNECTION_ATTEMPT_MAX = 6;
	public final static long RECONNECTION_DELAY = 2000;

	public final static int SEND_BUFFER_SIZE = 2 * 1024 * 1024;// 发送最大缓冲为2M
	
	private static org.fusesource.mqtt.client.MQTT mqtt = null;
	   
	public static org.fusesource.mqtt.client.MQTT getInstance() {
		if (mqtt == null) {
			mqtt = new org.fusesource.mqtt.client.MQTT();
			init();
		}
		return mqtt;
	}
	
	private static void init(){
		try {
			mqtt.setHost(CONNECTION_STRING);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 连接前清空会话信息
		mqtt.setCleanSession(CLEAN_START);
		// 设置重新连接的次数
		mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
		// 设置重连的间隔时间
		mqtt.setReconnectDelay(RECONNECTION_DELAY);
		// 设置心跳时间
		mqtt.setKeepAlive(KEEP_ALIVE);
		// 设置缓冲的大小
		mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
	}

}
