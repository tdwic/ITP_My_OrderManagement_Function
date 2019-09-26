package com.model;

import javax.swing.JTextField;

public class SalarySchema {
	private String txtpp5;
	private String  txtSchemaId;
	private String  txtpp1;
	private String  txtpp2;
	private String  txtpp3;
	private String  txtot;
	private String  txtpp4;
	private String role;
	
	public String getSchemaId() {
		return txtSchemaId;
	}
	
	public void setSchemaId (String txtSchemaId) {
		this.txtSchemaId = txtSchemaId;
		
	}
	
	public String getPeacesPrice1() {
		return txtpp1;
	}
	public void setPeacesPrice1(String pp1) {
		this.txtpp1 = pp1;
	}
	
	
	public String getPeacesPrice2() {
		return txtpp2;
	}
	public void setPeacesPrice2(String pp2) {
		this.txtpp2 = pp2;
	}
	
	
	public String getPeacesPrice3() {
		return txtpp3;
	}
	public void setPeacesPrice3(String pp3) {
		this.txtpp3 = pp3;
	}
	
	
	public String getPeacesPrice4() {
		return txtpp4;
	}
	public void setPeacesPrice4(String pp4) {
		this.txtpp4 = pp4;
	}
	
	
	public String getPeacesPrice5() {
		return txtpp5;
	}
	public void setPeacesPrice5(String pp5) {
		this.txtpp5 = pp5;
	}
	
	
	public String getOtRate() {
		return txtot;
	}
	public void setOtRate(String ot) {
		this.txtot = ot;
	}
	
	
/*	public String getRole() {
		return role;
	}
	public void setClientAddress(String role) {
		this.role = role;
	}
	
	public void name() {
		System.out.println(txtSchemaId);
	}
*/	

}
