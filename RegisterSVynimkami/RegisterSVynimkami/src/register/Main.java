package register;

/**
 * Created by jaro on 3.2.2014.
 */
public class Main {
	
    public static void main(String[] args) throws Exception {
        Register register = new DatabaseRegister();

//        register.addPerson(new Person("Janko Hrasko", "0900123456"));
//        register.addPerson(new Person("Danko Hrasko", "09123456"));
//        register.addPerson(new Person("Kajko Hrasko", "09001256"));
//        register.addPerson(new Person("Lukas Hrasko", "0900126"));
//        register.addPerson(new Person("Dezko Hrasko", "000123456"));
//        
//        register.save("register.bin");
//        try {
//			register.load("register.bin");
//			System.out.println("Register loaded from file register.bin");
//		} catch (Exception e) {
//			System.out.println("Failed to load register");
//		}
        
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
