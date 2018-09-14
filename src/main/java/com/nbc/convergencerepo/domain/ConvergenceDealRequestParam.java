package com.nbc.convergencerepo.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConvergenceDealRequestParam {

	private List<String> responseFields;

	@JsonProperty(required = false)
	public List<String> getResponseFields() {
		return responseFields;
	}

	public void setResponseFields(List<String> responseFields) {
		this.responseFields = responseFields;
	}
}
