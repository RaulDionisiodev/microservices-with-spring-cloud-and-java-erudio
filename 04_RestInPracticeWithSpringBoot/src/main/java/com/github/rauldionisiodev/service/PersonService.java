package com.github.rauldionisiodev.service;

import com.github.rauldionisiodev.exception.ResourceNotFoundException;
import com.github.rauldionisiodev.model.Person;
import com.github.rauldionisiodev.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;


    public Person findById(Long id) {
        logger.info("finding one person");

        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id")
        );
    }

    public List<Person>findAll() {
        logger.info("finding all people");
        return repository.findAll();
    }

    public Person create (Person person) {
        logger.info("creating one person");
        return repository.save(person);
    }

    public Person update (Person person) {
        logger.info("updating one person");
        Person entity = repository.findById(person.getId()).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id")
        );
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void delete (Long id) {
        logger.info("Deleting one person");
        Person entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id")
        );
        repository.delete(entity);
    }
}
