package com.te.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Document(collection= "vehicles")
public class VehicleMongo {
	@Id
	private String vehicleNo;
	private String mobileNumber;
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
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	public VehicleMongo() {
        super();
    }
	


}
