package com.nbc.convergencerepo.repository.convgdeal;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nbc.convergencerepo.domain.convgdeal.ConvergenceDealArchive;

@Repository
public interface ConvergenceDealArchiveRepository extends MongoRepository<ConvergenceDealArchive, Serializable> {

	List<ConvergenceDealArchive> findBycDealId(Long cDealId);

	List<ConvergenceDealArchive> findBycDealIdAndExportNo(Long cDealId, Integer exportNum);
	
	List<ConvergenceDealArchive> findBycDealIdAndSso(Long cDealId, String sso);
	
	List<ConvergenceDealArchive> findBycDealIdAndSsoAndFileIdIsNull(Long cDealId, String sso);


}