package com.nbc.convergencerepo.service;


import java.io.IOException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.nbc.convergencerepo.domain.convgdeal.ConvergenceDeal;
import com.nbc.convergencerepo.domain.convgdeal.Demo;
import com.nbc.convergencerepo.domain.convgdeal.DigitalOrder;
import com.nbc.convergencerepo.domain.convgdeal.LinearPlan;
import com.nbc.convergencerepo.domain.convgdeal.RevisionType;
import com.nbc.convergencerepo.domain.plans.LinearSearchPayload;
import com.nbc.convergencerepo.domain.plans.MultiMatch;

@Service
@RefreshScope
public class ValidationService {

	@Value("${linearSearchApi}")
	private String linearSearchApi;

	@Value("${digitalOrderApi}")
	private String digitalOrderApi;

	@Value("${defaultAudienceGuar}")
	private String defaultAudienceGuar;

	@Value("${planStatusApi}")
	private String planStatusApi;

	@Value("${invlidPlanStatusCodes}")
	private String invlidPlanStatusCodes;
	
	@Value("${revTypeApi}")
	private String revTypeApi;	
	
	
	public void validateConvergeDealSave(ConvergenceDeal conv) throws ValidationException {
		boolean islinearPresent = false, isDigitalPresent = false;		
		if (conv.getName() != null) {
			// linear and digital sections
			if (conv.getLinearDetails() != null && conv.getLinearDetails().getPlans() != null
					&& conv.getLinearDetails().getPlans().size() > 0) {
				islinearPresent = true;
				if (conv.getLinearDetails().getPlans().size() > 1 && conv.getLinearDetails().getPortfolioId() < 1)
					throw new ValidationException("Portfolio id is missing");
			}
			if (conv.getDigitalOrders() != null && conv.getDigitalOrders().size() > 0)
				isDigitalPresent = true;
			
			if (!islinearPresent && !isDigitalPresent)
				throw new ValidationException("Both linear and digital sections are missing");
			
			if (conv.getId() != null && "java.lang.String".equalsIgnoreCase(conv.getId().getClass().toString()))
				throw new ValidationException("Deal id is a string. Should be a number");
			
			// edit flow //Removed as per 158504671
			/*if (conv.getId() != null) {			
				
				if (conv.getRevisionType() == null || (conv.getRevisionType() != null && StringUtils.isEmpty(conv.getRevisionType().getName())))
					throw new ValidationException("Revision type is missing");
				if (conv.getOnairTemplate() == null || (conv.getOnairTemplate() != null && conv.getOnairTemplate().getId()<=0))
					throw new ValidationException("OnAir template is missing");
				if (conv.getDemo() == null || (conv.getDemo() != null && conv.getDemo().getId()<=0))
					throw new ValidationException("Demo is missing");
				
				
				if (conv.getAdvertiser() == null || (conv.getAdvertiser() != null && StringUtils.isEmpty(conv.getAdvertiser().getSfId())))
					throw new ValidationException("Advertiser is missing");				
				if (conv.getAgency() == null || (conv.getAgency() != null && StringUtils.isEmpty(conv.getAgency().getSfId())))
					throw new ValidationException("Agency is missing");
				if (conv.getDigitalAccountExecutive() == null || (conv.getDigitalAccountExecutive()!=null && conv.getDigitalAccountExecutive().size()<=0))
					throw new ValidationException("Digital AE is missing");								
				
				if (conv.getDigitalRatingSource() != null && conv.getDigitalRatingSource().size()>0) {					
					conv.getDigitalRatingSource().stream().forEach(rs -> {
						if ("java.lang.String".equalsIgnoreCase(rs.getId().toString()))
							throw new ValidationException("Digital rating source id is a string. Should be a number");;																																				
					});
				} else
					throw new ValidationException("Digital rating source is missing");
				if (conv.getDigitalProperty() != null && conv.getDigitalProperty().size()>0) {
					conv.getDigitalProperty().stream().forEach(prop -> {
						if ("java.lang.String".equalsIgnoreCase(prop.getId().toString()))
							throw new ValidationException("Digital Property id is a string. Should be a number");;
					});
				} else
					throw new ValidationException("Digital property is missing");				
			}*/ // Removed as 158504671
			if (conv.getBreakByPlan() == null)
				conv.setBreakByPlan(false);
			if (conv.getAudienceGuarantee() == null
					|| (conv.getAudienceGuarantee() != null && conv.getAudienceGuarantee().trim().length() == 0))
				conv.setAudienceGuarantee(defaultAudienceGuar);			
		} else
			throw new ValidationException("Deal name is missing");
	}
	
	public List<String> validateConvergeDealFetch(ConvergenceDeal deal, boolean validate) throws Exception {
		List<String> validationMsgs = null;
		try {
			validationMsgs = new ArrayList<String>();
			List<LinearPlan> fetchedLinearPlans = fetchlLinearPlans(deal);
			validateLinearPlans(fetchedLinearPlans, deal,validationMsgs);
			if(validate) {				
				fillDigitalOrderDetails(deal);
				validateDigitalAe(deal,validationMsgs);
				validateAdvertiser(deal,validationMsgs);
				validateAgency(deal,validationMsgs);
				validateRevisionType(deal,validationMsgs);						
			}
			setValidDemo(deal);
		} catch (Exception e) {
			throw e;
		}
		return validationMsgs;
	}

	public List<LinearPlan> fetchlLinearPlans(ConvergenceDeal deal) throws Exception {
		List<LinearPlan> fetchedLinearPlans = new ArrayList<LinearPlan>();
		LinearPlan linearPlan = null;
		Long portfolioId;
		try {
			if (deal != null && deal.getLinearDetails() != null) {
				portfolioId = deal.getLinearDetails().getPortfolioId();
				
				if (portfolioId!=null && portfolioId>0)
					fetchedLinearPlans.addAll(getPlansFromSearchApi("portfolioId", portfolioId.toString(), deal));
				else {
					linearPlan = deal.getLinearDetails().getPlans()!=null?deal.getLinearDetails().getPlans().get(0):null;
					if(linearPlan!=null)
					 fetchedLinearPlans.addAll(getPlansFromSearchApi("planId", linearPlan.getId().toString(), deal));
				}				
				if(portfolioId<=0 && fetchedLinearPlans.get(0)!=null) {
					portfolioId = fetchedLinearPlans.get(0).getPortfolioId();
					if(portfolioId!=null && portfolioId>0) {
						fetchedLinearPlans.clear();
						fetchedLinearPlans.addAll(getPlansFromSearchApi("portfolioId", portfolioId.toString(), deal));
					}
				}								
			}
		} catch (Exception e) {
			throw e;
		}
		return fetchedLinearPlans;
	}

	private void validateLinearPlans(List<LinearPlan> fetchedLinearPlans, ConvergenceDeal deal, List<String> validationMsgs) throws Exception {
		String invalidPlans = new String();
		try {
			StringBuilder ids = new StringBuilder();
			if (fetchedLinearPlans != null && fetchedLinearPlans.size() > 0) {
				for (LinearPlan fetchedLinearPlan : fetchedLinearPlans) {
					for (LinearPlan linearPlan : deal.getLinearDetails().getPlans()) {
						if (fetchedLinearPlan.getId().equals(linearPlan.getId()))
							fetchedLinearPlan.setEnabled(linearPlan.getEnabled());						
					}
					ids.append(fetchedLinearPlan.getId()).append(",");
				}
				ids = ids.length()>0?ids.deleteCharAt(ids.length()-1):ids;
				invalidPlans = checkPlanStatus(ids.toString());
				if (invalidPlans != null && invalidPlans.length() > 0)
					validationMsgs.add("Plan/s " + invalidPlans + " in invalid state");
				deal.getLinearDetails().setPlans(fetchedLinearPlans);
			}else {
			   if(deal.getLinearDetails()!=null) {
				if(deal.getLinearDetails().getPortfolioId() <= 0)//if its not portfolio
					validationMsgs.add("Linear plan is no longer available");
				else
					validationMsgs.add("No plans available for this portfolio");
				
				deal.setLinearDetails(null);
			   }
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void fillDigitalOrderDetails(ConvergenceDeal deal) throws Exception {
		try {
			List<DigitalOrder> digitalOrders = null;
			StringBuilder planIds = new StringBuilder();
			if (deal != null && deal.getDigitalOrders() != null)
				digitalOrders = deal.getDigitalOrders();
			if (digitalOrders != null) {
				for (DigitalOrder digitalOrder : digitalOrders) {
					planIds.append(digitalOrder.getOrderId()).append(",");
				}
				if (planIds.length() > 0)
					planIds.deleteCharAt(planIds.length() - 1);
				deal.setDigitalOrders(getDigitalOrders(planIds.toString()));
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private List<LinearPlan> getPlansFromSearchApi(String inputField, String id, ConvergenceDeal deal)
			throws Exception {
		LinearSearchPayload payload = new LinearSearchPayload();
		MultiMatch multiMatch = new MultiMatch();
		List<String> fields = null;
		Gson gson = new Gson();
		List<LinearPlan> linearPlans = new ArrayList<LinearPlan>();
		Map linearMap = null;
		List<LinkedTreeMap> resultList;
		String json=null;
		LinearPlan linearPlan = null;
		try {
			multiMatch.setInput(id);
			fields = new ArrayList<String>();
			fields.add(inputField);
			multiMatch.setFields(fields);
			payload.setMultiMatch(multiMatch);
			String payLoadStr = gson.toJson(payload);
			json = invokeService(linearSearchApi, HttpMethod.POST, payLoadStr);
			linearMap = gson.fromJson(json, HashMap.class);
			resultList = (ArrayList<LinkedTreeMap>) linearMap.get("result");
			for (LinkedTreeMap linearDataMap : resultList) {
				json = gson.toJson(linearDataMap);
				linearPlan = gson.fromJson(json, LinearPlan.class);
				linearPlan.setId(Long.valueOf(linearDataMap.get("planId").toString()));
				linearPlan.setPlanId(Long.valueOf(linearDataMap.get("planId").toString()));				
				//linearPlan.setName(linearPlan.getPlanName());
				linearPlan.setEnabled(true);//enabling it by deafault
				linearPlans.add(linearPlan);
			}
		} catch (Exception e) {
			throw e;
		}
		return linearPlans;
	}

	private List<DigitalOrder> getDigitalOrders(String ids) throws Exception {
		Gson gson = new Gson();
		List<LinkedTreeMap> orderMap = null;
		ObjectMapper mapper = new ObjectMapper();
		String json;
		Map<String, List> digitalOrdersMap = null;
		List<DigitalOrder> digitalOrders = new ArrayList<DigitalOrder>();
		try {
			json = invokeService(digitalOrderApi + ids, HttpMethod.GET, null);
			digitalOrdersMap = gson.fromJson(json, Map.class);
			if (digitalOrdersMap != null)
				orderMap = digitalOrdersMap.get("orders");			
			orderMap.stream().forEach(map -> digitalOrders.add(mapper.convertValue(map, DigitalOrder.class)));			
		} catch (Exception e) {
			throw e;
		}
		return digitalOrders;
	}

	private String checkPlanStatus(String ids) throws Exception {
		String jsonStr = null;
		Gson gson = new Gson();
		List<LinkedTreeMap> statusList = null;
		// List<Long> invalidPlans = new ArrayList<Long>();
		StringBuilder invalidPlans = new StringBuilder();
		try {
			jsonStr = invokeService(planStatusApi + ids, HttpMethod.GET, null);
			statusList = gson.fromJson(jsonStr, ArrayList.class);
			for (LinkedTreeMap map : statusList) {
				if (map != null && map.get("planStatusCode") != null
						&& invlidPlanStatusCodes.contains(map.get("planStatusCode").toString())) {
					invalidPlans.append(map.get("id")).append(",");
				}
			}
			if (invalidPlans.length() > 0)
				invalidPlans.deleteCharAt(invalidPlans.length() - 1);
		} catch (Exception e) {
			throw e;
		}
		return invalidPlans.toString();
	}

	public String invokeService(String api, HttpMethod method, String payload)
			throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		ResponseEntity response = null;
		String jsonStr = null;
		Proxy proxy = null;
		HttpHeaders headers = null;
		Gson gson = null;
		RestTemplate rest = null;
		HttpEntity<String> request = null;
		try {
			headers = new HttpHeaders();
			rest = new RestTemplate();
			headers.add("Accept", "application/json");
			headers.add("Content-Type", "application/json");
			request = new HttpEntity<String>(payload, headers);
			URI uri = new URI(api);
			response = rest.exchange(uri, method, request, Object.class);
			gson = new Gson();
			jsonStr = gson.toJson(response.getBody());
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	private void validateDigitalAe(ConvergenceDeal deal, List<String> validationMsgs) throws Exception {
		boolean isValid=false;
		try {
			if(deal!=null && deal.getDigitalOrders()!=null && deal.getDigitalOrders().size()>0) {
				for(DigitalOrder order : deal.getDigitalOrders()) {
					if(order!=null && deal.getDigitalAccountExecutive()!=null && deal.getDigitalAccountExecutive().size()>0 && deal.getDigitalAccountExecutive().get(0).getId()==order.getAccountExecutiveId()) {
						isValid = true;
					}
				}
				if(!isValid && deal.getDigitalAccountExecutive()!=null) {
					deal.setDigitalAccountExecutive(null);
					validationMsgs.add("Selected Digital AE is not valid");
				}
			}
			
		}catch(Exception e) {
			throw e;
		}
	}
	
	private void validateAdvertiser(ConvergenceDeal deal, List<String> validationMsgs) throws Exception {
		boolean isValid=false;
		try {			
			if(deal!=null && deal.getAdvertiser()!=null) { 
				if(deal.getLinearDetails()!=null && deal.getLinearDetails().getPlans()!=null && deal.getLinearDetails().getPlans().size()>0) {
					for(LinearPlan linearPlan : deal.getLinearDetails().getPlans()) {
						if(linearPlan!=null && linearPlan.getAdvertiserSFId()!=null && linearPlan.getAdvertiserSFId().equals(deal.getAdvertiser().getSfId())) 
							isValid =true;						
					}					
				}
				if(deal.getDigitalOrders()!=null && deal.getDigitalOrders().size()>0) {
					for(DigitalOrder order : deal.getDigitalOrders()) {
						if(order!=null && order.getAdvertiserSFId().equals(deal.getAdvertiser().getSfId()))
							isValid = true;
					}
				}
				if(!isValid && deal.getAdvertiser()!=null) {
					deal.setAdvertiser(null);
					validationMsgs.add("Selected Advertiser is not valid");
				}
		  }
		}catch(Exception e) {
			throw e;
		}
	}
	
	private void validateAgency(ConvergenceDeal deal, List<String> validationMsgs) {
		boolean isValid=false;
		try {			
			if(deal!=null && deal.getAgency()!=null) { 
				if(deal.getLinearDetails()!=null && deal.getLinearDetails().getPlans()!=null && deal.getLinearDetails().getPlans().size()>0) {
					for(LinearPlan linearPlan : deal.getLinearDetails().getPlans()) {
						if(linearPlan!=null && linearPlan.getAgencySFId()!=null && linearPlan.getAgencySFId().equals(deal.getAgency().getSfId())) 
							isValid =true;						
					}					
				}
				if(deal.getDigitalOrders()!=null && deal.getDigitalOrders().size()>0) {
					for(DigitalOrder order : deal.getDigitalOrders()) {
						if(order!=null && order.getAgencySFId().equals(deal.getAgency().getSfId()))
							isValid = true;
					}
				}
				if(!isValid && deal.getAgency()!=null) {
					deal.setAgency(null);
					validationMsgs.add("Selected Agency is not valid");
				}
		  }
		}catch(Exception e) {
			throw e;
		}
	}
	
	private void validateRevisionType(ConvergenceDeal deal, List<String> validationMsgs) throws Exception {
		boolean isValid=false;
		String json = null;
		List<RevisionType> revTypeList = null;
		Gson gson = new Gson();
		StringBuilder ids = new StringBuilder();
		try {
			if(deal!=null && deal.getRevisionType()!=null && deal.getLinearDetails()!=null && deal.getLinearDetails().getPlans()!=null) {
				deal.getLinearDetails().getPlans().stream().forEach(plan -> ids.append(plan.getId()).append(","));
				if(ids.length()>0)
					ids.deleteCharAt(ids.length()-1);
				
				json = invokeService(revTypeApi+ids, HttpMethod.GET, null); // logic for hold & order archives
				revTypeList = gson.fromJson(json,new TypeToken<List<RevisionType>>(){}.getType());				
				for(RevisionType revType : revTypeList) {
					if(revType!=null && revType.getId()==deal.getRevisionType().getId())
						isValid = true;
				}			
			}
			if(!isValid && deal.getRevisionType()!=null) {
				deal.setRevisionType(null);
				validationMsgs.add("Selected Revision Type is not valid");
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	private void setValidDemo(ConvergenceDeal deal) throws Exception{
		LinearPlan plan = null;
		Demo demo = new Demo();
		try {
			if(deal!=null && deal.getLinearDetails()!=null && deal.getLinearDetails().getPlans()!=null && deal.getLinearDetails().getPlans().size()>0) {
				plan = deal.getLinearDetails().getPlans().get(0);
				if(deal.getLinearDetails().getPortfolioId() > 0) {
					demo.setId(plan.getPfTargetGroupId());
					demo.setName(plan.getPfTargetGroupName());
				}else {
					demo.setId(plan.getTargetGroupId());
					demo.setName(plan.getTargetGroupName());
				}
				deal.setDemo(demo);
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
