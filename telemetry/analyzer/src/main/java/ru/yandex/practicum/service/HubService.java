package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.*;
import ru.yandex.practicum.exception.ScenarioAddedException;
import ru.yandex.practicum.model.*;
import ru.yandex.practicum.repository.*;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubService {

    private final SensorRepository sensorRepository;
    private final ScenarioRepository scenarioRepository;
    private final ConditionRepository conditionRepository;
    private final ActionRepository actionRepository;
    private final ScenarioConditionRepository scenarioConditionRepository;
    private final ScenarioActionRepository scenarioActionRepository;

    @Transactional
    public void processingEvent(DeviceAddedEvent event) {
        if (!sensorRepository.existsById(event.getId())) {
            SensorEntity sensorEntity = new SensorEntity(event.getId(), event.getHubId());
            sensorRepository.save(sensorEntity);
            log.info("Добавлено устройство");
        }
    }

    @Transactional
    public void processingEvent(DeviceRemovedEvent event) {
        if (sensorRepository.existsById(event.getId())) {
            SensorEntity sensorEntity = new SensorEntity(event.getId(), event.getHubId());
            sensorRepository.delete(sensorEntity);
            log.info("Устройство удалено");
        }
    }

    @Transactional
    public void processingEvent(ScenarioAddedEvent event) {
        ScenarioEntity scenarioEntity = new ScenarioEntity();
        Optional<ScenarioEntity> scenarioOptional = scenarioRepository.findByHubIdAndName(event.getHubId(), event.getName());
        if (scenarioOptional.isPresent()) {
            log.info("Сценарий уже существует");
            throw new ScenarioAddedException("Сценарий уже существует");
        }
        scenarioEntity.setHubId(event.getHubId());
        scenarioEntity.setName(event.getName());
        scenarioEntity = scenarioRepository.save(scenarioEntity);

        for (ScenarioCondition sc : event.getConditions()) {
            ConditionEntity conditionEntity = new ConditionEntity();
            conditionEntity.setType(sc.getType());
            conditionEntity.setOperation(sc.getConditionOperation());
            conditionEntity.setValue(sc.getValue());
            conditionRepository.save(conditionEntity);
            Optional<SensorEntity> sensorEntity = sensorRepository.findByIdAndHubId(sc.getSensorId(), event.getHubId());
            if (sensorEntity.isEmpty()) {
                log.warn("Нужный сенсор не найден");
                throw new ScenarioAddedException("Нужный сенсор не найден");
            }
            ScenarioConditionEntity scenarioConditionEntity = new ScenarioConditionEntity();
            scenarioConditionEntity.setScenarioEntity(scenarioEntity);
            scenarioConditionEntity.setSensorEntity(sensorEntity.get());
            scenarioConditionEntity.setConditionEntity(conditionEntity);
            scenarioConditionRepository.save(scenarioConditionEntity);
        }
        for (DeviceActionEvent da : event.getActions()) {
            ActionEntity actionEntity = new ActionEntity();
            actionEntity.setType(da.getType().name());
            actionEntity.setValue(da.getValue());
            actionRepository.save(actionEntity);
            Optional<SensorEntity> sensorEntity = sensorRepository.findByIdAndHubId(da.getSensorId(), event.getHubId());
            if (sensorEntity.isEmpty()) {
                log.warn("Нужный сенсор не найден");
                throw new ScenarioAddedException("Нужный сенсор не найден");
            }
            ScenarioActionEntity scenarioActionEntity = new ScenarioActionEntity();
            scenarioActionEntity.setScenarioEntity(scenarioEntity);
            scenarioActionEntity.setSensorEntity(sensorEntity.get());
            scenarioActionEntity.setActionEntity(actionEntity);
            scenarioActionRepository.save(scenarioActionEntity);
        }
        log.info("Сценарий добавлен");
    }

    @Transactional
    public void processingEvent(ScenarioRemovedEvent event) {
        Optional<ScenarioEntity> scenarioOptional = scenarioRepository.findByHubIdAndName(event.getHubId(), event.getName());
        if (scenarioOptional.isPresent()) {
            scenarioRepository.delete(scenarioOptional.get());
            log.info("Сценарий удалён");
        }
    }
}
