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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ListSelectionModel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;



public class FrameInstructor {

	private JFrame frameinstructor;
	private JPanel contentPane;
	Connection conn = null ; 
	PreparedStatement pst = null ; 
	ResultSet rs = null ; 
	DefaultTableModel model = new DefaultTableModel();
	Statement stm = null ; 
	
	private Image instructor_logo = new ImageIcon(FrameDashboard.class.getResource("res/InstructorLogo.png")).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
	private JTable table;
	private JTextField jtxtinstid;
	private JTextField jtxtName;
	private JTextField jtxtdeptname;
	private JTextField jtxtsalary;
	private JComboBox<String> comboBoxdept ;

	/**
	 * Launch the application.
	 */
	public static void InstructorScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameInstructor window = new FrameInstructor();
					window.frameinstructor.setVisible(true);
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
			String sql = "Select ID , name , dept_name , salary from instructor ";
			try 
			{
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[4];
				
				while(rs.next())
				{
					columnData[0] = rs.getString("ID");
					columnData[1] = rs.getString("name");
					columnData[2] = rs.getString("dept_name");
					columnData[3] = rs.getString("salary");
					model.addRow(columnData);
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null , e);
			}
		}
	}

	private void updateCombo() {
		String sql = "select * from department ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxdept.addItem(rs.getString("dept_name"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}

	/**
	 * Create the application.
	 */
	public FrameInstructor() {
		initialize();
		conn = Sqliteconnect.ConnectDB();
		Object col[] = {"ID" , "name" , "dept_name","salary" };
		model.setColumnIdentifiers(col);
		table.setModel(model);
		updateTable();
		updateCombo();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameinstructor = new JFrame();
		frameinstructor.getContentPane().setBackground(new Color(70, 130, 180));
		frameinstructor.setBounds(100, 100, 700, 700);
		frameinstructor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameinstructor.getContentPane().setLayout(null);
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		frameinstructor.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelClass = new JPanel();
		panelClass.setBackground(new Color(70, 130, 180));
		panelClass.setBounds(0, 0, 684, 661);
		contentPane.add(panelClass);
		panelClass.setLayout(null);
		
		JLabel lblClassLogo = new JLabel("Instructor's Database");
		lblClassLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClassLogo.setFont(new Font("Arial Narrow", Font.BOLD, 24));
		lblClassLogo.setBounds(47, 0, 492, 88);
		lblClassLogo.setIcon(new ImageIcon(instructor_logo));
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
				frameinstructor.dispose();
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
				MessageFormat header = new MessageFormat("Printing in progress");
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
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int row = table.getSelectedRow();
				String cellcourseid = table.getModel().getValueAt(row, 0).toString();
				try {
				String sql = "DELETE FROM instructor WHERE ID  ='"+cellcourseid+"'";
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
				}catch(Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"error.");}
				model = (DefaultTableModel) table.getModel();
				model.removeRow(table.getSelectedRow());
				
				
			
           
			
			
		
		}});
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setBackground(Color.BLACK);
		btnDelete.setBounds(364, 42, 120, 48);
		panelButtons.add(btnDelete);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				Integer[] R = new Integer[4];
				R[0] = Integer.parseInt(jtxtinstid.getText()) ;
				R[1] = Integer.parseInt(jtxtName.getText()) ;
				R[2] = Integer.parseInt(jtxtdeptname.getText()) ;
				R[3] = Integer.parseInt(jtxtsalary.getText()) ;
				}catch (NumberFormatException ev) {
					 ev.printStackTrace();
		
				}
				
				String  sql= "INSERT INTO instructor (ID  , name , dept_name , salary  )VALUES(?,?,?,?)";
				try{
					pst = conn.prepareStatement(sql);
					pst.setString(1,jtxtinstid.getText());
					pst.setString(2,jtxtName.getText());
					pst.setString(3,jtxtdeptname.getText());
					pst.setString(4,jtxtsalary.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();
					
					
				}catch(Exception ev) {
					
					
					JOptionPane.showMessageDialog(null,"Data been saved successfuly .");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{
						jtxtinstid.getText(),
						jtxtName.getText(),
						jtxtdeptname.getText(),
						jtxtsalary.getText(),
				});
				
				if(table.getSelectedRow()== -1) {
					if(table.getRowCount()==0) {
						JOptionPane.showMessageDialog(null,"Membership update confirmed","Classroom's Database ",JOptionPane.OK_OPTION);
					}
				}
				
			}
		});
		btnAdd.setBounds(34, 42, 120, 48);
		panelButtons.add(btnAdd);
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(Color.BLACK);
		
		JPanel panelConfig = new JPanel();
		panelConfig.setBackground(Color.GRAY);
		panelConfig.setBounds(0, 0, 203, 519);
		panelClass.add(panelConfig);
		panelConfig.setLayout(null);
		
		JTextPane txtpnInsertBuilding = new JTextPane();
		txtpnInsertBuilding.setEditable(false);
		txtpnInsertBuilding.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnInsertBuilding.setText("ID : ");
		txtpnInsertBuilding.setBackground(Color.GRAY);
		txtpnInsertBuilding.setBounds(10, 39, 143, 28);
		panelConfig.add(txtpnInsertBuilding);
		
		JTextPane txtpnRoomNumber = new JTextPane();
		txtpnRoomNumber.setEditable(false);
		txtpnRoomNumber.setText("Name : ");
		txtpnRoomNumber.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnRoomNumber.setBackground(Color.GRAY);
		txtpnRoomNumber.setBounds(10, 117, 183, 28);
		panelConfig.add(txtpnRoomNumber);
		
		JTextPane txtpnCapacity = new JTextPane();
		txtpnCapacity.setText("Department name : ");
		txtpnCapacity.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnCapacity.setEditable(false);
		txtpnCapacity.setBackground(Color.GRAY);
		txtpnCapacity.setBounds(10, 195, 183, 28);
		panelConfig.add(txtpnCapacity);
		
		JTextPane txtpnSalary = new JTextPane();
		txtpnSalary.setText("Salary : ");
		txtpnSalary.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnSalary.setEditable(false);
		txtpnSalary.setBackground(Color.GRAY);
		txtpnSalary.setBounds(10, 353, 183, 28);
		panelConfig.add(txtpnSalary);
		
		comboBoxdept = new JComboBox<String>();
		comboBoxdept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxdept.getSelectedItem().toString();
				jtxtdeptname.setText(selectedvalue);
				
			}
		});
		comboBoxdept.setBounds(10, 234, 143, 28);
		panelConfig.add(comboBoxdept);
		
		jtxtinstid = new JTextField();
		jtxtinstid.setBounds(10, 78, 143, 28);
		panelConfig.add(jtxtinstid);
		jtxtinstid.setColumns(10);
		
		jtxtName = new JTextField();
		jtxtName.setBounds(10, 156, 143, 28);
		panelConfig.add(jtxtName);
		jtxtName.setColumns(10);
		
		jtxtdeptname = new JTextField();
		jtxtdeptname.setEditable(false);
		jtxtdeptname.setBounds(10, 272, 143, 28);
		panelConfig.add(jtxtdeptname);
		jtxtdeptname.setColumns(10);
		
		jtxtsalary = new JTextField();
		jtxtsalary.setBounds(10, 392, 143, 28);
		panelConfig.add(jtxtsalary);
		jtxtsalary.setColumns(10);
		
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
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"ID", "name", "dept_name", "salary"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		

	}
}