package org.example.botservice;

import org.example.botservice.dao.OrderRepository;
import org.example.botservice.dto.ProductOnly;
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

import java.util.List;

@Component
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer,SpringLongPollingBot {
    public OrderRepository orderRepository;
    public TelegramClient telegramClient;
    public ButtonGenerator buttonGenerator;
    @Autowired
    public TelegramBot(OrderRepository orderRepository) {
        this.telegramClient = new OkHttpTelegramClient(getBotToken());
        this.orderRepository = orderRepository;
        this.buttonGenerator = new ButtonGenerator();
    }


    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            long chatId = update.getMessage().getChatId();
            List<TableProduct> products =  orderRepository.findAllProduct();
            System.out.println(products);
            SendMessage sendMessage = new SendMessage(Long.toString(chatId), "Заказы");
                sendMessage.setChatId(Long.toString(chatId));
                sendMessage.setText("Заказы");
                orderRepository.findAll().get().getProduct();
                sendMessage.setReplyMarkup(buttonGenerator.inlineKeyboardMarkup(products));
            try {
                telegramClient.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasMessage() && update.getMessage().getText().equals("stop")) {

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
