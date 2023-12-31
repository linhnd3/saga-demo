package org.payment.config;

import java.time.Duration;
import org.core.http.request.RestClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder().requestFactory(SimpleClientHttpRequestFactory::new);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(5)).setReadTimeout(Duration.ofSeconds(20))
            .build();
    }

    @Bean
    public RestClient restClient(RestTemplate restTemplate){
        return new RestClient(restTemplate);
    }

}
