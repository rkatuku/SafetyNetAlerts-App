package com.safetynet.alerts.dto;

public class PersonsAtFireStation {

	private final String firstName;
	private final String lastName;
	private final String address;
	private final String phoneNumber;

	public PersonsAtFireStation(String firstName, String lastName, String address, String phoneNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}
