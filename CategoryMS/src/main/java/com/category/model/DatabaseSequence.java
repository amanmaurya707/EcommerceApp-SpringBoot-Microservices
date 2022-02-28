package com.category.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Document(collection = "database_sequence")
public class DatabaseSequence {
	@Id
	private String id;
	private Long seq;


}
