package com.te.Entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Vehicle {
	@Id
    private String vehicleNo;

    @ManyToOne
    @JoinColumn(name = "mobile_number")
    private User user;

    private String model;

    private int yearOfManufacturing;
    
    private String make;
    
    private String color;
    
    private String fuelType;

	public Vehicle(String vehicleNo, User user, String model, int yearOfManufacturing, String make, String color,
			String fuelType) {
		super();
		this.vehicleNo = vehicleNo;
		this.user = user;
		this.model = model;
		this.yearOfManufacturing = yearOfManufacturing;
		this.make = make;
		this.color = color;
		this.fuelType = fuelType;
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

	
	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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


    
    public Vehicle() {
    	
    }
   

	
	

	


}
