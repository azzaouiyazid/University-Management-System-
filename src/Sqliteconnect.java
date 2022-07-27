import java.sql.*;
import javax.swing.*;
	
public class Sqliteconnect {

	public static Connection ConnectDB()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Electro-Market.ma\\eclipse-workspace\\Ums\\yazid.db");
			JOptionPane.showMessageDialog(null,"Connection has been made");
			return conn ; 
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Connection has not  been made");
			return null ; 
			
		}
	}
}

