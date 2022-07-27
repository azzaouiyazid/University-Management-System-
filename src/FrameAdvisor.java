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



public class FrameAdvisor {

	private JFrame frameadvisor;
	private JPanel contentPane;
	Connection conn = null ; 
	PreparedStatement pst = null ; 
	ResultSet rs = null ; 
	DefaultTableModel model = new DefaultTableModel();
	
	private Image advisor_logo = new ImageIcon(FrameDashboard.class.getResource("res/AdvisorLogo.png")).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
	private JTable table;
	private JComboBox<String> comboBoxstudentid ;
	private JComboBox<String> comboBoxinstructorid ;
	private JTextField jtxtstudentid;
	private JTextField jtxtinstructorid;

	/**
	 * Launch the application.
	 */
	public static void AdvisorScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameAdvisor window = new FrameAdvisor();
					window.frameadvisor.setVisible(true);
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
			String sql = "Select s_ID, i_ID  from advisor ";
			try 
			{
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[2];
				
				while(rs.next())
				{
					columnData[0] = rs.getString("s_id");
					columnData[1] = rs.getString("i_id");
					
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
		String sql = "select * from STUDENT ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxstudentid.addItem(rs.getString("ID"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}
	private void updateComboinstructorid() {
		String sql = "select * from instructor ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxstudentid.addItem(rs.getString("ID"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}

	/**
	 * Create the application.
	 */
	public FrameAdvisor() {
		initialize();
		conn = Sqliteconnect.ConnectDB();
		Object col[] = {"s_id" , "i_id"  };
		model.setColumnIdentifiers(col);
		table.setModel(model);
		updateTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frameadvisor = new JFrame();
		frameadvisor.getContentPane().setBackground(new Color(70, 130, 180));
		frameadvisor.setBounds(100, 100, 700, 700);
		frameadvisor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameadvisor.getContentPane().setLayout(null);
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		frameadvisor.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelClass = new JPanel();
		panelClass.setBackground(new Color(70, 130, 180));
		panelClass.setBounds(0, 0, 684, 661);
		contentPane.add(panelClass);
		panelClass.setLayout(null);
		
		JLabel lblClassLogo = new JLabel("Advisor's Database : ");
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
				frameadvisor.dispose();
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
					Integer[] D = new Integer[2];
					D[0] = Integer.parseInt(jtxtstudentid.getText()) ;
					D[1] = Integer.parseInt(jtxtinstructorid.getText()) ;
					}catch (NumberFormatException ev) {
						 ev.printStackTrace();
			
					}
				
				
				
				String  sql= "INSERT INTO ADVISOR (s_id , i_id)VALUES(?,?)";
				try{
					pst = conn.prepareStatement(sql);
					pst.setString(1,jtxtstudentid.getText());
					pst.setString(2,jtxtinstructorid.getText());
			
					
					pst.execute();
					
					rs.close();
					pst.close();
					
					
				}catch(Exception ev) {
					
					JOptionPane.showMessageDialog(null,"Data been saved successfuly .");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{
						jtxtstudentid .getText(),
						jtxtinstructorid.getText(),
						
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
				
				
				String sql = "DELETE FROM advisor where s_id='"+cell+"'";
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
		
		JTextPane txtpnstudentid = new JTextPane();
		txtpnstudentid.setEditable(false);
		txtpnstudentid.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnstudentid.setText("Student ID : ");
		txtpnstudentid.setBackground(Color.GRAY);
		txtpnstudentid.setBounds(10, 80, 183, 28);
		panelConfig.add(txtpnstudentid);
		
		JTextPane txtpninstructorid = new JTextPane();
		txtpninstructorid.setEditable(false);
		txtpninstructorid.setText("Instructor ID : ");
		txtpninstructorid.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpninstructorid.setBackground(Color.GRAY);
		txtpninstructorid.setBounds(10, 277, 183, 28);
		panelConfig.add(txtpninstructorid);
		
		jtxtinstructorid = new JTextField();
		jtxtinstructorid.setEditable(false);
		jtxtinstructorid.setBounds(10, 344, 143, 28);
		panelConfig.add(jtxtinstructorid);
		jtxtinstructorid.setColumns(10);
		
		jtxtstudentid = new JTextField();
		jtxtstudentid.setEditable(false);
		jtxtstudentid.setBounds(10, 147, 143, 28);
		panelConfig.add(jtxtstudentid);
		jtxtstudentid.setColumns(10);
		
		comboBoxinstructorid = new JComboBox<String>();
		comboBoxinstructorid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxinstructorid.getSelectedItem().toString();
				jtxtinstructorid.setText(selectedvalue);
				
			}
		});
		comboBoxinstructorid.setBounds(10, 316, 143, 28);
		panelConfig.add(comboBoxinstructorid);
		
		 comboBoxstudentid = new JComboBox<String>();
		comboBoxstudentid.setBounds(10, 119, 143, 28);
		panelConfig.add(comboBoxstudentid);
		
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
				"Student ID", "Instructor ID"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		

	}
}
