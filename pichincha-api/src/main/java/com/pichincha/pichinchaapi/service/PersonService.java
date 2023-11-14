package com.pichincha.pichinchaapi.service;

import com.pichincha.pichinchaapi.entity.Person;
import com.pichincha.pichinchaapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService extends BaseService<Person, Long> {

    private final PersonRepository personRepository;

    @Override
    protected JpaRepository<Person, Long> repository() {
        return personRepository;
    }
}
