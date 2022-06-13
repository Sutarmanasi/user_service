package com.cg.employeeapp.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfig {
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 500;
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 100;

    @Value("${http.proxyHost:#{null}}") // proxy.wdf.sap.corp
    private String proxyHost;

    @Value("${http.proxyPort:#0}") // defaults to 0
    private int proxyPort;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(getClientHttpRequestFactory());
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {

        RequestConfig config = getRequestConfig();

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setDefaultRequestConfig(config);

        clientBuilder.setProxy(config.getProxy());

        clientBuilder.setConnectionManager(getConnectionManager());
        // Optionally: setup background thread to close unused connections:
        clientBuilder.evictIdleConnections(15, TimeUnit.MINUTES);
        clientBuilder.evictExpiredConnections();

        CloseableHttpClient client = clientBuilder.build();

        // 1 client - n reused connections, per request a client contexts
        return new HttpComponentsClientHttpRequestFactory(client);
    }

    /**
     * Configures the proxy and the timeouts.
     *
     * Timeout Properties Explained:
     * Connection Timeout
     *      the time to establish the connection with the target host
     *
     * Socket Timeout
     *      the time waiting for data – after the connection was established;
     *      maximum time of inactivity between two data packets
     *
     * Connection Manager Timeout
     *      the time to wait for a connection from the connection manager/pool
     */
    private RequestConfig getRequestConfig() {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setSocketTimeout(4000)
                .setConnectionRequestTimeout(2000);

        if (proxyHost != null && !proxyHost.trim().isEmpty() && proxyPort != 0) {
            LoggerFactory.getLogger(getClass()).info("set proxy: " + proxyHost + ":" + String.valueOf(proxyPort));
            requestConfigBuilder.setProxy(new HttpHost(proxyHost, proxyPort, HttpHost.DEFAULT_SCHEME_NAME));
        }
        return requestConfigBuilder.build();
    }

    /**
     * Optimization: reuse connection to the same target host for subsequent requests.
     */
    private PoolingHttpClientConnectionManager getConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE); // per target host

        return connectionManager;
    }
}
