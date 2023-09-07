package com.jsp.onlinepharmacy.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.dao.AdminDao;
import com.jsp.onlinepharmacy.dao.MedicalStoreDao;
import com.jsp.onlinepharmacy.dao.StaffDao;
import com.jsp.onlinepharmacy.dto.AdminDto;
import com.jsp.onlinepharmacy.dto.MedicalStoreDto;
import com.jsp.onlinepharmacy.dto.StaffDto;
import com.jsp.onlinepharmacy.entity.Admin;
import com.jsp.onlinepharmacy.entity.MedicalStore;
import com.jsp.onlinepharmacy.entity.Staff;
import com.jsp.onlinepharmacy.exception.AdminIdNotFoundException;
import com.jsp.onlinepharmacy.exception.MedicalStoreIdNotFoundException;
import com.jsp.onlinepharmacy.exception.StaffIdNotFoundException;
import com.jsp.onlinepharmacy.util.ResponseStructure;

@Service
public class StaffService {
	@Autowired
	private StaffDao staffDao;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private MedicalStoreDao medicalStoreDao;
	
	@Autowired
	private AdminDao adminDao;

	public ResponseEntity<ResponseStructure<StaffDto>> addStaff(int adminId, int storeId, Staff staff) {
		
		MedicalStore dbMedicalStore=medicalStoreDao.getMedicalStoreById(storeId);
		if(dbMedicalStore!=null) {
			staff.setMedicalStore(dbMedicalStore);
			Admin dbAdmin= adminDao.findAdminById(adminId);
			if(dbAdmin!=null) {
				staff.setAdmin(dbAdmin);
				Staff dbStaff=staffDao.addStaff(staff);
				StaffDto dbStaffDto=this.mapper.map(dbStaff, StaffDto.class);
				dbStaffDto.setAdminDto(this.mapper.map(dbStaff, AdminDto.class));
				dbStaffDto.setMedicalStoreDto(this.mapper.map(dbStaff, MedicalStoreDto.class));
				
				ResponseStructure<StaffDto> structure= new ResponseStructure<>();
				structure.setMessage("Staff added successfully");
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setData(dbStaffDto);
				
				return new ResponseEntity<ResponseStructure<StaffDto>> (structure, HttpStatus.CREATED);
			}else {
				throw new AdminIdNotFoundException("Sorry! failed to add Staff");
				
			}
		}else {
			throw new MedicalStoreIdNotFoundException("Sorry! failed to add Staff");
			
		}
		
	}

	public ResponseEntity<ResponseStructure<StaffDto>> updateStaff(int staffId, Staff staff) {
		Staff dbStaff=staffDao.updateStaff(staffId,staff);
		if(dbStaff!=null) {
			
			
			StaffDto dbStaffDto=this.mapper.map(dbStaff, StaffDto.class);
			dbStaffDto.setAdminDto(this.mapper.map(dbStaff, AdminDto.class));
			dbStaffDto.setMedicalStoreDto(this.mapper.map(dbStaff, MedicalStoreDto.class));
			
			ResponseStructure<StaffDto> structure= new ResponseStructure<>();
			structure.setMessage("Staff added successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(dbStaffDto);
			
			return new ResponseEntity<ResponseStructure<StaffDto>> (structure, HttpStatus.OK);
			
		}else {
			
		}
		
		throw new StaffIdNotFoundException("Sorry!!! Failed to update Staff");
	}

	
	public ResponseEntity<ResponseStructure<StaffDto>> findStaffById(int staffId){
		Staff dbStaff=staffDao.findStaffById(staffId);
		if(dbStaff!=null) {
			StaffDto dbStaffDto=this.mapper.map(dbStaff, StaffDto.class);
			dbStaffDto.setAdminDto(this.mapper.map(dbStaff, AdminDto.class));
			dbStaffDto.setMedicalStoreDto(this.mapper.map(dbStaff, MedicalStoreDto.class));
			
			
			ResponseStructure<StaffDto> structure= new ResponseStructure<>();
			structure.setMessage("We have found the staff Id");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dbStaffDto);
			
			return new ResponseEntity<ResponseStructure<StaffDto>> (structure, HttpStatus.FOUND);
		}else {
			throw new StaffIdNotFoundException("Sorry!!! Faild to get the Staff Id");
		}
	}

	public ResponseEntity<ResponseStructure<StaffDto>> deleteStaffById(int staffId) {
		Staff dbStaff=staffDao.deleteStaffById(staffId);
		if(dbStaff!=null) {
			StaffDto dbStaffDto=this.mapper.map(dbStaff, StaffDto.class);
			dbStaffDto.setAdminDto(this.mapper.map(dbStaff, AdminDto.class));
			dbStaffDto.setMedicalStoreDto(this.mapper.map(dbStaff, MedicalStoreDto.class));
			
			
			ResponseStructure<StaffDto> structure= new ResponseStructure<>();
			structure.setMessage("Staff Id has been deleted");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(dbStaffDto);
			
			return new ResponseEntity<ResponseStructure<StaffDto>> (structure, HttpStatus.GONE);
		}
		else {
			throw new StaffIdNotFoundException("Sorry!!! Faild to get the Staff Id");
		}
	}
	
}


