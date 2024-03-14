package com.safetynet.alerts.model;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;

import java.util.List;

/**
 * Household class
 */
//@AllArgsConstructor
//@Getter
//@Setter
public class Household {

    private List<Person> householdComposition;

	public List<Person> getHouseholdComposition() {
		return householdComposition;
	}

	public void setHouseholdComposition(List<Person> householdComposition) {
		this.householdComposition = householdComposition;
	}

}
