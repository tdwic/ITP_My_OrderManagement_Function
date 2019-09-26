package com.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.model.SalarySchema;
import com.mysql.cj.jdbc.StatementWrapper;
import com.mysql.cj.xdevapi.Statement;
import com.mysql.jdbc.*;
import com.servise.SalaryRecordServices;
import com.util.DBConnection;

import com.util.ID_Generator;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

import java.sql.*;
import javax.swing.*;

public class ManagersSchema extends JFrame {

	private JPanel contentPane;
	private JTextField txtSchemaId;
	private JTextField txtpp1;
	private JTextField txtpp2;
	private JTextField txtpp3;
	private JTextField txtot;
	private JTextField txtpp4;
	private JTextField textField_2;
	private JTable table;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField txtpp5;
	
	
	//Other Common variables
	
	private PreparedStatement preStatement ;
	
	private int warnin_message_button = JOptionPane.YES_NO_OPTION;
	private int warning_message_result;
	private int refreshValue;
	private int numOfProducts, userInputQuantity;
	
	private String role = "SUP";
	private String QuickProductSearchID;
	private String QuickProductSearchKeyLock;
	
	private static Connection connection ;
	private static Statement statement ;
	private static boolean textBoxFull = false;
	
	private JComboBox<String> cmbProductType;
	
	
	
	
	
	
	
	
	SalarySchema salary = new SalarySchema();
	SalaryRecordServices SalaryRecordsServices = new SalaryRecordServices();
	ID_Generator id_Generator = new ID_Generator();
	private JTextField textField;
	//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	
	

	/**
	 * Launch the application.
	 * @throws SQLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	
	private void viewAllSalary() {
		try {
			String selectClient = "select * from salaryschema";
			connection = DBConnection.getDBConnection();
			preStatement = connection.prepareStatement(selectClient);
			ResultSet resultSet = preStatement.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultSet));
		} catch (Exception e) {
			
		}
	}
	
	
	private void textSetSalarySchema() {

		salary.setSchemaId(txtSchemaId.getText());
		salary.setPeacesPrice1(txtpp1.getText());
		salary.setPeacesPrice2(txtpp2.getText());
		salary.setPeacesPrice3(txtpp3.getText());
		salary.setPeacesPrice4(txtpp4.getText());
		salary.setPeacesPrice5(txtpp5.getText());
		salary.setOtRate(txtot.getText());
		//client.setClientAddress(.getText());
	}
	
	
	
	private void tableSelectItemSalary() {
		JOptionPane.showMessageDialog(null, "Schema Only");
		int rowNumber = table.getSelectedRow();
		     txtSchemaId.setText(table.getValueAt(rowNumber, 0).toString());
		     txtpp1.setText(table.getValueAt(rowNumber, 1).toString());
		     txtpp2.setText(table.getValueAt(rowNumber, 2).toString());
		     txtpp3.setText(table.getValueAt(rowNumber, 3).toString());
		     txtpp4.setText(table.getValueAt(rowNumber, 4).toString());
		     txtpp5.setText(table.getValueAt(rowNumber, 5).toString());
	    	 txtot.setText(table.getValueAt(rowNumber, 6).toString());
		      //.setText(table.getValueAt(rowNumber, 7).toString());
	}
	
	private Boolean IsSchemaCheckEmpty() {
		return ((txtSchemaId.getText().length()==0 || txtpp1.getText().length()==0) || txtot.getText().length()==0 );
	}
	
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		
		DBConnection con = new DBConnection();
		con.getDBConnection();
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagersSchema frame = new ManagersSchema();
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
	
	
	
	
	public ManagersSchema(){
		
	//	connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/unic", "root" , "07280614");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1167, 623);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("CheckBox.border"));
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblSalaryManagementSystem = new JLabel("Salary Management System - Managers/Supervisors");
		lblSalaryManagementSystem.setHorizontalAlignment(SwingConstants.CENTER);
		lblSalaryManagementSystem.setFont(new Font("Castellar", Font.BOLD, 25));
		panel.add(lblSalaryManagementSystem);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel addSchema = new JPanel();
		tabbedPane.addTab("Add Schema", null, addSchema, null);
		addSchema.setLayout(null);
		
		JLabel lblschemaId = new JLabel("Schema ID");
		lblschemaId.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 20));
		lblschemaId.setBounds(10, 11, 121, 30);
		addSchema.add(lblschemaId);
		
		txtSchemaId = new JTextField();
		txtSchemaId.setFont(new Font("Calibri", Font.BOLD, 18));
		txtSchemaId.setColumns(10);
		txtSchemaId.setBounds(141, 11, 140, 29);
		addSchema.add(txtSchemaId);
		
		JLabel lblRole = new JLabel("Role");
		lblRole.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 20));
		lblRole.setBounds(10, 111, 121, 30);
		addSchema.add(lblRole);
		
		JComboBox cmbRole = new JComboBox();
		cmbRole.setFont(new Font("Tahoma", Font.BOLD, 18));
		cmbRole.setModel(new DefaultComboBoxModel(new String[] {"Sales Manager", "Branch Manager", "Factory Manager", "Machine Supervisor", "Moled Supervisor", "Shifting Supervisor", "Store Supervisor", "Machine Workers", "Moled Workers", "Driver", "Cleaner", "Shifter"}));
		cmbRole.setBounds(103, 112, 192, 30);
		addSchema.add(cmbRole);
		
		JLabel lblOtRate = new JLabel("OT Rate");
		lblOtRate.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOtRate.setBounds(103, 168, 163, 24);
		addSchema.add(lblOtRate);
		
		JLabel lblpp1 = new JLabel("Peace Price(0 - 400)");
		lblpp1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblpp1.setBounds(103, 203, 192, 24);
		addSchema.add(lblpp1);
		
		JLabel lblpp2 = new JLabel("Peace Price(401 - 600)");
		lblpp2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblpp2.setBounds(103, 238, 230, 24);
		addSchema.add(lblpp2);
		
		JLabel lblpp3 = new JLabel("Peace Price(601-1000)");
		lblpp3.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblpp3.setBounds(103, 273, 214, 24);
		addSchema.add(lblpp3);
		
		txtpp1 = new JTextField();
		txtpp1.setColumns(10);
		txtpp1.setBounds(333, 206, 163, 24);
		addSchema.add(txtpp1);
		
		txtpp2 = new JTextField();
		txtpp2.setColumns(10);
		txtpp2.setBounds(333, 241, 163, 24);
		addSchema.add(txtpp2);
		
		txtpp3 = new JTextField();
		txtpp3.setColumns(10);
		txtpp3.setBounds(333, 276, 163, 24);
		addSchema.add(txtpp3);
		
		JButton btnreset = new JButton("RESET");
		btnreset.setHorizontalAlignment(SwingConstants.LEADING);
		btnreset.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		btnreset.setBounds(890, 404, 113, 23);
		addSchema.add(btnreset);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.setHorizontalAlignment(SwingConstants.LEADING);
		btnExit.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		btnExit.setBounds(1013, 404, 113, 23);
		addSchema.add(btnExit);
		
		JButton btnAdd = new JButton("Add Schema");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshValue = 1;
				textSetSalarySchema();	
				SalaryRecordsServices.addSchema(salary);
				viewAllSalary();
				
				}
		});
		btnAdd.setHorizontalAlignment(SwingConstants.LEADING);
		btnAdd.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		btnAdd.setBounds(238, 404, 163, 23);
		addSchema.add(btnAdd);
		
		txtot = new JTextField();
		txtot.setColumns(10);
		txtot.setBounds(333, 171, 163, 24);
		addSchema.add(txtot);
		
		JLabel lblpp4 = new JLabel("Peace Price(1001-1500)");
		lblpp4.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblpp4.setBounds(103, 308, 230, 24);
		addSchema.add(lblpp4);
		
		txtpp4 = new JTextField();
		txtpp4.setColumns(10);
		txtpp4.setBounds(333, 311, 163, 24);
		addSchema.add(txtpp4);
		
		JLabel lbpp5 = new JLabel("Peace Price(1501-2000)");
		lbpp5.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbpp5.setBounds(103, 343, 230, 24);
		addSchema.add(lbpp5);
		
		txtpp5 = new JTextField();
		txtpp5.setColumns(10);
		txtpp5.setBounds(333, 343, 163, 24);
		addSchema.add(txtpp5);
		
		JButton btnupdate = new JButton("Update");
		btnupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshValue = 2;
				textSetSalarySchema();
				SalaryRecordsServices.updateSchema(salary);
				viewAllSalary();
			}
		});
		btnupdate.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		btnupdate.setBounds(1018, 347, 108, 23);
		addSchema.add(btnupdate);
		
		
		
		JPanel editSchema = new JPanel();
		tabbedPane.addTab("Edit Schema", null, editSchema, null);
		editSchema.setLayout(null);
		
		JLabel label = new JLabel("Schema ID");
		label.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 20));
		label.setBounds(10, 21, 121, 30);
		editSchema.add(label);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Calibri", Font.BOLD, 18));
		textField_2.setColumns(10);
		textField_2.setBounds(141, 21, 140, 29);
		editSchema.add(textField_2);
		
		JLabel label_2 = new JLabel("Role");
		label_2.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 20));
		label_2.setBounds(10, 120, 121, 30);
		editSchema.add(label_2);
		
		JComboBox cmbRole2 = new JComboBox();
		cmbRole2.setModel(new DefaultComboBoxModel(new String[] {"Sales Manager", "Branch Manager", "Factory Manager", "Machine Supervisor", "Moled Supervisor", "Shifting Supervisor", "Store Supervisor", "Machine Workers", "Moled Workers", "Driver", "Cleaner", "Shifter"}));
		cmbRole2.setFont(new Font("Tahoma", Font.BOLD, 18));
		cmbRole2.setBounds(89, 120, 192, 30);
		editSchema.add(cmbRole2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(458, 120, 668, 272);
		editSchema.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel label_3 = new JLabel("Peace Price(0 - 300)");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_3.setBounds(10, 221, 192, 24);
		editSchema.add(label_3);
		
		JLabel label_4 = new JLabel("Peace Price(401 - 600)");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_4.setBounds(10, 256, 230, 24);
		editSchema.add(label_4);
		
		JLabel label_5 = new JLabel("Peace Price(600-1000)");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_5.setBounds(10, 291, 214, 24);
		editSchema.add(label_5);
		
		JLabel label_6 = new JLabel("Peace Price(1000-1500)");
		label_6.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_6.setBounds(10, 326, 230, 24);
		editSchema.add(label_6);
		
		JLabel label_7 = new JLabel("OT Rate");
		label_7.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_7.setBounds(10, 186, 163, 24);
		editSchema.add(label_7);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(258, 221, 163, 24);
		editSchema.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(258, 256, 163, 24);
		editSchema.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(258, 294, 163, 24);
		editSchema.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(258, 329, 163, 24);
		editSchema.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(258, 191, 163, 24);
		editSchema.add(textField_7);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
		btnUpdate.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		btnUpdate.setBounds(313, 405, 108, 23);
		editSchema.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.out.println(IsSchemaCheckEmpty());
				if (IsSchemaCheckEmpty() != false) {
					JOptionPane.showMessageDialog(null, "Please Search Select schema Details to Verify Before Done This Process, Because This Process Can't be Undone..");
				} else {
					warning_message_result = JOptionPane.showConfirmDialog (null, "Do you want to remove this Schema..","Warning",warnin_message_button);
					if(warning_message_result == JOptionPane.YES_OPTION){
						refreshValue = 4;
						SalaryRecordsServices.removeSchema(txtSchemaId.getText());
						viewAllSalary();
					}
					
				}
				
			}
		});
		btnDelete.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		btnDelete.setBounds(738, 405, 108, 23);
		editSchema.add(btnDelete);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		btnReset.setBounds(208, 405, 95, 23);
		editSchema.add(btnReset);
		
		JButton button = new JButton("EXIT");
		button.setHorizontalAlignment(SwingConstants.LEADING);
		button.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		button.setBounds(1013, 468, 113, 23);
		editSchema.add(button);
		
		JLabel lblpp5 = new JLabel("Peace Price(1000-1500)");
		lblpp5.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblpp5.setBounds(10, 368, 230, 24);
		editSchema.add(lblpp5);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(258, 368, 163, 24);
		editSchema.add(textField);
	}
}
