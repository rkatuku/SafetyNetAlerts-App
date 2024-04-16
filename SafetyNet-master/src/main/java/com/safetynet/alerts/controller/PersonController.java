package com.safetynet.alerts.controller;

import com.safetynet.alerts.status.Status;
import com.safetynet.alerts.dto.ChildAlert;
import com.safetynet.alerts.dto.PersonInfo;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.InterfacePersonService;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class PersonController {

	private static final Logger logger = LogManager.getLogger(PersonController.class);

	@Autowired
	private InterfacePersonService personService;

	@GetMapping("/personInfo")
	public List<PersonInfo> personInfo(@RequestParam final String firstName,
			@NotNull @RequestParam final String lastName, final HttpServletResponse response) {

		logger.debug("GET request received for personInfos: {}", lastName);
		List<PersonInfo> personInfos = personService.personInfo(firstName, lastName);

		if (!personInfos.isEmpty()) {
			logger.info("OK 200 - personInfos GET request");
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.error("404 FAILED - No person found with LastName : {}", lastName);
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
		return personInfos;
	}

	@GetMapping("/communityEmail")
	public List<String> getCommunityEmail(@NotNull @RequestParam(value = "city") final String city,
			final HttpServletResponse response) {

		List<String> communityEmail = personService.communityEmail(city);

		if (!communityEmail.isEmpty()) {
			logger.info("OK 200 - CommunityEmail GET request");
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.error("404 FAILED - Not found : {}", city);
			String notFounded = "Not found : " + city;
			communityEmail.add(notFounded);
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
		return communityEmail;
	}

	@GetMapping("/childAlert")
	public List<ChildAlert> getChildAlert(@NotNull @RequestParam(value = "address") final String address,
			final HttpServletResponse response) {

		logger.debug("GET request received for getChildAlert : {}", address);
		List<ChildAlert> childAlert = personService.childAlert(address);

		if (!childAlert.isEmpty()) {
			logger.info("OK 200 - ChildAlert GET request");
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.error("404 FAILED - No household with child found : {}", address);
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
		return childAlert;
	}

	@PostMapping("/person")
	public void createPerson(@NotNull @RequestBody final Map<String, String> personToCreate,
			final HttpServletResponse response) {
		Person personsCreated = personService.createPerson(personToCreate);

		if (personsCreated != null) {
			logger.info("OK 201 - Create Person POST request for {} {}", personsCreated.getFirstName(),
					personsCreated.getLastName());
			response.setStatus(Status.STATUS_CREATED_201);
		} else {
			logger.info("FAILED 409 - CONFLICT");
			response.setStatus(Status.ERROR_CONFLICT_409);
		}
	}

	@PutMapping("/person")
	public void updatePerson(@NotNull @RequestBody final Person personToUpdate, final HttpServletResponse response) {

		boolean isUpdated = personService.updatePerson(personToUpdate);

		if (isUpdated) {
			logger.info("SUCCESS - UpdatePerson PUT request");
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.error("404 - No person found : {} {}", personToUpdate.getFirstName(), personToUpdate.getLastName());
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
	}

	@DeleteMapping("/person")
	public void deletePerson(@NotNull @RequestBody final Person personToDelete, final HttpServletResponse response) {

		boolean isDeleted = personService.deletePerson(personToDelete);

		if (isDeleted) {
			logger.info("OK 200 - DeletePerson DELETE request");
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.error("404 - No person found : {} {}", personToDelete.getFirstName(), personToDelete.getLastName());
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
	}
}
