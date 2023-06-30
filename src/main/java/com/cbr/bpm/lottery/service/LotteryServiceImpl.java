package com.cbr.bpm.lottery.service;

import com.cbr.bpm.lottery.config.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class LotteryServiceImpl implements LotteryService {

    private final RestTemplate restTemplate;
    private final ApplicationProperties applicationProperties;

    public LotteryServiceImpl(@Qualifier("authenticatedRestTemplate") RestTemplate restTemplate,
                              ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void start() {
        restTemplate.postForLocation(applicationProperties.getLotteryUrl() + "/start", null);
    }

    @Override
    public void stop() {
        restTemplate.postForLocation(applicationProperties.getLotteryUrl() + "/offline", null);
    }
}
