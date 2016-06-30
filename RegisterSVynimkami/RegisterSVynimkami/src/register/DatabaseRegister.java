package register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    
    public static final String DROP_QUERY = "DROP TABLE register"; 
    public static final String CREATE_QUERY = 
    		"CREATE TABLE register (id INT PRIMARY KEY, name VARCHAR2(60) NOT NULL, phone_number VARCHAR2(20) NOT NULL)";
    public static final String SELECT_CONTACTS_QUERY = "SELECT name, phone_number FROM register";
    public static final String SELECT_CONTACTS_QUERY_BY_NAME = "SELECT name, phone_number FROM register where name = ?";
    public static final String SELECT_CONTACTS_QUERY_BY_PHONE = "SELECT name, phone_number FROM register where phone_number = ?";
    public static final String ADD_QUERY = "INSERT INTO register (id, name, phone_number) VALUES (ids.nextval, ?, ?)";
    public static final String GET_COUNT_QUERY = "SELECT COUNT(*) FROM register";
    
    public DatabaseRegister(){
		try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD)) {
			System.out.println("DB connected");
		}
		catch (Exception e){
			throw new RegisterException("Error: creating table", e);
		}
	}
	
	@Override
	public void addPerson(Person person){
		try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = c.prepareStatement(ADD_QUERY)) {
				stmt.setString(1, person.getName());
				stmt.setString(2, person.getPhoneNumber());
				stmt.executeUpdate();
		}
		catch (Exception e){
			throw new RegisterException("Error: adding person", e);
		}
	}
	
	@Override
	public int getCount() {
		try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = c.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(GET_COUNT_QUERY)) {
				if(rs.next()) {
					return rs.getInt(1);
				}
				else {
					return 0;
				}
			}
		}
		catch (Exception e){
			throw new RegisterException("Error: get count", e);
		}
	}
	
	public List<Person> getPersons() {
		List<Person> persons = new ArrayList<>();
		try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = c.createStatement()) {
				ResultSet rs = stmt.executeQuery(SELECT_CONTACTS_QUERY);
				while(rs.next()) {
					persons.add(new Person(rs.getString(1), rs.getString(2)));
				}
				return persons;
		}
		catch (Exception e){
			throw new RegisterException("Error: get persons", e);
		}
	}
	
	@Override
	public Person getPerson(int index) throws BadIndexException {
		return getPersons().get(index);
	}
	
	private Person find(String parameter, String query) {
		try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = c.prepareStatement(query)) {
				stmt.setString(1, parameter);
				try (ResultSet rs = stmt.executeQuery()) {
					if(rs.next()) {
						return new Person(rs.getString(1), rs.getString(2));
					}
					else {
						return null;
					}
				}
		}
		catch (Exception e){
			throw new RegisterException("Error: select person by one parameter", e);
		}
	}
	
	@Override
	public Person findPersonByName(String name) {
		return find(name, SELECT_CONTACTS_QUERY_BY_NAME);
	}

	@Override
	public Person findPersonByPhoneNumber(String phoneNumber) {
		return find(phoneNumber, SELECT_CONTACTS_QUERY_BY_PHONE);
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

