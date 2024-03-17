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

	public static void main(String[] args) {
		String welcomeToKillerBeans ="\n" +"\u001B[34m"+
				"  _  __  ___   _       _       _____   ____      ____    _____      _      _   _   ____  \n" +
				" | |/ / |_ _| | |     | |     | ____| |  _ \\    | __ )  | ____|    / \\    | \\ | | / ___| \n" +
				" | ' /   | |  | |     | |     |  _|   | |_) |   |  _ \\  |  _|     / _ \\   |  \\| | \\___ \\ \n" +
				" | . \\   | |  | |___  | |___  | |___  |  _ <    | |_) | | |___   / ___ \\  | |\\  |  ___) |\n" +
				" |_|\\_\\ |___| |_____| |_____| |_____| |_| \\_\\   |____/  |_____| /_/   \\_\\ |_| \\_| |____/ \n" +
				"                                                                                         \u001B[0m";
		System.out.println(welcomeToKillerBeans+"\n");

		System.out.println(
				"------------------------------------------------" +
		"\u001B[38;5;208m" + "\nWelcome to Killer Beans  \n" + "\u001B[0m"+

				"------------------------------------------------" +
				"\nType \u001B[32m help\u001B[0m to see available commands." +
				"\nType \u001B[34m sign-in \u001B[0m to get access to all commands\n" +
				"------------------------------------------------\n"
		);

		SpringApplication.run(Application.class, args);
	}

}
