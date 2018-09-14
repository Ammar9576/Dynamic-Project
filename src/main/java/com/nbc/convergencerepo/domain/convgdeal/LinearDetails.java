package com.nbc.convergencerepo.domain.convgdeal;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LinearDetails {
	private List<LinearPlan> plans;
	
	@JsonIgnore
	private Long portfolioId;


	public List<LinearPlan> getPlans() {
		return plans;
	}

	public void setPlans(List<LinearPlan> plans) {
		this.plans = plans;
	}

	public Long getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(Long portfolioId) {
		this.portfolioId = portfolioId;
	}


}
