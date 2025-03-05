package org.example.botservice;

import okhttp3.OkHttpClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

public class MyBot implements LongPollingSingleThreadUpdateConsumer {
TelegramClient telegramClient = 

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update.getMessage().getText());
            SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), update.getMessage().getText());
            try {
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                telegramClient.execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
