package com.surge.ProductService.Service;

import com.surge.ProductService.DTO.BaseResponse;
import com.surge.ProductService.DTO.ProductRequest;
import com.surge.ProductService.DTO.ProductResponse;

import java.util.UUID;

public interface ProductService {

    BaseResponse addProduct(ProductRequest productRequest);

    BaseResponse getProduct(String id);

    void reduceQuantity(String productId, long quantity);
}
