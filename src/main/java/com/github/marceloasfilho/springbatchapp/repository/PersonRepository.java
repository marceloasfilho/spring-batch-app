package com.github.marceloasfilho.springbatchapp.repository;

import com.github.marceloasfilho.springbatchapp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
