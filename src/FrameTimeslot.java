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



public class FrameTimeslot {

	private JFrame frametimeslot;
	private JPanel contentPane;
	Connection conn = null ; 
	PreparedStatement pst = null ; 
	ResultSet rs = null ; 
	DefaultTableModel model = new DefaultTableModel();
	
	private Image advisor_logo = new ImageIcon(FrameDashboard.class.getResource("res/AdvisorLogo.png")).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
	private JTable table;
	private JTextField jtxttimeslotid;
	private JTextField jtxtday;
	private JTextField jtxtstarthour;
	private JTextField jtxtstartmin;
	private JTextField jtxtendhour;
	private JTextField jtxtendmin;

	/**
	 * Launch the application.
	 */
	public static void TimeslotScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameTimeslot window = new FrameTimeslot();
					window.frametimeslot.setVisible(true);
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
			String sql = "Select time_slot_id,day , start_hr,start_min,end_hr,end_min from time_slot ";
			try 
			{
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[2];
				
				while(rs.next())
				{
					columnData[0] = rs.getString("time_slot_id");
					columnData[1] = rs.getString("day");
					columnData[2] = rs.getString("start_hr");
					columnData[3] = rs.getString("start_min");
					columnData[4] = rs.getString("end_hr");
					columnData[5] = rs.getString("end_min");
					
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
	public FrameTimeslot() {
		initialize();
		conn = Sqliteconnect.ConnectDB();
		Object col[] = {"time_slot_id","day" , "start_hr","start_min","end_hr","end_min" };
		model.setColumnIdentifiers(col);
		table.setModel(model);
		updateTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frametimeslot = new JFrame();
		frametimeslot.getContentPane().setBackground(new Color(70, 130, 180));
		frametimeslot.setBounds(100, 100, 700, 700);
		frametimeslot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frametimeslot.getContentPane().setLayout(null);
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		frametimeslot.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelClass = new JPanel();
		panelClass.setBackground(new Color(70, 130, 180));
		panelClass.setBounds(0, 0, 684, 661);
		contentPane.add(panelClass);
		panelClass.setLayout(null);
		
		JLabel lblClassLogo = new JLabel("Timeslot  Database : ");
		lblClassLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClassLogo.setFont(new Font("Arial Narrow", Font.BOLD, 24));
		lblClassLogo.setBounds(47, 0, 492, 88);
		lblClassLogo.setIcon(new ImageIcon(advisor_logo));
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
				frametimeslot.dispose();
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
					Integer[] D = new Integer[6];
					D[0] = Integer.parseInt(jtxttimeslotid.getText()) ;
					D[1] = Integer.parseInt(jtxtday.getText()) ;
					D[2] = Integer.parseInt(jtxtstarthour.getText()) ;
					D[3] = Integer.parseInt(jtxtstartmin.getText()) ;
					D[4] = Integer.parseInt(jtxtendhour.getText()) ;
					D[5] = Integer.parseInt(jtxtendmin.getText()) ;
					}catch (NumberFormatException ev) {
						 ev.printStackTrace();
			
					}
				
				
				
				String  sql= "INSERT INTO time_slot (time_slot_id,day , start_hr,start_min,end_hr,end_min)VALUES(?,?,?,?,?,?)";
				try{
					pst = conn.prepareStatement(sql);
					pst.setString(1,jtxttimeslotid.getText());
					pst.setString(2,jtxtday.getText());
					pst.setString(3,jtxtstarthour.getText());
					pst.setString(4,jtxtstartmin.getText());
					pst.setString(5,jtxtendhour.getText());
					pst.setString(6,jtxtendmin.getText());
			
					
					pst.execute();
					
					rs.close();
					pst.close();
					
					
				}catch(Exception ev) {
					
					JOptionPane.showMessageDialog(null,"Data been saved successfuly .");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{
						jtxttimeslotid .getText(),
						jtxtday.getText(),
						jtxtstarthour.getText(),
						jtxtstartmin.getText(),
						jtxtendhour.getText(),
						jtxtendmin.getText(),
						
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
				String cell = (table.getModel().getValueAt(row, 1).toString());
				
				
				String sql = "DELETE FROM time_slot where time_slot_id ='"+cell+"'";
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
		
		JTextPane txtpntimeslotid = new JTextPane();
		txtpntimeslotid.setEditable(false);
		txtpntimeslotid.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpntimeslotid.setText("Time slot ID : ");
		txtpntimeslotid.setBackground(Color.GRAY);
		txtpntimeslotid.setBounds(10, 11, 183, 28);
		panelConfig.add(txtpntimeslotid);
		
		JTextPane txtpnday = new JTextPane();
		txtpnday.setEditable(false);
		txtpnday.setText("Day : ");
		txtpnday.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnday.setBackground(Color.GRAY);
		txtpnday.setBounds(10, 89, 183, 28);
		panelConfig.add(txtpnday);
		
		jtxtday = new JTextField();
		jtxtday.setBounds(10, 128, 143, 28);
		panelConfig.add(jtxtday);
		jtxtday.setColumns(10);
		
		jtxttimeslotid = new JTextField();
		jtxttimeslotid.setBounds(10, 50, 143, 28);
		panelConfig.add(jtxttimeslotid);
		jtxttimeslotid.setColumns(10);
		
		JTextPane txtpnStartHour = new JTextPane();
		txtpnStartHour.setText("Start hour : ");
		txtpnStartHour.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnStartHour.setEditable(false);
		txtpnStartHour.setBackground(Color.GRAY);
		txtpnStartHour.setBounds(10, 167, 183, 28);
		panelConfig.add(txtpnStartHour);
		
		jtxtstarthour = new JTextField();
		jtxtstarthour.setColumns(10);
		jtxtstarthour.setBounds(10, 206, 143, 28);
		panelConfig.add(jtxtstarthour);
		
		JTextPane txtpnStartmin = new JTextPane();
		txtpnStartmin.setText("Start min : ");
		txtpnStartmin.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnStartmin.setEditable(false);
		txtpnStartmin.setBackground(Color.GRAY);
		txtpnStartmin.setBounds(10, 245, 183, 28);
		panelConfig.add(txtpnStartmin);
		
		jtxtstartmin = new JTextField();
		jtxtstartmin.setColumns(10);
		jtxtstartmin.setBounds(10, 284, 143, 28);
		panelConfig.add(jtxtstartmin);
		
		JTextPane txtpnendhour = new JTextPane();
		txtpnendhour.setText("End hour : ");
		txtpnendhour.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnendhour.setEditable(false);
		txtpnendhour.setBackground(Color.GRAY);
		txtpnendhour.setBounds(10, 323, 183, 28);
		panelConfig.add(txtpnendhour);
		
		jtxtendhour = new JTextField();
		jtxtendhour.setColumns(10);
		jtxtendhour.setBounds(10, 362, 143, 28);
		panelConfig.add(jtxtendhour);
		
		JTextPane txtpnendmin = new JTextPane();
		txtpnendmin.setText("End min : ");
		txtpnendmin.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnendmin.setEditable(false);
		txtpnendmin.setBackground(Color.GRAY);
		txtpnendmin.setBounds(10, 401, 183, 28);
		panelConfig.add(txtpnendmin);
		
		jtxtendmin = new JTextField();
		jtxtendmin.setColumns(10);
		jtxtendmin.setBounds(10, 440, 143, 28);
		panelConfig.add(jtxtendmin);
		
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
					"time_slot_id","day" , "start_hr","start_min","end_hr","end_min" 
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class , java.math.BigDecimal.class  , java.math.BigDecimal.class  ,java.math.BigDecimal.class ,java.math.BigDecimal.class 
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		

	}
}

