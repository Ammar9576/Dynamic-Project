package com.nbc.convergencerepo.domain;

import java.util.ArrayList;
import java.util.List;


public class ResponseObject {
   private int count;
   private List<? extends Object> data;
   private String error;
   
  public ResponseObject(int count,List<? extends Object> objList){
	  this.count = count;
	  this.data = objList;
  }
  
  public ResponseObject(String errorMsg){
	  List<String> errorList = new ArrayList<String>();
	  errorList.add(errorMsg);
	  this.data = errorList; 
  }
  
public ResponseObject() {
	// TODO Auto-generated constructor stub
}
/**
 * @return the count
 */
public int getCount() {
	return count;
}
/**
 * @param count the count to set
 */
public void setCount(int count) {
	this.count = count;
}
/**
 * @return the data
 */
public List<? extends Object> getData() {
	return data;
}
/**
 * @param data the data to set
 */
public void setData(List<? extends Object> data) {
	this.data = data;
}

public String getError() {
	return error;
}

public void setError(String error) {
	this.error = error;
}

   
}