package com.github.marceloasfilho.springbatchapp;

import com.github.marceloasfilho.springbatchapp.entity.Person;
import com.github.marceloasfilho.springbatchapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchAppApplication implements CommandLineRunner {

    @Autowired
    PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchAppApplication.class, args);
    }

    @Override
    public void run(String... args) {
        this.personRepository.save(Person.builder()
                .name("Ariadne")
                .build());
    }
}
