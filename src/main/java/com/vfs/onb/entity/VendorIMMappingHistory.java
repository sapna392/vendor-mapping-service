package com.vfs.onb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name="HISTORY_IM_VENDOR_MAPPING")
public class VendorIMMappingHistory {
	
	@Column(name = "REQUEST_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reqid;
	 
	@Column(name = "RELATIONSHIP_NO_VENDOR_IM")
	private String vendorimcode;

	@Column(name = "VENDOR_NAME")
	private String vendorname;

	@Column(name="VENDOR_CODE")
	private String vendorcode;
	
	@Column(name="IM_CODE")
	private String imcode;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="ACTION")
	private String action;
	
	@Column(name="MODIFIED_BY")
	private String modifiedby;
	
	@Column(name="FILE_LOCATION_PATH")
	private String filelocationpath;
	
	@Column(name="CREATION_TIME")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date creationtime;
	
	@Column(name="MODIFIED_TIME")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date modifiedtime;
	
}
