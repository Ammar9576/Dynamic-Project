package com.nbc.convergencerepo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nbc.convergencerepo.domain.ResponseModel;
import com.nbc.convergencerepo.domain.admin.Admin;
import com.nbc.convergencerepo.domain.admin.Property;
import com.nbc.convergencerepo.domain.admin.RatingStream;
import com.nbc.convergencerepo.service.SequenceService;
import com.nbc.convergencerepo.service.admin.AdminService;
import com.nbc.convergencerepo.service.admin.PropertyService;
import com.nbc.convergencerepo.service.admin.RatingStreamService;



@RestController
@RequestMapping(value="/data")
public class AdminController {
	
	@Autowired
	RatingStreamService rsService;
	
	@Autowired
	PropertyService propService;

	@Autowired
	AdminService adminService;
	
	@Autowired
	SequenceService seqSvc;
	
	
	@RequestMapping(value="/ratingStream/save",method=RequestMethod.POST)
	public ResponseModel saveRatingStreams(@RequestBody(required=true) String data) {		
		ResponseModel resModel = new ResponseModel();					
		try {		
		List<RatingStream> rsList = new Gson().fromJson(data,new TypeToken<List<RatingStream>>(){}.getType());
		rsList.stream().forEach(rs -> { if(rs!=null && rs.getId()==null) {
			try {
				rs.setId(seqSvc.getUniqueId());
			} catch (Exception e) {
				resModel.setSuccess(false);
				resModel.setResponse(e.getMessage());
				e.printStackTrace();
			}}});
		rsService.save(rsList);		
		resModel.setSuccess(true);
		resModel.setResponse("Saved Successfully");
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());			
		}		
		return resModel;
	}	
	
	@RequestMapping(value="/ratingStream/delete",method=RequestMethod.DELETE)
	public ResponseModel deleteRatingStream(@RequestBody(required=true) String data) {		
		ResponseModel resModel = new ResponseModel();					
		try {		
		RatingStream rs = new Gson().fromJson(data,RatingStream.class);				
		rsService.deleteRatingStream(rs);		
		resModel.setSuccess(true);
		resModel.setResponse("deleted Successfully");
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());			
		}		
		return resModel;
	}	
	

	@RequestMapping(value="/ratingStream/deleteAll",method=RequestMethod.DELETE)
	public ResponseModel deleteRatingStream() {		
		ResponseModel resModel = new ResponseModel();					
		try {							
		rsService.deleteAll();		
		resModel.setSuccess(true);
		resModel.setResponse("deleted Successfully");
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());			
		}		
		return resModel;
	}	
	
	@RequestMapping(value="/ratingStream/listAll")
	public ResponseModel listAllRatingStreams() {		
		ResponseModel resModel = new ResponseModel();
		List<RatingStream> rsList = new ArrayList<RatingStream>();		
		try {
			rsList = rsService.listAll();								
			resModel.setSuccess(true);
			resModel.setResponse(rsList);
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());	
		}
		return resModel;
	}      
	
	@RequestMapping(value="/ratingStream/list")
	public ResponseModel listActiveRatingStreams() {		
		ResponseModel resModel = new ResponseModel();
		List<RatingStream> rsList = new ArrayList<RatingStream>();		
		try {
			rsList = rsService.listActive();								
			resModel.setSuccess(true);
			resModel.setResponse(rsList);
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());	
		}
		return resModel;
	}     
		
	@RequestMapping(value="/property/save",method=RequestMethod.POST)
	public ResponseModel saveProperties(@RequestBody(required=true) String data) {		
		ResponseModel resModel = new ResponseModel();					
		try {		
		//String data1 = "[ {\"name\": \"Universo\",\"enabled\": true}, {\"name\": \"This Is Us\",\"enabled\": true}]";
		List<Property> propList = new Gson().fromJson(data,new TypeToken<List<Property>>(){}.getType());
		propList.stream().forEach(prop -> { if(prop!=null && prop.getId()==null) {
			try {
				prop.setId(seqSvc.getUniqueId());
			} catch (Exception e) {
				resModel.setSuccess(false);
				resModel.setResponse(e.getMessage());
				e.printStackTrace();
			}}});
		propService.save(propList);		
		resModel.setSuccess(true);
		resModel.setResponse("Saved Successfully");
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());			
		}		
		return resModel;
	}	
	
	@RequestMapping(value="/property/listAll")
	public ResponseModel listAllProperties() {		
		ResponseModel resModel = new ResponseModel();
		List<Property> propList = new ArrayList<Property>();		
		try {
			propList = propService.listAll();								
			resModel.setSuccess(true);
			resModel.setResponse(propList);
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());	
		}
		return resModel;
	}  
	
	@RequestMapping(value="/property/list")
	public ResponseModel listActiveProperties() {		
		ResponseModel resModel = new ResponseModel();
		List<Property> propList = new ArrayList<Property>();		
		try {
			propList = propService.listActive();								
			resModel.setSuccess(true);
			resModel.setResponse(propList);
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());	
		}
		return resModel;
	}  
	
	@RequestMapping(value="/property/delete",method=RequestMethod.DELETE)
	public ResponseModel deleteProperty(@RequestBody(required=true) String data) {		
		ResponseModel resModel = new ResponseModel();					
		try {		
		Property prop = new Gson().fromJson(data,Property.class);				
		propService.deleteProperty(prop);		
		resModel.setSuccess(true);
		resModel.setResponse("deleted Successfully");
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());			
		}		
		return resModel;
	}
	
	@RequestMapping(value="/property/deleteAll",method=RequestMethod.DELETE)
	public ResponseModel deleteProperties() {		
		ResponseModel resModel = new ResponseModel();					
		try {							
		propService.deleteAll();		
		resModel.setSuccess(true);
		resModel.setResponse("deleted Successfully");
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());			
		}		
		return resModel;
	}
	
	@RequestMapping(value="/dashboardAdmin/list")
	public ResponseModel getDashboardAdmin() {		
		ResponseModel resModel = new ResponseModel();
		List<Admin> dashboardAdminList = new ArrayList<Admin>();		
		try {			
			dashboardAdminList = adminService.list();								
			resModel.setSuccess(true);
			resModel.setResponse(dashboardAdminList);
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());	
		}
		return resModel;
	}     
	
	@RequestMapping(value="/dashboardAdmin/save",method=RequestMethod.POST)
	public ResponseModel saveDashboardAdmin(@RequestBody(required=true) String data) {		
		ResponseModel resModel = new ResponseModel();					
		try {		
		List<Admin> dashboardAdminList = new Gson().fromJson(data,new TypeToken<List<Admin>>(){}.getType());				
		adminService.save(dashboardAdminList);		
		resModel.setSuccess(true);
		resModel.setResponse("Saved Successfully");
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());			
		}		
		return resModel;
	}
	
	@RequestMapping(value="/admin/list")
	public ResponseModel getAdmin() {		
		ResponseModel resModel = new ResponseModel();
		List<Admin> dashboardAdminList = new ArrayList<Admin>();		
		try {			
			dashboardAdminList = adminService.list();								
			resModel.setSuccess(true);
			resModel.setResponse(dashboardAdminList);
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());	
		}
		return resModel;
	}     
	
	@RequestMapping(value="/admin/list/type/{type}")
	public ResponseModel getAdminByType(@PathVariable String type) {		
		ResponseModel resModel = new ResponseModel();
		List<Admin> dashboardAdminList = new ArrayList<Admin>();		
		try {			
			dashboardAdminList = adminService.listByType(type);								
			resModel.setSuccess(true);
			resModel.setResponse(dashboardAdminList);
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());	
		}
		return resModel;
	}    
	
	@RequestMapping(value="/admin/list/name/{name}")
	public ResponseModel getAdminByName(@PathVariable String name) {		
		ResponseModel resModel = new ResponseModel();
		List<Admin> dashboardAdminList = new ArrayList<Admin>();		
		try {			
			dashboardAdminList = adminService.listByName(name);								
			resModel.setSuccess(true);
			resModel.setResponse(dashboardAdminList);
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());	
		}
		return resModel;
	}    
	
	@RequestMapping(value="/admin/save",method=RequestMethod.POST)
	public ResponseModel saveAdmin(@RequestBody(required=true) String data) {		
		ResponseModel resModel = new ResponseModel();					
		try {		
		List<Admin> dashboardAdminList = new Gson().fromJson(data,new TypeToken<List<Admin>>(){}.getType());				
		adminService.save(dashboardAdminList);		
		resModel.setSuccess(true);
		resModel.setResponse("Saved Successfully");
		}catch(Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());			
		}		
		return resModel;
	}
	
}
