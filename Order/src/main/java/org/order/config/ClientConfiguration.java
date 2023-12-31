package org.order.config;

import org.core.http.client.PaymentClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@Configuration
public class ClientConfiguration {

    @Bean
    public PaymentClient getPaymentClient(RestTemplate restTemplate) {
        return new PaymentClient("http://localhost:8083", restTemplate);
    }


}
