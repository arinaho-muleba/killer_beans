package com.killerbeans.server;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

/*	@GetMapping
	public String greet( HttpServletRequest request){
		String token = request.getHeader("cookie");
		return  "Welcome to killer beans.\n Copy Token to CLI, Token : "+token;
	}
*/

}
