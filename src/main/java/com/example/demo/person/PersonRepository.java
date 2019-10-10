package com.example.demo.person;

import java.util.Optional;

public interface PersonRepository {
	void create(Person personToCreate);
	Optional<Person> findById(long id);
}
