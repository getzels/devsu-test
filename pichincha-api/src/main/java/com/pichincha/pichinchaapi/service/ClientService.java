package com.pichincha.pichinchaapi.service;

import com.pichincha.pichinchaapi.entity.Client;
import com.pichincha.pichinchaapi.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientService extends BaseService<Client, Long> {

    private final ClientRepository clientRepository;

    @Override
    protected JpaRepository<Client, Long> repository()
    {
        return clientRepository;
    }
}

