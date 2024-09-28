package com.example.multipart_file.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : Tonmoy Sikder[20240585] on 9/26/2024
 */

@Data
@Builder
public class ImageOcrRequest {
    private MultipartFile image;
}
