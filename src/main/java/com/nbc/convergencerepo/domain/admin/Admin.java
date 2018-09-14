package com.nbc.convergencerepo.domain.admin;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Admin {

	private String id;
	private String name;
	private String value;
	private String type;


	@org.springframework.data.annotation.Id
	public String getId() {
		return id;
	}

	public void setId() {
		this.id = new ObjectId().toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
