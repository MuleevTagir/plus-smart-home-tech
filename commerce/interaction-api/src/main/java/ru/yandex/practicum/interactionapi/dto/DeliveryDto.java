package ru.yandex.practicum.interactionapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.interactionapi.enums.DeliveryState;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryDto {
    @NotNull
    UUID deliveryId;
    @NotNull
    AddressDto fromAddress;
    @NotNull
    AddressDto toAddress;
    @NotNull
    UUID orderId;
    @NotNull
    DeliveryState deliveryState;
}
