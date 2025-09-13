package com.ddg.MongoDBDemo.service;

import com.ddg.MongoDBDemo.model.Product;
import com.ddg.MongoDBDemo.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;
    public ResponseEntity<Product> addProduct(Product product) {
        try {
            return new ResponseEntity<>(productRepo.save(product), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            return new ResponseEntity<>(productRepo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Product> getProductById(String id) {
       try {
            Product product = productRepo.findById(id).orElse(null);
            if (product == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    public ResponseEntity<Product> updateProduct(String id, Product product) {
        try {
            Product existingProduct = productRepo.findById(id).orElse(null);
            if (existingProduct == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                existingProduct.setProductName(product.getProductName());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setQuantity(product.getQuantity());
                return new ResponseEntity<>(productRepo.save(existingProduct), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteProduct(String id) {
        try {
            Product product = productRepo.findById(id).orElse(null);
            if (product == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                productRepo.delete(product);
                return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
