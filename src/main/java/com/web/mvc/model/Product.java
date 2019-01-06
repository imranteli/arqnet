package com.web.mvc.model;

import org.springframework.web.multipart.MultipartFile;

public class Product {

	private long id;
	
	private String title;
	
	private String category;
	
	private String author;
	
	private Double price;
	
	
	
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
