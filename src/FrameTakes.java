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
import java.sql.SQLException;
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
import javax.swing.DefaultComboBoxModel;



public class FrameTakes {

	private JFrame frametakes;
	private JPanel contentPane;
	Connection conn = null ; 
	PreparedStatement pst = null ; 
	ResultSet rs = null ; 
	DefaultTableModel model = new DefaultTableModel();
	Statement stm = null ; 
	
	private Image takes_logo = new ImageIcon(FrameDashboard.class.getResource("res/TakesLogo.png")).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
	private JTable table;
	private JTextField jtxtstudentid;
	private JTextField jtxtcourseid;
	private JTextField jtxtsecid;
	private JTextField jtxtgrade;
	private JTextField jtxtsemester;
	private JTextField jtxtyear;
	private JComboBox<String> comboBoxstudentid ;
	private JComboBox<String> comboBoxsecid ;
	private JComboBox<String> comboBoxyear ;
	private JComboBox<String> comboBoxsemester ;
	private JComboBox<String> comboBoxgrade ;
	private JComboBox<String> comboBoxcourseid;
	
	

	/**
	 * Launch the application.
	 */
	public static void TakesScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameTakes window = new FrameTakes ();
					window.frametakes.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void printSQLException(SQLException ex) {

	    for (Throwable e : ex) {
	        if (e instanceof SQLException) {
	           

	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " +
	                    ((SQLException)e).getSQLState());

	                System.err.println("Error Code: " +
	                    ((SQLException)e).getErrorCode());

	                System.err.println("Message: " + e.getMessage());

	                Throwable t = ex.getCause();
	                while(t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }
	
	public void updateTable()
	{
		conn = Sqliteconnect.ConnectDB();
		if(conn != null)
		{
			String sql = "Select ID , course_id , sec_id , semester , year , grade from takes ";
			try 
			{
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[6];
				
				while(rs.next())
				{
					columnData[0] = rs.getString("ID");
					columnData[1] = rs.getString("course_id");
					columnData[2] = rs.getString("sec_id");
					columnData[3] = rs.getString("semester");
					columnData[4] = rs.getString("year");
					columnData[5] = rs.getString("grade");
					
					model.addRow(columnData);
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null , e);
			}
		}
	}

	private void updateCombostudentid() {
		String sql = "select * from student ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxyear.addItem(rs.getString("ID"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}
	
	private void updateCombosecid() {
		String sql = "select * from section ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxgrade.addItem(rs.getString("sec_id"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}
	
	private void updateCombocourseid() {
		String sql = "select * from section ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxstudentid.addItem(rs.getString("course_id"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}

	/**
	 * Create the application.
	 */
	public FrameTakes() {
		initialize();
		conn = Sqliteconnect.ConnectDB();
		Object col[] = {"ID" , "course_id","sec_id" , "semester","year","grade" };
		model.setColumnIdentifiers(col);
		table.setModel(model);
		updateTable();
		updateCombostudentid();
		updateCombosecid();
		updateCombocourseid();
		updateCombosemester();
		updateComboyear();
	}
	
	private void updateComboyear() {
		String sql = "select * from section ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxstudentid.addItem(rs.getString("year"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}
	
	private void updateCombosemester() {
		String sql = "select * from section ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxstudentid.addItem(rs.getString("semester"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frametakes = new JFrame();
		frametakes.getContentPane().setBackground(new Color(70, 130, 180));
		frametakes.setBounds(100, 100, 700, 700);
		frametakes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frametakes.getContentPane().setLayout(null);
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		frametakes.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelClass = new JPanel();
		panelClass.setBackground(new Color(70, 130, 180));
		panelClass.setBounds(0, 0, 684, 661);
		contentPane.add(panelClass);
		panelClass.setLayout(null);
		
		JLabel lblClassLogo = new JLabel("Takes's Database");
		lblClassLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClassLogo.setFont(new Font("Arial Narrow", Font.BOLD, 24));
		lblClassLogo.setBounds(47, 0, 492, 88);
		lblClassLogo.setIcon(new ImageIcon(takes_logo));
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
				frametakes.dispose();
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
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int row = table.getSelectedRow();
				String cellstudentid = table.getModel().getValueAt(row, 0).toString();
				try {
				String sql = "DELETE FROM takes WHERE  ID ='"+cellstudentid+"'";
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
				Integer[] R = new Integer[6];
				R[0] = Integer.parseInt(jtxtstudentid.getText()) ;
				R[1] = Integer.parseInt(jtxtcourseid.getText()) ;
				R[2] = Integer.parseInt(jtxtsecid.getText()) ;
				R[3] = Integer.parseInt(jtxtsemester.getText()) ;
				R[4] = Integer.parseInt(jtxtyear.getText()) ;
				R[5] = Integer.parseInt(jtxtgrade.getText()) ;
			
				}catch (NumberFormatException ev) {
					 ev.printStackTrace();
		
				}
				
				String  sql= "INSERT INTO TAKES ( ID  ,course_id,  sec_id , semester , year , grade )VALUES(?,?,?,?,?,?)";
				try{ 
					pst = conn.prepareStatement(sql);
					pst.setString(1,jtxtstudentid.getText());
					pst.setString(2,jtxtcourseid.getText());
					pst.setString(3,jtxtsecid.getText());
					pst.setString(4,jtxtsemester.getText());
					pst.setString(5,jtxtyear.getText());
					pst.setString(6,jtxtgrade.getText());
				
					
					pst.execute();
					
					rs.close();
					pst.close();
					
					
				}catch(Exception ev ) {
					
					ev.printStackTrace();
					//JOptionev.printStackTrace();Pane.showMessageDialog(null,"Data been saved successfuly .");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{
						jtxtstudentid.getText(),
						jtxtcourseid.getText(),
						jtxtsecid.getText(),
						jtxtsemester.getText(),
						jtxtyear.getText(),
						jtxtgrade.getText(),
						
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
		
		JTextPane txtpnstudid = new JTextPane();
		txtpnstudid.setEditable(false);
		txtpnstudid.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnstudid.setText("Student ID : ");
		txtpnstudid.setBackground(Color.GRAY);
		txtpnstudid.setBounds(10, 11, 143, 28);
		panelConfig.add(txtpnstudid);
		
		JTextPane txtpnCourseid = new JTextPane();
		txtpnCourseid.setEditable(false);
		txtpnCourseid.setText("Course ID :");
		txtpnCourseid.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnCourseid.setBackground(Color.GRAY);
		txtpnCourseid.setBounds(10, 115, 183, 28);
		panelConfig.add(txtpnCourseid);
		
		JTextPane txtpnsectionid = new JTextPane();
		txtpnsectionid.setText("Section ID : ");
		txtpnsectionid.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnsectionid.setEditable(false);
		txtpnsectionid.setBackground(Color.GRAY);
		txtpnsectionid.setBounds(10, 194, 183, 28);
		panelConfig.add(txtpnsectionid);
		
		JTextPane txtpnyear = new JTextPane();
		txtpnyear.setText("Year : ");
		txtpnyear.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnyear.setEditable(false);
		txtpnyear.setBackground(Color.GRAY);
		txtpnyear.setBounds(10, 359, 183, 28);
		panelConfig.add(txtpnyear);
		
		comboBoxsecid = new JComboBox<String>();
		comboBoxsecid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxsecid.getSelectedItem().toString();
				jtxtsecid.setText(selectedvalue);
				
			}
		});
		comboBoxsecid.setBounds(10, 222, 143, 28);
		panelConfig.add(comboBoxsecid);
		
		jtxtstudentid = new JTextField();
		jtxtstudentid.setEditable(false);
		jtxtstudentid.setBounds(10, 70, 143, 28);
		panelConfig.add(jtxtstudentid);
		jtxtstudentid.setColumns(10);
		
		jtxtcourseid = new JTextField();
		jtxtcourseid.setEditable(false);
		jtxtcourseid.setBounds(10, 166, 143, 28);
		panelConfig.add(jtxtcourseid);
		jtxtcourseid.setColumns(10);
		
		jtxtsecid = new JTextField();
		jtxtsecid.setEditable(false);
		jtxtsecid.setBounds(10, 250, 143, 28);
		panelConfig.add(jtxtsecid);
		jtxtsecid.setColumns(10);
		
		jtxtgrade = new JTextField();
		jtxtgrade.setEditable(false);
		jtxtgrade.setBounds(10, 491, 143, 28);
		panelConfig.add(jtxtgrade);
		jtxtgrade.setColumns(10);
		
		JTextPane txtpnsemester = new JTextPane();
		txtpnsemester.setText("Semester : ");
		txtpnsemester.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnsemester.setEditable(false);
		txtpnsemester.setBackground(Color.GRAY);
		txtpnsemester.setBounds(10, 277, 183, 28);
		panelConfig.add(txtpnsemester);
		
		comboBoxsemester = new JComboBox<String>();
		comboBoxsemester.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxsemester.getSelectedItem().toString();
				jtxtsemester.setText(selectedvalue);
				
			}
		});
		comboBoxsemester.setBounds(10, 304, 143, 28);
		panelConfig.add(comboBoxsemester);
		
		jtxtsemester = new JTextField();
		jtxtsemester.setEditable(false);
		jtxtsemester.setColumns(10);
		jtxtsemester.setBounds(10, 330, 143, 28);
		panelConfig.add(jtxtsemester);
		
	    comboBoxyear = new JComboBox<String>();
		comboBoxyear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxyear.getSelectedItem().toString();
				jtxtyear.setText(selectedvalue);
				
			}
		});
		comboBoxyear.setBounds(10, 384, 143, 28);
		panelConfig.add(comboBoxyear);
		
		jtxtyear = new JTextField();
		jtxtyear.setEditable(false);
		jtxtyear.setColumns(10);
		jtxtyear.setBounds(10, 409, 143, 28);
		panelConfig.add(jtxtyear);
		
		JTextPane txtpngrade = new JTextPane();
		txtpngrade.setText("Grade : ");
		txtpngrade.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpngrade.setEditable(false);
		txtpngrade.setBackground(Color.GRAY);
		txtpngrade.setBounds(10, 436, 183, 28);
		panelConfig.add(txtpngrade);
		
		comboBoxgrade = new JComboBox<String>();
		comboBoxgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue1 = comboBoxgrade.getSelectedItem().toString();
				jtxtgrade.setText(selectedvalue1);
				
			}
		});
		
		comboBoxgrade.setBounds(10, 464, 143, 28);
		panelConfig.add(comboBoxgrade);
		
		comboBoxstudentid = new JComboBox<String>();
		comboBoxstudentid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxstudentid.getSelectedItem().toString();
				jtxtstudentid.setText(selectedvalue);
				
			}
		});
		comboBoxstudentid.setBounds(10, 43, 143, 28);
		panelConfig.add(comboBoxstudentid);
		
		comboBoxcourseid = new JComboBox<String>();
		comboBoxcourseid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxcourseid.getSelectedItem().toString();
				jtxtcourseid.setText(selectedvalue);
				
			}
		});
		comboBoxcourseid.setBounds(10, 139, 143, 28);
		panelConfig.add(comboBoxcourseid);
		
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
				"student ID,course_id", "sec_id", "semester", "year" , "grade"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class,String.class, java.math.BigDecimal.class , String.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		

	}
}
