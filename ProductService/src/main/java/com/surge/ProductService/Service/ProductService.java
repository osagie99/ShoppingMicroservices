package com.surge.ProductService.Service;

import com.surge.ProductService.DTO.ProductRequest;
import com.surge.ProductService.DTO.ProductResponse;

import java.util.UUID;

public interface ProductService {

    String addProduct(ProductRequest productRequest);

    ProductResponse getProduct(String id);

    void reduceQuantity(String productId, long quantity);
}
