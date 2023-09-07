package com.jsp.onlinepharmacy.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.onlinepharmacy.entity.Staff;
import com.jsp.onlinepharmacy.repository.StaffRepo;

@Repository
public class StaffDao {
	@Autowired
	private StaffRepo staffRepo;

	public Staff addStaff(Staff staff) {
		staffRepo.save(staff);
		return null;
	}

	public Staff updateStaff(int staffId, Staff staff) {
	Optional<Staff> optional= staffRepo.findById(staffId);
	if(optional.isPresent()){
		Staff oldStaff=optional.get();
		staff.setStaffId(staffId);
		staff.setAdmin(oldStaff.getAdmin());
		staff.setMedicalStore(oldStaff.getMedicalStore());
		staffRepo.save(staff);
		
	}
	return null;
		
	}

	public Staff findStaffById(int staffId) {
		Optional<Staff> optional=staffRepo.findById(staffId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
		
	}

	public Staff deleteStaffById(int staffId) {
		Optional<Staff> optional=staffRepo.findById(staffId);
		if(optional.isPresent()) {
			 Staff staff= optional.get();
			 staffRepo.deleteById(staffId);
			 return staff;
		}
		return null;
	}
}
