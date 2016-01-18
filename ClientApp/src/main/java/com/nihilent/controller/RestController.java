package com.nihilent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nihilent.model.Login;
import com.nihilent.model.Order;
import com.nihilent.model.Order.Product;
import com.nihilent.model.Response;

@Controller

public class RestController {
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	Response login(@RequestBody Login login) {
		Response response = new Response();
		if(login.getUsername().equals("admin") && login.getPassword().equals("admin")){
			response.setMessage("login successful");
		}else{
			response.setMessage("login invalid");
		}
		
		return response;
	}
	
	@RequestMapping(value = "/getorder", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	Order getOrders(@RequestBody Login login) {
		System.out.println("--getOrders called");
		Order order = new Order();
		Product p = new Product(111,"Camera","12-01-2013");
		Product p1 = new Product(1121,"Mobile","14-01-2013");
		Product p2 = new Product(121,"Bat","12-06-2013");
		
		
		order.getProduct().add(p);
		order.getProduct().add(p1);
		order.getProduct().add(p2);
		return order;

	}
	@RequestMapping(value = "/login1", method = RequestMethod.POST, headers = "Accept=application/xml")
	public String login1(@RequestBody Login login) {
		
		
		return "loggedin";
	}
}
