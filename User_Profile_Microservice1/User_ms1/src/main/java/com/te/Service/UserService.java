package com.te.Service;




import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.te.Entity.User;
import com.te.Entity.UserVehicleRequest;
import com.te.Entity.UserVehicleResponse;
import com.te.Entity.Vehicle;
import com.te.Entity.VehicleResponse;
import com.te.Exception.DuplicateMobileNumberException;
import com.te.Exception.DuplicateVehicleNoException;
import com.te.Exception.EmptyInputException;
import com.te.Exception.InvalidMobileNumberDigitException;
import com.te.Exception.InvalidMobileNumberException;
import com.te.Exception.InvalidNameException;
import com.te.Exception.InvalidUpdateException;
import com.te.Exception.InvalidYearOfManufacturingException;
import com.te.Exception.InvalidYearOfManufacturingTypeException;
import com.te.Exception.NoSuchElementException;
import com.te.Repository.UserRepository;
import com.te.Repository.VehicleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
    private UserRepository userRepository;
 
    @Autowired
    private VehicleRepository vehicleRepository;
 
    @Transactional
    public List<UserVehicleResponse> saveUserAndVehicles(UserVehicleRequest userVehicleRequest) {
    	if (userVehicleRequest == null || userVehicleRequest.getUser() == null || userVehicleRequest.getVehicles() == null) {
            throw new EmptyInputException();
        }
    	User user = userVehicleRequest.getUser();
        List<Vehicle> vehicles = userVehicleRequest.getVehicles();
        
        if(userRepository.existsByMobileNumberAndIdNot(user.getMobileNumber(), user.getId())) {            
        throw new DuplicateMobileNumberException();         
        }
        
        if (user.getName() == null || user.getName().isEmpty() || user.getMobileNumber() == null || user.getMobileNumber().isEmpty()) {
            throw new EmptyInputException();
        }
        if (!user.getName().matches("[a-zA-Z]+")) {
            throw new InvalidNameException();
        }
        if (!user.getMobileNumber().matches("[0-9]+")) {
            throw new InvalidMobileNumberDigitException();
        }
        if (user.getMobileNumber().length() != 10) {
            throw new InvalidMobileNumberException();
        }

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getModel() == null || vehicle.getModel().isEmpty()) {
                throw new EmptyInputException();
            }
            if (vehicle.getMake() == null || vehicle.getMake().isEmpty()) {
                throw new EmptyInputException();
            }
            if (vehicle.getColor() == null || vehicle.getColor().isEmpty()) {
                throw new EmptyInputException();
            }
            if (vehicle.getFuelType() == null || vehicle.getFuelType().isEmpty()) {
                throw new EmptyInputException();
            }
            Integer yearOfManufacturing = vehicle.getYearOfManufacturing();
            if (yearOfManufacturing == null) {
                throw new InvalidYearOfManufacturingTypeException();
            }
            if (vehicle.getYearOfManufacturing() <= 0) {
                throw new InvalidYearOfManufacturingException();
            }
            if (vehicleRepository.existsByVehicleNo(vehicle.getVehicleNo())) {
                throw new DuplicateVehicleNoException();
            }
        }

        userRepository.save(user);

        for (Vehicle vehicle : vehicles) {
            vehicle.setUser(user);
        }

        vehicleRepository.saveAll(vehicles);

        List<UserVehicleResponse> userVehicleResponses = new LinkedList<>();

        for (Vehicle vehicle : vehicles) {
            UserVehicleResponse response = new UserVehicleResponse();
            response.setVehicleNo(vehicle.getVehicleNo());
            response.setUser(user);
            response.setModel(vehicle.getModel());
            response.setYearOfManufacturing(vehicle.getYearOfManufacturing());
            response.setColor(vehicle.getColor());
            response.setMake(vehicle.getMake());
            response.setFuelType(vehicle.getFuelType());
            userVehicleResponses.add(response);
        }

        return userVehicleResponses;
    }

	public List<Vehicle> getVehiclesByMobileNumber(String mobileNumber) {
		User user = userRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new NoSuchElementException());
        return vehicleRepository.findByUserId(user.getId());
	}
	public List<UserVehicleResponse> getAllUsersWithVehicles() {
        List<User> users = userRepository.findAll();
        List<UserVehicleResponse> userVehicleResponses = new LinkedList<>();

        for (User user : users) {
            List<Vehicle> vehicles = user.getVehicles();

            for (Vehicle vehicle : vehicles) {
                UserVehicleResponse response = new UserVehicleResponse();
                response.setVehicleNo(vehicle.getVehicleNo());
                response.setUser(user);
                response.setModel(vehicle.getModel());
                response.setYearOfManufacturing(vehicle.getYearOfManufacturing());
                response.setColor(vehicle.getColor());
                response.setMake(vehicle.getMake());
                response.setFuelType(vehicle.getFuelType());
                userVehicleResponses.add(response);
            }
        }

        return userVehicleResponses;
    }
	public User getUserByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new EntityNotFoundException("User not found with mobile number: " + mobileNumber));
    }
 
    @Transactional
    public User updateUserAndVehicles(String mobileNumber, UserVehicleRequest userVehicleRequest) {
        User existingUser = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new NoSuchElementException());
 
        User updatedUser = userVehicleRequest.getUser();
        if (!existingUser.getId().equals(updatedUser.getId())) {
            throw new InvalidUpdateException();
        }
        existingUser.setName(updatedUser.getName());
 
        List<Vehicle> updatedVehicles = userVehicleRequest.getVehicles();
        List<Vehicle> existingVehicles = vehicleRepository.findByUserId(existingUser.getId());
 
        for (Vehicle updatedVehicle : updatedVehicles) {
            for (Vehicle existingVehicle : existingVehicles) {
//                if (!existingVehicle.getVehicleNo().equals(updatedVehicle.getVehicleNo())) {
//                    throw new InvalidUpdateException();
//                }
            }
        }

        existingVehicles.forEach(vehicle -> vehicle.setUser(null));
        existingVehicles.clear();
 
        for (Vehicle vehicle : updatedVehicles) {
            vehicle.setUser(existingUser);
        }
        
 
        userRepository.save(existingUser);
        vehicleRepository.saveAll(updatedVehicles);
        return existingUser;
    }
    public void deleteUserByMobileNumber(String mobileNumber) {
        User user = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new NoSuchElementException());
 
        List<Vehicle> vehicles = vehicleRepository.findByUserId(user.getId());
        vehicleRepository.deleteAll(vehicles);
 
        userRepository.delete(user);
    }
    public List<VehicleResponse> getVehicleResponsesByMobileNumber(String mobileNumber) {
        List<Vehicle> vehicles = vehicleRepository.findByUserMobileNumber(mobileNumber);
        return vehicles.stream()
                       .map(this::mapToVehicleResponse)
                       .collect(Collectors.toList());
    }
    
    private VehicleResponse mapToVehicleResponse(Vehicle vehicle) {
        VehicleResponse response = new VehicleResponse();
        response.setVehicleNo(vehicle.getVehicleNo());
        response.setModel(vehicle.getModel());
        response.setYearOfManufacturing(vehicle.getYearOfManufacturing());
        response.setMake(vehicle.getMake());
        response.setColor(vehicle.getColor());
        response.setFuelType(vehicle.getFuelType());
        return response;
    }

}
