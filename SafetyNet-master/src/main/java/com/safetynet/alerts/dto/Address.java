package com.safetynet.alerts.dto;

public class Address {

	private final String address;
	private final String city;
	private final String zip;

	public Address(String address, String city, String zip) {
		super();
		this.address = address;
		this.city = city;
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getZip() {
		return zip;
	}

}
