package com.makeup.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
@EnableScheduling
@SpringBootApplication
public class MakeupApplication {

	@Autowired
	ClientService clientService;


	public static void main(String[] args) {
		SpringApplication.run(MakeupApplication.class, args);
		System.out.println("hello");
	}

	@Bean
	public CommandLineRunner demo(){
		return (args) -> {

			ClientDto client1 = new ClientDto("Anna", "668529559", LocalDateTime.now().plusHours(5));
			ClientDto client2 = new ClientDto("Anna1", "668529552", LocalDateTime.now().plusHours(23));
			ClientDto client3 = new ClientDto("Anna2", "668529553", LocalDateTime.now().plusHours(30));
			ClientDto client4 = new ClientDto("Anna1", "668529554", LocalDateTime.now());



			clientService.save(client1);
			clientService.save(client2);
			clientService.save(client3);
			clientService.save(client4);


			System.out.println(clientService.getAllClients().toString());


//			ClientDto clientDto = new ClientDto("Anna", "656466", LocalDateTime.now(), client1.getUniqueCode());
//			clientService.update(clientDto);
//
//			clientService.delete(client2.getUniqueCode());


		};
	}
}
