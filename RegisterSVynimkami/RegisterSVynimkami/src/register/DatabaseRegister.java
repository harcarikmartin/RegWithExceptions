package register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import register.exception.BadIndexException;
import register.exception.ValidationException;
import register.exception.WrongFormatException;

public class DatabaseRegister implements Register {
//	private static final String URL = "jdbc:derby:/Users/martinharcarik/DerbyEmbeddedDB";
//	private static final String USER = "Martin";
//	private static final String PASSWORD = "Martin";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521/XE";
	private static final String USER = "Register";
	private static final String PASSWORD = "Register";
    
	private static Connection c = null;
    
    public static final String DROP_QUERY = "DROP TABLE register"; 
    public static final String CREATE_QUERY = 
    		"CREATE TABLE register (id INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
    		+ "name VARCHAR(60) NOT NULL, phone_number VARCHAR(20) NOT NULL)";
    public static final String ADD_QUERY = "INSERT INTO register (name, phone_number) VALUES (?, ?)";
    public static final String GET_COUNT_QUERY = "SELECT COUNT(*) FROM register";
    public static final String FIND_BY_NAME_QUERY = "";
    public static final String FIND_BY_NUMBER_QUERY = "";
    
    public DatabaseRegister(boolean drop) {
    	if(drop) {
    		try {
    			c = DriverManager.getConnection(URL, USER, PASSWORD);
    			PreparedStatement stmt = c.prepareStatement(DROP_QUERY);
    			stmt.executeUpdate();
    			stmt.close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    public DatabaseRegister() {
		try {
			c = DriverManager.getConnection(URL, USER, PASSWORD);
			try {
				PreparedStatement stmt = c.prepareStatement(CREATE_QUERY);
				stmt.executeUpdate();
				stmt.close();
			}
			catch (Exception e){
				System.out.println("DB Already Exists, use it");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addPerson(Person person) {
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
		int count = 0;
		Statement stmt;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(GET_COUNT_QUERY);
	        if(rs.next()) {
			count = rs.getInt(1);
	        }
	        stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public Person getPerson(int index) throws BadIndexException {
		Person p = null;
		Statement stmt;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT name, phone_number FROM register "
					+ "ORDER BY name, phone_number OFFSET "+Integer.toString(index)+ " ROWS "
							+ "FETCH NEXT 1 ROWS ONLY");
			if(rs.next()) {
	        p = new Person(rs.getString(1), rs.getString(2));
			}
	        stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (WrongFormatException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public Person find(String name, String number) {
		Person p = null;
		Statement stmt;
		try {
			stmt = c.createStatement();
			if(name != null) {
			ResultSet rs = stmt.executeQuery("SELECT name, phone_number FROM register "
					+ "WHERE name = '"+name+"'");
			if(rs.next()) {
		        p = new Person(rs.getString(1), rs.getString(2));
				}
		        stmt.close();
			}
			else if(number != null) {
				ResultSet rs = stmt.executeQuery("SELECT name, phone_number FROM register "
						+ "WHERE phone_number = '"+number+"'");
				if(rs.next()) {
			        p = new Person(rs.getString(1), rs.getString(2));
					}
			        stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (WrongFormatException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	@Override
	public void exit() {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println("Could not close the connection ");
		}
	}
	
	@Override
	public Person findPersonByName(String name) {
		return find(name, null);
	}

	@Override
	public Person findPersonByPhoneNumber(String phoneNumber) {
		return find(null, phoneNumber);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
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

