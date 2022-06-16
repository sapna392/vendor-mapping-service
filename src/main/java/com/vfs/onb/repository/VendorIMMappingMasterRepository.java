package com.vfs.onb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vfs.onb.entity.VendorIMMappingHistory;
import com.vfs.onb.entity.VendorIMMappingMaster;

public interface VendorIMMappingMasterRepository extends JpaRepository<VendorIMMappingMaster, Long> {

}
