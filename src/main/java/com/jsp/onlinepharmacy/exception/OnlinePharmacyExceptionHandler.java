package com.jsp.onlinepharmacy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.jsp.onlinepharmacy.util.ResponseStructure;
@RestControllerAdvice
public class OnlinePharmacyExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> addressIdNotFoundException(AddressIdNotFoundException ex){
		ResponseStructure<String> structure=new ResponseStructure<>();
		structure.setMessage("Address id not found");
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setData(ex.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> adminIdNotFoundException(AdminIdNotFoundException ex){
		ResponseStructure<String> structure= new ResponseStructure<>();
		structure.setMessage("Admin id is not found");
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setData(ex.getMessage());
		return new ResponseEntity<ResponseStructure<String>> (structure, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> staffIdNotFoundException(StaffIdNotFoundException ex){
		ResponseStructure<String> structure= new ResponseStructure<>();
		structure.setMessage("Staff id is not found");
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setData(ex.getMessage());
		return new ResponseEntity<ResponseStructure<String>> (structure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> addressAlreadyMapped(AddressAlreadymappedToCustomer  ex){
		 ResponseStructure<String> structure=new  ResponseStructure<String>();
		 structure.setMessage("Address is already mapped to customer");
		 structure.setStatus(HttpStatus.NOT_FOUND.value());
		 structure.setData(ex.getMessage());
		 return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> addressMappedToMedicalStore(AddressMappedToMedicalStore  ex){
		 ResponseStructure<String> structure=new  ResponseStructure<String>();
		 structure.setMessage("Address is mapped to medical store");
		 structure.setStatus(HttpStatus.NOT_FOUND.value());
		 structure.setData(ex.getMessage());
		 return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> medicalStoreIdNotFoundException(MedicalStoreIdNotFoundException  ex){
		 ResponseStructure<String> structure=new  ResponseStructure<String>();
		 structure.setMessage("Medicine is not available to medical store");
		 structure.setStatus(HttpStatus.NOT_FOUND.value());
		 structure.setData(ex.getMessage());
		 return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> medicineIdNotFoundException(MedicineIdNotFoundException  ex){
		 ResponseStructure<String> structure=new  ResponseStructure<String>();
		 structure.setMessage("Medicine is not available to medical store");
		 structure.setStatus(HttpStatus.NOT_FOUND.value());
		 structure.setData(ex.getMessage());
		 return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> customerIdNotFoundException(CustomerIdNotFoundException  ex){
		 ResponseStructure<String> structure=new  ResponseStructure<String>();
		 structure.setMessage("Customer Id Not Found");
		 structure.setStatus(HttpStatus.NOT_FOUND.value());
		 structure.setData(ex.getMessage());
		 return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> bookingAlreadyCancelledException(BookingAlreadyCancelledException  ex){
		 ResponseStructure<String> structure=new  ResponseStructure<String>();
		 structure.setMessage("Booking Already cancelled");
		 structure.setStatus(HttpStatus.NOT_FOUND.value());
		 structure.setData(ex.getMessage());
		 return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> bookingDeliveredException(BookingDeliveredException  ex){
		 ResponseStructure<String> structure=new  ResponseStructure<String>();
		 structure.setMessage("Booking Already delievered");
		 structure.setStatus(HttpStatus.NOT_FOUND.value());
		 structure.setData(ex.getMessage());
		 return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> bookingCantCancelNowException(BookingCantCancelledNow  ex){
		 ResponseStructure<String> structure=new  ResponseStructure<String>();
		 structure.setMessage("Booking Cant cancelled now because expected date is near");
		 structure.setStatus(HttpStatus.NOT_FOUND.value());
		 structure.setData(ex.getMessage());
		 return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> bookingIdNotFoundException(BookingIdNotFoundException  ex){
		 ResponseStructure<String> structure=new  ResponseStructure<String>();
		 structure.setMessage("Booking Id Not Found");
		 structure.setStatus(HttpStatus.NOT_FOUND.value());
		 structure.setData(ex.getMessage());
		 return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}
}

