package com.example.multipart_file.controller;

import com.example.multipart_file.response.MerchantVisitOcrResponse;
import com.example.multipart_file.service.MerchantVisitOcrService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author : Tonmoy Sikder[20240585] on 9/27/2024
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/multipart-image")
public class MultipartController {

    private final MerchantVisitOcrService merchantVisitOcrService;

    @PostMapping("/image-ocr")
    public ResponseEntity<?> performImageOCR(@RequestParam("file") MultipartFile image) throws JsonProcessingException {
        log.info("[MultipartController:performImageOCR] uploading image for OCR by userName: {}", "tonmoy");

        final MerchantVisitOcrResponse merchantVisitOcrResponse = merchantVisitOcrService.performImageOCR(image);

        return new ResponseEntity<>(merchantVisitOcrResponse, HttpStatus.OK);
    }

    @PostMapping("/image-upload")
    public ResponseEntity<?> imageUpload(@RequestParam("file") MultipartFile image) throws JsonProcessingException {
        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
        }

        try {
            String resourcePath = new File("src/main/resources/").getAbsolutePath();
            String uploadDir = resourcePath + "/uploads/";
            File uploadDirFile = new File(uploadDir);

            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            // Save the file
            File imageFile = new File(uploadDir + image.getOriginalFilename());
            image.transferTo(imageFile);

            return ResponseEntity.ok("File uploaded successfully: " + imageFile.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }
}
