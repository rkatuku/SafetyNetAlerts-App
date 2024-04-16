package com.safetynet.alerts.dto;

import java.util.List;

public class HouseholdsAtFireStation {

	private final Address address;
	private final List<PersonFlood> personsList;

	public HouseholdsAtFireStation(Address address, List<PersonFlood> personsList) {
		super();
		this.address = address;
		this.personsList = personsList;
	}

	public Address getAddress() {
		return address;
	}

	public List<PersonFlood> getPersonsList() {
		return personsList;
	}

}