package com.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.model.Product;
@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {
 @Query("{'productName':{'$regex':'^?0$','$options':'i'}}") 
 public Optional<Product> findByProductName(String productName);
 
 @Query("{'categoryId':?0}") //regex is work for string value only
 public List<Product> findAllByCategoryId(Long categoryId);
 
 @Query("{'productName':{'$regex':'^?0','$options':'i'}}") 
 public List<Product> findAllStartWithName(String productName);
}
