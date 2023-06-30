package com.cbr.bpm.lottery.service;

import com.cbr.bpm.lottery.config.ApplicationProperties;
import com.cbr.bpm.lottery.model.Participant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {

    private final RestTemplate restTemplate;
    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public ParticipantServiceImpl(@Qualifier("authenticatedRestTemplate") RestTemplate restTemplate,
                                  ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Participant> get() {
        return (List<Participant>) restTemplate.getForObject(applicationProperties.getParticipantsUrl(), List.class);
    }

    @Override
    public List<Participant> numerate(List<Participant> participants) throws JsonProcessingException {
        log.warn("Participants: {} {}", applicationProperties.getNumberingUrl(),
                objectMapper.writeValueAsString(participants));
        return (List<Participant>) restTemplate.postForObject(applicationProperties.getNumberingUrl(), participants, List.class);
    }
}
