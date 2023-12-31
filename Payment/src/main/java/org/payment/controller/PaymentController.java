package org.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.core.error.ErrorMessage;
import org.core.log.CentralLogger;
import org.payment.request.PaymentRequest;
import org.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest) {
        CentralLogger.appendLogToFile(String.format("[%s]: %s", this.getClass().getSimpleName(), "Creating an payment..."));
        var result = paymentService.charge(paymentRequest, false);

        if (result.isFailed()) {
            if (result.getErrorMessage() == ErrorMessage.CHARGE_PAYMENT_FAILED) {
                return ResponseEntity.status(500).body(result.getErrorMessage().getMessage());
            }
            if (result.getErrorMessage() == ErrorMessage.NOT_ENOUGH_MONEY) {
                return ResponseEntity.status(400).body(result.getErrorMessage().getMessage());
            }
            return ResponseEntity.status(500).body(result.getErrorMessage().getMessage());
        }

        return ResponseEntity.ok(String.valueOf(result.getContent()));
    }
}