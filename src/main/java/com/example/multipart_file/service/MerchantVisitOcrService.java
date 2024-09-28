package com.example.multipart_file.service;


import com.example.multipart_file.config.WebServiceManager;
import com.example.multipart_file.request.ImageOcrRequest;
import com.example.multipart_file.response.ImageOcrResponse;
import com.example.multipart_file.response.MerchantVisitOcrResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author : Tonmoy Sikder[20240585] on 9/26/2024
 */

@Service
@RequiredArgsConstructor
public class MerchantVisitOcrService {

    private final WebServiceManager webServiceManager;

    public MerchantVisitOcrResponse performImageOCR(MultipartFile image) throws JsonProcessingException {
        if (Objects.isNull(image) || image.isEmpty()) {
            throw new RuntimeException("Please upload a valid image.");
        }

        final ImageOcrRequest imageOcrRequest = ImageOcrRequest.builder()
                                                               .image(image)
                                                               .build();

        //final ImageOcrResponse imageOcrResponse = webServiceManager.postMultipartFormData("http://localhost:2018/multipart-image/image-ocr", image, ImageOcrResponse.class);
        final ImageOcrResponse imageOcrResponse = webServiceManager.postMultipartFormData("http://127.0.0.1:5000/upload", image, ImageOcrResponse.class);

        //Mock Data prepare
        final MerchantVisitOcrResponse ocrData = MerchantVisitOcrResponse.builder()
                                                                    .merchantId("CBL13021")
                                                                    .merchantName("Shwapno gulshan-1")
                                                                    .terminalId("123456")
                                                                    .merchantVisitDate("11/11/2024 11:11:00PM")
                                                                    .build();
        return ocrData;
    }
}
