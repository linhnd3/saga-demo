package org.order.controller;

import static org.core.log.CentralLogger.appendLogToFile;

import lombok.RequiredArgsConstructor;
import org.order.model.Order;
import org.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestParam String dishName, @RequestParam String username) {
        appendLogToFile(String.format("[%s]: %s", this.getClass().getSimpleName(), "Creating an order..."));
        var result = orderService.createOrder(new Order(username, dishName), false);
        if (result.isFailed()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result.getErrorMessage().getMessage());
        }
        return ResponseEntity.ok(result.getContent());
    }
}
