package com.space.customer.controller;

import com.space.customer.model.Product;
import com.space.customer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = service.getProducts();
        if (products==null){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(products);
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product product1=service.insertProduct(product);
        if(product1==null){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(product1);
        }
    }

    @PutMapping("/productUpdate/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable long id,@RequestBody Product newproduct){
        String response = service.updateProduct(id,newproduct);
        if(response.contains("successfully")){
            return ResponseEntity.ok("updated successfully");
        } else if (response.contains("failed")) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/productDelete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id){
        String response = service.delete(id);
        if(response.contains("successfully")){
            return ResponseEntity.ok("Deleted Successfully");
        }
        else if (response.contains("Error!!")){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.status(500).body(response);
        }
    }
}
