package com.safetynet.alerts.service;

import com.safetynet.alerts.model.AllInfo;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService implements InterfaceMedicalRecordService {

	private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

	@Autowired
	private AllInfo allInformations;

	public MedicalRecord createMedicalRecord(final MedicalRecord newMedicalRecord) {

		List<Person> personsList = allInformations.getPersonsList();

		for (Person existingPerson : personsList) {
			if (existingPerson.getFirstName().equalsIgnoreCase(newMedicalRecord.getFirstName().toUpperCase())
					&& existingPerson.getLastName().equalsIgnoreCase(newMedicalRecord.getLastName().toUpperCase())
					&& existingPerson.getMedicalRecord() == null) {
				existingPerson.setMedicalRecord(newMedicalRecord);
				return newMedicalRecord;
			}
		}
		logger.error("Error create the MedicalRecord : {} {}, " + "already existant", newMedicalRecord.getFirstName(),
				newMedicalRecord.getLastName());
		return null;
	}

	public boolean updateMedicalRecord(final MedicalRecord medicalRecord) {
		boolean isUpdated = false;
		List<Person> personsList = allInformations.getPersonsList();

		for (Person existingPerson : personsList) {
			if (existingPerson.getMedicalRecord() != null
					&& existingPerson.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName().toUpperCase())
					&& existingPerson.getLastName().equalsIgnoreCase(medicalRecord.getLastName().toUpperCase())) {
				existingPerson.setMedicalRecord(medicalRecord);
				isUpdated = true;
			}
		}
		return isUpdated;
	}

	public boolean deleteMedicalRecord(final String firstName, final String lastName) {

		boolean isDeleted = false;
		List<Person> personsList = allInformations.getPersonsList();

		for (Person existingPerson : personsList) {
			if (existingPerson.getMedicalRecord() != null
					&& existingPerson.getFirstName().equalsIgnoreCase(firstName.toUpperCase())
					&& existingPerson.getLastName().equalsIgnoreCase(lastName.toUpperCase())) {
				existingPerson.setMedicalRecord(null);
				isDeleted = true;
			}
		}
		return isDeleted;
	}
}
