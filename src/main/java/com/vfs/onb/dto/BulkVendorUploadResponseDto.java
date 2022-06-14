package com.vfs.onb.dto;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class BulkVendorUploadResponseDto {

	private String messagedescr;
	private String status;
	private String statuscode;
	//private String data;
}
