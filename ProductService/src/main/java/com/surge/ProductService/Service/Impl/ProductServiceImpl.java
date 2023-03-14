package com.surge.ProductService.Service.Impl;
import com.surge.ProductService.DTO.ProductRequest;
import com.surge.ProductService.DTO.ProductResponse;
import com.surge.ProductService.Entity.ProductEntity;
import com.surge.ProductService.Exception.LowerQuantityException;
import com.surge.ProductService.Exception.ProductNotFoundException;
import com.surge.ProductService.Repository.ProductRepository;
import com.surge.ProductService.Service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
        public String addProduct(ProductRequest productRequest) {
        log.info("====== PRODUCT CONTROLLER ======");
        log.info("====== CREATING PRODUCT ======");
        ProductEntity product = ProductEntity.builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        log.info("====== PRODUCT CREATED SUCCESSFULLY ======");
        productRepository.save(product);
        return product.getProductId();
    }

    @Override
    public ProductResponse getProduct(String id) {
        log.info("====== PRODUCT CONTROLLER ======");
        log.info("====== GET PRODUCT ======");
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Does not exist"));
        log.info("====== PRODUCT GOTTEN SUCCESSFULLY ======");
        return ProductResponse.builder()
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }

    @Override
    public void reduceQuantity(String productId, long quantity) {
        ProductResponse getProduct = getProduct(productId);
        if(getProduct == null) {
            throw new ProductNotFoundException("Product Does not exist");
        }
        if(getProduct.getQuantity() < quantity) {
            throw new LowerQuantityException("Lower Quantity");
        }
        ProductEntity product = productRepository.findById(productId).get();
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity Updated");

    }
}
