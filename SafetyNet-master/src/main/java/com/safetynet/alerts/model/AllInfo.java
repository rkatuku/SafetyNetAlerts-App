package com.safetynet.alerts.model;

import java.util.List;
import java.util.Map;

public class AllInfo {
	private List<Person> personsList;
	private Map<String, FireStation> firestations;
	private Map<String, List<Person>> personsPerHousehold;

	public AllInfo(List<Person> personsList, Map<String, FireStation> firestations,
			Map<String, List<Person>> personsPerHousehold) {
		super();
		this.personsList = personsList;
		this.firestations = firestations;
		this.personsPerHousehold = personsPerHousehold;
	}

	public List<Person> getPersonsList() {
		return personsList;
	}

	public void setPersonsList(List<Person> personsList) {
		this.personsList = personsList;
	}

	public Map<String, FireStation> getFirestations() {
		return firestations;
	}

	public void setFirestations(Map<String, FireStation> firestations) {
		this.firestations = firestations;
	}

	public Map<String, List<Person>> getPersonsPerHousehold() {
		return personsPerHousehold;
	}

	public void setPersonsPerHousehold(Map<String, List<Person>> personsPerHousehold) {
		this.personsPerHousehold = personsPerHousehold;
	}

	public Map<String, List<Person>> getHouseholds() {
		return personsPerHousehold;
	}

	public void setPersonList(final List<Person> persons) {
		this.personsList = persons;
	}

}
