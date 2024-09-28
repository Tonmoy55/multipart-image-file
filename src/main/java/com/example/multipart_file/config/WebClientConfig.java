package com.example.multipart_file.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {


    @Bean
    public WebClient webClient() {
        final int connectTimeout = 30000;
        final int readTimeout = 60000;

        final HttpClient httpClient = HttpClient.create()
                                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                                                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                                                                           .addHandlerLast(new WriteTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)));
        return WebClient.builder()
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();
    }
}
