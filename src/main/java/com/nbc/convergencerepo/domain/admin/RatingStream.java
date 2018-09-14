package com.nbc.convergencerepo.domain.admin;

import org.springframework.data.mongodb.core.mapping.Document;

import com.nbc.convergencerepo.domain.AbstractDomain;

@Document
public class RatingStream extends AbstractDomain<RatingStream> {

	@Override
	@org.springframework.data.annotation.Id
	public Long getId() {
		return id;
	}

	@Override
	public RatingStream minify() {
		RatingStream rs = new RatingStream();
		rs.setId(this.getId());
		rs.setName(this.getName());
		return rs;
	}

}
