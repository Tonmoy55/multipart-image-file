package com.example.multipart_file.response;

import lombok.Builder;
import lombok.Data;


/**
 * @author : Tonmoy Sikder[20240585] on 9/26/2024
 */

@Data
@Builder
public class MerchantVisitOcrResponse {
    private String merchantId;
    private String merchantName;
    private String terminalId;
    private String merchantVisitDate;
}
