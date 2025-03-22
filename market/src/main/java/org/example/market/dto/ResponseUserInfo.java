package org.example.market.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseUserInfo {
    private String username;

    private String sales;

    private String purchases;
}
