package com.makeup.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {


    private final ClientRepository clientRepository;

    public List<ClientDto> getAllClients(){
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper::toDto)
                .collect(Collectors.toList());
    }

    public ClientDto save (ClientDto clientDto){

        sendSms(clientDto);
        return ClientMapper.toDto(clientRepository.save(ClientMapper.toEntity(clientDto)));
    }
    public ClientDto update (ClientDto clientDto){
            ClientEntity client = clientRepository.findClientByUniqueCode(clientDto.getUniqueCode());
            client.setName(clientDto.getName());
            client.setPhoneNumber(clientDto.getPhoneNumber());
            client.setSelectedDate(clientDto.getSelectedDate());
            clientRepository.save(client);

        return ClientMapper.toDto(client);
    }

    public void delete(String code){
        clientRepository.deleteClientByUniqueCode(code);

    }

    private String sendSms(ClientDto clientDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Twoja wizyta została umowiona na " + clientDto.getSelectedDate().format(formatter) + " Twój numer klienta " + clientDto.getUniqueCode()+ " zachowaj go w celu zmiany danych";
    }


}
