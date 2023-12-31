package org.delivery.controller;

import org.core.log.CentralLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @PostMapping("/deliver")
    public ResponseEntity<Boolean> deliveryOrder(@RequestBody String dishName) {
        CentralLogger.appendLogToFile(String.format("[%s]: %s", this.getClass().getSimpleName(), "delivery an order"));
        return ResponseEntity.ok(true);
    }
}
