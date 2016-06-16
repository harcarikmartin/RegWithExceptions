package register;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by jaro on 3.2.2014.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Register register = new ListRegister(20);

        register.addPerson(new Person("Janko Hrasko", "0900123456"));

        ConsoleUI ui = new ConsoleUI(register);
        
        ui.run();
    	
//    	ArrayList<String> al = new ArrayList<>();
//    	LinkedList<String> ll = new LinkedList<>();
//    	
//    	long cas = System.currentTimeMillis();
//    	for (int i = 0; i < 100000; i++) {
//			al.add(0,"A");
//		}
//    	long time = System.currentTimeMillis() - cas;
//    	System.out.println(time);
//    	
//    	cas = System.currentTimeMillis();
//    	for (int i = 0; i < 100000; i++) {
//			ll.add(0,"A");
//		}
//    	time = System.currentTimeMillis() - cas;
//    	System.out.println(time);
    	
    	
    	
    }
}
