package com.example.springbootsoapproduct.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.product.AddProductRequest;
import com.example.product.AddProductResponse;
import com.example.product.DeleteProductRequest;
import com.example.product.DeleteProductResponse;
import com.example.product.GetProductRequest;
import com.example.product.GetProductResponse;
import com.example.product.UpdateProductDiscountRequest;
import com.example.product.UpdateProductDiscountResponse;
import com.example.springbootsoapproduct.service.Productservice;

@Endpoint
public class ProductEndpoint {
	
	public static final String NAMESPACE="http://www.example.com/product";
     
    @Autowired
    Productservice productservice;
	@PayloadRoot(namespace=NAMESPACE,localPart="getProductRequest")
	@ResponsePayload
	public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
		GetProductResponse response=new GetProductResponse();
		response.setProduct(productservice.getProduct(request.getProductId()));
		return response;
		
	}
	
	@PayloadRoot(namespace=NAMESPACE,localPart="deleteProductRequest")
	@ResponsePayload
	public DeleteProductResponse deleteProduct(@RequestPayload DeleteProductRequest request) {
		DeleteProductResponse response=new DeleteProductResponse();
		response.setResponse(productservice.deleteProduct(request.getProductId()));
		return response;
		
	}
	
	@PayloadRoot(namespace=NAMESPACE,localPart="addProductRequest")
	@ResponsePayload
	public AddProductResponse addProduct(@RequestPayload AddProductRequest request) {
		AddProductResponse response=new AddProductResponse();
		response.setResponse(productservice.addProduct(request.getProduct()));
		return response;
		
	}
	
	@PayloadRoot(namespace=NAMESPACE,localPart="updateProductDiscountRequest")
	@ResponsePayload
	public UpdateProductDiscountResponse updateProductDiscount(@RequestPayload UpdateProductDiscountRequest request) {
	    UpdateProductDiscountResponse response=new UpdateProductDiscountResponse();
		response.setProduct(productservice.updateProductDiscount(request.getProductId(),request.getProductDiscount()));
		return response;
		
	}
	
	
}
