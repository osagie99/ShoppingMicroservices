package com.surge.ProductService.Controller;

import com.surge.ProductService.DTO.BaseResponse;
import com.surge.ProductService.DTO.ProductRequest;
import com.surge.ProductService.DTO.ProductResponse;
import com.surge.ProductService.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping
    public ResponseEntity<BaseResponse> addProduct(@RequestBody ProductRequest productRequest) {
        BaseResponse productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public BaseResponse<ProductResponse> getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }
    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id")  String productId, @RequestParam long quantity) {
        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
