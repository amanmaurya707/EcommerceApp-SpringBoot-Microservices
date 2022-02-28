package com.category.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.category.model.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, Long>{
    @Query("{'category':{'$regex':'^?0','$options':'i'}}") //i:represent case insensitive ,m:represent exact match
	public List<Category> findAllCategoriesStartWithName(String category);
   
    @Query("{'category':{'$regex':'^?0$','$options':'i'}}") 
	public Optional<Category> findByCategoryName(String category);
	

}
