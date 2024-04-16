package com.safetynet.alerts.service;

import java.util.List;
import java.util.Map;

import com.safetynet.alerts.dto.ChildAlert;
import com.safetynet.alerts.dto.PersonInfo;
import com.safetynet.alerts.model.Person;

public interface InterfacePersonService {
	List<PersonInfo> personInfo(String firstName, String lastName);

	List<String> communityEmail(String city);

	Person createPerson(Map<String, String> personToCreate);

	boolean updatePerson(Person personToUpdate);

	boolean deletePerson(Person personToDelete);

	List<ChildAlert> childAlert(String address);

}
