package com.pichincha.pichinchaapi.controller;

import com.pichincha.pichinchaapi.dto.ClientDTO;
import com.pichincha.pichinchaapi.entity.Client;
import com.pichincha.pichinchaapi.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/client")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.findAll().stream()
                                               .map(this::convertToDTO)
                                               .collect(Collectors.toList());
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return clientService.findById(id)
                            .map(this::convertToDTO)
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        Client client = convertToEntity(clientDTO);
        Client createdClient = clientService.save(client);
        return ResponseEntity.ok(convertToDTO(createdClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        return clientService.findById(id)
                            .map(client -> {
                                client.setId(clientDTO.getId());
                                client.setName(clientDTO.getName());
                                client.setGender(clientDTO.getGender());
                                client.setAge(clientDTO.getAge());
                                client.setClientId(clientDTO.getClientId());
                                client.setActive(clientDTO.getActive());
                                client.setIdentification(clientDTO.getIdentification());
                                return convertToDTO(clientService.update(client));
                            })
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        return clientService.findById(id)
                            .map(client -> {
                                clientService.deleteById(id);
                                return ResponseEntity.ok().build();
                            })
                            .orElse(ResponseEntity.notFound().build());
    }

    private ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setGender(client.getGender());
        clientDTO.setAge(client.getAge());
        clientDTO.setClientId(client.getClientId());
        clientDTO.setActive(client.getActive());
        clientDTO.setIdentification(client.getIdentification());
        return clientDTO;
    }

    private Client convertToEntity(ClientDTO clientDTO) {
        return Client.builder()
        .id(clientDTO.getId())
        .name(clientDTO.getName())
        .gender(clientDTO.getGender())
        .age(clientDTO.getAge())
        .clientId(clientDTO.getClientId())
        .identification(clientDTO.getIdentification())
        .active(clientDTO.getActive()).build();
    }

}
