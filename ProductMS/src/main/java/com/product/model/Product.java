package com.product.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Document(collection ="Product")
public class Product {
	@Transient
	public static final String SEQUENCE_NAME="product_sequence";
	
	@Id
	@Field(name = "id")
	private Long id;

	@Field(name = "productName")
	private String productName;
	
	@Field(name = "price")
	private Double price;
	
	@Field(name = "categoryId")
	private Long categoryId;
	
	@Field(name = "stockQuantity")
	private Integer stockQuantity;
	
	@Field(name = "description")
	private String description;
	
	@Field(name = "productImage")
	private Binary productImage;
	

}
