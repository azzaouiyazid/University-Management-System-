import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JList;
import java.awt.Button;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ListSelectionModel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.sql.*;
import java.text.MessageFormat;
import javax.swing.*;



public class FrameDepartment {

	private JFrame framedepartment;
	private JPanel contentPane;
	Connection conn = null ; 
	PreparedStatement pst = null ; 
	ResultSet rs = null ; 
	DefaultTableModel model = new DefaultTableModel();
	
	private Image department_logo = new ImageIcon(FrameDashboard.class.getResource("res/DepartmentLogo.png")).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
	private JTable table;
	private JTextField jtxtDept_name;
	private JTextField jtxtBuildingdept;
	private JTextField jtxtBudget;

	/**
	 * Launch the application.
	 */
	public static void DepartmentScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameDepartment window = new FrameDepartment();
					window.framedepartment.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void updateTable()
	{
		conn = Sqliteconnect.ConnectDB();
		if(conn != null)
		{
			String sql = "Select dept_name , building , budget from department ";
			try 
			{
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[3];
				
				while(rs.next())
				{
					columnData[0] = rs.getString("dept_name");
					columnData[1] = rs.getString("building");
					columnData[2] = rs.getString("budget");
					model.addRow(columnData);
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null , e);
			}
		}
	}
	/**
	 * Create the application.
	 */
	public FrameDepartment() {
		initialize();
		conn = Sqliteconnect.ConnectDB();
		Object col[] = {"dept_name" , "building" , "budget" };
		model.setColumnIdentifiers(col);
		table.setModel(model);
		updateTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		framedepartment = new JFrame();
		framedepartment.getContentPane().setBackground(new Color(70, 130, 180));
		framedepartment.setBounds(100, 100, 700, 700);
		framedepartment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framedepartment.getContentPane().setLayout(null);
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		framedepartment.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelClass = new JPanel();
		panelClass.setBackground(new Color(70, 130, 180));
		panelClass.setBounds(0, 0, 684, 661);
		contentPane.add(panelClass);
		panelClass.setLayout(null);
		
		JLabel lblClassLogo = new JLabel("Department's Database");
		lblClassLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClassLogo.setFont(new Font("Arial Narrow", Font.BOLD, 24));
		lblClassLogo.setBounds(47, 0, 492, 88);
		lblClassLogo.setIcon(new ImageIcon(department_logo));
		panelClass.add(lblClassLogo);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(new Color(128, 128, 128));
		panelButtons.setBounds(0, 519, 684, 142);
		panelClass.add(panelButtons);
		panelButtons.setLayout(null);
		
		JButton btnBack = new JButton("Back to Menu");
		btnBack.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				framedepartment.dispose();
				FrameDashboard fmain = new FrameDashboard();
				fmain.main(null);
			}
		});
		btnBack.setForeground(Color.WHITE);
		btnBack.setBackground(Color.BLACK);
		btnBack.setBounds(530, 42, 120, 48);
		panelButtons.add(btnBack);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				MessageFormat header = new MessageFormat("Printing in progress");
				@SuppressWarnings("unused")
				MessageFormat footer = new MessageFormat("Page {0,number,integer}");
				
				try {
					table.print();
				
				}catch(java.awt.print.PrinterException ev) {
					System.err.format("No Printer found",ev.getMessage());
					
				}
			}
		});
		btnPrint.setForeground(Color.WHITE);
		btnPrint.setBackground(Color.BLACK);
		btnPrint.setBounds(193, 42, 120, 48);
		panelButtons.add(btnPrint);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer[] D = new Integer[3];
					D[0] = Integer.parseInt(jtxtDept_name.getText()) ;
					D[1] = Integer.parseInt(jtxtBuildingdept.getText()) ;
					D[2] = Integer.parseInt(jtxtBudget.getText()) ;
					}catch (NumberFormatException ev) {
						 ev.printStackTrace();
			
					}
				
				
				
				String  sql= "INSERT INTO DEPARTMENT (dept_name , building, budget)VALUES(?,?,?)";
				try{
					pst = conn.prepareStatement(sql);
					pst.setString(1,jtxtDept_name.getText());
					pst.setString(2,jtxtBuildingdept.getText());
					pst.setString(3,jtxtBudget.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();
					
					
				}catch(Exception ev) {
					
					JOptionPane.showMessageDialog(null,"Data been saved successfuly .");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{
						jtxtDept_name .getText(),
						jtxtBuildingdept.getText(),
						jtxtBudget.getText(),
				});
				
				if(table.getSelectedRow()== -1) {
					if(table.getRowCount()==0) {
						JOptionPane.showMessageDialog(null,"Membership update confirmed","Classroom's Database ",JOptionPane.OK_OPTION);
					}
				}
				
			}
			}
		);
		btnAdd.setBounds(30, 42, 120, 48);
		panelButtons.add(btnAdd);
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(Color.BLACK);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String cell = (table.getModel().getValueAt(row, 0).toString());
				
				
				String sql = "DELETE FROM department where dept_name='"+cell+"'";
				try {
					pst = conn.prepareStatement(sql);
					pst.executeUpdate();
					
                   
					JOptionPane.showMessageDialog(null,"Deleted successfuly .");
				} catch(SQLException ex) {
					 
					JOptionPane.showMessageDialog(null,"Message = "+ex.getMessage()+"\nSQLState = "
	                           +ex.getSQLState()+"\nErrorCode = "+ex.getErrorCode())
					;}
				
				model = (DefaultTableModel) table.getModel();
				model.removeRow(table.getSelectedRow());
				
				
				
				
				
           
			
			
		
		}});
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setBackground(Color.BLACK);
		btnDelete.setBounds(364, 42, 120, 48);
		panelButtons.add(btnDelete);
		
		JPanel panelConfig = new JPanel();
		panelConfig.setBackground(Color.GRAY);
		panelConfig.setBounds(0, 0, 203, 519);
		panelClass.add(panelConfig);
		panelConfig.setLayout(null);
		
		JTextPane txtpnInsertBuilding = new JTextPane();
		txtpnInsertBuilding.setEditable(false);
		txtpnInsertBuilding.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnInsertBuilding.setText("Department name :");
		txtpnInsertBuilding.setBackground(Color.GRAY);
		txtpnInsertBuilding.setBounds(10, 80, 183, 28);
		panelConfig.add(txtpnInsertBuilding);
		
		JTextPane txtpnRoomNumber = new JTextPane();
		txtpnRoomNumber.setEditable(false);
		txtpnRoomNumber.setText("Building :");
		txtpnRoomNumber.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnRoomNumber.setBackground(Color.GRAY);
		txtpnRoomNumber.setBounds(10, 178, 183, 28);
		panelConfig.add(txtpnRoomNumber);
		
		JTextPane txtpnCapacity = new JTextPane();
		txtpnCapacity.setText("Budget : ");
		txtpnCapacity.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnCapacity.setEditable(false);
		txtpnCapacity.setBackground(Color.GRAY);
		txtpnCapacity.setBounds(10, 294, 183, 28);
		panelConfig.add(txtpnCapacity);
		
		jtxtBuildingdept = new JTextField();
		jtxtBuildingdept.setBounds(10, 215, 143, 28);
		panelConfig.add(jtxtBuildingdept);
		jtxtBuildingdept.setColumns(10);
		
		jtxtDept_name = new JTextField();
		jtxtDept_name.setBounds(10, 119, 143, 28);
		panelConfig.add(jtxtDept_name);
		jtxtDept_name.setColumns(10);
		
		jtxtBudget = new JTextField();
		jtxtBudget.setBounds(10, 333, 143, 28);
		panelConfig.add(jtxtBudget);
		jtxtBudget.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(215, 146, 459, 361);
		panelClass.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 439, 339);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"dept_name", "building", "budget"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, Double.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		

	}
}
