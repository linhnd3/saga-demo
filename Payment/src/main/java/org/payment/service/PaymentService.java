package org.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.core.error.ErrorMessage;
import org.core.error.Result;
import org.core.http.client.RestaurantClient;
import org.core.log.CentralLogger;
import org.payment.model.Payment;
import org.payment.repository.PaymentRepository;
import org.payment.request.PaymentRequest;
import org.springframework.stereotype.Service;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestaurantClient restaurantClient;
    private final BalanceService balanceService;

    public Result<Long> charge(PaymentRequest paymentRequest, boolean isGoingToFailed) {
        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setOrderId(paymentRequest.getOrderId());

        try {
            var paymentResult = paymentRepository.save(payment);
            payment.setId(paymentResult.getId());
            if (isGoingToFailed) { // mock failed because of local db failed.
                CentralLogger.appendLogToFile(
                    String.format("[%s]: %s", this.getClass().getSimpleName(), "Save payment failed" + payment.getOrderId()));
                throw new Exception("Save payment failed");
            }

            // charge money
            var result = balanceService.chargeMoney(paymentRequest.getUsername(), paymentRequest.getAmount());
            if (result.isFailed()) { // failed because of banking service for example
                payment.setStatus(result.getErrorMessage().getMessage());
                paymentRepository.save(payment);
                CentralLogger.appendLogToFile(
                    String.format("[%s]: %s", this.getClass().getSimpleName(), "Update balance failed " + payment.getOrderId()));

                return new Result<>(result.getErrorMessage());
            }

            CentralLogger.appendLogToFile(
                String.format("[%s]: %s", this.getClass().getSimpleName(), "Charge payment success " + payment.getOrderId()));

            var processRestaurantResult = restaurantClient.sendRestaurantRequest(payment.getOrderId());
            if (processRestaurantResult.isFailed()) {
                // rollback
                payment.setStatus(processRestaurantResult.getErrorMessage().getMessage());
                paymentRepository.save(payment);
                balanceService.refund(paymentRequest.getUsername(), paymentRequest.getAmount());
                CentralLogger.appendLogToFile(
                    String.format("[%s]: %s", this.getClass().getSimpleName(), "Rollback charge payment for id " + payment.getOrderId()));
                return new Result<>(processRestaurantResult.getErrorMessage());
            }

            return new Result<>(payment.getId());


        } catch (Exception e) {
            return new Result<>(ErrorMessage.CHARGE_PAYMENT_FAILED);
        }
    }
}
