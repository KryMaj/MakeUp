package com.makeup.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class MakeupApplication {

	@Autowired
	ClientService clientService;


	public static void main(String[] args) {
		SpringApplication.run(MakeupApplication.class, args);
		System.out.println("hello");
	}


	public CommandLineRunner demo(){
		return (args) -> {

			ClientDto client1 = new ClientDto("Anna", "668529559", LocalDateTime.now());
			ClientDto client2 = new ClientDto("Anna1", "668529552", LocalDateTime.now());
			ClientDto client3 = new ClientDto("Anna2", "668529553", LocalDateTime.now());
			ClientDto client4 = new ClientDto("Anna1", "668529554", LocalDateTime.now());


			clientService.save(client1);
			clientService.save(client2);
			clientService.save(client3);
			clientService.save(client4);

			System.out.println(clientService.getAllClients());
		};
	}
}
