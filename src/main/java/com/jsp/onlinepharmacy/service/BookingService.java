package com.jsp.onlinepharmacy.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.dao.BookingDao;
import com.jsp.onlinepharmacy.dao.CustomerDao;
import com.jsp.onlinepharmacy.dao.MedicineDao;
import com.jsp.onlinepharmacy.dto.BookingDto;
import com.jsp.onlinepharmacy.entity.Booking;
import com.jsp.onlinepharmacy.entity.Customer;
import com.jsp.onlinepharmacy.entity.Medicine;
import com.jsp.onlinepharmacy.enums.BookingStatus;
import com.jsp.onlinepharmacy.exception.BookingAlreadyCancelledException;
import com.jsp.onlinepharmacy.exception.BookingCantCancelledNow;
import com.jsp.onlinepharmacy.exception.BookingDeliveredException;
import com.jsp.onlinepharmacy.exception.BookingIdNotFoundException;
import com.jsp.onlinepharmacy.exception.CustomerIdNotFoundException;
import com.jsp.onlinepharmacy.exception.MedicineIdNotFoundException;
import com.jsp.onlinepharmacy.util.ResponseStructure;



@Service
public class BookingService {
	@Autowired
	private BookingDao dao;
	
	@Autowired
	private MedicineDao medicineDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private ModelMapper mapper;
	

	public ResponseEntity<ResponseStructure<Booking>> addBooking(int medicineId, int customerId, BookingDto bookingDto) {
		Booking booking=this.mapper.map(bookingDto, Booking.class);
		Customer dbCustomer=customerDao.findCustomerById(customerId);
		if(dbCustomer!=null) {
			//customer is present
			Medicine dbMedicine= medicineDao.getMedicineById(medicineId);
			if(dbMedicine!=null) {
				//medicine id present
				List<Medicine> listOfMedicine = new ArrayList<Medicine>();
				listOfMedicine.add(dbMedicine);
				booking.setCustomer(dbCustomer);
				booking.setMedicines(listOfMedicine);
				
				//update customer also
				List<Booking> listOfBooking = new ArrayList<Booking>();
				listOfBooking.add(booking);
				dbCustomer.setBookings(listOfBooking);
				customerDao.updateCustomer(customerId, dbCustomer);
				
				//updating stock quantity
				dbMedicine.setStockQuantity(dbMedicine.getStockQuantity()-booking.getQuantity());
				
				//adding booking status
				booking.setBookingStatus(BookingStatus.ACTIVE);
				
				Booking dbBooking = dao.addBooking(booking);
				
				
				ResponseStructure<Booking> structure = new ResponseStructure<Booking>();
				structure.setMessage("Booking added successfully");
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setData(dbBooking);
				return new ResponseEntity<ResponseStructure<Booking>>(structure, HttpStatus.CREATED);

			} else {
				throw new MedicineIdNotFoundException("Sorry failed to add booking");
			}

		} else {
			throw new CustomerIdNotFoundException("Sorry failed to add booking");
		}
	}
	
	public ResponseEntity<ResponseStructure<Booking>> cancelBooking(int bookingId){
		Booking dbBooking=dao.findBookingById(bookingId);
		
		
		LocalDate cantcancelledday=dbBooking.getExpectedDate().minusDays(2);
//		Expected date=24
//		cantcancelleddate=24-2=22;
		
		if(LocalDate.now().equals(cantcancelledday)||LocalDate.now().isAfter(cantcancelledday)) {
			throw new BookingCantCancelledNow("Sorry booking cant cancelled Now");
		}
		if(dbBooking!=null) {
           if(dbBooking.getBookingStatus().equals(BookingStatus.CANCELLED)) {
        	   throw new BookingAlreadyCancelledException("sorry this booking already Cancelled");
           }else if(dbBooking.getBookingStatus().equals(BookingStatus.DELIVERED)) {
        	   throw new BookingDeliveredException("Sorry cant cancel Booking its already delivered");
           }else {
        	   Booking cancelledBooking=dao.cancelBooking(bookingId);
        	   ResponseStructure<Booking> structure=new ResponseStructure<Booking>();
        	   structure.setMessage("Booking cancelled Successfully");
        	   structure.setStatus(HttpStatus.OK.value());
        	   structure.setData(cancelledBooking);
               return new ResponseEntity<ResponseStructure<Booking>>(structure,HttpStatus.OK);       	   
           }
	
		}else {
			throw new BookingIdNotFoundException("Sorry failed to cancel booking");
		}
		
	}
}

//	public ResponseEntity<ResponseStructure<Booking>> findAllBookingHistory(BookingDto bookingDto) {
//		
//		return null;
//	}



