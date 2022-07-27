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



public class FrameSection {

	private JFrame framesection;
	private JPanel contentPane;
	Connection conn = null ; 
	PreparedStatement pst = null ; 
	ResultSet rs = null ; 
	DefaultTableModel model = new DefaultTableModel();
	Statement stm = null ; 
	
	private Image section_logo = new ImageIcon(FrameDashboard.class.getResource("res/SectionLogo.png")).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
	private JTable table;
	private JTextField jtxtcourseid;
	private JTextField jtxtsecid;
	private JTextField jtxtsemester;
	private JTextField jtxtroomnumber;
	private JTextField jtxtyear;
	private JTextField jtxtbuilding;
	private JTextField jtxttimeslot;
	private JComboBox<String> comboBoxsemester ;
	private JComboBox<String> comboBoxbuild ;
	private JComboBox<String> comboBoxyear ;
	private JComboBox<String> comboBoxroomno ;
	private JComboBox<String> comboBoxcourseid ;
	

	/**
	 * Launch the application.
	 */
	public static void SectionScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameSection window = new FrameSection ();
					window.framesection.setVisible(true);
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
			String sql = "Select course_id , sec_id , semester , year , building , room_number , time_slot_id from section ";
			try 
			{
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[7];
				
				while(rs.next())
				{
					columnData[0] = rs.getString("course_id");
					columnData[1] = rs.getString("sec_id");
					columnData[2] = rs.getString("semester");
					columnData[3] = rs.getString("year");
					columnData[4] = rs.getString("building");
					columnData[5] = rs.getString("room_number");
					columnData[6] = rs.getString("time_slot_id");
					model.addRow(columnData);
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null , e);
			}
		}
	}

	private void updateCombobuild() {
		String sql = "select * from classroom ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxbuild.addItem(rs.getString("building"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}
	
	private void updateComboroomno() {
		String sql = "select * from classroom ";
		try {
			pst= conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				comboBoxroomno.addItem(rs.getString("room_number"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			
		}
	}
	
	private void updateCombocourseid() {
		String sql = "select * from course ";
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
	public FrameSection() {
		initialize();
		conn = Sqliteconnect.ConnectDB();
		Object col[] = {"course_id" , "sec_id" , "semester","year","building" , "room_number" , "time_slot_id " };
		model.setColumnIdentifiers(col);
		table.setModel(model);
		updateTable();
		updateCombobuild();
		updateComboroomno();
		updateCombocourseid();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		framesection = new JFrame();
		framesection.getContentPane().setBackground(new Color(70, 130, 180));
		framesection.setBounds(100, 100, 700, 700);
		framesection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framesection.getContentPane().setLayout(null);
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		framesection.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelClass = new JPanel();
		panelClass.setBackground(new Color(70, 130, 180));
		panelClass.setBounds(0, 0, 684, 661);
		contentPane.add(panelClass);
		panelClass.setLayout(null);
		
		JLabel lblClassLogo = new JLabel("Section's Database");
		lblClassLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClassLogo.setFont(new Font("Arial Narrow", Font.BOLD, 24));
		lblClassLogo.setBounds(47, 0, 492, 88);
		lblClassLogo.setIcon(new ImageIcon(section_logo));
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
				framesection.dispose();
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
				String cellcourseid = table.getModel().getValueAt(row, 0).toString();
				try {
				String sql = "DELETE FROM section WHERE  course_id ='"+cellcourseid+"'";
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
				try { //"course_id" , "sec_id" , "semester","year","building" , "room_number" , "time_slot_id 
				Integer[] R = new Integer[7];
				R[0] = Integer.parseInt(jtxtcourseid.getText()) ;
				R[1] = Integer.parseInt(jtxtsecid.getText()) ;
				R[2] = Integer.parseInt(jtxtsemester.getText()) ;
				R[3] = Integer.parseInt(jtxtyear.getText()) ;
				R[4] = Integer.parseInt(jtxtbuilding.getText()) ;
				R[5] = Integer.parseInt(jtxtroomnumber.getText()) ;
				R[6] = Integer.parseInt(jtxttimeslot.getText()) ;
				}catch (NumberFormatException ev) {
					 ev.printStackTrace();
		
				}
				
				String  sql= "INSERT INTO SECTION ( course_id  , sec_id , semester , year , building , room_number , time_slot_id  )VALUES(?,?,?,?,?,?,?)";
				try{ 
					pst = conn.prepareStatement(sql);
					pst.setString(1,jtxtcourseid.getText());
					pst.setString(2,jtxtsecid.getText());
					pst.setString(3,jtxtsemester.getText());
					pst.setString(4,jtxtyear.getText());
					pst.setString(5,jtxtbuilding.getText());
					pst.setString(6,jtxtroomnumber.getText());
					pst.setString(7,jtxttimeslot.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();
					
					
				}catch(Exception ev ) {
					
					ev.printStackTrace();
					//JOptionev.printStackTrace();Pane.showMessageDialog(null,"Data been saved successfuly .");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{
						jtxtcourseid.getText(),
						jtxtsecid.getText(),
						jtxtsemester.getText(),
						jtxtyear.getText(),
						jtxtbuilding.getText(),
						jtxtroomnumber.getText(),
						jtxttimeslot.getText(),
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
		
		JTextPane txtpncourseid = new JTextPane();
		txtpncourseid.setEditable(false);
		txtpncourseid.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpncourseid.setText("Course ID : ");
		txtpncourseid.setBackground(Color.GRAY);
		txtpncourseid.setBounds(10, 11, 143, 22);
		panelConfig.add(txtpncourseid);
		
		JTextPane txtpnSectionID = new JTextPane();
		txtpnSectionID.setEditable(false);
		txtpnSectionID.setText("Section ID : \r\n");
		txtpnSectionID.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnSectionID.setBackground(Color.GRAY);
		txtpnSectionID.setBounds(10, 95, 183, 28);
		panelConfig.add(txtpnSectionID);
		
		JTextPane txtpnCapacity = new JTextPane();
		txtpnCapacity.setText("Semester : \r\n");
		txtpnCapacity.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnCapacity.setEditable(false);
		txtpnCapacity.setBackground(Color.GRAY);
		txtpnCapacity.setBounds(10, 146, 183, 28);
		panelConfig.add(txtpnCapacity);
		
		JTextPane txtpnBuilding = new JTextPane();
		txtpnBuilding.setText("Building : ");
		txtpnBuilding.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnBuilding.setEditable(false);
		txtpnBuilding.setBackground(Color.GRAY);
		txtpnBuilding.setBounds(10, 304, 183, 28);
		panelConfig.add(txtpnBuilding);
		
		comboBoxsemester = new JComboBox<String>();
		comboBoxsemester.setModel(new DefaultComboBoxModel(new String[] {"Fall", "Winter", "Spring", "Summer"}));
		comboBoxsemester.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxsemester.getSelectedItem().toString();
				jtxtsemester.setText(selectedvalue);
				
			}
		});
		comboBoxsemester.setBounds(10, 174, 143, 28);
		panelConfig.add(comboBoxsemester);
		
		jtxtcourseid = new JTextField();
		jtxtcourseid.setEditable(false);
		jtxtcourseid.setBounds(10, 70, 143, 28);
		panelConfig.add(jtxtcourseid);
		jtxtcourseid.setColumns(10);
		
		jtxtsecid = new JTextField();
		jtxtsecid.setBounds(10, 123, 143, 28);
		panelConfig.add(jtxtsecid);
		jtxtsecid.setColumns(10);
		
		jtxtsemester = new JTextField();
		jtxtsemester.setEditable(false);
		jtxtsemester.setBounds(10, 202, 143, 28);
		panelConfig.add(jtxtsemester);
		jtxtsemester.setColumns(10);
		
		jtxtroomnumber = new JTextField();
		jtxtroomnumber.setEditable(false);
		jtxtroomnumber.setBounds(10, 439, 143, 28);
		panelConfig.add(jtxtroomnumber);
		jtxtroomnumber.setColumns(10);
		
		JTextPane txtpnYear = new JTextPane();
		txtpnYear.setText("Year : ");
		txtpnYear.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnYear.setEditable(false);
		txtpnYear.setBackground(Color.GRAY);
		txtpnYear.setBounds(10, 227, 183, 28);
		panelConfig.add(txtpnYear);
		
		comboBoxyear = new JComboBox<String>();
		comboBoxyear.setModel(new DefaultComboBoxModel<String>(new String[] {"2100 ", "2099 ", "2098 ", "2097 ", "2096 ", "2095 ", "2094 ", "2093 ", "2092 ", "2091 ", "2090  ", "2089 ", "2088 ", "2087 ", "2086 ", "2085 ", "2084 ", "2083 ", "2082 ", "2081 ", "2080  ", "2079 ", "2078 ", "2077 ", "2076  ", "2075 ", "2074 ", "2073 ", "2072 ", "2071 ", "2070  ", "2069 ", "2068 ", "2067 ", "2066 ", "2065  ", "2064 ", "2063 ", "2062 ", "2061 ", "2060 ", "2059 ", "2058 ", "2057 ", "2056 ", "2055 ", "2054 ", "2053 ", "2052 ", "2051 ", "2050 ", "2049 ", "2048 ", "2047 ", "2046  ", "2045 ", "2044  ", "2043 ", "2042 ", "2041 ", "2040 ", "2039 ", "2038 ", "2037 ", "2036 ", "2035 ", "2034 ", "2033 ", "2032 ", "2031 ", "2030 ", "2029  ", "2028 ", "2027 ", "2026 ", "2025 ", "2024 ", "2023 ", "2022 ", "2021 ", "2020 ", "2019 ", "2018 ", "2017  ", "2016 ", "2015 ", "2014  ", "2013 ", "2012  ", "2011 ", "2010 ", "2009 ", "2008 ", "2007 ", "2006 ", "2005 ", "2004 ", "2003 ", "2002 ", "2001 ", "2000 ", "1999 ", "1998 ", "1997 ", "1996  ", "1995 ", "1994 ", "1993 ", "1992 ", "1991 ", "1990 ", "1989 ", "1988 ", "1987 ", "1986 ", "1985 ", "1984 ", "1983 ", "1982 ", "1981 ", "1980  ", "1979 ", "1978 ", "1977  ", "1976  ", "1975 ", "1974 ", "1973 ", "1972 ", "1971 ", "1970 ", "1969 ", "1968 ", "1967 ", "1966 ", "1965  ", "1964 ", "1963 ", "1962 ", "1961 ", "1960 ", "1959 ", "1958 ", "1957 ", "1956 ", "1955 ", "1954 ", "1953 ", "1952 ", "1951 ", "1950  ", "1949  ", "1948  ", "1947  ", "1946 ", "1945  ", "1944 ", "1943 ", "1942 ", "1941 ", "1940", "1939 ", "1938 ", "1937  ", "1936 ", "1935 ", "1934 ", "1933  ", "1932 ", "1931 ", "1930 ", "1929 ", "1928 ", "1927 ", "1926 ", "1925 ", "1924 ", "1923 ", "1922 ", "1921 ", "1920 ", "1919 ", "1918 ", "1917 ", "1916 ", "1915 ", "1914 ", "1913 ", "1912 ", "1911 ", "1910 ", "1909 ", "1908 ", "1907 ", "1906 ", "1905 ", "1904 ", "1903 ", "1902 ", "1901 ", "1900 ", "1899  ", "1898 ", "1897 ", "1896 ", "1895 ", "1894 ", "1893 ", "1892 ", "1891 ", "1890 ", "1889 ", "1888  ", "1887 ", "1886 ", "1885 ", "1884  ", "1883 ", "1882 ", "1881 ", "1880  ", "1879 ", "1878  ", "1877 ", "1876  ", "1875 ", "1874 ", "1873  ", "1872  ", "1871 ", "1870 ", "1869 ", "1868 ", "1867  ", "1866 ", "1865 ", "1864  ", "1863 ", "1862 ", "1861 ", "1860 ", "1859 ", "1858 ", "1857 ", "1856 ", "1855 ", "1854 ", "1853 ", "1852 ", "1851 ", "1850 ", "1849 ", "1848 ", "1847  ", "1846 ", "1845 ", "1844 ", "1843 ", "1842 ", "1841 ", "1840  ", "1839 ", "1838 ", "1837  ", "1836 ", "1835 ", "1834  ", "1833 ", "1832 ", "1831 ", "1830 ", "1829 ", "1828 ", "1827 ", "1826  ", "1825 ", "1824  ", "1823  ", "1822 ", "1821 ", "1820 ", "1819  ", "1818", "1817 ", "1816 ", "1815 ", "1814 ", "1813 ", "1812 ", "1811 ", "1810 ", "1809 ", "1808 ", "1807 ", "1806 ", "1805 ", "1804 ", "1803 ", "1802 ", "1801 ", "1800 ", "1799 ", "1798 ", "1797 ", "1796 ", "1795 ", "1794 ", "1793  ", "1792 ", "1791 ", "1790 ", "1789 ", "1788 ", "1787 ", "1786 ", "1785 ", "1784 ", "1783 ", "1782 ", "1781 ", "1780 ", "1779  ", "1778 ", "1777  ", "1776  ", "1775 ", "1774 ", "1773  ", "1772  ", "1771 ", "1770 ", "1769 ", "1768 ", "1767 ", "1766 ", "1765 ", "1764 ", "1763 ", "1762 ", "1761 ", "1760  ", "1759  ", "1758  ", "1757 ", "1756 ", "1755 ", "1754  ", "1753 ", "1752 ", "1751 ", "1750 ", "1749 ", "1748 ", "1747 ", "1746 ", "1745 ", "1744 ", "1743 ", "1742 ", "1741 ", "1740 ", "1739  ", "1738 ", "1737 ", "1736 ", "1735 ", "1734 ", "1733  ", "1732 ", "1731 ", "1730  ", "1729  ", "1728  ", "1727 ", "1726 ", "1725 ", "1724 ", "1723 ", "1722 ", "1721 ", "1720 ", "1719 ", "1718 ", "1717  ", "1716 ", "1715  ", "1714 ", "1713 ", "1712 ", "1711 ", "1710 ", "1709 ", "1708  ", "1707 ", "1706 ", "1705 ", "1704 ", "1703 ", "1702 ", "1701 "}));
		comboBoxyear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxyear.getSelectedItem().toString();
				jtxtyear.setText(selectedvalue);
				
			}
		});
		comboBoxyear.setBounds(10, 253, 143, 28);
		panelConfig.add(comboBoxyear);
		
		jtxtyear = new JTextField();
		jtxtyear.setEditable(false);
		jtxtyear.setColumns(10);
		jtxtyear.setBounds(10, 282, 143, 28);
		panelConfig.add(jtxtyear);
		
	    comboBoxbuild = new JComboBox<String>();
	    comboBoxbuild.setModel(new DefaultComboBoxModel(new String[] {""}));
		comboBoxbuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxbuild.getSelectedItem().toString();
				jtxtbuilding.setText(selectedvalue);
				
			}
		});
		comboBoxbuild.setBounds(10, 333, 143, 28);
		panelConfig.add(comboBoxbuild);
		
		jtxtbuilding = new JTextField();
		jtxtbuilding.setEditable(false);
		jtxtbuilding.setColumns(10);
		jtxtbuilding.setBounds(10, 361, 143, 28);
		panelConfig.add(jtxtbuilding);
		
		JTextPane txtpnRoomNumber = new JTextPane();
		txtpnRoomNumber.setText("Room number : ");
		txtpnRoomNumber.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnRoomNumber.setEditable(false);
		txtpnRoomNumber.setBackground(Color.GRAY);
		txtpnRoomNumber.setBounds(10, 385, 183, 28);
		panelConfig.add(txtpnRoomNumber);
		
		JTextPane txtpnTimeSlotId = new JTextPane();
		txtpnTimeSlotId.setText("Time slot ID : ");
		txtpnTimeSlotId.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtpnTimeSlotId.setEditable(false);
		txtpnTimeSlotId.setBackground(Color.GRAY);
		txtpnTimeSlotId.setBounds(10, 464, 183, 28);
		panelConfig.add(txtpnTimeSlotId);
		
		jtxttimeslot = new JTextField();
		jtxttimeslot.setColumns(10);
		jtxttimeslot.setBounds(10, 491, 143, 28);
		panelConfig.add(jtxttimeslot);
		
		comboBoxroomno = new JComboBox<String>();
		comboBoxroomno.setModel(new DefaultComboBoxModel(new String[] {""}));
		comboBoxroomno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue1 = comboBoxroomno.getSelectedItem().toString();
				jtxtroomnumber.setText(selectedvalue1);
				
			}
		});
		
		comboBoxroomno.setBounds(10, 413, 143, 28);
		panelConfig.add(comboBoxroomno);
		
		comboBoxcourseid = new JComboBox<String>();
		comboBoxcourseid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedvalue = comboBoxcourseid.getSelectedItem().toString();
				jtxtcourseid.setText(selectedvalue);
				
			}
		});
		comboBoxcourseid.setBounds(10, 43, 143, 28);
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
				"course_id", "sec_id", "semester", "year" , "building" , "room_number" , "time_slot_id"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, java.math.BigDecimal.class , String.class, String.class, String.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		

	}
}
