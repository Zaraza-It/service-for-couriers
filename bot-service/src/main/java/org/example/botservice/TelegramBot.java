package org.example.botservice;

import lombok.RequiredArgsConstructor;
import org.example.botservice.repository.OrderRepository;
import org.example.botservice.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer, SpringLongPollingBot {
    private OrderRepository orderRepository;
    private TelegramClient telegramClient;
    private ButtonGenerator buttonGenerator;

    @Value("${telegram.bot-token}")
    private String botToken;

    @Autowired
    public TelegramBot(OrderRepository orderRepository) {
        this.telegramClient = new OkHttpTelegramClient(getBotToken());
        this.orderRepository = orderRepository;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            long chatId = update.getMessage().getChatId();
            List<Order> products = orderRepository.findAll();

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
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }
}