package klusje_v3.repository;

import klusje_v3.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String>{
	
}
