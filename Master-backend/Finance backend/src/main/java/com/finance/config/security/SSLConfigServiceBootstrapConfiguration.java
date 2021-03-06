package com.finance.config.security;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import javax.net.ssl.SSLContext;
import org.apache.http.ssl.SSLContexts;
import org.springframework.web.client.RestTemplate;


@Configuration
public class SSLConfigServiceBootstrapConfiguration {

    private final ConfigClientProperties properties;

    @Value("${server.ssl.key-store-password}")
    private String keystorePass;
    @Value("${server.ssl.key-store}")
    private String keystorePath;

    public SSLConfigServiceBootstrapConfiguration(ConfigClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ConfigServicePropertySourceLocator configServicePropertySourceLocator() throws Exception {
        final char[] password = "master20202fa".toCharArray();
        final ClassPathResource resource = new ClassPathResource("keystore.jks");

        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(resource.getFile(), password, password)
                .loadTrustMaterial(resource.getFile(), password, new TrustSelfSignedStrategy()).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier((s, sslSession) -> true)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        ConfigServicePropertySourceLocator configServicePropertySourceLocator = new ConfigServicePropertySourceLocator(properties);
        configServicePropertySourceLocator.setRestTemplate(new RestTemplate(requestFactory));
        return configServicePropertySourceLocator;
    }
}
