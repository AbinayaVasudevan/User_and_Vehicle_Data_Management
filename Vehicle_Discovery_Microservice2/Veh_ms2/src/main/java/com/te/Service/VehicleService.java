package com.te.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.te.Entity.Vehicle;
import com.te.Entity.VehicleMongo;
import com.te.Entity.VehicleNoResponse;
import com.te.Exception.MobileNumberNotFoundException;
import com.te.Repository.VehicleMongoRepository;

import jakarta.transaction.Transactional;
@Service
public class VehicleService {
	@Autowired
    private VehicleMongoRepository vehicleMongoRepository;
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
 
    @Autowired
    private RestTemplate restTemplate; 
 
    @Transactional
    public List<VehicleMongo> fetchAndStoreVehicles(String mobileNumber) {
        ResponseEntity<List<Vehicle>> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/users/{mobileNumber}/vehicles",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Vehicle>>() {},
                mobileNumber
        );
 
        List<Vehicle> vehicles = responseEntity.getBody();
        
        List<VehicleMongo> vehiclesMongo = vehicles.stream()
                .map(v -> {
                    VehicleMongo vehicleMongo = new VehicleMongo();
                    vehicleMongo.setVehicleNo(v.getVehicleNo());
                    vehicleMongo.setModel(v.getModel());
                    vehicleMongo.setYearOfManufacturing(v.getYearOfManufacturing());
                    vehicleMongo.setMobileNumber(mobileNumber);
                    vehicleMongo.setMake(v.getMake());
                    vehicleMongo.setColor(v.getColor());
                    vehicleMongo.setFuelType(v.getFuelType());
                    return vehicleMongo;
                })
                .collect(Collectors.toList());
        vehicleMongoRepository.saveAll(vehiclesMongo);
 
        return vehiclesMongo;
}
    public List<VehicleMongo> getByMobileNumber(String mobileNumber) {
    	List<VehicleMongo> vehicles = vehicleMongoRepository.findByMobileNumber(mobileNumber);
    	if (vehicles.isEmpty()) {            
    		throw new MobileNumberNotFoundException();         
    	}
    	return vehicleMongoRepository.findByMobileNumber(mobileNumber);    
    	}
    public List<VehicleMongo> getAllVehicles() {
        return vehicleMongoRepository.findAll();
    }
    public void updateVehicleByMobileNumber(String mobileNumber, VehicleMongo vehicles) {
    	List<VehicleMongo> vehicle = vehicleMongoRepository.findByMobileNumber(mobileNumber);
    	if (vehicle.isEmpty()) {            
    		throw new MobileNumberNotFoundException();         
    	}
		vehicleMongoRepository.saveAll(vehicle);
    }
    public boolean deleteVehicleByMobileNumber(String mobileNumber) {
        
        List<VehicleMongo> vehicles = vehicleMongoRepository.findByMobileNumber(mobileNumber);
        if (!vehicles.isEmpty()) {
            
            for (VehicleMongo vehicle : vehicles) {
                vehicleMongoRepository.delete(vehicle);
            }
            return true;
        }
        return false;
    }
    public VehicleMongo getVehicleByVehicleNo(String vehicleNo) {
        return vehicleMongoRepository.findByVehicleNo(vehicleNo);
    }
    public String getVehicleNoByMobileNumber(String mobileNumber) {
        String vehicleNo = vehicleMongoRepository.findVehicleNoByMobileNumber(mobileNumber);
        if (vehicleNo != null) {
            return vehicleNo;
        } else {
            return "Vehicle not found for mobile number: " + mobileNumber;
        }
    }
    public List<VehicleNoResponse> getOnlyVehicleNoByMobileNumber(String mobileNumber) {
        List<VehicleMongo> vehicles = vehicleMongoRepository.findByMobileNumber(mobileNumber);
        List<VehicleNoResponse> vehicleResponses = new ArrayList<>();
        for (VehicleMongo vehicle : vehicles) {
            VehicleNoResponse response = new VehicleNoResponse();
            response.setVehicleNo(vehicle.getVehicleNo());
            vehicleResponses.add(response);
        }
        return vehicleResponses;
    }
}


