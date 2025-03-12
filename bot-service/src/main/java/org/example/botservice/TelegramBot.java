package org.example.botservice;

import org.example.botservice.dao.OrderRepository;
import org.example.botservice.dto.Order;
import org.example.botservice.dto.TableProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer,SpringLongPollingBot {
    public OrderRepository orderRepository;
    public TelegramClient telegramClient;
    @Autowired
    public ButtonGenerator buttonGenerator;
    @Autowired
    public TelegramBot(OrderRepository orderRepository) {
        this.telegramClient = new OkHttpTelegramClient(getBotToken());
        this.orderRepository = orderRepository;
    }


    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            long chatId = update.getMessage().getChatId();
            List<Order> products =  orderRepository.findAll() ;
            System.out.println(products.get(0).getProduct());
            SendMessage sendMessage = new SendMessage(Long.toString(chatId), "Заказы");
                sendMessage.setChatId(Long.toString(chatId));
                sendMessage.setText("Заказы");
                sendMessage.setReplyMarkup(buttonGenerator.inlineKeyboardMarkup(products));
            try {
                telegramClient.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasMessage() && update.getCallbackQuery() != null) {
          String uuidString = update.getCallbackQuery().getData();
          UUID uuid = UUID.fromString(uuidString);
          Order callOrder = orderRepository.findOrderByCallback(uuid);
            if (callOrder != null) {

                SendMessage message = SendMessage.builder()
                        .text(callOrder.toString())
                        .chatId(update.getMessage().getChatId().toString())

                        .build();
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
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
