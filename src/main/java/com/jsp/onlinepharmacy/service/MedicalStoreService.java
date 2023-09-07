package com.jsp.onlinepharmacy.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.dao.AddressDao;
import com.jsp.onlinepharmacy.dao.AdminDao;
import com.jsp.onlinepharmacy.dao.MedicalStoreDao;
import com.jsp.onlinepharmacy.dto.AddressDto;
import com.jsp.onlinepharmacy.dto.AdminDto;
import com.jsp.onlinepharmacy.dto.MedicalStoreDto;
import com.jsp.onlinepharmacy.entity.Address;
import com.jsp.onlinepharmacy.entity.Admin;
import com.jsp.onlinepharmacy.entity.MedicalStore;
import com.jsp.onlinepharmacy.exception.AddressAlreadymappedToCustomer;
import com.jsp.onlinepharmacy.exception.AddressIdNotFoundException;
import com.jsp.onlinepharmacy.exception.AddressMappedToMedicalStore;
import com.jsp.onlinepharmacy.exception.AdminIdNotFoundException;
import com.jsp.onlinepharmacy.exception.MedicalStoreIdNotFoundException;
import com.jsp.onlinepharmacy.util.ResponseStructure;

@Service
public class MedicalStoreService {
	@Autowired
	private MedicalStoreDao storeDao;
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<ResponseStructure<MedicalStoreDto>> saveMedicalStore(int adminId, int addressId,
			MedicalStoreDto medicalStoreDto) {
		MedicalStore medicalStore=this.mapper.map(medicalStoreDto, MedicalStore.class);
				// this medical is not having admin and address yet so first I need to get the admin
				Admin dbAdmin=adminDao.findAdminById(adminId);
				// checking whether this admin is present or not
				if(dbAdmin!=null) {
					medicalStore.setAdmin(dbAdmin);
					// checking whether address is present or not
				Address	dbAddress=addressDao.findAddressById(addressId);
					if(dbAddress!=null) {
						if(dbAddress.getCustomer()!=null) {
				    		   throw new AddressAlreadymappedToCustomer("Sorry! This address is already mapped to customer, please provide another address");
				    	   }
				    	   if(dbAddress.getMedicalStore()!=null) {
				    		   throw new AddressMappedToMedicalStore("Sorry address mapped to other medical store");
				    	   }
						medicalStore.setAddress(dbAddress);
						
						// update
						dbAddress.setMedicalStore(medicalStore);
						MedicalStore dbMedicalStore=storeDao.saveMedicalStore(medicalStore);
						
						Address dbMedicalStoreAddress= dbMedicalStore.getAddress();
						AddressDto addressDto=this.mapper.map(dbMedicalStoreAddress, AddressDto.class);
						Admin dbAdminMedicalStore=dbMedicalStore.getAdmin();
						
						MedicalStoreDto dto=this.mapper.map(dbMedicalStore, MedicalStoreDto.class);
						dto.setAddressDto(addressDto);
						dto.setAdminDto(this.mapper.map(dbAdminMedicalStore, AdminDto.class));
						
						ResponseStructure<MedicalStoreDto> structure=new ResponseStructure<>();
						structure.setMessage("Medical store has been added successfully");
						structure.setStatus(HttpStatus.CREATED.value());
						structure.setData(dto);
						return new ResponseEntity<ResponseStructure<MedicalStoreDto>> (structure, HttpStatus.CREATED);
						
					}else {
						throw new AddressIdNotFoundException("Sorry! Failed to add Medical store");
					}
					
				}else {
					throw new AdminIdNotFoundException("Sorry! Failed to add Medical store");
				}
				
		
	}

	public ResponseEntity<ResponseStructure<MedicalStoreDto>> updateMedicalStore(int storeId,
			MedicalStore medicalStore) {
		//   medicalstore=name,manager name,phone
		MedicalStore dbMedicalStore=storeDao.updateMedicalStore(storeId, medicalStore);
		if(dbMedicalStore!=null) {
			
			MedicalStoreDto storeDto=this.mapper.map(dbMedicalStore, MedicalStoreDto.class);
			storeDto.setAddressDto(this.mapper.map(dbMedicalStore.getAddress(), AddressDto.class));
			storeDto.setAdminDto(this.mapper.map(dbMedicalStore.getAdmin(), AdminDto.class));
			
			ResponseStructure<MedicalStoreDto> structure= new ResponseStructure<>();
			structure.setMessage("Medical store has been updated successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(storeDto);
			
			return new ResponseEntity<ResponseStructure<MedicalStoreDto>> (structure,HttpStatus.OK);
			
		}else {
			throw new MedicalStoreIdNotFoundException("Sorry! Faild to update Medical Store");
		}
		
	}

	public ResponseEntity<ResponseStructure<MedicalStoreDto>> getMedicalStoreById(int storeId) {
		MedicalStore dbMedicalStore=storeDao.getMedicalStoreById(storeId);
		if(dbMedicalStore!=null) {
			MedicalStoreDto storeDto=this.mapper.map(dbMedicalStore, MedicalStoreDto.class);
			storeDto.setAddressDto(this.mapper.map(dbMedicalStore.getAddress(), AddressDto.class));
			storeDto.setAdminDto(this.mapper.map(dbMedicalStore.getAdmin(), AdminDto.class));
			
			ResponseStructure<MedicalStoreDto> structure= new ResponseStructure<>();
			structure.setMessage("Medical store has been updated successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(storeDto);
			
			return new ResponseEntity<ResponseStructure<MedicalStoreDto>> (structure,HttpStatus.FOUND);
			
		}else {
			throw new MedicalStoreIdNotFoundException("Sorry! Faild to fetch Medical Store");
		}
	}

	public ResponseEntity<ResponseStructure<MedicalStoreDto>> deleteMedicalStoreById(int storeId) {
		// returning medical store entity
		MedicalStore dbMedicalStore=storeDao.deleteMedicalStoreById(storeId);
		if (dbMedicalStore!=null) {
			
			MedicalStoreDto storeDto=this.mapper.map(dbMedicalStore, MedicalStoreDto.class);
			storeDto.setAddressDto(this.mapper.map(dbMedicalStore.getAddress(), AddressDto.class));
			storeDto.setAdminDto(this.mapper.map(dbMedicalStore.getAdmin(), AdminDto.class));
//			
			ResponseStructure<MedicalStoreDto> structure= new ResponseStructure<>();
			structure.setMessage("Medical store has been deleted");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(storeDto);
			
			return new ResponseEntity<ResponseStructure<MedicalStoreDto>> (structure, HttpStatus.GONE);
		}else {
			throw new MedicalStoreIdNotFoundException("Sorry! Faild to delete Medical Store");
		}
		
	}
	

}
