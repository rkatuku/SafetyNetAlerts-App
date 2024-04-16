package com.safetynet.alerts.dto;

import java.util.List;

public class PersonInfo {

	private final String firstName;
	private final String lastName;
	private final int age;
	private final String address;
	private final String city;
	private final String zip;
	private final String email;
	private final List<String> medications;
	private final List<String> allergies;

	public PersonInfo(String firstName, String lastName, int age, String address, String city, String zip,
			String email, List<String> medications, List<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.address = address;
		this.city = city;
		this.zip = zip;
		this.email = email;
		this.medications = medications;
		this.allergies = allergies;
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

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getZip() {
		return zip;
	}

	public String getEmail() {
		return email;
	}

	public List<String> getMedications() {
		return medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

}
