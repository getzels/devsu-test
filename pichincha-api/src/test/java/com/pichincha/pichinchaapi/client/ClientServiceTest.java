package com.pichincha.pichinchaapi.client;

import com.pichincha.pichinchaapi.entity.Client;
import com.pichincha.pichinchaapi.repository.ClientRepository;
import com.pichincha.pichinchaapi.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void testAddClient() {
        Client client = new Client();
        client.setClientId("client123");
        client.setName("Test Client");
        client.setActive(Boolean.TRUE);
        client.setIdentification("9999999999999");

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client created = clientService.save(client);

        assertNotNull(created);
        assertEquals("client123", created.getClientId());
    }

    @Test
    public void testGetClientById() {
        Long clientId = 1L;
        Client client = new Client();
        client.setClientId("client123");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Client found = clientService.findById(clientId).get();

        assertNotNull(found);
        assertEquals(client.getClientId(), found.getClientId());
    }

    @Test
    public void testUpdateClient() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setClientId("client123");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        client.setPassword("newPassword");
        Client updatedClient = clientService.update(client);

        assertNotNull(updatedClient);
        assertEquals("newPassword", updatedClient.getPassword());
    }

    @Test
    public void testDeleteClient() {
        Long clientId = 1L;

        Client client = new Client();

        doNothing().when(clientRepository).delete(client);

        clientService.delete(client);
        verify(clientRepository).delete(client);
    }

}

