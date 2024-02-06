package com.makeup.demo;

import com.makeup.demo.exception.EntityException;
import com.makeup.demo.exception.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

        if (!checkIfTheDateIsAvailable(clientDto) || getAllClients().isEmpty()) {
            ClientDto clientToSave;
            if (clientDto.getUniqueCode() == null) {
                clientToSave = new ClientDto(clientDto.getName(), clientDto.getPhoneNumber(), clientDto.getSelectedDate());
            } else {
                clientToSave = clientDto;
            }

            sendSms(clientToSave);
            return ClientMapper.toDto(clientRepository.save(ClientMapper.toEntity(clientToSave)));
        }
        throw new EntityException(ExceptionMessages.ENTITY_NOT_FOUND.getMessage());


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

    private boolean checkIfTheDateIsAvailable(ClientDto clientDto){

       return getAllClients().stream()
                .anyMatch(c->c.getSelectedDate().equals(clientDto.getSelectedDate()));
    }

    @Scheduled(cron = "0 */1 * * * *") // Uruchamia co 24 godziny (przykładowy okres)
    public void sprawdzTerminyKlientow() {
        List<LocalDateTime> klienci = getDates();

        // Iteruj przez wszystkich klientów i sprawdź terminy
        for (LocalDateTime klient : klienci) {
            if (czyTerminNastepnegoDnia(klient)) {
                // Tutaj dodaj logikę lub akcje, które mają być wykonane, jeśli termin jest następnego dnia
                // Na przykład, wysyłanie powiadomienia, zmiana statusu, itp.
                System.out.println("Termin klienta "+ " jest jutro!");
            }
        }
    }


    private List<LocalDateTime> getDates(){
        return clientRepository.findAll().stream()
                .map(ClientEntity::getSelectedDate)
                .collect(Collectors.toList());
    }

    private boolean czyTerminNastepnegoDnia(LocalDateTime termin) {


        LocalDateTime localDateTime = LocalDateTime.now();

        if(localDateTime.getDayOfYear() == termin.minusDays(1).getDayOfYear() &&
                localDateTime.getYear() == termin.minusDays(1).getYear()){
            return  true;
        }
        return false;
    }



}
