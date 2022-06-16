package com.vfs.onb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vfs.onb.entity.VendorIMMappingPreAuth;

@Repository
public interface VendorIMMappingPreAuthRepository extends JpaRepository<VendorIMMappingPreAuth, Long> {
 
	boolean existsByvendorimcode(String vendorimcode);

	@Query(value="select s.vendorimcode from VendorIMMappingPreAuth s")
	List<String> checkDuplicateVendorIM();

	@Query(value="select s from VendorIMMappingPreAuth s where s.status= 'Pending' AND s.imcode=:imcode")
	List<VendorIMMappingPreAuth> pendingVendorList(@Param("imcode") String imcode);
	
	VendorIMMappingPreAuth findByvendorimcode(String vendorimcode);

	void deleteByvendorimcode(String onevendor); 

}
