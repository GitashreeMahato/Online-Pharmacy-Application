package com.jsp.onlinepharmacy.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.dao.AddressDao;
import com.jsp.onlinepharmacy.dao.CustomerDao;
import com.jsp.onlinepharmacy.dto.AddressDto;
import com.jsp.onlinepharmacy.dto.CustomerDto;
import com.jsp.onlinepharmacy.entity.Address;
import com.jsp.onlinepharmacy.entity.Customer;
import com.jsp.onlinepharmacy.exception.AddressAlreadymappedToCustomer;
import com.jsp.onlinepharmacy.exception.AddressIdNotFoundException;
import com.jsp.onlinepharmacy.exception.AddressMappedToMedicalStore;
import com.jsp.onlinepharmacy.exception.CustomerIdNotFoundException;
import com.jsp.onlinepharmacy.util.ResponseStructure;


@Service
public class CustomerService {
	@Autowired
	private CustomerDao dao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<ResponseStructure<CustomerDto>> addCustomer(int addressId, Customer customer) {
		Address dbAddress=addressDao.findAddressById(addressId);
		//   when addressId =100 your db address==null
//		dbAddress==is having id name also ereltionship entity that is your customer id=5
		
		
		//checking address Id is present or not
		if(dbAddress!=null) {
			if(dbAddress.getMedicalStore()!=null) {
				throw new AddressMappedToMedicalStore("Sorry! this address is mapped to medical store");
			}
			if(dbAddress.getCustomer()!=null) {
				throw new AddressAlreadymappedToCustomer("Sorry! this address is already mapped to customer");
			}
			dbAddress.setCustomer(customer);
			
			// yes address Id is present
			List<Address> listAddress= new ArrayList<>();
			listAddress.add(dbAddress);
		
	//   customer have only own attributes not a relationship attribute
			customer.setAddresses(listAddress);

//			now customer is having list of address also
		Customer	dbCustomer=dao.addCustomer(customer);  // saved the customer
		
		// dbcustomer is having its own attributes then relationship attributes that is list of addeess and list of bookings but it is null
		//but list of address is present
		//copy customer to customer dto
//		but this customer dto is having listOfAddressDto and ListOfBooking dto but still it is null
//		copy list of address to addressdto
		
		CustomerDto customerDto=this.mapper.map(dbCustomer, CustomerDto.class);
		List<AddressDto> addressDtos;  //initialize the list of addressdtos
		
		for(Address address:dbCustomer.getAddresses()) {
			AddressDto addressDto=this.mapper.map(address, AddressDto.class);
			addressDtos=new ArrayList<AddressDto>();
			addressDtos.add(addressDto);
			customerDto.setAddresses(addressDtos);
		}
		
		customerDto.setBookingsDto(null);
		
		ResponseStructure<CustomerDto> structure=new ResponseStructure<>();
		structure.setMessage("Customer added successfully");
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setData(customerDto);
		
		return new ResponseEntity<ResponseStructure<CustomerDto>> (structure, HttpStatus.CREATED);
		
		}else {
			throw new AddressIdNotFoundException("Sorry!! Failed to add Customer");
		}
		
	}

	public ResponseEntity<ResponseStructure<CustomerDto>> updateCustomer(int customerId, Customer customer) {
		Customer dbCustomer=dao.updateCustomer(customerId,customer);
		if(dbCustomer!=null) {
			CustomerDto customerDto=this.mapper.map(dbCustomer, CustomerDto.class);
			List<AddressDto> addressDtos;
			
			for(Address address: dbCustomer.getAddresses()) {
				AddressDto addressDto=this.mapper.map(address, AddressDto.class);
				addressDtos=new ArrayList<AddressDto>();
				addressDtos.add(addressDto);
				customerDto.setAddresses(addressDtos);
			} 
				customerDto.setBookingsDto(null);
				
				ResponseStructure<CustomerDto> structure=new ResponseStructure<CustomerDto>();
				structure.setMessage("CustometrAddedsuccessfully");
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setData(customerDto);
				return new ResponseEntity<ResponseStructure<CustomerDto>>(structure,HttpStatus.CREATED);
			}else {
				throw new CustomerIdNotFoundException("Sorry failed to update Customer");
			}
			}
		
		
	

	public ResponseEntity<ResponseStructure<CustomerDto>> findCustomerById(int customerId) {
		
		Customer dbCustomer=dao.findCustomerById(customerId);
		if(dbCustomer!=null) {
			CustomerDto customerDto=this.mapper.map(dbCustomer, CustomerDto.class);
			List<AddressDto> addressDtos;
		for(Address address:dbCustomer.getAddresses()) {
			AddressDto addressDto=this.mapper.map(address, AddressDto.class);
			addressDtos=new ArrayList<AddressDto>();
			addressDtos.add(addressDto);
			customerDto.setAddresses(addressDtos);
		} 
			customerDto.setBookingsDto(null);
			ResponseStructure<CustomerDto> structure=new ResponseStructure<CustomerDto>();
			structure.setMessage("Customer data fetched successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(customerDto);
			return new ResponseEntity<ResponseStructure<CustomerDto>>(structure,HttpStatus.FOUND);
		}else {
			throw new CustomerIdNotFoundException("Sorry failed to fetch Customer");
		}
	}

	public ResponseEntity<ResponseStructure<CustomerDto>> deleteCustomerById(int customerId) {
		
		Customer dbCustomer=dao.deleteCustomerById(customerId);
		if(dbCustomer!=null) {
			CustomerDto customerDto=this.mapper.map(dbCustomer, CustomerDto.class);
			List<AddressDto> addressDtos;
		for(Address address:dbCustomer.getAddresses()) {
			AddressDto addressDto=this.mapper.map(address, AddressDto.class);
			addressDtos=new ArrayList<AddressDto>();
			addressDtos.add(addressDto);
			customerDto.setAddresses(addressDtos);
		} 
			customerDto.setBookingsDto(null);
			ResponseStructure<CustomerDto> structure=new ResponseStructure<CustomerDto>();
			structure.setMessage("Customer data deleted successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(customerDto);
			return new ResponseEntity<ResponseStructure<CustomerDto>>(structure,HttpStatus.GONE);
		}else {
			throw new CustomerIdNotFoundException("Sorry failed to delete Customer");
		}

}

	public ResponseEntity<ResponseStructure<CustomerDto>> loginCustomer(String email, String password) {
		Customer dbCustomer=dao.findCustomerByEmail(email);
		return null;
	}
}
