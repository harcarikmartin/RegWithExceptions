package register;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import register.exception.*;

/**
 * User interface of the application.
 */
public class ConsoleUI {
	/** register.Register of persons. */
	private Register register;

	/**
	 * In JDK 6 use Console class instead.
	 * 
	 * @see readLine()
	 */
	private BufferedReader input = new BufferedReader(new InputStreamReader(
			System.in));

	/**
	 * Menu options.
	 */
	private enum Option {
		PRINT, ADD, UPDATE, REMOVE, REMOVE_BY_FIRST_LETTER, FIND, GET_AMOUNT_OF_RECORDS,EXIT
	};

	/**
	 * Menu options for finding.
	 * 
	 * @author P3502404
	 *
	 */
	private enum FindOption {
		FIND_BY_NAME, FIND_BY_PHONE_NUMBER, BACK
	}

	public ConsoleUI(Register register) {
		this.register = register;
	}

	public void run() {
		while (true) {
			switch (showMenu()) {
			case PRINT:
				printRegister();
				break;
			case ADD:
				addToRegister();
				break;
			case UPDATE:
				updateRegister();
				break;
			case REMOVE:
				removeFromRegister();
				break;
			case FIND:
				findInRegister();
				break;
			case REMOVE_BY_FIRST_LETTER:
				removeByFirstLetter();
				break;
			case GET_AMOUNT_OF_RECORDS:
				getCount();
				break;
			case EXIT:
				return;
			
			}
		}
	}
	
	private void getCount() {
		if(register.getCount() == 1) {
			System.out.println("Database has " + register.getCount() + " record");
		}
		else {
		System.out.println("Database has " + register.getCount() + " records");
		}
	}

	private void removeByFirstLetter() {
		System.out.println("Enter first character: ");
		String letter = readLine();
		char[] characterArray = new char[1];
		char character;
		letter.getChars(0, 1, characterArray, 0);
		character = characterArray[0];
		
		register.removeAllBy(character);
		
	}

	/**
	 * Reads line from console.
	 * 
	 * @return the string from console
	 */
	private String readLine() {
		// In JDK 6.0 and above Console class can be used
		// return System.console().readLine();

		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	private Option showMenu() {
		System.out.println("Menu.");
		for (Option option : Option.values()) {
			System.out.printf("%d. %s%n", option.ordinal() + 1, option);
		}
		System.out.println("-----------------------------------------------");

		int selection = -1;
		do {
			System.out.println("Option: ");
			selection = Integer.parseInt(readLine());
		} while (selection <= 0 || selection > Option.values().length);

		return Option.values()[selection - 1];
	}

	/**
	 * Displays menu and wait for correct entry.
	 * 
	 * @return
	 */
	private FindOption showFindMenu() {
		System.out.println("Find Options.");
		for (FindOption option : FindOption.values()) {
			System.out.printf("%d. %s%n", option.ordinal() + 1, option);
		}
		System.out.println("-----------------------------------------------");

		int selection = -1;
		do {
			System.out.println("Option: ");
			selection = Integer.parseInt(readLine());
		} while (selection <= 0 || selection > FindOption.values().length);

		return FindOption.values()[selection - 1];
	}

	/**
	 * Prints persons in register.
	 */
	private void printRegister() {

		int registerLength = register.getCount();

		if (registerLength > 0) 
			System.out.println("Index Name (Phone Number)");
		else
			System.out.println("Database is empty");
		System.out.println("-----------------------------------------------");

		int i = 0;
		while (i < registerLength) {

			try {
				System.out.printf("%4d. %s%n", i + 1, register.getPerson(i));
				i++;
			} catch (BadIndexException e) {
				//nerob nic, toto je nasa chyba
			}
		}

	}

	/**
	 * Adds person to register.
	 */
	private void addToRegister() {
		System.out.println("Enter Name: ");
		String name = readLine();
		System.out.println("Enter Phone Number: ");
		String phoneNumber = readLine();

		try {
			register.addPerson(new Person(name, phoneNumber));
		} catch (DuplicationException | ValidationException
				| WrongFormatException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Edits person in register.
	 */
	private void updateRegister() {
		System.out.println("Enter index: ");
		int index = Integer.parseInt(readLine());
		Person person = null;
		try {
			person = register.getPerson(index - 1);
		} catch (BadIndexException ex) {
			System.err.println(ex.getMessage());
			return;
		}

		System.out.println("Enter Name: ");
		String name = readLine();

		System.out.println("Enter Phone Number: ");
		String phoneNumber = readLine();

		boolean nastavenaOsoba = false;
		try {
			person.setName(name);
			person.setPhoneNumber(phoneNumber);
			nastavenaOsoba = true;
		} catch (WrongFormatException | ValidationException ex) {
			System.err.println(ex);
		} finally {
			if(nastavenaOsoba) {
				System.out.println("Nastavil som");
			} else {
				System.out.println("Nenastavil som");
			}
		}
	}

	/**
	 * Function handling menu a finding type.
	 */
	private void findInRegister() {

		Person person = null;

		switch (showFindMenu()) {
		case FIND_BY_NAME:
			System.out.println("Enter Name: ");
			String name = readLine();
			if (name.length() > 0)
				person = register.findPersonByName(name);
			break;

		case FIND_BY_PHONE_NUMBER:
			System.out.println("Enter Phone Number: ");
			String phoneNumber = readLine();
			if (phoneNumber.length() > 0)
				person = register.findPersonByPhoneNumber(phoneNumber);
			break;

		case BACK:
		}

		if (person != null)
			System.out.println(person);
	}

	/**
	 * Removes person from register.
	 */
	private void removeFromRegister() {
		System.out.println("Enter index: ");
		try {
			int index = Integer.parseInt(readLine());
			Person person = register.getPerson(index - 1);
			register.removePerson(person);
		} catch (NumberFormatException ex) {
			System.err.println("Musis zadat len cisla.");
		} catch (BadIndexException e) {
			System.err.println("Nemozem odstranit osobu: " + e.getMessage());
		}
	}

}
