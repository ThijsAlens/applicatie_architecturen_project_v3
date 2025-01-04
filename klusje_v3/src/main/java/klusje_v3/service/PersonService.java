package klusje_v3.service;

import klusje_v3.model.Person;

public interface PersonService {
	public Person getPersonByUsername(String username);
	public void updatePerson(Person p);
	public void addPerson(Person p);
}
