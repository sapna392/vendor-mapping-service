package com.vfs.onb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vfs.onb.entity.VendorIMMappingPreAuth;

@Repository
public interface VendorRepository extends JpaRepository<VendorIMMappingPreAuth, Long> {

//	boolean existsByimcode(Long imcode);
//
//	@Query(value="select s.comkey from VendorDetails s where s.imcode=:imcode AND s.vendorcode=:vendorcode")
//	String existsByvenimc(@Param("imcode") String id,@Param("vendorcode") String vendorcode);
	  
	@Query(value="select s.vendorimcode from VendorIMMappingPreAuth s")
	List<String> checkDuplicateVendorIM();

	@Query(value="select s from VendorIMMappingPreAuth s where s.status= 'Pending' AND s.imcode=:imcode")
	List<VendorIMMappingPreAuth> pendingVendorList(@Param("imcode") String imcode);
 
//	@Modifying
//	@Transactional
//	@Query("update TestClass u set u.status = :status where u.name = :name")
//	int updateTestClassSetStatusForName(@Param("status") String status, @Param("name") String name);
//	
//	@Query("select s.status from TestClass s where s.name=:name")
//	String findByName(@Param("name") String name);

	 
}
