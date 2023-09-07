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
import com.jsp.onlinepharmacy.dto.MedicalStoreDto;
import com.jsp.onlinepharmacy.entity.MedicalStore;
import com.jsp.onlinepharmacy.service.MedicalStoreService;
import com.jsp.onlinepharmacy.util.ResponseStructure;

@RestController
@RequestMapping("/medicalStore")
public class MedicalStoreController {
	@Autowired
	private MedicalStoreService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<MedicalStoreDto>> saveMedicalStore(@RequestParam int adminId, @RequestParam int addressId, @RequestBody MedicalStoreDto medicalStoreDto){
		return service.saveMedicalStore(adminId,addressId,medicalStoreDto);
		
	}
	@PutMapping
	public ResponseEntity<ResponseStructure<MedicalStoreDto>> updateMedicalStore(@RequestParam int storeId, @RequestBody MedicalStore medicalStore){
		return service.updateMedicalStore(storeId, medicalStore);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<MedicalStoreDto>> findMedicalStoreById(@RequestParam int storeId){
		return service.getMedicalStoreById(storeId);
		
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<MedicalStoreDto>> deleteMedicalStoreById(@RequestParam int storeId){
		return service.deleteMedicalStoreById(storeId);
	}

}
