package ru.yandex.practicum.order.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.interactionapi.feign.DeliveryClient;
import ru.yandex.practicum.interactionapi.feign.PaymentClient;
import ru.yandex.practicum.interactionapi.feign.ShoppingCartClient;
import ru.yandex.practicum.interactionapi.feign.WarehouseClient;

@Configuration
@EnableFeignClients(clients = {WarehouseClient.class, DeliveryClient.class, PaymentClient.class, ShoppingCartClient.class})
public class FeignConfig {

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .errorDecoder(new CustomErrorDecoder(new ObjectMapper()));
    }
}
