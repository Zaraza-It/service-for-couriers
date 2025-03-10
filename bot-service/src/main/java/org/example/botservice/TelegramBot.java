package org.example.botservice;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.botservice.dao.OrderRepository;
import org.example.botservice.dto.Order;
import org.glassfish.jersey.internal.inject.ParamConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer,SpringLongPollingBot {
    public OrderRepository orderRepository;
    public TelegramClient telegramClient;
    @Autowired
    public TelegramBot(OrderRepository orderRepository) {
        this.telegramClient = new OkHttpTelegramClient(getBotToken());
        this.orderRepository = orderRepository;
    }


    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage("Huy", update.getMessage().getChatId().toString());
                List<InlineKeyboardRow> keyboardRows = new ArrayList<KeyboardButton>();
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(keyboardRows);
            InlineKeyboardRow keyboard = new

            InlineKeyboardButton you = new InlineKeyboardButton("youtube");

            Buttons.add(you);
            keyboard.add(Buttons)
            keyboardRows.add(keyboard);
            inlineKeyboardMarkup.setKeyboard(keyboardRows);
            message.setReplyMarkup(inlineKeyboardMarkup);
            message.setChatId(update.getMessage().getChatId());

            try {
                telegramClient.execute(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public String getBotToken() {
        return "7429114213:AAE2-zlkX3fzcYUMyqxyFU83cJKMTLLXXyc";
    }


    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }




}
