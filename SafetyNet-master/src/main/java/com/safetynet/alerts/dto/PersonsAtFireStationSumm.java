package com.safetynet.alerts.dto;

import java.util.List;

public class PersonsAtFireStationSumm {

	private final List<PersonsAtFireStation> personsStationList;
	private final int countOfAdults;
	private final int countOfChildren;

	public PersonsAtFireStationSumm(List<PersonsAtFireStation> personsStationList, int totalAdultsNumber,
			int totalChildrenNumber) {
		super();
		this.personsStationList = personsStationList;
		this.countOfAdults = totalAdultsNumber;
		this.countOfChildren = totalChildrenNumber;
	}

	public List<PersonsAtFireStation> getPersonsStationList() {
		return personsStationList;
	}

	public int getTotalAdultsNumber() {
		return countOfAdults;
	}

	public int getTotalChildrenNumber() {
		return countOfChildren;
	}

}
