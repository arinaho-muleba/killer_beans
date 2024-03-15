package com.killerbean.shell;

import com.killerbean.shell.Helpers.Requests;
import com.killerbean.shell.commands.AccessCommands;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

@SpringBootApplication
@RestController
public class Application {
	@GetMapping(path="/")
	public String getUserT(@AuthenticationPrincipal OAuth2User oauth2User, HttpServletRequest request){

		String username = (String) oauth2User.getAttribute("login");
		System.out.println("UserName : "+username);
		//String accessToken = request.changeSessionId();
		String token = request.getHeader("cookie");
		String type= request.getAuthType();
		AccessCommands.connected = true;

		Requests.SESSION_TOKEN = request.getHeader("cookie");


		return " Access Token :"+Requests.SESSION_TOKEN +"UserName : "+username+" Tyepe ";
	}
	@GetMapping(path="/test")
	public String getUser( HttpServletRequest request) throws URISyntaxException {


		String accessToken = request.changeSessionId();
		String token = request.getHeader("cookie");
		String type= request.getAuthType();
		AccessCommands.connected = true;
		//return " Access Token :"+token;


		return "\n";
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
