package ru.yandex.practicum.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interactionapi.dto.AddressDto;
import ru.yandex.practicum.interactionapi.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.interactionapi.dto.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.interactionapi.request.ShippedToDeliveryRequest;
import ru.yandex.practicum.warehouse.service.WarehouseService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PutMapping
    public void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest requestDto) {
        log.info("Добавить новый товар на склад {}", requestDto);
        warehouseService.newProductInWarehouse(requestDto);
    }

    @PostMapping("/shipped")
    public void shippedToDelivery(ShippedToDeliveryRequest deliveryRequest) {
        log.info("Передать товары в доставку {}", deliveryRequest);
        warehouseService.shippedToDelivery(deliveryRequest);
    }

    @PostMapping("/return")
    public void acceptReturn(@RequestBody Map<UUID, Long> products) {
        log.info("Принять возврат товаров на склад {}", products);
        warehouseService.acceptReturn(products);
    }

    @PostMapping("/check")
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCartDto) {
        log.info("Предварительно проверить что количество товаров на складе достаточно для данной корзины продуктов {}", shoppingCartDto);
        return warehouseService.checkProductQuantityEnoughForShoppingCart(shoppingCartDto);
    }

    @PostMapping("/assembly")
    public BookedProductsDto assemblyProductsForOrder(@RequestBody @Valid AssemblyProductsForOrderRequest assemblyProductsForOrder) {
        log.info("Собрать товары к заказу для подготовки к отправке {}", assemblyProductsForOrder);
        return warehouseService.assemblyProductsForOrder(assemblyProductsForOrder);
    }

    @PostMapping("/add")
    public void addProductToWarehouse(@RequestBody @Valid AddProductToWarehouseRequest requestDto) {
        log.info("Принять товар на склад {}", requestDto);
        warehouseService.addProductToWarehouse(requestDto);
    }

    @GetMapping("/address")
    public AddressDto getWarehouseAddress() {
        log.info("Предоставить адрес склада для расчёта доставки.");
        return warehouseService.getWarehouseAddress();
    }

    @PostMapping("/booking")
    public BookedProductsDto bookingProducts(@RequestBody @Valid ShoppingCartDto shoppingCartDto) {
        log.info("Бронирование корзины покупок {}", shoppingCartDto);
        return warehouseService.bookingProducts(shoppingCartDto);
    }
}
