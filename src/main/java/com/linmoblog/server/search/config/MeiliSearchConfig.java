package com.linmoblog.server.search.config;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Poison02
 * @date 2024/4/7
 */
@Configuration
public class MeiliSearchConfig {

    @Resource
    private MeiliSearchProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public Client client() {
        return new Client(new Config(properties.getHost(), properties.getApiKey()));
    }

}
