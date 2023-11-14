package com.pichincha.pichinchaapi.client;

import com.pichincha.pichinchaapi.entity.Client;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.pichincha.pichinchaapi.repository.ClientRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
public class ClientRepositoryTest {

    @Mock
    private ClientRepository clientRepository;

    @Test
    public void testCreateClient() {
        Client client = new Client();
        client.setId(1L);
        client.setClientId("client123");
        client.setName("Test Client");
        client.setActive(Boolean.TRUE);
        client.setIdentification("9999999999999");

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientRepository.save(client);

        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getName()).isEqualTo(client.getName());
    }

    @Test
    public void testFindClientById() {
        Long clientId = 1L;
        Client client = new Client();
        client.setClientId("client123");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Optional<Client> found = clientRepository.findById(clientId);

        assertTrue(found.isPresent());
        assertEquals(client.getClientId(), found.get().getClientId());
        verify(clientRepository).findById(clientId);
    }
}
