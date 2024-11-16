package com.te.Entity;

public class VehicleResponse {
	private String vehicleNo;
    private String model;
    private String make;
    private String color;
    private String fuelType;
    private int yearOfManufacturing;
    

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}


	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}


	public String getMake() {
		return make;
	}


	public void setMake(String make) {
		this.make = make;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getFuelType() {
		return fuelType;
	}


	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}


	public int getYearOfManufacturing() {
		return yearOfManufacturing;
	}


	public void setYearOfManufacturing(int yearOfManufacturing) {
		this.yearOfManufacturing = yearOfManufacturing;
	}


	public VehicleResponse() {
		// TODO Auto-generated constructor stub
	}

}
