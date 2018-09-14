package com.nbc.convergencerepo.repository.admin;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nbc.convergencerepo.domain.admin.Property;



@Repository
public interface PropertyRepository  extends MongoRepository<Property, Serializable>{
	
}