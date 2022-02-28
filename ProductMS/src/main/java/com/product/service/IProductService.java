package com.product.service;

import java.util.List;

import com.product.dto.ProductDTO;

public interface IProductService {
	public List<ProductDTO> readAllProduct();
	public List<ProductDTO> readAllProductByCategoryId(Long categoryId);
	public List<ProductDTO> readAllProductWithPagingAndSorting(int pageNo,int pageSize);
	public List<ProductDTO> readAllProductStartWithName(String productName);
	public ProductDTO readProductByName(String productName);
	public ProductDTO readProductById(Long id);
	public ProductDTO saveProduct(ProductDTO productDTO);
	public ProductDTO editProduct(ProductDTO productDTO);
	public String removeProductById(Long id);
	public String removeAllProduct();
	public Long totalProduct();
	
}
