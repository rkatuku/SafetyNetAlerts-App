package com.safetynet.alerts.model;

import java.util.HashSet;
import java.util.Set;

/**
 * FireStation class
 */
public class FireStation {

	private final Set<String> addresses = new HashSet<>();
	private String station;

	public FireStation(String station) {
		super();
		this.station = station;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public Set<String> getAddresses() {
		return addresses;
	}

	public void addAddress(final String newAddress) {
		addresses.add(newAddress);
	}

}
