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



public class FrameTeaches {

	private JFrame frameteaches;
	private JPanel contentPane;
	Connection conn = null ; 
	PreparedStatement pst = null ; 
	ResultSet rs = null ; 
	DefaultTableModel model = new DefaultTableModel();
	Statement stm = null ; 
	
	private Image teaches_logo = new ImageIcon(FrameDashboard.class.getResource("res/TeachesLogo.png")).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
	private JTable table;
	
	private JTextField jtxtid;
	private JTextField jtxtcourseid;
	private JTextField jtxtsecid;
	private JTextField jtxtsemester;
	private JTextField jtxtyear;
	private JComboBox<String> comboBoxid ;
	private JComboBox<String> comboBoxsecid ;
	private JComboBox<String> comboBoxyear ;
	private JComboBox<String> comboBoxsemester ;
	private JComboBox<String> comboBoxcourseid ;
	
	

	/**
	 * Launch the application.
	 */
	public static void TeachesScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameTeaches window = new FrameTeaches();
					window.frameteaches.setVisible(true);
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
			String sql = "Select ID , course_id , sec_id , semester , year from teaches ";
			try 
			{
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[5];
				
				while(rs.next())
				{
					columnData[0] = rs.getString("ID");
					columnData[1] = rs.getString("course_id");
					columnData[2] = rs.getString("sec_id");
					columnData[3] = rs.getString("semester");
					columnData[4] = rs.getString("year");
					
					model.addRow(columnData);
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null , e);
			}
		}
	}
	
	private void updateComboid() {
		String sql = "select * from instructor ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxid.addItem(rs.getString("ID"));
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
				comboBoxcourseid.addItem(rs.getString("course_id"));
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
				comboBoxsecid.addItem(rs.getString("sec_id"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}
	
	private void updateComboyear() {
		String sql = "select * from section ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxcourseid.addItem(rs.getString("course_id"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}

	/**
	 * Create the application.
	 */
	public FrameTeaches() {
		initialize();
		conn = Sqliteconnect.ConnectDB();
		Object col[] = {"ID" , "course_id" , "sec_id","semester","year"};
		model.setColumnIdentifiers(col);
		table.setModel(model);
		updateTable();
		updateComboid();
		updateCombocourseid();
		updateCombosecid();
		updateComboyear();
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frameteaches = new JFrame();
		frameteaches.getContentPane().setBackground(new Color(70, 130, 180));
		frameteaches.setBounds(100, 100, 700, 700);
		frameteaches.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameteaches.getContentPane().setLayout(null);
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		frameteaches.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelClass = new JPanel();
		panelClass.setBackground(new Color(70, 130, 180));
		panelClass.setBounds(0, 0, 684, 661);
		contentPane.add(panelClass);
		panelClass.setLayout(null);
		
		JLabel lblClassLogo = new JLabel("Teaches Database");
		lblClassLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClassLogo.setFont(new Font("Arial Narrow", Font.BOLD, 24));
		lblClassLogo.setBounds(47, 0, 492, 88);
		lblClassLogo.setIcon(new ImageIcon(teaches_logo));
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
				frameteaches.dispose();
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
				String cellID = table.getModel().getValueAt(row, 0).toString();
				try {
				String sql = "DELETE FROM Teaches WHERE  ID ='"+cellID+"'";
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
				try { //"ID" , "course_id" , "sec_id","semester","year"
				Integer[] R = new Integer[5];
				R[0] = Integer.parseInt(jtxtid.getText()) ;
				R[1] = Integer.parseInt(jtxtcourseid.getText()) ;
				R[2] = Integer.parseInt(jtxtsecid.getText()) ;
				R[3] = Integer.parseInt(jtxtsemester.getText()) ;
				R[4] = Integer.parseInt(jtxtyear.getText()) ;
				
				
				}catch (NumberFormatException ev) {
					 ev.printStackTrace();
		
				}
				
				String  sql= "INSERT INTO Teaches (ID , course_id , sec_id, semester , year)VALUES(?,?,?,?,?)";
				try{ 
					pst = conn.prepareStatement(sql);
					
					pst.setString(1,jtxtid.getText());
					pst.setString(2,jtxtcourseid.getText());
					pst.setString(3,jtxtsecid.getText());
					pst.setString(4,jtxtsemester.getText());
					pst.setString(5,jtxtyear.getText());
				
					
					pst.execute();
					
					rs.close();
					pst.close();
					
					
				}catch(Exception ev ) {
					
					ev.printStackTrace();
					//JOptionev.printStackTrace();Pane.showMessageDialog(null,"Data been saved successfuly .");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{
						jtxtid.getText(),
						jtxtcourseid.getText(),
						jtxtsecid.getText(),
						jtxtsemester.getText(),
						jtxtyear.getText(),
						
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
		
		JTextPane txtpnID = new JTextPane();
		txtpnID.setEditable(false);
		txtpnID.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnID.setText("ID : ");
		txtpnID.setBackground(Color.GRAY);
		txtpnID.setBounds(10, 11, 143, 22);
		panelConfig.add(txtpnID);
		
		JTextPane txtpnCourseID = new JTextPane();
		txtpnCourseID.setEditable(false);
		txtpnCourseID.setText("Course ID  :");
		txtpnCourseID.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnCourseID.setBackground(Color.GRAY);
		txtpnCourseID.setBounds(10, 109, 183, 28);
		panelConfig.add(txtpnCourseID);
		
		JTextPane txtpnSectionID = new JTextPane();
		txtpnSectionID.setText("Section ID :");
		txtpnSectionID.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnSectionID.setEditable(false);
		txtpnSectionID.setBackground(Color.GRAY);
		txtpnSectionID.setBounds(10, 206, 183, 28);
		panelConfig.add(txtpnSectionID);
		
		JTextPane txtpnYear = new JTextPane();
		txtpnYear.setText("Year : ");
		txtpnYear.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnYear.setEditable(false);
		txtpnYear.setBackground(Color.GRAY);
		txtpnYear.setBounds(10, 396, 183, 28);
		panelConfig.add(txtpnYear);
		
		comboBoxid = new JComboBox<String>();
		comboBoxid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxid.getSelectedItem().toString();
				jtxtid.setText(selectedvalue);
				
			}
		});
		comboBoxid.setBounds(10, 44, 143, 28);
		panelConfig.add(comboBoxid);
		
		jtxtid = new JTextField();
		jtxtid.setEditable(false);
		jtxtid.setBounds(10, 70, 143, 28);
		panelConfig.add(jtxtid);
		jtxtid.setColumns(10);
		
		jtxtcourseid = new JTextField();
		jtxtcourseid.setEditable(false);
		jtxtcourseid.setBounds(10, 167, 143, 28);
		panelConfig.add(jtxtcourseid);
		jtxtcourseid.setColumns(10);
		
		jtxtsecid = new JTextField();
		jtxtsecid.setEditable(false);
		jtxtsecid.setBounds(10, 263, 143, 28);
		panelConfig.add(jtxtsecid);
		jtxtsecid.setColumns(10);
		
		JTextPane txtpnSemester = new JTextPane();
		txtpnSemester.setText("Semester :");
		txtpnSemester.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnSemester.setEditable(false);
		txtpnSemester.setBackground(Color.GRAY);
		txtpnSemester.setBounds(10, 302, 183, 28);
		panelConfig.add(txtpnSemester);
		
		comboBoxsemester = new JComboBox<String>();
		comboBoxsemester.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxsemester.getSelectedItem().toString();
				jtxtsemester.setText(selectedvalue);
				
			}
		});
		comboBoxsemester.setBounds(10, 333, 143, 28);
		panelConfig.add(comboBoxsemester);
		
		jtxtsemester = new JTextField();
		jtxtsemester.setEditable(false);
		jtxtsemester.setColumns(10);
		jtxtsemester.setBounds(10, 361, 143, 28);
		panelConfig.add(jtxtsemester);
		
		comboBoxyear = new JComboBox<String>();
		comboBoxyear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxyear.getSelectedItem().toString();
				jtxtyear.setText(selectedvalue);
				
			}
		});
		comboBoxyear.setBounds(10, 424, 143, 28);
		panelConfig.add(comboBoxyear);
		
	
		
		comboBoxcourseid = new JComboBox<String>();
		comboBoxcourseid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxcourseid.getSelectedItem().toString();
				jtxtcourseid.setText(selectedvalue);
				
			}
		});
		comboBoxcourseid.setBounds(10, 138, 143, 28);
		panelConfig.add(comboBoxcourseid);
		
		
		
		jtxtyear = new JTextField();
		jtxtyear.setEditable(false);
		jtxtyear.setColumns(10);
		jtxtyear.setBounds(10, 452, 143, 28);
		panelConfig.add(jtxtyear);
		
		comboBoxsecid = new JComboBox<String>();
		comboBoxsecid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxsecid.getSelectedItem().toString();
				jtxtsecid.setText(selectedvalue);
				
			}
		});
		comboBoxsecid.setBounds(10, 235, 143, 28);
		panelConfig.add(comboBoxsecid);
		
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
				"ID", "course_id","sec_id", "semester", "year" 
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class,String.class, java.math.BigDecimal.class 
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		

	}
}
