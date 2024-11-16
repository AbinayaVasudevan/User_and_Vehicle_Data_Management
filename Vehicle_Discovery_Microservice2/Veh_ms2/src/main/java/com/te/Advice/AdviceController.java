package com.te.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.te.Exception.MobileNumberNotFoundException;
import com.te.Exception.VehicleNotFoundException;


@ControllerAdvice
public class AdviceController {
	@ExceptionHandler(MobileNumberNotFoundException.class)
    public ResponseEntity mobileNumberNotFoundException(
            final MobileNumberNotFoundException mobileNumberNotFoundException) {
        return new ResponseEntity(
        		"No vehicles found for the given mobile number", HttpStatus.NOT_FOUND);
    }
	@ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity vehicleNotFoundException(
            final VehicleNotFoundException vehicleNotFoundException) {
        return new ResponseEntity(
        		"Given vehicleNo is not found", HttpStatus.NOT_FOUND);
    }

}
