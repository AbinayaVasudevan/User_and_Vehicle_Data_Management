package com.te.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.te.Entity.Vehicle;
import com.te.Entity.VehicleMongo;

@Repository
public interface VehicleMongoRepository extends MongoRepository<VehicleMongo, String> {

	List<VehicleMongo> findByMobileNumber(String mobileNumber);

	VehicleMongo findByVehicleNo(String vehicleNo);

	String findVehicleNoByMobileNumber(String mobileNumber);


	


}
