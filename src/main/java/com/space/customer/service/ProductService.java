package com.space.customer.service;

import com.space.customer.model.Product;
import com.space.customer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts(){
        return repository.findAll();
    }

    public Product insertProduct(Product product){
        return repository.save(product);
    }

    public String updateProduct (long id,Product newProduct){
        Optional<Product> optionalProduct =repository.findById(id);
        if(optionalProduct.isPresent()){
            Product existingProduct = optionalProduct.get();
            existingProduct.setProductName(newProduct.getProductName());
            existingProduct.setProductBrand(newProduct.getProductBrand());
            existingProduct.setProductDate(newProduct.getProductDate());
            existingProduct.setProductType(newProduct.getProductType());
            existingProduct.setProductMaterialType(newProduct.getProductMaterialType());
            existingProduct.setProductGender(newProduct.getProductGender());
            existingProduct.setProductColor(newProduct.getProductColor());
            existingProduct.setProductSize(newProduct.getProductSize());
            existingProduct.setProductImageUrl(newProduct.getProductImageUrl());
            existingProduct.setProductDesign(newProduct.getProductDesign());
            repository.save(existingProduct);
            return "Product updated successfully";
        }
        return "Product Updating failed";
    }
    public String delete(long id){
        Optional<Product> product = repository.findById(id);
        if(product.isPresent()){
            repository.deleteById(id);
            return "deleted successfully";
        }
        else {
            return "Error!!";
        }
    }

    public Optional<Product> getProductById(long id){
        return repository.findById(id);
    }
}
