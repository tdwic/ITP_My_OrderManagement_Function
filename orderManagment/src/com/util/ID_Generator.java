package com.util;

import java.util.ArrayList;

public class ID_Generator {
	public static final String Client_ID= "C000";
	public static final String Order_ID= "O000";
	
	public static String clientID_Generator(ArrayList<String> arrayList) {
		String clientID;
		int clientCnt = arrayList.size();
		
		clientCnt++;
		clientID = Client_ID + clientCnt;
		
		if (arrayList.contains(clientID)) {
			clientCnt++;
			clientID = Client_ID + clientCnt;
		}

		return clientID;
		
		
	}
	
	public static String orderID_Generator(ArrayList<String> arrayList) {
		String orderID;
		int orderCnt = arrayList.size();
		
		orderCnt++;
		orderID = Order_ID + orderCnt;
		
		if (arrayList.contains(orderID)) {
			orderCnt++;
			orderID = Order_ID + orderCnt;
		}

		return orderID;
	}
	

}
