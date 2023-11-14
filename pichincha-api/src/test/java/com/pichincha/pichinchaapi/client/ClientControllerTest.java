package com.pichincha.pichinchaapi.client;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pichincha.pichinchaapi.controller.ClientController;
import com.pichincha.pichinchaapi.entity.Client;
import com.pichincha.pichinchaapi.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void testCreateClient() throws Exception {
        Client client = new Client();
        client.setClientId("client123");

        when(clientService.save(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/api/client")
                        .contentType("application/json")
                        .content("{\"clientId\":\"client123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetClient() throws Exception {
        Long clientId = 1L;
        Client client = new Client();
        client.setClientId("client123");

        when(clientService.findById(clientId)).thenReturn(Optional.of(client));

        mockMvc.perform(get("/api/client/" + clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value("client123"));
    }

    @Test
    public void testUpdateClient() throws Exception {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setClientId("client123");
        client.setPassword("newPassword");

        when(clientService.findById(clientId)).thenReturn(Optional.of(client));
        when(clientService.update(any(Client.class))).thenReturn(client);

        mockMvc.perform(put("/api/client/" + clientId)
                        .contentType("application/json")
                        .content("""
                                {
                                        "id": 1,
                                        "name": "Getzels De Los Santos ",
                                        "gender": "MALE",
                                        "identification": "9999995558888",
                                        "age": 35,
                                        "clientId": "C124",
                                        "active": true
                                }
                                """))
                .andExpect(status().isOk());
    }
}

