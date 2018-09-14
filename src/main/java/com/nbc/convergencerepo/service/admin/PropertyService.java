package com.nbc.convergencerepo.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbc.convergencerepo.domain.admin.Property;
import com.nbc.convergencerepo.repository.admin.PropertyRepository;


@Service
public class PropertyService {
	@Autowired
	PropertyRepository propRepo;
	
	public List<Property> listAll() {
		return propRepo.findAll();
	}
	public List<Property> listActive() {				
		return propRepo.findAll(new Property().listActive());
	}
	public void saveProperty(Property prop) {
		try {
			propRepo.save(prop);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void deleteProperty(Property prop) {
		try {
			propRepo.delete(prop);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void deleteAll() {
		try {
			propRepo.deleteAll();
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void save(List<Property> propList) {
		try {
			propRepo.save(propList);
		}catch(Exception e) {
			throw e;
		}
	}
}
