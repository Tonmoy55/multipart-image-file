package com.example.multipart_file.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebServiceManager {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public <T, R> R postRequest(String uri, T requestBody, Class<R> responseType) throws JsonProcessingException {
        log.info("Sending POST request to URI: {}", uri);
        logRequestBody(requestBody);

        final R response = webClient.post()
                                    .uri(uri)
                                    .body(Mono.just(requestBody), requestBody.getClass())
                                    .retrieve()
                                    .bodyToMono(responseType)
                                    .block();

        logResponseBody(response);
        return response;
    }

    public <T, R> R getRequest(String uri, Class<R> responseType) throws JsonProcessingException {
        log.info("Sending GET request to URI: {}", uri);

        final R response = webClient.get()
                                    .uri(uri)
                                    .retrieve()
                                    .bodyToMono(responseType)
                                    .block();

        logResponseBody(response);
        return response;
    }

    private <T> void logRequestBody(T requestBody) throws JsonProcessingException {
        if (requestBody instanceof String) {
            log.info("Request Body: {}", requestBody);
        } else {
            final String prettyPrintedRequestBody = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(requestBody);
            log.info("Request Body for: {}", prettyPrintedRequestBody);
        }
    }

    private <R> void logResponseBody(R responseBody) throws JsonProcessingException {

        if (responseBody instanceof String) {
            log.info("Response Body: {}", responseBody);
        } else {
            final String prettyPrintedResponseBody = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(responseBody);
            log.info("Response Body: {}", prettyPrintedResponseBody);
        }
    }

    public <R> R postMultipartFormData(String uri, MultipartFile imageFile, Class<R> responseType) throws JsonProcessingException {
        log.info("Sending POST request to URI: {}", uri);
        log.info("Sending image file: {}", imageFile.getOriginalFilename());

        try {
            // Create multipart body with image
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("file", imageFile.getResource())
                    .contentType(MediaType.IMAGE_JPEG); // Adjust content type based on the file type

            final R response = webClient.post()
                                        .uri(uri)
                                        .contentType(MediaType.MULTIPART_FORM_DATA)
                                        .body(BodyInserters.fromMultipartData(builder.build()))
                                        .retrieve()
                                        .bodyToMono(responseType)
                                        .block();

            logResponseBody(response);
            return response;
        } catch (Exception e) {
            log.error("Error occurred while sending multipart/form-data request", e);
            throw new RuntimeException("Failed to send multipart request", e);
        }
    }
}
