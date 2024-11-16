package com.te;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.te.Entity.User;
import com.te.Entity.UserVehicleRequest;
import com.te.Entity.UserVehicleResponse;
import com.te.Entity.Vehicle;
import com.te.Entity.VehicleResponse;
import com.te.Exception.NoSuchElementException;
import com.te.Repository.UserRepository;
import com.te.Repository.VehicleRepository;
import com.te.Service.UserService;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UserMs1ApplicationTests {

	@Mock
    private UserRepository userRepository;
	
	@Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private UserService userService;
  
    
    @SuppressWarnings("deprecation")
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsersWithVehicles() {
        // Mocking UserRepository's behavior
        User user1 = new User();
        User user2 = new User();

        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();

        user1.setVehicles(Arrays.asList(vehicle1));
        user2.setVehicles(Arrays.asList(vehicle2));

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Calling the service method
        List<UserVehicleResponse> userVehicleResponses = userService.getAllUsersWithVehicles();

        // Assertions
        assertEquals(2, userVehicleResponses.size());

        UserVehicleResponse response1 = userVehicleResponses.get(0);
        assertEquals(user1, response1.getUser());
        assertEquals(vehicle1.getVehicleNo(), response1.getVehicleNo());
        assertEquals(vehicle1.getModel(), response1.getModel());
        assertEquals(vehicle1.getYearOfManufacturing(), response1.getYearOfManufacturing());
        assertEquals(vehicle1.getColor(), response1.getColor());
        assertEquals(vehicle1.getMake(), response1.getMake());
        assertEquals(vehicle1.getFuelType(), response1.getFuelType());

        UserVehicleResponse response2 = userVehicleResponses.get(1);
        assertEquals(user2, response2.getUser());
        assertEquals(vehicle2.getVehicleNo(), response2.getVehicleNo());
        assertEquals(vehicle2.getModel(), response2.getModel());
        assertEquals(vehicle2.getYearOfManufacturing(), response2.getYearOfManufacturing());
        assertEquals(vehicle2.getColor(), response2.getColor());
        assertEquals(vehicle2.getMake(), response2.getMake());
        assertEquals(vehicle2.getFuelType(), response2.getFuelType());
    }
    @Test
    public void testSaveUserAndVehicles_ValidInput() {
        User user = new User( (long) 1, "John", "1234567890");
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("123", user, "Toyota", 2015, "Camry", "Red", "Petrol"));
        UserVehicleRequest request = new UserVehicleRequest(user, vehicles);

        when(userRepository.existsByMobileNumberAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(vehicleRepository.existsByVehicleNo(anyString())).thenReturn(false);

        List<UserVehicleResponse> responses = userService.saveUserAndVehicles(request);

        assertNotNull(responses);
        assertEquals(vehicles.size(), responses.size());
        // Add more assertions as needed
    }
    
	@Test
    public void testDeleteUserByMobileNumber() {
        // Mocking UserRepository's behavior
        User user = new User((long) 1, "John", "1234567890");
        user.setId(1L);

        when(userRepository.findByMobileNumber("1234567890")).thenReturn(Optional.of(user));

        // Mocking VehicleRepository's behavior
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("ABC123", user, "Toyota",  2015, "Camry","Red", "Petrol"));
        vehicles.add(new Vehicle("XYZ789", user, "Honda",  2018,"Civic", "Blue", "Diesel"));

        when(vehicleRepository.findByUserId(1L)).thenReturn(vehicles);

        // Calling the service method
        userService.deleteUserByMobileNumber("1234567890");

        // Verifying interactions
        verify(vehicleRepository, times(1)).findByUserId(1L);
        verify(vehicleRepository, times(1)).deleteAll(vehicles);
        verify(userRepository, times(1)).delete(user);
    }
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateUserAndVehicles_Success() {
        // Mock data
        String mobileNumber = "1234567890";
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("John Doe");
        existingUser.setVehicles(new ArrayList<>());
        
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Jane Smith");

        List<Vehicle> updatedVehicles = new ArrayList<>();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setVehicleNo("ABC123");
        updatedVehicles.add(vehicle1);

        // Mock repository methods
        when(userRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(existingUser));
        when(vehicleRepository.findByUserId(existingUser.getId())).thenReturn(updatedVehicles);

        // Create request object
        UserVehicleRequest userVehicleRequest = new UserVehicleRequest(updatedUser, updatedVehicles);
        userVehicleRequest.setUser(updatedUser);
        userVehicleRequest.setVehicles(updatedVehicles);

        // Call the method
        User result = userService.updateUserAndVehicles(mobileNumber, userVehicleRequest);

        // Verify the changes
        assertEquals(updatedUser.getName(), existingUser.getName());
        assertEquals(updatedVehicles.size(), existingUser.getVehicles().size());
        for (Vehicle vehicle : existingUser.getVehicles()) {
            assertEquals(existingUser, vehicle.getUser());
        }

        // Verify repository calls
        verify(userRepository, times(1)).findByMobileNumber(mobileNumber);
        verify(vehicleRepository, times(1)).findByUserId(existingUser.getId());
        verify(userRepository, times(1)).save(existingUser);
        verify(vehicleRepository, times(1)).saveAll(updatedVehicles);

        // Verify the result
        assertNotNull(result);
        assertEquals(existingUser, result);
    }
    @Test
    public void testGetVehiclesByMobileNumber_Success() {
        // Mock data
        String mobileNumber = "1234567890";
        User user = new User();
        user.setId(1L);

        List<Vehicle> expectedVehicles = new ArrayList<>();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setVehicleNo("ABC123");
        expectedVehicles.add(vehicle1);

        // Mock repository methods
        when(userRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(user));
        when(vehicleRepository.findByUserId(user.getId())).thenReturn(expectedVehicles);

        // Call the method
        List<Vehicle> result = userService.getVehiclesByMobileNumber(mobileNumber);

        // Verify the result
        assertEquals(expectedVehicles, result);

        // Verify repository calls
        verify(userRepository, times(1)).findByMobileNumber(mobileNumber);
        verify(vehicleRepository, times(1)).findByUserId(user.getId());
    }
    @Test
    public void testGetVehicleResponsesByMobileNumber_Success() {
        // Mock data
        String mobileNumber = "1234567890";
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setVehicleNo("ABC123");
        vehicle1.setModel("Model1");
        vehicle1.setYearOfManufacturing(2020);
        vehicle1.setMake("Make1");
        vehicle1.setColor("Red");
        vehicle1.setFuelType("Petrol");
        vehicles.add(vehicle1);

        // Mock repository method
        when(vehicleRepository.findByUserMobileNumber(mobileNumber)).thenReturn(vehicles);

        // Call the method
        List<VehicleResponse> result = userService.getVehicleResponsesByMobileNumber(mobileNumber);

        // Verify the result
        assertEquals(vehicles.size(), result.size());
        VehicleResponse expectedResponse = mapToVehicleResponse(vehicle1);
        assertEquals(expectedResponse, result.get(0));

        // Verify repository calls
        verify(vehicleRepository, times(1)).findByUserMobileNumber(mobileNumber);
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
