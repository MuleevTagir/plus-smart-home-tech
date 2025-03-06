package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.hub.HubEvent;
import ru.practicum.dto.sensor.SensorEvent;
import ru.practicum.service.hub.HubService;
import ru.practicum.service.sensor.SensorService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CollectorController {

    private final SensorService sensorService;
    private final HubService hubService;

    @PostMapping("/events/sensors")
    public void collectSensorEvent(@Valid @RequestBody SensorEvent event) {

        log.info("SensorEvent : {}, {}, {}, {}", event.getId(), event.getHubId(), event.getTimestamp(), event.getType());
        log.debug("SensorEvent = {}", event);
        sensorService.sendSensorEvent(event);
    }

    @PostMapping("/events/hubs")
    public void collectHubEvent(@Valid @RequestBody HubEvent event) {

        log.info("HubEvent : {}, {}, {}", event.getHubId(), event.getTimestamp(), event.getType());
        log.debug("HubEvent = {}", event);

        hubService.sendHubEvent(event);
    }
}