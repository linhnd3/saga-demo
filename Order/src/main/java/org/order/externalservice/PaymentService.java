package org.order.externalservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.core.error.Result;
import org.core.http.client.PaymentClient;
import org.springframework.stereotype.Service;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentClient paymentClient;

    public Result<String> sendPaymentService(String username, Long orderId, Long amount) {
        var result = paymentClient.sendPaymentRequest(username, orderId, amount);
        if (result.isSucceed()) {
            return new Result<>(result.getContent());
        } else {
            log.error("[requestPaymentService] orderId {}, amount{} , message {}", orderId, amount, result.getContent());
            return new Result<>(result.getErrorMessage());
        }
    }
}
