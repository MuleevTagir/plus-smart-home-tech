package ru.yandex.practicum.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interactionapi.dto.AddressDto;
import ru.yandex.practicum.interactionapi.dto.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.feign.WarehouseClient;
import ru.yandex.practicum.interactionapi.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.service.WarehouseService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WarehouseController implements WarehouseClient {
    private final WarehouseService warehouseService;

    @Override
    public BookedProductsDto bookingProducts(ShoppingCartDto shoppingCartDto) {
        return null;
    }

    @Override
    public void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest requestDto) {
        log.info("Добавление нового товара на склад {}", requestDto);
        warehouseService.newProductInWarehouse(requestDto);
    }

    @Override
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCartDto) {
        log.info("Проверка достаточности продуктов для данной корзины {}", shoppingCartDto);
        return warehouseService.checkProductQuantityEnoughForShoppingCart(shoppingCartDto);
    }

    @Override
    public void addProductToWarehouse(@RequestBody @Valid AddProductToWarehouseRequest requestDto) {
        log.info("Принятие товара на склад {}", requestDto);
        warehouseService.addProductToWarehouse(requestDto);
    }

    @Override
    public AddressDto getAddress() {
        log.info("Получение адреса.");
        return warehouseService.getAddress();
    }
}
