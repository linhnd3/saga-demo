package org.core.http.client;

import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.core.error.ErrorMessage;
import org.core.error.Result;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@Slf4j
@AllArgsConstructor
public class RestaurantClient {

    private static final String ORDER_CREATE_API_URL = "/restaurant/create";

    private String host;
    private RestTemplate restTemplate;

    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        return headers;
    }

    public Result<String> sendRestaurantRequest(Long orderId) {
        try {
            log.info("[sendRestaurantRequest] orderId {}", orderId);
            Map params = Map.of("orderId", orderId);

            var request = new HttpEntity<>(params, httpHeaders());
            var responseEntity = restTemplate.exchange(
                host + ORDER_CREATE_API_URL,
                HttpMethod.POST,
                request,
                String.class
            );
            return new Result<>(responseEntity.getBody());
        } catch (Exception ex) {
            log.error("[sendRestaurantRequest] orderId {} failed", orderId, ex);
            return new Result<>(ErrorMessage.SEND_RESTAURANT_FAILED);
        }
    }

}
