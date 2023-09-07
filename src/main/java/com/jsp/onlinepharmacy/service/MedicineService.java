package com.jsp.onlinepharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.dao.MedicalStoreDao;
import com.jsp.onlinepharmacy.dao.MedicineDao;
import com.jsp.onlinepharmacy.entity.MedicalStore;
import com.jsp.onlinepharmacy.entity.Medicine;
import com.jsp.onlinepharmacy.exception.MedicalStoreIdNotFoundException;
import com.jsp.onlinepharmacy.exception.MedicineIdNotFoundException;
import com.jsp.onlinepharmacy.util.ResponseStructure;

@Service
public class MedicineService {
	@Autowired
	private MedicineDao dao;
	@Autowired
	private MedicalStoreDao storedao;

	public ResponseEntity<ResponseStructure<Medicine>> addMedicine(int storeId, Medicine medicine) {
		MedicalStore dbMedicalStore=storedao.getMedicalStoreById(storeId);
		if(dbMedicalStore!=null) {
			medicine.setMedicalStore(dbMedicalStore);
			Medicine dbMedicine=dao.addMedicine(medicine);
			
			ResponseStructure<Medicine> structure= new ResponseStructure<>();
			structure.setMessage("Medicine added successfully");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(dbMedicine);
			return new ResponseEntity<ResponseStructure<Medicine>> (structure, HttpStatus.CREATED);
		}
		else {
			throw new MedicalStoreIdNotFoundException("Sorry!!! failed to add medicine");
		}
		
	}

	public ResponseEntity<ResponseStructure<Medicine>> updateMedicine(int medicineId, Medicine medicine) {
		Medicine	dbMedicine=dao.updateMedicine(medicineId, medicine);
		if(dbMedicine!=null) {
			ResponseStructure<Medicine> structure= new ResponseStructure<>();
			structure.setMessage("Medicine updated successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(dbMedicine);
			return new ResponseEntity<ResponseStructure<Medicine>> (structure, HttpStatus.OK);
			
		}else {
			throw new MedicineIdNotFoundException("Sorry!! Failed to update medicine");
			
		}
		
	}

	public ResponseEntity<ResponseStructure<Medicine>> getMedicineById(int medicineId) {
		Medicine	dbMedicine=dao.getMedicineById(medicineId);
		if(dbMedicine!=null) {
			ResponseStructure<Medicine> structure= new ResponseStructure<>();
			structure.setMessage("Medicine fetched successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dbMedicine);
			return new ResponseEntity<ResponseStructure<Medicine>> (structure, HttpStatus.FOUND);
			
		}else {
			throw new MedicineIdNotFoundException("Sorry!! Failed to fetch medicine");
		
	}
	}

	public ResponseEntity<ResponseStructure<Medicine>> deleteMedicineById(int medicineId) {
		
		Medicine	dbMedicine=dao.deleteMedicineById(medicineId);
		if(dbMedicine!=null) {
			ResponseStructure<Medicine> structure= new ResponseStructure<>();
			structure.setMessage("Medicine deleted successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(dbMedicine);
			return new ResponseEntity<ResponseStructure<Medicine>> (structure, HttpStatus.GONE);
			
		}else {
			throw new MedicineIdNotFoundException("Sorry!! Failed to delete medicine");
		
	}
	}

	

}
