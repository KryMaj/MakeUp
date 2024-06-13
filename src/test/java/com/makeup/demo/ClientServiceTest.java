package com.makeup.demo;

import com.makeup.demo.exception.EntityException;
import com.makeup.demo.exception.ExceptionMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private ClientDto clientDto;
    private ClientEntity clientEntity;



    @BeforeEach
    void setUp() {
        clientDto = new ClientDto("Test Name", "123456789", LocalDateTime.now().plusDays(1));
        clientEntity = ClientMapper.toEntity(clientDto);
    }
    @Test
    void shouldGiveAllClients() {

        when(clientRepository.findAll()).thenReturn(Collections.singletonList(clientEntity));

        List<ClientDto> clients = clientService.getAllClients();

        assertNotNull(clients);
        assertEquals(1, clients.size());
        assertEquals(clientDto.getName(), clients.get(0).getName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void shouldSaveClient() {
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        ClientDto savedClient = clientService.save(clientDto);

        assertNotNull(savedClient);
        assertEquals(clientDto.getName(), savedClient.getName());
        verify(clientRepository, times(1)).save(any(ClientEntity.class));

    }
    @Test
    void shouldThrowExceptionWhenSaveClient() {
        when(clientRepository.findAll()).thenReturn(Collections.singletonList(clientEntity));

        EntityException exception = assertThrows(EntityException.class, () -> clientService.save(clientDto));

        assertEquals(ExceptionMessages.ENTITY_NOT_FOUND.getMessage(), exception.getMessage());
    }


    @Test
    void shouldUpdateClient() {
        when(clientRepository.findClientByUniqueCode(clientDto.getUniqueCode())).thenReturn(clientEntity);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

        ClientDto updatedClient = clientService.update(clientDto);

        assertNotNull(updatedClient);
        assertEquals(clientDto.getName(), updatedClient.getName());
        verify(clientRepository, times(2)).findClientByUniqueCode(clientDto.getUniqueCode());
        verify(clientRepository, times(1)).save(any(ClientEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdateClient() {
        when(clientRepository.findClientByUniqueCode(clientDto.getUniqueCode())).thenReturn(null);

        EntityException exception = assertThrows(EntityException.class, () -> clientService.update(clientDto));

        assertEquals(ExceptionMessages.CLIENT_ID_NOT_FOUND.getMessage(), exception.getMessage());
    }


    @Test
    void shouldDeleteClient() {
        when(clientRepository.findClientByUniqueCode(clientDto.getUniqueCode())).thenReturn(clientEntity);

        clientService.delete(clientDto.getUniqueCode());

        verify(clientRepository, times(1)).deleteClientByUniqueCode(clientDto.getUniqueCode());
    }
    @Test
    void shouldThrowExceptionWhenDeleteClient() {
        when(clientRepository.findClientByUniqueCode(clientDto.getUniqueCode())).thenReturn(null);

        EntityException exception = assertThrows(EntityException.class, () -> clientService.delete(clientDto.getUniqueCode()));

        assertEquals(ExceptionMessages.CLIENT_ID_NOT_FOUND.getMessage(), exception.getMessage());
    }
}