package com.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.JOptionPane;
import com.model.*;
import com.util.DbConnect;


public class OrderRecordsServices {
	
	private static Connection connection ;
	private PreparedStatement preStatement ;
	
	
	
	
	public void addOrder(Order order, Client client) {
		try {
			connection = DbConnect.getDBConnection();
			String addClient ="insert into orders (clientID, orderID, productType, orderDate, dayOfNeed, quantity, superviserID, color, transportType, location, remark) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
			
			preStatement = connection.prepareStatement(addClient);
			
			preStatement.setString(1, client.getClientId());
			preStatement.setString(2, order.getOrderID());
			preStatement.setString(3, order.getProductType());
			preStatement.setString(4, order.getOrderDate());
			preStatement.setString(5, order.getDayOfNeed());
			preStatement.setString(6, order.getQuantity());
			preStatement.setString(7, order.getSuperviserID());
			preStatement.setString(8, order.getColor());
			preStatement.setString(9, order.getTransportType());
			preStatement.setString(10, order.getLocation());
			preStatement.setString(11, order.getRemark());
			
			preStatement.executeUpdate() ;
			JOptionPane.showMessageDialog(null, "Order Inserted Sucessfully....");
			connection.commit();
			
		} catch(SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			JOptionPane.showMessageDialog(null, "Error....");
		}finally {
			try {
				if (preStatement != null) {
					
					preStatement.close();
				}
				if(connection != null) {
					
					connection.close();
				}
			}catch(SQLException e) {
				
			}
		}
	}
	
	public  void updateOrder(String orderID, Order order) {
		
	}
	
	public void removeOrder(String orderID) {
		
	}
	
	
	
	
	public String superviceIDView(String supervicerName) {
		String supervicerID = null ;
		try {
			String SupervicerID_query = "SELECT EID FROM (SELECT EID, CONCAT(FName,' ', LName) AS fullName FROM unic.user_main) result WHERE result.fullName = '"+supervicerName+"'";
			connection = DbConnect.getDBConnection();
			preStatement = connection.prepareStatement(SupervicerID_query);
			ResultSet supervicerResult = preStatement.executeQuery();
			while (supervicerResult.next()) {
				supervicerID = supervicerResult.getString("EID");	
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		//return productID;
		return supervicerID;
	}
	
	
	public ArrayList<String> getOrderID(){
		ArrayList<String> orderID_List = new ArrayList<String>();
		
		try {
			String OrderID_query = "select 0.orderID from order as o";
			connection = DbConnect.getDBConnection();
			preStatement = connection.prepareStatement(OrderID_query);
			ResultSet orderIDs = preStatement.executeQuery();
			
			while (orderIDs.next()) {
				orderID_List.add(orderIDs.getString(1));	
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				if (preStatement != null) {
					preStatement.close();
				}
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				
			}
		}
		
		return orderID_List;
	}

}
