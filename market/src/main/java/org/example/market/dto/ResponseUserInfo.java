package org.example.market.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseUserInfo {
    private String username;

    private int sales;

    private int purchases;

    private String roles;

    private byte[] image;

    private String quantityProducts;
}
