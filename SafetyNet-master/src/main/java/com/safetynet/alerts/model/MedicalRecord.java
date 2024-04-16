package com.safetynet.alerts.model;

import com.safetynet.alerts.utils.AgeCalculator;

import java.util.List;

 /*
 * MedicalRecord class
 */
public class MedicalRecord {

	private String firstName;
	private String lastName;
	private String birthday;
	private List<String> medications;
	private List<String> allergies;

	public MedicalRecord() {
	}

	public MedicalRecord(final String personBirthday, final List<String> personMedication,
			final List<String> personAllergies) {
		this.birthday = personBirthday;
		this.medications = personMedication;
		this.allergies = personAllergies;
	}

	public MedicalRecord(String firstName, String lastName, String birthday, List<String> medications,
			List<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.medications = medications;
		this.allergies = allergies;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

	/*
	 * @return person age
	 */
	public int getAge() {
		return AgeCalculator.ageCalculation(this.birthday);
	}

}
