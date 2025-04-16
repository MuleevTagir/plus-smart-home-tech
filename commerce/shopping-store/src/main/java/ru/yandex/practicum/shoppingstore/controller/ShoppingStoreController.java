package ru.yandex.practicum.shoppingstore.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interactionapi.dto.PageableDto;
import ru.yandex.practicum.interactionapi.enums.QuantityState;
import ru.yandex.practicum.interactionapi.feign.ShopingStoreClient;
import ru.yandex.practicum.interactionapi.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.interactionapi.dto.ProductDto;
import ru.yandex.practicum.interactionapi.enums.ProductCategory;
import ru.yandex.practicum.shoppingstore.service.ShoppingStoreService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShoppingStoreController implements ShopingStoreClient {

    private final ShoppingStoreService shoppingStoreService;

    @Override
    public List<ProductDto> getProducts(@RequestParam ProductCategory productCategory,
                                        @Valid PageableDto pageableDto) {
        log.info("Получение списка товаров по типу в пагинированном виде.");
        return shoppingStoreService.getProducts(productCategory, pageableDto);
    }

    @Override
    public ProductDto createNewProduct(@RequestBody @Valid ProductDto productDto) {
        log.info("Создание нового товара в ассортименте {}", productDto);
        return shoppingStoreService.createNewProduct(productDto);
    }

    @Override
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto) {
        log.info("Обновление товара в ассортименте {}", productDto);
        return shoppingStoreService.updateProduct(productDto);
    }

    @Override
    public Boolean removeProductFromStore(@RequestBody @NotNull UUID productId) {
        log.info("Удаление товара из ассортимента магазина. Функция для менеджерского состава. {}", productId);
        return shoppingStoreService.removeProductFromStore(productId);
    }

    @Override
    public Boolean setProductQuantityState(UUID productId, QuantityState quantityState) {
        return null;
    }

    @Override
    public Boolean setProductQuantityState(@Valid SetProductQuantityStateRequest setProductQuantityStateRequest) {
        log.info("Установка статуса по товару {}", setProductQuantityStateRequest);
        return shoppingStoreService.setProductQuantityState(setProductQuantityStateRequest);
    }

    @Override
    public ProductDto getProduct(@PathVariable @NotNull UUID productId) {
        log.info("Получение сведений по товару из БД: {}", productId);
        return shoppingStoreService.getProduct(productId);
    }
}
