package com.jsp.onlinepharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.onlinepharmacy.dto.BookingDto;
import com.jsp.onlinepharmacy.entity.Booking;
import com.jsp.onlinepharmacy.service.BookingService;
import com.jsp.onlinepharmacy.util.ResponseStructure;

import lombok.Getter;

@RestController
@RequestMapping("/booking")
public class BookingController {
	@Autowired
	private BookingService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Booking>> addBooking(@RequestParam int medicineId, @RequestParam int customerId, @RequestBody BookingDto bookingDto){
	return service.addBooking(medicineId, customerId, bookingDto);	
	}
	
//	@GetMapping
//	public ResponseEntity<ResponseStructure<Booking>> findAllBookingHistory(@RequestBody BookingDto bookingDto){
//		return service.findAllBookingHistory(bookingDto);	
//		}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<Booking>> calcelBooking(@RequestParam int bookingId){
		return service.cancelBooking(bookingId);	
		}
	

}
