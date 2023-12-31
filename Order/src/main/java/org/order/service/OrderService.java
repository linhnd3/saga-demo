package org.order.service;

import static org.core.log.CentralLogger.appendLogToFile;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.core.error.ErrorMessage;
import org.core.error.Result;
import org.order.externalservice.PaymentService;
import org.order.model.Order;
import org.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    private static final Map<String, Long> menu = Map.of(
        "banhmi", 20000L,
        "caphe", 10000L,
        "bokobe", 10000000L
    );

    public Result<String> createOrder(Order order, boolean isGoingToFailed) {

        Long orderId;
        try {
            // save order
            Order saveOrderResult = orderRepository.save(order);

            if (isGoingToFailed) {
                throw new RuntimeException("isGoingToFailed = true");
            }

            appendLogToFile(String.format("[%s]: %s", this.getClass().getSimpleName(), "Create an order success"));
            orderId = saveOrderResult.getId();

            // send request to payment service
            var paymentResult = paymentService.sendPaymentService(order.getUsername(), orderId, menu.getOrDefault(order.getDishName(), Long.MAX_VALUE));
            if (paymentResult.isFailed()) {
                // update order failed:
                order.setStatus("payment failed");
                orderRepository.save(order);
                appendLogToFile(String.format("[%s]: %s", this.getClass().getSimpleName(), "Update an order status to payment failed"));
                return new Result<>(ErrorMessage.CHARGE_PAYMENT_FAILED);
            }

            // update payment succeed
            order.setStatus("payment succeed: paymentId:" + paymentResult.getContent());
            orderRepository.save(order);
            appendLogToFile(String.format("[%s]: %s", this.getClass().getSimpleName(), "Update an order status to payment succeed"));

        } catch (Exception e) {
            appendLogToFile(String.format("[%s]: %s", this.getClass().getSimpleName(), "Create an order failed"));
            return new Result<>(ErrorMessage.SAVE_ORDER_FAILED);
        }

        return new Result<>(orderId.toString());


    }

}
