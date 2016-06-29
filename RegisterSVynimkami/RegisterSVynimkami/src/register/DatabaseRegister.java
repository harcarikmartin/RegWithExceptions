package register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import register.exception.BadIndexException;
import register.exception.DuplicationException;
import register.exception.ValidationException;
import register.exception.WrongFormatException;

public class DatabaseRegister implements Register {
	public static final String DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";
    public static final String URL = "jdbc:derby://localhost:1527/sample";
    public static final String USER = "Mar";
    public static final String PASSWORD = "Mar";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE register (id INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY1)"
    		+ ", name VARCHAR(60) NOT NULL, phone_number VARCHAR(20) NOT NULL)";
	private static final String ADD_QUERY = "INSERT INTO register (name, phone_number) VALUES (?, ?)";
	private static final String GET_COUNT_QUERY = "SELECT COUNT(*) FROM register";
	public static Connection c = null;
    
    public DatabaseRegister() {
    	try {
    		Class.forName(DRIVER_CLASS);
    		c = DriverManager.getConnection(URL, USER, PASSWORD);
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public void createTable() {
    	Statement stmt;
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(CREATE_TABLE_QUERY);
	        stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @Override
	public void addPerson(Person person) throws DuplicationException {
    	PreparedStatement stmt;
		try {
			stmt = c.prepareStatement(ADD_QUERY);
	        stmt.setString(1, person.getName());
	        stmt.setString(2, person.getPhoneNumber());
	        stmt.executeUpdate();
	        stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
	@Override
	public int getCount() {
		int result = 0;
		Statement stmt;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(GET_COUNT_QUERY);
	        result = rs.getInt(1);
	        rs.close();
	        stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Person getPerson(int index) throws BadIndexException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Person findPersonByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Person findPersonByPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void removePerson(Person person) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sortRegisterByName() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeAllBy(char firstLetter) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void load(String file) throws IOException, ValidationException, WrongFormatException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(String file) throws IOException {
		// TODO Auto-generated method stub
		
	}

    
}
