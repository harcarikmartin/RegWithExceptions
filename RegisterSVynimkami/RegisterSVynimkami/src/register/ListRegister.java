package register;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import register.exception.BadIndexException;
import register.exception.DuplicationException;
import register.exception.ValidationException;
import register.exception.WrongFormatException;

public class ListRegister implements Register, Serializable {

	private List<Person> persons;

	public ListRegister(int capacity) {
		persons = new ArrayList<>(capacity);
	}

	@Override
	public int getCount() {
		return persons.size();
	}

	@Override
	public int getSize() {
		return persons.size();
	}

	@Override
	public Person getPerson(int index) throws BadIndexException {
		try {
			return persons.get(index);
		} catch (IndexOutOfBoundsException e) {
			throw new BadIndexException();
		}
	}

	@Override
	public void addPerson(Person person) throws DuplicationException {
		if(persons.contains(person))
			throw new DuplicationException("User already exists (with same name or phone number)");
		
		persons.add(person);
		sortRegisterByName();
	}

	@Override
	public Person findPersonByName(String name) {
		name = name.toLowerCase();
		
		//--------traverse using Iterator-------
		Iterator<Person> i = persons.iterator(); //ziskam objekt iteratora
		while(i.hasNext()) { //zisti ci existuje nasledujuca polozka
			Person p = i.next(); //vrati nasledujucu polozku
			if (p.getName().toLowerCase().contains(name)) {
				return p;		
			}
			//removal example
//			i.remove(); //safe element removal in a LOOP
		}
		
		//--------traverse using the "enhanced for" loop--------
		for(Person p : persons) {
			if (p.getName().toLowerCase().contains(name)) {
				return p;
			}
			//removal example
//			persons.remove(p); //unsafe if used in a LOOP, throws ConcurrentModificationException
		}
		
		return null;
	}

	@Override
	public Person findPersonByPhoneNumber(String phoneNumber) {
		for (Person p : persons) {
			if (p.getPhoneNumber().equals(phoneNumber))
				return p;			
		}
		return null;
	}

	@Override
	public void removePerson(Person person) {
		persons.remove(person);
	}

	@Override
	public void sortRegisterByName() {
		Collections.sort(persons);
	}

	@Override
	public void removeAllBy(char firstLetter) {
		
		for(Person p : persons) {
			if(p.getName().charAt(0) == firstLetter) {
				System.out.println("Mazem " + p);
			}
		}
		
		for (int i = persons.size() - 1; i > -1; i--) {
			if(persons.get(i).getName().charAt(0) == firstLetter) {
				removePerson(persons.get(i));
			}
		}	
	}

	@Override
	public void save(String file) throws IOException {
		FileWriter fw = new FileWriter(file);
		for (Person person : persons) {
			fw.write(person.getName() + ", " + person.getPhoneNumber() + "\n");
		}
		fw.close();
	}

	@Override
	public void load(String file) throws IOException, ValidationException, WrongFormatException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		
		
		while((line = br.readLine()) != null) {
			StringBuilder name = new StringBuilder();
			StringBuilder number = new StringBuilder();
			
			for (int i = 0; i < line.indexOf(','); i++) {
				name.append(line.charAt(i));
			}
			for (int i = (line.indexOf(',') + 2); i < line.length(); i++) {
				number.append(line.charAt(i));
			}
			Person person = new Person(name.toString(), number.toString());
			persons.add(person);
		}
		br.close();
	}

}
