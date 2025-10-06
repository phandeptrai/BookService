package com.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "books")
public class Book {
	@Id
	private String id;
	private String name;
	private String des;
	private boolean active;  
	private Status status;	//AVAILABLE,BORROWED
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Status getStatus() {
		return status;
	}
	public void setAVAILABLE(Status status) {
		this.status = status;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
