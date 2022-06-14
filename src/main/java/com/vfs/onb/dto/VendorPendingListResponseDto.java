package com.vfs.onb.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.vfs.onb.entity.VendorIMMappingPreAuth;

import lombok.Data;

@Data
public class VendorPendingListResponseDto {

	private String messagedescr;
	private String status;
	private String statuscode;
	private List<VendorIMMappingPreAuth> data;
	
}
