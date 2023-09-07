package com.jsp.onlinepharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.onlinepharmacy.dto.StaffDto;
import com.jsp.onlinepharmacy.entity.Staff;
import com.jsp.onlinepharmacy.service.StaffService;
import com.jsp.onlinepharmacy.util.ResponseStructure;


@RestController
@RequestMapping("/staff")
public class StaffController {
	
	@Autowired
	private StaffService staffService;
	@PostMapping
	public ResponseEntity<ResponseStructure<StaffDto>> addStaff (@RequestParam int adminId, @RequestParam int storeId, @RequestBody Staff staff){
		staffService.addStaff(adminId,storeId,staff);
		return null;
		
	}
	@PutMapping
	public ResponseEntity<ResponseStructure<StaffDto>> updateStaff (@RequestParam int staffId, @RequestBody Staff staff){
		return staffService.updateStaff(staffId, staff);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<StaffDto>> findStaffById (@RequestParam int staffId){
		return staffService.findStaffById(staffId);
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<StaffDto>> deleteStaffById(@RequestParam int staffId){
		return staffService.deleteStaffById(staffId);
	}

}
