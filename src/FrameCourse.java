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



public class FrameCourse {

	private JFrame framecourse;
	private JPanel contentPane;
	Connection conn = null ; 
	PreparedStatement pst = null ; 
	ResultSet rs = null ; 
	DefaultTableModel model = new DefaultTableModel();
	Statement stm = null ; 
	
	private Image course_logo = new ImageIcon(FrameDashboard.class.getResource("res/CourseLogo.png")).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
	private JTable table;
	private JTextField jtxtcourseid;
	private JTextField jtxttitle;
	private JTextField jtxtcredits;
	private JTextField jtxtdept_name;
    private JComboBox<String> comboBoxdept ;
	/**
	 * Launch the application.
	 */
	public static void CourseScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameCourse window = new FrameCourse();
					window.framecourse.setVisible(true);
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
			String sql = "Select course_id , title , dept_name , credits from course ";
			try 
			{
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[4];
				
				while(rs.next())
				{
					columnData[0] = rs.getString("course_id");
					columnData[1] = rs.getString("title");
					columnData[2] = rs.getString("dept_name");
					columnData[3] = rs.getString("credits");
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
	public FrameCourse() {
		initialize();
		conn = Sqliteconnect.ConnectDB();
		Object col[] = {"course_id" , "title" , "dept_name","credits" };
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
		framecourse = new JFrame();
		framecourse.getContentPane().setBackground(new Color(70, 130, 180));
		framecourse.setBounds(100, 100, 700, 700);
		framecourse.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framecourse.getContentPane().setLayout(null);
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		framecourse.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelClass = new JPanel();
		panelClass.setBackground(new Color(70, 130, 180));
		panelClass.setBounds(0, 0, 684, 661);
		contentPane.add(panelClass);
		panelClass.setLayout(null);
		
		JLabel lblClassLogo = new JLabel("Course's Database");
		lblClassLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClassLogo.setFont(new Font("Arial Narrow", Font.BOLD, 24));
		lblClassLogo.setBounds(47, 0, 492, 88);
		lblClassLogo.setIcon(new ImageIcon(course_logo));
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
				framecourse.dispose();
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
				R[0] = Integer.parseInt(jtxtcourseid.getText()) ;
				R[1] = Integer.parseInt(jtxttitle.getText()) ;
				R[2] = Integer.parseInt(jtxtdept_name.getText()) ;
				R[3] = Integer.parseInt(jtxtcredits.getText()) ;
				}catch (NumberFormatException ev) {
					 ev.printStackTrace();
		
				}
				
				String  sql= "INSERT INTO COURSE (course_id , title , dept_name , credits )VALUES(?,?,?,?)";
				try{
					pst = conn.prepareStatement(sql);
					pst.setString(1,jtxtcourseid.getText());
					pst.setString(2,jtxttitle.getText());
					pst.setString(3,jtxtdept_name.getText());
					pst.setString(4,jtxtcredits.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();
					
					
				}catch(Exception ev) {
					
					
					JOptionPane.showMessageDialog(null,"Data been saved successfuly .");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{
					jtxtcourseid.getText(),
					jtxttitle.getText(),
					jtxtdept_name.getText(),
					jtxtcredits.getText(),
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
				String cellcourseid = table.getModel().getValueAt(row, 0).toString();
				try {
				String sql = "DELETE FROM course WHERE course_id ='"+cellcourseid+"'";
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
		txtpnCourse_ID.setText("Course ID  : ");
		txtpnCourse_ID.setBackground(Color.GRAY);
		txtpnCourse_ID.setBounds(10, 80, 143, 28);
		panelConfig.add(txtpnCourse_ID);
		
		JTextPane txtpnTitle = new JTextPane();
		txtpnTitle.setEditable(false);
		txtpnTitle.setText("Title : ");
		txtpnTitle.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnTitle.setBackground(Color.GRAY);
		txtpnTitle.setBounds(10, 166, 183, 28);
		panelConfig.add(txtpnTitle);
		
		JTextPane txtpnCredits = new JTextPane();
		txtpnCredits.setText("Credits  : ");
		txtpnCredits.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnCredits.setEditable(false);
		txtpnCredits.setBackground(Color.GRAY);
		txtpnCredits.setBounds(10, 439, 183, 28);
		panelConfig.add(txtpnCredits);
		
		jtxtcourseid = new JTextField();
		jtxtcourseid.setBounds(10, 119, 143, 28);
		panelConfig.add(jtxtcourseid);
		jtxtcourseid.setColumns(10);
		
		jtxttitle = new JTextField();
		jtxttitle.setColumns(10);
		jtxttitle.setBounds(10, 205, 143, 28);
		panelConfig.add(jtxttitle);
		
		jtxtcredits = new JTextField();
		jtxtcredits.setColumns(10);
		jtxtcredits.setBounds(10, 480, 143, 28);
		panelConfig.add(jtxtcredits);
		
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
				"course_id", "title", "dept_name","credits"
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