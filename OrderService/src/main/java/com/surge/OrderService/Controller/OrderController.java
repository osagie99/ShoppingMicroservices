package com.surge.OrderService.Controller;

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
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("/get-order/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("id") long id){
        return new ResponseEntity<OrderResponse>(orderService.getOrder(id), HttpStatus.OK);
    }
}
