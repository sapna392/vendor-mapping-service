package com.vfs.onb.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vfs.onb.dto.BulkVendorUploadResponseDto;
import com.vfs.onb.dto.VendorPendingListResponseDto;
import com.vfs.onb.entity.VendorIMMappingPreAuth;

@Service
public interface BulkVendorMappingService {
	
	BulkVendorUploadResponseDto addBulkVendor(String id,MultipartFile file)throws IOException;

	VendorPendingListResponseDto getPendingData(String id);
 
	BulkVendorUploadResponseDto addapprovedBulkVendor(ArrayList<VendorIMMappingPreAuth> bulkvendor);
}
  