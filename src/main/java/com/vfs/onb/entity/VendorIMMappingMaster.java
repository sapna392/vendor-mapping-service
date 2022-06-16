package com.vfs.onb.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
@Table(name="MASTER_IM_VENDOR_MAPPING")
public class VendorIMMappingMaster {

	@Column(name = "SEQUENCE_ID")
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
	
	@Column(name="FILE_UPLOADED_BY")
	private String uploadedby;
	
	@Column(name="UPLOAD_FILE_LOCATION")
	private String uploadfilelocation;
	
	@Column(name="CREATION_TIME")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date creationtime;
	
	@Column(name="AUTHORIZED_TIME")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date authorizedtime;
	
	@Column(name="AUTHORIZED_BY")
	private String authorizedby;
	
}
