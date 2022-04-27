package com.example.springbootsoapproduct.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.product.Product;
import com.example.springbootsoapproduct.repository.ProductRepository;

@Service
public class Productservice {
	
	@Autowired
	ProductRepository productRepository;
	
   public Product getProduct(int product_id) {
	  return productRepository.findProduct(product_id);
   }
   public String deleteProduct(int product_id) {
		  return productRepository.deleteProduct(product_id);
	   }
public String addProduct(Product product) {
	return productRepository.addProduct(product);
}
public Product updateProductDiscount(int productId, int productDiscount) {
	return productRepository.updateProductDiscount(productId,productDiscount);
}
}
