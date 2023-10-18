package com.example.server;

import com.example.server.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ServerApplication.class, args);

		ServerMain.ServerStarting(args);

	}

}
