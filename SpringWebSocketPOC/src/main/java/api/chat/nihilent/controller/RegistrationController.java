package api.chat.nihilent.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.chat.nihilent.model.Login;
import api.chat.nihilent.model.Registration;
import api.chat.nihilent.model.RegistrationDetail;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class RegistrationController {

	@RequestMapping(value="/api/registration",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    public ResponseEntity<List<Registration>> registration(@RequestBody(required = false) String registrationJson,HttpServletResponse  response) throws JsonParseException, JsonMappingException, IOException {
		//Request format {"firstname" : "chan1","lastname" : "aw","username" :"chan.aw" }
		response.setHeader("Access-Control-Allow-Origin", "*");
		System.out.println("registrationJson : " + registrationJson);
		Registration registration= new Registration();
		ObjectMapper mapper = new ObjectMapper();
		registration = mapper.readValue(registrationJson, Registration.class);
	    
        Registration registration1 = new Registration();
		registration1.setFirstName(registration.getFirstName());
		registration1.setLastName(registration.getLastName());
		registration1.setUserName(registration.getFirstName()+"."+registration.getLastName());
		RegistrationDetail.addRegistration(registration1);
			
		return new ResponseEntity(RegistrationDetail.getRegistration(),
                            HttpStatus.OK);
    }
	
	
	@RequestMapping(value="/api/login",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    public ResponseEntity<List<Registration>> login(@RequestBody(required = false) String loginIdJson,HttpServletResponse  response) throws JsonParseException, JsonMappingException, IOException {
	
		response.setHeader("Access-Control-Allow-Origin", "*");
		Login userName = new Login();
		ObjectMapper mapper = new ObjectMapper();
		userName = mapper.readValue(loginIdJson, Login.class);
		List<Registration> registrationList = RegistrationDetail.getRegistration();
		Iterator<Registration> itr=registrationList.iterator();
		while(itr.hasNext()){
			if(itr.next().getUserName().equals(userName.getUserName())){
				
				return new ResponseEntity(RegistrationDetail.getRegistration(),
		                HttpStatus.OK);
		    }
		}
		
		
		return new ResponseEntity("Not Found",
                HttpStatus.OK);
		
	}
}
