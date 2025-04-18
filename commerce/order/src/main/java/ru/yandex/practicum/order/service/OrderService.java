package ru.yandex.practicum.order.service;

import ru.yandex.practicum.interactionapi.dto.OrderDto;
import ru.yandex.practicum.interactionapi.request.CreateNewOrderRequest;
import ru.yandex.practicum.interactionapi.request.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderDto> getClientOrders(String username, Integer page, Integer size);

    OrderDto create(CreateNewOrderRequest newOrderRequest);

    OrderDto productReturn(ProductReturnRequest returnRequest);

    OrderDto payment(UUID orderId);

    OrderDto paymentFailed(UUID orderId);

    OrderDto delivery(UUID orderId);

    OrderDto deliveryFailed(UUID orderId);

    OrderDto complete(UUID orderId);

    OrderDto calculateTotalCost(UUID orderId);

    OrderDto calculateDeliveryCost(UUID orderId);

    OrderDto assembly(UUID orderId);

    OrderDto assemblyFailed(UUID orderId);
}
