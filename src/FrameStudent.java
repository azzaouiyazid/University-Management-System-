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
import java.sql.*;
import java.text.MessageFormat;
import javax.swing.*;



public class FrameStudent {

	private JFrame framestudent;
	private JPanel contentPane;
	Connection conn = null ; 
	PreparedStatement pst = null ; 
	ResultSet rs = null ; 
	DefaultTableModel model = new DefaultTableModel();
	Statement stm = null ; 
	
	private Image student_logo = new ImageIcon(FrameDashboard.class.getResource("res/StudentLogo.png")).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
	private JTable table;
	private JTextField jtxtid;
	private JTextField jtxtname;
	private JTextField jtxttotcred;
	private JTextField jtxtdept_name;
    private JComboBox<String> comboBoxdept ;
	/**
	 * Launch the application.
	 */
	public static void StudentScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameStudent window = new FrameStudent();
					window.framestudent.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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


	public void updateTable()
	{
		conn = Sqliteconnect.ConnectDB();
		if(conn != null)
		{
			String sql = "Select ID , name , dept_name , tot_cred from student ";
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
					columnData[3] = rs.getString("tot_cred");
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
	public FrameStudent() {
		initialize();
		conn = Sqliteconnect.ConnectDB();
		Object col[] = {"ID" , "name" , "dept_name","tot_cred" };
		model.setColumnIdentifiers(col);
		table.setModel(model);
		updateTable();
		updateCombo();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		framestudent = new JFrame();
		framestudent.getContentPane().setBackground(new Color(70, 130, 180));
		framestudent.setBounds(100, 100, 700, 700);
		framestudent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framestudent.getContentPane().setLayout(null);
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		framestudent.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelClass = new JPanel();
		panelClass.setBackground(new Color(70, 130, 180));
		panelClass.setBounds(0, 0, 684, 661);
		contentPane.add(panelClass);
		panelClass.setLayout(null);
		
		JLabel lblClassLogo = new JLabel("Student's Database");
		lblClassLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClassLogo.setFont(new Font("Arial Narrow", Font.BOLD, 24));
		lblClassLogo.setBounds(47, 0, 492, 88);
		lblClassLogo.setIcon(new ImageIcon(student_logo));
		panelClass.add(lblClassLogo);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(new Color(128, 128, 128));
		panelButtons.setBounds(0, 519, 684, 142);
		panelClass.add(panelButtons);
		panelButtons.setLayout(null);
		
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
		btnPrint.setBounds(205, 42, 120, 48);
		panelButtons.add(btnPrint);
		
		JButton btnBack = new JButton("Back to Menu");
		btnBack.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				framestudent.dispose();
				FrameDashboard fmain = new FrameDashboard();
				fmain.main(null);
			}
		});
		btnBack.setForeground(Color.WHITE);
		btnBack.setBackground(Color.BLACK);
		btnBack.setBounds(530, 42, 120, 48);
		panelButtons.add(btnBack);
		
		
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(35, 42, 120, 48);
		panelButtons.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				Integer[] R = new Integer[4];
				R[0] = Integer.parseInt(jtxtid.getText()) ;
				R[1] = Integer.parseInt(jtxtname.getText()) ;
				R[2] = Integer.parseInt(jtxtdept_name.getText()) ;
				R[3] = Integer.parseInt(jtxttotcred.getText()) ;
				}catch (NumberFormatException ev) {
					 ev.printStackTrace();
		
				}
				
				String  sql= "INSERT INTO STUDENT (ID , name , dept_name , tot_cred )VALUES(?,?,?,?)";
				try{
					pst = conn.prepareStatement(sql);
					pst.setString(1,jtxtid.getText());
					pst.setString(2,jtxtname.getText());
					pst.setString(3,jtxtdept_name.getText());
					pst.setString(4,jtxttotcred.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();
					
					
				}catch(Exception ev) {
					
					
					JOptionPane.showMessageDialog(null,"Data been saved successfuly .");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{
					jtxtid.getText(),
					jtxtname.getText(),
					jtxtdept_name.getText(),
					jtxttotcred.getText(),
				});
				
				if(table.getSelectedRow()== -1) {
					if(table.getRowCount()==0) {
						JOptionPane.showMessageDialog(null,"Membership update confirmed","Classroom's Database ",JOptionPane.OK_OPTION);
					}
				}
				
			}
		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(Color.BLACK);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int row = table.getSelectedRow();
				String cellid = table.getModel().getValueAt(row, 0).toString();
				try {
				String sql = "DELETE FROM STUDENT WHERE ID ='"+cellid+"'";
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
		
		JPanel panelConfig = new JPanel();
		panelConfig.setBackground(Color.GRAY);
		panelConfig.setBounds(0, 0, 203, 519);
		panelClass.add(panelConfig);
		panelConfig.setLayout(null);
		
		JTextPane txtpnCourse_ID = new JTextPane();
		txtpnCourse_ID.setEditable(false);
		txtpnCourse_ID.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnCourse_ID.setText("ID  : ");
		txtpnCourse_ID.setBackground(Color.GRAY);
		txtpnCourse_ID.setBounds(10, 80, 143, 28);
		panelConfig.add(txtpnCourse_ID);
		
		JTextPane txtpnTitle = new JTextPane();
		txtpnTitle.setEditable(false);
		txtpnTitle.setText("Name : ");
		txtpnTitle.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnTitle.setBackground(Color.GRAY);
		txtpnTitle.setBounds(10, 166, 183, 28);
		panelConfig.add(txtpnTitle);
		
		JTextPane txtpntotcred = new JTextPane();
		txtpntotcred.setText("Total Credits : ");
		txtpntotcred.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpntotcred.setEditable(false);
		txtpntotcred.setBackground(Color.GRAY);
		txtpntotcred.setBounds(10, 439, 183, 28);
		panelConfig.add(txtpntotcred);
		
		jtxtid = new JTextField();
		jtxtid.setBounds(10, 119, 143, 28);
		panelConfig.add(jtxtid);
		jtxtid.setColumns(10);
		
		jtxtname = new JTextField();
		jtxtname.setColumns(10);
		jtxtname.setBounds(10, 205, 143, 28);
		panelConfig.add(jtxtname);
		
		jtxttotcred = new JTextField();
		jtxttotcred.setColumns(10);
		jtxttotcred.setBounds(10, 480, 143, 28);
		panelConfig.add(jtxttotcred);
		
		jtxtdept_name = new JTextField();
		jtxtdept_name.setEditable(false);
		jtxtdept_name.setBounds(10, 340, 143, 28);
		panelConfig.add(jtxtdept_name);
		jtxtdept_name.setColumns(10);
		
		JTextPane txtpnDept_name = new JTextPane();
		txtpnDept_name.setText("Department Name : ");
		txtpnDept_name.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnDept_name.setEditable(false);
		txtpnDept_name.setBackground(Color.GRAY);
		txtpnDept_name.setBounds(10, 260, 183, 28);
		panelConfig.add(txtpnDept_name);
		
		comboBoxdept = new JComboBox<String>();
		comboBoxdept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxdept.getSelectedItem().toString();
				jtxtdept_name.setText(selectedvalue);
				
			}
		});
		comboBoxdept.setBounds(10, 299, 143, 28);
		panelConfig.add(comboBoxdept);
		
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
				"ID", "name", "dept_name","tot_cred"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class , 	java.math.BigDecimal.class
			};
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		

	}
}
