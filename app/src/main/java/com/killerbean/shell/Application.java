package com.killerbean.shell;

import com.killerbean.shell.Helpers.Requests;
import com.killerbean.shell.commands.AccessCommands;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class Application {
	@GetMapping(path="/")
	public String getUserT(@AuthenticationPrincipal OAuth2User oauth2User/*@RequestBody String requestBody*/){

		String username = (String) oauth2User.getAttribute("login");
		String email = (String) oauth2User.getAttribute("email");
		// You can access other user attributes as needed
		AccessCommands.connected = true;
		return " Body :"+oauth2User+"UserName : "+username;
	}


	public static void main(String[] args) {

		System.out.println(
				"-----------------------------------------" +
				"\nWelcome to Killer Beans  \n" +
				"-----------------------------------------" +
				"\nType help to see available commands." +
				"\nNote: you must be signed in.\n" +
				"-----------------------------------------\n"
		);

		SpringApplication.run(Application.class, args);
	}

}
