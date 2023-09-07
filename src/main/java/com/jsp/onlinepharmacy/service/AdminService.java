package com.jsp.onlinepharmacy.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.dao.AdminDao;
import com.jsp.onlinepharmacy.dto.AdminDto;
import com.jsp.onlinepharmacy.entity.Admin;
import com.jsp.onlinepharmacy.util.ResponseStructure;

@Service
public class AdminService {
	@Autowired
	private AdminDao dao;
	
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<AdminDto>> saveAdmin(Admin admin) {
		Admin dbAdmin=dao.saveAdmin(admin);
		// help you to copy entire one class data to the another class data 
		//convert to admin to admindto to return admindto
		
		 AdminDto adminDto=this.modelMapper.map(dbAdmin, AdminDto.class);
		 //                                   source      destination
		 ResponseStructure<AdminDto> structure= new ResponseStructure<>();
		 structure.setMessage("Admin saved successfully");
		 structure.setStatus(HttpStatus.CREATED.value());
		 structure.setData(adminDto);
		 return new ResponseEntity<ResponseStructure<AdminDto>> (structure, HttpStatus.CREATED);
		
		
	}

	public ResponseEntity<ResponseStructure<AdminDto>> updateAdmin(int adminId, Admin admin) {
	Admin dbAdmin	=dao.updateAdmin(adminId, admin);
	if(dbAdmin!=null) {
	AdminDto adminDto=this.modelMapper.map(dbAdmin, AdminDto.class);
	 
	 ResponseStructure<AdminDto> structure= new ResponseStructure<>();
	 structure.setMessage("Admin updated successfully");
	 structure.setStatus(HttpStatus.OK.value());
	 structure.setData(adminDto);
	 return new ResponseEntity<ResponseStructure<AdminDto>> (structure, HttpStatus.OK);

	}else {
		// raise admin id not found exception
		return null;
	}

}
}
