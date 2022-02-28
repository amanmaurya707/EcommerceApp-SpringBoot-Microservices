package com.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.product.dto.ProductDTO;
import com.product.model.Product;
import com.product.repository.ProductRepository;
import com.product.service.IProductService;
import com.product.service.SequenceGeneratorService;
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    SequenceGeneratorService generatorService;
    
	@Override
	public List<ProductDTO> readAllProduct() {
		List<Product> productList=productRepository.findAll();
		List<ProductDTO> productDTOList=new ArrayList<>();
		productList.forEach(product->{
			ProductDTO productDTO=new ProductDTO();
			BeanUtils.copyProperties(product, productDTO);
			productDTOList.add(productDTO);
			
		});
		return productDTOList;
	}

	@Override
	public List<ProductDTO> readAllProductByCategoryId(Long categoryId) {
		List<Product> productList=productRepository.findAllByCategoryId(categoryId);
		List<ProductDTO> productDTOList=new ArrayList<>();
		productList.forEach(product->{
			ProductDTO productDTO=new ProductDTO();
			BeanUtils.copyProperties(product, productDTO);
			productDTOList.add(productDTO);
			
		});
		return productDTOList;
	}

	@Override
	public List<ProductDTO> readAllProductWithPagingAndSorting(int pageNo, int pageSize) {
		Sort sort=Sort.by(Direction.ASC,"id");
		Pageable pageable=PageRequest.of(pageNo, pageSize,sort);
	    List<Product> productList=productRepository.findAll(pageable).getContent();
	    List<ProductDTO> productDTOList=new ArrayList<>();
	    
	    productList.forEach(product->{
	    	ProductDTO productDTO=new ProductDTO();
	    	BeanUtils.copyProperties(product, productDTO);
	    	productDTOList.add(productDTO);
	    	
	    });
	    return productDTOList;
		
	}

	@Override
	public List<ProductDTO> readAllProductStartWithName(String productName) {
		List<Product> productList=productRepository.findAllStartWithName(productName);
	    List<ProductDTO> productDTOList=new ArrayList<>();
		productList.forEach(product->{
			ProductDTO productDTO=new ProductDTO();
			BeanUtils.copyProperties(product, productDTO);
			productDTOList.add(productDTO);
			
		});
		return productDTOList;
	}

	@Override
	public ProductDTO readProductByName(String productName) {
	   Optional<Product> optional=productRepository.findByProductName(productName);
	   if (optional.isPresent()) {
		   Product product=optional.get();
		   ProductDTO productDTO=new ProductDTO();
		   BeanUtils.copyProperties(product, productDTO);
		   return productDTO;
		}
	   else
	   {
		return null;
	   }
	}

	@Override
	public ProductDTO readProductById(Long id) {
		Optional<Product> optional=productRepository.findById(id);
		if (optional.isPresent()) {
			Product product=optional.get();
			ProductDTO productDTO=new ProductDTO();
			BeanUtils.copyProperties(product, productDTO);
			return productDTO;
		}
		else
		{
		return null;
		}
	}

	@Override
	public ProductDTO saveProduct(ProductDTO productDTO) {
		Product product=new Product();
		BeanUtils.copyProperties(productDTO, product);
		Optional<Product> optional=productRepository.findByProductName(product.getProductName());
		if (optional.isPresent()) {
			
			return null;
		}
		else
		{
		product.setId(generatorService.generateSequence(Product.SEQUENCE_NAME));
		Product updatedProduct=productRepository.save(product);
		ProductDTO updatedProductDTO=new ProductDTO();
		BeanUtils.copyProperties(updatedProduct, updatedProductDTO);
		return updatedProductDTO;
		}
	}

	@Override
	public ProductDTO editProduct(ProductDTO productDTO) {
		Product product=new Product();
		BeanUtils.copyProperties(productDTO, product);
		if (productRepository.existsById(product.getId())) 
		{
		 Product updatedProduct=productRepository.save(product);	
		 ProductDTO updatedProductDTO=new ProductDTO();
		 BeanUtils.copyProperties(updatedProduct, updatedProductDTO);
		 return updatedProductDTO;
		}
		else
		{	
		return null;
		}
	}

	@Override
	public String removeProductById(Long id) {
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
			return "deleted id "+id;
		}
		else
		{
			return "product id doesn't exist";
		}
		
	}

	@Override
	public String removeAllProduct() {
		if (productRepository.count()!=0) {
			productRepository.deleteAll();
			return "deleted all";
		}
		else
		{
			return "no records found";
		}
	}

	@Override
	public Long totalProduct() {
		Long productCount=productRepository.count();
		return productCount;
	}

}
