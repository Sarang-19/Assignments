package com.example.springbootsoapproduct.repository;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.example.product.Product;


@Component
public class ProductRepository  {
	
	private static final Map<Integer,Product> product_Map=new HashMap<>();
   @PostConstruct
   public void initData() {
	   Product p1=new Product();
	   p1.setProductId(1);
	   p1.setProductName("Television");
	   p1.setProductPrice(35000);
	   p1.setProductDiscount(34);
	   product_Map.put(p1.getProductId(), p1);
	   
	   Product p2=new Product();
	   p2.setProductId(2);
	   p2.setProductName("Refrigerator");
	   p2.setProductPrice(55000);
	   p2.setProductDiscount(50);
	   product_Map.put(p2.getProductId(), p2);
	   
	   Product p3=new Product();
	   p3.setProductId(3);
	   p3.setProductName("AirConditioner");
	   p3.setProductPrice(65000);
	   p3.setProductDiscount(69);
	   product_Map.put(p3.getProductId(), p3);
	   
   }
   
   public Product findProduct(int id) {
	   if(product_Map.containsKey(id)) {
		   return product_Map.get(id);
	   }
	   else
	   {
		   return null;
	   }
   }
   
   public String deleteProduct(int id) {
	   Assert.notNull(id,"product id should not be null");
	   if(product_Map.containsKey(id)) {
		   product_Map.remove(id);
		   return id+" is removed from the repository";
	   }
	   else {
		   return id+" is not available in the repository";   
	   }
	   
   }

public String addProduct(Product product) {
	Assert.notNull(product,"product should not be null");
	if(product_Map.containsKey(product.getProductId())) {
		return "Product with the id"+product.getProductId()+"is already available";
	}
	else {
		product_Map.put(product.getProductId(), product);
		return "Product with the id"+product.getProductId()+"is added successfully";
	}
}

public Product updateProductDiscount(int productId, int productDiscount) {
	if(product_Map.containsKey(productId)) {
		product_Map.get(productId).setProductDiscount(productDiscount);
		return product_Map.get(productId);
	}
	return null;
}
}
