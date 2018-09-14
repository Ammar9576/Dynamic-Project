package com.nbc.convergencerepo.domain.convgdeal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DigitalOrder {
	private Long orderId;
	private String orderName;
	private Long accountExecutiveId;
	private String accountExecutiveName;
	private String advertiserName;
	private String advertiserSFId;
	private String agencyName;
	private String agencySFId;
	
	private String salesTeamId;
	private String salesTeamName;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Long getAccountExecutiveId() {
		return accountExecutiveId;
	}

	public void setAccountExecutiveId(Long accountExecutiveId) {
		this.accountExecutiveId = accountExecutiveId;
	}

	public String getAccountExecutiveName() {
		return accountExecutiveName;
	}

	public void setAccountExecutiveName(String accountExecutiveName) {
		this.accountExecutiveName = accountExecutiveName;
	}

	public String getAdvertiserName() {
		return advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	public String getAdvertiserSFId() {
		return advertiserSFId;
	}

	public void setAdvertiserSFId(String advertiserSFId) {
		this.advertiserSFId = advertiserSFId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencySFId() {
		return agencySFId;
	}

	public void setAgencySFId(String agencySFId) {
		this.agencySFId = agencySFId;
	}

	/**
	 * @return the salesTeamId
	 */
	public String getSalesTeamId() {
		return salesTeamId;
	}

	/**
	 * @param salesTeamId the salesTeamId to set
	 */
	public void setSalesTeamId(String salesTeamId) {
		this.salesTeamId = salesTeamId;
	}

	/**
	 * @return the salesTeamName
	 */
	public String getSalesTeamName() {
		return salesTeamName;
	}

	/**
	 * @param salesTeamName the salesTeamName to set
	 */
	public void setSalesTeamName(String salesTeamName) {
		this.salesTeamName = salesTeamName;
	}

}
