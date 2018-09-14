package com.nbc.convergencerepo.domain.convgdeal;

import org.springframework.data.mongodb.core.mapping.Document;
import com.nbc.convergencerepo.domain.AbstractDomain;

@SuppressWarnings("serial")
@Document
public class ConvergenceDealArchive extends AbstractDomain<ConvergenceDealArchive> {

	private Integer exportNo;
	private String sso;
	private String firstName;	
	private String lastName;
	private String userName;
	private String notes;
	private String exportDateTime;
	private Object fileId;
	private Boolean archiveFlag;
	private Long cDealId;
	private ConvergenceDeal cDealParams;

	public String getSso() {
		return sso;
	}

	public void setSso(String sso) {
		this.sso = sso;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Object getFileId() {
		return fileId;
	}

	public void setFileId(Object fileId) {
		this.fileId = fileId;
	}

	public Boolean getArchiveFlag() {
		return archiveFlag;
	}

	public void setArchiveFlag(Boolean archiveFlag) {
		this.archiveFlag = archiveFlag;
	}

	public Long getcDealId() {
		return cDealId;
	}

	public void setcDealId(Long cDealId) {
		this.cDealId = cDealId;
	}

	public Integer getExportNo() {
		return exportNo;
	}

	public void setExportNo(Integer exportNo) {
		this.exportNo = exportNo;
	}

	public String getExportDateTime() {
		return exportDateTime;
	}

	public void setExportDateTime(String exportDateTime) {
		this.exportDateTime = exportDateTime;
	}

	public ConvergenceDeal getcDealParams() {
		return cDealParams;
	}

	public void setcDealParams(ConvergenceDeal cDealParams) {
		this.cDealParams = cDealParams;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public ConvergenceDealArchive minify() {
		// TODO Auto-generated method stub
		return null;
	}

}
