package org.example.securityservice;

import org.example.market.MarketApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class TestMarketApplication {

    public static void main(String[] args) {
        SpringApplication.from(MarketApplication::main).with(TestMarketApplication.class);
    }
}
