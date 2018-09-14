package com.nbc.convergencerepo.domain.convgdeal;

public class Dashboard {
	long moatScore;
	double demoReachPercent;
	double demoAverageFrequency;
	String consumerTargets;
	double consumerReachPercent;
	double consumerAverageFrequency;
	boolean includeDashboard;

	public long getMoatScore() {
		return moatScore;
	}

	public void setMoatScore(long moatScore) {
		this.moatScore = moatScore;
	}

	public double getDemoReachPercent() {
		return demoReachPercent;
	}

	public void setDemoReachPercent(double demoReachPercent) {
		this.demoReachPercent = demoReachPercent;
	}

	public double getDemoAverageFrequency() {
		return demoAverageFrequency;
	}

	public void setDemoAverageFrequency(double demoAverageFrequency) {
		this.demoAverageFrequency = demoAverageFrequency;
	}

	public String getConsumerTargets() {
		return consumerTargets;
	}

	public void setConsumerTargets(String consumerTargets) {
		this.consumerTargets = consumerTargets;
	}

	public double getConsumerReachPercent() {
		return consumerReachPercent;
	}

	public void setConsumerReachPercent(double consumerReachPercent) {
		this.consumerReachPercent = consumerReachPercent;
	}

	public double getConsumerAverageFrequency() {
		return consumerAverageFrequency;
	}

	public void setConsumerAverageFrequency(double consumerAverageFrequency) {
		this.consumerAverageFrequency = consumerAverageFrequency;
	}

	public boolean isIncludeDashboard() {
		return includeDashboard;
	}

	public void setIncludeDashboard(boolean includeDashboard) {
		this.includeDashboard = includeDashboard;
	}

}
