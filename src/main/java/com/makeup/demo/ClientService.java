package com.makeup.demo;

import com.makeup.demo.exception.EntityException;
import com.makeup.demo.exception.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

            sendSmsAboutVisit(clientToSave);
            System.out.println(sendSmsAboutVisit(clientToSave));
            return ClientMapper.toDto(clientRepository.save(ClientMapper.toEntity(clientToSave)));
        }
        throw new EntityException(ExceptionMessages.ENTITY_NOT_FOUND.getMessage());


    }
    public ClientDto update (ClientDto clientDto){

        if (clientRepository.findClientByUniqueCode(clientDto.getUniqueCode())!=null){


            ClientEntity client = clientRepository.findClientByUniqueCode(clientDto.getUniqueCode());
            client.setName(clientDto.getName());
            client.setPhoneNumber(clientDto.getPhoneNumber());
            client.setSelectedDate(clientDto.getSelectedDate());
            clientRepository.save(client);

        return ClientMapper.toDto(client);
        }

        throw new EntityException(ExceptionMessages.CLIENT_ID_NOT_FOUND.getMessage());
    }

    public void delete(String code){
        if (clientRepository.findClientByUniqueCode(code)!=null){
            clientRepository.deleteClientByUniqueCode(code);
        } else {
            throw new EntityException(ExceptionMessages.CLIENT_ID_NOT_FOUND.getMessage());
        }


    }

    private String sendSmsAboutVisit(ClientDto clientDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Twoja wizyta została umowiona na " + clientDto.getSelectedDate().format(formatter) + " Twój numer klienta " + clientDto.getUniqueCode()+ " zachowaj go w celu zmiany danych";
    }

    private String remindAboutVisit(ClientDto clientDto){

        return "Przypominam o jutrzejszej wizycie, jeżeli nie możesz przyjsc, poinformuj o tym, podając swój indywidualny kod: "+ clientDto.getUniqueCode();
    }

    private boolean checkIfTheDateIsAvailable(ClientDto clientDto){

       return getAllClients().stream()
                .anyMatch(c->c.getSelectedDate().equals(clientDto.getSelectedDate()));
    }

    @Scheduled(cron = "0 */1 * * * *") // Uruchamia co 24 godziny (przykładowy okres)
    public void checkClientsDates() {
        List<ClientEntity> clients = new ArrayList<>(clientRepository.findAll());


        for (ClientEntity client : clients) {
            if (checkIfTheAppointmentIsTheNextDay(client.getSelectedDate())) {
                System.out.println(remindAboutVisit(ClientMapper.toDto(client)));
            }
        }
    }


    private List<LocalDateTime> getDates(){
        return clientRepository.findAll().stream()
                .map(ClientEntity::getSelectedDate)
                .collect(Collectors.toList());
    }

    private boolean checkIfTheAppointmentIsTheNextDay(LocalDateTime termin) {
        LocalDateTime localDateTime = LocalDateTime.now();

        if(localDateTime.getDayOfYear() == termin.minusDays(1).getDayOfYear() &&
                localDateTime.getYear() == termin.minusDays(1).getYear()){
            return  true;
        }
        return false;
    }

    private boolean checkIfTheAppointmentDateHasAlreadyPassed(LocalDateTime termin) {
        LocalDateTime localDateTime = LocalDateTime.now();

        if(localDateTime.getDayOfYear() == termin.plusDays(1).getDayOfYear() &&
                localDateTime.getYear() == termin.plusDays(1).getYear()){
            return  true;
        }
        return false;
    }


    @Scheduled(cron = "0 */1 * * * *") // Uruchamia co 24 godziny (przykładowy okres)
    public void removeClientsWhoseDeadlineHasPassed() {

        List<ClientEntity> clients = new ArrayList<>(clientRepository.findAll());



        for (ClientEntity client : clients) {
            if (checkIfTheAppointmentDateHasAlreadyPassed(client.getSelectedDate())) {
                System.out.println("Usunałem klienta: " + client.getUniqueCode());
                delete(client.getUniqueCode());
            }
        }



    }


}
