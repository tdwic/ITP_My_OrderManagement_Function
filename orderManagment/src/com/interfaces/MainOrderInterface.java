package com.interfaces;

import com.model.*;
import com.service.*;
import com.util.*;

import net.proteanit.sql.DbUtils;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private JTextField txtOrderID;
	private JTextField productID;
	private JTextField quantity1;
	private JTextField supervicerID;
	private JTextField Location;
	
	private JButton placeOrderButton;
	private JButton orderClearButton;
	private JButton orderUpdate;
	private JButton orderRemove;
	private JButton clientAddButton;
	private JButton clientRemoveButton;
	private JButton clientUpdateButton;
	private JButton clientClearButton;
	
	private JTable table;

	private JComboBox<String> cmbProductType;
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
		private int refreshValue = 0;
		private static int numOfProducts, userInputQuantity;
		
		private String role = "SUP";
		private String QuickProductSearchID;
		private String QuickProductSearchKeyLock;
		
		private static Connection connection ;
		private static Statement statement ;
		private static boolean textBoxFull = false;
		private static boolean processingRemoved = false;
		private static boolean orderRetriview = false;
		
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
			boolean validate2 = productID.getText().matches("^[P0-9]*$") && productID.getText().length() == 5 && cmbProductType.getSelectedIndex() != 0 ;
			boolean validate3 = (orderDate.getDate() != null);
			boolean validate4 = (dayOfNeed.getDate() != null);
			boolean validate5 = (dayOfComplete.getDate() != null);
			boolean validate6 = quantity1.getText().matches("^[0-9]*$") && quantity1.getText().length() >=2 ;
			boolean validate7 = supervicerID.getText().matches("^[E0-9]*$") && supervicerID.getText().length() >=2 && (cmbSuperID.getSelectedIndex() != 0) ;
			boolean validate8 = cmpTransport.getSelectedItem().equals("Company") || cmpTransport.getSelectedItem().equals("Private");
			boolean validate9 = Colorinput.getText().length() > 2;
			boolean validate10 = Location.getText().matches("^[0-9A-Za-z,./-]*$") &&Location.getText().length() > 2;
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
			Location.setText(table.getValueAt(rowNumber, 9).toString());
			
			if (table.getValueAt(rowNumber, 10).toString().equals("Processing")) {
				cmbRemark.setEnabled(true);
				cmbRemark.removeItem("Processing");
				processingRemoved = true;
				cmbRemark.setEnabled(true);;
				cmbRemark.setSelectedIndex(0);
				placeOrderButton.setEnabled(false);
				orderUpdate.setEnabled(true);
			} else {
				if (processingRemoved == true) {
					cmbRemark.addItem("Processing");
				}
				cmbRemark.setSelectedItem("Complete");
				cmbRemark.setEnabled(false);
				placeOrderButton.setEnabled(false);
				orderUpdate.setEnabled(false);
				
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
			txtOrderID.setEditable(true);
			cmbProductType.setSelectedIndex(0);
			orderDate.setDate(null);
			dayOfComplete.setDate(null);
			dayOfNeed.setDate(null);
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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1071, 962);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 181));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label_1 = new JLabel("Client ID");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_1.setBounds(23, 138, 86, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("First Name");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_2.setBounds(23, 175, 86, 14);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("Last Name");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_3.setBounds(23, 215, 86, 14);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("Company Name");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_4.setBounds(23, 252, 130, 14);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("NIC No");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_5.setBounds(23, 291, 71, 14);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("Contact No");
		label_6.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_6.setBounds(23, 329, 94, 14);
		contentPane.add(label_6);
		
		JLabel label_7 = new JLabel("Email");
		label_7.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_7.setBounds(23, 367, 86, 14);
		contentPane.add(label_7);
		
		JLabel label_8 = new JLabel("Address");
		label_8.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_8.setBounds(23, 404, 86, 14);
		contentPane.add(label_8);
		
		JLabel label_12 = new JLabel("Product Type");
		label_12.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_12.setBounds(532, 175, 130, 14);
		contentPane.add(label_12);
		
		JLabel label_13 = new JLabel("Order Date");
		label_13.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_13.setBounds(532, 215, 86, 14);
		contentPane.add(label_13);
		
		JLabel label_14 = new JLabel("Day Of Need");
		label_14.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_14.setBounds(532, 291, 113, 14);
		contentPane.add(label_14);
	
		JLabel label_15 = new JLabel("Remark");
		label_15.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_15.setBounds(532, 516, 113, 14);
		contentPane.add(label_15);
		
		JLabel label_16 = new JLabel("Location");
		label_16.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_16.setBounds(532, 485, 113, 14);
		contentPane.add(label_16);
		
		JLabel lblProductColour = new JLabel("Product Colour");
		lblProductColour.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProductColour.setBounds(532, 442, 130, 14);
		contentPane.add(lblProductColour);
		
		JLabel label_18 = new JLabel("Transport Type");
		label_18.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_18.setBounds(532, 404, 130, 14);
		contentPane.add(label_18);
		
		JLabel label_19 = new JLabel("Superviser ID");
		label_19.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_19.setBounds(532, 367, 113, 14);
		contentPane.add(label_19);
		
		JLabel label_20 = new JLabel("Quantity");
		label_20.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_20.setBounds(532, 329, 113, 14);
		contentPane.add(label_20);
		
		JLabel label_21 = new JLabel("Day Of Complete");
		label_21.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_21.setBounds(532, 252, 144, 14);
		contentPane.add(label_21);

		JLabel label_11 = new JLabel("Order ID");
		label_11.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_11.setBounds(532, 138, 86, 14);
		contentPane.add(label_11);
		
		
		
		
		JButton newOrderButton = new JButton("New Order");
		newOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				placeOrderButton.setEnabled(true);
				orderUpdate.setEnabled(false);
				orderRemove.setEnabled(false);
				clientRemoveButton.setEnabled(false);
				clientUpdateButton.setEnabled(false);
				
				
				clearCustomerFields();
				clearOrderFields();
				cmbRemark.setSelectedIndex(1);
				cmbRemark.setEnabled(false);
				
				warning_message_result = JOptionPane.showConfirmDialog (null, "Already a member of UNIC..","Warning",warnin_message_button);
				
				if(warning_message_result == JOptionPane.YES_OPTION){
					if (processingRemoved == true) {
						cmbRemark.addItem("Processing");
					}
					clientAddButton.setEnabled(false);
					JOptionPane.showMessageDialog(null, "Please Select that client from given list or search the client..");
					table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.viewAllClients()));
					txtOrderID.setText(ID_Generator.orderID_Generator(orderRecordsServices.getOrderID()));
					txtOrderID.setEditable(false);
					clientID.setEditable(false);
					refreshValue = 1;
					
					
				}else {
					if (processingRemoved == true) {
						cmbRemark.addItem("Processing");
					}
					clientID.setText(ID_Generator.clientID_Generator(clientRecordsServices.getClientID()));
					txtOrderID.setText(ID_Generator.orderID_Generator(orderRecordsServices.getOrderID()));
					clientID.setEditable(false);
					table.setModel(new DefaultTableModel());
					clientAddButton.setEnabled(true);
					clientUpdateButton.setEnabled(true);
					clientRemoveButton.setEnabled(true);
					
				}
				
			}
		});
		newOrderButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		newOrderButton.setBounds(22, 495, 420, 68);
		contentPane.add(newOrderButton);
		
		clientAddButton = new JButton("Add Client");
		clientAddButton.addActionListener(new ActionListener() {
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
		clientAddButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		clientAddButton.setBounds(23, 576, 193, 33);
		contentPane.add(clientAddButton);
	
		clientClearButton = new JButton("Cancel");
		clientClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearCustomerFields();
				clientID.setEditable(true);
				clientAddButton.setEnabled(true);
				clientRemoveButton.setEnabled(true);
				clientUpdateButton.setEnabled(true);
				
				if (orderRetriview == false) {
					table.setModel(new DefaultTableModel());
				}
				
			}
		});
		clientClearButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		clientClearButton.setBounds(221, 576, 221, 33);
		contentPane.add(clientClearButton);
		
		clientUpdateButton = new JButton("Update Client");
		clientUpdateButton.addActionListener(new ActionListener() {
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
		clientUpdateButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		clientUpdateButton.setBounds(23, 622, 193, 40);
		contentPane.add(clientUpdateButton);
		
		clientRemoveButton = new JButton("Remove Client");
		clientRemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				if (IsClientCheckEmpty() != false) {
					JOptionPane.showMessageDialog(null, "Please Search Select your Client Details to Verify Before Done This Process, Because This Process Can't be Undone..");
				} else {
					warning_message_result = JOptionPane.showConfirmDialog (null, "Do you want to remove this Client..","Warning",warnin_message_button);
					if(warning_message_result == JOptionPane.YES_OPTION){

						clientRecordsServices.removeCLient(clientID.getText());
						table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.viewAllClients()));
					}
					
				}
				
			}
				
		});
		
		clientRemoveButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		clientRemoveButton.setBounds(223, 622, 219, 40);
		contentPane.add(clientRemoveButton);
		
		orderClearButton = new JButton("Cancel");
		orderClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				placeOrderButton.setEnabled(true);
				orderRemove.setEnabled(true);
				orderUpdate.setEnabled(true);
				
				cmbRemark.setEnabled(true);
				clearOrderFields();
				if (processingRemoved == true) {
					cmbRemark.addItem("Processing");
				}
				//refreshValue = 5;
			}
		});
		orderClearButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		orderClearButton.setBounds(881, 570, 150, 46);
		contentPane.add(orderClearButton);
		
		orderRemove = new JButton("Remove Order");
		orderRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (IsOrderCheckEmpty() != false) {
					JOptionPane.showMessageDialog(null, "Please Search Select your Order Details to Verify Before Done This Process, Because This Process Can't be Undone..");
				} else {
					warning_message_result = JOptionPane.showConfirmDialog (null, "Do you want to remove this Order..","Warning",warnin_message_button);
					if(warning_message_result == JOptionPane.YES_OPTION){
						orderRetriview = true;
						orderRecordsServices.removeOrder(txtOrderID.getText());
						clearCustomerFields();
						clearOrderFields();
						table.setModel(DbUtils.resultSetToTableModel(orderRecordsServices.viewAllOrders()));
						
					}
					
				}
				
				
				
				
			}
		});
		orderRemove.setFont(new Font("Tahoma", Font.BOLD, 13));
		orderRemove.setBounds(881, 622, 150, 40);
		contentPane.add(orderRemove);
		
		orderUpdate = new JButton("Update Order");
		orderUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateOrderFields()) {
					//refreshValue = 5;
					textSetClient();
					textSetOrder();
					orderRecordsServices.updateOrder(order, client);
					table.setModel(DbUtils.resultSetToTableModel(orderRecordsServices.viewAllOrders()));
				} else {
					JOptionPane.showMessageDialog(null, "Check Again, There Are Some Mistakes In your Fields");
				}

			}
		});
		orderUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
		orderUpdate.setBounds(532, 622, 337, 40);
		contentPane.add(orderUpdate);
		
		placeOrderButton = new JButton("Place Order");
		placeOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfProducts = orderRecordsServices.chekAvailability(productID.getText());
				userInputQuantity = Integer.parseInt(quantity1.getText());
				if (rootPaneCheckingEnabled) {
					if (validateClientFields()) {
						if (validateOrderFields()) {
							if (numOfProducts > userInputQuantity) {
								
								//refreshValue = 5;
								textSetClient();
								textSetOrder();
								clientRecordsServices.addClient(client);
								orderRecordsServices.addOrder(order, client);
								orderRecordsServices.updateProductQuantity(productID.getText(), numOfProducts - userInputQuantity);
								table.setModel(DbUtils.resultSetToTableModel(orderRecordsServices.viewAllOrders()));
								clearCustomerFields();
								clearOrderFields();
								cmbRemark.setEnabled(true);
								orderUpdate.setEnabled(true);
								orderRemove.setEnabled(true);
								clientAddButton.setEnabled(true);
								clientRemoveButton.setEnabled(true);
								clientUpdateButton.setEnabled(true);
								clientID.setEditable(true);
								
							} else {
								JOptionPane.showMessageDialog(null,"Insuficent Amount To Proceed\nOnly Have "+numOfProducts+" Products Only");
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
		placeOrderButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		placeOrderButton.setBackground(Color.GREEN);
		placeOrderButton.setBounds(532, 569, 337, 45);
		contentPane.add(placeOrderButton);
		
		JButton button_11 = new JButton("All Orders");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshValue = 2;
				table.setModel(DbUtils.resultSetToTableModel(orderRecordsServices.viewAllOrders()));
			}
		});
		button_11.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_11.setBounds(881, 685, 151, 40);
		contentPane.add(button_11);
		
		JButton button_13 = new JButton("Report Generate");
		button_13.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_13.setBounds(881, 738, 150, 42);
		contentPane.add(button_13);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 688, 846, 226);
		contentPane.add(scrollPane);
		
		
		
		
		
		
		
		address = new JTextField();
		address.setColumns(10);
		address.setBounds(165, 397, 277, 30);
		contentPane.add(address);
		
		emailAddress = new JTextField();
		emailAddress.setColumns(10);
		emailAddress.setBounds(165, 360, 277, 30);
		contentPane.add(emailAddress);
		
		contactNumber = new JTextField();
		contactNumber.setColumns(10);
		contactNumber.setBounds(165, 322, 277, 30);
		contentPane.add(contactNumber);
		
		nicNo = new JTextField();
		nicNo.setColumns(10);
		nicNo.setBounds(165, 284, 277, 30);
		contentPane.add(nicNo);
		
		companyName = new JTextField();
		companyName.setText("None");
		companyName.setColumns(10);
		companyName.setBounds(165, 247, 277, 30);
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
		lastName.setBounds(165, 208, 277, 30);
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
		firstName.setBounds(165, 168, 277, 30);
		contentPane.add(firstName);
		
		clientID = new JTextField();
		clientID.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
		clientID.setBounds(165, 130, 277, 30);
		contentPane.add(clientID);
		
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
						cmbRemark.setEnabled(true);
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
		txtOrderID.setBounds(729, 130, 305, 30);
		contentPane.add(txtOrderID);
	
		cmbProductType = new JComboBox();
		cmbProductType.addItem("Please Select");
		cmbProductType.setToolTipText("Select Product Name");
		cmbProductType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean orderIDNull;
				if (orderRetriview == false) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						if (cmbProductType.getSelectedItem().equals("Please Select")) {
							productID.setText(null);						
						}
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
				
			}
		});
		cmbProductType.setBounds(729, 168, 199, 30);
		contentPane.add(cmbProductType);
		
		productID = new JTextField();
		productID.setEditable(false);
		productID.setColumns(10);
		productID.setBounds(940, 168, 94, 30);
		contentPane.add(productID);
		
		orderDate = new JDateChooser();
		orderDate.setDateFormatString("yyyy-MM-dd");
		orderDate.setBounds(730, 208, 304, 30);
		contentPane.add(orderDate);
		
		dayOfNeed = new JDateChooser();
		dayOfNeed.setDateFormatString("yyyy-MM-dd");
		dayOfNeed.setBounds(729, 284, 305, 30);
		contentPane.add(dayOfNeed);
		
		dayOfComplete = new JDateChooser();
		dayOfComplete.setDateFormatString("yyyy-MM-dd");
		dayOfComplete.setBounds(730, 247, 304, 30);
		contentPane.add(dayOfComplete);
		
		quantity1 = new JTextField();
		quantity1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				boolean validate6 = quantity1.getText().matches("^[0-9]*$");
				if (validate6) {
					
				} else {
					JOptionPane.showMessageDialog(null, "Use Only No of Products\nNo Special Charcters are allowed....!");
					quantity1.setText(null);
				}
				
				
			}
		});
		quantity1.setColumns(10);
		quantity1.setBounds(729, 322, 305, 30);
		contentPane.add(quantity1);
		
		cmbSuperID = new JComboBox();
		cmbSuperID.addItem("Please Select");
		cmbSuperID.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean supervicerSetNull;
				String supervicerIDresult;
				if (orderRetriview == false) {
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
				
			}
		});
		cmbSuperID.setBounds(729, 360, 199, 30);
		contentPane.add(cmbSuperID);
		
		supervicerID = new JTextField();
		supervicerID.setEditable(false);
		supervicerID.setColumns(10);
		supervicerID.setBounds(940, 360, 94, 30);
		contentPane.add(supervicerID);
		
		cmpTransport = new JComboBox();
		cmpTransport.addItem("Select Transport Type");
		cmpTransport.addItem("Company");
		cmpTransport.addItem("Private");
		cmpTransport.setBounds(729, 397, 305, 30);
		contentPane.add(cmpTransport);
		
		Location = new JTextField();
		Location.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				boolean validate10 = Location.getText().matches("^[0-9A-Za-z,./-]*$");
				
				if (validate10) {
					
				} else {
					JOptionPane.showMessageDialog(null, "Use Only Dilevery Location Address\nNo Special Charcters are allowed....!");
					Location.setText(null);
				}
				
			}
		});
		Location.setColumns(10);
		Location.setBounds(729, 472, 305, 30);
		contentPane.add(Location);
		
		Colorinput = new JTextField();
		Colorinput.setEditable(false);
		Colorinput.setBounds(729, 435, 305, 30);
		contentPane.add(Colorinput);
		Colorinput.setColumns(10);
		
		
		
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(65, 105, 225));
		panel.setBounds(0, 0, 1111, 94);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblOrderManagement = new JLabel("ORDER MANAGEMENT");
		lblOrderManagement.setForeground(Color.WHITE);
		lblOrderManagement.setFont(new Font("Showcard Gothic", Font.BOLD, 30));
		lblOrderManagement.setBounds(379, 13, 354, 51);
		panel.add(lblOrderManagement);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 0));
		panel_1.setBounds(0, 100, 1111, 14);
		contentPane.add(panel_1);
		
		cmbRemark = new JComboBox();
		cmbRemark.addItem("Please Select");
		cmbRemark.addItem("Processing");
		cmbRemark.addItem("Complete");
		cmbRemark.setBounds(729, 509, 305, 30);
		contentPane.add(cmbRemark);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if (refreshValue == 1) {
					tableSelectItemClient();
					clientAddButton.setEnabled(false);
					clientUpdateButton.setEnabled(true);
					clientRemoveButton.setEnabled(true);
				} else if (refreshValue == 2) {
					placeOrderButton.setEnabled(false);
					orderUpdate.setEnabled(true);
					clientAddButton.setEnabled(false);
					try {
						allOrderItems();
						orderRetriview = true;
						ResultSet productName = commonServices.searchAndSort("product", "productID", productID.getText().toString());
						ResultSet customerAll = commonServices.searchAndSort("customer", "clientID", clientID.getText().toString());
						ResultSet supervicerName = commonServices.searchAndSort("user_main", "EID", supervicerID.getText().toString());
						while (productName.next()) {
							cmbProductType.setSelectedItem(productName.getString("productName").toString());
							Colorinput.setText(productName.getString("colour").toString());
						}
						while (supervicerName.next()) {
							cmbSuperID.setSelectedItem(supervicerName.getString("FName").toString()+" "+supervicerName.getString("LName").toString());
							
						}
						
						while (customerAll.next()) {
							clientID.setText(customerAll.getString("clientID").toString());
						    firstName.setText(customerAll.getString("FName").toString());
						     lastName.setText(customerAll.getString("LName").toString());
						  companyName.setText(customerAll.getString("companyName").toString());
						        nicNo.setText(customerAll.getString("NICNo").toString());
						contactNumber.setText(customerAll.getString("ContactNo").toString());
						 emailAddress.setText(customerAll.getString("Email").toString());
						      address.setText(customerAll.getString("Address").toString());
							
						}
						orderRetriview = false;
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
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
		
		// orderClearButton.setEnabled(false);
		
		
		produtTypeFill();
		superviceNameID(role);
		clearCustomerFields();
		clearOrderFields();
		clientRemoveButton.setEnabled(false);
		clientUpdateButton.setEnabled(false);
		
		orderRemove.setEnabled(false);
		orderUpdate.setEnabled(false);
		placeOrderButton.setEnabled(false);

	}
}
