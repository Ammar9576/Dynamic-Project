package com.nbc.convergencerepo.repository.admin;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nbc.convergencerepo.domain.admin.Admin;




@Repository
public interface AdminRepository  extends MongoRepository<Admin, Serializable>{

	List<Admin> findByType(String type);

	List<Admin> findByName(String name);
	
	
	
}