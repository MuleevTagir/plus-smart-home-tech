package ru.yandex.practicum.delivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.delivery.service.DeliveryService;
import ru.yandex.practicum.interactionapi.dto.DeliveryDto;
import ru.yandex.practicum.interactionapi.dto.OrderDto;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PutMapping
    public DeliveryDto planDelivery(@RequestBody @Valid DeliveryDto deliveryDto) {
        log.info("Создать новую доставку в БД {}", deliveryDto);
        return deliveryService.planDelivery(deliveryDto);
    }

    @PostMapping("/successful")
    public void deliverySuccessful(@RequestBody UUID deliveryId) {
        log.info("Эмуляция успешной доставки товара {}", deliveryId);
        deliveryService.deliverySuccessful(deliveryId);
    }

    @PostMapping("/picked")
    public void deliveryPicked(@RequestBody UUID deliveryId) {
        log.info("Эмуляция получения товара в доставку {}", deliveryId);
        deliveryService.deliveryPicked(deliveryId);
    }

    @PostMapping("/failed")
    public void deliveryFailed(@RequestBody UUID deliveryId) {
        log.info("Эмуляция неудачного вручения товара {}", deliveryId);
        deliveryService.deliveryFailed(deliveryId);
    }

    @PostMapping("/cost")
    public Double deliveryCost(@RequestBody @Valid OrderDto orderDto) {
        log.info("Расчёт полной стоимости доставки заказа {}", orderDto);
        return deliveryService.deliveryCost(orderDto);
    }
}
