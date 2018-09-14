package com.nbc.convergencerepo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbc.convergencerepo.domain.SequenceGenearator;
import com.nbc.convergencerepo.repository.SequenceRepository;

@Service
public class SequenceService {
	
	@Autowired
	SequenceRepository seqRepo;
		
    public synchronized Long getUniqueId() throws Exception {
    	Long idVal;
    	try {
    	SequenceGenearator sequence = seqRepo.findOne("id");
    	idVal = sequence.getValue();
    	sequence.setValue(idVal+1);
    	seqRepo.save(sequence);
    	return idVal;
    	}catch (Exception e) {
    		throw e;
    	}
    }
}
