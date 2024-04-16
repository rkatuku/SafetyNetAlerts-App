package com.safetynet.alerts.dto;

import java.util.List;

public class PersonFlood {

	private final String firstName;
	private final String lastName;
	private final String phone;
	private final int age;
	private final List<String> medications;
	private final List<String> allergies;

	public PersonFlood(String firstName, String lastName, String phone, int age, List<String> medications,
			List<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.age = age;
		this.medications = medications;
		this.allergies = allergies;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public int getAge() {
		return age;
	}

	public List<String> getMedications() {
		return medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

}
