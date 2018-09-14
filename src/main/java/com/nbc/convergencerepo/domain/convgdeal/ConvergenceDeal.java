package com.nbc.convergencerepo.domain.convgdeal;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nbc.convergencerepo.domain.AbstractDomain;
import com.nbc.convergencerepo.domain.admin.Property;
import com.nbc.convergencerepo.domain.admin.RatingStream;

@SuppressWarnings("serial")
@Document
public class ConvergenceDeal extends AbstractDomain<ConvergenceDeal> {

	private Agency agency;
	private Advertiser advertiser;
	private List<DigitalAccountExecutive> digitalAccountExecutive;
	private List<Property> digitalProperty;
	private List<RatingStream> digitalRatingSource;
	private String audienceGuarantee;
	private Boolean breakByPlan;
	private OnairTemplate onairTemplate;
	private Demo demo;
	private RevisionType revisionType;
	private LinearDetails linearDetails;
	private List<DigitalOrder> digitalOrders;
	private String createdDate;
	@JsonInclude(value = Include.NON_NULL)
	private Dashboard dashboard;

	private Boolean guaranteedCPM;

	@LastModifiedDate
	private LocalDateTime modifiedOn;

	private Division division;

	private Integer exportCount;

	private String status;

	private Integer archivedExportNum;

	@Override
	@org.springframework.data.annotation.Id
	public Long getId() {
		return id;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}

	public List<DigitalAccountExecutive> getDigitalAccountExecutive() {
		return digitalAccountExecutive;
	}

	public void setDigitalAccountExecutive(List<DigitalAccountExecutive> digitalAccountExecutive) {
		this.digitalAccountExecutive = digitalAccountExecutive;
	}

	public List<Property> getDigitalProperty() {
		return digitalProperty;
	}

	public void setDigitalProperty(List<Property> digitalProperty) {
		this.digitalProperty = digitalProperty;
	}

	public List<RatingStream> getDigitalRatingSource() {
		return digitalRatingSource;
	}

	public void setDigitalRatingSource(List<RatingStream> digitalRatingSource) {
		this.digitalRatingSource = digitalRatingSource;
	}

	public String getAudienceGuarantee() {
		return audienceGuarantee;
	}

	public void setAudienceGuarantee(String audienceGuarantee) {
		this.audienceGuarantee = audienceGuarantee;
	}

	public Boolean getBreakByPlan() {
		return breakByPlan;
	}

	public void setBreakByPlan(Boolean breakByPlan) {
		this.breakByPlan = breakByPlan;
	}

	public Demo getDemo() {
		return demo;
	}

	public void setDemo(Demo demo) {
		this.demo = demo;
	}

	public OnairTemplate getOnairTemplate() {
		return onairTemplate;
	}

	public void setOnairTemplate(OnairTemplate onairTemplate) {
		this.onairTemplate = onairTemplate;
	}

	public RevisionType getRevisionType() {
		return revisionType;
	}

	public void setRevisionType(RevisionType revisionType) {
		this.revisionType = revisionType;
	}

	public LinearDetails getLinearDetails() {
		return linearDetails;
	}

	public void setLinearDetails(LinearDetails linearDetails) {
		this.linearDetails = linearDetails;
	}

	public List<DigitalOrder> getDigitalOrders() {
		return digitalOrders;
	}

	public void setDigitalOrders(List<DigitalOrder> digitalOrders) {
		this.digitalOrders = digitalOrders;
	}

	public Dashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the division
	 */
	public Division getDivision() {
		return division;
	}

	/**
	 * @param division
	 *            the division to set
	 */
	public void setDivision(Division division) {
		this.division = division;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the guaranteedCPM
	 */
	public Boolean getGuaranteedCPM() {
		return guaranteedCPM;
	}

	/**
	 * @param guaranteedCPM
	 *            the guaranteedCPM to set
	 */
	public void setGuaranteedCPM(Boolean guaranteedCPM) {
		this.guaranteedCPM = guaranteedCPM;
	}

	/**
	 * @return the modifiedOn
	 */
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	/**
	 * @param modifiedOn
	 *            the modifiedOn to set
	 */
	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Integer getExportCount() {
		return exportCount;
	}

	public void setExportCount(Integer exportCount) {
		this.exportCount = exportCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getArchivedExportNum() {
		return archivedExportNum;
	}

	public void setArchivedExportNum(Integer archivedExportNum) {
		this.archivedExportNum = archivedExportNum;
	}

	@Override
	public ConvergenceDeal minify() {
		ConvergenceDeal deal = new ConvergenceDeal();
		deal.setId(this.getId());
		return deal;
	}

}
