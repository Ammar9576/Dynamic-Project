package com.nbc.convergencerepo.domain.plans;

public class LinearSearchPayload {
	private String[] resultFields = { "score", "planId", "planName", "planVersionNumber", "channelId", "channelName",
			"portfolioId", "pfTargetGroupId", "pfTargetGroupName", "marketPlaceId", "marketPlaceName", "agencyId",
			"agencySFId", "agencyName", "advertiserId", "advertiserSFId", "advertiserName", "targetGroupId",
			"targetGroupName", "ratingStreamId", "ratingStreamName", "accountExecutiveId", "accountExecutiveName","pfAccountExecutiveId","pfAccountExecutiveName","pfAdvertiserId", "pfAdvertiserName","pfAdvertiserSFId",
			"pfAgencyId","pfAgencyName","pfAgencySFId","pfMarketPlaceId","pfMarketPlaceName","pfRatingStreamId","pfRatingStreamName","planStatusName"};
	private MultiMatch multiMatch;

	public String[] getResultFields() {
		return resultFields;
	}

	public void setResultFields(String[] resultFields) {
		this.resultFields = resultFields;
	}

	public MultiMatch getMultiMatch() {
		return multiMatch;
	}

	public void setMultiMatch(MultiMatch multiMatch) {
		this.multiMatch = multiMatch;
	}

}
