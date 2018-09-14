package com.nbc.convergencerepo.repository.convgdeal;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nbc.convergencerepo.domain.convgdeal.ConvergenceDeal;

@Repository
public interface ConvergenceDealRepository  extends MongoRepository<ConvergenceDeal, Serializable>{
	
}