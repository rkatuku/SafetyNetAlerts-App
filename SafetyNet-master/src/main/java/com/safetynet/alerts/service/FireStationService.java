package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.model.AllInfo;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service
public class FireStationService implements InterfaceFireStationService {

	private static final Logger logger = LogManager.getLogger(FireStationService.class);

	@Autowired
	private AllInfo allInformations;

	public boolean addAddressForFirestation(final Map<String, String> firestationMappingToCreate) {

		try {
			Map<String, FireStation> allFirestationsMapping = allInformations.getFirestations();
			String newAddress = firestationMappingToCreate.get("address");
			FireStation firestationRecovered = allFirestationsMapping.get(firestationMappingToCreate.get("station"));

			for (Entry<String, FireStation> entry : allFirestationsMapping.entrySet()) {
				FireStation firestationsNumber = entry.getValue();

				if (firestationsNumber.getAddresses().toString().contains(newAddress)) {
					logger.error("Address enter : {} already exist ", firestationMappingToCreate.values());
					return false;
				}
			}
			firestationRecovered.addAddress(newAddress);
			return true;
		} catch (NullPointerException np) {
			throw new NullPointerException("NPE - Verify station number" + np);
		}
	}

	public boolean updateAddressForFireStation(final Map<String, String> firestationMapCreate) {

		try {
			Map<String, FireStation> allFirestationsMapping = allInformations.getFirestations();
			String address = firestationMapCreate.get("address");
			FireStation firestationNumberRecovered = allFirestationsMapping.get(firestationMapCreate.get("station"));

			for (Entry<String, FireStation> entry : allFirestationsMapping.entrySet()) {
				FireStation firestationsNumber = entry.getValue();

				if (firestationsNumber.getAddresses().toString().contains(address)) {
					firestationsNumber.getAddresses().remove(address);
					firestationNumberRecovered.addAddress(address);
					return true;
				}
			}
			logger.error("Address enter : {} does not exist.", firestationMapCreate.values());
			return false;
		} catch (NullPointerException np) {
			throw new NullPointerException("NPE - Please verify the number station " + np);
		}
	}

	public boolean deleteAddressForFireStation(final String address) {
		Map<String, FireStation> allFirestationsMapping = allInformations.getFirestations();

		for (Entry<String, FireStation> entry : allFirestationsMapping.entrySet()) {
			FireStation firestationsNumber = entry.getValue();

			if (firestationsNumber.getAddresses().toString().contains(address)) {
				firestationsNumber.getAddresses().remove(address);
				return true;
			}
		}
		logger.error("Address entered : {} not exist.", address);
		return false;
	}

	public PersonsAtFireStationSumm firestationNumber(final String stationNumber) {

		int countOfAdults = 0;
		int countOfChildren = 0;

		Map<String, FireStation> allFirestationsMapping = allInformations.getFirestations();
		Set<String> addressesRecovered = new HashSet<>();
		for (Entry<String, FireStation> entry : allFirestationsMapping.entrySet()) {
			FireStation firestation = entry.getValue();

			if (firestation.getStation().equals(stationNumber)) {
				addressesRecovered = firestation.getAddresses();
			}
		}

		if (addressesRecovered.isEmpty()) {
			return new PersonsAtFireStationSumm(null, 0, 0);
		}
		List<Person> allPersonsList = allInformations.getPersonsList();
		List<PersonsAtFireStation> personsUnderResponsibility = new ArrayList<>();

		for (Person person : allPersonsList) {
			if (addressesRecovered.contains(person.getAddress())) {

				PersonsAtFireStation personStationDto = new PersonsAtFireStation(person.getFirstName(),
						person.getLastName(), person.getAddress(), person.getPhone());
				personsUnderResponsibility.add(personStationDto);

				if (!AgeCalculator.isChild(person)) {
					countOfAdults++;
				} else {
					countOfChildren++;
				}
			}
		}
		return new PersonsAtFireStationSumm(personsUnderResponsibility, countOfAdults, countOfChildren);
	}

	public List<String> phoneAlert(final String fireStation) {
		Map<String, FireStation> allFirestationsMapping = allInformations.getFirestations();
		Set<String> addressesRecovered = new HashSet<>();

		for (Entry<String, FireStation> entry : allFirestationsMapping.entrySet()) {
			FireStation firestationToRecover = entry.getValue();
			if (firestationToRecover.getStation().equals(fireStation)) {
				addressesRecovered = firestationToRecover.getAddresses();
			}
		}
		List<Person> allPersonsList = allInformations.getPersonsList();
		List<String> phoneNumberList = new ArrayList<>();

		for (Person person : allPersonsList) {
			if (addressesRecovered.contains(person.getAddress())) {
				phoneNumberList.add(person.getPhone());
			}
		}
		return phoneNumberList;
	}

	public List<FireStationByAddress> fire(final String address) {

		Map<String, FireStation> allFirestationsMapping = allInformations.getFirestations();
		List<FireStationByAddress> fireDtoPersonsList = new ArrayList<>();

		for (Entry<String, FireStation> entry : allFirestationsMapping.entrySet()) {
			FireStation firestation = entry.getValue();
			if (firestation.getAddresses().contains(address)) {
				String stationNumber = firestation.getStation();

				Map<String, List<Person>> households = allInformations.getHouseholds();
				for (Entry<String, List<Person>> entryset : households.entrySet()) {
					String householdAddress = entryset.getKey();
					if (!address.equals(householdAddress)) {
						continue;
					}
					List<Person> householdMembersList = entryset.getValue();
					for (Person person : householdMembersList) {
						FireStationByAddress fireDtoPerson = new FireStationByAddress(stationNumber,
								person.getFirstName(), person.getLastName(), person.getMedicalRecord().getAge(),
								person.getPhone(), person.getMedicalRecord().getMedications(),
								person.getMedicalRecord().getAllergies());
						fireDtoPersonsList.add(fireDtoPerson);
					}
				}
			}
		}
		return fireDtoPersonsList;
	}

	public List<Flood> flood(final List<String> stations) {

		List<Flood> floodDtoList = new ArrayList<>();
		Map<String, FireStation> allFireStations = allInformations.getFirestations();
		List<Person> allPersonsList = allInformations.getPersonsList();

		for (String station : stations) {

			Set<String> addressesRecovered = new HashSet<>();
			for (Entry<String, FireStation> entry : allFireStations.entrySet()) {
				FireStation firestationToRecover = entry.getValue();
				if (firestationToRecover.getStation().contains(station)) {
					addressesRecovered = firestationToRecover.getAddresses();
					break;
				}
			}
			if (addressesRecovered.isEmpty()) {
				logger.error("No station found {}", station);
				return null;
			}

			Map<Address, List<PersonFlood>> householdDTO = new HashMap<>();
			for (Person person : allPersonsList) {

				if (addressesRecovered.contains(person.getAddress())) {
					PersonFlood floodPerson = new PersonFlood(person.getFirstName(), person.getLastName(),
							person.getPhone(), person.getMedicalRecord().getAge(),
							person.getMedicalRecord().getMedications(), person.getMedicalRecord().getAllergies());
					Address addressDTO = new Address(person.getAddress(), person.getCity(), person.getZip());

					boolean isSameHouse = false;
					for (Entry<Address, List<PersonFlood>> entry : householdDTO.entrySet()) {
						if (entry.getKey().getAddress().equals(addressDTO.getAddress())
								&& entry.getKey().getCity().equals(addressDTO.getCity())
								&& entry.getKey().getZip().equals(addressDTO.getZip())) {
							entry.getValue().add(floodPerson);
							isSameHouse = true;
						}
					}
					if (!isSameHouse) {
						householdDTO.put(addressDTO, new ArrayList<>());
						for (Entry<Address, List<PersonFlood>> entry : householdDTO.entrySet()) {
							if (entry.getKey().getAddress().equals(addressDTO.getAddress())
									&& entry.getKey().getCity().equals(addressDTO.getCity())
									&& entry.getKey().getZip().equals(addressDTO.getZip())) {
								entry.getValue().add(floodPerson);
							}
						}
					}
				}
			}
			List<HouseholdsAtFireStation> householdsFloodList = new ArrayList<>();
			for (Entry<Address, List<PersonFlood>> entry : householdDTO.entrySet()) {
				HouseholdsAtFireStation householdsFloodDTO = new HouseholdsAtFireStation(entry.getKey(),
						entry.getValue());
				householdsFloodList.add(householdsFloodDTO);
			}
			Flood flood = new Flood(station, householdsFloodList);
			floodDtoList.add(flood);
		}
		return floodDtoList;
	}
}
