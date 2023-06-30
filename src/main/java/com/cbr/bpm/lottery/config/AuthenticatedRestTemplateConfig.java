package com.cbr.bpm.lottery.config;

import com.cbr.bpm.lottery.client.RestTemplateAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AuthenticatedRestTemplateConfig {

    private final ApplicationProperties applicationProperties;

    public AuthenticatedRestTemplateConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean(name = "authenticatedRestTemplate")
    public RestTemplate authenticatedRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors
                = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new RestTemplateAuthInterceptor(applicationProperties));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

}
