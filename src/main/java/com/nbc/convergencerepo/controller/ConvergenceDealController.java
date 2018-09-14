package com.nbc.convergencerepo.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.nbc.convergencerepo.domain.AbstractDomain;
import com.nbc.convergencerepo.domain.PageResponseObject;
import com.nbc.convergencerepo.domain.RequestObject;
import com.nbc.convergencerepo.domain.ResponseModel;
import com.nbc.convergencerepo.domain.convgdeal.ConvergenceDeal;
import com.nbc.convergencerepo.domain.convgdeal.ConvergenceDealArchive;
import com.nbc.convergencerepo.service.SequenceService;
import com.nbc.convergencerepo.service.convgdeal.ConvergenceDealService;

@RestController
@RequestMapping(value = "/convergenceDeal")
@EnableMongoAuditing
public class ConvergenceDealController {
	@Autowired
	ConvergenceDealService convSvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	SequenceService seqSvc;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseModel saveConvergenceDeal(@RequestBody(required = true) String data) {
		ResponseModel resModel = new ResponseModel();
		AbstractDomain<ConvergenceDeal> deal = null;
		try {
			ConvergenceDeal convDeal = new Gson().fromJson(data, ConvergenceDeal.class);			
			deal = convSvc.save(convDeal);
			resModel.setSuccess(true);
			resModel.setResponse(deal.getId());
		} catch (Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());
		}
		return resModel;
	}

	@RequestMapping(value = "/list/{id}")
	public ResponseModel listConvergenceDealById(@PathVariable Long id) {
		ResponseModel resModel = new ResponseModel();
		ConvergenceDeal convgDeal = null;
		try {
			resModel = convSvc.listById(id);
			resModel.setSuccess(true);
			//resModel.setResponse(convgDeal);
		} catch (Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());
		}
		return resModel;
	}
	
	@RequestMapping(value = "/listRawDeal/{id}")
	public ResponseModel listRawConvergenceDealById(@PathVariable Long id) {
		ResponseModel resModel = new ResponseModel();
		ConvergenceDeal convgDeal = null;
		try {
			resModel = convSvc.listRawById(id);
			resModel.setSuccess(true);
			//resModel.setResponse(convgDeal);
		} catch (Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());
		}
		return resModel;
	}
	
	@RequestMapping(value = "/listWithDashboard/{id}")
	public ResponseModel listWithDashboard(@PathVariable Long id) {
		ResponseModel resModel = new ResponseModel();
		ConvergenceDeal convgDeal = null;
		try {
			resModel = convSvc.listWithDashboard(id);
			resModel.setSuccess(true);
			//resModel.setResponse(convgDeal);
		} catch (Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());
		}
		return resModel;
	}

	@RequestMapping(value = "/list")
	public ResponseModel listActiveConvergenceDeals() {
		ResponseModel resModel = new ResponseModel();
		List<ConvergenceDeal> convgDealList = null;
		try {
			convgDealList = convSvc.listActive();

			resModel.setSuccess(true);
			resModel.setResponse(convgDealList);
		} catch (Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());
		}
		return resModel;
	}

	@RequestMapping(value = "/list/lite")
	public ResponseModel listActiveConvergenceDealsLite() {
		ResponseModel resModel = new ResponseModel();
		List<ConvergenceDeal> convgDealList = null;
		try {
			convgDealList = convSvc.listActive();
			resModel.setSuccess(true);
			resModel.setResponse(convSvc.stripConvergeDeal(convgDealList));
		} catch (Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());
		}
		return resModel;
	}

	@RequestMapping(value = "/listAll")
	public ResponseModel listAllConvergenceDeals() {
		ResponseModel resModel = new ResponseModel();
		List<ConvergenceDeal> convgDealList = null;
		try {
			convgDealList = convSvc.listAll();
			resModel.setSuccess(true);
			resModel.setResponse(convgDealList);
		} catch (Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());
		}
		return resModel;
	}

	@RequestMapping(value = "/listAll/lite")
	public ResponseModel listAllConvergenceDealsLite() {
		ResponseModel resModel = new ResponseModel();
		List<ConvergenceDeal> convgDealList = null;
		try {
			convgDealList = convSvc.listAll();
			resModel.setSuccess(true);
			resModel.setResponse(convSvc.stripConvergeDeal(convgDealList));
		} catch (Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());
		}
		return resModel;
	}
	
@RequestMapping(value = "/setPdfContent/{cDealId}",method=RequestMethod.POST)
	public ResponseModel setPdfContent(@PathVariable Long cDealId,@RequestParam String sso, @RequestBody byte[] pdfContent) {
		ResponseModel resModel = new ResponseModel();
		try {
			convSvc.setPdfContent(cDealId, sso, null, pdfContent); //determine if ui need to pass file name
			resModel.setSuccess(true);
			resModel.setResponse("PDF Saved Successfully");
		}catch(Exception e) {
			resModel.setSuccess(false);
			resModel.setResponse("Error while saving pdf--"+e.getMessage());
		}
		return resModel;
	}
  
	@RequestMapping(value = "/saveArchiveDeal/{cDealId}",method=RequestMethod.POST)
	public ResponseModel saveArchiveDeal(@PathVariable Long cDealId, @RequestParam String sso) {
		ResponseModel resModel = new ResponseModel();
		try {
			Integer exportNum = convSvc.saveCdealArchive(cDealId,sso,null);
			resModel.setSuccess(true);
			resModel.setResponse(exportNum);
		}catch(Exception e) {
			resModel.setSuccess(false);
			resModel.setResponse("Error while archiving deal--"+e.getMessage());
		}
		return resModel;
	}
	
	@RequestMapping(value = "/deleteCdealArchive/{cDealId}/{exportNo}",method=RequestMethod.DELETE)
	public ResponseModel deleteCdealArchive(@PathVariable Long cDealId, @PathVariable Integer exportNo) {
		ResponseModel resModel = new ResponseModel();
		try {
			convSvc.deleteCdealArchive(cDealId, exportNo);
			resModel.setSuccess(true);
			resModel.setResponse("cDealArchive record is deleted successfully");
		}catch(Exception e) {
			resModel.setSuccess(false);
			resModel.setResponse("Error while deleting cDealArchive--"+e.getMessage());
		}
		return resModel;
	}	
	
	@RequestMapping(value = "/getPdf/{fileId}")
	public ResponseModel getPdf(@PathVariable ObjectId fileId) {
		byte[] pdfContent = {};
		ResponseModel resModel = new ResponseModel();
		try {
		 pdfContent = convSvc.getPdfContent(fileId);	
		 resModel.setSuccess(true);
		 resModel.setResponse(pdfContent);		 
		}catch(Exception e) {
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());			
		}
		return resModel;
	}
	
	@RequestMapping(value = "/listArchiveDeals/{cDealId}",method=RequestMethod.POST)
	public ResponseModel listArchiveDeals(@PathVariable Long cDealId,@RequestBody RequestObject requestObj ) {
		ResponseModel resModel = new ResponseModel();		
		Field genericField = null;
		HashMap<String,Object> fieldsMap = null;
		List<Map<String,Object>> fieldsMapList = new ArrayList<>();
		List<ConvergenceDealArchive> archiveDeals = null;
		try {
			archiveDeals = convSvc.listArchiveDealsByCdealId(cDealId);
			resModel.setSuccess(true);
		   if(requestObj.getResponseFields()!=null && requestObj.getResponseFields().size()>0) {
				for(ConvergenceDealArchive archive : archiveDeals) {
					fieldsMap = new HashMap<String,Object>();
					for (String field : requestObj.getResponseFields()) {				
						try {
							genericField = archive.getClass().getDeclaredField(field);
							genericField.setAccessible(true);	
							if(field.equalsIgnoreCase("fileid"))
								fieldsMap.put(field, genericField.get(archive)!=null?genericField.get(archive).toString():null);
							else
								fieldsMap.put(field, genericField.get(archive)!=null?genericField.get(archive):null);
							} catch (IllegalArgumentException |IllegalAccessException |NoSuchFieldException |SecurityException e) {
								e.printStackTrace();
							} 
					}
					fieldsMapList.add(fieldsMap);
				}
				resModel.setResponse(fieldsMapList);
			}else			
				resModel.setResponse(archiveDeals);
		} catch (Exception e) {
			e.printStackTrace();
			resModel.setSuccess(false);
			resModel.setResponse(e.getMessage());
		}
		return resModel;
	}
	
	@RequestMapping(value = "/saveArchiveDeals/{cDealId}",method=RequestMethod.POST)
	public ResponseModel saveArchiveDeal(@PathVariable Long cDealId, @RequestBody List<ConvergenceDealArchive> cdealArchives) {
		ResponseModel resModel = new ResponseModel();
		try {			
				convSvc.saveCdealArchives(cDealId, cdealArchives);
				resModel.setSuccess(true);
				resModel.setResponse("Archives saved successfully");					
		}catch(Exception e) {
			resModel.setSuccess(false);
			resModel.setResponse("Error while saving archives--"+e.getMessage());
		}
		return resModel;
	}
	
	
	
	/**
	 * Added for real time indexing for convergence index
	 */
	
	@RequestMapping(value="/list/page/{pageNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PageResponseObject>  listActiveConvergenceDealsPage(@PathVariable  Integer pageNumber,
			@RequestParam(name="size",required=false) Integer pageSize,@RequestParam(name="indexParams",required=false) String indexParamsVal) throws Exception{
		
		Page<ConvergenceDeal> refData= null;
		PageResponseObject resObj = null;
		
		try{
			refData = convSvc.listActivePageData(pageNumber!=null&&pageNumber>0?pageNumber:1,pageSize);

			if(indexParamsVal!=null) {
				String indexParams[] =  indexParamsVal.split(",");
				if(indexParams.length>0) {
					List<Map<String,Object>> listMap = new ArrayList<>();
						if(refData.getContent()!=null && refData.getContent().size()>0){
						JsonArray convergenceDealObjArray = convertListToJsonArray(refData.getContent());
						GsonBuilder gsonBuilder = new GsonBuilder();
						Gson gson = gsonBuilder.create();
						Map<String,Object> fieldsMap = null;		
						for (JsonElement convJsonElem : convergenceDealObjArray) {
							fieldsMap = new HashMap<>();
							for (String indexParam : indexParams) {
								if(convJsonElem.getAsJsonObject().get(indexParam)!=null) {
									Object obj = gson.fromJson(convJsonElem.getAsJsonObject().get(indexParam).toString(), Object.class);
									fieldsMap.put(indexParam,obj);
								}
							}	
							listMap.add(fieldsMap);
						}	 
					}
	
					if(refData!=null){
						resObj = new PageResponseObject(refData.getNumberOfElements(), listMap, refData.getTotalElements(), refData.getTotalPages(), refData.getSize(), refData.getNumber()+1);
						return new ResponseEntity<PageResponseObject>(resObj, HttpStatus.OK);
					}else 
			            return new ResponseEntity<PageResponseObject>(HttpStatus.NO_CONTENT);	
				
				}else {
					System.out.println("Response fields does not exists");
					if(refData!=null){
						resObj = new PageResponseObject(refData.getNumberOfElements(), refData.getContent(), refData.getTotalElements(), refData.getTotalPages(), refData.getSize(), refData.getNumber()+1);
						return new ResponseEntity<PageResponseObject>(resObj, HttpStatus.OK);
					}else
			            return new ResponseEntity<PageResponseObject>(HttpStatus.NO_CONTENT);
				}
		  }else {
			  System.out.println("Response fields does not exists");
				if(refData!=null){
					resObj = new PageResponseObject(refData.getNumberOfElements(), refData.getContent(), refData.getTotalElements(), refData.getTotalPages(), refData.getSize(), refData.getNumber()+1);
					return new ResponseEntity<PageResponseObject>(resObj, HttpStatus.OK);
				}else
		            return new ResponseEntity<PageResponseObject>(HttpStatus.NO_CONTENT);
		  }
		}catch(IllegalArgumentException e){
			resObj = new PageResponseObject(e.getMessage());
			return new ResponseEntity<PageResponseObject>(resObj, HttpStatus.BAD_REQUEST);
		}catch(Exception e){	
			resObj = new PageResponseObject(e.getMessage());
			return new ResponseEntity<PageResponseObject>(resObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public  JsonArray convertListToJsonArray(List<ConvergenceDeal> convergenceDealList) throws Exception {
	
	   GsonBuilder gsonBuilder = new GsonBuilder();
	   Gson gson = gsonBuilder.create();
	   JsonArray convergenceDealObjArray = gson.toJsonTree(convergenceDealList).getAsJsonArray(); ;
       return convergenceDealObjArray;
	}
	

	

}
