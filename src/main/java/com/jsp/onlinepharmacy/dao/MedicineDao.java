package com.jsp.onlinepharmacy.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.onlinepharmacy.entity.Medicine;
import com.jsp.onlinepharmacy.repository.MedicineRepo;

@Repository
public class MedicineDao {
	@Autowired
	private MedicineRepo repo;

	public Medicine addMedicine(Medicine medicine) {
		
		return repo.save(medicine);
	}
	
	public Medicine updateMedicine(int medicineId, Medicine medicine) {
		Optional<Medicine> optional=repo.findById(medicineId);
		if(optional.isPresent()) {
			Medicine dbMedicine=optional.get();
			dbMedicine.setMedicineId(medicineId);
			medicine.setMedicalStore(dbMedicine.getMedicalStore());
			repo.save(medicine);
			return medicine;
		}
		return null;
	}

	public Medicine getMedicineById(int medicineId) {
		
		Optional<Medicine> optional=repo.findById(medicineId);
		if(optional.isPresent()) {
			
			return optional.get();
		}
		return null;
	}

	public Medicine deleteMedicineById(int medicineId) {
		
		Optional<Medicine> optional=repo.findById(medicineId);
		if(optional.isPresent()) {
			
			repo.deleteById(medicineId);
			return optional.get();
		}
		return null;
	}

	

}
