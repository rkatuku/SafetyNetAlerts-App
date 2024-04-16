package com.safetynet.alerts.controller;

import com.safetynet.alerts.status.Status;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.InterfaceMedicalRecordService;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class MedicalRecordController {

	private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

	@Autowired
	private InterfaceMedicalRecordService medicalRecordService;

	@PostMapping("/medicalRecord")
	public void createMedicalRecord(@RequestBody final MedicalRecord newMedicalRecord,
			final HttpServletResponse response) {

		MedicalRecord medicalRecordCreated = medicalRecordService.createMedicalRecord(newMedicalRecord);

		if (medicalRecordCreated != null) {
			logger.info("OK 201 - createMedicalRecord POST request");
			response.setStatus(Status.STATUS_CREATED_201);
		} else {
			logger.info("FAILED 409 - createMedicalRecord POST request");
			response.setStatus(Status.ERROR_CONFLICT_409);
		}
	}

	@PutMapping("/medicalRecord")
	public void updateMedicalRecord(@NotNull @RequestBody final MedicalRecord medicalRecord,
			final HttpServletResponse response) {

		boolean isUpdated = medicalRecordService.updateMedicalRecord(medicalRecord);

		if (isUpdated) {
			logger.info("OK 200 - MedicalRecord PUT request");
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.error("404 - FAILED to update MedicalRecord for person: {} {}." + " Unknown",
					medicalRecord.getFirstName(), medicalRecord.getLastName());
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
	}

	@DeleteMapping("/medicalRecord")
	public void deleteMedicalRecord(@NotNull @RequestParam final String firstName,
			@NotNull @RequestParam final String lastName, final HttpServletResponse response) {

		boolean isDeleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);

		if (isDeleted) {
			logger.info("OK 200 - MedicalRecord DELETE request");
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.error("404 - FAILED to delete MedicalRecord for : {} {}" + "Who is this person", firstName,
					lastName);
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
	}
}
