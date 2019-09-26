package com.util;

import java.util.ArrayList;

public class ID_Generator {
	
	public static final String Schema_ID_Letter= "Sc";

	public static String SchemaID;
	
	public static int SchemaScnt;
	
	
	public static String clientID_Generator(ArrayList<String> arrayList) {
		
		SchemaScnt = arrayList.size();
		
		SchemaScnt++;
		SchemaID = Schema_ID_Letter+ String.format("%04d",SchemaScnt);
		
		if (arrayList.contains(SchemaID)) {
			SchemaScnt++;
			SchemaID = Schema_ID_Letter+ String.format("%04d", SchemaScnt);
		}

		return SchemaID;

	}

}
