package com.safetynet.alerts.dto;

import java.util.List;

public class Flood {

	private final String station;
	private final List<HouseholdsAtFireStation> householdsList;

	public Flood(String station, List<HouseholdsAtFireStation> householdsList) {
		super();
		this.station = station;
		this.householdsList = householdsList;
	}

	public String getStationNumber() {
		return station;
	}

	public String getStation() {
		return station;
	}

	public List<HouseholdsAtFireStation> getHouseholdsList() {
		return householdsList;
	}

}
