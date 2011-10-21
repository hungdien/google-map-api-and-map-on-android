package com.mappro.model;

public class PlaceModel {
	
	public PlaceModel(String lat, String lng, String name, String address) {
		this.lat = lat;
		this.lng = lng;
		this.setName(name);
		this.address = address;
	}

	public PlaceModel() {
		this.lat = "";
		this.lng = "";
		this.address = "";
		this.name = "";
	}

	
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
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

	private String lat;
	private String lng;
	private String name;
	private String address;
}
