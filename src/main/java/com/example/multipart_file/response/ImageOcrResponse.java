package com.example.multipart_file.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : Tonmoy Sikder[20240585] on 9/26/2024
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageOcrResponse {

    @JsonProperty("merchant_name")
    private String merchantName;

    @JsonProperty("date")
    private String date;

    @JsonProperty("time")
    private String time;

    @JsonProperty("mid")
    private String merchantId;

    @JsonProperty("tid")
    private String terminalId;

    @JsonProperty("raw_data")
    private List<DataItem> rawData;

    @Data
    @AllArgsConstructor
    public static class DataItem {
        private String name;
        private Double value;
    }
}


