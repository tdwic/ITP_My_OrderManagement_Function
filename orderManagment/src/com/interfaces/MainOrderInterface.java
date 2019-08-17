package com.interfaces;

import java.awt.EventQueue;
import com.model.Client;
import com.model.Order;
import com.mysql.cj.xdevapi.Table;
import com.service.*;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.ws.AsyncHandler;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTable;
import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import javax.management.loading.PrivateClassLoader;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.time.Month;
import java.time.Year;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;
import com.util.DbConnect;
import com.util.ID_Generator;

import net.proteanit.sql.DbUtils;

import com.toedter.calendar.JDateChooser;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JSplitPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class MainOrderInterface {

	//Interface Items Objects
	
	private JFrame frame;
	private JTextField emailAddress;
	private JTextField contactNumber;
	private JTextField nicNo;
	private JTextField companyName;
	private JTextField lastName;
	private JTextField firstName;
	private JTextField clientID;
	private JTextField address;
	private JTextField txtOrderID;
	private JTextField textField_8;
	private JTextField quantity1;
	private JTextField textField_11;
	private JTextField Location;
	private JTextField Remark;
	private JTextField supervicerID;
	private JTextField productID;
	private JTable table;
	
	private JComboBox<String> cmbProductType;
	private JComboBox<String> comboBox_2;
	private JComboBox<String> cmbSuperID;
	private JComboBox<String> cmbColor;
	private JComboBox<String> cmpTransport;
	
	private JDateChooser dayOfNeed;
	private JDateChooser orderDate;
	private JDateChooser dayOfComplete;
	
	
	
	//Interface Items Objects
	
	
	//Other Common variables
	
	private int warnin_message_button = JOptionPane.YES_NO_OPTION;
	private int warning_message_result;
	private int refreshValue;
	private String role = "SUP";
	private static Connection connection ;
	private static Statement statement ;
	private PreparedStatement preStatement ;
	private static boolean textBoxFull = false;
	
	//Other Common variables
	
	
	//Object Declaration
	
	Client client = new Client();
	Order order = new Order();
	ClientRecordsServices clientRecordsServices = new ClientRecordsServices();
	OrderRecordsServices orderRecordsServices = new OrderRecordsServices();
	ID_Generator id_Generator = new ID_Generator();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	//Object Declaration
	
	
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
	
	
	public void produtColorFill() {
		try {
			String selectColour = "select distinct colour from unic.product";
			connection = DbConnect.getDBConnection();
			preStatement = connection.prepareStatement(selectColour);
			ResultSet colourSet = preStatement.executeQuery();
			
			while (colourSet.next()) {
				cmbColor.addItem(colourSet.getString("colour"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	public void productIDview(String nameProduct) {
		String proName = nameProduct;
		
		try {
			String OrderID_query = "select * From unic.product p Where productName = '"+proName+"'";
			connection = DbConnect.getDBConnection();
			preStatement = connection.prepareStatement(OrderID_query);
			ResultSet resultSet = preStatement.executeQuery();
			while (resultSet.next()) {
				productID.setText(resultSet.getString("productID"));
				cmbColor.setSelectedItem(resultSet.getString("colour"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
				
	}
	
	public void superviceNameID(String role) {
		String superRole = role;
		
		try {
			String selectSupervicer = "SELECT DISTINCT FName,LName FROM user_main WHERE Role = '"+superRole+"'";
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
	
	
	private void viewAllOrders() {
		try {
			String selectClient = "select * from order";
			connection = DbConnect.getDBConnection();
			preStatement = connection.prepareStatement(selectClient);
			ResultSet resultSet = preStatement.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultSet));
		} catch (Exception e) {
			
		}
	}
	
	private void viewAllClients() {
		try {
			String selectClient = "select * from customer";
			connection = DbConnect.getDBConnection();
			preStatement = connection.prepareStatement(selectClient);
			ResultSet resultSet = preStatement.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultSet));
		} catch (Exception e) {
			
		}
	}
	
	
	private void viewAllSupervicers(String supervicerName) {
		String superName = supervicerName;
		
		try {
			String selectClient = "SELECT EID,CONCAT(FName,' ', LName) AS Full_Name,NICNo,Role FROM (SELECT EID,FName,LName,NICNo,Role, CONCAT(FName,' ', LName) AS fullName FROM unic.user_main) result WHERE result.fullName = '"+superName+"'";
			connection = DbConnect.getDBConnection();
			preStatement = connection.prepareStatement(selectClient);
			ResultSet resultSet = preStatement.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultSet));
			int cnt = table.getRowCount();
			if (cnt >= 2) {
				JOptionPane.showMessageDialog(null, "select one");
			} else {
				table.setModel(new DefaultTableModel());
			}
			
		} catch (Exception e) {
			
		}
	}
	
	
	private void viewAllProducts(String productName) {
		String nameProduct = productName;
		
		try {
			String selectClient = "select * from product where productName = '"+nameProduct+"'";
			connection = DbConnect.getDBConnection();
			preStatement = connection.prepareStatement(selectClient);
			ResultSet resultSet = preStatement.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultSet));
			int cnt = table.getRowCount();
			if (cnt >= 2) {
				JOptionPane.showMessageDialog(null, "select one");
			} else {
				table.setModel(new DefaultTableModel());
			}
			
		} catch (Exception e) {
			
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
		order.setLocation(Location.getText());
		order.setRemark(Remark.getText());
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
	
	private void allOrderItems() {
		JOptionPane.showMessageDialog(null, "Both");
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
	
	private void allProductItems() {
		JOptionPane.showMessageDialog(null, "Product Only");
		int rowNumber = table.getSelectedRow();
		cmbProductType.setSelectedItem(table.getValueAt(rowNumber, 1).toString());
		productID.setText(table.getValueAt(rowNumber, 0).toString());
		cmbColor.setSelectedItem(table.getValueAt(rowNumber, 4).toString());
	}
	
	
	private void supervicerID() {
		JOptionPane.showMessageDialog(null, "Supervicer Only");
		int rowNumber = table.getSelectedRow();
		supervicerID.setText(table.getValueAt(rowNumber, 0).toString());
	
	}
	
	private Boolean IsClientCheckEmpty() {
		return ((clientID.getText().length()==0 || firstName.getText().length()==0) || lastName.getText().length()==0 );
	}
	
	
	/**
	 * Launch the application.
	 * @throws SQLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	
	
	
	
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		
		DbConnect dbConnect  = new DbConnect();
		dbConnect.getDBConnection();
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					MainOrderInterface window = new MainOrderInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainOrderInterface() {
		initialize();

		supervicerID = new JTextField();
		supervicerID.setEditable(false);
		supervicerID.setBounds(697, 208, 62, 20);
		frame.getContentPane().add(supervicerID);
		supervicerID.setColumns(10);
		
		JButton btnNewButton_7 = new JButton("Clear");
		btnNewButton_7.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_7.setBounds(215, 343, 144, 46);
		frame.getContentPane().add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("Clear");
		btnNewButton_8.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_8.setBounds(629, 343, 130, 46);
		frame.getContentPane().add(btnNewButton_8);
		
		produtTypeFill();
		produtColorFill();
		superviceNameID(role);
		
		table.setModel(new DefaultTableModel());
		
		JButton btnNewButton_9 = new JButton("New Order");
		btnNewButton_9.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				warning_message_result = JOptionPane.showConfirmDialog (null, "Already a member of UNIC..","Warning",warnin_message_button);
				
				if(warning_message_result == JOptionPane.YES_OPTION){
					
					JOptionPane.showMessageDialog(null, "Please Select that client from given list or search the client..");
					viewAllClients();
					
				}else {
					
					clientID.setText(ID_Generator.clientID_Generator(clientRecordsServices.getClientID()));
					txtOrderID.setText(ID_Generator.orderID_Generator(orderRecordsServices.getOrderID()));
					
					table.setModel(new DefaultTableModel());
					
				}
			}
		});
		btnNewButton_9.setBounds(10, 275, 349, 56);
		frame.getContentPane().add(btnNewButton_9);

		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		frame = new JFrame();
		frame.setBounds(100, 100, 785, 672);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		emailAddress = new JTextField();
		emailAddress.setColumns(10);
		emailAddress.setBounds(143, 208, 216, 20);
		frame.getContentPane().add(emailAddress);
		
		JLabel label = new JLabel("Email");
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setBounds(10, 204, 86, 14);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Contact No");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_1.setBounds(10, 180, 94, 14);
		frame.getContentPane().add(label_1);
		
		contactNumber = new JTextField();
		contactNumber.setColumns(10);
		contactNumber.setBounds(143, 183, 216, 20);
		frame.getContentPane().add(contactNumber);
		
		nicNo = new JTextField();
		nicNo.setColumns(10);
		nicNo.setBounds(143, 158, 216, 20);
		frame.getContentPane().add(nicNo);
		
		JLabel label_2 = new JLabel("NIC No");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_2.setBounds(10, 158, 71, 14);
		frame.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("Company Name");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_3.setBounds(10, 133, 113, 14);
		frame.getContentPane().add(label_3);
		
		companyName = new JTextField();
		companyName.setText("None");
		companyName.setColumns(10);
		companyName.setBounds(143, 133, 216, 20);
		frame.getContentPane().add(companyName);
		
		lastName = new JTextField();
		lastName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (lastName.getText().equals("") != true) {
					
					if (clientID.getText().equals("") == true) {
						textBoxFull = false;
					}
					
					table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.searchAndSort("LName",lastName.getText())));
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
		lastName.setBounds(143, 108, 216, 20);
		frame.getContentPane().add(lastName);
		
		JLabel label_4 = new JLabel("Last Name");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_4.setBounds(10, 108, 86, 14);
		frame.getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("First Name");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_5.setBounds(10, 83, 86, 14);
		frame.getContentPane().add(label_5);
		
		firstName = new JTextField();
		firstName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (firstName.getText().equals("") != true) {
					
					if (clientID.getText().equals("") == true) {
						textBoxFull = false;
					}
					
					table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.searchAndSort("FName",firstName.getText())));
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
		firstName.setBounds(143, 83, 216, 20);
		frame.getContentPane().add(firstName);
		
		clientID = new JTextField();
		clientID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = clientID.getText();
				if(clientID.getText().equals("") != true) {
					if (clientID.getText().equals("") == true) {
						textBoxFull = false;
					}
					table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.searchAndSort("clientID",clientID.getText())));
					textBoxFull = true;
				} else {
					if (textBoxFull != true) {
						table.setModel(new DefaultTableModel());
						textBoxFull = false;
					}
					
				}
			}
		});
		clientID.setColumns(10);
		clientID.setBounds(143, 58, 216, 20);
		frame.getContentPane().add(clientID);
		
		JLabel label_6 = new JLabel("Client ID");
		label_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_6.setBounds(10, 58, 86, 14);
		frame.getContentPane().add(label_6);
		
		address = new JTextField();
		address.setColumns(10);
		address.setBounds(143, 233, 216, 20);
		frame.getContentPane().add(address);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAddress.setBounds(10, 229, 86, 14);
		frame.getContentPane().add(lblAddress);
		
		JButton btnNewButton_1 = new JButton("Update Client");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshValue = 2;
				textSetClient();
				clientRecordsServices.updateClient(clientID.getText(), client);
				viewAllClients();
		
			}
		});
		btnNewButton_1.setBounds(10, 396, 193, 40);
		frame.getContentPane().add(btnNewButton_1);
		//s
		/*JButton btnSearchClient = new JButton("Search Client");
		btnSearchClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (clientID.getText().equals("") && firstName.getText().equals("") && lastName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Enter 'ClientID','First Name' or 'Last Name' to proceed the search...");
					
				} else if (firstName.getText().equals("") && lastName.getText().equals("")) {
					
					JOptionPane.showMessageDialog(null, "Client ID onlly");
					table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.searchAndSort("clientID",clientID.getText())));
					
				} else if (clientID.getText().equals("") && lastName.getText().equals("")) {
					
					JOptionPane.showMessageDialog(null, "First Name Only");
					table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.searchAndSort("FName",firstName.getText())));
					
				} else if (clientID.getText().equals("") && firstName.getText().equals("")) {
					
					JOptionPane.showMessageDialog(null, "Last Name Only");
					table.setModel(DbUtils.resultSetToTableModel(clientRecordsServices.searchAndSort("LName",lastName.getText())));
					
				} else {
					JOptionPane.showMessageDialog(null, "Use Only One Field to search");
					clientID.setText("");
					firstName.setText("");
					lastName.setText("");
				}
				refreshValue = 3;
				textSetClient();	
				
				
			}
		});
		btnSearchClient.setBounds(129, 344, 113, 23);
		frame.getContentPane().add(btnSearchClient);*/
		
		txtOrderID = new JTextField();
		txtOrderID.setColumns(10);
		txtOrderID.setBounds(550, 58, 209, 20);
		frame.getContentPane().add(txtOrderID);
		
		JLabel lblOrderId = new JLabel("Order ID");
		lblOrderId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOrderId.setBounds(417, 58, 86, 14);
		frame.getContentPane().add(lblOrderId);
		
		JLabel lblOrderType = new JLabel("Product Type");
		lblOrderType.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOrderType.setBounds(417, 83, 86, 14);
		frame.getContentPane().add(lblOrderType);
		
		JLabel lblOrderDate = new JLabel("Order Date");
		lblOrderDate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOrderDate.setBounds(417, 108, 86, 14);
		frame.getContentPane().add(lblOrderDate);
		
		JLabel lblDayOfNeed = new JLabel("Day Of Need");
		lblDayOfNeed.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDayOfNeed.setBounds(417, 133, 113, 14);
		frame.getContentPane().add(lblDayOfNeed);
		
		JLabel lblDayOfComplete = new JLabel("Day Of Complete");
		lblDayOfComplete.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDayOfComplete.setBounds(417, 158, 113, 14);
		frame.getContentPane().add(lblDayOfComplete);
		
		JLabel lblSuperviserId = new JLabel("Superviser ID");
		lblSuperviserId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSuperviserId.setBounds(417, 210, 94, 14);
		frame.getContentPane().add(lblSuperviserId);
		
		JButton btnNewButton_2 = new JButton("Place Order");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshValue = 8;
				textSetClient();
				textSetOrder();
				clientRecordsServices.addClient(client);
				orderRecordsServices.addOrder(order, client);
			}
		});
		btnNewButton_2.setBackground(Color.GREEN);
		btnNewButton_2.setBounds(417, 344, 200, 45);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Update Order");
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshValue = 9;
			}
		});
		btnNewButton_3.setBounds(417, 396, 200, 40);
		frame.getContentPane().add(btnNewButton_3);
		
		
		
		cmbSuperID = new JComboBox();
		cmbSuperID.addItem("Please Select");
		cmbSuperID.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					refreshValue = 7;
					viewAllSupervicers(arg0.getItem().toString());
					String supervicerIDresult;
					supervicerIDresult = orderRecordsServices.superviceIDView(arg0.getItem().toString());
					supervicerID.setText(supervicerIDresult); 
			    }
				
			}
		});
		cmbSuperID.setBounds(550, 209, 137, 20);
		frame.getContentPane().add(cmbSuperID);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setEditable(true);
		comboBox_2.setBounds(143, 8, 101, 22);
		frame.getContentPane().add(comboBox_2);
		
		JLabel lblProductType = new JLabel("Product Type");
		lblProductType.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductType.setBounds(10, 11, 86, 14);
		frame.getContentPane().add(lblProductType);
	
		orderDate = new JDateChooser();
		orderDate.setBounds(550, 108, 209, 20);
		orderDate.setDateFormatString("yyyy-MM-dd");
		frame.getContentPane().add(orderDate);
		
		
		dayOfNeed = new JDateChooser();
		dayOfNeed.setBounds(550, 133, 209, 20);
		dayOfNeed.setDateFormatString("yyyy-MM-dd");
		frame.getContentPane().add(dayOfNeed);
		
		
		dayOfComplete = new JDateChooser();
		dayOfComplete.setBounds(550, 158, 209, 20);
		dayOfComplete.setDateFormatString("yyyy-MM-dd");
		frame.getContentPane().add(dayOfComplete);
		
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(322, 9, 113, 20);
		frame.getContentPane().add(textField_8);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAmount.setBounds(262, 11, 86, 14);
		frame.getContentPane().add(lblAmount);
		
		JLabel lblAmount_1 = new JLabel("Quantity");
		lblAmount_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAmount_1.setBounds(417, 185, 113, 14);
		frame.getContentPane().add(lblAmount_1);
		
		quantity1 = new JTextField();
		quantity1.setColumns(10);
		quantity1.setBounds(550, 183, 209, 20);
		frame.getContentPane().add(quantity1);
		
		JLabel lblAvailability = new JLabel("Availability");
		lblAvailability.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAvailability.setBounds(458, 11, 86, 14);
		frame.getContentPane().add(lblAvailability);
		
		textField_11 = new JTextField();
		textField_11.setEnabled(false);
		textField_11.setColumns(10);
		textField_11.setBounds(542, 11, 94, 20);
		frame.getContentPane().add(textField_11);
		
		JButton btnQuichSearch = new JButton("Quick Search");
		btnQuichSearch.setBounds(646, 9, 113, 21);
		frame.getContentPane().add(btnQuichSearch);
		
		JLabel lblTransportType = new JLabel("Transport Type");
		lblTransportType.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTransportType.setBounds(417, 235, 113, 14);
		frame.getContentPane().add(lblTransportType);
		
		cmpTransport = new JComboBox();
		cmpTransport.setBounds(550, 233, 209, 20);
		frame.getContentPane().add(cmpTransport);
		cmpTransport.addItem("Company");
		cmpTransport.addItem("Private");
		
		JLabel lblRemark = new JLabel("Location");
		lblRemark.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRemark.setBounds(417, 286, 113, 14);
		frame.getContentPane().add(lblRemark);
		
		Location = new JTextField();
		Location.setColumns(10);
		Location.setBounds(550, 283, 209, 20);
		frame.getContentPane().add(Location);
		
		JButton btnNewButton = new JButton("Add Client");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				refreshValue = 1;
				textSetClient();	
				clientRecordsServices.addClient(client);
				viewAllClients();

			}
		});
		btnNewButton.setBounds(10, 344, 193, 45);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnViewAllOrders = new JButton("All Orders");
		btnViewAllOrders.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnViewAllOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshValue = 5;
				viewAllOrders();
			}
		});
		btnViewAllOrders.setBounds(646, 484, 114, 23);
		frame.getContentPane().add(btnViewAllOrders);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (refreshValue > 7) {
					JOptionPane.showMessageDialog(null, "Order Refresh");
					
				} else if(refreshValue == 5) {
					JOptionPane.showMessageDialog(null, "Both refresh");
				} else if(refreshValue == 6) {
					JOptionPane.showMessageDialog(null, "Product Item Refresh");
					viewAllProducts(cmbProductType.getSelectedItem().toString());
				} else if(refreshValue == 7) {
					JOptionPane.showMessageDialog(null, "Supervicer Refresh");
					viewAllSupervicers(cmbSuperID.getSelectedItem().toString());
				} else {
					JOptionPane.showMessageDialog(null, "Client refresh");
					viewAllClients();
				}
			}
		});
		btnRefresh.setBounds(646, 456, 114, 23);
		frame.getContentPane().add(btnRefresh);
		
		JButton btnRemoveClient = new JButton("Remove Client");
		btnRemoveClient.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRemoveClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	System.out.println(IsClientCheckEmpty());
				if (IsClientCheckEmpty() != false) {
					JOptionPane.showMessageDialog(null, "Please Search Select your Client Details to Verify Before Done This Process, Because This Process Can't be Undone..");
				} else {
					warning_message_result = JOptionPane.showConfirmDialog (null, "Do you want to remove this Client..","Warning",warnin_message_button);
					if(warning_message_result == JOptionPane.YES_OPTION){
						refreshValue = 4;
						clientRecordsServices.removeCLient(clientID.getText());
						viewAllClients();
					}
					
				}
				
			}

		});
		btnRemoveClient.setBounds(214, 396, 145, 40);
		frame.getContentPane().add(btnRemoveClient);
		
		JButton btnNewButton_5 = new JButton("Remove Order");
		btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshValue = 11;
			}
		});
		btnNewButton_5.setBounds(629, 396, 130, 40);
		frame.getContentPane().add(btnNewButton_5);
		
		Remark = new JTextField();
		Remark.setColumns(10);
		Remark.setBounds(550, 309, 209, 20);
		frame.getContentPane().add(Remark);
		
		JLabel label_7 = new JLabel("Remark");
		label_7.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_7.setBounds(417, 312, 113, 14);
		frame.getContentPane().add(label_7);
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblColor.setBounds(417, 260, 113, 14);
		frame.getContentPane().add(lblColor);
		
		cmbColor = new JComboBox();
		cmbColor.setBounds(550, 258, 209, 20);
		cmbColor.addItem("Please Select");
		frame.getContentPane().add(cmbColor);
		
		JButton btnNewButton_6 = new JButton("Report Generate");
		btnNewButton_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_6.setBounds(646, 575, 113, 42);
		frame.getContentPane().add(btnNewButton_6);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 456, 626, 161);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		cmbProductType = new JComboBox();
		cmbProductType.addItem("Please Select");
		cmbProductType.setToolTipText("Select Product Name");
		cmbProductType.addItemListener(new ItemListener() {
	
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					viewAllProducts(e.getItem().toString());
					refreshValue = 6;
					productIDview(e.getItem().toString());
			    }
			}
		});

		cmbProductType.setBounds(550, 83, 137, 20);
		frame.getContentPane().add(cmbProductType);
		
		productID = new JTextField();
		productID.setEditable(false);
		productID.setBounds(697, 81, 62, 22);
		frame.getContentPane().add(productID);
		productID.setColumns(10);
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{comboBox_2, textField_8, frame.getContentPane(), emailAddress, label, label_1, contactNumber, nicNo, label_2, label_3, companyName, lastName, label_4, label_5, firstName, clientID, label_6, address, btnQuichSearch, lblAddress, btnNewButton_1, txtOrderID, lblOrderId, lblOrderType, lblOrderDate, lblDayOfNeed, lblDayOfComplete, lblSuperviserId, btnNewButton_2, btnNewButton_3,  cmbSuperID, lblProductType, orderDate, orderDate.getCalendarButton(), dayOfNeed, dayOfNeed.getCalendarButton(), dayOfComplete, dayOfComplete.getCalendarButton(), lblAmount, lblAmount_1, quantity1, lblAvailability, lblTransportType, cmpTransport, lblRemark, Location, btnNewButton, btnViewAllOrders, btnRefresh, btnRemoveClient, btnNewButton_5, Remark, label_7, lblColor, cmbColor, btnNewButton_6}));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (refreshValue > 7) {
					tableSelectItemOrder();
					
				} else if(refreshValue == 5) {
					allOrderItems();
				}else if(refreshValue == 6) {
					allProductItems();
				} else if(refreshValue == 7) {
					supervicerID();
				}else {
					tableSelectItemClient();
				}
				
			}
		});
	}
}
