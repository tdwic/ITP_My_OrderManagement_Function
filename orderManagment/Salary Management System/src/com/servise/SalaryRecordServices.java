package com.servise;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.interfaces.ManagersSchema;

import com.model.SalarySchema;
import com.util.DBConnection;

import com.util.DBConnection;

import net.proteanit.sql.DbUtils;

public class SalaryRecordServices {
	
	private static Connection connection ;
	private PreparedStatement preStatement ;

	
	public void addSchema(SalarySchema schema) {
		try {
			connection = DBConnection.getDBConnection();
			String addSalary ="insert into salaryschema (schemaID,peacePrice1,peacePrice2,peacePrice3,peacePrice4,peacePrice5,OTrate,role) values (?, ? , ? , ? , ? , ?, ?, null)"; 
			
			preStatement = connection.prepareStatement(addSalary);
			
			preStatement.setString(1, schema.getSchemaId());
			preStatement.setString(2, schema.getPeacesPrice1());
			preStatement.setString(3, schema.getPeacesPrice2());
			preStatement.setString(4, schema.getPeacesPrice3());
			preStatement.setString(5, schema.getPeacesPrice4());
			preStatement.setString(6, schema.getPeacesPrice5());
			preStatement.setString(7, schema.getOtRate());
		//	preStatement.setString(8, schema.getRole());
			
			preStatement.executeUpdate() ;
			JOptionPane.showMessageDialog(null, "Record Inserted Sucessfully....");			
			connection.commit();
			
		} catch(SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {

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
	
	
	
	public void updateSchema(SalarySchema schema) {
		try {
			
			connection = DBConnection.getDBConnection();
			String updateSchema = "UPDATE unic.salaryschema SET  peaceprice1 = '"+schema.getPeacesPrice1()+"', peaceprice2 = '"+schema.getPeacesPrice2()+"', peacesPrice3 = '"+schema.getPeacesPrice3()+"', peaceprice4 = '"+schema.getPeacesPrice4()+"', peaceprice5 = '"+schema.getPeacesPrice5()+"', OTrate = '"+schema.getOtRate() /*+"', role = '"+schema.getRole() */+"' WHERE (clientID = '"+schema.getSchemaId()+"')";
	
			
			preStatement = connection.prepareStatement(updateSchema);
			
			preStatement.executeUpdate() ;
			JOptionPane.showMessageDialog(null, "Record no: "+schema.getSchemaId()+" Updated Sucessfully....");
			
		}catch(SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			
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


	
	public void removeSchema(String schemaID) {
		try {
			connection = DBConnection.getDBConnection();
			
			String deleteSchema = "delete  from salaryschema where salaryschema.schemaID = ?";
			
			preStatement = connection.prepareStatement(deleteSchema);
			
			preStatement.setString(1, schemaID);
			preStatement.executeUpdate() ;
			JOptionPane.showMessageDialog(null, "Record no: "+schemaID+" Removed Sucessfully....");
			
		}catch(SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			
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
	
	
	public ResultSet searchAndSort(String colom_name,String inputFieldName) {
		try {
			String selectSchema = "SELECT * FROM unic.salaryschema where "+colom_name+" like '"+"%"+inputFieldName+"%"+"'";
			connection = DBConnection.getDBConnection();
			preStatement = connection.prepareStatement(selectSchema);
			ResultSet resultSet = preStatement.executeQuery();
			return resultSet;
		} catch (Exception e) {
			return null;
		}
				
	}
	
	
	public ArrayList<String> getSchemaID(){
		ArrayList<String> schemaID_List = new ArrayList<String>();
		
		try {
			String schemaID_query = "select s.schemaID from salaryschema as s";
			connection = DBConnection.getDBConnection();
			preStatement = connection.prepareStatement(schemaID_query);
			ResultSet schemaIDs = preStatement.executeQuery();
			
			while (schemaIDs.next()) {
				schemaID_List.add(schemaIDs.getString(1));	
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
		
		return schemaID_List;
		
	
	}
	
	
	

}
