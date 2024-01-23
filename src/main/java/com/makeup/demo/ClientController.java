package com.makeup.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("client")
@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public List<ClientDto> getClients(){
        return clientService.getAllClients();
    }

    @PostMapping
    public ClientDto save(@RequestBody ClientDto clientDto) {
        return clientService.save(clientDto);
    }

    @PutMapping
    public ClientDto update(@RequestBody ClientDto clientDto) {
        return clientService.update(clientDto);
    }

    @DeleteMapping("/delete/{code}")
    public void deleteClient(@PathVariable String code) {
        clientService.delete(code);
    }

}
