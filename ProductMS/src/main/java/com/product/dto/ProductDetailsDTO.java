package com.product.dto;

import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
public class ProductDetailsDTO {
    private Long id;
    private String productName;
    private Double price;
    private Long categoryId;
    private CategoryDTO categoryDTO;
    private Integer stockQuantity;
    private String description;
    private Binary productImage;
}
