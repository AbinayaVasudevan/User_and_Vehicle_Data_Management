package com.te.Entity;

import java.util.Objects;

public class VehicleResponse {
	private String vehicleNo;
    private String model;
    private int yearOfManufacturing;
    private String make;
    private String color;
    private String fuelType;
    
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


	public int getYearOfManufacturing() {
		return yearOfManufacturing;
	}


	public void setYearOfManufacturing(int yearOfManufacturing) {
		this.yearOfManufacturing = yearOfManufacturing;
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


	public VehicleResponse() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    VehicleResponse other = (VehicleResponse) obj;
	    return Objects.equals(vehicleNo, other.vehicleNo) &&
	            Objects.equals(model, other.model) &&
	            yearOfManufacturing == other.yearOfManufacturing &&
	            Objects.equals(make, other.make) &&
	            Objects.equals(color, other.color) &&
	            Objects.equals(fuelType, other.fuelType);
	}

}
