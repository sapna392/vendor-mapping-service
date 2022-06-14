package com.vfs.onb.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vfs.onb.common.StatusConstant;
import com.vfs.onb.dto.BulkVendorUploadResponseDto;
import com.vfs.onb.dto.VendorPendingListResponseDto;
import com.vfs.onb.entity.VendorIMMappingPreAuth;
import com.vfs.onb.repository.VendorRepository;
import com.vfs.onb.service.BulkVendorMappingService;

@Service
public class BulkVendorMappingServiceImpl implements BulkVendorMappingService {

	private static final Logger logger = LoggerFactory.getLogger(BulkVendorMappingServiceImpl.class);

	@Autowired
	VendorRepository vendorrepo;

	// Maker
	
	public BulkVendorUploadResponseDto addBulkVendor(String id, MultipartFile file) throws IOException {

		List<String> contentList = null;
		String[] splitrow = null;
		VendorIMMappingPreAuth vendorimmappingpreauth = null;
		BulkVendorUploadResponseDto responsedto = new BulkVendorUploadResponseDto();
		List<VendorIMMappingPreAuth> bulkvendetailslist = new ArrayList<VendorIMMappingPreAuth>();

		try {
			contentList = Arrays
					.asList(new String(file.getInputStream().readAllBytes()).split(StatusConstant.LINE_DELIMITER));

			contentList = contentList.stream().filter(cl -> StringUtils.isNotEmpty(cl)).collect(Collectors.toList());

			file.transferTo(new File("C:\\Users\\Sbi\\Downloads\\uploadcheck\\" + file.getOriginalFilename()));
			List<String> duplicatevendorim =null;			

			for (String content : contentList) {

				if (!content.contains("#")) {

					if (file.getContentType().equals("text/plain")) {
						splitrow = content.split("\\|");
					} else if (file.getContentType().equals("text/csv")) {
						splitrow = content.split(",");
					}

					if ((!duplicatevendorim.contains(id + splitrow[1]))) {

						vendorimmappingpreauth = new VendorIMMappingPreAuth();

						vendorimmappingpreauth.setVendorimcode(id + splitrow[1]);
						vendorimmappingpreauth.setVendorname(splitrow[0]);
						vendorimmappingpreauth.setVendorcode(splitrow[1]);
						vendorimmappingpreauth.setImcode(id);
						vendorimmappingpreauth.setCreationtime(new Timestamp(System.currentTimeMillis()));
						vendorimmappingpreauth.setStatus(StatusConstant.PENDING_STATUS);
						vendorimmappingpreauth.setUploadfilelocation("C:\\Users\\Sbi\\Downloads\\uploadcheck\\");
						vendorimmappingpreauth.setUploadedby("LOGIN USER");

						bulkvendetailslist.add(vendorimmappingpreauth);
						logger.info("VendorDetails Values Set >>>>> " + splitrow.toString());

						responsedto.setMessagedescr(StatusConstant.MAPPED_VENDOR_IM_SUCCESSFULLY);
						responsedto.setStatus(StatusConstant.STATUS_SUCCESS);
						responsedto.setStatuscode(StatusConstant.STATUS_SUCCESS_CODE);

					} else {

						// Already Mapped VendorIM
					}
				}
			}
			vendorrepo.saveAll(bulkvendetailslist);
		} catch (Exception e) {
			responsedto.setStatuscode(StatusConstant.STATUS_FAILURE_CODE);
			responsedto.setStatus(StatusConstant.STATUS_FAILURE);
			responsedto.setMessagedescr(e.getMessage());
		}

		return responsedto;
	}

//	Checker
	public VendorPendingListResponseDto getPendingData(String imcode) {
		VendorPendingListResponseDto responseDTO = new VendorPendingListResponseDto();
		try {
			if (vendorrepo.pendingVendorList(imcode) != null) {
				responseDTO.setMessagedescr("All Pending Vendors");
				responseDTO.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDTO.setStatuscode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDTO.setData(vendorrepo.pendingVendorList(imcode));
			} else {
				responseDTO.setStatuscode(StatusConstant.STATUS_DATA_NOT_FOUND_CODE);
				responseDTO.setStatus(StatusConstant.STATUS_FAILURE);
				responseDTO.setMessagedescr(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
				responseDTO.setData(null);
			}
		} catch (Exception e) {
			responseDTO.setStatuscode(StatusConstant.STATUS_FAILURE_CODE);
			responseDTO.setStatus(StatusConstant.STATUS_FAILURE);
			responseDTO.setMessagedescr(e.getMessage());
			responseDTO.setData(null);
		}

		return responseDTO;
	}

	@Override
	public BulkVendorUploadResponseDto addapprovedBulkVendor(ArrayList<VendorIMMappingPreAuth> bulkvendor) {
		BulkVendorUploadResponseDto responseDTO = new BulkVendorUploadResponseDto();
		try {
			vendorrepo.saveAll(bulkvendor);
			responseDTO.setMessagedescr("Approved Pending Selected Vendors");
			responseDTO.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDTO.setStatuscode(StatusConstant.STATUS_SUCCESS_CODE);
		} catch (Exception e) {
			responseDTO.setStatuscode(StatusConstant.STATUS_FAILURE_CODE);
			responseDTO.setStatus(StatusConstant.STATUS_FAILURE);
			responseDTO.setMessagedescr(e.getMessage());
		}
		return responseDTO;
	}

}
