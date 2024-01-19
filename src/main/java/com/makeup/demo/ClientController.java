package com.makeup.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("client")
@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService client;

    @GetMapping
    public List<ClientDto> getClients(){
        return client.getAllClients();
    }
}
