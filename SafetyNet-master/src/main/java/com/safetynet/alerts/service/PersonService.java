package com.safetynet.alerts.service;

import com.safetynet.alerts.model.AllInfo;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.AgeCalculator;
import com.safetynet.alerts.dto.ChildAlert;
import com.safetynet.alerts.dto.PersonInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

@Service
public class PersonService implements InterfacePersonService {

	private static final Logger logger = LogManager.getLogger(PersonService.class);

	@Autowired
	private AllInfo allInformations;

	public List<PersonInfo> personInfo(final String firstName, final String lastName) {
		logger.debug("Request PersonInfo");
		List<PersonInfo> personsInfosList = new ArrayList<>();
		List<Person> personsList = allInformations.getPersonsList();

		for (Person person : personsList) {
			if (person.getLastName().equals(lastName)) {
				PersonInfo personsInfos = new PersonInfo(person.getFirstName(), person.getLastName(),
						person.getMedicalRecord().getAge(), person.getAddress(), person.getCity(), person.getZip(),
						person.getEmail(), person.getMedicalRecord().getMedications(),
						person.getMedicalRecord().getAllergies());
				personsInfosList.add(personsInfos);
			}
		}
		logger.debug("Request PersonInfo OK");
		return personsInfosList;
	}

	public List<String> communityEmail(final String city) {

		List<String> personsEmail = new ArrayList<>();
		List<Person> personsList = allInformations.getPersonsList();

		for (Person person : personsList) {
			if (person.getCity().equals(city)) {
				personsEmail.add(person.getEmail());
			}
		}
		return personsEmail;
	}

	public List<ChildAlert> childAlert(final String address) {
		logger.debug("Request ChildAlert");
		List<ChildAlert> childAlert = new ArrayList<>();
		Map<String, List<Person>> households = allInformations.getHouseholds();
		boolean isWithChilds = false;

		for (Entry<String, List<Person>> entry : households.entrySet()) {
			String householdAddress = entry.getKey();
			if (!address.equals(householdAddress)) {
				continue;
			}
			List<Person> householdMembersList = entry.getValue();
			for (Person person : householdMembersList) {
				if (AgeCalculator.isChild(person)) {
					int childAge = AgeCalculator.ageCalculation(person.getMedicalRecord().getBirthday());
					ChildAlert child = new ChildAlert(childAge, person.getFirstName(), person.getLastName());
					childAlert.add(child);
					isWithChilds = true;

				} else if (!AgeCalculator.isChild(person)) {
					int adultAge = AgeCalculator.ageCalculation(person.getMedicalRecord().getBirthday());
					ChildAlert adultHouseholdMember = new ChildAlert(adultAge, person.getFirstName(),
							person.getLastName());
					childAlert.add(adultHouseholdMember);
				}
			}
		}
		if (!isWithChilds) {
			childAlert.clear();
		}
		return childAlert;
	}

	public Person createPerson(final Map<String, String> personToCreate) {

		Person newPerson = new Person(
				personToCreate.get("firstName").substring(0, 1).toUpperCase()
						+ personToCreate.get("firstName").substring(1).toLowerCase(),
				personToCreate.get("lastName").substring(0, 1).toUpperCase()
						+ personToCreate.get("lastName").substring(1).toLowerCase(),
				personToCreate.get("address"),
				personToCreate.get("city").substring(0, 1).toUpperCase()
						+ personToCreate.get("city").substring(1).toLowerCase(),
				personToCreate.get("zip"), personToCreate.get("phone"), personToCreate.get("email"));
		List<Person> personsList = allInformations.getPersonsList();

		for (Person existingPerson : personsList) {
			if (existingPerson.getFirstName().equals(newPerson.getFirstName())
					&& existingPerson.getLastName().equals(newPerson.getLastName())) {
				logger.error("Error create person : {} {}, " + "person already exist", newPerson.getFirstName(),
						newPerson.getLastName());
				return null;
			}
		}
		personsList.add(newPerson);

		// Update households
		Map<String, List<Person>> households = allInformations.getHouseholds();
		List<Person> newPersonHousehold = households.computeIfAbsent(newPerson.getAddress(), temp -> new ArrayList<>());
		newPersonHousehold.add(newPerson);
		return newPerson;
	}

	public boolean updatePerson(final Person personToUpdate) {
		List<Person> personsList = allInformations.getPersonsList();
		boolean isUpdated = false;

		for (Person existingPerson : personsList) {
			if (existingPerson.getFirstName().equals(personToUpdate.getFirstName())
					&& existingPerson.getLastName().equals(personToUpdate.getLastName())) {
				existingPerson.setAddress(personToUpdate.getAddress());
				existingPerson.setCity(personToUpdate.getCity());
				existingPerson.setZip(personToUpdate.getZip());
				existingPerson.setPhone(personToUpdate.getPhone());
				existingPerson.setEmail(personToUpdate.getEmail());
				allInformations.setPersonsList(personsList);
				isUpdated = true;
			}
		}
		return isUpdated;
	}

	public boolean deletePerson(final Person personToDelete) {
		List<Person> personsList = allInformations.getPersonsList();
		boolean isDeleted = false;

		for (Iterator<Person> iter = personsList.iterator(); iter.hasNext();) {
			Person existingPerson = iter.next();

			if (existingPerson.getFirstName().matches(Pattern.quote(personToDelete.getFirstName()))
					&& existingPerson.getLastName().matches(Pattern.quote(personToDelete.getLastName()))) {
				iter.remove();
				isDeleted = true;
			}
		}
		return isDeleted;
	}
}
