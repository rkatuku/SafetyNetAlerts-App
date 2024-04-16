package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationByAddress;
import com.safetynet.alerts.dto.Flood;
import com.safetynet.alerts.dto.PersonsAtFireStationSumm;

import java.util.List;
import java.util.Map;

public interface InterfaceFireStationService {

	List<String> phoneAlert(String firestation);

	List<FireStationByAddress> fire(String address);

	List<Flood> flood(List<String> stations);

	boolean addAddressForFirestation(Map<String, String> firestationToCreate);

	boolean updateAddressForFireStation(Map<String, String> firestationToUpdate);

	boolean deleteAddressForFireStation(String address);

	PersonsAtFireStationSumm firestationNumber(String stationNumber);

}
