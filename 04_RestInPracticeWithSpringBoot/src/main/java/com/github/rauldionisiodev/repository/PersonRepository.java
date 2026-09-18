package com.github.rauldionisiodev.repository;

import com.github.rauldionisiodev.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
