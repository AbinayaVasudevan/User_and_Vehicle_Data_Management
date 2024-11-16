package com.te.Controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.te.Entity.Vehicle;
import com.te.Entity.VehicleMongo;
import com.te.Entity.VehicleNoResponse;
import com.te.Entity.VehicleResponse;
import com.te.Exception.MobileNumberNotFoundException;
import com.te.Exception.VehicleNotFoundException;
import com.te.Service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
	@Autowired
    private VehicleService vehicleService;
 
    @PostMapping("/fetch-and-store/{mobileNumber}")
    public ResponseEntity<List<VehicleMongo>> fetchAndStoreVehicles(@PathVariable String mobileNumber) {
    	try {
            List<VehicleMongo> savedVehicles = vehicleService.fetchAndStoreVehicles(mobileNumber);
            return new ResponseEntity<>(savedVehicles, HttpStatus.CREATED);
        } catch (HttpClientErrorException.NotFound e) {
            throw new MobileNumberNotFoundException();
        }
    }
    @GetMapping("/mobileNumber/{mobileNumber}")    
    public ResponseEntity<List<VehicleMongo>> getByMobileNumber(@PathVariable String mobileNumber) {        
    	try{
    		List<VehicleMongo> vehicleMongo = vehicleService.getByMobileNumber(mobileNumber);        
    		return ResponseEntity.ok(vehicleMongo);
    	} catch (HttpClientErrorException.NotFound e) {
            throw new MobileNumberNotFoundException();
        }
    	
    }
    @GetMapping("/all")
    public ResponseEntity<List<VehicleMongo>> getAllVehicles() {
        List<VehicleMongo> vehicles = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }
    @PutMapping("/{mobileNumber}")
    public ResponseEntity<String> updateVehicleByMobileNumber(@PathVariable String mobileNumber, @RequestBody VehicleMongo vehicles) {
    	vehicleService.updateVehicleByMobileNumber(mobileNumber, vehicles);
		return ResponseEntity.ok("Vehicle updated successfully");
        
    }
    @DeleteMapping("/{mobileNumber}")
    public String deleteVehicleByMobileNumber(@PathVariable String mobileNumber) {
        boolean deleted = vehicleService.deleteVehicleByMobileNumber(mobileNumber);
        if (deleted) {
            return "Vehicle with mobile number " + mobileNumber + " has been deleted successfully.";
        } else {
            return "Vehicle with mobile number " + mobileNumber + " not found.";
        }
    }
    @GetMapping("/{vehicleNo}")
    public ResponseEntity<VehicleResponse> getVehicleByVehicleNo(@PathVariable String vehicleNo) {
        VehicleMongo vehicle = vehicleService.getVehicleByVehicleNo(vehicleNo);
        if (vehicle == null) {
        	throw new VehicleNotFoundException();
        }

        VehicleResponse response = new VehicleResponse();
        response.setVehicleNo(vehicle.getVehicleNo());
        response.setModel(vehicle.getModel());
        response.setMake(vehicle.getMake());
        response.setColor(vehicle.getColor());
        response.setFuelType(vehicle.getFuelType());
        response.setYearOfManufacturing(vehicle.getYearOfManufacturing());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/getVehicleNo/{mobileNumber}")
    public List<VehicleNoResponse> getVehicleNoByMobileNumber(@PathVariable String mobileNumber) {
        return vehicleService.getOnlyVehicleNoByMobileNumber(mobileNumber);
    }
}
