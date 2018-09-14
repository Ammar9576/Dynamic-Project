package com.nbc.convergencerepo.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nbc.convergencerepo.domain.SequenceGenearator;

@Repository
public interface SequenceRepository extends MongoRepository<SequenceGenearator, Serializable> {

}