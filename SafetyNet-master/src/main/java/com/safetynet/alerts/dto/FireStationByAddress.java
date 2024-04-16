package com.safetynet.alerts.dto;

import java.util.List;

public class FireStationByAddress {

	private final String stationNumber;
	private final String firstName;
	private final String lastName;
	private final int age;
	private final String phoneNumber;
	private final List<String> medications;
	private final List<String> allergies;

	public FireStationByAddress(String stationNumber, String firstName, String lastName, int age, String phoneNumber,
			List<String> medications, List<String> allergies) {
		super();
		this.stationNumber = stationNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.medications = medications;
		this.allergies = allergies;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public List<String> getMedications() {
		return medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

}
