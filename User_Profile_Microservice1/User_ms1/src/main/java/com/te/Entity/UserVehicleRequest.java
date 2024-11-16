package com.te.Entity;

import java.util.List;

public class UserVehicleRequest {
	private User user;
	private List<Vehicle> vehicles;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	public UserVehicleRequest(User user, List<Vehicle> vehicles) {
		super();
		this.user = user;
		this.vehicles = vehicles;
	}
	
}
