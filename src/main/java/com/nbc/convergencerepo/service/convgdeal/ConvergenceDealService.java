package com.nbc.convergencerepo.service.convgdeal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.nbc.convergencerepo.domain.ResponseModel;
import com.nbc.convergencerepo.domain.convgdeal.ConvergenceDeal;
import com.nbc.convergencerepo.domain.convgdeal.ConvergenceDealArchive;
import com.nbc.convergencerepo.repository.convgdeal.ConvergenceDealArchiveRepository;
import com.nbc.convergencerepo.repository.convgdeal.ConvergenceDealRepository;
import com.nbc.convergencerepo.service.SequenceService;
import com.nbc.convergencerepo.service.ValidationService;

@Service
public class ConvergenceDealService {

  @Autowired
	GridFsTemplate gridFsTemplate;

  @Autowired
	ConvergenceDealRepository convRepo;

	@Autowired
	ConvergenceDealArchiveRepository convArchiveRepo;

	@Autowired
	ValidationService validateSvc;

	@Autowired
	SequenceService seqSvc;
	
	@Value("${reportName}")
	String reportName;
	
	@Value("${statusWorking}")
	String statusWorking;
	
	@Value("${statusOrder}")
	String statusOrder;
	
	@Value("${indexcDealApi}")
	String indexcDealApi;
	
	@Value("${defaultPageSize}")
	private String PAGE_SIZE;
	
	/*@Value("${maxDataLimit}")
	private int MAX_PAGE_SIZE;*/
	
	
	private Map<String, String> paramNameMap;
	
	public ResponseModel listById(Long id) throws Exception {
		List<String> validationMsgs = new ArrayList<String>();
		ResponseModel model = new ResponseModel();
		ConvergenceDeal deal = convRepo.findOne(id);
		validationMsgs = validateSvc.validateConvergeDealFetch(deal, true);		
		//setExportCount(deal);
		deal.setDashboard(null);
		model.setResponse(deal);
		model.setValidationMessages(validationMsgs);
		return model;
	}

	public ResponseModel listRawById(Long id) throws Exception {
		List<String> validationMsgs = new ArrayList<String>();
		ResponseModel model = new ResponseModel();
		ConvergenceDeal deal = convRepo.findOne(id);		
		validationMsgs = validateSvc.validateConvergeDealFetch(deal,false);		
		deal.setDashboard(null);
		model.setResponse(deal);
		model.setValidationMessages(validationMsgs);
		return model;
	}

	public ResponseModel listWithDashboard(Long id) throws Exception {
		List<String> validationMsgs = new ArrayList<String>();
		ResponseModel model = new ResponseModel();
		ConvergenceDeal deal = convRepo.findOne(id);
		validationMsgs = validateSvc.validateConvergeDealFetch(deal, false);
		model.setResponse(deal);
		model.setValidationMessages(validationMsgs);
		return model;
	}

	public List<ConvergenceDeal> listAll() {
		List<ConvergenceDeal> deals =convRepo.findAll();
		if (deals!=null && deals.size()>0) 
			deals.stream().forEach(deal -> { deal.getLinearDetails().getPlans().stream().forEach(plan -> {plan.setPlanId(plan.getId());});});								
		return deals;
	}

	public List<ConvergenceDeal> listActive() {
		List<ConvergenceDeal> deals =convRepo.findAll(new ConvergenceDeal().listActive()); 
		if (deals!=null && deals.size()>0) 
			deals.stream().forEach(deal -> { deal.getLinearDetails().getPlans().stream().forEach(plan -> {plan.setPlanId(plan.getId());});});								
		return deals;
	}

	public ConvergenceDeal save(ConvergenceDeal conv) throws Exception {
		ConvergenceDeal deal = null, existingDeal = null;
		DateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
		try {
			validateSvc.validateConvergeDealSave(conv);
			if (conv != null) {
				if (conv.getId() == null) {
					conv.setId(seqSvc.getUniqueId());
					sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
					conv.setCreatedDate(sdf.format(new Date()));
					conv.setExportCount(0);
					conv.setArchivedExportNum(0);
					conv.setStatus(statusWorking);
				} else {
					existingDeal = convRepo.findOne(conv.getId());
					if(existingDeal!=null) {
						conv.setCreatedDate(existingDeal.getCreatedDate());						
						conv.setExportCount(existingDeal.getExportCount()!=null?existingDeal.getExportCount():0);
						conv.setArchivedExportNum(existingDeal.getArchivedExportNum()!=null?existingDeal.getArchivedExportNum():0);
						conv.setStatus(existingDeal.getStatus()!=null?existingDeal.getStatus():statusWorking);
					}
				}				
			}
			deal = convRepo.save(conv);
			// Index cDeal to ES
			indexCDeal(deal.getId());
		} catch (Exception e) {
			throw e;
		}
		return deal;
	}

	public void saveAll(List<ConvergenceDeal> convList) {
		try {
			convRepo.save(convList);
		} catch (Exception e) {
			throw e;
		}
	}

	public void delete(ConvergenceDeal conv) {
		try {
			convRepo.delete(conv);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> stripConvergeDeal(List<ConvergenceDeal> deals) {
		List<Map<String, Object>> liteConvergeDeals = new ArrayList<Map<String, Object>>();
		for (ConvergenceDeal convergenceDeal : deals) {
			Map<String, Object> liteDeal = new LinkedHashMap<String, Object>();
			liteDeal.put("id", convergenceDeal.getId());
			liteDeal.put("name", convergenceDeal.getName());
			liteDeal.put("agencyName",
					convergenceDeal.getAgency() != null ? convergenceDeal.getAgency().getName() : null);
			liteDeal.put("advertiserName",
					convergenceDeal.getAdvertiser() != null ? convergenceDeal.getAdvertiser().getName() : null);
			liteDeal.put("demo", convergenceDeal.getDemo() != null ? convergenceDeal.getDemo().getName() : null);
			liteDeal.put("createdDate",
					convergenceDeal.getCreatedDate() != null ? convergenceDeal.getCreatedDate() : null);
			liteConvergeDeals.add(liteDeal);
		}
		return liteConvergeDeals;

	}
	
	public void setPdfContent(Long cDealId, String sso, String fileName, byte[] pdfContent) throws Exception {
		ConvergenceDealArchive archiveDeal = null;
		List<ConvergenceDealArchive> archiveDeals = convArchiveRepo.findBycDealIdAndSsoAndFileIdIsNull(cDealId, sso);
		if(archiveDeals!=null && archiveDeals.size()>0) {
				//List<ConvergenceDealArchive> filteredArchiveDeals = archiveDeals.stream().filter(deal -> deal.getExportNo().equals(exportNum)).collect(Collectors.toList());
			Optional<ConvergenceDealArchive> archiveDealObj = archiveDeals.stream().max(Comparator.comparing(ConvergenceDealArchive::getExportNo));
			if(archiveDealObj.isPresent()) {
				archiveDeal = archiveDealObj.get();
				archiveDeal.setFileId(uploadPdf(fileName, pdfContent,cDealId,archiveDeal.getExportNo()));
				convArchiveRepo.save(archiveDeal);
			}else {
				throw new Exception("No archives found for CDeal---"+cDealId+" and sso---"+sso);
			}
		}else
			throw new Exception("No archives found for CDeal---"+cDealId+" and sso---"+sso);		
	}
	
	private Object uploadPdf(String fileName, byte[] pdfContent,Long cDealId, Integer exportNum) throws Exception{
		GridFSFile file = null;
		try {
		InputStream is = new ByteArrayInputStream(pdfContent);	
		if(StringUtils.isEmpty(fileName))
		   fileName = reportName+"-["+cDealId+"-"+exportNum+"].pdf";
		file = gridFsTemplate.store(is, fileName);
		file.save();
		}
		catch(Exception e) {
			throw new Exception("Error while saving pdf in mongo---"+e.getMessage()); 			
		}
		return file.getId();
	}
	
	public byte[] getPdfContent(ObjectId fileId) throws IOException {		
		GridFSDBFile pdfFile = null;
		ByteArrayOutputStream  os =new ByteArrayOutputStream();
		try {		
			pdfFile = gridFsTemplate.findOne(Query.query(GridFsCriteria.where("_id").is(fileId)));
			pdfFile.writeTo(os);			
		}catch(Exception e) {
			throw e;
		}
		return os.toByteArray();
	}

	public Integer saveCdealArchive(Long dealId, String sso, String notes) throws Exception {
		List<ConvergenceDealArchive> allExports = null;
		ConvergenceDeal deal = convRepo.findOne(dealId);
		ConvergenceDealArchive archiveDeal = populateArchiveDeal(deal, sso, notes);			
		convArchiveRepo.save(archiveDeal);
		allExports = convArchiveRepo.findBycDealId(dealId);
		if(allExports!=null)
			deal.setExportCount(allExports.size());
		convRepo.save(deal);
		return archiveDeal.getExportNo();
	}

	private ConvergenceDealArchive populateArchiveDeal(ConvergenceDeal deal, String sso, String notes) throws Exception {
		DateFormat sdf = new SimpleDateFormat("MM/dd/YYYY h:mm a");
		sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		ConvergenceDealArchive archiveDeal = new ConvergenceDealArchive();
		archiveDeal.setId(seqSvc.getUniqueId());
		archiveDeal.setArchiveFlag(false);
		archiveDeal.setcDealId(deal.getId());
		archiveDeal.setEnabled(true);
		archiveDeal.setExportDateTime(sdf.format(new Date()));
		setExportIdAndStatus(archiveDeal, deal.getId());		
		archiveDeal.setcDealParams(deal);		
		setSsoAndName(sso,archiveDeal);		
		return archiveDeal;
	}

	private void setExportIdAndStatus(ConvergenceDealArchive cDealArchive, Long cDealId) {
		ConvergenceDealArchive latestcDealArchive = null;
		List<ConvergenceDealArchive> archiveDeals = convArchiveRepo.findBycDealId(cDealId);
		if (archiveDeals != null && archiveDeals.size() > 0) {
			Optional<ConvergenceDealArchive> latestcDealArchiveObj = archiveDeals.stream()
					.max(Comparator.comparing(ConvergenceDealArchive::getExportNo));		
			if (latestcDealArchiveObj.isPresent()) {
				latestcDealArchive = latestcDealArchiveObj.get();
				cDealArchive.setExportNo(latestcDealArchive.getExportNo() + 1);				
			}
		} else {
			cDealArchive.setExportNo(1);			
		}
	}
	
	private void setSsoAndName(String sso, ConvergenceDealArchive archiveDeal) {
		if(sso!=null) {
			archiveDeal.setFirstName(sso.substring(0, sso.indexOf(".")));
			archiveDeal.setLastName(sso.substring(sso.indexOf('.')+1,sso.indexOf('(')));
			archiveDeal.setUserName(archiveDeal.getFirstName().charAt(0)+". "+archiveDeal.getLastName());
			archiveDeal.setSso(sso.substring(sso.indexOf('(')+1,sso.indexOf(')')));
		}	   
	}
	
	public void deleteCdealArchive(Long cDealId, Integer exportNo) throws Exception {
		List<ConvergenceDealArchive> archiveDeals = convArchiveRepo.findBycDealIdAndExportNo(cDealId,exportNo);
		if(archiveDeals!=null && archiveDeals.size()>0) {
			if(archiveDeals.size()>1)
				throw new Exception("More than one archive deal is found for the given exportno");			
			convArchiveRepo.delete(archiveDeals.get(0));
		}else
			throw new Exception("No archive deal is found for the given cdealId and exportno");
	}
	
	public List<ConvergenceDealArchive> listArchiveDealsByCdealId(Long cDealId) throws Exception {
		List<ConvergenceDealArchive> archiveDeals = convArchiveRepo.findBycDealId(cDealId);					
		return archiveDeals;
	}	
	
	public void saveCdealArchives(Long cDealId, List<ConvergenceDealArchive> cdealArchives) throws Exception {		
		List<ConvergenceDealArchive> archiveDeals = null, allArchiveDeals =null;		
		ConvergenceDeal convDeal = null;		
		int archivedExportNo=0;
		try {						
				convDeal = convRepo.findOne(cDealId);
			 if(convDeal!=null) {
				allArchiveDeals = convArchiveRepo.findBycDealId(convDeal.getId());
			 if(allArchiveDeals!=null && allArchiveDeals.size()>0) {
				 if(cdealArchives!=null &&  cdealArchives.size()>0) {
					for(ConvergenceDealArchive cdealArchive: cdealArchives) {
					 for(ConvergenceDealArchive export :allArchiveDeals) {
					  if(export.getExportNo().equals(cdealArchive.getExportNo())) {					  
							if(cdealArchive.getNotes()!=null)
								export.setNotes(cdealArchive.getNotes());
							if(cdealArchive.getArchiveFlag()!=null)
								export.setArchiveFlag(cdealArchive.getArchiveFlag());
							break;
	   			       }			 		  		  
					}
				}
				archiveDeals = allArchiveDeals.stream().filter(obj -> obj.getArchiveFlag()).collect(Collectors.toList());
				if(archiveDeals!=null && archiveDeals.size()>1)
					throw new Exception("More than one export is being archived");
				archivedExportNo = archiveDeals!=null && archiveDeals.size()==1?archiveDeals.get(0).getExportNo():0;				
				convDeal.setArchivedExportNum(archivedExportNo);
				convDeal.setStatus(archiveDeals!=null && archiveDeals.size()==1?statusOrder:statusWorking);
				convDeal.setExportCount(allArchiveDeals.size());
				convArchiveRepo.save(allArchiveDeals);
				convRepo.save(convDeal);
			 }
			}else 	
				throw new Exception("No archives found for this cDeal");	
		}else
			throw new Exception("Invalid cDeal");
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void indexCDeal(Long dealId) throws Exception {
		try {
			RestTemplate restTemplate = new RestTemplate();                
	        restTemplate.getForObject(indexcDealApi+dealId,HashMap.class);
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	public Page<ConvergenceDeal> listActivePageData(Integer pageNumber,Integer pageSize) {
			
			Page<ConvergenceDeal> convergenceDealsData = null;
			PageRequest request = null;
			Page<ConvergenceDeal> afterConvd = null;
			
			request =   new PageRequest(pageNumber - 1, !StringUtils.isEmpty(pageSize)&&pageSize>0?pageSize:Integer.parseInt(PAGE_SIZE),Direction.ASC,"id");
			convergenceDealsData = convRepo.findAll(new ConvergenceDeal().listActive(),request);
		
			List<ConvergenceDeal> deals  = convergenceDealsData.getContent();
			if (deals!=null && deals.size()>0) 
			deals.stream().forEach(deal -> { deal.getLinearDetails().getPlans().stream().forEach(plan -> {plan.setPlanId(plan.getId());});});
			
			afterConvd = new PageImpl<>(deals, request,convergenceDealsData.getTotalElements() );
			return afterConvd;
		
		
	}
	
}