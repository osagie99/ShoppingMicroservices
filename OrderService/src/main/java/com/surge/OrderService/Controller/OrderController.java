package com.surge.OrderService.Controller;

import com.surge.OrderService.Dto.BaseResponse;
import com.surge.OrderService.Dto.OrderRequest;
import com.surge.OrderService.Dto.OrderResponse;
import com.surge.OrderService.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<BaseResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        BaseResponse orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("/get-order/{id}")
    public ResponseEntity<BaseResponse> getOrder(@PathVariable("id") long id){
        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
    }
}
