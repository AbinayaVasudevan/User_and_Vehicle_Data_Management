package com.te.Controller;

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


import com.te.Entity.User;
import com.te.Entity.UserVehicleRequest;
import com.te.Entity.UserVehicleResponse;
import com.te.Entity.Vehicle;
import com.te.Entity.VehicleResponse;
import com.te.Exception.NoSuchElementException;
import com.te.Service.UserService;
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
    private UserService userService;
	
	@PostMapping("/save-user-and-vehicles")
	public ResponseEntity<List<UserVehicleResponse>> saveUserAndVehicles(@RequestBody UserVehicleRequest userVehicleRequest) {
	    List<UserVehicleResponse> savedUserVehicles = userService.saveUserAndVehicles(userVehicleRequest);
	    return new ResponseEntity<>(savedUserVehicles, HttpStatus.CREATED);
	}
    @GetMapping("/{mobileNumber}/vehicles")
    public ResponseEntity<List<Vehicle>> getVehiclesByMobileNumber(@PathVariable String mobileNumber) {
       
        List<Vehicle> vehicles = userService.getVehiclesByMobileNumber(mobileNumber);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<UserVehicleResponse>> getAllUsersWithVehicles() {
        List<UserVehicleResponse> userVehicleResponses = userService.getAllUsersWithVehicles();
        return new ResponseEntity<>(userVehicleResponses, HttpStatus.OK);
    }
    @PutMapping("/update/{mobileNumber}")
    public ResponseEntity<User> updateUserAndVehicles(@PathVariable String mobileNumber, @RequestBody UserVehicleRequest userVehicleRequest) {
        User updatedUser = userService.updateUserAndVehicles(mobileNumber, userVehicleRequest);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/delete/{mobileNumber}")
    public ResponseEntity<String> deleteUserByMobileNumber(@PathVariable String mobileNumber) {
        userService.deleteUserByMobileNumber(mobileNumber);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/mobileNumber/{mobileNumber}")
    public ResponseEntity<List<VehicleResponse>> getOnlyVehiclesByMobileNumber(@PathVariable String mobileNumber) {
        List<VehicleResponse> vehicleResponses = userService.getVehicleResponsesByMobileNumber(mobileNumber);
        if (vehicleResponses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vehicleResponses);
    }

}
