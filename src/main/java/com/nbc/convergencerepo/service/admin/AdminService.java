package com.nbc.convergencerepo.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbc.convergencerepo.domain.admin.Admin;
import com.nbc.convergencerepo.repository.admin.AdminRepository;


@Service
public class AdminService {
	
	@Autowired
	AdminRepository adminRepo;
	
	public List<Admin> list() {
		return adminRepo.findAll();
	}
	
	public List<Admin> listByType(String type) {
		return adminRepo.findByType(type);
	}
	
	
	public List<Admin> listByName(String name) {
		return adminRepo.findByName(name);
	}
	
	public void save(List<Admin> dashboardAdminList) {
		try {
			adminRepo.save(dashboardAdminList);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void delete(Admin info) {
		try {
			adminRepo.delete(info);
		}catch(Exception e) {
			throw e;
		}
	}
}
