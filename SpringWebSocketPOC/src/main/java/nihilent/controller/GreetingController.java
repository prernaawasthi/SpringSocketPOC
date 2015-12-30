package nihilent.controller;

import nihilent.pojo.Greeting;
import nihilent.pojo.Message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) throws Exception {
        Thread.sleep(3000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}