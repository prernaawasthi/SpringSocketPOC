package api.chat.nihilent.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import api.chat.nihilent.dto.ChatMessage;
import api.chat.nihilent.model.ChatMessageModel;
import api.chat.nihilent.model.Message;
import api.chat.nihilent.model.Registration;
import api.chat.nihilent.repository.ChatMessageRepository;
import api.chat.nihilent.repository.RegistrationRepository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class ChatMessageController {

	@Autowired
	private ChatMessageRepository chatMessageRepository;


	@Autowired
	private RegistrationRepository registrationRepository;


	@RequestMapping("/chat")
	public String chat() {
		return "chat";
	}

	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	@MessageMapping("/newMessage")
	@SendTo("/topic/newMessage")
	public ChatMessage save(ChatMessageModel chatMessageModel) {
		System.out.println("Inside save method *********");
		ChatMessageModel chatMessage = new ChatMessageModel(chatMessageModel.getText(), chatMessageModel.getAuthor(), new Date());
		ChatMessageModel message = chatMessageRepository.save(chatMessage);
		List<ChatMessageModel> chatMessageModelList = chatMessageRepository.findAll(new PageRequest(0, 5, Sort.Direction.DESC, "createDate")).getContent();
		return new ChatMessage(chatMessageModelList.toString());
	}

	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ChatMessage list(HttpServletResponse  response) {
		System.out.println("Inside list method *********");
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<ChatMessageModel> chatMessageModelList = chatMessageRepository.findAll(new PageRequest(0, 5, Sort.Direction.DESC, "createDate")).getContent();
		return new ChatMessage(chatMessageModelList.toString());
	}


	@RequestMapping(value="/register",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Boolean> register(@RequestBody(required = false) String loginIdJson,HttpServletResponse  response) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("Inside register method *********"+loginIdJson);
		response.setHeader("Access-Control-Allow-Origin", "*");
		ObjectMapper mapper = new ObjectMapper();
		Message name = mapper.readValue(loginIdJson, Message.class);
		if(name.getName()==null || "".equals(name.getName())) return new ResponseEntity(false, HttpStatus.OK);
		Registration reg=new Registration();
		reg.setFirstName(name.getName());
		reg.setUserName(name.getName());
		registrationRepository.save(reg);		
		return new ResponseEntity(true, HttpStatus.OK);

	}

}