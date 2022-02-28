package com.category.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "category")
public class Category{
	@Transient
	public static final String SEQUENCE_NAME="category_sequence";
	
	@Id
	@Field(name = "id")
	private Long id;

	@Field(name = "category")
	private String category;
	

}
