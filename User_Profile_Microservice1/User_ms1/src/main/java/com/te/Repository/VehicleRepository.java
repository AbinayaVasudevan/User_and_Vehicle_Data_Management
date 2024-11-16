package com.te.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.Entity.Vehicle;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,String> {

	List<Vehicle> findByUserId(Long id);

	List<Vehicle> findByUserMobileNumber(String mobileNumber);

	boolean existsByVehicleNo(String vehicleNo);

	

	

}
