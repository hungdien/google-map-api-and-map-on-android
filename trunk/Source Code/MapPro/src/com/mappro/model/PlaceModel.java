package com.mappro.model;

public class PlaceModel {
	
	public PlaceModel(double lat, double lng, String name, String address) {
		this.lat = lat;
		this.lng = lng;
		this.setName(name);
		this.address = address;
	}

	public PlaceModel() {
		this.lat = 0.0;
		this.lng = 0.0;
		this.address = "";
		this.name = "";
	}
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private double lat;
	private double lng;
	private String name;
	private String address;
}
