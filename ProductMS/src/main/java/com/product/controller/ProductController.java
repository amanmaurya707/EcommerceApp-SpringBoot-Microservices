package com.product.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.Binary;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.product.dto.CategoryDTO;
import com.product.dto.ProductDTO;
import com.product.dto.ProductDetailsDTO;
import com.product.service.IProductService;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ProductController {
@Autowired
IProductService productService;

@Autowired
RestTemplate restTemplate;

private static final String CATEGORY_URL="http://localhost:1111/Category/{id}";

@GetMapping("/listAllProduct")
public ResponseEntity<List<ProductDetailsDTO>> listAllProduct()
{
	List<ProductDTO> productDTOList=productService.readAllProduct();
	List<ProductDetailsDTO> productDetailsDTOList=new ArrayList<>();
	productDTOList.forEach(productDTO->{
		ProductDetailsDTO productDetailsDTO=new ProductDetailsDTO();
		BeanUtils.copyProperties(productDTO, productDetailsDTO);
		Long categoryId=productDetailsDTO.getCategoryId();
		ResponseEntity<CategoryDTO> responseCategoryDTO=restTemplate.getForEntity(CATEGORY_URL, CategoryDTO.class,categoryId);
		CategoryDTO categoryDTO=responseCategoryDTO.getBody();
		productDetailsDTO.setCategoryDTO(categoryDTO);
		productDetailsDTOList.add(productDetailsDTO);
	} );
	 return new ResponseEntity<List<ProductDetailsDTO>>(productDetailsDTOList,HttpStatus.OK);
	
}
@GetMapping("/listAllProduct/{categoryId}")
public ResponseEntity<List<ProductDetailsDTO>> listAllProductByCategoryId(@PathVariable Long categoryId)
{
	List<ProductDTO> productDTOList=productService.readAllProductByCategoryId(categoryId);
	List<ProductDetailsDTO> productDetailsDTOList=new ArrayList<>();
	productDTOList.forEach(productDTO->{
		ProductDetailsDTO productDetailsDTO=new ProductDetailsDTO();
		BeanUtils.copyProperties(productDTO, productDetailsDTO);
		//Long categoryId=productDetailsDTO.getCategoryId();
        ResponseEntity<CategoryDTO> responseCategoryDTO=restTemplate.getForEntity(CATEGORY_URL, CategoryDTO.class,categoryId);
		CategoryDTO categoryDTO=responseCategoryDTO.getBody();
		productDetailsDTO.setCategoryDTO(categoryDTO);
		productDetailsDTOList.add(productDetailsDTO);
	} );
	 return new ResponseEntity<List<ProductDetailsDTO>>(productDetailsDTOList,HttpStatus.OK);
	
}
@GetMapping("/listAllProduct/{pageNo}/{pageSize}")
public ResponseEntity<List<ProductDetailsDTO>> listAllProductWithPagingAndSorting(@PathVariable int pageNo,@PathVariable int pageSize)
{
	List<ProductDTO> productDTOList=productService.readAllProductWithPagingAndSorting(pageNo, pageSize);
	List<ProductDetailsDTO> productDetailsDTOList=new ArrayList<>();
	productDTOList.forEach(productDTO->{
		ProductDetailsDTO productDetailsDTO=new ProductDetailsDTO();
		BeanUtils.copyProperties(productDTO, productDetailsDTO);
		Long categoryId=productDetailsDTO.getCategoryId();
        ResponseEntity<CategoryDTO> responseCategoryDTO=restTemplate.getForEntity(CATEGORY_URL, CategoryDTO.class,categoryId);
		CategoryDTO categoryDTO=responseCategoryDTO.getBody();
		productDetailsDTO.setCategoryDTO(categoryDTO);
		productDetailsDTOList.add(productDetailsDTO);
	} );
	 return new ResponseEntity<List<ProductDetailsDTO>>(productDetailsDTOList,HttpStatus.OK);
	
}
@GetMapping("/searchAllProduct/{productName}")
public ResponseEntity<List<ProductDetailsDTO>> searchAllProductStartWithName(@PathVariable String productName)
{ 
	List<ProductDTO> productDTOList=productService.readAllProductStartWithName(productName);
	List<ProductDetailsDTO> productDetailsDTOList=new ArrayList<>();
	productDTOList.forEach(productDTO->{
		ProductDetailsDTO productDetailsDTO=new ProductDetailsDTO();
		BeanUtils.copyProperties(productDTO, productDetailsDTO);
		Long categoryId=productDetailsDTO.getCategoryId();
        ResponseEntity<CategoryDTO> responseCategoryDTO=restTemplate.getForEntity(CATEGORY_URL, CategoryDTO.class,categoryId);
		CategoryDTO categoryDTO=responseCategoryDTO.getBody();
		productDetailsDTO.setCategoryDTO(categoryDTO);
		productDetailsDTOList.add(productDetailsDTO);
	} );
	 return new ResponseEntity<List<ProductDetailsDTO>>(productDetailsDTOList,HttpStatus.OK);
}
@GetMapping("/product/{productName}")
public ResponseEntity<ProductDetailsDTO> getProductByName(@PathVariable String productName)
{
	ProductDTO productDTO=productService.readProductByName(productName);
	if (productDTO==null)
	{
		return null;
	}
	else
	{
		ProductDetailsDTO productDetailsDTO=new ProductDetailsDTO();
		BeanUtils.copyProperties(productDTO, productDetailsDTO);
		Long categoryId=productDetailsDTO.getCategoryId(); //OR//productDTO.getCategoryId();
		ResponseEntity<CategoryDTO> responseCategoryDTO=restTemplate.getForEntity(CATEGORY_URL,CategoryDTO.class,categoryId);
		CategoryDTO categoryDTO=responseCategoryDTO.getBody();
		productDetailsDTO.setCategoryDTO(categoryDTO);
		return ResponseEntity.ok(productDetailsDTO);
	}
	
}
@GetMapping("/{id}") 
public ResponseEntity<ProductDetailsDTO> getProductById(@PathVariable Long id)
{
	ProductDTO productDTO=productService.readProductById(id);
	if (productDTO==null) {
		return null;
	}
	else
	{
	ProductDetailsDTO productDetailsDTO=new ProductDetailsDTO();
	BeanUtils.copyProperties(productDTO, productDetailsDTO);
	Long categoryId=productDetailsDTO.getCategoryId(); //OR//productDTO.getCategoryId();
	ResponseEntity<CategoryDTO> responseCategoryDTO=restTemplate.getForEntity(CATEGORY_URL,CategoryDTO.class,categoryId);
	CategoryDTO categoryDTO=responseCategoryDTO.getBody();
	productDetailsDTO.setCategoryDTO(categoryDTO);
	return ResponseEntity.ok(productDetailsDTO);
	}
	
}
@GetMapping("/totalProduct") 
public ResponseEntity<Long> totalProduct()
{
	return ResponseEntity.ok(productService.totalProduct());
	
}

@PostMapping(value = "/addProduct",consumes =MediaType.MULTIPART_FORM_DATA_VALUE,produces = {MediaType.APPLICATION_JSON_VALUE})
public ResponseEntity<ProductDetailsDTO> addProduct(@RequestParam("productName") String productName,@RequestParam("price") Double price,@RequestParam("categoryId") Long categoryId,@RequestParam("stockQuantity") Integer stockQuantity,@RequestParam("description") String description,@RequestParam("productImage") MultipartFile file) throws IOException 
{  
	ProductDTO productDTO=new ProductDTO();
	productDTO.setProductName(productName);
	productDTO.setPrice(price);
	productDTO.setCategoryId(categoryId);
	productDTO.setStockQuantity(stockQuantity);
	productDTO.setDescription(description);
	//conversion of MultipartFile to Binary type of mongo
	byte[]  fileData=file.getBytes();
	Binary binary=new Binary(fileData);

	productDTO.setProductImage(binary);
	
   ProductDTO saveProductDTO=productService.saveProduct(productDTO);
   if (saveProductDTO==null) {
	   return null;
   }
   else
   {
		ProductDetailsDTO productDetailsDTO=new ProductDetailsDTO();
		BeanUtils.copyProperties(saveProductDTO, productDetailsDTO);
		//Long categoryId=productDetailsDTO.getCategoryId(); //OR//productDTO.getCategoryId();
		ResponseEntity<CategoryDTO> responseCategoryDTO=restTemplate.getForEntity(CATEGORY_URL,CategoryDTO.class,categoryId);
		CategoryDTO categoryDTO=responseCategoryDTO.getBody();
		productDetailsDTO.setCategoryDTO(categoryDTO);
		return ResponseEntity.ok(productDetailsDTO);
   }
	
}
@PutMapping(value="/updateProduct",consumes ="multipart/form-data") 
public ResponseEntity<ProductDetailsDTO> updateProduct(@RequestParam("id") Long id,@RequestParam("productName") String productName,@RequestParam("price") Double price,@RequestParam("categoryId") Long categoryId,@RequestParam("stockQuantity") Integer stockQuantity,@RequestParam("description") String description,@RequestParam("productImage") MultipartFile file) throws IOException 
{
	ProductDTO productDTO=new ProductDTO();
	productDTO.setId(id);
	productDTO.setProductName(productName);
	productDTO.setPrice(price);
	productDTO.setCategoryId(categoryId);
	productDTO.setStockQuantity(stockQuantity);
	productDTO.setDescription(description);
	//conversion of MultipartFile to Binary type of mongo
	byte[]  fileData=file.getBytes();
	Binary binary=new Binary(fileData);

	productDTO.setProductImage(binary);
	   ProductDTO updatedProductDTO=productService.editProduct(productDTO);
	   if (updatedProductDTO==null) {
		   return null;
	   }
	   else
	   {
			ProductDetailsDTO productDetailsDTO=new ProductDetailsDTO();
			BeanUtils.copyProperties(updatedProductDTO, productDetailsDTO);
			//Long categoryId=productDetailsDTO.getCategoryId(); //OR//productDTO.getCategoryId();
			ResponseEntity<CategoryDTO> responseCategoryDTO=restTemplate.getForEntity(CATEGORY_URL,CategoryDTO.class,categoryId);
			CategoryDTO categoryDTO=responseCategoryDTO.getBody();
			productDetailsDTO.setCategoryDTO(categoryDTO);
			return ResponseEntity.ok(productDetailsDTO);
	   }
	
}
@DeleteMapping("/deleteProduct/{id}") 
public ResponseEntity<String> deleteProductById(@PathVariable Long id)
{
	return ResponseEntity.ok(productService.removeProductById(id));
	
}
@DeleteMapping("/deleteAllProduct") 
public ResponseEntity<String> deleteAllProduct()
{
	return ResponseEntity.ok(productService.removeAllProduct());
	
}
}
