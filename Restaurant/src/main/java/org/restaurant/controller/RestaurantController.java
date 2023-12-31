package org.restaurant.controller;

import org.core.log.CentralLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {


    @PostMapping("/create")
    public ResponseEntity<Boolean> createOrder(@RequestBody String dishName) {
        CentralLogger.appendLogToFile(String.format("[%s]: %s", this.getClass().getSimpleName(), "Create an dish"));
        return ResponseEntity.ok(true);
    }
}
