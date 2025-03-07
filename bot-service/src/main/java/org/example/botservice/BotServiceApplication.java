    package org.example.botservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class BotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotServiceApplication.class, args);
        try {

            final String token = "7429114213:AAE2-zlkX3fzcYUMyqxyFU83cJKMTLLXXyc";
            TelegramBotsLongPollingApplication botsLongPollingApplication = new TelegramBotsLongPollingApplication();
            botsLongPollingApplication.registerBot(token, new MBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
