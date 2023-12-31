package org.payment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private String username;
    private Long orderId;
    private Long amount;
}
