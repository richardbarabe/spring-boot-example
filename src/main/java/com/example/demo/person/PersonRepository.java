package com.example.demo.person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
	void create(Person personToCreate);
	
	/**
	 * Find a specific person in the database, based on it's unique id.
	 * @param id Unique id of the person to find.
	 * @return optionally, the person found.
	 */
	Optional<Person> findById(long id);
	
	/**
	 * Find and return all the persons.
	 * @return A list containing all the persons in the database.  Can return an empty list if the database is empty.
	 */
	List<Person> findAll();
	
	/**
	 * Update the specified person.
	 * @param personToUpdate The Person object to update.
	 * @throws PersonNotFoundException If the specified person, based on it's id, cannot be updated because it is not in the database.
	 */
	void update(Person personToUpdate) throws PersonNotFoundException;
	
	/**
	 * Delete the desired person based on it's unique id.
	 * @param id Unique id of the person to delete.
	 */
	void delete(long id);
}
