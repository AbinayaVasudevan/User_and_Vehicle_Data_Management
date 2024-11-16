package com.te.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

@ControllerAdvice
public class AdviceController {
	
	@ExceptionHandler(EmptyInputException.class)
    public ResponseEntity emptyInputException(
            final EmptyInputException emptyInputException) {
        return new ResponseEntity(
        		"kindly provide all details", HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(InvalidYearOfManufacturingException.class)
    public ResponseEntity invalidYearOfManufacturingException(
            final InvalidYearOfManufacturingException invalidYearOfManufacturingException) {
        return new ResponseEntity(
        		"Year of manufacturing must be greater than 0", HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(InvalidMobileNumberException.class)
    public ResponseEntity invalidMobileNumberException(
            final InvalidMobileNumberException invalidMobileNumberException) {
        return new ResponseEntity(
        		"Mobile number must be exactly 10 digits long", HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(InvalidMobileNumberDigitException.class)
    public ResponseEntity invalidMobileNumberDigitException(
            final InvalidMobileNumberDigitException invalidMobileNumberDigitException) {
        return new ResponseEntity(
        		"Mobile number must contain only numeric digits", HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(InvalidNameException.class)
    public ResponseEntity invalidNameException(
            final InvalidNameException invalidNameException) {
        return new ResponseEntity(
        		"Name must contain only alphabetic characters", HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(InvalidYearOfManufacturingTypeException.class)
    public ResponseEntity invalidYearOfManufacturingTypeException(
            final InvalidYearOfManufacturingTypeException invalidYearOfManufacturingTypeException) {
        return new ResponseEntity(
        		"Year of manufacturing must be provided as an integer", HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity noSuchElementException(
            final NoSuchElementException noSuchElementException) {
        return new ResponseEntity(
        		"No vehicles found for the given mobile number", HttpStatus.NOT_FOUND);
    }
	@ExceptionHandler(DuplicateMobileNumberException.class)
    public ResponseEntity duplicateMobileNumberException(
            final DuplicateMobileNumberException duplicateMobileNumberException) {
        return new ResponseEntity(
        		"Mobile number is already associated with another user", HttpStatus.NOT_FOUND);
    }
	@ExceptionHandler(DuplicateVehicleNoException.class)
    public ResponseEntity duplicateVehicleNoException(
            final DuplicateVehicleNoException duplicateVehicleNoException) {
        return new ResponseEntity(
        		"Vehicle number already exists", HttpStatus.NOT_FOUND);
    }
	@ExceptionHandler(InvalidUpdateException.class)
    public ResponseEntity invalidUpdateException(
            final InvalidUpdateException invalidUpdateException) {
        return new ResponseEntity(
        		"User ID and VehicleNo cannot be changed", HttpStatus.NOT_FOUND);
    }

}
