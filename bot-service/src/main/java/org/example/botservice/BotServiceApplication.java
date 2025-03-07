    package org.example.botservice;

import org.aspectj.weaver.ast.Or;
import org.example.botservice.dao.OrderRepository;
import org.example.botservice.dto.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

    @SpringBootApplication
public class BotServiceApplication {

        public static void main(String[] args) {
            SpringApplication.run(BotServiceApplication.class, args);


        }
    }
