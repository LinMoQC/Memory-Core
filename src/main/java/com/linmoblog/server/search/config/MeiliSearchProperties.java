package com.linmoblog.server.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Poison02
 * @date 2024/4/7
 */
@Data
@Component
@ConfigurationProperties(prefix = "blog.search")
public class MeiliSearchProperties {

    private String host;

    private String apiKey;

}
