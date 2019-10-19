package com.example.demo.person;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
	
	private static final String QUERY_FIND_BY_ID = "SELECT * FROM person WHERE id=? limit 1";
	private static final String QUERY_FIND_ALL = "SELECT * FROM person";
	private static final String QUERY_UPDATE_BY_ID = "UPDATE person set firstname=?, lastname=? WHERE id=?";
	private static final String QUERY_DELETE_BY_ID = "DELETE FROM person where id=?";

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	SimpleJdbcInsert simpleJdbcInsert;
	
	static RowMapper<Person> rowMapper = (rs, numRows) -> {
		Person personFound = new Person();
		personFound.setId(rs.getLong("id"));
		personFound.setFirstname(rs.getString("firstname"));
		personFound.setLastname(rs.getString("lastname"));
		return personFound;
	};
	
    @Autowired
    public PersonRepositoryImpl(DataSource dataSource) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
          .withTableName("person").usingGeneratedKeyColumns("id");
    }
	
	@Override
	public void create(Person personToCreate) {
		Map<String, String> params = Map.of(
				"firstname", personToCreate.getFirstname(),
				"lastname", personToCreate.getLastname());
		
		Number id = simpleJdbcInsert.executeAndReturnKey(params);
		personToCreate.setId(id.longValue());
		
	}

	@Override
	public Optional<Person> findById(long id) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(QUERY_FIND_BY_ID, rowMapper, id));
		} catch(EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Person> findAll() {
		return jdbcTemplate.query(QUERY_FIND_ALL, rowMapper);
	}

	@Override
	public void update(Person person) throws PersonNotFoundException {
		int nbRowUpdated = jdbcTemplate.update(QUERY_UPDATE_BY_ID, 
				person.getFirstname(), person.getLastname(), person.getId());
		if( nbRowUpdated == 0 ) {
			throw new PersonNotFoundException();
		}
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update(QUERY_DELETE_BY_ID, id);
	}

}
