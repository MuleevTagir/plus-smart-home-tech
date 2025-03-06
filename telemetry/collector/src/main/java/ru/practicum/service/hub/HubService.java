package ru.practicum.service.hub;

import ru.practicum.dto.hub.HubEvent;

public interface HubService {
    void sendHubEvent(HubEvent hubEvent);
}