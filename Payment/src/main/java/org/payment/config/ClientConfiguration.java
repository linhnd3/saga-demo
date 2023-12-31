package org.payment.config;

import org.core.http.client.RestaurantClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@Configuration
public class ClientConfiguration {

    @Bean
    public RestaurantClient getPaymentClient(RestTemplate restTemplate) {
        return new RestaurantClient("http://localhost:8084", restTemplate);
    }


}
