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
@Table(name="PRE_AUTH_IM_VENDOR_MAPPING")
public class VendorIMMappingPreAuth {
	
	@Id
	@Column(name = "SEQUENCE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long seqid;
	 
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
	
	@Column(name="UPLOADED_BY")
	private String uploadedby;
	
	@Column(name="UPLOAD_FILE_LOCATION")
	private String uploadfilelocation;
	
	@Column(name="CREATION_TIME")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date creationtime;
	
/*	
	@Column
	private String subdealercode;

	@Column
	private String address;
	
	@Column
    private String city;
	
	@Column
	private String district;
	
	@Column
	private String state;
	
	@Column
	private String country;
	
	@Column
	private int pincode;
	
	@Column
	private String emailid;
	
	@Column
	private int mobileno;
	
	@Column
	private String fax;
	
	@Column
	private String additionalfields;
	
	@Column
	private String businessgroup;
	
	@Column
	private String creditaccountnumberwithsbi;
	
	@Column
	private String creditaccountnumber;
	
	@Column
	private String branchifscode;
	
	@Column
	private String defaultcreditaccountnnmberwithdsbiother;
	*/

}
