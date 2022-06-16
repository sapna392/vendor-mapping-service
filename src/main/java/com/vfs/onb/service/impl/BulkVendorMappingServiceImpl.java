package com.vfs.onb.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vfs.onb.common.StatusConstant;
import com.vfs.onb.dto.BulkVendorUploadResponseDto;
import com.vfs.onb.dto.VendorPendingListResponseDto;
import com.vfs.onb.entity.VendorIMMappingMaster;
import com.vfs.onb.entity.VendorIMMappingPreAuth;
import com.vfs.onb.repository.VendorIMMappingHistoryRepository;
import com.vfs.onb.repository.VendorIMMappingMasterRepository;
import com.vfs.onb.repository.VendorIMMappingPreAuthRepository;
import com.vfs.onb.service.BulkVendorMappingService;

@Service
public class BulkVendorMappingServiceImpl implements BulkVendorMappingService {

	private static final Logger logger = LoggerFactory.getLogger(BulkVendorMappingServiceImpl.class);

	@Autowired
	VendorIMMappingPreAuthRepository vendorimpreauthrepo;

	@Autowired
	VendorIMMappingMasterRepository vendorimmasterrepo;

	@Autowired
	VendorIMMappingHistoryRepository vendorimhistoryrepo;

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
			List<String> existingvendors = new ArrayList<String>();

			for (String content : contentList) {

				if (!content.contains("#")) {

					if (file.getContentType().equals("text/plain")) {
						splitrow = content.split("\\|");
					} else if (file.getContentType().equals("text/csv")) {
						splitrow = content.split(",");
					}

					if (!vendorimpreauthrepo.existsByvendorimcode(id + splitrow[1])) {
						vendorimmappingpreauth = new VendorIMMappingPreAuth();

						vendorimmappingpreauth.setVendorimcode(id + splitrow[1]);
						vendorimmappingpreauth.setVendorname(splitrow[0]);
						vendorimmappingpreauth.setVendorcode(splitrow[1]);
						vendorimmappingpreauth.setImcode(id);
						vendorimmappingpreauth.setCreationtime(new Timestamp(System.currentTimeMillis()));
						vendorimmappingpreauth.setStatus(StatusConstant.PENDING_STATUS);
						vendorimmappingpreauth.setUploadfilelocation("C:\\Users\\Sbi\\Downloads\\uploadcheck\\");
						vendorimmappingpreauth.setUploadedby("MAKER USER");

						bulkvendetailslist.add(vendorimmappingpreauth);
						logger.info("VendorDetails Values Set >>>>> " + splitrow.toString());

						responsedto.setMessagedescr(StatusConstant.MAPPED_VENDOR_IM_SUCCESSFULLY);
						responsedto.setStatus(StatusConstant.STATUS_SUCCESS);
						responsedto.setStatuscode(StatusConstant.STATUS_SUCCESS_CODE);
					} else {
						existingvendors.add(id + splitrow[1]);
					}
				}
				vendorimpreauthrepo.saveAll(bulkvendetailslist);
			} 

			if (!existingvendors.isEmpty()) {
				responsedto.setMessagedescr(
						"File Contains Duplicate Entries Please Check , Other Non Duplicates Records Inserted Successfully");
				responsedto.setStatus(StatusConstant.STATUS_SUCCESS);
				responsedto.setStatuscode(StatusConstant.STATUS_SUCCESS_CODE);
			}
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
			if (vendorimpreauthrepo.pendingVendorList(imcode) != null) {
				responseDTO.setMessagedescr("All Pending Vendors");
				responseDTO.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDTO.setStatuscode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDTO.setData(vendorimpreauthrepo.pendingVendorList(imcode));
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

	@Transactional
	public BulkVendorUploadResponseDto addapprovedBulkVendor(List<String> vendorimcode) {
		BulkVendorUploadResponseDto responseDTO = new BulkVendorUploadResponseDto();
		try {

			VendorIMMappingPreAuth vendorimmappingpreauth = new VendorIMMappingPreAuth();
			VendorIMMappingMaster vendorimmapping = null;

			List<VendorIMMappingPreAuth> bulkvendetailslist = new ArrayList<VendorIMMappingPreAuth>();
			List<VendorIMMappingMaster> bulkvednor = new ArrayList<VendorIMMappingMaster>();

			for (String onevendor : vendorimcode) {
				vendorimmappingpreauth = vendorimpreauthrepo.findByvendorimcode(onevendor);
				vendorimmapping = new VendorIMMappingMaster();
				
				vendorimmapping.setImcode(vendorimmappingpreauth.getImcode());
				vendorimmapping.setStatus("Approved");
				vendorimmapping.setUploadedby(vendorimmappingpreauth.getUploadedby());
				vendorimmapping.setUploadfilelocation(vendorimmappingpreauth.getUploadfilelocation());
				vendorimmapping.setVendorcode(vendorimmappingpreauth.getVendorcode());
				vendorimmapping.setVendorimcode(vendorimmappingpreauth.getVendorimcode());
				vendorimmapping.setCreationtime(vendorimmappingpreauth.getCreationtime());
				vendorimmapping.setVendorname(vendorimmappingpreauth.getVendorname());
				vendorimmapping.setAuthorizedtime(new Timestamp(System.currentTimeMillis()));
				vendorimmapping.setAuthorizedby("CHECKER USER");
				
				vendorimpreauthrepo.deleteByvendorimcode(onevendor);
				bulkvednor.add(vendorimmapping);
			}
			
			vendorimmasterrepo.saveAll(bulkvednor);

			responseDTO.setMessagedescr("Selected Vendors Status Approved");
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
