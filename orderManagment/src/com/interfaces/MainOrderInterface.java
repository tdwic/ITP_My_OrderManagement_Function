package com.interfaces;

import com.model.*;
import com.service.*;
import com.util.*;

import net.proteanit.sql.DbUtils;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class MainOrderInterface extends JFrame {

	private JPanel contentPane;
	private JTextField address;
	private JTextField emailAddress;
	private JTextField contactNumber;
	private JTextField nicNo;
	private JTextField companyName;
	private JTextField lastName;
	private JTextField firstName;
	private JTextField clientID;
	private JTextField quickSearchAmount;
	private JTextField textField_11;
	private JTextField txtOrderID;
	private JTextField productID;
	private JTextField quantity1;
	private JTextField supervicerID;
	private JTextField Location;
	private JTable table;
	
	
	
	private JComboBox<String> cmbProductType;
	private JComboBox<String> comboBox_2;
	private JComboBox<String> cmbSuperID;
	private JComboBox<String> cmpTransport;
	private JComboBox<String> cmbRemark;
	
	private JDateChooser dayOfNeed;
	private JDateChooser orderDate;
	private JDateChooser dayOfComplete;
	
	
	//Other Common variables
	
		private PreparedStatement preStatement ;
		
		private int warnin_message_button = JOptionPane.YES_NO_OPTION;
		private int warning_message_result;
		private int refreshValue;
		private static int numOfProducts, userInputQuantity;
		
		private String role = "SUP";
		private String QuickProductSearchID;
		private String QuickProductSearchKeyLock;
		
		private static Connection connection ;
		private static Statement statement ;
		private static boolean textBoxFull = false;
		
		//Other Common variables
		
		
		//Object Declaration
		
		Client client = new Client();
		Order order = new Order();
		ClientRecordsServices clientRecordsServices = new ClientRecordsServices();
		OrderRecordsServices orderRecordsServices = new OrderRecordsServices();
		CommonServices commonServices = new CommonServices();
		ID_Generator id_Generator = new ID_Generator();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		private JTextField Colorinput;
		
		
		//Object Declaration
		
		public boolean validateClientFields() {
			
			boolean validate1 = clientID.getText().matches("^[a-zA-Z0-9]*$") && clientID.getText().length() == 5 ;
			boolean validate2 = firstName.getText().matches("^[a-zA-Z]*$") && firstName.getText().length() > 2;
			boolean validate3 = lastName.getText().matches("^[a-zA-Z]*$") && lastName.getText().length() > 2;
			boolean validate4 = companyName.getText().matches("^[a-zA-Z0-9]*$") && companyName.getText().length() >= 4;
			boolean validate5 = nicNo.getText().matches("^[V0-9]*$") && nicNo.getText().length() == 10;
			boolean validate6 = contactNumber.getText().matches("^[0-9]*$") && contactNumber.getText().length() == 10;
			boolean validate7 = emailAddress.getText().matches("^[1-9a-zA-Z@.]*$") && emailAddress.getText().length() > 5;
			boolean validate8 = address.getText().matches("^[0-9a-zA-Z/,.]*$") && address.getText().length() > 2;
			
			if (validate1  && validate2 && validate3 && validate4 && validate5 && validate6 && validate7 && validate8 ) {
				return true;
			} else {
				return false;
			}
		}
		
		
		public boolean validateOrderFields() {
			boolean validate1 = txtOrderID.getText().matches("^[O0-9]*$") && txtOrderID.getText().length() == 5 ;
			boolean validate2 = productID.getText().matches("^[P0-9]*$") && productID.getText().length() == 5 && (cmbProductType.getSelectedIndex() == 1 || cmbProductType.getSelectedIndex() == 2) ;
			boolean validate3 = (orderDate.getDate() != null);
			boolean validate4 = (dayOfNeed.getDate() != null);
			boolean validate5 = (dayOfComplete.getDate() != null);
			boolean validate6 = quantity1.getText().matches("^[0-9]*$") && quantity1.getText().length() >=2 ;
			boolean validate7 = supervicerID.getText().matches("^[E0-9]*$") && supervicerID.getText().length() >=2 && (cmbSuperID.getSelectedIndex() != 0) ;
			boolean validate8 = cmpTransport.getSelectedItem().equals("Company") || cmpTransport.getSelectedItem().equals("Private");
			boolean validate9 = Colorinput.getText().length() > 2;
			boolean validate10 = Location.getText().matches("^[,/0-9A-Za-z]*$") &&Location.getText().length() > 2;
			boolean validate11 = cmbRemark.getSelectedIndex() == 1 || cmbRemark.getSelectedIndex() == 2;
			
			if (validate1  && validate2 && validate3 && validate4 && validate5 && validate6 && validate7 && validate8 && validate9 && validate10 && validate11 ) {
				return true;
			} else {
				return false;
			}

		}
		
		public void produtTypeFill() {
			try {
				String selectProductName= "select distinct productName from unic.product";
				connection = DbConnect.getDBConnection();
				preStatement = connection.prepareStatement(selectProductName);
				ResultSet productSet = preStatement.executeQuery();
				
				while (productSet.next()) {
					cmbProductType.addItem(productSet.getString("productName"));
					comboBox_2.addItem(productSet.getString("productName"));
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		
		/*public void produtColorFill() {
			try {
				String selectColour = "select distinct colour from unic.product";
				connection = DbConnect.getDBConnection();
				preStatement = connection.prepareStatement(selectColour);
				ResultSet colourSet = preStatement.executeQuery();
				
				while (colourSet.next()) {
					Colorinput.setText(colourSet.getString("colour"));
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}*/
		

		public void productIDview(String nameProduct) {
			String proName = nameProduct;
			
			try {
				String OrderID_query = "select * From unic.product p Where productName = '"+proName+"'";
				connection = DbConnect.getDBConnection();
				preStatement = connection.prepareStatement(OrderID_query);
				ResultSet resultSet = preStatement.executeQuery();
				while (resultSet.next()) {
					
						QuickProductSearchID = (resultSet.getString("productID"));
						cmbProductType.setSelectedItem(resultSet.getString("productName"));
				
					productID.setText(resultSet.getString("productID"));
					Colorinput.setText(resultSet.getString("colour"));
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
					
		}
		
		public void superviceNameID(String role) {
			
			
			try {
				String selectSupervicer = "SELECT DISTINCT FName,LName FROM user_main WHERE Role = '"+role+"'";
				connection = DbConnect.getDBConnection();
				preStatement = connection.prepareStatement(selectSupervicer);
				ResultSet productSet = preStatement.executeQuery();
				
				while (productSet.next()) {
					cmbSuperID.addItem(productSet.getString("FName")+" "+productSet.getString("LName"));
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		
		private boolean viewAllSupervicers(String supervicerName) {
			String superName = supervicerName;
			
			try {
				String selectClient = "SELECT EID,CONCAT(FName,' ', LName) AS Full_Name,NICNo,Role FROM (SELECT EID,FName,LName,NICNo,Role, CONCAT(FName,' ', LName) AS fullName FROM unic.user_main) result WHERE result.fullName = '"+superName+"'";
				connection = DbConnect.getDBConnection();
				preStatement = connection.prepareStatement(selectClient);
				ResultSet resultSet = preStatement.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(resultSet));
				int cnt = table.getRowCount();
				if (cnt >= 2) {
					supervicerID.setText(null);
					JOptionPane.showMessageDialog(null, "select one");
					return true;
				} else {
					table.setModel(new DefaultTableModel());
					return false;
				}
				
			} catch (Exception e) {
				return false;
			}
		}
		
		
		private boolean viewAllProducts(String productName) {
			int cnt = 0;
			String nameProduct = productName;
			table.setModel(new DefaultTableModel());
			try {
				String selectClient = "select * from product where productName = '"+nameProduct+"'";
				connection = DbConnect.getDBConnection();
				preStatement = connection.prepareStatement(selectClient);
				ResultSet resultSet = preStatement.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(resultSet));
				
				cnt = table.getRowCount();
				
				if (cnt >= 2) {
					productID.setText(null);
					JOptionPane.showMessageDialog(null, "select one");
					return true;
				} else {
					
					table.setModel(new DefaultTableModel());
					cnt = 0;
					return false;
				}
				
			} catch (Exception e) {
				return false;
			}
		}
		
		
		private void textSetClient() {

			client.setClientId(clientID.getText());
			client.setFirstName(firstName.getText());
			client.setLastName(lastName.getText());
			client.setCompanyName(companyName.getText());
			client.setNicNo(nicNo.getText());
			client.setContactNo(contactNumber.getText());
			client.setEmailAddress(emailAddress.getText());
			client.setClientAddress(address.getText());
		}

		private void textSetOrder() {
			order.setOrderID(txtOrderID.getText());
			order.setProductType(productID.getText());
			order.setOrderDate(dateFormat.format(orderDate.getDate()));
			order.setDayOfNeed(dateFormat.format(dayOfNeed.getDate()));
			order.setDayOfComplete(dateFormat.format(dayOfComplete.getDate()));
			order.setQuantity(quantity1.getText());
			order.setSuperviserID(supervicerID.getText());
			order.setTransportType(cmpTransport.getSelectedItem().toString());
			order.setColor(Colorinput.getText());
			order.setLocation(Location.getText());
			order.setRemark(cmbRemark.getSelectedItem().toString());
		}
		
		
		
		private void tableSelectItemClient() {
			JOptionPane.showMessageDialog(null, "CLient Only");
			int rowNumber = table.getSelectedRow();
			     clientID.setText(table.getValueAt(rowNumber, 0).toString());
			    firstName.setText(table.getValueAt(rowNumber, 1).toString());
			     lastName.setText(table.getValueAt(rowNumber, 2).toString());
			  companyName.setText(table.getValueAt(rowNumber, 3).toString());
			        nicNo.setText(table.getValueAt(rowNumber, 4).toString());
			contactNumber.setText(table.getValueAt(rowNumber, 5).toString());
			 emailAddress.setText(table.getValueAt(rowNumber, 6).toString());
			      address.setText(table.getValueAt(rowNumber, 7).toString());
		}
		
		private void tableSelectItemOrder() {
			JOptionPane.showMessageDialog(null, "Order Only");
			int rowNumber = table.getSelectedRow();
			
			   clientID.setText(table.getValueAt(rowNumber, 0).toString());
			  firstName.setText(table.getValueAt(rowNumber, 1).toString());
			   lastName.setText(table.getValueAt(rowNumber, 2).toString());
			companyName.setText(table.getValueAt(rowNumber, 3).toString());
			      nicNo.setText(table.getValueAt(rowNumber, 4).toString());
		  contactNumber.setText(table.getValueAt(rowNumber, 5).toString());
		   emailAddress.setText(table.getValueAt(rowNumber, 6).toString());
			    address.setText(table.getValueAt(rowNumber, 7).toString());
		}
		
		private void allOrderItems() throws ParseException {
			JOptionPane.showMessageDialog(null, "Both");
			int rowNumber = table.getSelectedRow();
			   
			txtOrderID.setText(table.getValueAt(rowNumber, 0).toString());
			clientID.setText(table.getValueAt(rowNumber, 1).toString());
			productID.setText(table.getValueAt(rowNumber, 2).toString());
			orderDate.setDate(dateFormat.parse(table.getValueAt(rowNumber, 3).toString()));
			dayOfNeed.setDate(dateFormat.parse(table.getValueAt(rowNumber, 4).toString()));
			dayOfComplete.setDate(dateFormat.parse(table.getValueAt(rowNumber, 5).toString()));
			quantity1.setText(table.getValueAt(rowNumber, 6).toString());
			supervicerID.setText(table.getValueAt(rowNumber, 7).toString());
			cmpTransport.setSelectedItem(table.getValueAt(rowNumber, 8).toString());
			//Colorinput.setText(table.getValueAt(rowNumber, 0).toString());
			Location.setText(table.getValueAt(rowNumber, 9).toString());
			if (table.getValueAt(rowNumber, 10).toString().equals("Processing")) {
				cmbRemark.removeItem("Processing");
				cmbRemark.setEditable(true);
				cmbRemark.setSelectedIndex(0);
			} else {
				cmbRemark.setSelectedIndex(1);
				
				cmbRemark.setEditable(false);
				
			}
			
			//Remark.setText(table.getValueAt(rowNumber, 10).toString());
			  
		}
		
		private void clearCustomerFields(){
			clientID.setText(null);
			firstName.setText(null);
			lastName.setText(null);	
			companyName.setText("None");	
			nicNo.setText(null);
			contactNumber.setText(null);				
			emailAddress.setText(null);
			address.setText(null);					
		}
		
		private void clearOrderFields() {
			txtOrderID.setText(null);
			cmbProductType.setSelectedIndex(0);
			productID.setText(null);
			quantity1.setText(null);
			cmbSuperID.setSelectedIndex(0);
			supervicerID.setText(null);
			cmpTransport.setSelectedIndex(0);
			Colorinput.setText(null);
			Location.setText(null);
			cmbRemark.setSelectedIndex(0);
			
		}
		
		
		private void allProductItems() {
			int rowNumber = table.getSelectedRow();
			JOptionPane.showMessageDialog(null, "Product Only");
			cmbProductType.setSelectedItem(table.getValueAt(rowNumber, 1).toString());
			productID.setText(table.getValueAt(rowNumber, 0).toString());
			Colorinput.setText(table.getValueAt(rowNumber, 4).toString());
			QuickProductSearchID = (table.getValueAt(rowNumber, 0).toString());
			
			System.out.println(QuickProductSearchID);
		}
		
		
		private void supervicerID() {
			JOptionPane.showMessageDialog(null, "Supervicer Only");
			int rowNumber = table.getSelectedRow();
			supervicerID.setText(table.getValueAt(rowNumber, 0).toString());
		
		}
		
		private Boolean IsClientCheckEmpty() {
			return ((clientID.getText().length()==0 || firstName.getText().length()==0) || lastName.getText().length()==0 );
		}
		
		private boolean IsOrderCheckEmpty() {
			return ((txtOrderID.getText().length() == 0 || productID.getText().length() == 0 ));
		}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainOrderInterface frame = new MainOrderInterface();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainOrderInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1028, 879);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 181));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Product Type");
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setBounds(12, 154, 86, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Client ID");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_1.setBounds(12, 200, 86, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("First Name");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_2.setBounds(12, 225, 86, 14);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("Last Name");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_3.setBounds(12, 250, 86, 14);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("Company Name");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_4.setBounds(12, 275, 113, 14);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("NIC No");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_5.setBounds(12, 300, 71, 14);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("Contact No");
		label_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_6.setBounds(12, 322, 94, 14);
		contentPane.add(label_6);
		
		JLabel label_7 = new JLabel("Email");
		label_7.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_7.setBounds(12, 346, 86, 14);
		contentPane.add(label_7);
		
		JLabel label_8 = new JLabel("Address");
		label_8.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_8.setBounds(12, 371, 86, 14);
		contentPane.add(label_8);
		
		
		
		JLabel label_9 = new JLabel("Amount");
		label_9.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_9.setBounds(269, 154, 86, 14);
		contentPane.add(label_9);
		
		JLabel label_10 = new JLabel("Availability");
		label_10.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_10.setBounds(491, 156, 86, 14);
		contentPane.add(label_10);
		
		JLabel label_12 = new JLabel("Product Type");
		label_12.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_12.setBounds(450, 228, 86, 14);
		contentPane.add(label_12);
		
		JLabel label_13 = new JLabel("Order Date");
		label_13.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_13.setBounds(450, 253, 86, 14);
		contentPane.add(label_13);
		
		JLabel label_14 = new JLabel("Day Of Need");
		label_14.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_14.setBounds(450, 278, 113, 14);
		contentPane.add(label_14);
	
		JLabel label_15 = new JLabel("Remark");
		label_15.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_15.setBounds(450, 457, 113, 14);
		contentPane.add(label_15);
		
		JLabel label_16 = new JLabel("Location");
		label_16.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_16.setBounds(450, 431, 113, 14);
		contentPane.add(label_16);
		
		JLabel label_17 = new JLabel("Color");
		label_17.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_17.setBounds(450, 405, 113, 14);
		contentPane.add(label_17);
		
		JLabel label_18 = new JLabel("Transport Type");
		label_18.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_18.setBounds(450, 380, 113, 14);
		contentPane.add(label_18);
		
		JLabel label_19 = new JLabel("Superviser ID");
		label_19.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_19.setBounds(450, 355, 94, 14);
		contentPane.add(label_19);
		
		JLabel label_20 = new JLabel("Quantity");
		label_20.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_20.setBounds(450, 330, 113, 14);
		contentPane.add(label_20);
		
		JLabel label_21 = new JLabel("Day Of Complete");
		label_21.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_21.setBounds(450, 303, 113, 14);
		contentPane.add(label_21);

		JLabel label_11 = new JLabel("Order ID");
		label_11.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_11.setBounds(450, 203, 86, 14);
		contentPane.add(label_11);
		
		
		
		
		JButton button = new JButton("New Order");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				warning_message_result = JOptionPane.showConfirmDialog (null, "Already a member of UNIC..","Warning",warnin_message_button);
				
				if(warning_message_result == JOptionPane.YES_OPTION){
					cmbRemark.addItem("Processing");
					clearCustomerFields();
					JOptionPane.showMessageDialog(null, "Please Select that client from given list or search the client..");
					table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.viewAllClients()));
					txtOrderID.setText(ID_Generator.orderID_Generator(orderRecordsServices.getOrderID()));
					txtOrderID.setEditable(false);
					clientID.setEditable(false);
					refreshValue = 1;
					
				}else {
					cmbRemark.addItem("Processing");
					clearCustomerFields();
					clientID.setText(ID_Generator.clientID_Generator(clientRecordsServices.getClientID()));
					txtOrderID.setText(ID_Generator.orderID_Generator(orderRecordsServices.getOrderID()));
					clientID.setEditable(false);
					table.setModel(new DefaultTableModel());
					
				}
				
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		button.setBounds(12, 408, 349, 56);
		contentPane.add(button);
		
		JButton button_1 = new JButton("Add Client");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean clientValidate = validateClientFields();
				
				if (clientValidate) {
					refreshValue = 1;
					textSetClient();	
					clientRecordsServices.addClient(client);
					table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.viewAllClients()));
				} else {
					JOptionPane.showMessageDialog(null, "Please Check  the Fields Again Carefully");
				}	
				
			}
		});
		button_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		button_1.setBounds(12, 471, 193, 45);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("Clear");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearCustomerFields();
				clientID.setEditable(true);
				table.setModel(new DefaultTableModel());
			}
		});
		button_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_2.setBounds(217, 470, 144, 46);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("Update Client");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean clientValidate = validateClientFields();
			
				if (clientValidate) {
					refreshValue = 1;
					textSetClient();
					clientRecordsServices.updateClient(client);
					table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.viewAllClients()));
				} else {
					JOptionPane.showMessageDialog(null, "Please Check  the Fields Again Carefully");
				}	
			}
		});
		button_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_3.setBounds(12, 523, 193, 40);
		contentPane.add(button_3);
		
		JButton button_4 = new JButton("Remove Client");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				if (IsClientCheckEmpty() != false) {
					JOptionPane.showMessageDialog(null, "Please Search Select your Client Details to Verify Before Done This Process, Because This Process Can't be Undone..");
				} else {
					warning_message_result = JOptionPane.showConfirmDialog (null, "Do you want to remove this Client..","Warning",warnin_message_button);
					if(warning_message_result == JOptionPane.YES_OPTION){
						refreshValue = 1;
						clientRecordsServices.removeCLient(clientID.getText());
						table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.viewAllClients()));
					}
					
				}
				
			}
				
		});
		
		button_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_4.setBounds(216, 523, 145, 40);
		contentPane.add(button_4);
		
		JButton button_6 = new JButton("Clear");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cmbRemark.addItem("Processing");
				refreshValue = 5;
			}
		});
		button_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_6.setBounds(662, 488, 130, 46);
		contentPane.add(button_6);
		
		JButton button_7 = new JButton("Remove Order");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (IsOrderCheckEmpty() != false) {
					JOptionPane.showMessageDialog(null, "Please Search Select your Order Details to Verify Before Done This Process, Because This Process Can't be Undone..");
				} else {
					warning_message_result = JOptionPane.showConfirmDialog (null, "Do you want to remove this Order..","Warning",warnin_message_button);
					if(warning_message_result == JOptionPane.YES_OPTION){
						refreshValue = 5;
						orderRecordsServices.removeOrder(txtOrderID.getText());
						clearCustomerFields();
						clearOrderFields();
						table.setModel(DbUtils.resultSetToTableModel(orderRecordsServices.viewAllOrders()));
						
					}
					
				}
				
				
				
				
			}
		});
		button_7.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_7.setBounds(662, 541, 130, 40);
		contentPane.add(button_7);
		
		JButton button_8 = new JButton("Update Order");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				refreshValue = 5;
				textSetClient();
				textSetOrder();
				orderRecordsServices.updateOrder(order, client);
				table.setModel(DbUtils.resultSetToTableModel(orderRecordsServices.viewAllOrders()));
				
			}
		});
		button_8.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_8.setBounds(450, 541, 200, 40);
		contentPane.add(button_8);
		
		JButton button_9 = new JButton("Place Order");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfProducts = orderRecordsServices.chekAvailability(productID.getText());
				userInputQuantity = Integer.parseInt(quantity1.getText());
				if (rootPaneCheckingEnabled) {
					if (validateClientFields()) {
						if (validateOrderFields()) {
							if (numOfProducts > userInputQuantity) {
								refreshValue = 5;
								textSetClient();
								textSetOrder();
								clientRecordsServices.addClient(client);
								orderRecordsServices.addOrder(order, client);
								orderRecordsServices.updateProductQuantity(productID.getText(), numOfProducts - userInputQuantity);
								table.setModel(DbUtils.resultSetToTableModel(orderRecordsServices.viewAllOrders()));
								
							} else {
								JOptionPane.showMessageDialog(null,"Insuficent Amount To Proceed");
							}
							
							
						} else {
							JOptionPane.showMessageDialog(null, "Please Check Order Fields Again Carefully");
						}
						
					} else  {
						JOptionPane.showMessageDialog(null, "Please Check Client Fields Again Carefully");
					}
					
					
				} else {

				}
				

			}
		});
		button_9.setFont(new Font("Tahoma", Font.BOLD, 17));
		button_9.setBackground(Color.GREEN);
		button_9.setBounds(450, 489, 200, 45);
		contentPane.add(button_9);
		
		JButton button_10 = new JButton("Refresh");
		button_10.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_10.setBounds(703, 613, 114, 23);
		contentPane.add(button_10);
		
		JButton button_11 = new JButton("All Orders");
		button_11.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_11.setBounds(703, 641, 114, 23);
		contentPane.add(button_11);
		
		JButton button_12 = new JButton("New button");
		button_12.setBounds(703, 689, 97, 25);
		contentPane.add(button_12);
		
		JButton button_13 = new JButton("Report Generate");
		button_13.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_13.setBounds(703, 732, 113, 42);
		contentPane.add(button_13);
		
		JButton button_5 = new JButton("Quick Search");
		button_5.setBounds(679, 154, 113, 21);
		contentPane.add(button_5);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 576, 603, 243);
		contentPane.add(scrollPane);
		
		
		
		
		
		
		
		address = new JTextField();
		address.setColumns(10);
		address.setBounds(145, 375, 216, 20);
		contentPane.add(address);
		
		emailAddress = new JTextField();
		emailAddress.setColumns(10);
		emailAddress.setBounds(145, 350, 216, 20);
		contentPane.add(emailAddress);
		
		contactNumber = new JTextField();
		contactNumber.setColumns(10);
		contactNumber.setBounds(145, 325, 216, 20);
		contentPane.add(contactNumber);
		
		nicNo = new JTextField();
		nicNo.setColumns(10);
		nicNo.setBounds(145, 300, 216, 20);
		contentPane.add(nicNo);
		
		companyName = new JTextField();
		companyName.setText("None");
		companyName.setColumns(10);
		companyName.setBounds(145, 275, 216, 20);
		contentPane.add(companyName);
		
		lastName = new JTextField();
		lastName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if (lastName.getText().equals("") != true) {
					
					if (clientID.getText().equals("") == true) {
						textBoxFull = false;
					}
					refreshValue = 1;
					table.setModel(DbUtils.resultSetToTableModel(commonServices.searchAndSort("customer","LName",lastName.getText())));
					textBoxFull = true;
				} else {
					if (textBoxFull != true) {
						table.setModel(new DefaultTableModel());
						textBoxFull = false;
					}
				}
				
			}
		});
		lastName.setColumns(10);
		lastName.setBounds(145, 250, 216, 20);
		contentPane.add(lastName);
		
		firstName = new JTextField();
		firstName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if (firstName.getText().equals("") != true) {
					
					if (clientID.getText().equals("") == true) {
						textBoxFull = false;
					}
					refreshValue = 1;
					table.setModel(DbUtils.resultSetToTableModel(commonServices.searchAndSort("customer","FName",firstName.getText())));
					textBoxFull = true;
				} else {
					if (textBoxFull != true) {
						table.setModel(new DefaultTableModel());
						textBoxFull = false;
					}
				}
				
			}
		});
		firstName.setColumns(10);
		firstName.setBounds(145, 225, 216, 20);
		contentPane.add(firstName);
		
		clientID = new JTextField();
		clientID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				boolean validate1 = clientID.getText().matches("^[C0-9]*$");
				String clientIdUserInput = clientID.getText();
				
				if (validate1 != true) {
					JOptionPane.showMessageDialog(null, "No Special Charcters, Only letter 'C' in UPERCASE.. \n'! @ # $ % ^ & * ( ) _ + : c '");
					clientID.setText(null);
					
				}else {
					if(clientIdUserInput.equals("") != true) {
						if (clientIdUserInput.equals("") == true) {
							textBoxFull = false;
						}
						refreshValue = 1;
						table.setModel(DbUtils.resultSetToTableModel(commonServices.searchAndSort("customer","clientID",clientIdUserInput)));
						textBoxFull = true;
					} else {
						if (textBoxFull != true) {
							table.setModel(new DefaultTableModel());
							textBoxFull = false;
						}
						
					}
				}
				
				
				
			}
		});
		clientID.setColumns(10);
		clientID.setBounds(145, 200, 216, 20);
		contentPane.add(clientID);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setToolTipText("Select Product Name");
		comboBox_2.setEditable(true);
		comboBox_2.setBounds(145, 150, 101, 22);
		contentPane.add(comboBox_2);
		
		
		quickSearchAmount = new JTextField();
		quickSearchAmount.setColumns(10);
		quickSearchAmount.setBounds(355, 154, 113, 20);
		contentPane.add(quickSearchAmount);
		
		textField_11 = new JTextField();
		textField_11.setEnabled(false);
		textField_11.setColumns(10);
		textField_11.setBounds(575, 156, 94, 20);
		contentPane.add(textField_11);
		
		txtOrderID = new JTextField();
		txtOrderID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String orderIdUserInput = txtOrderID.getText();
				
				boolean validate1 = txtOrderID.getText().matches("^[O0-9]*$") && txtOrderID.getText().length() <= 5;
				if (validate1) {
					if(orderIdUserInput.equals("") != true) {
						if (orderIdUserInput.equals("") == true) {
							textBoxFull = false;
						}
						refreshValue = 2;
						table.setModel(DbUtils.resultSetToTableModel(commonServices.searchAndSort("order","orderID",orderIdUserInput)));
						textBoxFull = true;
					} else {
						if (textBoxFull != true) {
							table.setModel(new DefaultTableModel());
							textBoxFull = false;
						}
						
					}
				}else {
					table.setModel(new DefaultTableModel());
					txtOrderID.setText(null);
					JOptionPane.showMessageDialog(null, "Specail Characters and Any LOWERCASE Letters Are Not Valid\nUse This Correct Format 'Eg:-O0001' ");
					
				}
				
				
				
			}
		});
		txtOrderID.setColumns(10);
		txtOrderID.setBounds(583, 203, 209, 20);
		contentPane.add(txtOrderID);
	
		cmbProductType = new JComboBox();
		cmbProductType.addItem("Please Select");
		cmbProductType.setToolTipText("Select Product Name");
		cmbProductType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean orderIDNull;
				if (e.getStateChange() == ItemEvent.SELECTED) {
					QuickProductSearchKeyLock = "lock";
					orderIDNull=viewAllProducts(e.getItem().toString());
					refreshValue = 3;
					if (orderIDNull == true) {
						productID.setText(null);
					}else {
						productIDview(e.getItem().toString());
					}
					numOfProducts = orderRecordsServices.chekAvailability(String.valueOf(productID.getText()));
					System.out.println(numOfProducts);
			    }	
				
			}
		});
		cmbProductType.setBounds(583, 228, 137, 20);
		contentPane.add(cmbProductType);
		
		productID = new JTextField();
		productID.setEditable(false);
		productID.setColumns(10);
		productID.setBounds(730, 226, 62, 22);
		contentPane.add(productID);
		
		orderDate = new JDateChooser();
		orderDate.setDateFormatString("yyyy-MM-dd");
		orderDate.setBounds(583, 253, 209, 20);
		contentPane.add(orderDate);
		
		dayOfNeed = new JDateChooser();
		dayOfNeed.setDateFormatString("yyyy-MM-dd");
		dayOfNeed.setBounds(583, 278, 209, 20);
		contentPane.add(dayOfNeed);
		
		dayOfComplete = new JDateChooser();
		dayOfComplete.setDateFormatString("yyyy-MM-dd");
		dayOfComplete.setBounds(583, 303, 209, 20);
		contentPane.add(dayOfComplete);
		
		quantity1 = new JTextField();
		quantity1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				boolean validate6 = quantity1.getText().matches("^[0-9]*$") && quantity1.getText().length() >=2 ;
				
			}
		});
		quantity1.setColumns(10);
		quantity1.setBounds(583, 328, 209, 20);
		contentPane.add(quantity1);
		
		cmbSuperID = new JComboBox();
		cmbSuperID.addItem("Please Select");
		cmbSuperID.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean supervicerSetNull;
				String supervicerIDresult;
				if (e.getStateChange() == ItemEvent.SELECTED) {
					refreshValue = 4;
					supervicerSetNull = viewAllSupervicers(e.getItem().toString());
					
					if (supervicerSetNull == true) {
						supervicerID.setText(null); 
					} else {
						supervicerIDresult = orderRecordsServices.superviceIDView(e.getItem().toString());
						supervicerID.setText(supervicerIDresult);
					}
					 
			    }
				
			}
		});
		cmbSuperID.setBounds(583, 354, 137, 20);
		contentPane.add(cmbSuperID);
		
		supervicerID = new JTextField();
		supervicerID.setEditable(false);
		supervicerID.setColumns(10);
		supervicerID.setBounds(730, 354, 62, 20);
		contentPane.add(supervicerID);
		
		cmpTransport = new JComboBox();
		cmpTransport.addItem("Select Transport Type");
		cmpTransport.addItem("Company");
		cmpTransport.addItem("Private");
		cmpTransport.setBounds(583, 377, 209, 20);
		contentPane.add(cmpTransport);
		
		Location = new JTextField();
		Location.setColumns(10);
		Location.setBounds(583, 428, 209, 20);
		contentPane.add(Location);
		
		Colorinput = new JTextField();
		Colorinput.setEditable(false);
		Colorinput.setBounds(583, 401, 209, 22);
		contentPane.add(Colorinput);
		Colorinput.setColumns(10);
		
		
		
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(65, 105, 225));
		panel.setBounds(0, 0, 1010, 94);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblOrderManagement = new JLabel("ORDER MANAGEMENT");
		lblOrderManagement.setForeground(Color.WHITE);
		lblOrderManagement.setFont(new Font("Showcard Gothic", Font.BOLD, 30));
		lblOrderManagement.setBounds(379, 13, 354, 51);
		panel.add(lblOrderManagement);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 0));
		panel_1.setBounds(0, 100, 1010, 14);
		contentPane.add(panel_1);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//cmbRemark.inde;
				validateOrderFields();

		//System.out.println(validate11);
			}
		});
		btnNewButton.setBounds(856, 270, 97, 25);
		contentPane.add(btnNewButton);
		
		cmbRemark = new JComboBox();
		cmbRemark.addItem("Please Select");
		cmbRemark.addItem("Processing");
		cmbRemark.addItem("Complete");
		cmbRemark.setBounds(583, 453, 209, 22);
		contentPane.add(cmbRemark);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				/*if (refreshValue > 7) {
					tableSelectItemOrder();
					
				} else if(refreshValue == 5) {
					allOrderItems();
				}else if(refreshValue == 6) {
					
				} else if(refreshValue == 7) {
					supervicerID();
				}else {
					
				}*/
				
				
				if (refreshValue == 1) {
					tableSelectItemClient();
				} else if (refreshValue == 2) {
					try {
						allOrderItems();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if (refreshValue == 3) {
					allProductItems();
				}else if (refreshValue == 4) {
					supervicerID();
				}
				
				
			}
		});
		
		
		
		
		produtTypeFill();
		superviceNameID(role);
		

	}
}
