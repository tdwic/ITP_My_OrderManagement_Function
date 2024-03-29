package com.util;

import java.util.ArrayList;

public class ID_Generator {
	
	public static final String Client_ID_Letter= "C";
	public static final String Order_ID_Letter= "O";

	public static String clientID;
	public static String orderID;
	
	public static int clientCnt;
	public static int orderCnt;
	
	
	public static String clientID_Generator(ArrayList<String> arrayList) {
		
		clientCnt = arrayList.size();
		
		clientCnt++;
		clientID = Client_ID_Letter+ String.format("%04d",clientCnt);
		
		if (arrayList.contains(clientID)) {
			clientCnt++;
			clientID = Client_ID_Letter+ String.format("%04d", clientCnt);
		}

		return clientID;

	}
	
	public static String orderID_Generator(ArrayList<String> arrayList) {
		
		orderCnt = arrayList.size();
		
		orderCnt++;
		orderID = Order_ID_Letter+ String.format("%04d",orderCnt);
		
		if (arrayList.contains(orderID)) {
			orderCnt++;
			orderID = Order_ID_Letter+ String.format("%04d", orderCnt);
		}

		return orderID;
	}
	

}
