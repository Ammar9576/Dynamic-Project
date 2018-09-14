package com.nbc.convergencerepo.domain.admin;



import org.springframework.data.mongodb.core.mapping.Document;

import com.nbc.convergencerepo.domain.AbstractDomain;

@Document
public class Property extends AbstractDomain<Property> {
	
	@Override
	@org.springframework.data.annotation.Id
	public Long getId() {
		return id;
	}

	@Override
	public Property minify() {
		Property prop = new Property();
		prop.setId(this.getId());
		prop.setName(this.getName());
		return prop;
	}	
}
