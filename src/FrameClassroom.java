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



public class FrameClassroom {

	private JFrame frameclassroom;
	private JPanel contentPane;
	Connection conn = null ; 
	PreparedStatement pst = null ; 
	ResultSet rs = null ; 
	DefaultTableModel model = new DefaultTableModel();
	Statement stm = null ; 
	
	private Image classroom_logo = new ImageIcon(FrameDashboard.class.getResource("res/ClassroomLogo.png")).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
	private JTable table;
	private JTextField jtxtBuilding;
	private JTextField jtxtRoomnumber;
	private JTextField jtxtCapacity;
	

	/**
	 * Launch the application.
	 */
	public static void ClassroomScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameClassroom window = new FrameClassroom();
					window.frameclassroom.setVisible(true);
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
			String sql = "Select building , room_number , capacity from classroom ";
			try 
			{
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[3];
				
				while(rs.next())
				{
					columnData[0] = rs.getString("building");
					columnData[1] = rs.getString("room_number");
					columnData[2] = rs.getString("capacity");
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
	public FrameClassroom() {
		initialize();
		conn = Sqliteconnect.ConnectDB();
		Object col[] = {"building" , "room_number" , "capacity" };
		model.setColumnIdentifiers(col);
		table.setModel(model);
		updateTable();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frameclassroom = new JFrame();
		frameclassroom.getContentPane().setBackground(new Color(70, 130, 180));
		frameclassroom.setBounds(100, 100, 700, 700);
		frameclassroom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameclassroom.getContentPane().setLayout(null);
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		frameclassroom.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelClass = new JPanel();
		panelClass.setBackground(new Color(70, 130, 180));
		panelClass.setBounds(0, 0, 684, 661);
		contentPane.add(panelClass);
		panelClass.setLayout(null);
		
		JLabel lblClassLogo = new JLabel("Classroom's Database");
		lblClassLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClassLogo.setFont(new Font("Arial Narrow", Font.BOLD, 24));
		lblClassLogo.setBounds(47, 0, 492, 88);
		lblClassLogo.setIcon(new ImageIcon(classroom_logo));
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
				frameclassroom.dispose();
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
				Integer[] R = new Integer[3];
				R[0] = Integer.parseInt(jtxtBuilding.getText()) ;
				R[1] = Integer.parseInt(jtxtRoomnumber.getText()) ;
				R[2] = Integer.parseInt(jtxtCapacity.getText()) ;
				}catch (NumberFormatException ev) {
					 ev.printStackTrace();
		
				}
				
				String  sql= "INSERT INTO CLASSROOM (building , room_number , capacity)VALUES(?,?,?)";
				try{
					pst = conn.prepareStatement(sql);
					pst.setString(1,jtxtBuilding.getText());
					pst.setString(2,jtxtRoomnumber.getText());
					pst.setString(3,jtxtCapacity.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();
					
					
				}catch(Exception ev) {
					
					
					JOptionPane.showMessageDialog(null,"Data been saved successfuly .");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{
					jtxtBuilding.getText(),
					jtxtRoomnumber.getText(),
					jtxtCapacity.getText(),
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
				String cellbuilding = table.getModel().getValueAt(row, 0).toString();
				try {
				String sql = "DELETE FROM classroom WHERE building ='"+cellbuilding+"'";
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
		
		JTextPane txtpnInsertBuilding = new JTextPane();
		txtpnInsertBuilding.setEditable(false);
		txtpnInsertBuilding.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnInsertBuilding.setText("Building : ");
		txtpnInsertBuilding.setBackground(Color.GRAY);
		txtpnInsertBuilding.setBounds(10, 80, 143, 28);
		panelConfig.add(txtpnInsertBuilding);
		
		JTextPane txtpnRoomNumber = new JTextPane();
		txtpnRoomNumber.setEditable(false);
		txtpnRoomNumber.setText("Room Number : ");
		txtpnRoomNumber.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnRoomNumber.setBackground(Color.GRAY);
		txtpnRoomNumber.setBounds(10, 239, 183, 28);
		panelConfig.add(txtpnRoomNumber);
		
		JTextPane txtpnCapacity = new JTextPane();
		txtpnCapacity.setText("Capacity : ");
		txtpnCapacity.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnCapacity.setEditable(false);
		txtpnCapacity.setBackground(Color.GRAY);
		txtpnCapacity.setBounds(10, 394, 183, 28);
		panelConfig.add(txtpnCapacity);
		
		jtxtBuilding = new JTextField();
		jtxtBuilding.setBounds(10, 119, 143, 28);
		panelConfig.add(jtxtBuilding);
		jtxtBuilding.setColumns(10);
		
		jtxtRoomnumber = new JTextField();
		jtxtRoomnumber.setColumns(10);
		jtxtRoomnumber.setBounds(10, 278, 143, 28);
		panelConfig.add(jtxtRoomnumber);
		
		jtxtCapacity = new JTextField();
		jtxtCapacity.setColumns(10);
		jtxtCapacity.setBounds(10, 433, 143, 28);
		panelConfig.add(jtxtCapacity);
		
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
				"building", "room_number", "capacity"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, Integer.class
			};
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		

	}
}
