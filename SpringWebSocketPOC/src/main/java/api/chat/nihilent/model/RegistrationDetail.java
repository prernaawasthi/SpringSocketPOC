package api.chat.nihilent.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationDetail {

	static List<Registration> registrationList = new  ArrayList<Registration>() ;
	
	public static void addRegistration(Registration reg){
		
		registrationList.add(reg);
	}
	
	public static List<Registration>  getRegistration(){
		return registrationList;
	}
}
