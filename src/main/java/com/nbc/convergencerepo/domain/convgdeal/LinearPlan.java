package com.nbc.convergencerepo.domain.convgdeal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nbc.convergencerepo.domain.AbstractDomain;


@JsonIgnoreProperties(ignoreUnknown=true)
public class LinearPlan extends AbstractDomain<LinearPlan> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long planId;
	private String planName;
	private Long accountExecutiveId;
	private String accountExecutiveName;
	private Long advertiserId;
	private String advertiserName;
	private String advertiserSFId;
	private Long agencyId;
	private String agencyName;
	private String agencySFId;
	private Long channelId;
	private String channelName;
	private Long marketPlaceId;
	private String marketPlaceName;
	private Long pfAccountExecutiveId;
	private String pfAccountExecutiveName;
	private Long pfAdvertiserId;
	private String pfAdvertiserName;
	private String pfAdvertiserSFId;
	private Long pfAgencyId;
	private String pfAgencyName;
	private String pfAgencySFId;
	private Long pfMarketPlaceId;
	private String pfMarketPlaceName;
	private Long pfRatingStreamId;
	private String pfRatingStreamName;
	private Long pfTargetGroupId;
	private String pfTargetGroupName;
	private String planVersionNumber;
	private Long portfolioId;
	private Long ratingStreamId;
	private String ratingStreamName;
	private Long targetGroupId;
	private String targetGroupName;
	private String planStatusName;

	public String getPlanVersionNumber() {
		return planVersionNumber;
	}

	public void setPlanVersionNumber(String planVersionNumber) {
		this.planVersionNumber = planVersionNumber;
	}

	public String getAdvertiserSFId() {
		return advertiserSFId;
	}

	public void setAdvertiserSFId(String advertiserSFId) {
		this.advertiserSFId = advertiserSFId;
	}

	public String getMarketPlaceName() {
		return marketPlaceName;
	}

	public void setMarketPlaceName(String marketPlaceName) {
		this.marketPlaceName = marketPlaceName;
	}

	public Long getMarketPlaceId() {
		return marketPlaceId;
	}

	public void setMarketPlaceId(Long marketPlaceId) {
		this.marketPlaceId = marketPlaceId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public String getTargetGroupName() {
		return targetGroupName;
	}

	public void setTargetGroupName(String targetGroupName) {
		this.targetGroupName = targetGroupName;
	}

	public Long getAccountExecutiveId() {
		return accountExecutiveId;
	}

	public void setAccountExecutiveId(Long accountExecutiveId) {
		this.accountExecutiveId = accountExecutiveId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAdvertiserName() {
		return advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	public String getRatingStreamName() {
		return ratingStreamName;
	}

	public void setRatingStreamName(String ratingStreamName) {
		this.ratingStreamName = ratingStreamName;
	}

	public Long getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}

	public String getAgencySFId() {
		return agencySFId;
	}

	public void setAgencySFId(String agencySFId) {
		this.agencySFId = agencySFId;
	}

	public String getPfTargetGroupName() {
		return pfTargetGroupName;
	}

	public void setPfTargetGroupName(String pfTargetGroupName) {
		this.pfTargetGroupName = pfTargetGroupName;
	}

	public String getAccountExecutiveName() {
		return accountExecutiveName;
	}

	public void setAccountExecutiveName(String accountExecutiveName) {
		this.accountExecutiveName = accountExecutiveName;
	}

	public Long getTargetGroupId() {
		return targetGroupId;
	}

	public void setTargetGroupId(Long targetGroupId) {
		this.targetGroupId = targetGroupId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Long getRatingStreamId() {
		return ratingStreamId;
	}

	public void setRatingStreamId(Long ratingStreamId) {
		this.ratingStreamId = ratingStreamId;
	}

	public Long getPfTargetGroupId() {
		return pfTargetGroupId;
	}

	public void setPfTargetGroupId(Long pfTargetGroupId) {
		this.pfTargetGroupId = pfTargetGroupId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * public Long getPlanId() { return planId; }
	 * 
	 * public void setPlanId(Long planId) { this.planId = planId; }
	 */

	public Long getPfAccountExecutiveId() {
		return pfAccountExecutiveId;
	}

	public void setPfAccountExecutiveId(Long pfAccountExecutiveId) {
		this.pfAccountExecutiveId = pfAccountExecutiveId;
	}

	public String getPfAccountExecutiveName() {
		return pfAccountExecutiveName;
	}

	public void setPfAccountExecutiveName(String pfAccountExecutiveName) {
		this.pfAccountExecutiveName = pfAccountExecutiveName;
	}

	public Long getPfAdvertiserId() {
		return pfAdvertiserId;
	}

	public void setPfAdvertiserId(Long pfAdvertiserId) {
		this.pfAdvertiserId = pfAdvertiserId;
	}

	public String getPfAdvertiserName() {
		return pfAdvertiserName;
	}

	public void setPfAdvertiserName(String pfAdvertiserName) {
		this.pfAdvertiserName = pfAdvertiserName;
	}

	public String getPfAdvertiserSFId() {
		return pfAdvertiserSFId;
	}

	public void setPfAdvertiserSFId(String pfAdvertiserSFId) {
		this.pfAdvertiserSFId = pfAdvertiserSFId;
	}

	public Long getPfAgencyId() {
		return pfAgencyId;
	}

	public void setPfAgencyId(Long pfAgencyId) {
		this.pfAgencyId = pfAgencyId;
	}

	public String getPfAgencyName() {
		return pfAgencyName;
	}

	public void setPfAgencyName(String pfAgencyName) {
		this.pfAgencyName = pfAgencyName;
	}

	public String getPfAgencySFId() {
		return pfAgencySFId;
	}

	public void setPfAgencySFId(String pfAgencySFId) {
		this.pfAgencySFId = pfAgencySFId;
	}

	public Long getPfMarketPlaceId() {
		return pfMarketPlaceId;
	}

	public void setPfMarketPlaceId(Long pfMarketPlaceId) {
		this.pfMarketPlaceId = pfMarketPlaceId;
	}

	public String getPfMarketPlaceName() {
		return pfMarketPlaceName;
	}

	public void setPfMarketPlaceName(String pfMarketPlaceName) {
		this.pfMarketPlaceName = pfMarketPlaceName;
	}

	public Long getPfRatingStreamId() {
		return pfRatingStreamId;
	}

	public void setPfRatingStreamId(Long pfRatingStreamId) {
		this.pfRatingStreamId = pfRatingStreamId;
	}

	public String getPfRatingStreamName() {
		return pfRatingStreamName;
	}

	public void setPfRatingStreamName(String pfRatingStreamName) {
		this.pfRatingStreamName = pfRatingStreamName;
	}

	public Long getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(Long portfolioId) {
		this.portfolioId = portfolioId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getPlanStatusName() {
		return planStatusName;
	}

	public void setPlanStatusName(String planStatusName) {
		this.planStatusName = planStatusName;
	}

	@Override
	@JsonIgnore
	public Long getId() {
		return id;
	}

	@Override
	@JsonIgnore
	public String getName() {
		return name;
	}

	@Override
	public LinearPlan minify() {
		// TODO Auto-generated method stub
		return null;
	}

}
