package com.te;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.te.Entity.Vehicle;
import com.te.Entity.VehicleMongo;
import com.te.Repository.VehicleMongoRepository;
import com.te.Service.VehicleService;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class VehMs2ApplicationTests {

	 	@Mock
	    private RestTemplate restTemplate;

	    @Mock
	    private VehicleMongoRepository vehicleMongoRepository;

	    @InjectMocks
	    private VehicleService vehicleService;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.initMocks(this);
	    }

	    @Test
	    public void testFetchAndStoreVehicles_Success() {
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

	        ResponseEntity<List<Vehicle>> responseEntity = ResponseEntity.ok(vehicles);

	        // Mock RestTemplate exchange method
	        when(restTemplate.exchange(
	                eq("http://localhost:8080/api/users/{mobileNumber}/vehicles"),
	                eq(HttpMethod.GET),
	                isNull(),
	                any(ParameterizedTypeReference.class),
	                eq(mobileNumber)
	        )).thenReturn(responseEntity);

	        // Call the method
	        List<VehicleMongo> result = vehicleService.fetchAndStoreVehicles(mobileNumber);

	        // Verify the result
	        assertEquals(vehicles.size(), result.size());
	        VehicleMongo expectedVehicleMongo = mapToVehicleMongo(vehicle1, mobileNumber);
	        assertEquals(expectedVehicleMongo, result.get(0));

	        // Verify repository calls
	        verify(vehicleMongoRepository, times(1)).saveAll(result);
	    }

	    private VehicleMongo mapToVehicleMongo(Vehicle vehicle, String mobileNumber) {
	        VehicleMongo vehicleMongo = new VehicleMongo();
	        vehicleMongo.setVehicleNo(vehicle.getVehicleNo());
	        vehicleMongo.setModel(vehicle.getModel());
	        vehicleMongo.setYearOfManufacturing(vehicle.getYearOfManufacturing());
	        vehicleMongo.setMobileNumber(mobileNumber);
	        vehicleMongo.setMake(vehicle.getMake());
	        vehicleMongo.setColor(vehicle.getColor());
	        vehicleMongo.setFuelType(vehicle.getFuelType());
	        return vehicleMongo;
	    }

}
