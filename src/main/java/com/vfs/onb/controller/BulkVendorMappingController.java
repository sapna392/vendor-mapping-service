package com.vfs.onb.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vfs.onb.dto.BulkVendorUploadResponseDto;
import com.vfs.onb.dto.VendorPendingListResponseDto;
import com.vfs.onb.entity.VendorIMMappingPreAuth;
import com.vfs.onb.service.BulkVendorMappingService; 

@RestController
@RequestMapping(value = "scfu/api")
@CrossOrigin 
public class BulkVendorMappingController { 
 
	@Autowired
	BulkVendorMappingService bulkvendormappingservice; 

	private static final Logger logger = LoggerFactory.getLogger(BulkVendorMappingController.class);

	// Maker Bulk Upload Vendor Mapping
	@PostMapping(value = "/bulkmappingvendorim/{id}")
	public BulkVendorUploadResponseDto uploadBulkVendorFile(@PathVariable String id,
			@RequestPart("file") MultipartFile file) throws IOException {
		logger.info("In VendorFinController uploadFile method IM Code From Request >>>>>>>>>>>>>    " + id);
		return bulkvendormappingservice.addBulkVendor(id, file);
	}
 
	// Checker Pending Vendor List
	@GetMapping(value = "/pendingvendorimmapping/{id}") 
	public VendorPendingListResponseDto getPending(@PathVariable String id) {
		return bulkvendormappingservice.getPendingData(id); 
	} 

	// Checker Update Pending Vendor to Approved
	@PutMapping(value = "/approvedbulkvendor")
	public BulkVendorUploadResponseDto approvedPendingVendor(@RequestBody ArrayList<VendorIMMappingPreAuth> bulkvendor) throws IOException {
		logger.info("In VendorFinController uploadFile method IM Code From Request >>>>>>>>>>>>>    " );
		return bulkvendormappingservice.addapprovedBulkVendor(bulkvendor);
	}
}
