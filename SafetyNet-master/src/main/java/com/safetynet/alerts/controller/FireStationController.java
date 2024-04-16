package com.safetynet.alerts.controller;

import com.safetynet.alerts.status.Status;
import com.safetynet.alerts.dto.FireStationByAddress;
import com.safetynet.alerts.dto.Flood;
import com.safetynet.alerts.dto.PersonsAtFireStationSumm;
import com.safetynet.alerts.service.InterfaceFireStationService;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class FireStationController {

	private static final Logger logger = LogManager.getLogger(FireStationController.class);

	@Autowired
	private InterfaceFireStationService fireStationService;

	@PostMapping("/firestation")
	public void addAddressForFirestation(@NotNull @RequestBody final Map<String, String> stationToCreate,
			final HttpServletResponse response) {

		boolean isAdded = fireStationService.addAddressForFirestation(stationToCreate);

		if (isAdded) {
			logger.info("OK 200 - addAddressForFireStation POST request " + "-  FireStation number : {}, Address : {}",
					stationToCreate.get("station"), stationToCreate.get("address"));
			response.setStatus(Status.STATUS_CREATED_201);
		} else {
			response.setStatus(Status.ERROR_CONFLICT_409);
		}
	}

	@PutMapping("/firestation")
	public void updateAddressForFireStation(@NotNull @RequestBody final Map<String, String> mappingToUpdate,
			final HttpServletResponse response) {

		boolean isUpdated = fireStationService.updateAddressForFireStation(mappingToUpdate);

		if (isUpdated) {
			logger.info("OK 200 - UpdateFireStation PUT request " + "-  FireStation number : {}, Address : {}",
					mappingToUpdate.get("station"), mappingToUpdate.get("address"));
			response.setStatus(Status.STATUS_OK_200);
		} else {
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
	}

	@DeleteMapping("/firestation")
	public void deleteAddressForFireStation(@NotNull @RequestParam final String address,
			final HttpServletResponse response) {

		boolean isDeleted = fireStationService.deleteAddressForFireStation(address);

		if (isDeleted) {
			logger.info("OK 200 - DeleteFireStation DELETE request " + "-  Address : {}", address);
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.info("404 NOT FOUND - Please verify the address");
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
	}

	@GetMapping("/firestation")
	public PersonsAtFireStationSumm firestationNumber(
			@NotNull @RequestParam(value = "stationNumber") final String stationNumber,
			final HttpServletResponse response) {

		PersonsAtFireStationSumm personsAtFireStationSumm = fireStationService.firestationNumber(stationNumber);

		if (personsAtFireStationSumm.getPersonsStationList() != null) {
			logger.info("OK 200 - FireStationNumber GET request");
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.error("FAILED 404 - No FireStation found : {}. " + "Check the station number", stationNumber);
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
		return personsAtFireStationSumm;
	}

	@GetMapping("/phoneAlert")
	public List<String> phoneAlert(@NotNull @RequestParam(value = "firestation") final String firestation,
			final HttpServletResponse response) {
		List<String> phoneNumberList = fireStationService.phoneAlert(firestation);

		if (!phoneNumberList.isEmpty()) {
			logger.info("OK 200 - /phoneAlert?firestation=" + firestation);
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.error("FAILED 404 - /phoneAlert?firestation=" + firestation);
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
		return phoneNumberList;
	}

	@GetMapping("/fire")
	public List<FireStationByAddress> fire(@NotNull @RequestParam(value = "address") final String address,
			final HttpServletResponse response) {

		List<FireStationByAddress> fireDTOPersons = fireStationService.fire(address);

		if (!fireDTOPersons.isEmpty()) {
			logger.info("OK 200 - /fire?address=" + address);
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.error("FAILED 200 - /fire?address=" + address);
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
		return fireDTOPersons;
	}

	@GetMapping("/flood/stations")
	public List<Flood> flood(@NotNull @RequestParam(value = "stations") final List<String> stations,
			final HttpServletResponse response) {

		List<Flood> flood = fireStationService.flood(stations);

		if (flood != null) {
			logger.info("OK 200 - /flood/stations?stations=" + stations);
			response.setStatus(Status.STATUS_OK_200);
		} else {
			logger.error("FAILED 404 - /flood/stations?stations=" + stations);
			response.setStatus(Status.ERROR_NOT_FOUND_404);
		}
		return flood;
	}

}
